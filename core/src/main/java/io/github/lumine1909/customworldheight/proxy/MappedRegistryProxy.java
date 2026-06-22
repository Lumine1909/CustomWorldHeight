package io.github.lumine1909.customworldheight.proxy;

import com.example.proxying.api.ProxyService;
import com.example.proxying.api.ProxySupplier;
import com.example.proxying.api.annotation.Proxy;
import com.example.proxying.api.annotation.Set;

import java.util.Map;

@Proxy(target = "net.minecraft.core.MappedRegistry")
public interface MappedRegistryProxy {

    ProxySupplier<MappedRegistryProxy> SERVICE = ProxyService.get().register(MappedRegistryProxy.class);

    static MappedRegistryProxy wrap(Object object) {
        return SERVICE.wrap(object);
    }

    @Set("frozen")
    void setFrozen(boolean frozen);

    @Set("unregisteredIntrusiveHolders")
    void setIntrusiveHolders(Map<?, ?> intrusiveHolders);
}