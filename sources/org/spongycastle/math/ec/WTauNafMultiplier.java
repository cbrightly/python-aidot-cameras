package org.spongycastle.math.ec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;

public class WTauNafMultiplier extends AbstractECMultiplier {
    /* access modifiers changed from: protected */
    public ECPoint b(ECPoint point, BigInteger k) {
        if (point instanceof ECPoint.AbstractF2m) {
            ECPoint.AbstractF2m p = (ECPoint.AbstractF2m) point;
            ECCurve.AbstractF2m curve = (ECCurve.AbstractF2m) p.i();
            int m = curve.t();
            byte a = curve.n().t().byteValue();
            byte mu = Tnaf.c(a);
            return d(p, Tnaf.j(k, m, a, curve.H(), mu, (byte) 10), curve.x(p, "bc_wtnaf"), a, mu);
        }
        throw new IllegalArgumentException("Only ECPoint.AbstractF2m can be used in WTauNafMultiplier");
    }

    private ECPoint.AbstractF2m d(ECPoint.AbstractF2m p, ZTauElement lambda, PreCompInfo preCompInfo, byte a, byte mu) {
        return c(p, Tnaf.l(mu, lambda, (byte) 4, BigInteger.valueOf(16), Tnaf.g(mu, 4), a == 0 ? Tnaf.d : Tnaf.f), preCompInfo);
    }

    /* JADX WARNING: type inference failed for: r9v0, types: [org.spongycastle.math.ec.ECPoint] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static org.spongycastle.math.ec.ECPoint.AbstractF2m c(org.spongycastle.math.ec.ECPoint.AbstractF2m r10, byte[] r11, org.spongycastle.math.ec.PreCompInfo r12) {
        /*
            org.spongycastle.math.ec.ECCurve r0 = r10.i()
            org.spongycastle.math.ec.ECCurve$AbstractF2m r0 = (org.spongycastle.math.ec.ECCurve.AbstractF2m) r0
            org.spongycastle.math.ec.ECFieldElement r1 = r0.n()
            java.math.BigInteger r1 = r1.t()
            byte r1 = r1.byteValue()
            if (r12 == 0) goto L_0x0021
            boolean r2 = r12 instanceof org.spongycastle.math.ec.WTauNafPreCompInfo
            if (r2 != 0) goto L_0x0019
            goto L_0x0021
        L_0x0019:
            r2 = r12
            org.spongycastle.math.ec.WTauNafPreCompInfo r2 = (org.spongycastle.math.ec.WTauNafPreCompInfo) r2
            org.spongycastle.math.ec.ECPoint$AbstractF2m[] r2 = r2.a()
            goto L_0x0033
        L_0x0021:
            org.spongycastle.math.ec.ECPoint$AbstractF2m[] r2 = org.spongycastle.math.ec.Tnaf.d(r10, r1)
            org.spongycastle.math.ec.WTauNafPreCompInfo r3 = new org.spongycastle.math.ec.WTauNafPreCompInfo
            r3.<init>()
            r3.b(r2)
            java.lang.String r4 = "bc_wtnaf"
            r0.C(r10, r4, r3)
        L_0x0033:
            int r3 = r2.length
            org.spongycastle.math.ec.ECPoint$AbstractF2m[] r3 = new org.spongycastle.math.ec.ECPoint.AbstractF2m[r3]
            r4 = 0
        L_0x0037:
            int r5 = r2.length
            if (r4 >= r5) goto L_0x0047
            r5 = r2[r4]
            org.spongycastle.math.ec.ECPoint r5 = r5.x()
            org.spongycastle.math.ec.ECPoint$AbstractF2m r5 = (org.spongycastle.math.ec.ECPoint.AbstractF2m) r5
            r3[r4] = r5
            int r4 = r4 + 1
            goto L_0x0037
        L_0x0047:
            org.spongycastle.math.ec.ECCurve r4 = r10.i()
            org.spongycastle.math.ec.ECPoint r4 = r4.u()
            org.spongycastle.math.ec.ECPoint$AbstractF2m r4 = (org.spongycastle.math.ec.ECPoint.AbstractF2m) r4
            r5 = 0
            int r6 = r11.length
            int r6 = r6 + -1
        L_0x0055:
            if (r6 < 0) goto L_0x0078
            int r5 = r5 + 1
            byte r7 = r11[r6]
            if (r7 == 0) goto L_0x0075
            org.spongycastle.math.ec.ECPoint$AbstractF2m r4 = r4.J(r5)
            r5 = 0
            if (r7 <= 0) goto L_0x0069
            int r8 = r7 >>> 1
            r8 = r2[r8]
            goto L_0x006e
        L_0x0069:
            int r8 = -r7
            int r8 = r8 >>> 1
            r8 = r3[r8]
        L_0x006e:
            org.spongycastle.math.ec.ECPoint r9 = r4.a(r8)
            r4 = r9
            org.spongycastle.math.ec.ECPoint$AbstractF2m r4 = (org.spongycastle.math.ec.ECPoint.AbstractF2m) r4
        L_0x0075:
            int r6 = r6 + -1
            goto L_0x0055
        L_0x0078:
            if (r5 <= 0) goto L_0x007e
            org.spongycastle.math.ec.ECPoint$AbstractF2m r4 = r4.J(r5)
        L_0x007e:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.math.ec.WTauNafMultiplier.c(org.spongycastle.math.ec.ECPoint$AbstractF2m, byte[], org.spongycastle.math.ec.PreCompInfo):org.spongycastle.math.ec.ECPoint$AbstractF2m");
    }
}
