package org.spongycastle.pqc.math.linearalgebra;

import org.glassfish.grizzly.http.server.util.MappingData;

public final class LittleEndianConversions {
    private LittleEndianConversions() {
    }

    public static int d(byte[] input) {
        return (input[0] & 255) | ((input[1] & 255) << 8) | ((input[2] & 255) << MappingData.PATH) | ((input[3] & 255) << 24);
    }

    public static int e(byte[] input, int inOff) {
        int inOff2 = inOff + 1;
        int inOff3 = inOff2 + 1;
        return (input[inOff] & 255) | ((input[inOff2] & 255) << 8) | ((input[inOff3] & 255) << MappingData.PATH) | ((input[inOff3 + 1] & 255) << 24);
    }

    public static int f(byte[] input, int inOff, int inLen) {
        int result = 0;
        for (int i = inLen - 1; i >= 0; i--) {
            result |= (input[inOff + i] & 255) << (i * 8);
        }
        return result;
    }

    public static byte[] c(int x) {
        return new byte[]{(byte) x, (byte) (x >>> 8), (byte) (x >>> 16), (byte) (x >>> 24)};
    }

    public static void a(int value, byte[] output, int outOff) {
        int outOff2 = outOff + 1;
        output[outOff] = (byte) value;
        int outOff3 = outOff2 + 1;
        output[outOff2] = (byte) (value >>> 8);
        int outOff4 = outOff3 + 1;
        output[outOff3] = (byte) (value >>> 16);
        int i = outOff4 + 1;
        output[outOff4] = (byte) (value >>> 24);
    }

    public static void b(int value, byte[] output, int outOff, int outLen) {
        for (int i = outLen - 1; i >= 0; i--) {
            output[outOff + i] = (byte) (value >>> (i * 8));
        }
    }

    public static byte[] g(int[] input, int outLen) {
        int intLen = input.length;
        byte[] result = new byte[outLen];
        int index = 0;
        int i = 0;
        while (i <= intLen - 2) {
            a(input[i], result, index);
            i++;
            index += 4;
        }
        b(input[intLen - 1], result, index, outLen - index);
        return result;
    }

    public static int[] h(byte[] input) {
        int intLen = (input.length + 3) / 4;
        int lastLen = input.length & 3;
        int[] result = new int[intLen];
        int index = 0;
        int i = 0;
        while (i <= intLen - 2) {
            result[i] = e(input, index);
            i++;
            index += 4;
        }
        if (lastLen != 0) {
            result[intLen - 1] = f(input, index, lastLen);
        } else {
            result[intLen - 1] = e(input, index);
        }
        return result;
    }
}
