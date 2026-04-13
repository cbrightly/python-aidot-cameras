package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat224;

public class SecP224R1Field {
    static final int[] a = {1, 0, 0, -1, -1, -1, -1};
    static final int[] b = {1, 0, 0, -2, -1, -1, 0, 2, 0, 0, -2, -1, -1, -1};
    private static final int[] c = {-1, -1, -1, 1, 0, 0, -1, -3, -1, -1, 1};

    public static void a(int[] x, int[] y, int[] z) {
        if (Nat224.a(x, y, z) != 0 || (z[6] == -1 && Nat224.i(z, a))) {
            c(z);
        }
    }

    public static void b(int[] x, int[] z) {
        if (Nat.r(7, x, z) != 0 || (z[6] == -1 && Nat224.i(z, a))) {
            c(z);
        }
    }

    public static int[] d(BigInteger x) {
        int[] z = Nat224.g(x);
        if (z[6] == -1) {
            int[] iArr = a;
            if (Nat224.i(z, iArr)) {
                Nat224.s(iArr, z);
            }
        }
        return z;
    }

    public static void e(int[] x, int[] y, int[] z) {
        int[] tt = Nat224.e();
        Nat224.l(x, y, tt);
        h(tt, z);
    }

    public static void f(int[] x, int[] y, int[] zz) {
        if (Nat224.p(x, y, zz) != 0 || (zz[13] == -1 && Nat.p(14, zz, b))) {
            int[] iArr = c;
            if (Nat.e(iArr.length, iArr, zz) != 0) {
                Nat.s(14, zz, iArr.length);
            }
        }
    }

    public static void g(int[] x, int[] z) {
        if (Nat224.k(x)) {
            Nat224.u(z);
        } else {
            Nat224.r(a, x, z);
        }
    }

    public static void h(int[] xx, int[] z) {
        int[] iArr = z;
        long xx10 = ((long) xx[10]) & 4294967295L;
        long xx11 = ((long) xx[11]) & 4294967295L;
        long xx12 = ((long) xx[12]) & 4294967295L;
        long xx13 = ((long) xx[13]) & 4294967295L;
        long t0 = ((((long) xx[7]) & 4294967295L) + xx11) - 1;
        long t1 = (((long) xx[8]) & 4294967295L) + xx12;
        long xx122 = xx12;
        long t2 = (((long) xx[9]) & 4294967295L) + xx13;
        long xx132 = xx13;
        long cc = 0 + ((((long) xx[0]) & 4294967295L) - t0);
        long z0 = cc & 4294967295L;
        long cc2 = (cc >> 32) + ((((long) xx[1]) & 4294967295L) - t1);
        iArr[1] = (int) cc2;
        long xx112 = xx11;
        long cc3 = (cc2 >> 32) + ((((long) xx[2]) & 4294967295L) - t2);
        iArr[2] = (int) cc3;
        long cc4 = (cc3 >> 32) + (((((long) xx[3]) & 4294967295L) + t0) - xx10);
        long z3 = cc4 & 4294967295L;
        long j = t0;
        long cc5 = (cc4 >> 32) + (((((long) xx[4]) & 4294967295L) + t1) - xx112);
        iArr[4] = (int) cc5;
        long cc6 = (cc5 >> 32) + (((((long) xx[5]) & 4294967295L) + t2) - xx122);
        iArr[5] = (int) cc6;
        long cc7 = (cc6 >> 32) + (((((long) xx[6]) & 4294967295L) + xx10) - xx132);
        iArr[6] = (int) cc7;
        long cc8 = (cc7 >> 32) + 1;
        long z32 = z3 + cc8;
        long z02 = z0 - cc8;
        iArr[0] = (int) z02;
        long cc9 = z02 >> 32;
        if (cc9 != 0) {
            long j2 = xx10;
            long cc10 = cc9 + (((long) iArr[1]) & 4294967295L);
            iArr[1] = (int) cc10;
            long cc11 = (cc10 >> 32) + (4294967295L & ((long) iArr[2]));
            iArr[2] = (int) cc11;
            z32 += cc11 >> 32;
        }
        iArr[3] = (int) z32;
        if (((z32 >> 32) != 0 && Nat.s(7, iArr, 4) != 0) || (iArr[6] == -1 && Nat224.i(iArr, a))) {
            c(z);
        }
    }

    public static void i(int x, int[] z) {
        long cc = 0;
        if (x != 0) {
            long xx07 = ((long) x) & 4294967295L;
            long cc2 = 0 + ((((long) z[0]) & 4294967295L) - xx07);
            z[0] = (int) cc2;
            long cc3 = cc2 >> 32;
            if (cc3 != 0) {
                long cc4 = cc3 + (((long) z[1]) & 4294967295L);
                z[1] = (int) cc4;
                long cc5 = (cc4 >> 32) + (((long) z[2]) & 4294967295L);
                z[2] = (int) cc5;
                cc3 = cc5 >> 32;
            }
            long cc6 = cc3 + (4294967295L & ((long) z[3])) + xx07;
            z[3] = (int) cc6;
            cc = cc6 >> 32;
        }
        if ((cc != 0 && Nat.s(7, z, 4) != 0) || (z[6] == -1 && Nat224.i(z, a))) {
            c(z);
        }
    }

    public static void j(int[] x, int[] z) {
        int[] tt = Nat224.e();
        Nat224.q(x, tt);
        h(tt, z);
    }

    public static void k(int[] x, int n, int[] z) {
        int[] tt = Nat224.e();
        Nat224.q(x, tt);
        h(tt, z);
        while (true) {
            n--;
            if (n > 0) {
                Nat224.q(z, tt);
                h(tt, z);
            } else {
                return;
            }
        }
    }

    public static void m(int[] x, int[] y, int[] z) {
        if (Nat224.r(x, y, z) != 0) {
            l(z);
        }
    }

    public static void n(int[] x, int[] z) {
        if (Nat.D(7, x, 0, z) != 0 || (z[6] == -1 && Nat224.i(z, a))) {
            c(z);
        }
    }

    private static void c(int[] z) {
        long c2 = (((long) z[0]) & 4294967295L) - 1;
        z[0] = (int) c2;
        long c3 = c2 >> 32;
        if (c3 != 0) {
            long c4 = c3 + (((long) z[1]) & 4294967295L);
            z[1] = (int) c4;
            long c5 = (c4 >> 32) + (((long) z[2]) & 4294967295L);
            z[2] = (int) c5;
            c3 = c5 >> 32;
        }
        long c6 = c3 + (4294967295L & ((long) z[3])) + 1;
        z[3] = (int) c6;
        if ((c6 >> 32) != 0) {
            Nat.s(7, z, 4);
        }
    }

    private static void l(int[] z) {
        long c2 = (((long) z[0]) & 4294967295L) + 1;
        z[0] = (int) c2;
        long c3 = c2 >> 32;
        if (c3 != 0) {
            long c4 = c3 + (((long) z[1]) & 4294967295L);
            z[1] = (int) c4;
            long c5 = (c4 >> 32) + (((long) z[2]) & 4294967295L);
            z[2] = (int) c5;
            c3 = c5 >> 32;
        }
        long c6 = c3 + ((4294967295L & ((long) z[3])) - 1);
        z[3] = (int) c6;
        if ((c6 >> 32) != 0) {
            Nat.l(7, z, 4);
        }
    }
}
