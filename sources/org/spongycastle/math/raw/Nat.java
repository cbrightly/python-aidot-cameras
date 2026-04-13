package org.spongycastle.math.raw;

import java.math.BigInteger;
import org.spongycastle.util.Pack;

public abstract class Nat {
    public static int a(int len, int[] x, int[] y, int[] z) {
        long c = 0;
        for (int i = 0; i < len; i++) {
            long c2 = c + (((long) x[i]) & 4294967295L) + (4294967295L & ((long) y[i]));
            z[i] = (int) c2;
            c = c2 >>> 32;
        }
        return (int) c;
    }

    public static int b(int len, int x, int[] z) {
        long c = (((long) z[0]) & 4294967295L) + (((long) x) & 4294967295L);
        z[0] = (int) c;
        long c2 = (c >>> 32) + (4294967295L & ((long) z[1])) + 1;
        z[1] = (int) c2;
        if ((c2 >>> 32) == 0) {
            return 0;
        }
        return s(len, z, 2);
    }

    public static int c(int len, int[] x, int[] y, int[] z) {
        long c = 0;
        for (int i = 0; i < len; i++) {
            long c2 = c + (((long) x[i]) & 4294967295L) + (((long) y[i]) & 4294967295L) + (4294967295L & ((long) z[i]));
            z[i] = (int) c2;
            c = c2 >>> 32;
        }
        return (int) c;
    }

    public static int e(int len, int[] x, int[] z) {
        long c = 0;
        for (int i = 0; i < len; i++) {
            long c2 = c + (((long) x[i]) & 4294967295L) + (4294967295L & ((long) z[i]));
            z[i] = (int) c2;
            c = c2 >>> 32;
        }
        return (int) c;
    }

    public static int d(int len, int[] x, int xOff, int[] z, int zOff) {
        long c = 0;
        for (int i = 0; i < len; i++) {
            long c2 = c + (((long) x[xOff + i]) & 4294967295L) + (4294967295L & ((long) z[zOff + i]));
            z[zOff + i] = (int) c2;
            c = c2 >>> 32;
        }
        return (int) c;
    }

    public static int f(int len, int x, int[] z, int zPos) {
        long c = (((long) x) & 4294967295L) + (4294967295L & ((long) z[zPos]));
        z[zPos] = (int) c;
        if ((c >>> 32) == 0) {
            return 0;
        }
        return s(len, z, zPos + 1);
    }

    public static int g(int len, int x, int[] z) {
        long c = (((long) x) & 4294967295L) + (4294967295L & ((long) z[0]));
        z[0] = (int) c;
        if ((c >>> 32) == 0) {
            return 0;
        }
        return s(len, z, 1);
    }

    public static int[] h(int len, int[] x) {
        int[] z = new int[len];
        System.arraycopy(x, 0, z, 0, len);
        return z;
    }

    public static int[] i(int len) {
        return new int[len];
    }

    public static long[] j(int len) {
        return new long[len];
    }

    public static int k(int len, int[] z) {
        for (int i = 0; i < len; i++) {
            int i2 = z[i] - 1;
            z[i] = i2;
            if (i2 != -1) {
                return 0;
            }
        }
        return -1;
    }

    public static int l(int len, int[] z, int zPos) {
        for (int i = zPos; i < len; i++) {
            int i2 = z[i] - 1;
            z[i] = i2;
            if (i2 != -1) {
                return 0;
            }
        }
        return -1;
    }

    public static boolean m(int len, int[] x, int[] y) {
        for (int i = len - 1; i >= 0; i--) {
            if (x[i] != y[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] n(int bits, BigInteger x) {
        if (x.signum() < 0 || x.bitLength() > bits) {
            throw new IllegalArgumentException();
        }
        int[] z = i((bits + 31) >> 5);
        int i = 0;
        while (x.signum() != 0) {
            z[i] = x.intValue();
            x = x.shiftRight(32);
            i++;
        }
        return z;
    }

    public static int o(int[] x, int bit) {
        if (bit == 0) {
            return x[0] & 1;
        }
        int w = bit >> 5;
        if (w < 0 || w >= x.length) {
            return 0;
        }
        return (x[w] >>> (bit & 31)) & 1;
    }

    public static boolean p(int len, int[] x, int[] y) {
        for (int i = len - 1; i >= 0; i--) {
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

    public static int q(int len, int[] z) {
        for (int i = 0; i < len; i++) {
            int i2 = z[i] + 1;
            z[i] = i2;
            if (i2 != 0) {
                return 0;
            }
        }
        return 1;
    }

    public static int r(int len, int[] x, int[] z) {
        int i = 0;
        while (i < len) {
            int c = x[i] + 1;
            z[i] = c;
            i++;
            if (c != 0) {
                while (i < len) {
                    z[i] = x[i];
                    i++;
                }
                return 0;
            }
        }
        return 1;
    }

    public static int s(int len, int[] z, int zPos) {
        for (int i = zPos; i < len; i++) {
            int i2 = z[i] + 1;
            z[i] = i2;
            if (i2 != 0) {
                return 0;
            }
        }
        return 1;
    }

    public static int t(int len, int[] z, int zOff, int zPos) {
        for (int i = zPos; i < len; i++) {
            int i2 = zOff + i;
            int i3 = z[i2] + 1;
            z[i2] = i3;
            if (i3 != 0) {
                return 0;
            }
        }
        return 1;
    }

    public static boolean u(int len, int[] x) {
        if (x[0] != 1) {
            return false;
        }
        for (int i = 1; i < len; i++) {
            if (x[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean v(int len, int[] x) {
        for (int i = 0; i < len; i++) {
            if (x[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static int w(int len, int a, int[] x, int b, int[] y, int[] z, int zOff) {
        long c = 0;
        long aVal = ((long) a) & 4294967295L;
        long bVal = ((long) b) & 4294967295L;
        int i = 0;
        do {
            long c2 = c + ((((long) x[i]) & 4294967295L) * aVal) + ((((long) y[i]) & 4294967295L) * bVal) + (((long) z[zOff + i]) & 4294967295L);
            z[zOff + i] = (int) c2;
            c = c2 >>> 32;
            i++;
        } while (i < len);
        return (int) c;
    }

    public static int x(int len, int x, int[] y, int yOff, int[] z, int zOff) {
        long c = 0;
        long xVal = ((long) x) & 4294967295L;
        int i = 0;
        do {
            long c2 = c + ((((long) y[yOff + i]) & 4294967295L) * xVal) + (((long) z[zOff + i]) & 4294967295L);
            z[zOff + i] = (int) c2;
            c = c2 >>> 32;
            i++;
        } while (i < len);
        return (int) c;
    }

    public static int y(int len, int[] z, int c) {
        int i = len;
        while (true) {
            i--;
            if (i < 0) {
                return c << 31;
            }
            int next = z[i];
            z[i] = (next >>> 1) | (c << 31);
            c = next;
        }
    }

    public static int z(int len, int[] z, int bits, int c) {
        int i = len;
        while (true) {
            i--;
            if (i < 0) {
                return c << (-bits);
            }
            int next = z[i];
            z[i] = (next >>> bits) | (c << (-bits));
            c = next;
        }
    }

    public static int A(int len, int[] x, int xOff, int bits, int c, int[] z, int zOff) {
        int i = len;
        while (true) {
            i--;
            if (i < 0) {
                return c << (-bits);
            }
            int next = x[xOff + i];
            z[zOff + i] = (next >>> bits) | (c << (-bits));
            c = next;
        }
    }

    public static int B(int len, int[] z, int c) {
        int i = len;
        while (true) {
            i--;
            if (i < 0) {
                return c;
            }
            int next = z[i];
            z[i] = c;
            c = next;
        }
    }

    public static int D(int len, int[] x, int c, int[] z) {
        for (int i = 0; i < len; i++) {
            int next = x[i];
            z[i] = (next << 1) | (c >>> 31);
            c = next;
        }
        return c >>> 31;
    }

    public static int C(int len, int[] x, int xOff, int c, int[] z, int zOff) {
        for (int i = 0; i < len; i++) {
            int next = x[xOff + i];
            z[zOff + i] = (next << 1) | (c >>> 31);
            c = next;
        }
        return c >>> 31;
    }

    public static long E(int len, long[] x, int xOff, long c, long[] z, int zOff) {
        for (int i = 0; i < len; i++) {
            long next = x[xOff + i];
            z[zOff + i] = (next << 1) | (c >>> 63);
            c = next;
        }
        return c >>> 63;
    }

    public static int F(int len, int[] z, int bits, int c) {
        for (int i = 0; i < len; i++) {
            int next = z[i];
            z[i] = (next << bits) | (c >>> (-bits));
            c = next;
        }
        return c >>> (-bits);
    }

    public static long H(int len, long[] z, int zOff, int bits, long c) {
        for (int i = 0; i < len; i++) {
            long next = z[zOff + i];
            z[zOff + i] = (next << bits) | (c >>> (-bits));
            c = next;
        }
        return c >>> (-bits);
    }

    public static int G(int len, int[] x, int bits, int c, int[] z) {
        for (int i = 0; i < len; i++) {
            int next = x[i];
            z[i] = (next << bits) | (c >>> (-bits));
            c = next;
        }
        return c >>> (-bits);
    }

    public static long I(int len, long[] x, int xOff, int bits, long c, long[] z, int zOff) {
        for (int i = 0; i < len; i++) {
            long next = x[xOff + i];
            z[zOff + i] = (next << bits) | (c >>> (-bits));
            c = next;
        }
        return c >>> (-bits);
    }

    public static int J(int len, int[] x, int[] y, int[] z) {
        long c = 0;
        for (int i = 0; i < len; i++) {
            long c2 = c + ((((long) x[i]) & 4294967295L) - (4294967295L & ((long) y[i])));
            z[i] = (int) c2;
            c = c2 >> 32;
        }
        return (int) c;
    }

    public static int K(int len, int x, int[] z) {
        long c = (((long) z[0]) & 4294967295L) - (((long) x) & 4294967295L);
        z[0] = (int) c;
        long c2 = (c >> 32) + ((4294967295L & ((long) z[1])) - 1);
        z[1] = (int) c2;
        if ((c2 >> 32) == 0) {
            return 0;
        }
        return l(len, z, 2);
    }

    public static int M(int len, int[] x, int[] z) {
        long c = 0;
        for (int i = 0; i < len; i++) {
            long c2 = c + ((((long) z[i]) & 4294967295L) - (4294967295L & ((long) x[i])));
            z[i] = (int) c2;
            c = c2 >> 32;
        }
        return (int) c;
    }

    public static int L(int len, int[] x, int xOff, int[] z, int zOff) {
        long c = 0;
        for (int i = 0; i < len; i++) {
            long c2 = c + ((((long) z[zOff + i]) & 4294967295L) - (4294967295L & ((long) x[xOff + i])));
            z[zOff + i] = (int) c2;
            c = c2 >> 32;
        }
        return (int) c;
    }

    public static int N(int len, int x, int[] z) {
        long c = (((long) z[0]) & 4294967295L) - (4294967295L & ((long) x));
        z[0] = (int) c;
        if ((c >> 32) == 0) {
            return 0;
        }
        return l(len, z, 1);
    }

    public static BigInteger O(int len, int[] x) {
        byte[] bs = new byte[(len << 2)];
        for (int i = 0; i < len; i++) {
            int x_i = x[i];
            if (x_i != 0) {
                Pack.d(x_i, bs, ((len - 1) - i) << 2);
            }
        }
        return new BigInteger(1, bs);
    }

    public static void P(int len, int[] z) {
        for (int i = 0; i < len; i++) {
            z[i] = 0;
        }
    }

    public static void Q(int len, long[] z) {
        for (int i = 0; i < len; i++) {
            z[i] = 0;
        }
    }
}
