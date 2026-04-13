package org.spongycastle.pqc.crypto.rainbow.util;

import java.lang.reflect.Array;

public class ComputeInField {
    private short[][] a;
    short[] b;

    public short[] j(short[][] B, short[] b2) {
        if (B.length != b2.length) {
            return null;
        }
        try {
            int length = B.length;
            int[] iArr = new int[2];
            iArr[1] = B.length + 1;
            iArr[0] = length;
            this.a = (short[][]) Array.newInstance(short.class, iArr);
            this.b = new short[B.length];
            for (int i = 0; i < B.length; i++) {
                for (int j = 0; j < B[0].length; j++) {
                    this.a[i][j] = B[i][j];
                }
            }
            for (int i2 = 0; i2 < b2.length; i2++) {
                short[][] sArr = this.a;
                sArr[i2][b2.length] = GF2Field.a(b2[i2], sArr[i2][b2.length]);
            }
            d(false);
            k();
            return this.b;
        } catch (RuntimeException e) {
            return null;
        }
    }

    public short[][] e(short[][] coef) {
        Class<short> cls = short.class;
        try {
            int length = coef.length;
            int[] iArr = new int[2];
            iArr[1] = coef.length * 2;
            iArr[0] = length;
            this.a = (short[][]) Array.newInstance(cls, iArr);
            if (coef.length == coef[0].length) {
                for (int i = 0; i < coef.length; i++) {
                    for (int j = 0; j < coef.length; j++) {
                        this.a[i][j] = coef[i][j];
                    }
                    for (int j2 = coef.length; j2 < coef.length * 2; j2++) {
                        this.a[i][j2] = 0;
                    }
                    short[][] sArr = this.a;
                    sArr[i][sArr.length + i] = 1;
                }
                d(true);
                int i2 = 0;
                while (true) {
                    short[][] sArr2 = this.a;
                    if (i2 >= sArr2.length) {
                        break;
                    }
                    short factor = GF2Field.b(sArr2[i2][i2]);
                    int j3 = i2;
                    while (true) {
                        short[][] sArr3 = this.a;
                        if (j3 >= sArr3.length * 2) {
                            break;
                        }
                        sArr3[i2][j3] = GF2Field.c(sArr3[i2][j3], factor);
                        j3++;
                    }
                    i2++;
                }
                c();
                short[][] sArr4 = this.a;
                int length2 = sArr4.length;
                int[] iArr2 = new int[2];
                iArr2[1] = sArr4.length;
                iArr2[0] = length2;
                short[][] inverse = (short[][]) Array.newInstance(cls, iArr2);
                int i3 = 0;
                while (true) {
                    short[][] sArr5 = this.a;
                    if (i3 >= sArr5.length) {
                        return inverse;
                    }
                    int j4 = sArr5.length;
                    while (true) {
                        short[][] sArr6 = this.a;
                        if (j4 >= sArr6.length * 2) {
                            break;
                        }
                        inverse[i3][j4 - sArr6.length] = sArr6[i3][j4];
                        j4++;
                    }
                    i3++;
                }
            } else {
                throw new RuntimeException("The matrix is not invertible. Please choose another one!");
            }
        } catch (RuntimeException e) {
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0058, code lost:
        r2 = r2 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void d(boolean r10) {
        /*
            r9 = this;
            r0 = 0
            if (r10 == 0) goto L_0x0009
            short[][] r1 = r9.a
            int r1 = r1.length
            int r1 = r1 * 2
            goto L_0x000e
        L_0x0009:
            short[][] r1 = r9.a
            int r1 = r1.length
            int r1 = r1 + 1
        L_0x000e:
            r2 = 0
        L_0x000f:
            short[][] r3 = r9.a
            int r3 = r3.length
            int r3 = r3 + -1
            if (r2 >= r3) goto L_0x005b
            int r3 = r2 + 1
        L_0x0018:
            short[][] r4 = r9.a
            int r5 = r4.length
            if (r3 >= r5) goto L_0x0058
            r5 = r4[r3]
            short r5 = r5[r2]
            r4 = r4[r2]
            short r4 = r4[r2]
            short r4 = org.spongycastle.pqc.crypto.rainbow.util.GF2Field.b(r4)
            if (r4 == 0) goto L_0x0050
            r6 = r2
        L_0x002c:
            if (r6 >= r1) goto L_0x004d
            short[][] r7 = r9.a
            r7 = r7[r2]
            short r7 = r7[r6]
            short r0 = org.spongycastle.pqc.crypto.rainbow.util.GF2Field.c(r7, r4)
            short r0 = org.spongycastle.pqc.crypto.rainbow.util.GF2Field.c(r5, r0)
            short[][] r7 = r9.a
            r8 = r7[r3]
            r7 = r7[r3]
            short r7 = r7[r6]
            short r7 = org.spongycastle.pqc.crypto.rainbow.util.GF2Field.a(r7, r0)
            r8[r6] = r7
            int r6 = r6 + 1
            goto L_0x002c
        L_0x004d:
            int r3 = r3 + 1
            goto L_0x0018
        L_0x0050:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "Matrix not invertible! We have to choose another one!"
            r6.<init>(r7)
            throw r6
        L_0x0058:
            int r2 = r2 + 1
            goto L_0x000f
        L_0x005b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.pqc.crypto.rainbow.util.ComputeInField.d(boolean):void");
    }

    private void c() {
        for (int k = this.a.length - 1; k > 0; k--) {
            int i = k - 1;
            while (i >= 0) {
                short[][] sArr = this.a;
                short factor1 = sArr[i][k];
                short factor2 = GF2Field.b(sArr[k][k]);
                if (factor2 != 0) {
                    int j = k;
                    while (true) {
                        short[][] sArr2 = this.a;
                        if (j >= sArr2.length * 2) {
                            break;
                        }
                        short tmp = GF2Field.c(factor1, GF2Field.c(sArr2[k][j], factor2));
                        short[][] sArr3 = this.a;
                        sArr3[i][j] = GF2Field.a(sArr3[i][j], tmp);
                        j++;
                    }
                    i--;
                } else {
                    throw new RuntimeException("The matrix is not invertible");
                }
            }
        }
    }

    private void k() {
        short[][] sArr = this.a;
        short temp = GF2Field.b(sArr[sArr.length - 1][sArr.length - 1]);
        if (temp != 0) {
            short[] sArr2 = this.b;
            short[][] sArr3 = this.a;
            sArr2[sArr3.length - 1] = GF2Field.c(sArr3[sArr3.length - 1][sArr3.length], temp);
            int i = this.a.length - 2;
            while (i >= 0) {
                short[][] sArr4 = this.a;
                short tmp = sArr4[i][sArr4.length];
                for (int j = sArr4.length - 1; j > i; j--) {
                    tmp = GF2Field.a(tmp, GF2Field.c(this.a[i][j], this.b[j]));
                }
                short temp2 = GF2Field.b(this.a[i][i]);
                if (temp2 != 0) {
                    this.b[i] = GF2Field.c(tmp, temp2);
                    i--;
                } else {
                    throw new IllegalStateException("Not solvable equation system");
                }
            }
            return;
        }
        throw new IllegalStateException("The equation system is not solvable");
    }

    public short[] i(short[][] M1, short[] m) {
        if (M1[0].length == m.length) {
            short[] B = new short[M1.length];
            for (int i = 0; i < M1.length; i++) {
                for (int j = 0; j < m.length; j++) {
                    B[i] = GF2Field.a(B[i], GF2Field.c(M1[i][j], m[j]));
                }
            }
            return B;
        }
        throw new RuntimeException("Multiplication is not possible!");
    }

    public short[] b(short[] vector1, short[] vector2) {
        if (vector1.length == vector2.length) {
            short[] rslt = new short[vector1.length];
            for (int n = 0; n < rslt.length; n++) {
                rslt[n] = GF2Field.a(vector1[n], vector2[n]);
            }
            return rslt;
        }
        throw new RuntimeException("Multiplication is not possible!");
    }

    public short[][] h(short[] vector1, short[] vector2) {
        if (vector1.length == vector2.length) {
            int length = vector1.length;
            int[] iArr = new int[2];
            iArr[1] = vector2.length;
            iArr[0] = length;
            short[][] rslt = (short[][]) Array.newInstance(short.class, iArr);
            for (int i = 0; i < vector1.length; i++) {
                for (int j = 0; j < vector2.length; j++) {
                    rslt[i][j] = GF2Field.c(vector1[i], vector2[j]);
                }
            }
            return rslt;
        }
        throw new RuntimeException("Multiplication is not possible!");
    }

    public short[] g(short scalar, short[] vector) {
        short[] rslt = new short[vector.length];
        for (int n = 0; n < rslt.length; n++) {
            rslt[n] = GF2Field.c(scalar, vector[n]);
        }
        return rslt;
    }

    public short[][] f(short scalar, short[][] matrix) {
        int length = matrix.length;
        int[] iArr = new int[2];
        iArr[1] = matrix[0].length;
        iArr[0] = length;
        short[][] rslt = (short[][]) Array.newInstance(short.class, iArr);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                rslt[i][j] = GF2Field.c(scalar, matrix[i][j]);
            }
        }
        return rslt;
    }

    public short[][] a(short[][] matrix1, short[][] matrix2) {
        if (matrix1.length == matrix2.length && matrix1[0].length == matrix2[0].length) {
            int length = matrix1.length;
            int[] iArr = new int[2];
            iArr[1] = matrix1.length;
            iArr[0] = length;
            short[][] rslt = (short[][]) Array.newInstance(short.class, iArr);
            for (int i = 0; i < matrix1.length; i++) {
                for (int j = 0; j < matrix2.length; j++) {
                    rslt[i][j] = GF2Field.a(matrix1[i][j], matrix2[i][j]);
                }
            }
            return rslt;
        }
        throw new RuntimeException("Addition is not possible!");
    }
}
