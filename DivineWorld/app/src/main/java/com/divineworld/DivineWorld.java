package com.divineworld;

import com.divineworld.utils.ConfigLoader;
import com.divineworld.listeners.WorldLoadListener;
import com.divineworld.listeners.FirstJoinOracleSpawn;
import com.divineworld.listeners.GenesisBookListener;
import com.divineworld.registry.*;
import com.divineworld.commands.DWCommand;
import com.divineworld.commands.GenesisCommand;
import com.divineworld.commands.GodModeCommand;

import java.io.ObjectInputFilter.Config;

import org.bukkit.plugin.java.JavaPlugin;

public class DivineWorld extends JavaPlugin {

    private static DivineWorld instance;

    public static DivineWorld getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        ConfigLoader.load();

        getLogger().info("🔥 Divine World is booting up...");

        
        getServer().getPluginManager().registerEvents(new GenesisBookListener(this), this);

        // 🔧 Register Core Systems
        BehaviorRegistry.init();
        BotRegistry.init();
        WorldSystemRegistry.init();
        RitualRegistry.init();
        DoctrineRegistry.init();
        TribeRegistry.init();
        GodRegistry.init();

        // 💬 Register Commands
        getCommand("dw").setExecutor(new DWCommand());
        getCommand("genesis").setExecutor(new GenesisCommand());
        getCommand("godmode").setExecutor(new GodModeCommand());

        getServer().getPluginManager().registerEvents(new WorldLoadListener(), this);
	getServer().getPluginManager().registerEvents(new FirstJoinOracleSpawn(), this);


        // 📡 Schedule Background Tasks (NPC logic, evolution, emotion, etc.)
        TaskScheduler.startAll();

        getLogger().info("✅ Divine World boot complete.");
    }

    @Override
    public void onDisable() {
        getLogger().info("💤 Divine World shutting down...");
        TaskScheduler.stopAll();
    }
}
