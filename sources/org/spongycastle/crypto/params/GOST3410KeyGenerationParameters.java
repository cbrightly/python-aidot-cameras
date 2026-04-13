package org.spongycastle.crypto.params;

import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;

public class GOST3410KeyGenerationParameters extends KeyGenerationParameters {
    private GOST3410Parameters f;

    public GOST3410KeyGenerationParameters(SecureRandom random, GOST3410Parameters params) {
        super(random, params.b().bitLength() - 1);
        this.f = params;
    }

    public GOST3410Parameters c() {
        return this.f;
    }
}
