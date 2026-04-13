package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class ElGamalPrivateKeyParameters extends ElGamalKeyParameters {
    private BigInteger f;

    public ElGamalPrivateKeyParameters(BigInteger x, ElGamalParameters params) {
        super(true, params);
        this.f = x;
    }

    public BigInteger c() {
        return this.f;
    }

    public boolean equals(Object obj) {
        if ((obj instanceof ElGamalPrivateKeyParameters) && ((ElGamalPrivateKeyParameters) obj).c().equals(this.f)) {
            return super.equals(obj);
        }
        return false;
    }

    public int hashCode() {
        return c().hashCode();
    }
}
