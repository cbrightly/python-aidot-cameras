package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

public class SecT113R1Curve extends ECCurve.AbstractF2m {
    protected SecT113R1Point j = new SecT113R1Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecT113R1Curve() {
        super(113, 9, 0, 0);
        this.b = m(new BigInteger(1, Hex.a("003088250CA6E7C7FE649CE85820F7")));
        this.c = m(new BigInteger(1, Hex.a("00E8BEE4D3E2260744188BE0E9C723")));
        this.d = new BigInteger(1, Hex.a("0100000000000000D9CCEC8A39E56F"));
        this.e = BigInteger.valueOf(2);
        this.f = 6;
    }

    /* access modifiers changed from: protected */
    public ECCurve c() {
        return new SecT113R1Curve();
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
        return new SecT113R1Point(this, x, y, withCompression);
    }

    /* access modifiers changed from: protected */
    public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        return new SecT113R1Point(this, x, y, zs, withCompression);
    }

    public ECPoint u() {
        return this.j;
    }

    public boolean I() {
        return false;
    }
}
