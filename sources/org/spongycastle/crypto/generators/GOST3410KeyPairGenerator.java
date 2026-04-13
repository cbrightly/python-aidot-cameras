package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.GOST3410KeyGenerationParameters;
import org.spongycastle.crypto.params.GOST3410Parameters;
import org.spongycastle.crypto.params.GOST3410PrivateKeyParameters;
import org.spongycastle.crypto.params.GOST3410PublicKeyParameters;
import org.spongycastle.math.ec.WNafUtil;

public class GOST3410KeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private GOST3410KeyGenerationParameters g;

    public void b(KeyGenerationParameters param) {
        this.g = (GOST3410KeyGenerationParameters) param;
    }

    public AsymmetricCipherKeyPair a() {
        GOST3410Parameters GOST3410Params = this.g.c();
        SecureRandom random = this.g.a();
        BigInteger q = GOST3410Params.c();
        BigInteger p = GOST3410Params.b();
        BigInteger a = GOST3410Params.a();
        while (true) {
            BigInteger x = new BigInteger(256, random);
            if (x.signum() >= 1 && x.compareTo(q) < 0 && WNafUtil.e(x) >= 64) {
                return new AsymmetricCipherKeyPair(new GOST3410PublicKeyParameters(a.modPow(x, p), GOST3410Params), new GOST3410PrivateKeyParameters(x, GOST3410Params));
            }
        }
    }
}
