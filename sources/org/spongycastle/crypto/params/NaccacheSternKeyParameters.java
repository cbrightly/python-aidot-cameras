package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class NaccacheSternKeyParameters extends AsymmetricKeyParameter {
    private BigInteger d;
    private BigInteger f;
    int q;

    public NaccacheSternKeyParameters(boolean privateKey, BigInteger g, BigInteger n, int lowerSigmaBound) {
        super(privateKey);
        this.d = g;
        this.f = n;
        this.q = lowerSigmaBound;
    }

    public BigInteger b() {
        return this.d;
    }

    public int c() {
        return this.q;
    }

    public BigInteger d() {
        return this.f;
    }
}
