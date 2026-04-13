package org.spongycastle.math.ec.endo;

import java.math.BigInteger;

public class GLVTypeBParameters {
    protected final BigInteger a;
    protected final BigInteger b;
    protected final BigInteger c;
    protected final BigInteger d;
    protected final BigInteger e;
    protected final BigInteger f;
    protected final BigInteger g;
    protected final BigInteger h;
    protected final int i;

    private static void a(BigInteger[] v, String name) {
        if (v == null || v.length != 2 || v[0] == null || v[1] == null) {
            throw new IllegalArgumentException("'" + name + "' must consist of exactly 2 (non-null) values");
        }
    }

    public GLVTypeBParameters(BigInteger beta, BigInteger lambda, BigInteger[] v1, BigInteger[] v2, BigInteger g1, BigInteger g2, int bits) {
        a(v1, "v1");
        a(v2, "v2");
        this.a = beta;
        this.b = lambda;
        this.c = v1[0];
        this.d = v1[1];
        this.e = v2[0];
        this.f = v2[1];
        this.g = g1;
        this.h = g2;
        this.i = bits;
    }

    public BigInteger b() {
        return this.a;
    }

    public BigInteger f() {
        return this.c;
    }

    public BigInteger g() {
        return this.d;
    }

    public BigInteger h() {
        return this.e;
    }

    public BigInteger i() {
        return this.f;
    }

    public BigInteger d() {
        return this.g;
    }

    public BigInteger e() {
        return this.h;
    }

    public int c() {
        return this.i;
    }
}
