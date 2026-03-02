package io.github.lumine1909.customworldheight.data;

import io.github.lumine1909.customworldheight.api.BaseDimensionType;
import io.github.lumine1909.customworldheight.api.Height;
import io.github.lumine1909.customworldheight.api.Identifier;
import org.bukkit.World;

import java.util.Optional;
import java.util.function.Function;

public class LevelData<DimensionType, ResourceKey, Holder> {

    protected final Identifier id;
    protected final Height height;
    protected final BaseDimensionType dimension;

    protected DimensionType dimensionType;
    protected ResourceKey resourceKey;
    protected Holder holder;

    protected Function<World, Holder> accessor;

    public LevelData(Identifier id, Height height, BaseDimensionType dimension) {
        this.id = id;
        this.height = height;
        this.dimension = dimension;
    }

    public Identifier getId() {
        return id;
    }

    public Height getApiHeight() {
        return height;
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

    public Optional<Float> computeCloudHeight(Float defaultHeight) {
        return height.cloudHeight().get(Optional.ofNullable(defaultHeight));
    }

    public Optional<Integer> computeCloudHeightInteger(Optional<Integer> defaultHeight) {
        return height.cloudHeight().get(defaultHeight.map(i -> (Float) (float) i)).map((Float::intValue));
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
