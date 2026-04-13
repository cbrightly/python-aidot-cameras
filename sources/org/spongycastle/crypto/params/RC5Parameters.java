package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class RC5Parameters implements CipherParameters {
    private byte[] c;
    private int d;

    public RC5Parameters(byte[] key, int rounds) {
        if (key.length <= 255) {
            byte[] bArr = new byte[key.length];
            this.c = bArr;
            this.d = rounds;
            System.arraycopy(key, 0, bArr, 0, key.length);
            return;
        }
        throw new IllegalArgumentException("RC5 key length can be no greater than 255");
    }

    public byte[] a() {
        return this.c;
    }

    public int b() {
        return this.d;
    }
}
