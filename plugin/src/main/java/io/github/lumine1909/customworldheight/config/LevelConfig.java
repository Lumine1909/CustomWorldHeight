package io.github.lumine1909.customworldheight.config;

import io.github.lumine1909.customworldheight.data.LevelData;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static io.github.lumine1909.customworldheight.CustomWorldHeight.dataHandler;
import static io.github.lumine1909.customworldheight.CustomWorldHeight.plugin;

public class LevelConfig {

    private static final Map<String, String> W0RLD_NAME_MAP = new HashMap<>();
    private static final Map<String, String> WORLD_REGEX_MAP = new TreeMap<>();
    private static final Map<String, LevelData<?, ?, ?>> CACHED_DATA = new HashMap<>();

    public static void readData(FileConfiguration config) {
        W0RLD_NAME_MAP.clear();
        WORLD_REGEX_MAP.clear();
        for (String key : config.getKeys(false)) {
            String name = config.getString(key + ".world", null);
            String regex = config.getString(key + ".regex", "\\w\\b\\w"); // Don't match anything
            int height = config.getInt(key + ".height", 384);
            int minY = config.getInt(key + ".min-y", -64);
            int logicalHeight = config.getInt(key + ".logical-height", 256);
            String dimension = config.getString(key + ".dimension-type", "custom");
            plugin.getSLF4JLogger().info(
                "Loaded config: name={}, regex={}, height={}, minY={}, logicalHeight={}, dimension={}",
                name, regex, height, minY, logicalHeight, dimension
            );
            Height h = new Height(height, minY, logicalHeight);
            if (name != null) {
                W0RLD_NAME_MAP.put(name, key);
            } else {
                WORLD_REGEX_MAP.put(regex, key);
            }
            CACHED_DATA.put(key, dataHandler.createData(key, h, BaseDimension.getByName(dimension)));
        }
    }

    public static LevelData<?, ?, ?> getDataOrThrow(String key) {
        return Objects.requireNonNull(CACHED_DATA.get(key));
    }

    public static String checkConfigData(String worldName) {
        if (WORLD_REGEX_MAP.containsKey(worldName)) {
            return W0RLD_NAME_MAP.get(worldName);
        }
        for (Map.Entry<String, String> entry : WORLD_REGEX_MAP.entrySet()) {
            if (worldName.matches(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
