package org.spongycastle.jce.spec;

import java.security.PublicKey;
import java.security.spec.KeySpec;
import org.spongycastle.jce.interfaces.MQVPublicKey;

public class MQVPublicKeySpec implements KeySpec, MQVPublicKey {
    private PublicKey ephemeralKey;
    private PublicKey staticKey;

    public MQVPublicKeySpec(PublicKey staticKey2, PublicKey ephemeralKey2) {
        this.staticKey = staticKey2;
        this.ephemeralKey = ephemeralKey2;
    }

    public PublicKey getStaticKey() {
        return this.staticKey;
    }

    public PublicKey getEphemeralKey() {
        return this.ephemeralKey;
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
