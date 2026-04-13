package org.spongycastle.asn1.eac;

import org.spongycastle.util.Arrays;

public class PackedDate {
    private byte[] a;

    public int hashCode() {
        return Arrays.J(this.a);
    }

    public boolean equals(Object o) {
        if (!(o instanceof PackedDate)) {
            return false;
        }
        return Arrays.b(this.a, ((PackedDate) o).a);
    }

    public String toString() {
        char[] dateC = new char[this.a.length];
        for (int i = 0; i != dateC.length; i++) {
            dateC[i] = (char) ((this.a[i] & 255) + 48);
        }
        return new String(dateC);
    }
}
