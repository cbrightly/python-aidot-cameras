package org.spongycastle.crypto.engines;

import org.spongycastle.util.Pack;

public class ChaCha7539Engine extends Salsa20Engine {
    public String b() {
        return "ChaCha7539-" + this.d;
    }

    /* access modifiers changed from: protected */
    public int i() {
        return 12;
    }

    /* access modifiers changed from: protected */
    public void f(long diff) {
        int lo = (int) diff;
        if (((int) (diff >>> 32)) <= 0) {
            int[] iArr = this.f;
            int oldState = iArr[12];
            iArr[12] = iArr[12] + lo;
            if (oldState != 0 && iArr[12] < oldState) {
                throw new IllegalStateException("attempt to increase counter past 2^32.");
            }
            return;
        }
        throw new IllegalStateException("attempt to increase counter past 2^32.");
    }

    /* access modifiers changed from: protected */
    public void c() {
        int[] iArr = this.f;
        int i = iArr[12] + 1;
        iArr[12] = i;
        if (i == 0) {
            throw new IllegalStateException("attempt to increase counter past 2^32.");
        }
    }

    /* access modifiers changed from: protected */
    public void p(long diff) {
        int lo = (int) diff;
        if (((int) (diff >>> 32)) == 0) {
            int[] iArr = this.f;
            if ((((long) iArr[12]) & 4294967295L) >= (4294967295L & ((long) lo))) {
                iArr[12] = iArr[12] - lo;
                return;
            }
            throw new IllegalStateException("attempt to reduce counter past zero.");
        }
        throw new IllegalStateException("attempt to reduce counter past zero.");
    }

    /* access modifiers changed from: protected */
    public void o() {
        int[] iArr = this.f;
        if (iArr[12] != 0) {
            iArr[12] = iArr[12] - 1;
            return;
        }
        throw new IllegalStateException("attempt to reduce counter past zero.");
    }

    /* access modifiers changed from: protected */
    public long h() {
        return ((long) this.f[12]) & 4294967295L;
    }

    /* access modifiers changed from: protected */
    public void m() {
        this.f[12] = 0;
    }

    /* access modifiers changed from: protected */
    public void s(byte[] keyBytes, byte[] ivBytes) {
        if (keyBytes != null) {
            if (keyBytes.length == 32) {
                l(keyBytes.length, this.f, 0);
                Pack.k(keyBytes, 0, this.f, 4, 8);
            } else {
                throw new IllegalArgumentException(b() + " requires 256 bit key");
            }
        }
        Pack.k(ivBytes, 0, this.f, 13, 3);
    }

    /* access modifiers changed from: protected */
    public void g(byte[] output) {
        ChaChaEngine.t(this.d, this.f, this.g);
        Pack.i(this.g, output, 0);
    }
}
