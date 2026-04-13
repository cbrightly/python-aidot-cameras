package org.spongycastle.math.ec;

import java.math.BigInteger;

public class ReferenceMultiplier extends AbstractECMultiplier {
    /* access modifiers changed from: protected */
    public ECPoint b(ECPoint p, BigInteger k) {
        return ECAlgorithms.n(p, k);
    }
}
