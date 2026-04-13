package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

public class SecT131R1Curve extends ECCurve.AbstractF2m {
    protected SecT131R1Point j = new SecT131R1Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecT131R1Curve() {
        super(131, 2, 3, 8);
        this.b = m(new BigInteger(1, Hex.a("07A11B09A76B562144418FF3FF8C2570B8")));
        this.c = m(new BigInteger(1, Hex.a("0217C05610884B63B9C6C7291678F9D341")));
        this.d = new BigInteger(1, Hex.a("0400000000000000023123953A9464B54D"));
        this.e = BigInteger.valueOf(2);
        this.f = 6;
    }

    /* access modifiers changed from: protected */
    public ECCurve c() {
        return new SecT131R1Curve();
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
        return new SecT131R1Point(this, x, y, withCompression);
    }

    /* access modifiers changed from: protected */
    public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        return new SecT131R1Point(this, x, y, zs, withCompression);
    }

    public ECPoint u() {
        return this.j;
    }

    public boolean I() {
        return false;
    }
}
