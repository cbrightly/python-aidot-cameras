package org.spongycastle.math.ec;

import java.math.BigInteger;

public class DoubleAddMultiplier extends AbstractECMultiplier {
    /* access modifiers changed from: protected */
    public ECPoint b(ECPoint p, BigInteger k) {
        ECPoint[] R = {p.i().u(), p};
        int n = k.bitLength();
        for (int i = 0; i < n; i++) {
            int b = k.testBit(i);
            int bp = 1 - b;
            R[bp] = R[bp].I(R[b]);
        }
        return R[0];
    }
}
