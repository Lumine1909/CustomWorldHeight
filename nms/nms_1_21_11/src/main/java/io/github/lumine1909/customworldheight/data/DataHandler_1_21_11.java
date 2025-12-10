package io.github.lumine1909.customworldheight.data;

import ca.spottedleaf.moonrise.patches.starlight.light.StarLightInterface;
import io.github.lumine1909.customworldheight.config.BaseDimension;
import io.github.lumine1909.customworldheight.config.Height;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.jetbrains.annotations.NotNull;

import java.util.IdentityHashMap;
import java.util.Objects;

import static io.github.lumine1909.customworldheight.util.ReflectionUtil.set;

public class DataHandler_1_21_11 implements DataHandler<DimensionType, Holder<@NotNull DimensionType>, ResourceKey<@NotNull DimensionType>> {

    private static final MappedRegistry<@NotNull DimensionType> REGISTRY = (MappedRegistry<@NotNull DimensionType>) MinecraftServer.getServer().registryAccess().lookup(Registries.DIMENSION_TYPE).orElseThrow();

    @Override
    public DimensionType getDimensionType(World world) {
        ServerLevel level = ((CraftWorld) world).getHandle();
        return level.dimensionType();
    }

    @Override
    public ResourceKey<@NotNull DimensionType> getResourceKey(World world) {
        return getHolder(world).unwrapKey().orElseThrow();
    }

    @Override
    public Holder<@NotNull DimensionType> getHolder(World world) {
        ServerLevel level = ((CraftWorld) world).getHandle();
        return level.dimensionTypeRegistration();
    }

    @Override
    public LevelData<DimensionType, ResourceKey<@NotNull DimensionType>, Holder<@NotNull DimensionType>> createData(String name, Height height, BaseDimension dimension) {
        LevelData<DimensionType, ResourceKey<@NotNull DimensionType>, Holder<@NotNull DimensionType>> levelData = new LevelData_1_21_11(name, height, dimension);
        switch (dimension) {
            case OVERWORLD -> processData(levelData, REGISTRY.getOrThrow(BuiltinDimensionTypes.OVERWORLD));
            case NETHER -> processData(levelData, REGISTRY.getOrThrow(BuiltinDimensionTypes.NETHER));
            case END -> processData(levelData, REGISTRY.getOrThrow(BuiltinDimensionTypes.END));
            case CAVES -> processData(levelData, REGISTRY.getOrThrow(BuiltinDimensionTypes.OVERWORLD_CAVES));
            case CUSTOM -> levelData.accessor = world -> {
                processData(levelData, getHolder(world));
                return levelData.getHolder(world);
            };
        }
        return levelData;
    }

    @Override
    public void processData(LevelData<DimensionType, ResourceKey<@NotNull DimensionType>, Holder<@NotNull DimensionType>> data, Holder<@NotNull DimensionType> holder) {
        DimensionType old = holder.value();
        Float originalCloudHeight = Objects.requireNonNull(old.attributes().get(EnvironmentAttributes.CLOUD_HEIGHT)).applyModifier(0f);
        EnvironmentAttributeMap newAttributes = EnvironmentAttributeMap.builder()
            .putAll(old.attributes())
            .set(EnvironmentAttributes.CLOUD_HEIGHT, data.computeCloudHeight(originalCloudHeight))
            .build();
        DimensionType newDimension = new DimensionType(
            old.hasFixedTime(), old.hasSkyLight(), old.hasCeiling(), old.coordinateScale(),
            data.getMinY(), data.getHeight(), data.getLogicalHeight(),
            old.infiniburn(), old.ambientLight(), old.monsterSettings(),
            old.skybox(), old.cardinalLightType(), newAttributes, old.timelines()
        );
        ResourceKey<@NotNull DimensionType> newResourceKey = ResourceKey.create(Registries.DIMENSION_TYPE, Identifier.fromNamespaceAndPath("customworldheight", data.getName()));
        data.setDimensionType(newDimension);
        data.setResourceKey(newResourceKey);
        Holder<@NotNull DimensionType> newHolder = register(data.getDimensionType(), data.getResourceKey());
        data.setHolder(newHolder);
    }

    @Override
    public Holder<@NotNull DimensionType> register(DimensionType dimensionType, ResourceKey<@NotNull DimensionType> resourceKey) {
        set(MappedRegistry.class, "frozen", REGISTRY, false);
        set(MappedRegistry.class, "unregisteredIntrusiveHolders", REGISTRY, new IdentityHashMap<>());
        REGISTRY.createIntrusiveHolder(dimensionType);
        Holder<@NotNull DimensionType> holder = REGISTRY.register(resourceKey, dimensionType, RegistrationInfo.BUILT_IN);
        set(MappedRegistry.class, "unregisteredIntrusiveHolders", REGISTRY, null);
        set(MappedRegistry.class, "frozen", REGISTRY, true);
        return holder;
    }

    public void processWorld(World world, LevelData<DimensionType, ResourceKey<@NotNull DimensionType>, Holder<@NotNull DimensionType>> data) {
        ServerLevel level = ((CraftWorld) world).getHandle();

        Holder<@NotNull DimensionType> holder = data.getHolder(world);
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