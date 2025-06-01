package io.github.lumine1909.customworldheight.data;

import ca.spottedleaf.starlight.common.light.StarLightInterface;
import io.github.lumine1909.customworldheight.config.BaseDimension;
import io.github.lumine1909.customworldheight.config.Height;
import io.papermc.paper.chunk.system.entity.EntityLookup;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;

import java.util.IdentityHashMap;

import static io.github.lumine1909.customworldheight.util.ReflectionUtil.set;

public class DataHandler_1_20_5 implements DataHandler<DimensionType, Holder<DimensionType>, ResourceKey<DimensionType>> {

    private static final MappedRegistry<DimensionType> REGISTRY = (MappedRegistry<DimensionType>) MinecraftServer.getServer().registryAccess().registry(Registries.DIMENSION_TYPE).orElseThrow();

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
    public LevelData<DimensionType, ResourceKey<DimensionType>, Holder<DimensionType>> createData(String name, Height height, BaseDimension dimension) {
        LevelData<DimensionType, ResourceKey<DimensionType>, Holder<DimensionType>> levelData = new LevelData_1_20_5(name, height, dimension);
        switch (dimension) {
            case OVERWORLD -> processData(levelData, REGISTRY.getHolderOrThrow(BuiltinDimensionTypes.OVERWORLD));
            case NETHER -> processData(levelData, REGISTRY.getHolderOrThrow(BuiltinDimensionTypes.NETHER));
            case END -> processData(levelData, REGISTRY.getHolderOrThrow(BuiltinDimensionTypes.END));
            case CAVES -> processData(levelData, REGISTRY.getHolderOrThrow(BuiltinDimensionTypes.OVERWORLD_CAVES));
            case CUSTOM -> levelData.accessor = world -> {
                processData(levelData, getHolder(world));
                return levelData.getHolder(world);
            };
        }
        return levelData;
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
        ResourceKey<DimensionType> newResourceKey = ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation("customworldheight", data.getName()));
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

        Holder<DimensionType> holder = data.getHolder(world);
        set(Level.class, "dimensionTypeRegistration", level, holder);
        StarLightInterface lightInterface = level.getChunkSource().getLightEngine().theLightEngine;
        EntityLookup entityLookup = level.getEntityLookup();
        final int minY = data.getMinY();
        final int height = data.getHeight();
        final int maxY = minY + height - 1;
        final int minSectionY = minY >> 4;
        final int maxSectionY = maxY >> 4;
        set(Level.class, "minSection", level, minSectionY);
        set(Level.class, "maxSection", level, maxSectionY);
        set(EntityLookup.class, "minSection", entityLookup, minSectionY);
        set(EntityLookup.class, "maxSection", entityLookup, maxSectionY);
        set(lightInterface.getClass(), "minSection", lightInterface, minSectionY);
        set(lightInterface.getClass(), "maxSection", lightInterface, maxSectionY);
        set(lightInterface.getClass(), "minLightSection", lightInterface, minSectionY - 1);
        set(lightInterface.getClass(), "maxLightSection", lightInterface, maxSectionY + 1);
    }
}