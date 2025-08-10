package com.divineworld.utils;

import com.google.gson.*;
import java.io.*;

public class ConfigLoader {
    private static JsonObject config;

    public static void load() {
        try (FileReader reader = new FileReader("plugins/DivineWorld/config/settings.json")) {
            config = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean getBoolean(String key) {
        return config.get(key).getAsBoolean();
    }

    public static int getInt(String key) {
        return config.get(key).getAsInt();
    }
}
