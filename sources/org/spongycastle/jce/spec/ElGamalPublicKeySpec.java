package org.spongycastle.jce.spec;

import java.math.BigInteger;

public class ElGamalPublicKeySpec extends ElGamalKeySpec {
    private BigInteger d;

    public BigInteger b() {
        return this.d;
    }
}
