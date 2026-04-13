package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class ParametersWithUKM implements CipherParameters {
    private byte[] c;
    private CipherParameters d;

    public ParametersWithUKM(CipherParameters parameters, byte[] ukm) {
        this(parameters, ukm, 0, ukm.length);
    }

    public ParametersWithUKM(CipherParameters parameters, byte[] ukm, int ivOff, int ivLen) {
        byte[] bArr = new byte[ivLen];
        this.c = bArr;
        this.d = parameters;
        System.arraycopy(ukm, ivOff, bArr, 0, ivLen);
    }

    public byte[] b() {
        return this.c;
    }

    public CipherParameters a() {
        return this.d;
    }
}
