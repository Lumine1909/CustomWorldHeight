package io.github.lumine1909.customworldheight.data;

import ca.spottedleaf.moonrise.patches.starlight.light.StarLightInterface;
import io.github.lumine1909.customworldheight.config.LevelConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;

import java.util.IdentityHashMap;

import static io.github.lumine1909.customworldheight.util.ReflectionUtil.set;

public class DataHandler_1_21_3 implements DataHandler<DimensionType, Holder<DimensionType>, ResourceKey<DimensionType>> {

    private static final MappedRegistry<DimensionType> REGISTRY = (MappedRegistry<DimensionType>) MinecraftServer.getServer().registryAccess().lookup(Registries.DIMENSION_TYPE).orElseThrow();

    @Override
    public DimensionType getDimensionType(World world) {
        ServerLevel level = ((CraftWorld) world).getHandle();
        return level.dimensionType();
    }

    @Override
    public ResourceKey<DimensionType> getResourceKey(World world) {
        return getHolder(world).unwrapKey().orElseThrow();
    }

    @Override
    public Holder<DimensionType> getHolder(World world) {
        ServerLevel level = ((CraftWorld) world).getHandle();
        return level.dimensionTypeRegistration();
    }

    @Override
    public LevelData<DimensionType, ResourceKey<DimensionType>, Holder<DimensionType>> createData(World world) {
        return new LevelData_1_21_3(world.getName(), LevelConfig.getHeight(world.getName()));
    }

    @Override
    public void processData(LevelData<DimensionType, ResourceKey<DimensionType>, Holder<DimensionType>> data, Holder<DimensionType> holder) {
        DimensionType old = holder.value();
        DimensionType newDimension = new DimensionType(
            old.fixedTime(), old.hasSkyLight(), old.hasCeiling(),
            old.ultraWarm(), old.natural(), old.coordinateScale(),
            old.bedWorks(), old.respawnAnchorWorks(),
            data.getMinY(), data.getHeight(), data.getLogicalHeight(),
            old.infiniburn(), old.effectsLocation(),
            old.ambientLight(), old.monsterSettings()
        );
        ResourceKey<DimensionType> newResourceKey = ResourceKey.create(Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath("customworldheight", data.getName()));
        data.setDimensionType(newDimension);
        data.setResourceKey(newResourceKey);
        Holder<DimensionType> newHolder = register(data.getDimensionType(), data.getResourceKey());
        data.setHolder(newHolder);
    }

    @Override
    public Holder<DimensionType> register(DimensionType dimensionType, ResourceKey<DimensionType> resourceKey) {
        set(MappedRegistry.class, "frozen", REGISTRY, false);
        set(MappedRegistry.class, "unregisteredIntrusiveHolders", REGISTRY, new IdentityHashMap<>());
        REGISTRY.createIntrusiveHolder(dimensionType);
        Holder<DimensionType> holder = REGISTRY.register(resourceKey, dimensionType, RegistrationInfo.BUILT_IN);
        set(MappedRegistry.class, "unregisteredIntrusiveHolders", REGISTRY, null);
        set(MappedRegistry.class, "frozen", REGISTRY, true);
        return holder;
    }

    public void processWorld(World world, LevelData<DimensionType, ResourceKey<DimensionType>, Holder<DimensionType>> data) {
        ServerLevel level = ((CraftWorld) world).getHandle();

        Holder<DimensionType> holder = data.getHolder();
        set(Level.class, "dimensionTypeRegistration", level, holder);
        StarLightInterface lightInterface = level.getLightEngine().starlight$getLightEngine();
        final int minY = data.getMinY();
        final int height = data.getHeight();
        final int maxY = minY + height - 1;
        final int minSectionY = minY >> 4;
        final int maxSectionY = maxY >> 4;
        final int sectionsCount = maxSectionY - minSectionY + 1;
        set(Level.class, "minY", level, minY);
        set(Level.class, "height", level, height);
        set(Level.class, "maxY", level, maxY);
        set(Level.class, "minSectionY", level, minSectionY);
        set(Level.class, "maxSectionY", level, maxSectionY);
        set(Level.class, "sectionsCount", level, sectionsCount);
        set(lightInterface.getClass(), "minSection", lightInterface, minSectionY);
        set(lightInterface.getClass(), "maxSection", lightInterface, maxSectionY);
        set(lightInterface.getClass(), "minLightSection", lightInterface, minSectionY - 1);
        set(lightInterface.getClass(), "maxLightSection", lightInterface, maxSectionY + 1);
    }
}