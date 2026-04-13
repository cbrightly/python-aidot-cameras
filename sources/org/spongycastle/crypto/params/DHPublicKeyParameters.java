package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class DHPublicKeyParameters extends DHKeyParameters {
    private static final BigInteger f = BigInteger.valueOf(1);
    private static final BigInteger q = BigInteger.valueOf(2);
    private BigInteger x;

    public DHPublicKeyParameters(BigInteger y, DHParameters params) {
        super(false, params);
        this.x = d(y, params);
    }

    private BigInteger d(BigInteger y, DHParameters dhParams) {
        if (y != null) {
            BigInteger bigInteger = q;
            if (y.compareTo(bigInteger) < 0 || y.compareTo(dhParams.e().subtract(bigInteger)) > 0) {
                throw new IllegalArgumentException("invalid DH public key");
            } else if (dhParams.f() == null || f.equals(y.modPow(dhParams.f(), dhParams.e()))) {
                return y;
            } else {
                throw new IllegalArgumentException("Y value does not appear to be in correct group");
            }
        } else {
            throw new NullPointerException("y value cannot be null");
        }
    }

    public BigInteger c() {
        return this.x;
    }

    public int hashCode() {
        return this.x.hashCode() ^ super.hashCode();
    }

    public boolean equals(Object obj) {
        if ((obj instanceof DHPublicKeyParameters) && ((DHPublicKeyParameters) obj).c().equals(this.x) && super.equals(obj)) {
            return true;
        }
        return false;
    }
}
