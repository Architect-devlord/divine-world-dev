package com.divineworld.motivation;

import com.divineworld.npc.DWBot;

public class MotivationEngine {

    private final DWBot bot;
    private double hunger = 0.2;
    private double tiredness = 0.1;
    private double curiosity = 0.6;

    public MotivationEngine(DWBot bot) {
        this.bot = bot;
    }

    public void updateNeeds() {
        hunger = clamp(hunger + 0.01, 0, 1);
        tiredness = clamp(tiredness + 0.005, 0, 1);
        curiosity = clamp(curiosity + 0.002, 0, 1);
    }

    public double get(String type) {
        return switch (type) {
            case "hunger" -> hunger;
            case "tiredness" -> tiredness;
            case "curiosity" -> curiosity;
            default -> 0.5;
        };
    }

    public void fulfill(String type, double amount) {
        switch (type) {
            case "hunger" -> hunger = clamp(hunger - amount, 0, 1);
            case "tiredness" -> tiredness = clamp(tiredness - amount, 0, 1);
            case "curiosity" -> curiosity = clamp(curiosity - amount, 0, 1);
        }
    }

    private double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}
