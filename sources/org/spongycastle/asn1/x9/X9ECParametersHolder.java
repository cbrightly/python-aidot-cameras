package org.spongycastle.asn1.x9;

public abstract class X9ECParametersHolder {
    private X9ECParameters a;

    /* access modifiers changed from: protected */
    public abstract X9ECParameters a();

    public synchronized X9ECParameters b() {
        if (this.a == null) {
            this.a = a();
        }
        return this.a;
    }
}
