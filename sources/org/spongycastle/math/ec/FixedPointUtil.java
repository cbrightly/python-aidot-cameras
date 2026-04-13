package org.spongycastle.math.ec;

import java.math.BigInteger;

public class FixedPointUtil {
    public static int a(ECCurve c) {
        BigInteger order = c.w();
        return order == null ? c.t() + 1 : order.bitLength();
    }

    public static FixedPointPreCompInfo b(PreCompInfo preCompInfo) {
        if (preCompInfo == null || !(preCompInfo instanceof FixedPointPreCompInfo)) {
            return new FixedPointPreCompInfo();
        }
        return (FixedPointPreCompInfo) preCompInfo;
    }

    public static FixedPointPreCompInfo c(ECPoint p, int minWidth) {
        ECCurve c = p.i();
        int n = 1 << minWidth;
        FixedPointPreCompInfo info = b(c.x(p, "bc_fixed_point"));
        ECPoint[] lookupTable = info.b();
        if (lookupTable == null || lookupTable.length < n) {
            int d = ((a(c) + minWidth) - 1) / minWidth;
            ECPoint[] pow2Table = new ECPoint[(minWidth + 1)];
            pow2Table[0] = p;
            for (int i = 1; i < minWidth; i++) {
                pow2Table[i] = pow2Table[i - 1].G(d);
            }
            pow2Table[minWidth] = pow2Table[0].E(pow2Table[1]);
            c.A(pow2Table);
            ECPoint[] lookupTable2 = new ECPoint[n];
            lookupTable2[0] = pow2Table[0];
            for (int bit = minWidth - 1; bit >= 0; bit--) {
                ECPoint pow2 = pow2Table[bit];
                int step = 1 << bit;
                for (int i2 = step; i2 < n; i2 += step << 1) {
                    lookupTable2[i2] = lookupTable2[i2 - step].a(pow2);
                }
            }
            c.A(lookupTable2);
            info.d(pow2Table[minWidth]);
            info.e(lookupTable2);
            info.f(minWidth);
            c.C(p, "bc_fixed_point", info);
        }
        return info;
    }
}
