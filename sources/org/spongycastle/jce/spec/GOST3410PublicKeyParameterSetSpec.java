package org.spongycastle.jce.spec;

import java.math.BigInteger;

public class GOST3410PublicKeyParameterSetSpec {
    private BigInteger a;
    private BigInteger b;
    private BigInteger c;

    public GOST3410PublicKeyParameterSetSpec(BigInteger p, BigInteger q, BigInteger a2) {
        this.a = p;
        this.b = q;
        this.c = a2;
    }

    public BigInteger b() {
        return this.a;
    }

    public BigInteger c() {
        return this.b;
    }

    public BigInteger a() {
        return this.c;
    }

    public boolean equals(Object o) {
        if (!(o instanceof GOST3410PublicKeyParameterSetSpec)) {
            return false;
        }
        GOST3410PublicKeyParameterSetSpec other = (GOST3410PublicKeyParameterSetSpec) o;
        if (!this.c.equals(other.c) || !this.a.equals(other.a) || !this.b.equals(other.b)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (this.c.hashCode() ^ this.a.hashCode()) ^ this.b.hashCode();
    }
}
