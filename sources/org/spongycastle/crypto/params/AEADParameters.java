package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class AEADParameters implements CipherParameters {
    private byte[] c;
    private byte[] d;
    private KeyParameter f;
    private int q;

    public AEADParameters(KeyParameter key, int macSize, byte[] nonce) {
        this(key, macSize, nonce, (byte[]) null);
    }

    public AEADParameters(KeyParameter key, int macSize, byte[] nonce, byte[] associatedText) {
        this.f = key;
        this.d = nonce;
        this.q = macSize;
        this.c = associatedText;
    }

    public KeyParameter b() {
        return this.f;
    }

    public int c() {
        return this.q;
    }

    public byte[] a() {
        return this.c;
    }

    public byte[] d() {
        return this.d;
    }
}
