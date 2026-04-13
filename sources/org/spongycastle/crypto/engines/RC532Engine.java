package org.spongycastle.crypto.engines;

import org.glassfish.grizzly.http.server.util.MappingData;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.RC5Parameters;

public class RC532Engine implements BlockCipher {
    private int a = 12;
    private int[] b = null;
    private boolean c;

    public String b() {
        return "RC5-32";
    }

    public int c() {
        return 8;
    }

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof RC5Parameters) {
            RC5Parameters p = (RC5Parameters) params;
            this.a = p.b();
            j(p.a());
        } else if (params instanceof KeyParameter) {
            j(((KeyParameter) params).a());
        } else {
            throw new IllegalArgumentException("invalid parameter passed to RC532 init - " + params.getClass().getName());
        }
        this.c = forEncryption;
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        if (this.c) {
            return g(in, inOff, out, outOff);
        }
        return e(in, inOff, out, outOff);
    }

    public void reset() {
    }

    private void j(byte[] key) {
        int[] iArr;
        int iter;
        int[] L = new int[((key.length + 3) / 4)];
        for (int i = 0; i != key.length; i++) {
            int i2 = i / 4;
            L[i2] = L[i2] + ((key[i] & 255) << ((i % 4) * 8));
        }
        int[] iArr2 = new int[((this.a + 1) * 2)];
        this.b = iArr2;
        iArr2[0] = -1209970333;
        int i3 = 1;
        while (true) {
            iArr = this.b;
            if (i3 >= iArr.length) {
                break;
            }
            iArr[i3] = iArr[i3 - 1] - 1640531527;
            i3++;
        }
        if (L.length > iArr.length) {
            iter = L.length * 3;
        } else {
            iter = iArr.length * 3;
        }
        int A = 0;
        int B = 0;
        int i4 = 0;
        int j = 0;
        for (int k = 0; k < iter; k++) {
            int[] iArr3 = this.b;
            int h = h(iArr3[i4] + A + B, 3);
            iArr3[i4] = h;
            A = h;
            int h2 = h(L[j] + A + B, A + B);
            L[j] = h2;
            B = h2;
            i4 = (i4 + 1) % this.b.length;
            j = (j + 1) % L.length;
        }
    }

    private int g(byte[] in, int inOff, byte[] out, int outOff) {
        int A = d(in, inOff) + this.b[0];
        int B = d(in, inOff + 4) + this.b[1];
        for (int i = 1; i <= this.a; i++) {
            A = h(A ^ B, B) + this.b[i * 2];
            B = h(B ^ A, A) + this.b[(i * 2) + 1];
        }
        k(A, out, outOff);
        k(B, out, outOff + 4);
        return 8;
    }

    private int e(byte[] in, int inOff, byte[] out, int outOff) {
        int A = d(in, inOff);
        int B = d(in, inOff + 4);
        for (int i = this.a; i >= 1; i--) {
            B = i(B - this.b[(i * 2) + 1], A) ^ A;
            A = i(A - this.b[i * 2], B) ^ B;
        }
        k(A - this.b[0], out, outOff);
        k(B - this.b[1], out, outOff + 4);
        return 8;
    }

    private int h(int x, int y) {
        return (x << (y & 31)) | (x >>> (32 - (y & 31)));
    }

    private int i(int x, int y) {
        return (x >>> (y & 31)) | (x << (32 - (y & 31)));
    }

    private int d(byte[] src, int srcOff) {
        return (src[srcOff] & 255) | ((src[srcOff + 1] & 255) << 8) | ((src[srcOff + 2] & 255) << MappingData.PATH) | ((src[srcOff + 3] & 255) << 24);
    }

    private void k(int word, byte[] dst, int dstOff) {
        dst[dstOff] = (byte) word;
        dst[dstOff + 1] = (byte) (word >> 8);
        dst[dstOff + 2] = (byte) (word >> 16);
        dst[dstOff + 3] = (byte) (word >> 24);
    }
}
