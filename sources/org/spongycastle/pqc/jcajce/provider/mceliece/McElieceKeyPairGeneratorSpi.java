package org.spongycastle.pqc.jcajce.provider.mceliece;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.pqc.crypto.mceliece.McElieceKeyGenerationParameters;
import org.spongycastle.pqc.crypto.mceliece.McElieceKeyPairGenerator;
import org.spongycastle.pqc.crypto.mceliece.McElieceParameters;
import org.spongycastle.pqc.crypto.mceliece.McEliecePrivateKeyParameters;
import org.spongycastle.pqc.crypto.mceliece.McEliecePublicKeyParameters;
import org.spongycastle.pqc.jcajce.spec.McElieceKeyGenParameterSpec;

public class McElieceKeyPairGeneratorSpi extends KeyPairGenerator {
    McElieceKeyPairGenerator a;

    public McElieceKeyPairGeneratorSpi() {
        super("McEliece");
    }

    public void initialize(AlgorithmParameterSpec params) {
        this.a = new McElieceKeyPairGenerator();
        super.initialize(params);
        McElieceKeyGenParameterSpec ecc = (McElieceKeyGenParameterSpec) params;
        this.a.c(new McElieceKeyGenerationParameters(new SecureRandom(), new McElieceParameters(ecc.a(), ecc.b())));
    }

    public void initialize(int keySize, SecureRandom random) {
        try {
            initialize(new McElieceKeyGenParameterSpec());
        } catch (InvalidAlgorithmParameterException e) {
        }
    }

    public KeyPair generateKeyPair() {
        AsymmetricCipherKeyPair generateKeyPair = this.a.a();
        return new KeyPair(new BCMcEliecePublicKey((McEliecePublicKeyParameters) generateKeyPair.b()), new BCMcEliecePrivateKey((McEliecePrivateKeyParameters) generateKeyPair.a()));
    }
}
