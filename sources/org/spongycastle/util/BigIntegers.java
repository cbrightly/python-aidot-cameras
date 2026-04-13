package org.spongycastle.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class BigIntegers {
    private static final BigInteger a = BigInteger.valueOf(0);

    public static byte[] b(BigInteger value) {
        byte[] bytes = value.toByteArray();
        if (bytes[0] != 0) {
            return bytes;
        }
        byte[] tmp = new byte[(bytes.length - 1)];
        System.arraycopy(bytes, 1, tmp, 0, tmp.length);
        return tmp;
    }

    public static byte[] a(int length, BigInteger value) {
        byte[] bytes = value.toByteArray();
        if (bytes.length == length) {
            return bytes;
        }
        int start = 0;
        if (bytes[0] == 0) {
            start = 1;
        }
        int count = bytes.length - start;
        if (count <= length) {
            byte[] tmp = new byte[length];
            System.arraycopy(bytes, start, tmp, tmp.length - count, count);
            return tmp;
        }
        throw new IllegalArgumentException("standard length exceeded for value");
    }

    public static BigInteger c(BigInteger min, BigInteger max, SecureRandom random) {
        int cmp = min.compareTo(max);
        if (cmp >= 0) {
            if (cmp <= 0) {
                return min;
            }
            throw new IllegalArgumentException("'min' may not be greater than 'max'");
        } else if (min.bitLength() > max.bitLength() / 2) {
            return c(a, max.subtract(min), random).add(min);
        } else {
            for (int i = 0; i < 1000; i++) {
                BigInteger x = new BigInteger(max.bitLength(), random);
                if (x.compareTo(min) >= 0 && x.compareTo(max) <= 0) {
                    return x;
                }
            }
            return new BigInteger(max.subtract(min).bitLength() - 1, random).add(min);
        }
    }

    public static BigInteger d(byte[] buf, int off, int length) {
        byte[] mag = buf;
        if (!(off == 0 && length == buf.length)) {
            mag = new byte[length];
            System.arraycopy(buf, off, mag, 0, length);
        }
        return new BigInteger(1, mag);
    }
}
