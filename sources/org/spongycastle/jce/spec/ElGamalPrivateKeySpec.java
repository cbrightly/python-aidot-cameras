package org.spongycastle.jce.spec;

import java.math.BigInteger;

public class ElGamalPrivateKeySpec extends ElGamalKeySpec {
    private BigInteger d;

    public BigInteger b() {
        return this.d;
    }
}
