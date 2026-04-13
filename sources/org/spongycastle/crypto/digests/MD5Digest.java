package org.spongycastle.crypto.digests;

import org.glassfish.grizzly.http.server.util.MappingData;
import org.spongycastle.util.Memoable;

public class MD5Digest extends GeneralDigest implements EncodableDigest {
    private int d;
    private int e;
    private int f;
    private int g;
    private int[] h = new int[16];
    private int i;

    public MD5Digest() {
        reset();
    }

    public MD5Digest(MD5Digest t) {
        super(t);
        w(t);
    }

    private void w(MD5Digest t) {
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
        return "MD5";
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

    private void y(int word, byte[] out, int outOff) {
        out[outOff] = (byte) word;
        out[outOff + 1] = (byte) (word >>> 8);
        out[outOff + 2] = (byte) (word >>> 16);
        out[outOff + 3] = (byte) (word >>> 24);
    }

    public int c(byte[] out, int outOff) {
        o();
        y(this.d, out, outOff);
        y(this.e, out, outOff + 4);
        y(this.f, out, outOff + 8);
        y(this.g, out, outOff + 12);
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

    private int x(int x, int n) {
        return (x << n) | (x >>> (32 - n));
    }

    private int s(int u, int v, int w) {
        return (u & v) | ((~u) & w);
    }

    private int t(int u, int v, int w) {
        return (u & w) | ((~w) & v);
    }

    private int u(int u, int v, int w) {
        return (u ^ v) ^ w;
    }

    private int v(int u, int v, int w) {
        return ((~w) | u) ^ v;
    }

    /* access modifiers changed from: protected */
    public void p() {
        int a = this.d;
        int b = this.e;
        int c = this.f;
        int d2 = this.g;
        int a2 = x(((s(b, c, d2) + a) + this.h[0]) - 680876936, 7) + b;
        int d3 = x(((s(a2, b, c) + d2) + this.h[1]) - 389564586, 12) + a2;
        int c2 = x(s(d3, a2, b) + c + this.h[2] + 606105819, 17) + d3;
        int b2 = x(((s(c2, d3, a2) + b) + this.h[3]) - 1044525330, 22) + c2;
        int a3 = x(((s(b2, c2, d3) + a2) + this.h[4]) - 176418897, 7) + b2;
        int d4 = x(s(a3, b2, c2) + d3 + this.h[5] + 1200080426, 12) + a3;
        int c3 = x(((s(d4, a3, b2) + c2) + this.h[6]) - 1473231341, 17) + d4;
        int b3 = x(((s(c3, d4, a3) + b2) + this.h[7]) - 45705983, 22) + c3;
        int a4 = x(s(b3, c3, d4) + a3 + this.h[8] + 1770035416, 7) + b3;
        int d5 = x(((s(a4, b3, c3) + d4) + this.h[9]) - 1958414417, 12) + a4;
        int c4 = x(((s(d5, a4, b3) + c3) + this.h[10]) - 42063, 17) + d5;
        int b4 = x(((s(c4, d5, a4) + b3) + this.h[11]) - 1990404162, 22) + c4;
        int a5 = x(s(b4, c4, d5) + a4 + this.h[12] + 1804603682, 7) + b4;
        int d6 = x(((s(a5, b4, c4) + d5) + this.h[13]) - 40341101, 12) + a5;
        int c5 = x(((s(d6, a5, b4) + c4) + this.h[14]) - 1502002290, 17) + d6;
        int b5 = x(s(c5, d6, a5) + b4 + this.h[15] + 1236535329, 22) + c5;
        int a6 = x(((t(b5, c5, d6) + a5) + this.h[1]) - 165796510, 5) + b5;
        int d7 = x(((t(a6, b5, c5) + d6) + this.h[6]) - 1069501632, 9) + a6;
        int c6 = x(t(d7, a6, b5) + c5 + this.h[11] + 643717713, 14) + d7;
        int b6 = x(((t(c6, d7, a6) + b5) + this.h[0]) - 373897302, 20) + c6;
        int a7 = x(((t(b6, c6, d7) + a6) + this.h[5]) - 701558691, 5) + b6;
        int d8 = x(t(a7, b6, c6) + d7 + this.h[10] + 38016083, 9) + a7;
        int c7 = x(((t(d8, a7, b6) + c6) + this.h[15]) - 660478335, 14) + d8;
        int b7 = x(((t(c7, d8, a7) + b6) + this.h[4]) - 405537848, 20) + c7;
        int a8 = x(t(b7, c7, d8) + a7 + this.h[9] + 568446438, 5) + b7;
        int d9 = x(((t(a8, b7, c7) + d8) + this.h[14]) - 1019803690, 9) + a8;
        int c8 = x(((t(d9, a8, b7) + c7) + this.h[3]) - 187363961, 14) + d9;
        int b8 = x(t(c8, d9, a8) + b7 + this.h[8] + 1163531501, 20) + c8;
        int a9 = x(((t(b8, c8, d9) + a8) + this.h[13]) - 1444681467, 5) + b8;
        int d10 = x(((t(a9, b8, c8) + d9) + this.h[2]) - 51403784, 9) + a9;
        int c9 = x(t(d10, a9, b8) + c8 + this.h[7] + 1735328473, 14) + d10;
        int b9 = x(((t(c9, d10, a9) + b8) + this.h[12]) - 1926607734, 20) + c9;
        int a10 = x(((u(b9, c9, d10) + a9) + this.h[5]) - 378558, 4) + b9;
        int d11 = x(((u(a10, b9, c9) + d10) + this.h[8]) - 2022574463, 11) + a10;
        int c10 = x(u(d11, a10, b9) + c9 + this.h[11] + 1839030562, 16) + d11;
        int b10 = x(((u(c10, d11, a10) + b9) + this.h[14]) - 35309556, 23) + c10;
        int a11 = x(((u(b10, c10, d11) + a10) + this.h[1]) - 1530992060, 4) + b10;
        int d12 = x(u(a11, b10, c10) + d11 + this.h[4] + 1272893353, 11) + a11;
        int c11 = x(((u(d12, a11, b10) + c10) + this.h[7]) - 155497632, 16) + d12;
        int b11 = x(((u(c11, d12, a11) + b10) + this.h[10]) - 1094730640, 23) + c11;
        int a12 = x(u(b11, c11, d12) + a11 + this.h[13] + 681279174, 4) + b11;
        int d13 = x(((u(a12, b11, c11) + d12) + this.h[0]) - 358537222, 11) + a12;
        int c12 = x(((u(d13, a12, b11) + c11) + this.h[3]) - 722521979, 16) + d13;
        int b12 = x(u(c12, d13, a12) + b11 + this.h[6] + 76029189, 23) + c12;
        int a13 = x(((u(b12, c12, d13) + a12) + this.h[9]) - 640364487, 4) + b12;
        int d14 = x(((u(a13, b12, c12) + d13) + this.h[12]) - 421815835, 11) + a13;
        int c13 = x(u(d14, a13, b12) + c12 + this.h[15] + 530742520, 16) + d14;
        int b13 = x(((u(c13, d14, a13) + b12) + this.h[2]) - 995338651, 23) + c13;
        int a14 = x(((v(b13, c13, d14) + a13) + this.h[0]) - 198630844, 6) + b13;
        int d15 = x(v(a14, b13, c13) + d14 + this.h[7] + 1126891415, 10) + a14;
        int c14 = x(((v(d15, a14, b13) + c13) + this.h[14]) - 1416354905, 15) + d15;
        int b14 = x(((v(c14, d15, a14) + b13) + this.h[5]) - 57434055, 21) + c14;
        int a15 = x(v(b14, c14, d15) + a14 + this.h[12] + 1700485571, 6) + b14;
        int d16 = x(((v(a15, b14, c14) + d15) + this.h[3]) - 1894986606, 10) + a15;
        int c15 = x(((v(d16, a15, b14) + c14) + this.h[10]) - 1051523, 15) + d16;
        int b15 = x(((v(c15, d16, a15) + b14) + this.h[1]) - 2054922799, 21) + c15;
        int a16 = x(v(b15, c15, d16) + a15 + this.h[8] + 1873313359, 6) + b15;
        int d17 = x(((v(a16, b15, c15) + d16) + this.h[15]) - 30611744, 10) + a16;
        int c16 = x(((v(d17, a16, b15) + c15) + this.h[6]) - 1560198380, 15) + d17;
        int b16 = x(v(c16, d17, a16) + b15 + this.h[13] + 1309151649, 21) + c16;
        int a17 = x(((v(b16, c16, d17) + a16) + this.h[4]) - 145523070, 6) + b16;
        int d18 = x(((v(a17, b16, c16) + d17) + this.h[11]) - 1120210379, 10) + a17;
        int c17 = x(v(d18, a17, b16) + c16 + this.h[2] + 718787259, 15) + d18;
        this.d += a17;
        this.e += x(((v(c17, d18, a17) + b16) + this.h[9]) - 343485551, 21) + c17;
        this.f += c17;
        this.g += d18;
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
        return new MD5Digest(this);
    }

    public void m(Memoable other) {
        w((MD5Digest) other);
    }
}
