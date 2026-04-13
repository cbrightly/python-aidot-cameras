package org.spongycastle.jcajce.spec;

import javax.crypto.SecretKey;

public class RepeatedSecretKeySpec implements SecretKey {
    private String algorithm;

    public RepeatedSecretKeySpec(String algorithm2) {
        this.algorithm = algorithm2;
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public String getFormat() {
        return null;
    }

    public byte[] getEncoded() {
        return null;
    }
}
