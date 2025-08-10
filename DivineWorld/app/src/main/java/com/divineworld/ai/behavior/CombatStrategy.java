package com.divineworld.ai.behavior;

import com.divineworld.npc.DWBot;
import org.bukkit.entity.LivingEntity;

public class CombatStrategy {
    private final DWBot bot;

    public CombatStrategy(DWBot bot) {
        this.bot = bot;
    }

    public void tick(LivingEntity target) {
        double health = bot.getEntity().getHealth();

        if (health < 6) {
            // Flee if low health
            bot.getEntity().setVelocity(bot.getEntity().getLocation().getDirection().multiply(-1));
            bot.getEmotion().adjust("fear", 0.2);
            return;
        }
            
        
        // Example: use Bukkit damage API
        target.damage(4.0, bot.getEntity()); // 4.0 damage, source is bot's entity

    }
}

