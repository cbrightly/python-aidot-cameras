package org.spongycastle.pqc.math.ntru.polynomial;

import java.math.BigInteger;

public class Resultant {
    public BigIntPolynomial a;
    public BigInteger b;

    Resultant(BigIntPolynomial rho, BigInteger res) {
        this.a = rho;
        this.b = res;
    }
}
