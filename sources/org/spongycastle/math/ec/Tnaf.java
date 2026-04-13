package org.spongycastle.math.ec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;

public class Tnaf {
    private static final BigInteger a;
    private static final BigInteger b = ECConstants.c.negate();
    private static final BigInteger c;
    public static final ZTauElement[] d;
    public static final byte[][] e = {null, new byte[]{1}, null, new byte[]{-1, 0, 1}, null, new byte[]{1, 0, 1}, null, new byte[]{-1, 0, 0, 1}};
    public static final ZTauElement[] f;
    public static final byte[][] g = {null, new byte[]{1}, null, new byte[]{-1, 0, 1}, null, new byte[]{1, 0, 1}, null, new byte[]{-1, 0, 0, -1}};

    Tnaf() {
    }

    static {
        BigInteger bigInteger = ECConstants.b;
        BigInteger negate = bigInteger.negate();
        a = negate;
        BigInteger negate2 = ECConstants.d.negate();
        c = negate2;
        BigInteger bigInteger2 = ECConstants.a;
        d = new ZTauElement[]{null, new ZTauElement(bigInteger, bigInteger2), null, new ZTauElement(negate2, negate), null, new ZTauElement(negate, negate), null, new ZTauElement(bigInteger, negate), null};
        f = new ZTauElement[]{null, new ZTauElement(bigInteger, bigInteger2), null, new ZTauElement(negate2, bigInteger), null, new ZTauElement(negate, bigInteger), null, new ZTauElement(bigInteger, bigInteger), null};
    }

    public static BigInteger i(byte mu, ZTauElement lambda) {
        BigInteger bigInteger = lambda.a;
        BigInteger s1 = bigInteger.multiply(bigInteger);
        BigInteger s2 = lambda.a.multiply(lambda.b);
        BigInteger bigInteger2 = lambda.b;
        BigInteger s3 = bigInteger2.multiply(bigInteger2).shiftLeft(1);
        if (mu == 1) {
            return s1.add(s2).add(s3);
        }
        if (mu == -1) {
            return s1.subtract(s2).add(s3);
        }
        throw new IllegalArgumentException("mu must be 1 or -1");
    }

    public static ZTauElement k(SimpleBigDecimal lambda0, SimpleBigDecimal lambda1, byte mu) {
        SimpleBigDecimal eta;
        SimpleBigDecimal check2;
        SimpleBigDecimal check1;
        byte b2 = mu;
        int scale = lambda0.f();
        if (lambda1.f() != scale) {
            throw new IllegalArgumentException("lambda0 and lambda1 do not have same scale");
        } else if (b2 == 1 || b2 == -1) {
            BigInteger f0 = lambda0.h();
            BigInteger f1 = lambda1.h();
            SimpleBigDecimal eta0 = lambda0.i(f0);
            SimpleBigDecimal eta1 = lambda1.i(f1);
            SimpleBigDecimal eta2 = eta0.a(eta0);
            if (b2 == 1) {
                eta = eta2.a(eta1);
            } else {
                eta = eta2.j(eta1);
            }
            SimpleBigDecimal threeEta1 = eta1.a(eta1).a(eta1);
            SimpleBigDecimal fourEta1 = threeEta1.a(eta1);
            if (b2 == 1) {
                check1 = eta0.j(threeEta1);
                check2 = eta0.a(fourEta1);
            } else {
                check1 = eta0.a(threeEta1);
                check2 = eta0.j(fourEta1);
            }
            byte h0 = 0;
            byte h1 = 0;
            BigInteger bigInteger = ECConstants.b;
            if (eta.d(bigInteger) >= 0) {
                int i = scale;
                if (check1.d(a) < 0) {
                    h1 = mu;
                } else {
                    h0 = 1;
                }
            } else {
                if (check2.d(ECConstants.c) >= 0) {
                    h1 = mu;
                }
            }
            if (eta.d(a) < 0) {
                if (check1.d(bigInteger) >= 0) {
                    h1 = (byte) (-b2);
                } else {
                    h0 = -1;
                }
            } else if (check2.d(b) < 0) {
                h1 = (byte) (-b2);
            }
            SimpleBigDecimal simpleBigDecimal = check1;
            return new ZTauElement(f0.add(BigInteger.valueOf((long) h0)), f1.add(BigInteger.valueOf((long) h1)));
        } else {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
    }

    public static SimpleBigDecimal a(BigInteger k, BigInteger s, BigInteger vm, byte a2, int m, int c2) {
        int _k = ((m + 5) / 2) + c2;
        BigInteger gs = s.multiply(k.shiftRight(((m - _k) - 2) + a2));
        BigInteger gsPlusJs = gs.add(vm.multiply(gs.shiftRight(m)));
        BigInteger ls = gsPlusJs.shiftRight(_k - c2);
        if (gsPlusJs.testBit((_k - c2) - 1)) {
            ls = ls.add(ECConstants.b);
        }
        return new SimpleBigDecimal(ls, c2);
    }

    public static byte c(int curveA) {
        return (byte) (curveA == 0 ? -1 : 1);
    }

    public static BigInteger[] b(byte mu, int k, boolean doV) {
        BigInteger u1;
        BigInteger u0;
        BigInteger s;
        if (mu == 1 || mu == -1) {
            if (doV) {
                u0 = ECConstants.c;
                u1 = BigInteger.valueOf((long) mu);
            } else {
                u0 = ECConstants.a;
                u1 = ECConstants.b;
            }
            for (int i = 1; i < k; i++) {
                if (mu == 1) {
                    s = u1;
                } else {
                    s = u1.negate();
                }
                u0 = u1;
                u1 = s.subtract(u0.shiftLeft(1));
            }
            return new BigInteger[]{u0, u1};
        }
        throw new IllegalArgumentException("mu must be 1 or -1");
    }

    public static BigInteger g(byte mu, int w) {
        if (w != 4) {
            BigInteger[] us = b(mu, w, false);
            BigInteger twoToW = ECConstants.a.setBit(w);
            return ECConstants.c.multiply(us[0]).multiply(us[1].modInverse(twoToW)).mod(twoToW);
        } else if (mu == 1) {
            return BigInteger.valueOf(6);
        } else {
            return BigInteger.valueOf(10);
        }
    }

    public static BigInteger[] f(ECCurve.AbstractF2m curve) {
        if (curve.I()) {
            int m = curve.t();
            int a2 = curve.n().t().intValue();
            byte mu = c(a2);
            int shifts = e(curve.p());
            BigInteger[] ui = b(mu, (m + 3) - a2, false);
            if (mu == 1) {
                ui[0] = ui[0].negate();
                ui[1] = ui[1].negate();
            }
            BigInteger bigInteger = ECConstants.b;
            return new BigInteger[]{bigInteger.add(ui[1]).shiftRight(shifts), bigInteger.add(ui[0]).shiftRight(shifts).negate()};
        }
        throw new IllegalArgumentException("si is defined for Koblitz curves only");
    }

    protected static int e(BigInteger h) {
        if (h != null) {
            if (h.equals(ECConstants.c)) {
                return 1;
            }
            if (h.equals(ECConstants.e)) {
                return 2;
            }
        }
        throw new IllegalArgumentException("h (Cofactor) must be 2 or 4");
    }

    public static ZTauElement j(BigInteger k, int m, byte a2, BigInteger[] s, byte mu, byte c2) {
        BigInteger d0;
        byte b2 = mu;
        if (b2 == 1) {
            d0 = s[0].add(s[1]);
        } else {
            d0 = s[0].subtract(s[1]);
        }
        int i = m;
        BigInteger bigInteger = k;
        BigInteger bigInteger2 = b(b2, m, true)[1];
        byte b3 = a2;
        int i2 = m;
        byte b4 = c2;
        ZTauElement q = k(a(bigInteger, s[0], bigInteger2, b3, i2, b4), a(bigInteger, s[1], bigInteger2, b3, i2, b4), b2);
        BigInteger bigInteger3 = k;
        return new ZTauElement(k.subtract(d0.multiply(q.a)).subtract(BigInteger.valueOf(2).multiply(s[1]).multiply(q.b)), s[1].multiply(q.a).subtract(s[0].multiply(q.b)));
    }

    /* JADX WARNING: type inference failed for: r7v0, types: [org.spongycastle.math.ec.ECPoint] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.spongycastle.math.ec.ECPoint.AbstractF2m h(org.spongycastle.math.ec.ECPoint.AbstractF2m r8, byte[] r9) {
        /*
            org.spongycastle.math.ec.ECCurve r0 = r8.i()
            org.spongycastle.math.ec.ECPoint r1 = r0.u()
            org.spongycastle.math.ec.ECPoint$AbstractF2m r1 = (org.spongycastle.math.ec.ECPoint.AbstractF2m) r1
            org.spongycastle.math.ec.ECPoint r2 = r8.x()
            org.spongycastle.math.ec.ECPoint$AbstractF2m r2 = (org.spongycastle.math.ec.ECPoint.AbstractF2m) r2
            r3 = 0
            int r4 = r9.length
            int r4 = r4 + -1
        L_0x0014:
            if (r4 < 0) goto L_0x0030
            int r3 = r3 + 1
            byte r5 = r9[r4]
            if (r5 == 0) goto L_0x002d
            org.spongycastle.math.ec.ECPoint$AbstractF2m r1 = r1.J(r3)
            r3 = 0
            if (r5 <= 0) goto L_0x0025
            r6 = r8
            goto L_0x0026
        L_0x0025:
            r6 = r2
        L_0x0026:
            org.spongycastle.math.ec.ECPoint r7 = r1.a(r6)
            r1 = r7
            org.spongycastle.math.ec.ECPoint$AbstractF2m r1 = (org.spongycastle.math.ec.ECPoint.AbstractF2m) r1
        L_0x002d:
            int r4 = r4 + -1
            goto L_0x0014
        L_0x0030:
            if (r3 <= 0) goto L_0x0036
            org.spongycastle.math.ec.ECPoint$AbstractF2m r1 = r1.J(r3)
        L_0x0036:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.math.ec.Tnaf.h(org.spongycastle.math.ec.ECPoint$AbstractF2m, byte[]):org.spongycastle.math.ec.ECPoint$AbstractF2m");
    }

    public static byte[] l(byte mu, ZTauElement lambda, byte width, BigInteger pow2w, BigInteger tw, ZTauElement[] alpha) {
        byte uLocal;
        byte b2 = mu;
        ZTauElement zTauElement = lambda;
        BigInteger bigInteger = pow2w;
        if (b2 == 1 || b2 == -1) {
            int log2Norm = i(mu, lambda).bitLength();
            byte[] u = new byte[(log2Norm > 30 ? log2Norm + 4 + width : width + 34)];
            BigInteger pow2wMin1 = bigInteger.shiftRight(1);
            BigInteger r0 = zTauElement.a;
            BigInteger r1 = zTauElement.b;
            int i = 0;
            while (true) {
                BigInteger bigInteger2 = ECConstants.a;
                if (r0.equals(bigInteger2) && r1.equals(bigInteger2)) {
                    return u;
                }
                if (r0.testBit(0)) {
                    BigInteger uUnMod = r0.add(r1.multiply(tw)).mod(bigInteger);
                    if (uUnMod.compareTo(pow2wMin1) >= 0) {
                        uLocal = (byte) uUnMod.subtract(bigInteger).intValue();
                    } else {
                        uLocal = (byte) uUnMod.intValue();
                    }
                    u[i] = uLocal;
                    boolean s = true;
                    if (uLocal < 0) {
                        s = false;
                        uLocal = (byte) (-uLocal);
                    }
                    if (s) {
                        BigInteger r02 = r0.subtract(alpha[uLocal].a);
                        r1 = r1.subtract(alpha[uLocal].b);
                        r0 = r02;
                    } else {
                        BigInteger r03 = r0.add(alpha[uLocal].a);
                        r1 = r1.add(alpha[uLocal].b);
                        r0 = r03;
                    }
                } else {
                    BigInteger bigInteger3 = tw;
                    u[i] = 0;
                }
                BigInteger t = r0;
                if (b2 == 1) {
                    r0 = r1.add(r0.shiftRight(1));
                } else {
                    r0 = r1.subtract(r0.shiftRight(1));
                }
                r1 = t.shiftRight(1).negate();
                i++;
            }
        } else {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
    }

    public static ECPoint.AbstractF2m[] d(ECPoint.AbstractF2m p, byte a2) {
        byte[][] alphaTnaf = a2 == 0 ? e : g;
        ECPoint.AbstractF2m[] pu = new ECPoint.AbstractF2m[((alphaTnaf.length + 1) >>> 1)];
        pu[0] = p;
        int precompLen = alphaTnaf.length;
        for (int i = 3; i < precompLen; i += 2) {
            pu[i >>> 1] = h(p, alphaTnaf[i]);
        }
        p.i().A(pu);
        return pu;
    }
}
