package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class MQVPublicParameters implements CipherParameters {
    private ECPublicKeyParameters c;
    private ECPublicKeyParameters d;

    public MQVPublicParameters(ECPublicKeyParameters staticPublicKey, ECPublicKeyParameters ephemeralPublicKey) {
        if (staticPublicKey == null) {
            throw new NullPointerException("staticPublicKey cannot be null");
        } else if (ephemeralPublicKey == null) {
            throw new NullPointerException("ephemeralPublicKey cannot be null");
        } else if (staticPublicKey.b().equals(ephemeralPublicKey.b())) {
            this.c = staticPublicKey;
            this.d = ephemeralPublicKey;
        } else {
            throw new IllegalArgumentException("Static and ephemeral public keys have different domain parameters");
        }
    }

    public ECPublicKeyParameters b() {
        return this.c;
    }

    public ECPublicKeyParameters a() {
        return this.d;
    }
}
