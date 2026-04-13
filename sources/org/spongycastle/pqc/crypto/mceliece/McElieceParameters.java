package org.spongycastle.pqc.crypto.mceliece;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.pqc.math.linearalgebra.PolynomialRingGF2;

public class McElieceParameters implements CipherParameters {
    private int c;
    private int d;
    private int f;
    private int q;
    private Digest x;

    public McElieceParameters() {
        this(11, 50);
    }

    public McElieceParameters(int m, int t) {
        this(m, t, (Digest) null);
    }

    public McElieceParameters(int m, int t, Digest digest) {
        if (m < 1) {
            throw new IllegalArgumentException("m must be positive");
        } else if (m <= 32) {
            this.c = m;
            int i = 1 << m;
            this.f = i;
            if (t < 0) {
                throw new IllegalArgumentException("t must be positive");
            } else if (t <= i) {
                this.d = t;
                this.q = PolynomialRingGF2.c(m);
                this.x = digest;
            } else {
                throw new IllegalArgumentException("t must be less than n = 2^m");
            }
        } else {
            throw new IllegalArgumentException("m is too large");
        }
    }

    public int b() {
        return this.c;
    }

    public int c() {
        return this.f;
    }

    public int d() {
        return this.d;
    }

    public int a() {
        return this.q;
    }
}
