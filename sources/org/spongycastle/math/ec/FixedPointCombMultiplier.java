package org.spongycastle.math.ec;

import java.math.BigInteger;

public class FixedPointCombMultiplier extends AbstractECMultiplier {
    /* access modifiers changed from: protected */
    public ECPoint b(ECPoint p, BigInteger k) {
        ECCurve c = p.i();
        int size = FixedPointUtil.a(c);
        if (k.bitLength() <= size) {
            FixedPointPreCompInfo info = FixedPointUtil.c(p, c(size));
            ECPoint[] lookupTable = info.b();
            int width = info.c();
            int d = ((size + width) - 1) / width;
            ECPoint R = c.u();
            int top = (d * width) - 1;
            for (int i = 0; i < d; i++) {
                int index = 0;
                for (int j = top - i; j >= 0; j -= d) {
                    index <<= 1;
                    if (k.testBit(j)) {
                        index |= 1;
                    }
                }
                R = R.I(lookupTable[index]);
            }
            return R.a(info.a());
        }
        throw new IllegalStateException("fixed-point comb doesn't support scalars larger than the curve order");
    }

    /* access modifiers changed from: protected */
    public int c(int combSize) {
        return combSize > 257 ? 6 : 5;
    }
}
