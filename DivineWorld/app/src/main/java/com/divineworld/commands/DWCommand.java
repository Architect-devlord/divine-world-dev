package com.divineworld.commands;

import com.divineworld.debug.DebugTools;
import com.divineworld.tribe.TribeSystem;
import com.divineworld.god.GodManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class DWCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (args.length == 0) {
            player.sendMessage("📘 Divine World Commands:");
            player.sendMessage("/dw spawnTribe");
            player.sendMessage("/dw spawnGod");
            player.sendMessage("/dw debug");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "spawntribe" -> {
                TribeSystem.spawnNewTribe(player.getLocation());
                player.sendMessage("🏕️ Tribe created at your location.");
            }
            case "spawngod" -> {
                GodManager.spawnBotGod(player.getLocation());
                player.sendMessage("🌀 A divine being emerges.");
            }
            case "debug" -> {
                DebugTools.openDebugGUI(player);
            }
            case "journal" -> {
                DiscoveryJournalUI.open(player);
                player.sendMessage("Discovery Journal opened");
            }

            default -> {
                player.sendMessage("❌ Unknown subcommand. Use /dw for help.");
            }
        }

        return true;
    }
}
