package org.spongycastle.pqc.math.linearalgebra;

import com.meituan.robust.Constants;
import java.security.SecureRandom;
import org.slf4j.e;

public class PolynomialGF2mSmallM {
    private GF2mField a;
    private int b;
    private int[] c;

    public PolynomialGF2mSmallM(GF2mField field, int deg, char typeOfPolynomial, SecureRandom sr) {
        this.a = field;
        switch (typeOfPolynomial) {
            case 'I':
                this.c = g(deg, sr);
                f();
                return;
            default:
                throw new IllegalArgumentException(" Error: type " + typeOfPolynomial + " is not defined for GF2smallmPolynomial");
        }
    }

    private int[] g(int deg, SecureRandom sr) {
        int[] resCoeff = new int[(deg + 1)];
        resCoeff[deg] = 1;
        resCoeff[0] = this.a.g(sr);
        for (int i = 1; i < deg; i++) {
            resCoeff[i] = this.a.f(sr);
        }
        while (q(resCoeff) == 0) {
            int n = RandUtils.a(sr, deg);
            if (n == 0) {
                resCoeff[0] = this.a.g(sr);
            } else {
                resCoeff[n] = this.a.f(sr);
            }
        }
        return resCoeff;
    }

    public PolynomialGF2mSmallM(GF2mField field, int degree) {
        this.a = field;
        this.b = degree;
        int[] iArr = new int[(degree + 1)];
        this.c = iArr;
        iArr[degree] = 1;
    }

    public PolynomialGF2mSmallM(GF2mField field, int[] coeffs) {
        this.a = field;
        this.c = F(coeffs);
        f();
    }

    public PolynomialGF2mSmallM(GF2mField field, byte[] enc) {
        this.a = field;
        int d = 8;
        int count = 1;
        while (field.d() > d) {
            count++;
            d += 8;
        }
        if (enc.length % count == 0) {
            this.c = new int[(enc.length / count)];
            int count2 = 0;
            int i = 0;
            while (true) {
                int[] iArr = this.c;
                if (i < iArr.length) {
                    int j = 0;
                    while (j < d) {
                        int[] iArr2 = this.c;
                        iArr2[i] = ((enc[count2] & 255) << j) ^ iArr2[i];
                        j += 8;
                        count2++;
                    }
                    if (this.a.i(this.c[i])) {
                        i++;
                    } else {
                        throw new IllegalArgumentException(" Error: byte array is not encoded polynomial over given finite field GF2m");
                    }
                } else if (iArr.length == 1 || iArr[iArr.length - 1] != 0) {
                    f();
                    return;
                } else {
                    throw new IllegalArgumentException(" Error: byte array is not encoded polynomial over given finite field GF2m");
                }
            }
        } else {
            throw new IllegalArgumentException(" Error: byte array is not encoded polynomial over given finite field GF2m");
        }
    }

    public PolynomialGF2mSmallM(PolynomialGF2mSmallM other) {
        this.a = other.a;
        this.b = other.b;
        this.c = IntUtils.a(other.c);
    }

    public PolynomialGF2mSmallM(GF2mVector vect) {
        this(vect.c(), vect.d());
    }

    public int l() {
        int[] iArr = this.c;
        int d = iArr.length - 1;
        if (iArr[d] == 0) {
            return -1;
        }
        return d;
    }

    public int n() {
        int i = this.b;
        if (i == -1) {
            return 0;
        }
        return this.c[i];
    }

    private static int o(int[] a2) {
        int degree = e(a2);
        if (degree == -1) {
            return 0;
        }
        return a2[degree];
    }

    public int k(int index) {
        if (index < 0 || index > this.b) {
            return 0;
        }
        return this.c[index];
    }

    public byte[] m() {
        int d = 8;
        int count = 1;
        while (this.a.d() > d) {
            count++;
            d += 8;
        }
        byte[] res = new byte[(this.c.length * count)];
        int count2 = 0;
        for (int i = 0; i < this.c.length; i++) {
            int j = 0;
            while (j < d) {
                res[count2] = (byte) (this.c[i] >>> j);
                j += 8;
                count2++;
            }
        }
        return res;
    }

    public int i(int e) {
        int[] iArr = this.c;
        int i = this.b;
        int result = iArr[i];
        for (int i2 = i - 1; i2 >= 0; i2--) {
            result = this.a.j(result, e) ^ this.c[i2];
        }
        return result;
    }

    public PolynomialGF2mSmallM a(PolynomialGF2mSmallM addend) {
        return new PolynomialGF2mSmallM(this.a, b(this.c, addend.c));
    }

    public void d(PolynomialGF2mSmallM addend) {
        this.c = b(this.c, addend.c);
        f();
    }

    private int[] b(int[] a2, int[] b2) {
        int[] addend;
        int[] result;
        if (a2.length < b2.length) {
            result = new int[b2.length];
            System.arraycopy(b2, 0, result, 0, b2.length);
            addend = a2;
        } else {
            result = new int[a2.length];
            System.arraycopy(a2, 0, result, 0, a2.length);
            addend = b2;
        }
        for (int i = addend.length - 1; i >= 0; i--) {
            result[i] = this.a.a(result[i], addend[i]);
        }
        return result;
    }

    public PolynomialGF2mSmallM c(int degree) {
        int[] monomial = new int[(degree + 1)];
        monomial[degree] = 1;
        return new PolynomialGF2mSmallM(this.a, b(this.c, monomial));
    }

    public PolynomialGF2mSmallM z(int element) {
        if (this.a.i(element)) {
            return new PolynomialGF2mSmallM(this.a, A(this.c, element));
        }
        throw new ArithmeticException("Not an element of the finite field this polynomial is defined over.");
    }

    public void y(int element) {
        if (this.a.i(element)) {
            this.c = A(this.c, element);
            f();
            return;
        }
        throw new ArithmeticException("Not an element of the finite field this polynomial is defined over.");
    }

    private int[] A(int[] a2, int element) {
        int degree = e(a2);
        if (degree == -1 || element == 0) {
            return new int[1];
        }
        if (element == 1) {
            return IntUtils.a(a2);
        }
        int[] result = new int[(degree + 1)];
        for (int i = degree; i >= 0; i--) {
            result[i] = this.a.j(a2[i], element);
        }
        return result;
    }

    public PolynomialGF2mSmallM B(int k) {
        return new PolynomialGF2mSmallM(this.a, C(this.c, k));
    }

    private static int[] C(int[] a2, int k) {
        int d = e(a2);
        if (d == -1) {
            return new int[1];
        }
        int[] result = new int[(d + k + 1)];
        System.arraycopy(a2, 0, result, k, d + 1);
        return result;
    }

    private int[][] h(int[] a2, int[] f) {
        int df = e(f);
        int da = e(a2) + 1;
        if (df != -1) {
            int[][] result = {new int[1], new int[da]};
            int hc = this.a.h(o(f));
            result[0][0] = 0;
            System.arraycopy(a2, 0, result[1], 0, result[1].length);
            while (df <= e(result[1])) {
                int[] coeff = {this.a.j(o(result[1]), hc)};
                int[] q = A(f, coeff[0]);
                int n = e(result[1]) - df;
                int[] q2 = C(q, n);
                result[0] = b(C(coeff, n), result[0]);
                result[1] = b(q2, result[1]);
            }
            return result;
        }
        throw new ArithmeticException("Division by zero.");
    }

    private int[] j(int[] f, int[] g) {
        int[] a2 = f;
        int[] b2 = g;
        if (e(a2) == -1) {
            return b2;
        }
        while (e(b2) != -1) {
            int[] c2 = s(a2, b2);
            a2 = new int[b2.length];
            System.arraycopy(b2, 0, a2, 0, a2.length);
            b2 = new int[c2.length];
            System.arraycopy(c2, 0, b2, 0, b2.length);
        }
        return A(a2, this.a.h(o(a2)));
    }

    public PolynomialGF2mSmallM D(PolynomialGF2mSmallM factor) {
        return new PolynomialGF2mSmallM(this.a, E(this.c, factor.c));
    }

    private int[] E(int[] a2, int[] b2) {
        int[] mult2;
        int[] mult1;
        if (e(a2) < e(b2)) {
            mult1 = b2;
            mult2 = a2;
        } else {
            mult1 = a2;
            mult2 = b2;
        }
        int[] mult12 = F(mult1);
        int[] mult22 = F(mult2);
        if (mult22.length == 1) {
            return A(mult12, mult22[0]);
        }
        int d1 = mult12.length;
        int d2 = mult22.length;
        int[] iArr = new int[((d1 + d2) - 1)];
        if (d2 != d1) {
            int[] res1 = new int[d2];
            int[] res2 = new int[(d1 - d2)];
            System.arraycopy(mult12, 0, res1, 0, res1.length);
            System.arraycopy(mult12, d2, res2, 0, res2.length);
            return b(E(res1, mult22), C(E(res2, mult22), d2));
        }
        int d22 = (d1 + 1) >>> 1;
        int d = d1 - d22;
        int[] firstPartMult1 = new int[d22];
        int[] firstPartMult2 = new int[d22];
        int[] secondPartMult1 = new int[d];
        int[] secondPartMult2 = new int[d];
        System.arraycopy(mult12, 0, firstPartMult1, 0, firstPartMult1.length);
        System.arraycopy(mult12, d22, secondPartMult1, 0, secondPartMult1.length);
        System.arraycopy(mult22, 0, firstPartMult2, 0, firstPartMult2.length);
        System.arraycopy(mult22, d22, secondPartMult2, 0, secondPartMult2.length);
        int[] helpPoly1 = b(firstPartMult1, secondPartMult1);
        int[] helpPoly2 = b(firstPartMult2, secondPartMult2);
        int[] res12 = E(firstPartMult1, firstPartMult2);
        int[] res22 = E(helpPoly1, helpPoly2);
        try {
            int[] res3 = E(secondPartMult1, secondPartMult2);
            return b(C(b(b(b(res22, res12), res3), C(res3, d22)), d22), res12);
        } finally {
            int[] result = result;
        }
    }

    private boolean q(int[] a2) {
        if (a2[0] == 0) {
            return false;
        }
        int d = e(a2) >> 1;
        int[] u = {0, 1};
        int[] Y = {0, 1};
        int fieldDegree = this.a.d();
        for (int i = 0; i < d; i++) {
            for (int j = fieldDegree - 1; j >= 0; j--) {
                u = v(u, u, a2);
            }
            u = F(u);
            if (e(j(b(u, Y), a2)) != 0) {
                return false;
            }
        }
        return true;
    }

    public PolynomialGF2mSmallM r(PolynomialGF2mSmallM f) {
        return new PolynomialGF2mSmallM(this.a, s(this.c, f.c));
    }

    private int[] s(int[] a2, int[] f) {
        int df = e(f);
        if (df != -1) {
            int[] result = new int[a2.length];
            int hc = this.a.h(o(f));
            System.arraycopy(a2, 0, result, 0, result.length);
            while (df <= e(result)) {
                result = b(A(C(f, e(result) - df), this.a.j(o(result), hc)), result);
            }
            return result;
        }
        throw new ArithmeticException("Division by zero");
    }

    private int[] v(int[] a2, int[] b2, int[] g) {
        return s(E(a2, b2), g);
    }

    public PolynomialGF2mSmallM x(PolynomialGF2mSmallM[] matrix) {
        int length = matrix.length;
        int[] resultCoeff = new int[length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (i < matrix[j].c.length) {
                    int[] iArr = this.c;
                    if (j < iArr.length) {
                        resultCoeff[i] = this.a.a(resultCoeff[i], this.a.j(matrix[j].c[i], iArr[j]));
                    }
                }
            }
        }
        for (int i2 = 0; i2 < length; i2++) {
            resultCoeff[i2] = this.a.l(resultCoeff[i2]);
        }
        return new PolynomialGF2mSmallM(this.a, resultCoeff);
    }

    private int[] t(int[] a2, int[] b2, int[] g) {
        int[] r0 = F(g);
        int[] r1 = s(b2, g);
        int[] s0 = {0};
        int[] s1 = s(a2, g);
        while (e(r1) != -1) {
            int[][] q = h(r0, r1);
            r0 = F(r1);
            r1 = F(q[1]);
            int[] s2 = b(s0, v(q[0], s1, g));
            s0 = F(s1);
            s1 = F(s2);
        }
        return A(s0, this.a.h(o(r0)));
    }

    public PolynomialGF2mSmallM u(PolynomialGF2mSmallM a2) {
        return new PolynomialGF2mSmallM(this.a, t(new int[]{1}, this.c, a2.c));
    }

    public PolynomialGF2mSmallM[] w(PolynomialGF2mSmallM g) {
        int dg = g.b >> 1;
        int[] a0 = F(g.c);
        int[] a1 = s(this.c, g.c);
        int[] b0 = {0};
        int[] b1 = {1};
        while (e(a1) > dg) {
            int[][] q = h(a0, a1);
            a0 = a1;
            a1 = q[1];
            int[] b2 = b(b0, v(q[0], b1, g.c));
            b0 = b1;
            b1 = b2;
        }
        return new PolynomialGF2mSmallM[]{new PolynomialGF2mSmallM(this.a, a1), new PolynomialGF2mSmallM(this.a, b1)};
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof PolynomialGF2mSmallM)) {
            return false;
        }
        PolynomialGF2mSmallM p = (PolynomialGF2mSmallM) other;
        if (!this.a.equals(p.a) || this.b != p.b || !p(this.c, p.c)) {
            return false;
        }
        return true;
    }

    private static boolean p(int[] a2, int[] b2) {
        int da = e(a2);
        if (da != e(b2)) {
            return false;
        }
        for (int i = 0; i <= da; i++) {
            if (a2[i] != b2[i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int hash = this.a.hashCode();
        int j = 0;
        while (true) {
            int[] iArr = this.c;
            if (j >= iArr.length) {
                return hash;
            }
            hash = (hash * 31) + iArr[j];
            j++;
        }
    }

    public String toString() {
        String str = " Polynomial over " + this.a.toString() + ": \n";
        for (int i = 0; i < this.c.length; i++) {
            str = str + this.a.b(this.c[i]) + "Y^" + i + e.ANY_NON_NULL_MARKER;
        }
        return str + Constants.PACKNAME_END;
    }

    private void f() {
        this.b = this.c.length - 1;
        while (true) {
            int i = this.b;
            if (i >= 0 && this.c[i] == 0) {
                this.b = i - 1;
            } else {
                return;
            }
        }
    }

    private static int e(int[] a2) {
        int degree = a2.length - 1;
        while (degree >= 0 && a2[degree] == 0) {
            degree--;
        }
        return degree;
    }

    private static int[] F(int[] a2) {
        int d = e(a2);
        if (d == -1) {
            return new int[1];
        }
        if (a2.length == d + 1) {
            return IntUtils.a(a2);
        }
        int[] result = new int[(d + 1)];
        System.arraycopy(a2, 0, result, 0, d + 1);
        return result;
    }
}
