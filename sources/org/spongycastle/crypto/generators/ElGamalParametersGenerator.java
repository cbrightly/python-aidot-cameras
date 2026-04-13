package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.params.ElGamalParameters;

public class ElGamalParametersGenerator {
    private int a;
    private int b;
    private SecureRandom c;

    public void b(int size, int certainty, SecureRandom random) {
        this.a = size;
        this.b = certainty;
        this.c = random;
    }

    public ElGamalParameters a() {
        BigInteger[] safePrimes = DHParametersHelper.a(this.a, this.b, this.c);
        BigInteger p = safePrimes[0];
        return new ElGamalParameters(p, DHParametersHelper.b(p, safePrimes[1], this.c));
    }
}
