package org.spongycastle.pqc.math.ntru.polynomial;

import java.security.SecureRandom;
import org.spongycastle.pqc.math.ntru.util.Util;
import org.spongycastle.util.Arrays;

public class DenseTernaryPolynomial extends IntegerPolynomial implements TernaryPolynomial {
    public DenseTernaryPolynomial(IntegerPolynomial intPoly) {
        this(intPoly.c);
    }

    public DenseTernaryPolynomial(int[] coeffs) {
        super(coeffs);
        R();
    }

    private void R() {
        int c;
        int i = 0;
        while (true) {
            int[] iArr = this.c;
            if (i != iArr.length) {
                c = iArr[i];
                if (c < -1 || c > 1) {
                } else {
                    i++;
                }
            } else {
                return;
            }
        }
        throw new IllegalStateException("Illegal value: " + c + ", must be one of {-1, 0, 1}");
    }

    public static DenseTernaryPolynomial S(int N, int numOnes, int numNegOnes, SecureRandom random) {
        return new DenseTernaryPolynomial(Util.b(N, numOnes, numNegOnes, random));
    }

    public IntegerPolynomial c(IntegerPolynomial poly2, int modulus) {
        if (modulus != 2048) {
            return super.c(poly2, modulus);
        }
        IntegerPolynomial poly2Pos = (IntegerPolynomial) poly2.clone();
        poly2Pos.A(2048);
        return new LongPolynomial5(poly2Pos).a(this).b();
    }

    public int[] d() {
        int N = this.c.length;
        int[] ones = new int[N];
        int onesIdx = 0;
        for (int i = 0; i < N; i++) {
            if (this.c[i] == 1) {
                ones[onesIdx] = i;
                onesIdx++;
            }
        }
        return Arrays.y(ones, onesIdx);
    }

    public int[] f() {
        int N = this.c.length;
        int[] negOnes = new int[N];
        int negOnesIdx = 0;
        for (int i = 0; i < N; i++) {
            if (this.c[i] == -1) {
                negOnes[negOnesIdx] = i;
                negOnesIdx++;
            }
        }
        return Arrays.y(negOnes, negOnesIdx);
    }

    public int size() {
        return this.c.length;
    }
}
