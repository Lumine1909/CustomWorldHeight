package io.github.lumine1909.customworldheight.util;

import io.github.lumine1909.customworldheight.data.DataHandler;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class NmsLoader {

    private static final NavigableMap<Integer, String> DATA_HANDLERS = new TreeMap<>() {{
        put(12005, "io.github.lumine1909.customworldheight.data.DataHandler_1_20_5");
        put(12100, "io.github.lumine1909.customworldheight.data.DataHandler_1_21");
        put(12103, "io.github.lumine1909.customworldheight.data.DataHandler_1_21_3");
        put(12106, "io.github.lumine1909.customworldheight.data.DataHandler_1_21_6");
        put(12111, "io.github.lumine1909.customworldheight.data.DataHandler_1_21_11");
        put(260000, "io.github.lumine1909.customworldheight.data.DataHandler_26_1");
        put(260200, "io.github.lumine1909.customworldheight.data.DataHandler_26_2");
    }};

    public static DataHandler<?, ?, ?> loadDataHandler(int version) {
        Map.Entry<Integer, String> entry = DATA_HANDLERS.floorEntry(version);
        if (entry == null) {
            throw new RuntimeException("Unsupported version: " + version);
        }
        try {
            Class<?> clazz = Class.forName(entry.getValue());
            return (DataHandler<?, ?, ?>) clazz.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to load NMS implementation for version " + version, e);
        }
    }
}
