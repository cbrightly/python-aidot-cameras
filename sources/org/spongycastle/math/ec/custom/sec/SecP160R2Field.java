package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat160;

public class SecP160R2Field {
    static final int[] a = {-21389, -2, -1, -1, -1};
    static final int[] b = {457489321, 42778, 1, 0, 0, -42778, -3, -1, -1, -1};
    private static final int[] c = {-457489321, -42779, -2, -1, -1, 42777, 2};

    public static void a(int[] x, int[] y, int[] z) {
        if (Nat160.a(x, y, z) != 0 || (z[4] == -1 && Nat160.h(z, a))) {
            Nat.b(5, 21389, z);
        }
    }

    public static void b(int[] x, int[] z) {
        if (Nat.r(5, x, z) != 0 || (z[4] == -1 && Nat160.h(z, a))) {
            Nat.b(5, 21389, z);
        }
    }

    public static int[] c(BigInteger x) {
        int[] z = Nat160.f(x);
        if (z[4] == -1) {
            int[] iArr = a;
            if (Nat160.h(z, iArr)) {
                Nat160.s(iArr, z);
            }
        }
        return z;
    }

    public static void d(int[] x, int[] y, int[] z) {
        int[] tt = Nat160.d();
        Nat160.k(x, y, tt);
        g(tt, z);
    }

    public static void e(int[] x, int[] y, int[] zz) {
        if (Nat160.o(x, y, zz) != 0 || (zz[9] == -1 && Nat.p(10, zz, b))) {
            int[] iArr = c;
            if (Nat.e(iArr.length, iArr, zz) != 0) {
                Nat.s(10, zz, iArr.length);
            }
        }
    }

    public static void f(int[] x, int[] z) {
        if (Nat160.j(x)) {
            Nat160.u(z);
        } else {
            Nat160.r(a, x, z);
        }
    }

    public static void g(int[] xx, int[] z) {
        if (Nat160.m(21389, Nat160.l(21389, xx, 5, xx, 0, z, 0), z, 0) != 0 || (z[4] == -1 && Nat160.h(z, a))) {
            Nat.b(5, 21389, z);
        }
    }

    public static void h(int x, int[] z) {
        if ((x != 0 && Nat160.n(21389, x, z, 0) != 0) || (z[4] == -1 && Nat160.h(z, a))) {
            Nat.b(5, 21389, z);
        }
    }

    public static void i(int[] x, int[] z) {
        int[] tt = Nat160.d();
        Nat160.q(x, tt);
        g(tt, z);
    }

    public static void j(int[] x, int n, int[] z) {
        int[] tt = Nat160.d();
        Nat160.q(x, tt);
        g(tt, z);
        while (true) {
            n--;
            if (n > 0) {
                Nat160.q(z, tt);
                g(tt, z);
            } else {
                return;
            }
        }
    }

    public static void k(int[] x, int[] y, int[] z) {
        if (Nat160.r(x, y, z) != 0) {
            Nat.K(5, 21389, z);
        }
    }

    public static void l(int[] x, int[] z) {
        if (Nat.D(5, x, 0, z) != 0 || (z[4] == -1 && Nat160.h(z, a))) {
            Nat.b(5, 21389, z);
        }
    }
}
