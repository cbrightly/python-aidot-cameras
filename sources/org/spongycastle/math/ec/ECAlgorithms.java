package org.spongycastle.math.ec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.endo.ECEndomorphism;
import org.spongycastle.math.ec.endo.GLVEndomorphism;
import org.spongycastle.math.field.FiniteField;
import org.spongycastle.math.field.PolynomialExtensionField;

public class ECAlgorithms {
    public static boolean i(ECCurve c) {
        return j(c.s());
    }

    public static boolean j(FiniteField field) {
        return field.a() > 1 && field.b().equals(ECConstants.c) && (field instanceof PolynomialExtensionField);
    }

    public static boolean k(ECCurve c) {
        return l(c.s());
    }

    public static boolean l(FiniteField field) {
        return field.a() == 1;
    }

    public static ECPoint o(ECPoint P, BigInteger a, ECPoint Q, BigInteger b) {
        ECCurve cp = P.i();
        ECPoint Q2 = h(cp, Q);
        if ((cp instanceof ECCurve.AbstractF2m) && ((ECCurve.AbstractF2m) cp).I()) {
            return p(P.w(a).a(Q2.w(b)));
        }
        ECEndomorphism endomorphism = cp.r();
        if (!(endomorphism instanceof GLVEndomorphism)) {
            return p(a(P, a, Q2, b));
        }
        return p(g(new ECPoint[]{P, Q2}, new BigInteger[]{a, b}, (GLVEndomorphism) endomorphism));
    }

    public static ECPoint h(ECCurve c, ECPoint p) {
        if (c.l(p.i())) {
            return c.y(p);
        }
        throw new IllegalArgumentException("Point must be on the same curve");
    }

    public static void m(ECFieldElement[] zs, int off, int len, ECFieldElement scale) {
        ECFieldElement[] c = new ECFieldElement[len];
        c[0] = zs[off];
        int i = 0;
        while (true) {
            i++;
            if (i >= len) {
                break;
            }
            c[i] = c[i - 1].j(zs[off + i]);
        }
        int j = i - 1;
        if (scale != null) {
            c[j] = c[j].j(scale);
        }
        ECFieldElement u = c[j].g();
        while (j > 0) {
            int i2 = j - 1;
            int j2 = j + off;
            ECFieldElement tmp = zs[j2];
            zs[j2] = c[i2].j(u);
            u = u.j(tmp);
            j = i2;
        }
        zs[off] = u;
    }

    public static ECPoint n(ECPoint p, BigInteger k) {
        BigInteger x = k.abs();
        ECPoint q = p.i().u();
        int t = x.bitLength();
        if (t > 0) {
            if (x.testBit(0)) {
                q = p;
            }
            for (int i = 1; i < t; i++) {
                p = p.H();
                if (x.testBit(i)) {
                    q = q.a(p);
                }
            }
        }
        return k.signum() < 0 ? q.x() : q;
    }

    public static ECPoint p(ECPoint p) {
        if (p.v()) {
            return p;
        }
        throw new IllegalArgumentException("Invalid point");
    }

    static ECPoint a(ECPoint P, BigInteger k, ECPoint Q, BigInteger l) {
        boolean negL = false;
        boolean negK = k.signum() < 0;
        if (l.signum() < 0) {
            negL = true;
        }
        BigInteger k2 = k.abs();
        BigInteger l2 = l.abs();
        int widthP = Math.max(2, Math.min(16, WNafUtil.h(k2.bitLength())));
        int widthQ = Math.max(2, Math.min(16, WNafUtil.h(l2.bitLength())));
        WNafPreCompInfo infoP = WNafUtil.k(P, widthP, true);
        WNafPreCompInfo infoQ = WNafUtil.k(Q, widthQ, true);
        return c(negK ? infoP.b() : infoP.a(), negK ? infoP.a() : infoP.b(), WNafUtil.d(widthP, k2), negL ? infoQ.b() : infoQ.a(), negL ? infoQ.a() : infoQ.b(), WNafUtil.d(widthQ, l2));
    }

    static ECPoint b(ECPoint P, BigInteger k, ECPointMap pointMapQ, BigInteger l) {
        boolean negL = false;
        boolean negK = k.signum() < 0;
        if (l.signum() < 0) {
            negL = true;
        }
        BigInteger k2 = k.abs();
        BigInteger l2 = l.abs();
        int width = Math.max(2, Math.min(16, WNafUtil.h(Math.max(k2.bitLength(), l2.bitLength()))));
        ECPoint Q = WNafUtil.j(P, width, true, pointMapQ);
        WNafPreCompInfo infoP = WNafUtil.f(P);
        WNafPreCompInfo infoQ = WNafUtil.f(Q);
        return c(negK ? infoP.b() : infoP.a(), negK ? infoP.a() : infoP.b(), WNafUtil.d(width, k2), negL ? infoQ.b() : infoQ.a(), negL ? infoQ.a() : infoQ.b(), WNafUtil.d(width, l2));
    }

    private static ECPoint c(ECPoint[] preCompP, ECPoint[] preCompNegP, byte[] wnafP, ECPoint[] preCompQ, ECPoint[] preCompNegQ, byte[] wnafQ) {
        byte[] bArr = wnafP;
        byte[] bArr2 = wnafQ;
        int len = Math.max(bArr.length, bArr2.length);
        ECPoint infinity = preCompP[0].i().u();
        ECPoint R = infinity;
        int zeroes = 0;
        int i = len - 1;
        while (i >= 0) {
            int wiP = i < bArr.length ? bArr[i] : 0;
            int wiQ = i < bArr2.length ? bArr2[i] : 0;
            if ((wiP | wiQ) == 0) {
                zeroes++;
            } else {
                ECPoint r = infinity;
                if (wiP != 0) {
                    r = r.a((wiP < 0 ? preCompNegP : preCompP)[Math.abs(wiP) >>> 1]);
                }
                if (wiQ != 0) {
                    r = r.a((wiQ < 0 ? preCompNegQ : preCompQ)[Math.abs(wiQ) >>> 1]);
                }
                if (zeroes > 0) {
                    R = R.G(zeroes);
                    zeroes = 0;
                }
                R = R.I(r);
            }
            i--;
        }
        if (zeroes > 0) {
            return R.G(zeroes);
        }
        return R;
    }

    static ECPoint e(ECPoint[] ps, BigInteger[] ks) {
        int count = ps.length;
        boolean[] negs = new boolean[count];
        WNafPreCompInfo[] infos = new WNafPreCompInfo[count];
        byte[][] wnafs = new byte[count][];
        for (int i = 0; i < count; i++) {
            BigInteger ki = ks[i];
            negs[i] = ki.signum() < 0;
            BigInteger ki2 = ki.abs();
            int width = Math.max(2, Math.min(16, WNafUtil.h(ki2.bitLength())));
            infos[i] = WNafUtil.k(ps[i], width, true);
            wnafs[i] = WNafUtil.d(width, ki2);
        }
        return f(negs, infos, wnafs);
    }

    static ECPoint g(ECPoint[] ps, BigInteger[] ks, GLVEndomorphism glvEndomorphism) {
        BigInteger n = ps[0].i().w();
        BigInteger[] abs = new BigInteger[(len << 1)];
        int j = 0;
        for (int i = 0; i < len; i++) {
            BigInteger[] ab = glvEndomorphism.c(ks[i].mod(n));
            int j2 = j + 1;
            abs[j] = ab[0];
            j = j2 + 1;
            abs[j2] = ab[1];
        }
        ECPointMap pointMap = glvEndomorphism.b();
        if (glvEndomorphism.a()) {
            return d(ps, pointMap, abs);
        }
        ECPoint[] pqs = new ECPoint[(len << 1)];
        int j3 = 0;
        for (ECPoint p : ps) {
            ECPoint q = pointMap.a(p);
            int j4 = j3 + 1;
            pqs[j3] = p;
            j3 = j4 + 1;
            pqs[j4] = q;
        }
        return e(pqs, abs);
    }

    static ECPoint d(ECPoint[] ps, ECPointMap pointMap, BigInteger[] ks) {
        ECPoint[] eCPointArr = ps;
        int halfCount = eCPointArr.length;
        int fullCount = halfCount << 1;
        boolean[] negs = new boolean[fullCount];
        WNafPreCompInfo[] infos = new WNafPreCompInfo[fullCount];
        byte[][] wnafs = new byte[fullCount][];
        for (int i = 0; i < halfCount; i++) {
            int j0 = i << 1;
            int j1 = j0 + 1;
            BigInteger kj0 = ks[j0];
            boolean z = false;
            negs[j0] = kj0.signum() < 0;
            BigInteger kj02 = kj0.abs();
            BigInteger kj1 = ks[j1];
            if (kj1.signum() < 0) {
                z = true;
            }
            negs[j1] = z;
            BigInteger kj12 = kj1.abs();
            int width = Math.max(2, Math.min(16, WNafUtil.h(Math.max(kj02.bitLength(), kj12.bitLength()))));
            ECPoint P = eCPointArr[i];
            ECPoint Q = WNafUtil.j(P, width, true, pointMap);
            infos[j0] = WNafUtil.f(P);
            infos[j1] = WNafUtil.f(Q);
            wnafs[j0] = WNafUtil.d(width, kj02);
            wnafs[j1] = WNafUtil.d(width, kj12);
        }
        ECPointMap eCPointMap = pointMap;
        return f(negs, infos, wnafs);
    }

    private static ECPoint f(boolean[] negs, WNafPreCompInfo[] infos, byte[][] wnafs) {
        byte[][] bArr = wnafs;
        int len = 0;
        for (byte[] length : bArr) {
            len = Math.max(len, length.length);
        }
        int i = 0;
        ECPoint infinity = infos[0].a()[0].i().u();
        ECPoint R = infinity;
        int zeroes = 0;
        int i2 = len - 1;
        while (i2 >= 0) {
            ECPoint r = infinity;
            int j = 0;
            while (j < count) {
                byte[] wnaf = bArr[j];
                int wi = i2 < wnaf.length ? wnaf[i2] : i;
                if (wi != 0) {
                    int n = Math.abs(wi);
                    WNafPreCompInfo info = infos[j];
                    r = r.a(((wi < 0 ? 1 : i) == negs[j] ? info.a() : info.b())[n >>> 1]);
                }
                j++;
                i = 0;
            }
            if (r == infinity) {
                zeroes++;
            } else {
                if (zeroes > 0) {
                    R = R.G(zeroes);
                    zeroes = 0;
                }
                R = R.I(r);
            }
            i2--;
            i = 0;
        }
        if (zeroes > 0) {
            return R.G(zeroes);
        }
        return R;
    }
}
