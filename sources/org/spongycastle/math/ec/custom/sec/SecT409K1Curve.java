package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import meshsdk.BaseResp;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECMultiplier;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.WTauNafMultiplier;
import org.spongycastle.util.encoders.Hex;

public class SecT409K1Curve extends ECCurve.AbstractF2m {
    protected SecT409K1Point j = new SecT409K1Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecT409K1Curve() {
        super(BaseResp.ERR_DISCONNECT_FAIL, 87, 0, 0);
        this.b = m(BigInteger.valueOf(0));
        this.c = m(BigInteger.valueOf(1));
        this.d = new BigInteger(1, Hex.a("7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFE5F83B2D4EA20400EC4557D5ED3E3E7CA5B4B5C83B8E01E5FCF"));
        this.e = BigInteger.valueOf(4);
        this.f = 6;
    }

    /* access modifiers changed from: protected */
    public ECCurve c() {
        return new SecT409K1Curve();
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
        return BaseResp.ERR_DISCONNECT_FAIL;
    }

    public ECFieldElement m(BigInteger x) {
        return new SecT409FieldElement(x);
    }

    /* access modifiers changed from: protected */
    public ECPoint h(ECFieldElement x, ECFieldElement y, boolean withCompression) {
        return new SecT409K1Point(this, x, y, withCompression);
    }

    /* access modifiers changed from: protected */
    public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        return new SecT409K1Point(this, x, y, zs, withCompression);
    }

    public ECPoint u() {
        return this.j;
    }

    public boolean I() {
        return true;
    }
}
