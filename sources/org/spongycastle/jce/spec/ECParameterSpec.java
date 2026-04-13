package org.spongycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;

public class ECParameterSpec implements AlgorithmParameterSpec {
    private ECCurve a;
    private byte[] b;
    private ECPoint c;
    private BigInteger d;
    private BigInteger e;

    public ECParameterSpec(ECCurve curve, ECPoint G, BigInteger n) {
        this.a = curve;
        this.c = G.y();
        this.d = n;
        this.e = BigInteger.valueOf(1);
        this.b = null;
    }

    public ECParameterSpec(ECCurve curve, ECPoint G, BigInteger n, BigInteger h, byte[] seed) {
        this.a = curve;
        this.c = G.y();
        this.d = n;
        this.e = h;
        this.b = seed;
    }

    public ECCurve a() {
        return this.a;
    }

    public ECPoint b() {
        return this.c;
    }

    public BigInteger d() {
        return this.d;
    }

    public BigInteger c() {
        return this.e;
    }

    public byte[] e() {
        return this.b;
    }

    public boolean equals(Object o) {
        if (!(o instanceof ECParameterSpec)) {
            return false;
        }
        ECParameterSpec other = (ECParameterSpec) o;
        if (!a().l(other.a()) || !b().e(other.b())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return a().hashCode() ^ b().hashCode();
    }
}
