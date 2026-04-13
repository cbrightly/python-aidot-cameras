package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class KeyParameter implements CipherParameters {
    private byte[] c;

    public KeyParameter(byte[] key) {
        this(key, 0, key.length);
    }

    public KeyParameter(byte[] key, int keyOff, int keyLen) {
        byte[] bArr = new byte[keyLen];
        this.c = bArr;
        System.arraycopy(key, keyOff, bArr, 0, keyLen);
    }

    public byte[] a() {
        return this.c;
    }
}
