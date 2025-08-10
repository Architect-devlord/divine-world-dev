package com.divineworld.ai.tree;

import com.divineworld.npc.DWBot;

/**
 * Sequence: runs children in order until one returns true (did work).
 * If a child does work (returns true), the sequence returns true and continues next tick
 * from same child (simple stateful sequence could be added later).
 */
public class SequenceNode extends CompositeNode {

    private int currentIndex = 0;

    @Override
    public boolean tick(DWBot bot) {
        if (children.isEmpty()) return false;
        // run current child
        Node child = children.get(Math.min(currentIndex, children.size() - 1));
        boolean did = child.tick(bot);
        if (!did) {
            // child finished/not performing -> advance
            currentIndex++;
            if (currentIndex >= children.size()) currentIndex = 0; // reset
        } else {
            // child did work — let it continue next tick
            return true;
        }
        return false;
    }
}
