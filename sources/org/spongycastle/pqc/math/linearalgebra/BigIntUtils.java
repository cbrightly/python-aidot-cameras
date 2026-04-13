package org.spongycastle.pqc.math.linearalgebra;

import java.math.BigInteger;

public final class BigIntUtils {
    private BigIntUtils() {
    }

    public static byte[] a(BigInteger value) {
        byte[] valBytes = value.toByteArray();
        if (valBytes.length == 1 || (value.bitLength() & 7) != 0) {
            return valBytes;
        }
        byte[] result = new byte[(value.bitLength() >> 3)];
        System.arraycopy(valBytes, 1, result, 0, result.length);
        return result;
    }
}
