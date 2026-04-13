package org.spongycastle.crypto.params;

import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.util.Arrays;

public final class KDFCounterParameters implements DerivationParameters {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private int d;

    public byte[] c() {
        return this.a;
    }

    public byte[] a() {
        return Arrays.h(this.b);
    }

    public byte[] b() {
        return Arrays.h(this.c);
    }

    public int d() {
        return this.d;
    }
}
