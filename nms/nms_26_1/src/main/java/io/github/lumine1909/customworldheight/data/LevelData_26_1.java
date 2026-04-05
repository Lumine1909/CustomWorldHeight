package io.github.lumine1909.customworldheight.data;

import io.github.lumine1909.customworldheight.api.BaseDimensionType;
import io.github.lumine1909.customworldheight.api.Height;
import io.github.lumine1909.customworldheight.api.Identifier;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;

public class LevelData_26_1 extends LevelData<DimensionType, ResourceKey<@NotNull DimensionType>, Holder<@NotNull DimensionType>> {

    public LevelData_26_1(Identifier id, Height height, BaseDimensionType dimension) {
        super(id, height, dimension);
    }
}
