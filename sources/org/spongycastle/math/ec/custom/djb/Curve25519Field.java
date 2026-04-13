package org.spongycastle.math.ec.custom.djb;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat256;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class Curve25519Field {
    static final int[] a = {-19, -1, -1, -1, -1, -1, -1, Integer.MAX_VALUE};
    private static final int[] b = {361, 0, 0, 0, 0, 0, 0, 0, -19, -1, -1, -1, -1, -1, -1, 1073741823};

    public static void a(int[] x, int[] y, int[] z) {
        Nat256.a(x, y, z);
        if (Nat256.q(z, a)) {
            m(z);
        }
    }

    public static void b(int[] x, int[] z) {
        Nat.r(8, x, z);
        if (Nat256.q(z, a)) {
            m(z);
        }
    }

    public static int[] d(BigInteger x) {
        int[] z = Nat256.m(x);
        while (true) {
            int[] iArr = a;
            if (!Nat256.q(z, iArr)) {
                return z;
            }
            Nat256.G(iArr, z);
        }
    }

    public static void e(int[] x, int[] y, int[] z) {
        int[] tt = Nat256.h();
        Nat256.w(x, y, tt);
        h(tt, z);
    }

    public static void f(int[] x, int[] y, int[] zz) {
        Nat256.A(x, y, zz);
        if (Nat.p(16, zz, b)) {
            l(zz);
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
        int xx07 = xx[7];
        Nat.C(8, xx, 8, xx07, z, 0);
        int z7 = z[7];
        z[7] = (z7 & Integer.MAX_VALUE) + Nat.g(7, ((Nat256.B(19, xx, z) << 1) + ((z7 >>> 31) - (xx07 >>> 31))) * 19, z);
        if (Nat256.q(z, a)) {
            m(z);
        }
    }

    public static void i(int x, int[] z) {
        int z7 = z[7];
        z[7] = (z7 & Integer.MAX_VALUE) + Nat.g(7, ((x << 1) | (z7 >>> 31)) * 19, z);
        if (Nat256.q(z, a)) {
            m(z);
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

    public static void n(int[] x, int[] y, int[] z) {
        if (Nat256.F(x, y, z) != 0) {
            c(z);
        }
    }

    public static void o(int[] x, int[] z) {
        Nat.D(8, x, 0, z);
        if (Nat256.q(z, a)) {
            m(z);
        }
    }

    private static int c(int[] z) {
        long c = (((long) z[0]) & 4294967295L) - 19;
        z[0] = (int) c;
        long c2 = c >> 32;
        if (c2 != 0) {
            c2 = (long) Nat.l(7, z, 1);
        }
        long c3 = c2 + (4294967295L & ((long) z[7])) + IjkMediaMeta.AV_CH_WIDE_LEFT;
        z[7] = (int) c3;
        return (int) (c3 >> 32);
    }

    private static int m(int[] z) {
        long c = (((long) z[0]) & 4294967295L) + 19;
        z[0] = (int) c;
        long c2 = c >> 32;
        if (c2 != 0) {
            c2 = (long) Nat.s(7, z, 1);
        }
        long c3 = c2 + ((4294967295L & ((long) z[7])) - IjkMediaMeta.AV_CH_WIDE_LEFT);
        z[7] = (int) c3;
        return (int) (c3 >> 32);
    }

    private static int l(int[] zz) {
        int[] iArr = b;
        long c = (((long) zz[0]) & 4294967295L) - (((long) iArr[0]) & 4294967295L);
        zz[0] = (int) c;
        long c2 = c >> 32;
        if (c2 != 0) {
            c2 = (long) Nat.l(8, zz, 1);
        }
        long c3 = c2 + (((long) zz[8]) & 4294967295L) + 19;
        zz[8] = (int) c3;
        long c4 = c3 >> 32;
        if (c4 != 0) {
            c4 = (long) Nat.s(15, zz, 9);
        }
        long c5 = c4 + ((((long) zz[15]) & 4294967295L) - (4294967295L & ((long) (iArr[15] + 1))));
        zz[15] = (int) c5;
        return (int) (c5 >> 32);
    }
}
