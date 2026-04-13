package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

public class SecP192R1Curve extends ECCurve.AbstractFp {
    public static final BigInteger i = new BigInteger(1, Hex.a("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF"));
    protected SecP192R1Point j = new SecP192R1Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecP192R1Curve() {
        super(i);
        this.b = m(new BigInteger(1, Hex.a("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFC")));
        this.c = m(new BigInteger(1, Hex.a("64210519E59C80E70FA7E9AB72243049FEB8DEECC146B9B1")));
        this.d = new BigInteger(1, Hex.a("FFFFFFFFFFFFFFFFFFFFFFFF99DEF836146BC9B1B4D22831"));
        this.e = BigInteger.valueOf(1);
        this.f = 2;
    }

    /* access modifiers changed from: protected */
    public ECCurve c() {
        return new SecP192R1Curve();
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
        return new SecP192R1FieldElement(x);
    }

    /* access modifiers changed from: protected */
    public ECPoint h(ECFieldElement x, ECFieldElement y, boolean withCompression) {
        return new SecP192R1Point(this, x, y, withCompression);
    }

    /* access modifiers changed from: protected */
    public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        return new SecP192R1Point(this, x, y, zs, withCompression);
    }

    public ECPoint u() {
        return this.j;
    }
}
