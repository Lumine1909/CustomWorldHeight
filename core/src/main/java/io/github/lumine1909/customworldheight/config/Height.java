package io.github.lumine1909.customworldheight.config;

import java.util.Optional;
import java.util.function.Function;

public record Height(int height, int minY, int logicalHeight, Function<Optional<Float>, Optional<Float>> couldHeightFunc) {

}
