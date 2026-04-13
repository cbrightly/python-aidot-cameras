package org.spongycastle.pqc.math.ntru.polynomial;

public interface TernaryPolynomial extends Polynomial {
    int[] d();

    int[] f();

    int size();
}
