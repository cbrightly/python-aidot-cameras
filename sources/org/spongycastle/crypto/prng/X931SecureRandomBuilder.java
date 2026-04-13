package org.spongycastle.crypto.prng;

import java.security.SecureRandom;

public class X931SecureRandomBuilder {
    private SecureRandom a;
    private EntropySourceProvider b;

    public X931SecureRandomBuilder() {
        this(new SecureRandom(), false);
    }

    public X931SecureRandomBuilder(SecureRandom entropySource, boolean predictionResistant) {
        this.a = entropySource;
        this.b = new BasicEntropySourceProvider(entropySource, predictionResistant);
    }
}
