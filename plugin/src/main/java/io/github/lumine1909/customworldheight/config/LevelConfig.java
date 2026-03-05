package io.github.lumine1909.customworldheight.config;

import io.github.lumine1909.customworldheight.api.BaseDimensionType;
import io.github.lumine1909.customworldheight.api.Height;
import io.github.lumine1909.customworldheight.api.Identifier;
import io.github.lumine1909.customworldheight.api.WorldHeightService;
import io.github.lumine1909.customworldheight.data.LevelData;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static io.github.lumine1909.customworldheight.CustomWorldHeight.dataHandler;
import static io.github.lumine1909.customworldheight.CustomWorldHeight.plugin;

public class LevelConfig implements WorldHeightService {

    private static final Map<String, Identifier> WORLD_NAME_MAP = new HashMap<>();
    private static final Map<String, Identifier> WORLD_REGEX_MAP = new TreeMap<>();
    private static final Map<Identifier, LevelData<?, ?, ?>> CACHED_DATA = new HashMap<>();

    public LevelConfig(FileConfiguration config) {
        readData(config);
    }

    public static LevelData<?, ?, ?> getDataOrThrow(Identifier id) {
        return Objects.requireNonNull(CACHED_DATA.get(id));
    }

    public static Identifier checkConfigData(String worldName) {
        if (WORLD_NAME_MAP.containsKey(worldName)) {
            return WORLD_NAME_MAP.get(worldName);
        }
        for (Map.Entry<String, Identifier> entry : WORLD_REGEX_MAP.entrySet()) {
            if (worldName.matches(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void readData(FileConfiguration config) {
        WORLD_NAME_MAP.clear();
        WORLD_REGEX_MAP.clear();
        for (String key : config.getKeys(false)) {
            Identifier id = Identifier.of("customworldheight", key);
            String name = config.getString(key + ".world", null);
            String regex = config.getString(key + ".regex", "\\w\\b\\w"); // Don't match anything
            int height = config.getInt(key + ".height", 384);
            int minY = config.getInt(key + ".min-y", -64);
            int logicalHeight = config.getInt(key + ".logical-height", 256);
            String cloudHeightStr = config.getString(key + ".cloud-height", "empty");
            String dimension = config.getString(key + ".dimension-type", "custom");
            Height.CloudHeight cloudHeight = switch (cloudHeightStr) {
                case "empty" -> Height.CloudHeight.EMPTY;
                case "default" -> Height.CloudHeight.DEFAULT;
                default -> {
                    try {
                        float f = Float.parseFloat(cloudHeightStr);
                        yield Height.CloudHeight.height(f);
                    } catch (NumberFormatException ignored) {
                    }
                    yield Height.CloudHeight.DEFAULT;
                }
            };
            plugin.getSLF4JLogger().info(
                "Loaded config: value={}, regex={}, height={}, minY={}, logicalHeight={}, dimension={}, couldHeight={}",
                name, regex, height, minY, logicalHeight, dimension, cloudHeightStr
            );
            Height h = new Height(height, minY, logicalHeight, cloudHeight);
            register(id, name, regex, h, BaseDimensionType.getByName(dimension), false);
        }
    }

    @Override
    public Height getHeight(Identifier id) {
        return getDataOrThrow(id).getApiHeight();
    }

    @Override
    public void removeHeight(Identifier id) {
        CACHED_DATA.remove(id);
    }

    @Override
    public void registerWorld(Identifier id, String name, Height height, BaseDimensionType dimensionType, boolean override) {
        register(id, name, null, height, dimensionType, override);
    }

    @Override
    public void registerRegex(Identifier id, String regex, Height height, BaseDimensionType dimensionType, boolean override) {
        register(id, null, regex, height, dimensionType, override);
    }

    private void register(Identifier id, String name, String regex, Height height, BaseDimensionType dimensionType, boolean override) {
        if (name != null) {
            WORLD_NAME_MAP.put(name, id);
        } else {
            WORLD_REGEX_MAP.put(regex, id);
        }
        if (override || !CACHED_DATA.containsKey(id)) {
            CACHED_DATA.put(id, dataHandler.createData(id, height, dimensionType));
        }
    }
}
