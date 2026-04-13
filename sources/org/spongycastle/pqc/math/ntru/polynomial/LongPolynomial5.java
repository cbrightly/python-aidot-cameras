package org.spongycastle.pqc.math.ntru.polynomial;

import java.lang.reflect.Array;
import org.spongycastle.util.Arrays;

public class LongPolynomial5 {
    private long[] a;
    private int b;

    public LongPolynomial5(IntegerPolynomial p) {
        int length = p.c.length;
        this.b = length;
        this.a = new long[((length + 4) / 5)];
        int cIdx = 0;
        int shift = 0;
        for (int i = 0; i < this.b; i++) {
            long[] jArr = this.a;
            jArr[cIdx] = jArr[cIdx] | (((long) p.c[i]) << shift);
            shift += 12;
            if (shift >= 60) {
                shift = 0;
                cIdx++;
            }
        }
    }

    private LongPolynomial5(long[] coeffs, int numCoeffs) {
        this.a = coeffs;
        this.b = numCoeffs;
    }

    public LongPolynomial5 a(TernaryPolynomial poly2) {
        long iCoeff;
        int newIdx;
        int i = 1;
        int[] iArr = new int[2];
        iArr[1] = (this.a.length + ((poly2.size() + 4) / 5)) - 1;
        iArr[0] = 5;
        long[][] prod = (long[][]) Array.newInstance(long.class, iArr);
        int[] ones = poly2.d();
        for (int idx = 0; idx != ones.length; idx++) {
            int pIdx = ones[idx];
            int cIdx = pIdx / 5;
            int m = pIdx - (cIdx * 5);
            int i2 = 0;
            while (true) {
                long[] jArr = this.a;
                if (i2 >= jArr.length) {
                    break;
                }
                prod[m][cIdx] = (prod[m][cIdx] + jArr[i2]) & 576319980446939135L;
                cIdx++;
                i2++;
            }
        }
        int[] negOnes = poly2.f();
        for (int idx2 = 0; idx2 != negOnes.length; idx2++) {
            int pIdx2 = negOnes[idx2];
            int cIdx2 = pIdx2 / 5;
            int m2 = pIdx2 - (cIdx2 * 5);
            int i3 = 0;
            while (true) {
                long[] jArr2 = this.a;
                if (i3 >= jArr2.length) {
                    break;
                }
                prod[m2][cIdx2] = ((prod[m2][cIdx2] + 576601524159907840L) - jArr2[i3]) & 576319980446939135L;
                cIdx2++;
                i3++;
            }
        }
        long[] cCoeffs = Arrays.z(prod[0], prod[0].length + 1);
        for (int m3 = 1; m3 <= 4; m3++) {
            int shift = m3 * 12;
            int shift60 = 60 - shift;
            long mask = (1 << shift60) - 1;
            int pLen = prod[m3].length;
            for (int i4 = 0; i4 < pLen; i4++) {
                cCoeffs[i4] = (cCoeffs[i4] + ((prod[m3][i4] & mask) << shift)) & 576319980446939135L;
                int nextIdx = i4 + 1;
                cCoeffs[nextIdx] = (cCoeffs[nextIdx] + (prod[m3][i4] >> shift60)) & 576319980446939135L;
            }
        }
        int shift2 = (this.b % 5) * 12;
        int cIdx3 = this.a.length - 1;
        while (cIdx3 < cCoeffs.length) {
            long[] jArr3 = this.a;
            if (cIdx3 == jArr3.length - i) {
                iCoeff = this.b == 5 ? 0 : cCoeffs[cIdx3] >> shift2;
                newIdx = 0;
            } else {
                iCoeff = cCoeffs[cIdx3];
                newIdx = (cIdx3 * 5) - this.b;
            }
            int base = newIdx / 5;
            int m4 = newIdx - (base * 5);
            long upper = iCoeff >> ((5 - m4) * 12);
            cCoeffs[base] = (cCoeffs[base] + (iCoeff << (m4 * 12))) & 576319980446939135L;
            int base1 = base + 1;
            if (base1 < jArr3.length) {
                cCoeffs[base1] = (cCoeffs[base1] + upper) & 576319980446939135L;
            }
            cIdx3++;
            i = 1;
        }
        return new LongPolynomial5(cCoeffs, this.b);
    }

    public IntegerPolynomial b() {
        int[] intCoeffs = new int[this.b];
        int cIdx = 0;
        int shift = 0;
        for (int i = 0; i < this.b; i++) {
            intCoeffs[i] = (int) ((this.a[cIdx] >> shift) & 2047);
            shift += 12;
            if (shift >= 60) {
                shift = 0;
                cIdx++;
            }
        }
        return new IntegerPolynomial(intCoeffs);
    }
}
