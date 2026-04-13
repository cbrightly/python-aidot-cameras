package org.spongycastle.crypto.digests;

import java.lang.reflect.Array;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.crypto.engines.GOST28147Engine;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithSBox;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Memoable;
import org.spongycastle.util.Pack;

public class GOST3411Digest implements ExtendedDigest, Memoable {
    private static final byte[] a = {0, -1, 0, -1, 0, -1, 0, -1, -1, 0, -1, 0, -1, 0, -1, 0, 0, -1, -1, 0, -1, 0, 0, -1, -1, 0, 0, 0, -1, -1, 0, -1};
    private byte[] b = new byte[32];
    private byte[] c = new byte[32];
    private byte[] d = new byte[32];
    private byte[] e = new byte[32];
    private byte[][] f = ((byte[][]) Array.newInstance(byte.class, new int[]{4, 32}));
    private byte[] g = new byte[32];
    private int h;
    private long i;
    private BlockCipher j = new GOST28147Engine();
    private byte[] k;
    private byte[] l = new byte[32];
    byte[] m = new byte[8];
    short[] n = new short[16];
    short[] o = new short[16];
    byte[] p = new byte[32];
    byte[] q = new byte[32];
    byte[] r = new byte[32];
    byte[] s = new byte[32];

    public GOST3411Digest() {
        byte[] j2 = GOST28147Engine.j("D-A");
        this.k = j2;
        this.j.a(true, new ParametersWithSBox((CipherParameters) null, j2));
        reset();
    }

    public GOST3411Digest(byte[] sBoxParam) {
        byte[] h2 = Arrays.h(sBoxParam);
        this.k = h2;
        this.j.a(true, new ParametersWithSBox((CipherParameters) null, h2));
        reset();
    }

    public GOST3411Digest(GOST3411Digest t) {
        m(t);
    }

    public String b() {
        return "GOST3411";
    }

    public int e() {
        return 32;
    }

    public void d(byte in) {
        byte[] bArr = this.g;
        int i2 = this.h;
        int i3 = i2 + 1;
        this.h = i3;
        bArr[i2] = in;
        if (i3 == bArr.length) {
            v(bArr);
            u(this.g, 0);
            this.h = 0;
        }
        this.i++;
    }

    public void update(byte[] in, int inOff, int len) {
        while (this.h != 0 && len > 0) {
            d(in[inOff]);
            inOff++;
            len--;
        }
        while (true) {
            byte[] bArr = this.g;
            if (len <= bArr.length) {
                break;
            }
            System.arraycopy(in, inOff, bArr, 0, bArr.length);
            v(this.g);
            u(this.g, 0);
            byte[] bArr2 = this.g;
            inOff += bArr2.length;
            len -= bArr2.length;
            this.i += (long) bArr2.length;
        }
        while (len > 0) {
            d(in[inOff]);
            inOff++;
            len--;
        }
    }

    private byte[] p(byte[] in) {
        for (int k2 = 0; k2 < 8; k2++) {
            byte[] bArr = this.l;
            bArr[k2 * 4] = in[k2];
            bArr[(k2 * 4) + 1] = in[k2 + 8];
            bArr[(k2 * 4) + 2] = in[k2 + 16];
            bArr[(k2 * 4) + 3] = in[k2 + 24];
        }
        return this.l;
    }

    private byte[] n(byte[] in) {
        for (int j2 = 0; j2 < 8; j2++) {
            this.m[j2] = (byte) (in[j2] ^ in[j2 + 8]);
        }
        System.arraycopy(in, 8, in, 0, 24);
        System.arraycopy(this.m, 0, in, 24, 8);
        return in;
    }

    private void o(byte[] key, byte[] s2, int sOff, byte[] in, int inOff) {
        this.j.a(true, new KeyParameter(key));
        this.j.f(in, inOff, s2, sOff);
    }

    private void t(byte[] in) {
        q(in, this.n);
        short[] sArr = this.o;
        short[] sArr2 = this.n;
        sArr[15] = (short) (((((sArr2[0] ^ sArr2[1]) ^ sArr2[2]) ^ sArr2[3]) ^ sArr2[12]) ^ sArr2[15]);
        System.arraycopy(sArr2, 1, sArr, 0, 15);
        r(this.o, in);
    }

    /* access modifiers changed from: protected */
    public void u(byte[] in, int inOff) {
        System.arraycopy(in, inOff, this.d, 0, 32);
        System.arraycopy(this.b, 0, this.q, 0, 32);
        System.arraycopy(this.d, 0, this.r, 0, 32);
        for (int j2 = 0; j2 < 32; j2++) {
            this.s[j2] = (byte) (this.q[j2] ^ this.r[j2]);
        }
        o(p(this.s), this.p, 0, this.b, 0);
        for (int i2 = 1; i2 < 4; i2++) {
            byte[] tmpA = n(this.q);
            for (int j3 = 0; j3 < 32; j3++) {
                this.q[j3] = (byte) (tmpA[j3] ^ this.f[i2][j3]);
            }
            this.r = n(n(this.r));
            for (int j4 = 0; j4 < 32; j4++) {
                this.s[j4] = (byte) (this.q[j4] ^ this.r[j4]);
            }
            o(p(this.s), this.p, i2 * 8, this.b, i2 * 8);
        }
        for (int n2 = 0; n2 < 12; n2++) {
            t(this.p);
        }
        for (int n3 = 0; n3 < 32; n3++) {
            byte[] bArr = this.p;
            bArr[n3] = (byte) (bArr[n3] ^ this.d[n3]);
        }
        t(this.p);
        for (int n4 = 0; n4 < 32; n4++) {
            byte[] bArr2 = this.p;
            bArr2[n4] = (byte) (this.b[n4] ^ bArr2[n4]);
        }
        for (int n5 = 0; n5 < 61; n5++) {
            t(this.p);
        }
        byte[] bArr3 = this.p;
        byte[] bArr4 = this.b;
        System.arraycopy(bArr3, 0, bArr4, 0, bArr4.length);
    }

    private void s() {
        Pack.r(this.i * 8, this.c, 0);
        while (this.h != 0) {
            d((byte) 0);
        }
        u(this.c, 0);
        u(this.e, 0);
    }

    public int c(byte[] out, int outOff) {
        s();
        byte[] bArr = this.b;
        System.arraycopy(bArr, 0, out, outOff, bArr.length);
        reset();
        return 32;
    }

    public void reset() {
        this.i = 0;
        this.h = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.b;
            if (i2 >= bArr.length) {
                break;
            }
            bArr[i2] = 0;
            i2++;
        }
        int i3 = 0;
        while (true) {
            byte[] bArr2 = this.c;
            if (i3 >= bArr2.length) {
                break;
            }
            bArr2[i3] = 0;
            i3++;
        }
        int i4 = 0;
        while (true) {
            byte[] bArr3 = this.d;
            if (i4 >= bArr3.length) {
                break;
            }
            bArr3[i4] = 0;
            i4++;
        }
        int i5 = 0;
        while (true) {
            byte[][] bArr4 = this.f;
            if (i5 >= bArr4[1].length) {
                break;
            }
            bArr4[1][i5] = 0;
            i5++;
        }
        int i6 = 0;
        while (true) {
            byte[][] bArr5 = this.f;
            if (i6 >= bArr5[3].length) {
                break;
            }
            bArr5[3][i6] = 0;
            i6++;
        }
        int i7 = 0;
        while (true) {
            byte[] bArr6 = this.e;
            if (i7 >= bArr6.length) {
                break;
            }
            bArr6[i7] = 0;
            i7++;
        }
        int i8 = 0;
        while (true) {
            byte[] bArr7 = this.g;
            if (i8 < bArr7.length) {
                bArr7[i8] = 0;
                i8++;
            } else {
                byte[] bArr8 = a;
                System.arraycopy(bArr8, 0, this.f[2], 0, bArr8.length);
                return;
            }
        }
    }

    private void v(byte[] in) {
        int carry = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.e;
            if (i2 != bArr.length) {
                int sum = (bArr[i2] & 255) + (in[i2] & 255) + carry;
                bArr[i2] = (byte) sum;
                carry = sum >>> 8;
                i2++;
            } else {
                return;
            }
        }
    }

    private void q(byte[] S, short[] wS) {
        for (int i2 = 0; i2 < S.length / 2; i2++) {
            wS[i2] = (short) (((S[(i2 * 2) + 1] << 8) & 65280) | (S[i2 * 2] & 255));
        }
    }

    private void r(short[] wS, byte[] S) {
        for (int i2 = 0; i2 < S.length / 2; i2++) {
            S[(i2 * 2) + 1] = (byte) (wS[i2] >> 8);
            S[i2 * 2] = (byte) wS[i2];
        }
    }

    public int k() {
        return 32;
    }

    public Memoable copy() {
        return new GOST3411Digest(this);
    }

    public void m(Memoable other) {
        GOST3411Digest t = (GOST3411Digest) other;
        byte[] bArr = t.k;
        this.k = bArr;
        this.j.a(true, new ParametersWithSBox((CipherParameters) null, bArr));
        reset();
        byte[] bArr2 = t.b;
        System.arraycopy(bArr2, 0, this.b, 0, bArr2.length);
        byte[] bArr3 = t.c;
        System.arraycopy(bArr3, 0, this.c, 0, bArr3.length);
        byte[] bArr4 = t.d;
        System.arraycopy(bArr4, 0, this.d, 0, bArr4.length);
        byte[] bArr5 = t.e;
        System.arraycopy(bArr5, 0, this.e, 0, bArr5.length);
        byte[][] bArr6 = t.f;
        System.arraycopy(bArr6[1], 0, this.f[1], 0, bArr6[1].length);
        byte[][] bArr7 = t.f;
        System.arraycopy(bArr7[2], 0, this.f[2], 0, bArr7[2].length);
        byte[][] bArr8 = t.f;
        System.arraycopy(bArr8[3], 0, this.f[3], 0, bArr8[3].length);
        byte[] bArr9 = t.g;
        System.arraycopy(bArr9, 0, this.g, 0, bArr9.length);
        this.h = t.h;
        this.i = t.i;
    }
}
