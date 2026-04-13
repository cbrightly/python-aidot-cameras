package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class ParametersWithID implements CipherParameters {
    private CipherParameters c;
    private byte[] d;

    public byte[] a() {
        return this.d;
    }

    public CipherParameters b() {
        return this.c;
    }
}
