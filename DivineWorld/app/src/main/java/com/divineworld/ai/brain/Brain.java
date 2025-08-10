package com.divineworld.ai.brain;

import com.divineworld.ai.goap.GoalPlanner;
import com.divineworld.ai.tree.BehaviorTree;
import com.divineworld.memory.MemoryBank;
import com.divineworld.emotion.EmotionState;
import com.divineworld.motivation.MotivationEngine;
import com.divineworld.npc.DWBot;

import java.io.*;
import java.util.*;

/**
 * Brain: Controls NPC decision-making by combining
 * GOAP (Goal-Oriented Action Planning) and Behavior Trees.
 */
public class Brain {

    private final DWBot owner;
    private final GoalPlanner planner;
    private final BehaviorTree tree;
    private BrainData data;

    public Brain(DWBot owner) {
        this.owner = owner;
        this.planner = new GoalPlanner(owner);
        this.tree = new BehaviorTree(owner);
        this.data = new BrainData();
    }

    /**
     * Main AI loop called every tick from DWBot.
     */
    public void planAndAct() {
        // 1. Build bias from needs, emotions, and memories
        Map<String, Double> biasMap = buildNeedBias();

        // 2. Push bias into planner
        pushBiasToPlanner(biasMap);
        

        // 3. Run Behavior Tree first, fallback to GOAP
        boolean acted = false;
        try {
            acted = tree.tick();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!acted) {
            try {
                planner.tick();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Builds a bias map for goal weighting based on NPC state.
     */
    private Map<String, Double> buildNeedBias() {
        Map<String, Double> bias = new HashMap<>();

        // From motivation needs
        MotivationEngine motivation = owner.getMotivation();
        motivation.getAllNeeds().forEach((need, value) -> {
            bias.put(need.name().toLowerCase(), value);
        });

        // From emotion intensity
        EmotionState emotion = owner.getEmotion();
        emotion.getAllEmotions().forEach((emo, value) -> {
            bias.merge(emo.name().toLowerCase(), value * 0.5, Double::sum);
        });

        // From memory
        Map<String, Double> memBias = deriveBiasFromRecentMemories();
        memBias.forEach((k, v) -> bias.merge(k, v, Double::sum));

        return bias;
    }

    /**
     * Looks at recent memories to add extra bias factors.
     */
    private Map<String, Double> deriveBiasFromRecentMemories() {
        Map<String, Double> bias = new HashMap<>();
        MemoryBank mem = owner.getMemory();
        mem.getRecentMemories(10).forEach(memory -> {
            if (memory.contains("combat")) bias.merge("safety", 1.0, Double::sum);
            if (memory.contains("gift")) bias.merge("social", 0.5, Double::sum);
        });
        return bias;
    }

    /**
     * Pushes bias/context into the planner via supported methods.
     */
    private void pushBiasToPlanner(Map<String, Double> bias) {
        try {
            planner.getClass().getMethod("applyNeeds", Map.class).invoke(planner, bias);
        } catch (NoSuchMethodException ignored) {
            try {
                planner.getClass().getMethod("setContext", Map.class).invoke(planner, bias);
            } catch (NoSuchMethodException ignored2) {
                // Try reflection into bias map
                try {
                    var field = planner.getClass().getDeclaredField("lastBiasMap");
                    field.setAccessible(true);
                    field.set(planner, bias);
                } catch (Exception ignored3) {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves minimal AI state to file.
     */
    public void saveToFile() {
        File dir = new File("plugins/DivineWorld/brains");
        if (!dir.exists()) dir.mkdirs();
        File f = new File(dir, owner.getUUID() + ".dat");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f))) {
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads minimal AI state from file.
     */
    public void loadFromFile() {
        File f = new File("plugins/DivineWorld/brains", owner.getUUID() + ".dat");
        if (!f.exists()) return;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = in.readObject();
            if (obj instanceof BrainData) {
                this.data = (BrainData) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static class BrainData implements Serializable {
        public int version = 1;
        public String lastActiveGoal = null;
        public Map<String, Double> persistentGoalWeights = new HashMap<>();
    }
}
