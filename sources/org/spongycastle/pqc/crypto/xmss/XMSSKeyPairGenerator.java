package org.spongycastle.pqc.crypto.xmss;

import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.pqc.crypto.xmss.OTSHashAddress;
import org.spongycastle.pqc.crypto.xmss.XMSSPrivateKeyParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSPublicKeyParameters;

public final class XMSSKeyPairGenerator {
    private XMSSParameters a;
    private SecureRandom b;

    public void c(KeyGenerationParameters param) {
        XMSSKeyGenerationParameters parameters = (XMSSKeyGenerationParameters) param;
        this.b = parameters.a();
        this.a = parameters.c();
    }

    public AsymmetricCipherKeyPair a() {
        XMSSPrivateKeyParameters privateKey = b(this.a, this.b);
        XMSSNode root = privateKey.b().getRoot();
        XMSSPrivateKeyParameters privateKey2 = new XMSSPrivateKeyParameters.Builder(this.a).p(privateKey.i()).o(privateKey.h()).m(privateKey.f()).n(root.getValue()).k(privateKey.b()).j();
        return new AsymmetricCipherKeyPair(new XMSSPublicKeyParameters.Builder(this.a).g(root.getValue()).f(privateKey2.f()).e(), privateKey2);
    }

    private XMSSPrivateKeyParameters b(XMSSParameters params, SecureRandom prng) {
        int n = params.c();
        byte[] secretKeySeed = new byte[n];
        prng.nextBytes(secretKeySeed);
        byte[] secretKeyPRF = new byte[n];
        prng.nextBytes(secretKeyPRF);
        byte[] publicSeed = new byte[n];
        prng.nextBytes(publicSeed);
        return new XMSSPrivateKeyParameters.Builder(params).p(secretKeySeed).o(secretKeyPRF).m(publicSeed).k(new BDS(params, publicSeed, secretKeySeed, (OTSHashAddress) new OTSHashAddress.Builder().l())).j();
    }
}
