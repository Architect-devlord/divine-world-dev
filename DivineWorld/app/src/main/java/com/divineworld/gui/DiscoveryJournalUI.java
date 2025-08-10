package com.divineworld.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class DiscoveryJournalUI {
    public static void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, "📘 Discovery Journal");
        // You can add custom items here later
        player.openInventory(gui);
    }
}
