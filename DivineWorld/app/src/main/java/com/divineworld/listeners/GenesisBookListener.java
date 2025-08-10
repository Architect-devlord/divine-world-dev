package com.divineworld.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import com.divineworld.commands.GenesisManager;

import org.bukkit.event.block.Action;

public class GenesisBookListener implements Listener {
    private final Plugin plugin;

    public GenesisBookListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (!GenesisManager.isGenesisBook(item, plugin)) return;

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            event.setCancelled(true);
            if (GenesisManager.canUseGenesis(player)) {
                GenesisManager.triggerGenesis(player);
                item.setAmount(item.getAmount() - 1);
            } else if (GenesisManager.canUseDivineReset(player)) {
                GenesisManager.triggerDivineReset(player);
                item.setAmount(item.getAmount() - 1);
            } else {
                player.sendMessage("Genesis/Divine Reset is on cooldown or not available.");
            }
        }
    }
}