package org.spongycastle.math.ec.custom.sec;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.math.BigInteger;
import org.spongycastle.math.raw.Interleave;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat320;

public class SecT283Field {
    private static final long[] a = {878416384462358536L, 3513665537849438403L, -9076969306111048948L, 585610922974906400L, 34087042};

    public static void a(long[] x, long[] y, long[] z) {
        z[0] = x[0] ^ y[0];
        z[1] = x[1] ^ y[1];
        z[2] = x[2] ^ y[2];
        z[3] = x[3] ^ y[3];
        z[4] = x[4] ^ y[4];
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
        zz[8] = xx[8] ^ yy[8];
    }

    public static void c(long[] x, long[] z) {
        z[0] = x[0] ^ 1;
        z[1] = x[1];
        z[2] = x[2];
        z[3] = x[3];
        z[4] = x[4];
    }

    public static long[] d(BigInteger x) {
        long[] z = Nat320.d(x);
        n(z, 0);
        return z;
    }

    public static void j(long[] x, long[] z) {
        if (!Nat320.f(x)) {
            long[] t0 = Nat320.a();
            long[] t1 = Nat320.a();
            p(x, t0);
            k(t0, x, t0);
            r(t0, 2, t1);
            k(t1, t0, t1);
            r(t1, 4, t0);
            k(t0, t1, t0);
            r(t0, 8, t1);
            k(t1, t0, t1);
            p(t1, t1);
            k(t1, x, t1);
            r(t1, 17, t0);
            k(t0, t1, t0);
            p(t0, t0);
            k(t0, x, t0);
            r(t0, 35, t1);
            k(t1, t0, t1);
            r(t1, 70, t0);
            k(t0, t1, t0);
            p(t0, t0);
            k(t0, x, t0);
            r(t0, NeedPermissionEvent.PER_ANDROID_NOTIFICATION, t1);
            k(t1, t0, t1);
            p(t1, z);
            return;
        }
        throw new IllegalStateException();
    }

    public static void k(long[] x, long[] y, long[] z) {
        long[] tt = Nat320.b();
        g(x, y, tt);
        m(tt, z);
    }

    public static void l(long[] x, long[] y, long[] zz) {
        long[] tt = Nat320.b();
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
        long x8 = xx[8];
        long x42 = x4 ^ ((((x8 >>> 27) ^ (x8 >>> 22)) ^ (x8 >>> 20)) ^ (x8 >>> 15));
        long t = x42 >>> 27;
        z[0] = ((((x0 ^ ((((x5 << 37) ^ (x5 << 42)) ^ (x5 << 44)) ^ (x5 << 49))) ^ t) ^ (t << 5)) ^ (t << 7)) ^ (t << 12);
        z[1] = (x1 ^ ((((x6 << 37) ^ (x6 << 42)) ^ (x6 << 44)) ^ (x6 << 49))) ^ ((((x5 >>> 27) ^ (x5 >>> 22)) ^ (x5 >>> 20)) ^ (x5 >>> 15));
        z[2] = (x2 ^ ((((x7 << 37) ^ (x7 << 42)) ^ (x7 << 44)) ^ (x7 << 49))) ^ ((((x6 >>> 27) ^ (x6 >>> 22)) ^ (x6 >>> 20)) ^ (x6 >>> 15));
        z[3] = (x3 ^ ((((x8 << 37) ^ (x8 << 42)) ^ (x8 << 44)) ^ (x8 << 49))) ^ ((((x7 >>> 27) ^ (x7 >>> 22)) ^ (x7 >>> 20)) ^ (x7 >>> 15));
        z[4] = x42 & 134217727;
    }

    public static void n(long[] z, int zOff) {
        long z4 = z[zOff + 4];
        long t = z4 >>> 27;
        z[zOff] = z[zOff] ^ ((((t << 5) ^ t) ^ (t << 7)) ^ (t << 12));
        z[zOff + 4] = 134217727 & z4;
    }

    public static void o(long[] x, long[] z) {
        long[] jArr = z;
        long[] odd = Nat320.a();
        long u0 = Interleave.e(x[0]);
        long u1 = Interleave.e(x[1]);
        long e0 = (u0 & 4294967295L) | (u1 << 32);
        odd[0] = (u0 >>> 32) | (u1 & -4294967296L);
        long u02 = Interleave.e(x[2]);
        long u12 = Interleave.e(x[3]);
        long e1 = (u02 & 4294967295L) | (u12 << 32);
        odd[1] = (u02 >>> 32) | (u12 & -4294967296L);
        long u03 = Interleave.e(x[4]);
        odd[2] = u03 >>> 32;
        k(odd, a, jArr);
        jArr[0] = jArr[0] ^ e0;
        jArr[1] = jArr[1] ^ e1;
        jArr[2] = jArr[2] ^ (4294967295L & u03);
    }

    public static void p(long[] x, long[] z) {
        long[] tt = Nat.j(9);
        i(x, tt);
        m(tt, z);
    }

    public static void q(long[] x, long[] zz) {
        long[] tt = Nat.j(9);
        i(x, tt);
        b(zz, tt, zz);
    }

    public static void r(long[] x, int n, long[] z) {
        long[] tt = Nat.j(9);
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
        long z8 = zz[8];
        long z9 = zz[9];
        zz[0] = z0 ^ (z1 << 57);
        zz[1] = (z1 >>> 7) ^ (z2 << 50);
        zz[2] = (z2 >>> 14) ^ (z3 << 43);
        zz[3] = (z3 >>> 21) ^ (z4 << 36);
        zz[4] = (z4 >>> 28) ^ (z5 << 29);
        zz[5] = (z5 >>> 35) ^ (z6 << 22);
        zz[6] = (z6 >>> 42) ^ (z7 << 15);
        zz[7] = (z7 >>> 49) ^ (z8 << 8);
        zz[8] = (z8 >>> 56) ^ (z9 << 1);
        zz[9] = z9 >>> 63;
    }

    protected static void f(long[] x, long[] z) {
        long x0 = x[0];
        long x1 = x[1];
        long x2 = x[2];
        long x3 = x[3];
        long x4 = x[4];
        z[0] = x0 & 144115188075855871L;
        z[1] = ((x0 >>> 57) ^ (x1 << 7)) & 144115188075855871L;
        z[2] = ((x1 >>> 50) ^ (x2 << 14)) & 144115188075855871L;
        z[3] = ((x2 >>> 43) ^ (x3 << 21)) & 144115188075855871L;
        z[4] = (x3 >>> 36) ^ (x4 << 28);
    }

    protected static void g(long[] x, long[] y, long[] zz) {
        long[] a2 = new long[5];
        long[] b = new long[5];
        f(x, a2);
        f(y, b);
        long[] p = new long[26];
        long[] jArr = p;
        h(a2[0], b[0], jArr, 0);
        h(a2[1], b[1], jArr, 2);
        h(a2[2], b[2], jArr, 4);
        h(a2[3], b[3], jArr, 6);
        h(a2[4], b[4], jArr, 8);
        long u0 = a2[0] ^ a2[1];
        long v0 = b[0] ^ b[1];
        long u1 = a2[0] ^ a2[2];
        long v1 = b[0] ^ b[2];
        long u2 = a2[2] ^ a2[4];
        long v2 = b[2] ^ b[4];
        long u3 = a2[3] ^ a2[4];
        long v3 = b[3] ^ b[4];
        h(u1 ^ a2[3], v1 ^ b[3], jArr, 18);
        h(u2 ^ a2[1], v2 ^ b[1], jArr, 20);
        long A4 = u0 ^ u3;
        long B4 = v0 ^ v3;
        long A5 = A4 ^ a2[2];
        long B5 = B4 ^ b[2];
        h(A4, B4, jArr, 22);
        h(A5, B5, jArr, 24);
        h(u0, v0, jArr, 10);
        h(u1, v1, jArr, 12);
        h(u2, v2, jArr, 14);
        h(u3, v3, jArr, 16);
        zz[0] = p[0];
        zz[9] = p[9];
        long t1 = p[0] ^ p[1];
        long t2 = p[2] ^ t1;
        long t3 = t2 ^ p[10];
        zz[1] = t3;
        long t4 = p[3] ^ p[4];
        long t7 = t2 ^ (t4 ^ (p[11] ^ p[12]));
        zz[2] = t7;
        long t9 = p[5] ^ p[6];
        long t11 = ((t1 ^ t4) ^ t9) ^ p[8];
        long t12 = p[13] ^ p[14];
        zz[3] = (t11 ^ t12) ^ ((p[18] ^ p[22]) ^ p[24]);
        long t18 = (p[7] ^ p[8]) ^ p[9];
        long t19 = t18 ^ p[17];
        zz[8] = t19;
        long t22 = (t18 ^ t9) ^ (p[15] ^ p[16]);
        zz[7] = t22;
        long t24 = p[19] ^ p[20];
        long t25 = p[25] ^ p[24];
        long t27 = t24 ^ t25;
        zz[4] = (t27 ^ (p[18] ^ p[23])) ^ (t22 ^ t3);
        zz[5] = (t27 ^ (t7 ^ t19)) ^ (p[21] ^ p[22]);
        zz[6] = (((((t11 ^ p[0]) ^ p[9]) ^ t12) ^ p[21]) ^ p[23]) ^ p[25];
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

    protected static void i(long[] x, long[] zz) {
        for (int i = 0; i < 4; i++) {
            Interleave.c(x[i], zz, i << 1);
        }
        zz[8] = Interleave.b((int) x[4]);
    }
}
