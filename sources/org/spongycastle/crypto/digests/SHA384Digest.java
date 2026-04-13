package org.spongycastle.crypto.digests;

import org.spongycastle.util.Memoable;
import org.spongycastle.util.Pack;

public class SHA384Digest extends LongDigest {
    public SHA384Digest() {
    }

    public SHA384Digest(SHA384Digest t) {
        super(t);
    }

    public String b() {
        return "SHA-384";
    }

    public int e() {
        return 48;
    }

    public int c(byte[] out, int outOff) {
        v();
        Pack.p(this.f, out, outOff);
        Pack.p(this.g, out, outOff + 8);
        Pack.p(this.h, out, outOff + 16);
        Pack.p(this.i, out, outOff + 24);
        Pack.p(this.j, out, outOff + 32);
        Pack.p(this.k, out, outOff + 40);
        reset();
        return 48;
    }

    public void reset() {
        super.reset();
        this.f = -3766243637369397544L;
        this.g = 7105036623409894663L;
        this.h = -7973340178411365097L;
        this.i = 1526699215303891257L;
        this.j = 7436329637833083697L;
        this.k = -8163818279084223215L;
        this.l = -2662702644619276377L;
        this.m = 5167115440072839076L;
    }

    public Memoable copy() {
        return new SHA384Digest(this);
    }

    public void m(Memoable other) {
        super.u((SHA384Digest) other);
    }
}
