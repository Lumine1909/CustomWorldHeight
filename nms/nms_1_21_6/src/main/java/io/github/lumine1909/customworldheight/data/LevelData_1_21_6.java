package io.github.lumine1909.customworldheight.data;

import io.github.lumine1909.customworldheight.config.BaseDimension;
import io.github.lumine1909.customworldheight.config.Height;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.DimensionType;

public class LevelData_1_21_6 extends LevelData<DimensionType, ResourceKey<DimensionType>, Holder<DimensionType>> {

    public LevelData_1_21_6(String name, Height height, BaseDimension dimension) {
        super(name, height, dimension);
    }
}
