package org.spongycastle.crypto.prng;

import org.spongycastle.crypto.Digest;

public class DigestRandomGenerator implements RandomGenerator {
    private static long a = 10;
    private long b;
    private long c = 1;
    private Digest d;
    private byte[] e;
    private byte[] f;

    public DigestRandomGenerator(Digest digest) {
        this.d = digest;
        this.f = new byte[digest.e()];
        this.e = new byte[digest.e()];
        this.b = 1;
    }

    public void c(byte[] inSeed) {
        synchronized (this) {
            g(inSeed);
            g(this.f);
            f(this.f);
        }
    }

    public void a(byte[] bytes) {
        b(bytes, 0, bytes.length);
    }

    public void b(byte[] bytes, int start, int len) {
        synchronized (this) {
            int stateOff = 0;
            h();
            int end = start + len;
            int i = start;
            while (i != end) {
                if (stateOff == this.e.length) {
                    h();
                    stateOff = 0;
                }
                bytes[i] = this.e[stateOff];
                i++;
                stateOff++;
            }
        }
    }

    private void d() {
        g(this.f);
        long j = this.c;
        this.c = 1 + j;
        e(j);
        f(this.f);
    }

    private void h() {
        long j = this.b;
        this.b = 1 + j;
        e(j);
        g(this.e);
        g(this.f);
        f(this.e);
        if (this.b % a == 0) {
            d();
        }
    }

    private void e(long seed) {
        for (int i = 0; i != 8; i++) {
            this.d.d((byte) ((int) seed));
            seed >>>= 8;
        }
    }

    private void g(byte[] inSeed) {
        this.d.update(inSeed, 0, inSeed.length);
    }

    private void f(byte[] result) {
        this.d.c(result, 0);
    }
}
