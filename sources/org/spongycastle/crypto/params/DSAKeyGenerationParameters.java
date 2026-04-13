package org.spongycastle.crypto.params;

import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;

public class DSAKeyGenerationParameters extends KeyGenerationParameters {
    private DSAParameters f;

    public DSAKeyGenerationParameters(SecureRandom random, DSAParameters params) {
        super(random, params.b().bitLength() - 1);
        this.f = params;
    }

    public DSAParameters c() {
        return this.f;
    }
}
