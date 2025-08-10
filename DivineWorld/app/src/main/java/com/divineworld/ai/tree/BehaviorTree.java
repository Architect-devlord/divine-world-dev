package com.divineworld.ai.tree;

import java.util.*;

/**
 * Very basic Behavior Tree system.
 * Behaviors are stored by name and can be executed in sequence.
 */
public class BehaviorTree {

    private final Map<String, Runnable> behaviors = new HashMap<>();
    private String currentBehavior = null;

    public void registerBehavior(String name, Runnable behavior) {
        behaviors.put(name, behavior);
    }

    public void setCurrentBehavior(String name) {
        if (behaviors.containsKey(name)) {
            currentBehavior = name;
        }
    }

    public boolean hasBehavior(String name) {
        return behaviors.containsKey(name);
    }

    public String getCurrentBehavior() {
        return currentBehavior;
    }

    // in BehaviorTree.java
public boolean tick() {
    if (currentBehavior != null && behaviors.containsKey(currentBehavior)) {
        behaviors.get(currentBehavior).run();
        return true; // changed  true (performed to returnwork)
    }
    return false;
}

}
