package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;

public interface BlockCipherPadding {
    int a(byte[] bArr);

    void b(SecureRandom secureRandom);

    int c(byte[] bArr, int i);
}
