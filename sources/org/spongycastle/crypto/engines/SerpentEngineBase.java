package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public abstract class SerpentEngineBase implements BlockCipher {
    protected boolean a;
    protected int[] b;
    protected int c;
    protected int d;
    protected int e;
    protected int f;

    /* access modifiers changed from: protected */
    public abstract void e(byte[] bArr, int i, byte[] bArr2, int i2);

    /* access modifiers changed from: protected */
    public abstract void g(byte[] bArr, int i, byte[] bArr2, int i2);

    /* access modifiers changed from: protected */
    public abstract int[] q(byte[] bArr);

    SerpentEngineBase() {
    }

    public void a(boolean encrypting, CipherParameters params) {
        if (params instanceof KeyParameter) {
            this.a = encrypting;
            this.b = q(((KeyParameter) params).a());
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to " + b() + " init - " + params.getClass().getName());
    }

    public String b() {
        return "Serpent";
    }

    public int c() {
        return 16;
    }

    public final int f(byte[] in, int inOff, byte[] out, int outOff) {
        if (this.b == null) {
            throw new IllegalStateException(b() + " not initialised");
        } else if (inOff + 16 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + 16 > out.length) {
            throw new OutputLengthException("output buffer too short");
        } else if (this.a) {
            g(in, inOff, out, outOff);
            return 16;
        } else {
            e(in, inOff, out, outOff);
            return 16;
        }
    }

    public void reset() {
    }

    protected static int r(int x, int bits) {
        return (x << bits) | (x >>> (-bits));
    }

    protected static int s(int x, int bits) {
        return (x >>> bits) | (x << (-bits));
    }

    /* access modifiers changed from: protected */
    public final void t(int a2, int b2, int c2, int d2) {
        int t1 = a2 ^ d2;
        int t3 = c2 ^ t1;
        int t4 = b2 ^ t3;
        int i = (a2 & d2) ^ t4;
        this.f = i;
        int t7 = (b2 & t1) ^ a2;
        this.e = (c2 | t7) ^ t4;
        int t12 = i & (t3 ^ t7);
        this.d = (~t3) ^ t12;
        this.c = (~t7) ^ t12;
    }

    /* access modifiers changed from: protected */
    public final void h(int a2, int b2, int c2, int d2) {
        int t1 = ~a2;
        int t2 = a2 ^ b2;
        int t4 = (t1 | t2) ^ d2;
        int t5 = c2 ^ t4;
        int i = t2 ^ t5;
        this.e = i;
        int t8 = (d2 & t2) ^ t1;
        int i2 = (i & t8) ^ t4;
        this.d = i2;
        int i3 = (i2 | t5) ^ (a2 & t4);
        this.f = i3;
        this.c = i3 ^ (t5 ^ t8);
    }

    /* access modifiers changed from: protected */
    public final void u(int a2, int b2, int c2, int d2) {
        int t2 = (~a2) ^ b2;
        int t5 = (a2 | t2) ^ c2;
        int i = d2 ^ t5;
        this.e = i;
        int t7 = (d2 | t2) ^ b2;
        int t8 = i ^ t2;
        int i2 = (t5 & t7) ^ t8;
        this.f = i2;
        int t11 = t5 ^ t7;
        this.d = i2 ^ t11;
        this.c = (t8 & t11) ^ t5;
    }

    /* access modifiers changed from: protected */
    public final void i(int a2, int b2, int c2, int d2) {
        int t1 = b2 ^ d2;
        int t3 = (b2 & t1) ^ a2;
        int t4 = t1 ^ t3;
        int i = c2 ^ t4;
        this.f = i;
        int t7 = (t1 & t3) ^ b2;
        int i2 = t3 ^ (i | t7);
        this.d = i2;
        int t10 = ~i2;
        int t11 = i ^ t7;
        this.c = t10 ^ t11;
        this.e = (t10 | t11) ^ t4;
    }

    /* access modifiers changed from: protected */
    public final void v(int a2, int b2, int c2, int d2) {
        int t1 = ~a2;
        int t2 = b2 ^ d2;
        int i = t2 ^ (c2 & t1);
        this.c = i;
        int t5 = c2 ^ t1;
        int t7 = b2 & (c2 ^ i);
        int i2 = t5 ^ t7;
        this.f = i2;
        int i3 = ((i | t5) & (d2 | t7)) ^ a2;
        this.e = i3;
        this.d = (i3 ^ (d2 | t1)) ^ (i2 ^ t2);
    }

    /* access modifiers changed from: protected */
    public final void j(int a2, int b2, int c2, int d2) {
        int t1 = b2 ^ d2;
        int t2 = ~t1;
        int t3 = a2 ^ c2;
        int t4 = c2 ^ t1;
        int i = t3 ^ (b2 & t4);
        this.c = i;
        int i2 = t1 ^ (t3 | (d2 ^ (a2 | t2)));
        this.f = i2;
        int t11 = ~t4;
        int t12 = i | i2;
        this.d = t11 ^ t12;
        this.e = (d2 & t11) ^ (t3 ^ t12);
    }

    /* access modifiers changed from: protected */
    public final void w(int a2, int b2, int c2, int d2) {
        int t1 = a2 ^ b2;
        int t3 = a2 | d2;
        int t4 = c2 ^ d2;
        int t6 = (a2 & c2) | (t1 & t3);
        int i = t4 ^ t6;
        this.e = i;
        int t9 = t6 ^ (b2 ^ t3);
        int i2 = t1 ^ (t4 & t9);
        this.c = i2;
        int t12 = i & i2;
        this.d = t9 ^ t12;
        this.f = (b2 | d2) ^ (t4 ^ t12);
    }

    /* access modifiers changed from: protected */
    public final void k(int a2, int b2, int c2, int d2) {
        int t2 = b2 ^ c2;
        int t4 = a2 ^ (b2 & t2);
        int t6 = d2 | t4;
        int i = t2 ^ t6;
        this.c = i;
        int t9 = d2 ^ (t2 | t6);
        this.e = (c2 ^ t4) ^ t9;
        int t11 = (a2 | b2) ^ t9;
        int i2 = t4 ^ (i & t11);
        this.f = i2;
        this.d = (i ^ t11) ^ i2;
    }

    /* access modifiers changed from: protected */
    public final void x(int a2, int b2, int c2, int d2) {
        int t1 = a2 ^ d2;
        int t3 = c2 ^ (d2 & t1);
        int t4 = b2 | t3;
        this.f = t1 ^ t4;
        int t6 = ~b2;
        int i = t3 ^ (t1 | t6);
        this.c = i;
        int t10 = t1 ^ t6;
        int i2 = (i & a2) ^ (t4 & t10);
        this.e = i2;
        this.d = (i2 & t10) ^ (a2 ^ t3);
    }

    /* access modifiers changed from: protected */
    public final void l(int a2, int b2, int c2, int d2) {
        int t3 = b2 ^ (a2 & (c2 | d2));
        int t5 = c2 ^ (a2 & t3);
        int i = d2 ^ t5;
        this.d = i;
        int t7 = ~a2;
        int i2 = t3 ^ (t5 & i);
        this.f = i2;
        int t11 = d2 ^ (i | t7);
        this.c = i2 ^ t11;
        this.e = (i ^ t7) ^ (t3 & t11);
    }

    /* access modifiers changed from: protected */
    public final void y(int a2, int b2, int c2, int d2) {
        int i = a2;
        int t1 = ~i;
        int t2 = i ^ b2;
        int t3 = i ^ d2;
        int i2 = (c2 ^ t1) ^ (t2 | t3);
        this.c = i2;
        int t7 = d2 & i2;
        int i3 = t7 ^ (t2 ^ i2);
        this.d = i3;
        int t12 = t3 ^ (i2 | t1);
        this.e = (t2 | t7) ^ t12;
        this.f = (i3 & t12) ^ (b2 ^ t7);
    }

    /* access modifiers changed from: protected */
    public final void m(int a2, int b2, int c2, int d2) {
        int t1 = ~c2;
        int t3 = d2 ^ (b2 & t1);
        int t4 = a2 & t3;
        int i = t4 ^ (b2 ^ t1);
        this.f = i;
        int t7 = i | b2;
        this.d = t3 ^ (a2 & t7);
        int t10 = a2 | d2;
        this.c = t10 ^ (t1 ^ t7);
        this.e = (b2 & t10) ^ ((a2 ^ c2) | t4);
    }

    /* access modifiers changed from: protected */
    public final void z(int a2, int b2, int c2, int d2) {
        int t2 = a2 ^ d2;
        int t3 = b2 ^ t2;
        int t5 = c2 ^ ((~a2) | t2);
        int i = b2 ^ t5;
        this.d = i;
        int t8 = d2 ^ (i | t2);
        int i2 = t3 ^ (t5 & t8);
        this.e = i2;
        int t11 = t5 ^ t8;
        this.c = i2 ^ t11;
        this.f = (~t5) ^ (t3 & t11);
    }

    /* access modifiers changed from: protected */
    public final void n(int a2, int b2, int c2, int d2) {
        int t1 = ~a2;
        int t2 = a2 ^ b2;
        int t3 = c2 ^ t2;
        int t5 = d2 ^ (c2 | t1);
        this.d = t3 ^ t5;
        int t8 = t2 ^ (t3 & t5);
        int i = t5 ^ (b2 | t8);
        this.f = i;
        int t11 = i | b2;
        this.c = t8 ^ t11;
        this.e = (d2 & t1) ^ (t3 ^ t11);
    }

    /* access modifiers changed from: protected */
    public final void A(int a2, int b2, int c2, int d2) {
        int t1 = b2 ^ c2;
        int t3 = d2 ^ (c2 & t1);
        int t4 = a2 ^ t3;
        int i = b2 ^ (t4 & (d2 | t1));
        this.d = i;
        int i2 = t1 ^ (a2 & t4);
        this.f = i2;
        int t11 = t4 ^ (i | t3);
        int i3 = t3 ^ (i2 & t11);
        this.e = i3;
        this.c = (i2 & i3) ^ (~t11);
    }

    /* access modifiers changed from: protected */
    public final void o(int a2, int b2, int c2, int d2) {
        int t3 = (a2 & b2) | c2;
        int t4 = (a2 | b2) & d2;
        int i = t3 ^ t4;
        this.f = i;
        int t7 = b2 ^ t4;
        int i2 = a2 ^ ((i ^ (~d2)) | t7);
        this.d = i2;
        int i3 = (c2 ^ t7) ^ (d2 | i2);
        this.c = i3;
        this.e = ((i & a2) ^ i3) ^ (i2 ^ t3);
    }

    /* access modifiers changed from: protected */
    public final void d() {
        int x0 = r(this.c, 13);
        int x2 = r(this.e, 3);
        int x3 = (this.f ^ x2) ^ (x0 << 3);
        this.d = r((this.d ^ x0) ^ x2, 1);
        int r = r(x3, 7);
        this.f = r;
        this.c = r(r ^ (this.d ^ x0), 5);
        this.e = r((this.d << 7) ^ (this.f ^ x2), 22);
    }

    /* access modifiers changed from: protected */
    public final void p() {
        int x2 = (s(this.e, 22) ^ this.f) ^ (this.d << 7);
        int s = s(this.c, 5) ^ this.d;
        int i = this.f;
        int x0 = s ^ i;
        int x3 = s(i, 7);
        int x1 = s(this.d, 1);
        this.f = (x3 ^ x2) ^ (x0 << 3);
        this.d = (x1 ^ x0) ^ x2;
        this.e = s(x2, 3);
        this.c = s(x0, 13);
    }
}
