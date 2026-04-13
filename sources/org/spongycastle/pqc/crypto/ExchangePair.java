package org.spongycastle.pqc.crypto;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.util.Arrays;

public class ExchangePair {
    private final AsymmetricKeyParameter a;
    private final byte[] b;

    public ExchangePair(AsymmetricKeyParameter publicKey, byte[] shared) {
        this.a = publicKey;
        this.b = Arrays.h(shared);
    }

    public AsymmetricKeyParameter a() {
        return this.a;
    }

    public byte[] b() {
        return Arrays.h(this.b);
    }
}
