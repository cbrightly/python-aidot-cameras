package org.spongycastle.pqc.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.pqc.math.linearalgebra.PolynomialRingGF2;

public class McElieceCCA2KeyGenParameterSpec implements AlgorithmParameterSpec {
    private final int a;
    private final int b;
    private final int c;
    private int d;
    private final String e;

    public McElieceCCA2KeyGenParameterSpec() {
        this(11, 50, "SHA-256");
    }

    public McElieceCCA2KeyGenParameterSpec(int m, int t, String digest) {
        if (m < 1) {
            throw new IllegalArgumentException("m must be positive");
        } else if (m <= 32) {
            this.a = m;
            int i = 1 << m;
            this.c = i;
            if (t < 0) {
                throw new IllegalArgumentException("t must be positive");
            } else if (t <= i) {
                this.b = t;
                this.d = PolynomialRingGF2.c(m);
                this.e = digest;
            } else {
                throw new IllegalArgumentException("t must be less than n = 2^m");
            }
        } else {
            throw new IllegalArgumentException("m is too large");
        }
    }

    public int b() {
        return this.a;
    }

    public int c() {
        return this.b;
    }

    public String a() {
        return this.e;
    }
}
