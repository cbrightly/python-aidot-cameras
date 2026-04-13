package org.spongycastle.crypto.prng.drbg;

import org.spongycastle.math.ec.ECPoint;

public class DualECPoints {
    private final ECPoint a;
    private final ECPoint b;
    private final int c;
    private final int d;

    public DualECPoints(int securityStrength, ECPoint p, ECPoint q, int cofactor) {
        if (p.i().l(q.i())) {
            this.c = securityStrength;
            this.a = p;
            this.b = q;
            this.d = cofactor;
            return;
        }
        throw new IllegalArgumentException("points need to be on the same curve");
    }
}
