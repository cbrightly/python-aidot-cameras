package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class IESParameters implements CipherParameters {
    private byte[] c;
    private byte[] d;
    private int f;

    public IESParameters(byte[] derivation, byte[] encoding, int macKeySize) {
        this.c = derivation;
        this.d = encoding;
        this.f = macKeySize;
    }

    public byte[] a() {
        return this.c;
    }

    public byte[] b() {
        return this.d;
    }

    public int c() {
        return this.f;
    }
}
