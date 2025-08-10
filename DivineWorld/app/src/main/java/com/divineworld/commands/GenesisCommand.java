package com.divineworld.commands;

import com.divineworld.listeners.OracleSystem;
import com.divineworld.commands.GenesisManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GenesisCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (GenesisManager.canUseGenesis(player)) {
            GenesisManager.triggerGenesis(player);
        } else {
            player.sendMessage("Genesis is on cooldown or already used.");
        }
        return true;
    }
}