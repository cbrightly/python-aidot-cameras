package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.crypto.params.SkeinParameters;
import org.spongycastle.util.Memoable;

public class SkeinDigest implements ExtendedDigest, Memoable {
    private SkeinEngine a;

    public SkeinDigest(int stateSizeBits, int digestSizeBits) {
        this.a = new SkeinEngine(stateSizeBits, digestSizeBits);
        n((SkeinParameters) null);
    }

    public SkeinDigest(SkeinDigest digest) {
        this.a = new SkeinEngine(digest.a);
    }

    public void m(Memoable other) {
        this.a.m(((SkeinDigest) other).a);
    }

    public Memoable copy() {
        return new SkeinDigest(this);
    }

    public String b() {
        return "Skein-" + (this.a.f() * 8) + "-" + (this.a.g() * 8);
    }

    public int e() {
        return this.a.g();
    }

    public int k() {
        return this.a.f();
    }

    public void n(SkeinParameters params) {
        this.a.h(params);
    }

    public void reset() {
        this.a.l();
    }

    public void d(byte in) {
        this.a.r(in);
    }

    public void update(byte[] in, int inOff, int len) {
        this.a.s(in, inOff, len);
    }

    public int c(byte[] out, int outOff) {
        return this.a.e(out, outOff);
    }
}
