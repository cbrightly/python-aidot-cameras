package org.spongycastle.math.ec.custom.sec;

import com.alibaba.fastjson.asm.Opcodes;
import java.math.BigInteger;
import org.spongycastle.math.raw.Interleave;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat448;

public class SecT409Field {
    public static void a(long[] x, long[] y, long[] z) {
        z[0] = x[0] ^ y[0];
        z[1] = x[1] ^ y[1];
        z[2] = x[2] ^ y[2];
        z[3] = x[3] ^ y[3];
        z[4] = x[4] ^ y[4];
        z[5] = x[5] ^ y[5];
        z[6] = x[6] ^ y[6];
    }

    public static void b(long[] xx, long[] yy, long[] zz) {
        for (int i = 0; i < 13; i++) {
            zz[i] = xx[i] ^ yy[i];
        }
    }

    public static void c(long[] x, long[] z) {
        z[0] = x[0] ^ 1;
        z[1] = x[1];
        z[2] = x[2];
        z[3] = x[3];
        z[4] = x[4];
        z[5] = x[5];
        z[6] = x[6];
    }

    public static long[] d(BigInteger x) {
        long[] z = Nat448.d(x);
        n(z, 0);
        return z;
    }

    public static void j(long[] x, long[] z) {
        if (!Nat448.f(x)) {
            long[] t0 = Nat448.a();
            long[] t1 = Nat448.a();
            long[] t2 = Nat448.a();
            p(x, t0);
            r(t0, 1, t1);
            k(t0, t1, t0);
            r(t1, 1, t1);
            k(t0, t1, t0);
            r(t0, 3, t1);
            k(t0, t1, t0);
            r(t0, 6, t1);
            k(t0, t1, t0);
            r(t0, 12, t1);
            k(t0, t1, t2);
            r(t2, 24, t0);
            r(t0, 24, t1);
            k(t0, t1, t0);
            r(t0, 48, t1);
            k(t0, t1, t0);
            r(t0, 96, t1);
            k(t0, t1, t0);
            r(t0, Opcodes.CHECKCAST, t1);
            k(t0, t1, t0);
            k(t0, t2, z);
            return;
        }
        throw new IllegalStateException();
    }

    public static void k(long[] x, long[] y, long[] z) {
        long[] tt = Nat448.b();
        g(x, y, tt);
        m(tt, z);
    }

    public static void l(long[] x, long[] y, long[] zz) {
        long[] tt = Nat448.b();
        g(x, y, tt);
        b(zz, tt, zz);
    }

    public static void m(long[] xx, long[] z) {
        long x00 = xx[0];
        long x01 = xx[1];
        long x02 = xx[2];
        long x03 = xx[3];
        long x04 = xx[4];
        long x05 = xx[5];
        long x06 = xx[6];
        long x07 = xx[7];
        long u = xx[12];
        long x062 = x06 ^ ((u >>> 25) ^ (u << 62));
        long x072 = x07 ^ (u >>> 2);
        long u2 = xx[11];
        long x042 = x04 ^ (u2 << 39);
        long x052 = (x05 ^ (u << 39)) ^ ((u2 >>> 25) ^ (u2 << 62));
        long x063 = x062 ^ (u2 >>> 2);
        long u3 = xx[10];
        long x032 = x03 ^ (u3 << 39);
        long x043 = x042 ^ ((u3 >>> 25) ^ (u3 << 62));
        long x053 = x052 ^ (u3 >>> 2);
        long u4 = xx[9];
        long x022 = x02 ^ (u4 << 39);
        long x033 = x032 ^ ((u4 >>> 25) ^ (u4 << 62));
        long x044 = x043 ^ (u4 >>> 2);
        long u5 = xx[8];
        long x012 = x01 ^ (u5 << 39);
        long x023 = x022 ^ ((u5 >>> 25) ^ (u5 << 62));
        long x034 = x033 ^ (u5 >>> 2);
        long u6 = x072;
        long t = x063 >>> 25;
        z[0] = (x00 ^ (u6 << 39)) ^ t;
        z[1] = (x012 ^ ((u6 >>> 25) ^ (u6 << 62))) ^ (t << 23);
        z[2] = x023 ^ (u6 >>> 2);
        z[3] = x034;
        z[4] = x044;
        z[5] = x053;
        z[6] = x063 & 33554431;
    }

    public static void n(long[] z, int zOff) {
        long z6 = z[zOff + 6];
        long t = z6 >>> 25;
        z[zOff] = z[zOff] ^ t;
        int i = zOff + 1;
        z[i] = z[i] ^ (t << 23);
        z[zOff + 6] = 33554431 & z6;
    }

    public static void o(long[] x, long[] z) {
        long u0 = Interleave.e(x[0]);
        long u1 = Interleave.e(x[1]);
        long e0 = (u0 & 4294967295L) | (u1 << 32);
        long c0 = (u0 >>> 32) | (u1 & -4294967296L);
        long u02 = Interleave.e(x[2]);
        long u12 = Interleave.e(x[3]);
        long e1 = (u02 & 4294967295L) | (u12 << 32);
        long c1 = (u02 >>> 32) | (u12 & -4294967296L);
        long u03 = Interleave.e(x[4]);
        long u13 = Interleave.e(x[5]);
        long e2 = (u03 & 4294967295L) | (u13 << 32);
        long c2 = (u03 >>> 32) | (-4294967296L & u13);
        long u04 = Interleave.e(x[6]);
        long c3 = u04 >>> 32;
        z[0] = e0 ^ (c0 << 44);
        z[1] = (e1 ^ (c1 << 44)) ^ (c0 >>> 20);
        z[2] = (e2 ^ (c2 << 44)) ^ (c1 >>> 20);
        z[3] = (((4294967295L & u04) ^ (c3 << 44)) ^ (c2 >>> 20)) ^ (c0 << 13);
        z[4] = ((c3 >>> 20) ^ (c1 << 13)) ^ (c0 >>> 51);
        z[5] = (c2 << 13) ^ (c1 >>> 51);
        z[6] = (c3 << 13) ^ (c2 >>> 51);
    }

    public static void p(long[] x, long[] z) {
        long[] tt = Nat.j(13);
        i(x, tt);
        m(tt, z);
    }

    public static void q(long[] x, long[] zz) {
        long[] tt = Nat.j(13);
        i(x, tt);
        b(zz, tt, zz);
    }

    public static void r(long[] x, int n, long[] z) {
        long[] tt = Nat.j(13);
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
        long z00 = zz[0];
        long z01 = zz[1];
        long z02 = zz[2];
        long z03 = zz[3];
        long z04 = zz[4];
        long z05 = zz[5];
        long z06 = zz[6];
        long z07 = zz[7];
        long z08 = zz[8];
        long z09 = zz[9];
        long z10 = zz[10];
        long z11 = zz[11];
        long z12 = zz[12];
        long z13 = zz[13];
        zz[0] = z00 ^ (z01 << 59);
        zz[1] = (z01 >>> 5) ^ (z02 << 54);
        zz[2] = (z02 >>> 10) ^ (z03 << 49);
        zz[3] = (z03 >>> 15) ^ (z04 << 44);
        zz[4] = (z04 >>> 20) ^ (z05 << 39);
        zz[5] = (z05 >>> 25) ^ (z06 << 34);
        zz[6] = (z06 >>> 30) ^ (z07 << 29);
        zz[7] = (z07 >>> 35) ^ (z08 << 24);
        zz[8] = (z08 >>> 40) ^ (z09 << 19);
        zz[9] = (z09 >>> 45) ^ (z10 << 14);
        zz[10] = (z10 >>> 50) ^ (z11 << 9);
        zz[11] = ((z11 >>> 55) ^ (z12 << 4)) ^ (z13 << 63);
        zz[12] = (z12 >>> 60) ^ (z13 >>> 1);
        zz[13] = 0;
    }

    protected static void f(long[] x, long[] z) {
        long x0 = x[0];
        long x1 = x[1];
        long x2 = x[2];
        long x3 = x[3];
        long x4 = x[4];
        long x5 = x[5];
        long x6 = x[6];
        z[0] = x0 & 576460752303423487L;
        z[1] = ((x0 >>> 59) ^ (x1 << 5)) & 576460752303423487L;
        z[2] = ((x1 >>> 54) ^ (x2 << 10)) & 576460752303423487L;
        z[3] = ((x2 >>> 49) ^ (x3 << 15)) & 576460752303423487L;
        z[4] = ((x3 >>> 44) ^ (x4 << 20)) & 576460752303423487L;
        z[5] = ((x4 >>> 39) ^ (x5 << 25)) & 576460752303423487L;
        z[6] = (x5 >>> 34) ^ (x6 << 30);
    }

    protected static void g(long[] x, long[] y, long[] zz) {
        long[] a = new long[7];
        long[] b = new long[7];
        f(x, a);
        f(y, b);
        for (int i = 0; i < 7; i++) {
            h(a, b[i], zz, i);
        }
        e(zz);
    }

    protected static void h(long[] xs, long y, long[] z, int zOff) {
        long[] u = new long[8];
        u[1] = y;
        u[2] = u[1] << 1;
        char c = 3;
        u[3] = u[2] ^ y;
        u[4] = u[2] << 1;
        u[5] = u[4] ^ y;
        u[6] = u[3] << 1;
        u[7] = u[6] ^ y;
        int i = 0;
        while (i < 7) {
            long x = xs[i];
            int j = (int) x;
            long h = 0;
            long l = u[j & 7] ^ (u[(j >>> 3) & 7] << c);
            int k = 54;
            while (true) {
                int j2 = (int) (x >>> k);
                long g = u[j2 & 7] ^ (u[(j2 >>> 3) & 7] << c);
                l ^= g << k;
                h ^= g >>> (-k);
                k -= 6;
                if (k <= 0) {
                    break;
                }
                c = 3;
            }
            int i2 = zOff + i;
            z[i2] = z[i2] ^ (l & 576460752303423487L);
            int i3 = zOff + i + 1;
            z[i3] = z[i3] ^ ((l >>> 59) ^ (h << 5));
            i++;
            c = 3;
        }
    }

    protected static void i(long[] x, long[] zz) {
        for (int i = 0; i < 6; i++) {
            Interleave.c(x[i], zz, i << 1);
        }
        zz[12] = Interleave.b((int) x[6]);
    }
}
