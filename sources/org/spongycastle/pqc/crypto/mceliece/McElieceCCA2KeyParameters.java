package org.spongycastle.pqc.crypto.mceliece;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public class McElieceCCA2KeyParameters extends AsymmetricKeyParameter {
    private String d;

    public McElieceCCA2KeyParameters(boolean isPrivate, String params) {
        super(isPrivate);
        this.d = params;
    }

    public String b() {
        return this.d;
    }
}
