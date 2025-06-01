package io.github.lumine1909.customworldheight.data;

import io.github.lumine1909.customworldheight.config.BaseDimension;
import io.github.lumine1909.customworldheight.config.Height;
import org.bukkit.World;

public interface DataHandler<DimensionType, Holder, ResourceKey> {

    DimensionType getDimensionType(World world);

    ResourceKey getResourceKey(World world);

    Holder getHolder(World world);

    LevelData<DimensionType, ResourceKey, Holder> createData(String name, Height height, BaseDimension dimension);

    void processData(LevelData<DimensionType, ResourceKey, Holder> data, Holder holder);

    void processWorld(World world, LevelData<DimensionType, ResourceKey, Holder> data);

    Holder register(DimensionType dimensionType, ResourceKey resourceKey);
}
