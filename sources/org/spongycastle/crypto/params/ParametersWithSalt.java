package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class ParametersWithSalt implements CipherParameters {
    private byte[] c;
    private CipherParameters d;

    public byte[] b() {
        return this.c;
    }

    public CipherParameters a() {
        return this.d;
    }
}
