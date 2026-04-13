package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Pack;

public class Poly1305 implements Mac {
    private final BlockCipher a;
    private final byte[] b;
    private int c;
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
    private int n;
    private int o;
    private final byte[] p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;

    public Poly1305() {
        this.b = new byte[1];
        this.p = new byte[16];
        this.q = 0;
        this.a = null;
    }

    public Poly1305(BlockCipher cipher) {
        this.b = new byte[1];
        this.p = new byte[16];
        this.q = 0;
        if (cipher.c() == 16) {
            this.a = cipher;
            return;
        }
        throw new IllegalArgumentException("Poly1305 requires a 128 bit block cipher.");
    }

    public void a(CipherParameters params) {
        byte[] nonce = null;
        if (this.a != null) {
            if (params instanceof ParametersWithIV) {
                ParametersWithIV ivParams = (ParametersWithIV) params;
                nonce = ivParams.a();
                params = ivParams.b();
            } else {
                throw new IllegalArgumentException("Poly1305 requires an IV when used with a block cipher.");
            }
        }
        if (params instanceof KeyParameter) {
            h(((KeyParameter) params).a(), nonce);
            reset();
            return;
        }
        throw new IllegalArgumentException("Poly1305 requires a key.");
    }

    private void h(byte[] key, byte[] nonce) {
        int kOff;
        byte[] kBytes;
        if (key.length != 32) {
            throw new IllegalArgumentException("Poly1305 key must be 256 bits.");
        } else if (this.a == null || (nonce != null && nonce.length == 16)) {
            int t0 = Pack.j(key, 0);
            int t1 = Pack.j(key, 4);
            int t2 = Pack.j(key, 8);
            int t3 = Pack.j(key, 12);
            this.c = 67108863 & t0;
            int i2 = ((t0 >>> 26) | (t1 << 6)) & 67108611;
            this.d = i2;
            int i3 = ((t1 >>> 20) | (t2 << 12)) & 67092735;
            this.e = i3;
            int i4 = ((t2 >>> 14) | (t3 << 18)) & 66076671;
            this.f = i4;
            int i5 = (t3 >>> 8) & 1048575;
            this.g = i5;
            this.h = i2 * 5;
            this.i = i3 * 5;
            this.j = i4 * 5;
            this.k = i5 * 5;
            BlockCipher blockCipher = this.a;
            if (blockCipher == null) {
                kBytes = key;
                kOff = 16;
            } else {
                byte[] kBytes2 = new byte[16];
                blockCipher.a(true, new KeyParameter(key, 16, 16));
                this.a.f(nonce, 0, kBytes2, 0);
                kBytes = kBytes2;
                kOff = 0;
            }
            this.l = Pack.j(kBytes, kOff + 0);
            this.m = Pack.j(kBytes, kOff + 4);
            this.n = Pack.j(kBytes, kOff + 8);
            this.o = Pack.j(kBytes, kOff + 12);
        } else {
            throw new IllegalArgumentException("Poly1305 requires a 128 bit IV.");
        }
    }

    public String b() {
        if (this.a == null) {
            return "Poly1305";
        }
        return "Poly1305-" + this.a.b();
    }

    public int e() {
        return 16;
    }

    public void d(byte in) {
        byte[] bArr = this.b;
        bArr[0] = in;
        update(bArr, 0, 1);
    }

    public void update(byte[] in, int inOff, int len) {
        int copied = 0;
        while (len > copied) {
            if (this.q == 16) {
                g();
                this.q = 0;
            }
            int toCopy = Math.min(len - copied, 16 - this.q);
            System.arraycopy(in, copied + inOff, this.p, this.q, toCopy);
            copied += toCopy;
            this.q += toCopy;
        }
    }

    private void g() {
        int i2 = this.q;
        if (i2 < 16) {
            this.p[i2] = 1;
            for (int i3 = i2 + 1; i3 < 16; i3++) {
                this.p[i3] = 0;
            }
        }
        long t0 = ((long) Pack.j(this.p, 0)) & 4294967295L;
        long t1 = ((long) Pack.j(this.p, 4)) & 4294967295L;
        long t2 = ((long) Pack.j(this.p, 8)) & 4294967295L;
        long t3 = 4294967295L & ((long) Pack.j(this.p, 12));
        int i4 = (int) (((long) this.r) + (t0 & 67108863));
        this.r = i4;
        this.s = (int) (((long) this.s) + ((((t1 << 32) | t0) >>> 26) & 67108863));
        this.t = (int) (((long) this.t) + ((((t2 << 32) | t1) >>> 20) & 67108863));
        this.u = (int) (((long) this.u) + ((((t3 << 32) | t2) >>> 14) & 67108863));
        int i5 = (int) (((long) this.v) + (t3 >>> 8));
        this.v = i5;
        if (this.q == 16) {
            this.v = i5 + 16777216;
        }
        long tp0 = f(i4, this.c) + f(this.s, this.k) + f(this.t, this.j) + f(this.u, this.i) + f(this.v, this.h);
        long tp1 = f(this.r, this.d) + f(this.s, this.c) + f(this.t, this.k) + f(this.u, this.j) + f(this.v, this.i);
        long j2 = t0;
        long tp2 = f(this.r, this.e) + f(this.s, this.d) + f(this.t, this.c) + f(this.u, this.k) + f(this.v, this.j);
        long j3 = t3;
        long tp3 = f(this.r, this.f) + f(this.s, this.e) + f(this.t, this.d) + f(this.u, this.c) + f(this.v, this.k);
        long j4 = t1;
        long tp4 = f(this.r, this.g) + f(this.s, this.f) + f(this.t, this.e) + f(this.u, this.d) + f(this.v, this.c);
        int i6 = ((int) tp0) & 67108863;
        this.r = i6;
        long tp12 = tp1 + (tp0 >>> 26);
        int i7 = ((int) tp12) & 67108863;
        this.s = i7;
        long tp22 = tp2 + (tp12 >>> 26);
        long j5 = tp0;
        this.t = ((int) tp22) & 67108863;
        long tp32 = tp3 + (tp22 >>> 26);
        this.u = ((int) tp32) & 67108863;
        long tp42 = tp4 + (tp32 >>> 26);
        this.v = ((int) tp42) & 67108863;
        int i8 = i6 + (((int) (tp42 >>> 26)) * 5);
        this.r = i8;
        this.s = i7 + (i8 >>> 26);
        this.r = i8 & 67108863;
    }

    public int c(byte[] out, int outOff) {
        byte[] bArr = out;
        int i2 = outOff;
        if (i2 + 16 <= bArr.length) {
            if (this.q > 0) {
                g();
            }
            int i3 = this.s;
            int i4 = this.r;
            int i5 = i3 + (i4 >>> 26);
            this.s = i5;
            int i6 = i4 & 67108863;
            this.r = i6;
            int i7 = this.t + (i5 >>> 26);
            this.t = i7;
            int i8 = i5 & 67108863;
            this.s = i8;
            int i9 = this.u + (i7 >>> 26);
            this.u = i9;
            int i10 = i7 & 67108863;
            this.t = i10;
            int i11 = this.v + (i9 >>> 26);
            this.v = i11;
            int i12 = i9 & 67108863;
            this.u = i12;
            int i13 = i6 + ((i11 >>> 26) * 5);
            this.r = i13;
            int i14 = i11 & 67108863;
            this.v = i14;
            int i15 = i8 + (i13 >>> 26);
            this.s = i15;
            int i16 = i13 & 67108863;
            this.r = i16;
            int g0 = i16 + 5;
            int b2 = g0 >>> 26;
            int g02 = g0 & 67108863;
            int g1 = i15 + b2;
            int b3 = g1 >>> 26;
            int g12 = g1 & 67108863;
            int g2 = i10 + b3;
            int b4 = g2 >>> 26;
            int g22 = g2 & 67108863;
            int g3 = i12 + b4;
            int g32 = 67108863 & g3;
            int g4 = (i14 + (g3 >>> 26)) - 67108864;
            int b5 = (g4 >>> 31) - 1;
            int nb = ~b5;
            int i17 = (i16 & nb) | (g02 & b5);
            this.r = i17;
            int i18 = (i15 & nb) | (g12 & b5);
            this.s = i18;
            int i19 = (i10 & nb) | (g22 & b5);
            this.t = i19;
            int i20 = (i12 & nb) | (g32 & b5);
            this.u = i20;
            int i21 = (i14 & nb) | (g4 & b5);
            this.v = i21;
            int i22 = g32;
            int i23 = g02;
            int i24 = nb;
            long f0 = (((long) (i17 | (i18 << 26))) & 4294967295L) + (((long) this.l) & 4294967295L);
            int i25 = g12;
            int i26 = g22;
            long f1 = (((long) ((i18 >>> 6) | (i19 << 20))) & 4294967295L) + (((long) this.m) & 4294967295L);
            int i27 = g4;
            int i28 = b5;
            long f2 = (((long) ((i19 >>> 12) | (i20 << 14))) & 4294967295L) + (((long) this.n) & 4294967295L);
            long f3 = (((long) ((i20 >>> 18) | (i21 << 8))) & 4294967295L) + (((long) this.o) & 4294967295L);
            Pack.h((int) f0, bArr, i2);
            long f12 = f1 + (f0 >>> 32);
            Pack.h((int) f12, bArr, i2 + 4);
            long f22 = f2 + (f12 >>> 32);
            Pack.h((int) f22, bArr, i2 + 8);
            Pack.h((int) (f3 + (f22 >>> 32)), bArr, i2 + 12);
            reset();
            return 16;
        }
        throw new OutputLengthException("Output buffer is too short.");
    }

    public void reset() {
        this.q = 0;
        this.v = 0;
        this.u = 0;
        this.t = 0;
        this.s = 0;
        this.r = 0;
    }

    private static final long f(int i1, int i2) {
        return (((long) i1) & 4294967295L) * ((long) i2);
    }
}
