package org.spongycastle.pqc.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;

public class XMSSMTParameterSpec implements AlgorithmParameterSpec {
    private final int a;
    private final int b;
    private final String c;

    public String c() {
        return this.c;
    }

    public int a() {
        return this.a;
    }

    public int b() {
        return this.b;
    }
}
