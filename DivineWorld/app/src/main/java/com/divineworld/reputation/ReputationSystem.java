package com.divineworld.reputation;

import java.util.*;

public class ReputationSystem {
    private static final Map<String, Map<String, Double>> rep = new HashMap<>();

    public static void adjust(String tribe, String player, double amount) {
        rep.putIfAbsent(tribe, new HashMap<>());
        rep.get(tribe).put(player, rep.get(tribe).getOrDefault(player, 0.0) + amount);
    }

    public static double get(String tribe, String player) {
        return rep.getOrDefault(tribe, Collections.emptyMap()).getOrDefault(player, 0.0);
    }
}