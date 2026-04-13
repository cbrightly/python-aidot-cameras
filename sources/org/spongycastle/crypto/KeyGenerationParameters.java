package org.spongycastle.crypto;

import java.security.SecureRandom;

public class KeyGenerationParameters {
    private SecureRandom c;
    private int d;

    public KeyGenerationParameters(SecureRandom random, int strength) {
        this.c = random;
        this.d = strength;
    }

    public SecureRandom a() {
        return this.c;
    }

    public int b() {
        return this.d;
    }
}
