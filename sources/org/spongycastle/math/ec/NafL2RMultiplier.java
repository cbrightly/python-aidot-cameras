package org.spongycastle.math.ec;

import java.math.BigInteger;

public class NafL2RMultiplier extends AbstractECMultiplier {
    /* access modifiers changed from: protected */
    public ECPoint b(ECPoint p, BigInteger k) {
        int[] naf = WNafUtil.a(k);
        ECPoint addP = p.y();
        ECPoint subP = addP.x();
        ECPoint R = p.i().u();
        int i = naf.length;
        while (true) {
            i--;
            if (i < 0) {
                return R;
            }
            int ni = naf[i];
            R = R.I((ni >> 16) < 0 ? subP : addP).G(65535 & ni);
        }
    }
}
