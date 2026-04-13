package org.spongycastle.math.ec;

import java.math.BigInteger;

public class NafR2LMultiplier extends AbstractECMultiplier {
    /* access modifiers changed from: protected */
    public ECPoint b(ECPoint p, BigInteger k) {
        int[] naf = WNafUtil.a(k);
        ECPoint R0 = p.i().u();
        ECPoint R1 = p;
        int zeroes = 0;
        for (int ni : naf) {
            int digit = ni >> 16;
            R1 = R1.G(zeroes + (65535 & ni));
            R0 = R0.a(digit < 0 ? R1.x() : R1);
            zeroes = 1;
        }
        return R0;
    }
}
