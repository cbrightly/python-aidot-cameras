package org.spongycastle.crypto.params;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.Arrays;

public class ECDomainParameters implements ECConstants {
    private ECCurve g;
    private byte[] h;
    private ECPoint i;
    private BigInteger j;
    private BigInteger k;

    public ECDomainParameters(ECCurve curve, ECPoint G, BigInteger n) {
        this(curve, G, n, ECConstants.b, (byte[]) null);
    }

    public ECDomainParameters(ECCurve curve, ECPoint G, BigInteger n, BigInteger h2) {
        this(curve, G, n, h2, (byte[]) null);
    }

    public ECDomainParameters(ECCurve curve, ECPoint G, BigInteger n, BigInteger h2, byte[] seed) {
        this.g = curve;
        this.i = G.y();
        this.j = n;
        this.k = h2;
        this.h = seed;
    }

    public ECCurve a() {
        return this.g;
    }

    public ECPoint b() {
        return this.i;
    }

    public BigInteger d() {
        return this.j;
    }

    public BigInteger c() {
        return this.k;
    }

    public byte[] e() {
        return Arrays.h(this.h);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ECDomainParameters)) {
            return false;
        }
        ECDomainParameters other = (ECDomainParameters) obj;
        if (!this.g.l(other.g) || !this.i.e(other.i) || !this.j.equals(other.j) || !this.k.equals(other.k)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((this.g.hashCode() * 37) ^ this.i.hashCode()) * 37) ^ this.j.hashCode()) * 37) ^ this.k.hashCode();
    }
}
