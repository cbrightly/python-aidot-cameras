package org.spongycastle.pqc.crypto;

import org.spongycastle.crypto.CipherParameters;

public interface MessageSigner {
    void a(boolean z, CipherParameters cipherParameters);

    byte[] b(byte[] bArr);

    boolean c(byte[] bArr, byte[] bArr2);
}
