package org.spongycastle.crypto.agreement.jpake;

import java.math.BigInteger;

public class JPAKEUtil {
    static final BigInteger a = BigInteger.valueOf(0);
    static final BigInteger b = BigInteger.valueOf(1);

    public static void a(Object object, String description) {
        if (object == null) {
            throw new NullPointerException(description + " must not be null");
        }
    }
}
