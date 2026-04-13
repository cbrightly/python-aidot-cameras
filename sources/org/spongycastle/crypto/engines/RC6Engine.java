package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public class RC6Engine implements BlockCipher {
    private int[] a = null;
    private boolean b;

    public String b() {
        return "RC6";
    }

    public int c() {
        return 16;
    }

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof KeyParameter) {
            this.b = forEncryption;
            j(((KeyParameter) params).a());
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to RC6 init - " + params.getClass().getName());
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        int blockSize = c();
        if (this.a == null) {
            throw new IllegalStateException("RC6 engine not initialised");
        } else if (inOff + blockSize > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + blockSize > out.length) {
            throw new OutputLengthException("output buffer too short");
        } else if (this.b) {
            return g(in, inOff, out, outOff);
        } else {
            return e(in, inOff, out, outOff);
        }
    }

    public void reset() {
    }

    private void j(byte[] key) {
        int[] iArr;
        int iter;
        if ((key.length + 3) / 4 == 0) {
        }
        int[] L = new int[(((key.length + 4) - 1) / 4)];
        for (int i = key.length - 1; i >= 0; i--) {
            L[i / 4] = (L[i / 4] << 8) + (key[i] & 255);
        }
        int[] iArr2 = new int[44];
        this.a = iArr2;
        iArr2[0] = -1209970333;
        int i2 = 1;
        while (true) {
            iArr = this.a;
            if (i2 >= iArr.length) {
                break;
            }
            iArr[i2] = iArr[i2 - 1] - 1640531527;
            i2++;
        }
        if (L.length > iArr.length) {
            iter = L.length * 3;
        } else {
            iter = iArr.length * 3;
        }
        int A = 0;
        int B = 0;
        int i3 = 0;
        int j = 0;
        for (int k = 0; k < iter; k++) {
            int[] iArr3 = this.a;
            int h = h(iArr3[i3] + A + B, 3);
            iArr3[i3] = h;
            A = h;
            int h2 = h(L[j] + A + B, A + B);
            L[j] = h2;
            B = h2;
            i3 = (i3 + 1) % this.a.length;
            j = (j + 1) % L.length;
        }
    }

    private int g(byte[] in, int inOff, byte[] out, int outOff) {
        int A = d(in, inOff);
        int B = d(in, inOff + 4);
        int C = d(in, inOff + 8);
        int D = d(in, inOff + 12);
        int[] iArr = this.a;
        int B2 = B + iArr[0];
        int D2 = D + iArr[1];
        for (int i = 1; i <= 20; i++) {
            int t = h(((B2 * 2) + 1) * B2, 5);
            int u = h(((D2 * 2) + 1) * D2, 5);
            int A2 = h(A ^ t, u) + this.a[i * 2];
            int temp = A2;
            A = B2;
            B2 = h(C ^ u, t) + this.a[(i * 2) + 1];
            C = D2;
            D2 = temp;
        }
        int[] iArr2 = this.a;
        int C2 = C + iArr2[43];
        k(A + iArr2[42], out, outOff);
        k(B2, out, outOff + 4);
        k(C2, out, outOff + 8);
        k(D2, out, outOff + 12);
        return 16;
    }

    private int e(byte[] in, int inOff, byte[] out, int outOff) {
        int A = d(in, inOff);
        int B = d(in, inOff + 4);
        int C = d(in, inOff + 8);
        int D = d(in, inOff + 12);
        int[] iArr = this.a;
        int C2 = C - iArr[43];
        int A2 = A - iArr[42];
        for (int i = 20; i >= 1; i--) {
            int temp = D;
            D = C2;
            int C3 = B;
            B = A2;
            int t = h(((B * 2) + 1) * B, 5);
            int u = h(((D * 2) + 1) * D, 5);
            C2 = i(C3 - this.a[(i * 2) + 1], t) ^ u;
            A2 = i(temp - this.a[i * 2], u) ^ t;
        }
        int[] iArr2 = this.a;
        k(A2, out, outOff);
        k(B - iArr2[0], out, outOff + 4);
        k(C2, out, outOff + 8);
        k(D - iArr2[1], out, outOff + 12);
        return 16;
    }

    private int h(int x, int y) {
        return (x << y) | (x >>> (-y));
    }

    private int i(int x, int y) {
        return (x >>> y) | (x << (-y));
    }

    private int d(byte[] src, int srcOff) {
        int word = 0;
        for (int i = 3; i >= 0; i--) {
            word = (word << 8) + (src[i + srcOff] & 255);
        }
        return word;
    }

    private void k(int word, byte[] dst, int dstOff) {
        for (int i = 0; i < 4; i++) {
            dst[i + dstOff] = (byte) word;
            word >>>= 8;
        }
    }
}
