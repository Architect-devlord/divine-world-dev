package com.divineworld.memory;

import com.divineworld.DivineWorld;
import com.divineworld.npc.DWBot;
import com.google.gson.*;

import java.io.*;
import java.util.*;

public class MemoryBank {

    private final DWBot bot;
    private final List<MemoryEntry> memories = new ArrayList<>();
    private final File memoryFile;

    // 1 MC day = 20 min = 1_200_000 ms; 60 MC days = 72_000_000 ms
    private static final long MC_DAY_MS = 20 * 60 * 1000;
    private static final long LONG_TERM_EXPIRY = MC_DAY_MS * 60;

    public MemoryBank(DWBot bot) {
        this.bot = bot;

        // Correct save location inside plugin data folder
        File folder = new File(DivineWorld.getInstance().getDataFolder(), "npc_memories");
        if (!folder.exists()) folder.mkdirs();

        this.memoryFile = new File(folder, "npc_" + bot.getUUID() + "_memories.json");
        loadLongTermMemories();
    }

    public void remember(String event, String subject, double impact) {
        memories.add(new MemoryEntry(event, subject, System.currentTimeMillis(), impact));
    }

    public List<MemoryEntry> getAll() {
        List<MemoryEntry> all = new ArrayList<>(memories);
        all.addAll(loadLongTermMemories());
        return all;
    }

    public void pruneOld() {
        long now = System.currentTimeMillis();
        Iterator<MemoryEntry> it = memories.iterator();
        List<MemoryEntry> toStore = new ArrayList<>();
        while (it.hasNext()) {
            MemoryEntry mem = it.next();
            if ((now - mem.time) > MC_DAY_MS) {
                toStore.add(mem);
                it.remove();
            }
        }
        if (!toStore.isEmpty()) {
            saveLongTermMemories(toStore);
        }
        cleanupLongTermMemories();
    }

    private void saveLongTermMemories(List<MemoryEntry> newMemories) {
        List<MemoryEntry> all = loadLongTermMemories();
        all.addAll(newMemories);
        try (Writer writer = new FileWriter(memoryFile)) {
            new Gson().toJson(all, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<MemoryEntry> loadLongTermMemories() {
        if (!memoryFile.exists()) return new ArrayList<>();
        try (Reader reader = new FileReader(memoryFile)) {
            MemoryEntry[] arr = new Gson().fromJson(reader, MemoryEntry[].class);
            return arr == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(arr));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Remove memories not accessed in 60 MC days
    private void cleanupLongTermMemories() {
        long now = System.currentTimeMillis();
        List<MemoryEntry> all = loadLongTermMemories();
        boolean changed = false;
        Iterator<MemoryEntry> it = all.iterator();
        while (it.hasNext()) {
            MemoryEntry mem = it.next();
            if ((now - mem.time) > LONG_TERM_EXPIRY) {
                it.remove();
                changed = true;
            }
        }
        if (changed) {
            try (Writer writer = new FileWriter(memoryFile)) {
                new Gson().toJson(all, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Access a memory by subject or event (updates timestamp)
    public MemoryEntry recall(String key) {
        for (MemoryEntry mem : memories) {
            if (mem.event.equals(key) || mem.subject.equals(key)) {
                mem.time = System.currentTimeMillis();
                return mem;
            }
        }
        List<MemoryEntry> longTerm = loadLongTermMemories();
        for (MemoryEntry mem : longTerm) {
            if (mem.event.equals(key) || mem.subject.equals(key)) {
                mem.time = System.currentTimeMillis();
                saveLongTermMemories(longTerm); // update timestamp
                return mem;
            }
        }
        return null;
    }
}
