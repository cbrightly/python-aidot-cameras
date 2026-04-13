package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat192;

public class SecP192R1Field {
    static final int[] a = {-1, -1, -2, -1, -1, -1};
    static final int[] b = {1, 0, 2, 0, 1, 0, -2, -1, -3, -1, -1, -1};
    private static final int[] c = {-1, -1, -3, -1, -2, -1, 1, 0, 2};

    public static void a(int[] x, int[] y, int[] z) {
        if (Nat192.a(x, y, z) != 0 || (z[5] == -1 && Nat192.p(z, a))) {
            c(z);
        }
    }

    public static void b(int[] x, int[] z) {
        if (Nat.r(6, x, z) != 0 || (z[5] == -1 && Nat192.p(z, a))) {
            c(z);
        }
    }

    public static int[] d(BigInteger x) {
        int[] z = Nat192.l(x);
        if (z[5] == -1) {
            int[] iArr = a;
            if (Nat192.p(z, iArr)) {
                Nat192.E(iArr, z);
            }
        }
        return z;
    }

    public static void e(int[] x, int[] y, int[] z) {
        int[] tt = Nat192.g();
        Nat192.v(x, y, tt);
        h(tt, z);
    }

    public static void f(int[] x, int[] y, int[] zz) {
        if (Nat192.z(x, y, zz) != 0 || (zz[11] == -1 && Nat.p(12, zz, b))) {
            int[] iArr = c;
            if (Nat.e(iArr.length, iArr, zz) != 0) {
                Nat.s(12, zz, iArr.length);
            }
        }
    }

    public static void g(int[] x, int[] z) {
        if (Nat192.s(x)) {
            Nat192.H(z);
        } else {
            Nat192.D(a, x, z);
        }
    }

    public static void h(int[] xx, int[] z) {
        int[] iArr = z;
        long xx06 = ((long) xx[6]) & 4294967295L;
        long xx07 = ((long) xx[7]) & 4294967295L;
        long xx10 = ((long) xx[10]) & 4294967295L;
        long xx11 = ((long) xx[11]) & 4294967295L;
        long t0 = xx06 + xx10;
        long t1 = xx07 + xx11;
        long j = xx10;
        long cc = 0 + (((long) xx[0]) & 4294967295L) + t0;
        int z0 = (int) cc;
        long j2 = xx11;
        long cc2 = (cc >> 32) + (((long) xx[1]) & 4294967295L) + t1;
        iArr[1] = (int) cc2;
        long t02 = t0 + (((long) xx[8]) & 4294967295L);
        long t12 = t1 + (((long) xx[9]) & 4294967295L);
        long cc3 = (cc2 >> 32) + (((long) xx[2]) & 4294967295L) + t02;
        long z2 = cc3 & 4294967295L;
        long cc4 = (cc3 >> 32) + (((long) xx[3]) & 4294967295L) + t12;
        iArr[3] = (int) cc4;
        long cc5 = (cc4 >> 32) + (((long) xx[4]) & 4294967295L) + (t02 - xx06);
        iArr[4] = (int) cc5;
        long cc6 = (cc5 >> 32) + (((long) xx[5]) & 4294967295L) + (t12 - xx07);
        iArr[5] = (int) cc6;
        long cc7 = cc6 >> 32;
        long z22 = z2 + cc7;
        long cc8 = cc7 + (((long) z0) & 4294967295L);
        iArr[0] = (int) cc8;
        long cc9 = cc8 >> 32;
        if (cc9 != 0) {
            long cc10 = cc9 + (((long) iArr[1]) & 4294967295L);
            iArr[1] = (int) cc10;
            z22 += cc10 >> 32;
        }
        iArr[2] = (int) z22;
        if (((z22 >> 32) != 0 && Nat.s(6, iArr, 3) != 0) || (iArr[5] == -1 && Nat192.p(iArr, a))) {
            c(z);
        }
    }

    public static void i(int x, int[] z) {
        long cc = 0;
        if (x != 0) {
            long xx06 = ((long) x) & 4294967295L;
            long cc2 = 0 + (((long) z[0]) & 4294967295L) + xx06;
            z[0] = (int) cc2;
            long cc3 = cc2 >> 32;
            if (cc3 != 0) {
                long cc4 = cc3 + (((long) z[1]) & 4294967295L);
                z[1] = (int) cc4;
                cc3 = cc4 >> 32;
            }
            long cc5 = cc3 + (4294967295L & ((long) z[2])) + xx06;
            z[2] = (int) cc5;
            cc = cc5 >> 32;
        }
        if ((cc != 0 && Nat.s(6, z, 3) != 0) || (z[5] == -1 && Nat192.p(z, a))) {
            c(z);
        }
    }

    public static void j(int[] x, int[] z) {
        int[] tt = Nat192.g();
        Nat192.B(x, tt);
        h(tt, z);
    }

    public static void k(int[] x, int n, int[] z) {
        int[] tt = Nat192.g();
        Nat192.B(x, tt);
        h(tt, z);
        while (true) {
            n--;
            if (n > 0) {
                Nat192.B(z, tt);
                h(tt, z);
            } else {
                return;
            }
        }
    }

    public static void m(int[] x, int[] y, int[] z) {
        if (Nat192.D(x, y, z) != 0) {
            l(z);
        }
    }

    public static void n(int[] x, int[] z) {
        if (Nat.D(6, x, 0, z) != 0 || (z[5] == -1 && Nat192.p(z, a))) {
            c(z);
        }
    }

    private static void c(int[] z) {
        long c2 = (((long) z[0]) & 4294967295L) + 1;
        z[0] = (int) c2;
        long c3 = c2 >> 32;
        if (c3 != 0) {
            long c4 = c3 + (((long) z[1]) & 4294967295L);
            z[1] = (int) c4;
            c3 = c4 >> 32;
        }
        long c5 = c3 + (4294967295L & ((long) z[2])) + 1;
        z[2] = (int) c5;
        if ((c5 >> 32) != 0) {
            Nat.s(6, z, 3);
        }
    }

    private static void l(int[] z) {
        long c2 = (((long) z[0]) & 4294967295L) - 1;
        z[0] = (int) c2;
        long c3 = c2 >> 32;
        if (c3 != 0) {
            long c4 = c3 + (((long) z[1]) & 4294967295L);
            z[1] = (int) c4;
            c3 = c4 >> 32;
        }
        long c5 = c3 + ((4294967295L & ((long) z[2])) - 1);
        z[2] = (int) c5;
        if ((c5 >> 32) != 0) {
            Nat.l(6, z, 3);
        }
    }
}
