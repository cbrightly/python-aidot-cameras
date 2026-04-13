package org.spongycastle.math.ec;

import java.math.BigInteger;

public abstract class AbstractECMultiplier implements ECMultiplier {
    /* access modifiers changed from: protected */
    public abstract ECPoint b(ECPoint eCPoint, BigInteger bigInteger);

    public ECPoint a(ECPoint p, BigInteger k) {
        int sign = k.signum();
        if (sign == 0 || p.t()) {
            return p.i().u();
        }
        ECPoint positive = b(p, k.abs());
        return ECAlgorithms.p(sign > 0 ? positive : positive.x());
    }
}
