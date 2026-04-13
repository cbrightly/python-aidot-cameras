package org.spongycastle.pqc.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;

public class XMSSParameterSpec implements AlgorithmParameterSpec {
    private final int a;
    private final String b;

    public String b() {
        return this.b;
    }

    public int a() {
        return this.a;
    }
}
