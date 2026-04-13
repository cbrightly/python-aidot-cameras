package org.spongycastle.pqc.crypto.xmss;

import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.pqc.crypto.xmss.OTSHashAddress;
import org.spongycastle.pqc.crypto.xmss.XMSSMTPrivateKeyParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSMTPublicKeyParameters;

public final class XMSSMTKeyPairGenerator {
    private XMSSMTParameters a;
    private XMSSParameters b;
    private SecureRandom c;

    public void c(KeyGenerationParameters param) {
        XMSSMTKeyGenerationParameters parameters = (XMSSMTKeyGenerationParameters) param;
        this.c = parameters.a();
        XMSSMTParameters c2 = parameters.c();
        this.a = c2;
        this.b = c2.h();
    }

    public AsymmetricCipherKeyPair a() {
        XMSSMTPrivateKeyParameters privateKey = b(new XMSSMTPrivateKeyParameters.Builder(this.a).j().b());
        this.b.f().j(new byte[this.a.b()], privateKey.f());
        int rootLayerIndex = this.a.d() - 1;
        BDS bdsRoot = new BDS(this.b, privateKey.f(), privateKey.i(), (OTSHashAddress) ((OTSHashAddress.Builder) new OTSHashAddress.Builder().g(rootLayerIndex)).l());
        XMSSNode root = bdsRoot.getRoot();
        privateKey.b().put(rootLayerIndex, bdsRoot);
        XMSSMTPrivateKeyParameters privateKey2 = new XMSSMTPrivateKeyParameters.Builder(this.a).p(privateKey.i()).o(privateKey.h()).m(privateKey.f()).n(root.getValue()).k(privateKey.b()).j();
        return new AsymmetricCipherKeyPair(new XMSSMTPublicKeyParameters.Builder(this.a).g(root.getValue()).f(privateKey2.f()).e(), privateKey2);
    }

    private XMSSMTPrivateKeyParameters b(BDSStateMap bdsState) {
        int n = this.a.b();
        byte[] secretKeySeed = new byte[n];
        this.c.nextBytes(secretKeySeed);
        byte[] secretKeyPRF = new byte[n];
        this.c.nextBytes(secretKeyPRF);
        byte[] publicSeed = new byte[n];
        this.c.nextBytes(publicSeed);
        return new XMSSMTPrivateKeyParameters.Builder(this.a).p(secretKeySeed).o(secretKeyPRF).m(publicSeed).k(bdsState).j();
    }
}
