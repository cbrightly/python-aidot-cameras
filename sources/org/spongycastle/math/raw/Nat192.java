package org.spongycastle.math.raw;

import java.math.BigInteger;
import org.spongycastle.util.Pack;

public abstract class Nat192 {
    public static int a(int[] x, int[] y, int[] z) {
        long c = 0 + (((long) x[0]) & 4294967295L) + (((long) y[0]) & 4294967295L);
        z[0] = (int) c;
        long c2 = (c >>> 32) + (((long) x[1]) & 4294967295L) + (((long) y[1]) & 4294967295L);
        z[1] = (int) c2;
        long c3 = (c2 >>> 32) + (((long) x[2]) & 4294967295L) + (((long) y[2]) & 4294967295L);
        z[2] = (int) c3;
        long c4 = (c3 >>> 32) + (((long) x[3]) & 4294967295L) + (((long) y[3]) & 4294967295L);
        z[3] = (int) c4;
        long c5 = (c4 >>> 32) + (((long) x[4]) & 4294967295L) + (((long) y[4]) & 4294967295L);
        z[4] = (int) c5;
        long c6 = (c5 >>> 32) + (((long) x[5]) & 4294967295L) + (((long) y[5]) & 4294967295L);
        z[5] = (int) c6;
        return (int) (c6 >>> 32);
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
        long c5 = (c4 >>> 32) + (((long) x[4]) & 4294967295L) + (((long) y[4]) & 4294967295L) + (((long) z[4]) & 4294967295L);
        z[4] = (int) c5;
        long c6 = (c5 >>> 32) + (((long) x[5]) & 4294967295L) + (((long) y[5]) & 4294967295L) + (((long) z[5]) & 4294967295L);
        z[5] = (int) c6;
        return (int) (c6 >>> 32);
    }

    public static int c(int[] x, int xOff, int[] z, int zOff, int cIn) {
        long c = (((long) cIn) & 4294967295L) + (((long) x[xOff + 0]) & 4294967295L) + (((long) z[zOff + 0]) & 4294967295L);
        z[zOff + 0] = (int) c;
        long c2 = (c >>> 32) + (((long) x[xOff + 1]) & 4294967295L) + (((long) z[zOff + 1]) & 4294967295L);
        z[zOff + 1] = (int) c2;
        long c3 = (c2 >>> 32) + (((long) x[xOff + 2]) & 4294967295L) + (((long) z[zOff + 2]) & 4294967295L);
        z[zOff + 2] = (int) c3;
        long c4 = (c3 >>> 32) + (((long) x[xOff + 3]) & 4294967295L) + (((long) z[zOff + 3]) & 4294967295L);
        z[zOff + 3] = (int) c4;
        long c5 = (c4 >>> 32) + (((long) x[xOff + 4]) & 4294967295L) + (((long) z[zOff + 4]) & 4294967295L);
        z[zOff + 4] = (int) c5;
        long c6 = (c5 >>> 32) + (((long) x[xOff + 5]) & 4294967295L) + (4294967295L & ((long) z[zOff + 5]));
        z[zOff + 5] = (int) c6;
        return (int) (c6 >>> 32);
    }

    public static int d(int[] u, int uOff, int[] v, int vOff) {
        long c = 0 + (((long) u[uOff + 0]) & 4294967295L) + (((long) v[vOff + 0]) & 4294967295L);
        u[uOff + 0] = (int) c;
        v[vOff + 0] = (int) c;
        long c2 = (c >>> 32) + (((long) u[uOff + 1]) & 4294967295L) + (((long) v[vOff + 1]) & 4294967295L);
        u[uOff + 1] = (int) c2;
        v[vOff + 1] = (int) c2;
        long c3 = (c2 >>> 32) + (((long) u[uOff + 2]) & 4294967295L) + (((long) v[vOff + 2]) & 4294967295L);
        u[uOff + 2] = (int) c3;
        v[vOff + 2] = (int) c3;
        long c4 = (c3 >>> 32) + (((long) u[uOff + 3]) & 4294967295L) + (((long) v[vOff + 3]) & 4294967295L);
        u[uOff + 3] = (int) c4;
        v[vOff + 3] = (int) c4;
        long c5 = (c4 >>> 32) + (((long) u[uOff + 4]) & 4294967295L) + (((long) v[vOff + 4]) & 4294967295L);
        u[uOff + 4] = (int) c5;
        v[vOff + 4] = (int) c5;
        long c6 = (c5 >>> 32) + (((long) u[uOff + 5]) & 4294967295L) + (((long) v[vOff + 5]) & 4294967295L);
        u[uOff + 5] = (int) c6;
        v[vOff + 5] = (int) c6;
        return (int) (c6 >>> 32);
    }

    public static int[] e() {
        return new int[6];
    }

    public static long[] f() {
        return new long[3];
    }

    public static int[] g() {
        return new int[12];
    }

    public static long[] h() {
        return new long[6];
    }

    public static boolean i(int[] x, int xOff, int[] y, int yOff, int[] z, int zOff) {
        boolean pos = o(x, xOff, y, yOff);
        if (pos) {
            C(x, xOff, y, yOff, z, zOff);
        } else {
            C(y, yOff, x, xOff, z, zOff);
        }
        return pos;
    }

    public static boolean j(int[] x, int[] y) {
        for (int i = 5; i >= 0; i--) {
            if (x[i] != y[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean k(long[] x, long[] y) {
        for (int i = 2; i >= 0; i--) {
            if (x[i] != y[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] l(BigInteger x) {
        if (x.signum() < 0 || x.bitLength() > 192) {
            throw new IllegalArgumentException();
        }
        int[] z = e();
        int i = 0;
        while (x.signum() != 0) {
            z[i] = x.intValue();
            x = x.shiftRight(32);
            i++;
        }
        return z;
    }

    public static long[] m(BigInteger x) {
        if (x.signum() < 0 || x.bitLength() > 192) {
            throw new IllegalArgumentException();
        }
        long[] z = f();
        int i = 0;
        while (x.signum() != 0) {
            z[i] = x.longValue();
            x = x.shiftRight(64);
            i++;
        }
        return z;
    }

    public static int n(int[] x, int bit) {
        if (bit == 0) {
            return x[0] & 1;
        }
        int w = bit >> 5;
        if (w < 0 || w >= 6) {
            return 0;
        }
        return (x[w] >>> (bit & 31)) & 1;
    }

    public static boolean p(int[] x, int[] y) {
        for (int i = 5; i >= 0; i--) {
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

    public static boolean o(int[] x, int xOff, int[] y, int yOff) {
        for (int i = 5; i >= 0; i--) {
            int x_i = x[xOff + i] ^ Integer.MIN_VALUE;
            int y_i = Integer.MIN_VALUE ^ y[yOff + i];
            if (x_i < y_i) {
                return false;
            }
            if (x_i > y_i) {
                return true;
            }
        }
        return true;
    }

    public static boolean q(int[] x) {
        if (x[0] != 1) {
            return false;
        }
        for (int i = 1; i < 6; i++) {
            if (x[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean r(long[] x) {
        if (x[0] != 1) {
            return false;
        }
        for (int i = 1; i < 3; i++) {
            if (x[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean s(int[] x) {
        for (int i = 0; i < 6; i++) {
            if (x[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean t(long[] x) {
        for (int i = 0; i < 3; i++) {
            if (x[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static void v(int[] x, int[] y, int[] zz) {
        long y_1 = ((long) y[1]) & 4294967295L;
        long y_3 = ((long) y[3]) & 4294967295L;
        long y_4 = ((long) y[4]) & 4294967295L;
        long y_2 = ((long) y[2]) & 4294967295L;
        long y_5 = ((long) y[5]) & 4294967295L;
        long y_0 = ((long) y[0]) & 4294967295L;
        long x_0 = ((long) x[0]) & 4294967295L;
        long c = 0 + (x_0 * y_0);
        zz[0] = (int) c;
        long c2 = (c >>> 32) + (x_0 * y_1);
        zz[1] = (int) c2;
        long c3 = (c2 >>> 32) + (x_0 * y_2);
        zz[2] = (int) c3;
        long c4 = (c3 >>> 32) + (x_0 * y_3);
        zz[3] = (int) c4;
        long c5 = (c4 >>> 32) + (x_0 * y_4);
        zz[4] = (int) c5;
        long c6 = (c5 >>> 32) + (x_0 * y_5);
        zz[5] = (int) c6;
        zz[6] = (int) (c6 >>> 32);
        int i = 1;
        for (int i2 = 6; i < i2; i2 = 6) {
            long x_i = ((long) x[i]) & 4294967295L;
            long y_52 = y_5;
            long c7 = 0 + (x_i * y_0) + (((long) zz[i + 0]) & 4294967295L);
            zz[i + 0] = (int) c7;
            long y_12 = y_1;
            long c8 = (c7 >>> 32) + (x_i * y_1) + (((long) zz[i + 1]) & 4294967295L);
            zz[i + 1] = (int) c8;
            long y_42 = y_4;
            long c9 = (c8 >>> 32) + (x_i * y_2) + (((long) zz[i + 2]) & 4294967295L);
            zz[i + 2] = (int) c9;
            long c10 = (c9 >>> 32) + (x_i * y_3) + (((long) zz[i + 3]) & 4294967295L);
            zz[i + 3] = (int) c10;
            long c11 = (c10 >>> 32) + (x_i * y_42) + (((long) zz[i + 4]) & 4294967295L);
            zz[i + 4] = (int) c11;
            long c12 = (c11 >>> 32) + (x_i * y_52) + (((long) zz[i + 5]) & 4294967295L);
            zz[i + 5] = (int) c12;
            zz[i + 6] = (int) (c12 >>> 32);
            i++;
            y_1 = y_12;
            y_5 = y_52;
            y_4 = y_42;
        }
    }

    public static void u(int[] x, int xOff, int[] y, int yOff, int[] zz, int zzOff) {
        long y_0 = ((long) y[yOff + 0]) & 4294967295L;
        long y_1 = ((long) y[yOff + 1]) & 4294967295L;
        long y_2 = ((long) y[yOff + 2]) & 4294967295L;
        long y_3 = ((long) y[yOff + 3]) & 4294967295L;
        long y_4 = ((long) y[yOff + 4]) & 4294967295L;
        long y_5 = ((long) y[yOff + 5]) & 4294967295L;
        long x_0 = ((long) x[xOff + 0]) & 4294967295L;
        long c = 0 + (x_0 * y_0);
        zz[zzOff + 0] = (int) c;
        long c2 = (c >>> 32) + (x_0 * y_1);
        zz[zzOff + 1] = (int) c2;
        long c3 = (c2 >>> 32) + (x_0 * y_2);
        zz[zzOff + 2] = (int) c3;
        long c4 = (c3 >>> 32) + (x_0 * y_3);
        zz[zzOff + 3] = (int) c4;
        long c5 = (c4 >>> 32) + (x_0 * y_4);
        zz[zzOff + 4] = (int) c5;
        long c6 = (c5 >>> 32) + (x_0 * y_5);
        zz[zzOff + 5] = (int) c6;
        zz[zzOff + 6] = (int) (c6 >>> 32);
        int i = 1;
        int zzOff2 = zzOff;
        while (i < 6) {
            zzOff2++;
            long x_i = ((long) x[xOff + i]) & 4294967295L;
            long c7 = 0 + (x_i * y_0) + (((long) zz[zzOff2 + 0]) & 4294967295L);
            zz[zzOff2 + 0] = (int) c7;
            long y_12 = y_1;
            long c8 = (c7 >>> 32) + (x_i * y_1) + (((long) zz[zzOff2 + 1]) & 4294967295L);
            zz[zzOff2 + 1] = (int) c8;
            long c9 = (c8 >>> 32) + (x_i * y_2) + (((long) zz[zzOff2 + 2]) & 4294967295L);
            zz[zzOff2 + 2] = (int) c9;
            long c10 = (c9 >>> 32) + (x_i * y_3) + (((long) zz[zzOff2 + 3]) & 4294967295L);
            zz[zzOff2 + 3] = (int) c10;
            long c11 = (c10 >>> 32) + (x_i * y_4) + (((long) zz[zzOff2 + 4]) & 4294967295L);
            zz[zzOff2 + 4] = (int) c11;
            long c12 = (c11 >>> 32) + (x_i * y_5) + (((long) zz[zzOff2 + 5]) & 4294967295L);
            zz[zzOff2 + 5] = (int) c12;
            zz[zzOff2 + 6] = (int) (c12 >>> 32);
            i++;
            y_1 = y_12;
            y_0 = y_0;
        }
    }

    public static int z(int[] x, int[] y, int[] zz) {
        long y_0 = ((long) y[0]) & 4294967295L;
        long y_1 = ((long) y[1]) & 4294967295L;
        long y_2 = ((long) y[2]) & 4294967295L;
        long y_3 = ((long) y[3]) & 4294967295L;
        long y_4 = ((long) y[4]) & 4294967295L;
        long y_5 = ((long) y[5]) & 4294967295L;
        long x_i = 0;
        int i = 0;
        while (i < 6) {
            long zc = x_i;
            long x_i2 = ((long) x[i]) & 4294967295L;
            long y_02 = y_0;
            long c = 0 + (x_i2 * y_0) + (((long) zz[i + 0]) & 4294967295L);
            long y_52 = y_5;
            zz[i + 0] = (int) c;
            long c2 = (c >>> 32) + (x_i2 * y_1) + (((long) zz[i + 1]) & 4294967295L);
            zz[i + 1] = (int) c2;
            long c3 = (c2 >>> 32) + (x_i2 * y_2) + (((long) zz[i + 2]) & 4294967295L);
            zz[i + 2] = (int) c3;
            long c4 = (c3 >>> 32) + (x_i2 * y_3) + (((long) zz[i + 3]) & 4294967295L);
            zz[i + 3] = (int) c4;
            long c5 = (c4 >>> 32) + (x_i2 * y_4) + (((long) zz[i + 4]) & 4294967295L);
            zz[i + 4] = (int) c5;
            long c6 = (c5 >>> 32) + (x_i2 * y_52) + (((long) zz[i + 5]) & 4294967295L);
            zz[i + 5] = (int) c6;
            long c7 = (c6 >>> 32) + zc + (((long) zz[i + 6]) & 4294967295L);
            zz[i + 6] = (int) c7;
            x_i = c7 >>> 32;
            i++;
            y_5 = y_52;
            y_0 = y_02;
            y_1 = y_1;
        }
        return (int) x_i;
    }

    public static long w(int w, int[] x, int xOff, int[] y, int yOff, int[] z, int zOff) {
        long wVal = ((long) w) & 4294967295L;
        long x0 = ((long) x[xOff + 0]) & 4294967295L;
        long c = 0 + (wVal * x0) + (((long) y[yOff + 0]) & 4294967295L);
        z[zOff + 0] = (int) c;
        long x1 = ((long) x[xOff + 1]) & 4294967295L;
        long c2 = (c >>> 32) + (wVal * x1) + x0 + (((long) y[yOff + 1]) & 4294967295L);
        z[zOff + 1] = (int) c2;
        long x2 = ((long) x[xOff + 2]) & 4294967295L;
        long j = x0;
        long c3 = (c2 >>> 32) + (wVal * x2) + x1 + (((long) y[yOff + 2]) & 4294967295L);
        z[zOff + 2] = (int) c3;
        long x3 = ((long) x[xOff + 3]) & 4294967295L;
        long j2 = x1;
        long c4 = (c3 >>> 32) + (wVal * x3) + x2 + (((long) y[yOff + 3]) & 4294967295L);
        z[zOff + 3] = (int) c4;
        long x4 = ((long) x[xOff + 4]) & 4294967295L;
        long j3 = x3;
        long c5 = (c4 >>> 32) + (wVal * x4) + x3 + (((long) y[yOff + 4]) & 4294967295L);
        z[zOff + 4] = (int) c5;
        long x5 = ((long) x[xOff + 5]) & 4294967295L;
        long j4 = wVal;
        long c6 = (c5 >>> 32) + (wVal * x5) + x4 + (((long) y[yOff + 5]) & 4294967295L);
        z[zOff + 5] = (int) c6;
        return (c6 >>> 32) + x5;
    }

    public static int x(int x, long y, int[] z, int zOff) {
        int[] iArr = z;
        int i = zOff;
        long xVal = ((long) x) & 4294967295L;
        long y00 = y & 4294967295L;
        long c = 0 + (xVal * y00) + (((long) iArr[i + 0]) & 4294967295L);
        iArr[i + 0] = (int) c;
        long y01 = y >>> 32;
        long j = xVal;
        long c2 = (c >>> 32) + (xVal * y01) + y00 + (((long) iArr[i + 1]) & 4294967295L);
        iArr[i + 1] = (int) c2;
        long c3 = (c2 >>> 32) + (((long) iArr[i + 2]) & 4294967295L) + y01;
        iArr[i + 2] = (int) c3;
        long c4 = (c3 >>> 32) + (((long) iArr[i + 3]) & 4294967295L);
        iArr[i + 3] = (int) c4;
        if ((c4 >>> 32) == 0) {
            return 0;
        }
        return Nat.t(6, iArr, i, 4);
    }

    public static int y(int x, int y, int[] z, int zOff) {
        long yVal = ((long) y) & 4294967295L;
        long c = 0 + (yVal * (((long) x) & 4294967295L)) + (((long) z[zOff + 0]) & 4294967295L);
        z[zOff + 0] = (int) c;
        long c2 = (c >>> 32) + (((long) z[zOff + 1]) & 4294967295L) + yVal;
        z[zOff + 1] = (int) c2;
        long c3 = (c2 >>> 32) + (4294967295L & ((long) z[zOff + 2]));
        z[zOff + 2] = (int) c3;
        if ((c3 >>> 32) == 0) {
            return 0;
        }
        return Nat.t(6, z, zOff, 3);
    }

    public static void B(int[] x, int[] zz) {
        long x_0 = ((long) x[0]) & 4294967295L;
        int c = 0;
        int i = 5;
        int j = 12;
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
                long x_22 = x_2;
                long x_3 = ((long) x[3]) & 4294967295L;
                long j3 = zz_1;
                long zz_5 = (((long) zz[5]) & 4294967295L) + (zz_4 >>> 32);
                long j4 = zz_22;
                long zz_6 = (((long) zz[6]) & 4294967295L) + (zz_5 >>> 32);
                long zz_32 = (zz_3 & 4294967295L) + (x_3 * x_02);
                int w3 = (int) zz_32;
                zz[3] = (w3 << 1) | c2;
                int c3 = w3 >>> 31;
                long zz_42 = (zz_4 & 4294967295L) + (zz_32 >>> 32) + (x_3 * x_12);
                long zz_52 = (zz_5 & 4294967295L) + (zz_42 >>> 32) + (x_3 * x_22);
                long zz_62 = zz_6 + (zz_52 >>> 32);
                long j5 = zz_32;
                long x_4 = ((long) x[4]) & 4294967295L;
                long x_32 = x_3;
                long zz_7 = (((long) zz[7]) & 4294967295L) + (zz_62 >>> 32);
                int i5 = w3;
                long zz_8 = (((long) zz[8]) & 4294967295L) + (zz_7 >>> 32);
                long zz_43 = (zz_42 & 4294967295L) + (x_4 * x_02);
                int w4 = (int) zz_43;
                zz[4] = (w4 << 1) | c3;
                long zz_53 = (zz_52 & 4294967295L) + (zz_43 >>> 32) + (x_4 * x_12);
                long zz_63 = (zz_62 & 4294967295L) + (zz_53 >>> 32) + (x_4 * x_22);
                long zz_72 = (zz_7 & 4294967295L) + (zz_63 >>> 32) + (x_4 * x_32);
                long zz_64 = zz_63 & 4294967295L;
                long zz_82 = zz_8 + (zz_72 >>> 32);
                long x_5 = ((long) x[5]) & 4294967295L;
                long j6 = zz_43;
                long zz_9 = (((long) zz[9]) & 4294967295L) + (zz_82 >>> 32);
                int i6 = w4;
                long x_42 = x_4;
                long zz_10 = (((long) zz[10]) & 4294967295L) + (zz_9 >>> 32);
                long zz_54 = (zz_53 & 4294967295L) + (x_5 * x_02);
                int w5 = (int) zz_54;
                zz[5] = (w5 << 1) | (w4 >>> 31);
                int c4 = w5 >>> 31;
                long j7 = zz_54;
                long zz_65 = zz_64 + (zz_54 >>> 32) + (x_5 * x_12);
                long zz_73 = (zz_72 & 4294967295L) + (zz_65 >>> 32) + (x_5 * x_22);
                long zz_83 = (zz_82 & 4294967295L) + (zz_73 >>> 32) + (x_5 * x_32);
                long zz_92 = (zz_9 & 4294967295L) + (zz_83 >>> 32) + (x_5 * x_42);
                long zz_102 = zz_10 + (zz_92 >>> 32);
                int w6 = (int) zz_65;
                zz[6] = (w6 << 1) | c4;
                int c5 = w6 >>> 31;
                int w7 = (int) zz_73;
                zz[7] = (w7 << 1) | c5;
                int c6 = w7 >>> 31;
                int w8 = (int) zz_83;
                zz[8] = (w8 << 1) | c6;
                int c7 = w8 >>> 31;
                int w9 = (int) zz_92;
                zz[9] = (w9 << 1) | c7;
                int c8 = w9 >>> 31;
                int w10 = (int) zz_102;
                zz[10] = (w10 << 1) | c8;
                long j8 = zz_92;
                zz[11] = ((zz[11] + ((int) (zz_102 >>> 32))) << 1) | (w10 >>> 31);
                return;
            }
            i = i2;
        }
    }

    public static void A(int[] x, int xOff, int[] zz, int zzOff) {
        long x_0 = ((long) x[xOff + 0]) & 4294967295L;
        int c = 0;
        int i = 5;
        int j = 12;
        while (true) {
            int i2 = i - 1;
            long xVal = ((long) x[xOff + i]) & 4294967295L;
            long p = xVal * xVal;
            int j2 = j - 1;
            zz[zzOff + j2] = (c << 31) | ((int) (p >>> 33));
            j = j2 - 1;
            zz[zzOff + j] = (int) (p >>> 1);
            c = (int) p;
            if (i2 <= 0) {
                long p2 = x_0 * x_0;
                zz[zzOff + 0] = (int) p2;
                long x_1 = ((long) x[xOff + 1]) & 4294967295L;
                long zz_1 = ((((long) (c << 31)) & 4294967295L) | (p2 >>> 33)) + (x_1 * x_0);
                int w = (int) zz_1;
                zz[zzOff + 1] = (w << 1) | (((int) (p2 >>> 32)) & 1);
                long zz_2 = (((long) zz[zzOff + 2]) & 4294967295L) + (zz_1 >>> 32);
                long x_2 = ((long) x[xOff + 2]) & 4294967295L;
                long x_12 = x_1;
                long j3 = zz_1;
                long zz_22 = zz_2 + (x_2 * x_0);
                int w2 = (int) zz_22;
                zz[zzOff + 2] = (w2 << 1) | (w >>> 31);
                int c2 = w2 >>> 31;
                long zz_3 = (((long) zz[zzOff + 3]) & 4294967295L) + (zz_22 >>> 32) + (x_2 * x_12);
                long zz_4 = (((long) zz[zzOff + 4]) & 4294967295L) + (zz_3 >>> 32);
                long j4 = zz_22;
                int w3 = w2;
                long x_3 = ((long) x[xOff + 3]) & 4294967295L;
                long x_22 = x_2;
                long zz_5 = (((long) zz[zzOff + 5]) & 4294967295L) + (zz_4 >>> 32);
                int i3 = w3;
                long zz_6 = (((long) zz[zzOff + 6]) & 4294967295L) + (zz_5 >>> 32);
                long zz_32 = (zz_3 & 4294967295L) + (x_3 * x_0);
                int w4 = (int) zz_32;
                zz[zzOff + 3] = (w4 << 1) | c2;
                int c3 = w4 >>> 31;
                long zz_42 = (zz_4 & 4294967295L) + (zz_32 >>> 32) + (x_3 * x_12);
                long zz_52 = (zz_5 & 4294967295L) + (zz_42 >>> 32) + (x_3 * x_22);
                long zz_62 = zz_6 + (zz_52 >>> 32);
                long x_4 = ((long) x[xOff + 4]) & 4294967295L;
                long j5 = zz_32;
                long zz_7 = (((long) zz[zzOff + 7]) & 4294967295L) + (zz_62 >>> 32);
                int i4 = w4;
                long x_32 = x_3;
                long zz_8 = (((long) zz[zzOff + 8]) & 4294967295L) + (zz_7 >>> 32);
                long zz_43 = (zz_42 & 4294967295L) + (x_4 * x_0);
                int w5 = (int) zz_43;
                zz[zzOff + 4] = (w5 << 1) | c3;
                int c4 = w5 >>> 31;
                long zz_53 = (zz_52 & 4294967295L) + (zz_43 >>> 32) + (x_4 * x_12);
                long zz_63 = (zz_62 & 4294967295L) + (zz_53 >>> 32) + (x_4 * x_22);
                long zz_72 = (zz_7 & 4294967295L) + (zz_63 >>> 32) + (x_4 * x_32);
                long zz_82 = zz_8 + (zz_72 >>> 32);
                long j6 = zz_43;
                long x_5 = ((long) x[xOff + 5]) & 4294967295L;
                int i5 = w5;
                long zz_9 = (((long) zz[zzOff + 9]) & 4294967295L) + (zz_82 >>> 32);
                long zz_83 = zz_82 & 4294967295L;
                long zz_10 = (((long) zz[zzOff + 10]) & 4294967295L) + (zz_9 >>> 32);
                long zz_54 = (zz_53 & 4294967295L) + (x_5 * x_0);
                int w6 = (int) zz_54;
                zz[zzOff + 5] = (w6 << 1) | c4;
                int c5 = w6 >>> 31;
                long zz_64 = (zz_63 & 4294967295L) + (zz_54 >>> 32) + (x_5 * x_12);
                long zz_73 = (zz_72 & 4294967295L) + (zz_64 >>> 32) + (x_5 * x_22);
                long j7 = x_0;
                long zz_84 = zz_83 + (zz_73 >>> 32) + (x_5 * x_32);
                long zz_92 = (zz_9 & 4294967295L) + (zz_84 >>> 32) + (x_5 * x_4);
                long zz_102 = zz_10 + (zz_92 >>> 32);
                int w7 = (int) zz_64;
                zz[zzOff + 6] = (w7 << 1) | c5;
                int c6 = w7 >>> 31;
                int w8 = (int) zz_73;
                zz[zzOff + 7] = (w8 << 1) | c6;
                int c7 = w8 >>> 31;
                int w9 = (int) zz_84;
                zz[zzOff + 8] = (w9 << 1) | c7;
                int c8 = w9 >>> 31;
                int w10 = (int) zz_92;
                zz[zzOff + 9] = (w10 << 1) | c8;
                int c9 = w10 >>> 31;
                int w11 = (int) zz_102;
                zz[zzOff + 10] = (w11 << 1) | c9;
                long j8 = zz_84;
                zz[zzOff + 11] = ((zz[zzOff + 11] + ((int) (zz_102 >>> 32))) << 1) | (w11 >>> 31);
                return;
            }
            i = i2;
        }
    }

    public static int D(int[] x, int[] y, int[] z) {
        long c = 0 + ((((long) x[0]) & 4294967295L) - (((long) y[0]) & 4294967295L));
        z[0] = (int) c;
        long c2 = (c >> 32) + ((((long) x[1]) & 4294967295L) - (((long) y[1]) & 4294967295L));
        z[1] = (int) c2;
        long c3 = (c2 >> 32) + ((((long) x[2]) & 4294967295L) - (((long) y[2]) & 4294967295L));
        z[2] = (int) c3;
        long c4 = (c3 >> 32) + ((((long) x[3]) & 4294967295L) - (((long) y[3]) & 4294967295L));
        z[3] = (int) c4;
        long c5 = (c4 >> 32) + ((((long) x[4]) & 4294967295L) - (((long) y[4]) & 4294967295L));
        z[4] = (int) c5;
        long c6 = (c5 >> 32) + ((((long) x[5]) & 4294967295L) - (((long) y[5]) & 4294967295L));
        z[5] = (int) c6;
        return (int) (c6 >> 32);
    }

    public static int C(int[] x, int xOff, int[] y, int yOff, int[] z, int zOff) {
        long c = 0 + ((((long) x[xOff + 0]) & 4294967295L) - (((long) y[yOff + 0]) & 4294967295L));
        z[zOff + 0] = (int) c;
        long c2 = (c >> 32) + ((((long) x[xOff + 1]) & 4294967295L) - (((long) y[yOff + 1]) & 4294967295L));
        z[zOff + 1] = (int) c2;
        long c3 = (c2 >> 32) + ((((long) x[xOff + 2]) & 4294967295L) - (((long) y[yOff + 2]) & 4294967295L));
        z[zOff + 2] = (int) c3;
        long c4 = (c3 >> 32) + ((((long) x[xOff + 3]) & 4294967295L) - (((long) y[yOff + 3]) & 4294967295L));
        z[zOff + 3] = (int) c4;
        long c5 = (c4 >> 32) + ((((long) x[xOff + 4]) & 4294967295L) - (((long) y[yOff + 4]) & 4294967295L));
        z[zOff + 4] = (int) c5;
        long c6 = (c5 >> 32) + ((((long) x[xOff + 5]) & 4294967295L) - (((long) y[yOff + 5]) & 4294967295L));
        z[zOff + 5] = (int) c6;
        return (int) (c6 >> 32);
    }

    public static int E(int[] x, int[] z) {
        long c = 0 + ((((long) z[0]) & 4294967295L) - (((long) x[0]) & 4294967295L));
        z[0] = (int) c;
        long c2 = (c >> 32) + ((((long) z[1]) & 4294967295L) - (((long) x[1]) & 4294967295L));
        z[1] = (int) c2;
        long c3 = (c2 >> 32) + ((((long) z[2]) & 4294967295L) - (((long) x[2]) & 4294967295L));
        z[2] = (int) c3;
        long c4 = (c3 >> 32) + ((((long) z[3]) & 4294967295L) - (((long) x[3]) & 4294967295L));
        z[3] = (int) c4;
        long c5 = (c4 >> 32) + ((((long) z[4]) & 4294967295L) - (((long) x[4]) & 4294967295L));
        z[4] = (int) c5;
        long c6 = (c5 >> 32) + ((((long) z[5]) & 4294967295L) - (((long) x[5]) & 4294967295L));
        z[5] = (int) c6;
        return (int) (c6 >> 32);
    }

    public static BigInteger F(int[] x) {
        byte[] bs = new byte[24];
        for (int i = 0; i < 6; i++) {
            int x_i = x[i];
            if (x_i != 0) {
                Pack.d(x_i, bs, (5 - i) << 2);
            }
        }
        return new BigInteger(1, bs);
    }

    public static BigInteger G(long[] x) {
        byte[] bs = new byte[24];
        for (int i = 0; i < 3; i++) {
            long x_i = x[i];
            if (x_i != 0) {
                Pack.p(x_i, bs, (2 - i) << 3);
            }
        }
        return new BigInteger(1, bs);
    }

    public static void H(int[] z) {
        z[0] = 0;
        z[1] = 0;
        z[2] = 0;
        z[3] = 0;
        z[4] = 0;
        z[5] = 0;
    }
}
