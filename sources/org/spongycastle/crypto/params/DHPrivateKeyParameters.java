package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class DHPrivateKeyParameters extends DHKeyParameters {
    private BigInteger f;

    public DHPrivateKeyParameters(BigInteger x, DHParameters params) {
        super(true, params);
        this.f = x;
    }

    public BigInteger c() {
        return this.f;
    }

    public int hashCode() {
        return this.f.hashCode() ^ super.hashCode();
    }

    public boolean equals(Object obj) {
        if ((obj instanceof DHPrivateKeyParameters) && ((DHPrivateKeyParameters) obj).c().equals(this.f) && super.equals(obj)) {
            return true;
        }
        return false;
    }
}
