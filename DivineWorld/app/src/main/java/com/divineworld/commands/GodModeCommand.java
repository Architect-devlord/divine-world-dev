package com.divineworld.commands;

import com.divineworld.god.GodManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        boolean isNowGod = GodManager.toggleGodMode(player);

        player.sendMessage(isNowGod
            ? "✨ You have entered God Mode. Use your powers wisely."
            : "🔕 You have returned to mortal form.");

        return true;
    }
}
