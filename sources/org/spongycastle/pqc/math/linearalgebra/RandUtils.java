package org.spongycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;

public class RandUtils {
    static int a(SecureRandom rand, int n) {
        int bits;
        int value;
        if (((-n) & n) == n) {
            return (int) ((((long) n) * ((long) (rand.nextInt() >>> 1))) >> 31);
        }
        do {
            bits = rand.nextInt() >>> 1;
            value = bits % n;
        } while ((bits - value) + (n - 1) < 0);
        return value;
    }
}
