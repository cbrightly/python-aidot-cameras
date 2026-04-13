package org.spongycastle.crypto.engines;

import com.alibaba.fastjson.asm.Opcodes;

public final class CAST6Engine extends CAST5Engine {
    protected int[] n = new int[48];
    protected int[] o = new int[48];
    protected int[] p = new int[Opcodes.CHECKCAST];
    protected int[] q = new int[Opcodes.CHECKCAST];
    private int[] r = new int[8];

    public String b() {
        return "CAST6";
    }

    public void reset() {
    }

    public int c() {
        return 16;
    }

    /* access modifiers changed from: protected */
    public void p(byte[] key) {
        int i;
        byte[] bArr = key;
        int Cm = 1518500249;
        int Cr = 19;
        int i2 = 0;
        while (true) {
            i = 8;
            if (i2 >= 24) {
                break;
            }
            for (int j = 0; j < 8; j++) {
                this.q[(i2 * 8) + j] = Cm;
                Cm += 1859775393;
                this.p[(i2 * 8) + j] = Cr;
                Cr = (Cr + 17) & 31;
            }
            i2++;
        }
        byte[] tmpKey = new byte[64];
        System.arraycopy(bArr, 0, tmpKey, 0, bArr.length);
        for (int i3 = 0; i3 < 8; i3++) {
            this.r[i3] = g(tmpKey, i3 * 4);
        }
        int i4 = 0;
        while (i4 < 12) {
            int i22 = i4 * 2 * i;
            int[] iArr = this.r;
            iArr[6] = j(iArr[7], this.q[i22], this.p[i22]) ^ iArr[6];
            int[] iArr2 = this.r;
            iArr2[5] = iArr2[5] ^ k(iArr2[6], this.q[i22 + 1], this.p[i22 + 1]);
            int[] iArr3 = this.r;
            iArr3[4] = l(iArr3[5], this.q[i22 + 2], this.p[i22 + 2]) ^ iArr3[4];
            int[] iArr4 = this.r;
            iArr4[3] = j(iArr4[4], this.q[i22 + 3], this.p[i22 + 3]) ^ iArr4[3];
            int[] iArr5 = this.r;
            iArr5[2] = k(iArr5[3], this.q[i22 + 4], this.p[i22 + 4]) ^ iArr5[2];
            int[] iArr6 = this.r;
            iArr6[1] = l(iArr6[2], this.q[i22 + 5], this.p[i22 + 5]) ^ iArr6[1];
            int[] iArr7 = this.r;
            iArr7[0] = j(iArr7[1], this.q[i22 + 6], this.p[i22 + 6]) ^ iArr7[0];
            int[] iArr8 = this.r;
            iArr8[7] = k(iArr8[0], this.q[i22 + 7], this.p[i22 + 7]) ^ iArr8[7];
            int i23 = ((i4 * 2) + 1) * 8;
            int[] iArr9 = this.r;
            iArr9[6] = j(iArr9[7], this.q[i23], this.p[i23]) ^ iArr9[6];
            int[] iArr10 = this.r;
            iArr10[5] = k(iArr10[6], this.q[i23 + 1], this.p[i23 + 1]) ^ iArr10[5];
            int[] iArr11 = this.r;
            iArr11[4] = l(iArr11[5], this.q[i23 + 2], this.p[i23 + 2]) ^ iArr11[4];
            int[] iArr12 = this.r;
            iArr12[3] = j(iArr12[4], this.q[i23 + 3], this.p[i23 + 3]) ^ iArr12[3];
            int[] iArr13 = this.r;
            iArr13[2] = k(iArr13[3], this.q[i23 + 4], this.p[i23 + 4]) ^ iArr13[2];
            int[] iArr14 = this.r;
            iArr14[1] = l(iArr14[2], this.q[i23 + 5], this.p[i23 + 5]) ^ iArr14[1];
            int[] iArr15 = this.r;
            iArr15[0] = j(iArr15[1], this.q[i23 + 6], this.p[i23 + 6]) ^ iArr15[0];
            int[] iArr16 = this.r;
            iArr16[7] = k(iArr16[0], this.q[i23 + 7], this.p[i23 + 7]) ^ iArr16[7];
            int[] iArr17 = this.n;
            int[] iArr18 = this.r;
            iArr17[i4 * 4] = iArr18[0] & 31;
            iArr17[(i4 * 4) + 1] = iArr18[2] & 31;
            iArr17[(i4 * 4) + 2] = iArr18[4] & 31;
            iArr17[(i4 * 4) + 3] = iArr18[6] & 31;
            int[] iArr19 = this.o;
            iArr19[i4 * 4] = iArr18[7];
            iArr19[(i4 * 4) + 1] = iArr18[5];
            iArr19[(i4 * 4) + 2] = iArr18[3];
            iArr19[(i4 * 4) + 3] = iArr18[1];
            i4++;
            i = 8;
        }
    }

    /* access modifiers changed from: protected */
    public int o(byte[] src, int srcIndex, byte[] dst, int dstIndex) {
        int[] result = new int[4];
        r(g(src, srcIndex), g(src, srcIndex + 4), g(src, srcIndex + 8), g(src, srcIndex + 12), result);
        d(result[0], dst, dstIndex);
        d(result[1], dst, dstIndex + 4);
        d(result[2], dst, dstIndex + 8);
        d(result[3], dst, dstIndex + 12);
        return 16;
    }

    /* access modifiers changed from: protected */
    public int n(byte[] src, int srcIndex, byte[] dst, int dstIndex) {
        int[] result = new int[4];
        q(g(src, srcIndex), g(src, srcIndex + 4), g(src, srcIndex + 8), g(src, srcIndex + 12), result);
        d(result[0], dst, dstIndex);
        d(result[1], dst, dstIndex + 4);
        d(result[2], dst, dstIndex + 8);
        d(result[3], dst, dstIndex + 12);
        return 16;
    }

    /* access modifiers changed from: protected */
    public final void r(int A, int B, int C, int D, int[] result) {
        for (int i = 0; i < 6; i++) {
            int x = i * 4;
            C ^= j(D, this.o[x], this.n[x]);
            B ^= k(C, this.o[x + 1], this.n[x + 1]);
            A ^= l(B, this.o[x + 2], this.n[x + 2]);
            D ^= j(A, this.o[x + 3], this.n[x + 3]);
        }
        for (int i2 = 6; i2 < 12; i2++) {
            int x2 = i2 * 4;
            D ^= j(A, this.o[x2 + 3], this.n[x2 + 3]);
            A ^= l(B, this.o[x2 + 2], this.n[x2 + 2]);
            B ^= k(C, this.o[x2 + 1], this.n[x2 + 1]);
            C ^= j(D, this.o[x2], this.n[x2]);
        }
        result[0] = A;
        result[1] = B;
        result[2] = C;
        result[3] = D;
    }

    /* access modifiers changed from: protected */
    public final void q(int A, int B, int C, int D, int[] result) {
        for (int i = 0; i < 6; i++) {
            int x = (11 - i) * 4;
            C ^= j(D, this.o[x], this.n[x]);
            B ^= k(C, this.o[x + 1], this.n[x + 1]);
            A ^= l(B, this.o[x + 2], this.n[x + 2]);
            D ^= j(A, this.o[x + 3], this.n[x + 3]);
        }
        for (int i2 = 6; i2 < 12; i2++) {
            int x2 = (11 - i2) * 4;
            D ^= j(A, this.o[x2 + 3], this.n[x2 + 3]);
            A ^= l(B, this.o[x2 + 2], this.n[x2 + 2]);
            B ^= k(C, this.o[x2 + 1], this.n[x2 + 1]);
            C ^= j(D, this.o[x2], this.n[x2]);
        }
        result[0] = A;
        result[1] = B;
        result[2] = C;
        result[3] = D;
    }
}
