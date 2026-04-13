package org.spongycastle.crypto.params;

import java.security.SecureRandom;

public class DSAParameterGenerationParameters {
    private final int a;
    private final int b;
    private final int c;
    private final int d;
    private final SecureRandom e;

    public DSAParameterGenerationParameters(int L, int N, int certainty, SecureRandom random) {
        this(L, N, certainty, random, -1);
    }

    public DSAParameterGenerationParameters(int L, int N, int certainty, SecureRandom random, int usageIndex) {
        this.a = L;
        this.b = N;
        this.d = certainty;
        this.c = usageIndex;
        this.e = random;
    }

    public int b() {
        return this.a;
    }

    public int c() {
        return this.b;
    }

    public int a() {
        return this.d;
    }

    public SecureRandom d() {
        return this.e;
    }

    public int e() {
        return this.c;
    }
}
