package org.spongycastle.jcajce.provider.drbg;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.SecureRandomSpi;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.prng.EntropySource;
import org.spongycastle.crypto.prng.EntropySourceProvider;
import org.spongycastle.crypto.prng.SP800SecureRandom;
import org.spongycastle.crypto.prng.SP800SecureRandomBuilder;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.ClassUtil;
import org.spongycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;
import org.spongycastle.util.Strings;

public class DRBG {
    /* access modifiers changed from: private */
    public static final String a = DRBG.class.getName();
    private static final String[][] b = {new String[]{"sun.security.provider.Sun", "sun.security.provider.SecureRandom"}, new String[]{"org.apache.harmony.security.provider.crypto.CryptoProvider", "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl"}, new String[]{"com.android.org.conscrypt.OpenSSLProvider", "com.android.org.conscrypt.OpenSSLRandom"}, new String[]{"org.conscrypt.OpenSSLProvider", "org.conscrypt.OpenSSLRandom"}};
    /* access modifiers changed from: private */
    public static final Object[] c = h();

    private static final Object[] h() {
        int t = 0;
        while (true) {
            String[][] strArr = b;
            if (t >= strArr.length) {
                return null;
            }
            String[] pair = strArr[t];
            try {
                return new Object[]{Class.forName(pair[0]).newInstance(), Class.forName(pair[1]).newInstance()};
            } catch (Throwable th) {
                t++;
            }
        }
    }

    public static class CoreSecureRandom extends SecureRandom {
        CoreSecureRandom() {
            super((SecureRandomSpi) DRBG.c[1], (Provider) DRBG.c[0]);
        }
    }

    /* access modifiers changed from: private */
    public static SecureRandom g() {
        if (c != null) {
            return new CoreSecureRandom();
        }
        return new SecureRandom();
    }

    private static EntropySourceProvider f() {
        final String sourceClass = System.getProperty("org.spongycastle.drbg.entropysource");
        return (EntropySourceProvider) AccessController.doPrivileged(new PrivilegedAction<EntropySourceProvider>() {
            /* renamed from: a */
            public EntropySourceProvider run() {
                try {
                    return (EntropySourceProvider) ClassUtil.a(DRBG.class, sourceClass).newInstance();
                } catch (Exception e) {
                    throw new IllegalStateException("entropy source " + sourceClass + " not created: " + e.getMessage(), e);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static SecureRandom e(boolean isPredictionResistant) {
        byte[] personalisationString;
        byte[] personalisationString2;
        if (System.getProperty("org.spongycastle.drbg.entropysource") != null) {
            EntropySourceProvider entropyProvider = f();
            EntropySource initSource = entropyProvider.get(128);
            if (isPredictionResistant) {
                personalisationString2 = i(initSource.a());
            } else {
                personalisationString2 = j(initSource.a());
            }
            return new SP800SecureRandomBuilder(entropyProvider).c(personalisationString2).b(new SHA512Digest(), Arrays.r(initSource.a(), initSource.a()), isPredictionResistant);
        }
        SecureRandom randomSource = new HybridSecureRandom();
        if (isPredictionResistant) {
            personalisationString = i(randomSource.generateSeed(16));
        } else {
            personalisationString = j(randomSource.generateSeed(16));
        }
        return new SP800SecureRandomBuilder(randomSource, true).c(personalisationString).b(new SHA512Digest(), randomSource.generateSeed(32), isPredictionResistant);
    }

    public static class Default extends SecureRandomSpi {
        private static final SecureRandom c = DRBG.e(true);

        /* access modifiers changed from: protected */
        public void engineSetSeed(byte[] bytes) {
            c.setSeed(bytes);
        }

        /* access modifiers changed from: protected */
        public void engineNextBytes(byte[] bytes) {
            c.nextBytes(bytes);
        }

        /* access modifiers changed from: protected */
        public byte[] engineGenerateSeed(int numBytes) {
            return c.generateSeed(numBytes);
        }
    }

    public static class NonceAndIV extends SecureRandomSpi {
        private static final SecureRandom c = DRBG.e(false);

        /* access modifiers changed from: protected */
        public void engineSetSeed(byte[] bytes) {
            c.setSeed(bytes);
        }

        /* access modifiers changed from: protected */
        public void engineNextBytes(byte[] bytes) {
            c.nextBytes(bytes);
        }

        /* access modifiers changed from: protected */
        public byte[] engineGenerateSeed(int numBytes) {
            return c.generateSeed(numBytes);
        }
    }

    public static class Mappings extends AsymmetricAlgorithmProvider {
        public void a(ConfigurableProvider provider) {
            provider.addAlgorithm("SecureRandom.DEFAULT", DRBG.a + "$Default");
            provider.addAlgorithm("SecureRandom.NONCEANDIV", DRBG.a + "$NonceAndIV");
        }
    }

    private static byte[] i(byte[] seed) {
        return Arrays.t(Strings.f("Default"), seed, Pack.q(Thread.currentThread().getId()), Pack.q(System.currentTimeMillis()));
    }

    private static byte[] j(byte[] seed) {
        return Arrays.t(Strings.f("Nonce"), seed, Pack.u(Thread.currentThread().getId()), Pack.u(System.currentTimeMillis()));
    }

    public static class HybridSecureRandom extends SecureRandom {
        /* access modifiers changed from: private */
        public final SecureRandom baseRandom;
        private final SP800SecureRandom drbg;
        private final AtomicInteger samples = new AtomicInteger(0);
        /* access modifiers changed from: private */
        public final AtomicBoolean seedAvailable = new AtomicBoolean(false);

        HybridSecureRandom() {
            super((SecureRandomSpi) null, (Provider) null);
            SecureRandom d = DRBG.g();
            this.baseRandom = d;
            this.drbg = new SP800SecureRandomBuilder(new EntropySourceProvider() {
                public EntropySource get(int bitsRequired) {
                    return new SignallingEntropySource(bitsRequired);
                }
            }).c(Strings.f("Bouncy Castle Hybrid Entropy Source")).a(new HMac(new SHA512Digest()), d.generateSeed(32), false);
        }

        public void setSeed(byte[] seed) {
            SP800SecureRandom sP800SecureRandom = this.drbg;
            if (sP800SecureRandom != null) {
                sP800SecureRandom.setSeed(seed);
            }
        }

        public void setSeed(long seed) {
            SP800SecureRandom sP800SecureRandom = this.drbg;
            if (sP800SecureRandom != null) {
                sP800SecureRandom.setSeed(seed);
            }
        }

        public byte[] generateSeed(int numBytes) {
            byte[] data = new byte[numBytes];
            if (this.samples.getAndIncrement() > 20 && this.seedAvailable.getAndSet(false)) {
                this.samples.set(0);
                this.drbg.reseed((byte[]) null);
            }
            this.drbg.nextBytes(data);
            return data;
        }

        public class SignallingEntropySource implements EntropySource {
            private final int a;
            /* access modifiers changed from: private */
            public final AtomicReference b = new AtomicReference();
            private final AtomicBoolean c = new AtomicBoolean(false);

            SignallingEntropySource(int bitsRequired) {
                this.a = (bitsRequired + 7) / 8;
            }

            public byte[] a() {
                byte[] seed = (byte[]) this.b.getAndSet((Object) null);
                if (seed == null || seed.length != this.a) {
                    seed = HybridSecureRandom.this.baseRandom.generateSeed(this.a);
                } else {
                    this.c.set(false);
                }
                if (!this.c.getAndSet(true)) {
                    new Thread(new EntropyGatherer(this.a)).start();
                }
                return seed;
            }

            public int b() {
                return this.a * 8;
            }

            public class EntropyGatherer implements Runnable {
                private final int c;

                EntropyGatherer(int numBytes) {
                    this.c = numBytes;
                }

                public void run() {
                    SignallingEntropySource.this.b.set(HybridSecureRandom.this.baseRandom.generateSeed(this.c));
                    HybridSecureRandom.this.seedAvailable.set(true);
                }
            }
        }
    }
}
