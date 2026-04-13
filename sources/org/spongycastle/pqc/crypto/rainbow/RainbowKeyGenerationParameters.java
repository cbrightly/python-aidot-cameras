package org.spongycastle.pqc.crypto.rainbow;

import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;

public class RainbowKeyGenerationParameters extends KeyGenerationParameters {
    private RainbowParameters f;

    public RainbowKeyGenerationParameters(SecureRandom random, RainbowParameters params) {
        super(random, params.c()[params.c().length - 1] - params.c()[0]);
        this.f = params;
    }

    public RainbowParameters c() {
        return this.f;
    }
}
