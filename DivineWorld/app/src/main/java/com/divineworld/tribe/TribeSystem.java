package com.divineworld.tribe;

import com.divineworld.npc.DWBot;
import com.divineworld.utils.NameGenerator;
import org.bukkit.Location;

import java.util.*;

public class TribeSystem {

    private static final List<Tribe> tribes = new ArrayList<>();

    public static void init() {
        // Load from disk later
    }

    public static void triggerGenesis(Location location, String founderName) {
        Tribe tribe = new Tribe(NameGenerator.generateTribeName(), location, founderName);
        tribes.add(tribe);
        tribe.spawnInitialMembers();
    }

    public static void spawnNewTribe(Location location) {
        Tribe tribe = new Tribe(NameGenerator.generateTribeName(), location, "Wanderer");
        tribes.add(tribe);
        tribe.spawnInitialMembers();
    }

    public static List<Tribe> getAllTribes() {
        return tribes;
    }
}
