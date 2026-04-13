package org.spongycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;

public class McElieceKeyGenerationParameters extends KeyGenerationParameters {
    private McElieceParameters f;

    public McElieceKeyGenerationParameters(SecureRandom random, McElieceParameters params) {
        super(random, 256);
        this.f = params;
    }

    public McElieceParameters c() {
        return this.f;
    }
}
