package org.spongycastle.math.ec;

import java.math.BigInteger;

public class MixedNafR2LMultiplier extends AbstractECMultiplier {
    protected int a;
    protected int b;

    public MixedNafR2LMultiplier() {
        this(2, 4);
    }

    public MixedNafR2LMultiplier(int additionCoord, int doublingCoord) {
        this.a = additionCoord;
        this.b = doublingCoord;
    }

    /* access modifiers changed from: protected */
    public ECPoint b(ECPoint p, BigInteger k) {
        ECCurve curveOrig = p.i();
        ECCurve curveAdd = c(curveOrig, this.a);
        ECCurve curveDouble = c(curveOrig, this.b);
        int[] naf = WNafUtil.a(k);
        ECPoint Ra = curveAdd.u();
        ECPoint Td = curveDouble.y(p);
        int zeroes = 0;
        for (int ni : naf) {
            int digit = ni >> 16;
            Td = Td.G(zeroes + (65535 & ni));
            ECPoint Tj = curveAdd.y(Td);
            if (digit < 0) {
                Tj = Tj.x();
            }
            Ra = Ra.a(Tj);
            zeroes = 1;
        }
        return curveOrig.y(Ra);
    }

    /* access modifiers changed from: protected */
    public ECCurve c(ECCurve c, int coord) {
        if (c.q() == coord) {
            return c;
        }
        if (c.D(coord)) {
            return c.d().b(coord).a();
        }
        throw new IllegalArgumentException("Coordinate system " + coord + " not supported by this curve");
    }
}
