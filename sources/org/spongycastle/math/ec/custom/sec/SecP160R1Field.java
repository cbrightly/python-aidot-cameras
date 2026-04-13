package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat160;

public class SecP160R1Field {
    static final int[] a = {Integer.MAX_VALUE, -1, -1, -1, -1};
    static final int[] b = {1, 1073741825, 0, 0, 0, -2, -2, -1, -1, -1};
    private static final int[] c = {-1, -1073741826, -1, -1, -1, 1, 1};

    public static void a(int[] x, int[] y, int[] z) {
        if (Nat160.a(x, y, z) != 0 || (z[4] == -1 && Nat160.h(z, a))) {
            Nat.g(5, -2147483647, z);
        }
    }

    public static void b(int[] x, int[] z) {
        if (Nat.r(5, x, z) != 0 || (z[4] == -1 && Nat160.h(z, a))) {
            Nat.g(5, -2147483647, z);
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
        int[] iArr = z;
        long x5 = ((long) xx[5]) & 4294967295L;
        long x6 = ((long) xx[6]) & 4294967295L;
        long x7 = ((long) xx[7]) & 4294967295L;
        long x8 = ((long) xx[8]) & 4294967295L;
        long x9 = ((long) xx[9]) & 4294967295L;
        long c2 = 0 + (((long) xx[0]) & 4294967295L) + x5 + (x5 << 31);
        iArr[0] = (int) c2;
        long c3 = (c2 >>> 32) + (((long) xx[1]) & 4294967295L) + x6 + (x6 << 31);
        iArr[1] = (int) c3;
        long c4 = (c3 >>> 32) + (((long) xx[2]) & 4294967295L) + x7 + (x7 << 31);
        iArr[2] = (int) c4;
        long c5 = (c4 >>> 32) + (((long) xx[3]) & 4294967295L) + x8 + (x8 << 31);
        iArr[3] = (int) c5;
        long c6 = (c5 >>> 32) + (4294967295L & ((long) xx[4])) + x9 + (x9 << 31);
        iArr[4] = (int) c6;
        h((int) (c6 >>> 32), iArr);
    }

    public static void h(int x, int[] z) {
        if ((x != 0 && Nat160.p(-2147483647, x, z, 0) != 0) || (z[4] == -1 && Nat160.h(z, a))) {
            Nat.g(5, -2147483647, z);
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
            Nat.N(5, -2147483647, z);
        }
    }

    public static void l(int[] x, int[] z) {
        if (Nat.D(5, x, 0, z) != 0 || (z[4] == -1 && Nat160.h(z, a))) {
            Nat.g(5, -2147483647, z);
        }
    }
}
