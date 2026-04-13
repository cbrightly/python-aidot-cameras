package org.spongycastle.crypto.params;

import java.math.BigInteger;
import org.spongycastle.crypto.CipherParameters;

public class DSAParameters implements CipherParameters {
    private BigInteger c;
    private BigInteger d;
    private BigInteger f;
    private DSAValidationParameters q;

    public DSAParameters(BigInteger p, BigInteger q2, BigInteger g) {
        this.c = g;
        this.f = p;
        this.d = q2;
    }

    public DSAParameters(BigInteger p, BigInteger q2, BigInteger g, DSAValidationParameters params) {
        this.c = g;
        this.f = p;
        this.d = q2;
        this.q = params;
    }

    public BigInteger b() {
        return this.f;
    }

    public BigInteger c() {
        return this.d;
    }

    public BigInteger a() {
        return this.c;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DSAParameters)) {
            return false;
        }
        DSAParameters pm = (DSAParameters) obj;
        if (!pm.b().equals(this.f) || !pm.c().equals(this.d) || !pm.a().equals(this.c)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (b().hashCode() ^ c().hashCode()) ^ a().hashCode();
    }
}
