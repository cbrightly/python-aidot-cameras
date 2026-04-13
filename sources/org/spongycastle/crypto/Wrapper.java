package org.spongycastle.crypto;

public interface Wrapper {
    void a(boolean z, CipherParameters cipherParameters);

    String b();

    byte[] c(byte[] bArr, int i, int i2);

    byte[] wrap(byte[] bArr, int i, int i2);
}
