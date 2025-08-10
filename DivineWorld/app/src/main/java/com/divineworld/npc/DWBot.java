package com.divineworld.npc;

import com.divineworld.emotion.EmotionState;
import com.divineworld.memory.MemoryBank;
import com.divineworld.motivation.MotivationEngine;
import com.divineworld.personality.MBTITraits;
import com.divineworld.ai.brain.Brain;
import com.divineworld.registry.BotRegistry;
import com.divineworld.trade.TradeEngine;
import com.divineworld.DivineWorld;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Villager;

import java.io.*;
import java.util.UUID;

public class DWBot {

    private boolean pregnant = false;
    private long pregnancyStart = 0;
    private final UUID uuid;
    private final String name;
    private final MBTITraits personality;
    private final Brain brain;
    private final MemoryBank memory;
    private final EmotionState emotion;
    private final MotivationEngine motivation;
    private final TradeEngine tradeEngine;
    private Villager entity;
    private final String gender;

    // New: file reference for saving/loading NPC data
    private final File memoryFile;

    public DWBot(String name, Location spawnLoc, MBTITraits personality, String gender) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.personality = personality;
        this.memory = new MemoryBank(this);
        this.emotion = new EmotionState(this);
        this.motivation = new MotivationEngine(this);
        this.brain = new Brain(this);
        this.tradeEngine = new TradeEngine();
        this.gender = gender;

        // Create folder if it doesn't exist
        File folder = new File(DivineWorld.getInstance().getDataFolder(), "npc_memories");
        if (!folder.exists()) folder.mkdirs();
        this.memoryFile = new File(folder, "npc_" + uuid + "_memories.json");

        // Spawn NPC entity
        this.entity = spawnLoc.getWorld().spawn(spawnLoc, Villager.class);
        this.entity.setCustomName("🧍 " + name);
        this.entity.setCustomNameVisible(true);

        BotRegistry.register(this);
    }

    public void save() {
        try (Writer w = new FileWriter(memoryFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            BotSaveData data = new BotSaveData(this);
            gson.toJson(data, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        if (!memoryFile.exists()) return;
        try (Reader r = new FileReader(memoryFile)) {
            Gson gson = new GsonBuilder().create();
            BotSaveData data = gson.fromJson(r, BotSaveData.class);
            data.applyTo(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called every tick to update NPC state.
     */
    public void tick() {
        motivation.updateNeeds();
        brain.planAndAct();
        emotion.regulate();
        memory.pruneOld();
    }

    public UUID getUUID() { return uuid; }
    public String getName() { return name; }
    public MBTITraits getPersonality() { return personality; }
    public MemoryBank getMemory() { return memory; }
    public EmotionState getEmotion() { return emotion; }
    public MotivationEngine getMotivation() { return motivation; }
    public Villager getEntity() { return entity; }
    public TradeEngine getTradeEngine() { return tradeEngine; }
    public String getGender() { return gender; }

    public boolean isPregnant() { return pregnant; }
    public void setPregnant(boolean value) { pregnant = value; }
    public long getPregnancyStart() { return pregnancyStart; }
    public void setPregnancyStart(long time) { pregnancyStart = time; }

    // Inner class for serialization
    private static class BotSaveData {
        String name;
        MBTITraits personality;
        String gender;
        boolean pregnant;
        long pregnancyStart;
        Object memoryData; // Replace with actual memory structure

        BotSaveData(DWBot bot) {
            this.name = bot.name;
            this.personality = bot.personality;
            this.gender = bot.gender;
            this.pregnant = bot.pregnant;
            this.pregnancyStart = bot.pregnancyStart;
            this.memoryData = bot.memory.serialize();
        }

        void applyTo(DWBot bot) {
            bot.pregnant = this.pregnant;
            bot.pregnancyStart = this.pregnancyStart;
            bot.memory.deserialize(memoryData);
        }
    }
}
