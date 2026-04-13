package org.spongycastle.crypto.params;

import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.util.Arrays;

public class HKDFParameters implements DerivationParameters {
    private final byte[] a;
    private final boolean b;
    private final byte[] c;
    private final byte[] d;

    public byte[] a() {
        return Arrays.h(this.a);
    }

    public boolean d() {
        return this.b;
    }

    public byte[] c() {
        return Arrays.h(this.c);
    }

    public byte[] b() {
        return Arrays.h(this.d);
    }
}
