package org.spongycastle.crypto.params;

import java.math.BigInteger;
import java.util.Vector;

public class NaccacheSternPrivateKeyParameters extends NaccacheSternKeyParameters {
    private BigInteger x;
    private Vector y;

    public NaccacheSternPrivateKeyParameters(BigInteger g, BigInteger n, int lowerSigmaBound, Vector smallPrimes, BigInteger phi_n) {
        super(true, g, n, lowerSigmaBound);
        this.y = smallPrimes;
        this.x = phi_n;
    }

    public BigInteger e() {
        return this.x;
    }

    public Vector f() {
        return this.y;
    }
}
