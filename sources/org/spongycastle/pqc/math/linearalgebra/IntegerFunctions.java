package org.spongycastle.pqc.math.linearalgebra;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class IntegerFunctions {
    private static final BigInteger a = BigInteger.valueOf(0);
    private static final BigInteger b = BigInteger.valueOf(1);
    private static final BigInteger c = BigInteger.valueOf(2);
    private static final BigInteger d = BigInteger.valueOf(4);
    private static final int[] e = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41};
    private static SecureRandom f = null;
    private static final int[] g = {0, 1, 0, -1, 0, -1, 0, 1};

    private IntegerFunctions() {
    }

    public static int b(int n) {
        int m;
        if (n == 0) {
            return 1;
        }
        if (n < 0) {
            m = -n;
        } else {
            m = n;
        }
        int d2 = 0;
        while (m > 0) {
            d2++;
            m >>>= 8;
        }
        return d2;
    }

    public static BigInteger a(int n, int t) {
        BigInteger result = b;
        if (n != 0) {
            if (t > (n >>> 1)) {
                t = n - t;
            }
            for (int i = 1; i <= t; i++) {
                result = result.multiply(BigInteger.valueOf((long) (n - (i - 1)))).divide(BigInteger.valueOf((long) i));
            }
            return result;
        } else if (t == 0) {
            return result;
        } else {
            return a;
        }
    }
}
