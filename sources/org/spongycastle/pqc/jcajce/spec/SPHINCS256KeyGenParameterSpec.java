package org.spongycastle.pqc.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;

public class SPHINCS256KeyGenParameterSpec implements AlgorithmParameterSpec {
    private final String a;

    public SPHINCS256KeyGenParameterSpec() {
        this("SHA512-256");
    }

    public SPHINCS256KeyGenParameterSpec(String treeHash) {
        this.a = treeHash;
    }

    public String a() {
        return this.a;
    }
}
