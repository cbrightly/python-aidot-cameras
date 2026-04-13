package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

public class SecP160R2Curve extends ECCurve.AbstractFp {
    public static final BigInteger i = new BigInteger(1, Hex.a("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFAC73"));
    protected SecP160R2Point j = new SecP160R2Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecP160R2Curve() {
        super(i);
        this.b = m(new BigInteger(1, Hex.a("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFAC70")));
        this.c = m(new BigInteger(1, Hex.a("B4E134D3FB59EB8BAB57274904664D5AF50388BA")));
        this.d = new BigInteger(1, Hex.a("0100000000000000000000351EE786A818F3A1A16B"));
        this.e = BigInteger.valueOf(1);
        this.f = 2;
    }

    /* access modifiers changed from: protected */
    public ECCurve c() {
        return new SecP160R2Curve();
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
        return new SecP160R2FieldElement(x);
    }

    /* access modifiers changed from: protected */
    public ECPoint h(ECFieldElement x, ECFieldElement y, boolean withCompression) {
        return new SecP160R2Point(this, x, y, withCompression);
    }

    /* access modifiers changed from: protected */
    public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        return new SecP160R2Point(this, x, y, zs, withCompression);
    }

    public ECPoint u() {
        return this.j;
    }
}
