package org.spongycastle.jce.spec;

import java.security.spec.KeySpec;

public class ElGamalKeySpec implements KeySpec {
    private ElGamalParameterSpec c;

    public ElGamalParameterSpec a() {
        return this.c;
    }
}
