package io.github.lumine1909.customworldheight.data;

import io.github.lumine1909.customworldheight.api.BaseDimensionType;
import io.github.lumine1909.customworldheight.api.Height;
import io.github.lumine1909.customworldheight.api.Identifier;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.DimensionType;

public class LevelData_1_21_11 extends LevelData<DimensionType, ResourceKey<DimensionType>, Holder<DimensionType>> {

    public LevelData_1_21_11(Identifier id, Height height, BaseDimensionType dimension) {
        super(id, height, dimension);
    }
}
