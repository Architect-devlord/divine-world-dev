package com.divineworld.ai.goap;

import com.divineworld.npc.DWBot;

import java.util.*;

/**
 * GoalPlanner - simple GOAP-style planner that:
 *  - accepts need biases via applyNeeds()
 *  - ranks candidate goals
 *  - expands a plan (sequence of Actions)
 *  - steps through the current plan each tick
 *
 * Designed to be conservative and easy to extend.
 *
 * NOTE: Action implementations must call back into DWBot to perform world actions.
 */
public class GoalPlanner {

    private final DWBot bot;

    // last bias map pushed from Brain (goal -> bias)
    private Map<String, Double> lastBiasMap = new HashMap<>();

    // the current plan: queue of actions to perform
    private final Deque<Action> currentPlan = new ArrayDeque<>();

    // small registry of goal -> plan generator (can be extended at runtime)
    private final Map<String, PlanFactory> goalFactories = new HashMap<>();

    public GoalPlanner(DWBot bot) {
        this.bot = bot;
        registerDefaultGoals();
    }

    /** Allow brain to push needs/bias influence */
    public void applyNeeds(Map<String, Double> needs) {
        if (needs == null) return;
        this.lastBiasMap = new HashMap<>(needs);
    }

    /** Optional fallback context setter */
    public void setContext(Map<String, Object> ctx) {
        if (ctx == null) return;
        Object o = ctx.get("needs");
        if (o instanceof Map) {
            //noinspection unchecked
            applyNeeds((Map<String, Double>) o);
        }
    }

    public Map<String, Double> getLastBiasMap() {
        return Collections.unmodifiableMap(lastBiasMap);
    }

    /** Called by Brain when BT did nothing. Planner should attempt to act one step. */
    public void tick() {
        // If we have a current plan, try to run next action
        if (!currentPlan.isEmpty()) {
            Action a = currentPlan.peek();
            try {
                Action.Status s = a.run(bot);
                if (s == Action.Status.SUCCESS) {
                    currentPlan.poll(); // remove completed action
                } else if (s == Action.Status.RUNNING) {
                    // keep running next tick
                } else if (s == Action.Status.FAILURE) {
                    // plan broken — clear and replan next tick
                    currentPlan.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
                currentPlan.clear();
            }
            return;
        }

        // No plan -> choose a goal
        String chosenGoal = chooseTopGoal();
        if (chosenGoal == null) return;

        PlanFactory factory = goalFactories.get(chosenGoal);
        if (factory != null) {
            List<Action> plan = factory.createPlan(bot);
            if (plan != null && !plan.isEmpty()) {
                currentPlan.addAll(plan);
            }
        }
    }

    /** Choose goal by highest bias (simple heuristic). Returns null if none. */
    private String chooseTopGoal() {
        if (lastBiasMap == null || lastBiasMap.isEmpty()) return null;
        return lastBiasMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /** Expose current plan for debugging */
    public List<String> getCurrentPlanIds() {
        List<String> ids = new ArrayList<>();
        for (Action a : currentPlan) ids.add(a.getId());
        return ids;
    }

    /** Register some simple default goals and plan factories.
     *  These are small example stubs that call placeholder DWBot methods.
     *  Replace the inner Actions with real implementations integrated with your bot.
     */
    private void registerDefaultGoals() {
        // Explore goal: wander to new area, maybe map it
        goalFactories.put("explore", (bot) -> {
            List<Action> plan = new ArrayList<>();
            plan.add(new Action("Navigate_To_Unknown", (b) -> {
                // Use your pathfinder: navigate to unexplored coordinate
                b.getNavigation().wanderRandomly();
                return Action.Status.SUCCESS;
            }));
            plan.add(new Action("Mark_Landmark", (b) -> {
                b.getMemory().remember("found_landmark", "location", 0.8);
                return Action.Status.SUCCESS;
            }));
            return plan;
        });

        // Hunger goal: find food and eat
        goalFactories.put("hunger", (bot) -> {
            List<Action> plan = new ArrayList<>();
            plan.add(new Action("Find_Food", (b) -> {
                // Example: find nearest chest/crop with food
                boolean found = b.getNavigation().navigateToNearest("food");
                return found ? Action.Status.SUCCESS : Action.Status.FAILURE;
            }));
            plan.add(new Action("Consume_Food", (b) -> {
                // Example: consume an item in inventory
                boolean ate = b.getInventory().consumeOne("food");
                if (ate) {
                    b.getMotivation().fulfill("hunger", 0.7);
                    b.getEmotion().adjust("joy", 0.05);
                    return Action.Status.SUCCESS;
                } else {
                    return Action.Status.FAILURE;
                }
            }));
            return plan;
        });

        // Safety goal: run to shelter
        goalFactories.put("safety", (bot) -> {
            List<Action> plan = new ArrayList<>();
            plan.add(new Action("FleeToShelter", (b) -> {
                boolean ok = b.getNavigation().navigateTo("home_base");
                return ok ? Action.Status.SUCCESS : Action.Status.RUNNING;
            }));
            return plan;
        });

        // Socialize/Trade
        goalFactories.put("social", (bot) -> {
            List<Action> plan = new ArrayList<>();
            plan.add(new Action("Find_Peer", (b) -> {
                boolean found = b.getSocial().findNearestFriend();
                return found ? Action.Status.SUCCESS : Action.Status.FAILURE;
            }));
            plan.add(new Action("TalkOrTrade", (b) -> {
                b.getTradeEngine().attemptTradeWithNearest();
                b.getEmotion().adjust("joy", 0.1);
                return Action.Status.SUCCESS;
            }));
            return plan;
        });
    }

    /** Simple factory functional interface */
    private interface PlanFactory {
        List<Action> createPlan(DWBot bot);
    }
}
