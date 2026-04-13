package org.spongycastle.crypto.params;

public class GOST3410KeyParameters extends AsymmetricKeyParameter {
    private GOST3410Parameters d;

    public GOST3410KeyParameters(boolean isPrivate, GOST3410Parameters params) {
        super(isPrivate);
        this.d = params;
    }

    public GOST3410Parameters b() {
        return this.d;
    }
}
