package org.spongycastle.math.raw;

import java.math.BigInteger;
import org.spongycastle.util.Pack;

public abstract class Nat320 {
    public static long[] a() {
        return new long[5];
    }

    public static long[] b() {
        return new long[10];
    }

    public static boolean c(long[] x, long[] y) {
        for (int i = 4; i >= 0; i--) {
            if (x[i] != y[i]) {
                return false;
            }
        }
        return true;
    }

    public static long[] d(BigInteger x) {
        if (x.signum() < 0 || x.bitLength() > 320) {
            throw new IllegalArgumentException();
        }
        long[] z = a();
        int i = 0;
        while (x.signum() != 0) {
            z[i] = x.longValue();
            x = x.shiftRight(64);
            i++;
        }
        return z;
    }

    public static boolean e(long[] x) {
        if (x[0] != 1) {
            return false;
        }
        for (int i = 1; i < 5; i++) {
            if (x[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean f(long[] x) {
        for (int i = 0; i < 5; i++) {
            if (x[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static BigInteger g(long[] x) {
        byte[] bs = new byte[40];
        for (int i = 0; i < 5; i++) {
            long x_i = x[i];
            if (x_i != 0) {
                Pack.p(x_i, bs, (4 - i) << 3);
            }
        }
        return new BigInteger(1, bs);
    }
}
