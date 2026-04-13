package org.spongycastle.pqc.jcajce.provider.mceliece;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.pqc.crypto.mceliece.McElieceCCA2KeyGenerationParameters;
import org.spongycastle.pqc.crypto.mceliece.McElieceCCA2KeyPairGenerator;
import org.spongycastle.pqc.crypto.mceliece.McElieceCCA2Parameters;
import org.spongycastle.pqc.crypto.mceliece.McElieceCCA2PrivateKeyParameters;
import org.spongycastle.pqc.crypto.mceliece.McElieceCCA2PublicKeyParameters;
import org.spongycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;

public class McElieceCCA2KeyPairGeneratorSpi extends KeyPairGenerator {
    private McElieceCCA2KeyPairGenerator a;

    public McElieceCCA2KeyPairGeneratorSpi() {
        super("McEliece-CCA2");
    }

    public void initialize(AlgorithmParameterSpec params) {
        this.a = new McElieceCCA2KeyPairGenerator();
        super.initialize(params);
        McElieceCCA2KeyGenParameterSpec ecc = (McElieceCCA2KeyGenParameterSpec) params;
        this.a.b(new McElieceCCA2KeyGenerationParameters(new SecureRandom(), new McElieceCCA2Parameters(ecc.b(), ecc.c(), ecc.a())));
    }

    public void initialize(int keySize, SecureRandom random) {
        this.a = new McElieceCCA2KeyPairGenerator();
        this.a.b(new McElieceCCA2KeyGenerationParameters(random, new McElieceCCA2Parameters()));
    }

    public KeyPair generateKeyPair() {
        AsymmetricCipherKeyPair generateKeyPair = this.a.a();
        return new KeyPair(new BCMcElieceCCA2PublicKey((McElieceCCA2PublicKeyParameters) generateKeyPair.b()), new BCMcElieceCCA2PrivateKey((McElieceCCA2PrivateKeyParameters) generateKeyPair.a()));
    }
}
