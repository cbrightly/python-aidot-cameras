package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Interleave;
import org.spongycastle.math.raw.Nat192;

public class SecT163Field {
    private static final long[] a = {-5270498306774157648L, 5270498306774195053L, 19634136210L};

    public static void a(long[] x, long[] y, long[] z) {
        z[0] = x[0] ^ y[0];
        z[1] = x[1] ^ y[1];
        z[2] = x[2] ^ y[2];
    }

    public static void b(long[] xx, long[] yy, long[] zz) {
        zz[0] = xx[0] ^ yy[0];
        zz[1] = xx[1] ^ yy[1];
        zz[2] = xx[2] ^ yy[2];
        zz[3] = xx[3] ^ yy[3];
        zz[4] = xx[4] ^ yy[4];
        zz[5] = xx[5] ^ yy[5];
    }

    public static void c(long[] x, long[] z) {
        z[0] = x[0] ^ 1;
        z[1] = x[1];
        z[2] = x[2];
    }

    public static long[] d(BigInteger x) {
        long[] z = Nat192.m(x);
        m(z, 0);
        return z;
    }

    public static void i(long[] x, long[] z) {
        if (!Nat192.t(x)) {
            long[] t0 = Nat192.f();
            long[] t1 = Nat192.f();
            o(x, t0);
            q(t0, 1, t1);
            j(t0, t1, t0);
            q(t1, 1, t1);
            j(t0, t1, t0);
            q(t0, 3, t1);
            j(t0, t1, t0);
            q(t1, 3, t1);
            j(t0, t1, t0);
            q(t0, 9, t1);
            j(t0, t1, t0);
            q(t1, 9, t1);
            j(t0, t1, t0);
            q(t0, 27, t1);
            j(t0, t1, t0);
            q(t1, 27, t1);
            j(t0, t1, t0);
            q(t0, 81, t1);
            j(t0, t1, z);
            return;
        }
        throw new IllegalStateException();
    }

    public static void j(long[] x, long[] y, long[] z) {
        long[] tt = Nat192.h();
        f(x, y, tt);
        l(tt, z);
    }

    public static void k(long[] x, long[] y, long[] zz) {
        long[] tt = Nat192.h();
        f(x, y, tt);
        b(zz, tt, zz);
    }

    public static void l(long[] xx, long[] z) {
        long x0 = xx[0];
        long x1 = xx[1];
        long x2 = xx[2];
        long x3 = xx[3];
        long x4 = xx[4];
        long x5 = xx[5];
        long x32 = x3 ^ ((((x5 >>> 35) ^ (x5 >>> 32)) ^ (x5 >>> 29)) ^ (x5 >>> 28));
        long x22 = (x2 ^ ((((x5 << 29) ^ (x5 << 32)) ^ (x5 << 35)) ^ (x5 << 36))) ^ ((((x4 >>> 35) ^ (x4 >>> 32)) ^ (x4 >>> 29)) ^ (x4 >>> 28));
        long t = x22 >>> 35;
        z[0] = ((((x0 ^ ((((x32 << 29) ^ (x32 << 32)) ^ (x32 << 35)) ^ (x32 << 36))) ^ t) ^ (t << 3)) ^ (t << 6)) ^ (t << 7);
        z[1] = (x1 ^ ((((x4 << 29) ^ (x4 << 32)) ^ (x4 << 35)) ^ (x4 << 36))) ^ ((((x32 >>> 35) ^ (x32 >>> 32)) ^ (x32 >>> 29)) ^ (x32 >>> 28));
        z[2] = x22 & 34359738367L;
    }

    public static void m(long[] z, int zOff) {
        long z2 = z[zOff + 2];
        long t = z2 >>> 35;
        z[zOff] = z[zOff] ^ ((((t << 3) ^ t) ^ (t << 6)) ^ (t << 7));
        z[zOff + 2] = 34359738367L & z2;
    }

    public static void n(long[] x, long[] z) {
        long[] jArr = z;
        long[] odd = Nat192.f();
        long u0 = Interleave.e(x[0]);
        long u1 = Interleave.e(x[1]);
        long e0 = (u0 & 4294967295L) | (u1 << 32);
        odd[0] = (u0 >>> 32) | (-4294967296L & u1);
        long u02 = Interleave.e(x[2]);
        odd[1] = u02 >>> 32;
        j(odd, a, jArr);
        jArr[0] = jArr[0] ^ e0;
        jArr[1] = jArr[1] ^ (4294967295L & u02);
    }

    public static void o(long[] x, long[] z) {
        long[] tt = Nat192.h();
        h(x, tt);
        l(tt, z);
    }

    public static void p(long[] x, long[] zz) {
        long[] tt = Nat192.h();
        h(x, tt);
        b(zz, tt, zz);
    }

    public static void q(long[] x, int n, long[] z) {
        long[] tt = Nat192.h();
        h(x, tt);
        l(tt, z);
        while (true) {
            n--;
            if (n > 0) {
                h(z, tt);
                l(tt, z);
            } else {
                return;
            }
        }
    }

    protected static void e(long[] zz) {
        long z0 = zz[0];
        long z1 = zz[1];
        long z2 = zz[2];
        long z3 = zz[3];
        long z4 = zz[4];
        long z5 = zz[5];
        zz[0] = z0 ^ (z1 << 55);
        zz[1] = (z1 >>> 9) ^ (z2 << 46);
        zz[2] = (z2 >>> 18) ^ (z3 << 37);
        zz[3] = (z3 >>> 27) ^ (z4 << 28);
        zz[4] = (z4 >>> 36) ^ (z5 << 19);
        zz[5] = z5 >>> 45;
    }

    protected static void f(long[] x, long[] y, long[] zz) {
        long f0 = x[0];
        long f1 = x[1];
        long f2 = (f1 >>> 46) ^ (x[2] << 18);
        long f12 = ((f0 >>> 55) ^ (f1 << 9)) & 36028797018963967L;
        long f02 = f0 & 36028797018963967L;
        long g0 = y[0];
        long g1 = y[1];
        long g2 = (g1 >>> 46) ^ (y[2] << 18);
        long g12 = ((g0 >>> 55) ^ (g1 << 9)) & 36028797018963967L;
        long g02 = g0 & 36028797018963967L;
        long[] H = new long[10];
        g(f02, g02, H, 0);
        g(f2, g2, H, 2);
        long t0 = (f02 ^ f12) ^ f2;
        long t1 = (g02 ^ g12) ^ g2;
        long[] jArr = H;
        g(t0, t1, jArr, 4);
        long t2 = (f12 << 1) ^ (f2 << 2);
        long t3 = (g12 << 1) ^ (g2 << 2);
        g(f02 ^ t2, g02 ^ t3, jArr, 6);
        g(t0 ^ t2, t1 ^ t3, jArr, 8);
        long t4 = H[6] ^ H[8];
        long t5 = H[7] ^ H[9];
        long v0 = (t4 << 1) ^ H[6];
        long v1 = (t4 ^ (t5 << 1)) ^ H[7];
        long u0 = H[0];
        long u1 = (H[1] ^ H[0]) ^ H[4];
        long u2 = H[1] ^ H[5];
        long w0 = ((u0 ^ v0) ^ (H[2] << 4)) ^ (H[2] << 1);
        long w1 = (((u1 ^ v1) ^ (H[3] << 4)) ^ (H[3] << 1)) ^ (w0 >>> 55);
        long w2 = (u2 ^ t5) ^ (w1 >>> 55);
        long w12 = w1 & 36028797018963967L;
        long w02 = ((w0 & 36028797018963967L) >>> 1) ^ ((w12 & 1) << 54);
        long w03 = w02 ^ (w02 << 1);
        long w04 = w03 ^ (w03 << 2);
        long w05 = w04 ^ (w04 << 4);
        long w06 = w05 ^ (w05 << 8);
        long w07 = w06 ^ (w06 << 16);
        long w08 = (w07 ^ (w07 << 32)) & 36028797018963967L;
        long w13 = ((w12 >>> 1) ^ ((w2 & 1) << 54)) ^ (w08 >>> 54);
        long w14 = w13 ^ (w13 << 1);
        long w15 = w14 ^ (w14 << 2);
        long w16 = w15 ^ (w15 << 4);
        long w17 = w16 ^ (w16 << 8);
        long w18 = w17 ^ (w17 << 16);
        long w19 = (w18 ^ (w18 << 32)) & 36028797018963967L;
        long w22 = (w2 >>> 1) ^ (w19 >>> 54);
        long w23 = w22 ^ (w22 << 1);
        long w24 = w23 ^ (w23 << 2);
        long w25 = w24 ^ (w24 << 4);
        long w26 = w25 ^ (w25 << 8);
        long w27 = w26 ^ (w26 << 16);
        long w28 = w27 ^ (w27 << 32);
        zz[0] = u0;
        zz[1] = (u1 ^ w08) ^ H[2];
        zz[2] = ((u2 ^ w19) ^ w08) ^ H[3];
        zz[3] = w28 ^ w19;
        zz[4] = w28 ^ H[2];
        zz[5] = H[3];
        e(zz);
    }

    protected static void g(long x, long y, long[] z, int zOff) {
        long j = x;
        long[] u = new long[8];
        u[1] = y;
        u[2] = u[1] << 1;
        u[3] = u[2] ^ y;
        u[4] = u[2] << 1;
        u[5] = u[4] ^ y;
        u[6] = u[3] << 1;
        u[7] = u[6] ^ y;
        long h = 0;
        long l = u[((int) j) & 3];
        int k = 47;
        do {
            int j2 = (int) (j >>> k);
            long g = (u[j2 & 7] ^ (u[(j2 >>> 3) & 7] << 3)) ^ (u[(j2 >>> 6) & 7] << 6);
            l ^= g << k;
            h ^= g >>> (-k);
            k -= 9;
        } while (k > 0);
        z[zOff] = 36028797018963967L & l;
        z[zOff + 1] = (l >>> 55) ^ (h << 9);
    }

    protected static void h(long[] x, long[] zz) {
        Interleave.c(x[0], zz, 0);
        Interleave.c(x[1], zz, 2);
        long x2 = x[2];
        zz[4] = Interleave.b((int) x2);
        zz[5] = ((long) Interleave.d((int) (x2 >>> 32))) & 4294967295L;
    }
}
