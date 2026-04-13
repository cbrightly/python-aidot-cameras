package org.spongycastle.crypto.params;

import org.spongycastle.crypto.KeyGenerationParameters;

public class CramerShoupKeyGenerationParameters extends KeyGenerationParameters {
    private CramerShoupParameters f;

    public CramerShoupParameters c() {
        return this.f;
    }
}
