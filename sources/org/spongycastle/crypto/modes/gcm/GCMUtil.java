package org.spongycastle.crypto.modes.gcm;

import org.spongycastle.util.Pack;

public abstract class GCMUtil {
    private static final int[] a = d();

    private static int[] d() {
        int[] lookup = new int[256];
        for (int c = 0; c < 256; c++) {
            int v = 0;
            for (int i = 7; i >= 0; i--) {
                if (((1 << i) & c) != 0) {
                    v ^= -520093696 >>> (7 - i);
                }
            }
            lookup[c] = v;
        }
        return lookup;
    }

    public static int[] i() {
        int[] tmp = new int[4];
        tmp[0] = Integer.MIN_VALUE;
        return tmp;
    }

    public static void a(int[] x, byte[] z) {
        Pack.e(x, z, 0);
    }

    public static int[] c(byte[] x) {
        int[] z = new int[4];
        Pack.b(x, 0, z);
        return z;
    }

    public static void b(byte[] x, int[] z) {
        Pack.b(x, 0, z);
    }

    public static void e(byte[] x, byte[] y) {
        int[] t1 = c(x);
        f(t1, c(y));
        a(t1, x);
    }

    public static void f(int[] x, int[] y) {
        int r00 = x[0];
        int r01 = x[1];
        int r02 = x[2];
        int r03 = x[3];
        int r10 = 0;
        int r11 = 0;
        int r12 = 0;
        int r13 = 0;
        for (int i = 0; i < 4; i++) {
            int bits = y[i];
            for (int j = 0; j < 32; j++) {
                int m1 = bits >> 31;
                bits <<= 1;
                r10 ^= r00 & m1;
                r11 ^= r01 & m1;
                r12 ^= r02 & m1;
                r13 ^= r03 & m1;
                r03 = (r03 >>> 1) | (r02 << 31);
                r02 = (r02 >>> 1) | (r01 << 31);
                r01 = (r01 >>> 1) | (r00 << 31);
                r00 = (r00 >>> 1) ^ (((r03 << 31) >> 8) & -520093696);
            }
        }
        x[0] = r10;
        x[1] = r11;
        x[2] = r12;
        x[3] = r13;
    }

    public static void g(int[] x, int[] z) {
        z[0] = z[0] ^ (-520093696 & (j(x, z) >> 8));
    }

    public static void h(int[] x, int[] y) {
        y[0] = y[0] ^ a[k(x, 8, y) >>> 24];
    }

    static int j(int[] x, int[] z) {
        int b = x[0];
        z[0] = b >>> 1;
        int c = b << 31;
        int b2 = x[1];
        z[1] = (b2 >>> 1) | c;
        int c2 = b2 << 31;
        int b3 = x[2];
        z[2] = (b3 >>> 1) | c2;
        int c3 = b3 << 31;
        int b4 = x[3];
        z[3] = (b4 >>> 1) | c3;
        return b4 << 31;
    }

    static int k(int[] x, int n, int[] z) {
        int b = x[0];
        int nInv = 32 - n;
        z[0] = b >>> n;
        int c = b << nInv;
        int b2 = x[1];
        z[1] = (b2 >>> n) | c;
        int c2 = b2 << nInv;
        int b3 = x[2];
        z[2] = (b3 >>> n) | c2;
        int c3 = b3 << nInv;
        int b4 = x[3];
        z[3] = (b4 >>> n) | c3;
        return b4 << nInv;
    }

    public static void l(byte[] x, byte[] y) {
        int i = 0;
        do {
            x[i] = (byte) (x[i] ^ y[i]);
            int i2 = i + 1;
            x[i2] = (byte) (x[i2] ^ y[i2]);
            int i3 = i2 + 1;
            x[i3] = (byte) (x[i3] ^ y[i3]);
            int i4 = i3 + 1;
            x[i4] = (byte) (x[i4] ^ y[i4]);
            i = i4 + 1;
        } while (i < 16);
    }

    public static void m(byte[] x, byte[] y, int yOff, int yLen) {
        while (true) {
            yLen--;
            if (yLen >= 0) {
                x[yLen] = (byte) (x[yLen] ^ y[yOff + yLen]);
            } else {
                return;
            }
        }
    }

    public static void n(int[] x, int[] y, int[] z) {
        z[0] = x[0] ^ y[0];
        z[1] = x[1] ^ y[1];
        z[2] = x[2] ^ y[2];
        z[3] = x[3] ^ y[3];
    }
}
