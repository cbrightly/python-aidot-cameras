package org.spongycastle.jce.spec;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import org.spongycastle.jce.interfaces.MQVPrivateKey;

public class MQVPrivateKeySpec implements KeySpec, MQVPrivateKey {
    private PrivateKey ephemeralPrivateKey;
    private PublicKey ephemeralPublicKey;
    private PrivateKey staticPrivateKey;

    public MQVPrivateKeySpec(PrivateKey staticPrivateKey2, PrivateKey ephemeralPrivateKey2) {
        this(staticPrivateKey2, ephemeralPrivateKey2, (PublicKey) null);
    }

    public MQVPrivateKeySpec(PrivateKey staticPrivateKey2, PrivateKey ephemeralPrivateKey2, PublicKey ephemeralPublicKey2) {
        this.staticPrivateKey = staticPrivateKey2;
        this.ephemeralPrivateKey = ephemeralPrivateKey2;
        this.ephemeralPublicKey = ephemeralPublicKey2;
    }

    public PrivateKey getStaticPrivateKey() {
        return this.staticPrivateKey;
    }

    public PrivateKey getEphemeralPrivateKey() {
        return this.ephemeralPrivateKey;
    }

    public PublicKey getEphemeralPublicKey() {
        return this.ephemeralPublicKey;
    }

    public String getAlgorithm() {
        return "ECMQV";
    }

    public String getFormat() {
        return null;
    }

    public byte[] getEncoded() {
        return null;
    }
}
