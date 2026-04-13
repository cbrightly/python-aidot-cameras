package org.spongycastle.math.field;

import java.math.BigInteger;

public class PrimeField implements FiniteField {
    protected final BigInteger a;

    PrimeField(BigInteger characteristic) {
        this.a = characteristic;
    }

    public BigInteger b() {
        return this.a;
    }

    public int a() {
        return 1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PrimeField)) {
            return false;
        }
        return this.a.equals(((PrimeField) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
