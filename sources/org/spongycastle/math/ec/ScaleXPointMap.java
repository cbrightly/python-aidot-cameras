package org.spongycastle.math.ec;

public class ScaleXPointMap implements ECPointMap {
    protected final ECFieldElement a;

    public ScaleXPointMap(ECFieldElement scale) {
        this.a = scale;
    }

    public ECPoint a(ECPoint p) {
        return p.C(this.a);
    }
}
