package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.ExtendedDigest;

public class ShortenedDigest implements ExtendedDigest {
    private ExtendedDigest a;
    private int b;

    public String b() {
        return this.a.b() + "(" + (this.b * 8) + ")";
    }

    public int e() {
        return this.b;
    }

    public void d(byte in) {
        this.a.d(in);
    }

    public void update(byte[] in, int inOff, int len) {
        this.a.update(in, inOff, len);
    }

    public int c(byte[] out, int outOff) {
        byte[] tmp = new byte[this.a.e()];
        this.a.c(tmp, 0);
        System.arraycopy(tmp, 0, out, outOff, this.b);
        return this.b;
    }

    public void reset() {
        this.a.reset();
    }

    public int k() {
        return this.a.k();
    }
}
