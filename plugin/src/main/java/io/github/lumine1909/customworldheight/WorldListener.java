package io.github.lumine1909.customworldheight;

import io.github.lumine1909.customworldheight.config.LevelConfig;
import io.github.lumine1909.customworldheight.data.LevelData;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import static io.github.lumine1909.customworldheight.CustomWorldHeight.dataHandler;
import static io.github.lumine1909.customworldheight.CustomWorldHeight.plugin;

public class WorldListener implements Listener {

    @SuppressWarnings("unchecked")
    @EventHandler
    public void onWorldInit(WorldInitEvent e) {
        if (!LevelConfig.shouldProcess(e.getWorld().getName())) {
            return;
        }
        World world = e.getWorld();
        plugin.getLogger().info("Modifying world " + world.getName() + "'s height...");
        LevelData<?, ?, ?> data = dataHandler.createData(world);
        dataHandler.processData(data, dataHandler.getHolder(world));
        dataHandler.processWorld(world, data);
    }
}