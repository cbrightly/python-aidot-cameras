package org.spongycastle.crypto.params;

import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.util.Arrays;

public final class KDFDoublePipelineIterationParameters implements DerivationParameters {
    private final byte[] a;
    private final boolean b;
    private final int c;
    private final byte[] d;

    public byte[] b() {
        return this.a;
    }

    public boolean d() {
        return this.b;
    }

    public int c() {
        return this.c;
    }

    public byte[] a() {
        return Arrays.h(this.d);
    }
}
