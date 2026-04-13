package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.ExtendedDigest;

public class NonMemoableDigest implements ExtendedDigest {
    private ExtendedDigest a;

    public String b() {
        return this.a.b();
    }

    public int e() {
        return this.a.e();
    }

    public void d(byte in) {
        this.a.d(in);
    }

    public void update(byte[] in, int inOff, int len) {
        this.a.update(in, inOff, len);
    }

    public int c(byte[] out, int outOff) {
        return this.a.c(out, outOff);
    }

    public void reset() {
        this.a.reset();
    }

    public int k() {
        return this.a.k();
    }
}
