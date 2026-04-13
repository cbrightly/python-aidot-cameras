package org.spongycastle.math.ec.custom.sec;

import com.alibaba.fastjson.asm.Opcodes;
import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

public class SecT163R1Curve extends ECCurve.AbstractF2m {
    protected SecT163R1Point j = new SecT163R1Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecT163R1Curve() {
        super(Opcodes.IF_ICMPGT, 3, 6, 7);
        this.b = m(new BigInteger(1, Hex.a("07B6882CAAEFA84F9554FF8428BD88E246D2782AE2")));
        this.c = m(new BigInteger(1, Hex.a("0713612DCDDCB40AAB946BDA29CA91F73AF958AFD9")));
        this.d = new BigInteger(1, Hex.a("03FFFFFFFFFFFFFFFFFFFF48AAB689C29CA710279B"));
        this.e = BigInteger.valueOf(2);
        this.f = 6;
    }

    /* access modifiers changed from: protected */
    public ECCurve c() {
        return new SecT163R1Curve();
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
        return Opcodes.IF_ICMPGT;
    }

    public ECFieldElement m(BigInteger x) {
        return new SecT163FieldElement(x);
    }

    /* access modifiers changed from: protected */
    public ECPoint h(ECFieldElement x, ECFieldElement y, boolean withCompression) {
        return new SecT163R1Point(this, x, y, withCompression);
    }

    /* access modifiers changed from: protected */
    public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        return new SecT163R1Point(this, x, y, zs, withCompression);
    }

    public ECPoint u() {
        return this.j;
    }

    public boolean I() {
        return false;
    }
}
