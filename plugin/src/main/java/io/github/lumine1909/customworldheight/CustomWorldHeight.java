package io.github.lumine1909.customworldheight;

import io.github.lumine1909.customworldheight.config.LevelConfig;
import io.github.lumine1909.customworldheight.data.DataHandler;
import io.github.lumine1909.customworldheight.data.DataHandler_1_21_4;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomWorldHeight extends JavaPlugin {

    public static CustomWorldHeight plugin;
    public static DataHandler dataHandler;

    public static int obtainVersion() {
        try {
            String[] versions = Bukkit.getMinecraftVersion().split("\\.");
            if (versions.length == 2) {
                return Integer.parseInt(versions[1]) * 100;
            } else if (versions.length == 3) {
                return Integer.parseInt(versions[1]) * 100 + Integer.parseInt(versions[2]);
            }
        } catch (Exception ignored) {
        }
        return -1;
    }

    @Override
    public void onLoad() {
        plugin = this;
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        LevelConfig.readData(getConfig());
        if (obtainVersion() >= 2104) {
            dataHandler = new DataHandler_1_21_4();
        } else {
            throw new RuntimeException();
        }
        Bukkit.getPluginManager().registerEvents(new WorldListener(), this);
    }
}