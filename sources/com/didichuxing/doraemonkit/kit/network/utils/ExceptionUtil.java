package com.didichuxing.doraemonkit.kit.network.utils;

public class ExceptionUtil {
    public static <T extends Throwable> void propagateIfInstanceOf(Throwable t, Class<T> type) {
        if (type.isInstance(t)) {
            throw t;
        }
    }

    public static RuntimeException propagate(Throwable t) {
        propagateIfInstanceOf(t, Error.class);
        propagateIfInstanceOf(t, RuntimeException.class);
        throw new RuntimeException(t);
    }

    public static <T extends Throwable> void sneakyThrow(Throwable t) {
        throw t;
    }
}
