package io.github.lumine1909.customworldheight.data;

import org.bukkit.World;

public interface DataHandler<DimensionType, Holder, ResourceKey> {

    DimensionType getDimensionType(World world);

    ResourceKey getResourceKey(World world);

    Holder getHolder(World world);

    LevelData<DimensionType, ResourceKey, Holder> createData(World world);

    void processData(LevelData<DimensionType, ResourceKey, Holder> data, Holder holder);

    void processWorld(World world, LevelData<DimensionType, ResourceKey, Holder> data);

    Holder register(DimensionType dimensionType, ResourceKey resourceKey);
}
