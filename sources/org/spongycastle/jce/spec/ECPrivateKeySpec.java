package org.spongycastle.jce.spec;

import java.math.BigInteger;

public class ECPrivateKeySpec extends ECKeySpec {
    private BigInteger d;

    public ECPrivateKeySpec(BigInteger d2, ECParameterSpec spec) {
        super(spec);
        this.d = d2;
    }

    public BigInteger b() {
        return this.d;
    }
}
