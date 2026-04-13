package org.spongycastle.math.ec;

import java.math.BigInteger;
import org.spongycastle.math.ec.endo.GLVEndomorphism;

public class GLVMultiplier extends AbstractECMultiplier {
    protected final ECCurve a;
    protected final GLVEndomorphism b;

    public GLVMultiplier(ECCurve curve, GLVEndomorphism glvEndomorphism) {
        if (curve == null || curve.w() == null) {
            throw new IllegalArgumentException("Need curve with known group order");
        }
        this.a = curve;
        this.b = glvEndomorphism;
    }

    /* access modifiers changed from: protected */
    public ECPoint b(ECPoint p, BigInteger k) {
        if (this.a.l(p.i())) {
            BigInteger[] ab = this.b.c(k.mod(p.i().w()));
            BigInteger a2 = ab[0];
            BigInteger b2 = ab[1];
            ECPointMap pointMap = this.b.b();
            if (this.b.a()) {
                return ECAlgorithms.b(p, a2, pointMap, b2);
            }
            return ECAlgorithms.a(p, a2, pointMap.a(p), b2);
        }
        throw new IllegalStateException();
    }
}
