package io.github.lumine1909.customworldheight.api;

import org.bukkit.Bukkit;

import java.util.Objects;

public interface WorldHeightService {

    WorldHeightService INSTANCE = Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(WorldHeightService.class)).getProvider();

    Height getHeight(Identifier id);

    void removeHeight(Identifier id);

    default void registerWorld(Identifier id, String name, Height height, BaseDimensionType dimensionType) {
        registerWorld(id, name, height, dimensionType, false);
    }

    void registerWorld(Identifier id, String name, Height height, BaseDimensionType dimensionType, boolean override);

    default void registerRegex(Identifier id, String regex, Height height, BaseDimensionType dimensionType) {
        registerRegex(id, regex, height, dimensionType, true);
    }

    void registerRegex(Identifier id, String regex, Height height, BaseDimensionType dimensionType, boolean override);
}
