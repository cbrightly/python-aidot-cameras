package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;

public class DSTU4145KeyPairGenerator extends ECKeyPairGenerator {
    public AsymmetricCipherKeyPair a() {
        AsymmetricCipherKeyPair pair = super.a();
        ECPublicKeyParameters pub2 = (ECPublicKeyParameters) pair.b();
        return new AsymmetricCipherKeyPair(new ECPublicKeyParameters(pub2.c().x(), pub2.b()), (ECPrivateKeyParameters) pair.a());
    }
}
