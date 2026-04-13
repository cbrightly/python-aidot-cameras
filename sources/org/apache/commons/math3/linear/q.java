package org.apache.commons.math3.linear;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.MathUnsupportedOperationException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.OutOfRangeException;

/* compiled from: RealVector */
public abstract class q {
    public abstract q append(double d2);

    public abstract q append(q qVar);

    public abstract q copy();

    public abstract q ebeDivide(q qVar);

    public abstract q ebeMultiply(q qVar);

    public abstract int getDimension();

    public abstract double getEntry(int i);

    public abstract q getSubVector(int i, int i2);

    public abstract boolean isInfinite();

    public abstract boolean isNaN();

    public abstract void setEntry(int i, double d2);

    public abstract void setSubVector(int i, q qVar);

    public void addToEntry(int index, double increment) {
        setEntry(index, getEntry(index) + increment);
    }

    /* access modifiers changed from: protected */
    public void checkVectorDimensions(q v) {
        checkVectorDimensions(v.getDimension());
    }

    /* access modifiers changed from: protected */
    public void checkVectorDimensions(int n) {
        int d2 = getDimension();
        if (d2 != n) {
            throw new DimensionMismatchException(d2, n);
        }
    }

    /* access modifiers changed from: protected */
    public void checkIndex(int index) {
        if (index < 0 || index >= getDimension()) {
            throw new OutOfRangeException(org.apache.commons.math3.exception.util.d.INDEX, Integer.valueOf(index), 0, Integer.valueOf(getDimension() - 1));
        }
    }

    /* access modifiers changed from: protected */
    public void checkIndices(int start, int end) {
        int dim = getDimension();
        if (start < 0 || start >= dim) {
            throw new OutOfRangeException(org.apache.commons.math3.exception.util.d.INDEX, Integer.valueOf(start), 0, Integer.valueOf(dim - 1));
        } else if (end < 0 || end >= dim) {
            throw new OutOfRangeException(org.apache.commons.math3.exception.util.d.INDEX, Integer.valueOf(end), 0, Integer.valueOf(dim - 1));
        } else if (end < start) {
            throw new NumberIsTooSmallException(org.apache.commons.math3.exception.util.d.INITIAL_ROW_AFTER_FINAL_ROW, Integer.valueOf(end), Integer.valueOf(start), false);
        }
    }

    public q add(q v) {
        checkVectorDimensions(v);
        q result = v.copy();
        Iterator<c> it = iterator();
        while (it.hasNext()) {
            c e = it.next();
            int index = e.a();
            result.setEntry(index, e.b() + result.getEntry(index));
        }
        return result;
    }

    public q subtract(q v) {
        checkVectorDimensions(v);
        q result = v.mapMultiply(-1.0d);
        Iterator<c> it = iterator();
        while (it.hasNext()) {
            c e = it.next();
            int index = e.a();
            result.setEntry(index, e.b() + result.getEntry(index));
        }
        return result;
    }

    public q mapAdd(double d2) {
        return copy().mapAddToSelf(d2);
    }

    public q mapAddToSelf(double d2) {
        if (d2 != 0.0d) {
            return mapToSelf(org.apache.commons.math3.analysis.b.a(new org.apache.commons.math3.analysis.function.a(), d2));
        }
        return this;
    }

    public double dotProduct(q v) {
        checkVectorDimensions(v);
        double d2 = 0.0d;
        int n = getDimension();
        for (int i = 0; i < n; i++) {
            d2 += getEntry(i) * v.getEntry(i);
        }
        return d2;
    }

    public double cosine(q v) {
        double norm = getNorm();
        double vNorm = v.getNorm();
        if (norm != 0.0d && vNorm != 0.0d) {
            return dotProduct(v) / (norm * vNorm);
        }
        throw new MathArithmeticException(org.apache.commons.math3.exception.util.d.ZERO_NORM, new Object[0]);
    }

    public double getDistance(q v) {
        checkVectorDimensions(v);
        double d2 = 0.0d;
        Iterator<c> it = iterator();
        while (it.hasNext()) {
            c e = it.next();
            double diff = e.b() - v.getEntry(e.a());
            d2 += diff * diff;
        }
        return org.apache.commons.math3.util.c.z(d2);
    }

    public double getNorm() {
        double sum = 0.0d;
        Iterator<c> it = iterator();
        while (it.hasNext()) {
            double value = it.next().b();
            sum += value * value;
        }
        return org.apache.commons.math3.util.c.z(sum);
    }

    public double getL1Norm() {
        double norm = 0.0d;
        Iterator<c> it = iterator();
        while (it.hasNext()) {
            norm += org.apache.commons.math3.util.c.a(it.next().b());
        }
        return norm;
    }

    public double getLInfNorm() {
        double norm = 0.0d;
        Iterator<c> it = iterator();
        while (it.hasNext()) {
            norm = org.apache.commons.math3.util.c.o(norm, org.apache.commons.math3.util.c.a(it.next().b()));
        }
        return norm;
    }

    public double getL1Distance(q v) {
        checkVectorDimensions(v);
        double d2 = 0.0d;
        Iterator<c> it = iterator();
        while (it.hasNext()) {
            c e = it.next();
            d2 += org.apache.commons.math3.util.c.a(e.b() - v.getEntry(e.a()));
        }
        return d2;
    }

    public double getLInfDistance(q v) {
        checkVectorDimensions(v);
        double d2 = 0.0d;
        Iterator<c> it = iterator();
        while (it.hasNext()) {
            c e = it.next();
            d2 = org.apache.commons.math3.util.c.o(org.apache.commons.math3.util.c.a(e.b() - v.getEntry(e.a())), d2);
        }
        return d2;
    }

    public int getMinIndex() {
        int minIndex = -1;
        double minValue = Double.POSITIVE_INFINITY;
        Iterator<c> it = iterator();
        while (it.hasNext()) {
            c entry = it.next();
            if (entry.b() <= minValue) {
                minIndex = entry.a();
                minValue = entry.b();
            }
        }
        return minIndex;
    }

    public double getMinValue() {
        int minIndex = getMinIndex();
        if (minIndex < 0) {
            return Double.NaN;
        }
        return getEntry(minIndex);
    }

    public int getMaxIndex() {
        int maxIndex = -1;
        double maxValue = Double.NEGATIVE_INFINITY;
        Iterator<c> it = iterator();
        while (it.hasNext()) {
            c entry = it.next();
            if (entry.b() >= maxValue) {
                maxIndex = entry.a();
                maxValue = entry.b();
            }
        }
        return maxIndex;
    }

    public double getMaxValue() {
        int maxIndex = getMaxIndex();
        if (maxIndex < 0) {
            return Double.NaN;
        }
        return getEntry(maxIndex);
    }

    public q mapMultiply(double d2) {
        return copy().mapMultiplyToSelf(d2);
    }

    public q mapMultiplyToSelf(double d2) {
        return mapToSelf(org.apache.commons.math3.analysis.b.a(new org.apache.commons.math3.analysis.function.c(), d2));
    }

    public q mapSubtract(double d2) {
        return copy().mapSubtractToSelf(d2);
    }

    public q mapSubtractToSelf(double d2) {
        return mapAddToSelf(-d2);
    }

    public q mapDivide(double d2) {
        return copy().mapDivideToSelf(d2);
    }

    public q mapDivideToSelf(double d2) {
        return mapToSelf(org.apache.commons.math3.analysis.b.a(new org.apache.commons.math3.analysis.function.b(), d2));
    }

    public m outerProduct(q v) {
        int m = getDimension();
        int n = v.getDimension();
        m product = new c(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                product.setEntry(i, j, getEntry(i) * v.getEntry(j));
            }
        }
        return product;
    }

    public q projection(q v) {
        if (v.dotProduct(v) != 0.0d) {
            return v.mapMultiply(dotProduct(v) / v.dotProduct(v));
        }
        throw new MathArithmeticException(org.apache.commons.math3.exception.util.d.ZERO_NORM, new Object[0]);
    }

    public void set(double value) {
        Iterator<c> it = iterator();
        while (it.hasNext()) {
            it.next().d(value);
        }
    }

    public double[] toArray() {
        int dim = getDimension();
        double[] values = new double[dim];
        for (int i = 0; i < dim; i++) {
            values[i] = getEntry(i);
        }
        return values;
    }

    public q unitVector() {
        double norm = getNorm();
        if (norm != 0.0d) {
            return mapDivide(norm);
        }
        throw new MathArithmeticException(org.apache.commons.math3.exception.util.d.ZERO_NORM, new Object[0]);
    }

    public void unitize() {
        if (getNorm() != 0.0d) {
            mapDivideToSelf(getNorm());
            return;
        }
        throw new MathArithmeticException(org.apache.commons.math3.exception.util.d.ZERO_NORM, new Object[0]);
    }

    public Iterator<c> sparseIterator() {
        return new d();
    }

    /* compiled from: RealVector */
    public class a implements Iterator<c> {
        private int c = 0;
        private c d;
        final /* synthetic */ int f;

        a(int i) {
            this.f = i;
            this.d = new c();
        }

        public boolean hasNext() {
            return this.c < this.f;
        }

        /* renamed from: b */
        public c next() {
            int i = this.c;
            if (i < this.f) {
                c cVar = this.d;
                this.c = i + 1;
                cVar.c(i);
                return this.d;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new MathUnsupportedOperationException();
        }
    }

    public Iterator<c> iterator() {
        return new a(getDimension());
    }

    public q map(org.apache.commons.math3.analysis.c function) {
        return copy().mapToSelf(function);
    }

    public q mapToSelf(org.apache.commons.math3.analysis.c function) {
        Iterator<c> it = iterator();
        while (it.hasNext()) {
            c e = it.next();
            e.d(function.value(e.b()));
        }
        return this;
    }

    public q combine(double a2, double b2, q y) {
        return copy().combineToSelf(a2, b2, y);
    }

    public q combineToSelf(double a2, double b2, q y) {
        checkVectorDimensions(y);
        for (int i = 0; i < getDimension(); i++) {
            setEntry(i, (a2 * getEntry(i)) + (b2 * y.getEntry(i)));
        }
        return this;
    }

    public double walkInDefaultOrder(t visitor) {
        int dim = getDimension();
        visitor.b(dim, 0, dim - 1);
        for (int i = 0; i < dim; i++) {
            visitor.a(i, getEntry(i));
        }
        return visitor.end();
    }

    public double walkInDefaultOrder(t visitor, int start, int end) {
        checkIndices(start, end);
        visitor.b(getDimension(), start, end);
        for (int i = start; i <= end; i++) {
            visitor.a(i, getEntry(i));
        }
        return visitor.end();
    }

    public double walkInOptimizedOrder(t visitor) {
        return walkInDefaultOrder(visitor);
    }

    public double walkInOptimizedOrder(t visitor, int start, int end) {
        return walkInDefaultOrder(visitor, start, end);
    }

    public double walkInDefaultOrder(r visitor) {
        int dim = getDimension();
        visitor.b(dim, 0, dim - 1);
        for (int i = 0; i < dim; i++) {
            setEntry(i, visitor.a(i, getEntry(i)));
        }
        return visitor.end();
    }

    public double walkInDefaultOrder(r visitor, int start, int end) {
        checkIndices(start, end);
        visitor.b(getDimension(), start, end);
        for (int i = start; i <= end; i++) {
            setEntry(i, visitor.a(i, getEntry(i)));
        }
        return visitor.end();
    }

    public double walkInOptimizedOrder(r visitor) {
        return walkInDefaultOrder(visitor);
    }

    public double walkInOptimizedOrder(r visitor, int start, int end) {
        return walkInDefaultOrder(visitor, start, end);
    }

    /* compiled from: RealVector */
    public class c {
        private int a;

        public c() {
            c(0);
        }

        public double b() {
            return q.this.getEntry(a());
        }

        public void d(double value) {
            q.this.setEntry(a(), value);
        }

        public int a() {
            return this.a;
        }

        public void c(int index) {
            this.a = index;
        }
    }

    public boolean equals(Object other) {
        throw new MathUnsupportedOperationException();
    }

    public int hashCode() {
        throw new MathUnsupportedOperationException();
    }

    /* compiled from: RealVector */
    public class d implements Iterator<c> {
        private final int c;
        private c d;
        private c f;

        protected d() {
            this.c = q.this.getDimension();
            this.d = new c();
            c cVar = new c();
            this.f = cVar;
            if (cVar.b() == 0.0d) {
                b(this.f);
            }
        }

        /* access modifiers changed from: protected */
        public void b(c e) {
            if (e != null) {
                do {
                    e.c(e.a() + 1);
                    if (e.a() >= this.c || e.b() != 0.0d) {
                    }
                    e.c(e.a() + 1);
                    break;
                } while (e.b() != 0.0d);
                if (e.a() >= this.c) {
                    e.c(-1);
                }
            }
        }

        public boolean hasNext() {
            return this.f.a() >= 0;
        }

        /* renamed from: c */
        public c next() {
            int index = this.f.a();
            if (index >= 0) {
                this.d.c(index);
                b(this.f);
                return this.d;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new MathUnsupportedOperationException();
        }
    }

    /* compiled from: RealVector */
    public static final class b extends q {
        final /* synthetic */ q a;

        b(q qVar) {
            this.a = qVar;
        }

        public q mapToSelf(org.apache.commons.math3.analysis.c function) {
            throw new MathUnsupportedOperationException();
        }

        public q map(org.apache.commons.math3.analysis.c function) {
            return this.a.map(function);
        }

        /* compiled from: RealVector */
        public class a implements Iterator<c> {
            private final c c;
            final /* synthetic */ Iterator d;

            a(Iterator it) {
                this.d = it;
                this.c = new c();
            }

            public boolean hasNext() {
                return this.d.hasNext();
            }

            /* renamed from: b */
            public c next() {
                this.c.c(((c) this.d.next()).a());
                return this.c;
            }

            public void remove() {
                throw new MathUnsupportedOperationException();
            }
        }

        public Iterator<c> iterator() {
            return new a(this.a.iterator());
        }

        public Iterator<c> sparseIterator() {
            return new C0485b(this.a.sparseIterator());
        }

        /* renamed from: org.apache.commons.math3.linear.q$b$b  reason: collision with other inner class name */
        /* compiled from: RealVector */
        public class C0485b implements Iterator<c> {
            private final c c;
            final /* synthetic */ Iterator d;

            C0485b(Iterator it) {
                this.d = it;
                this.c = new c();
            }

            public boolean hasNext() {
                return this.d.hasNext();
            }

            /* renamed from: b */
            public c next() {
                this.c.c(((c) this.d.next()).a());
                return this.c;
            }

            public void remove() {
                throw new MathUnsupportedOperationException();
            }
        }

        public q copy() {
            return this.a.copy();
        }

        public q add(q w) {
            return this.a.add(w);
        }

        public q subtract(q w) {
            return this.a.subtract(w);
        }

        public q mapAdd(double d) {
            return this.a.mapAdd(d);
        }

        public q mapAddToSelf(double d) {
            throw new MathUnsupportedOperationException();
        }

        public q mapSubtract(double d) {
            return this.a.mapSubtract(d);
        }

        public q mapSubtractToSelf(double d) {
            throw new MathUnsupportedOperationException();
        }

        public q mapMultiply(double d) {
            return this.a.mapMultiply(d);
        }

        public q mapMultiplyToSelf(double d) {
            throw new MathUnsupportedOperationException();
        }

        public q mapDivide(double d) {
            return this.a.mapDivide(d);
        }

        public q mapDivideToSelf(double d) {
            throw new MathUnsupportedOperationException();
        }

        public q ebeMultiply(q w) {
            return this.a.ebeMultiply(w);
        }

        public q ebeDivide(q w) {
            return this.a.ebeDivide(w);
        }

        public double dotProduct(q w) {
            return this.a.dotProduct(w);
        }

        public double cosine(q w) {
            return this.a.cosine(w);
        }

        public double getNorm() {
            return this.a.getNorm();
        }

        public double getL1Norm() {
            return this.a.getL1Norm();
        }

        public double getLInfNorm() {
            return this.a.getLInfNorm();
        }

        public double getDistance(q w) {
            return this.a.getDistance(w);
        }

        public double getL1Distance(q w) {
            return this.a.getL1Distance(w);
        }

        public double getLInfDistance(q w) {
            return this.a.getLInfDistance(w);
        }

        public q unitVector() {
            return this.a.unitVector();
        }

        public void unitize() {
            throw new MathUnsupportedOperationException();
        }

        public m outerProduct(q w) {
            return this.a.outerProduct(w);
        }

        public double getEntry(int index) {
            return this.a.getEntry(index);
        }

        public void setEntry(int index, double value) {
            throw new MathUnsupportedOperationException();
        }

        public void addToEntry(int index, double value) {
            throw new MathUnsupportedOperationException();
        }

        public int getDimension() {
            return this.a.getDimension();
        }

        public q append(q w) {
            return this.a.append(w);
        }

        public q append(double d) {
            return this.a.append(d);
        }

        public q getSubVector(int index, int n) {
            return this.a.getSubVector(index, n);
        }

        public void setSubVector(int index, q w) {
            throw new MathUnsupportedOperationException();
        }

        public void set(double value) {
            throw new MathUnsupportedOperationException();
        }

        public double[] toArray() {
            return this.a.toArray();
        }

        public boolean isNaN() {
            return this.a.isNaN();
        }

        public boolean isInfinite() {
            return this.a.isInfinite();
        }

        public q combine(double a2, double b, q y) {
            return this.a.combine(a2, b, y);
        }

        public q combineToSelf(double a2, double b, q y) {
            throw new MathUnsupportedOperationException();
        }

        /* compiled from: RealVector */
        public class c extends c {
            c() {
                super();
            }

            public double b() {
                return b.this.a.getEntry(a());
            }

            public void d(double value) {
                throw new MathUnsupportedOperationException();
            }
        }
    }

    public static q unmodifiableRealVector(q v) {
        return new b(v);
    }
}
