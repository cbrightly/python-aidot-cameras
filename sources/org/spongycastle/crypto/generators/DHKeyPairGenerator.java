package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.DHKeyGenerationParameters;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;

public class DHKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private DHKeyGenerationParameters g;

    public void b(KeyGenerationParameters param) {
        this.g = (DHKeyGenerationParameters) param;
    }

    public AsymmetricCipherKeyPair a() {
        DHKeyGeneratorHelper helper = DHKeyGeneratorHelper.a;
        DHParameters dhp = this.g.c();
        BigInteger x = helper.a(dhp, this.g.a());
        return new AsymmetricCipherKeyPair(new DHPublicKeyParameters(helper.b(dhp, x), dhp), new DHPrivateKeyParameters(x, dhp));
    }
}
