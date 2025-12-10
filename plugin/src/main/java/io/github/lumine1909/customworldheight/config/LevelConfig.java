package io.github.lumine1909.customworldheight.config;

import io.github.lumine1909.customworldheight.data.LevelData;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.function.Function;

import static io.github.lumine1909.customworldheight.CustomWorldHeight.dataHandler;
import static io.github.lumine1909.customworldheight.CustomWorldHeight.plugin;

public class LevelConfig {

    private static final Map<String, String> WORLD_NAME_MAP = new HashMap<>();
    private static final Map<String, String> WORLD_REGEX_MAP = new TreeMap<>();
    private static final Map<String, LevelData<?, ?, ?>> CACHED_DATA = new HashMap<>();

    public static void readData(FileConfiguration config) {
        WORLD_NAME_MAP.clear();
        WORLD_REGEX_MAP.clear();
        for (String key : config.getKeys(false)) {
            String name = config.getString(key + ".world", null);
            String regex = config.getString(key + ".regex", "\\w\\b\\w"); // Don't match anything
            int height = config.getInt(key + ".height", 384);
            int minY = config.getInt(key + ".min-y", -64);
            int logicalHeight = config.getInt(key + ".logical-height", 256);
            String cloudHeight = config.getString(key + ".cloud-height", "empty");
            String dimension = config.getString(key + ".dimension-type", "custom");
            Function<Optional<Float>, Optional<Float>> cloudHeightFunc = switch (cloudHeight) {
                case "empty" -> v -> Optional.empty();
                case "default" -> Function.identity();
                default -> {
                    try {
                        float f = Float.parseFloat(cloudHeight);
                        yield t -> Optional.of(f);
                    } catch (NumberFormatException ignored) {
                    }
                    yield Function.identity();
                }
            };
            plugin.getSLF4JLogger().info(
                "Loaded config: name={}, regex={}, height={}, minY={}, logicalHeight={}, dimension={}, couldHeight={}",
                name, regex, height, minY, logicalHeight, dimension, cloudHeight
            );
            Height h = new Height(height, minY, logicalHeight, cloudHeightFunc);
            if (name != null) {
                WORLD_NAME_MAP.put(name, key);
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
        if (WORLD_NAME_MAP.containsKey(worldName)) {
            return WORLD_NAME_MAP.get(worldName);
        }
        for (Map.Entry<String, String> entry : WORLD_REGEX_MAP.entrySet()) {
            if (worldName.matches(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
