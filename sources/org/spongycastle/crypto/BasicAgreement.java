package org.spongycastle.crypto;

import java.math.BigInteger;

public interface BasicAgreement {
    void a(CipherParameters cipherParameters);

    int b();

    BigInteger c(CipherParameters cipherParameters);
}
