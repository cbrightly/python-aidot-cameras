package org.spongycastle.crypto.params;

import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;

public class ElGamalKeyGenerationParameters extends KeyGenerationParameters {
    private ElGamalParameters f;

    public ElGamalKeyGenerationParameters(SecureRandom random, ElGamalParameters params) {
        super(random, d(params));
        this.f = params;
    }

    public ElGamalParameters c() {
        return this.f;
    }

    static int d(ElGamalParameters params) {
        return params.b() != 0 ? params.b() : params.c().bitLength();
    }
}
