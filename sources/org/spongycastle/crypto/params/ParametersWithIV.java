package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class ParametersWithIV implements CipherParameters {
    private byte[] c;
    private CipherParameters d;

    public ParametersWithIV(CipherParameters parameters, byte[] iv) {
        this(parameters, iv, 0, iv.length);
    }

    public ParametersWithIV(CipherParameters parameters, byte[] iv, int ivOff, int ivLen) {
        byte[] bArr = new byte[ivLen];
        this.c = bArr;
        this.d = parameters;
        System.arraycopy(iv, ivOff, bArr, 0, ivLen);
    }

    public byte[] a() {
        return this.c;
    }

    public CipherParameters b() {
        return this.d;
    }
}
