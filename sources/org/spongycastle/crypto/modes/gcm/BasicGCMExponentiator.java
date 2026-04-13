package org.spongycastle.crypto.modes.gcm;

import org.spongycastle.util.Arrays;

public class BasicGCMExponentiator implements GCMExponentiator {
    private int[] a;

    public void a(byte[] x) {
        this.a = GCMUtil.c(x);
    }

    public void b(long pow, byte[] output) {
        int[] y = GCMUtil.i();
        if (pow > 0) {
            int[] powX = Arrays.k(this.a);
            do {
                if ((1 & pow) != 0) {
                    GCMUtil.f(y, powX);
                }
                GCMUtil.f(powX, powX);
                pow >>>= 1;
            } while (pow > 0);
        }
        GCMUtil.a(y, output);
    }
}
