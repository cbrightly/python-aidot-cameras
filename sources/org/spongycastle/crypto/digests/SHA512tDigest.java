package org.spongycastle.crypto.digests;

import org.spongycastle.util.Memoable;
import org.spongycastle.util.MemoableResetException;

public class SHA512tDigest extends LongDigest {
    private int p;
    private long q;
    private long r;
    private long s;
    private long t;
    private long u;
    private long v;
    private long w;
    private long x;

    public SHA512tDigest(int bitLength) {
        if (bitLength >= 512) {
            throw new IllegalArgumentException("bitLength cannot be >= 512");
        } else if (bitLength % 8 != 0) {
            throw new IllegalArgumentException("bitLength needs to be a multiple of 8");
        } else if (bitLength != 384) {
            int i = bitLength / 8;
            this.p = i;
            B(i * 8);
            reset();
        } else {
            throw new IllegalArgumentException("bitLength cannot be 384 use SHA384 instead");
        }
    }

    public SHA512tDigest(SHA512tDigest t2) {
        super(t2);
        this.p = t2.p;
        m(t2);
    }

    public String b() {
        return "SHA-512/" + Integer.toString(this.p * 8);
    }

    public int e() {
        return this.p;
    }

    public int c(byte[] out, int outOff) {
        v();
        A(this.f, out, outOff, this.p);
        A(this.g, out, outOff + 8, this.p - 8);
        A(this.h, out, outOff + 16, this.p - 16);
        A(this.i, out, outOff + 24, this.p - 24);
        A(this.j, out, outOff + 32, this.p - 32);
        A(this.k, out, outOff + 40, this.p - 40);
        A(this.l, out, outOff + 48, this.p - 48);
        A(this.m, out, outOff + 56, this.p - 56);
        reset();
        return this.p;
    }

    public void reset() {
        super.reset();
        this.f = this.q;
        this.g = this.r;
        this.h = this.s;
        this.i = this.t;
        this.j = this.u;
        this.k = this.v;
        this.l = this.w;
        this.m = this.x;
    }

    private void B(int bitLength) {
        this.f = -3482333909917012819L;
        this.g = 2216346199247487646L;
        this.h = -7364697282686394994L;
        this.i = 65953792586715988L;
        this.j = -816286391624063116L;
        this.k = 4512832404995164602L;
        this.l = -5033199132376557362L;
        this.m = -124578254951840548L;
        d((byte) 83);
        d((byte) 72);
        d((byte) 65);
        d((byte) 45);
        d((byte) 53);
        d((byte) 49);
        d((byte) 50);
        d((byte) 47);
        if (bitLength > 100) {
            d((byte) ((bitLength / 100) + 48));
            int bitLength2 = bitLength % 100;
            d((byte) ((bitLength2 / 10) + 48));
            d((byte) ((bitLength2 % 10) + 48));
        } else if (bitLength > 10) {
            d((byte) ((bitLength / 10) + 48));
            d((byte) ((bitLength % 10) + 48));
        } else {
            d((byte) (bitLength + 48));
        }
        v();
        this.q = this.f;
        this.r = this.g;
        this.s = this.h;
        this.t = this.i;
        this.u = this.j;
        this.v = this.k;
        this.w = this.l;
        this.x = this.m;
    }

    private static void A(long n, byte[] bs, int off, int max) {
        if (max > 0) {
            z((int) (n >>> 32), bs, off, max);
            if (max > 4) {
                z((int) (4294967295L & n), bs, off + 4, max - 4);
            }
        }
    }

    private static void z(int n, byte[] bs, int off, int max) {
        int num = Math.min(4, max);
        while (true) {
            num--;
            if (num >= 0) {
                bs[off + num] = (byte) (n >>> ((3 - num) * 8));
            } else {
                return;
            }
        }
    }

    public Memoable copy() {
        return new SHA512tDigest(this);
    }

    public void m(Memoable other) {
        SHA512tDigest t2 = (SHA512tDigest) other;
        if (this.p == t2.p) {
            super.u(t2);
            this.q = t2.q;
            this.r = t2.r;
            this.s = t2.s;
            this.t = t2.t;
            this.u = t2.u;
            this.v = t2.v;
            this.w = t2.w;
            this.x = t2.x;
            return;
        }
        throw new MemoableResetException("digestLength inappropriate in other");
    }
}
