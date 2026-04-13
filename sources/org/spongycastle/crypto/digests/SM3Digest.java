package org.spongycastle.crypto.digests;

import org.glassfish.grizzly.http.server.util.MappingData;
import org.spongycastle.util.Memoable;
import org.spongycastle.util.Pack;

public class SM3Digest extends GeneralDigest {
    private static final int[] d = new int[64];
    private int[] e = new int[8];
    private int[] f = new int[16];
    private int g;
    private int[] h = new int[68];
    private int[] i = new int[64];

    static {
        for (int i2 = 0; i2 < 16; i2++) {
            d[i2] = (2043430169 << i2) | (2043430169 >>> (32 - i2));
        }
        for (int i3 = 16; i3 < 64; i3++) {
            int n = i3 % 32;
            d[i3] = (2055708042 << n) | (2055708042 >>> (32 - n));
        }
    }

    public SM3Digest() {
        reset();
    }

    public SM3Digest(SM3Digest t) {
        super(t);
        y(t);
    }

    private void y(SM3Digest t) {
        int[] iArr = t.e;
        int[] iArr2 = this.e;
        System.arraycopy(iArr, 0, iArr2, 0, iArr2.length);
        int[] iArr3 = t.f;
        int[] iArr4 = this.f;
        System.arraycopy(iArr3, 0, iArr4, 0, iArr4.length);
        this.g = t.g;
    }

    public String b() {
        return "SM3";
    }

    public int e() {
        return 32;
    }

    public Memoable copy() {
        return new SM3Digest(this);
    }

    public void m(Memoable other) {
        SM3Digest d2 = (SM3Digest) other;
        super.n(d2);
        y(d2);
    }

    public void reset() {
        super.reset();
        int[] iArr = this.e;
        iArr[0] = 1937774191;
        iArr[1] = 1226093241;
        iArr[2] = 388252375;
        iArr[3] = -628488704;
        iArr[4] = -1452330820;
        iArr[5] = 372324522;
        iArr[6] = -477237683;
        iArr[7] = -1325724082;
        this.g = 0;
    }

    public int c(byte[] out, int outOff) {
        o();
        Pack.d(this.e[0], out, outOff + 0);
        Pack.d(this.e[1], out, outOff + 4);
        Pack.d(this.e[2], out, outOff + 8);
        Pack.d(this.e[3], out, outOff + 12);
        Pack.d(this.e[4], out, outOff + 16);
        Pack.d(this.e[5], out, outOff + 20);
        Pack.d(this.e[6], out, outOff + 24);
        Pack.d(this.e[7], out, outOff + 28);
        reset();
        return 32;
    }

    /* access modifiers changed from: protected */
    public void r(byte[] in, int inOff) {
        int inOff2 = inOff + 1;
        int inOff3 = inOff2 + 1;
        int n = ((in[inOff] & 255) << 24) | ((in[inOff2] & 255) << MappingData.PATH) | ((in[inOff3] & 255) << 8) | (in[inOff3 + 1] & 255);
        int[] iArr = this.f;
        int i2 = this.g;
        iArr[i2] = n;
        int i3 = i2 + 1;
        this.g = i3;
        if (i3 >= 16) {
            p();
        }
    }

    /* access modifiers changed from: protected */
    public void q(long bitLength) {
        int i2 = this.g;
        if (i2 > 14) {
            this.f[i2] = 0;
            this.g = i2 + 1;
            p();
        }
        while (true) {
            int i3 = this.g;
            if (i3 < 14) {
                this.f[i3] = 0;
                this.g = i3 + 1;
            } else {
                int[] iArr = this.f;
                int i4 = i3 + 1;
                this.g = i4;
                iArr[i3] = (int) (bitLength >>> 32);
                this.g = i4 + 1;
                iArr[i4] = (int) bitLength;
                return;
            }
        }
    }

    private int w(int x) {
        return (x ^ ((x << 9) | (x >>> 23))) ^ ((x << 17) | (x >>> 15));
    }

    private int x(int x) {
        return (x ^ ((x << 15) | (x >>> 17))) ^ ((x << 23) | (x >>> 9));
    }

    private int s(int x, int y, int z) {
        return (x ^ y) ^ z;
    }

    private int t(int x, int y, int z) {
        return (x & y) | (x & z) | (y & z);
    }

    private int u(int x, int y, int z) {
        return (x ^ y) ^ z;
    }

    private int v(int x, int y, int z) {
        return (x & y) | ((~x) & z);
    }

    /* access modifiers changed from: protected */
    public void p() {
        int i2;
        int i3;
        int j = 0;
        while (true) {
            if (j >= 16) {
                break;
            }
            this.h[j] = this.f[j];
            j++;
        }
        for (int j2 = 16; j2 < 68; j2++) {
            int[] iArr = this.h;
            int wj3 = iArr[j2 - 3];
            int wj13 = iArr[j2 - 13];
            iArr[j2] = (x((iArr[j2 - 16] ^ iArr[j2 - 9]) ^ ((wj3 << 15) | (wj3 >>> 17))) ^ ((wj13 << 7) | (wj13 >>> 25))) ^ this.h[j2 - 6];
        }
        int j3 = 0;
        while (true) {
            if (j3 >= 64) {
                break;
            }
            int[] iArr2 = this.i;
            int[] iArr3 = this.h;
            iArr2[j3] = iArr3[j3 + 4] ^ iArr3[j3];
            j3++;
        }
        int[] iArr4 = this.e;
        int A = iArr4[0];
        int B = iArr4[1];
        int C = iArr4[2];
        int D = iArr4[3];
        int E = iArr4[4];
        int F = iArr4[5];
        int G = iArr4[6];
        int H = iArr4[7];
        int G2 = G;
        int j4 = 0;
        for (i2 = 16; j4 < i2; i2 = 16) {
            int a12 = (A << 12) | (A >>> 20);
            int s1_ = a12 + E + d[j4];
            int SS1 = (s1_ << 7) | (s1_ >>> 25);
            int TT1 = s(A, B, C) + D + (SS1 ^ a12) + this.i[j4];
            int TT2 = u(E, F, G2) + H + SS1 + this.h[j4];
            D = C;
            C = (B << 9) | (B >>> 23);
            B = A;
            A = TT1;
            H = G2;
            G2 = (F << 19) | (F >>> 13);
            F = E;
            E = w(TT2);
            j4++;
        }
        int j5 = 16;
        for (i3 = 64; j5 < i3; i3 = 64) {
            int a122 = (A << 12) | (A >>> 20);
            int s1_2 = a122 + E + d[j5];
            int SS12 = (s1_2 << 7) | (s1_2 >>> 25);
            int TT12 = t(A, B, C) + D + (SS12 ^ a122) + this.i[j5];
            int TT22 = v(E, F, G2) + H + SS12 + this.h[j5];
            D = C;
            C = (B << 9) | (B >>> 23);
            B = A;
            A = TT12;
            H = G2;
            G2 = (F << 19) | (F >>> 13);
            F = E;
            E = w(TT22);
            j5++;
        }
        int[] iArr5 = this.e;
        iArr5[0] = iArr5[0] ^ A;
        iArr5[1] = iArr5[1] ^ B;
        iArr5[2] = iArr5[2] ^ C;
        iArr5[3] = iArr5[3] ^ D;
        iArr5[4] = iArr5[4] ^ E;
        iArr5[5] = iArr5[5] ^ F;
        iArr5[6] = iArr5[6] ^ G2;
        iArr5[7] = iArr5[7] ^ H;
        this.g = 0;
    }
}
