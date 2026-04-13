package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import meshsdk.BaseResp;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

public class SecT409R1Curve extends ECCurve.AbstractF2m {
    protected SecT409R1Point j = new SecT409R1Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecT409R1Curve() {
        super(BaseResp.ERR_DISCONNECT_FAIL, 87, 0, 0);
        this.b = m(BigInteger.valueOf(1));
        this.c = m(new BigInteger(1, Hex.a("0021A5C2C8EE9FEB5C4B9A753B7B476B7FD6422EF1F3DD674761FA99D6AC27C8A9A197B272822F6CD57A55AA4F50AE317B13545F")));
        this.d = new BigInteger(1, Hex.a("010000000000000000000000000000000000000000000000000001E2AAD6A612F33307BE5FA47C3C9E052F838164CD37D9A21173"));
        this.e = BigInteger.valueOf(2);
        this.f = 6;
    }

    /* access modifiers changed from: protected */
    public ECCurve c() {
        return new SecT409R1Curve();
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
        return BaseResp.ERR_DISCONNECT_FAIL;
    }

    public ECFieldElement m(BigInteger x) {
        return new SecT409FieldElement(x);
    }

    /* access modifiers changed from: protected */
    public ECPoint h(ECFieldElement x, ECFieldElement y, boolean withCompression) {
        return new SecT409R1Point(this, x, y, withCompression);
    }

    /* access modifiers changed from: protected */
    public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        return new SecT409R1Point(this, x, y, zs, withCompression);
    }

    public ECPoint u() {
        return this.j;
    }

    public boolean I() {
        return false;
    }
}
