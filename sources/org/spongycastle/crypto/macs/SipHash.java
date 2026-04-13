package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Pack;

public class SipHash implements Mac {
    protected final int a;
    protected final int b;
    protected long c;
    protected long d;
    protected long e;
    protected long f;
    protected long g;
    protected long h;
    protected long i;
    protected int j;
    protected int k;

    public SipHash() {
        this.i = 0;
        this.j = 0;
        this.k = 0;
        this.a = 2;
        this.b = 4;
    }

    public SipHash(int c2, int d2) {
        this.i = 0;
        this.j = 0;
        this.k = 0;
        this.a = c2;
        this.b = d2;
    }

    public String b() {
        return "SipHash-" + this.a + "-" + this.b;
    }

    public int e() {
        return 8;
    }

    public void a(CipherParameters params) {
        if (params instanceof KeyParameter) {
            byte[] key = ((KeyParameter) params).a();
            if (key.length == 16) {
                this.c = Pack.m(key, 0);
                this.d = Pack.m(key, 8);
                reset();
                return;
            }
            throw new IllegalArgumentException("'params' must be a 128-bit key");
        }
        throw new IllegalArgumentException("'params' must be an instance of KeyParameter");
    }

    public void d(byte input) {
        long j2 = this.i >>> 8;
        this.i = j2;
        this.i = j2 | ((((long) input) & 255) << 56);
        int i2 = this.j + 1;
        this.j = i2;
        if (i2 == 8) {
            h();
            this.j = 0;
        }
    }

    public void update(byte[] input, int offset, int length) {
        byte[] bArr = input;
        int i2 = length;
        int i3 = 0;
        int fullWords = i2 & -8;
        int i4 = this.j;
        if (i4 == 0) {
            while (i3 < fullWords) {
                this.i = Pack.m(bArr, offset + i3);
                h();
                i3 += 8;
            }
            while (i3 < i2) {
                long j2 = this.i >>> 8;
                this.i = j2;
                this.i = j2 | ((((long) bArr[offset + i3]) & 255) << 56);
                i3++;
            }
            this.j = i2 - fullWords;
            return;
        }
        int bits = i4 << 3;
        while (i3 < fullWords) {
            long n = Pack.m(bArr, offset + i3);
            this.i = (n << bits) | (this.i >>> (-bits));
            h();
            this.i = n;
            i3 += 8;
        }
        while (i3 < i2) {
            long j3 = this.i >>> 8;
            this.i = j3;
            this.i = j3 | ((((long) bArr[offset + i3]) & 255) << 56);
            int i5 = this.j + 1;
            this.j = i5;
            if (i5 == 8) {
                h();
                this.j = 0;
            }
            i3++;
        }
    }

    public long g() {
        long j2 = this.i;
        int i2 = this.j;
        long j3 = j2 >>> ((7 - i2) << 3);
        this.i = j3;
        long j4 = j3 >>> 8;
        this.i = j4;
        this.i = j4 | ((((long) ((this.k << 3) + i2)) & 255) << 56);
        h();
        this.g ^= 255;
        f(this.b);
        long result = ((this.e ^ this.f) ^ this.g) ^ this.h;
        reset();
        return result;
    }

    public int c(byte[] out, int outOff) {
        Pack.r(g(), out, outOff);
        return 8;
    }

    public void reset() {
        long j2 = this.c;
        this.e = 8317987319222330741L ^ j2;
        long j3 = this.d;
        this.f = 7237128888997146477L ^ j3;
        this.g = j2 ^ 7816392313619706465L;
        this.h = 8387220255154660723L ^ j3;
        this.i = 0;
        this.j = 0;
        this.k = 0;
    }

    /* access modifiers changed from: protected */
    public void h() {
        this.k++;
        this.h ^= this.i;
        f(this.a);
        this.e ^= this.i;
    }

    /* access modifiers changed from: protected */
    public void f(int n) {
        long r0 = this.e;
        long r1 = this.f;
        long r2 = this.g;
        long r3 = this.h;
        for (int r = 0; r < n; r++) {
            long r02 = r0 + r1;
            long r22 = r2 + r3;
            long r12 = i(r1, 13) ^ r02;
            long r32 = i(r3, 16) ^ r22;
            long r23 = r22 + r12;
            r0 = i(r02, 32) + r32;
            r1 = i(r12, 17) ^ r23;
            r3 = i(r32, 21) ^ r0;
            r2 = i(r23, 32);
        }
        this.e = r0;
        this.f = r1;
        this.g = r2;
        this.h = r3;
    }

    protected static long i(long x, int n) {
        return (x << n) | (x >>> (-n));
    }
}
