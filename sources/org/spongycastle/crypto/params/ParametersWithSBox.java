package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class ParametersWithSBox implements CipherParameters {
    private CipherParameters c;
    private byte[] d;

    public ParametersWithSBox(CipherParameters parameters, byte[] sBox) {
        this.c = parameters;
        this.d = sBox;
    }

    public byte[] b() {
        return this.d;
    }

    public CipherParameters a() {
        return this.c;
    }
}
