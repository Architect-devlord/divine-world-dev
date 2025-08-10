package com.divineworld.ai.tree;

import com.divineworld.npc.DWBot;

/**
 * Selector: tries each child until one performs work (returns true).
 * Useful for reactive checks: heal if low, else fight if attacker nearby, else idle.
 */
public class SelectorNode extends CompositeNode {

    @Override
    public boolean tick(DWBot bot) {
        for (Node child : children) {
            boolean did = child.tick(bot);
            if (did) return true;
        }
        return false;
    }
}
