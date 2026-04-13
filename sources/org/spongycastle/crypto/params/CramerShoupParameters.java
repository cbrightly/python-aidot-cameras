package org.spongycastle.crypto.params;

import java.math.BigInteger;
import org.spongycastle.crypto.CipherParameters;

public class CramerShoupParameters implements CipherParameters {
    private BigInteger c;
    private BigInteger d;
    private BigInteger f;

    public boolean equals(Object obj) {
        if (!(obj instanceof DSAParameters)) {
            return false;
        }
        CramerShoupParameters pm = (CramerShoupParameters) obj;
        if (!pm.c().equals(this.c) || !pm.a().equals(this.d) || !pm.b().equals(this.f)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (c().hashCode() ^ a().hashCode()) ^ b().hashCode();
    }

    public BigInteger a() {
        return this.d;
    }

    public BigInteger b() {
        return this.f;
    }

    public BigInteger c() {
        return this.c;
    }
}
