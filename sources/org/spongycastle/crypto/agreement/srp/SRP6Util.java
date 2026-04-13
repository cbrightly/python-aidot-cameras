package org.spongycastle.crypto.agreement.srp;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.util.BigIntegers;

public class SRP6Util {
    private static BigInteger a = BigInteger.valueOf(0);
    private static BigInteger b = BigInteger.valueOf(1);

    public static BigInteger a(Digest digest, BigInteger N, BigInteger g) {
        return f(digest, N, N, g);
    }

    public static BigInteger b(Digest digest, BigInteger N, BigInteger A, BigInteger B) {
        return f(digest, N, A, B);
    }

    public static BigInteger c(Digest digest, BigInteger N, byte[] salt, byte[] identity, byte[] password) {
        byte[] output = new byte[digest.e()];
        digest.update(identity, 0, identity.length);
        digest.d((byte) 58);
        digest.update(password, 0, password.length);
        digest.c(output, 0);
        digest.update(salt, 0, salt.length);
        digest.update(output, 0, output.length);
        digest.c(output, 0);
        return new BigInteger(1, output);
    }

    public static BigInteger d(Digest digest, BigInteger N, BigInteger g, SecureRandom random) {
        return BigIntegers.c(b.shiftLeft(Math.min(256, N.bitLength() / 2) - 1), N.subtract(b), random);
    }

    public static BigInteger g(BigInteger N, BigInteger val) {
        BigInteger val2 = val.mod(N);
        if (!val2.equals(a)) {
            return val2;
        }
        throw new CryptoException("Invalid public value: 0");
    }

    private static BigInteger f(Digest digest, BigInteger N, BigInteger n1, BigInteger n2) {
        int padLength = (N.bitLength() + 7) / 8;
        byte[] n1_bytes = e(n1, padLength);
        byte[] n2_bytes = e(n2, padLength);
        digest.update(n1_bytes, 0, n1_bytes.length);
        digest.update(n2_bytes, 0, n2_bytes.length);
        byte[] output = new byte[digest.e()];
        digest.c(output, 0);
        return new BigInteger(1, output);
    }

    private static byte[] e(BigInteger n, int length) {
        byte[] bs = BigIntegers.b(n);
        if (bs.length >= length) {
            return bs;
        }
        byte[] tmp = new byte[length];
        System.arraycopy(bs, 0, tmp, length - bs.length, bs.length);
        return tmp;
    }
}
