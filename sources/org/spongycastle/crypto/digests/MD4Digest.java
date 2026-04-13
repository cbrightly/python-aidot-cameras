package org.spongycastle.crypto.digests;

import org.glassfish.grizzly.http.server.util.MappingData;
import org.spongycastle.util.Memoable;

public class MD4Digest extends GeneralDigest {
    private int d;
    private int e;
    private int f;
    private int g;
    private int[] h = new int[16];
    private int i;

    public MD4Digest() {
        reset();
    }

    public MD4Digest(MD4Digest t) {
        super(t);
        v(t);
    }

    private void v(MD4Digest t) {
        super.n(t);
        this.d = t.d;
        this.e = t.e;
        this.f = t.f;
        this.g = t.g;
        int[] iArr = t.h;
        System.arraycopy(iArr, 0, this.h, 0, iArr.length);
        this.i = t.i;
    }

    public String b() {
        return "MD4";
    }

    public int e() {
        return 16;
    }

    /* access modifiers changed from: protected */
    public void r(byte[] in, int inOff) {
        int[] iArr = this.h;
        int i2 = this.i;
        int i3 = i2 + 1;
        this.i = i3;
        iArr[i2] = (in[inOff] & 255) | ((in[inOff + 1] & 255) << 8) | ((in[inOff + 2] & 255) << MappingData.PATH) | ((in[inOff + 3] & 255) << 24);
        if (i3 == 16) {
            p();
        }
    }

    /* access modifiers changed from: protected */
    public void q(long bitLength) {
        if (this.i > 14) {
            p();
        }
        int[] iArr = this.h;
        iArr[14] = (int) (-1 & bitLength);
        iArr[15] = (int) (bitLength >>> 32);
    }

    private void x(int word, byte[] out, int outOff) {
        out[outOff] = (byte) word;
        out[outOff + 1] = (byte) (word >>> 8);
        out[outOff + 2] = (byte) (word >>> 16);
        out[outOff + 3] = (byte) (word >>> 24);
    }

    public int c(byte[] out, int outOff) {
        o();
        x(this.d, out, outOff);
        x(this.e, out, outOff + 4);
        x(this.f, out, outOff + 8);
        x(this.g, out, outOff + 12);
        reset();
        return 16;
    }

    public void reset() {
        super.reset();
        this.d = 1732584193;
        this.e = -271733879;
        this.f = -1732584194;
        this.g = 271733878;
        this.i = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.h;
            if (i2 != iArr.length) {
                iArr[i2] = 0;
                i2++;
            } else {
                return;
            }
        }
    }

    private int w(int x, int n) {
        return (x << n) | (x >>> (32 - n));
    }

    private int s(int u, int v, int w) {
        return (u & v) | ((~u) & w);
    }

    private int t(int u, int v, int w) {
        return (u & v) | (u & w) | (v & w);
    }

    private int u(int u, int v, int w) {
        return (u ^ v) ^ w;
    }

    /* access modifiers changed from: protected */
    public void p() {
        int a = this.d;
        int b = this.e;
        int c = this.f;
        int d2 = this.g;
        int a2 = w(s(b, c, d2) + a + this.h[0], 3);
        int d3 = w(s(a2, b, c) + d2 + this.h[1], 7);
        int c2 = w(s(d3, a2, b) + c + this.h[2], 11);
        int b2 = w(s(c2, d3, a2) + b + this.h[3], 19);
        int a3 = w(s(b2, c2, d3) + a2 + this.h[4], 3);
        int d4 = w(s(a3, b2, c2) + d3 + this.h[5], 7);
        int c3 = w(s(d4, a3, b2) + c2 + this.h[6], 11);
        int b3 = w(s(c3, d4, a3) + b2 + this.h[7], 19);
        int a4 = w(s(b3, c3, d4) + a3 + this.h[8], 3);
        int d5 = w(s(a4, b3, c3) + d4 + this.h[9], 7);
        int c4 = w(s(d5, a4, b3) + c3 + this.h[10], 11);
        int b4 = w(s(c4, d5, a4) + b3 + this.h[11], 19);
        int a5 = w(s(b4, c4, d5) + a4 + this.h[12], 3);
        int d6 = w(s(a5, b4, c4) + d5 + this.h[13], 7);
        int c5 = w(s(d6, a5, b4) + c4 + this.h[14], 11);
        int b5 = w(s(c5, d6, a5) + b4 + this.h[15], 19);
        int a6 = w(t(b5, c5, d6) + a5 + this.h[0] + 1518500249, 3);
        int d7 = w(t(a6, b5, c5) + d6 + this.h[4] + 1518500249, 5);
        int c6 = w(t(d7, a6, b5) + c5 + this.h[8] + 1518500249, 9);
        int b6 = w(t(c6, d7, a6) + b5 + this.h[12] + 1518500249, 13);
        int a7 = w(t(b6, c6, d7) + a6 + this.h[1] + 1518500249, 3);
        int d8 = w(t(a7, b6, c6) + d7 + this.h[5] + 1518500249, 5);
        int c7 = w(t(d8, a7, b6) + c6 + this.h[9] + 1518500249, 9);
        int b7 = w(t(c7, d8, a7) + b6 + this.h[13] + 1518500249, 13);
        int a8 = w(t(b7, c7, d8) + a7 + this.h[2] + 1518500249, 3);
        int d9 = w(t(a8, b7, c7) + d8 + this.h[6] + 1518500249, 5);
        int c8 = w(t(d9, a8, b7) + c7 + this.h[10] + 1518500249, 9);
        int b8 = w(t(c8, d9, a8) + b7 + this.h[14] + 1518500249, 13);
        int a9 = w(t(b8, c8, d9) + a8 + this.h[3] + 1518500249, 3);
        int d10 = w(t(a9, b8, c8) + d9 + this.h[7] + 1518500249, 5);
        int c9 = w(t(d10, a9, b8) + c8 + this.h[11] + 1518500249, 9);
        int b9 = w(t(c9, d10, a9) + b8 + this.h[15] + 1518500249, 13);
        int a10 = w(u(b9, c9, d10) + a9 + this.h[0] + 1859775393, 3);
        int d11 = w(u(a10, b9, c9) + d10 + this.h[8] + 1859775393, 9);
        int c10 = w(u(d11, a10, b9) + c9 + this.h[4] + 1859775393, 11);
        int b10 = w(u(c10, d11, a10) + b9 + this.h[12] + 1859775393, 15);
        int a11 = w(u(b10, c10, d11) + a10 + this.h[2] + 1859775393, 3);
        int d12 = w(u(a11, b10, c10) + d11 + this.h[10] + 1859775393, 9);
        int c11 = w(u(d12, a11, b10) + c10 + this.h[6] + 1859775393, 11);
        int b11 = w(u(c11, d12, a11) + b10 + this.h[14] + 1859775393, 15);
        int a12 = w(u(b11, c11, d12) + a11 + this.h[1] + 1859775393, 3);
        int d13 = w(u(a12, b11, c11) + d12 + this.h[9] + 1859775393, 9);
        int c12 = w(u(d13, a12, b11) + c11 + this.h[5] + 1859775393, 11);
        int b12 = w(u(c12, d13, a12) + b11 + this.h[13] + 1859775393, 15);
        int a13 = w(u(b12, c12, d13) + a12 + this.h[3] + 1859775393, 3);
        int d14 = w(u(a13, b12, c12) + d13 + this.h[11] + 1859775393, 9);
        int c13 = w(u(d14, a13, b12) + c12 + this.h[7] + 1859775393, 11);
        int b13 = w(u(c13, d14, a13) + b12 + this.h[15] + 1859775393, 15);
        this.d += a13;
        this.e += b13;
        this.f += c13;
        this.g += d14;
        this.i = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.h;
            if (i2 != iArr.length) {
                iArr[i2] = 0;
                i2++;
            } else {
                return;
            }
        }
    }

    public Memoable copy() {
        return new MD4Digest(this);
    }

    public void m(Memoable other) {
        v((MD4Digest) other);
    }
}
