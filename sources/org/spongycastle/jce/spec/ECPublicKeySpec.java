package org.spongycastle.jce.spec;

import org.spongycastle.math.ec.ECPoint;

public class ECPublicKeySpec extends ECKeySpec {
    private ECPoint d;

    public ECPublicKeySpec(ECPoint q, ECParameterSpec spec) {
        super(spec);
        if (q.i() != null) {
            this.d = q.y();
        } else {
            this.d = q;
        }
    }

    public ECPoint b() {
        return this.d;
    }
}
