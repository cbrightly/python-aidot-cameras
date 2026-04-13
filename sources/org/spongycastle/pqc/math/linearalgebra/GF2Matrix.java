package org.spongycastle.pqc.math.linearalgebra;

import com.meituan.robust.Constants;
import java.lang.reflect.Array;
import java.security.SecureRandom;

public class GF2Matrix extends Matrix {
    private int[][] c;
    private int d;

    public GF2Matrix(byte[] enc) {
        if (enc.length >= 9) {
            this.a = LittleEndianConversions.e(enc, 0);
            int e = LittleEndianConversions.e(enc, 4);
            this.b = e;
            int i = this.a;
            int n = ((e + 7) >>> 3) * i;
            if (i <= 0 || n != enc.length - 8) {
                throw new ArithmeticException("given array is not an encoded matrix over GF(2)");
            }
            int i2 = (e + 31) >>> 5;
            this.d = i2;
            int[] iArr = new int[2];
            iArr[1] = i2;
            iArr[0] = i;
            this.c = (int[][]) Array.newInstance(int.class, iArr);
            int i3 = this.b;
            int q = i3 >> 5;
            int r = i3 & 31;
            int count = 8;
            for (int i4 = 0; i4 < this.a; i4++) {
                int j = 0;
                while (j < q) {
                    this.c[i4][j] = LittleEndianConversions.e(enc, count);
                    j++;
                    count += 4;
                }
                int j2 = 0;
                while (j2 < r) {
                    int[] iArr2 = this.c[i4];
                    iArr2[q] = ((enc[count] & 255) << j2) ^ iArr2[q];
                    j2 += 8;
                    count++;
                }
            }
            return;
        }
        throw new ArithmeticException("given array is not an encoded matrix over GF(2)");
    }

    public GF2Matrix(int numColumns, int[][] matrix) {
        int bitMask;
        if (matrix[0].length == ((numColumns + 31) >> 5)) {
            this.b = numColumns;
            this.a = matrix.length;
            this.d = matrix[0].length;
            int rest = numColumns & 31;
            if (rest == 0) {
                bitMask = -1;
            } else {
                bitMask = (1 << rest) - 1;
            }
            for (int i = 0; i < this.a; i++) {
                int[] iArr = matrix[i];
                int i2 = this.d - 1;
                iArr[i2] = iArr[i2] & bitMask;
            }
            this.c = matrix;
            return;
        }
        throw new ArithmeticException("Int array does not match given number of columns.");
    }

    public GF2Matrix(int n, char typeOfMatrix) {
        this(n, typeOfMatrix, new SecureRandom());
    }

    public GF2Matrix(int n, char typeOfMatrix, SecureRandom sr) {
        if (n > 0) {
            switch (typeOfMatrix) {
                case 'I':
                    g(n);
                    return;
                case 'L':
                    d(n, sr);
                    return;
                case 'R':
                    e(n, sr);
                    return;
                case 'U':
                    f(n, sr);
                    return;
                case 'Z':
                    h(n, n);
                    return;
                default:
                    throw new ArithmeticException("Unknown matrix type.");
            }
        } else {
            throw new ArithmeticException("Size of matrix is non-positive.");
        }
    }

    public GF2Matrix(GF2Matrix a) {
        this.b = a.a();
        this.a = a.b();
        this.d = a.d;
        this.c = new int[a.c.length][];
        int i = 0;
        while (true) {
            int[][] iArr = this.c;
            if (i < iArr.length) {
                iArr[i] = IntUtils.a(a.c[i]);
                i++;
            } else {
                return;
            }
        }
    }

    private GF2Matrix(int m, int n) {
        if (n <= 0 || m <= 0) {
            throw new ArithmeticException("size of matrix is non-positive");
        }
        h(m, n);
    }

    private void h(int m, int n) {
        this.a = m;
        this.b = n;
        int i = (n + 31) >>> 5;
        this.d = i;
        int[] iArr = new int[2];
        iArr[1] = i;
        iArr[0] = m;
        this.c = (int[][]) Array.newInstance(int.class, iArr);
        for (int i2 = 0; i2 < this.a; i2++) {
            for (int j = 0; j < this.d; j++) {
                this.c[i2][j] = 0;
            }
        }
    }

    private void g(int n) {
        this.a = n;
        this.b = n;
        int i = (n + 31) >>> 5;
        this.d = i;
        int[] iArr = new int[2];
        iArr[1] = i;
        iArr[0] = n;
        this.c = (int[][]) Array.newInstance(int.class, iArr);
        for (int i2 = 0; i2 < this.a; i2++) {
            for (int j = 0; j < this.d; j++) {
                this.c[i2][j] = 0;
            }
        }
        for (int i3 = 0; i3 < this.a; i3++) {
            this.c[i3][i3 >>> 5] = 1 << (i3 & 31);
        }
    }

    private void d(int n, SecureRandom sr) {
        this.a = n;
        this.b = n;
        int i = (n + 31) >>> 5;
        this.d = i;
        int[] iArr = new int[2];
        iArr[1] = i;
        iArr[0] = n;
        this.c = (int[][]) Array.newInstance(int.class, iArr);
        for (int i2 = 0; i2 < this.a; i2++) {
            int q = i2 >>> 5;
            int r = i2 & 31;
            int s = 31 - r;
            int r2 = 1 << r;
            for (int j = 0; j < q; j++) {
                this.c[i2][j] = sr.nextInt();
            }
            this.c[i2][q] = (sr.nextInt() >>> s) | r2;
            for (int j2 = q + 1; j2 < this.d; j2++) {
                this.c[i2][j2] = 0;
            }
        }
    }

    private void f(int n, SecureRandom sr) {
        int help;
        int i;
        this.a = n;
        this.b = n;
        int i2 = (n + 31) >>> 5;
        this.d = i2;
        int[] iArr = new int[2];
        iArr[1] = i2;
        iArr[0] = n;
        this.c = (int[][]) Array.newInstance(int.class, iArr);
        int rest = n & 31;
        if (rest == 0) {
            help = -1;
        } else {
            help = (1 << rest) - 1;
        }
        for (int i3 = 0; i3 < this.a; i3++) {
            int q = i3 >>> 5;
            int r = i3 & 31;
            int s = r;
            int r2 = 1 << r;
            for (int j = 0; j < q; j++) {
                this.c[i3][j] = 0;
            }
            this.c[i3][q] = (sr.nextInt() << s) | r2;
            int j2 = q + 1;
            while (true) {
                i = this.d;
                if (j2 >= i) {
                    break;
                }
                this.c[i3][j2] = sr.nextInt();
                j2++;
            }
            int[] iArr2 = this.c[i3];
            int i4 = i - 1;
            iArr2[i4] = iArr2[i4] & help;
        }
    }

    private void e(int n, SecureRandom sr) {
        this.a = n;
        this.b = n;
        int i = (n + 31) >>> 5;
        this.d = i;
        int[] iArr = new int[2];
        iArr[1] = i;
        iArr[0] = n;
        this.c = (int[][]) Array.newInstance(int.class, iArr);
        GF2Matrix rm = (GF2Matrix) new GF2Matrix(n, Constants.OBJECT_TYPE, sr).r(new GF2Matrix(n, 'U', sr));
        int[] p = new Permutation(n, sr).c();
        for (int i2 = 0; i2 < n; i2++) {
            System.arraycopy(rm.c[i2], 0, this.c[p[i2]], 0, this.d);
        }
    }

    public static GF2Matrix[] k(int n, SecureRandom sr) {
        int i = n;
        SecureRandom secureRandom = sr;
        GF2Matrix[] result = new GF2Matrix[2];
        int length = (i + 31) >> 5;
        GF2Matrix lm = new GF2Matrix(i, Constants.OBJECT_TYPE, secureRandom);
        GF2Matrix um = new GF2Matrix(i, 'U', secureRandom);
        GF2Matrix rm = (GF2Matrix) lm.r(um);
        Permutation p = new Permutation(i, secureRandom);
        int[] pVec = p.c();
        int[] iArr = new int[2];
        int i2 = 1;
        iArr[1] = length;
        iArr[0] = i;
        int[][] matrix = (int[][]) Array.newInstance(int.class, iArr);
        for (int i3 = 0; i3 < i; i3++) {
            System.arraycopy(rm.c[pVec[i3]], 0, matrix[i3], 0, length);
        }
        result[0] = new GF2Matrix(i, matrix);
        GF2Matrix invLm = new GF2Matrix(i, 'I');
        int i4 = 0;
        while (i4 < i) {
            int q = i4 >>> 5;
            int r = i2 << (i4 & 31);
            int j = i4 + 1;
            while (j < i) {
                if ((lm.c[j][q] & r) != 0) {
                    int k = 0;
                    while (k <= q) {
                        int[][] matrix2 = matrix;
                        int[][] matrix3 = invLm.c;
                        int[] iArr2 = matrix3[j];
                        iArr2[k] = iArr2[k] ^ matrix3[i4][k];
                        k++;
                        matrix = matrix2;
                    }
                }
                j++;
                SecureRandom secureRandom2 = sr;
                matrix = matrix;
            }
            i4++;
            SecureRandom secureRandom3 = sr;
            i2 = 1;
        }
        GF2Matrix invUm = new GF2Matrix(i, 'I');
        int i5 = i - 1;
        while (i5 >= 0) {
            int q2 = i5 >>> 5;
            int r2 = 1 << (i5 & 31);
            int j2 = i5 - 1;
            while (j2 >= 0) {
                if ((um.c[j2][q2] & r2) != 0) {
                    int k2 = q2;
                    while (k2 < length) {
                        int length2 = length;
                        int[][] iArr3 = invUm.c;
                        int[] iArr4 = iArr3[j2];
                        iArr4[k2] = iArr4[k2] ^ iArr3[i5][k2];
                        k2++;
                        length = length2;
                    }
                }
                j2--;
                int i6 = n;
                length = length;
            }
            i5--;
            int i7 = n;
        }
        result[1] = (GF2Matrix) invUm.r(invLm.s(p));
        return result;
    }

    public byte[] m() {
        int i = this.a;
        byte[] enc = new byte[((((this.b + 7) >>> 3) * i) + 8)];
        LittleEndianConversions.a(i, enc, 0);
        LittleEndianConversions.a(this.b, enc, 4);
        int i2 = this.b;
        int q = i2 >>> 5;
        int r = i2 & 31;
        int count = 8;
        for (int i3 = 0; i3 < this.a; i3++) {
            int j = 0;
            while (j < q) {
                LittleEndianConversions.a(this.c[i3][j], enc, count);
                j++;
                count += 4;
            }
            int j2 = 0;
            while (j2 < r) {
                enc[count] = (byte) ((this.c[i3][q] >>> j2) & 255);
                j2 += 8;
                count++;
            }
        }
        return enc;
    }

    public GF2Matrix n() {
        int i = this.b;
        int i2 = this.a;
        if (i > i2) {
            int length = (i2 + 31) >> 5;
            int[] iArr = new int[2];
            iArr[1] = length;
            iArr[0] = i2;
            int[][] result = (int[][]) Array.newInstance(int.class, iArr);
            int i3 = this.a;
            int bitMask = (1 << (i3 & 31)) - 1;
            if (bitMask == 0) {
                bitMask = -1;
            }
            for (int i4 = i3 - 1; i4 >= 0; i4--) {
                System.arraycopy(this.c[i4], 0, result[i4], 0, length);
                int[] iArr2 = result[i4];
                int i5 = length - 1;
                iArr2[i5] = iArr2[i5] & bitMask;
            }
            return new GF2Matrix(this.a, result);
        }
        throw new ArithmeticException("empty submatrix");
    }

    public GF2Matrix l() {
        int i = this.b;
        int i2 = this.a;
        GF2Matrix result = new GF2Matrix(i2, i + i2);
        int i3 = this.a;
        int ind = (i3 - 1) + this.b;
        int i4 = i3 - 1;
        while (i4 >= 0) {
            System.arraycopy(this.c[i4], 0, result.c[i4], 0, this.d);
            int[] iArr = result.c[i4];
            int i5 = ind >> 5;
            iArr[i5] = iArr[i5] | (1 << (ind & 31));
            i4--;
            ind--;
        }
        return result;
    }

    public GF2Matrix o() {
        int i;
        int i2 = this.b;
        int i3 = this.a;
        if (i2 > i3) {
            int q = i3 >> 5;
            int r = i3 & 31;
            GF2Matrix result = new GF2Matrix(i3, i2 - i3);
            for (int i4 = this.a - 1; i4 >= 0; i4--) {
                if (r != 0) {
                    int ind = q;
                    int j = 0;
                    while (true) {
                        i = result.d;
                        if (j >= i - 1) {
                            break;
                        }
                        int[] iArr = result.c[i4];
                        int[][] iArr2 = this.c;
                        int ind2 = ind + 1;
                        iArr[j] = (iArr2[i4][ind] >>> r) | (iArr2[i4][ind2] << (32 - r));
                        j++;
                        ind = ind2;
                    }
                    int[][] iArr3 = result.c;
                    int[][] iArr4 = this.c;
                    int ind3 = ind + 1;
                    iArr3[i4][i - 1] = iArr4[i4][ind] >>> r;
                    if (ind3 < this.d) {
                        int[] iArr5 = iArr3[i4];
                        int i5 = i - 1;
                        iArr5[i5] = iArr5[i5] | (iArr4[i4][ind3] << (32 - r));
                    }
                } else {
                    System.arraycopy(this.c[i4], q, result.c[i4], 0, result.d);
                }
            }
            return result;
        }
        throw new ArithmeticException("empty submatrix");
    }

    public Matrix j() {
        int i = this.b;
        int[] iArr = new int[2];
        iArr[1] = (this.a + 31) >>> 5;
        iArr[0] = i;
        int[][] result = (int[][]) Array.newInstance(int.class, iArr);
        int i2 = 0;
        while (true) {
            int i3 = this.a;
            if (i2 >= i3) {
                return new GF2Matrix(i3, result);
            }
            for (int j = 0; j < this.b; j++) {
                int qt = i2 >>> 5;
                int rt = i2 & 31;
                if (((this.c[i2][j >>> 5] >>> (j & 31)) & 1) == 1) {
                    int[] iArr2 = result[j];
                    iArr2[qt] = iArr2[qt] | (1 << rt);
                }
            }
            i2++;
        }
    }

    public Matrix i() {
        Class<int> cls = int.class;
        int i = this.a;
        if (i == this.b) {
            int[] iArr = new int[2];
            iArr[1] = this.d;
            iArr[0] = i;
            int[][] tmpMatrix = (int[][]) Array.newInstance(cls, iArr);
            for (int i2 = this.a - 1; i2 >= 0; i2--) {
                tmpMatrix[i2] = IntUtils.a(this.c[i2]);
            }
            int i3 = this.a;
            int[] iArr2 = new int[2];
            iArr2[1] = this.d;
            iArr2[0] = i3;
            int[][] invMatrix = (int[][]) Array.newInstance(cls, iArr2);
            for (int i4 = this.a - 1; i4 >= 0; i4--) {
                invMatrix[i4][i4 >> 5] = 1 << (i4 & 31);
            }
            for (int i5 = 0; i5 < this.a; i5++) {
                int q = i5 >> 5;
                int bitMask = 1 << (i5 & 31);
                if ((tmpMatrix[i5][q] & bitMask) == 0) {
                    boolean foundNonZero = false;
                    int j = i5 + 1;
                    while (j < this.a) {
                        if ((tmpMatrix[j][q] & bitMask) != 0) {
                            foundNonZero = true;
                            u(tmpMatrix, i5, j);
                            u(invMatrix, i5, j);
                            j = this.a;
                        }
                        j++;
                    }
                    if (!foundNonZero) {
                        throw new ArithmeticException("Matrix is not invertible.");
                    }
                }
                for (int j2 = this.a - 1; j2 >= 0; j2--) {
                    if (!(j2 == i5 || (tmpMatrix[j2][q] & bitMask) == 0)) {
                        c(tmpMatrix[i5], tmpMatrix[j2], q);
                        c(invMatrix[i5], invMatrix[j2], 0);
                    }
                }
            }
            return new GF2Matrix(this.b, invMatrix);
        }
        throw new ArithmeticException("Matrix is not invertible.");
    }

    public Vector p(Vector vec) {
        if (!(vec instanceof GF2Vector)) {
            throw new ArithmeticException("vector is not defined over GF(2)");
        } else if (vec.a == this.a) {
            int[] v = ((GF2Vector) vec).g();
            int[] res = new int[this.d];
            int i = this.a;
            int q = i >> 5;
            int r = 1 << (i & 31);
            int row = 0;
            for (int i2 = 0; i2 < q; i2++) {
                int bitMask = 1;
                do {
                    if ((v[i2] & bitMask) != 0) {
                        for (int j = 0; j < this.d; j++) {
                            res[j] = res[j] ^ this.c[row][j];
                        }
                    }
                    row++;
                    bitMask <<= 1;
                } while (bitMask != 0);
            }
            for (int bitMask2 = 1; bitMask2 != r; bitMask2 <<= 1) {
                if ((v[q] & bitMask2) != 0) {
                    for (int j2 = 0; j2 < this.d; j2++) {
                        res[j2] = res[j2] ^ this.c[row][j2];
                    }
                }
                row++;
            }
            return new GF2Vector(res, this.b);
        } else {
            throw new ArithmeticException("length mismatch");
        }
    }

    public Vector q(Vector vec) {
        if (!(vec instanceof GF2Vector)) {
            throw new ArithmeticException("vector is not defined over GF(2)");
        } else if (vec.a == this.a) {
            int[] v = ((GF2Vector) vec).g();
            int i = this.a;
            int[] res = new int[(((this.b + i) + 31) >>> 5)];
            int words = i >>> 5;
            int row = 0;
            for (int i2 = 0; i2 < words; i2++) {
                int bitMask = 1;
                do {
                    if ((v[i2] & bitMask) != 0) {
                        for (int j = 0; j < this.d; j++) {
                            res[j] = res[j] ^ this.c[row][j];
                        }
                        int j2 = this.b;
                        int q = (j2 + row) >>> 5;
                        res[q] = res[q] | (1 << ((j2 + row) & 31));
                    }
                    row++;
                    bitMask <<= 1;
                } while (bitMask != 0);
            }
            int rem = 1 << (this.a & 31);
            for (int bitMask2 = 1; bitMask2 != rem; bitMask2 <<= 1) {
                if ((v[words] & bitMask2) != 0) {
                    for (int j3 = 0; j3 < this.d; j3++) {
                        res[j3] = res[j3] ^ this.c[row][j3];
                    }
                    int j4 = this.b;
                    int q2 = (j4 + row) >>> 5;
                    res[q2] = res[q2] | (1 << ((j4 + row) & 31));
                }
                row++;
            }
            return new GF2Vector(res, this.a + this.b);
        } else {
            throw new ArithmeticException("length mismatch");
        }
    }

    public Matrix r(Matrix mat) {
        int d2;
        Matrix matrix = mat;
        if (!(matrix instanceof GF2Matrix)) {
            throw new ArithmeticException("matrix is not defined over GF(2)");
        } else if (matrix.a == this.b) {
            GF2Matrix a = (GF2Matrix) matrix;
            GF2Matrix result = new GF2Matrix(this.a, matrix.b);
            int rest = this.b & 31;
            int i = 1;
            if (rest == 0) {
                d2 = this.d;
            } else {
                d2 = this.d - 1;
            }
            int i2 = 0;
            while (i2 < this.a) {
                int count = 0;
                int j = 0;
                while (j < d2) {
                    int e = this.c[i2][j];
                    int h = 0;
                    while (h < 32) {
                        if (((i << h) & e) != 0) {
                            for (int g = 0; g < a.d; g++) {
                                int[] iArr = result.c[i2];
                                iArr[g] = a.c[count][g] ^ iArr[g];
                            }
                        }
                        count++;
                        h++;
                        i = 1;
                    }
                    j++;
                    i = 1;
                }
                int e2 = this.c[i2][this.d - 1];
                for (int h2 = 0; h2 < rest; h2++) {
                    if (((1 << h2) & e2) != 0) {
                        for (int g2 = 0; g2 < a.d; g2++) {
                            int[] iArr2 = result.c[i2];
                            iArr2[g2] = iArr2[g2] ^ a.c[count][g2];
                        }
                    }
                    count++;
                }
                i2++;
                i = 1;
            }
            return result;
        } else {
            throw new ArithmeticException("length mismatch");
        }
    }

    public Matrix s(Permutation p) {
        int[] pVec = p.c();
        int length = pVec.length;
        int i = this.b;
        if (length == i) {
            GF2Matrix result = new GF2Matrix(this.a, i);
            for (int i2 = this.b - 1; i2 >= 0; i2--) {
                int q = i2 >>> 5;
                int r = i2 & 31;
                int pq = pVec[i2] >>> 5;
                int pr = pVec[i2] & 31;
                for (int j = this.a - 1; j >= 0; j--) {
                    int[] iArr = result.c[j];
                    iArr[q] = iArr[q] | (((this.c[j][pq] >>> pr) & 1) << r);
                }
            }
            return result;
        }
        throw new ArithmeticException("length mismatch");
    }

    public Vector t(Vector vec) {
        if (!(vec instanceof GF2Vector)) {
            throw new ArithmeticException("vector is not defined over GF(2)");
        } else if (vec.a == this.b) {
            int[] v = ((GF2Vector) vec).g();
            int[] res = new int[((this.a + 31) >>> 5)];
            int i = 0;
            while (true) {
                int i2 = this.a;
                if (i >= i2) {
                    return new GF2Vector(res, i2);
                }
                int help = 0;
                for (int j = 0; j < this.d; j++) {
                    help ^= this.c[i][j] & v[j];
                }
                int bitValue = 0;
                for (int j2 = 0; j2 < 32; j2++) {
                    bitValue ^= (help >>> j2) & 1;
                }
                if (bitValue == 1) {
                    int i3 = i >>> 5;
                    res[i3] = res[i3] | (1 << (i & 31));
                }
                i++;
            }
        } else {
            throw new ArithmeticException("length mismatch");
        }
    }

    public boolean equals(Object other) {
        if (!(other instanceof GF2Matrix)) {
            return false;
        }
        GF2Matrix otherMatrix = (GF2Matrix) other;
        if (this.a != otherMatrix.a || this.b != otherMatrix.b || this.d != otherMatrix.d) {
            return false;
        }
        for (int i = 0; i < this.a; i++) {
            if (!IntUtils.b(this.c[i], otherMatrix.c[i])) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int hash = (((this.a * 31) + this.b) * 31) + this.d;
        for (int i = 0; i < this.a; i++) {
            hash = (hash * 31) + this.c[i].hashCode();
        }
        return hash;
    }

    public String toString() {
        int d2;
        int rest = this.b & 31;
        if (rest == 0) {
            d2 = this.d;
        } else {
            d2 = this.d - 1;
        }
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < this.a; i++) {
            buf.append(i + ": ");
            for (int j = 0; j < d2; j++) {
                int a = this.c[i][j];
                for (int k = 0; k < 32; k++) {
                    if (((a >>> k) & 1) == 0) {
                        buf.append('0');
                    } else {
                        buf.append('1');
                    }
                }
                buf.append(' ');
            }
            int a2 = this.c[i][this.d - 1];
            for (int k2 = 0; k2 < rest; k2++) {
                if (((a2 >>> k2) & 1) == 0) {
                    buf.append('0');
                } else {
                    buf.append('1');
                }
            }
            buf.append(10);
        }
        return buf.toString();
    }

    private static void u(int[][] matrix, int first, int second) {
        int[] tmp = matrix[first];
        matrix[first] = matrix[second];
        matrix[second] = tmp;
    }

    private static void c(int[] fromRow, int[] toRow, int startIndex) {
        for (int i = toRow.length - 1; i >= startIndex; i--) {
            toRow[i] = fromRow[i] ^ toRow[i];
        }
    }
}
