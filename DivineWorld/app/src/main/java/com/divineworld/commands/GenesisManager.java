package com.divineworld.commands;

import com.divineworld.npc.NPCManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class GenesisManager {
    private static boolean genesisUsed = false;
    private static boolean divineResetUsed = false;
    private static long genesisCooldownEnd = 0;
    private static long divineResetCooldownEnd = 0;
    private static final long MC_DAY_MS = 20 * 60 * 1000; // 1 MC day = 20 min real time

    public static void giveGenesisBook(Player player, Plugin plugin) {
        ItemStack genesisBook = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) genesisBook.getItemMeta();
        meta.setTitle("Genesis");
        meta.setAuthor("Oracle");
        meta.setLore(List.of("Tap the ground to begin civilization.", "Use with care!"));
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "genesis_book"), PersistentDataType.BYTE, (byte)1);
        genesisBook.setItemMeta(meta);
        player.getInventory().addItem(genesisBook);
    }

    public static boolean canUseGenesis(Player player) {
        return !genesisUsed && System.currentTimeMillis() > genesisCooldownEnd;
    }

    public static void triggerGenesis(Player player) {
        Location loc = player.getLocation().add(player.getLocation().getDirection().multiply(2));
        NPCManager.spawnNPC(loc, "male");
        NPCManager.spawnNPC(loc, "female");
        genesisUsed = true;
        genesisCooldownEnd = System.currentTimeMillis() + (3 * MC_DAY_MS);
        player.sendMessage("🌱 Genesis begins. The first tribe is born.");
        player.sendTitle("🌍 The World Begins", "You are the First Spark 🔥", 10, 70, 20);
    }

    public static boolean canUseDivineReset(Player player) {
        return genesisUsed && !divineResetUsed && System.currentTimeMillis() > divineResetCooldownEnd;
    }

    public static void triggerDivineReset(Player player) {
        NPCManager.killAllNPCs();
        divineResetUsed = true;
        divineResetCooldownEnd = System.currentTimeMillis() + (3 * MC_DAY_MS);
        genesisUsed = false; // Allow Genesis after reset
        player.sendMessage("⚡ Divine Reset: All NPCs have perished. You may use Genesis again after cooldown.");
    }

    public static boolean isGenesisBook(ItemStack item, Plugin plugin) {
        if (item == null || item.getType() != Material.WRITTEN_BOOK) return false;
        BookMeta meta = (BookMeta) item.getItemMeta();
        return meta != null && meta.getPersistentDataContainer().has(new NamespacedKey(plugin, "genesis_book"), PersistentDataType.BYTE);
    }
}