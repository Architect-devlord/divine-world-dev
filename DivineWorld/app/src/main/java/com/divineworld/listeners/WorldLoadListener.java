package com.divineworld.listeners;

import com.divineworld.god.GodSystem;
import com.divineworld.story.StoryManager;
import com.divineworld.tribe.TribeSystem;
import com.divineworld.explorer.ExplorationSystem;
import com.divineworld.registry.DivineSystemRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.World;

public class WorldLoadListener implements Listener {

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        World world = event.getWorld();
        DivineSystemRegistry.initializeWorld(world);
        TribeSystem.loadTribes(world);
        GodSystem.loadGodAvatars(world);
        StoryManager.loadStoryScripts(world);
        ExplorationSystem.loadMapLibraries(world);
    }
}
