package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class DSAPublicKeyParameters extends DSAKeyParameters {
    private static final BigInteger f = BigInteger.valueOf(1);
    private static final BigInteger q = BigInteger.valueOf(2);
    private BigInteger x;

    public DSAPublicKeyParameters(BigInteger y, DSAParameters params) {
        super(false, params);
        this.x = d(y, params);
    }

    private BigInteger d(BigInteger y, DSAParameters params) {
        if (params == null) {
            return y;
        }
        BigInteger bigInteger = q;
        if (bigInteger.compareTo(y) <= 0 && params.b().subtract(bigInteger).compareTo(y) >= 0 && f.equals(y.modPow(params.c(), params.b()))) {
            return y;
        }
        throw new IllegalArgumentException("y value does not appear to be in correct group");
    }

    public BigInteger c() {
        return this.x;
    }
}
