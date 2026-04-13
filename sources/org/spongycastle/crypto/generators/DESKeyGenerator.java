package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.DESParameters;

public class DESKeyGenerator extends CipherKeyGenerator {
    public void b(KeyGenerationParameters param) {
        super.b(param);
        int i = this.b;
        if (i == 0 || i == 7) {
            this.b = 8;
        } else if (i != 8) {
            throw new IllegalArgumentException("DES key must be 64 bits long.");
        }
    }

    public byte[] a() {
        byte[] newKey = new byte[8];
        do {
            this.a.nextBytes(newKey);
            DESParameters.c(newKey);
        } while (DESParameters.b(newKey, 0));
        return newKey;
    }
}
