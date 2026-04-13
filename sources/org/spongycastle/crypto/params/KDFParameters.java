package org.spongycastle.crypto.params;

import org.spongycastle.crypto.DerivationParameters;

public class KDFParameters implements DerivationParameters {
    byte[] a;
    byte[] b;

    public KDFParameters(byte[] shared, byte[] iv) {
        this.b = shared;
        this.a = iv;
    }

    public byte[] b() {
        return this.b;
    }

    public byte[] a() {
        return this.a;
    }
}
