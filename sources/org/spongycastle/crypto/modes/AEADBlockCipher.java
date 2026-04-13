package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;

public interface AEADBlockCipher {
    void a(boolean z, CipherParameters cipherParameters);

    int c(byte[] bArr, int i);

    int d(byte[] bArr, int i, int i2, byte[] bArr2, int i3);

    int e(int i);

    int f(int i);

    BlockCipher g();

    byte[] h();

    void i(byte[] bArr, int i, int i2);
}
