package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;

public interface DSAKCalculator {
    void a(BigInteger bigInteger, SecureRandom secureRandom);

    BigInteger b();

    boolean c();

    void d(BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr);
}
