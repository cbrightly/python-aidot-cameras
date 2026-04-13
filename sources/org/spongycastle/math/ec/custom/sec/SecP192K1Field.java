package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat192;

public class SecP192K1Field {
    static final int[] a = {-4553, -2, -1, -1, -1, -1};
    static final int[] b = {20729809, 9106, 1, 0, 0, 0, -9106, -3, -1, -1, -1, -1};
    private static final int[] c = {-20729809, -9107, -2, -1, -1, -1, 9105, 2};

    public static void a(int[] x, int[] y, int[] z) {
        if (Nat192.a(x, y, z) != 0 || (z[5] == -1 && Nat192.p(z, a))) {
            Nat.b(6, 4553, z);
        }
    }

    public static void b(int[] x, int[] z) {
        if (Nat.r(6, x, z) != 0 || (z[5] == -1 && Nat192.p(z, a))) {
            Nat.b(6, 4553, z);
        }
    }

    public static int[] c(BigInteger x) {
        int[] z = Nat192.l(x);
        if (z[5] == -1) {
            int[] iArr = a;
            if (Nat192.p(z, iArr)) {
                Nat192.E(iArr, z);
            }
        }
        return z;
    }

    public static void d(int[] x, int[] y, int[] z) {
        int[] tt = Nat192.g();
        Nat192.v(x, y, tt);
        g(tt, z);
    }

    public static void e(int[] x, int[] y, int[] zz) {
        if (Nat192.z(x, y, zz) != 0 || (zz[11] == -1 && Nat.p(12, zz, b))) {
            int[] iArr = c;
            if (Nat.e(iArr.length, iArr, zz) != 0) {
                Nat.s(12, zz, iArr.length);
            }
        }
    }

    public static void f(int[] x, int[] z) {
        if (Nat192.s(x)) {
            Nat192.H(z);
        } else {
            Nat192.D(a, x, z);
        }
    }

    public static void g(int[] xx, int[] z) {
        if (Nat192.x(4553, Nat192.w(4553, xx, 6, xx, 0, z, 0), z, 0) != 0 || (z[5] == -1 && Nat192.p(z, a))) {
            Nat.b(6, 4553, z);
        }
    }

    public static void h(int x, int[] z) {
        if ((x != 0 && Nat192.y(4553, x, z, 0) != 0) || (z[5] == -1 && Nat192.p(z, a))) {
            Nat.b(6, 4553, z);
        }
    }

    public static void i(int[] x, int[] z) {
        int[] tt = Nat192.g();
        Nat192.B(x, tt);
        g(tt, z);
    }

    public static void j(int[] x, int n, int[] z) {
        int[] tt = Nat192.g();
        Nat192.B(x, tt);
        g(tt, z);
        while (true) {
            n--;
            if (n > 0) {
                Nat192.B(z, tt);
                g(tt, z);
            } else {
                return;
            }
        }
    }

    public static void k(int[] x, int[] y, int[] z) {
        if (Nat192.D(x, y, z) != 0) {
            Nat.K(6, 4553, z);
        }
    }

    public static void l(int[] x, int[] z) {
        if (Nat.D(6, x, 0, z) != 0 || (z[5] == -1 && Nat192.p(z, a))) {
            Nat.b(6, 4553, z);
        }
    }
}
