package org.spongycastle.math.ec.endo;

import java.math.BigInteger;

public interface GLVEndomorphism extends ECEndomorphism {
    BigInteger[] c(BigInteger bigInteger);
}
