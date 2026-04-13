package org.spongycastle.pqc.crypto.rainbow;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import org.spongycastle.pqc.crypto.rainbow.util.GF2Field;
import org.spongycastle.pqc.crypto.rainbow.util.RainbowUtil;
import org.spongycastle.util.Arrays;

public class Layer {
    private int a;
    private int b;
    private int c;
    private short[][][] d;
    private short[][][] e;
    private short[][] f;
    private short[] g;

    public Layer(byte vi, byte viNext, short[][][] coeffAlpha, short[][][] coeffBeta, short[][] coeffGamma, short[] coeffEta) {
        byte b2 = vi & 255;
        this.a = b2;
        byte b3 = viNext & 255;
        this.b = b3;
        this.c = b3 - b2;
        this.d = coeffAlpha;
        this.e = coeffBeta;
        this.f = coeffGamma;
        this.g = coeffEta;
    }

    public Layer(int vi, int viNext, SecureRandom sr) {
        Class<short> cls = short.class;
        this.a = vi;
        this.b = viNext;
        int i = viNext - vi;
        this.c = i;
        int[] iArr = new int[3];
        iArr[2] = vi;
        iArr[1] = i;
        iArr[0] = i;
        this.d = (short[][][]) Array.newInstance(cls, iArr);
        int i2 = this.c;
        int i3 = this.a;
        int[] iArr2 = new int[3];
        iArr2[2] = i3;
        iArr2[1] = i3;
        iArr2[0] = i2;
        this.e = (short[][][]) Array.newInstance(cls, iArr2);
        int i4 = this.c;
        int[] iArr3 = new int[2];
        iArr3[1] = this.b;
        iArr3[0] = i4;
        this.f = (short[][]) Array.newInstance(cls, iArr3);
        this.g = new short[this.c];
        int numOfPoly = this.c;
        for (int k = 0; k < numOfPoly; k++) {
            for (int i5 = 0; i5 < this.c; i5++) {
                for (int j = 0; j < this.a; j++) {
                    this.d[k][i5][j] = (short) (sr.nextInt() & 255);
                }
            }
        }
        for (int k2 = 0; k2 < numOfPoly; k2++) {
            for (int i6 = 0; i6 < this.a; i6++) {
                for (int j2 = 0; j2 < this.a; j2++) {
                    this.e[k2][i6][j2] = (short) (sr.nextInt() & 255);
                }
            }
        }
        for (int k3 = 0; k3 < numOfPoly; k3++) {
            for (int i7 = 0; i7 < this.b; i7++) {
                this.f[k3][i7] = (short) (sr.nextInt() & 255);
            }
        }
        for (int k4 = 0; k4 < numOfPoly; k4++) {
            this.g[k4] = (short) (sr.nextInt() & 255);
        }
    }

    public short[][] h(short[] x) {
        int i = this.c;
        int[] iArr = new int[2];
        iArr[1] = i + 1;
        iArr[0] = i;
        short[][] coeff = (short[][]) Array.newInstance(short.class, iArr);
        short[] sum = new short[this.c];
        for (int k = 0; k < this.c; k++) {
            for (int i2 = 0; i2 < this.a; i2++) {
                for (int j = 0; j < this.a; j++) {
                    sum[k] = GF2Field.a(sum[k], GF2Field.c(GF2Field.c(this.e[k][i2][j], x[i2]), x[j]));
                }
            }
        }
        for (int k2 = 0; k2 < this.c; k2++) {
            for (int i3 = 0; i3 < this.c; i3++) {
                for (int j2 = 0; j2 < this.a; j2++) {
                    coeff[k2][i3] = GF2Field.a(coeff[k2][i3], GF2Field.c(this.d[k2][i3][j2], x[j2]));
                }
            }
        }
        for (int k3 = 0; k3 < this.c; k3++) {
            for (int i4 = 0; i4 < this.a; i4++) {
                sum[k3] = GF2Field.a(sum[k3], GF2Field.c(this.f[k3][i4], x[i4]));
            }
        }
        for (int k4 = 0; k4 < this.c; k4++) {
            for (int i5 = this.a; i5 < this.b; i5++) {
                short[] sArr = coeff[k4];
                int i6 = this.a;
                sArr[i5 - i6] = GF2Field.a(this.f[k4][i5], coeff[k4][i5 - i6]);
            }
        }
        for (int k5 = 0; k5 < this.c; k5++) {
            sum[k5] = GF2Field.a(sum[k5], this.g[k5]);
        }
        int k6 = 0;
        while (true) {
            int i7 = this.c;
            if (k6 >= i7) {
                return coeff;
            }
            coeff[k6][i7] = sum[k6];
            k6++;
        }
    }

    public int f() {
        return this.a;
    }

    public int g() {
        return this.b;
    }

    public int e() {
        return this.c;
    }

    public short[][][] a() {
        return this.d;
    }

    public short[][][] b() {
        return this.e;
    }

    public short[][] d() {
        return this.f;
    }

    public short[] c() {
        return this.g;
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof Layer)) {
            return false;
        }
        Layer otherLayer = (Layer) other;
        if (this.a != otherLayer.f() || this.b != otherLayer.g() || this.c != otherLayer.e() || !RainbowUtil.k(this.d, otherLayer.a()) || !RainbowUtil.k(this.e, otherLayer.b()) || !RainbowUtil.j(this.f, otherLayer.d()) || !RainbowUtil.i(this.g, otherLayer.c())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((((((((this.a * 37) + this.b) * 37) + this.c) * 37) + Arrays.R(this.d)) * 37) + Arrays.R(this.e)) * 37) + Arrays.Q(this.f)) * 37) + Arrays.P(this.g);
    }
}
