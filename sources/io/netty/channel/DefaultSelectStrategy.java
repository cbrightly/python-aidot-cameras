package io.netty.channel;

import io.netty.util.IntSupplier;

public final class DefaultSelectStrategy implements SelectStrategy {
    static final SelectStrategy INSTANCE = new DefaultSelectStrategy();

    private DefaultSelectStrategy() {
    }

    public int calculateStrategy(IntSupplier selectSupplier, boolean hasTasks) {
        if (hasTasks) {
            return selectSupplier.get();
        }
        return -1;
    }
}
