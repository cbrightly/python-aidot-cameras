package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

public class SecT571R1Curve extends ECCurve.AbstractF2m {
    static final SecT571FieldElement j;
    static final SecT571FieldElement k;
    protected SecT571R1Point l = new SecT571R1Point(this, (ECFieldElement) null, (ECFieldElement) null);

    static {
        SecT571FieldElement secT571FieldElement = new SecT571FieldElement(new BigInteger(1, Hex.a("02F40E7E2221F295DE297117B7F3D62F5C6A97FFCB8CEFF1CD6BA8CE4A9A18AD84FFABBD8EFA59332BE7AD6756A66E294AFD185A78FF12AA520E4DE739BACA0C7FFEFF7F2955727A")));
        j = secT571FieldElement;
        k = (SecT571FieldElement) secT571FieldElement.n();
    }

    public SecT571R1Curve() {
        super(571, 2, 5, 10);
        this.b = m(BigInteger.valueOf(1));
        this.c = j;
        this.d = new BigInteger(1, Hex.a("03FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFE661CE18FF55987308059B186823851EC7DD9CA1161DE93D5174D66E8382E9BB2FE84E47"));
        this.e = BigInteger.valueOf(2);
        this.f = 6;
    }

    /* access modifiers changed from: protected */
    public ECCurve c() {
        return new SecT571R1Curve();
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
        return 571;
    }

    public ECFieldElement m(BigInteger x) {
        return new SecT571FieldElement(x);
    }

    /* access modifiers changed from: protected */
    public ECPoint h(ECFieldElement x, ECFieldElement y, boolean withCompression) {
        return new SecT571R1Point(this, x, y, withCompression);
    }

    /* access modifiers changed from: protected */
    public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        return new SecT571R1Point(this, x, y, zs, withCompression);
    }

    public ECPoint u() {
        return this.l;
    }

    public boolean I() {
        return false;
    }
}
