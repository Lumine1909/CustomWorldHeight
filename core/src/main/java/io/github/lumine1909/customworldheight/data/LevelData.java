package io.github.lumine1909.customworldheight.data;

import io.github.lumine1909.customworldheight.config.Height;

public class LevelData<DimensionType, ResourceKey, Holder> {

    protected String name;
    protected Height height;

    protected DimensionType dimensionType;
    protected ResourceKey resourceKey;
    protected Holder holder;

    public LevelData(String name, Height height) {
        this.name = name;
        this.height = height;
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

    public Holder getHolder() {
        return holder;
    }

    public void setHolder(Holder holder) {
        this.holder = holder;
    }
}
