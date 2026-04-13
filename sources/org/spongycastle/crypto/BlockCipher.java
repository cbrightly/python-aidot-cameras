package org.spongycastle.crypto;

public interface BlockCipher {
    void a(boolean z, CipherParameters cipherParameters);

    String b();

    int c();

    int f(byte[] bArr, int i, byte[] bArr2, int i2);

    void reset();
}
