package com.divineworld.npc;

import com.divineworld.personality.MBTITraits;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class NPCManager {
    private static final List<DWBot> npcs = new ArrayList<>();

    public static DWBot spawnNPC(Location loc, String gender) {
        String name = gender.equals("male") ? "Adam" : "Eve"; // Example names
        MBTITraits traits = MBTITraits.randomTraits();
        DWBot npc = new DWBot(name, loc, traits, gender);

        // Load saved data if exists
        npc.load();

        npcs.add(npc);
        return npc;
    }

    public static void killAllNPCs() {
        for (DWBot npc : npcs) {
            npc.getEntity().remove();
        }
        npcs.clear();
    }

    public static List<DWBot> getAllNPCs() {
        return npcs;
    }

    public static void saveAllNPCs() {
        for (DWBot npc : npcs) {
            npc.save();
        }
    }
}
