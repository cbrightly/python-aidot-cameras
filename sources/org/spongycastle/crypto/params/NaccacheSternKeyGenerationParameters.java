package org.spongycastle.crypto.params;

import org.spongycastle.crypto.KeyGenerationParameters;

public class NaccacheSternKeyGenerationParameters extends KeyGenerationParameters {
    private int f;
    private int q;
    private boolean x;

    public int c() {
        return this.f;
    }

    public int d() {
        return this.q;
    }

    public boolean e() {
        return this.x;
    }
}
