package org.spongycastle.pqc.math.linearalgebra;

public class PolynomialRingGF2m {
    private GF2mField a;
    private PolynomialGF2mSmallM b;
    protected PolynomialGF2mSmallM[] c;
    protected PolynomialGF2mSmallM[] d;

    public PolynomialRingGF2m(GF2mField field, PolynomialGF2mSmallM p) {
        this.a = field;
        this.b = p;
        b();
        a();
    }

    public PolynomialGF2mSmallM[] c() {
        return this.d;
    }

    private void b() {
        int numColumns = this.b.l();
        this.c = new PolynomialGF2mSmallM[numColumns];
        for (int i = 0; i < (numColumns >> 1); i++) {
            int[] monomCoeffs = new int[((i << 1) + 1)];
            monomCoeffs[i << 1] = 1;
            this.c[i] = new PolynomialGF2mSmallM(this.a, monomCoeffs);
        }
        for (int i2 = numColumns >> 1; i2 < numColumns; i2++) {
            int[] monomCoeffs2 = new int[((i2 << 1) + 1)];
            monomCoeffs2[i2 << 1] = 1;
            this.c[i2] = new PolynomialGF2mSmallM(this.a, monomCoeffs2).r(this.b);
        }
    }

    private void a() {
        int coef;
        int numColumns = this.b.l();
        PolynomialGF2mSmallM[] tmpMatrix = new PolynomialGF2mSmallM[numColumns];
        for (int i = numColumns - 1; i >= 0; i--) {
            tmpMatrix[i] = new PolynomialGF2mSmallM(this.c[i]);
        }
        this.d = new PolynomialGF2mSmallM[numColumns];
        for (int i2 = numColumns - 1; i2 >= 0; i2--) {
            this.d[i2] = new PolynomialGF2mSmallM(this.a, i2);
        }
        for (int i3 = 0; i3 < numColumns; i3++) {
            if (tmpMatrix[i3].k(i3) == 0) {
                boolean foundNonZero = false;
                int j = i3 + 1;
                while (j < numColumns) {
                    if (tmpMatrix[j].k(i3) != 0) {
                        foundNonZero = true;
                        d(tmpMatrix, i3, j);
                        d(this.d, i3, j);
                        j = numColumns;
                    }
                    j++;
                }
                if (!foundNonZero) {
                    throw new ArithmeticException("Squaring matrix is not invertible.");
                }
            }
            int invCoef = this.a.h(tmpMatrix[i3].k(i3));
            tmpMatrix[i3].y(invCoef);
            this.d[i3].y(invCoef);
            for (int j2 = 0; j2 < numColumns; j2++) {
                if (!(j2 == i3 || (coef = tmpMatrix[j2].k(i3)) == 0)) {
                    PolynomialGF2mSmallM tmpSqColumn = tmpMatrix[i3].z(coef);
                    PolynomialGF2mSmallM tmpInvColumn = this.d[i3].z(coef);
                    tmpMatrix[j2].d(tmpSqColumn);
                    this.d[j2].d(tmpInvColumn);
                }
            }
        }
    }

    private static void d(PolynomialGF2mSmallM[] matrix, int first, int second) {
        PolynomialGF2mSmallM tmp = matrix[first];
        matrix[first] = matrix[second];
        matrix[second] = tmp;
    }
}
