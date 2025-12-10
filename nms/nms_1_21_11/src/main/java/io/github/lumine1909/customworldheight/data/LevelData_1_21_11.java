package io.github.lumine1909.customworldheight.data;

import io.github.lumine1909.customworldheight.config.BaseDimension;
import io.github.lumine1909.customworldheight.config.Height;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;

public class LevelData_1_21_11 extends LevelData<DimensionType, ResourceKey<@NotNull DimensionType>, Holder<@NotNull DimensionType>> {

    public LevelData_1_21_11(String name, Height height, BaseDimension dimension) {
        super(name, height, dimension);
    }
}
