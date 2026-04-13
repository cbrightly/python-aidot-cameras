package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.crypto.Digest;

public final class WOTSPlusParameters {
    private final XMSSOid a;
    private final Digest b;
    private final int c;
    private final int d;
    private final int e;
    private final int f;
    private final int g;

    protected WOTSPlusParameters(Digest digest) {
        if (digest != null) {
            this.b = digest;
            int h = XMSSUtil.h(digest);
            this.c = h;
            this.d = 16;
            int ceil = (int) Math.ceil(((double) (h * 8)) / ((double) XMSSUtil.o(16)));
            this.f = ceil;
            int floor = ((int) Math.floor((double) (XMSSUtil.o((16 - 1) * ceil) / XMSSUtil.o(16)))) + 1;
            this.g = floor;
            int i = ceil + floor;
            this.e = i;
            WOTSPlusOid b2 = WOTSPlusOid.b(digest.b(), h, 16, i);
            this.a = b2;
            if (b2 == null) {
                throw new IllegalArgumentException("cannot find OID for digest algorithm: " + digest.b());
            }
            return;
        }
        throw new NullPointerException("digest == null");
    }

    /* access modifiers changed from: protected */
    public Digest a() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public int b() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public int f() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    public int c() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public int d() {
        return this.f;
    }

    /* access modifiers changed from: protected */
    public int e() {
        return this.g;
    }
}
