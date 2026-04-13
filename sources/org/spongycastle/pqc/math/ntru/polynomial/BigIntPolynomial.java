package org.spongycastle.pqc.math.ntru.polynomial;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.spongycastle.util.Arrays;

public class BigIntPolynomial {
    private static final double a = Math.log10(2.0d);
    BigInteger[] b;

    BigIntPolynomial(int N) {
        this.b = new BigInteger[N];
        for (int i = 0; i < N; i++) {
            this.b[i] = Constants.a;
        }
    }

    BigIntPolynomial(BigInteger[] coeffs) {
        this.b = coeffs;
    }

    public BigIntPolynomial(IntegerPolynomial p) {
        this.b = new BigInteger[p.c.length];
        int i = 0;
        while (true) {
            BigInteger[] bigIntegerArr = this.b;
            if (i < bigIntegerArr.length) {
                bigIntegerArr[i] = BigInteger.valueOf((long) p.c[i]);
                i++;
            } else {
                return;
            }
        }
    }

    public BigIntPolynomial g(BigIntPolynomial poly2) {
        BigInteger[] bigIntegerArr;
        int N = this.b.length;
        if (poly2.b.length == N) {
            BigIntPolynomial c = i(poly2);
            if (c.b.length > N) {
                int k = N;
                while (true) {
                    bigIntegerArr = c.b;
                    if (k >= bigIntegerArr.length) {
                        break;
                    }
                    bigIntegerArr[k - N] = bigIntegerArr[k - N].add(bigIntegerArr[k]);
                    k++;
                }
                c.b = Arrays.A(bigIntegerArr, N);
            }
            return c;
        }
        throw new IllegalArgumentException("Number of coefficients must be the same");
    }

    private BigIntPolynomial i(BigIntPolynomial poly2) {
        BigIntPolynomial bigIntPolynomial = poly2;
        BigInteger[] a2 = this.b;
        BigInteger[] b2 = bigIntPolynomial.b;
        int n = bigIntPolynomial.b.length;
        if (n <= 1) {
            BigInteger[] c = Arrays.n(this.b);
            for (int i = 0; i < this.b.length; i++) {
                c[i] = c[i].multiply(bigIntPolynomial.b[0]);
            }
            return new BigIntPolynomial(c);
        }
        int n1 = n / 2;
        BigIntPolynomial a1 = new BigIntPolynomial(Arrays.A(a2, n1));
        BigIntPolynomial a22 = new BigIntPolynomial(Arrays.E(a2, n1, n));
        BigIntPolynomial b1 = new BigIntPolynomial(Arrays.A(b2, n1));
        BigIntPolynomial b22 = new BigIntPolynomial(Arrays.E(b2, n1, n));
        BigIntPolynomial A = (BigIntPolynomial) a1.clone();
        A.a(a22);
        BigIntPolynomial B = (BigIntPolynomial) b1.clone();
        B.a(b22);
        BigIntPolynomial c1 = a1.i(b1);
        BigIntPolynomial c2 = a22.i(b22);
        BigIntPolynomial c3 = A.i(B);
        c3.j(c1);
        c3.j(c2);
        BigIntPolynomial c4 = new BigIntPolynomial((n * 2) - 1);
        int i2 = 0;
        while (true) {
            BigInteger[] bigIntegerArr = c1.b;
            BigInteger[] a3 = a2;
            if (i2 >= bigIntegerArr.length) {
                break;
            }
            c4.b[i2] = bigIntegerArr[i2];
            i2++;
            BigIntPolynomial bigIntPolynomial2 = poly2;
            a2 = a3;
        }
        int i3 = 0;
        while (true) {
            BigInteger[] bigIntegerArr2 = c3.b;
            if (i3 >= bigIntegerArr2.length) {
                break;
            }
            BigInteger[] bigIntegerArr3 = c4.b;
            bigIntegerArr3[n1 + i3] = bigIntegerArr3[n1 + i3].add(bigIntegerArr2[i3]);
            i3++;
            b2 = b2;
        }
        int i4 = 0;
        while (true) {
            BigInteger[] bigIntegerArr4 = c2.b;
            if (i4 >= bigIntegerArr4.length) {
                return c4;
            }
            BigInteger[] bigIntegerArr5 = c4.b;
            bigIntegerArr5[(n1 * 2) + i4] = bigIntegerArr5[(n1 * 2) + i4].add(bigIntegerArr4[i4]);
            i4++;
            n = n;
        }
    }

    public void a(BigIntPolynomial b2) {
        BigInteger[] bigIntegerArr = b2.b;
        int length = bigIntegerArr.length;
        BigInteger[] bigIntegerArr2 = this.b;
        if (length > bigIntegerArr2.length) {
            int N = bigIntegerArr2.length;
            this.b = Arrays.A(bigIntegerArr2, bigIntegerArr.length);
            int i = N;
            while (true) {
                BigInteger[] bigIntegerArr3 = this.b;
                if (i >= bigIntegerArr3.length) {
                    break;
                }
                bigIntegerArr3[i] = Constants.a;
                i++;
            }
        }
        int i2 = 0;
        while (true) {
            BigInteger[] bigIntegerArr4 = b2.b;
            if (i2 < bigIntegerArr4.length) {
                BigInteger[] bigIntegerArr5 = this.b;
                bigIntegerArr5[i2] = bigIntegerArr5[i2].add(bigIntegerArr4[i2]);
                i2++;
            } else {
                return;
            }
        }
    }

    public void j(BigIntPolynomial b2) {
        BigInteger[] bigIntegerArr = b2.b;
        int length = bigIntegerArr.length;
        BigInteger[] bigIntegerArr2 = this.b;
        if (length > bigIntegerArr2.length) {
            int N = bigIntegerArr2.length;
            this.b = Arrays.A(bigIntegerArr2, bigIntegerArr.length);
            int i = N;
            while (true) {
                BigInteger[] bigIntegerArr3 = this.b;
                if (i >= bigIntegerArr3.length) {
                    break;
                }
                bigIntegerArr3[i] = Constants.a;
                i++;
            }
        }
        int i2 = 0;
        while (true) {
            BigInteger[] bigIntegerArr4 = b2.b;
            if (i2 < bigIntegerArr4.length) {
                BigInteger[] bigIntegerArr5 = this.b;
                bigIntegerArr5[i2] = bigIntegerArr5[i2].subtract(bigIntegerArr4[i2]);
                i2++;
            } else {
                return;
            }
        }
    }

    public void h(BigInteger factor) {
        int i = 0;
        while (true) {
            BigInteger[] bigIntegerArr = this.b;
            if (i < bigIntegerArr.length) {
                bigIntegerArr[i] = bigIntegerArr[i].multiply(factor);
                i++;
            } else {
                return;
            }
        }
    }

    public void c(BigInteger divisor) {
        BigInteger d = divisor.add(Constants.b).divide(BigInteger.valueOf(2));
        int i = 0;
        while (true) {
            BigInteger[] bigIntegerArr = this.b;
            if (i < bigIntegerArr.length) {
                bigIntegerArr[i] = bigIntegerArr[i].compareTo(Constants.a) > 0 ? this.b[i].add(d) : this.b[i].add(d.negate());
                BigInteger[] bigIntegerArr2 = this.b;
                bigIntegerArr2[i] = bigIntegerArr2[i].divide(divisor);
                i++;
            } else {
                return;
            }
        }
    }

    public BigDecimalPolynomial b(BigDecimal divisor, int decimalPlaces) {
        BigDecimal factor = Constants.c.divide(divisor, ((int) (((double) e().bitLength()) * a)) + 1 + decimalPlaces + 1, 6);
        BigDecimalPolynomial p = new BigDecimalPolynomial(this.b.length);
        for (int i = 0; i < this.b.length; i++) {
            p.c[i] = new BigDecimal(this.b[i]).multiply(factor).setScale(decimalPlaces, 6);
        }
        return p;
    }

    public int d() {
        return ((int) (((double) e().bitLength()) * a)) + 1;
    }

    private BigInteger e() {
        BigInteger max = this.b[0].abs();
        int i = 1;
        while (true) {
            BigInteger[] bigIntegerArr = this.b;
            if (i >= bigIntegerArr.length) {
                return max;
            }
            BigInteger coeff = bigIntegerArr[i].abs();
            if (coeff.compareTo(max) > 0) {
                max = coeff;
            }
            i++;
        }
    }

    public void f(BigInteger modulus) {
        int i = 0;
        while (true) {
            BigInteger[] bigIntegerArr = this.b;
            if (i < bigIntegerArr.length) {
                bigIntegerArr[i] = bigIntegerArr[i].mod(modulus);
                i++;
            } else {
                return;
            }
        }
    }

    public Object clone() {
        return new BigIntPolynomial((BigInteger[]) this.b.clone());
    }

    public int hashCode() {
        return (1 * 31) + Arrays.O(this.b);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && Arrays.f(this.b, ((BigIntPolynomial) obj).b)) {
            return true;
        }
        return false;
    }
}
