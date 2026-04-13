package org.spongycastle.pqc.crypto.rainbow;

public class RainbowPrivateKeyParameters extends RainbowKeyParameters {
    private short[][] f;
    private Layer[] p0;
    private short[] q;
    private short[][] x;
    private short[] y;
    private int[] z;

    public RainbowPrivateKeyParameters(short[][] A1inv, short[] b1, short[][] A2inv, short[] b2, int[] vi, Layer[] layers) {
        super(true, vi[vi.length - 1] - vi[0]);
        this.f = A1inv;
        this.q = b1;
        this.x = A2inv;
        this.y = b2;
        this.z = vi;
        this.p0 = layers;
    }

    public short[] c() {
        return this.q;
    }

    public short[][] e() {
        return this.f;
    }

    public short[] d() {
        return this.y;
    }

    public short[][] f() {
        return this.x;
    }

    public Layer[] g() {
        return this.p0;
    }

    public int[] h() {
        return this.z;
    }
}
