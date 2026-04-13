package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.params.CramerShoupKeyGenerationParameters;
import org.spongycastle.crypto.params.CramerShoupParameters;
import org.spongycastle.crypto.params.CramerShoupPrivateKeyParameters;
import org.spongycastle.crypto.params.CramerShoupPublicKeyParameters;
import org.spongycastle.util.BigIntegers;

public class CramerShoupKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private static final BigInteger g = BigInteger.valueOf(1);
    private CramerShoupKeyGenerationParameters h;

    public AsymmetricCipherKeyPair a() {
        CramerShoupParameters csParams = this.h.c();
        CramerShoupPrivateKeyParameters sk = c(this.h.a(), csParams);
        CramerShoupPublicKeyParameters pk = b(csParams, sk);
        sk.h(pk);
        return new AsymmetricCipherKeyPair(pk, sk);
    }

    private BigInteger d(BigInteger p, SecureRandom random) {
        BigInteger bigInteger = g;
        return BigIntegers.c(bigInteger, p.subtract(bigInteger), random);
    }

    private CramerShoupPrivateKeyParameters c(SecureRandom random, CramerShoupParameters csParams) {
        BigInteger p = csParams.c();
        return new CramerShoupPrivateKeyParameters(csParams, d(p, random), d(p, random), d(p, random), d(p, random), d(p, random));
    }

    private CramerShoupPublicKeyParameters b(CramerShoupParameters csParams, CramerShoupPrivateKeyParameters sk) {
        BigInteger g1 = csParams.a();
        BigInteger g2 = csParams.b();
        BigInteger p = csParams.c();
        return new CramerShoupPublicKeyParameters(csParams, g1.modPow(sk.c(), p).multiply(g2.modPow(sk.d(), p)), g1.modPow(sk.e(), p).multiply(g2.modPow(sk.f(), p)), g1.modPow(sk.g(), p));
    }
}
