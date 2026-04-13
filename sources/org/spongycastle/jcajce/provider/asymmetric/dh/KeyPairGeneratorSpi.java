package org.spongycastle.jcajce.provider.asymmetric.dh;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Hashtable;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.generators.DHBasicKeyPairGenerator;
import org.spongycastle.crypto.generators.DHParametersGenerator;
import org.spongycastle.crypto.params.DHKeyGenerationParameters;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.util.Integers;

public class KeyPairGeneratorSpi extends KeyPairGenerator {
    private static Hashtable a = new Hashtable();
    private static Object b = new Object();
    DHKeyGenerationParameters c;
    DHBasicKeyPairGenerator d = new DHBasicKeyPairGenerator();
    int e = 2048;
    SecureRandom f = new SecureRandom();
    boolean g = false;

    public KeyPairGeneratorSpi() {
        super("DH");
    }

    public void initialize(int strength, SecureRandom random) {
        this.e = strength;
        this.f = random;
        this.g = false;
    }

    public void initialize(AlgorithmParameterSpec params, SecureRandom random) {
        if (params instanceof DHParameterSpec) {
            DHParameterSpec dhParams = (DHParameterSpec) params;
            DHKeyGenerationParameters dHKeyGenerationParameters = new DHKeyGenerationParameters(random, new DHParameters(dhParams.getP(), dhParams.getG(), (BigInteger) null, dhParams.getL()));
            this.c = dHKeyGenerationParameters;
            this.d.b(dHKeyGenerationParameters);
            this.g = true;
            return;
        }
        throw new InvalidAlgorithmParameterException("parameter object not a DHParameterSpec");
    }

    public KeyPair generateKeyPair() {
        if (!this.g) {
            Integer paramStrength = Integers.b(this.e);
            if (a.containsKey(paramStrength)) {
                this.c = (DHKeyGenerationParameters) a.get(paramStrength);
            } else {
                DHParameterSpec dhParams = BouncyCastleProvider.CONFIGURATION.d(this.e);
                if (dhParams != null) {
                    this.c = new DHKeyGenerationParameters(this.f, new DHParameters(dhParams.getP(), dhParams.getG(), (BigInteger) null, dhParams.getL()));
                } else {
                    synchronized (b) {
                        if (a.containsKey(paramStrength)) {
                            this.c = (DHKeyGenerationParameters) a.get(paramStrength);
                        } else {
                            DHParametersGenerator pGen = new DHParametersGenerator();
                            int i = this.e;
                            pGen.b(i, PrimeCertaintyCalculator.a(i), this.f);
                            DHKeyGenerationParameters dHKeyGenerationParameters = new DHKeyGenerationParameters(this.f, pGen.a());
                            this.c = dHKeyGenerationParameters;
                            a.put(paramStrength, dHKeyGenerationParameters);
                        }
                    }
                }
            }
            this.d.b(this.c);
            this.g = true;
        }
        AsymmetricCipherKeyPair pair = this.d.a();
        return new KeyPair(new BCDHPublicKey((DHPublicKeyParameters) pair.b()), new BCDHPrivateKey((DHPrivateKeyParameters) pair.a()));
    }
}
