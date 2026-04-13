package org.spongycastle.pqc.math.ntru.euclid;

import java.math.BigInteger;

public class BigIntEuclidean {
    public BigInteger a;
    public BigInteger b;
    public BigInteger c;

    private BigIntEuclidean() {
    }

    public static BigIntEuclidean a(BigInteger a2, BigInteger b2) {
        BigInteger x = BigInteger.ZERO;
        BigInteger lastx = BigInteger.ONE;
        BigInteger y = BigInteger.ONE;
        BigInteger lasty = BigInteger.ZERO;
        while (!b2.equals(BigInteger.ZERO)) {
            BigInteger[] quotientAndRemainder = a2.divideAndRemainder(b2);
            BigInteger quotient = quotientAndRemainder[0];
            BigInteger bigInteger = a2;
            a2 = b2;
            b2 = quotientAndRemainder[1];
            BigInteger temp = x;
            x = lastx.subtract(quotient.multiply(x));
            lastx = temp;
            BigInteger temp2 = y;
            y = lasty.subtract(quotient.multiply(y));
            lasty = temp2;
        }
        BigIntEuclidean result = new BigIntEuclidean();
        result.a = lastx;
        result.b = lasty;
        result.c = a2;
        return result;
    }
}
