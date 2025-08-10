package com.divineworld.trade;

import org.bukkit.Material;

public class TradeOffer {
    public Material giveItem;
    public int giveAmount;
    public Material takeItem;
    public int takeAmount;

    public TradeOffer(Material giveItem, int giveAmount, Material takeItem, int takeAmount) {
        this.giveItem = giveItem;
        this.giveAmount = giveAmount;
        this.takeItem = takeItem;
        this.takeAmount = takeAmount;
    }

    public String toString() {
        return giveAmount + "x " + giveItem.name() + " for " + takeAmount + "x " + takeItem.name();
    }
}
