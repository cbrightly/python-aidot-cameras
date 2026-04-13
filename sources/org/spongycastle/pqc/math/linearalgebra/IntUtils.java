package org.spongycastle.pqc.math.linearalgebra;

public final class IntUtils {
    private IntUtils() {
    }

    public static boolean b(int[] left, int[] right) {
        if (left.length != right.length) {
            return false;
        }
        boolean result = true;
        for (int i = left.length - 1; i >= 0; i--) {
            result &= left[i] == right[i];
        }
        return result;
    }

    public static int[] a(int[] array) {
        int[] result = new int[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }
}
