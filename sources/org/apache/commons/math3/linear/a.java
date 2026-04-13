package org.apache.commons.math3.linear;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.util.f;

/* compiled from: AbstractRealMatrix */
public abstract class a extends l implements m {
    private static final o c;

    public abstract m copy();

    public abstract m createMatrix(int i, int i2);

    public abstract int getColumnDimension();

    public abstract double getEntry(int i, int i2);

    public abstract int getRowDimension();

    public abstract void setEntry(int i, int i2, double d2);

    static {
        o e2 = o.e(Locale.US);
        c = e2;
        e2.c().setMinimumFractionDigits(1);
    }

    protected a() {
    }

    protected a(int rowDimension, int columnDimension) {
        if (rowDimension < 1) {
            throw new NotStrictlyPositiveException(Integer.valueOf(rowDimension));
        } else if (columnDimension < 1) {
            throw new NotStrictlyPositiveException(Integer.valueOf(columnDimension));
        }
    }

    public m add(m m) {
        j.a(this, m);
        int rowCount = getRowDimension();
        int columnCount = getColumnDimension();
        m out = createMatrix(rowCount, columnCount);
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                out.setEntry(row, col, getEntry(row, col) + m.getEntry(row, col));
            }
        }
        return out;
    }

    public m subtract(m m) {
        j.h(this, m);
        int rowCount = getRowDimension();
        int columnCount = getColumnDimension();
        m out = createMatrix(rowCount, columnCount);
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                out.setEntry(row, col, getEntry(row, col) - m.getEntry(row, col));
            }
        }
        return out;
    }

    public m scalarAdd(double d2) {
        int rowCount = getRowDimension();
        int columnCount = getColumnDimension();
        m out = createMatrix(rowCount, columnCount);
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                out.setEntry(row, col, getEntry(row, col) + d2);
            }
        }
        return out;
    }

    public m scalarMultiply(double d2) {
        int rowCount = getRowDimension();
        int columnCount = getColumnDimension();
        m out = createMatrix(rowCount, columnCount);
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                out.setEntry(row, col, getEntry(row, col) * d2);
            }
        }
        return out;
    }

    public m multiply(m m) {
        j.d(this, m);
        int nRows = getRowDimension();
        int nCols = m.getColumnDimension();
        int nSum = getColumnDimension();
        m out = createMatrix(nRows, nCols);
        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                double sum = 0.0d;
                for (int i = 0; i < nSum; i++) {
                    sum += getEntry(row, i) * m.getEntry(i, col);
                }
                out.setEntry(row, col, sum);
            }
        }
        return out;
    }

    public m preMultiply(m m) {
        return m.multiply(this);
    }

    public m power(int p) {
        if (p < 0) {
            throw new NotPositiveException(org.apache.commons.math3.exception.util.d.NOT_POSITIVE_EXPONENT, Integer.valueOf(p));
        } else if (!isSquare()) {
            throw new NonSquareMatrixException(getRowDimension(), getColumnDimension());
        } else if (p == 0) {
            return j.i(getRowDimension());
        } else {
            if (p == 1) {
                return copy();
            }
            char[] binaryRepresentation = Integer.toBinaryString(p - 1).toCharArray();
            ArrayList<Integer> nonZeroPositions = new ArrayList<>();
            int maxI = -1;
            for (int i = 0; i < binaryRepresentation.length; i++) {
                if (binaryRepresentation[i] == '1') {
                    int pos = (binaryRepresentation.length - i) - 1;
                    nonZeroPositions.add(Integer.valueOf(pos));
                    if (maxI == -1) {
                        maxI = pos;
                    }
                }
            }
            m[] results = new m[(maxI + 1)];
            results[0] = copy();
            for (int i2 = 1; i2 <= maxI; i2++) {
                results[i2] = results[i2 - 1].multiply(results[i2 - 1]);
            }
            m result = copy();
            Iterator i$ = nonZeroPositions.iterator();
            while (i$.hasNext()) {
                result = result.multiply(results[i$.next().intValue()]);
            }
            return result;
        }
    }

    public double[][] getData() {
        int rowDimension = getRowDimension();
        int[] iArr = new int[2];
        iArr[1] = getColumnDimension();
        iArr[0] = rowDimension;
        double[][] data = (double[][]) Array.newInstance(double.class, iArr);
        for (int i = 0; i < data.length; i++) {
            double[] dataI = data[i];
            for (int j = 0; j < dataI.length; j++) {
                dataI[j] = getEntry(i, j);
            }
        }
        return data;
    }

    /* renamed from: org.apache.commons.math3.linear.a$a  reason: collision with other inner class name */
    /* compiled from: AbstractRealMatrix */
    public class C0484a implements p {
        private double a;
        private double b;
        private double c;

        C0484a() {
        }

        public void b(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
            this.a = (double) endRow;
            this.b = 0.0d;
            this.c = 0.0d;
        }

        public void a(int row, int column, double value) {
            double a2 = this.b + org.apache.commons.math3.util.c.a(value);
            this.b = a2;
            if (((double) row) == this.a) {
                this.c = org.apache.commons.math3.util.c.o(this.c, a2);
                this.b = 0.0d;
            }
        }

        public double end() {
            return this.c;
        }
    }

    public double getNorm() {
        return walkInColumnOrder((p) new C0484a());
    }

    /* compiled from: AbstractRealMatrix */
    public class b implements p {
        private double a;

        b() {
        }

        public void b(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
            this.a = 0.0d;
        }

        public void a(int row, int column, double value) {
            this.a += value * value;
        }

        public double end() {
            return org.apache.commons.math3.util.c.z(this.a);
        }
    }

    public double getFrobeniusNorm() {
        return walkInOptimizedOrder((p) new b());
    }

    public m getSubMatrix(int startRow, int endRow, int startColumn, int endColumn) {
        j.f(this, startRow, endRow, startColumn, endColumn);
        m subMatrix = createMatrix((endRow - startRow) + 1, (endColumn - startColumn) + 1);
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startColumn; j <= endColumn; j++) {
                subMatrix.setEntry(i - startRow, j - startColumn, getEntry(i, j));
            }
        }
        return subMatrix;
    }

    public m getSubMatrix(int[] selectedRows, int[] selectedColumns) {
        j.g(this, selectedRows, selectedColumns);
        m subMatrix = createMatrix(selectedRows.length, selectedColumns.length);
        subMatrix.walkInOptimizedOrder(new c(selectedRows, selectedColumns));
        return subMatrix;
    }

    /* compiled from: AbstractRealMatrix */
    public class c extends g {
        final /* synthetic */ int[] a;
        final /* synthetic */ int[] b;

        c(int[] iArr, int[] iArr2) {
            this.a = iArr;
            this.b = iArr2;
        }

        public double a(int row, int column, double value) {
            return a.this.getEntry(this.a[row], this.b[column]);
        }
    }

    public void copySubMatrix(int startRow, int endRow, int startColumn, int endColumn, double[][] destination) {
        j.f(this, startRow, endRow, startColumn, endColumn);
        int rowsCount = (endRow + 1) - startRow;
        int columnsCount = (endColumn + 1) - startColumn;
        if (destination.length < rowsCount || destination[0].length < columnsCount) {
            throw new MatrixDimensionMismatchException(destination.length, destination[0].length, rowsCount, columnsCount);
        }
        int i = 1;
        while (i < rowsCount) {
            if (destination[i].length >= columnsCount) {
                i++;
            } else {
                throw new MatrixDimensionMismatchException(destination.length, destination[i].length, rowsCount, columnsCount);
            }
        }
        walkInOptimizedOrder((p) new d(destination), startRow, endRow, startColumn, endColumn);
    }

    /* compiled from: AbstractRealMatrix */
    public class d extends h {
        private int a;
        private int b;
        final /* synthetic */ double[][] c;

        d(double[][] dArr) {
            this.c = dArr;
        }

        public void b(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
            this.a = startRow;
            this.b = startColumn;
        }

        public void a(int row, int column, double value) {
            this.c[row - this.a][column - this.b] = value;
        }
    }

    public void copySubMatrix(int[] selectedRows, int[] selectedColumns, double[][] destination) {
        j.g(this, selectedRows, selectedColumns);
        int nCols = selectedColumns.length;
        if (destination.length < selectedRows.length || destination[0].length < nCols) {
            throw new MatrixDimensionMismatchException(destination.length, destination[0].length, selectedRows.length, selectedColumns.length);
        }
        int i = 0;
        while (i < selectedRows.length) {
            double[] destinationI = destination[i];
            if (destinationI.length >= nCols) {
                for (int j = 0; j < selectedColumns.length; j++) {
                    destinationI[j] = getEntry(selectedRows[i], selectedColumns[j]);
                }
                i++;
            } else {
                throw new MatrixDimensionMismatchException(destination.length, destinationI.length, selectedRows.length, selectedColumns.length);
            }
        }
    }

    public void setSubMatrix(double[][] subMatrix, int row, int column) {
        f.a(subMatrix);
        int nRows = subMatrix.length;
        if (nRows != 0) {
            int nCols = subMatrix[0].length;
            if (nCols != 0) {
                int r = 1;
                while (r < nRows) {
                    if (subMatrix[r].length == nCols) {
                        r++;
                    } else {
                        throw new DimensionMismatchException(nCols, subMatrix[r].length);
                    }
                }
                j.e(this, row);
                j.b(this, column);
                j.e(this, (nRows + row) - 1);
                j.b(this, (nCols + column) - 1);
                for (int i = 0; i < nRows; i++) {
                    for (int j = 0; j < nCols; j++) {
                        setEntry(row + i, column + j, subMatrix[i][j]);
                    }
                }
                return;
            }
            throw new NoDataException(org.apache.commons.math3.exception.util.d.AT_LEAST_ONE_COLUMN);
        }
        throw new NoDataException(org.apache.commons.math3.exception.util.d.AT_LEAST_ONE_ROW);
    }

    public m getRowMatrix(int row) {
        j.e(this, row);
        int nCols = getColumnDimension();
        m out = createMatrix(1, nCols);
        for (int i = 0; i < nCols; i++) {
            out.setEntry(0, i, getEntry(row, i));
        }
        return out;
    }

    public void setRowMatrix(int row, m matrix) {
        j.e(this, row);
        int nCols = getColumnDimension();
        if (matrix.getRowDimension() == 1 && matrix.getColumnDimension() == nCols) {
            for (int i = 0; i < nCols; i++) {
                setEntry(row, i, matrix.getEntry(0, i));
            }
            return;
        }
        throw new MatrixDimensionMismatchException(matrix.getRowDimension(), matrix.getColumnDimension(), 1, nCols);
    }

    public m getColumnMatrix(int column) {
        j.b(this, column);
        int nRows = getRowDimension();
        m out = createMatrix(nRows, 1);
        for (int i = 0; i < nRows; i++) {
            out.setEntry(i, 0, getEntry(i, column));
        }
        return out;
    }

    public void setColumnMatrix(int column, m matrix) {
        j.b(this, column);
        int nRows = getRowDimension();
        if (matrix.getRowDimension() == nRows && matrix.getColumnDimension() == 1) {
            for (int i = 0; i < nRows; i++) {
                setEntry(i, column, matrix.getEntry(i, 0));
            }
            return;
        }
        throw new MatrixDimensionMismatchException(matrix.getRowDimension(), matrix.getColumnDimension(), nRows, 1);
    }

    public q getRowVector(int row) {
        return new d(getRow(row), false);
    }

    public void setRowVector(int row, q vector) {
        j.e(this, row);
        int nCols = getColumnDimension();
        if (vector.getDimension() == nCols) {
            for (int i = 0; i < nCols; i++) {
                setEntry(row, i, vector.getEntry(i));
            }
            return;
        }
        throw new MatrixDimensionMismatchException(1, vector.getDimension(), 1, nCols);
    }

    public q getColumnVector(int column) {
        return new d(getColumn(column), false);
    }

    public void setColumnVector(int column, q vector) {
        j.b(this, column);
        int nRows = getRowDimension();
        if (vector.getDimension() == nRows) {
            for (int i = 0; i < nRows; i++) {
                setEntry(i, column, vector.getEntry(i));
            }
            return;
        }
        throw new MatrixDimensionMismatchException(vector.getDimension(), 1, nRows, 1);
    }

    public double[] getRow(int row) {
        j.e(this, row);
        int nCols = getColumnDimension();
        double[] out = new double[nCols];
        for (int i = 0; i < nCols; i++) {
            out[i] = getEntry(row, i);
        }
        return out;
    }

    public void setRow(int row, double[] array) {
        j.e(this, row);
        int nCols = getColumnDimension();
        if (array.length == nCols) {
            for (int i = 0; i < nCols; i++) {
                setEntry(row, i, array[i]);
            }
            return;
        }
        throw new MatrixDimensionMismatchException(1, array.length, 1, nCols);
    }

    public double[] getColumn(int column) {
        j.b(this, column);
        int nRows = getRowDimension();
        double[] out = new double[nRows];
        for (int i = 0; i < nRows; i++) {
            out[i] = getEntry(i, column);
        }
        return out;
    }

    public void setColumn(int column, double[] array) {
        j.b(this, column);
        int nRows = getRowDimension();
        if (array.length == nRows) {
            for (int i = 0; i < nRows; i++) {
                setEntry(i, column, array[i]);
            }
            return;
        }
        throw new MatrixDimensionMismatchException(array.length, 1, nRows, 1);
    }

    public void addToEntry(int row, int column, double increment) {
        j.c(this, row, column);
        setEntry(row, column, getEntry(row, column) + increment);
    }

    public void multiplyEntry(int row, int column, double factor) {
        j.c(this, row, column);
        setEntry(row, column, getEntry(row, column) * factor);
    }

    public m transpose() {
        m out = createMatrix(getColumnDimension(), getRowDimension());
        walkInOptimizedOrder((p) new e(out));
        return out;
    }

    /* compiled from: AbstractRealMatrix */
    public class e extends h {
        final /* synthetic */ m a;

        e(m mVar) {
            this.a = mVar;
        }

        public void a(int row, int column, double value) {
            this.a.setEntry(column, row, value);
        }
    }

    public boolean isSquare() {
        return getColumnDimension() == getRowDimension();
    }

    public double getTrace() {
        int nRows = getRowDimension();
        int nCols = getColumnDimension();
        if (nRows == nCols) {
            double trace = 0.0d;
            for (int i = 0; i < nRows; i++) {
                trace += getEntry(i, i);
            }
            return trace;
        }
        throw new NonSquareMatrixException(nRows, nCols);
    }

    public double[] operate(double[] v) {
        int nRows = getRowDimension();
        int nCols = getColumnDimension();
        if (v.length == nCols) {
            double[] out = new double[nRows];
            for (int row = 0; row < nRows; row++) {
                double sum = 0.0d;
                for (int i = 0; i < nCols; i++) {
                    sum += getEntry(row, i) * v[i];
                }
                out[row] = sum;
            }
            return out;
        }
        throw new DimensionMismatchException(v.length, nCols);
    }

    public q operate(q v) {
        try {
            return new d(operate(((d) v).getDataRef()), false);
        } catch (ClassCastException e2) {
            int nRows = getRowDimension();
            int nCols = getColumnDimension();
            if (v.getDimension() == nCols) {
                double[] out = new double[nRows];
                for (int row = 0; row < nRows; row++) {
                    double sum = 0.0d;
                    for (int i = 0; i < nCols; i++) {
                        sum += getEntry(row, i) * v.getEntry(i);
                    }
                    out[row] = sum;
                }
                return new d(out, false);
            }
            throw new DimensionMismatchException(v.getDimension(), nCols);
        }
    }

    public double[] preMultiply(double[] v) {
        int nRows = getRowDimension();
        int nCols = getColumnDimension();
        if (v.length == nRows) {
            double[] out = new double[nCols];
            for (int col = 0; col < nCols; col++) {
                double sum = 0.0d;
                for (int i = 0; i < nRows; i++) {
                    sum += getEntry(i, col) * v[i];
                }
                out[col] = sum;
            }
            return out;
        }
        throw new DimensionMismatchException(v.length, nRows);
    }

    public q preMultiply(q v) {
        try {
            return new d(preMultiply(((d) v).getDataRef()), false);
        } catch (ClassCastException e2) {
            int nRows = getRowDimension();
            int nCols = getColumnDimension();
            if (v.getDimension() == nRows) {
                double[] out = new double[nCols];
                for (int col = 0; col < nCols; col++) {
                    double sum = 0.0d;
                    for (int i = 0; i < nRows; i++) {
                        sum += getEntry(i, col) * v.getEntry(i);
                    }
                    out[col] = sum;
                }
                return new d(out, false);
            }
            throw new DimensionMismatchException(v.getDimension(), nRows);
        }
    }

    public double walkInRowOrder(n visitor) {
        int rows = getRowDimension();
        int columns = getColumnDimension();
        visitor.b(rows, columns, 0, rows - 1, 0, columns - 1);
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                setEntry(row, column, visitor.a(row, column, getEntry(row, column)));
            }
        }
        return visitor.end();
    }

    public double walkInRowOrder(p visitor) {
        int rows = getRowDimension();
        int columns = getColumnDimension();
        visitor.b(rows, columns, 0, rows - 1, 0, columns - 1);
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                visitor.a(row, column, getEntry(row, column));
            }
        }
        return visitor.end();
    }

    public double walkInRowOrder(n visitor, int startRow, int endRow, int startColumn, int endColumn) {
        j.f(this, startRow, endRow, startColumn, endColumn);
        visitor.b(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
        for (int row = startRow; row <= endRow; row++) {
            for (int column = startColumn; column <= endColumn; column++) {
                setEntry(row, column, visitor.a(row, column, getEntry(row, column)));
            }
        }
        return visitor.end();
    }

    public double walkInRowOrder(p visitor, int startRow, int endRow, int startColumn, int endColumn) {
        j.f(this, startRow, endRow, startColumn, endColumn);
        visitor.b(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
        for (int row = startRow; row <= endRow; row++) {
            for (int column = startColumn; column <= endColumn; column++) {
                visitor.a(row, column, getEntry(row, column));
            }
        }
        return visitor.end();
    }

    public double walkInColumnOrder(n visitor) {
        int rows = getRowDimension();
        int columns = getColumnDimension();
        visitor.b(rows, columns, 0, rows - 1, 0, columns - 1);
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                setEntry(row, column, visitor.a(row, column, getEntry(row, column)));
            }
        }
        return visitor.end();
    }

    public double walkInColumnOrder(p visitor) {
        int rows = getRowDimension();
        int columns = getColumnDimension();
        visitor.b(rows, columns, 0, rows - 1, 0, columns - 1);
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                visitor.a(row, column, getEntry(row, column));
            }
        }
        return visitor.end();
    }

    public double walkInColumnOrder(n visitor, int startRow, int endRow, int startColumn, int endColumn) {
        j.f(this, startRow, endRow, startColumn, endColumn);
        visitor.b(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
        for (int column = startColumn; column <= endColumn; column++) {
            for (int row = startRow; row <= endRow; row++) {
                setEntry(row, column, visitor.a(row, column, getEntry(row, column)));
            }
        }
        return visitor.end();
    }

    public double walkInColumnOrder(p visitor, int startRow, int endRow, int startColumn, int endColumn) {
        j.f(this, startRow, endRow, startColumn, endColumn);
        visitor.b(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
        for (int column = startColumn; column <= endColumn; column++) {
            for (int row = startRow; row <= endRow; row++) {
                visitor.a(row, column, getEntry(row, column));
            }
        }
        return visitor.end();
    }

    public double walkInOptimizedOrder(n visitor) {
        return walkInRowOrder(visitor);
    }

    public double walkInOptimizedOrder(p visitor) {
        return walkInRowOrder(visitor);
    }

    public double walkInOptimizedOrder(n visitor, int startRow, int endRow, int startColumn, int endColumn) {
        return walkInRowOrder(visitor, startRow, endRow, startColumn, endColumn);
    }

    public double walkInOptimizedOrder(p visitor, int startRow, int endRow, int startColumn, int endColumn) {
        return walkInRowOrder(visitor, startRow, endRow, startColumn, endColumn);
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        String fullClassName = getClass().getName();
        res.append(fullClassName.substring(fullClassName.lastIndexOf(46) + 1));
        res.append(c.a(this));
        return res.toString();
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof m)) {
            return false;
        }
        m m = (m) object;
        int nRows = getRowDimension();
        int nCols = getColumnDimension();
        if (m.getColumnDimension() != nCols || m.getRowDimension() != nRows) {
            return false;
        }
        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                if (getEntry(row, col) != m.getEntry(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {
        int nRows = getRowDimension();
        int nCols = getColumnDimension();
        int ret = (((7 * 31) + nRows) * 31) + nCols;
        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                ret = (ret * 31) + ((((row + 1) * 11) + ((col + 1) * 17)) * f.c(getEntry(row, col)));
            }
        }
        return ret;
    }
}
