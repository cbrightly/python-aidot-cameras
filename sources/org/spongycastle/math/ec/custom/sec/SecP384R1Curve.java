package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

public class SecP384R1Curve extends ECCurve.AbstractFp {
    public static final BigInteger i = new BigInteger(1, Hex.a("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFF0000000000000000FFFFFFFF"));
    protected SecP384R1Point j = new SecP384R1Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecP384R1Curve() {
        super(i);
        this.b = m(new BigInteger(1, Hex.a("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFF0000000000000000FFFFFFFC")));
        this.c = m(new BigInteger(1, Hex.a("B3312FA7E23EE7E4988E056BE3F82D19181D9C6EFE8141120314088F5013875AC656398D8A2ED19D2A85C8EDD3EC2AEF")));
        this.d = new BigInteger(1, Hex.a("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC7634D81F4372DDF581A0DB248B0A77AECEC196ACCC52973"));
        this.e = BigInteger.valueOf(1);
        this.f = 2;
    }

    /* access modifiers changed from: protected */
    public ECCurve c() {
        return new SecP384R1Curve();
    }

    public boolean D(int coord) {
        switch (coord) {
            case 2:
                return true;
            default:
                return false;
        }
    }

    public int t() {
        return i.bitLength();
    }

    public ECFieldElement m(BigInteger x) {
        return new SecP384R1FieldElement(x);
    }

    /* access modifiers changed from: protected */
    public ECPoint h(ECFieldElement x, ECFieldElement y, boolean withCompression) {
        return new SecP384R1Point(this, x, y, withCompression);
    }

    /* access modifiers changed from: protected */
    public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        return new SecP384R1Point(this, x, y, zs, withCompression);
    }

    public ECPoint u() {
        return this.j;
    }
}
