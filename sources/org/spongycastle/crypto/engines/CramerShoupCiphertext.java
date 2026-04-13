package org.spongycastle.crypto.engines;

import java.math.BigInteger;

public class CramerShoupCiphertext {
    BigInteger a;
    BigInteger b;
    BigInteger c;
    BigInteger d;

    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("u1: " + this.a.toString());
        result.append("\nu2: " + this.b.toString());
        result.append("\ne: " + this.c.toString());
        result.append("\nv: " + this.d.toString());
        return result.toString();
    }
}
