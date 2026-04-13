package org.spongycastle.crypto.params;

import org.spongycastle.util.Arrays;

public class DHValidationParameters {
    private byte[] a;
    private int b;

    public DHValidationParameters(byte[] seed, int counter) {
        this.a = seed;
        this.b = counter;
    }

    public boolean equals(Object o) {
        if (!(o instanceof DHValidationParameters)) {
            return false;
        }
        DHValidationParameters other = (DHValidationParameters) o;
        if (other.b != this.b) {
            return false;
        }
        return Arrays.b(this.a, other.a);
    }

    public int hashCode() {
        return this.b ^ Arrays.J(this.a);
    }
}
