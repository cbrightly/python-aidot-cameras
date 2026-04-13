package org.spongycastle.math.ec;

import org.spongycastle.math.ec.ECPoint;

public class WTauNafPreCompInfo implements PreCompInfo {
    protected ECPoint.AbstractF2m[] a = null;

    public ECPoint.AbstractF2m[] a() {
        return this.a;
    }

    public void b(ECPoint.AbstractF2m[] preComp) {
        this.a = preComp;
    }
}
