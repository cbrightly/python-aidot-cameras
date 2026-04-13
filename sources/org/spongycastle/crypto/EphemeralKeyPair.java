package org.spongycastle.crypto;

public class EphemeralKeyPair {
    private AsymmetricCipherKeyPair a;
    private KeyEncoder b;

    public EphemeralKeyPair(AsymmetricCipherKeyPair keyPair, KeyEncoder publicKeyEncoder) {
        this.a = keyPair;
        this.b = publicKeyEncoder;
    }

    public AsymmetricCipherKeyPair b() {
        return this.a;
    }

    public byte[] a() {
        return this.b.a(this.a.b());
    }
}
