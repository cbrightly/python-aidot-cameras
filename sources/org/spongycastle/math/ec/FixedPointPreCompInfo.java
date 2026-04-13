package org.spongycastle.math.ec;

public class FixedPointPreCompInfo implements PreCompInfo {
    protected ECPoint a = null;
    protected ECPoint[] b = null;
    protected int c = -1;

    public ECPoint a() {
        return this.a;
    }

    public void d(ECPoint offset) {
        this.a = offset;
    }

    public ECPoint[] b() {
        return this.b;
    }

    public void e(ECPoint[] preComp) {
        this.b = preComp;
    }

    public int c() {
        return this.c;
    }

    public void f(int width) {
        this.c = width;
    }
}
