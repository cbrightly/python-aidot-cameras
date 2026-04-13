package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Interleave;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat576;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class SecT571Field {
    private static final long[] a = {3161836309350906777L, -7642453882179322845L, -3821226941089661423L, 7312758566309945096L, -556661012383879292L, 8945041530681231562L, -4750851271514160027L, 6847946401097695794L, 541669439031730457L};

    public static void b(long[] x, long[] y, long[] z) {
        for (int i = 0; i < 9; i++) {
            z[i] = x[i] ^ y[i];
        }
    }

    private static void a(long[] x, int xOff, long[] y, int yOff, long[] z, int zOff) {
        for (int i = 0; i < 9; i++) {
            z[zOff + i] = x[xOff + i] ^ y[yOff + i];
        }
    }

    public static void d(long[] x, long[] y, long[] z) {
        for (int i = 0; i < 9; i++) {
            z[i] = z[i] ^ (x[i] ^ y[i]);
        }
    }

    private static void c(long[] x, int xOff, long[] y, int yOff, long[] z, int zOff) {
        for (int i = 0; i < 9; i++) {
            int i2 = zOff + i;
            z[i2] = z[i2] ^ (x[xOff + i] ^ y[yOff + i]);
        }
    }

    public static void e(long[] xx, long[] yy, long[] zz) {
        for (int i = 0; i < 18; i++) {
            zz[i] = xx[i] ^ yy[i];
        }
    }

    public static void f(long[] x, long[] z) {
        z[0] = x[0] ^ 1;
        for (int i = 1; i < 9; i++) {
            z[i] = x[i];
        }
    }

    public static long[] g(BigInteger x) {
        long[] z = Nat576.d(x);
        r(z, 0);
        return z;
    }

    public static void k(long[] x, long[] z) {
        if (!Nat576.f(x)) {
            long[] t0 = Nat576.a();
            long[] t1 = Nat576.a();
            long[] t2 = Nat576.a();
            t(x, t2);
            t(t2, t0);
            t(t0, t1);
            l(t0, t1, t0);
            v(t0, 2, t1);
            l(t0, t1, t0);
            l(t0, t2, t0);
            v(t0, 5, t1);
            l(t0, t1, t0);
            v(t1, 5, t1);
            l(t0, t1, t0);
            v(t0, 15, t1);
            l(t0, t1, t2);
            v(t2, 30, t0);
            v(t0, 30, t1);
            l(t0, t1, t0);
            v(t0, 60, t1);
            l(t0, t1, t0);
            v(t1, 60, t1);
            l(t0, t1, t0);
            v(t0, 180, t1);
            l(t0, t1, t0);
            v(t1, 180, t1);
            l(t0, t1, t0);
            l(t0, t2, z);
            return;
        }
        throw new IllegalStateException();
    }

    public static void l(long[] x, long[] y, long[] z) {
        long[] tt = Nat576.b();
        h(x, y, tt);
        q(tt, z);
    }

    public static void m(long[] x, long[] y, long[] zz) {
        long[] tt = Nat576.b();
        h(x, y, tt);
        e(zz, tt, zz);
    }

    public static void n(long[] x, long[] precomp, long[] z) {
        long[] tt = Nat576.b();
        i(x, precomp, tt);
        q(tt, z);
    }

    public static void o(long[] x, long[] precomp, long[] zz) {
        long[] tt = Nat576.b();
        i(x, precomp, tt);
        e(zz, tt, zz);
    }

    public static long[] p(long[] x) {
        long[] t = new long[(IjkMediaMeta.FF_PROFILE_H264_HIGH_444 << 1)];
        System.arraycopy(x, 0, t, 9, 9);
        int tOff = 0;
        for (int i = 7; i > 0; i--) {
            tOff += 18;
            Nat.E(9, t, tOff >>> 1, 0, t, tOff);
            r(t, tOff);
            a(t, 9, t, tOff, t, tOff + 9);
        }
        Nat.I(IjkMediaMeta.FF_PROFILE_H264_HIGH_444, t, 0, 4, 0, t, IjkMediaMeta.FF_PROFILE_H264_HIGH_444);
        return t;
    }

    public static void q(long[] xx, long[] z) {
        long xx09 = xx[9];
        long u = xx[17];
        long xx092 = ((((u >>> 59) ^ xx09) ^ (u >>> 57)) ^ (u >>> 54)) ^ (u >>> 49);
        long v = (((xx[8] ^ (u << 5)) ^ (u << 7)) ^ (u << 10)) ^ (u << 15);
        for (int i = 16; i >= 10; i--) {
            long u2 = xx[i];
            z[i - 8] = (((v ^ (u2 >>> 59)) ^ (u2 >>> 57)) ^ (u2 >>> 54)) ^ (u2 >>> 49);
            v = (((xx[i - 9] ^ (u2 << 5)) ^ (u2 << 7)) ^ (u2 << 10)) ^ (u2 << 15);
        }
        long u3 = xx092;
        z[1] = (((v ^ (u3 >>> 59)) ^ (u3 >>> 57)) ^ (u3 >>> 54)) ^ (u3 >>> 49);
        long v2 = (((xx[0] ^ (u3 << 5)) ^ (u3 << 7)) ^ (u3 << 10)) ^ (u3 << 15);
        long x08 = z[8];
        long t = x08 >>> 59;
        z[0] = (((v2 ^ t) ^ (t << 2)) ^ (t << 5)) ^ (t << 10);
        z[8] = 576460752303423487L & x08;
    }

    public static void r(long[] z, int zOff) {
        long z8 = z[zOff + 8];
        long t = z8 >>> 59;
        z[zOff] = z[zOff] ^ ((((t << 2) ^ t) ^ (t << 5)) ^ (t << 10));
        z[zOff + 8] = 576460752303423487L & z8;
    }

    public static void s(long[] x, long[] z) {
        long[] evn = Nat576.a();
        long[] odd = Nat576.a();
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            int pos2 = pos + 1;
            long u0 = Interleave.e(x[pos]);
            pos = pos2 + 1;
            long u1 = Interleave.e(x[pos2]);
            evn[i] = (4294967295L & u0) | (u1 << 32);
            odd[i] = (u0 >>> 32) | (-4294967296L & u1);
        }
        long u02 = Interleave.e(x[pos]);
        evn[4] = u02 & 4294967295L;
        odd[4] = u02 >>> 32;
        l(odd, a, z);
        b(z, evn, z);
    }

    public static void t(long[] x, long[] z) {
        long[] tt = Nat576.b();
        j(x, tt);
        q(tt, z);
    }

    public static void u(long[] x, long[] zz) {
        long[] tt = Nat576.b();
        j(x, tt);
        e(zz, tt, zz);
    }

    public static void v(long[] x, int n, long[] z) {
        long[] tt = Nat576.b();
        j(x, tt);
        q(tt, z);
        while (true) {
            n--;
            if (n > 0) {
                j(z, tt);
                q(tt, z);
            } else {
                return;
            }
        }
    }

    protected static void h(long[] x, long[] y, long[] zz) {
        i(x, p(y), zz);
    }

    protected static void i(long[] x, long[] precomp, long[] zz) {
        for (int k = 56; k >= 0; k -= 8) {
            for (int j = 1; j < 9; j += 2) {
                int aVal = (int) (x[j] >>> k);
                c(precomp, (aVal & 15) * 9, precomp, (((aVal >>> 4) & 15) + 16) * 9, zz, j - 1);
            }
            Nat.H(16, zz, 0, 8, 0);
        }
        for (int k2 = 56; k2 >= 0; k2 -= 8) {
            for (int j2 = 0; j2 < 9; j2 += 2) {
                int aVal2 = (int) (x[j2] >>> k2);
                c(precomp, (aVal2 & 15) * 9, precomp, (((aVal2 >>> 4) & 15) + 16) * 9, zz, j2);
            }
            if (k2 > 0) {
                Nat.H(18, zz, 0, 8, 0);
            }
        }
    }

    protected static void j(long[] x, long[] zz) {
        for (int i = 0; i < 9; i++) {
            Interleave.c(x[i], zz, i << 1);
        }
    }
}
