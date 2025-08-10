package com.divineworld.god;

import com.divineworld.visual.Visuals;
import com.divineworld.registry.DivineEntityRegistry;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GodDisguiseManager {

    public static void toggleDisguise(UUID godId, Player player) {
        if (!DivineEntityRegistry.isGod(godId)) return;

        MortalForm current = GodState.getDisguise(godId);

        if (current == null) {
            GodState.setDisguise(godId, MortalForm.fromPlayer(player));
            Visuals.applyMortalForm(player, godId);
        } else {
            GodState.removeDisguise(godId);
            Visuals.restoreDivineAura(player, godId);
        }
    }
}
