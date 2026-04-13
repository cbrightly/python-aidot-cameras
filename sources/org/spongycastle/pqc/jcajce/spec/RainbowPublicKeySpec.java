package org.spongycastle.pqc.jcajce.spec;

import java.security.spec.KeySpec;

public class RainbowPublicKeySpec implements KeySpec {
    private short[][] c;
    private short[][] d;
    private short[] f;
    private int q;

    public RainbowPublicKeySpec(int docLength, short[][] coeffquadratic, short[][] coeffSingular, short[] coeffScalar) {
        this.q = docLength;
        this.c = coeffquadratic;
        this.d = coeffSingular;
        this.f = coeffScalar;
    }

    public int d() {
        return this.q;
    }

    public short[][] a() {
        return this.c;
    }

    public short[][] c() {
        return this.d;
    }

    public short[] b() {
        return this.f;
    }
}
