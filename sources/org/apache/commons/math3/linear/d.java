package org.apache.commons.math3.linear;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.commons.math3.analysis.c;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.linear.q;
import org.apache.commons.math3.util.f;

/* compiled from: ArrayRealVector */
public class d extends q implements Serializable {
    private static final s c = s.c();
    private static final long serialVersionUID = -1097961340710804027L;
    private double[] data;

    public d() {
        this.data = new double[0];
    }

    public d(int size) {
        this.data = new double[size];
    }

    public d(int size, double preset) {
        double[] dArr = new double[size];
        this.data = dArr;
        Arrays.fill(dArr, preset);
    }

    public d(double[] d) {
        this.data = (double[]) d.clone();
    }

    public d(double[] d, boolean copyArray) {
        if (d != null) {
            this.data = copyArray ? (double[]) d.clone() : d;
            return;
        }
        throw new NullArgumentException();
    }

    public d(double[] d, int pos, int size) {
        if (d == null) {
            throw new NullArgumentException();
        } else if (d.length >= pos + size) {
            double[] dArr = new double[size];
            this.data = dArr;
            System.arraycopy(d, pos, dArr, 0, size);
        } else {
            throw new NumberIsTooLargeException(Integer.valueOf(pos + size), Integer.valueOf(d.length), true);
        }
    }

    public d(Double[] d) {
        this.data = new double[d.length];
        for (int i = 0; i < d.length; i++) {
            this.data[i] = d[i].doubleValue();
        }
    }

    public d(Double[] d, int pos, int size) {
        if (d == null) {
            throw new NullArgumentException();
        } else if (d.length >= pos + size) {
            this.data = new double[size];
            for (int i = pos; i < pos + size; i++) {
                this.data[i - pos] = d[i].doubleValue();
            }
        } else {
            throw new NumberIsTooLargeException(Integer.valueOf(pos + size), Integer.valueOf(d.length), true);
        }
    }

    public d(q v) {
        if (v != null) {
            this.data = new double[v.getDimension()];
            int i = 0;
            while (true) {
                double[] dArr = this.data;
                if (i < dArr.length) {
                    dArr[i] = v.getEntry(i);
                    i++;
                } else {
                    return;
                }
            }
        } else {
            throw new NullArgumentException();
        }
    }

    public d(d v) {
        this(v, true);
    }

    public d(d v, boolean deep) {
        double[] dArr = v.data;
        this.data = deep ? (double[]) dArr.clone() : dArr;
    }

    public d(d v1, d v2) {
        double[] dArr = new double[(v1.data.length + v2.data.length)];
        this.data = dArr;
        double[] dArr2 = v1.data;
        System.arraycopy(dArr2, 0, dArr, 0, dArr2.length);
        double[] dArr3 = v2.data;
        System.arraycopy(dArr3, 0, this.data, v1.data.length, dArr3.length);
    }

    public d(d v1, q v2) {
        int l1 = v1.data.length;
        int l2 = v2.getDimension();
        double[] dArr = new double[(l1 + l2)];
        this.data = dArr;
        System.arraycopy(v1.data, 0, dArr, 0, l1);
        for (int i = 0; i < l2; i++) {
            this.data[l1 + i] = v2.getEntry(i);
        }
    }

    public d(q v1, d v2) {
        int l1 = v1.getDimension();
        int l2 = v2.data.length;
        this.data = new double[(l1 + l2)];
        for (int i = 0; i < l1; i++) {
            this.data[i] = v1.getEntry(i);
        }
        System.arraycopy(v2.data, 0, this.data, l1, l2);
    }

    public d(d v1, double[] v2) {
        int l1 = v1.getDimension();
        int l2 = v2.length;
        double[] dArr = new double[(l1 + l2)];
        this.data = dArr;
        System.arraycopy(v1.data, 0, dArr, 0, l1);
        System.arraycopy(v2, 0, this.data, l1, l2);
    }

    public d(double[] v1, d v2) {
        int l1 = v1.length;
        int l2 = v2.getDimension();
        double[] dArr = new double[(l1 + l2)];
        this.data = dArr;
        System.arraycopy(v1, 0, dArr, 0, l1);
        System.arraycopy(v2.data, 0, this.data, l1, l2);
    }

    public d(double[] v1, double[] v2) {
        int l1 = v1.length;
        int l2 = v2.length;
        double[] dArr = new double[(l1 + l2)];
        this.data = dArr;
        System.arraycopy(v1, 0, dArr, 0, l1);
        System.arraycopy(v2, 0, this.data, l1, l2);
    }

    public d copy() {
        return new d(this, true);
    }

    public d add(q v) {
        if (v instanceof d) {
            double[] vData = ((d) v).data;
            int dim = vData.length;
            checkVectorDimensions(dim);
            d result = new d(dim);
            double[] resultData = result.data;
            for (int i = 0; i < dim; i++) {
                resultData[i] = this.data[i] + vData[i];
            }
            return result;
        }
        checkVectorDimensions(v);
        double[] out = (double[]) this.data.clone();
        Iterator<q.c> it = v.iterator();
        while (it.hasNext()) {
            q.c e = it.next();
            int a = e.a();
            out[a] = out[a] + e.b();
        }
        return new d(out, false);
    }

    public d subtract(q v) {
        if (v instanceof d) {
            double[] vData = ((d) v).data;
            int dim = vData.length;
            checkVectorDimensions(dim);
            d result = new d(dim);
            double[] resultData = result.data;
            for (int i = 0; i < dim; i++) {
                resultData[i] = this.data[i] - vData[i];
            }
            return result;
        }
        checkVectorDimensions(v);
        double[] out = (double[]) this.data.clone();
        Iterator<q.c> it = v.iterator();
        while (it.hasNext()) {
            q.c e = it.next();
            int a = e.a();
            out[a] = out[a] - e.b();
        }
        return new d(out, false);
    }

    public d map(c function) {
        return copy().mapToSelf(function);
    }

    public d mapToSelf(c function) {
        int i = 0;
        while (true) {
            double[] dArr = this.data;
            if (i >= dArr.length) {
                return this;
            }
            dArr[i] = function.value(dArr[i]);
            i++;
        }
    }

    public q mapAddToSelf(double d) {
        int i = 0;
        while (true) {
            double[] dArr = this.data;
            if (i >= dArr.length) {
                return this;
            }
            dArr[i] = dArr[i] + d;
            i++;
        }
    }

    public q mapSubtractToSelf(double d) {
        int i = 0;
        while (true) {
            double[] dArr = this.data;
            if (i >= dArr.length) {
                return this;
            }
            dArr[i] = dArr[i] - d;
            i++;
        }
    }

    public q mapMultiplyToSelf(double d) {
        int i = 0;
        while (true) {
            double[] dArr = this.data;
            if (i >= dArr.length) {
                return this;
            }
            dArr[i] = dArr[i] * d;
            i++;
        }
    }

    public q mapDivideToSelf(double d) {
        int i = 0;
        while (true) {
            double[] dArr = this.data;
            if (i >= dArr.length) {
                return this;
            }
            dArr[i] = dArr[i] / d;
            i++;
        }
    }

    public d ebeMultiply(q v) {
        if (v instanceof d) {
            double[] vData = ((d) v).data;
            int dim = vData.length;
            checkVectorDimensions(dim);
            d result = new d(dim);
            double[] resultData = result.data;
            for (int i = 0; i < dim; i++) {
                resultData[i] = this.data[i] * vData[i];
            }
            return result;
        }
        checkVectorDimensions(v);
        double[] out = (double[]) this.data.clone();
        for (int i2 = 0; i2 < this.data.length; i2++) {
            out[i2] = out[i2] * v.getEntry(i2);
        }
        return new d(out, false);
    }

    public d ebeDivide(q v) {
        if (v instanceof d) {
            double[] vData = ((d) v).data;
            int dim = vData.length;
            checkVectorDimensions(dim);
            d result = new d(dim);
            double[] resultData = result.data;
            for (int i = 0; i < dim; i++) {
                resultData[i] = this.data[i] / vData[i];
            }
            return result;
        }
        checkVectorDimensions(v);
        double[] out = (double[]) this.data.clone();
        for (int i2 = 0; i2 < this.data.length; i2++) {
            out[i2] = out[i2] / v.getEntry(i2);
        }
        return new d(out, false);
    }

    public double[] getDataRef() {
        return this.data;
    }

    public double dotProduct(q v) {
        if (!(v instanceof d)) {
            return super.dotProduct(v);
        }
        double[] vData = ((d) v).data;
        checkVectorDimensions(vData.length);
        double dot = 0.0d;
        int i = 0;
        while (true) {
            double[] dArr = this.data;
            if (i >= dArr.length) {
                return dot;
            }
            dot += dArr[i] * vData[i];
            i++;
        }
    }

    public double getNorm() {
        double sum = 0.0d;
        for (double a : this.data) {
            sum += a * a;
        }
        return org.apache.commons.math3.util.c.z(sum);
    }

    public double getL1Norm() {
        double sum = 0.0d;
        for (double a : this.data) {
            sum += org.apache.commons.math3.util.c.a(a);
        }
        return sum;
    }

    public double getLInfNorm() {
        double max = 0.0d;
        for (double a : this.data) {
            max = org.apache.commons.math3.util.c.o(max, org.apache.commons.math3.util.c.a(a));
        }
        return max;
    }

    public double getDistance(q v) {
        if (v instanceof d) {
            double[] vData = ((d) v).data;
            checkVectorDimensions(vData.length);
            double sum = 0.0d;
            int i = 0;
            while (true) {
                double[] dArr = this.data;
                if (i >= dArr.length) {
                    return org.apache.commons.math3.util.c.z(sum);
                }
                double delta = dArr[i] - vData[i];
                sum += delta * delta;
                i++;
            }
        } else {
            checkVectorDimensions(v);
            double sum2 = 0.0d;
            int i2 = 0;
            while (true) {
                double[] dArr2 = this.data;
                if (i2 >= dArr2.length) {
                    return org.apache.commons.math3.util.c.z(sum2);
                }
                double delta2 = dArr2[i2] - v.getEntry(i2);
                sum2 += delta2 * delta2;
                i2++;
            }
        }
    }

    public double getL1Distance(q v) {
        if (v instanceof d) {
            double[] vData = ((d) v).data;
            checkVectorDimensions(vData.length);
            double sum = 0.0d;
            int i = 0;
            while (true) {
                double[] dArr = this.data;
                if (i >= dArr.length) {
                    return sum;
                }
                sum += org.apache.commons.math3.util.c.a(dArr[i] - vData[i]);
                i++;
            }
        } else {
            checkVectorDimensions(v);
            double sum2 = 0.0d;
            int i2 = 0;
            while (true) {
                double[] dArr2 = this.data;
                if (i2 >= dArr2.length) {
                    return sum2;
                }
                sum2 += org.apache.commons.math3.util.c.a(dArr2[i2] - v.getEntry(i2));
                i2++;
            }
        }
    }

    public double getLInfDistance(q v) {
        if (v instanceof d) {
            double[] vData = ((d) v).data;
            checkVectorDimensions(vData.length);
            double max = 0.0d;
            int i = 0;
            while (true) {
                double[] dArr = this.data;
                if (i >= dArr.length) {
                    return max;
                }
                max = org.apache.commons.math3.util.c.o(max, org.apache.commons.math3.util.c.a(dArr[i] - vData[i]));
                i++;
            }
        } else {
            checkVectorDimensions(v);
            double max2 = 0.0d;
            int i2 = 0;
            while (true) {
                double[] dArr2 = this.data;
                if (i2 >= dArr2.length) {
                    return max2;
                }
                max2 = org.apache.commons.math3.util.c.o(max2, org.apache.commons.math3.util.c.a(dArr2[i2] - v.getEntry(i2)));
                i2++;
            }
        }
    }

    public m outerProduct(q v) {
        if (v instanceof d) {
            double[] vData = ((d) v).data;
            int m = this.data.length;
            int n = vData.length;
            m out = j.j(m, n);
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    out.setEntry(i, j, this.data[i] * vData[j]);
                }
            }
            return out;
        }
        int m2 = this.data.length;
        int n2 = v.getDimension();
        m out2 = j.j(m2, n2);
        for (int i2 = 0; i2 < m2; i2++) {
            for (int j2 = 0; j2 < n2; j2++) {
                out2.setEntry(i2, j2, this.data[i2] * v.getEntry(j2));
            }
        }
        return out2;
    }

    public double getEntry(int index) {
        try {
            return this.data[index];
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfRangeException(org.apache.commons.math3.exception.util.d.INDEX, Integer.valueOf(index), 0, Integer.valueOf(getDimension() - 1));
        }
    }

    public int getDimension() {
        return this.data.length;
    }

    public q append(q v) {
        try {
            return new d(this, (d) v);
        } catch (ClassCastException e) {
            return new d(this, v);
        }
    }

    public d append(d v) {
        return new d(this, v);
    }

    public q append(double in) {
        double[] dArr = this.data;
        double[] out = new double[(dArr.length + 1)];
        System.arraycopy(dArr, 0, out, 0, dArr.length);
        out[this.data.length] = in;
        return new d(out, false);
    }

    public q getSubVector(int index, int n) {
        if (n >= 0) {
            d out = new d(n);
            try {
                System.arraycopy(this.data, index, out.data, 0, n);
            } catch (IndexOutOfBoundsException e) {
                checkIndex(index);
                checkIndex((index + n) - 1);
            }
            return out;
        }
        throw new NotPositiveException(org.apache.commons.math3.exception.util.d.NUMBER_OF_ELEMENTS_SHOULD_BE_POSITIVE, Integer.valueOf(n));
    }

    public void setEntry(int index, double value) {
        try {
            this.data[index] = value;
        } catch (IndexOutOfBoundsException e) {
            checkIndex(index);
        }
    }

    public void addToEntry(int index, double increment) {
        try {
            double[] dArr = this.data;
            dArr[index] = dArr[index] + increment;
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfRangeException(org.apache.commons.math3.exception.util.d.INDEX, Integer.valueOf(index), 0, Integer.valueOf(this.data.length - 1));
        }
    }

    public void setSubVector(int index, q v) {
        if (v instanceof d) {
            setSubVector(index, ((d) v).data);
            return;
        }
        int i = index;
        while (i < v.getDimension() + index) {
            try {
                this.data[i] = v.getEntry(i - index);
                i++;
            } catch (IndexOutOfBoundsException e) {
                checkIndex(index);
                checkIndex((v.getDimension() + index) - 1);
                return;
            }
        }
    }

    public void setSubVector(int index, double[] v) {
        try {
            System.arraycopy(v, 0, this.data, index, v.length);
        } catch (IndexOutOfBoundsException e) {
            checkIndex(index);
            checkIndex((v.length + index) - 1);
        }
    }

    public void set(double value) {
        Arrays.fill(this.data, value);
    }

    public double[] toArray() {
        return (double[]) this.data.clone();
    }

    public String toString() {
        return c.a(this);
    }

    /* access modifiers changed from: protected */
    public void checkVectorDimensions(q v) {
        checkVectorDimensions(v.getDimension());
    }

    /* access modifiers changed from: protected */
    public void checkVectorDimensions(int n) {
        if (this.data.length != n) {
            throw new DimensionMismatchException(this.data.length, n);
        }
    }

    public boolean isNaN() {
        for (double v : this.data) {
            if (Double.isNaN(v)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInfinite() {
        if (isNaN()) {
            return false;
        }
        for (double v : this.data) {
            if (Double.isInfinite(v)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof q)) {
            return false;
        }
        q rhs = (q) other;
        if (this.data.length != rhs.getDimension()) {
            return false;
        }
        if (rhs.isNaN()) {
            return isNaN();
        }
        int i = 0;
        while (true) {
            double[] dArr = this.data;
            if (i >= dArr.length) {
                return true;
            }
            if (dArr[i] != rhs.getEntry(i)) {
                return false;
            }
            i++;
        }
    }

    public int hashCode() {
        if (isNaN()) {
            return 9;
        }
        return f.d(this.data);
    }

    public d combine(double a, double b, q y) {
        return copy().combineToSelf(a, b, y);
    }

    public d combineToSelf(double a, double b, q y) {
        if (!(y instanceof d)) {
            checkVectorDimensions(y);
            int i = 0;
            while (true) {
                double[] dArr = this.data;
                if (i >= dArr.length) {
                    break;
                }
                dArr[i] = (dArr[i] * a) + (y.getEntry(i) * b);
                i++;
            }
        } else {
            double[] yData = ((d) y).data;
            checkVectorDimensions(yData.length);
            int i2 = 0;
            while (true) {
                double[] dArr2 = this.data;
                if (i2 >= dArr2.length) {
                    break;
                }
                dArr2[i2] = (dArr2[i2] * a) + (yData[i2] * b);
                i2++;
            }
        }
        return this;
    }

    public double walkInDefaultOrder(t visitor) {
        double[] dArr = this.data;
        visitor.b(dArr.length, 0, dArr.length - 1);
        int i = 0;
        while (true) {
            double[] dArr2 = this.data;
            if (i >= dArr2.length) {
                return visitor.end();
            }
            visitor.a(i, dArr2[i]);
            i++;
        }
    }

    public double walkInDefaultOrder(t visitor, int start, int end) {
        checkIndices(start, end);
        visitor.b(this.data.length, start, end);
        for (int i = start; i <= end; i++) {
            visitor.a(i, this.data[i]);
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
        double[] dArr = this.data;
        visitor.b(dArr.length, 0, dArr.length - 1);
        int i = 0;
        while (true) {
            double[] dArr2 = this.data;
            if (i >= dArr2.length) {
                return visitor.end();
            }
            dArr2[i] = visitor.a(i, dArr2[i]);
            i++;
        }
    }

    public double walkInDefaultOrder(r visitor, int start, int end) {
        checkIndices(start, end);
        visitor.b(this.data.length, start, end);
        for (int i = start; i <= end; i++) {
            double[] dArr = this.data;
            dArr[i] = visitor.a(i, dArr[i]);
        }
        return visitor.end();
    }

    public double walkInOptimizedOrder(r visitor) {
        return walkInDefaultOrder(visitor);
    }

    public double walkInOptimizedOrder(r visitor, int start, int end) {
        return walkInDefaultOrder(visitor, start, end);
    }
}
