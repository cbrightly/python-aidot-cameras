package org.spongycastle.crypto.modes.gcm;

public class BasicGCMMultiplier implements GCMMultiplier {
    private int[] a;

    public void a(byte[] H) {
        this.a = GCMUtil.c(H);
    }

    public void b(byte[] x) {
        int[] t = GCMUtil.c(x);
        GCMUtil.f(t, this.a);
        GCMUtil.a(t, x);
    }
}
