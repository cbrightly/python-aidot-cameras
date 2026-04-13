package org.spongycastle.crypto.params;

import org.spongycastle.util.Arrays;

public class DSAValidationParameters {
    private int a;
    private byte[] b;
    private int c;

    public DSAValidationParameters(byte[] seed, int counter) {
        this(seed, counter, -1);
    }

    public DSAValidationParameters(byte[] seed, int counter, int usageIndex) {
        this.b = seed;
        this.c = counter;
        this.a = usageIndex;
    }

    public int hashCode() {
        return this.c ^ Arrays.J(this.b);
    }

    public boolean equals(Object o) {
        if (!(o instanceof DSAValidationParameters)) {
            return false;
        }
        DSAValidationParameters other = (DSAValidationParameters) o;
        if (other.c != this.c) {
            return false;
        }
        return Arrays.b(this.b, other.b);
    }
}
