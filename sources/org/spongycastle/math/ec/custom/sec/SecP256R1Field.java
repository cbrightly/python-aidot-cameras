package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat256;

public class SecP256R1Field {
    static final int[] a = {-1, -1, -1, 0, 0, 0, 1, -1};
    static final int[] b = {1, 0, 0, -2, -1, -1, -2, 1, -2, 1, -2, 1, 1, -2, 2, -2};

    public static void a(int[] x, int[] y, int[] z) {
        if (Nat256.a(x, y, z) != 0 || (z[7] == -1 && Nat256.q(z, a))) {
            c(z);
        }
    }

    public static void b(int[] x, int[] z) {
        if (Nat.r(8, x, z) != 0 || (z[7] == -1 && Nat256.q(z, a))) {
            c(z);
        }
    }

    public static int[] d(BigInteger x) {
        int[] z = Nat256.m(x);
        if (z[7] == -1) {
            int[] iArr = a;
            if (Nat256.q(z, iArr)) {
                Nat256.G(iArr, z);
            }
        }
        return z;
    }

    public static void e(int[] x, int[] y, int[] z) {
        int[] tt = Nat256.h();
        Nat256.w(x, y, tt);
        h(tt, z);
    }

    public static void f(int[] x, int[] y, int[] zz) {
        if (Nat256.A(x, y, zz) != 0 || ((zz[15] >>> 1) >= Integer.MAX_VALUE && Nat.p(16, zz, b))) {
            Nat.M(16, b, zz);
        }
    }

    public static void g(int[] x, int[] z) {
        if (Nat256.t(x)) {
            Nat256.J(z);
        } else {
            Nat256.F(a, x, z);
        }
    }

    public static void h(int[] xx, int[] z) {
        int[] iArr = z;
        long xx09 = ((long) xx[9]) & 4294967295L;
        long xx10 = ((long) xx[10]) & 4294967295L;
        long xx11 = ((long) xx[11]) & 4294967295L;
        long xx12 = ((long) xx[12]) & 4294967295L;
        long xx13 = ((long) xx[13]) & 4294967295L;
        long xx14 = ((long) xx[14]) & 4294967295L;
        long xx15 = ((long) xx[15]) & 4294967295L;
        long xx08 = (((long) xx[8]) & 4294967295L) - 6;
        long t1 = xx09 + xx10;
        long t2 = (xx10 + xx11) - xx15;
        long t3 = xx11 + xx12;
        long t4 = xx12 + xx13;
        long t5 = xx13 + xx14;
        long t6 = xx14 + xx15;
        long t7 = t5 - (xx08 + xx09);
        long j = xx09;
        long cc = 0 + (((((long) xx[0]) & 4294967295L) - t3) - t7);
        iArr[0] = (int) cc;
        long j2 = xx10;
        long cc2 = (cc >> 32) + ((((((long) xx[1]) & 4294967295L) + t1) - t4) - t6);
        iArr[1] = (int) cc2;
        long j3 = xx11;
        long cc3 = (cc2 >> 32) + (((((long) xx[2]) & 4294967295L) + t2) - t5);
        iArr[2] = (int) cc3;
        long cc4 = (cc3 >> 32) + ((((((long) xx[3]) & 4294967295L) + (t3 << 1)) + t7) - t6);
        iArr[3] = (int) cc4;
        long cc5 = (cc4 >> 32) + ((((((long) xx[4]) & 4294967295L) + (t4 << 1)) + xx14) - t1);
        iArr[4] = (int) cc5;
        long cc6 = (cc5 >> 32) + (((((long) xx[5]) & 4294967295L) + (t5 << 1)) - t2);
        iArr[5] = (int) cc6;
        long cc7 = (cc6 >> 32) + (((long) xx[6]) & 4294967295L) + (t6 << 1) + t7;
        iArr[6] = (int) cc7;
        long cc8 = (cc7 >> 32) + (((((((long) xx[7]) & 4294967295L) + (xx15 << 1)) + xx08) - t2) - t4);
        iArr[7] = (int) cc8;
        i((int) ((cc8 >> 32) + 6), iArr);
    }

    public static void i(int x, int[] z) {
        long cc = 0;
        if (x != 0) {
            long xx08 = ((long) x) & 4294967295L;
            long cc2 = 0 + (((long) z[0]) & 4294967295L) + xx08;
            z[0] = (int) cc2;
            long cc3 = cc2 >> 32;
            if (cc3 != 0) {
                long cc4 = cc3 + (((long) z[1]) & 4294967295L);
                z[1] = (int) cc4;
                long cc5 = (cc4 >> 32) + (((long) z[2]) & 4294967295L);
                z[2] = (int) cc5;
                cc3 = cc5 >> 32;
            }
            long cc6 = cc3 + ((((long) z[3]) & 4294967295L) - xx08);
            z[3] = (int) cc6;
            long cc7 = cc6 >> 32;
            if (cc7 != 0) {
                long cc8 = cc7 + (((long) z[4]) & 4294967295L);
                z[4] = (int) cc8;
                long cc9 = (cc8 >> 32) + (((long) z[5]) & 4294967295L);
                z[5] = (int) cc9;
                cc7 = cc9 >> 32;
            }
            long cc10 = cc7 + ((((long) z[6]) & 4294967295L) - xx08);
            z[6] = (int) cc10;
            long cc11 = (cc10 >> 32) + (4294967295L & ((long) z[7])) + xx08;
            z[7] = (int) cc11;
            cc = cc11 >> 32;
        }
        if (cc != 0 || (z[7] == -1 && Nat256.q(z, a))) {
            c(z);
        }
    }

    public static void j(int[] x, int[] z) {
        int[] tt = Nat256.h();
        Nat256.D(x, tt);
        h(tt, z);
    }

    public static void k(int[] x, int n, int[] z) {
        int[] tt = Nat256.h();
        Nat256.D(x, tt);
        h(tt, z);
        while (true) {
            n--;
            if (n > 0) {
                Nat256.D(z, tt);
                h(tt, z);
            } else {
                return;
            }
        }
    }

    public static void m(int[] x, int[] y, int[] z) {
        if (Nat256.F(x, y, z) != 0) {
            l(z);
        }
    }

    public static void n(int[] x, int[] z) {
        if (Nat.D(8, x, 0, z) != 0 || (z[7] == -1 && Nat256.q(z, a))) {
            c(z);
        }
    }

    private static void c(int[] z) {
        long c = (((long) z[0]) & 4294967295L) + 1;
        z[0] = (int) c;
        long c2 = c >> 32;
        if (c2 != 0) {
            long c3 = c2 + (((long) z[1]) & 4294967295L);
            z[1] = (int) c3;
            long c4 = (c3 >> 32) + (((long) z[2]) & 4294967295L);
            z[2] = (int) c4;
            c2 = c4 >> 32;
        }
        long c5 = c2 + ((((long) z[3]) & 4294967295L) - 1);
        z[3] = (int) c5;
        long c6 = c5 >> 32;
        if (c6 != 0) {
            long c7 = c6 + (((long) z[4]) & 4294967295L);
            z[4] = (int) c7;
            long c8 = (c7 >> 32) + (((long) z[5]) & 4294967295L);
            z[5] = (int) c8;
            c6 = c8 >> 32;
        }
        long c9 = c6 + ((((long) z[6]) & 4294967295L) - 1);
        z[6] = (int) c9;
        z[7] = (int) ((c9 >> 32) + (4294967295L & ((long) z[7])) + 1);
    }

    private static void l(int[] z) {
        long c = (((long) z[0]) & 4294967295L) - 1;
        z[0] = (int) c;
        long c2 = c >> 32;
        if (c2 != 0) {
            long c3 = c2 + (((long) z[1]) & 4294967295L);
            z[1] = (int) c3;
            long c4 = (c3 >> 32) + (((long) z[2]) & 4294967295L);
            z[2] = (int) c4;
            c2 = c4 >> 32;
        }
        long c5 = c2 + (((long) z[3]) & 4294967295L) + 1;
        z[3] = (int) c5;
        long c6 = c5 >> 32;
        if (c6 != 0) {
            long c7 = c6 + (((long) z[4]) & 4294967295L);
            z[4] = (int) c7;
            long c8 = (c7 >> 32) + (((long) z[5]) & 4294967295L);
            z[5] = (int) c8;
            c6 = c8 >> 32;
        }
        long c9 = c6 + (((long) z[6]) & 4294967295L) + 1;
        z[6] = (int) c9;
        z[7] = (int) ((c9 >> 32) + ((4294967295L & ((long) z[7])) - 1));
    }
}
