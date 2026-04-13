package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHValidationParameters;

public class DHParametersGenerator {
    private static final BigInteger a = BigInteger.valueOf(2);
    private int b;
    private int c;
    private SecureRandom d;

    public void b(int size, int certainty, SecureRandom random) {
        this.b = size;
        this.c = certainty;
        this.d = random;
    }

    public DHParameters a() {
        BigInteger[] safePrimes = DHParametersHelper.a(this.b, this.c, this.d);
        BigInteger p = safePrimes[0];
        BigInteger q = safePrimes[1];
        return new DHParameters(p, DHParametersHelper.b(p, q, this.d), q, a, (DHValidationParameters) null);
    }
}
