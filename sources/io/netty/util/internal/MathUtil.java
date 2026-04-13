package io.netty.util.internal;

public final class MathUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private MathUtil() {
    }

    public static int findNextPositivePowerOfTwo(int value) {
        if (value > Integer.MIN_VALUE && value < 1073741824) {
            return 1 << (32 - Integer.numberOfLeadingZeros(value - 1));
        }
        throw new AssertionError();
    }

    public static int safeFindNextPositivePowerOfTwo(int value) {
        if (value <= 0) {
            return 1;
        }
        if (value >= 1073741824) {
            return 1073741824;
        }
        return findNextPositivePowerOfTwo(value);
    }

    public static boolean isOutOfBounds(int index, int length, int capacity) {
        return (((index | length) | (index + length)) | (capacity - (index + length))) < 0;
    }

    public static int compare(long x, long y) {
        if (x < y) {
            return -1;
        }
        return x > y ? 1 : 0;
    }
}
