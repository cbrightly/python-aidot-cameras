package org.spongycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;

public class GF2Vector extends Vector {
    private int[] b;

    public GF2Vector(int length) {
        if (length >= 0) {
            this.a = length;
            this.b = new int[((length + 31) >> 5)];
            return;
        }
        throw new ArithmeticException("Negative length.");
    }

    public GF2Vector(int length, SecureRandom sr) {
        this.a = length;
        int size = (length + 31) >> 5;
        this.b = new int[size];
        for (int i = size - 1; i >= 0; i--) {
            this.b[i] = sr.nextInt();
        }
        int r = length & 31;
        if (r != 0) {
            int[] iArr = this.b;
            int i2 = size - 1;
            iArr[i2] = iArr[i2] & ((1 << r) - 1);
        }
    }

    public GF2Vector(int length, int t, SecureRandom sr) {
        if (t <= length) {
            this.a = length;
            this.b = new int[((length + 31) >> 5)];
            int[] help = new int[length];
            for (int i = 0; i < length; i++) {
                help[i] = i;
            }
            int m = length;
            for (int i2 = 0; i2 < t; i2++) {
                int j = RandUtils.a(sr, m);
                j(help[j]);
                m--;
                help[j] = help[m];
            }
            return;
        }
        throw new ArithmeticException("The hamming weight is greater than the length of vector.");
    }

    public GF2Vector(int length, int[] v) {
        if (length >= 0) {
            this.a = length;
            int size = (length + 31) >> 5;
            if (v.length == size) {
                int[] a = IntUtils.a(v);
                this.b = a;
                int r = length & 31;
                if (r != 0) {
                    int i = size - 1;
                    a[i] = a[i] & ((1 << r) - 1);
                    return;
                }
                return;
            }
            throw new ArithmeticException("length mismatch");
        }
        throw new ArithmeticException("negative length");
    }

    public GF2Vector(GF2Vector other) {
        this.a = other.a;
        this.b = IntUtils.a(other.b);
    }

    protected GF2Vector(int[] v, int length) {
        this.b = v;
        this.a = length;
    }

    public static GF2Vector c(int length, byte[] encVec) {
        if (length >= 0) {
            if (encVec.length <= ((length + 7) >> 3)) {
                return new GF2Vector(length, LittleEndianConversions.h(encVec));
            }
            throw new ArithmeticException("length mismatch");
        }
        throw new ArithmeticException("negative length");
    }

    public byte[] e() {
        return LittleEndianConversions.g(this.b, (this.a + 7) >> 3);
    }

    public int[] g() {
        return this.b;
    }

    public int f() {
        int weight = 0;
        int i = 0;
        while (true) {
            int[] iArr = this.b;
            if (i >= iArr.length) {
                return weight;
            }
            int e = iArr[i];
            for (int j = 0; j < 32; j++) {
                if ((e & 1) != 0) {
                    weight++;
                }
                e >>>= 1;
            }
            i++;
        }
    }

    public boolean h() {
        for (int i = this.b.length - 1; i >= 0; i--) {
            if (this.b[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public void j(int index) {
        if (index < this.a) {
            int[] iArr = this.b;
            int i = index >> 5;
            iArr[i] = iArr[i] | (1 << (index & 31));
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    public Vector a(Vector other) {
        if (!(other instanceof GF2Vector)) {
            throw new ArithmeticException("vector is not defined over GF(2)");
        } else if (this.a == ((GF2Vector) other).a) {
            int[] vec = IntUtils.a(((GF2Vector) other).b);
            for (int i = vec.length - 1; i >= 0; i--) {
                vec[i] = vec[i] ^ this.b[i];
            }
            return new GF2Vector(this.a, vec);
        } else {
            throw new ArithmeticException("length mismatch");
        }
    }

    public Vector i(Permutation p) {
        int[] pVec = p.c();
        int i = this.a;
        if (i == pVec.length) {
            GF2Vector result = new GF2Vector(i);
            for (int i2 = 0; i2 < pVec.length; i2++) {
                if ((this.b[pVec[i2] >> 5] & (1 << (pVec[i2] & 31))) != 0) {
                    int[] iArr = result.b;
                    int i3 = i2 >> 5;
                    iArr[i3] = (1 << (i2 & 31)) | iArr[i3];
                }
            }
            return result;
        }
        throw new ArithmeticException("length mismatch");
    }

    public GF2Vector d(int k) {
        int i = this.a;
        if (k > i) {
            throw new ArithmeticException("invalid length");
        } else if (k == i) {
            return new GF2Vector(this);
        } else {
            GF2Vector result = new GF2Vector(k);
            int i2 = this.a;
            int q = (i2 - k) >> 5;
            int r = (i2 - k) & 31;
            int length = (k + 31) >> 5;
            int ind = q;
            if (r != 0) {
                int i3 = 0;
                while (i3 < length - 1) {
                    int[] iArr = result.b;
                    int[] iArr2 = this.b;
                    int ind2 = ind + 1;
                    iArr[i3] = (iArr2[ind] >>> r) | (iArr2[ind2] << (32 - r));
                    i3++;
                    ind = ind2;
                }
                int[] iArr3 = result.b;
                int[] iArr4 = this.b;
                int ind3 = ind + 1;
                iArr3[length - 1] = iArr4[ind] >>> r;
                if (ind3 < iArr4.length) {
                    int i4 = length - 1;
                    iArr3[i4] = iArr3[i4] | (iArr4[ind3] << (32 - r));
                }
                int i5 = ind3;
            } else {
                System.arraycopy(this.b, q, result.b, 0, length);
            }
            return result;
        }
    }

    public GF2mVector k(GF2mField field) {
        int m = field.d();
        int i = this.a;
        if (i % m == 0) {
            int t = i / m;
            int[] result = new int[t];
            int count = 0;
            for (int i2 = t - 1; i2 >= 0; i2--) {
                for (int j = field.d() - 1; j >= 0; j--) {
                    if (((this.b[count >>> 5] >>> (count & 31)) & 1) == 1) {
                        result[i2] = result[i2] ^ (1 << j);
                    }
                    count++;
                }
            }
            return new GF2mVector(field, result);
        }
        throw new ArithmeticException("conversion is impossible");
    }

    public boolean equals(Object other) {
        if (!(other instanceof GF2Vector)) {
            return false;
        }
        GF2Vector otherVec = (GF2Vector) other;
        if (this.a != otherVec.a || !IntUtils.b(this.b, otherVec.b)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (this.a * 31) + this.b.hashCode();
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < this.a; i++) {
            if (i != 0 && (i & 31) == 0) {
                buf.append(' ');
            }
            if ((this.b[i >> 5] & (1 << (i & 31))) == 0) {
                buf.append('0');
            } else {
                buf.append('1');
            }
        }
        return buf.toString();
    }
}
