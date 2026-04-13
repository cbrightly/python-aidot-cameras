package org.spongycastle.crypto.params;

import org.spongycastle.math.ec.ECPoint;

public class ECPublicKeyParameters extends ECKeyParameters {
    private final ECPoint f;

    public ECPublicKeyParameters(ECPoint Q, ECDomainParameters params) {
        super(false, params);
        this.f = d(Q);
    }

    private ECPoint d(ECPoint q) {
        if (q == null) {
            throw new IllegalArgumentException("point has null value");
        } else if (!q.t()) {
            ECPoint q2 = q.y();
            if (q2.v()) {
                return q2;
            }
            throw new IllegalArgumentException("point not on curve");
        } else {
            throw new IllegalArgumentException("point at infinity");
        }
    }

    public ECPoint c() {
        return this.f;
    }
}
