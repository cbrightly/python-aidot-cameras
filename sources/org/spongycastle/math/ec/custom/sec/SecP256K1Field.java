package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat256;

public class SecP256K1Field {
    static final int[] a = {-977, -2, -1, -1, -1, -1, -1, -1};
    static final int[] b = {954529, 1954, 1, 0, 0, 0, 0, 0, -1954, -3, -1, -1, -1, -1, -1, -1};
    private static final int[] c = {-954529, -1955, -2, -1, -1, -1, -1, -1, 1953, 2};

    public static void a(int[] x, int[] y, int[] z) {
        if (Nat256.a(x, y, z) != 0 || (z[7] == -1 && Nat256.q(z, a))) {
            Nat.b(8, 977, z);
        }
    }

    public static void b(int[] x, int[] z) {
        if (Nat.r(8, x, z) != 0 || (z[7] == -1 && Nat256.q(z, a))) {
            Nat.b(8, 977, z);
        }
    }

    public static int[] c(BigInteger x) {
        int[] z = Nat256.m(x);
        if (z[7] == -1) {
            int[] iArr = a;
            if (Nat256.q(z, iArr)) {
                Nat256.G(iArr, z);
            }
        }
        return z;
    }

    public static void d(int[] x, int[] y, int[] z) {
        int[] tt = Nat256.h();
        Nat256.w(x, y, tt);
        g(tt, z);
    }

    public static void e(int[] x, int[] y, int[] zz) {
        if (Nat256.A(x, y, zz) != 0 || (zz[15] == -1 && Nat.p(16, zz, b))) {
            int[] iArr = c;
            if (Nat.e(iArr.length, iArr, zz) != 0) {
                Nat.s(16, zz, iArr.length);
            }
        }
    }

    public static void f(int[] x, int[] z) {
        if (Nat256.t(x)) {
            Nat256.J(z);
        } else {
            Nat256.F(a, x, z);
        }
    }

    public static void g(int[] xx, int[] z) {
        if (Nat256.y(977, Nat256.x(977, xx, 8, xx, 0, z, 0), z, 0) != 0 || (z[7] == -1 && Nat256.q(z, a))) {
            Nat.b(8, 977, z);
        }
    }

    public static void h(int x, int[] z) {
        if ((x != 0 && Nat256.z(977, x, z, 0) != 0) || (z[7] == -1 && Nat256.q(z, a))) {
            Nat.b(8, 977, z);
        }
    }

    public static void i(int[] x, int[] z) {
        int[] tt = Nat256.h();
        Nat256.D(x, tt);
        g(tt, z);
    }

    public static void j(int[] x, int n, int[] z) {
        int[] tt = Nat256.h();
        Nat256.D(x, tt);
        g(tt, z);
        while (true) {
            n--;
            if (n > 0) {
                Nat256.D(z, tt);
                g(tt, z);
            } else {
                return;
            }
        }
    }

    public static void k(int[] x, int[] y, int[] z) {
        if (Nat256.F(x, y, z) != 0) {
            Nat.K(8, 977, z);
        }
    }

    public static void l(int[] x, int[] z) {
        if (Nat.D(8, x, 0, z) != 0 || (z[7] == -1 && Nat256.q(z, a))) {
            Nat.b(8, 977, z);
        }
    }
}
