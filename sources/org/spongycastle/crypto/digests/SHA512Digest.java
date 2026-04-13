package org.spongycastle.crypto.digests;

import org.spongycastle.util.Memoable;
import org.spongycastle.util.Pack;

public class SHA512Digest extends LongDigest {
    public SHA512Digest() {
    }

    public SHA512Digest(SHA512Digest t) {
        super(t);
    }

    public String b() {
        return "SHA-512";
    }

    public int e() {
        return 64;
    }

    public int c(byte[] out, int outOff) {
        v();
        Pack.p(this.f, out, outOff);
        Pack.p(this.g, out, outOff + 8);
        Pack.p(this.h, out, outOff + 16);
        Pack.p(this.i, out, outOff + 24);
        Pack.p(this.j, out, outOff + 32);
        Pack.p(this.k, out, outOff + 40);
        Pack.p(this.l, out, outOff + 48);
        Pack.p(this.m, out, outOff + 56);
        reset();
        return 64;
    }

    public void reset() {
        super.reset();
        this.f = 7640891576956012808L;
        this.g = -4942790177534073029L;
        this.h = 4354685564936845355L;
        this.i = -6534734903238641935L;
        this.j = 5840696475078001361L;
        this.k = -7276294671716946913L;
        this.l = 2270897969802886507L;
        this.m = 6620516959819538809L;
    }

    public Memoable copy() {
        return new SHA512Digest(this);
    }

    public void m(Memoable other) {
        u((SHA512Digest) other);
    }
}
