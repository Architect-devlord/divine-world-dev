package com.divineworld.tribe;

import java.util.*;

public class Doctrine {

    private final String id;
    private final String name;
    private final List<String> beliefs;
    private final String founder;
    private final long foundedTime;

    public Doctrine(String id, String name, List<String> beliefs, String founder) {
        this.id = id;
        this.name = name;
        this.beliefs = new ArrayList<>(beliefs);
        this.founder = founder;
        this.foundedTime = System.currentTimeMillis();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<String> getBeliefs() { return beliefs; }
    public String getFounder() { return founder; }
    public long getFoundedTime() { return foundedTime; }

    public void mutate(String newBelief) {
        if (!beliefs.contains(newBelief)) beliefs.add(newBelief);
    }
}
