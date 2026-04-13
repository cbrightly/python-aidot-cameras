package org.spongycastle.pqc.crypto.gmss;

public class GMSSPublicKeyParameters extends GMSSKeyParameters {
    private byte[] f;

    public GMSSPublicKeyParameters(byte[] key, GMSSParameters gmssParameterSet) {
        super(false, gmssParameterSet);
        this.f = key;
    }

    public byte[] c() {
        return this.f;
    }
}
