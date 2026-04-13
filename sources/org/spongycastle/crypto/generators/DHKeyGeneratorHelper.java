package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.math.ec.WNafUtil;
import org.spongycastle.util.BigIntegers;

public class DHKeyGeneratorHelper {
    static final DHKeyGeneratorHelper a = new DHKeyGeneratorHelper();
    private static final BigInteger b = BigInteger.valueOf(1);
    private static final BigInteger c = BigInteger.valueOf(2);

    private DHKeyGeneratorHelper() {
    }

    /* access modifiers changed from: package-private */
    public BigInteger a(DHParameters dhParams, SecureRandom random) {
        BigInteger x;
        BigInteger x2;
        int limit = dhParams.c();
        if (limit != 0) {
            int minWeight = limit >>> 2;
            do {
                x2 = new BigInteger(limit, random).setBit(limit - 1);
            } while (WNafUtil.e(x2) < minWeight);
            return x2;
        }
        BigInteger min = c;
        int m = dhParams.d();
        if (m != 0) {
            min = b.shiftLeft(m - 1);
        }
        BigInteger q = dhParams.f();
        if (q == null) {
            q = dhParams.e();
        }
        BigInteger max = q.subtract(c);
        int minWeight2 = max.bitLength() >>> 2;
        do {
            x = BigIntegers.c(min, max, random);
        } while (WNafUtil.e(x) < minWeight2);
        return x;
    }

    /* access modifiers changed from: package-private */
    public BigInteger b(DHParameters dhParams, BigInteger x) {
        return dhParams.b().modPow(x, dhParams.e());
    }
}
