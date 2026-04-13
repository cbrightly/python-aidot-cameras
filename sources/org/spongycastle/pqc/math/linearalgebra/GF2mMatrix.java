package org.spongycastle.pqc.math.linearalgebra;

public class GF2mMatrix extends Matrix {
    protected GF2mField c;
    protected int[][] d;

    public boolean equals(Object other) {
        int i;
        if (other == null || !(other instanceof GF2mMatrix)) {
            return false;
        }
        GF2mMatrix otherMatrix = (GF2mMatrix) other;
        if (!this.c.equals(otherMatrix.c) || otherMatrix.a != (i = this.b) || otherMatrix.b != i) {
            return false;
        }
        for (int i2 = 0; i2 < this.a; i2++) {
            for (int j = 0; j < this.b; j++) {
                if (this.d[i2][j] != otherMatrix.d[i2][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {
        int hash = (((this.c.hashCode() * 31) + this.a) * 31) + this.b;
        for (int i = 0; i < this.a; i++) {
            for (int j = 0; j < this.b; j++) {
                hash = (hash * 31) + this.d[i][j];
            }
        }
        return hash;
    }

    public String toString() {
        String str = this.a + " x " + this.b + " Matrix over " + this.c.toString() + ": \n";
        for (int i = 0; i < this.a; i++) {
            for (int j = 0; j < this.b; j++) {
                str = str + this.c.b(this.d[i][j]) + " : ";
            }
            str = str + "\n";
        }
        return str;
    }
}
