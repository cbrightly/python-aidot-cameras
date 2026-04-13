package org.spongycastle.math.ec;

public class WNafPreCompInfo implements PreCompInfo {
    protected ECPoint[] a = null;
    protected ECPoint[] b = null;
    protected ECPoint c = null;

    public ECPoint[] a() {
        return this.a;
    }

    public void d(ECPoint[] preComp) {
        this.a = preComp;
    }

    public ECPoint[] b() {
        return this.b;
    }

    public void e(ECPoint[] preCompNeg) {
        this.b = preCompNeg;
    }

    public ECPoint c() {
        return this.c;
    }

    public void f(ECPoint twice) {
        this.c = twice;
    }
}
