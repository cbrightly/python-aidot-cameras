package org.spongycastle.pqc.math.linearalgebra;

public abstract class GF2nField {
    protected int a;
    protected GF2Polynomial b;

    /* access modifiers changed from: protected */
    public abstract void a();

    public final int b() {
        return this.a;
    }

    public final GF2Polynomial c() {
        if (this.b == null) {
            a();
        }
        return new GF2Polynomial(this.b);
    }

    public final boolean equals(Object other) {
        if (other == null || !(other instanceof GF2nField)) {
            return false;
        }
        GF2nField otherField = (GF2nField) other;
        if (otherField.a != this.a || !this.b.equals(otherField.b)) {
            return false;
        }
        if ((this instanceof GF2nPolynomialField) && !(otherField instanceof GF2nPolynomialField)) {
            return false;
        }
        if (!(this instanceof GF2nONBField) || (otherField instanceof GF2nONBField)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.a + this.b.hashCode();
    }
}
