package org.spongycastle.pqc.jcajce.provider.newhope;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.pqc.crypto.newhope.NHKeyPairGenerator;
import org.spongycastle.pqc.crypto.newhope.NHPrivateKeyParameters;
import org.spongycastle.pqc.crypto.newhope.NHPublicKeyParameters;

public class NHKeyPairGeneratorSpi extends KeyPairGenerator {
    NHKeyPairGenerator a = new NHKeyPairGenerator();
    SecureRandom b = new SecureRandom();
    boolean c = false;

    public NHKeyPairGeneratorSpi() {
        super("NH");
    }

    public void initialize(int strength, SecureRandom random) {
        if (strength == 1024) {
            this.a.b(new KeyGenerationParameters(random, 1024));
            this.c = true;
            return;
        }
        throw new IllegalArgumentException("strength must be 1024 bits");
    }

    public void initialize(AlgorithmParameterSpec params, SecureRandom random) {
        throw new InvalidAlgorithmParameterException("parameter object not recognised");
    }

    public KeyPair generateKeyPair() {
        if (!this.c) {
            this.a.b(new KeyGenerationParameters(this.b, 1024));
            this.c = true;
        }
        AsymmetricCipherKeyPair pair = this.a.a();
        return new KeyPair(new BCNHPublicKey((NHPublicKeyParameters) pair.b()), new BCNHPrivateKey((NHPrivateKeyParameters) pair.a()));
    }
}
