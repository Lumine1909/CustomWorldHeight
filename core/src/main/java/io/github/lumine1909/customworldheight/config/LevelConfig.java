package io.github.lumine1909.customworldheight.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class LevelConfig {

    private static final Map<String, Height> HEIGHT_MAP = new HashMap<>();

    public static void readData(FileConfiguration config) {
        HEIGHT_MAP.clear();
        for (String key : config.getKeys(false)) {
            String name = config.getString(key + ".world");
            int height = config.getInt(key + ".height", 384);
            int minY = config.getInt(key + ".min-y", -64);
            int logicalHeight = config.getInt(key + ".logical-height", 256);
            HEIGHT_MAP.put(name, new Height(height, minY, logicalHeight));
        }
    }

    public static Height getHeight(String world) {
        return HEIGHT_MAP.get(world);
    }

    public static boolean shouldProcess(String world) {
        return HEIGHT_MAP.containsKey(world);
    }
}
