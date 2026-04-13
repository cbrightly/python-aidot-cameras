package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.RSAKeyGenParameterSpec;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.generators.RSAKeyPairGenerator;
import org.spongycastle.crypto.params.RSAKeyGenerationParameters;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator;

public class KeyPairGeneratorSpi extends KeyPairGenerator {
    static final BigInteger a = BigInteger.valueOf(65537);
    RSAKeyGenerationParameters b;
    RSAKeyPairGenerator c = new RSAKeyPairGenerator();

    public KeyPairGeneratorSpi() {
        super("RSA");
        RSAKeyGenerationParameters rSAKeyGenerationParameters = new RSAKeyGenerationParameters(a, new SecureRandom(), 2048, PrimeCertaintyCalculator.a(2048));
        this.b = rSAKeyGenerationParameters;
        this.c.d(rSAKeyGenerationParameters);
    }

    public void initialize(int strength, SecureRandom random) {
        RSAKeyGenerationParameters rSAKeyGenerationParameters = new RSAKeyGenerationParameters(a, random, strength, PrimeCertaintyCalculator.a(strength));
        this.b = rSAKeyGenerationParameters;
        this.c.d(rSAKeyGenerationParameters);
    }

    public void initialize(AlgorithmParameterSpec params, SecureRandom random) {
        if (params instanceof RSAKeyGenParameterSpec) {
            RSAKeyGenParameterSpec rsaParams = (RSAKeyGenParameterSpec) params;
            RSAKeyGenerationParameters rSAKeyGenerationParameters = new RSAKeyGenerationParameters(rsaParams.getPublicExponent(), random, rsaParams.getKeysize(), PrimeCertaintyCalculator.a(2048));
            this.b = rSAKeyGenerationParameters;
            this.c.d(rSAKeyGenerationParameters);
            return;
        }
        throw new InvalidAlgorithmParameterException("parameter object not a RSAKeyGenParameterSpec");
    }

    public KeyPair generateKeyPair() {
        AsymmetricCipherKeyPair pair = this.c.a();
        return new KeyPair(new BCRSAPublicKey((RSAKeyParameters) pair.b()), new BCRSAPrivateCrtKey((RSAPrivateCrtKeyParameters) pair.a()));
    }
}
