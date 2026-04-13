package org.spongycastle.crypto.digests;

import org.glassfish.grizzly.http.server.util.MappingData;
import org.spongycastle.util.Memoable;

public class RIPEMD256Digest extends GeneralDigest {
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int[] l = new int[16];
    private int m;

    public RIPEMD256Digest() {
        reset();
    }

    public RIPEMD256Digest(RIPEMD256Digest t) {
        super(t);
        B(t);
    }

    private void B(RIPEMD256Digest t) {
        super.n(t);
        this.d = t.d;
        this.e = t.e;
        this.f = t.f;
        this.g = t.g;
        this.h = t.h;
        this.i = t.i;
        this.j = t.j;
        this.k = t.k;
        int[] iArr = t.l;
        System.arraycopy(iArr, 0, this.l, 0, iArr.length);
        this.m = t.m;
    }

    public String b() {
        return "RIPEMD256";
    }

    public int e() {
        return 32;
    }

    /* access modifiers changed from: protected */
    public void r(byte[] in, int inOff) {
        int[] iArr = this.l;
        int i2 = this.m;
        int i3 = i2 + 1;
        this.m = i3;
        iArr[i2] = (in[inOff] & 255) | ((in[inOff + 1] & 255) << 8) | ((in[inOff + 2] & 255) << MappingData.PATH) | ((in[inOff + 3] & 255) << 24);
        if (i3 == 16) {
            p();
        }
    }

    /* access modifiers changed from: protected */
    public void q(long bitLength) {
        if (this.m > 14) {
            p();
        }
        int[] iArr = this.l;
        iArr[14] = (int) (-1 & bitLength);
        iArr[15] = (int) (bitLength >>> 32);
    }

    private void G(int word, byte[] out, int outOff) {
        out[outOff] = (byte) word;
        out[outOff + 1] = (byte) (word >>> 8);
        out[outOff + 2] = (byte) (word >>> 16);
        out[outOff + 3] = (byte) (word >>> 24);
    }

    public int c(byte[] out, int outOff) {
        o();
        G(this.d, out, outOff);
        G(this.e, out, outOff + 4);
        G(this.f, out, outOff + 8);
        G(this.g, out, outOff + 12);
        G(this.h, out, outOff + 16);
        G(this.i, out, outOff + 20);
        G(this.j, out, outOff + 24);
        G(this.k, out, outOff + 28);
        reset();
        return 32;
    }

    public void reset() {
        super.reset();
        this.d = 1732584193;
        this.e = -271733879;
        this.f = -1732584194;
        this.g = 271733878;
        this.h = 1985229328;
        this.i = -19088744;
        this.j = -1985229329;
        this.k = 19088743;
        this.m = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.l;
            if (i2 != iArr.length) {
                iArr[i2] = 0;
                i2++;
            } else {
                return;
            }
        }
    }

    private int A(int x, int n) {
        return (x << n) | (x >>> (32 - n));
    }

    private int C(int x, int y, int z) {
        return (x ^ y) ^ z;
    }

    private int D(int x, int y, int z) {
        return (x & y) | ((~x) & z);
    }

    private int E(int x, int y, int z) {
        return ((~y) | x) ^ z;
    }

    private int F(int x, int y, int z) {
        return (x & z) | ((~z) & y);
    }

    private int s(int a, int b, int c, int d2, int x, int s) {
        return A(C(b, c, d2) + a + x, s);
    }

    private int t(int a, int b, int c, int d2, int x, int s) {
        return A(D(b, c, d2) + a + x + 1518500249, s);
    }

    private int u(int a, int b, int c, int d2, int x, int s) {
        return A(E(b, c, d2) + a + x + 1859775393, s);
    }

    private int v(int a, int b, int c, int d2, int x, int s) {
        return A(((F(b, c, d2) + a) + x) - 1894007588, s);
    }

    private int w(int a, int b, int c, int d2, int x, int s) {
        return A(C(b, c, d2) + a + x, s);
    }

    private int x(int a, int b, int c, int d2, int x, int s) {
        return A(D(b, c, d2) + a + x + 1836072691, s);
    }

    private int y(int a, int b, int c, int d2, int x, int s) {
        return A(E(b, c, d2) + a + x + 1548603684, s);
    }

    private int z(int a, int b, int c, int d2, int x, int s) {
        return A(F(b, c, d2) + a + x + 1352829926, s);
    }

    /* access modifiers changed from: protected */
    public void p() {
        int a = this.d;
        int b = this.e;
        int c = this.f;
        int d2 = this.g;
        int aa = this.h;
        int bb = this.i;
        int cc = this.j;
        int dd = this.k;
        int i2 = a;
        int a2 = s(a, b, c, d2, this.l[0], 11);
        int d3 = s(d2, a2, b, c, this.l[1], 14);
        int c2 = s(c, d3, a2, b, this.l[2], 15);
        int b2 = s(b, c2, d3, a2, this.l[3], 12);
        int a3 = s(a2, b2, c2, d3, this.l[4], 5);
        int d4 = s(d3, a3, b2, c2, this.l[5], 8);
        int c3 = s(c2, d4, a3, b2, this.l[6], 7);
        int b3 = s(b2, c3, d4, a3, this.l[7], 9);
        int a4 = s(a3, b3, c3, d4, this.l[8], 11);
        int d5 = s(d4, a4, b3, c3, this.l[9], 13);
        int c4 = s(c3, d5, a4, b3, this.l[10], 14);
        int b4 = s(b3, c4, d5, a4, this.l[11], 15);
        int a5 = s(a4, b4, c4, d5, this.l[12], 6);
        int d6 = s(d5, a5, b4, c4, this.l[13], 7);
        int c5 = s(c4, d6, a5, b4, this.l[14], 9);
        int b5 = s(b4, c5, d6, a5, this.l[15], 8);
        int aa2 = z(aa, bb, cc, dd, this.l[5], 8);
        int dd2 = z(dd, aa2, bb, cc, this.l[14], 9);
        int cc2 = z(cc, dd2, aa2, bb, this.l[7], 9);
        int bb2 = z(bb, cc2, dd2, aa2, this.l[0], 11);
        int aa3 = z(aa2, bb2, cc2, dd2, this.l[9], 13);
        int dd3 = z(dd2, aa3, bb2, cc2, this.l[2], 15);
        int cc3 = z(cc2, dd3, aa3, bb2, this.l[11], 15);
        int bb3 = z(bb2, cc3, dd3, aa3, this.l[4], 5);
        int aa4 = z(aa3, bb3, cc3, dd3, this.l[13], 7);
        int dd4 = z(dd3, aa4, bb3, cc3, this.l[6], 7);
        int cc4 = z(cc3, dd4, aa4, bb3, this.l[15], 8);
        int bb4 = z(bb3, cc4, dd4, aa4, this.l[8], 11);
        int aa5 = z(aa4, bb4, cc4, dd4, this.l[1], 14);
        int dd5 = z(dd4, aa5, bb4, cc4, this.l[10], 14);
        int cc5 = z(cc4, dd5, aa5, bb4, this.l[3], 12);
        int bb5 = z(bb4, cc5, dd5, aa5, this.l[12], 6);
        int a6 = t(aa5, b5, c5, d6, this.l[7], 7);
        int d7 = t(d6, a6, b5, c5, this.l[4], 6);
        int c6 = t(c5, d7, a6, b5, this.l[13], 8);
        int b6 = t(b5, c6, d7, a6, this.l[1], 13);
        int a7 = t(a6, b6, c6, d7, this.l[10], 11);
        int d8 = t(d7, a7, b6, c6, this.l[6], 9);
        int c7 = t(c6, d8, a7, b6, this.l[15], 7);
        int b7 = t(b6, c7, d8, a7, this.l[3], 15);
        int a8 = t(a7, b7, c7, d8, this.l[12], 7);
        int d9 = t(d8, a8, b7, c7, this.l[0], 12);
        int c8 = t(c7, d9, a8, b7, this.l[9], 15);
        int b8 = t(b7, c8, d9, a8, this.l[5], 9);
        int a9 = t(a8, b8, c8, d9, this.l[2], 11);
        int d10 = t(d9, a9, b8, c8, this.l[14], 7);
        int c9 = t(c8, d10, a9, b8, this.l[11], 13);
        int b9 = t(b8, c9, d10, a9, this.l[8], 12);
        int aa6 = y(a5, bb5, cc5, dd5, this.l[6], 9);
        int dd6 = y(dd5, aa6, bb5, cc5, this.l[11], 13);
        int cc6 = y(cc5, dd6, aa6, bb5, this.l[3], 15);
        int bb6 = y(bb5, cc6, dd6, aa6, this.l[7], 7);
        int aa7 = y(aa6, bb6, cc6, dd6, this.l[0], 12);
        int dd7 = y(dd6, aa7, bb6, cc6, this.l[13], 8);
        int cc7 = y(cc6, dd7, aa7, bb6, this.l[5], 9);
        int bb7 = y(bb6, cc7, dd7, aa7, this.l[10], 11);
        int aa8 = y(aa7, bb7, cc7, dd7, this.l[14], 7);
        int dd8 = y(dd7, aa8, bb7, cc7, this.l[15], 7);
        int cc8 = y(cc7, dd8, aa8, bb7, this.l[8], 12);
        int bb8 = y(bb7, cc8, dd8, aa8, this.l[12], 7);
        int aa9 = y(aa8, bb8, cc8, dd8, this.l[4], 6);
        int dd9 = y(dd8, aa9, bb8, cc8, this.l[9], 15);
        int cc9 = y(cc8, dd9, aa9, bb8, this.l[1], 13);
        int b10 = y(bb8, cc9, dd9, aa9, this.l[2], 11);
        int bb9 = b9;
        int i3 = d10;
        int a10 = u(a9, b10, c9, i3, this.l[3], 11);
        int d11 = u(i3, a10, b10, c9, this.l[10], 13);
        int c10 = u(c9, d11, a10, b10, this.l[14], 6);
        int b11 = u(b10, c10, d11, a10, this.l[4], 7);
        int a11 = u(a10, b11, c10, d11, this.l[9], 14);
        int d12 = u(d11, a11, b11, c10, this.l[15], 9);
        int c11 = u(c10, d12, a11, b11, this.l[8], 13);
        int b12 = u(b11, c11, d12, a11, this.l[1], 15);
        int a12 = u(a11, b12, c11, d12, this.l[2], 14);
        int d13 = u(d12, a12, b12, c11, this.l[7], 8);
        int c12 = u(c11, d13, a12, b12, this.l[0], 13);
        int b13 = u(b12, c12, d13, a12, this.l[6], 6);
        int a13 = u(a12, b13, c12, d13, this.l[13], 5);
        int d14 = u(d13, a13, b13, c12, this.l[11], 12);
        int c13 = u(c12, d14, a13, b13, this.l[5], 7);
        int b14 = u(b13, c13, d14, a13, this.l[12], 5);
        int aa10 = x(aa9, bb9, cc9, dd9, this.l[15], 9);
        int dd10 = x(dd9, aa10, bb9, cc9, this.l[5], 7);
        int cc10 = x(cc9, dd10, aa10, bb9, this.l[1], 15);
        int bb10 = x(bb9, cc10, dd10, aa10, this.l[3], 11);
        int aa11 = x(aa10, bb10, cc10, dd10, this.l[7], 8);
        int dd11 = x(dd10, aa11, bb10, cc10, this.l[14], 6);
        int cc11 = x(cc10, dd11, aa11, bb10, this.l[6], 6);
        int bb11 = x(bb10, cc11, dd11, aa11, this.l[9], 14);
        int aa12 = x(aa11, bb11, cc11, dd11, this.l[11], 12);
        int dd12 = x(dd11, aa12, bb11, cc11, this.l[8], 13);
        int cc12 = x(cc11, dd12, aa12, bb11, this.l[12], 5);
        int bb12 = x(bb11, cc12, dd12, aa12, this.l[2], 14);
        int aa13 = x(aa12, bb12, cc12, dd12, this.l[10], 13);
        int dd13 = x(dd12, aa13, bb12, cc12, this.l[0], 13);
        int cc13 = x(cc12, dd13, aa13, bb12, this.l[4], 7);
        int bb13 = x(bb12, cc13, dd13, aa13, this.l[13], 5);
        int c14 = cc13;
        int cc14 = c13;
        int a14 = v(a13, b14, c14, d14, this.l[1], 11);
        int d15 = v(d14, a14, b14, c14, this.l[9], 12);
        int c15 = v(c14, d15, a14, b14, this.l[11], 14);
        int b15 = v(b14, c15, d15, a14, this.l[10], 15);
        int a15 = v(a14, b15, c15, d15, this.l[0], 14);
        int d16 = v(d15, a15, b15, c15, this.l[8], 15);
        int c16 = v(c15, d16, a15, b15, this.l[12], 9);
        int b16 = v(b15, c16, d16, a15, this.l[4], 8);
        int a16 = v(a15, b16, c16, d16, this.l[13], 9);
        int d17 = v(d16, a16, b16, c16, this.l[3], 14);
        int c17 = v(c16, d17, a16, b16, this.l[7], 5);
        int b17 = v(b16, c17, d17, a16, this.l[15], 6);
        int a17 = v(a16, b17, c17, d17, this.l[14], 8);
        int d18 = v(d17, a17, b17, c17, this.l[5], 6);
        int c18 = v(c17, d18, a17, b17, this.l[6], 5);
        int b18 = v(b17, c18, d18, a17, this.l[2], 12);
        int aa14 = w(aa13, bb13, cc14, dd13, this.l[8], 15);
        int dd14 = w(dd13, aa14, bb13, cc14, this.l[6], 5);
        int cc15 = w(cc14, dd14, aa14, bb13, this.l[4], 8);
        int bb14 = w(bb13, cc15, dd14, aa14, this.l[1], 11);
        int aa15 = w(aa14, bb14, cc15, dd14, this.l[3], 14);
        int dd15 = w(dd14, aa15, bb14, cc15, this.l[11], 14);
        int cc16 = w(cc15, dd15, aa15, bb14, this.l[15], 6);
        int bb15 = w(bb14, cc16, dd15, aa15, this.l[0], 14);
        int aa16 = w(aa15, bb15, cc16, dd15, this.l[5], 6);
        int dd16 = w(dd15, aa16, bb15, cc16, this.l[12], 9);
        int cc17 = w(cc16, dd16, aa16, bb15, this.l[2], 12);
        int bb16 = w(bb15, cc17, dd16, aa16, this.l[13], 9);
        int aa17 = w(aa16, bb16, cc17, dd16, this.l[9], 12);
        int dd17 = w(dd16, aa17, bb16, cc17, this.l[7], 5);
        int cc18 = w(cc17, dd17, aa17, bb16, this.l[10], 15);
        int bb17 = w(bb16, cc18, dd17, aa17, this.l[14], 8);
        this.d += a17;
        this.e += b18;
        this.f += c18;
        this.g += dd17;
        this.h += aa17;
        this.i += bb17;
        this.j += cc18;
        this.k += d18;
        this.m = 0;
        int i4 = 0;
        while (true) {
            int[] iArr = this.l;
            if (i4 != iArr.length) {
                iArr[i4] = 0;
                i4++;
            } else {
                return;
            }
        }
    }

    public Memoable copy() {
        return new RIPEMD256Digest(this);
    }

    public void m(Memoable other) {
        B((RIPEMD256Digest) other);
    }
}
