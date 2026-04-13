package org.spongycastle.pqc.math.linearalgebra;

import java.lang.reflect.Array;
import java.security.SecureRandom;

public final class GoppaCode {

    public static class MatrixSet {
    }

    private GoppaCode() {
    }

    public static class MaMaPe {
        private GF2Matrix a;
        private GF2Matrix b;
        private Permutation c;

        public MaMaPe(GF2Matrix s, GF2Matrix h, Permutation p) {
            this.a = s;
            this.b = h;
            this.c = p;
        }

        public GF2Matrix b() {
            return this.b;
        }

        public Permutation a() {
            return this.c;
        }
    }

    public static GF2Matrix b(GF2mField field, PolynomialGF2mSmallM gp) {
        GF2mField gF2mField = field;
        PolynomialGF2mSmallM polynomialGF2mSmallM = gp;
        Class<int> cls = int.class;
        int m = field.d();
        int n = 1 << m;
        int t = gp.l();
        int[] iArr = new int[2];
        iArr[1] = n;
        iArr[0] = t;
        int[][] hArray = (int[][]) Array.newInstance(cls, iArr);
        int[] iArr2 = new int[2];
        iArr2[1] = n;
        iArr2[0] = t;
        int[][] yz = (int[][]) Array.newInstance(cls, iArr2);
        for (int j = 0; j < n; j++) {
            yz[0][j] = gF2mField.h(polynomialGF2mSmallM.i(j));
        }
        for (int i = 1; i < t; i++) {
            for (int j2 = 0; j2 < n; j2++) {
                yz[i][j2] = gF2mField.j(yz[i - 1][j2], j2);
            }
        }
        for (int i2 = 0; i2 < t; i2++) {
            for (int j3 = 0; j3 < n; j3++) {
                for (int k = 0; k <= i2; k++) {
                    hArray[i2][j3] = gF2mField.a(hArray[i2][j3], gF2mField.j(yz[k][j3], polynomialGF2mSmallM.k((t + k) - i2)));
                }
            }
        }
        int[] iArr3 = new int[2];
        iArr3[1] = (n + 31) >>> 5;
        iArr3[0] = t * m;
        int[][] result = (int[][]) Array.newInstance(cls, iArr3);
        for (int j4 = 0; j4 < n; j4++) {
            int q = j4 >>> 5;
            int r = 1 << (j4 & 31);
            for (int i3 = 0; i3 < t; i3++) {
                int e = hArray[i3][j4];
                for (int u = 0; u < m; u++) {
                    if (((e >>> u) & 1) != 0) {
                        int[] iArr4 = result[(((i3 + 1) * m) - u) - 1];
                        iArr4[q] = iArr4[q] ^ r;
                    }
                }
            }
        }
        return new GF2Matrix(n, result);
    }

    public static MaMaPe a(GF2Matrix h, SecureRandom sr) {
        Permutation p;
        GF2Matrix hp;
        GF2Matrix sInv;
        boolean found;
        int n = h.a();
        GF2Matrix s = null;
        do {
            p = new Permutation(n, sr);
            hp = (GF2Matrix) h.s(p);
            sInv = hp.n();
            found = true;
            try {
                s = (GF2Matrix) sInv.i();
                continue;
            } catch (ArithmeticException e) {
                found = false;
                continue;
            }
        } while (!found);
        return new MaMaPe(sInv, ((GF2Matrix) s.r(hp)).o(), p);
    }

    public static GF2Vector c(GF2Vector syndVec, GF2mField field, PolynomialGF2mSmallM gp, PolynomialGF2mSmallM[] sqRootMatrix) {
        PolynomialGF2mSmallM polynomialGF2mSmallM = gp;
        int n = 1 << field.d();
        GF2Vector errors = new GF2Vector(n);
        if (!syndVec.h()) {
            PolynomialGF2mSmallM[] ab = new PolynomialGF2mSmallM(syndVec.k(field)).u(polynomialGF2mSmallM).c(1).x(sqRootMatrix).w(polynomialGF2mSmallM);
            PolynomialGF2mSmallM a2plusXb2 = ab[0].D(ab[0]).a(ab[1].D(ab[1]).B(1));
            PolynomialGF2mSmallM elp = a2plusXb2.z(field.h(a2plusXb2.n()));
            for (int i = 0; i < n; i++) {
                if (elp.i(i) == 0) {
                    errors.j(i);
                }
            }
        } else {
            GF2mField gF2mField = field;
            PolynomialGF2mSmallM[] polynomialGF2mSmallMArr = sqRootMatrix;
        }
        return errors;
    }
}
