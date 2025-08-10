package com.divineworld.trade;

import java.util.*;

public class TradeEngine {
    private final Map<String, Double> itemValues = new HashMap<>();

    public TradeEngine() {
        // Default values (can evolve later)
        itemValues.put("WHEAT", 1.0);
        itemValues.put("IRON_INGOT", 5.0);
        itemValues.put("DIAMOND", 15.0);
        itemValues.put("POTATO", 1.2);
        itemValues.put("EMERALD", 12.0);
    }

    public boolean isFair(TradeOffer offer) {
        double giveValue = offer.giveAmount * itemValues.getOrDefault(offer.giveItem.name(), 1.0);
        double takeValue = offer.takeAmount * itemValues.getOrDefault(offer.takeItem.name(), 1.0);
        return Math.abs(giveValue - takeValue) <= 3.0;
    }

    public void learn(TradeOffer offer, boolean accepted) {
        double ratio = (double) offer.takeAmount / offer.giveAmount;
        if (accepted) {
            // Value of given item increases slightly
            itemValues.put(offer.giveItem.name(), itemValues.getOrDefault(offer.giveItem.name(), 1.0) * 1.05);
        } else {
            // Decrease if rejected
            itemValues.put(offer.giveItem.name(), itemValues.getOrDefault(offer.giveItem.name(), 1.0) * 0.95);
        }
    }
}
