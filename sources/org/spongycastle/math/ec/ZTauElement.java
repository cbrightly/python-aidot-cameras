package org.spongycastle.math.ec;

import java.math.BigInteger;

public class ZTauElement {
    public final BigInteger a;
    public final BigInteger b;

    public ZTauElement(BigInteger u, BigInteger v) {
        this.a = u;
        this.b = v;
    }
}
