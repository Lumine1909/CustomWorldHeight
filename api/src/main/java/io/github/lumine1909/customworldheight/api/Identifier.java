package io.github.lumine1909.customworldheight.api;

import org.jetbrains.annotations.NotNull;

public record Identifier(String namespace, String value) {

    public static Identifier of(String namespace, String name) {
        return new Identifier(namespace, name);
    }

    public static Identifier parse(String id) {
        String[] str = id.split(":");
        if (str.length != 2) {
            throw new IllegalArgumentException("Invalid identifier format");
        }
        return new Identifier(str[0], str[1]);
    }

    @Override
    public @NotNull String toString() {
        return namespace + ":" + value;
    }
}
