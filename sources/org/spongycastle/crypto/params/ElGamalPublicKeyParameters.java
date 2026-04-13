package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class ElGamalPublicKeyParameters extends ElGamalKeyParameters {
    private BigInteger f;

    public ElGamalPublicKeyParameters(BigInteger y, ElGamalParameters params) {
        super(false, params);
        this.f = y;
    }

    public BigInteger c() {
        return this.f;
    }

    public int hashCode() {
        return this.f.hashCode() ^ super.hashCode();
    }

    public boolean equals(Object obj) {
        if ((obj instanceof ElGamalPublicKeyParameters) && ((ElGamalPublicKeyParameters) obj).c().equals(this.f) && super.equals(obj)) {
            return true;
        }
        return false;
    }
}
