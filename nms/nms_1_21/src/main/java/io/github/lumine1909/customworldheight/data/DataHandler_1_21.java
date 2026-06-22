package io.github.lumine1909.customworldheight.data;

import io.github.lumine1909.customworldheight.api.BaseDimensionType;
import io.github.lumine1909.customworldheight.api.Height;
import io.github.lumine1909.customworldheight.api.Identifier;
import io.github.lumine1909.customworldheight.proxy.EntityLookupProxy;
import io.github.lumine1909.customworldheight.proxy.LevelProxy;
import io.github.lumine1909.customworldheight.proxy.MappedRegistryProxy;
import io.github.lumine1909.customworldheight.proxy.StarLightInterfaceProxy;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;

import java.util.IdentityHashMap;

public class DataHandler_1_21 implements DataHandler<DimensionType, Holder<DimensionType>, ResourceKey<DimensionType>> {

    private static final MappedRegistry<DimensionType> REGISTRY = (MappedRegistry<DimensionType>) MinecraftServer.getServer().registryAccess().registry(Registries.DIMENSION_TYPE).orElseThrow();
    private static final MappedRegistryProxy registry = MappedRegistryProxy.wrap(REGISTRY);

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
    public LevelData<DimensionType, ResourceKey<DimensionType>, Holder<DimensionType>> createData(Identifier id, Height height, BaseDimensionType dimension) {
        LevelData<DimensionType, ResourceKey<DimensionType>, Holder<DimensionType>> levelData = new LevelData_1_21(id, height, dimension);
        switch (dimension) {
            case OVERWORLD -> processData(levelData, REGISTRY.getHolderOrThrow(BuiltinDimensionTypes.OVERWORLD));
            case NETHER -> processData(levelData, REGISTRY.getHolderOrThrow(BuiltinDimensionTypes.NETHER));
            case END -> processData(levelData, REGISTRY.getHolderOrThrow(BuiltinDimensionTypes.END));
            case CAVES -> processData(levelData, REGISTRY.getHolderOrThrow(BuiltinDimensionTypes.OVERWORLD_CAVES));
            case CUSTOM -> levelData.accessor = world -> {
                processData(levelData, getHolder(world));
                return levelData.findHolder(world);
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
        ResourceKey<DimensionType> newResourceKey = ResourceKey.create(Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath(data.getId().namespace(), data.getId().value()));
        data.setDimensionType(newDimension);
        data.setResourceKey(newResourceKey);
        Holder<DimensionType> newHolder = register(data.getDimensionType(), data.getResourceKey());
        data.setHolder(newHolder);
    }

    @Override
    public Holder<DimensionType> register(DimensionType dimensionType, ResourceKey<DimensionType> resourceKey) {
        registry.setFrozen(false);
        registry.setIntrusiveHolders(new IdentityHashMap<>());
        REGISTRY.createIntrusiveHolder(dimensionType);
        Holder<DimensionType> holder = REGISTRY.register(resourceKey, dimensionType, RegistrationInfo.BUILT_IN);
        registry.setIntrusiveHolders(null);
        registry.setFrozen(true);
        return holder;
    }

    public void processWorld(World world, LevelData<DimensionType, ResourceKey<DimensionType>, Holder<DimensionType>> data) {
        ServerLevel serverLevel = ((CraftWorld) world).getHandle();
        Holder<DimensionType> holder = data.findHolder(world);
        final int minY = data.getMinY();
        final int height = data.getHeight();
        final int maxY = minY + height - 1;
        final int minSectionY = minY >> 4;
        final int maxSectionY = maxY >> 4;

        LevelProxy level = LevelProxy.wrap(serverLevel);
        level.setDimensionType(holder);
        level.setMinSectionY(minSectionY);
        level.setMaxSectionY(maxSectionY);

        EntityLookupProxy entityLookup = EntityLookupProxy.wrap(serverLevel.moonrise$getEntityLookup());
        entityLookup.setMinSectionY(minSectionY);
        entityLookup.setMaxSectionY(maxSectionY);

        StarLightInterfaceProxy lightInterface = StarLightInterfaceProxy.wrap(serverLevel.getChunkSource().getLightEngine().starlight$getLightEngine());
        lightInterface.setMinSection(minSectionY);
        lightInterface.setMaxSection(maxSectionY);
        lightInterface.setMinLightSection(minSectionY - 1);
        lightInterface.setMaxLightSection(maxSectionY + 1);
    }
}