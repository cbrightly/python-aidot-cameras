package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class GOST3410PrivateKeyParameters extends GOST3410KeyParameters {
    private BigInteger f;

    public GOST3410PrivateKeyParameters(BigInteger x, GOST3410Parameters params) {
        super(true, params);
        this.f = x;
    }

    public BigInteger c() {
        return this.f;
    }
}
