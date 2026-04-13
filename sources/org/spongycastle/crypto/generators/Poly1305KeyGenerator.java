package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;

public class Poly1305KeyGenerator extends CipherKeyGenerator {
    public void b(KeyGenerationParameters param) {
        super.b(new KeyGenerationParameters(param.a(), 256));
    }

    public byte[] a() {
        byte[] key = super.a();
        c(key);
        return key;
    }

    public static void c(byte[] key) {
        if (key.length == 32) {
            key[3] = (byte) (key[3] & 15);
            key[7] = (byte) (key[7] & 15);
            key[11] = (byte) (key[11] & 15);
            key[15] = (byte) (key[15] & 15);
            key[4] = (byte) (key[4] & -4);
            key[8] = (byte) (key[8] & -4);
            key[12] = (byte) (key[12] & -4);
            return;
        }
        throw new IllegalArgumentException("Poly1305 key must be 256 bits.");
    }
}
