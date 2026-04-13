package org.spongycastle.jce.provider;

import org.spongycastle.asn1.x509.ReasonFlags;

public class ReasonsMask {
    static final ReasonsMask a = new ReasonsMask(33023);
    private int b;

    ReasonsMask(ReasonFlags reasons) {
        this.b = reasons.v();
    }

    private ReasonsMask(int reasons) {
        this.b = reasons;
    }

    ReasonsMask() {
        this(0);
    }

    /* access modifiers changed from: package-private */
    public void a(ReasonsMask mask) {
        this.b |= mask.b();
    }

    /* access modifiers changed from: package-private */
    public boolean e() {
        return this.b == a.b;
    }

    /* access modifiers changed from: package-private */
    public ReasonsMask d(ReasonsMask mask) {
        ReasonsMask _mask = new ReasonsMask();
        _mask.a(new ReasonsMask(this.b & mask.b()));
        return _mask;
    }

    /* access modifiers changed from: package-private */
    public boolean c(ReasonsMask mask) {
        return (this.b | (mask.b() ^ this.b)) != 0;
    }

    /* access modifiers changed from: package-private */
    public int b() {
        return this.b;
    }
}
