package org.glassfish.tyrus.core;

import java.util.HashMap;

public class PrimitivesToWrappers {
    private static final HashMap<Class<?>, Class<?>> conversionMap;

    PrimitivesToWrappers() {
    }

    static {
        HashMap<Class<?>, Class<?>> hashMap = new HashMap<>();
        conversionMap = hashMap;
        hashMap.put(Integer.TYPE, Integer.class);
        hashMap.put(Short.TYPE, Short.class);
        hashMap.put(Long.TYPE, Long.class);
        hashMap.put(Double.TYPE, Double.class);
        hashMap.put(Float.TYPE, Float.class);
        hashMap.put(Boolean.TYPE, Boolean.class);
        hashMap.put(Byte.TYPE, Byte.class);
        hashMap.put(Character.TYPE, Character.class);
        hashMap.put(Void.TYPE, Void.class);
    }

    public static Class<?> getPrimitiveWrapper(Class<?> c) {
        if (!c.isPrimitive()) {
            return c;
        }
        HashMap<Class<?>, Class<?>> hashMap = conversionMap;
        return hashMap.containsKey(c) ? hashMap.get(c) : c;
    }

    public static boolean isPrimitiveWrapper(Class<?> c) {
        return conversionMap.containsValue(c);
    }
}
