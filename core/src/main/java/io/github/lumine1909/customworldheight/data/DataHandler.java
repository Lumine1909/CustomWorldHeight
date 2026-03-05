package io.github.lumine1909.customworldheight.data;

import io.github.lumine1909.customworldheight.api.BaseDimensionType;
import io.github.lumine1909.customworldheight.api.Height;
import io.github.lumine1909.customworldheight.api.Identifier;
import org.bukkit.World;

public interface DataHandler<DimensionType, Holder, ResourceKey> {

    DimensionType getDimensionType(World world);

    ResourceKey getResourceKey(World world);

    Holder getHolder(World world);

    LevelData<DimensionType, ResourceKey, Holder> createData(Identifier id, Height height, BaseDimensionType dimension);

    void processData(LevelData<DimensionType, ResourceKey, Holder> data, Holder holder);

    void processWorld(World world, LevelData<DimensionType, ResourceKey, Holder> data);

    Holder register(DimensionType dimensionType, ResourceKey resourceKey);
}
