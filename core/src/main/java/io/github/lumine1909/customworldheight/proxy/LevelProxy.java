package io.github.lumine1909.customworldheight.proxy;

import com.example.proxying.api.ProxyService;
import com.example.proxying.api.ProxySupplier;
import com.example.proxying.api.annotation.Proxy;
import com.example.proxying.api.annotation.Set;

@Proxy(target = "net.minecraft.world.level.Level", allOptionalByDefault = true)
public interface LevelProxy {

    ProxySupplier<LevelProxy> SERVICE = ProxyService.get().register(LevelProxy.class);

    static LevelProxy wrap(Object object) {
        return SERVICE.wrap(object);
    }

    @Set("minY")
    void setMinY(int minY);

    @Set("height")
    void setHeight(int height);

    @Set("maxY")
    void setMaxY(int maxY);

    @Set({"minSectionY", "minSection"})
    void setMinSectionY(int minSectionY);

    @Set({"maxSectionY", "maxSection"})
    void setMaxSectionY(int maxSectionY);

    @Set("sectionsCount")
    void setSectionsCount(int sectionsCount);

    @Set(value = "dimensionTypeRegistration", type = "net.minecraft.core.Holder")
    void setDimensionType(Object holder);
}
