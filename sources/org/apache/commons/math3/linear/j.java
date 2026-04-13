package org.apache.commons.math3.linear;

import com.meituan.robust.Constants;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.exception.util.d;
import org.apache.commons.math3.util.f;

/* compiled from: MatrixUtils */
public class j {
    public static final o a = o.d();
    public static final o b = new o(Constants.ARRAY_TYPE, "]", "", "", "; ", ", ");

    public static m j(int rows, int columns) {
        return rows * columns <= 4096 ? new c(rows, columns) : new e(rows, columns);
    }

    public static m i(int dimension) {
        m m = j(dimension, dimension);
        for (int i = 0; i < dimension; i++) {
            m.setEntry(i, i, 1.0d);
        }
        return m;
    }

    public static q k(double[] data) {
        if (data != null) {
            return new d(data, true);
        }
        throw new NullArgumentException();
    }

    public static void c(b m, int row, int column) {
        e(m, row);
        b(m, column);
    }

    public static void e(b m, int row) {
        if (row < 0 || row >= m.getRowDimension()) {
            throw new OutOfRangeException(d.ROW_INDEX, Integer.valueOf(row), 0, Integer.valueOf(m.getRowDimension() - 1));
        }
    }

    public static void b(b m, int column) {
        if (column < 0 || column >= m.getColumnDimension()) {
            throw new OutOfRangeException(d.COLUMN_INDEX, Integer.valueOf(column), 0, Integer.valueOf(m.getColumnDimension() - 1));
        }
    }

    public static void f(b m, int startRow, int endRow, int startColumn, int endColumn) {
        e(m, startRow);
        e(m, endRow);
        if (endRow >= startRow) {
            b(m, startColumn);
            b(m, endColumn);
            if (endColumn < startColumn) {
                throw new NumberIsTooSmallException(d.INITIAL_COLUMN_AFTER_FINAL_COLUMN, Integer.valueOf(endColumn), Integer.valueOf(startColumn), false);
            }
            return;
        }
        throw new NumberIsTooSmallException(d.INITIAL_ROW_AFTER_FINAL_ROW, Integer.valueOf(endRow), Integer.valueOf(startRow), false);
    }

    public static void g(b m, int[] selectedRows, int[] selectedColumns) {
        if (selectedRows == null) {
            throw new NullArgumentException();
        } else if (selectedColumns == null) {
            throw new NullArgumentException();
        } else if (selectedRows.length == 0) {
            throw new NoDataException(d.EMPTY_SELECTED_ROW_INDEX_ARRAY);
        } else if (selectedColumns.length != 0) {
            for (int row : selectedRows) {
                e(m, row);
            }
            for (int column : selectedColumns) {
                b(m, column);
            }
        } else {
            throw new NoDataException(d.EMPTY_SELECTED_COLUMN_INDEX_ARRAY);
        }
    }

    public static void a(b left, b right) {
        if (left.getRowDimension() != right.getRowDimension() || left.getColumnDimension() != right.getColumnDimension()) {
            throw new MatrixDimensionMismatchException(left.getRowDimension(), left.getColumnDimension(), right.getRowDimension(), right.getColumnDimension());
        }
    }

    public static void h(b left, b right) {
        if (left.getRowDimension() != right.getRowDimension() || left.getColumnDimension() != right.getColumnDimension()) {
            throw new MatrixDimensionMismatchException(left.getRowDimension(), left.getColumnDimension(), right.getRowDimension(), right.getColumnDimension());
        }
    }

    public static void d(b left, b right) {
        if (left.getColumnDimension() != right.getRowDimension()) {
            throw new DimensionMismatchException(left.getColumnDimension(), right.getRowDimension());
        }
    }

    public static m l(m matrix) {
        return m(matrix, 0.0d);
    }

    public static m m(m matrix, double threshold) {
        f.a(matrix);
        if (!matrix.isSquare()) {
            throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
        } else if (matrix instanceof i) {
            return ((i) matrix).inverse(threshold);
        } else {
            return new k(matrix, threshold).b().a();
        }
    }
}
