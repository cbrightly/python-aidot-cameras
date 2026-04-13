package org.spongycastle.math.ec;

import java.math.BigInteger;

public class MontgomeryLadderMultiplier extends AbstractECMultiplier {
    /* access modifiers changed from: protected */
    public ECPoint b(ECPoint p, BigInteger k) {
        ECPoint[] R = {p.i().u(), p};
        int i = k.bitLength();
        while (true) {
            i--;
            if (i < 0) {
                return R[0];
            }
            int b = k.testBit(i);
            int bp = 1 - b;
            R[bp] = R[bp].a(R[b]);
            R[b] = R[b].H();
        }
    }
}
