package org.spongycastle.math.field;

import org.spongycastle.util.Arrays;

public class GF2Polynomial implements Polynomial {
    protected final int[] a;

    GF2Polynomial(int[] exponents) {
        this.a = Arrays.k(exponents);
    }

    public int b() {
        int[] iArr = this.a;
        return iArr[iArr.length - 1];
    }

    public int[] a() {
        return Arrays.k(this.a);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GF2Polynomial)) {
            return false;
        }
        return Arrays.d(this.a, ((GF2Polynomial) obj).a);
    }

    public int hashCode() {
        return Arrays.L(this.a);
    }
}
