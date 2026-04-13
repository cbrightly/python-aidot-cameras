package org.spongycastle.pqc.math.ntru.util;

public class ArrayEncoder {
    private static final int[] a = {0, 0, 0, 1, 1, 1, -1, -1};
    private static final int[] b = {0, 1, -1, 0, 1, -1, 0, 1};
    private static final int[] c = {1, 1, 1, 0, 0, 0, 1, 0, 1};
    private static final int[] d = {1, 1, 1, 1, 0, 0, 0, 1, 0};
    private static final int[] e = {1, 0, 1, 0, 0, 1, 1, 1, 0};

    public static byte[] d(int[] a2, int q) {
        int bitsPerCoeff = 31 - Integer.numberOfLeadingZeros(q);
        byte[] data = new byte[(((a2.length * bitsPerCoeff) + 7) / 8)];
        int bitIndex = 0;
        int byteIndex = 0;
        for (int i = 0; i < a2.length; i++) {
            for (int j = 0; j < bitsPerCoeff; j++) {
                data[byteIndex] = (byte) (data[byteIndex] | (((a2[i] >> j) & 1) << bitIndex));
                if (bitIndex == 7) {
                    bitIndex = 0;
                    byteIndex++;
                } else {
                    bitIndex++;
                }
            }
        }
        return data;
    }

    public static int[] b(byte[] data, int N, int q) {
        int[] coeffs = new int[N];
        int bitsPerCoeff = 31 - Integer.numberOfLeadingZeros(q);
        int numBits = N * bitsPerCoeff;
        int coeffIndex = 0;
        for (int bitIndex = 0; bitIndex < numBits; bitIndex++) {
            if (bitIndex > 0 && bitIndex % bitsPerCoeff == 0) {
                coeffIndex++;
            }
            coeffs[coeffIndex] = coeffs[coeffIndex] + (e(data, bitIndex) << (bitIndex % bitsPerCoeff));
        }
        return coeffs;
    }

    public static int[] a(byte[] data, int N) {
        int[] coeffs = new int[N];
        int coeffIndex = 0;
        int bit1 = 0;
        while (bit1 < data.length * 8) {
            int bitIndex = bit1 + 1;
            int bitIndex2 = bitIndex + 1;
            int bitIndex3 = bitIndex2 + 1;
            int coeffTableIndex = (e(data, bit1) * 4) + (e(data, bitIndex) * 2) + e(data, bitIndex2);
            int coeffIndex2 = coeffIndex + 1;
            coeffs[coeffIndex] = a[coeffTableIndex];
            coeffIndex = coeffIndex2 + 1;
            coeffs[coeffIndex2] = b[coeffTableIndex];
            if (coeffIndex > N - 2) {
                break;
            }
            bit1 = bitIndex3;
        }
        return coeffs;
    }

    public static byte[] c(int[] arr) {
        int[] iArr = arr;
        byte[] data = new byte[(((((iArr.length * 3) + 1) / 2) + 7) / 8)];
        int bitIndex = 0;
        int byteIndex = 0;
        int coeff1 = 0;
        while (coeff1 < (iArr.length / 2) * 2) {
            int i = coeff1 + 1;
            int coeff12 = iArr[coeff1] + 1;
            int i2 = i + 1;
            int coeff2 = iArr[i] + 1;
            if (coeff12 == 0 && coeff2 == 0) {
                throw new IllegalStateException("Illegal encoding!");
            }
            int bitTableIndex = (coeff12 * 3) + coeff2;
            int[] bits = {c[bitTableIndex], d[bitTableIndex], e[bitTableIndex]};
            for (int j = 0; j < 3; j++) {
                data[byteIndex] = (byte) (data[byteIndex] | (bits[j] << bitIndex));
                if (bitIndex == 7) {
                    bitIndex = 0;
                    byteIndex++;
                } else {
                    bitIndex++;
                }
            }
            coeff1 = i2;
        }
        return data;
    }

    private static int e(byte[] arr, int bitIndex) {
        return ((arr[bitIndex / 8] & 255) >> (bitIndex % 8)) & 1;
    }
}
