package com.divineworld.registry;

import java.util.HashMap;
import java.util.Map;

public class BehaviorRegistry {

    private static final Map<String, Class<?>> registeredBehaviors = new HashMap<>();

    public static void init() {
        register("farming", com.divineworld.ai.behaviors.FarmingBehavior.class);
        register("crafting", com.divineworld.ai.behaviors.CraftingBehavior.class);
        register("ritual", com.divineworld.ai.behaviors.RitualBehavior.class);
        register("gossip", com.divineworld.ai.behaviors.GossipBehavior.class);
        register("explore", com.divineworld.ai.behaviors.ExploreBehavior.class);
        register("combat", com.divineworld.ai.behaviors.CombatBehavior.class);
        register("trade", com.divineworld.ai.behaviors.TradeBehavior.class);
        register("scribe", com.divineworld.ai.behaviors.ScribeBehavior.class);
    }

    public static void register(String name, Class<?> clazz) {
        registeredBehaviors.put(name, clazz);
    }

    public static Class<?> getBehavior(String name) {
        return registeredBehaviors.get(name);
    }
}
