package org.spongycastle.crypto.params;

public class RC2Parameters extends KeyParameter {
    private int d;

    public RC2Parameters(byte[] key, int bits) {
        super(key);
        this.d = bits;
    }

    public int b() {
        return this.d;
    }
}
