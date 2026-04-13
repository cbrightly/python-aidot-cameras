package org.spongycastle.pqc.crypto.mceliece;

import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;

public class McElieceCCA2PublicKeyParameters extends McElieceCCA2KeyParameters {
    private int f;
    private int q;
    private GF2Matrix x;

    public McElieceCCA2PublicKeyParameters(int n, int t, GF2Matrix matrix, String digest) {
        super(false, digest);
        this.f = n;
        this.q = t;
        this.x = new GF2Matrix(matrix);
    }

    public int e() {
        return this.f;
    }

    public int f() {
        return this.q;
    }

    public GF2Matrix c() {
        return this.x;
    }

    public int d() {
        return this.x.b();
    }
}
