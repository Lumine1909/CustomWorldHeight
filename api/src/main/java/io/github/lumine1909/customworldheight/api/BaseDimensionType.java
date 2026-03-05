package io.github.lumine1909.customworldheight.api;

import java.util.HashMap;
import java.util.Map;

public enum BaseDimensionType {
    OVERWORLD("overworld"),
    NETHER("the_nether"),
    END("the_end"),
    CAVES("overworld_caves"),
    CUSTOM("custom");

    private static final Map<String, BaseDimensionType> byName = new HashMap<>();

    static {
        for (BaseDimensionType dimension : values()) {
            byName.put(dimension.name, dimension);
        }
    }

    private final String name;

    BaseDimensionType(String name) {
        this.name = name;
    }

    public static BaseDimensionType getByName(String name) {
        return byName.getOrDefault(name, CUSTOM);
    }

    public String getName() {
        return name;
    }
}
