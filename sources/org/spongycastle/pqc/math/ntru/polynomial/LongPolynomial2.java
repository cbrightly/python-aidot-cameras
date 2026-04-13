package org.spongycastle.pqc.math.ntru.polynomial;

import org.spongycastle.util.Arrays;

public class LongPolynomial2 {
    private long[] a;
    private int b;

    public LongPolynomial2(IntegerPolynomial p) {
        int pIdx;
        long c1;
        int length = p.c.length;
        this.b = length;
        this.a = new long[((length + 1) / 2)];
        int idx = 0;
        for (int pIdx2 = 0; pIdx2 < this.b; pIdx2 = pIdx) {
            pIdx = pIdx2 + 1;
            int c0 = p.c[pIdx2];
            while (c0 < 0) {
                c0 += 2048;
            }
            if (pIdx < this.b) {
                pIdx++;
                c1 = (long) p.c[pIdx];
            } else {
                c1 = 0;
            }
            while (c1 < 0) {
                c1 += 2048;
            }
            this.a[idx] = ((long) c0) + (c1 << 24);
            idx++;
        }
    }

    private LongPolynomial2(long[] coeffs) {
        this.a = coeffs;
    }

    private LongPolynomial2(int N) {
        this.a = new long[N];
    }

    public LongPolynomial2 b(LongPolynomial2 poly2) {
        long[] jArr;
        long[] jArr2;
        int N = this.a.length;
        if (poly2.a.length == N && this.b == poly2.b) {
            LongPolynomial2 c = d(poly2);
            if (c.a.length > N) {
                if (this.b % 2 == 0) {
                    int k = N;
                    while (true) {
                        jArr2 = c.a;
                        if (k >= jArr2.length) {
                            break;
                        }
                        jArr2[k - N] = (jArr2[k - N] + jArr2[k]) & 34342963199L;
                        k++;
                    }
                    c.a = Arrays.z(jArr2, N);
                } else {
                    int k2 = N;
                    while (true) {
                        jArr = c.a;
                        if (k2 >= jArr.length) {
                            break;
                        }
                        jArr[k2 - N] = jArr[k2 - N] + (jArr[k2 - 1] >> 24);
                        jArr[k2 - N] = jArr[k2 - N] + ((2047 & jArr[k2]) << 24);
                        int i = k2 - N;
                        jArr[i] = jArr[i] & 34342963199L;
                        k2++;
                    }
                    long[] z = Arrays.z(jArr, N);
                    c.a = z;
                    int length = z.length - 1;
                    z[length] = z[length] & 2047;
                }
            }
            LongPolynomial2 c2 = new LongPolynomial2(c.a);
            c2.b = this.b;
            return c2;
        }
        throw new IllegalArgumentException("Number of coefficients must be the same");
    }

    public IntegerPolynomial g() {
        int[] intCoeffs = new int[this.b];
        int uIdx = 0;
        int i = 0;
        while (true) {
            long[] jArr = this.a;
            if (i >= jArr.length) {
                return new IntegerPolynomial(intCoeffs);
            }
            int uIdx2 = uIdx + 1;
            intCoeffs[uIdx] = (int) (jArr[i] & 2047);
            if (uIdx2 < this.b) {
                uIdx = uIdx2 + 1;
                intCoeffs[uIdx2] = (int) ((jArr[i] >> 24) & 2047);
            } else {
                uIdx = uIdx2;
            }
            i++;
        }
    }

    private LongPolynomial2 d(LongPolynomial2 poly2) {
        LongPolynomial2 longPolynomial2 = poly2;
        long[] a2 = this.a;
        long[] b2 = longPolynomial2.a;
        int n = longPolynomial2.a.length;
        if (n <= 32) {
            int cn = n * 2;
            LongPolynomial2 c = new LongPolynomial2(new long[cn]);
            for (int k = 0; k < cn; k++) {
                for (int i = Math.max(0, (k - n) + 1); i <= Math.min(k, n - 1); i++) {
                    long c0 = a2[k - i] * b2[i];
                    long[] jArr = c.a;
                    jArr[k] = (jArr[k] + (c0 & ((c0 & 2047) + 34342961152L))) & 34342963199L;
                    jArr[k + 1] = (jArr[k + 1] + ((c0 >>> 48) & 2047)) & 34342963199L;
                }
            }
            return c;
        }
        int n1 = n / 2;
        LongPolynomial2 a1 = new LongPolynomial2(Arrays.z(a2, n1));
        LongPolynomial2 a22 = new LongPolynomial2(Arrays.D(a2, n1, n));
        LongPolynomial2 b1 = new LongPolynomial2(Arrays.z(b2, n1));
        LongPolynomial2 b22 = new LongPolynomial2(Arrays.D(b2, n1, n));
        LongPolynomial2 A = (LongPolynomial2) a1.clone();
        A.a(a22);
        LongPolynomial2 B = (LongPolynomial2) b1.clone();
        B.a(b22);
        LongPolynomial2 c1 = a1.d(b1);
        LongPolynomial2 c2 = a22.d(b22);
        LongPolynomial2 c3 = A.d(B);
        c3.e(c1);
        c3.e(c2);
        LongPolynomial2 c4 = new LongPolynomial2(n * 2);
        int i2 = 0;
        while (true) {
            long[] jArr2 = c1.a;
            long[] a3 = a2;
            if (i2 >= jArr2.length) {
                break;
            }
            c4.a[i2] = jArr2[i2] & 34342963199L;
            i2++;
            a2 = a3;
        }
        int i3 = 0;
        while (true) {
            long[] jArr3 = c3.a;
            if (i3 >= jArr3.length) {
                break;
            }
            long[] jArr4 = c4.a;
            jArr4[n1 + i3] = (jArr4[n1 + i3] + jArr3[i3]) & 34342963199L;
            i3++;
        }
        int i4 = 0;
        while (true) {
            long[] jArr5 = c2.a;
            if (i4 >= jArr5.length) {
                return c4;
            }
            long[] jArr6 = c4.a;
            jArr6[(n1 * 2) + i4] = (jArr6[(n1 * 2) + i4] + jArr5[i4]) & 34342963199L;
            i4++;
        }
    }

    private void a(LongPolynomial2 b2) {
        long[] jArr = b2.a;
        int length = jArr.length;
        long[] jArr2 = this.a;
        if (length > jArr2.length) {
            this.a = Arrays.z(jArr2, jArr.length);
        }
        int i = 0;
        while (true) {
            long[] jArr3 = b2.a;
            if (i < jArr3.length) {
                long[] jArr4 = this.a;
                jArr4[i] = (jArr4[i] + jArr3[i]) & 34342963199L;
                i++;
            } else {
                return;
            }
        }
    }

    private void e(LongPolynomial2 b2) {
        long[] jArr = b2.a;
        int length = jArr.length;
        long[] jArr2 = this.a;
        if (length > jArr2.length) {
            this.a = Arrays.z(jArr2, jArr.length);
        }
        int i = 0;
        while (true) {
            long[] jArr3 = b2.a;
            if (i < jArr3.length) {
                long[] jArr4 = this.a;
                jArr4[i] = 34342963199L & ((jArr4[i] + 140737496743936L) - jArr3[i]);
                i++;
            } else {
                return;
            }
        }
    }

    public void f(LongPolynomial2 b2, int mask) {
        long longMask = (((long) mask) << 24) + ((long) mask);
        int i = 0;
        while (true) {
            long[] jArr = b2.a;
            if (i < jArr.length) {
                long[] jArr2 = this.a;
                jArr2[i] = ((jArr2[i] + 140737496743936L) - jArr[i]) & longMask;
                i++;
            } else {
                return;
            }
        }
    }

    public void c(int mask) {
        long longMask = (((long) mask) << 24) + ((long) mask);
        int i = 0;
        while (true) {
            long[] jArr = this.a;
            if (i < jArr.length) {
                jArr[i] = (jArr[i] << 1) & longMask;
                i++;
            } else {
                return;
            }
        }
    }

    public Object clone() {
        LongPolynomial2 p = new LongPolynomial2((long[]) this.a.clone());
        p.b = this.b;
        return p;
    }

    public boolean equals(Object obj) {
        if (obj instanceof LongPolynomial2) {
            return Arrays.e(this.a, ((LongPolynomial2) obj).a);
        }
        return false;
    }
}
