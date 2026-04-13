package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class SRP6GroupParameters {
    private BigInteger a;
    private BigInteger b;

    public SRP6GroupParameters(BigInteger N, BigInteger g) {
        this.a = N;
        this.b = g;
    }

    public BigInteger a() {
        return this.b;
    }

    public BigInteger b() {
        return this.a;
    }
}
