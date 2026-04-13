package org.spongycastle.pqc.math.linearalgebra;

public final class ByteUtils {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private ByteUtils() {
    }

    public static boolean b(byte[] left, byte[] right) {
        if (left == null) {
            if (right == null) {
                return true;
            }
            return false;
        } else if (right == null || left.length != right.length) {
            return false;
        } else {
            boolean result = true;
            for (int i = left.length - 1; i >= 0; i--) {
                result &= left[i] == right[i];
            }
            return result;
        }
    }

    public static String e(byte[] input) {
        String result = "";
        for (int i = 0; i < input.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(result);
            char[] cArr = a;
            sb.append(cArr[(input[i] >>> 4) & 15]);
            result = sb.toString() + cArr[input[i] & 15];
        }
        return result;
    }

    public static byte[] a(byte[] x1, byte[] x2) {
        byte[] result = new byte[(x1.length + x2.length)];
        System.arraycopy(x1, 0, result, 0, x1.length);
        System.arraycopy(x2, 0, result, x1.length, x2.length);
        return result;
    }

    public static byte[][] c(byte[] input, int index) {
        if (index <= input.length) {
            byte[][] result = {new byte[index], new byte[(input.length - index)]};
            System.arraycopy(input, 0, result[0], 0, index);
            System.arraycopy(input, index, result[1], 0, input.length - index);
            return result;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static byte[] d(byte[] input, int start, int end) {
        byte[] result = new byte[(end - start)];
        System.arraycopy(input, start, result, 0, end - start);
        return result;
    }
}
