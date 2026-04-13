package org.spongycastle.math.ec.custom.sec;

import com.alibaba.fastjson.asm.Opcodes;
import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECMultiplier;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.WTauNafMultiplier;
import org.spongycastle.util.encoders.Hex;

public class SecT163K1Curve extends ECCurve.AbstractF2m {
    protected SecT163K1Point j = new SecT163K1Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecT163K1Curve() {
        super(Opcodes.IF_ICMPGT, 3, 6, 7);
        ECFieldElement m = m(BigInteger.valueOf(1));
        this.b = m;
        this.c = m;
        this.d = new BigInteger(1, Hex.a("04000000000000000000020108A2E0CC0D99F8A5EF"));
        this.e = BigInteger.valueOf(2);
        this.f = 6;
    }

    /* access modifiers changed from: protected */
    public ECCurve c() {
        return new SecT163K1Curve();
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
        return Opcodes.IF_ICMPGT;
    }

    public ECFieldElement m(BigInteger x) {
        return new SecT163FieldElement(x);
    }

    /* access modifiers changed from: protected */
    public ECPoint h(ECFieldElement x, ECFieldElement y, boolean withCompression) {
        return new SecT163K1Point(this, x, y, withCompression);
    }

    /* access modifiers changed from: protected */
    public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        return new SecT163K1Point(this, x, y, zs, withCompression);
    }

    public ECPoint u() {
        return this.j;
    }

    public boolean I() {
        return true;
    }
}
