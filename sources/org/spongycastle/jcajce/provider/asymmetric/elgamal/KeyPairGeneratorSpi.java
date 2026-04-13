package org.spongycastle.jcajce.provider.asymmetric.elgamal;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.generators.ElGamalKeyPairGenerator;
import org.spongycastle.crypto.generators.ElGamalParametersGenerator;
import org.spongycastle.crypto.params.ElGamalKeyGenerationParameters;
import org.spongycastle.crypto.params.ElGamalParameters;
import org.spongycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.spongycastle.crypto.params.ElGamalPublicKeyParameters;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ElGamalParameterSpec;

public class KeyPairGeneratorSpi extends KeyPairGenerator {
    ElGamalKeyGenerationParameters a;
    ElGamalKeyPairGenerator b = new ElGamalKeyPairGenerator();
    int c = 1024;
    int d = 20;
    SecureRandom e = new SecureRandom();
    boolean f = false;

    public KeyPairGeneratorSpi() {
        super("ElGamal");
    }

    public void initialize(int strength, SecureRandom random) {
        this.c = strength;
        this.e = random;
    }

    public void initialize(AlgorithmParameterSpec params, SecureRandom random) {
        if ((params instanceof ElGamalParameterSpec) || (params instanceof DHParameterSpec)) {
            if (params instanceof ElGamalParameterSpec) {
                ElGamalParameterSpec elParams = (ElGamalParameterSpec) params;
                this.a = new ElGamalKeyGenerationParameters(random, new ElGamalParameters(elParams.b(), elParams.a()));
            } else {
                DHParameterSpec dhParams = (DHParameterSpec) params;
                this.a = new ElGamalKeyGenerationParameters(random, new ElGamalParameters(dhParams.getP(), dhParams.getG(), dhParams.getL()));
            }
            this.b.b(this.a);
            this.f = true;
            return;
        }
        throw new InvalidAlgorithmParameterException("parameter object not a DHParameterSpec or an ElGamalParameterSpec");
    }

    public KeyPair generateKeyPair() {
        if (!this.f) {
            DHParameterSpec dhParams = BouncyCastleProvider.CONFIGURATION.d(this.c);
            if (dhParams != null) {
                this.a = new ElGamalKeyGenerationParameters(this.e, new ElGamalParameters(dhParams.getP(), dhParams.getG(), dhParams.getL()));
            } else {
                ElGamalParametersGenerator pGen = new ElGamalParametersGenerator();
                pGen.b(this.c, this.d, this.e);
                this.a = new ElGamalKeyGenerationParameters(this.e, pGen.a());
            }
            this.b.b(this.a);
            this.f = true;
        }
        AsymmetricCipherKeyPair pair = this.b.a();
        return new KeyPair(new BCElGamalPublicKey((ElGamalPublicKeyParameters) pair.b()), new BCElGamalPrivateKey((ElGamalPrivateKeyParameters) pair.a()));
    }
}
