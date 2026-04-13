package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

public class SecP256R1Curve extends ECCurve.AbstractFp {
    public static final BigInteger i = new BigInteger(1, Hex.a("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFF"));
    protected SecP256R1Point j = new SecP256R1Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecP256R1Curve() {
        super(i);
        this.b = m(new BigInteger(1, Hex.a("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC")));
        this.c = m(new BigInteger(1, Hex.a("5AC635D8AA3A93E7B3EBBD55769886BC651D06B0CC53B0F63BCE3C3E27D2604B")));
        this.d = new BigInteger(1, Hex.a("FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F3B9CAC2FC632551"));
        this.e = BigInteger.valueOf(1);
        this.f = 2;
    }

    /* access modifiers changed from: protected */
    public ECCurve c() {
        return new SecP256R1Curve();
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
        return new SecP256R1FieldElement(x);
    }

    /* access modifiers changed from: protected */
    public ECPoint h(ECFieldElement x, ECFieldElement y, boolean withCompression) {
        return new SecP256R1Point(this, x, y, withCompression);
    }

    /* access modifiers changed from: protected */
    public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        return new SecP256R1Point(this, x, y, zs, withCompression);
    }

    public ECPoint u() {
        return this.j;
    }
}
