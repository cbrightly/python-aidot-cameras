package org.spongycastle.jcajce.provider.symmetric.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;

public class BaseKeyGenerator extends KeyGeneratorSpi {
    protected String a;
    protected int b;
    protected int c;
    protected CipherKeyGenerator d;
    protected boolean e = true;

    protected BaseKeyGenerator(String algName, int defaultKeySize, CipherKeyGenerator engine) {
        this.a = algName;
        this.c = defaultKeySize;
        this.b = defaultKeySize;
        this.d = engine;
    }

    /* access modifiers changed from: protected */
    public void engineInit(AlgorithmParameterSpec params, SecureRandom random) {
        throw new InvalidAlgorithmParameterException("Not Implemented");
    }

    /* access modifiers changed from: protected */
    public void engineInit(SecureRandom random) {
        if (random != null) {
            this.d.b(new KeyGenerationParameters(random, this.c));
            this.e = false;
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(int keySize, SecureRandom random) {
        if (random == null) {
            try {
                random = new SecureRandom();
            } catch (IllegalArgumentException e2) {
                throw new InvalidParameterException(e2.getMessage());
            }
        }
        this.d.b(new KeyGenerationParameters(random, keySize));
        this.e = false;
    }

    /* access modifiers changed from: protected */
    public SecretKey engineGenerateKey() {
        if (this.e) {
            this.d.b(new KeyGenerationParameters(new SecureRandom(), this.c));
            this.e = false;
        }
        return new SecretKeySpec(this.d.a(), this.a);
    }
}
