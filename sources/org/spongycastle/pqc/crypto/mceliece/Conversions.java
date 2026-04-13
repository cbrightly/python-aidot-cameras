package org.spongycastle.pqc.crypto.mceliece;

import java.math.BigInteger;
import org.spongycastle.pqc.math.linearalgebra.BigIntUtils;
import org.spongycastle.pqc.math.linearalgebra.GF2Vector;
import org.spongycastle.pqc.math.linearalgebra.IntegerFunctions;

public final class Conversions {
    private static final BigInteger a = BigInteger.valueOf(0);
    private static final BigInteger b = BigInteger.valueOf(1);

    private Conversions() {
    }

    public static GF2Vector b(int n, int t, byte[] m) {
        if (n >= t) {
            BigInteger c = IntegerFunctions.a(n, t);
            BigInteger i = new BigInteger(1, m);
            if (i.compareTo(c) < 0) {
                GF2Vector result = new GF2Vector(n);
                int nn = n;
                int tt = t;
                for (int j = 0; j < n; j++) {
                    c = c.multiply(BigInteger.valueOf((long) (nn - tt))).divide(BigInteger.valueOf((long) nn));
                    nn--;
                    if (c.compareTo(i) <= 0) {
                        result.j(j);
                        i = i.subtract(c);
                        tt--;
                        if (nn == tt) {
                            c = b;
                        } else {
                            c = c.multiply(BigInteger.valueOf((long) (tt + 1))).divide(BigInteger.valueOf((long) (nn - tt)));
                        }
                    }
                }
                return result;
            }
            throw new IllegalArgumentException("Encoded number too large.");
        }
        throw new IllegalArgumentException("n < t");
    }

    public static byte[] a(int n, int t, GF2Vector vec) {
        if (vec.b() == n && vec.f() == t) {
            int[] vecArray = vec.g();
            BigInteger bc = IntegerFunctions.a(n, t);
            BigInteger d = a;
            int nn = n;
            int tt = t;
            for (int i = 0; i < n; i++) {
                bc = bc.multiply(BigInteger.valueOf((long) (nn - tt))).divide(BigInteger.valueOf((long) nn));
                nn--;
                if ((vecArray[i >> 5] & (1 << (i & 31))) != 0) {
                    d = d.add(bc);
                    tt--;
                    if (nn == tt) {
                        bc = b;
                    } else {
                        bc = bc.multiply(BigInteger.valueOf((long) (tt + 1))).divide(BigInteger.valueOf((long) (nn - tt)));
                    }
                }
            }
            return BigIntUtils.a(d);
        }
        throw new IllegalArgumentException("vector has wrong length or hamming weight");
    }
}
