package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.EphemeralKeyPair;
import org.spongycastle.crypto.KeyEncoder;

public class EphemeralKeyPairGenerator {
    private AsymmetricCipherKeyPairGenerator a;
    private KeyEncoder b;

    public EphemeralKeyPairGenerator(AsymmetricCipherKeyPairGenerator gen, KeyEncoder keyEncoder) {
        this.a = gen;
        this.b = keyEncoder;
    }

    public EphemeralKeyPair a() {
        return new EphemeralKeyPair(this.a.a(), this.b);
    }
}
