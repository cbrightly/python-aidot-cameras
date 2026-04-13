package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.RC5Parameters;

public class RC564Engine implements BlockCipher {
    private int a = 12;
    private long[] b = null;
    private boolean c;

    public String b() {
        return "RC5-64";
    }

    public int c() {
        return 16;
    }

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof RC5Parameters) {
            RC5Parameters p = (RC5Parameters) params;
            this.c = forEncryption;
            this.a = p.b();
            j(p.a());
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to RC564 init - " + params.getClass().getName());
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
        long[] jArr;
        int iter;
        long[] L = new long[((key.length + 7) / 8)];
        for (int i = 0; i != key.length; i++) {
            int i2 = i / 8;
            L[i2] = L[i2] + (((long) (key[i] & 255)) << ((i % 8) * 8));
        }
        long[] jArr2 = new long[((this.a + 1) * 2)];
        this.b = jArr2;
        jArr2[0] = -5196783011329398165L;
        int i3 = 1;
        while (true) {
            jArr = this.b;
            if (i3 >= jArr.length) {
                break;
            }
            jArr[i3] = jArr[i3 - 1] - 7046029254386353131L;
            i3++;
        }
        if (L.length > jArr.length) {
            iter = L.length * 3;
        } else {
            iter = jArr.length * 3;
        }
        long A = 0;
        long B = 0;
        int i4 = 0;
        int j = 0;
        for (int k = 0; k < iter; k++) {
            long[] jArr3 = this.b;
            long h = h(jArr3[i4] + A + B, 3);
            jArr3[i4] = h;
            A = h;
            long h2 = h(L[j] + A + B, A + B);
            L[j] = h2;
            B = h2;
            i4 = (i4 + 1) % this.b.length;
            j = (j + 1) % L.length;
        }
    }

    private int g(byte[] in, int inOff, byte[] out, int outOff) {
        long A = d(in, inOff) + this.b[0];
        long B = d(in, inOff + 8) + this.b[1];
        for (int i = 1; i <= this.a; i++) {
            A = h(A ^ B, B) + this.b[i * 2];
            B = h(B ^ A, A) + this.b[(i * 2) + 1];
        }
        k(A, out, outOff);
        k(B, out, outOff + 8);
        return 16;
    }

    private int e(byte[] in, int inOff, byte[] out, int outOff) {
        long A = d(in, inOff);
        long B = d(in, inOff + 8);
        for (int i = this.a; i >= 1; i--) {
            B = i(B - this.b[(i * 2) + 1], A) ^ A;
            A = i(A - this.b[i * 2], B) ^ B;
        }
        k(A - this.b[0], out, outOff);
        k(B - this.b[1], out, outOff + 8);
        return 16;
    }

    private long h(long x, long y) {
        return (x >>> ((int) (64 - (63 & y)))) | (x << ((int) (y & 63)));
    }

    private long i(long x, long y) {
        return (x << ((int) (64 - (63 & y)))) | (x >>> ((int) (y & 63)));
    }

    private long d(byte[] src, int srcOff) {
        long word = 0;
        for (int i = 7; i >= 0; i--) {
            word = (word << 8) + ((long) (src[i + srcOff] & 255));
        }
        return word;
    }

    private void k(long word, byte[] dst, int dstOff) {
        for (int i = 0; i < 8; i++) {
            dst[i + dstOff] = (byte) ((int) word);
            word >>>= 8;
        }
    }
}
