package org.spongycastle.pqc.crypto.rainbow;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public class RainbowKeyParameters extends AsymmetricKeyParameter {
    private int d;

    public RainbowKeyParameters(boolean isPrivate, int docLength) {
        super(isPrivate);
        this.d = docLength;
    }

    public int b() {
        return this.d;
    }
}
