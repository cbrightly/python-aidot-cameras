package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat384;

public class SecP384R1Field {
    static final int[] a = {-1, 0, 0, -1, -2, -1, -1, -1, -1, -1, -1, -1};
    static final int[] b = {1, -2, 0, 2, 0, -2, 0, 2, 1, 0, 0, 0, -2, 1, 0, -2, -3, -1, -1, -1, -1, -1, -1, -1};
    private static final int[] c = {-1, 1, -1, -3, -1, 1, -1, -3, -2, -1, -1, -1, 1, -2, -1, 1, 2};

    public static void a(int[] x, int[] y, int[] z) {
        if (Nat.a(12, x, y, z) != 0 || (z[11] == -1 && Nat.p(12, z, a))) {
            d(z);
        }
    }

    public static void b(int[] xx, int[] yy, int[] zz) {
        if (Nat.a(24, xx, yy, zz) != 0 || (zz[23] == -1 && Nat.p(24, zz, b))) {
            int[] iArr = c;
            if (Nat.e(iArr.length, iArr, zz) != 0) {
                Nat.s(24, zz, iArr.length);
            }
        }
    }

    public static void c(int[] x, int[] z) {
        if (Nat.r(12, x, z) != 0 || (z[11] == -1 && Nat.p(12, z, a))) {
            d(z);
        }
    }

    public static int[] e(BigInteger x) {
        int[] z = Nat.n(384, x);
        if (z[11] == -1) {
            int[] iArr = a;
            if (Nat.p(12, z, iArr)) {
                Nat.M(12, iArr, z);
            }
        }
        return z;
    }

    public static void f(int[] x, int[] y, int[] z) {
        int[] tt = Nat.i(24);
        Nat384.a(x, y, tt);
        h(tt, z);
    }

    public static void g(int[] x, int[] z) {
        if (Nat.v(12, x)) {
            Nat.P(12, z);
        } else {
            Nat.J(12, a, x, z);
        }
    }

    public static void h(int[] xx, int[] z) {
        int[] iArr = z;
        long xx17 = ((long) xx[17]) & 4294967295L;
        long xx20 = ((long) xx[20]) & 4294967295L;
        long xx21 = ((long) xx[21]) & 4294967295L;
        long xx19 = ((long) xx[19]) & 4294967295L;
        long xx22 = ((long) xx[22]) & 4294967295L;
        long xx18 = ((long) xx[18]) & 4294967295L;
        long xx23 = ((long) xx[23]) & 4294967295L;
        long xx16 = ((long) xx[16]) & 4294967295L;
        long t0 = ((((long) xx[12]) & 4294967295L) + xx20) - 1;
        long xx202 = xx20;
        long t1 = (((long) xx[13]) & 4294967295L) + xx22;
        long t2 = (((long) xx[14]) & 4294967295L) + xx22 + xx23;
        long t3 = (((long) xx[15]) & 4294967295L) + xx23;
        long t4 = xx17 + xx21;
        long t5 = xx21 - xx23;
        long t6 = xx22 - xx23;
        long t7 = t0 + t5;
        long j = xx22;
        long cc = 0 + (((long) xx[0]) & 4294967295L) + t7;
        iArr[0] = (int) cc;
        long cc2 = (cc >> 32) + (((((long) xx[1]) & 4294967295L) + xx23) - t0) + t1;
        iArr[1] = (int) cc2;
        long cc3 = (cc2 >> 32) + (((((long) xx[2]) & 4294967295L) - xx21) - t1) + t2;
        iArr[2] = (int) cc3;
        long cc4 = (cc3 >> 32) + ((((long) xx[3]) & 4294967295L) - t2) + t3 + t7;
        iArr[3] = (int) cc4;
        long cc5 = (cc4 >> 32) + (((((((long) xx[4]) & 4294967295L) + xx16) + xx21) + t1) - t3) + t7;
        iArr[4] = (int) cc5;
        long cc6 = (cc5 >> 32) + ((((long) xx[5]) & 4294967295L) - xx16) + t1 + t2 + t4;
        iArr[5] = (int) cc6;
        long cc7 = (cc6 >> 32) + (((((long) xx[6]) & 4294967295L) + xx18) - xx17) + t2 + t3;
        iArr[6] = (int) cc7;
        long cc8 = (cc7 >> 32) + ((((((long) xx[7]) & 4294967295L) + xx16) + xx19) - xx18) + t3;
        iArr[7] = (int) cc8;
        long cc9 = (cc8 >> 32) + (((((((long) xx[8]) & 4294967295L) + xx16) + xx17) + xx202) - xx19);
        iArr[8] = (int) cc9;
        long cc10 = (cc9 >> 32) + (((((long) xx[9]) & 4294967295L) + xx18) - xx202) + t4;
        iArr[9] = (int) cc10;
        long cc11 = (cc10 >> 32) + ((((((long) xx[10]) & 4294967295L) + xx18) + xx19) - t5) + t6;
        iArr[10] = (int) cc11;
        long cc12 = (cc11 >> 32) + ((((((long) xx[11]) & 4294967295L) + xx19) + xx202) - t6);
        iArr[11] = (int) cc12;
        i((int) ((cc12 >> 32) + 1), iArr);
    }

    public static void i(int x, int[] z) {
        long cc = 0;
        if (x != 0) {
            long xx12 = ((long) x) & 4294967295L;
            long cc2 = 0 + (((long) z[0]) & 4294967295L) + xx12;
            z[0] = (int) cc2;
            long cc3 = (cc2 >> 32) + ((((long) z[1]) & 4294967295L) - xx12);
            z[1] = (int) cc3;
            long cc4 = cc3 >> 32;
            if (cc4 != 0) {
                long cc5 = cc4 + (((long) z[2]) & 4294967295L);
                z[2] = (int) cc5;
                cc4 = cc5 >> 32;
            }
            long cc6 = cc4 + (((long) z[3]) & 4294967295L) + xx12;
            z[3] = (int) cc6;
            long cc7 = (cc6 >> 32) + (4294967295L & ((long) z[4])) + xx12;
            z[4] = (int) cc7;
            cc = cc7 >> 32;
        }
        if ((cc != 0 && Nat.s(12, z, 5) != 0) || (z[11] == -1 && Nat.p(12, z, a))) {
            d(z);
        }
    }

    public static void j(int[] x, int[] z) {
        int[] tt = Nat.i(24);
        Nat384.b(x, tt);
        h(tt, z);
    }

    public static void k(int[] x, int n, int[] z) {
        int[] tt = Nat.i(24);
        Nat384.b(x, tt);
        h(tt, z);
        while (true) {
            n--;
            if (n > 0) {
                Nat384.b(z, tt);
                h(tt, z);
            } else {
                return;
            }
        }
    }

    public static void m(int[] x, int[] y, int[] z) {
        if (Nat.J(12, x, y, z) != 0) {
            l(z);
        }
    }

    public static void n(int[] x, int[] z) {
        if (Nat.D(12, x, 0, z) != 0 || (z[11] == -1 && Nat.p(12, z, a))) {
            d(z);
        }
    }

    private static void d(int[] z) {
        long c2 = (((long) z[0]) & 4294967295L) + 1;
        z[0] = (int) c2;
        long c3 = (c2 >> 32) + ((((long) z[1]) & 4294967295L) - 1);
        z[1] = (int) c3;
        long c4 = c3 >> 32;
        if (c4 != 0) {
            long c5 = c4 + (((long) z[2]) & 4294967295L);
            z[2] = (int) c5;
            c4 = c5 >> 32;
        }
        long c6 = c4 + (((long) z[3]) & 4294967295L) + 1;
        z[3] = (int) c6;
        long c7 = (c6 >> 32) + (4294967295L & ((long) z[4])) + 1;
        z[4] = (int) c7;
        if ((c7 >> 32) != 0) {
            Nat.s(12, z, 5);
        }
    }

    private static void l(int[] z) {
        long c2 = (((long) z[0]) & 4294967295L) - 1;
        z[0] = (int) c2;
        long c3 = (c2 >> 32) + (((long) z[1]) & 4294967295L) + 1;
        z[1] = (int) c3;
        long c4 = c3 >> 32;
        if (c4 != 0) {
            long c5 = c4 + (((long) z[2]) & 4294967295L);
            z[2] = (int) c5;
            c4 = c5 >> 32;
        }
        long c6 = c4 + ((((long) z[3]) & 4294967295L) - 1);
        z[3] = (int) c6;
        long c7 = (c6 >> 32) + ((4294967295L & ((long) z[4])) - 1);
        z[4] = (int) c7;
        if ((c7 >> 32) != 0) {
            Nat.l(12, z, 5);
        }
    }
}
