package io.github.lumine1909;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.dimension.WorldDimension;

public class WorldSettings {
    int height;
    int minY;
    int logicalHeight;
    Holder<DimensionManager> dimManager;
    ResourceKey<DimensionManager> dimRes;
    public WorldSettings(int height, int minY, int logicalHeight) {
        this.height = height;
        this.minY = minY;
        this.logicalHeight = logicalHeight;
    }
}
