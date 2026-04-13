package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

public class SecT131R2Curve extends ECCurve.AbstractF2m {
    protected SecT131R2Point j = new SecT131R2Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecT131R2Curve() {
        super(131, 2, 3, 8);
        this.b = m(new BigInteger(1, Hex.a("03E5A88919D7CAFCBF415F07C2176573B2")));
        this.c = m(new BigInteger(1, Hex.a("04B8266A46C55657AC734CE38F018F2192")));
        this.d = new BigInteger(1, Hex.a("0400000000000000016954A233049BA98F"));
        this.e = BigInteger.valueOf(2);
        this.f = 6;
    }

    /* access modifiers changed from: protected */
    public ECCurve c() {
        return new SecT131R2Curve();
    }

    public boolean D(int coord) {
        switch (coord) {
            case 6:
                return true;
            default:
                return false;
        }
    }

    public int t() {
        return 131;
    }

    public ECFieldElement m(BigInteger x) {
        return new SecT131FieldElement(x);
    }

    /* access modifiers changed from: protected */
    public ECPoint h(ECFieldElement x, ECFieldElement y, boolean withCompression) {
        return new SecT131R2Point(this, x, y, withCompression);
    }

    /* access modifiers changed from: protected */
    public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        return new SecT131R2Point(this, x, y, zs, withCompression);
    }

    public ECPoint u() {
        return this.j;
    }

    public boolean I() {
        return false;
    }
}
