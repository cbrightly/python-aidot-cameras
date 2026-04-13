package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Interleave;
import org.spongycastle.math.raw.Nat128;

public class SecT113Field {
    public static void a(long[] x, long[] y, long[] z) {
        z[0] = x[0] ^ y[0];
        z[1] = x[1] ^ y[1];
    }

    public static void b(long[] xx, long[] yy, long[] zz) {
        zz[0] = xx[0] ^ yy[0];
        zz[1] = xx[1] ^ yy[1];
        zz[2] = xx[2] ^ yy[2];
        zz[3] = xx[3] ^ yy[3];
    }

    public static void c(long[] x, long[] z) {
        z[0] = x[0] ^ 1;
        z[1] = x[1];
    }

    public static long[] d(BigInteger x) {
        long[] z = Nat128.j(x);
        l(z, 0);
        return z;
    }

    public static void h(long[] x, long[] z) {
        if (!Nat128.p(x)) {
            long[] t0 = Nat128.d();
            long[] t1 = Nat128.d();
            n(x, t0);
            i(t0, x, t0);
            n(t0, t0);
            i(t0, x, t0);
            p(t0, 3, t1);
            i(t1, t0, t1);
            n(t1, t1);
            i(t1, x, t1);
            p(t1, 7, t0);
            i(t0, t1, t0);
            p(t0, 14, t1);
            i(t1, t0, t1);
            p(t1, 28, t0);
            i(t0, t1, t0);
            p(t0, 56, t1);
            i(t1, t0, t1);
            n(t1, z);
            return;
        }
        throw new IllegalStateException();
    }

    public static void i(long[] x, long[] y, long[] z) {
        long[] tt = Nat128.f();
        e(x, y, tt);
        k(tt, z);
    }

    public static void j(long[] x, long[] y, long[] zz) {
        long[] tt = Nat128.f();
        e(x, y, tt);
        b(zz, tt, zz);
    }

    public static void k(long[] xx, long[] z) {
        long x0 = xx[0];
        long x1 = xx[1];
        long x2 = xx[2];
        long x3 = xx[3];
        long x22 = x2 ^ ((x3 >>> 49) ^ (x3 >>> 40));
        long x12 = (x1 ^ ((x3 << 15) ^ (x3 << 24))) ^ ((x22 >>> 49) ^ (x22 >>> 40));
        long t = x12 >>> 49;
        z[0] = ((x0 ^ ((x22 << 15) ^ (x22 << 24))) ^ t) ^ (t << 9);
        z[1] = 562949953421311L & x12;
    }

    public static void l(long[] z, int zOff) {
        long z1 = z[zOff + 1];
        long t = z1 >>> 49;
        z[zOff] = z[zOff] ^ ((t << 9) ^ t);
        z[zOff + 1] = 562949953421311L & z1;
    }

    public static void m(long[] x, long[] z) {
        long u0 = Interleave.e(x[0]);
        long u1 = Interleave.e(x[1]);
        long c0 = (u0 >>> 32) | (-4294967296L & u1);
        z[0] = ((c0 << 57) ^ ((4294967295L & u0) | (u1 << 32))) ^ (c0 << 5);
        z[1] = (c0 >>> 7) ^ (c0 >>> 59);
    }

    public static void n(long[] x, long[] z) {
        long[] tt = Nat128.f();
        g(x, tt);
        k(tt, z);
    }

    public static void o(long[] x, long[] zz) {
        long[] tt = Nat128.f();
        g(x, tt);
        b(zz, tt, zz);
    }

    public static void p(long[] x, int n, long[] z) {
        long[] tt = Nat128.f();
        g(x, tt);
        k(tt, z);
        while (true) {
            n--;
            if (n > 0) {
                g(z, tt);
                k(tt, z);
            } else {
                return;
            }
        }
    }

    protected static void e(long[] x, long[] y, long[] zz) {
        long f0 = x[0];
        long f1 = ((f0 >>> 57) ^ (x[1] << 7)) & 144115188075855871L;
        long f02 = f0 & 144115188075855871L;
        long g0 = y[0];
        long g1 = ((g0 >>> 57) ^ (y[1] << 7)) & 144115188075855871L;
        long g02 = g0 & 144115188075855871L;
        long[] H = new long[6];
        long[] jArr = H;
        f(f02, g02, jArr, 0);
        f(f1, g1, jArr, 2);
        f(f02 ^ f1, g02 ^ g1, H, 4);
        long r = H[1] ^ H[2];
        long z0 = H[0];
        long z3 = H[3];
        long z1 = (H[4] ^ z0) ^ r;
        long z2 = (H[5] ^ z3) ^ r;
        zz[0] = z0 ^ (z1 << 57);
        zz[1] = (z1 >>> 7) ^ (z2 << 50);
        zz[2] = (z2 >>> 14) ^ (z3 << 43);
        zz[3] = z3 >>> 21;
    }

    protected static void f(long x, long y, long[] z, int zOff) {
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
        long l = u[((int) j) & 7];
        int k = 48;
        do {
            int j2 = (int) (j >>> k);
            long g = (u[j2 & 7] ^ (u[(j2 >>> 3) & 7] << 3)) ^ (u[(j2 >>> 6) & 7] << 6);
            l ^= g << k;
            h ^= g >>> (-k);
            k -= 9;
        } while (k > 0);
        z[zOff] = 144115188075855871L & l;
        z[zOff + 1] = ((h ^ (((72198606942111744L & j) & ((y << 7) >> 63)) >>> 8)) << 7) ^ (l >>> 57);
    }

    protected static void g(long[] x, long[] zz) {
        Interleave.c(x[0], zz, 0);
        Interleave.c(x[1], zz, 2);
    }
}
