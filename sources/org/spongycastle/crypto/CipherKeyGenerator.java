package org.spongycastle.crypto;

import java.security.SecureRandom;

public class CipherKeyGenerator {
    protected SecureRandom a;
    protected int b;

    public void b(KeyGenerationParameters param) {
        this.a = param.a();
        this.b = (param.b() + 7) / 8;
    }

    public byte[] a() {
        byte[] key = new byte[this.b];
        this.a.nextBytes(key);
        return key;
    }
}
