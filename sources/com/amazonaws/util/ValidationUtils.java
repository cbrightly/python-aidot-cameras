package com.amazonaws.util;

import java.util.Collection;

public class ValidationUtils {
    public static <T> T assertNotNull(T object, String fieldName) {
        if (object != null) {
            return object;
        }
        throw new IllegalArgumentException(String.format("%s cannot be null", new Object[]{fieldName}));
    }

    public static void assertParameterNotNull(Object parameterValue, String errorMessage) {
        if (parameterValue == null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void assertAllAreNull(String messageIfNull, Object... objects) {
        int length = objects.length;
        int i = 0;
        while (i < length) {
            if (objects[i] == null) {
                i++;
            } else {
                throw new IllegalArgumentException(messageIfNull);
            }
        }
    }

    public static int assertIsPositive(int num, String fieldName) {
        if (num > 0) {
            return num;
        }
        throw new IllegalArgumentException(String.format("%s must be positive", new Object[]{fieldName}));
    }

    public static <T extends Collection<?>> T assertNotEmpty(T collection, String fieldName) {
        assertNotNull(collection, fieldName);
        if (!collection.isEmpty()) {
            return collection;
        }
        throw new IllegalArgumentException(String.format("%s cannot be empty", new Object[]{fieldName}));
    }

    public static <T> T[] assertNotEmpty(T[] array, String fieldName) {
        assertNotNull(array, fieldName);
        if (array.length != 0) {
            return array;
        }
        throw new IllegalArgumentException(String.format("%s cannot be empty", new Object[]{fieldName}));
    }

    public static String assertStringNotEmpty(String string, String fieldName) {
        assertNotNull(string, fieldName);
        if (!string.isEmpty()) {
            return string;
        }
        throw new IllegalArgumentException(String.format("%s cannot be empty", new Object[]{fieldName}));
    }
}
