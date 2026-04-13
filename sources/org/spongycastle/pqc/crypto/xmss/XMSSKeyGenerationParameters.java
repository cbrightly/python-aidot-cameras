package org.spongycastle.pqc.crypto.xmss;

import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;

public final class XMSSKeyGenerationParameters extends KeyGenerationParameters {
    private final XMSSParameters f;

    public XMSSKeyGenerationParameters(XMSSParameters xmssParameters, SecureRandom prng) {
        super(prng, -1);
        this.f = xmssParameters;
    }

    public XMSSParameters c() {
        return this.f;
    }
}
