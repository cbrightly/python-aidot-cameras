package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class GOST3410PublicKeyParameters extends GOST3410KeyParameters {
    private BigInteger f;

    public GOST3410PublicKeyParameters(BigInteger y, GOST3410Parameters params) {
        super(false, params);
        this.f = y;
    }

    public BigInteger c() {
        return this.f;
    }
}
