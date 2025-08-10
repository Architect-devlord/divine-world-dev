package com.divineworld.tasks;

public class TaskScheduler {
    private static BukkitTask task;
    public static void startAll() {
        stopAll();
        task = Bukkit.getScheduler().runTaskTimer(DivineWorld.getInstance(), () -> {
            try {
                for (DWBot bot : new ArrayList<>(BotRegistry.getAllBots())) bot.tick();
            } catch (Exception e) {
                DivineWorld.getInstance().getLogger().severe("TaskScheduler error: " + e);
            }
        }, 1L, 1L);
    }
    public static void stopAll() {
        if (task != null) task.cancel();
    }
}


