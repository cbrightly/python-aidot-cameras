package org.spongycastle.math.raw;

import java.math.BigInteger;
import org.spongycastle.util.Pack;

public abstract class Nat128 {
    public static int a(int[] x, int[] y, int[] z) {
        long c = 0 + (((long) x[0]) & 4294967295L) + (((long) y[0]) & 4294967295L);
        z[0] = (int) c;
        long c2 = (c >>> 32) + (((long) x[1]) & 4294967295L) + (((long) y[1]) & 4294967295L);
        z[1] = (int) c2;
        long c3 = (c2 >>> 32) + (((long) x[2]) & 4294967295L) + (((long) y[2]) & 4294967295L);
        z[2] = (int) c3;
        long c4 = (c3 >>> 32) + (((long) x[3]) & 4294967295L) + (((long) y[3]) & 4294967295L);
        z[3] = (int) c4;
        return (int) (c4 >>> 32);
    }

    public static int b(int[] x, int[] y, int[] z) {
        long c = 0 + (((long) x[0]) & 4294967295L) + (((long) y[0]) & 4294967295L) + (((long) z[0]) & 4294967295L);
        z[0] = (int) c;
        long c2 = (c >>> 32) + (((long) x[1]) & 4294967295L) + (((long) y[1]) & 4294967295L) + (((long) z[1]) & 4294967295L);
        z[1] = (int) c2;
        long c3 = (c2 >>> 32) + (((long) x[2]) & 4294967295L) + (((long) y[2]) & 4294967295L) + (((long) z[2]) & 4294967295L);
        z[2] = (int) c3;
        long c4 = (c3 >>> 32) + (((long) x[3]) & 4294967295L) + (((long) y[3]) & 4294967295L) + (((long) z[3]) & 4294967295L);
        z[3] = (int) c4;
        return (int) (c4 >>> 32);
    }

    public static int[] c() {
        return new int[4];
    }

    public static long[] d() {
        return new long[2];
    }

    public static int[] e() {
        return new int[8];
    }

    public static long[] f() {
        return new long[4];
    }

    public static boolean g(int[] x, int[] y) {
        for (int i = 3; i >= 0; i--) {
            if (x[i] != y[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean h(long[] x, long[] y) {
        for (int i = 1; i >= 0; i--) {
            if (x[i] != y[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] i(BigInteger x) {
        if (x.signum() < 0 || x.bitLength() > 128) {
            throw new IllegalArgumentException();
        }
        int[] z = c();
        int i = 0;
        while (x.signum() != 0) {
            z[i] = x.intValue();
            x = x.shiftRight(32);
            i++;
        }
        return z;
    }

    public static long[] j(BigInteger x) {
        if (x.signum() < 0 || x.bitLength() > 128) {
            throw new IllegalArgumentException();
        }
        long[] z = d();
        int i = 0;
        while (x.signum() != 0) {
            z[i] = x.longValue();
            x = x.shiftRight(64);
            i++;
        }
        return z;
    }

    public static int k(int[] x, int bit) {
        if (bit == 0) {
            return x[0] & 1;
        }
        int w = bit >> 5;
        if (w < 0 || w >= 4) {
            return 0;
        }
        return (x[w] >>> (bit & 31)) & 1;
    }

    public static boolean l(int[] x, int[] y) {
        for (int i = 3; i >= 0; i--) {
            int x_i = x[i] ^ Integer.MIN_VALUE;
            int y_i = Integer.MIN_VALUE ^ y[i];
            if (x_i < y_i) {
                return false;
            }
            if (x_i > y_i) {
                return true;
            }
        }
        return true;
    }

    public static boolean m(int[] x) {
        if (x[0] != 1) {
            return false;
        }
        for (int i = 1; i < 4; i++) {
            if (x[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean n(long[] x) {
        if (x[0] != 1) {
            return false;
        }
        for (int i = 1; i < 2; i++) {
            if (x[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean o(int[] x) {
        for (int i = 0; i < 4; i++) {
            if (x[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean p(long[] x) {
        for (int i = 0; i < 2; i++) {
            if (x[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static void q(int[] x, int[] y, int[] zz) {
        long y_0 = ((long) y[0]) & 4294967295L;
        long y_1 = ((long) y[1]) & 4294967295L;
        long y_3 = ((long) y[3]) & 4294967295L;
        long y_2 = ((long) y[2]) & 4294967295L;
        long x_0 = ((long) x[0]) & 4294967295L;
        long c = 0 + (x_0 * y_0);
        zz[0] = (int) c;
        long c2 = (c >>> 32) + (x_0 * y_1);
        zz[1] = (int) c2;
        long c3 = (c2 >>> 32) + (x_0 * y_2);
        zz[2] = (int) c3;
        long c4 = (c3 >>> 32) + (x_0 * y_3);
        zz[3] = (int) c4;
        zz[4] = (int) (c4 >>> 32);
        int i = 1;
        for (int i2 = 4; i < i2; i2 = 4) {
            long x_i = ((long) x[i]) & 4294967295L;
            long y_02 = y_0;
            long c5 = 0 + (x_i * y_0) + (((long) zz[i + 0]) & 4294967295L);
            zz[i + 0] = (int) c5;
            long c6 = (c5 >>> 32) + (x_i * y_1) + (((long) zz[i + 1]) & 4294967295L);
            zz[i + 1] = (int) c6;
            long c7 = (c6 >>> 32) + (x_i * y_2) + (((long) zz[i + 2]) & 4294967295L);
            zz[i + 2] = (int) c7;
            long c8 = (c7 >>> 32) + (x_i * y_3) + (((long) zz[i + 3]) & 4294967295L);
            zz[i + 3] = (int) c8;
            zz[i + 4] = (int) (c8 >>> 32);
            i++;
            y_0 = y_02;
        }
    }

    public static int r(int[] x, int[] y, int[] zz) {
        long y_0 = ((long) y[0]) & 4294967295L;
        long y_1 = ((long) y[1]) & 4294967295L;
        long y_2 = ((long) y[2]) & 4294967295L;
        long y_3 = ((long) y[3]) & 4294967295L;
        long x_i = 0;
        int i = 0;
        while (i < 4) {
            long zc = x_i;
            long x_i2 = ((long) x[i]) & 4294967295L;
            long c = 0 + (x_i2 * y_0) + (((long) zz[i + 0]) & 4294967295L);
            zz[i + 0] = (int) c;
            long c2 = (c >>> 32) + (x_i2 * y_1) + (((long) zz[i + 1]) & 4294967295L);
            zz[i + 1] = (int) c2;
            long y_12 = y_1;
            long c3 = (c2 >>> 32) + (x_i2 * y_2) + (((long) zz[i + 2]) & 4294967295L);
            zz[i + 2] = (int) c3;
            long c4 = (c3 >>> 32) + (x_i2 * y_3) + (((long) zz[i + 3]) & 4294967295L);
            zz[i + 3] = (int) c4;
            long c5 = (c4 >>> 32) + zc + (((long) zz[i + 4]) & 4294967295L);
            zz[i + 4] = (int) c5;
            x_i = c5 >>> 32;
            i++;
            y_0 = y_0;
            y_1 = y_12;
        }
        return (int) x_i;
    }

    public static void s(int[] x, int[] zz) {
        long x_0 = ((long) x[0]) & 4294967295L;
        int c = 0;
        int i = 3;
        int j = 8;
        while (true) {
            int i2 = i - 1;
            long xVal = ((long) x[i]) & 4294967295L;
            long p = xVal * xVal;
            int j2 = j - 1;
            zz[j2] = (c << 31) | ((int) (p >>> 33));
            j = j2 - 1;
            zz[j] = (int) (p >>> 1);
            c = (int) p;
            if (i2 <= 0) {
                long p2 = x_0 * x_0;
                zz[0] = (int) p2;
                long x_1 = ((long) x[1]) & 4294967295L;
                long zz_1 = ((((long) (c << 31)) & 4294967295L) | (p2 >>> 33)) + (x_1 * x_0);
                int w = (int) zz_1;
                zz[1] = (w << 1) | (((int) (p2 >>> 32)) & 1);
                long zz_2 = (((long) zz[2]) & 4294967295L) + (zz_1 >>> 32);
                int i3 = w;
                long x_2 = ((long) x[2]) & 4294967295L;
                long x_12 = x_1;
                long x_02 = x_0;
                long zz_22 = zz_2 + (x_2 * x_02);
                int w2 = (int) zz_22;
                zz[2] = (w2 << 1) | (w >>> 31);
                int c2 = w2 >>> 31;
                long zz_3 = (((long) zz[3]) & 4294967295L) + (zz_22 >>> 32) + (x_2 * x_12);
                long zz_4 = (((long) zz[4]) & 4294967295L) + (zz_3 >>> 32);
                int i4 = w2;
                long x_3 = ((long) x[3]) & 4294967295L;
                long j3 = zz_1;
                long zz_5 = (((long) zz[5]) & 4294967295L) + (zz_4 >>> 32);
                long j4 = zz_22;
                long zz_6 = (((long) zz[6]) & 4294967295L) + (zz_5 >>> 32);
                long zz_32 = (zz_3 & 4294967295L) + (x_3 * x_02);
                int w3 = (int) zz_32;
                zz[3] = (w3 << 1) | c2;
                long zz_42 = (zz_4 & 4294967295L) + (zz_32 >>> 32) + (x_3 * x_12);
                long zz_52 = (zz_5 & 4294967295L) + (zz_42 >>> 32) + (x_3 * x_2);
                long zz_62 = zz_6 + (zz_52 >>> 32);
                long zz_53 = 4294967295L & zz_52;
                int w4 = (int) zz_42;
                zz[4] = (w4 << 1) | (w3 >>> 31);
                int c3 = w4 >>> 31;
                int w5 = (int) zz_53;
                zz[5] = (w5 << 1) | c3;
                int c4 = w5 >>> 31;
                int w6 = (int) zz_62;
                zz[6] = (w6 << 1) | c4;
                long j5 = zz_42;
                zz[7] = ((zz[7] + ((int) (zz_62 >>> 32))) << 1) | (w6 >>> 31);
                return;
            }
            i = i2;
        }
    }

    public static int t(int[] x, int[] y, int[] z) {
        long c = 0 + ((((long) x[0]) & 4294967295L) - (((long) y[0]) & 4294967295L));
        z[0] = (int) c;
        long c2 = (c >> 32) + ((((long) x[1]) & 4294967295L) - (((long) y[1]) & 4294967295L));
        z[1] = (int) c2;
        long c3 = (c2 >> 32) + ((((long) x[2]) & 4294967295L) - (((long) y[2]) & 4294967295L));
        z[2] = (int) c3;
        long c4 = (c3 >> 32) + ((((long) x[3]) & 4294967295L) - (((long) y[3]) & 4294967295L));
        z[3] = (int) c4;
        return (int) (c4 >> 32);
    }

    public static int u(int[] x, int[] z) {
        long c = 0 + ((((long) z[0]) & 4294967295L) - (((long) x[0]) & 4294967295L));
        z[0] = (int) c;
        long c2 = (c >> 32) + ((((long) z[1]) & 4294967295L) - (((long) x[1]) & 4294967295L));
        z[1] = (int) c2;
        long c3 = (c2 >> 32) + ((((long) z[2]) & 4294967295L) - (((long) x[2]) & 4294967295L));
        z[2] = (int) c3;
        long c4 = (c3 >> 32) + ((((long) z[3]) & 4294967295L) - (((long) x[3]) & 4294967295L));
        z[3] = (int) c4;
        return (int) (c4 >> 32);
    }

    public static BigInteger v(int[] x) {
        byte[] bs = new byte[16];
        for (int i = 0; i < 4; i++) {
            int x_i = x[i];
            if (x_i != 0) {
                Pack.d(x_i, bs, (3 - i) << 2);
            }
        }
        return new BigInteger(1, bs);
    }

    public static BigInteger w(long[] x) {
        byte[] bs = new byte[16];
        for (int i = 0; i < 2; i++) {
            long x_i = x[i];
            if (x_i != 0) {
                Pack.p(x_i, bs, (1 - i) << 3);
            }
        }
        return new BigInteger(1, bs);
    }

    public static void x(int[] z) {
        z[0] = 0;
        z[1] = 0;
        z[2] = 0;
        z[3] = 0;
    }
}
