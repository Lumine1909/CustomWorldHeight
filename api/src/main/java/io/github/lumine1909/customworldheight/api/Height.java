package io.github.lumine1909.customworldheight.api;

import java.util.Optional;
import java.util.function.Function;

public record Height(int height, int minY, int logicalHeight, CloudHeight cloudHeight) {

    public static class CloudHeight {

        public static final CloudHeight EMPTY = new CloudHeight(v -> Optional.empty());
        public static final CloudHeight DEFAULT = new CloudHeight(Function.identity());
        private final Function<Optional<Float>, Optional<Float>> function;

        private CloudHeight(Function<Optional<Float>, Optional<Float>> function) {
            this.function = function;
        }

        public static CloudHeight height(float height) {
            return new CloudHeight(v -> Optional.of(height));
        }

        public Optional<Float> get(Optional<Float> defaultHeight) {
            return function.apply(defaultHeight);
        }
    }
}
