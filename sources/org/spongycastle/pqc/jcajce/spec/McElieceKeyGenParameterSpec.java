package org.spongycastle.pqc.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.pqc.math.linearalgebra.PolynomialRingGF2;

public class McElieceKeyGenParameterSpec implements AlgorithmParameterSpec {
    private int a;
    private int b;
    private int c;
    private int d;

    public McElieceKeyGenParameterSpec() {
        this(11, 50);
    }

    public McElieceKeyGenParameterSpec(int m, int t) {
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
            } else {
                throw new IllegalArgumentException("t must be less than n = 2^m");
            }
        } else {
            throw new IllegalArgumentException("m is too large");
        }
    }

    public int a() {
        return this.a;
    }

    public int b() {
        return this.b;
    }
}
