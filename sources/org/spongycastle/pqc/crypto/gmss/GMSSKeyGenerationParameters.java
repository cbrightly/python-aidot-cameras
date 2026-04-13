package org.spongycastle.pqc.crypto.gmss;

import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;

public class GMSSKeyGenerationParameters extends KeyGenerationParameters {
    private GMSSParameters f;

    public GMSSKeyGenerationParameters(SecureRandom random, GMSSParameters params) {
        super(random, 1);
        this.f = params;
    }

    public GMSSParameters c() {
        return this.f;
    }
}
