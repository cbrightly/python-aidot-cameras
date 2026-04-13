package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

public class SecP224K1Curve extends ECCurve.AbstractFp {
    public static final BigInteger i = new BigInteger(1, Hex.a("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFE56D"));
    protected SecP224K1Point j = new SecP224K1Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecP224K1Curve() {
        super(i);
        this.b = m(ECConstants.a);
        this.c = m(BigInteger.valueOf(5));
        this.d = new BigInteger(1, Hex.a("010000000000000000000000000001DCE8D2EC6184CAF0A971769FB1F7"));
        this.e = BigInteger.valueOf(1);
        this.f = 2;
    }

    /* access modifiers changed from: protected */
    public ECCurve c() {
        return new SecP224K1Curve();
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
        return new SecP224K1FieldElement(x);
    }

    /* access modifiers changed from: protected */
    public ECPoint h(ECFieldElement x, ECFieldElement y, boolean withCompression) {
        return new SecP224K1Point(this, x, y, withCompression);
    }

    /* access modifiers changed from: protected */
    public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        return new SecP224K1Point(this, x, y, zs, withCompression);
    }

    public ECPoint u() {
        return this.j;
    }
}
