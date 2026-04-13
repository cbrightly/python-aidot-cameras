package org.spongycastle.pqc.crypto.mceliece;

import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;

public class McEliecePublicKeyParameters extends McElieceKeyParameters {
    private int f;
    private int q;
    private GF2Matrix x;

    public McEliecePublicKeyParameters(int n, int t, GF2Matrix g) {
        super(false, (McElieceParameters) null);
        this.f = n;
        this.q = t;
        this.x = new GF2Matrix(g);
    }

    public int d() {
        return this.f;
    }

    public int e() {
        return this.q;
    }

    public GF2Matrix b() {
        return this.x;
    }

    public int c() {
        return this.x.b();
    }
}
