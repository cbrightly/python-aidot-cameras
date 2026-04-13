package org.spongycastle.pqc.crypto.xmss;

import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;

public final class XMSSMTKeyGenerationParameters extends KeyGenerationParameters {
    private final XMSSMTParameters f;

    public XMSSMTKeyGenerationParameters(XMSSMTParameters xmssmtParameters, SecureRandom prng) {
        super(prng, -1);
        this.f = xmssmtParameters;
    }

    public XMSSMTParameters c() {
        return this.f;
    }
}
