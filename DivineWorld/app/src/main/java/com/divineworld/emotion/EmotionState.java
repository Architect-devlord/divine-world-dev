package com.divineworld.emotion;

import com.divineworld.npc.DWBot;

import java.util.*;

public class EmotionState {

    private final DWBot bot;
    private final Map<String, Double> emotions = new HashMap<>();

    public EmotionState(DWBot bot) {
        this.bot = bot;
        emotions.put("joy", 0.5);
        emotions.put("fear", 0.5);
        emotions.put("anger", 0.5);
        emotions.put("grief", 0.0);
        emotions.put("hope", 0.5);
    }

    public void regulate() {
        // Emotions decay or intensify based on memory and events
        for (var key : emotions.keySet()) {
            emotions.put(key, clamp(emotions.get(key) * 0.99, 0.0, 1.0));
        }
    }

    public void adjust(String type, double delta) {
        emotions.put(type, clamp(emotions.getOrDefault(type, 0.5) + delta, 0.0, 1.0));
    }

    public double get(String type) {
        return emotions.getOrDefault(type, 0.5);
    }

    private double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    public Map<String, Double> snapshot() {
        return new HashMap<>(emotions);
    }
}
