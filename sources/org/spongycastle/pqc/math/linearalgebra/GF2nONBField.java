package org.spongycastle.pqc.math.linearalgebra;

public class GF2nONBField extends GF2nField {
    private int c;
    private int d;
    private int e;

    /* access modifiers changed from: package-private */
    public int e() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public int d() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    public void a() {
        int i = this.e;
        if (i == 1) {
            this.b = new GF2Polynomial(this.a + 1, "ALL");
        } else if (i == 2) {
            GF2Polynomial q = new GF2Polynomial(this.a + 1, "ONE");
            GF2Polynomial p = new GF2Polynomial(this.a + 1, "X");
            p.b(q);
            for (int i2 = 1; i2 < this.a; i2++) {
                GF2Polynomial r = q;
                q = p;
                p = q.r();
                p.b(r);
            }
            this.b = p;
        }
    }
}
