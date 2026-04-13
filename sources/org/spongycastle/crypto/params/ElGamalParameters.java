package org.spongycastle.crypto.params;

import java.math.BigInteger;
import org.spongycastle.crypto.CipherParameters;

public class ElGamalParameters implements CipherParameters {
    private BigInteger c;
    private BigInteger d;
    private int f;

    public ElGamalParameters(BigInteger p, BigInteger g) {
        this(p, g, 0);
    }

    public ElGamalParameters(BigInteger p, BigInteger g, int l) {
        this.c = g;
        this.d = p;
        this.f = l;
    }

    public BigInteger c() {
        return this.d;
    }

    public BigInteger a() {
        return this.c;
    }

    public int b() {
        return this.f;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ElGamalParameters)) {
            return false;
        }
        ElGamalParameters pm = (ElGamalParameters) obj;
        if (!pm.c().equals(this.d) || !pm.a().equals(this.c) || pm.b() != this.f) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (c().hashCode() ^ a().hashCode()) + this.f;
    }
}
