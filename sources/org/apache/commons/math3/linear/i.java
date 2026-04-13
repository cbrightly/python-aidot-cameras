package org.apache.commons.math3.linear;

import java.io.Serializable;
import java.lang.reflect.Array;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.util.c;
import org.apache.commons.math3.util.f;
import org.apache.commons.math3.util.g;

/* compiled from: DiagonalMatrix */
public class i extends a implements Serializable {
    private static final long serialVersionUID = 20121229;
    private final double[] data;

    public i(int dimension) {
        super(dimension, dimension);
        this.data = new double[dimension];
    }

    public i(double[] d) {
        this(d, true);
    }

    public i(double[] d, boolean copyArray) {
        f.a(d);
        this.data = copyArray ? (double[]) d.clone() : d;
    }

    public m createMatrix(int rowDimension, int columnDimension) {
        if (rowDimension == columnDimension) {
            return new i(rowDimension);
        }
        throw new DimensionMismatchException(rowDimension, columnDimension);
    }

    public m copy() {
        return new i(this.data);
    }

    public i add(i m) {
        j.a(this, m);
        int dim = getRowDimension();
        double[] outData = new double[dim];
        for (int i = 0; i < dim; i++) {
            outData[i] = this.data[i] + m.data[i];
        }
        return new i(outData, false);
    }

    public i subtract(i m) {
        j.h(this, m);
        int dim = getRowDimension();
        double[] outData = new double[dim];
        for (int i = 0; i < dim; i++) {
            outData[i] = this.data[i] - m.data[i];
        }
        return new i(outData, false);
    }

    public i multiply(i m) {
        j.d(this, m);
        int dim = getRowDimension();
        double[] outData = new double[dim];
        for (int i = 0; i < dim; i++) {
            outData[i] = this.data[i] * m.data[i];
        }
        return new i(outData, false);
    }

    public m multiply(m m) {
        if (m instanceof i) {
            return multiply((i) m);
        }
        j.d(this, m);
        int nRows = m.getRowDimension();
        int nCols = m.getColumnDimension();
        int[] iArr = new int[2];
        iArr[1] = nCols;
        iArr[0] = nRows;
        double[][] product = (double[][]) Array.newInstance(double.class, iArr);
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                product[r][c] = this.data[r] * m.getEntry(r, c);
            }
        }
        return new c(product, false);
    }

    public double[][] getData() {
        int dim = getRowDimension();
        int[] iArr = new int[2];
        iArr[1] = dim;
        iArr[0] = dim;
        double[][] out = (double[][]) Array.newInstance(double.class, iArr);
        for (int i = 0; i < dim; i++) {
            out[i][i] = this.data[i];
        }
        return out;
    }

    public double[] getDataRef() {
        return this.data;
    }

    public double getEntry(int row, int column) {
        j.c(this, row, column);
        if (row == column) {
            return this.data[row];
        }
        return 0.0d;
    }

    public void setEntry(int row, int column, double value) {
        if (row == column) {
            j.e(this, row);
            this.data[row] = value;
            return;
        }
        a(value);
    }

    public void addToEntry(int row, int column, double increment) {
        if (row == column) {
            j.e(this, row);
            double[] dArr = this.data;
            dArr[row] = dArr[row] + increment;
            return;
        }
        a(increment);
    }

    public void multiplyEntry(int row, int column, double factor) {
        if (row == column) {
            j.e(this, row);
            double[] dArr = this.data;
            dArr[row] = dArr[row] * factor;
        }
    }

    public int getRowDimension() {
        return this.data.length;
    }

    public int getColumnDimension() {
        return this.data.length;
    }

    public double[] operate(double[] v) {
        return multiply(new i(v, false)).getDataRef();
    }

    public double[] preMultiply(double[] v) {
        return operate(v);
    }

    public q preMultiply(q v) {
        double[] vectorData;
        if (v instanceof d) {
            vectorData = ((d) v).getDataRef();
        } else {
            vectorData = v.toArray();
        }
        return j.k(preMultiply(vectorData));
    }

    private void a(double value) {
        if (!g.b(0.0d, value, 1)) {
            throw new NumberIsTooLargeException(Double.valueOf(c.a(value)), 0, true);
        }
    }

    public i inverse() {
        return inverse(0.0d);
    }

    public i inverse(double threshold) {
        if (!isSingular(threshold)) {
            double[] result = new double[this.data.length];
            int i = 0;
            while (true) {
                double[] dArr = this.data;
                if (i >= dArr.length) {
                    return new i(result, false);
                }
                result[i] = 1.0d / dArr[i];
                i++;
            }
        } else {
            throw new SingularMatrixException();
        }
    }

    public boolean isSingular(double threshold) {
        int i = 0;
        while (true) {
            double[] dArr = this.data;
            if (i >= dArr.length) {
                return false;
            }
            if (g.a(dArr[i], 0.0d, threshold)) {
                return true;
            }
            i++;
        }
    }
}
