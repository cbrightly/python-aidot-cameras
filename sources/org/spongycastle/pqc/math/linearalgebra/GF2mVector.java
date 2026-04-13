package org.spongycastle.pqc.math.linearalgebra;

public class GF2mVector extends Vector {
    private GF2mField b;
    private int[] c;

    public GF2mVector(GF2mField field, int[] vector) {
        this.b = field;
        this.a = vector.length;
        int i = vector.length - 1;
        while (i >= 0) {
            if (field.i(vector[i])) {
                i--;
            } else {
                throw new ArithmeticException("Element array is not specified over the given finite field.");
            }
        }
        this.c = IntUtils.a(vector);
    }

    public GF2mField c() {
        return this.b;
    }

    public int[] d() {
        return IntUtils.a(this.c);
    }

    public Vector a(Vector addend) {
        throw new RuntimeException("not implemented");
    }

    public boolean equals(Object other) {
        if (!(other instanceof GF2mVector)) {
            return false;
        }
        GF2mVector otherVec = (GF2mVector) other;
        if (!this.b.equals(otherVec.b)) {
            return false;
        }
        return IntUtils.b(this.c, otherVec.c);
    }

    public int hashCode() {
        return (this.b.hashCode() * 31) + this.c.hashCode();
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < this.c.length; i++) {
            for (int j = 0; j < this.b.d(); j++) {
                if ((this.c[i] & (1 << (j & 31))) != 0) {
                    buf.append('1');
                } else {
                    buf.append('0');
                }
            }
            buf.append(' ');
        }
        return buf.toString();
    }
}
