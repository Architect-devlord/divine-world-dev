package com.divineworld.god;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class GodManager {

    private static final Set<String> godPlayers = new HashSet<>();

    public static boolean toggleGodMode(Player player) {
        if (godPlayers.contains(player.getName())) {
            godPlayers.remove(player.getName());
            return false;
        } else {
            godPlayers.add(player.getName());
            return true;
        }
    }

    public static boolean isGod(Player player) {
        return godPlayers.contains(player.getName());
    }

    public static void spawnBotGod(org.bukkit.Location location) {
        // Will later spawn disguised bot with miracle abilities
    }
}
