package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.DESParameters;
import org.spongycastle.crypto.params.DESedeParameters;

public class DESedeKeyGenerator extends DESKeyGenerator {
    public void b(KeyGenerationParameters param) {
        this.a = param.a();
        int b = (param.b() + 7) / 8;
        this.b = b;
        if (b == 0 || b == 21) {
            this.b = 24;
        } else if (b == 14) {
            this.b = 16;
        } else if (b != 24 && b != 16) {
            throw new IllegalArgumentException("DESede key must be 192 or 128 bits long.");
        }
    }

    public byte[] a() {
        byte[] newKey = new byte[this.b];
        int count = 0;
        while (true) {
            this.a.nextBytes(newKey);
            DESParameters.c(newKey);
            count++;
            if (count >= 20 || (!DESedeParameters.g(newKey, 0, newKey.length) && DESedeParameters.f(newKey, 0))) {
            }
        }
        if (!DESedeParameters.g(newKey, 0, newKey.length) && DESedeParameters.f(newKey, 0)) {
            return newKey;
        }
        throw new IllegalStateException("Unable to generate DES-EDE key");
    }
}
