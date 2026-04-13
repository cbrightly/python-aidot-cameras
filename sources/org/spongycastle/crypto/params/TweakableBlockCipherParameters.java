package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class TweakableBlockCipherParameters implements CipherParameters {
    private final byte[] c;
    private final KeyParameter d;

    public KeyParameter a() {
        return this.d;
    }

    public byte[] b() {
        return this.c;
    }
}
