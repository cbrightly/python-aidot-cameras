package org.spongycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;

public class ElGamalParameterSpec implements AlgorithmParameterSpec {
    private BigInteger a;
    private BigInteger b;

    public ElGamalParameterSpec(BigInteger p, BigInteger g) {
        this.a = p;
        this.b = g;
    }

    public BigInteger b() {
        return this.a;
    }

    public BigInteger a() {
        return this.b;
    }
}
