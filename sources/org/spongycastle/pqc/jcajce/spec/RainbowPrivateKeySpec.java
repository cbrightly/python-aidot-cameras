package org.spongycastle.pqc.jcajce.spec;

import java.security.spec.KeySpec;
import org.spongycastle.pqc.crypto.rainbow.Layer;

public class RainbowPrivateKeySpec implements KeySpec {
    private short[][] c;
    private short[] d;
    private short[][] f;
    private short[] q;
    private int[] x;
    private Layer[] y;

    public RainbowPrivateKeySpec(short[][] A1inv, short[] b1, short[][] A2inv, short[] b2, int[] vi, Layer[] layers) {
        this.c = A1inv;
        this.d = b1;
        this.f = A2inv;
        this.q = b2;
        this.x = vi;
        this.y = layers;
    }

    public short[] a() {
        return this.d;
    }

    public short[][] c() {
        return this.c;
    }

    public short[] b() {
        return this.q;
    }

    public short[][] d() {
        return this.f;
    }

    public Layer[] e() {
        return this.y;
    }

    public int[] f() {
        return this.x;
    }
}
