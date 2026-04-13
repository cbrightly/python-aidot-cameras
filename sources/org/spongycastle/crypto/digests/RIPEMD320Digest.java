package org.spongycastle.crypto.digests;

import org.glassfish.grizzly.http.server.util.MappingData;
import org.spongycastle.util.Memoable;

public class RIPEMD320Digest extends GeneralDigest {
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int[] n = new int[16];
    private int o;

    public RIPEMD320Digest() {
        reset();
    }

    public RIPEMD320Digest(RIPEMD320Digest t) {
        super(t);
        t(t);
    }

    private void t(RIPEMD320Digest t) {
        super.n(t);
        this.d = t.d;
        this.e = t.e;
        this.f = t.f;
        this.g = t.g;
        this.h = t.h;
        this.i = t.i;
        this.j = t.j;
        this.k = t.k;
        this.l = t.l;
        this.m = t.m;
        int[] iArr = t.n;
        System.arraycopy(iArr, 0, this.n, 0, iArr.length);
        this.o = t.o;
    }

    public String b() {
        return "RIPEMD320";
    }

    public int e() {
        return 40;
    }

    /* access modifiers changed from: protected */
    public void r(byte[] in, int inOff) {
        int[] iArr = this.n;
        int i2 = this.o;
        int i3 = i2 + 1;
        this.o = i3;
        iArr[i2] = (in[inOff] & 255) | ((in[inOff + 1] & 255) << 8) | ((in[inOff + 2] & 255) << MappingData.PATH) | ((in[inOff + 3] & 255) << 24);
        if (i3 == 16) {
            p();
        }
    }

    /* access modifiers changed from: protected */
    public void q(long bitLength) {
        if (this.o > 14) {
            p();
        }
        int[] iArr = this.n;
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
        z(this.i, out, outOff + 20);
        z(this.j, out, outOff + 24);
        z(this.k, out, outOff + 28);
        z(this.l, out, outOff + 32);
        z(this.m, out, outOff + 36);
        reset();
        return 40;
    }

    public void reset() {
        super.reset();
        this.d = 1732584193;
        this.e = -271733879;
        this.f = -1732584194;
        this.g = 271733878;
        this.h = -1009589776;
        this.i = 1985229328;
        this.j = -19088744;
        this.k = -1985229329;
        this.l = 19088743;
        this.m = 1009589775;
        this.o = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.n;
            if (i2 != iArr.length) {
                iArr[i2] = 0;
                i2++;
            } else {
                return;
            }
        }
    }

    private int s(int x, int n2) {
        return (x << n2) | (x >>> (32 - n2));
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
        int c = this.f;
        int d2 = this.g;
        int e2 = this.h;
        int aa = this.i;
        int bb = this.j;
        int cc = this.k;
        int dd = this.l;
        int ee = this.m;
        int a2 = s(u(b, c, d2) + a + this.n[0], 11) + e2;
        int c2 = s(c, 10);
        int e3 = s(u(a2, b, c2) + e2 + this.n[1], 14) + d2;
        int b2 = s(b, 10);
        int d3 = s(u(e3, a2, b2) + d2 + this.n[2], 15) + c2;
        int a3 = s(a2, 10);
        int c3 = s(u(d3, e3, a3) + c2 + this.n[3], 12) + b2;
        int e4 = s(e3, 10);
        int b3 = s(u(c3, d3, e4) + b2 + this.n[4], 5) + a3;
        int b4 = s(d3, 10);
        int a4 = s(u(b3, c3, b4) + a3 + this.n[5], 8) + e4;
        int a5 = s(c3, 10);
        int e5 = s(u(a4, b3, a5) + e4 + this.n[6], 7) + b4;
        int b5 = s(b3, 10);
        int d4 = s(u(e5, a4, b5) + b4 + this.n[7], 9) + a5;
        int a6 = s(a4, 10);
        int c4 = s(u(d4, e5, a6) + a5 + this.n[8], 11) + b5;
        int e6 = s(e5, 10);
        int b6 = s(u(c4, d4, e6) + b5 + this.n[9], 13) + a6;
        int b7 = s(d4, 10);
        int a7 = s(u(b6, c4, b7) + a6 + this.n[10], 14) + e6;
        int a8 = s(c4, 10);
        int e7 = s(u(a7, b6, a8) + e6 + this.n[11], 15) + b7;
        int b8 = s(b6, 10);
        int d5 = s(u(e7, a7, b8) + b7 + this.n[12], 6) + a8;
        int a9 = s(a7, 10);
        int c5 = s(u(d5, e7, a9) + a8 + this.n[13], 7) + b8;
        int e8 = s(e7, 10);
        int b9 = s(u(c5, d5, e8) + b8 + this.n[14], 9) + a9;
        int b10 = s(d5, 10);
        int a10 = s(c5, 10);
        int aa2 = s(y(bb, cc, dd) + aa + this.n[5] + 1352829926, 8) + ee;
        int cc2 = s(cc, 10);
        int ee2 = s(y(aa2, bb, cc2) + ee + this.n[14] + 1352829926, 9) + dd;
        int bb2 = s(bb, 10);
        int dd2 = s(y(ee2, aa2, bb2) + dd + this.n[7] + 1352829926, 9) + cc2;
        int aa3 = s(aa2, 10);
        int cc3 = s(y(dd2, ee2, aa3) + cc2 + this.n[0] + 1352829926, 11) + bb2;
        int ee3 = s(ee2, 10);
        int bb3 = s(y(cc3, dd2, ee3) + bb2 + this.n[9] + 1352829926, 13) + aa3;
        int dd3 = s(dd2, 10);
        int aa4 = s(y(bb3, cc3, dd3) + aa3 + this.n[2] + 1352829926, 15) + ee3;
        int cc4 = s(cc3, 10);
        int ee4 = s(y(aa4, bb3, cc4) + ee3 + this.n[11] + 1352829926, 15) + dd3;
        int bb4 = s(bb3, 10);
        int dd4 = s(y(ee4, aa4, bb4) + dd3 + this.n[4] + 1352829926, 5) + cc4;
        int aa5 = s(aa4, 10);
        int cc5 = s(y(dd4, ee4, aa5) + cc4 + this.n[13] + 1352829926, 7) + bb4;
        int ee5 = s(ee4, 10);
        int bb5 = s(y(cc5, dd4, ee5) + bb4 + this.n[6] + 1352829926, 7) + aa5;
        int dd5 = s(dd4, 10);
        int aa6 = s(y(bb5, cc5, dd5) + aa5 + this.n[15] + 1352829926, 8) + ee5;
        int cc6 = s(cc5, 10);
        int ee6 = s(y(aa6, bb5, cc6) + ee5 + this.n[8] + 1352829926, 11) + dd5;
        int bb6 = s(bb5, 10);
        int dd6 = s(y(ee6, aa6, bb6) + dd5 + this.n[1] + 1352829926, 14) + cc6;
        int aa7 = s(aa6, 10);
        int cc7 = s(y(dd6, ee6, aa7) + cc6 + this.n[10] + 1352829926, 14) + bb6;
        int ee7 = s(ee6, 10);
        int bb7 = s(y(cc7, dd6, ee7) + bb6 + this.n[3] + 1352829926, 12) + aa7;
        int dd7 = s(dd6, 10);
        int cc8 = s(cc7, 10);
        int t = s(u(b9, c5, b10) + a9 + this.n[15], 8) + e8;
        int a11 = s(y(bb7, cc7, dd7) + aa7 + this.n[12] + 1352829926, 6) + ee7;
        int aa8 = t;
        int e9 = s(v(a11, b9, a10) + e8 + this.n[7] + 1518500249, 7) + b10;
        int b11 = s(b9, 10);
        int d6 = s(v(e9, a11, b11) + b10 + this.n[4] + 1518500249, 6) + a10;
        int a12 = s(a11, 10);
        int c6 = s(v(d6, e9, a12) + a10 + this.n[13] + 1518500249, 8) + b11;
        int e10 = s(e9, 10);
        int b12 = s(v(c6, d6, e10) + b11 + this.n[1] + 1518500249, 13) + a12;
        int b13 = s(d6, 10);
        int a13 = s(v(b12, c6, b13) + a12 + this.n[10] + 1518500249, 11) + e10;
        int a14 = s(c6, 10);
        int e11 = s(v(a13, b12, a14) + e10 + this.n[6] + 1518500249, 9) + b13;
        int b14 = s(b12, 10);
        int d7 = s(v(e11, a13, b14) + b13 + this.n[15] + 1518500249, 7) + a14;
        int a15 = s(a13, 10);
        int c7 = s(v(d7, e11, a15) + a14 + this.n[3] + 1518500249, 15) + b14;
        int e12 = s(e11, 10);
        int b15 = s(v(c7, d7, e12) + b14 + this.n[12] + 1518500249, 7) + a15;
        int b16 = s(d7, 10);
        int a16 = s(v(b15, c7, b16) + a15 + this.n[0] + 1518500249, 12) + e12;
        int a17 = s(c7, 10);
        int e13 = s(v(a16, b15, a17) + e12 + this.n[9] + 1518500249, 15) + b16;
        int b17 = s(b15, 10);
        int d8 = s(v(e13, a16, b17) + b16 + this.n[5] + 1518500249, 9) + a17;
        int a18 = s(a16, 10);
        int c8 = s(v(d8, e13, a18) + a17 + this.n[2] + 1518500249, 11) + b17;
        int e14 = s(e13, 10);
        int b18 = s(v(c8, d8, e14) + b17 + this.n[14] + 1518500249, 7) + a18;
        int b19 = s(d8, 10);
        int a19 = s(v(b18, c8, b19) + a18 + this.n[11] + 1518500249, 13) + e14;
        int a20 = s(c8, 10);
        int e15 = s(v(a19, b18, a20) + e14 + this.n[8] + 1518500249, 12) + b19;
        int b20 = s(b18, 10);
        int ee8 = s(x(aa8, bb7, cc8) + ee7 + this.n[6] + 1548603684, 9) + dd7;
        int bb8 = s(bb7, 10);
        int dd8 = s(x(ee8, aa8, bb8) + dd7 + this.n[11] + 1548603684, 13) + cc8;
        int aa9 = s(aa8, 10);
        int cc9 = s(x(dd8, ee8, aa9) + cc8 + this.n[3] + 1548603684, 15) + bb8;
        int ee9 = s(ee8, 10);
        int bb9 = s(x(cc9, dd8, ee9) + bb8 + this.n[7] + 1548603684, 7) + aa9;
        int dd9 = s(dd8, 10);
        int aa10 = s(x(bb9, cc9, dd9) + aa9 + this.n[0] + 1548603684, 12) + ee9;
        int cc10 = s(cc9, 10);
        int ee10 = s(x(aa10, bb9, cc10) + ee9 + this.n[13] + 1548603684, 8) + dd9;
        int bb10 = s(bb9, 10);
        int dd10 = s(x(ee10, aa10, bb10) + dd9 + this.n[5] + 1548603684, 9) + cc10;
        int aa11 = s(aa10, 10);
        int cc11 = s(x(dd10, ee10, aa11) + cc10 + this.n[10] + 1548603684, 11) + bb10;
        int ee11 = s(ee10, 10);
        int bb11 = s(x(cc11, dd10, ee11) + bb10 + this.n[14] + 1548603684, 7) + aa11;
        int dd11 = s(dd10, 10);
        int aa12 = s(x(bb11, cc11, dd11) + aa11 + this.n[15] + 1548603684, 7) + ee11;
        int cc12 = s(cc11, 10);
        int ee12 = s(x(aa12, bb11, cc12) + ee11 + this.n[8] + 1548603684, 12) + dd11;
        int bb12 = s(bb11, 10);
        int dd12 = s(x(ee12, aa12, bb12) + dd11 + this.n[12] + 1548603684, 7) + cc12;
        int aa13 = s(aa12, 10);
        int cc13 = s(x(dd12, ee12, aa13) + cc12 + this.n[4] + 1548603684, 6) + bb12;
        int ee13 = s(ee12, 10);
        int bb13 = s(x(cc13, dd12, ee13) + bb12 + this.n[9] + 1548603684, 15) + aa13;
        int dd13 = s(dd12, 10);
        int aa14 = s(x(bb13, cc13, dd13) + aa13 + this.n[1] + 1548603684, 13) + ee13;
        int cc14 = s(cc13, 10);
        int ee14 = s(x(aa14, bb13, cc14) + ee13 + this.n[2] + 1548603684, 11) + dd13;
        int t2 = b20;
        int b21 = s(bb13, 10);
        int bb14 = t2;
        int d9 = s(w(e15, a19, b21) + b19 + this.n[3] + 1859775393, 11) + a20;
        int a21 = s(a19, 10);
        int c9 = s(w(d9, e15, a21) + a20 + this.n[10] + 1859775393, 13) + b21;
        int e16 = s(e15, 10);
        int b22 = s(w(c9, d9, e16) + b21 + this.n[14] + 1859775393, 6) + a21;
        int b23 = s(d9, 10);
        int a22 = s(w(b22, c9, b23) + a21 + this.n[4] + 1859775393, 7) + e16;
        int a23 = s(c9, 10);
        int e17 = s(w(a22, b22, a23) + e16 + this.n[9] + 1859775393, 14) + b23;
        int b24 = s(b22, 10);
        int d10 = s(w(e17, a22, b24) + b23 + this.n[15] + 1859775393, 9) + a23;
        int a24 = s(a22, 10);
        int c10 = s(w(d10, e17, a24) + a23 + this.n[8] + 1859775393, 13) + b24;
        int e18 = s(e17, 10);
        int b25 = s(w(c10, d10, e18) + b24 + this.n[1] + 1859775393, 15) + a24;
        int b26 = s(d10, 10);
        int a25 = s(w(b25, c10, b26) + a24 + this.n[2] + 1859775393, 14) + e18;
        int a26 = s(c10, 10);
        int e19 = s(w(a25, b25, a26) + e18 + this.n[7] + 1859775393, 8) + b26;
        int b27 = s(b25, 10);
        int d11 = s(w(e19, a25, b27) + b26 + this.n[0] + 1859775393, 13) + a26;
        int a27 = s(a25, 10);
        int c11 = s(w(d11, e19, a27) + a26 + this.n[6] + 1859775393, 6) + b27;
        int e20 = s(e19, 10);
        int b28 = s(w(c11, d11, e20) + b27 + this.n[13] + 1859775393, 5) + a27;
        int b29 = s(d11, 10);
        int a28 = s(w(b28, c11, b29) + a27 + this.n[11] + 1859775393, 12) + e20;
        int a29 = s(c11, 10);
        int e21 = s(w(a28, b28, a29) + e20 + this.n[5] + 1859775393, 7) + b29;
        int b30 = s(b28, 10);
        int d12 = s(w(e21, a28, b30) + b29 + this.n[12] + 1859775393, 5) + a29;
        int a30 = s(a28, 10);
        int dd14 = s(w(ee14, aa14, bb14) + dd13 + this.n[15] + 1836072691, 9) + cc14;
        int aa15 = s(aa14, 10);
        int cc15 = s(w(dd14, ee14, aa15) + cc14 + this.n[5] + 1836072691, 7) + bb14;
        int ee15 = s(ee14, 10);
        int bb15 = s(w(cc15, dd14, ee15) + bb14 + this.n[1] + 1836072691, 15) + aa15;
        int dd15 = s(dd14, 10);
        int aa16 = s(w(bb15, cc15, dd15) + aa15 + this.n[3] + 1836072691, 11) + ee15;
        int cc16 = s(cc15, 10);
        int ee16 = s(w(aa16, bb15, cc16) + ee15 + this.n[7] + 1836072691, 8) + dd15;
        int bb16 = s(bb15, 10);
        int dd16 = s(w(ee16, aa16, bb16) + dd15 + this.n[14] + 1836072691, 6) + cc16;
        int aa17 = s(aa16, 10);
        int cc17 = s(w(dd16, ee16, aa17) + cc16 + this.n[6] + 1836072691, 6) + bb16;
        int ee17 = s(ee16, 10);
        int bb17 = s(w(cc17, dd16, ee17) + bb16 + this.n[9] + 1836072691, 14) + aa17;
        int dd17 = s(dd16, 10);
        int aa18 = s(w(bb17, cc17, dd17) + aa17 + this.n[11] + 1836072691, 12) + ee17;
        int cc18 = s(cc17, 10);
        int ee18 = s(w(aa18, bb17, cc18) + ee17 + this.n[8] + 1836072691, 13) + dd17;
        int bb18 = s(bb17, 10);
        int dd18 = s(w(ee18, aa18, bb18) + dd17 + this.n[12] + 1836072691, 5) + cc18;
        int aa19 = s(aa18, 10);
        int cc19 = s(w(dd18, ee18, aa19) + cc18 + this.n[2] + 1836072691, 14) + bb18;
        int ee19 = s(ee18, 10);
        int bb19 = s(w(cc19, dd18, ee19) + bb18 + this.n[10] + 1836072691, 13) + aa19;
        int dd19 = s(dd18, 10);
        int aa20 = s(w(bb19, cc19, dd19) + aa19 + this.n[0] + 1836072691, 13) + ee19;
        int cc20 = s(cc19, 10);
        int ee20 = s(w(aa20, bb19, cc20) + ee19 + this.n[4] + 1836072691, 7) + dd19;
        int bb20 = s(bb19, 10);
        int dd20 = s(w(ee20, aa20, bb20) + dd19 + this.n[13] + 1836072691, 5) + cc20;
        int aa21 = s(aa20, 10);
        int c12 = s(((x(d12, e21, a30) + cc20) + this.n[1]) - 1894007588, 11) + b30;
        int e22 = s(e21, 10);
        int b31 = s(((x(c12, d12, e22) + b30) + this.n[9]) - 1894007588, 12) + a30;
        int b32 = s(d12, 10);
        int a31 = s(((x(b31, c12, b32) + a30) + this.n[11]) - 1894007588, 14) + e22;
        int c13 = s(c12, 10);
        int e23 = s(((x(a31, b31, c13) + e22) + this.n[10]) - 1894007588, 15) + b32;
        int b33 = s(b31, 10);
        int d13 = s(((x(e23, a31, b33) + b32) + this.n[0]) - 1894007588, 14) + c13;
        int a32 = s(a31, 10);
        int c14 = s(((x(d13, e23, a32) + c13) + this.n[8]) - 1894007588, 15) + b33;
        int e24 = s(e23, 10);
        int b34 = s(((x(c14, d13, e24) + b33) + this.n[12]) - 1894007588, 9) + a32;
        int b35 = s(d13, 10);
        int a33 = s(((x(b34, c14, b35) + a32) + this.n[4]) - 1894007588, 8) + e24;
        int a34 = s(c14, 10);
        int e25 = s(((x(a33, b34, a34) + e24) + this.n[13]) - 1894007588, 9) + b35;
        int b36 = s(b34, 10);
        int d14 = s(((x(e25, a33, b36) + b35) + this.n[3]) - 1894007588, 14) + a34;
        int a35 = s(a33, 10);
        int c15 = s(((x(d14, e25, a35) + a34) + this.n[7]) - 1894007588, 5) + b36;
        int e26 = s(e25, 10);
        int b37 = s(((x(c15, d14, e26) + b36) + this.n[15]) - 1894007588, 6) + a35;
        int b38 = s(d14, 10);
        int a36 = s(((x(b37, c15, b38) + a35) + this.n[14]) - 1894007588, 8) + e26;
        int a37 = s(c15, 10);
        int e27 = s(((x(a36, b37, a37) + e26) + this.n[5]) - 1894007588, 6) + b38;
        int b39 = s(b37, 10);
        int d15 = s(((x(e27, a36, b39) + b38) + this.n[6]) - 1894007588, 5) + a37;
        int a38 = s(a36, 10);
        int c16 = s(((x(d15, e27, a38) + a37) + this.n[2]) - 1894007588, 12) + b39;
        int e28 = s(e27, 10);
        int cc21 = s(v(dd20, ee20, aa21) + a29 + this.n[8] + 2053994217, 15) + bb20;
        int ee21 = s(ee20, 10);
        int bb21 = s(v(cc21, dd20, ee21) + bb20 + this.n[6] + 2053994217, 5) + aa21;
        int dd21 = s(dd20, 10);
        int aa22 = s(v(bb21, cc21, dd21) + aa21 + this.n[4] + 2053994217, 8) + ee21;
        int cc22 = s(cc21, 10);
        int ee22 = s(v(aa22, bb21, cc22) + ee21 + this.n[1] + 2053994217, 11) + dd21;
        int bb22 = s(bb21, 10);
        int dd22 = s(v(ee22, aa22, bb22) + dd21 + this.n[3] + 2053994217, 14) + cc22;
        int aa23 = s(aa22, 10);
        int cc23 = s(v(dd22, ee22, aa23) + cc22 + this.n[11] + 2053994217, 14) + bb22;
        int ee23 = s(ee22, 10);
        int bb23 = s(v(cc23, dd22, ee23) + bb22 + this.n[15] + 2053994217, 6) + aa23;
        int dd23 = s(dd22, 10);
        int aa24 = s(v(bb23, cc23, dd23) + aa23 + this.n[0] + 2053994217, 14) + ee23;
        int cc24 = s(cc23, 10);
        int ee24 = s(v(aa24, bb23, cc24) + ee23 + this.n[5] + 2053994217, 6) + dd23;
        int bb24 = s(bb23, 10);
        int dd24 = s(v(ee24, aa24, bb24) + dd23 + this.n[12] + 2053994217, 9) + cc24;
        int aa25 = s(aa24, 10);
        int cc25 = s(v(dd24, ee24, aa25) + cc24 + this.n[2] + 2053994217, 12) + bb24;
        int ee25 = s(ee24, 10);
        int bb25 = s(v(cc25, dd24, ee25) + bb24 + this.n[13] + 2053994217, 9) + aa25;
        int dd25 = s(dd24, 10);
        int aa26 = s(v(bb25, cc25, dd25) + aa25 + this.n[9] + 2053994217, 12) + ee25;
        int cc26 = s(cc25, 10);
        int ee26 = s(v(aa26, bb25, cc26) + ee25 + this.n[7] + 2053994217, 5) + dd25;
        int bb26 = s(bb25, 10);
        int dd26 = s(v(ee26, aa26, bb26) + dd25 + this.n[10] + 2053994217, 15) + cc26;
        int aa27 = s(aa26, 10);
        int cc27 = s(v(dd26, ee26, aa27) + cc26 + this.n[14] + 2053994217, 8) + bb26;
        int ee27 = s(ee26, 10);
        int d16 = dd26;
        int dd27 = d15;
        int b40 = s(((y(c16, d16, e28) + b39) + this.n[4]) - 1454113458, 9) + a38;
        int d17 = s(d16, 10);
        int a39 = s(((y(b40, c16, d17) + a38) + this.n[0]) - 1454113458, 15) + e28;
        int a40 = s(c16, 10);
        int e29 = s(((y(a39, b40, a40) + e28) + this.n[5]) - 1454113458, 5) + d17;
        int b41 = s(b40, 10);
        int d18 = s(((y(e29, a39, b41) + d17) + this.n[9]) - 1454113458, 11) + a40;
        int a41 = s(a39, 10);
        int c17 = s(((y(d18, e29, a41) + a40) + this.n[7]) - 1454113458, 6) + b41;
        int e30 = s(e29, 10);
        int b42 = s(((y(c17, d18, e30) + b41) + this.n[12]) - 1454113458, 8) + a41;
        int b43 = s(d18, 10);
        int a42 = s(((y(b42, c17, b43) + a41) + this.n[2]) - 1454113458, 13) + e30;
        int a43 = s(c17, 10);
        int e31 = s(((y(a42, b42, a43) + e30) + this.n[10]) - 1454113458, 12) + b43;
        int b44 = s(b42, 10);
        int d19 = s(((y(e31, a42, b44) + b43) + this.n[14]) - 1454113458, 5) + a43;
        int a44 = s(a42, 10);
        int c18 = s(((y(d19, e31, a44) + a43) + this.n[1]) - 1454113458, 12) + b44;
        int e32 = s(e31, 10);
        int b45 = s(((y(c18, d19, e32) + b44) + this.n[3]) - 1454113458, 13) + a44;
        int b46 = s(d19, 10);
        int a45 = s(((y(b45, c18, b46) + a44) + this.n[8]) - 1454113458, 14) + e32;
        int a46 = s(c18, 10);
        int e33 = s(((y(a45, b45, a46) + e32) + this.n[11]) - 1454113458, 11) + b46;
        int b47 = s(b45, 10);
        int d20 = s(((y(e33, a45, b47) + b46) + this.n[6]) - 1454113458, 8) + a46;
        int a47 = s(a45, 10);
        int c19 = s(((y(d20, e33, a47) + a46) + this.n[15]) - 1454113458, 5) + b47;
        int e34 = s(e33, 10);
        int b48 = s(d20, 10);
        int bb27 = s(u(cc27, dd27, ee27) + bb26 + this.n[12], 8) + aa27;
        int dd28 = s(dd27, 10);
        int aa28 = s(u(bb27, cc27, dd28) + aa27 + this.n[15], 5) + ee27;
        int cc28 = s(cc27, 10);
        int ee28 = s(u(aa28, bb27, cc28) + ee27 + this.n[10], 12) + dd28;
        int bb28 = s(bb27, 10);
        int dd29 = s(u(ee28, aa28, bb28) + dd28 + this.n[4], 9) + cc28;
        int aa29 = s(aa28, 10);
        int cc29 = s(u(dd29, ee28, aa29) + cc28 + this.n[1], 12) + bb28;
        int ee29 = s(ee28, 10);
        int bb29 = s(u(cc29, dd29, ee29) + bb28 + this.n[5], 5) + aa29;
        int dd30 = s(dd29, 10);
        int aa30 = s(u(bb29, cc29, dd30) + aa29 + this.n[8], 14) + ee29;
        int cc30 = s(cc29, 10);
        int ee30 = s(u(aa30, bb29, cc30) + ee29 + this.n[7], 6) + dd30;
        int bb30 = s(bb29, 10);
        int dd31 = s(u(ee30, aa30, bb30) + dd30 + this.n[6], 8) + cc30;
        int aa31 = s(aa30, 10);
        int cc31 = s(u(dd31, ee30, aa31) + cc30 + this.n[2], 13) + bb30;
        int ee31 = s(ee30, 10);
        int bb31 = s(u(cc31, dd31, ee31) + bb30 + this.n[13], 6) + aa31;
        int dd32 = s(dd31, 10);
        int aa32 = s(u(bb31, cc31, dd32) + aa31 + this.n[14], 5) + ee31;
        int cc32 = s(cc31, 10);
        int ee32 = s(u(aa32, bb31, cc32) + ee31 + this.n[0], 15) + dd32;
        int bb32 = s(bb31, 10);
        int dd33 = s(u(ee32, aa32, bb32) + dd32 + this.n[3], 13) + cc32;
        int aa33 = s(aa32, 10);
        int cc33 = s(u(dd33, ee32, aa33) + cc32 + this.n[9], 11) + bb32;
        int ee33 = s(ee32, 10);
        int dd34 = s(dd33, 10);
        this.d += a47;
        this.e += s(((y(c19, d20, e34) + b47) + this.n[13]) - 1454113458, 6) + a47;
        this.f += c19;
        this.g += b48;
        this.h += ee33;
        this.i += aa33;
        this.j += s(u(cc33, dd33, ee33) + bb32 + this.n[11], 11) + aa33;
        this.k += cc33;
        this.l += dd34;
        this.m += e34;
        this.o = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.n;
            if (i2 != iArr.length) {
                iArr[i2] = 0;
                i2++;
            } else {
                return;
            }
        }
    }

    public Memoable copy() {
        return new RIPEMD320Digest(this);
    }

    public void m(Memoable other) {
        t((RIPEMD320Digest) other);
    }
}
