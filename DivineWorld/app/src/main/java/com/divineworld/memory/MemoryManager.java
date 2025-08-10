package com.divineworld.memory;

import com.divineworld.DivineWorld;
import com.divineworld.npc.DWBot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MemoryManager {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static File getMemoryFolder() {
        File folder = new File(DivineWorld.getInstance().getDataFolder(), "npc_memories");
        if (!folder.exists()) folder.mkdirs();
        return folder;
    }

    public static File getMemoryFile(DWBot bot) {
        return new File(getMemoryFolder(), "npc_" + bot.getUUID() + "_memories.json");
    }

    public static void saveMemory(DWBot bot, NPCMemory memory) {
        try (FileWriter writer = new FileWriter(getMemoryFile(bot))) {
            gson.toJson(memory, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static NPCMemory loadMemory(DWBot bot) {
        File file = getMemoryFile(bot);
        if (!file.exists()) {
            return new NPCMemory();
        }
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, NPCMemory.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new NPCMemory();
        }
    }
}
