package org.spongycastle.math.ec;

import com.tutk.IOTC.AVIOCTRLDEFs;
import java.math.BigInteger;

public abstract class WNafUtil {
    private static final int[] a = {13, 41, 121, 337, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_FORMATEXTSTORAGE_RESP, 2305};
    private static final byte[] b = new byte[0];
    private static final int[] c = new int[0];
    private static final ECPoint[] d = new ECPoint[0];

    public static int[] a(BigInteger k) {
        if ((k.bitLength() >>> 16) != 0) {
            throw new IllegalArgumentException("'k' must have bitlength < 2^16");
        } else if (k.signum() == 0) {
            return c;
        } else {
            BigInteger _3k = k.shiftLeft(1).add(k);
            int bits = _3k.bitLength();
            int[] naf = new int[(bits >> 1)];
            BigInteger diff = _3k.xor(k);
            int highBit = bits - 1;
            int zeroes = 0;
            int zeroes2 = 0;
            int i = 1;
            while (i < highBit) {
                if (!diff.testBit(i)) {
                    zeroes2++;
                } else {
                    naf[zeroes] = ((k.testBit(i) ? -1 : 1) << 16) | zeroes2;
                    i++;
                    zeroes2 = 1;
                    zeroes++;
                }
                i++;
            }
            int length = zeroes + 1;
            naf[zeroes] = 65536 | zeroes2;
            if (naf.length > length) {
                return n(naf, length);
            }
            return naf;
        }
    }

    public static int[] b(int width, BigInteger k) {
        if (width == 2) {
            return a(k);
        }
        if (width < 2 || width > 16) {
            throw new IllegalArgumentException("'width' must be in the range [2, 16]");
        } else if ((k.bitLength() >>> 16) != 0) {
            throw new IllegalArgumentException("'k' must have bitlength < 2^16");
        } else if (k.signum() == 0) {
            return c;
        } else {
            int[] wnaf = new int[((k.bitLength() / width) + 1)];
            int pow2 = 1 << width;
            int mask = pow2 - 1;
            int sign = pow2 >>> 1;
            boolean carry = false;
            int length = 0;
            int pos = 0;
            while (pos <= k.bitLength()) {
                if (k.testBit(pos) == carry) {
                    pos++;
                } else {
                    k = k.shiftRight(pos);
                    int digit = k.intValue() & mask;
                    if (carry) {
                        digit++;
                    }
                    carry = (digit & sign) != 0;
                    if (carry) {
                        digit -= pow2;
                    }
                    wnaf[length] = (digit << 16) | (length > 0 ? pos - 1 : pos);
                    pos = width;
                    length++;
                }
            }
            if (wnaf.length > length) {
                return n(wnaf, length);
            }
            return wnaf;
        }
    }

    public static byte[] c(BigInteger k) {
        if (k.signum() == 0) {
            return b;
        }
        BigInteger _3k = k.shiftLeft(1).add(k);
        int digits = _3k.bitLength() - 1;
        byte[] naf = new byte[digits];
        BigInteger diff = _3k.xor(k);
        int i = 1;
        while (i < digits) {
            if (diff.testBit(i)) {
                naf[i - 1] = (byte) (k.testBit(i) ? -1 : 1);
                i++;
            }
            i++;
        }
        naf[digits - 1] = 1;
        return naf;
    }

    public static byte[] d(int width, BigInteger k) {
        if (width == 2) {
            return c(k);
        }
        if (width < 2 || width > 8) {
            throw new IllegalArgumentException("'width' must be in the range [2, 8]");
        } else if (k.signum() == 0) {
            return b;
        } else {
            byte[] wnaf = new byte[(k.bitLength() + 1)];
            int pow2 = 1 << width;
            int mask = pow2 - 1;
            int sign = pow2 >>> 1;
            boolean carry = false;
            int length = 0;
            int pos = 0;
            while (pos <= k.bitLength()) {
                if (k.testBit(pos) == carry) {
                    pos++;
                } else {
                    k = k.shiftRight(pos);
                    int digit = k.intValue() & mask;
                    if (carry) {
                        digit++;
                    }
                    carry = (digit & sign) != 0;
                    if (carry) {
                        digit -= pow2;
                    }
                    int length2 = length + (length > 0 ? pos - 1 : pos);
                    wnaf[length2] = (byte) digit;
                    pos = width;
                    length = length2 + 1;
                }
            }
            if (wnaf.length > length) {
                return m(wnaf, length);
            }
            return wnaf;
        }
    }

    public static int e(BigInteger k) {
        if (k.signum() == 0) {
            return 0;
        }
        return k.shiftLeft(1).add(k).xor(k).bitCount();
    }

    public static WNafPreCompInfo f(ECPoint p) {
        return g(p.i().x(p, "bc_wnaf"));
    }

    public static WNafPreCompInfo g(PreCompInfo preCompInfo) {
        if (preCompInfo == null || !(preCompInfo instanceof WNafPreCompInfo)) {
            return new WNafPreCompInfo();
        }
        return (WNafPreCompInfo) preCompInfo;
    }

    public static int h(int bits) {
        return i(bits, a);
    }

    public static int i(int bits, int[] windowSizeCutoffs) {
        int w = 0;
        while (w < windowSizeCutoffs.length && bits >= windowSizeCutoffs[w]) {
            w++;
        }
        return w + 2;
    }

    public static ECPoint j(ECPoint p, int width, boolean includeNegated, ECPointMap pointMap) {
        ECCurve c2 = p.i();
        WNafPreCompInfo wnafPreCompP = k(p, width, includeNegated);
        ECPoint q = pointMap.a(p);
        WNafPreCompInfo wnafPreCompQ = g(c2.x(q, "bc_wnaf"));
        ECPoint twiceP = wnafPreCompP.c();
        if (twiceP != null) {
            wnafPreCompQ.f(pointMap.a(twiceP));
        }
        ECPoint[] preCompP = wnafPreCompP.a();
        ECPoint[] preCompQ = new ECPoint[preCompP.length];
        for (int i = 0; i < preCompP.length; i++) {
            preCompQ[i] = pointMap.a(preCompP[i]);
        }
        wnafPreCompQ.d(preCompQ);
        if (includeNegated) {
            ECPoint[] preCompNegQ = new ECPoint[preCompQ.length];
            for (int i2 = 0; i2 < preCompNegQ.length; i2++) {
                preCompNegQ[i2] = preCompQ[i2].x();
            }
            wnafPreCompQ.e(preCompNegQ);
        }
        c2.C(q, "bc_wnaf", wnafPreCompQ);
        return q;
    }

    public static WNafPreCompInfo k(ECPoint p, int width, boolean includeNegated) {
        int pos;
        ECPoint eCPoint = p;
        ECCurve c2 = p.i();
        WNafPreCompInfo wnafPreCompInfo = g(c2.x(p, "bc_wnaf"));
        int iniPreCompLen = 0;
        int reqPreCompLen = 1 << Math.max(0, width - 2);
        ECPoint[] preComp = wnafPreCompInfo.a();
        if (preComp == null) {
            preComp = d;
        } else {
            iniPreCompLen = preComp.length;
        }
        if (iniPreCompLen < reqPreCompLen) {
            preComp = l(preComp, reqPreCompLen);
            if (reqPreCompLen == 1) {
                preComp[0] = p.y();
            } else {
                int curPreCompLen = iniPreCompLen;
                if (curPreCompLen == 0) {
                    preComp[0] = eCPoint;
                    curPreCompLen = 1;
                }
                ECFieldElement iso = null;
                if (reqPreCompLen == 2) {
                    preComp[1] = p.F();
                } else {
                    ECPoint twiceP = wnafPreCompInfo.c();
                    ECPoint last = preComp[curPreCompLen - 1];
                    if (twiceP == null) {
                        twiceP = preComp[0].H();
                        wnafPreCompInfo.f(twiceP);
                        if (!twiceP.t() && ECAlgorithms.k(c2) && c2.t() >= 64) {
                            switch (c2.q()) {
                                case 2:
                                case 3:
                                case 4:
                                    iso = twiceP.s(0);
                                    twiceP = c2.f(twiceP.q().t(), twiceP.r().t());
                                    ECFieldElement iso2 = iso.o();
                                    last = last.C(iso2).D(iso2.j(iso));
                                    if (iniPreCompLen == 0) {
                                        preComp[0] = last;
                                        break;
                                    }
                                    break;
                            }
                        }
                    }
                    while (curPreCompLen < reqPreCompLen) {
                        ECPoint a2 = last.a(twiceP);
                        last = a2;
                        preComp[curPreCompLen] = a2;
                        curPreCompLen++;
                    }
                }
                c2.B(preComp, iniPreCompLen, reqPreCompLen - iniPreCompLen, iso);
            }
        }
        wnafPreCompInfo.d(preComp);
        if (includeNegated) {
            ECPoint[] preCompNeg = wnafPreCompInfo.b();
            if (preCompNeg == null) {
                pos = 0;
                preCompNeg = new ECPoint[reqPreCompLen];
            } else {
                pos = preCompNeg.length;
                if (pos < reqPreCompLen) {
                    preCompNeg = l(preCompNeg, reqPreCompLen);
                }
            }
            while (pos < reqPreCompLen) {
                preCompNeg[pos] = preComp[pos].x();
                pos++;
            }
            wnafPreCompInfo.e(preCompNeg);
        }
        c2.C(p, "bc_wnaf", wnafPreCompInfo);
        return wnafPreCompInfo;
    }

    private static byte[] m(byte[] a2, int length) {
        byte[] result = new byte[length];
        System.arraycopy(a2, 0, result, 0, result.length);
        return result;
    }

    private static int[] n(int[] a2, int length) {
        int[] result = new int[length];
        System.arraycopy(a2, 0, result, 0, result.length);
        return result;
    }

    private static ECPoint[] l(ECPoint[] a2, int length) {
        ECPoint[] result = new ECPoint[length];
        System.arraycopy(a2, 0, result, 0, a2.length);
        return result;
    }
}
