package org.spongycastle.crypto.digests;

import org.glassfish.grizzly.http.server.util.MappingData;
import org.spongycastle.util.Memoable;

public class RIPEMD160Digest extends GeneralDigest {
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int[] i = new int[16];
    private int j;

    public RIPEMD160Digest() {
        reset();
    }

    public RIPEMD160Digest(RIPEMD160Digest t) {
        super(t);
        t(t);
    }

    private void t(RIPEMD160Digest t) {
        super.n(t);
        this.d = t.d;
        this.e = t.e;
        this.f = t.f;
        this.g = t.g;
        this.h = t.h;
        int[] iArr = t.i;
        System.arraycopy(iArr, 0, this.i, 0, iArr.length);
        this.j = t.j;
    }

    public String b() {
        return "RIPEMD160";
    }

    public int e() {
        return 20;
    }

    /* access modifiers changed from: protected */
    public void r(byte[] in, int inOff) {
        int[] iArr = this.i;
        int i2 = this.j;
        int i3 = i2 + 1;
        this.j = i3;
        iArr[i2] = (in[inOff] & 255) | ((in[inOff + 1] & 255) << 8) | ((in[inOff + 2] & 255) << MappingData.PATH) | ((in[inOff + 3] & 255) << 24);
        if (i3 == 16) {
            p();
        }
    }

    /* access modifiers changed from: protected */
    public void q(long bitLength) {
        if (this.j > 14) {
            p();
        }
        int[] iArr = this.i;
        iArr[14] = (int) (-1 & bitLength);
        iArr[15] = (int) (bitLength >>> 32);
    }

    private void z(int word, byte[] out, int outOff) {
        out[outOff] = (byte) word;
        out[outOff + 1] = (byte) (word >>> 8);
        out[outOff + 2] = (byte) (word >>> 16);
        out[outOff + 3] = (byte) (word >>> 24);
    }

    public int c(byte[] out, int outOff) {
        o();
        z(this.d, out, outOff);
        z(this.e, out, outOff + 4);
        z(this.f, out, outOff + 8);
        z(this.g, out, outOff + 12);
        z(this.h, out, outOff + 16);
        reset();
        return 20;
    }

    public void reset() {
        super.reset();
        this.d = 1732584193;
        this.e = -271733879;
        this.f = -1732584194;
        this.g = 271733878;
        this.h = -1009589776;
        this.j = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.i;
            if (i2 != iArr.length) {
                iArr[i2] = 0;
                i2++;
            } else {
                return;
            }
        }
    }

    private int s(int x, int n) {
        return (x << n) | (x >>> (32 - n));
    }

    private int u(int x, int y, int z) {
        return (x ^ y) ^ z;
    }

    private int v(int x, int y, int z) {
        return (x & y) | ((~x) & z);
    }

    private int w(int x, int y, int z) {
        return ((~y) | x) ^ z;
    }

    private int x(int x, int y, int z) {
        return (x & z) | ((~z) & y);
    }

    private int y(int x, int y, int z) {
        return ((~z) | y) ^ x;
    }

    /* access modifiers changed from: protected */
    public void p() {
        int a = this.d;
        int b = this.e;
        int bb = b;
        int c = this.f;
        int cc = c;
        int d2 = this.g;
        int dd = d2;
        int e2 = this.h;
        int ee = e2;
        int a2 = s(u(b, c, d2) + a + this.i[0], 11) + e2;
        int c2 = s(c, 10);
        int e3 = s(u(a2, b, c2) + e2 + this.i[1], 14) + d2;
        int b2 = s(b, 10);
        int d3 = s(u(e3, a2, b2) + d2 + this.i[2], 15) + c2;
        int a3 = s(a2, 10);
        int c3 = s(u(d3, e3, a3) + c2 + this.i[3], 12) + b2;
        int e4 = s(e3, 10);
        int b3 = s(u(c3, d3, e4) + b2 + this.i[4], 5) + a3;
        int b4 = s(d3, 10);
        int a4 = s(u(b3, c3, b4) + a3 + this.i[5], 8) + e4;
        int a5 = s(c3, 10);
        int e5 = s(u(a4, b3, a5) + e4 + this.i[6], 7) + b4;
        int b5 = s(b3, 10);
        int d4 = s(u(e5, a4, b5) + b4 + this.i[7], 9) + a5;
        int a6 = s(a4, 10);
        int c4 = s(u(d4, e5, a6) + a5 + this.i[8], 11) + b5;
        int e6 = s(e5, 10);
        int b6 = s(u(c4, d4, e6) + b5 + this.i[9], 13) + a6;
        int b7 = s(d4, 10);
        int a7 = s(u(b6, c4, b7) + a6 + this.i[10], 14) + e6;
        int a8 = s(c4, 10);
        int e7 = s(u(a7, b6, a8) + e6 + this.i[11], 15) + b7;
        int b8 = s(b6, 10);
        int d5 = s(u(e7, a7, b8) + b7 + this.i[12], 6) + a8;
        int a9 = s(a7, 10);
        int c5 = s(u(d5, e7, a9) + a8 + this.i[13], 7) + b8;
        int e8 = s(e7, 10);
        int b9 = s(u(c5, d5, e8) + b8 + this.i[14], 9) + a9;
        int b10 = s(d5, 10);
        int a10 = s(u(b9, c5, b10) + a9 + this.i[15], 8) + e8;
        int a11 = s(c5, 10);
        int aa = s(y(bb, cc, dd) + a + this.i[5] + 1352829926, 8) + ee;
        int cc2 = s(cc, 10);
        int ee2 = s(y(aa, bb, cc2) + ee + this.i[14] + 1352829926, 9) + dd;
        int bb2 = s(bb, 10);
        int dd2 = s(y(ee2, aa, bb2) + dd + this.i[7] + 1352829926, 9) + cc2;
        int aa2 = s(aa, 10);
        int cc3 = s(y(dd2, ee2, aa2) + cc2 + this.i[0] + 1352829926, 11) + bb2;
        int ee3 = s(ee2, 10);
        int bb3 = s(y(cc3, dd2, ee3) + bb2 + this.i[9] + 1352829926, 13) + aa2;
        int dd3 = s(dd2, 10);
        int aa3 = s(y(bb3, cc3, dd3) + aa2 + this.i[2] + 1352829926, 15) + ee3;
        int cc4 = s(cc3, 10);
        int ee4 = s(y(aa3, bb3, cc4) + ee3 + this.i[11] + 1352829926, 15) + dd3;
        int bb4 = s(bb3, 10);
        int dd4 = s(y(ee4, aa3, bb4) + dd3 + this.i[4] + 1352829926, 5) + cc4;
        int aa4 = s(aa3, 10);
        int cc5 = s(y(dd4, ee4, aa4) + cc4 + this.i[13] + 1352829926, 7) + bb4;
        int ee5 = s(ee4, 10);
        int bb5 = s(y(cc5, dd4, ee5) + bb4 + this.i[6] + 1352829926, 7) + aa4;
        int dd5 = s(dd4, 10);
        int aa5 = s(y(bb5, cc5, dd5) + aa4 + this.i[15] + 1352829926, 8) + ee5;
        int cc6 = s(cc5, 10);
        int ee6 = s(y(aa5, bb5, cc6) + ee5 + this.i[8] + 1352829926, 11) + dd5;
        int bb6 = s(bb5, 10);
        int dd6 = s(y(ee6, aa5, bb6) + dd5 + this.i[1] + 1352829926, 14) + cc6;
        int aa6 = s(aa5, 10);
        int cc7 = s(y(dd6, ee6, aa6) + cc6 + this.i[10] + 1352829926, 14) + bb6;
        int ee7 = s(ee6, 10);
        int bb7 = s(y(cc7, dd6, ee7) + bb6 + this.i[3] + 1352829926, 12) + aa6;
        int dd7 = s(dd6, 10);
        int aa7 = s(y(bb7, cc7, dd7) + aa6 + this.i[12] + 1352829926, 6) + ee7;
        int cc8 = s(cc7, 10);
        int e9 = s(v(a10, b9, a11) + e8 + this.i[7] + 1518500249, 7) + b10;
        int b11 = s(b9, 10);
        int d6 = s(v(e9, a10, b11) + b10 + this.i[4] + 1518500249, 6) + a11;
        int a12 = s(a10, 10);
        int c6 = s(v(d6, e9, a12) + a11 + this.i[13] + 1518500249, 8) + b11;
        int e10 = s(e9, 10);
        int b12 = s(v(c6, d6, e10) + b11 + this.i[1] + 1518500249, 13) + a12;
        int b13 = s(d6, 10);
        int a13 = s(v(b12, c6, b13) + a12 + this.i[10] + 1518500249, 11) + e10;
        int a14 = s(c6, 10);
        int e11 = s(v(a13, b12, a14) + e10 + this.i[6] + 1518500249, 9) + b13;
        int b14 = s(b12, 10);
        int d7 = s(v(e11, a13, b14) + b13 + this.i[15] + 1518500249, 7) + a14;
        int a15 = s(a13, 10);
        int c7 = s(v(d7, e11, a15) + a14 + this.i[3] + 1518500249, 15) + b14;
        int e12 = s(e11, 10);
        int b15 = s(v(c7, d7, e12) + b14 + this.i[12] + 1518500249, 7) + a15;
        int b16 = s(d7, 10);
        int a16 = s(v(b15, c7, b16) + a15 + this.i[0] + 1518500249, 12) + e12;
        int a17 = s(c7, 10);
        int e13 = s(v(a16, b15, a17) + e12 + this.i[9] + 1518500249, 15) + b16;
        int b17 = s(b15, 10);
        int d8 = s(v(e13, a16, b17) + b16 + this.i[5] + 1518500249, 9) + a17;
        int a18 = s(a16, 10);
        int c8 = s(v(d8, e13, a18) + a17 + this.i[2] + 1518500249, 11) + b17;
        int e14 = s(e13, 10);
        int b18 = s(v(c8, d8, e14) + b17 + this.i[14] + 1518500249, 7) + a18;
        int b19 = s(d8, 10);
        int a19 = s(v(b18, c8, b19) + a18 + this.i[11] + 1518500249, 13) + e14;
        int a20 = s(c8, 10);
        int e15 = s(v(a19, b18, a20) + e14 + this.i[8] + 1518500249, 12) + b19;
        int b20 = s(b18, 10);
        int ee8 = s(x(aa7, bb7, cc8) + ee7 + this.i[6] + 1548603684, 9) + dd7;
        int bb8 = s(bb7, 10);
        int dd8 = s(x(ee8, aa7, bb8) + dd7 + this.i[11] + 1548603684, 13) + cc8;
        int aa8 = s(aa7, 10);
        int cc9 = s(x(dd8, ee8, aa8) + cc8 + this.i[3] + 1548603684, 15) + bb8;
        int ee9 = s(ee8, 10);
        int bb9 = s(x(cc9, dd8, ee9) + bb8 + this.i[7] + 1548603684, 7) + aa8;
        int dd9 = s(dd8, 10);
        int aa9 = s(x(bb9, cc9, dd9) + aa8 + this.i[0] + 1548603684, 12) + ee9;
        int cc10 = s(cc9, 10);
        int ee10 = s(x(aa9, bb9, cc10) + ee9 + this.i[13] + 1548603684, 8) + dd9;
        int bb10 = s(bb9, 10);
        int dd10 = s(x(ee10, aa9, bb10) + dd9 + this.i[5] + 1548603684, 9) + cc10;
        int aa10 = s(aa9, 10);
        int cc11 = s(x(dd10, ee10, aa10) + cc10 + this.i[10] + 1548603684, 11) + bb10;
        int ee11 = s(ee10, 10);
        int bb11 = s(x(cc11, dd10, ee11) + bb10 + this.i[14] + 1548603684, 7) + aa10;
        int dd11 = s(dd10, 10);
        int aa11 = s(x(bb11, cc11, dd11) + aa10 + this.i[15] + 1548603684, 7) + ee11;
        int cc12 = s(cc11, 10);
        int ee12 = s(x(aa11, bb11, cc12) + ee11 + this.i[8] + 1548603684, 12) + dd11;
        int bb12 = s(bb11, 10);
        int dd12 = s(x(ee12, aa11, bb12) + dd11 + this.i[12] + 1548603684, 7) + cc12;
        int aa12 = s(aa11, 10);
        int cc13 = s(x(dd12, ee12, aa12) + cc12 + this.i[4] + 1548603684, 6) + bb12;
        int ee13 = s(ee12, 10);
        int bb13 = s(x(cc13, dd12, ee13) + bb12 + this.i[9] + 1548603684, 15) + aa12;
        int dd13 = s(dd12, 10);
        int aa13 = s(x(bb13, cc13, dd13) + aa12 + this.i[1] + 1548603684, 13) + ee13;
        int cc14 = s(cc13, 10);
        int ee14 = s(x(aa13, bb13, cc14) + ee13 + this.i[2] + 1548603684, 11) + dd13;
        int bb14 = s(bb13, 10);
        int d9 = s(w(e15, a19, b20) + b19 + this.i[3] + 1859775393, 11) + a20;
        int a21 = s(a19, 10);
        int c9 = s(w(d9, e15, a21) + a20 + this.i[10] + 1859775393, 13) + b20;
        int e16 = s(e15, 10);
        int b21 = s(w(c9, d9, e16) + b20 + this.i[14] + 1859775393, 6) + a21;
        int b22 = s(d9, 10);
        int a22 = s(w(b21, c9, b22) + a21 + this.i[4] + 1859775393, 7) + e16;
        int a23 = s(c9, 10);
        int e17 = s(w(a22, b21, a23) + e16 + this.i[9] + 1859775393, 14) + b22;
        int b23 = s(b21, 10);
        int d10 = s(w(e17, a22, b23) + b22 + this.i[15] + 1859775393, 9) + a23;
        int a24 = s(a22, 10);
        int c10 = s(w(d10, e17, a24) + a23 + this.i[8] + 1859775393, 13) + b23;
        int e18 = s(e17, 10);
        int b24 = s(w(c10, d10, e18) + b23 + this.i[1] + 1859775393, 15) + a24;
        int b25 = s(d10, 10);
        int a25 = s(w(b24, c10, b25) + a24 + this.i[2] + 1859775393, 14) + e18;
        int a26 = s(c10, 10);
        int e19 = s(w(a25, b24, a26) + e18 + this.i[7] + 1859775393, 8) + b25;
        int b26 = s(b24, 10);
        int d11 = s(w(e19, a25, b26) + b25 + this.i[0] + 1859775393, 13) + a26;
        int a27 = s(a25, 10);
        int c11 = s(w(d11, e19, a27) + a26 + this.i[6] + 1859775393, 6) + b26;
        int e20 = s(e19, 10);
        int b27 = s(w(c11, d11, e20) + b26 + this.i[13] + 1859775393, 5) + a27;
        int b28 = s(d11, 10);
        int a28 = s(w(b27, c11, b28) + a27 + this.i[11] + 1859775393, 12) + e20;
        int a29 = s(c11, 10);
        int e21 = s(w(a28, b27, a29) + e20 + this.i[5] + 1859775393, 7) + b28;
        int b29 = s(b27, 10);
        int d12 = s(w(e21, a28, b29) + b28 + this.i[12] + 1859775393, 5) + a29;
        int a30 = s(a28, 10);
        int dd14 = s(w(ee14, aa13, bb14) + dd13 + this.i[15] + 1836072691, 9) + cc14;
        int aa14 = s(aa13, 10);
        int cc15 = s(w(dd14, ee14, aa14) + cc14 + this.i[5] + 1836072691, 7) + bb14;
        int ee15 = s(ee14, 10);
        int bb15 = s(w(cc15, dd14, ee15) + bb14 + this.i[1] + 1836072691, 15) + aa14;
        int dd15 = s(dd14, 10);
        int aa15 = s(w(bb15, cc15, dd15) + aa14 + this.i[3] + 1836072691, 11) + ee15;
        int cc16 = s(cc15, 10);
        int ee16 = s(w(aa15, bb15, cc16) + ee15 + this.i[7] + 1836072691, 8) + dd15;
        int bb16 = s(bb15, 10);
        int dd16 = s(w(ee16, aa15, bb16) + dd15 + this.i[14] + 1836072691, 6) + cc16;
        int aa16 = s(aa15, 10);
        int cc17 = s(w(dd16, ee16, aa16) + cc16 + this.i[6] + 1836072691, 6) + bb16;
        int ee17 = s(ee16, 10);
        int bb17 = s(w(cc17, dd16, ee17) + bb16 + this.i[9] + 1836072691, 14) + aa16;
        int dd17 = s(dd16, 10);
        int aa17 = s(w(bb17, cc17, dd17) + aa16 + this.i[11] + 1836072691, 12) + ee17;
        int cc18 = s(cc17, 10);
        int ee18 = s(w(aa17, bb17, cc18) + ee17 + this.i[8] + 1836072691, 13) + dd17;
        int bb18 = s(bb17, 10);
        int dd18 = s(w(ee18, aa17, bb18) + dd17 + this.i[12] + 1836072691, 5) + cc18;
        int aa18 = s(aa17, 10);
        int cc19 = s(w(dd18, ee18, aa18) + cc18 + this.i[2] + 1836072691, 14) + bb18;
        int ee19 = s(ee18, 10);
        int bb19 = s(w(cc19, dd18, ee19) + bb18 + this.i[10] + 1836072691, 13) + aa18;
        int dd19 = s(dd18, 10);
        int aa19 = s(w(bb19, cc19, dd19) + aa18 + this.i[0] + 1836072691, 13) + ee19;
        int cc20 = s(cc19, 10);
        int ee20 = s(w(aa19, bb19, cc20) + ee19 + this.i[4] + 1836072691, 7) + dd19;
        int bb20 = s(bb19, 10);
        int dd20 = s(w(ee20, aa19, bb20) + dd19 + this.i[13] + 1836072691, 5) + cc20;
        int aa20 = s(aa19, 10);
        int c12 = s(((x(d12, e21, a30) + a29) + this.i[1]) - 1894007588, 11) + b29;
        int e22 = s(e21, 10);
        int b30 = s(((x(c12, d12, e22) + b29) + this.i[9]) - 1894007588, 12) + a30;
        int b31 = s(d12, 10);
        int a31 = s(((x(b30, c12, b31) + a30) + this.i[11]) - 1894007588, 14) + e22;
        int a32 = s(c12, 10);
        int e23 = s(((x(a31, b30, a32) + e22) + this.i[10]) - 1894007588, 15) + b31;
        int b32 = s(b30, 10);
        int d13 = s(((x(e23, a31, b32) + b31) + this.i[0]) - 1894007588, 14) + a32;
        int a33 = s(a31, 10);
        int c13 = s(((x(d13, e23, a33) + a32) + this.i[8]) - 1894007588, 15) + b32;
        int e24 = s(e23, 10);
        int b33 = s(((x(c13, d13, e24) + b32) + this.i[12]) - 1894007588, 9) + a33;
        int b34 = s(d13, 10);
        int a34 = s(((x(b33, c13, b34) + a33) + this.i[4]) - 1894007588, 8) + e24;
        int a35 = s(c13, 10);
        int e25 = s(((x(a34, b33, a35) + e24) + this.i[13]) - 1894007588, 9) + b34;
        int b35 = s(b33, 10);
        int d14 = s(((x(e25, a34, b35) + b34) + this.i[3]) - 1894007588, 14) + a35;
        int a36 = s(a34, 10);
        int c14 = s(((x(d14, e25, a36) + a35) + this.i[7]) - 1894007588, 5) + b35;
        int e26 = s(e25, 10);
        int b36 = s(((x(c14, d14, e26) + b35) + this.i[15]) - 1894007588, 6) + a36;
        int b37 = s(d14, 10);
        int a37 = s(((x(b36, c14, b37) + a36) + this.i[14]) - 1894007588, 8) + e26;
        int a38 = s(c14, 10);
        int e27 = s(((x(a37, b36, a38) + e26) + this.i[5]) - 1894007588, 6) + b37;
        int b38 = s(b36, 10);
        int d15 = s(((x(e27, a37, b38) + b37) + this.i[6]) - 1894007588, 5) + a38;
        int a39 = s(a37, 10);
        int c15 = s(((x(d15, e27, a39) + a38) + this.i[2]) - 1894007588, 12) + b38;
        int e28 = s(e27, 10);
        int cc21 = s(v(dd20, ee20, aa20) + cc20 + this.i[8] + 2053994217, 15) + bb20;
        int ee21 = s(ee20, 10);
        int bb21 = s(v(cc21, dd20, ee21) + bb20 + this.i[6] + 2053994217, 5) + aa20;
        int dd21 = s(dd20, 10);
        int aa21 = s(v(bb21, cc21, dd21) + aa20 + this.i[4] + 2053994217, 8) + ee21;
        int cc22 = s(cc21, 10);
        int ee22 = s(v(aa21, bb21, cc22) + ee21 + this.i[1] + 2053994217, 11) + dd21;
        int bb22 = s(bb21, 10);
        int dd22 = s(v(ee22, aa21, bb22) + dd21 + this.i[3] + 2053994217, 14) + cc22;
        int aa22 = s(aa21, 10);
        int cc23 = s(v(dd22, ee22, aa22) + cc22 + this.i[11] + 2053994217, 14) + bb22;
        int ee23 = s(ee22, 10);
        int bb23 = s(v(cc23, dd22, ee23) + bb22 + this.i[15] + 2053994217, 6) + aa22;
        int dd23 = s(dd22, 10);
        int aa23 = s(v(bb23, cc23, dd23) + aa22 + this.i[0] + 2053994217, 14) + ee23;
        int cc24 = s(cc23, 10);
        int ee24 = s(v(aa23, bb23, cc24) + ee23 + this.i[5] + 2053994217, 6) + dd23;
        int bb24 = s(bb23, 10);
        int dd24 = s(v(ee24, aa23, bb24) + dd23 + this.i[12] + 2053994217, 9) + cc24;
        int aa24 = s(aa23, 10);
        int cc25 = s(v(dd24, ee24, aa24) + cc24 + this.i[2] + 2053994217, 12) + bb24;
        int ee25 = s(ee24, 10);
        int bb25 = s(v(cc25, dd24, ee25) + bb24 + this.i[13] + 2053994217, 9) + aa24;
        int dd25 = s(dd24, 10);
        int aa25 = s(v(bb25, cc25, dd25) + aa24 + this.i[9] + 2053994217, 12) + ee25;
        int cc26 = s(cc25, 10);
        int ee26 = s(v(aa25, bb25, cc26) + ee25 + this.i[7] + 2053994217, 5) + dd25;
        int bb26 = s(bb25, 10);
        int dd26 = s(v(ee26, aa25, bb26) + dd25 + this.i[10] + 2053994217, 15) + cc26;
        int aa26 = s(aa25, 10);
        int cc27 = s(v(dd26, ee26, aa26) + cc26 + this.i[14] + 2053994217, 8) + bb26;
        int ee27 = s(ee26, 10);
        int b39 = s(((y(c15, d15, e28) + b38) + this.i[4]) - 1454113458, 9) + a39;
        int b40 = s(d15, 10);
        int a40 = s(((y(b39, c15, b40) + a39) + this.i[0]) - 1454113458, 15) + e28;
        int a41 = s(c15, 10);
        int e29 = s(((y(a40, b39, a41) + e28) + this.i[5]) - 1454113458, 5) + b40;
        int b41 = s(b39, 10);
        int d16 = s(((y(e29, a40, b41) + b40) + this.i[9]) - 1454113458, 11) + a41;
        int a42 = s(a40, 10);
        int c16 = s(((y(d16, e29, a42) + a41) + this.i[7]) - 1454113458, 6) + b41;
        int e30 = s(e29, 10);
        int b42 = s(((y(c16, d16, e30) + b41) + this.i[12]) - 1454113458, 8) + a42;
        int b43 = s(d16, 10);
        int a43 = s(((y(b42, c16, b43) + a42) + this.i[2]) - 1454113458, 13) + e30;
        int a44 = s(c16, 10);
        int e31 = s(((y(a43, b42, a44) + e30) + this.i[10]) - 1454113458, 12) + b43;
        int b44 = s(b42, 10);
        int d17 = s(((y(e31, a43, b44) + b43) + this.i[14]) - 1454113458, 5) + a44;
        int a45 = s(a43, 10);
        int c17 = s(((y(d17, e31, a45) + a44) + this.i[1]) - 1454113458, 12) + b44;
        int e32 = s(e31, 10);
        int b45 = s(((y(c17, d17, e32) + b44) + this.i[3]) - 1454113458, 13) + a45;
        int b46 = s(d17, 10);
        int a46 = s(((y(b45, c17, b46) + a45) + this.i[8]) - 1454113458, 14) + e32;
        int a47 = s(c17, 10);
        int e33 = s(((y(a46, b45, a47) + e32) + this.i[11]) - 1454113458, 11) + b46;
        int b47 = s(b45, 10);
        int d18 = s(((y(e33, a46, b47) + b46) + this.i[6]) - 1454113458, 8) + a47;
        int a48 = s(a46, 10);
        int c18 = s(((y(d18, e33, a48) + a47) + this.i[15]) - 1454113458, 5) + b47;
        int e34 = s(e33, 10);
        int b48 = s(d18, 10);
        int bb27 = s(u(cc27, dd26, ee27) + bb26 + this.i[12], 8) + aa26;
        int dd27 = s(dd26, 10);
        int aa27 = s(u(bb27, cc27, dd27) + aa26 + this.i[15], 5) + ee27;
        int cc28 = s(cc27, 10);
        int ee28 = s(u(aa27, bb27, cc28) + ee27 + this.i[10], 12) + dd27;
        int bb28 = s(bb27, 10);
        int dd28 = s(u(ee28, aa27, bb28) + dd27 + this.i[4], 9) + cc28;
        int aa28 = s(aa27, 10);
        int cc29 = s(u(dd28, ee28, aa28) + cc28 + this.i[1], 12) + bb28;
        int ee29 = s(ee28, 10);
        int bb29 = s(u(cc29, dd28, ee29) + bb28 + this.i[5], 5) + aa28;
        int dd29 = s(dd28, 10);
        int aa29 = s(u(bb29, cc29, dd29) + aa28 + this.i[8], 14) + ee29;
        int cc30 = s(cc29, 10);
        int ee30 = s(u(aa29, bb29, cc30) + ee29 + this.i[7], 6) + dd29;
        int bb30 = s(bb29, 10);
        int dd30 = s(u(ee30, aa29, bb30) + dd29 + this.i[6], 8) + cc30;
        int aa30 = s(aa29, 10);
        int cc31 = s(u(dd30, ee30, aa30) + cc30 + this.i[2], 13) + bb30;
        int ee31 = s(ee30, 10);
        int bb31 = s(u(cc31, dd30, ee31) + bb30 + this.i[13], 6) + aa30;
        int dd31 = s(dd30, 10);
        int aa31 = s(u(bb31, cc31, dd31) + aa30 + this.i[14], 5) + ee31;
        int cc32 = s(cc31, 10);
        int ee32 = s(u(aa31, bb31, cc32) + ee31 + this.i[0], 15) + dd31;
        int bb32 = s(bb31, 10);
        int dd32 = s(u(ee32, aa31, bb32) + dd31 + this.i[3], 13) + cc32;
        int aa32 = s(aa31, 10);
        int cc33 = s(u(dd32, ee32, aa32) + cc32 + this.i[9], 11) + bb32;
        int ee33 = s(ee32, 10);
        int dd33 = s(dd32, 10) + this.e + c18;
        this.e = this.f + b48 + ee33;
        this.f = this.g + e34 + aa32;
        this.g = this.h + a48 + s(u(cc33, dd32, ee33) + bb32 + this.i[11], 11) + aa32;
        this.h = this.d + s(((y(c18, d18, e34) + b47) + this.i[13]) - 1454113458, 6) + a48 + cc33;
        this.d = dd33;
        this.j = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.i;
            if (i2 != iArr.length) {
                iArr[i2] = 0;
                i2++;
            } else {
                return;
            }
        }
    }

    public Memoable copy() {
        return new RIPEMD160Digest(this);
    }

    public void m(Memoable other) {
        t((RIPEMD160Digest) other);
    }
}
