package io.netty.util;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.util.concurrent.ConcurrentMap;

public final class AttributeKey<T> extends UniqueName {
    private static final ConcurrentMap<String, AttributeKey> names = PlatformDependent.newConcurrentHashMap();

    public static <T> AttributeKey<T> valueOf(String name) {
        ObjectUtil.checkNotNull(name, "name");
        ConcurrentMap<String, AttributeKey> concurrentMap = names;
        AttributeKey<T> option = (AttributeKey) concurrentMap.get(name);
        if (option != null) {
            return option;
        }
        AttributeKey<T> option2 = new AttributeKey<>(name);
        AttributeKey<T> old = concurrentMap.putIfAbsent(name, option2);
        if (old != null) {
            return old;
        }
        return option2;
    }

    public static boolean exists(String name) {
        ObjectUtil.checkNotNull(name, "name");
        return names.containsKey(name);
    }

    public static <T> AttributeKey<T> newInstance(String name) {
        ObjectUtil.checkNotNull(name, "name");
        AttributeKey<T> option = new AttributeKey<>(name);
        if (names.putIfAbsent(name, option) == null) {
            return option;
        }
        throw new IllegalArgumentException(String.format("'%s' is already in use", new Object[]{name}));
    }

    @Deprecated
    public AttributeKey(String name) {
        super(name);
    }
}
