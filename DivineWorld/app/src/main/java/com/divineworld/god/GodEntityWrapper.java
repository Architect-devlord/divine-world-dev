package com.divineworld.god;

import org.bukkit.entity.LivingEntity;

public class GodEntityWrapper {

    public static void override(LivingEntity entity) {
        entity.setCustomName("§cDivine Beast");
        entity.setAI(false);
        // Attach a DW-style brain here if needed
    }
}
