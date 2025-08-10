package com.divineworld.registry;

import com.divineworld.npc.DWBot;

import java.util.*;

public class BotRegistry {

    private static final Map<UUID, DWBot> bots = new HashMap<>();

    public static void init() {
        // Will later load bots from disk
    }

    public static void register(DWBot bot) {
        bots.put(bot.getUUID(), bot);
    }

    public static Collection<DWBot> getAllBots() {
        return bots.values();
    }

    public static Optional<DWBot> get(UUID uuid) {
        return Optional.ofNullable(bots.get(uuid));
    }

    public static void remove(UUID uuid) {
        bots.remove(uuid);
    }
}
