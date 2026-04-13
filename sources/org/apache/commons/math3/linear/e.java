package org.apache.commons.math3.linear;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.util.d;
import org.apache.commons.math3.util.c;
import org.apache.commons.math3.util.f;

/* compiled from: BlockRealMatrix */
public class e extends a implements Serializable {
    public static final int BLOCK_SIZE = 52;
    private static final long serialVersionUID = 4991895511313664478L;
    private final int blockColumns;
    private final int blockRows;
    private final double[][] blocks;
    private final int columns;
    private final int rows;

    public e(int rows2, int columns2) {
        super(rows2, columns2);
        this.rows = rows2;
        this.columns = columns2;
        this.blockRows = ((rows2 + 52) - 1) / 52;
        this.blockColumns = ((columns2 + 52) - 1) / 52;
        this.blocks = createBlocksLayout(rows2, columns2);
    }

    public e(double[][] rawData) {
        this(rawData.length, rawData[0].length, toBlocksLayout(rawData), false);
    }

    public e(int rows2, int columns2, double[][] blockData, boolean copyArray) {
        super(rows2, columns2);
        this.rows = rows2;
        this.columns = columns2;
        int i = ((rows2 + 52) - 1) / 52;
        this.blockRows = i;
        int i2 = ((columns2 + 52) - 1) / 52;
        this.blockColumns = i2;
        if (copyArray) {
            this.blocks = new double[(i * i2)][];
        } else {
            this.blocks = blockData;
        }
        int index = 0;
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int iHeight = a(iBlock);
            int jBlock = 0;
            while (jBlock < this.blockColumns) {
                if (blockData[index].length == b(jBlock) * iHeight) {
                    if (copyArray) {
                        this.blocks[index] = (double[]) blockData[index].clone();
                    }
                    jBlock++;
                    index++;
                } else {
                    throw new DimensionMismatchException(blockData[index].length, b(jBlock) * iHeight);
                }
            }
        }
    }

    public static double[][] toBlocksLayout(double[][] rawData) {
        double[][] dArr = rawData;
        int rows2 = dArr.length;
        int columns2 = dArr[0].length;
        int blockRows2 = ((rows2 + 52) - 1) / 52;
        int blockColumns2 = ((columns2 + 52) - 1) / 52;
        int i = 0;
        while (i < dArr.length) {
            int length = dArr[i].length;
            if (length == columns2) {
                i++;
            } else {
                throw new DimensionMismatchException(columns2, length);
            }
        }
        double[][] blocks2 = new double[(blockRows2 * blockColumns2)][];
        int blockIndex = 0;
        for (int iBlock = 0; iBlock < blockRows2; iBlock++) {
            int pStart = iBlock * 52;
            int pEnd = c.q(pStart + 52, rows2);
            int iHeight = pEnd - pStart;
            int jBlock = 0;
            while (jBlock < blockColumns2) {
                int qStart = jBlock * 52;
                int jWidth = c.q(qStart + 52, columns2) - qStart;
                double[] block = new double[(iHeight * jWidth)];
                blocks2[blockIndex] = block;
                int rows3 = rows2;
                int index = 0;
                int columns3 = columns2;
                int p = pStart;
                while (p < pEnd) {
                    System.arraycopy(dArr[p], qStart, block, index, jWidth);
                    index += jWidth;
                    p++;
                    blockRows2 = blockRows2;
                }
                blockIndex++;
                jBlock++;
                columns2 = columns3;
                rows2 = rows3;
            }
            int i2 = columns2;
            int i3 = blockRows2;
        }
        return blocks2;
    }

    public static double[][] createBlocksLayout(int rows2, int columns2) {
        int blockRows2 = ((rows2 + 52) - 1) / 52;
        int blockColumns2 = ((columns2 + 52) - 1) / 52;
        double[][] blocks2 = new double[(blockRows2 * blockColumns2)][];
        int blockIndex = 0;
        for (int iBlock = 0; iBlock < blockRows2; iBlock++) {
            int pStart = iBlock * 52;
            int iHeight = c.q(pStart + 52, rows2) - pStart;
            for (int jBlock = 0; jBlock < blockColumns2; jBlock++) {
                int qStart = jBlock * 52;
                blocks2[blockIndex] = new double[(iHeight * (c.q(qStart + 52, columns2) - qStart))];
                blockIndex++;
            }
        }
        return blocks2;
    }

    public e createMatrix(int rowDimension, int columnDimension) {
        return new e(rowDimension, columnDimension);
    }

    public e copy() {
        e copied = new e(this.rows, this.columns);
        int i = 0;
        while (true) {
            double[][] dArr = this.blocks;
            if (i >= dArr.length) {
                return copied;
            }
            System.arraycopy(dArr[i], 0, copied.blocks[i], 0, dArr[i].length);
            i++;
        }
    }

    public e add(m m) {
        m mVar = m;
        try {
            return add((e) mVar);
        } catch (ClassCastException e) {
            j.a(this, m);
            e out = new e(this.rows, this.columns);
            int blockIndex = 0;
            for (int iBlock = 0; iBlock < out.blockRows; iBlock++) {
                for (int jBlock = 0; jBlock < out.blockColumns; jBlock++) {
                    double[] outBlock = out.blocks[blockIndex];
                    double[] tBlock = this.blocks[blockIndex];
                    int pStart = iBlock * 52;
                    int pEnd = c.q(pStart + 52, this.rows);
                    int qStart = jBlock * 52;
                    int qEnd = c.q(qStart + 52, this.columns);
                    int k = 0;
                    for (int p = pStart; p < pEnd; p++) {
                        for (int q = qStart; q < qEnd; q++) {
                            outBlock[k] = tBlock[k] + mVar.getEntry(p, q);
                            k++;
                        }
                    }
                    blockIndex++;
                }
            }
            return out;
        }
    }

    public e add(e m) {
        j.a(this, m);
        e out = new e(this.rows, this.columns);
        int blockIndex = 0;
        while (true) {
            double[][] dArr = out.blocks;
            if (blockIndex >= dArr.length) {
                return out;
            }
            double[] outBlock = dArr[blockIndex];
            double[] tBlock = this.blocks[blockIndex];
            double[] mBlock = m.blocks[blockIndex];
            for (int k = 0; k < outBlock.length; k++) {
                outBlock[k] = tBlock[k] + mBlock[k];
            }
            blockIndex++;
        }
    }

    public e subtract(m m) {
        m mVar = m;
        try {
            return subtract((e) mVar);
        } catch (ClassCastException e) {
            j.h(this, m);
            e out = new e(this.rows, this.columns);
            int blockIndex = 0;
            for (int iBlock = 0; iBlock < out.blockRows; iBlock++) {
                for (int jBlock = 0; jBlock < out.blockColumns; jBlock++) {
                    double[] outBlock = out.blocks[blockIndex];
                    double[] tBlock = this.blocks[blockIndex];
                    int pStart = iBlock * 52;
                    int pEnd = c.q(pStart + 52, this.rows);
                    int qStart = jBlock * 52;
                    int qEnd = c.q(qStart + 52, this.columns);
                    int k = 0;
                    for (int p = pStart; p < pEnd; p++) {
                        for (int q = qStart; q < qEnd; q++) {
                            outBlock[k] = tBlock[k] - mVar.getEntry(p, q);
                            k++;
                        }
                    }
                    blockIndex++;
                }
            }
            return out;
        }
    }

    public e subtract(e m) {
        j.h(this, m);
        e out = new e(this.rows, this.columns);
        int blockIndex = 0;
        while (true) {
            double[][] dArr = out.blocks;
            if (blockIndex >= dArr.length) {
                return out;
            }
            double[] outBlock = dArr[blockIndex];
            double[] tBlock = this.blocks[blockIndex];
            double[] mBlock = m.blocks[blockIndex];
            for (int k = 0; k < outBlock.length; k++) {
                outBlock[k] = tBlock[k] - mBlock[k];
            }
            blockIndex++;
        }
    }

    public e scalarAdd(double d) {
        e out = new e(this.rows, this.columns);
        int blockIndex = 0;
        while (true) {
            double[][] dArr = out.blocks;
            if (blockIndex >= dArr.length) {
                return out;
            }
            double[] outBlock = dArr[blockIndex];
            double[] tBlock = this.blocks[blockIndex];
            for (int k = 0; k < outBlock.length; k++) {
                outBlock[k] = tBlock[k] + d;
            }
            blockIndex++;
        }
    }

    public m scalarMultiply(double d) {
        e out = new e(this.rows, this.columns);
        int blockIndex = 0;
        while (true) {
            double[][] dArr = out.blocks;
            if (blockIndex >= dArr.length) {
                return out;
            }
            double[] outBlock = dArr[blockIndex];
            double[] tBlock = this.blocks[blockIndex];
            for (int k = 0; k < outBlock.length; k++) {
                outBlock[k] = tBlock[k] * d;
            }
            blockIndex++;
        }
    }

    public e multiply(m m) {
        e eVar = this;
        m mVar = m;
        try {
            return eVar.multiply((e) mVar);
        } catch (ClassCastException e) {
            cce = e;
            j.d(this, m);
            e out = new e(eVar.rows, m.getColumnDimension());
            int blockIndex = 0;
            int iBlock = 0;
            while (iBlock < out.blockRows) {
                int pStart = iBlock * 52;
                int pEnd = c.q(pStart + 52, eVar.rows);
                int jBlock = 0;
                while (jBlock < out.blockColumns) {
                    int qStart = jBlock * 52;
                    int qEnd = c.q(qStart + 52, m.getColumnDimension());
                    double[] outBlock = out.blocks[blockIndex];
                    int kBlock = 0;
                    while (kBlock < eVar.blockColumns) {
                        int kWidth = eVar.b(kBlock);
                        double[] tBlock = eVar.blocks[(eVar.blockColumns * iBlock) + kBlock];
                        int rStart = kBlock * 52;
                        int k = 0;
                        ClassCastException cce = cce;
                        int p = pStart;
                        while (p < pEnd) {
                            int lStart = (p - pStart) * kWidth;
                            int lEnd = lStart + kWidth;
                            int pStart2 = pStart;
                            int q = qStart;
                            while (q < qEnd) {
                                double sum = 0.0d;
                                int r = pEnd;
                                int r2 = rStart;
                                int qStart2 = qStart;
                                for (int l = lStart; l < lEnd; l++) {
                                    sum += tBlock[l] * mVar.getEntry(r2, q);
                                    r2++;
                                }
                                outBlock[k] = outBlock[k] + sum;
                                k++;
                                q++;
                                pEnd = r;
                                qStart = qStart2;
                            }
                            int i = qStart;
                            p++;
                            pStart = pStart2;
                        }
                        int i2 = pEnd;
                        int i3 = qStart;
                        kBlock++;
                        eVar = this;
                        cce = cce;
                    }
                    int i4 = pStart;
                    int i5 = pEnd;
                    int i6 = qStart;
                    blockIndex++;
                    jBlock++;
                    eVar = this;
                }
                int i7 = pStart;
                int i8 = pEnd;
                iBlock++;
                eVar = this;
            }
            return out;
        }
    }

    public e multiply(e m) {
        int iBlock;
        e eVar = this;
        e eVar2 = m;
        j.d(this, m);
        e out = new e(eVar.rows, eVar2.columns);
        int blockIndex = 0;
        int iBlock2 = 0;
        while (iBlock2 < out.blockRows) {
            int pStart = iBlock2 * 52;
            int pEnd = c.q(pStart + 52, eVar.rows);
            int jBlock = 0;
            while (jBlock < out.blockColumns) {
                int jWidth = out.b(jBlock);
                int jWidth2 = jWidth + jWidth;
                int jWidth3 = jWidth2 + jWidth;
                int jWidth4 = jWidth3 + jWidth;
                double[] outBlock = out.blocks[blockIndex];
                int kBlock = 0;
                while (kBlock < eVar.blockColumns) {
                    int kWidth = eVar.b(kBlock);
                    e out2 = out;
                    double[] tBlock = eVar.blocks[(eVar.blockColumns * iBlock2) + kBlock];
                    double[] mBlock = eVar2.blocks[(eVar2.blockColumns * kBlock) + jBlock];
                    int k = 0;
                    int p = pStart;
                    while (p < pEnd) {
                        int lStart = (p - pStart) * kWidth;
                        int pStart2 = pStart;
                        int lEnd = lStart + kWidth;
                        int pEnd2 = pEnd;
                        int nStart = 0;
                        while (nStart < jWidth) {
                            double sum = 0.0d;
                            int n = nStart;
                            int kWidth2 = kWidth;
                            int l = lStart;
                            while (true) {
                                iBlock = iBlock2;
                                if (l >= lEnd - 3) {
                                    break;
                                }
                                sum += (tBlock[l] * mBlock[n]) + (tBlock[l + 1] * mBlock[n + jWidth]) + (tBlock[l + 2] * mBlock[n + jWidth2]) + (tBlock[l + 3] * mBlock[n + jWidth3]);
                                l += 4;
                                n += jWidth4;
                                iBlock2 = iBlock;
                            }
                            while (l < lEnd) {
                                sum += tBlock[l] * mBlock[n];
                                n += jWidth;
                                l++;
                            }
                            outBlock[k] = outBlock[k] + sum;
                            k++;
                            nStart++;
                            kWidth = kWidth2;
                            iBlock2 = iBlock;
                        }
                        int i = kWidth;
                        p++;
                        pStart = pStart2;
                        pEnd = pEnd2;
                    }
                    int i2 = pStart;
                    int i3 = pEnd;
                    int i4 = kWidth;
                    kBlock++;
                    eVar = this;
                    eVar2 = m;
                    out = out2;
                }
                int i5 = iBlock2;
                int i6 = pStart;
                int i7 = pEnd;
                blockIndex++;
                jBlock++;
                eVar = this;
                eVar2 = m;
            }
            int i8 = pStart;
            int i9 = pEnd;
            iBlock2++;
            eVar = this;
            eVar2 = m;
        }
        return out;
    }

    public double[][] getData() {
        int rowDimension = getRowDimension();
        int[] iArr = new int[2];
        iArr[1] = getColumnDimension();
        iArr[0] = rowDimension;
        double[][] data = (double[][]) Array.newInstance(double.class, iArr);
        int lastColumns = this.columns - ((this.blockColumns - 1) * 52);
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int pStart = iBlock * 52;
            int pEnd = c.q(pStart + 52, this.rows);
            int regularPos = 0;
            int lastPos = 0;
            for (int p = pStart; p < pEnd; p++) {
                double[] dataP = data[p];
                int blockIndex = this.blockColumns * iBlock;
                int dataPos = 0;
                int jBlock = 0;
                while (jBlock < this.blockColumns - 1) {
                    System.arraycopy(this.blocks[blockIndex], regularPos, dataP, dataPos, 52);
                    dataPos += 52;
                    jBlock++;
                    blockIndex++;
                }
                System.arraycopy(this.blocks[blockIndex], lastPos, dataP, dataPos, lastColumns);
                regularPos += 52;
                lastPos += lastColumns;
            }
        }
        return data;
    }

    public double getNorm() {
        double[] colSums = new double[52];
        double maxColSum = 0.0d;
        for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
            int jWidth = b(jBlock);
            Arrays.fill(colSums, 0, jWidth, 0.0d);
            for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
                int iHeight = a(iBlock);
                double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                for (int j = 0; j < jWidth; j++) {
                    double sum = 0.0d;
                    for (int i = 0; i < iHeight; i++) {
                        sum += c.a(block[(i * jWidth) + j]);
                    }
                    colSums[j] = colSums[j] + sum;
                }
            }
            for (int j2 = 0; j2 < jWidth; j2++) {
                maxColSum = c.o(maxColSum, colSums[j2]);
            }
        }
        return maxColSum;
    }

    public double getFrobeniusNorm() {
        double sum2 = 0.0d;
        int blockIndex = 0;
        while (true) {
            double[][] dArr = this.blocks;
            if (blockIndex >= dArr.length) {
                return c.z(sum2);
            }
            for (double entry : dArr[blockIndex]) {
                sum2 += entry * entry;
            }
            blockIndex++;
        }
    }

    public e getSubMatrix(int startRow, int endRow, int startColumn, int endColumn) {
        int qBlock;
        int jBlock;
        int iBlock;
        j.f(this, startRow, endRow, startColumn, endColumn);
        e out = new e((endRow - startRow) + 1, (endColumn - startColumn) + 1);
        int rowsShift = startRow % 52;
        int blockStartColumn = startColumn / 52;
        int columnsShift = startColumn % 52;
        int pBlock = startRow / 52;
        int iBlock2 = 0;
        while (iBlock2 < out.blockRows) {
            int iHeight = out.a(iBlock2);
            int qBlock2 = blockStartColumn;
            int jBlock2 = 0;
            while (jBlock2 < out.blockColumns) {
                int jWidth = out.b(jBlock2);
                double[] outBlock = out.blocks[(out.blockColumns * iBlock2) + jBlock2];
                int index = (this.blockColumns * pBlock) + qBlock2;
                int width = b(qBlock2);
                int heightExcess = (iHeight + rowsShift) - 52;
                int widthExcess = (jWidth + columnsShift) - 52;
                if (heightExcess <= 0) {
                    jBlock = jBlock2;
                    qBlock = qBlock2;
                    iBlock = iBlock2;
                    if (widthExcess > 0) {
                        int width2 = b(qBlock + 1);
                        int i = rowsShift;
                        double[] dArr = outBlock;
                        int i2 = jWidth;
                        c(this.blocks[index], width, i, iHeight + rowsShift, columnsShift, 52, dArr, i2, 0, 0);
                        c(this.blocks[index + 1], width2, i, iHeight + rowsShift, 0, widthExcess, dArr, i2, 0, jWidth - widthExcess);
                    } else {
                        c(this.blocks[index], width, rowsShift, iHeight + rowsShift, columnsShift, jWidth + columnsShift, outBlock, jWidth, 0, 0);
                    }
                } else if (widthExcess > 0) {
                    int width22 = b(qBlock2 + 1);
                    int i3 = rowsShift;
                    double[] dArr2 = outBlock;
                    jBlock = jBlock2;
                    int jBlock3 = jWidth;
                    qBlock = qBlock2;
                    iBlock = iBlock2;
                    c(this.blocks[index], width, i3, 52, columnsShift, 52, dArr2, jBlock3, 0, 0);
                    c(this.blocks[index + 1], width22, i3, 52, 0, widthExcess, dArr2, jBlock3, 0, jWidth - widthExcess);
                    int i4 = heightExcess;
                    c(this.blocks[index + this.blockColumns], width, 0, i4, columnsShift, 52, dArr2, jBlock3, iHeight - heightExcess, 0);
                    c(this.blocks[index + this.blockColumns + 1], width22, 0, i4, 0, widthExcess, dArr2, jBlock3, iHeight - heightExcess, jWidth - widthExcess);
                } else {
                    jBlock = jBlock2;
                    qBlock = qBlock2;
                    iBlock = iBlock2;
                    int i5 = width;
                    int i6 = columnsShift;
                    double[] dArr3 = outBlock;
                    int i7 = jWidth;
                    c(this.blocks[index], i5, rowsShift, 52, i6, jWidth + columnsShift, dArr3, i7, 0, 0);
                    c(this.blocks[index + this.blockColumns], i5, 0, heightExcess, i6, jWidth + columnsShift, dArr3, i7, iHeight - heightExcess, 0);
                }
                qBlock2 = qBlock + 1;
                jBlock2 = jBlock + 1;
                iBlock2 = iBlock;
            }
            int i8 = jBlock2;
            int i9 = qBlock2;
            pBlock++;
            iBlock2++;
        }
        return out;
    }

    private void c(double[] srcBlock, int srcWidth, int srcStartRow, int srcEndRow, int srcStartColumn, int srcEndColumn, double[] dstBlock, int dstWidth, int dstStartRow, int dstStartColumn) {
        int length = srcEndColumn - srcStartColumn;
        int srcPos = (srcStartRow * srcWidth) + srcStartColumn;
        int dstPos = (dstStartRow * dstWidth) + dstStartColumn;
        for (int srcRow = srcStartRow; srcRow < srcEndRow; srcRow++) {
            System.arraycopy(srcBlock, srcPos, dstBlock, dstPos, length);
            srcPos += srcWidth;
            dstPos += dstWidth;
        }
    }

    public void setSubMatrix(double[][] subMatrix, int row, int column) {
        e eVar = this;
        double[][] dArr = subMatrix;
        int i = row;
        int i2 = column;
        f.a(subMatrix);
        int refLength = dArr[0].length;
        if (refLength != 0) {
            int endRow = (dArr.length + i) - 1;
            int endColumn = (i2 + refLength) - 1;
            j.f(eVar, i, endRow, i2, endColumn);
            double[][] arr$ = subMatrix;
            int len$ = arr$.length;
            int i$ = 0;
            while (i$ < len$) {
                double[] subRow = arr$[i$];
                if (subRow.length == refLength) {
                    i$++;
                } else {
                    throw new DimensionMismatchException(refLength, subRow.length);
                }
            }
            int blockStartRow = i / 52;
            int blockEndRow = (endRow + 52) / 52;
            int blockStartColumn = i2 / 52;
            int blockEndColumn = (endColumn + 52) / 52;
            int iBlock = blockStartRow;
            while (iBlock < blockEndRow) {
                int iHeight = eVar.a(iBlock);
                int firstRow = iBlock * 52;
                int iStart = c.p(i, firstRow);
                int refLength2 = refLength;
                int iEnd = c.q(endRow + 1, firstRow + iHeight);
                int jBlock = blockStartColumn;
                while (jBlock < blockEndColumn) {
                    int jWidth = eVar.b(jBlock);
                    int endRow2 = endRow;
                    int endRow3 = jBlock * 52;
                    int jStart = c.p(i2, endRow3);
                    int blockStartRow2 = blockStartRow;
                    int endColumn2 = endColumn;
                    int jEnd = c.q(endColumn + 1, endRow3 + jWidth);
                    int jLength = jEnd - jStart;
                    int i3 = jEnd;
                    int blockEndRow2 = blockEndRow;
                    double[] block = eVar.blocks[(eVar.blockColumns * iBlock) + jBlock];
                    int i4 = iStart;
                    while (i4 < iEnd) {
                        System.arraycopy(dArr[i4 - i], jStart - i2, block, ((i4 - firstRow) * jWidth) + (jStart - endRow3), jLength);
                        i4++;
                        dArr = subMatrix;
                        i = row;
                    }
                    jBlock++;
                    eVar = this;
                    dArr = subMatrix;
                    i = row;
                    endRow = endRow2;
                    blockStartRow = blockStartRow2;
                    endColumn = endColumn2;
                    blockEndRow = blockEndRow2;
                }
                int i5 = endColumn;
                int i6 = blockStartRow;
                int i7 = blockEndRow;
                iBlock++;
                eVar = this;
                dArr = subMatrix;
                i = row;
                refLength = refLength2;
            }
            return;
        }
        throw new NoDataException(d.AT_LEAST_ONE_COLUMN);
    }

    public e getRowMatrix(int row) {
        j.e(this, row);
        e out = new e(1, this.columns);
        int iBlock = row / 52;
        int iRow = row - (iBlock * 52);
        int outBlockIndex = 0;
        int outIndex = 0;
        double[] outBlock = out.blocks[0];
        for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
            int jWidth = b(jBlock);
            double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
            int available = outBlock.length - outIndex;
            if (jWidth > available) {
                System.arraycopy(block, iRow * jWidth, outBlock, outIndex, available);
                outBlockIndex++;
                outBlock = out.blocks[outBlockIndex];
                System.arraycopy(block, iRow * jWidth, outBlock, 0, jWidth - available);
                outIndex = jWidth - available;
            } else {
                System.arraycopy(block, iRow * jWidth, outBlock, outIndex, jWidth);
                outIndex += jWidth;
            }
        }
        return out;
    }

    public void setRowMatrix(int row, m matrix) {
        try {
            setRowMatrix(row, (e) matrix);
        } catch (ClassCastException e) {
            super.setRowMatrix(row, matrix);
        }
    }

    public void setRowMatrix(int row, e matrix) {
        j.e(this, row);
        int nCols = getColumnDimension();
        if (matrix.getRowDimension() == 1 && matrix.getColumnDimension() == nCols) {
            int iBlock = row / 52;
            int iRow = row - (iBlock * 52);
            int mBlockIndex = 0;
            int mIndex = 0;
            double[] mBlock = matrix.blocks[0];
            for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
                int jWidth = b(jBlock);
                double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                int available = mBlock.length - mIndex;
                if (jWidth > available) {
                    System.arraycopy(mBlock, mIndex, block, iRow * jWidth, available);
                    mBlockIndex++;
                    mBlock = matrix.blocks[mBlockIndex];
                    System.arraycopy(mBlock, 0, block, iRow * jWidth, jWidth - available);
                    mIndex = jWidth - available;
                } else {
                    System.arraycopy(mBlock, mIndex, block, iRow * jWidth, jWidth);
                    mIndex += jWidth;
                }
            }
            return;
        }
        throw new MatrixDimensionMismatchException(matrix.getRowDimension(), matrix.getColumnDimension(), 1, nCols);
    }

    public e getColumnMatrix(int column) {
        j.b(this, column);
        e out = new e(this.rows, 1);
        int jBlock = column / 52;
        int jColumn = column - (jBlock * 52);
        int jWidth = b(jBlock);
        int outBlockIndex = 0;
        int outIndex = 0;
        double[] outBlock = out.blocks[0];
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int iHeight = a(iBlock);
            double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
            int i = 0;
            while (i < iHeight) {
                if (outIndex >= outBlock.length) {
                    outBlockIndex++;
                    outBlock = out.blocks[outBlockIndex];
                    outIndex = 0;
                }
                outBlock[outIndex] = block[(i * jWidth) + jColumn];
                i++;
                outIndex++;
            }
        }
        return out;
    }

    public void setColumnMatrix(int column, m matrix) {
        try {
            setColumnMatrix(column, (e) matrix);
        } catch (ClassCastException e) {
            super.setColumnMatrix(column, matrix);
        }
    }

    /* access modifiers changed from: package-private */
    public void setColumnMatrix(int column, e matrix) {
        e eVar = matrix;
        j.b(this, column);
        int nRows = getRowDimension();
        if (matrix.getRowDimension() == nRows && matrix.getColumnDimension() == 1) {
            int jBlock = column / 52;
            int jColumn = column - (jBlock * 52);
            int jWidth = b(jBlock);
            int mBlockIndex = 0;
            int mIndex = 0;
            double[] mBlock = eVar.blocks[0];
            for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
                int iHeight = a(iBlock);
                double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                int i = 0;
                while (i < iHeight) {
                    if (mIndex >= mBlock.length) {
                        mBlockIndex++;
                        mBlock = eVar.blocks[mBlockIndex];
                        mIndex = 0;
                    }
                    block[(i * jWidth) + jColumn] = mBlock[mIndex];
                    i++;
                    mIndex++;
                }
            }
            return;
        }
        throw new MatrixDimensionMismatchException(matrix.getRowDimension(), matrix.getColumnDimension(), nRows, 1);
    }

    public q getRowVector(int row) {
        j.e(this, row);
        double[] outData = new double[this.columns];
        int iBlock = row / 52;
        int iRow = row - (iBlock * 52);
        int outIndex = 0;
        for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
            int jWidth = b(jBlock);
            System.arraycopy(this.blocks[(this.blockColumns * iBlock) + jBlock], iRow * jWidth, outData, outIndex, jWidth);
            outIndex += jWidth;
        }
        return new d(outData, false);
    }

    public void setRowVector(int row, q vector) {
        try {
            setRow(row, ((d) vector).getDataRef());
        } catch (ClassCastException e) {
            super.setRowVector(row, vector);
        }
    }

    public q getColumnVector(int column) {
        j.b(this, column);
        double[] outData = new double[this.rows];
        int jBlock = column / 52;
        int jColumn = column - (jBlock * 52);
        int jWidth = b(jBlock);
        int outIndex = 0;
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int iHeight = a(iBlock);
            double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
            int i = 0;
            while (i < iHeight) {
                outData[outIndex] = block[(i * jWidth) + jColumn];
                i++;
                outIndex++;
            }
        }
        return new d(outData, false);
    }

    public void setColumnVector(int column, q vector) {
        try {
            setColumn(column, ((d) vector).getDataRef());
        } catch (ClassCastException e) {
            super.setColumnVector(column, vector);
        }
    }

    public double[] getRow(int row) {
        j.e(this, row);
        double[] out = new double[this.columns];
        int iBlock = row / 52;
        int iRow = row - (iBlock * 52);
        int outIndex = 0;
        for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
            int jWidth = b(jBlock);
            System.arraycopy(this.blocks[(this.blockColumns * iBlock) + jBlock], iRow * jWidth, out, outIndex, jWidth);
            outIndex += jWidth;
        }
        return out;
    }

    public void setRow(int row, double[] array) {
        j.e(this, row);
        int nCols = getColumnDimension();
        if (array.length == nCols) {
            int iBlock = row / 52;
            int iRow = row - (iBlock * 52);
            int outIndex = 0;
            for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
                int jWidth = b(jBlock);
                System.arraycopy(array, outIndex, this.blocks[(this.blockColumns * iBlock) + jBlock], iRow * jWidth, jWidth);
                outIndex += jWidth;
            }
            return;
        }
        throw new MatrixDimensionMismatchException(1, array.length, 1, nCols);
    }

    public double[] getColumn(int column) {
        j.b(this, column);
        double[] out = new double[this.rows];
        int jBlock = column / 52;
        int jColumn = column - (jBlock * 52);
        int jWidth = b(jBlock);
        int outIndex = 0;
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int iHeight = a(iBlock);
            double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
            int i = 0;
            while (i < iHeight) {
                out[outIndex] = block[(i * jWidth) + jColumn];
                i++;
                outIndex++;
            }
        }
        return out;
    }

    public void setColumn(int column, double[] array) {
        j.b(this, column);
        int nRows = getRowDimension();
        if (array.length == nRows) {
            int jBlock = column / 52;
            int jColumn = column - (jBlock * 52);
            int jWidth = b(jBlock);
            int outIndex = 0;
            for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
                int iHeight = a(iBlock);
                double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                int i = 0;
                while (i < iHeight) {
                    block[(i * jWidth) + jColumn] = array[outIndex];
                    i++;
                    outIndex++;
                }
            }
            return;
        }
        throw new MatrixDimensionMismatchException(array.length, 1, nRows, 1);
    }

    public double getEntry(int row, int column) {
        j.c(this, row, column);
        int iBlock = row / 52;
        int jBlock = column / 52;
        return this.blocks[(this.blockColumns * iBlock) + jBlock][((row - (iBlock * 52)) * b(jBlock)) + (column - (jBlock * 52))];
    }

    public void setEntry(int row, int column, double value) {
        j.c(this, row, column);
        int iBlock = row / 52;
        int jBlock = column / 52;
        this.blocks[(this.blockColumns * iBlock) + jBlock][((row - (iBlock * 52)) * b(jBlock)) + (column - (jBlock * 52))] = value;
    }

    public void addToEntry(int row, int column, double increment) {
        j.c(this, row, column);
        int iBlock = row / 52;
        int jBlock = column / 52;
        int k = ((row - (iBlock * 52)) * b(jBlock)) + (column - (jBlock * 52));
        double[] dArr = this.blocks[(this.blockColumns * iBlock) + jBlock];
        dArr[k] = dArr[k] + increment;
    }

    public void multiplyEntry(int row, int column, double factor) {
        j.c(this, row, column);
        int iBlock = row / 52;
        int jBlock = column / 52;
        int k = ((row - (iBlock * 52)) * b(jBlock)) + (column - (jBlock * 52));
        double[] dArr = this.blocks[(this.blockColumns * iBlock) + jBlock];
        dArr[k] = dArr[k] * factor;
    }

    public e transpose() {
        e eVar = this;
        e out = new e(getColumnDimension(), getRowDimension());
        int blockIndex = 0;
        int iBlock = 0;
        while (iBlock < eVar.blockColumns) {
            int jBlock = 0;
            while (jBlock < eVar.blockRows) {
                double[] outBlock = out.blocks[blockIndex];
                double[] tBlock = eVar.blocks[(eVar.blockColumns * jBlock) + iBlock];
                int pStart = iBlock * 52;
                int pEnd = c.q(pStart + 52, eVar.columns);
                int qStart = jBlock * 52;
                int qEnd = c.q(qStart + 52, eVar.rows);
                int k = 0;
                int p = pStart;
                while (p < pEnd) {
                    int lInc = pEnd - pStart;
                    int l = p - pStart;
                    for (int q = qStart; q < qEnd; q++) {
                        outBlock[k] = tBlock[l];
                        k++;
                        l += lInc;
                    }
                    p++;
                }
                blockIndex++;
                jBlock++;
                eVar = this;
            }
            iBlock++;
            eVar = this;
        }
        return out;
    }

    public int getRowDimension() {
        return this.rows;
    }

    public int getColumnDimension() {
        return this.columns;
    }

    public double[] operate(double[] v) {
        double[] dArr = v;
        if (dArr.length == this.columns) {
            double[] out = new double[this.rows];
            for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
                int pStart = iBlock * 52;
                int pEnd = c.q(pStart + 52, this.rows);
                int jBlock = 0;
                while (true) {
                    int i = this.blockColumns;
                    if (jBlock >= i) {
                        break;
                    }
                    double[] block = this.blocks[(i * iBlock) + jBlock];
                    int qStart = jBlock * 52;
                    int qEnd = c.q(qStart + 52, this.columns);
                    int k = 0;
                    for (int p = pStart; p < pEnd; p++) {
                        double sum = 0.0d;
                        int q = qStart;
                        while (q < qEnd - 3) {
                            sum += (block[k] * dArr[q]) + (block[k + 1] * dArr[q + 1]) + (block[k + 2] * dArr[q + 2]) + (block[k + 3] * dArr[q + 3]);
                            k += 4;
                            q += 4;
                        }
                        while (q < qEnd) {
                            sum += block[k] * dArr[q];
                            q++;
                            k++;
                        }
                        out[p] = out[p] + sum;
                    }
                    jBlock++;
                }
            }
            return out;
        }
        throw new DimensionMismatchException(dArr.length, this.columns);
    }

    public double[] preMultiply(double[] v) {
        int qEnd;
        double[] dArr = v;
        if (dArr.length == this.rows) {
            double[] out = new double[this.columns];
            for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
                int jWidth = b(jBlock);
                int jWidth2 = jWidth + jWidth;
                int jWidth3 = jWidth2 + jWidth;
                int jWidth4 = jWidth3 + jWidth;
                int qStart = jBlock * 52;
                int qEnd2 = c.q(qStart + 52, this.columns);
                for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
                    double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                    int pStart = iBlock * 52;
                    int pEnd = c.q(pStart + 52, this.rows);
                    int q = qStart;
                    while (q < qEnd2) {
                        int k = q - qStart;
                        double sum = 0.0d;
                        int qStart2 = qStart;
                        int p = pStart;
                        while (true) {
                            qEnd = qEnd2;
                            if (p >= pEnd - 3) {
                                break;
                            }
                            sum += (block[k] * dArr[p]) + (block[k + jWidth] * dArr[p + 1]) + (block[k + jWidth2] * dArr[p + 2]) + (block[k + jWidth3] * dArr[p + 3]);
                            k += jWidth4;
                            p += 4;
                            qEnd2 = qEnd;
                        }
                        while (p < pEnd) {
                            sum += block[k] * dArr[p];
                            k += jWidth;
                            p++;
                        }
                        out[q] = out[q] + sum;
                        q++;
                        qStart = qStart2;
                        qEnd2 = qEnd;
                    }
                    int i = qEnd2;
                }
                int i2 = qEnd2;
            }
            return out;
        }
        throw new DimensionMismatchException(dArr.length, this.rows);
    }

    public double walkInRowOrder(n visitor) {
        int i = this.rows;
        int i2 = this.columns;
        visitor.b(i, i2, 0, i - 1, 0, i2 - 1);
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int pStart = iBlock * 52;
            int pEnd = c.q(pStart + 52, this.rows);
            for (int p = pStart; p < pEnd; p++) {
                for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
                    int jWidth = b(jBlock);
                    int qStart = jBlock * 52;
                    int qEnd = c.q(qStart + 52, this.columns);
                    double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                    int k = (p - pStart) * jWidth;
                    for (int q = qStart; q < qEnd; q++) {
                        block[k] = visitor.a(p, q, block[k]);
                        k++;
                    }
                }
            }
        }
        return visitor.end();
    }

    public double walkInRowOrder(p visitor) {
        int i = this.rows;
        int i2 = this.columns;
        visitor.b(i, i2, 0, i - 1, 0, i2 - 1);
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int pStart = iBlock * 52;
            int pEnd = c.q(pStart + 52, this.rows);
            for (int p = pStart; p < pEnd; p++) {
                for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
                    int jWidth = b(jBlock);
                    int qStart = jBlock * 52;
                    int qEnd = c.q(qStart + 52, this.columns);
                    double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                    int k = (p - pStart) * jWidth;
                    for (int q = qStart; q < qEnd; q++) {
                        visitor.a(p, q, block[k]);
                        k++;
                    }
                }
            }
        }
        return visitor.end();
    }

    public double walkInRowOrder(n visitor, int startRow, int endRow, int startColumn, int endColumn) {
        e eVar = this;
        int i = startRow;
        int i2 = endRow;
        int i3 = startColumn;
        int i4 = endColumn;
        j.f(eVar, i, i2, i3, i4);
        visitor.b(eVar.rows, eVar.columns, startRow, endRow, startColumn, endColumn);
        int iBlock = i / 52;
        while (iBlock < (i2 / 52) + 1) {
            int p0 = iBlock * 52;
            int pStart = c.p(i, p0);
            int pEnd = c.q((iBlock + 1) * 52, i2 + 1);
            int p = pStart;
            while (p < pEnd) {
                int jBlock = i3 / 52;
                while (jBlock < (i4 / 52) + 1) {
                    int jWidth = eVar.b(jBlock);
                    int q0 = jBlock * 52;
                    int qStart = c.p(i3, q0);
                    int qEnd = c.q((jBlock + 1) * 52, i4 + 1);
                    int pStart2 = pStart;
                    double[] block = eVar.blocks[(eVar.blockColumns * iBlock) + jBlock];
                    int k = (((p - p0) * jWidth) + qStart) - q0;
                    int q = qStart;
                    while (q < qEnd) {
                        int p02 = p0;
                        block[k] = visitor.a(p, q, block[k]);
                        k++;
                        q++;
                        int i5 = startRow;
                        jWidth = jWidth;
                        p0 = p02;
                    }
                    int p03 = p0;
                    int i6 = jWidth;
                    n nVar = visitor;
                    jBlock++;
                    eVar = this;
                    int i7 = startRow;
                    pStart = pStart2;
                    p0 = p03;
                }
                int p04 = p0;
                int i8 = pStart;
                n nVar2 = visitor;
                p++;
                eVar = this;
                int i9 = startRow;
                p0 = p04;
            }
            int i10 = pStart;
            n nVar3 = visitor;
            iBlock++;
            eVar = this;
            i = startRow;
        }
        n nVar4 = visitor;
        return visitor.end();
    }

    public double walkInRowOrder(p visitor, int startRow, int endRow, int startColumn, int endColumn) {
        e eVar = this;
        int i = startRow;
        int i2 = endRow;
        int i3 = startColumn;
        int i4 = endColumn;
        j.f(eVar, i, i2, i3, i4);
        visitor.b(eVar.rows, eVar.columns, startRow, endRow, startColumn, endColumn);
        int iBlock = i / 52;
        while (iBlock < (i2 / 52) + 1) {
            int p0 = iBlock * 52;
            int pStart = c.p(i, p0);
            int pEnd = c.q((iBlock + 1) * 52, i2 + 1);
            int p = pStart;
            while (p < pEnd) {
                int jBlock = i3 / 52;
                while (jBlock < (i4 / 52) + 1) {
                    int jWidth = eVar.b(jBlock);
                    int q0 = jBlock * 52;
                    int qStart = c.p(i3, q0);
                    int qEnd = c.q((jBlock + 1) * 52, i4 + 1);
                    int pStart2 = pStart;
                    double[] block = eVar.blocks[(eVar.blockColumns * iBlock) + jBlock];
                    int k = (((p - p0) * jWidth) + qStart) - q0;
                    int q = qStart;
                    while (q < qEnd) {
                        int p02 = p0;
                        visitor.a(p, q, block[k]);
                        k++;
                        q++;
                        int i5 = startRow;
                        jWidth = jWidth;
                        p0 = p02;
                    }
                    int p03 = p0;
                    int i6 = jWidth;
                    p pVar = visitor;
                    jBlock++;
                    eVar = this;
                    int i7 = startRow;
                    pStart = pStart2;
                    p0 = p03;
                }
                int p04 = p0;
                int i8 = pStart;
                p pVar2 = visitor;
                p++;
                eVar = this;
                int i9 = startRow;
                p0 = p04;
            }
            int i10 = pStart;
            p pVar3 = visitor;
            iBlock++;
            eVar = this;
            i = startRow;
        }
        p pVar4 = visitor;
        return visitor.end();
    }

    public double walkInOptimizedOrder(n visitor) {
        int i = this.rows;
        int i2 = this.columns;
        visitor.b(i, i2, 0, i - 1, 0, i2 - 1);
        int blockIndex = 0;
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int pStart = iBlock * 52;
            int pEnd = c.q(pStart + 52, this.rows);
            for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
                int qStart = jBlock * 52;
                int qEnd = c.q(qStart + 52, this.columns);
                double[] block = this.blocks[blockIndex];
                int k = 0;
                for (int p = pStart; p < pEnd; p++) {
                    for (int q = qStart; q < qEnd; q++) {
                        block[k] = visitor.a(p, q, block[k]);
                        k++;
                    }
                }
                blockIndex++;
            }
        }
        return visitor.end();
    }

    public double walkInOptimizedOrder(p visitor) {
        int i = this.rows;
        int i2 = this.columns;
        visitor.b(i, i2, 0, i - 1, 0, i2 - 1);
        int blockIndex = 0;
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int pStart = iBlock * 52;
            int pEnd = c.q(pStart + 52, this.rows);
            for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
                int qStart = jBlock * 52;
                int qEnd = c.q(qStart + 52, this.columns);
                double[] block = this.blocks[blockIndex];
                int k = 0;
                for (int p = pStart; p < pEnd; p++) {
                    for (int q = qStart; q < qEnd; q++) {
                        visitor.a(p, q, block[k]);
                        k++;
                    }
                }
                blockIndex++;
            }
        }
        return visitor.end();
    }

    public double walkInOptimizedOrder(n visitor, int startRow, int endRow, int startColumn, int endColumn) {
        e eVar = this;
        int i = startRow;
        int i2 = endRow;
        int i3 = startColumn;
        int i4 = endColumn;
        j.f(eVar, i, i2, i3, i4);
        visitor.b(eVar.rows, eVar.columns, startRow, endRow, startColumn, endColumn);
        int iBlock = i / 52;
        while (iBlock < (i2 / 52) + 1) {
            int p0 = iBlock * 52;
            int pStart = c.p(i, p0);
            int pEnd = c.q((iBlock + 1) * 52, i2 + 1);
            int jBlock = i3 / 52;
            while (jBlock < (i4 / 52) + 1) {
                int jWidth = eVar.b(jBlock);
                int q0 = jBlock * 52;
                int qStart = c.p(i3, q0);
                int qEnd = c.q((jBlock + 1) * 52, i4 + 1);
                double[] block = eVar.blocks[(eVar.blockColumns * iBlock) + jBlock];
                int p = pStart;
                while (p < pEnd) {
                    int k = (((p - p0) * jWidth) + qStart) - q0;
                    int q = qStart;
                    while (q < qEnd) {
                        int pEnd2 = pEnd;
                        block[k] = visitor.a(p, q, block[k]);
                        k++;
                        q++;
                        p0 = p0;
                        pStart = pStart;
                        pEnd = pEnd2;
                    }
                    int i5 = pStart;
                    int pEnd3 = pEnd;
                    n nVar = visitor;
                    p++;
                    pEnd = pEnd3;
                }
                int i6 = pStart;
                int pEnd4 = pEnd;
                n nVar2 = visitor;
                jBlock++;
                eVar = this;
                pEnd = pEnd4;
            }
            int i7 = pStart;
            int i8 = pEnd;
            n nVar3 = visitor;
            iBlock++;
            eVar = this;
        }
        n nVar4 = visitor;
        return visitor.end();
    }

    public double walkInOptimizedOrder(p visitor, int startRow, int endRow, int startColumn, int endColumn) {
        e eVar = this;
        int i = startRow;
        int i2 = endRow;
        int i3 = startColumn;
        int i4 = endColumn;
        j.f(eVar, i, i2, i3, i4);
        visitor.b(eVar.rows, eVar.columns, startRow, endRow, startColumn, endColumn);
        int iBlock = i / 52;
        while (iBlock < (i2 / 52) + 1) {
            int p0 = iBlock * 52;
            int pStart = c.p(i, p0);
            int pEnd = c.q((iBlock + 1) * 52, i2 + 1);
            int jBlock = i3 / 52;
            while (jBlock < (i4 / 52) + 1) {
                int jWidth = eVar.b(jBlock);
                int q0 = jBlock * 52;
                int qStart = c.p(i3, q0);
                int qEnd = c.q((jBlock + 1) * 52, i4 + 1);
                double[] block = eVar.blocks[(eVar.blockColumns * iBlock) + jBlock];
                int p = pStart;
                while (p < pEnd) {
                    int k = (((p - p0) * jWidth) + qStart) - q0;
                    int q = qStart;
                    while (q < qEnd) {
                        int pEnd2 = pEnd;
                        visitor.a(p, q, block[k]);
                        k++;
                        q++;
                        p0 = p0;
                        pStart = pStart;
                        pEnd = pEnd2;
                    }
                    int i5 = pStart;
                    int pEnd3 = pEnd;
                    p pVar = visitor;
                    p++;
                    pEnd = pEnd3;
                }
                int i6 = pStart;
                int pEnd4 = pEnd;
                p pVar2 = visitor;
                jBlock++;
                eVar = this;
                pEnd = pEnd4;
            }
            int i7 = pStart;
            int i8 = pEnd;
            p pVar3 = visitor;
            iBlock++;
            eVar = this;
        }
        p pVar4 = visitor;
        return visitor.end();
    }

    private int a(int blockRow) {
        if (blockRow == this.blockRows - 1) {
            return this.rows - (blockRow * 52);
        }
        return 52;
    }

    private int b(int blockColumn) {
        if (blockColumn == this.blockColumns - 1) {
            return this.columns - (blockColumn * 52);
        }
        return 52;
    }
}
