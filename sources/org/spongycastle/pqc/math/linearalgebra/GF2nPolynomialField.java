package org.spongycastle.pqc.math.linearalgebra;

public class GF2nPolynomialField extends GF2nField {
    private boolean c;
    private boolean d;
    private int e;
    private int[] f;

    /* access modifiers changed from: protected */
    public void a() {
        if (!f() && !d()) {
            e();
        }
    }

    private boolean f() {
        boolean done = false;
        int l = 0;
        GF2Polynomial gF2Polynomial = new GF2Polynomial(this.a + 1);
        this.b = gF2Polynomial;
        gF2Polynomial.q(0);
        this.b.q(this.a);
        for (int i = 1; i < this.a && !done; i++) {
            this.b.q(i);
            boolean done2 = this.b.j();
            l++;
            if (done2) {
                this.c = true;
                this.e = i;
                return done2;
            }
            this.b.p(i);
            done = this.b.j();
        }
        return done;
    }

    private boolean d() {
        boolean done = false;
        int l = 0;
        GF2Polynomial gF2Polynomial = new GF2Polynomial(this.a + 1);
        this.b = gF2Polynomial;
        gF2Polynomial.q(0);
        this.b.q(this.a);
        for (int i = 1; i <= this.a - 3 && !done; i++) {
            this.b.q(i);
            for (int j = i + 1; j <= this.a - 2 && !done; j++) {
                this.b.q(j);
                for (int k = j + 1; k <= this.a - 1 && !done; k++) {
                    this.b.q(k);
                    if ((((this.a & 1) != 0) | ((i & 1) != 0) | ((j & 1) != 0)) || ((k & 1) != 0)) {
                        done = this.b.j();
                        l++;
                        if (done) {
                            this.d = true;
                            int[] iArr = this.f;
                            iArr[0] = i;
                            iArr[1] = j;
                            iArr[2] = k;
                            return done;
                        }
                    }
                    this.b.p(k);
                }
                this.b.p(j);
            }
            this.b.p(i);
        }
        return done;
    }

    private boolean e() {
        this.b = new GF2Polynomial(this.a + 1);
        int l = 0;
        while (0 == 0) {
            l++;
            this.b.m();
            this.b.q(this.a);
            this.b.q(0);
            if (this.b.j()) {
                return true;
            }
        }
        return false;
    }
}
