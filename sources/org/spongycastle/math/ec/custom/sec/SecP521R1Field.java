package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat512;

public class SecP521R1Field {
    static final int[] a = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 511};

    public static void a(int[] x, int[] y, int[] z) {
        int c = Nat.a(16, x, y, z) + x[16] + y[16];
        if (c > 511 || (c == 511 && Nat.m(16, z, a))) {
            c = (c + Nat.q(16, z)) & 511;
        }
        z[16] = c;
    }

    public static void b(int[] x, int[] z) {
        int c = Nat.r(16, x, z) + x[16];
        if (c > 511 || (c == 511 && Nat.m(16, z, a))) {
            c = (c + Nat.q(16, z)) & 511;
        }
        z[16] = c;
    }

    public static int[] c(BigInteger x) {
        int[] z = Nat.n(521, x);
        if (Nat.m(17, z, a)) {
            Nat.P(17, z);
        }
        return z;
    }

    public static void f(int[] x, int[] y, int[] z) {
        int[] tt = Nat.i(33);
        d(x, y, tt);
        h(tt, z);
    }

    public static void g(int[] x, int[] z) {
        if (Nat.v(17, x)) {
            Nat.P(17, z);
        } else {
            Nat.J(17, a, x, z);
        }
    }

    public static void h(int[] xx, int[] z) {
        int xx32 = xx[32];
        int c = (Nat.A(16, xx, 16, 9, xx32, z, 0) >>> 23) + (xx32 >>> 9) + Nat.e(16, xx, z);
        if (c > 511 || (c == 511 && Nat.m(16, z, a))) {
            c = (c + Nat.q(16, z)) & 511;
        }
        z[16] = c;
    }

    public static void i(int[] z) {
        int z16 = z[16];
        int c = Nat.g(16, z16 >>> 9, z) + (z16 & 511);
        if (c > 511 || (c == 511 && Nat.m(16, z, a))) {
            c = (c + Nat.q(16, z)) & 511;
        }
        z[16] = c;
    }

    public static void j(int[] x, int[] z) {
        int[] tt = Nat.i(33);
        e(x, tt);
        h(tt, z);
    }

    public static void k(int[] x, int n, int[] z) {
        int[] tt = Nat.i(33);
        e(x, tt);
        h(tt, z);
        while (true) {
            n--;
            if (n > 0) {
                e(z, tt);
                h(tt, z);
            } else {
                return;
            }
        }
    }

    public static void l(int[] x, int[] y, int[] z) {
        int c = (Nat.J(16, x, y, z) + x[16]) - y[16];
        if (c < 0) {
            c = (c + Nat.k(16, z)) & 511;
        }
        z[16] = c;
    }

    public static void m(int[] x, int[] z) {
        int x16 = x[16];
        z[16] = (Nat.D(16, x, x16 << 23, z) | (x16 << 1)) & 511;
    }

    protected static void d(int[] x, int[] y, int[] zz) {
        Nat512.a(x, y, zz);
        int x16 = x[16];
        int y16 = y[16];
        zz[32] = Nat.w(16, x16, y, y16, x, zz, 16) + (x16 * y16);
    }

    protected static void e(int[] x, int[] zz) {
        Nat512.b(x, zz);
        int x16 = x[16];
        zz[32] = Nat.x(16, x16 << 1, x, 0, zz, 16) + (x16 * x16);
    }
}
