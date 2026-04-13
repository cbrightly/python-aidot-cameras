package org.spongycastle.math.ec.custom.sec;

import com.alibaba.fastjson.asm.Opcodes;
import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

public class SecT193R1Curve extends ECCurve.AbstractF2m {
    protected SecT193R1Point j = new SecT193R1Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecT193R1Curve() {
        super(Opcodes.INSTANCEOF, 15, 0, 0);
        this.b = m(new BigInteger(1, Hex.a("0017858FEB7A98975169E171F77B4087DE098AC8A911DF7B01")));
        this.c = m(new BigInteger(1, Hex.a("00FDFB49BFE6C3A89FACADAA7A1E5BBC7CC1C2E5D831478814")));
        this.d = new BigInteger(1, Hex.a("01000000000000000000000000C7F34A778F443ACC920EBA49"));
        this.e = BigInteger.valueOf(2);
        this.f = 6;
    }

    /* access modifiers changed from: protected */
    public ECCurve c() {
        return new SecT193R1Curve();
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
        return Opcodes.INSTANCEOF;
    }

    public ECFieldElement m(BigInteger x) {
        return new SecT193FieldElement(x);
    }

    /* access modifiers changed from: protected */
    public ECPoint h(ECFieldElement x, ECFieldElement y, boolean withCompression) {
        return new SecT193R1Point(this, x, y, withCompression);
    }

    /* access modifiers changed from: protected */
    public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        return new SecT193R1Point(this, x, y, zs, withCompression);
    }

    public ECPoint u() {
        return this.j;
    }

    public boolean I() {
        return false;
    }
}
