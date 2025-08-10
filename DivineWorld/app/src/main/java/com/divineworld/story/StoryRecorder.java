package com.divineworld.story;

import com.google.gson.*;
import java.io.*;
import java.util.*;

public class StoryRecorder {

    public static void log(String tribeName, String event) {
        File file = new File("plugins/DivineWorld/data/stories/" + tribeName + ".json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject root;

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                root = new JsonObject();
                root.add("events", new JsonArray());
            } else {
                root = JsonParser.parseReader(new FileReader(file)).getAsJsonObject();
            }

            JsonObject e = new JsonObject();
            e.addProperty("time", System.currentTimeMillis());
            e.addProperty("event", event);
            root.getAsJsonArray("events").add(e);

            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(root, writer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
