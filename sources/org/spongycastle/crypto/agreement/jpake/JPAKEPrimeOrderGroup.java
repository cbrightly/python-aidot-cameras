package org.spongycastle.crypto.agreement.jpake;

import java.math.BigInteger;

public class JPAKEPrimeOrderGroup {
    private final BigInteger a;
    private final BigInteger b;
    private final BigInteger c;

    JPAKEPrimeOrderGroup(BigInteger p, BigInteger q, BigInteger g, boolean skipChecks) {
        JPAKEUtil.a(p, "p");
        JPAKEUtil.a(q, "q");
        JPAKEUtil.a(g, "g");
        if (!skipChecks) {
            BigInteger bigInteger = JPAKEUtil.b;
            if (!p.subtract(bigInteger).mod(q).equals(JPAKEUtil.a)) {
                throw new IllegalArgumentException("p-1 must be evenly divisible by q");
            } else if (g.compareTo(BigInteger.valueOf(2)) == -1 || g.compareTo(p.subtract(bigInteger)) == 1) {
                throw new IllegalArgumentException("g must be in [2, p-1]");
            } else if (!g.modPow(q, p).equals(bigInteger)) {
                throw new IllegalArgumentException("g^q mod p must equal 1");
            } else if (!p.isProbablePrime(20)) {
                throw new IllegalArgumentException("p must be prime");
            } else if (!q.isProbablePrime(20)) {
                throw new IllegalArgumentException("q must be prime");
            }
        }
        this.a = p;
        this.b = q;
        this.c = g;
    }
}
