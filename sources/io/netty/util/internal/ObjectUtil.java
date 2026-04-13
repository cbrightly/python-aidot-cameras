package io.netty.util.internal;

public final class ObjectUtil {
    private ObjectUtil() {
    }

    public static <T> T checkNotNull(T arg, String text) {
        if (arg != null) {
            return arg;
        }
        throw new NullPointerException(text);
    }

    public static int checkPositive(int i, String name) {
        if (i > 0) {
            return i;
        }
        throw new IllegalArgumentException(name + ": " + i + " (expected: > 0)");
    }

    public static long checkPositive(long i, String name) {
        if (i > 0) {
            return i;
        }
        throw new IllegalArgumentException(name + ": " + i + " (expected: > 0)");
    }

    public static int checkPositiveOrZero(int i, String name) {
        if (i >= 0) {
            return i;
        }
        throw new IllegalArgumentException(name + ": " + i + " (expected: >= 0)");
    }

    public static long checkPositiveOrZero(long i, String name) {
        if (i >= 0) {
            return i;
        }
        throw new IllegalArgumentException(name + ": " + i + " (expected: >= 0)");
    }

    public static <T> T[] checkNonEmpty(T[] array, String name) {
        checkNotNull(array, name);
        int length = array.length;
        checkPositive(length, name + ".length");
        return array;
    }

    public static int intValue(Integer wrapper, int defaultValue) {
        return wrapper != null ? wrapper.intValue() : defaultValue;
    }

    public static long longValue(Long wrapper, long defaultValue) {
        return wrapper != null ? wrapper.longValue() : defaultValue;
    }
}
