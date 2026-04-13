package org.spongycastle.crypto;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public class AsymmetricCipherKeyPair {
    private AsymmetricKeyParameter a;
    private AsymmetricKeyParameter b;

    public AsymmetricCipherKeyPair(AsymmetricKeyParameter publicParam, AsymmetricKeyParameter privateParam) {
        this.a = publicParam;
        this.b = privateParam;
    }

    public AsymmetricKeyParameter b() {
        return this.a;
    }

    public AsymmetricKeyParameter a() {
        return this.b;
    }
}
