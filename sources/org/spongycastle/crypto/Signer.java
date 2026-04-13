package org.spongycastle.crypto;

public interface Signer {
    void a(boolean z, CipherParameters cipherParameters);

    boolean b(byte[] bArr);

    byte[] c();

    void d(byte b);

    void update(byte[] bArr, int i, int i2);
}
