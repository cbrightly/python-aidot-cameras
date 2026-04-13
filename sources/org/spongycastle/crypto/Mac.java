package org.spongycastle.crypto;

public interface Mac {
    void a(CipherParameters cipherParameters);

    String b();

    int c(byte[] bArr, int i);

    void d(byte b);

    int e();

    void reset();

    void update(byte[] bArr, int i, int i2);
}
