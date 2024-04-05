package io.github.lumine1909;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class WorldManager extends JavaPlugin {
    public static Map<String, WorldSettings> settingsMap = new HashMap<>();
    public static JavaPlugin plugin;

    @Override
    public void onLoad() {
        plugin = this;
        saveDefaultConfig();
        loadConfig();
    }
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new WorldListener(), this);
    }
    public void loadConfig() {
        reloadConfig();
        getConfig().getKeys(false).forEach(k -> {
            int height = getConfig().getInt(k + ".height");
            int minY = getConfig().getInt(k + ".miny");
            int logicalHeight = getConfig().getInt(k + ".logical-height");
            settingsMap.put(k, new WorldSettings(height, minY, logicalHeight));
        });
    }
}
