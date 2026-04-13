package org.spongycastle.math.ec.endo;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPointMap;
import org.spongycastle.math.ec.ScaleXPointMap;

public class GLVTypeBEndomorphism implements GLVEndomorphism {
    protected final ECCurve a;
    protected final GLVTypeBParameters b;
    protected final ECPointMap c;

    public GLVTypeBEndomorphism(ECCurve curve, GLVTypeBParameters parameters) {
        this.a = curve;
        this.b = parameters;
        this.c = new ScaleXPointMap(curve.m(parameters.b()));
    }

    public BigInteger[] c(BigInteger k) {
        int bits = this.b.c();
        BigInteger b1 = d(k, this.b.d(), bits);
        BigInteger b2 = d(k, this.b.e(), bits);
        GLVTypeBParameters p = this.b;
        return new BigInteger[]{k.subtract(b1.multiply(p.f()).add(b2.multiply(p.h()))), b1.multiply(p.g()).add(b2.multiply(p.i())).negate()};
    }

    public ECPointMap b() {
        return this.c;
    }

    public boolean a() {
        return true;
    }

    /* access modifiers changed from: protected */
    public BigInteger d(BigInteger k, BigInteger g, int t) {
        boolean negative = g.signum() < 0;
        BigInteger b2 = k.multiply(g.abs());
        boolean extra = b2.testBit(t - 1);
        BigInteger b3 = b2.shiftRight(t);
        if (extra) {
            b3 = b3.add(ECConstants.b);
        }
        return negative ? b3.negate() : b3;
    }
}
