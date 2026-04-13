package org.spongycastle.crypto.params;

public class ECKeyParameters extends AsymmetricKeyParameter {
    ECDomainParameters d;

    protected ECKeyParameters(boolean isPrivate, ECDomainParameters params) {
        super(isPrivate);
        this.d = params;
    }

    public ECDomainParameters b() {
        return this.d;
    }
}
