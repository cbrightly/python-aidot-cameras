package org.spongycastle.crypto.params;

public class DSAKeyParameters extends AsymmetricKeyParameter {
    private DSAParameters d;

    public DSAKeyParameters(boolean isPrivate, DSAParameters params) {
        super(isPrivate);
        this.d = params;
    }

    public DSAParameters b() {
        return this.d;
    }
}
