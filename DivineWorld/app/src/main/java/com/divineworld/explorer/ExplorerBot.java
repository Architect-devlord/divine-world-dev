package com.divineworld.explorer;

import com.divineworld.npc.DWBot;
import com.divineworld.tribe.Tribe;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

public class ExplorerBot {

    private final DWBot bot;
    private final Set<String> exploredChunks = new HashSet<>();
    private final Tribe tribe;

    public ExplorerBot(DWBot bot, Tribe tribe) {
        this.bot = bot;
        this.tribe = tribe;
    }

    public void exploreChunk(Chunk chunk) {
        String chunkId = chunk.getX() + "," + chunk.getZ();
        if (!exploredChunks.contains(chunkId)) {
            exploredChunks.add(chunkId);
            logDiscovery(chunk);
        }
    }

    public void logDiscovery(Chunk chunk) {
        // Log to tribe library
        tribe.addMyth("Discovered chunk: " + chunk.getX() + "," + chunk.getZ());
        // You can also mark as safe/dangerous, record biome, etc.
    }

    public void returnToTribe() {
        Location tribeCenter = tribe.getOrigin();
        bot.getEntity().teleport(tribeCenter);
    }
}
