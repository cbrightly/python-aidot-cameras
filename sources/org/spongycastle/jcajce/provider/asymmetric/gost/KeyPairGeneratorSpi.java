package org.spongycastle.jcajce.provider.asymmetric.gost;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.generators.GOST3410KeyPairGenerator;
import org.spongycastle.crypto.params.GOST3410KeyGenerationParameters;
import org.spongycastle.crypto.params.GOST3410Parameters;
import org.spongycastle.crypto.params.GOST3410PrivateKeyParameters;
import org.spongycastle.crypto.params.GOST3410PublicKeyParameters;
import org.spongycastle.jce.spec.GOST3410ParameterSpec;
import org.spongycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

public class KeyPairGeneratorSpi extends KeyPairGenerator {
    GOST3410KeyGenerationParameters a;
    GOST3410KeyPairGenerator b = new GOST3410KeyPairGenerator();
    GOST3410ParameterSpec c;
    int d = 1024;
    SecureRandom e = null;
    boolean f = false;

    public KeyPairGeneratorSpi() {
        super("GOST3410");
    }

    public void initialize(int strength, SecureRandom random) {
        this.d = strength;
        this.e = random;
    }

    private void a(GOST3410ParameterSpec gParams, SecureRandom random) {
        GOST3410PublicKeyParameterSetSpec spec = gParams.getPublicKeyParameters();
        GOST3410KeyGenerationParameters gOST3410KeyGenerationParameters = new GOST3410KeyGenerationParameters(random, new GOST3410Parameters(spec.b(), spec.c(), spec.a()));
        this.a = gOST3410KeyGenerationParameters;
        this.b.b(gOST3410KeyGenerationParameters);
        this.f = true;
        this.c = gParams;
    }

    public void initialize(AlgorithmParameterSpec params, SecureRandom random) {
        if (params instanceof GOST3410ParameterSpec) {
            a((GOST3410ParameterSpec) params, random);
            return;
        }
        throw new InvalidAlgorithmParameterException("parameter object not a GOST3410ParameterSpec");
    }

    public KeyPair generateKeyPair() {
        if (!this.f) {
            a(new GOST3410ParameterSpec(CryptoProObjectIdentifiers.q.s()), new SecureRandom());
        }
        AsymmetricCipherKeyPair pair = this.b.a();
        return new KeyPair(new BCGOST3410PublicKey((GOST3410PublicKeyParameters) pair.b(), this.c), new BCGOST3410PrivateKey((GOST3410PrivateKeyParameters) pair.a(), this.c));
    }
}
