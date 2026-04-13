package org.spongycastle.crypto;

public interface StreamCipher {
    void a(boolean z, CipherParameters cipherParameters);

    String b();

    int d(byte[] bArr, int i, int i2, byte[] bArr2, int i3);

    byte e(byte b);

    void reset();
}
