package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.DSAKeyGenerationParameters;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAPrivateKeyParameters;
import org.spongycastle.crypto.params.DSAPublicKeyParameters;
import org.spongycastle.math.ec.WNafUtil;
import org.spongycastle.util.BigIntegers;

public class DSAKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private static final BigInteger g = BigInteger.valueOf(1);
    private DSAKeyGenerationParameters h;

    public void d(KeyGenerationParameters param) {
        this.h = (DSAKeyGenerationParameters) param;
    }

    public AsymmetricCipherKeyPair a() {
        DSAParameters dsaParams = this.h.c();
        BigInteger x = c(dsaParams.c(), this.h.a());
        return new AsymmetricCipherKeyPair(new DSAPublicKeyParameters(b(dsaParams.b(), dsaParams.a(), x), dsaParams), new DSAPrivateKeyParameters(x, dsaParams));
    }

    private static BigInteger c(BigInteger q, SecureRandom random) {
        BigInteger x;
        int minWeight = q.bitLength() >>> 2;
        do {
            BigInteger bigInteger = g;
            x = BigIntegers.c(bigInteger, q.subtract(bigInteger), random);
        } while (WNafUtil.e(x) < minWeight);
        return x;
    }

    private static BigInteger b(BigInteger p, BigInteger g2, BigInteger x) {
        return g2.modPow(x, p);
    }
}
