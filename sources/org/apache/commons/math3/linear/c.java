package org.apache.commons.math3.linear;

import java.io.Serializable;
import java.lang.reflect.Array;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MathIllegalStateException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.util.d;
import org.apache.commons.math3.util.f;

/* compiled from: Array2DRowRealMatrix */
public class c extends a implements Serializable {
    private static final long serialVersionUID = -1067294169172445528L;
    private double[][] data;

    public c() {
    }

    public c(int rowDimension, int columnDimension) {
        super(rowDimension, columnDimension);
        int[] iArr = new int[2];
        iArr[1] = columnDimension;
        iArr[0] = rowDimension;
        this.data = (double[][]) Array.newInstance(double.class, iArr);
    }

    public c(double[][] d) {
        a(d);
    }

    public c(double[][] d, boolean copyArray) {
        if (copyArray) {
            a(d);
        } else if (d != null) {
            int nRows = d.length;
            if (nRows != 0) {
                int nCols = d[0].length;
                if (nCols != 0) {
                    int r = 1;
                    while (r < nRows) {
                        if (d[r].length == nCols) {
                            r++;
                        } else {
                            throw new DimensionMismatchException(d[r].length, nCols);
                        }
                    }
                    this.data = d;
                    return;
                }
                throw new NoDataException(d.AT_LEAST_ONE_COLUMN);
            }
            throw new NoDataException(d.AT_LEAST_ONE_ROW);
        } else {
            throw new NullArgumentException();
        }
    }

    public c(double[] v) {
        int nRows = v.length;
        int[] iArr = new int[2];
        iArr[1] = 1;
        iArr[0] = nRows;
        this.data = (double[][]) Array.newInstance(double.class, iArr);
        for (int row = 0; row < nRows; row++) {
            this.data[row][0] = v[row];
        }
    }

    public m createMatrix(int rowDimension, int columnDimension) {
        return new c(rowDimension, columnDimension);
    }

    public m copy() {
        return new c(b(), false);
    }

    public c add(c m) {
        j.a(this, m);
        int rowCount = getRowDimension();
        int columnCount = getColumnDimension();
        int[] iArr = new int[2];
        iArr[1] = columnCount;
        iArr[0] = rowCount;
        double[][] outData = (double[][]) Array.newInstance(double.class, iArr);
        for (int row = 0; row < rowCount; row++) {
            double[] dataRow = this.data[row];
            double[] mRow = m.data[row];
            double[] outDataRow = outData[row];
            for (int col = 0; col < columnCount; col++) {
                outDataRow[col] = dataRow[col] + mRow[col];
            }
        }
        return new c(outData, false);
    }

    public c subtract(c m) {
        j.h(this, m);
        int rowCount = getRowDimension();
        int columnCount = getColumnDimension();
        int[] iArr = new int[2];
        iArr[1] = columnCount;
        iArr[0] = rowCount;
        double[][] outData = (double[][]) Array.newInstance(double.class, iArr);
        for (int row = 0; row < rowCount; row++) {
            double[] dataRow = this.data[row];
            double[] mRow = m.data[row];
            double[] outDataRow = outData[row];
            for (int col = 0; col < columnCount; col++) {
                outDataRow[col] = dataRow[col] - mRow[col];
            }
        }
        return new c(outData, false);
    }

    public c multiply(c m) {
        j.d(this, m);
        int nRows = getRowDimension();
        int nCols = m.getColumnDimension();
        int nSum = getColumnDimension();
        int[] iArr = new int[2];
        iArr[1] = nCols;
        iArr[0] = nRows;
        double[][] outData = (double[][]) Array.newInstance(double.class, iArr);
        double[] mCol = new double[nSum];
        double[][] mData = m.data;
        for (int col = 0; col < nCols; col++) {
            for (int mRow = 0; mRow < nSum; mRow++) {
                mCol[mRow] = mData[mRow][col];
            }
            for (int row = 0; row < nRows; row++) {
                double[] dataRow = this.data[row];
                double sum = 0.0d;
                for (int i = 0; i < nSum; i++) {
                    sum += dataRow[i] * mCol[i];
                }
                outData[row][col] = sum;
            }
        }
        return new c(outData, false);
    }

    public double[][] getData() {
        return b();
    }

    public double[][] getDataRef() {
        return this.data;
    }

    public void setSubMatrix(double[][] subMatrix, int row, int column) {
        if (this.data != null) {
            super.setSubMatrix(subMatrix, row, column);
        } else if (row > 0) {
            throw new MathIllegalStateException(d.FIRST_ROWS_NOT_INITIALIZED_YET, Integer.valueOf(row));
        } else if (column <= 0) {
            f.a(subMatrix);
            if (subMatrix.length != 0) {
                int nCols = subMatrix[0].length;
                if (nCols != 0) {
                    int length = subMatrix.length;
                    int[] iArr = new int[2];
                    iArr[1] = nCols;
                    iArr[0] = length;
                    this.data = (double[][]) Array.newInstance(double.class, iArr);
                    int i = 0;
                    while (true) {
                        double[][] dArr = this.data;
                        if (i >= dArr.length) {
                            return;
                        }
                        if (subMatrix[i].length == nCols) {
                            System.arraycopy(subMatrix[i], 0, dArr[i + row], column, nCols);
                            i++;
                        } else {
                            throw new DimensionMismatchException(subMatrix[i].length, nCols);
                        }
                    }
                } else {
                    throw new NoDataException(d.AT_LEAST_ONE_COLUMN);
                }
            } else {
                throw new NoDataException(d.AT_LEAST_ONE_ROW);
            }
        } else {
            throw new MathIllegalStateException(d.FIRST_COLUMNS_NOT_INITIALIZED_YET, Integer.valueOf(column));
        }
    }

    public double getEntry(int row, int column) {
        j.c(this, row, column);
        return this.data[row][column];
    }

    public void setEntry(int row, int column, double value) {
        j.c(this, row, column);
        this.data[row][column] = value;
    }

    public void addToEntry(int row, int column, double increment) {
        j.c(this, row, column);
        double[] dArr = this.data[row];
        dArr[column] = dArr[column] + increment;
    }

    public void multiplyEntry(int row, int column, double factor) {
        j.c(this, row, column);
        double[] dArr = this.data[row];
        dArr[column] = dArr[column] * factor;
    }

    public int getRowDimension() {
        double[][] dArr = this.data;
        if (dArr == null) {
            return 0;
        }
        return dArr.length;
    }

    public int getColumnDimension() {
        double[][] dArr = this.data;
        if (dArr == null || dArr[0] == null) {
            return 0;
        }
        return dArr[0].length;
    }

    public double[] operate(double[] v) {
        int nRows = getRowDimension();
        int nCols = getColumnDimension();
        if (v.length == nCols) {
            double[] out = new double[nRows];
            for (int row = 0; row < nRows; row++) {
                double[] dataRow = this.data[row];
                double sum = 0.0d;
                for (int i = 0; i < nCols; i++) {
                    sum += dataRow[i] * v[i];
                }
                out[row] = sum;
            }
            return out;
        }
        throw new DimensionMismatchException(v.length, nCols);
    }

    public double[] preMultiply(double[] v) {
        int nRows = getRowDimension();
        int nCols = getColumnDimension();
        if (v.length == nRows) {
            double[] out = new double[nCols];
            for (int col = 0; col < nCols; col++) {
                double sum = 0.0d;
                for (int i = 0; i < nRows; i++) {
                    sum += this.data[i][col] * v[i];
                }
                out[col] = sum;
            }
            return out;
        }
        throw new DimensionMismatchException(v.length, nRows);
    }

    public double walkInRowOrder(n visitor) {
        int rows = getRowDimension();
        int columns = getColumnDimension();
        visitor.b(rows, columns, 0, rows - 1, 0, columns - 1);
        for (int i = 0; i < rows; i++) {
            double[] rowI = this.data[i];
            for (int j = 0; j < columns; j++) {
                rowI[j] = visitor.a(i, j, rowI[j]);
            }
        }
        return visitor.end();
    }

    public double walkInRowOrder(p visitor) {
        int rows = getRowDimension();
        int columns = getColumnDimension();
        visitor.b(rows, columns, 0, rows - 1, 0, columns - 1);
        for (int i = 0; i < rows; i++) {
            double[] rowI = this.data[i];
            for (int j = 0; j < columns; j++) {
                visitor.a(i, j, rowI[j]);
            }
        }
        return visitor.end();
    }

    public double walkInRowOrder(n visitor, int startRow, int endRow, int startColumn, int endColumn) {
        j.f(this, startRow, endRow, startColumn, endColumn);
        visitor.b(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
        for (int i = startRow; i <= endRow; i++) {
            double[] rowI = this.data[i];
            for (int j = startColumn; j <= endColumn; j++) {
                rowI[j] = visitor.a(i, j, rowI[j]);
            }
        }
        return visitor.end();
    }

    public double walkInRowOrder(p visitor, int startRow, int endRow, int startColumn, int endColumn) {
        j.f(this, startRow, endRow, startColumn, endColumn);
        visitor.b(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
        for (int i = startRow; i <= endRow; i++) {
            double[] rowI = this.data[i];
            for (int j = startColumn; j <= endColumn; j++) {
                visitor.a(i, j, rowI[j]);
            }
        }
        return visitor.end();
    }

    public double walkInColumnOrder(n visitor) {
        int rows = getRowDimension();
        int columns = getColumnDimension();
        visitor.b(rows, columns, 0, rows - 1, 0, columns - 1);
        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < rows; i++) {
                double[] rowI = this.data[i];
                rowI[j] = visitor.a(i, j, rowI[j]);
            }
        }
        return visitor.end();
    }

    public double walkInColumnOrder(p visitor) {
        int rows = getRowDimension();
        int columns = getColumnDimension();
        visitor.b(rows, columns, 0, rows - 1, 0, columns - 1);
        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < rows; i++) {
                visitor.a(i, j, this.data[i][j]);
            }
        }
        return visitor.end();
    }

    public double walkInColumnOrder(n visitor, int startRow, int endRow, int startColumn, int endColumn) {
        j.f(this, startRow, endRow, startColumn, endColumn);
        visitor.b(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
        for (int j = startColumn; j <= endColumn; j++) {
            for (int i = startRow; i <= endRow; i++) {
                double[] rowI = this.data[i];
                rowI[j] = visitor.a(i, j, rowI[j]);
            }
        }
        return visitor.end();
    }

    public double walkInColumnOrder(p visitor, int startRow, int endRow, int startColumn, int endColumn) {
        j.f(this, startRow, endRow, startColumn, endColumn);
        visitor.b(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
        for (int j = startColumn; j <= endColumn; j++) {
            for (int i = startRow; i <= endRow; i++) {
                visitor.a(i, j, this.data[i][j]);
            }
        }
        return visitor.end();
    }

    private double[][] b() {
        int nRows = getRowDimension();
        int[] iArr = new int[2];
        iArr[1] = getColumnDimension();
        iArr[0] = nRows;
        double[][] out = (double[][]) Array.newInstance(double.class, iArr);
        for (int i = 0; i < nRows; i++) {
            double[][] dArr = this.data;
            System.arraycopy(dArr[i], 0, out[i], 0, dArr[i].length);
        }
        return out;
    }

    private void a(double[][] in) {
        setSubMatrix(in, 0, 0);
    }
}
