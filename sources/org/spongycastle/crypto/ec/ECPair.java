package org.spongycastle.crypto.ec;

import org.spongycastle.math.ec.ECPoint;

public class ECPair {
    private final ECPoint a;
    private final ECPoint b;

    public ECPoint b() {
        return this.a;
    }

    public ECPoint c() {
        return this.b;
    }

    public boolean a(ECPair other) {
        return other.b().e(b()) && other.c().e(c());
    }

    public boolean equals(Object other) {
        if (other instanceof ECPair) {
            return a((ECPair) other);
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode() + (this.b.hashCode() * 37);
    }
}
