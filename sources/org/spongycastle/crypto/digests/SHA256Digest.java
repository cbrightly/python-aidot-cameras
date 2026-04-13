package org.spongycastle.crypto.digests;

import org.glassfish.grizzly.http.server.util.MappingData;
import org.spongycastle.util.Memoable;
import org.spongycastle.util.Pack;

public class SHA256Digest extends GeneralDigest implements EncodableDigest {
    static final int[] d = {1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998};
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int[] m = new int[64];
    private int n;

    public SHA256Digest() {
        reset();
    }

    public SHA256Digest(SHA256Digest t) {
        super(t);
        y(t);
    }

    private void y(SHA256Digest t) {
        super.n(t);
        this.e = t.e;
        this.f = t.f;
        this.g = t.g;
        this.h = t.h;
        this.i = t.i;
        this.j = t.j;
        this.k = t.k;
        this.l = t.l;
        int[] iArr = t.m;
        System.arraycopy(iArr, 0, this.m, 0, iArr.length);
        this.n = t.n;
    }

    public String b() {
        return "SHA-256";
    }

    public int e() {
        return 32;
    }

    /* access modifiers changed from: protected */
    public void r(byte[] in, int inOff) {
        int inOff2 = inOff + 1;
        int inOff3 = inOff2 + 1;
        int n2 = (in[inOff] << 24) | ((in[inOff2] & 255) << MappingData.PATH) | ((in[inOff3] & 255) << 8) | (in[inOff3 + 1] & 255);
        int[] iArr = this.m;
        int i2 = this.n;
        iArr[i2] = n2;
        int i3 = i2 + 1;
        this.n = i3;
        if (i3 == 16) {
            p();
        }
    }

    /* access modifiers changed from: protected */
    public void q(long bitLength) {
        if (this.n > 14) {
            p();
        }
        int[] iArr = this.m;
        iArr[14] = (int) (bitLength >>> 32);
        iArr[15] = (int) (-1 & bitLength);
    }

    public int c(byte[] out, int outOff) {
        o();
        Pack.d(this.e, out, outOff);
        Pack.d(this.f, out, outOff + 4);
        Pack.d(this.g, out, outOff + 8);
        Pack.d(this.h, out, outOff + 12);
        Pack.d(this.i, out, outOff + 16);
        Pack.d(this.j, out, outOff + 20);
        Pack.d(this.k, out, outOff + 24);
        Pack.d(this.l, out, outOff + 28);
        reset();
        return 32;
    }

    public void reset() {
        super.reset();
        this.e = 1779033703;
        this.f = -1150833019;
        this.g = 1013904242;
        this.h = -1521486534;
        this.i = 1359893119;
        this.j = -1694144372;
        this.k = 528734635;
        this.l = 1541459225;
        this.n = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.m;
            if (i2 != iArr.length) {
                iArr[i2] = 0;
                i2++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void p() {
        for (int t = 16; t <= 63; t++) {
            int[] iArr = this.m;
            int x = x(iArr[t - 2]);
            int[] iArr2 = this.m;
            iArr[t] = x + iArr2[t - 7] + w(iArr2[t - 15]) + this.m[t - 16];
        }
        int a = this.e;
        int b = this.f;
        int c = this.g;
        int d2 = this.h;
        int e2 = this.i;
        int f2 = this.j;
        int g2 = this.k;
        int h2 = this.l;
        int t2 = 0;
        for (int i2 = 0; i2 < 8; i2++) {
            int v = v(e2) + s(e2, f2, g2);
            int[] iArr3 = d;
            int h3 = h2 + v + iArr3[t2] + this.m[t2];
            int d3 = d2 + h3;
            int h4 = h3 + u(a) + t(a, b, c);
            int t3 = t2 + 1;
            int g3 = g2 + v(d3) + s(d3, e2, f2) + iArr3[t3] + this.m[t3];
            int c2 = c + g3;
            int g4 = g3 + u(h4) + t(h4, a, b);
            int t4 = t3 + 1;
            int f3 = f2 + v(c2) + s(c2, d3, e2) + iArr3[t4] + this.m[t4];
            int b2 = b + f3;
            int f4 = f3 + u(g4) + t(g4, h4, a);
            int t5 = t4 + 1;
            int e3 = e2 + v(b2) + s(b2, c2, d3) + iArr3[t5] + this.m[t5];
            int a2 = a + e3;
            int e4 = e3 + u(f4) + t(f4, g4, h4);
            int t6 = t5 + 1;
            int d4 = d3 + v(a2) + s(a2, b2, c2) + iArr3[t6] + this.m[t6];
            h2 = h4 + d4;
            d2 = d4 + u(e4) + t(e4, f4, g4);
            int t7 = t6 + 1;
            int c3 = c2 + v(h2) + s(h2, a2, b2) + iArr3[t7] + this.m[t7];
            g2 = g4 + c3;
            c = c3 + u(d2) + t(d2, e4, f4);
            int t8 = t7 + 1;
            int b3 = b2 + v(g2) + s(g2, h2, a2) + iArr3[t8] + this.m[t8];
            f2 = f4 + b3;
            b = b3 + u(c) + t(c, d2, e4);
            int t9 = t8 + 1;
            int a3 = a2 + v(f2) + s(f2, g2, h2) + iArr3[t9] + this.m[t9];
            e2 = e4 + a3;
            a = a3 + u(b) + t(b, c, d2);
            t2 = t9 + 1;
        }
        this.e += a;
        this.f += b;
        this.g += c;
        this.h += d2;
        this.i += e2;
        this.j += f2;
        this.k += g2;
        this.l += h2;
        this.n = 0;
        for (int i3 = 0; i3 < 16; i3++) {
            this.m[i3] = 0;
        }
    }

    private int s(int x, int y, int z) {
        return (x & y) ^ ((~x) & z);
    }

    private int t(int x, int y, int z) {
        return ((x & y) ^ (x & z)) ^ (y & z);
    }

    private int u(int x) {
        return (((x >>> 2) | (x << 30)) ^ ((x >>> 13) | (x << 19))) ^ ((x >>> 22) | (x << 10));
    }

    private int v(int x) {
        return (((x >>> 6) | (x << 26)) ^ ((x >>> 11) | (x << 21))) ^ ((x >>> 25) | (x << 7));
    }

    private int w(int x) {
        return (((x >>> 7) | (x << 25)) ^ ((x >>> 18) | (x << 14))) ^ (x >>> 3);
    }

    private int x(int x) {
        return (((x >>> 17) | (x << 15)) ^ ((x >>> 19) | (x << 13))) ^ (x >>> 10);
    }

    public Memoable copy() {
        return new SHA256Digest(this);
    }

    public void m(Memoable other) {
        y((SHA256Digest) other);
    }
}
