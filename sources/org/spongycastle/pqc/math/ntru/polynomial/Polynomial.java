package org.spongycastle.pqc.math.ntru.polynomial;

public interface Polynomial {
    IntegerPolynomial a();

    BigIntPolynomial b(BigIntPolynomial bigIntPolynomial);

    IntegerPolynomial c(IntegerPolynomial integerPolynomial, int i);

    IntegerPolynomial e(IntegerPolynomial integerPolynomial);
}
