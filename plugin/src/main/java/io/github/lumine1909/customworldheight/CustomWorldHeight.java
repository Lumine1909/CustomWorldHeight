package io.github.lumine1909.customworldheight;

import io.github.lumine1909.customworldheight.api.WorldHeightService;
import io.github.lumine1909.customworldheight.config.LevelConfig;
import io.github.lumine1909.customworldheight.data.*;
import io.github.lumine1909.customworldheight.metrics.Metrics;
import io.github.lumine1909.customworldheight.util.NmsLoader;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@SuppressWarnings("rawtypes")
public class CustomWorldHeight extends JavaPlugin implements Listener {

    public static CustomWorldHeight plugin;
    public static LevelConfig levelConfig;
    public static DataHandler dataHandler;

    public static int obtainVersion() {
        try {
            String[] versions = Bukkit.getMinecraftVersion().split("\\.");
            return Integer.parseInt(versions[0]) * 10000
                + (versions.length > 1 ? Integer.parseInt(versions[1]) : 0) * 100
                + (versions.length > 2 ? Integer.parseInt(versions[2]) : 0);
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
        dataHandler = NmsLoader.loadDataHandler(version);
        levelConfig = new LevelConfig(getConfig());
        Bukkit.getServicesManager().register(WorldHeightService.class, levelConfig, this, ServicePriority.Normal);
        Bukkit.getPluginManager().registerEvents(new WorldListener(), this);
        Bukkit.getPluginManager().registerEvents(this, this);
        new Metrics(this, 26056);
    }

    @EventHandler
    public void onReload(ServerLoadEvent event) {
        if (event.getType() == ServerLoadEvent.LoadType.RELOAD) {
            getSLF4JLogger().error("This plugin does not support reload! Use at your own risk!");
        }
    }
}