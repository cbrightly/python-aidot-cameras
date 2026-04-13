package org.apache.commons.math3.complex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.util.d;
import org.apache.commons.math3.util.c;
import org.apache.commons.math3.util.f;
import org.apache.commons.math3.util.g;

/* compiled from: Complex */
public class a implements Serializable {
    public static final a I = new a(0.0d, 1.0d);
    public static final a INF = new a(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final a NaN = new a(Double.NaN, Double.NaN);
    public static final a ONE = new a(1.0d, 0.0d);
    public static final a ZERO = new a(0.0d, 0.0d);
    private static final long serialVersionUID = -6195664516687396620L;
    private final transient boolean c;
    private final transient boolean d;
    private final double imaginary;
    private final double real;

    public a(double real2) {
        this(real2, 0.0d);
    }

    public a(double real2, double imaginary2) {
        this.real = real2;
        this.imaginary = imaginary2;
        boolean z = false;
        boolean z2 = Double.isNaN(real2) || Double.isNaN(imaginary2);
        this.c = z2;
        if (!z2 && (Double.isInfinite(real2) || Double.isInfinite(imaginary2))) {
            z = true;
        }
        this.d = z;
    }

    public double abs() {
        if (this.c) {
            return Double.NaN;
        }
        if (isInfinite()) {
            return Double.POSITIVE_INFINITY;
        }
        if (c.a(this.real) < c.a(this.imaginary)) {
            double d2 = this.imaginary;
            if (d2 == 0.0d) {
                return c.a(this.real);
            }
            double q = this.real / d2;
            return c.a(d2) * c.z((q * q) + 1.0d);
        }
        double d3 = this.real;
        if (d3 == 0.0d) {
            return c.a(this.imaginary);
        }
        double q2 = this.imaginary / d3;
        return c.a(d3) * c.z((q2 * q2) + 1.0d);
    }

    public a add(a addend) {
        f.a(addend);
        if (this.c || addend.c) {
            return NaN;
        }
        return createComplex(this.real + addend.getReal(), this.imaginary + addend.getImaginary());
    }

    public a add(double addend) {
        if (this.c || Double.isNaN(addend)) {
            return NaN;
        }
        return createComplex(this.real + addend, this.imaginary);
    }

    public a conjugate() {
        if (this.c) {
            return NaN;
        }
        return createComplex(this.real, -this.imaginary);
    }

    public a divide(a divisor) {
        f.a(divisor);
        if (this.c || divisor.c) {
            return NaN;
        }
        double c2 = divisor.getReal();
        double d2 = divisor.getImaginary();
        if (c2 == 0.0d && d2 == 0.0d) {
            return NaN;
        }
        if (divisor.isInfinite() && !isInfinite()) {
            return ZERO;
        }
        if (c.a(c2) < c.a(d2)) {
            double q = c2 / d2;
            double denominator = (c2 * q) + d2;
            double d3 = this.real;
            double d4 = this.imaginary;
            return createComplex(((d3 * q) + d4) / denominator, ((d4 * q) - d3) / denominator);
        }
        double q2 = d2 / c2;
        double denominator2 = (d2 * q2) + c2;
        double d5 = this.imaginary;
        double d6 = this.real;
        return createComplex(((d5 * q2) + d6) / denominator2, (d5 - (d6 * q2)) / denominator2);
    }

    public a divide(double divisor) {
        if (this.c || Double.isNaN(divisor)) {
            return NaN;
        }
        if (divisor == 0.0d) {
            return NaN;
        }
        if (Double.isInfinite(divisor)) {
            return !isInfinite() ? ZERO : NaN;
        }
        return createComplex(this.real / divisor, this.imaginary / divisor);
    }

    public a reciprocal() {
        if (this.c) {
            return NaN;
        }
        double d2 = this.real;
        if (d2 == 0.0d && this.imaginary == 0.0d) {
            return INF;
        }
        if (this.d) {
            return ZERO;
        }
        if (c.a(d2) < c.a(this.imaginary)) {
            double d3 = this.real;
            double d4 = this.imaginary;
            double q = d3 / d4;
            double scale = 1.0d / ((d3 * q) + d4);
            return createComplex(scale * q, -scale);
        }
        double d5 = this.imaginary;
        double d6 = this.real;
        double q2 = d5 / d6;
        double scale2 = 1.0d / ((d5 * q2) + d6);
        return createComplex(scale2, (-scale2) * q2);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof a)) {
            return false;
        }
        a c2 = (a) other;
        if (c2.c) {
            return this.c;
        }
        if (!f.b(this.real, c2.real) || !f.b(this.imaginary, c2.imaginary)) {
            return false;
        }
        return true;
    }

    public static boolean equals(a x, a y, int maxUlps) {
        return g.b(x.real, y.real, maxUlps) && g.b(x.imaginary, y.imaginary, maxUlps);
    }

    public static boolean equals(a x, a y) {
        return equals(x, y, 1);
    }

    public static boolean equals(a x, a y, double eps) {
        return g.a(x.real, y.real, eps) && g.a(x.imaginary, y.imaginary, eps);
    }

    public static boolean equalsWithRelativeTolerance(a x, a y, double eps) {
        return g.c(x.real, y.real, eps) && g.c(x.imaginary, y.imaginary, eps);
    }

    public int hashCode() {
        if (this.c) {
            return 7;
        }
        return ((f.c(this.imaginary) * 17) + f.c(this.real)) * 37;
    }

    public double getImaginary() {
        return this.imaginary;
    }

    public double getReal() {
        return this.real;
    }

    public boolean isNaN() {
        return this.c;
    }

    public boolean isInfinite() {
        return this.d;
    }

    public a multiply(a factor) {
        f.a(factor);
        if (this.c || factor.c) {
            return NaN;
        }
        if (Double.isInfinite(this.real) || Double.isInfinite(this.imaginary) || Double.isInfinite(factor.real) || Double.isInfinite(factor.imaginary)) {
            return INF;
        }
        double d2 = this.real;
        double d3 = factor.real;
        double d4 = this.imaginary;
        double d5 = factor.imaginary;
        return createComplex((d2 * d3) - (d4 * d5), (d2 * d5) + (d4 * d3));
    }

    public a multiply(int factor) {
        if (this.c) {
            return NaN;
        }
        if (Double.isInfinite(this.real) || Double.isInfinite(this.imaginary)) {
            return INF;
        }
        return createComplex(this.real * ((double) factor), this.imaginary * ((double) factor));
    }

    public a multiply(double factor) {
        if (this.c || Double.isNaN(factor)) {
            return NaN;
        }
        if (Double.isInfinite(this.real) || Double.isInfinite(this.imaginary) || Double.isInfinite(factor)) {
            return INF;
        }
        return createComplex(this.real * factor, this.imaginary * factor);
    }

    public a negate() {
        if (this.c) {
            return NaN;
        }
        return createComplex(-this.real, -this.imaginary);
    }

    public a subtract(a subtrahend) {
        f.a(subtrahend);
        if (this.c || subtrahend.c) {
            return NaN;
        }
        return createComplex(this.real - subtrahend.getReal(), this.imaginary - subtrahend.getImaginary());
    }

    public a subtract(double subtrahend) {
        if (this.c || Double.isNaN(subtrahend)) {
            return NaN;
        }
        return createComplex(this.real - subtrahend, this.imaginary);
    }

    public a acos() {
        if (this.c) {
            return NaN;
        }
        a sqrt1z = sqrt1z();
        a aVar = I;
        return add(sqrt1z.multiply(aVar)).log().multiply(aVar.negate());
    }

    public a asin() {
        if (this.c) {
            return NaN;
        }
        a sqrt1z = sqrt1z();
        a aVar = I;
        return sqrt1z.add(multiply(aVar)).log().multiply(aVar.negate());
    }

    public a atan() {
        if (this.c) {
            return NaN;
        }
        a aVar = I;
        return add(aVar).divide(aVar.subtract(this)).log().multiply(aVar.divide(createComplex(2.0d, 0.0d)));
    }

    public a cos() {
        if (this.c) {
            return NaN;
        }
        return createComplex(c.f(this.real) * c.h(this.imaginary), (-c.w(this.real)) * c.y(this.imaginary));
    }

    public a cosh() {
        if (this.c) {
            return NaN;
        }
        return createComplex(c.h(this.real) * c.f(this.imaginary), c.y(this.real) * c.w(this.imaginary));
    }

    public a exp() {
        if (this.c) {
            return NaN;
        }
        double expReal = c.j(this.real);
        return createComplex(c.f(this.imaginary) * expReal, c.w(this.imaginary) * expReal);
    }

    public a log() {
        if (this.c) {
            return NaN;
        }
        return createComplex(c.m(abs()), c.d(this.imaginary, this.real));
    }

    public a pow(a x) {
        f.a(x);
        return log().multiply(x).exp();
    }

    public a pow(double x) {
        return log().multiply(x).exp();
    }

    public a sin() {
        if (this.c) {
            return NaN;
        }
        return createComplex(c.w(this.real) * c.h(this.imaginary), c.f(this.real) * c.y(this.imaginary));
    }

    public a sinh() {
        if (this.c) {
            return NaN;
        }
        return createComplex(c.y(this.real) * c.f(this.imaginary), c.h(this.real) * c.w(this.imaginary));
    }

    public a sqrt() {
        if (this.c) {
            return NaN;
        }
        double d2 = this.real;
        if (d2 == 0.0d && this.imaginary == 0.0d) {
            return createComplex(0.0d, 0.0d);
        }
        double t = c.z((c.a(d2) + abs()) / 2.0d);
        if (this.real >= 0.0d) {
            return createComplex(t, this.imaginary / (2.0d * t));
        }
        return createComplex(c.a(this.imaginary) / (2.0d * t), c.e(1.0d, this.imaginary) * t);
    }

    public a sqrt1z() {
        return createComplex(1.0d, 0.0d).subtract(multiply(this)).sqrt();
    }

    public a tan() {
        if (this.c || Double.isInfinite(this.real)) {
            return NaN;
        }
        double d2 = this.imaginary;
        if (d2 > 20.0d) {
            return createComplex(0.0d, 1.0d);
        }
        if (d2 < -20.0d) {
            return createComplex(0.0d, -1.0d);
        }
        double real2 = this.real * 2.0d;
        double imaginary2 = d2 * 2.0d;
        double d3 = c.f(real2) + c.h(imaginary2);
        return createComplex(c.w(real2) / d3, c.y(imaginary2) / d3);
    }

    public a tanh() {
        if (this.c || Double.isInfinite(this.imaginary)) {
            return NaN;
        }
        double d2 = this.real;
        if (d2 > 20.0d) {
            return createComplex(1.0d, 0.0d);
        }
        if (d2 < -20.0d) {
            return createComplex(-1.0d, 0.0d);
        }
        double real2 = d2 * 2.0d;
        double imaginary2 = this.imaginary * 2.0d;
        double d3 = c.h(real2) + c.f(imaginary2);
        return createComplex(c.y(real2) / d3, c.w(imaginary2) / d3);
    }

    public double getArgument() {
        return c.d(getImaginary(), getReal());
    }

    public List<a> nthRoot(int n) {
        int i = n;
        if (i > 0) {
            List<Complex> result = new ArrayList<>();
            if (this.c) {
                result.add(NaN);
                return result;
            } else if (isInfinite()) {
                result.add(INF);
                return result;
            } else {
                double nthRootOfAbs = c.t(abs(), 1.0d / ((double) i));
                double slice = 6.283185307179586d / ((double) i);
                double innerPart = getArgument() / ((double) i);
                int k = 0;
                while (k < i) {
                    result.add(createComplex(c.f(innerPart) * nthRootOfAbs, c.w(innerPart) * nthRootOfAbs));
                    innerPart += slice;
                    k++;
                    i = n;
                }
                return result;
            }
        } else {
            throw new NotPositiveException(d.CANNOT_COMPUTE_NTH_ROOT_FOR_NEGATIVE_N, Integer.valueOf(n));
        }
    }

    /* access modifiers changed from: protected */
    public a createComplex(double realPart, double imaginaryPart) {
        return new a(realPart, imaginaryPart);
    }

    public static a valueOf(double realPart, double imaginaryPart) {
        if (Double.isNaN(realPart) || Double.isNaN(imaginaryPart)) {
            return NaN;
        }
        return new a(realPart, imaginaryPart);
    }

    public static a valueOf(double realPart) {
        if (Double.isNaN(realPart)) {
            return NaN;
        }
        return new a(realPart);
    }

    /* access modifiers changed from: protected */
    public final Object readResolve() {
        return createComplex(this.real, this.imaginary);
    }

    public b getField() {
        return b.getInstance();
    }

    public String toString() {
        return "(" + this.real + ", " + this.imaginary + ")";
    }
}
