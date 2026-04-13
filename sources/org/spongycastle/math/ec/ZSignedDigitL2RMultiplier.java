package org.spongycastle.math.ec;

import java.math.BigInteger;

public class ZSignedDigitL2RMultiplier extends AbstractECMultiplier {
    /* access modifiers changed from: protected */
    public ECPoint b(ECPoint p, BigInteger k) {
        ECPoint addP = p.y();
        ECPoint subP = addP.x();
        ECPoint R0 = addP;
        int n = k.bitLength();
        int s = k.getLowestSetBit();
        int i = n;
        while (true) {
            i--;
            if (i <= s) {
                return R0.G(s);
            }
            R0 = R0.I(k.testBit(i) ? addP : subP);
        }
    }
}
