package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomDSAKCalculator implements DSAKCalculator {
    private static final BigInteger a = BigInteger.valueOf(0);
    private BigInteger b;
    private SecureRandom c;

    public boolean c() {
        return false;
    }

    public void a(BigInteger n, SecureRandom random) {
        this.b = n;
        this.c = random;
    }

    public void d(BigInteger n, BigInteger d, byte[] message) {
        throw new IllegalStateException("Operation not supported");
    }

    public BigInteger b() {
        int qBitLength = this.b.bitLength();
        while (true) {
            BigInteger k = new BigInteger(qBitLength, this.c);
            if (!k.equals(a) && k.compareTo(this.b) < 0) {
                return k;
            }
        }
    }
}
