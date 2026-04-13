package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECMultiplier;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.WTauNafMultiplier;
import org.spongycastle.util.encoders.Hex;

public class SecT283K1Curve extends ECCurve.AbstractF2m {
    protected SecT283K1Point j = new SecT283K1Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecT283K1Curve() {
        super(283, 5, 7, 12);
        this.b = m(BigInteger.valueOf(0));
        this.c = m(BigInteger.valueOf(1));
        this.d = new BigInteger(1, Hex.a("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFE9AE2ED07577265DFF7F94451E061E163C61"));
        this.e = BigInteger.valueOf(4);
        this.f = 6;
    }

    /* access modifiers changed from: protected */
    public ECCurve c() {
        return new SecT283K1Curve();
    }

    public boolean D(int coord) {
        switch (coord) {
            case 6:
                return true;
            default:
                return false;
        }
    }

    /* access modifiers changed from: protected */
    public ECMultiplier e() {
        return new WTauNafMultiplier();
    }

    public int t() {
        return 283;
    }

    public ECFieldElement m(BigInteger x) {
        return new SecT283FieldElement(x);
    }

    /* access modifiers changed from: protected */
    public ECPoint h(ECFieldElement x, ECFieldElement y, boolean withCompression) {
        return new SecT283K1Point(this, x, y, withCompression);
    }

    /* access modifiers changed from: protected */
    public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        return new SecT283K1Point(this, x, y, zs, withCompression);
    }

    public ECPoint u() {
        return this.j;
    }

    public boolean I() {
        return true;
    }
}
