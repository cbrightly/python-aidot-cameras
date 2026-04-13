package org.spongycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.field.FiniteField;
import org.spongycastle.math.field.Polynomial;
import org.spongycastle.math.field.PolynomialExtensionField;
import org.spongycastle.util.Arrays;

public class ECNamedCurveSpec extends ECParameterSpec {
    private String a;

    private static EllipticCurve a(ECCurve curve, byte[] seed) {
        return new EllipticCurve(b(curve.s()), curve.n().t(), curve.o().t(), seed);
    }

    private static ECField b(FiniteField field) {
        if (ECAlgorithms.l(field)) {
            return new ECFieldFp(field.b());
        }
        Polynomial poly = ((PolynomialExtensionField) field).c();
        int[] exponents = poly.a();
        return new ECFieldF2m(poly.b(), Arrays.U(Arrays.C(exponents, 1, exponents.length - 1)));
    }

    private static ECPoint c(org.spongycastle.math.ec.ECPoint g) {
        org.spongycastle.math.ec.ECPoint g2 = g.y();
        return new ECPoint(g2.f().t(), g2.g().t());
    }

    public ECNamedCurveSpec(String name, EllipticCurve curve, ECPoint g, BigInteger n, BigInteger h) {
        super(curve, g, n, h.intValue());
        this.a = name;
    }

    public ECNamedCurveSpec(String name, ECCurve curve, org.spongycastle.math.ec.ECPoint g, BigInteger n, BigInteger h, byte[] seed) {
        super(a(curve, seed), c(g), n, h.intValue());
        this.a = name;
    }

    public String d() {
        return this.a;
    }
}
