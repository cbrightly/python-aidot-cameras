package org.spongycastle.math.ec.custom.djb;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.raw.Nat256;
import org.spongycastle.util.encoders.Hex;

public class Curve25519 extends ECCurve.AbstractFp {
    public static final BigInteger i = Nat256.H(Curve25519Field.a);
    protected Curve25519Point j = new Curve25519Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public Curve25519() {
        super(i);
        this.b = m(new BigInteger(1, Hex.a("2AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA984914A144")));
        this.c = m(new BigInteger(1, Hex.a("7B425ED097B425ED097B425ED097B425ED097B425ED097B4260B5E9C7710C864")));
        this.d = new BigInteger(1, Hex.a("1000000000000000000000000000000014DEF9DEA2F79CD65812631A5CF5D3ED"));
        this.e = BigInteger.valueOf(8);
        this.f = 4;
    }

    /* access modifiers changed from: protected */
    public ECCurve c() {
        return new Curve25519();
    }

    public boolean D(int coord) {
        switch (coord) {
            case 4:
                return true;
            default:
                return false;
        }
    }

    public int t() {
        return i.bitLength();
    }

    public ECFieldElement m(BigInteger x) {
        return new Curve25519FieldElement(x);
    }

    /* access modifiers changed from: protected */
    public ECPoint h(ECFieldElement x, ECFieldElement y, boolean withCompression) {
        return new Curve25519Point(this, x, y, withCompression);
    }

    /* access modifiers changed from: protected */
    public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        return new Curve25519Point(this, x, y, zs, withCompression);
    }

    public ECPoint u() {
        return this.j;
    }
}
