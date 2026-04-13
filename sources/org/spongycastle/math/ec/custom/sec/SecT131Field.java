package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Interleave;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat192;

public class SecT131Field {
    private static final long[] a = {2791191049453778211L, 2791191049453778402L, 6};

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
            j(t0, x, t0);
            q(t0, 2, t1);
            j(t1, t0, t1);
            q(t1, 4, t0);
            j(t0, t1, t0);
            q(t0, 8, t1);
            j(t1, t0, t1);
            q(t1, 16, t0);
            j(t0, t1, t0);
            q(t0, 32, t1);
            j(t1, t0, t1);
            o(t1, t1);
            j(t1, x, t1);
            q(t1, 65, t0);
            j(t0, t1, t0);
            o(t0, z);
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
        long x32 = x3 ^ (x4 >>> 59);
        long x22 = (x2 ^ ((((x4 >>> 3) ^ (x4 >>> 1)) ^ x4) ^ (x4 << 5))) ^ (x32 >>> 59);
        long t = x22 >>> 3;
        z[0] = ((((x0 ^ ((x32 << 61) ^ (x32 << 63))) ^ t) ^ (t << 2)) ^ (t << 3)) ^ (t << 8);
        z[1] = ((x1 ^ ((x4 << 61) ^ (x4 << 63))) ^ ((((x32 >>> 3) ^ (x32 >>> 1)) ^ x32) ^ (x32 << 5))) ^ (t >>> 56);
        z[2] = x22 & 7;
    }

    public static void m(long[] z, int zOff) {
        long z2 = z[zOff + 2];
        long t = z2 >>> 3;
        z[zOff] = z[zOff] ^ ((((t << 2) ^ t) ^ (t << 3)) ^ (t << 8));
        int i = zOff + 1;
        z[i] = z[i] ^ (t >>> 56);
        z[zOff + 2] = 7 & z2;
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
        long[] tt = Nat.j(5);
        h(x, tt);
        l(tt, z);
    }

    public static void p(long[] x, long[] zz) {
        long[] tt = Nat.j(5);
        h(x, tt);
        b(zz, tt, zz);
    }

    public static void q(long[] x, int n, long[] z) {
        long[] tt = Nat.j(5);
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
        zz[0] = z0 ^ (z1 << 44);
        zz[1] = (z1 >>> 20) ^ (z2 << 24);
        zz[2] = ((z2 >>> 40) ^ (z3 << 4)) ^ (z4 << 48);
        zz[3] = ((z3 >>> 60) ^ (z5 << 28)) ^ (z4 >>> 16);
        zz[4] = z5 >>> 36;
        zz[5] = 0;
    }

    protected static void f(long[] x, long[] y, long[] zz) {
        long f0 = x[0];
        long f1 = x[1];
        long f2 = ((f1 >>> 24) ^ (x[2] << 40)) & 17592186044415L;
        long f12 = ((f0 >>> 44) ^ (f1 << 20)) & 17592186044415L;
        long f02 = f0 & 17592186044415L;
        long g0 = y[0];
        long g1 = y[1];
        long g2 = ((g1 >>> 24) ^ (y[2] << 40)) & 17592186044415L;
        long g12 = ((g0 >>> 44) ^ (g1 << 20)) & 17592186044415L;
        long g02 = g0 & 17592186044415L;
        long[] H = new long[10];
        long[] jArr = H;
        g(f02, g02, jArr, 0);
        g(f2, g2, jArr, 2);
        long t0 = (f02 ^ f12) ^ f2;
        long t1 = (g02 ^ g12) ^ g2;
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
        long w1 = (((u1 ^ v1) ^ (H[3] << 4)) ^ (H[3] << 1)) ^ (w0 >>> 44);
        long w2 = (u2 ^ t5) ^ (w1 >>> 44);
        long w12 = w1 & 17592186044415L;
        long w02 = ((w0 & 17592186044415L) >>> 1) ^ ((w12 & 1) << 43);
        long w03 = w02 ^ (w02 << 1);
        long w04 = w03 ^ (w03 << 2);
        long w05 = w04 ^ (w04 << 4);
        long w06 = w05 ^ (w05 << 8);
        long w07 = w06 ^ (w06 << 16);
        long w08 = (w07 ^ (w07 << 32)) & 17592186044415L;
        long w13 = ((w12 >>> 1) ^ ((w2 & 1) << 43)) ^ (w08 >>> 43);
        long w14 = w13 ^ (w13 << 1);
        long w15 = w14 ^ (w14 << 2);
        long w16 = w15 ^ (w15 << 4);
        long w17 = w16 ^ (w16 << 8);
        long w18 = w17 ^ (w17 << 16);
        long w19 = (w18 ^ (w18 << 32)) & 17592186044415L;
        long w22 = (w2 >>> 1) ^ (w19 >>> 43);
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
        int j2 = (int) j;
        long h = 0;
        long l = (u[j2 & 7] ^ (u[(j2 >>> 3) & 7] << 3)) ^ (u[(j2 >>> 6) & 7] << 6);
        int k = 33;
        do {
            int j3 = (int) (j >>> k);
            long g = ((u[j3 & 7] ^ (u[(j3 >>> 3) & 7] << 3)) ^ (u[(j3 >>> 6) & 7] << 6)) ^ (u[(j3 >>> 9) & 7] << 9);
            l ^= g << k;
            h ^= g >>> (-k);
            k -= 12;
        } while (k > 0);
        z[zOff] = 17592186044415L & l;
        z[zOff + 1] = (l >>> 44) ^ (h << 20);
    }

    protected static void h(long[] x, long[] zz) {
        Interleave.c(x[0], zz, 0);
        Interleave.c(x[1], zz, 2);
        zz[4] = ((long) Interleave.d((int) x[2])) & 4294967295L;
    }
}
