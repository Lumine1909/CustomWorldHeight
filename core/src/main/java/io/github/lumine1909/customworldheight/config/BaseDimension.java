package io.github.lumine1909.customworldheight.config;

import java.util.HashMap;
import java.util.Map;

public enum BaseDimension {
    OVERWORLD("overworld"),
    NETHER("the_nether"),
    END("the_end"),
    CAVES("overworld_caves"),
    CUSTOM("custom");

    private static final Map<String, BaseDimension> byName = new HashMap<>();

    static {
        for (BaseDimension dimension : values()) {
            byName.put(dimension.name, dimension);
        }
    }

    private final String name;

    BaseDimension(String name) {
        this.name = name;
    }

    public static BaseDimension getByName(String name) {
        return byName.getOrDefault(name, CUSTOM);
    }

    public String getName() {
        return name;
    }
}
