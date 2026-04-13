package org.spongycastle.math.ec.custom.sec;

import com.alibaba.fastjson.asm.Opcodes;
import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

public class SecT163R2Curve extends ECCurve.AbstractF2m {
    protected SecT163R2Point j = new SecT163R2Point(this, (ECFieldElement) null, (ECFieldElement) null);

    public SecT163R2Curve() {
        super(Opcodes.IF_ICMPGT, 3, 6, 7);
        this.b = m(BigInteger.valueOf(1));
        this.c = m(new BigInteger(1, Hex.a("020A601907B8C953CA1481EB10512F78744A3205FD")));
        this.d = new BigInteger(1, Hex.a("040000000000000000000292FE77E70C12A4234C33"));
        this.e = BigInteger.valueOf(2);
        this.f = 6;
    }

    /* access modifiers changed from: protected */
    public ECCurve c() {
        return new SecT163R2Curve();
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
        return new SecT163R2Point(this, x, y, withCompression);
    }

    /* access modifiers changed from: protected */
    public ECPoint i(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        return new SecT163R2Point(this, x, y, zs, withCompression);
    }

    public ECPoint u() {
        return this.j;
    }

    public boolean I() {
        return false;
    }
}
