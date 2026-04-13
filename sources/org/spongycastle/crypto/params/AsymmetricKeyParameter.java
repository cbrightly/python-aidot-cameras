package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class AsymmetricKeyParameter implements CipherParameters {
    boolean c;

    public AsymmetricKeyParameter(boolean privateKey) {
        this.c = privateKey;
    }

    public boolean a() {
        return this.c;
    }
}
