package org.spongycastle.crypto.prng;

import java.security.SecureRandom;
import org.spongycastle.crypto.prng.drbg.SP80090DRBG;

public class SP800SecureRandom extends SecureRandom {
    private SP80090DRBG drbg;
    private final DRBGProvider drbgProvider;
    private final EntropySource entropySource;
    private final boolean predictionResistant;
    private final SecureRandom randomSource;

    SP800SecureRandom(SecureRandom randomSource2, EntropySource entropySource2, DRBGProvider drbgProvider2, boolean predictionResistant2) {
        this.randomSource = randomSource2;
        this.entropySource = entropySource2;
        this.drbgProvider = drbgProvider2;
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
            if (this.drbg == null) {
                this.drbg = this.drbgProvider.a(this.entropySource);
            }
            if (this.drbg.a(bytes, (byte[]) null, this.predictionResistant) < 0) {
                this.drbg.b((byte[]) null);
                this.drbg.a(bytes, (byte[]) null, this.predictionResistant);
            }
        }
    }

    public byte[] generateSeed(int numBytes) {
        return EntropyUtil.a(this.entropySource, numBytes);
    }

    public void reseed(byte[] additionalInput) {
        synchronized (this) {
            if (this.drbg == null) {
                this.drbg = this.drbgProvider.a(this.entropySource);
            }
            this.drbg.b(additionalInput);
        }
    }
}
