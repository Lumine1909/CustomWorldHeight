package io.github.lumine1909.customworldheight.proxy;

import com.example.proxying.api.ProxyService;
import com.example.proxying.api.ProxySupplier;
import com.example.proxying.api.annotation.Proxy;
import com.example.proxying.api.annotation.Set;

@Proxy(
    target = {
        "io.papermc.paper.chunk.system.entity.EntityLookup",
        "ca.spottedleaf.moonrise.patches.chunk_system.level.entity.EntityLookup"
    },
    allOptionalByDefault = true
)
public interface EntityLookupProxy {

    ProxySupplier<EntityLookupProxy> SERVICE = ProxyService.get().register(EntityLookupProxy.class);

    static EntityLookupProxy wrap(Object object) {
        return SERVICE.wrap(object);
    }

    @Set("minSection")
    void setMinSectionY(int minSectionY);

    @Set("maxSection")
    void setMaxSectionY(int maxSectionY);
}
