package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.ElGamalKeyGenerationParameters;
import org.spongycastle.crypto.params.ElGamalParameters;
import org.spongycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.spongycastle.crypto.params.ElGamalPublicKeyParameters;

public class ElGamalKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private ElGamalKeyGenerationParameters g;

    public void b(KeyGenerationParameters param) {
        this.g = (ElGamalKeyGenerationParameters) param;
    }

    public AsymmetricCipherKeyPair a() {
        DHKeyGeneratorHelper helper = DHKeyGeneratorHelper.a;
        ElGamalParameters egp = this.g.c();
        DHParameters dhp = new DHParameters(egp.c(), egp.a(), (BigInteger) null, egp.b());
        BigInteger x = helper.a(dhp, this.g.a());
        return new AsymmetricCipherKeyPair(new ElGamalPublicKeyParameters(helper.b(dhp, x), egp), new ElGamalPrivateKeyParameters(x, egp));
    }
}
