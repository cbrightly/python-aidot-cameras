package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class ECPrivateKeyParameters extends ECKeyParameters {
    BigInteger f;

    public ECPrivateKeyParameters(BigInteger d, ECDomainParameters params) {
        super(true, params);
        this.f = d;
    }

    public BigInteger c() {
        return this.f;
    }
}
