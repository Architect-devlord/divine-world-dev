package com.divineworld.memory;

import java.util.ArrayList;
import java.util.List;

public class NPCMemory {
    public List<String> pastEvents = new ArrayList<>();
    public List<String> relationships = new ArrayList<>();
    public List<String> learnedSkills = new ArrayList<>();

    public void rememberEvent(String event) {
        pastEvents.add(event);
    }

    public void learnSkill(String skill) {
        learnedSkills.add(skill);
    }

    public void addRelationship(String relation) {
        relationships.add(relation);
    }
}
