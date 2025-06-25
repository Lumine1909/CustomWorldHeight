package io.github.lumine1909.customworldheight.config;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public record Height(int height, int minY, int logicalHeight, Function<Optional<Integer>, Optional<Integer>> couldHeightFunc) {

}
