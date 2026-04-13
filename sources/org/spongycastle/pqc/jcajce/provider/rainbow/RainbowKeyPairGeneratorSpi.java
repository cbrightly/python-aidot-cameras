package org.spongycastle.pqc.jcajce.provider.rainbow;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.pqc.crypto.rainbow.RainbowKeyGenerationParameters;
import org.spongycastle.pqc.crypto.rainbow.RainbowKeyPairGenerator;
import org.spongycastle.pqc.crypto.rainbow.RainbowParameters;
import org.spongycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters;
import org.spongycastle.pqc.crypto.rainbow.RainbowPublicKeyParameters;
import org.spongycastle.pqc.jcajce.spec.RainbowParameterSpec;

public class RainbowKeyPairGeneratorSpi extends KeyPairGenerator {
    RainbowKeyGenerationParameters a;
    RainbowKeyPairGenerator b = new RainbowKeyPairGenerator();
    int c = 1024;
    SecureRandom d = new SecureRandom();
    boolean e = false;

    public RainbowKeyPairGeneratorSpi() {
        super("Rainbow");
    }

    public void initialize(int strength, SecureRandom random) {
        this.c = strength;
        this.d = random;
    }

    public void initialize(AlgorithmParameterSpec params, SecureRandom random) {
        if (params instanceof RainbowParameterSpec) {
            RainbowKeyGenerationParameters rainbowKeyGenerationParameters = new RainbowKeyGenerationParameters(random, new RainbowParameters(((RainbowParameterSpec) params).a()));
            this.a = rainbowKeyGenerationParameters;
            this.b.h(rainbowKeyGenerationParameters);
            this.e = true;
            return;
        }
        throw new InvalidAlgorithmParameterException("parameter object not a RainbowParameterSpec");
    }

    public KeyPair generateKeyPair() {
        if (!this.e) {
            RainbowKeyGenerationParameters rainbowKeyGenerationParameters = new RainbowKeyGenerationParameters(this.d, new RainbowParameters(new RainbowParameterSpec().a()));
            this.a = rainbowKeyGenerationParameters;
            this.b.h(rainbowKeyGenerationParameters);
            this.e = true;
        }
        AsymmetricCipherKeyPair pair = this.b.a();
        return new KeyPair(new BCRainbowPublicKey((RainbowPublicKeyParameters) pair.b()), new BCRainbowPrivateKey((RainbowPrivateKeyParameters) pair.a()));
    }
}
