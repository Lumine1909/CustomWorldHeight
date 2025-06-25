package io.github.lumine1909.customworldheight.data;

import io.github.lumine1909.customworldheight.config.BaseDimension;
import io.github.lumine1909.customworldheight.config.Height;
import org.bukkit.World;

import java.util.Optional;
import java.util.function.Function;

public class LevelData<DimensionType, ResourceKey, Holder> {

    protected final String name;
    protected Height height;
    protected BaseDimension dimension;

    protected DimensionType dimensionType;
    protected ResourceKey resourceKey;
    protected Holder holder;

    protected Function<World, Holder> accessor;

    public LevelData(String name, Height height, BaseDimension dimension) {
        this.name = name;
        this.height = height;
        this.dimension = dimension;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height.height();
    }

    public int getMinY() {
        return height.minY();
    }

    public int getLogicalHeight() {
        return height.logicalHeight();
    }

    public Optional<Integer> getCloudHeight(Optional<Integer> defaultHeight) {
        return height.couldHeightFunc().apply(defaultHeight);
    }

    public ResourceKey getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(ResourceKey resourceKey) {
        this.resourceKey = resourceKey;
    }

    public DimensionType getDimensionType() {
        return dimensionType;
    }

    public void setDimensionType(DimensionType dimensionType) {
        this.dimensionType = dimensionType;
    }

    public Holder getHolder(World world) {
        if (holder != null) {
            return holder;
        }
        this.holder = accessor.apply(world);
        return holder;
    }

    public void setHolder(Holder holder) {
        this.holder = holder;
    }
}
