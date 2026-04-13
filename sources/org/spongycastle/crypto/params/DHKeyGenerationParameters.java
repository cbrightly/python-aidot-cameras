package org.spongycastle.crypto.params;

import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;

public class DHKeyGenerationParameters extends KeyGenerationParameters {
    private DHParameters f;

    public DHKeyGenerationParameters(SecureRandom random, DHParameters params) {
        super(random, d(params));
        this.f = params;
    }

    public DHParameters c() {
        return this.f;
    }

    static int d(DHParameters params) {
        return params.c() != 0 ? params.c() : params.e().bitLength();
    }
}
