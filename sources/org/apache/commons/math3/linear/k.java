package org.apache.commons.math3.linear;

import java.lang.reflect.Array;
import java.util.Arrays;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.util.c;

/* compiled from: QRDecomposition */
public class k {
    private double[][] a;
    private double[] b;
    private m c = null;
    private m d = null;
    private m e = null;
    private m f = null;
    private final double g;

    public k(m matrix, double threshold) {
        this.g = threshold;
        int m = matrix.getRowDimension();
        int n = matrix.getColumnDimension();
        this.a = matrix.transpose().getData();
        this.b = new double[c.q(m, n)];
        a(this.a);
    }

    /* access modifiers changed from: protected */
    public void a(double[][] matrix) {
        for (int minor = 0; minor < c.q(matrix.length, matrix[0].length); minor++) {
            c(minor, matrix);
        }
    }

    /* access modifiers changed from: protected */
    public void c(int minor, double[][] matrix) {
        double[][] dArr = matrix;
        double[] qrtMinor = dArr[minor];
        double xNormSqr = 0.0d;
        for (int row = minor; row < qrtMinor.length; row++) {
            double c2 = qrtMinor[row];
            xNormSqr += c2 * c2;
        }
        double a2 = qrtMinor[minor] > 0.0d ? -c.z(xNormSqr) : c.z(xNormSqr);
        this.b[minor] = a2;
        if (a2 != 0.0d) {
            qrtMinor[minor] = qrtMinor[minor] - a2;
            for (int col = minor + 1; col < dArr.length; col++) {
                double[] qrtCol = dArr[col];
                double alpha = 0.0d;
                for (int row2 = minor; row2 < qrtCol.length; row2++) {
                    alpha -= qrtCol[row2] * qrtMinor[row2];
                }
                double alpha2 = alpha / (qrtMinor[minor] * a2);
                for (int row3 = minor; row3 < qrtCol.length; row3++) {
                    qrtCol[row3] = qrtCol[row3] - (qrtMinor[row3] * alpha2);
                }
            }
        }
    }

    public f b() {
        return new b(this.a, this.b, this.g);
    }

    /* compiled from: QRDecomposition */
    public static class b implements f {
        private final double[][] a;
        private final double[] b;
        private final double c;

        private b(double[][] qrt, double[] rDiag, double threshold) {
            this.a = qrt;
            this.b = rDiag;
            this.c = threshold;
        }

        public boolean b() {
            for (double diag : this.b) {
                if (c.a(diag) <= this.c) {
                    return false;
                }
            }
            return true;
        }

        public m c(m b2) {
            double d;
            b bVar = this;
            double[][] dArr = bVar.a;
            int n = dArr.length;
            int i = 0;
            int m = dArr[0].length;
            if (b2.getRowDimension() != m) {
                throw new DimensionMismatchException(b2.getRowDimension(), m);
            } else if (b()) {
                int columns = b2.getColumnDimension();
                int blockSize = 52;
                int cBlocks = ((columns + 52) - 1) / 52;
                double[][] xBlocks = e.createBlocksLayout(n, columns);
                int rowDimension = b2.getRowDimension();
                int[] iArr = new int[2];
                iArr[1] = 52;
                iArr[0] = rowDimension;
                double[][] y = (double[][]) Array.newInstance(double.class, iArr);
                double[] alpha = new double[52];
                int columns2 = 0;
                while (columns2 < cBlocks) {
                    int kStart = columns2 * 52;
                    int kEnd = c.q(kStart + 52, columns);
                    int kWidth = kEnd - kStart;
                    int kBlock = columns2;
                    b2.copySubMatrix(0, m - 1, kStart, kEnd - 1, y);
                    int minor = 0;
                    while (true) {
                        d = 1.0d;
                        if (minor >= c.q(m, n)) {
                            break;
                        }
                        double[] qrtMinor = bVar.a[minor];
                        double factor = 1.0d / (bVar.b[minor] * qrtMinor[minor]);
                        int columns3 = columns;
                        int blockSize2 = blockSize;
                        Arrays.fill(alpha, i, kWidth, 0.0d);
                        for (int row = minor; row < m; row++) {
                            double d2 = qrtMinor[row];
                            double[] yRow = y[row];
                            for (int k = 0; k < kWidth; k++) {
                                alpha[k] = alpha[k] + (yRow[k] * d2);
                            }
                        }
                        for (int k2 = 0; k2 < kWidth; k2++) {
                            alpha[k2] = alpha[k2] * factor;
                        }
                        int row2 = minor;
                        while (row2 < m) {
                            double d3 = qrtMinor[row2];
                            double[] yRow2 = y[row2];
                            double[] qrtMinor2 = qrtMinor;
                            for (int k3 = 0; k3 < kWidth; k3++) {
                                yRow2[k3] = yRow2[k3] + (alpha[k3] * d3);
                            }
                            row2++;
                            qrtMinor = qrtMinor2;
                        }
                        minor++;
                        columns = columns3;
                        blockSize = blockSize2;
                        i = 0;
                    }
                    int columns4 = columns;
                    int blockSize3 = blockSize;
                    int j = bVar.b.length - 1;
                    while (j >= 0) {
                        int jBlock = j / 52;
                        double factor2 = d / bVar.b[j];
                        double[] yJ = y[j];
                        double[] xBlock = xBlocks[(jBlock * cBlocks) + kBlock];
                        int index = (j - (jBlock * 52)) * kWidth;
                        int k4 = 0;
                        while (k4 < kWidth) {
                            yJ[k4] = yJ[k4] * factor2;
                            xBlock[index] = yJ[k4];
                            k4++;
                            index++;
                        }
                        double[] qrtJ = bVar.a[j];
                        int i2 = 0;
                        while (i2 < j) {
                            double rIJ = qrtJ[i2];
                            double[] yI = y[i2];
                            for (int k5 = 0; k5 < kWidth; k5++) {
                                yI[k5] = yI[k5] - (yJ[k5] * rIJ);
                            }
                            i2++;
                        }
                        j--;
                        d = 1.0d;
                        bVar = this;
                    }
                    columns = columns4;
                    blockSize = blockSize3;
                    i = 0;
                    columns2 = kBlock + 1;
                    bVar = this;
                }
                int i3 = columns2;
                return new e(n, columns, xBlocks, false);
            } else {
                throw new SingularMatrixException();
            }
        }

        public m a() {
            return c(j.i(this.a[0].length));
        }
    }
}
