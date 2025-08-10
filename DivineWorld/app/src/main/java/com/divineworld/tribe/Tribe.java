package com.divineworld.tribe;

import com.divineworld.npc.DWBot;
import com.divineworld.personality.MBTITraits;
import org.bukkit.Location;

import java.util.*;

public class Tribe {

    private final String name;
    private final Location origin;
    private final List<DWBot> members = new ArrayList<>();
    private final Doctrine doctrine;

    private final List<String> discoveredChunks = new ArrayList<>();

    public void logDiscoveredChunk(String chunkId) {
        if (!discoveredChunks.contains(chunkId)) {
            discoveredChunks.add(chunkId);
        }
    }

    public List<String> getDiscoveredChunks() {
        return discoveredChunks;
    }

    public Location getOrigin() {
        return origin;
    }

    private boolean hasMigrated = false;

    public void tryMigrateIfNeeded() {
        if (members.size() > 20 && !hasMigrated) {
            Location newLocation = origin.clone().add(500 + new Random().nextInt(200), 0, 500 + new Random().nextInt(200));
            origin = newLocation;
            for (DWBot bot : members) {
                bot.getEntity().teleport(newLocation);
            }
           addMyth("The tribe has migrated to a new land.");
            hasMigrated = true;
        }
    }



    public Tribe(String name, Location origin, String founder) {
        this.name = name;
        this.origin = origin;
        this.doctrine = new Doctrine(UUID.randomUUID().toString(), "First Flame", new ArrayList<>(), founder);
    }

    public void spawnInitialMembers() {
        for (int i = 0; i < 5; i++) {
            DWBot bot = new DWBot("Bot_" + i, origin, MBTITraits.random());
            members.add(bot);
        }
        doctrine.mutate("🔥 Fire is sacred.");
        doctrine.mutate("🧬 Knowledge must be passed on.");
    }

    public String getName() { return name; }
    public List<DWBot> getMembers() { return members; }
    public Doctrine getDoctrine() { return doctrine; }
    
    public void addMyth(String event) {
    StoryRecorder.log(this.name, event);
    }

}
