package io.github.lumine1909;

import ca.spottedleaf.starlight.common.light.StarLightInterface;
import com.mojang.serialization.Lifecycle;
import io.papermc.paper.chunk.system.light.LightQueue;
import io.papermc.paper.util.WorldUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryMaterials;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.dimension.DimensionManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R3.CraftServer;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import java.lang.reflect.Field;
import java.util.IdentityHashMap;
import static io.github.lumine1909.WorldManager.plugin;
import static io.github.lumine1909.WorldManager.settingsMap;

public class WorldListener implements Listener {
    private static Field holdDimF;
    private static Field resDimF;
    static {
        try {
            holdDimF = net.minecraft.world.level.World.class.getDeclaredField("E");
            resDimF = net.minecraft.world.level.World.class.getDeclaredField("D");
            holdDimF.setAccessible(true);
            resDimF.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @EventHandler
    public void onWorldInit(WorldInitEvent e) {
        if (!settingsMap.containsKey(e.getWorld().getName())) {
            return;
        }
        plugin.getLogger().info("Modifying world " + e.getWorld().getName() + "'s height...");
        addRegistries(e.getWorld());
        processWorldOnLoad(e.getWorld());
    }

    public static void processWorldOnLoad(World world) {
        WorldServer ws = ((CraftWorld) world).getHandle();
        WorldSettings settings = settingsMap.get(world.getName());
        try {
            Holder<DimensionManager> dmh = settings.dimManager;
            ResourceKey<DimensionManager> dimRes = settings.dimRes;
            holdDimF.set(ws, dmh);
            resDimF.set(ws, dimRes);
            StarLightInterface sl = ws.l().a().theLightEngine;
            int minS = WorldUtil.getMinSection(ws);
            int maxS = WorldUtil.getMaxSection(ws);
            Field minsF = net.minecraft.world.level.World.class.getDeclaredField("minSection");
            Field maxsF = net.minecraft.world.level.World.class.getDeclaredField("maxSection");
            minsF.setAccessible(true);
            maxsF.setAccessible(true);
            minsF.set(ws, minS);
            maxsF.set(ws, maxS);
            minsF = sl.getClass().getDeclaredField("minSection");
            maxsF = sl.getClass().getDeclaredField("maxSection");
            Field lightQF = sl.getClass().getDeclaredField("lightQueue");
            minsF.setAccessible(true);
            maxsF.setAccessible(true);
            lightQF.setAccessible(true);
            minsF.set(sl, minS);
            maxsF.set(sl, maxS);
            lightQF.set(sl, new LightQueue(sl));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRegistries(World world) {
        String worldName = world.getName();
        try {
            RegistryMaterials<DimensionManager> dimReg = (RegistryMaterials<DimensionManager>) ((CraftServer) Bukkit.getServer()).getServer().aZ().d(Registries.ay);
            ResourceKey<DimensionManager> dimRes = ResourceKey.a(Registries.ay, new MinecraftKey("worldmanager", "custom_" + worldName));
            WorldServer ws = ((CraftWorld) world).getHandle();
            DimensionManager dim1 = ws.ad().a();
            DimensionManager dim = new DimensionManager(dim1.f(), dim1.g(), dim1.h(), dim1.i(), dim1.j(), dim1.k(), dim1.l(), dim1.m(),
                    settingsMap.get(worldName).minY, settingsMap.get(worldName).height, settingsMap.get(worldName).logicalHeight, dim1.q(), dim1.r(), dim1.s(), dim1.t());
            Holder<DimensionManager> dmh = Holder.a(dim);

            Field unregHolderF = RegistryMaterials.class.getDeclaredField("m");
            unregHolderF.setAccessible(true);
            unregHolderF.set(dimReg, new IdentityHashMap<>());
            Field frozen = RegistryMaterials.class.getDeclaredField("l");
            frozen.setAccessible(true);
            frozen.set(dimReg, false);
            dimReg.f(dim);
            dimReg.a(dimRes, dim, Lifecycle.stable());
            unregHolderF.set(dimReg, null);
            frozen.set(dimReg, true);
            WorldSettings wse = settingsMap.get(worldName);
            wse.dimRes = dimRes;
            wse.dimManager = dmh;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}