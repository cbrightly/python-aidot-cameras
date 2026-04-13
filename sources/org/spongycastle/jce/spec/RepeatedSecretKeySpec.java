package org.spongycastle.jce.spec;

public class RepeatedSecretKeySpec extends org.spongycastle.jcajce.spec.RepeatedSecretKeySpec {
    private String algorithm;

    public RepeatedSecretKeySpec(String algorithm2) {
        super(algorithm2);
    }
}
