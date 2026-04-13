package org.spongycastle.pqc.math.ntru.polynomial;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.pqc.math.ntru.util.Util;
import org.spongycastle.util.Arrays;

public class SparseTernaryPolynomial implements TernaryPolynomial {
    private int a;
    private int[] b;
    private int[] c;

    public SparseTernaryPolynomial(int[] coeffs) {
        int length = coeffs.length;
        this.a = length;
        this.b = new int[length];
        this.c = new int[length];
        int onesIdx = 0;
        int negOnesIdx = 0;
        for (int i = 0; i < this.a; i++) {
            int c2 = coeffs[i];
            switch (c2) {
                case -1:
                    this.c[negOnesIdx] = i;
                    negOnesIdx++;
                    break;
                case 0:
                    break;
                case 1:
                    this.b[onesIdx] = i;
                    onesIdx++;
                    break;
                default:
                    throw new IllegalArgumentException("Illegal value: " + c2 + ", must be one of {-1, 0, 1}");
            }
        }
        this.b = Arrays.y(this.b, onesIdx);
        this.c = Arrays.y(this.c, negOnesIdx);
    }

    public static SparseTernaryPolynomial g(int N, int numOnes, int numNegOnes, SecureRandom random) {
        return new SparseTernaryPolynomial(Util.b(N, numOnes, numNegOnes, random));
    }

    public IntegerPolynomial e(IntegerPolynomial poly2) {
        int[] b2 = poly2.c;
        int length = b2.length;
        int i = this.a;
        if (length == i) {
            int[] c2 = new int[i];
            int idx = 0;
            while (true) {
                int[] iArr = this.b;
                if (idx == iArr.length) {
                    break;
                }
                int i2 = iArr[idx];
                int i3 = this.a;
                int j = (i3 - 1) - i2;
                for (int k = i3 - 1; k >= 0; k--) {
                    c2[k] = c2[k] + b2[j];
                    j--;
                    if (j < 0) {
                        j = this.a - 1;
                    }
                }
                idx++;
            }
            int idx2 = 0;
            while (true) {
                int[] iArr2 = this.c;
                if (idx2 == iArr2.length) {
                    return new IntegerPolynomial(c2);
                }
                int i4 = iArr2[idx2];
                int i5 = this.a;
                int j2 = (i5 - 1) - i4;
                for (int k2 = i5 - 1; k2 >= 0; k2--) {
                    c2[k2] = c2[k2] - b2[j2];
                    j2--;
                    if (j2 < 0) {
                        j2 = this.a - 1;
                    }
                }
                idx2++;
            }
        } else {
            throw new IllegalArgumentException("Number of coefficients must be the same");
        }
    }

    public IntegerPolynomial c(IntegerPolynomial poly2, int modulus) {
        IntegerPolynomial c2 = e(poly2);
        c2.w(modulus);
        return c2;
    }

    public BigIntPolynomial b(BigIntPolynomial poly2) {
        BigInteger[] b2 = poly2.b;
        int length = b2.length;
        int i = this.a;
        if (length == i) {
            BigInteger[] c2 = new BigInteger[i];
            for (int i2 = 0; i2 < this.a; i2++) {
                c2[i2] = BigInteger.ZERO;
            }
            int idx = 0;
            while (true) {
                int[] iArr = this.b;
                if (idx == iArr.length) {
                    break;
                }
                int i3 = iArr[idx];
                int i4 = this.a;
                int j = (i4 - 1) - i3;
                for (int k = i4 - 1; k >= 0; k--) {
                    c2[k] = c2[k].add(b2[j]);
                    j--;
                    if (j < 0) {
                        j = this.a - 1;
                    }
                }
                idx++;
            }
            int idx2 = 0;
            while (true) {
                int[] iArr2 = this.c;
                if (idx2 == iArr2.length) {
                    return new BigIntPolynomial(c2);
                }
                int i5 = iArr2[idx2];
                int i6 = this.a;
                int j2 = (i6 - 1) - i5;
                for (int k2 = i6 - 1; k2 >= 0; k2--) {
                    c2[k2] = c2[k2].subtract(b2[j2]);
                    j2--;
                    if (j2 < 0) {
                        j2 = this.a - 1;
                    }
                }
                idx2++;
            }
        } else {
            throw new IllegalArgumentException("Number of coefficients must be the same");
        }
    }

    public int[] d() {
        return this.b;
    }

    public int[] f() {
        return this.c;
    }

    public IntegerPolynomial a() {
        int[] coeffs = new int[this.a];
        int idx = 0;
        while (true) {
            int[] iArr = this.b;
            if (idx == iArr.length) {
                break;
            }
            coeffs[iArr[idx]] = 1;
            idx++;
        }
        int idx2 = 0;
        while (true) {
            int[] iArr2 = this.c;
            if (idx2 == iArr2.length) {
                return new IntegerPolynomial(coeffs);
            }
            coeffs[iArr2[idx2]] = -1;
            idx2++;
        }
    }

    public int size() {
        return this.a;
    }

    public int hashCode() {
        return (((((1 * 31) + this.a) * 31) + Arrays.L(this.c)) * 31) + Arrays.L(this.b);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SparseTernaryPolynomial other = (SparseTernaryPolynomial) obj;
        if (this.a == other.a && Arrays.d(this.c, other.c) && Arrays.d(this.b, other.b)) {
            return true;
        }
        return false;
    }
}
