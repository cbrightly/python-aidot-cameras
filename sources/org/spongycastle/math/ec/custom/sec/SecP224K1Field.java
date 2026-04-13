package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat224;

public class SecP224K1Field {
    static final int[] a = {-6803, -2, -1, -1, -1, -1, -1};
    static final int[] b = {46280809, 13606, 1, 0, 0, 0, 0, -13606, -3, -1, -1, -1, -1, -1};
    private static final int[] c = {-46280809, -13607, -2, -1, -1, -1, -1, 13605, 2};

    public static void a(int[] x, int[] y, int[] z) {
        if (Nat224.a(x, y, z) != 0 || (z[6] == -1 && Nat224.i(z, a))) {
            Nat.b(7, 6803, z);
        }
    }

    public static void b(int[] x, int[] z) {
        if (Nat.r(7, x, z) != 0 || (z[6] == -1 && Nat224.i(z, a))) {
            Nat.b(7, 6803, z);
        }
    }

    public static int[] c(BigInteger x) {
        int[] z = Nat224.g(x);
        if (z[6] == -1 && Nat224.i(z, a)) {
            Nat.b(7, 6803, z);
        }
        return z;
    }

    public static void d(int[] x, int[] y, int[] z) {
        int[] tt = Nat224.e();
        Nat224.l(x, y, tt);
        g(tt, z);
    }

    public static void e(int[] x, int[] y, int[] zz) {
        if (Nat224.p(x, y, zz) != 0 || (zz[13] == -1 && Nat.p(14, zz, b))) {
            int[] iArr = c;
            if (Nat.e(iArr.length, iArr, zz) != 0) {
                Nat.s(14, zz, iArr.length);
            }
        }
    }

    public static void f(int[] x, int[] z) {
        if (Nat224.k(x)) {
            Nat224.u(z);
        } else {
            Nat224.r(a, x, z);
        }
    }

    public static void g(int[] xx, int[] z) {
        if (Nat224.n(6803, Nat224.m(6803, xx, 7, xx, 0, z, 0), z, 0) != 0 || (z[6] == -1 && Nat224.i(z, a))) {
            Nat.b(7, 6803, z);
        }
    }

    public static void h(int x, int[] z) {
        if ((x != 0 && Nat224.o(6803, x, z, 0) != 0) || (z[6] == -1 && Nat224.i(z, a))) {
            Nat.b(7, 6803, z);
        }
    }

    public static void i(int[] x, int[] z) {
        int[] tt = Nat224.e();
        Nat224.q(x, tt);
        g(tt, z);
    }

    public static void j(int[] x, int n, int[] z) {
        int[] tt = Nat224.e();
        Nat224.q(x, tt);
        g(tt, z);
        while (true) {
            n--;
            if (n > 0) {
                Nat224.q(z, tt);
                g(tt, z);
            } else {
                return;
            }
        }
    }

    public static void k(int[] x, int[] y, int[] z) {
        if (Nat224.r(x, y, z) != 0) {
            Nat.K(7, 6803, z);
        }
    }

    public static void l(int[] x, int[] z) {
        if (Nat.D(7, x, 0, z) != 0 || (z[6] == -1 && Nat224.i(z, a))) {
            Nat.b(7, 6803, z);
        }
    }
}
