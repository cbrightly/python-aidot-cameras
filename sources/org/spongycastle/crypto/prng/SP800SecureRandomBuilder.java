package org.spongycastle.crypto.prng;

import java.security.SecureRandom;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.prng.drbg.CTRSP800DRBG;
import org.spongycastle.crypto.prng.drbg.HMacSP800DRBG;
import org.spongycastle.crypto.prng.drbg.HashSP800DRBG;
import org.spongycastle.crypto.prng.drbg.SP80090DRBG;

public class SP800SecureRandomBuilder {
    private final SecureRandom a;
    private final EntropySourceProvider b;
    private byte[] c;
    private int d;
    private int e;

    public SP800SecureRandomBuilder() {
        this(new SecureRandom(), false);
    }

    public SP800SecureRandomBuilder(SecureRandom entropySource, boolean predictionResistant) {
        this.d = 256;
        this.e = 256;
        this.a = entropySource;
        this.b = new BasicEntropySourceProvider(entropySource, predictionResistant);
    }

    public SP800SecureRandomBuilder(EntropySourceProvider entropySourceProvider) {
        this.d = 256;
        this.e = 256;
        this.a = null;
        this.b = entropySourceProvider;
    }

    public SP800SecureRandomBuilder c(byte[] personalizationString) {
        this.c = personalizationString;
        return this;
    }

    public SP800SecureRandom b(Digest digest, byte[] nonce, boolean predictionResistant) {
        return new SP800SecureRandom(this.a, this.b.get(this.e), new HashDRBGProvider(digest, nonce, this.c, this.d), predictionResistant);
    }

    public SP800SecureRandom a(Mac hMac, byte[] nonce, boolean predictionResistant) {
        return new SP800SecureRandom(this.a, this.b.get(this.e), new HMacDRBGProvider(hMac, nonce, this.c, this.d), predictionResistant);
    }

    public static class HashDRBGProvider implements DRBGProvider {
        private final Digest a;
        private final byte[] b;
        private final byte[] c;
        private final int d;

        public HashDRBGProvider(Digest digest, byte[] nonce, byte[] personalizationString, int securityStrength) {
            this.a = digest;
            this.b = nonce;
            this.c = personalizationString;
            this.d = securityStrength;
        }

        public SP80090DRBG a(EntropySource entropySource) {
            return new HashSP800DRBG(this.a, this.d, entropySource, this.c, this.b);
        }
    }

    public static class HMacDRBGProvider implements DRBGProvider {
        private final Mac a;
        private final byte[] b;
        private final byte[] c;
        private final int d;

        public HMacDRBGProvider(Mac hMac, byte[] nonce, byte[] personalizationString, int securityStrength) {
            this.a = hMac;
            this.b = nonce;
            this.c = personalizationString;
            this.d = securityStrength;
        }

        public SP80090DRBG a(EntropySource entropySource) {
            return new HMacSP800DRBG(this.a, this.d, entropySource, this.c, this.b);
        }
    }

    public static class CTRDRBGProvider implements DRBGProvider {
        private final BlockCipher a;
        private final int b;
        private final byte[] c;
        private final byte[] d;
        private final int e;

        public SP80090DRBG a(EntropySource entropySource) {
            return new CTRSP800DRBG(this.a, this.b, this.e, entropySource, this.d, this.c);
        }
    }
}
