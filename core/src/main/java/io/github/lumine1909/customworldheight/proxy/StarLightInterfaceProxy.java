package io.github.lumine1909.customworldheight.proxy;

import com.example.proxying.api.ProxyService;
import com.example.proxying.api.ProxySupplier;
import com.example.proxying.api.annotation.Proxy;
import com.example.proxying.api.annotation.Set;

@Proxy(
    target = {
        "ca.spottedleaf.moonrise.patches.starlight.light.StarLightInterface",
        "ca.spottedleaf.starlight.common.light.StarLightInterface"
    },
    allOptionalByDefault = true
)
public interface StarLightInterfaceProxy {

    ProxySupplier<StarLightInterfaceProxy> SERVICE = ProxyService.get().register(StarLightInterfaceProxy.class);

    static StarLightInterfaceProxy wrap(Object object) {
        return SERVICE.wrap(object);
    }

    @Set("minSection")
    void setMinSection(int minSection);

    @Set("maxSection")
    void setMaxSection(int maxSection);

    @Set("minLightSection")
    void setMinLightSection(int minLightSection);

    @Set("maxLightSection")
    void setMaxLightSection(int maxLightSection);
}
