package com.divineworld.ai.tree;

import com.divineworld.npc.DWBot;

/** Node base type. Returns true if it performed an action this tick. */
public interface Node {
    /**
     * Tick the node once.
     * @return true if node performed an action / handled input, false if no action (tree can continue)
     */
    boolean tick(DWBot bot);
}
