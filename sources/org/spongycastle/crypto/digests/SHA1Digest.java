package org.spongycastle.crypto.digests;

import org.glassfish.grizzly.http.server.util.MappingData;
import org.spongycastle.util.Memoable;
import org.spongycastle.util.Pack;

public class SHA1Digest extends GeneralDigest implements EncodableDigest {
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int[] i = new int[80];
    private int j;

    public SHA1Digest() {
        reset();
    }

    public SHA1Digest(SHA1Digest t) {
        super(t);
        s(t);
    }

    private void s(SHA1Digest t) {
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
        return "SHA-1";
    }

    public int e() {
        return 20;
    }

    /* access modifiers changed from: protected */
    public void r(byte[] in, int inOff) {
        int inOff2 = inOff + 1;
        int inOff3 = inOff2 + 1;
        int n = (in[inOff] << 24) | ((in[inOff2] & 255) << MappingData.PATH) | ((in[inOff3] & 255) << 8) | (in[inOff3 + 1] & 255);
        int[] iArr = this.i;
        int i2 = this.j;
        iArr[i2] = n;
        int i3 = i2 + 1;
        this.j = i3;
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
        iArr[14] = (int) (bitLength >>> 32);
        iArr[15] = (int) (-1 & bitLength);
    }

    public int c(byte[] out, int outOff) {
        o();
        Pack.d(this.d, out, outOff);
        Pack.d(this.e, out, outOff + 4);
        Pack.d(this.f, out, outOff + 8);
        Pack.d(this.g, out, outOff + 12);
        Pack.d(this.h, out, outOff + 16);
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

    private int t(int u, int v, int w) {
        return (u & v) | ((~u) & w);
    }

    private int v(int u, int v, int w) {
        return (u ^ v) ^ w;
    }

    private int u(int u, int v, int w) {
        return (u & v) | (u & w) | (v & w);
    }

    /* access modifiers changed from: protected */
    public void p() {
        for (int i2 = 16; i2 < 80; i2++) {
            int[] iArr = this.i;
            int t = ((iArr[i2 - 3] ^ iArr[i2 - 8]) ^ iArr[i2 - 14]) ^ iArr[i2 - 16];
            iArr[i2] = (t << 1) | (t >>> 31);
        }
        int A = this.d;
        int B = this.e;
        int C = this.f;
        int D = this.g;
        int E = this.h;
        int idx = 0;
        int j2 = 0;
        while (j2 < 4) {
            int idx2 = idx + 1;
            int E2 = E + ((A << 5) | (A >>> 27)) + t(B, C, D) + this.i[idx] + 1518500249;
            int B2 = (B << 30) | (B >>> 2);
            int idx3 = idx2 + 1;
            int D2 = D + ((E2 << 5) | (E2 >>> 27)) + t(A, B2, C) + this.i[idx2] + 1518500249;
            int A2 = (A << 30) | (A >>> 2);
            int idx4 = idx3 + 1;
            int C2 = C + ((D2 << 5) | (D2 >>> 27)) + t(E2, A2, B2) + this.i[idx3] + 1518500249;
            E = (E2 << 30) | (E2 >>> 2);
            int idx5 = idx4 + 1;
            B = B2 + ((C2 << 5) | (C2 >>> 27)) + t(D2, E, A2) + this.i[idx4] + 1518500249;
            D = (D2 << 30) | (D2 >>> 2);
            A = A2 + ((B << 5) | (B >>> 27)) + t(C2, D, E) + this.i[idx5] + 1518500249;
            C = (C2 << 30) | (C2 >>> 2);
            j2++;
            idx = idx5 + 1;
        }
        int j3 = 0;
        while (j3 < 4) {
            int idx6 = idx + 1;
            int E3 = E + ((A << 5) | (A >>> 27)) + v(B, C, D) + this.i[idx] + 1859775393;
            int B3 = (B << 30) | (B >>> 2);
            int idx7 = idx6 + 1;
            int D3 = D + ((E3 << 5) | (E3 >>> 27)) + v(A, B3, C) + this.i[idx6] + 1859775393;
            int A3 = (A << 30) | (A >>> 2);
            int idx8 = idx7 + 1;
            int C3 = C + ((D3 << 5) | (D3 >>> 27)) + v(E3, A3, B3) + this.i[idx7] + 1859775393;
            E = (E3 << 30) | (E3 >>> 2);
            int idx9 = idx8 + 1;
            B = B3 + ((C3 << 5) | (C3 >>> 27)) + v(D3, E, A3) + this.i[idx8] + 1859775393;
            D = (D3 << 30) | (D3 >>> 2);
            A = A3 + ((B << 5) | (B >>> 27)) + v(C3, D, E) + this.i[idx9] + 1859775393;
            C = (C3 << 30) | (C3 >>> 2);
            j3++;
            idx = idx9 + 1;
        }
        int j4 = 0;
        while (j4 < 4) {
            int idx10 = idx + 1;
            int E4 = E + (((((A << 5) | (A >>> 27)) + u(B, C, D)) + this.i[idx]) - 1894007588);
            int B4 = (B << 30) | (B >>> 2);
            int idx11 = idx10 + 1;
            int D4 = D + (((((E4 << 5) | (E4 >>> 27)) + u(A, B4, C)) + this.i[idx10]) - 1894007588);
            int A4 = (A << 30) | (A >>> 2);
            int idx12 = idx11 + 1;
            int C4 = C + (((((D4 << 5) | (D4 >>> 27)) + u(E4, A4, B4)) + this.i[idx11]) - 1894007588);
            E = (E4 << 30) | (E4 >>> 2);
            int idx13 = idx12 + 1;
            B = B4 + (((((C4 << 5) | (C4 >>> 27)) + u(D4, E, A4)) + this.i[idx12]) - 1894007588);
            D = (D4 << 30) | (D4 >>> 2);
            A = A4 + (((((B << 5) | (B >>> 27)) + u(C4, D, E)) + this.i[idx13]) - 1894007588);
            C = (C4 << 30) | (C4 >>> 2);
            j4++;
            idx = idx13 + 1;
        }
        int j5 = 0;
        while (j5 <= 3) {
            int idx14 = idx + 1;
            int E5 = E + (((((A << 5) | (A >>> 27)) + v(B, C, D)) + this.i[idx]) - 899497514);
            int B5 = (B << 30) | (B >>> 2);
            int idx15 = idx14 + 1;
            int D5 = D + (((((E5 << 5) | (E5 >>> 27)) + v(A, B5, C)) + this.i[idx14]) - 899497514);
            int A5 = (A << 30) | (A >>> 2);
            int idx16 = idx15 + 1;
            int C5 = C + (((((D5 << 5) | (D5 >>> 27)) + v(E5, A5, B5)) + this.i[idx15]) - 899497514);
            E = (E5 << 30) | (E5 >>> 2);
            int idx17 = idx16 + 1;
            B = B5 + (((((C5 << 5) | (C5 >>> 27)) + v(D5, E, A5)) + this.i[idx16]) - 899497514);
            D = (D5 << 30) | (D5 >>> 2);
            A = A5 + (((((B << 5) | (B >>> 27)) + v(C5, D, E)) + this.i[idx17]) - 899497514);
            C = (C5 << 30) | (C5 >>> 2);
            j5++;
            idx = idx17 + 1;
        }
        this.d += A;
        this.e += B;
        this.f += C;
        this.g += D;
        this.h += E;
        this.j = 0;
        for (int i3 = 0; i3 < 16; i3++) {
            this.i[i3] = 0;
        }
    }

    public Memoable copy() {
        return new SHA1Digest(this);
    }

    public void m(Memoable other) {
        SHA1Digest d2 = (SHA1Digest) other;
        super.n(d2);
        s(d2);
    }
}
