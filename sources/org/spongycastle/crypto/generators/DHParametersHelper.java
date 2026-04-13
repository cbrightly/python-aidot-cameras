package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.math.ec.WNafUtil;
import org.spongycastle.util.BigIntegers;

public class DHParametersHelper {
    private static final BigInteger a = BigInteger.valueOf(1);
    private static final BigInteger b = BigInteger.valueOf(2);

    DHParametersHelper() {
    }

    static BigInteger[] a(int size, int certainty, SecureRandom random) {
        int qLength = size - 1;
        int minWeight = size >>> 2;
        while (true) {
            BigInteger q = new BigInteger(qLength, 2, random);
            BigInteger p = q.shiftLeft(1).add(a);
            if (p.isProbablePrime(certainty) && ((certainty <= 2 || q.isProbablePrime(certainty - 2)) && WNafUtil.e(p) >= minWeight)) {
                return new BigInteger[]{p, q};
            }
        }
    }

    static BigInteger b(BigInteger p, BigInteger q, SecureRandom random) {
        BigInteger g;
        BigInteger pMinusTwo = p.subtract(b);
        do {
            BigInteger bigInteger = b;
            g = BigIntegers.c(bigInteger, pMinusTwo, random).modPow(bigInteger, p);
        } while (g.equals(a));
        return g;
    }
}
