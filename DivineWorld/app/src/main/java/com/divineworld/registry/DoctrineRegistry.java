package com.divineworld.registry;

import com.divineworld.tribe.Doctrine;
import com.divineworld.utils.FileUtils;
import com.google.gson.Gson;

import java.io.File;
import java.util.*;

public class DoctrineRegistry {

    private static final Map<String, Doctrine> doctrines = new HashMap<>();

    public static void init() {
        File dir = new File("plugins/DivineWorld/data/doctrines/");
        if (!dir.exists()) dir.mkdirs();

        for (File file : Objects.requireNonNull(dir.listFiles())) {
            Doctrine doctrine = new Gson().fromJson(FileUtils.read(file), Doctrine.class);
            doctrines.put(doctrine.getId(), doctrine);
        }
    }

    public static void register(Doctrine doctrine) {
        doctrines.put(doctrine.getId(), doctrine);
    }

    public static Collection<Doctrine> getAll() {
        return doctrines.values();
    }

    public static Doctrine get(String id) {
        return doctrines.get(id);
    }
}
