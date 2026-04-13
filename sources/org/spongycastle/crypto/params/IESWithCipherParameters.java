package org.spongycastle.crypto.params;

public class IESWithCipherParameters extends IESParameters {
    private int q;

    public IESWithCipherParameters(byte[] derivation, byte[] encoding, int macKeySize, int cipherKeySize) {
        super(derivation, encoding, macKeySize);
        this.q = cipherKeySize;
    }

    public int d() {
        return this.q;
    }
}
