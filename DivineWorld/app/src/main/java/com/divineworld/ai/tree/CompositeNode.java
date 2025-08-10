package com.divineworld.ai.tree;

import com.divineworld.npc.DWBot;

import java.util.ArrayList;
import java.util.List;

/** Composite node base (selector/sequence) */
public abstract class CompositeNode implements Node {
    protected final List<Node> children = new ArrayList<>();

    public void add(Node child) { children.add(child); }
}
