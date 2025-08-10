package com.divineworld.listeners;

import com.divineworld.utils.BookFactory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class OracleSystem implements Listener {

    private final Set<UUID> tutorialCompleted = new HashSet<>();

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!tutorialCompleted.contains(player.getUniqueId())) {
            Location front = player.getLocation().add(player.getLocation().getDirection().multiply(3));
            LivingEntity oracle = (LivingEntity) player.getWorld().spawnEntity(front, EntityType.WANDERING_TRADER);
            oracle.setCustomName("§dOracle");
            oracle.setCustomNameVisible(true);
            oracle.setAI(false);
            oracle.setInvulnerable(true);

            player.sendMessage("§dThe Oracle appears before you...");
            player.sendMessage("§7Say 'I know' in chat if you wish to skip the tutorial.");

            runTutorial(player, oracle);
        }
    }

    private void runTutorial(Player player, LivingEntity oracle) {
        new BukkitRunnable() {
            int step = 0;

            public void run() {
                if (tutorialCompleted.contains(player.getUniqueId())) {
                    cancel();
                    return;
                }

                switch (step++) {
                    case 0 -> player.sendMessage("§e[Oracle] Welcome, divine one. I will teach you how this world breathes.");
                    case 1 -> player.sendMessage("§e[Oracle] Tribes form through Genesis. They grow, evolve, worship, and fall.");
                    case 2 -> player.sendMessage("§e[Oracle] Your will shapes their culture, belief, and destiny.");
                    case 3 -> player.sendMessage("§e[Oracle] You may reset the world, but only when all life has ended or genesis is used.");
                    case 4 -> player.sendMessage("§e[Oracle] Interact with me after this to receive sacred texts.");
                    case 5 -> {
                        player.sendMessage("§aThe tutorial has ended. Right-click the Oracle.");
                        cancel();
                    }
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("DivineWorld"), 60L, 100L);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.getMessage().equalsIgnoreCase("I know")) {
            Player player = event.getPlayer();
            tutorialCompleted.add(player.getUniqueId());
            player.sendMessage("§aTutorial skipped. Right-click the Oracle to receive your books.");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onOracleInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getCustomName() != null &&
            event.getRightClicked().getCustomName().equals("§dOracle")) {

            Player player = event.getPlayer();

            if (!player.getInventory().contains(BookFactory.genesisCodex())) {
                player.getInventory().addItem(BookFactory.genesisCodex());
                player.getInventory().addItem(BookFactory.firstFlameBook());
                player.sendMessage("§bYou have received the Genesis Codex and Teachings of the First Flame.");
            }
        }
    }
}