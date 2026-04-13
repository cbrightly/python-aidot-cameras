package org.spongycastle.pqc.math.ntru.polynomial;

import java.security.SecureRandom;

public class ProductFormPolynomial implements Polynomial {
    private SparseTernaryPolynomial a;
    private SparseTernaryPolynomial b;
    private SparseTernaryPolynomial c;

    public ProductFormPolynomial(SparseTernaryPolynomial f1, SparseTernaryPolynomial f2, SparseTernaryPolynomial f3) {
        this.a = f1;
        this.b = f2;
        this.c = f3;
    }

    public static ProductFormPolynomial g(int N, int df1, int df2, int df3Ones, int df3NegOnes, SecureRandom random) {
        return new ProductFormPolynomial(SparseTernaryPolynomial.g(N, df1, df1, random), SparseTernaryPolynomial.g(N, df2, df2, random), SparseTernaryPolynomial.g(N, df3Ones, df3NegOnes, random));
    }

    public IntegerPolynomial e(IntegerPolynomial b2) {
        IntegerPolynomial c2 = this.b.e(this.a.e(b2));
        c2.h(this.c.e(b2));
        return c2;
    }

    public BigIntPolynomial b(BigIntPolynomial b2) {
        BigIntPolynomial c2 = this.b.b(this.a.b(b2));
        c2.a(this.c.b(b2));
        return c2;
    }

    public IntegerPolynomial a() {
        IntegerPolynomial i = this.a.e(this.b.a());
        i.h(this.c.a());
        return i;
    }

    public IntegerPolynomial c(IntegerPolynomial poly2, int modulus) {
        IntegerPolynomial c2 = e(poly2);
        c2.w(modulus);
        return c2;
    }

    public int hashCode() {
        int i = 1 * 31;
        SparseTernaryPolynomial sparseTernaryPolynomial = this.a;
        int i2 = 0;
        int result = (i + (sparseTernaryPolynomial == null ? 0 : sparseTernaryPolynomial.hashCode())) * 31;
        SparseTernaryPolynomial sparseTernaryPolynomial2 = this.b;
        int result2 = (result + (sparseTernaryPolynomial2 == null ? 0 : sparseTernaryPolynomial2.hashCode())) * 31;
        SparseTernaryPolynomial sparseTernaryPolynomial3 = this.c;
        if (sparseTernaryPolynomial3 != null) {
            i2 = sparseTernaryPolynomial3.hashCode();
        }
        return result2 + i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ProductFormPolynomial other = (ProductFormPolynomial) obj;
        SparseTernaryPolynomial sparseTernaryPolynomial = this.a;
        if (sparseTernaryPolynomial == null) {
            if (other.a != null) {
                return false;
            }
        } else if (!sparseTernaryPolynomial.equals(other.a)) {
            return false;
        }
        SparseTernaryPolynomial sparseTernaryPolynomial2 = this.b;
        if (sparseTernaryPolynomial2 == null) {
            if (other.b != null) {
                return false;
            }
        } else if (!sparseTernaryPolynomial2.equals(other.b)) {
            return false;
        }
        SparseTernaryPolynomial sparseTernaryPolynomial3 = this.c;
        if (sparseTernaryPolynomial3 == null) {
            if (other.c != null) {
                return false;
            }
        } else if (!sparseTernaryPolynomial3.equals(other.c)) {
            return false;
        }
        return true;
    }
}
