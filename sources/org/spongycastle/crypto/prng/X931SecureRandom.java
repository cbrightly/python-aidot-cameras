package org.spongycastle.crypto.prng;

import java.security.SecureRandom;

public class X931SecureRandom extends SecureRandom {
    private final X931RNG drbg;
    private final boolean predictionResistant;
    private final SecureRandom randomSource;

    X931SecureRandom(SecureRandom randomSource2, X931RNG drbg2, boolean predictionResistant2) {
        this.randomSource = randomSource2;
        this.drbg = drbg2;
        this.predictionResistant = predictionResistant2;
    }

    public void setSeed(byte[] seed) {
        synchronized (this) {
            SecureRandom secureRandom = this.randomSource;
            if (secureRandom != null) {
                secureRandom.setSeed(seed);
            }
        }
    }

    public void setSeed(long seed) {
        synchronized (this) {
            SecureRandom secureRandom = this.randomSource;
            if (secureRandom != null) {
                secureRandom.setSeed(seed);
            }
        }
    }

    public void nextBytes(byte[] bytes) {
        synchronized (this) {
            if (this.drbg.a(bytes, this.predictionResistant) < 0) {
                this.drbg.f();
                this.drbg.a(bytes, this.predictionResistant);
            }
        }
    }

    public byte[] generateSeed(int numBytes) {
        return EntropyUtil.a(this.drbg.b(), numBytes);
    }
}
