package org.spongycastle.jcajce.spec;

import java.security.spec.KeySpec;
import org.spongycastle.util.Arrays;

public class TLSKeyMaterialSpec implements KeySpec {
    private final byte[] c;
    private final String d;
    private final int f;
    private final byte[] q;

    public String a() {
        return this.d;
    }

    public int b() {
        return this.f;
    }

    public byte[] c() {
        return Arrays.h(this.c);
    }

    public byte[] d() {
        return Arrays.h(this.q);
    }
}
