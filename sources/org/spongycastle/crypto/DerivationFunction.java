package org.spongycastle.crypto;

public interface DerivationFunction {
    int a(byte[] bArr, int i, int i2);

    void b(DerivationParameters derivationParameters);
}
