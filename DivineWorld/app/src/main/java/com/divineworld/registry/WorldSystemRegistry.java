package com.divineworld.registry;

import com.divineworld.tribe.TribeSystem;
import com.divineworld.memory.MemorySystem;
import com.divineworld.motivation.MotivationSystem;
import com.divineworld.emotion.EmotionSystem;

public class WorldSystemRegistry {

    public static void init() {
        TribeSystem.init();
        MemorySystem.init();
        MotivationSystem.init();
        EmotionSystem.init();
    }
}
