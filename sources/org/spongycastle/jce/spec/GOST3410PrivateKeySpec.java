package org.spongycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.KeySpec;

public class GOST3410PrivateKeySpec implements KeySpec {
    private BigInteger c;
    private BigInteger d;
    private BigInteger f;
    private BigInteger q;

    public GOST3410PrivateKeySpec(BigInteger x, BigInteger p, BigInteger q2, BigInteger a) {
        this.c = x;
        this.d = p;
        this.f = q2;
        this.q = a;
    }

    public BigInteger d() {
        return this.c;
    }

    public BigInteger b() {
        return this.d;
    }

    public BigInteger c() {
        return this.f;
    }

    public BigInteger a() {
        return this.q;
    }
}
