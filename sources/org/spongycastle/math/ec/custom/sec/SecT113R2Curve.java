package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

public class SecT113R2Curve extends ECCurve.AbstractF2m {
    protected SecT113R2Point j = new SecT113R2Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecT113R2Curve() {
        super(113, 9, 0, 0);
        this.b = m(new BigInteger(1, Hex.a("00689918DBEC7E5A0DD6DFC0AA55C7")));
        this.c = m(new BigInteger(1, Hex.a("0095E9A9EC9B297BD4BF36E059184F")));
        this.d = new BigInteger(1, Hex.a("010000000000000108789B2496AF93"));
        this.e = BigInteger.valueOf(2);
        this.f = 6;
    }

    /* access modifiers changed from: protected */
    public ECCurve c() {
        return new SecT113R2Curve();
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
        return 113;
    }

    public ECFieldElement m(BigInteger x) {
        return new SecT113FieldElement(x);
    }

    /* access modifiers changed from: protected */
    public ECPoint h(ECFieldElement x, ECFieldElement y, boolean withCompression) {
        return new SecT113R2Point(this, x, y, withCompression);
    }

    /* access modifiers changed from: protected */
    public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        return new SecT113R2Point(this, x, y, zs, withCompression);
    }

    public ECPoint u() {
        return this.j;
    }

    public boolean I() {
        return false;
    }
}
