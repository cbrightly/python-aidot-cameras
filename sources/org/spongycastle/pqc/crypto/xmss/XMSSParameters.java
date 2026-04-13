package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.crypto.Digest;

public final class XMSSParameters {
    private final XMSSOid a;
    private final WOTSPlus b;
    private final int c;
    private final int d;

    public XMSSParameters(int height, Digest digest) {
        if (height < 2) {
            throw new IllegalArgumentException("height must be >= 2");
        } else if (digest != null) {
            WOTSPlus wOTSPlus = new WOTSPlus(new WOTSPlusParameters(digest));
            this.b = wOTSPlus;
            this.c = height;
            this.d = a();
            this.a = DefaultXMSSOid.b(b().b(), c(), g(), wOTSPlus.e().c(), height);
        } else {
            throw new NullPointerException("digest == null");
        }
    }

    private int a() {
        int k = 2;
        while (true) {
            int i = this.c;
            if (k > i) {
                throw new IllegalStateException("should never happen...");
            } else if ((i - k) % 2 == 0) {
                return k;
            } else {
                k++;
            }
        }
    }

    /* access modifiers changed from: protected */
    public Digest b() {
        return this.b.e().a();
    }

    public int c() {
        return this.b.e().b();
    }

    public int g() {
        return this.b.e().f();
    }

    public int d() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public WOTSPlus f() {
        return this.b;
    }

    /* access modifiers changed from: package-private */
    public int e() {
        return this.d;
    }
}
