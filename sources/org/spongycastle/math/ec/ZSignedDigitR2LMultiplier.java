package org.spongycastle.math.ec;

import java.math.BigInteger;

public class ZSignedDigitR2LMultiplier extends AbstractECMultiplier {
    /* access modifiers changed from: protected */
    public ECPoint b(ECPoint p, BigInteger k) {
        ECPoint R0 = p.i().u();
        int n = k.bitLength();
        int s = k.getLowestSetBit();
        ECPoint R1 = p.G(s);
        int i = s;
        while (true) {
            i++;
            if (i >= n) {
                return R0.a(R1);
            }
            R0 = R0.a(k.testBit(i) ? R1 : R1.x());
            R1 = R1.H();
        }
    }
}
