package org.spongycastle.math.ec;

public class ScaleYPointMap implements ECPointMap {
    protected final ECFieldElement a;

    public ECPoint a(ECPoint p) {
        return p.D(this.a);
    }
}
