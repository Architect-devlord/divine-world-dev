package com.divineworld.ai.tree;

import com.divineworld.npc.DWBot;
import java.util.function.Function;

/**
 * ActionNode runs a small behavior function.
 * The function returns true if it performed an action this tick.
 */
public class ActionNode implements Node {

    private final Function<DWBot, Boolean> action;

    public ActionNode(Function<DWBot, Boolean> action) {
        this.action = action;
    }

    @Override
    public boolean tick(DWBot bot) {
        try {
            return Boolean.TRUE.equals(action.apply(bot));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
