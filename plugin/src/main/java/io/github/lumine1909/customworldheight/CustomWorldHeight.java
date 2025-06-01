package io.github.lumine1909.customworldheight;

import io.github.lumine1909.customworldheight.config.LevelConfig;
import io.github.lumine1909.customworldheight.data.DataHandler;
import io.github.lumine1909.customworldheight.data.DataHandler_1_20_5;
import io.github.lumine1909.customworldheight.data.DataHandler_1_21;
import io.github.lumine1909.customworldheight.data.DataHandler_1_21_3;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@SuppressWarnings("rawtypes")
public class CustomWorldHeight extends JavaPlugin implements Listener {

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
        File config = new File(getDataFolder(), "config.yml");
        if (!config.exists()) {
            getSLF4JLogger().warn("This is the first time you start this plugin. Please restart the server after finish your config!");
            saveDefaultConfig();
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onEnable() {
        int version = obtainVersion();
        if (version >= 2103) {
            dataHandler = new DataHandler_1_21_3();
        } else if (version >= 2100) {
            dataHandler = new DataHandler_1_21();
        } else if (version >= 2005) {
            dataHandler = new DataHandler_1_20_5();
        } else {
            throw new RuntimeException("Unsupported version: " + version);
        }
        LevelConfig.readData(getConfig());
        Bukkit.getPluginManager().registerEvents(new WorldListener(), this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onReload(ServerLoadEvent event) {
        if (event.getType() == ServerLoadEvent.LoadType.RELOAD) {
            getSLF4JLogger().error("This plugin does not support reload! Use at your own risk!");
        }
    }
}