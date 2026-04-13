package org.spongycastle.jce.spec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;

public class ECNamedCurveParameterSpec extends ECParameterSpec {
    private String f;

    public ECNamedCurveParameterSpec(String name, ECCurve curve, ECPoint G, BigInteger n, BigInteger h, byte[] seed) {
        super(curve, G, n, h, seed);
        this.f = name;
    }

    public String f() {
        return this.f;
    }
}
