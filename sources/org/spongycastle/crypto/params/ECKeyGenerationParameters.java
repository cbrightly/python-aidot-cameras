package org.spongycastle.crypto.params;

import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;

public class ECKeyGenerationParameters extends KeyGenerationParameters {
    private ECDomainParameters f;

    public ECKeyGenerationParameters(ECDomainParameters domainParams, SecureRandom random) {
        super(random, domainParams.d().bitLength());
        this.f = domainParams;
    }

    public ECDomainParameters c() {
        return this.f;
    }
}
