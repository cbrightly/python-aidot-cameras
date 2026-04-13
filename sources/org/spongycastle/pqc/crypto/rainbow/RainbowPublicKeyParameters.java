package org.spongycastle.pqc.crypto.rainbow;

public class RainbowPublicKeyParameters extends RainbowKeyParameters {
    private short[][] f;
    private short[][] q;
    private short[] x;

    public RainbowPublicKeyParameters(int docLength, short[][] coeffQuadratic, short[][] coeffSingular, short[] coeffScalar) {
        super(false, docLength);
        this.f = coeffQuadratic;
        this.q = coeffSingular;
        this.x = coeffScalar;
    }

    public short[][] c() {
        return this.f;
    }

    public short[][] e() {
        return this.q;
    }

    public short[] d() {
        return this.x;
    }
}
