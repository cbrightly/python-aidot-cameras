package org.spongycastle.asn1;

import java.io.InputStream;

public abstract class LimitedInputStream extends InputStream {
    protected final InputStream c;
    private int d;

    LimitedInputStream(InputStream in, int limit) {
        this.c = in;
        this.d = limit;
    }

    /* access modifiers changed from: package-private */
    public int a() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    public void c(boolean on) {
        InputStream inputStream = this.c;
        if (inputStream instanceof IndefiniteLengthInputStream) {
            ((IndefiniteLengthInputStream) inputStream).i(on);
        }
    }
}
