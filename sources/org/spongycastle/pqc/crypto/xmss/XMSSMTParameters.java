package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.crypto.Digest;

public final class XMSSMTParameters {
    private final XMSSOid a;
    private final XMSSParameters b;
    private final int c;
    private final int d;

    public XMSSMTParameters(int height, int layers, Digest digest) {
        this.c = height;
        this.d = layers;
        this.b = new XMSSParameters(i(height, layers), digest);
        this.a = DefaultXMSSMTOid.b(a().b(), b(), g(), e(), c(), layers);
    }

    private static int i(int height, int layers) {
        if (height < 2) {
            throw new IllegalArgumentException("totalHeight must be > 1");
        } else if (height % layers != 0) {
            throw new IllegalArgumentException("layers must divide totalHeight without remainder");
        } else if (height / layers != 1) {
            return height / layers;
        } else {
            throw new IllegalArgumentException("height / layers must be greater than 1");
        }
    }

    public int c() {
        return this.c;
    }

    public int d() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    public XMSSParameters h() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public WOTSPlus f() {
        return this.b.f();
    }

    /* access modifiers changed from: protected */
    public Digest a() {
        return this.b.b();
    }

    public int b() {
        return this.b.c();
    }

    public int g() {
        return this.b.g();
    }

    /* access modifiers changed from: protected */
    public int e() {
        return this.b.f().e().c();
    }
}
