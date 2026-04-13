package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class CramerShoupPublicKeyParameters extends CramerShoupKeyParameters {
    private BigInteger f;
    private BigInteger q;
    private BigInteger x;

    public CramerShoupPublicKeyParameters(CramerShoupParameters params, BigInteger c, BigInteger d, BigInteger h) {
        super(false, params);
        this.f = c;
        this.q = d;
        this.x = h;
    }

    public BigInteger c() {
        return this.f;
    }

    public BigInteger d() {
        return this.q;
    }

    public BigInteger e() {
        return this.x;
    }

    public int hashCode() {
        return ((this.f.hashCode() ^ this.q.hashCode()) ^ this.x.hashCode()) ^ super.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CramerShoupPublicKeyParameters)) {
            return false;
        }
        CramerShoupPublicKeyParameters other = (CramerShoupPublicKeyParameters) obj;
        if (!other.c().equals(this.f) || !other.d().equals(this.q) || !other.e().equals(this.x) || !super.equals(obj)) {
            return false;
        }
        return true;
    }
}
