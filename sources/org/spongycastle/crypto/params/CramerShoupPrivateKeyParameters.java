package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class CramerShoupPrivateKeyParameters extends CramerShoupKeyParameters {
    private BigInteger f;
    private CramerShoupPublicKeyParameters p0;
    private BigInteger q;
    private BigInteger x;
    private BigInteger y;
    private BigInteger z;

    public CramerShoupPrivateKeyParameters(CramerShoupParameters params, BigInteger x1, BigInteger x2, BigInteger y1, BigInteger y2, BigInteger z2) {
        super(true, params);
        this.f = x1;
        this.q = x2;
        this.x = y1;
        this.y = y2;
        this.z = z2;
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

    public BigInteger f() {
        return this.y;
    }

    public BigInteger g() {
        return this.z;
    }

    public void h(CramerShoupPublicKeyParameters pk) {
        this.p0 = pk;
    }

    public int hashCode() {
        return ((((this.f.hashCode() ^ this.q.hashCode()) ^ this.x.hashCode()) ^ this.y.hashCode()) ^ this.z.hashCode()) ^ super.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CramerShoupPrivateKeyParameters)) {
            return false;
        }
        CramerShoupPrivateKeyParameters other = (CramerShoupPrivateKeyParameters) obj;
        if (!other.c().equals(this.f) || !other.d().equals(this.q) || !other.e().equals(this.x) || !other.f().equals(this.y) || !other.g().equals(this.z) || !super.equals(obj)) {
            return false;
        }
        return true;
    }
}
