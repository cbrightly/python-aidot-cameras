package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat128;
import org.spongycastle.math.raw.Nat256;

public class SecP128R1Field {
    static final int[] a = {-1, -1, -1, -3};
    static final int[] b = {1, 0, 0, 4, -2, -1, 3, -4};
    private static final int[] c = {-1, -1, -1, -5, 1, 0, -4, 3};

    public static void a(int[] x, int[] y, int[] z) {
        if (Nat128.a(x, y, z) != 0 || ((z[3] >>> 1) >= 2147483646 && Nat128.l(z, a))) {
            c(z);
        }
    }

    public static void b(int[] x, int[] z) {
        if (Nat.r(4, x, z) != 0 || ((z[3] >>> 1) >= 2147483646 && Nat128.l(z, a))) {
            c(z);
        }
    }

    public static int[] d(BigInteger x) {
        int[] z = Nat128.i(x);
        if ((z[3] >>> 1) >= 2147483646) {
            int[] iArr = a;
            if (Nat128.l(z, iArr)) {
                Nat128.u(iArr, z);
            }
        }
        return z;
    }

    public static void e(int[] x, int[] y, int[] z) {
        int[] tt = Nat128.e();
        Nat128.q(x, y, tt);
        h(tt, z);
    }

    public static void f(int[] x, int[] y, int[] zz) {
        if (Nat128.r(x, y, zz) != 0 || ((zz[7] >>> 1) >= 2147483646 && Nat256.q(zz, b))) {
            int[] iArr = c;
            Nat.e(iArr.length, iArr, zz);
        }
    }

    public static void g(int[] x, int[] z) {
        if (Nat128.o(x)) {
            Nat128.x(z);
        } else {
            Nat128.t(a, x, z);
        }
    }

    public static void h(int[] xx, int[] z) {
        int[] iArr = z;
        long x7 = ((long) xx[7]) & 4294967295L;
        long x6 = (((long) xx[6]) & 4294967295L) + (x7 << 1);
        long x2 = (((long) xx[2]) & 4294967295L) + x6;
        long x5 = (((long) xx[5]) & 4294967295L) + (x6 << 1);
        long x1 = (((long) xx[1]) & 4294967295L) + x5;
        long x4 = (((long) xx[4]) & 4294967295L) + (x5 << 1);
        long j = x5;
        long x0 = (((long) xx[0]) & 4294967295L) + x4;
        iArr[0] = (int) x0;
        long x12 = x1 + (x0 >>> 32);
        iArr[1] = (int) x12;
        long j2 = x6;
        long x22 = x2 + (x12 >>> 32);
        iArr[2] = (int) x22;
        long x3 = (((long) xx[3]) & 4294967295L) + x7 + (x4 << 1) + (x22 >>> 32);
        iArr[3] = (int) x3;
        long j3 = x4;
        i((int) (x3 >>> 32), iArr);
    }

    public static void i(int x, int[] z) {
        while (x != 0) {
            long x4 = ((long) x) & 4294967295L;
            long c2 = (((long) z[0]) & 4294967295L) + x4;
            z[0] = (int) c2;
            long c3 = c2 >> 32;
            if (c3 != 0) {
                long c4 = c3 + (((long) z[1]) & 4294967295L);
                z[1] = (int) c4;
                long c5 = (c4 >> 32) + (((long) z[2]) & 4294967295L);
                z[2] = (int) c5;
                c3 = c5 >> 32;
            }
            long c6 = c3 + (4294967295L & ((long) z[3])) + (x4 << 1);
            z[3] = (int) c6;
            x = (int) (c6 >> 32);
        }
    }

    public static void j(int[] x, int[] z) {
        int[] tt = Nat128.e();
        Nat128.s(x, tt);
        h(tt, z);
    }

    public static void k(int[] x, int n, int[] z) {
        int[] tt = Nat128.e();
        Nat128.s(x, tt);
        h(tt, z);
        while (true) {
            n--;
            if (n > 0) {
                Nat128.s(z, tt);
                h(tt, z);
            } else {
                return;
            }
        }
    }

    public static void m(int[] x, int[] y, int[] z) {
        if (Nat128.t(x, y, z) != 0) {
            l(z);
        }
    }

    public static void n(int[] x, int[] z) {
        if (Nat.D(4, x, 0, z) != 0 || ((z[3] >>> 1) >= 2147483646 && Nat128.l(z, a))) {
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
            long c5 = (c4 >> 32) + (((long) z[2]) & 4294967295L);
            z[2] = (int) c5;
            c3 = c5 >> 32;
        }
        z[3] = (int) (c3 + (4294967295L & ((long) z[3])) + 2);
    }

    private static void l(int[] z) {
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
        z[3] = (int) (c3 + ((4294967295L & ((long) z[3])) - 2));
    }
}
