package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class DSAPrivateKeyParameters extends DSAKeyParameters {
    private BigInteger f;

    public DSAPrivateKeyParameters(BigInteger x, DSAParameters params) {
        super(true, params);
        this.f = x;
    }

    public BigInteger c() {
        return this.f;
    }
}
