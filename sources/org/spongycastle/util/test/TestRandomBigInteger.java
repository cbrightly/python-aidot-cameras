package org.spongycastle.util.test;

import java.math.BigInteger;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.test.FixedSecureRandom;

public class TestRandomBigInteger extends FixedSecureRandom {
    public TestRandomBigInteger(String encoding) {
        this(encoding, 10);
    }

    public TestRandomBigInteger(String encoding, int radix) {
        super(new FixedSecureRandom.Source[]{new FixedSecureRandom.BigInteger(BigIntegers.b(new BigInteger(encoding, radix)))});
    }

    public TestRandomBigInteger(byte[] encoding) {
        super(new FixedSecureRandom.Source[]{new FixedSecureRandom.BigInteger(encoding)});
    }

    public TestRandomBigInteger(int bitLength, byte[] encoding) {
        super(new FixedSecureRandom.Source[]{new FixedSecureRandom.BigInteger(bitLength, encoding)});
    }
}
