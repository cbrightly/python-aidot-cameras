package org.spongycastle.pqc.crypto.gmss;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public class GMSSKeyParameters extends AsymmetricKeyParameter {
    private GMSSParameters d;

    public GMSSKeyParameters(boolean isPrivate, GMSSParameters params) {
        super(isPrivate);
        this.d = params;
    }

    public GMSSParameters b() {
        return this.d;
    }
}
