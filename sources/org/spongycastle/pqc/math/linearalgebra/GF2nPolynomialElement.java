package org.spongycastle.pqc.math.linearalgebra;

import net.sqlcipher.database.SQLiteDatabase;

public class GF2nPolynomialElement extends GF2nElement {
    private static final int[] c = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728, SQLiteDatabase.CREATE_IF_NECESSARY, 536870912, 1073741824, Integer.MIN_VALUE, 0};
    private GF2Polynomial d;

    public GF2nPolynomialElement(GF2nPolynomialElement other) {
        this.a = other.a;
        this.b = other.b;
        this.d = new GF2Polynomial(other.d);
    }

    public Object clone() {
        return new GF2nPolynomialElement(this);
    }

    public boolean a() {
        return this.d.l();
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof GF2nPolynomialElement)) {
            return false;
        }
        GF2nPolynomialElement otherElem = (GF2nPolynomialElement) other;
        GF2nField gF2nField = this.a;
        if (gF2nField == otherElem.a || gF2nField.c().equals(otherElem.a.c())) {
            return this.d.equals(otherElem.d);
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode() + this.d.hashCode();
    }

    public String toString() {
        return this.d.v(16);
    }
}
