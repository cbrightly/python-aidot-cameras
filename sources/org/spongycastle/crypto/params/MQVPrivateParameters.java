package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class MQVPrivateParameters implements CipherParameters {
    private ECPrivateKeyParameters c;
    private ECPrivateKeyParameters d;
    private ECPublicKeyParameters f;

    public MQVPrivateParameters(ECPrivateKeyParameters staticPrivateKey, ECPrivateKeyParameters ephemeralPrivateKey, ECPublicKeyParameters ephemeralPublicKey) {
        if (staticPrivateKey == null) {
            throw new NullPointerException("staticPrivateKey cannot be null");
        } else if (ephemeralPrivateKey != null) {
            ECDomainParameters parameters = staticPrivateKey.b();
            if (parameters.equals(ephemeralPrivateKey.b())) {
                if (ephemeralPublicKey == null) {
                    ephemeralPublicKey = new ECPublicKeyParameters(parameters.b().w(ephemeralPrivateKey.c()), parameters);
                } else if (!parameters.equals(ephemeralPublicKey.b())) {
                    throw new IllegalArgumentException("Ephemeral public key has different domain parameters");
                }
                this.c = staticPrivateKey;
                this.d = ephemeralPrivateKey;
                this.f = ephemeralPublicKey;
                return;
            }
            throw new IllegalArgumentException("Static and ephemeral private keys have different domain parameters");
        } else {
            throw new NullPointerException("ephemeralPrivateKey cannot be null");
        }
    }

    public ECPrivateKeyParameters c() {
        return this.c;
    }

    public ECPrivateKeyParameters a() {
        return this.d;
    }

    public ECPublicKeyParameters b() {
        return this.f;
    }
}
