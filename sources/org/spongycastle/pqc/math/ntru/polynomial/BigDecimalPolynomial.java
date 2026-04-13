package org.spongycastle.pqc.math.ntru.polynomial;

import java.math.BigDecimal;

public class BigDecimalPolynomial {
    private static final BigDecimal a = new BigDecimal("0");
    private static final BigDecimal b = new BigDecimal("0.5");
    BigDecimal[] c;

    BigDecimalPolynomial(int N) {
        this.c = new BigDecimal[N];
        for (int i = 0; i < N; i++) {
            this.c[i] = a;
        }
    }

    BigDecimalPolynomial(BigDecimal[] coeffs) {
        this.c = coeffs;
    }

    public BigDecimalPolynomial(BigIntPolynomial p) {
        int N = p.b.length;
        this.c = new BigDecimal[N];
        for (int i = 0; i < N; i++) {
            this.c[i] = new BigDecimal(p.b[i]);
        }
    }

    public void d() {
        int i = 0;
        while (true) {
            BigDecimal[] bigDecimalArr = this.c;
            if (i < bigDecimalArr.length) {
                bigDecimalArr[i] = bigDecimalArr[i].multiply(b);
                i++;
            } else {
                return;
            }
        }
    }

    public BigDecimalPolynomial f(BigIntPolynomial poly2) {
        return e(new BigDecimalPolynomial(poly2));
    }

    public BigDecimalPolynomial e(BigDecimalPolynomial poly2) {
        BigDecimal[] bigDecimalArr;
        int N = this.c.length;
        if (poly2.c.length == N) {
            BigDecimalPolynomial c2 = g(poly2);
            if (c2.c.length > N) {
                int k = N;
                while (true) {
                    bigDecimalArr = c2.c;
                    if (k >= bigDecimalArr.length) {
                        break;
                    }
                    bigDecimalArr[k - N] = bigDecimalArr[k - N].add(bigDecimalArr[k]);
                    k++;
                }
                c2.c = b(bigDecimalArr, N);
            }
            return c2;
        }
        throw new IllegalArgumentException("Number of coefficients must be the same");
    }

    private BigDecimalPolynomial g(BigDecimalPolynomial poly2) {
        BigDecimalPolynomial bigDecimalPolynomial = poly2;
        BigDecimal[] a2 = this.c;
        BigDecimal[] b2 = bigDecimalPolynomial.c;
        int n = bigDecimalPolynomial.c.length;
        if (n <= 1) {
            BigDecimal[] c2 = (BigDecimal[]) this.c.clone();
            for (int i = 0; i < this.c.length; i++) {
                c2[i] = c2[i].multiply(bigDecimalPolynomial.c[0]);
            }
            return new BigDecimalPolynomial(c2);
        }
        int n1 = n / 2;
        BigDecimalPolynomial a1 = new BigDecimalPolynomial(b(a2, n1));
        BigDecimalPolynomial a22 = new BigDecimalPolynomial(c(a2, n1, n));
        BigDecimalPolynomial b1 = new BigDecimalPolynomial(b(b2, n1));
        BigDecimalPolynomial b22 = new BigDecimalPolynomial(c(b2, n1, n));
        BigDecimalPolynomial A = (BigDecimalPolynomial) a1.clone();
        A.a(a22);
        BigDecimalPolynomial B = (BigDecimalPolynomial) b1.clone();
        B.a(b22);
        BigDecimalPolynomial c1 = a1.g(b1);
        BigDecimalPolynomial c22 = a22.g(b22);
        BigDecimalPolynomial c3 = A.g(B);
        c3.i(c1);
        c3.i(c22);
        BigDecimalPolynomial c4 = new BigDecimalPolynomial((n * 2) - 1);
        int i2 = 0;
        while (true) {
            BigDecimal[] bigDecimalArr = c1.c;
            BigDecimal[] a3 = a2;
            if (i2 >= bigDecimalArr.length) {
                break;
            }
            c4.c[i2] = bigDecimalArr[i2];
            i2++;
            BigDecimalPolynomial bigDecimalPolynomial2 = poly2;
            a2 = a3;
        }
        int i3 = 0;
        while (true) {
            BigDecimal[] bigDecimalArr2 = c3.c;
            if (i3 >= bigDecimalArr2.length) {
                break;
            }
            BigDecimal[] bigDecimalArr3 = c4.c;
            bigDecimalArr3[n1 + i3] = bigDecimalArr3[n1 + i3].add(bigDecimalArr2[i3]);
            i3++;
            b2 = b2;
        }
        int i4 = 0;
        while (true) {
            BigDecimal[] bigDecimalArr4 = c22.c;
            if (i4 >= bigDecimalArr4.length) {
                return c4;
            }
            BigDecimal[] bigDecimalArr5 = c4.c;
            bigDecimalArr5[(n1 * 2) + i4] = bigDecimalArr5[(n1 * 2) + i4].add(bigDecimalArr4[i4]);
            i4++;
            n = n;
        }
    }

    public void a(BigDecimalPolynomial b2) {
        BigDecimal[] bigDecimalArr = b2.c;
        int length = bigDecimalArr.length;
        BigDecimal[] bigDecimalArr2 = this.c;
        if (length > bigDecimalArr2.length) {
            int N = bigDecimalArr2.length;
            this.c = b(bigDecimalArr2, bigDecimalArr.length);
            int i = N;
            while (true) {
                BigDecimal[] bigDecimalArr3 = this.c;
                if (i >= bigDecimalArr3.length) {
                    break;
                }
                bigDecimalArr3[i] = a;
                i++;
            }
        }
        int i2 = 0;
        while (true) {
            BigDecimal[] bigDecimalArr4 = b2.c;
            if (i2 < bigDecimalArr4.length) {
                BigDecimal[] bigDecimalArr5 = this.c;
                bigDecimalArr5[i2] = bigDecimalArr5[i2].add(bigDecimalArr4[i2]);
                i2++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void i(BigDecimalPolynomial b2) {
        BigDecimal[] bigDecimalArr = b2.c;
        int length = bigDecimalArr.length;
        BigDecimal[] bigDecimalArr2 = this.c;
        if (length > bigDecimalArr2.length) {
            int N = bigDecimalArr2.length;
            this.c = b(bigDecimalArr2, bigDecimalArr.length);
            int i = N;
            while (true) {
                BigDecimal[] bigDecimalArr3 = this.c;
                if (i >= bigDecimalArr3.length) {
                    break;
                }
                bigDecimalArr3[i] = a;
                i++;
            }
        }
        int i2 = 0;
        while (true) {
            BigDecimal[] bigDecimalArr4 = b2.c;
            if (i2 < bigDecimalArr4.length) {
                BigDecimal[] bigDecimalArr5 = this.c;
                bigDecimalArr5[i2] = bigDecimalArr5[i2].subtract(bigDecimalArr4[i2]);
                i2++;
            } else {
                return;
            }
        }
    }

    public BigIntPolynomial h() {
        int N = this.c.length;
        BigIntPolynomial p = new BigIntPolynomial(N);
        for (int i = 0; i < N; i++) {
            p.b[i] = this.c[i].setScale(0, 6).toBigInteger();
        }
        return p;
    }

    public Object clone() {
        return new BigDecimalPolynomial((BigDecimal[]) this.c.clone());
    }

    private BigDecimal[] b(BigDecimal[] a2, int length) {
        BigDecimal[] tmp = new BigDecimal[length];
        System.arraycopy(a2, 0, tmp, 0, a2.length < length ? a2.length : length);
        return tmp;
    }

    private BigDecimal[] c(BigDecimal[] a2, int from, int to) {
        int newLength = to - from;
        BigDecimal[] tmp = new BigDecimal[(to - from)];
        System.arraycopy(a2, from, tmp, 0, a2.length - from < newLength ? a2.length - from : newLength);
        return tmp;
    }
}
