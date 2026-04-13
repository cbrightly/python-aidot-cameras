package org.spongycastle.jce.spec;

import java.security.spec.KeySpec;

public class ECKeySpec implements KeySpec {
    private ECParameterSpec c;

    protected ECKeySpec(ECParameterSpec spec) {
        this.c = spec;
    }

    public ECParameterSpec a() {
        return this.c;
    }
}
