package org.spongycastle.crypto.params;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;

public class RSAKeyGenerationParameters extends KeyGenerationParameters {
    private BigInteger f;
    private int q;

    public RSAKeyGenerationParameters(BigInteger publicExponent, SecureRandom random, int strength, int certainty) {
        super(random, strength);
        if (strength < 12) {
            throw new IllegalArgumentException("key strength too small");
        } else if (publicExponent.testBit(0)) {
            this.f = publicExponent;
            this.q = certainty;
        } else {
            throw new IllegalArgumentException("public exponent cannot be even");
        }
    }

    public BigInteger d() {
        return this.f;
    }

    public int c() {
        return this.q;
    }
}
