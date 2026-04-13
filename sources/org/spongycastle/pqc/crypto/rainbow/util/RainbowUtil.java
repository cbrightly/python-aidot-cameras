package org.spongycastle.pqc.crypto.rainbow.util;

import java.lang.reflect.Array;

public class RainbowUtil {
    public static int[] g(byte[] in) {
        int[] out = new int[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = in[i] & 255;
        }
        return out;
    }

    public static short[] b(byte[] in) {
        short[] out = new short[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = (short) (in[i] & 255);
        }
        return out;
    }

    public static short[][] d(byte[][] in) {
        int length = in.length;
        int[] iArr = new int[2];
        iArr[1] = in[0].length;
        iArr[0] = length;
        short[][] out = (short[][]) Array.newInstance(short.class, iArr);
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                out[i][j] = (short) (in[i][j] & 255);
            }
        }
        return out;
    }

    public static short[][][] f(byte[][][] in) {
        int length = in.length;
        int length2 = in[0].length;
        int[] iArr = new int[3];
        iArr[2] = in[0][0].length;
        iArr[1] = length2;
        iArr[0] = length;
        short[][][] out = (short[][][]) Array.newInstance(short.class, iArr);
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                for (int k = 0; k < in[0][0].length; k++) {
                    out[i][j][k] = (short) (in[i][j][k] & 255);
                }
            }
        }
        return out;
    }

    public static byte[] h(int[] in) {
        byte[] out = new byte[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = (byte) in[i];
        }
        return out;
    }

    public static byte[] a(short[] in) {
        byte[] out = new byte[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = (byte) in[i];
        }
        return out;
    }

    public static byte[][] c(short[][] in) {
        int length = in.length;
        int[] iArr = new int[2];
        iArr[1] = in[0].length;
        iArr[0] = length;
        byte[][] out = (byte[][]) Array.newInstance(byte.class, iArr);
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                out[i][j] = (byte) in[i][j];
            }
        }
        return out;
    }

    public static byte[][][] e(short[][][] in) {
        int length = in.length;
        int length2 = in[0].length;
        int[] iArr = new int[3];
        iArr[2] = in[0][0].length;
        iArr[1] = length2;
        iArr[0] = length;
        byte[][][] out = (byte[][][]) Array.newInstance(byte.class, iArr);
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                for (int k = 0; k < in[0][0].length; k++) {
                    out[i][j][k] = (byte) in[i][j][k];
                }
            }
        }
        return out;
    }

    public static boolean i(short[] left, short[] right) {
        if (left.length != right.length) {
            return false;
        }
        boolean result = true;
        for (int i = left.length - 1; i >= 0; i--) {
            result &= left[i] == right[i];
        }
        return result;
    }

    public static boolean j(short[][] left, short[][] right) {
        if (left.length != right.length) {
            return false;
        }
        boolean result = true;
        for (int i = left.length - 1; i >= 0; i--) {
            result &= i(left[i], right[i]);
        }
        return result;
    }

    public static boolean k(short[][][] left, short[][][] right) {
        if (left.length != right.length) {
            return false;
        }
        boolean result = true;
        for (int i = left.length - 1; i >= 0; i--) {
            result &= j(left[i], right[i]);
        }
        return result;
    }
}
