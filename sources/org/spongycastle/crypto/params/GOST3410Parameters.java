package org.spongycastle.crypto.params;

import java.math.BigInteger;
import org.spongycastle.crypto.CipherParameters;

public class GOST3410Parameters implements CipherParameters {
    private BigInteger c;
    private BigInteger d;
    private BigInteger f;
    private GOST3410ValidationParameters q;

    public GOST3410Parameters(BigInteger p, BigInteger q2, BigInteger a) {
        this.c = p;
        this.d = q2;
        this.f = a;
    }

    public GOST3410Parameters(BigInteger p, BigInteger q2, BigInteger a, GOST3410ValidationParameters params) {
        this.f = a;
        this.c = p;
        this.d = q2;
        this.q = params;
    }

    public BigInteger b() {
        return this.c;
    }

    public BigInteger c() {
        return this.d;
    }

    public BigInteger a() {
        return this.f;
    }

    public int hashCode() {
        return (this.c.hashCode() ^ this.d.hashCode()) ^ this.f.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GOST3410Parameters)) {
            return false;
        }
        GOST3410Parameters pm = (GOST3410Parameters) obj;
        if (!pm.b().equals(this.c) || !pm.c().equals(this.d) || !pm.a().equals(this.f)) {
            return false;
        }
        return true;
    }
}
