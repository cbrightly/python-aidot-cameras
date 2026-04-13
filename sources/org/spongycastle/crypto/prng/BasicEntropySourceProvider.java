package org.spongycastle.crypto.prng;

import java.security.SecureRandom;

public class BasicEntropySourceProvider implements EntropySourceProvider {
    /* access modifiers changed from: private */
    public final SecureRandom a;
    private final boolean b;

    public BasicEntropySourceProvider(SecureRandom random, boolean isPredictionResistant) {
        this.a = random;
        this.b = isPredictionResistant;
    }

    public EntropySource get(final int bitsRequired) {
        return new EntropySource() {
            public byte[] a() {
                if (!(BasicEntropySourceProvider.this.a instanceof SP800SecureRandom) && !(BasicEntropySourceProvider.this.a instanceof X931SecureRandom)) {
                    return BasicEntropySourceProvider.this.a.generateSeed((bitsRequired + 7) / 8);
                }
                byte[] rv = new byte[((bitsRequired + 7) / 8)];
                BasicEntropySourceProvider.this.a.nextBytes(rv);
                return rv;
            }

            public int b() {
                return bitsRequired;
            }
        };
    }
}
