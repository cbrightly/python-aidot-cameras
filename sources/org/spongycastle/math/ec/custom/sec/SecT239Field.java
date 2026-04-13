package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Interleave;
import org.spongycastle.math.raw.Nat256;

public class SecT239Field {
    public static void a(long[] x, long[] y, long[] z) {
        z[0] = x[0] ^ y[0];
        z[1] = x[1] ^ y[1];
        z[2] = x[2] ^ y[2];
        z[3] = x[3] ^ y[3];
    }

    public static void b(long[] xx, long[] yy, long[] zz) {
        zz[0] = xx[0] ^ yy[0];
        zz[1] = xx[1] ^ yy[1];
        zz[2] = xx[2] ^ yy[2];
        zz[3] = xx[3] ^ yy[3];
        zz[4] = xx[4] ^ yy[4];
        zz[5] = xx[5] ^ yy[5];
        zz[6] = xx[6] ^ yy[6];
        zz[7] = xx[7] ^ yy[7];
    }

    public static void c(long[] x, long[] z) {
        z[0] = x[0] ^ 1;
        z[1] = x[1];
        z[2] = x[2];
        z[3] = x[3];
    }

    public static long[] d(BigInteger x) {
        long[] z = Nat256.n(x);
        n(z, 0);
        return z;
    }

    public static void j(long[] x, long[] z) {
        if (!Nat256.u(x)) {
            long[] t0 = Nat256.g();
            long[] t1 = Nat256.g();
            p(x, t0);
            k(t0, x, t0);
            p(t0, t0);
            k(t0, x, t0);
            r(t0, 3, t1);
            k(t1, t0, t1);
            p(t1, t1);
            k(t1, x, t1);
            r(t1, 7, t0);
            k(t0, t1, t0);
            r(t0, 14, t1);
            k(t1, t0, t1);
            p(t1, t1);
            k(t1, x, t1);
            r(t1, 29, t0);
            k(t0, t1, t0);
            p(t0, t0);
            k(t0, x, t0);
            r(t0, 59, t1);
            k(t1, t0, t1);
            p(t1, t1);
            k(t1, x, t1);
            r(t1, 119, t0);
            k(t0, t1, t0);
            p(t0, z);
            return;
        }
        throw new IllegalStateException();
    }

    public static void k(long[] x, long[] y, long[] z) {
        long[] tt = Nat256.i();
        g(x, y, tt);
        m(tt, z);
    }

    public static void l(long[] x, long[] y, long[] zz) {
        long[] tt = Nat256.i();
        g(x, y, tt);
        b(zz, tt, zz);
    }

    public static void m(long[] xx, long[] z) {
        long x0 = xx[0];
        long x1 = xx[1];
        long x2 = xx[2];
        long x3 = xx[3];
        long x4 = xx[4];
        long x5 = xx[5];
        long x6 = xx[6];
        long x7 = xx[7];
        long x62 = x6 ^ (x7 >>> 17);
        long x52 = (x5 ^ (x7 << 47)) ^ (x62 >>> 17);
        long x42 = ((x4 ^ (x7 >>> 47)) ^ (x62 << 47)) ^ (x52 >>> 17);
        long x32 = (((x3 ^ (x7 << 17)) ^ (x62 >>> 47)) ^ (x52 << 47)) ^ (x42 >>> 17);
        long t = x32 >>> 47;
        z[0] = (x0 ^ (x42 << 17)) ^ t;
        z[1] = (x1 ^ (x52 << 17)) ^ (x42 >>> 47);
        z[2] = (((x2 ^ (x62 << 17)) ^ (x52 >>> 47)) ^ (x42 << 47)) ^ (t << 30);
        z[3] = x32 & 140737488355327L;
    }

    public static void n(long[] z, int zOff) {
        long z3 = z[zOff + 3];
        long t = z3 >>> 47;
        z[zOff] = z[zOff] ^ t;
        int i = zOff + 2;
        z[i] = z[i] ^ (t << 30);
        z[zOff + 3] = 140737488355327L & z3;
    }

    public static void o(long[] x, long[] z) {
        long[] jArr = z;
        long u0 = Interleave.e(x[0]);
        long u1 = Interleave.e(x[1]);
        long e0 = (u0 & 4294967295L) | (u1 << 32);
        long c0 = (u0 >>> 32) | (u1 & -4294967296L);
        long u02 = Interleave.e(x[2]);
        long u12 = Interleave.e(x[3]);
        long e1 = (4294967295L & u02) | (u12 << 32);
        long c1 = (u02 >>> 32) | (-4294967296L & u12);
        long c3 = c1 >>> 49;
        long c2 = (c0 >>> 49) | (c1 << 15);
        long c12 = c1 ^ (c0 << 15);
        long[] tt = Nat256.i();
        int[] shifts = {39, 120};
        int i = 0;
        while (true) {
            long u03 = u02;
            if (i < shifts.length) {
                int w = shifts[i] >>> 6;
                int s = shifts[i] & 63;
                tt[w] = tt[w] ^ (c0 << s);
                int i2 = w + 1;
                int[] shifts2 = shifts;
                tt[i2] = tt[i2] ^ ((c12 << s) | (c0 >>> (-s)));
                int i3 = w + 2;
                tt[i3] = tt[i3] ^ ((c2 << s) | (c12 >>> (-s)));
                int i4 = w + 3;
                tt[i4] = tt[i4] ^ ((c3 << s) | (c2 >>> (-s)));
                int i5 = w + 4;
                tt[i5] = tt[i5] ^ (c3 >>> (-s));
                i++;
                u02 = u03;
                c0 = c0;
                shifts = shifts2;
                u12 = u12;
            } else {
                m(tt, jArr);
                jArr[0] = jArr[0] ^ e0;
                jArr[1] = jArr[1] ^ e1;
                return;
            }
        }
    }

    public static void p(long[] x, long[] z) {
        long[] tt = Nat256.i();
        i(x, tt);
        m(tt, z);
    }

    public static void q(long[] x, long[] zz) {
        long[] tt = Nat256.i();
        i(x, tt);
        b(zz, tt, zz);
    }

    public static void r(long[] x, int n, long[] z) {
        long[] tt = Nat256.i();
        i(x, tt);
        m(tt, z);
        while (true) {
            n--;
            if (n > 0) {
                i(z, tt);
                m(tt, z);
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
        long z6 = zz[6];
        long z7 = zz[7];
        zz[0] = z0 ^ (z1 << 60);
        zz[1] = (z1 >>> 4) ^ (z2 << 56);
        zz[2] = (z2 >>> 8) ^ (z3 << 52);
        zz[3] = (z3 >>> 12) ^ (z4 << 48);
        zz[4] = (z4 >>> 16) ^ (z5 << 44);
        zz[5] = (z5 >>> 20) ^ (z6 << 40);
        zz[6] = (z6 >>> 24) ^ (z7 << 36);
        zz[7] = z7 >>> 28;
    }

    protected static void f(long[] x, long[] z) {
        long x0 = x[0];
        long x1 = x[1];
        long x2 = x[2];
        long x3 = x[3];
        z[0] = x0 & 1152921504606846975L;
        z[1] = ((x0 >>> 60) ^ (x1 << 4)) & 1152921504606846975L;
        z[2] = 1152921504606846975L & ((x1 >>> 56) ^ (x2 << 8));
        z[3] = (x2 >>> 52) ^ (x3 << 12);
    }

    protected static void g(long[] x, long[] y, long[] zz) {
        long[] f = new long[4];
        long[] g = new long[4];
        f(x, f);
        f(y, g);
        long[] jArr = zz;
        h(f[0], g[0], jArr, 0);
        h(f[1], g[1], jArr, 1);
        h(f[2], g[2], jArr, 2);
        h(f[3], g[3], jArr, 3);
        for (int i = 5; i > 0; i--) {
            zz[i] = zz[i] ^ zz[i - 1];
        }
        h(f[0] ^ f[1], g[0] ^ g[1], zz, 1);
        h(f[2] ^ f[3], g[2] ^ g[3], zz, 3);
        for (int i2 = 7; i2 > 1; i2--) {
            zz[i2] = zz[i2] ^ zz[i2 - 2];
        }
        long c0 = f[0] ^ f[2];
        long c1 = f[1] ^ f[3];
        long d0 = g[0] ^ g[2];
        long d1 = g[1] ^ g[3];
        h(c0 ^ c1, d0 ^ d1, zz, 3);
        long[] t = new long[3];
        long[] jArr2 = t;
        h(c0, d0, jArr2, 0);
        h(c1, d1, jArr2, 1);
        long t0 = t[0];
        long t1 = t[1];
        long t2 = t[2];
        zz[2] = zz[2] ^ t0;
        zz[3] = zz[3] ^ (t0 ^ t1);
        zz[4] = zz[4] ^ (t2 ^ t1);
        zz[5] = zz[5] ^ t2;
        e(zz);
    }

    protected static void h(long x, long y, long[] z, int zOff) {
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
        long l = u[j2 & 7] ^ (u[(j2 >>> 3) & 7] << 3);
        int k = 54;
        do {
            int j3 = (int) (j >>> k);
            long g = u[j3 & 7] ^ (u[(j3 >>> 3) & 7] << 3);
            l ^= g << k;
            h ^= g >>> (-k);
            k -= 6;
        } while (k > 0);
        z[zOff] = z[zOff] ^ (1152921504606846975L & l);
        int i = zOff + 1;
        z[i] = z[i] ^ ((l >>> 60) ^ (((((585610922974906400L & j) & ((y << 4) >> 63)) >>> 5) ^ h) << 4));
    }

    protected static void i(long[] x, long[] zz) {
        Interleave.c(x[0], zz, 0);
        Interleave.c(x[1], zz, 2);
        Interleave.c(x[2], zz, 4);
        long x3 = x[3];
        zz[6] = Interleave.b((int) x3);
        zz[7] = ((long) Interleave.a((int) (x3 >>> 32))) & 4294967295L;
    }
}
