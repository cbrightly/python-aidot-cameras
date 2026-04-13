package org.spongycastle.jcajce.provider.asymmetric.dsa;

import com.alibaba.fastjson.asm.Opcodes;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;
import java.util.Hashtable;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.generators.DSAKeyPairGenerator;
import org.spongycastle.crypto.generators.DSAParametersGenerator;
import org.spongycastle.crypto.params.DSAKeyGenerationParameters;
import org.spongycastle.crypto.params.DSAParameterGenerationParameters;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAPrivateKeyParameters;
import org.spongycastle.crypto.params.DSAPublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator;
import org.spongycastle.util.Integers;
import org.spongycastle.util.Properties;

public class KeyPairGeneratorSpi extends KeyPairGenerator {
    private static Hashtable a = new Hashtable();
    private static Object b = new Object();
    DSAKeyGenerationParameters c;
    DSAKeyPairGenerator d = new DSAKeyPairGenerator();
    int e = 2048;
    SecureRandom f = new SecureRandom();
    boolean g = false;

    public KeyPairGeneratorSpi() {
        super("DSA");
    }

    public void initialize(int strength, SecureRandom random) {
        if (strength < 512 || strength > 4096 || ((strength < 1024 && strength % 64 != 0) || (strength >= 1024 && strength % 1024 != 0))) {
            throw new InvalidParameterException("strength must be from 512 - 4096 and a multiple of 1024 above 1024");
        }
        this.e = strength;
        this.f = random;
        this.g = false;
    }

    public void initialize(AlgorithmParameterSpec params, SecureRandom random) {
        if (params instanceof DSAParameterSpec) {
            DSAParameterSpec dsaParams = (DSAParameterSpec) params;
            DSAKeyGenerationParameters dSAKeyGenerationParameters = new DSAKeyGenerationParameters(random, new DSAParameters(dsaParams.getP(), dsaParams.getQ(), dsaParams.getG()));
            this.c = dSAKeyGenerationParameters;
            this.d.d(dSAKeyGenerationParameters);
            this.g = true;
            return;
        }
        throw new InvalidAlgorithmParameterException("parameter object not a DSAParameterSpec");
    }

    public KeyPair generateKeyPair() {
        DSAParametersGenerator pGen;
        if (!this.g) {
            Integer paramStrength = Integers.b(this.e);
            if (a.containsKey(paramStrength)) {
                this.c = (DSAKeyGenerationParameters) a.get(paramStrength);
            } else {
                synchronized (b) {
                    if (a.containsKey(paramStrength)) {
                        this.c = (DSAKeyGenerationParameters) a.get(paramStrength);
                    } else {
                        int certainty = PrimeCertaintyCalculator.a(this.e);
                        int i = this.e;
                        if (i == 1024) {
                            pGen = new DSAParametersGenerator();
                            if (Properties.d("org.spongycastle.dsa.FIPS186-2for1024bits")) {
                                pGen.k(this.e, certainty, this.f);
                            } else {
                                pGen.l(new DSAParameterGenerationParameters(1024, Opcodes.IF_ICMPNE, certainty, this.f));
                            }
                        } else if (i > 1024) {
                            DSAParameterGenerationParameters dsaParams = new DSAParameterGenerationParameters(i, 256, certainty, this.f);
                            DSAParametersGenerator pGen2 = new DSAParametersGenerator(new SHA256Digest());
                            pGen2.l(dsaParams);
                            pGen = pGen2;
                        } else {
                            pGen = new DSAParametersGenerator();
                            pGen.k(this.e, certainty, this.f);
                        }
                        DSAKeyGenerationParameters dSAKeyGenerationParameters = new DSAKeyGenerationParameters(this.f, pGen.d());
                        this.c = dSAKeyGenerationParameters;
                        a.put(paramStrength, dSAKeyGenerationParameters);
                    }
                }
            }
            this.d.d(this.c);
            this.g = true;
        }
        AsymmetricCipherKeyPair pair = this.d.a();
        return new KeyPair(new BCDSAPublicKey((DSAPublicKeyParameters) pair.b()), new BCDSAPrivateKey((DSAPrivateKeyParameters) pair.a()));
    }
}
