package org.spongycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;

public class McElieceCCA2KeyGenerationParameters extends KeyGenerationParameters {
    private McElieceCCA2Parameters f;

    public McElieceCCA2KeyGenerationParameters(SecureRandom random, McElieceCCA2Parameters params) {
        super(random, 128);
        this.f = params;
    }

    public McElieceCCA2Parameters c() {
        return this.f;
    }
}
