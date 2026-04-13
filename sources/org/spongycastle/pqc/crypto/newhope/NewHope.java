package org.spongycastle.pqc.crypto.newhope;

import java.security.SecureRandom;
import org.spongycastle.crypto.digests.SHA3Digest;

public class NewHope {
    NewHope() {
    }

    public static void f(SecureRandom rand, byte[] send, short[] sk) {
        byte[] seed = new byte[32];
        rand.nextBytes(seed);
        short[] a = new short[1024];
        e(a, seed);
        byte[] noiseSeed = new byte[32];
        rand.nextBytes(noiseSeed);
        Poly.d(sk, noiseSeed, (byte) 0);
        Poly.h(sk);
        short[] e = new short[1024];
        Poly.d(e, noiseSeed, (byte) 1);
        Poly.h(e);
        short[] r = new short[1024];
        Poly.f(a, sk, r);
        short[] pk = new short[1024];
        Poly.a(r, e, pk);
        c(send, pk, seed);
    }

    public static void i(SecureRandom rand, byte[] sharedKey, byte[] send, byte[] received) {
        short[] pkA = new short[1024];
        byte[] seed = new byte[32];
        a(pkA, seed, received);
        short[] a = new short[1024];
        e(a, seed);
        byte[] noiseSeed = new byte[32];
        rand.nextBytes(noiseSeed);
        short[] sp = new short[1024];
        Poly.d(sp, noiseSeed, (byte) 0);
        Poly.h(sp);
        short[] ep = new short[1024];
        Poly.d(ep, noiseSeed, (byte) 1);
        Poly.h(ep);
        short[] bp = new short[1024];
        Poly.f(a, sp, bp);
        Poly.a(bp, ep, bp);
        short[] v = new short[1024];
        Poly.f(pkA, sp, v);
        Poly.c(v);
        short[] epp = new short[1024];
        Poly.d(epp, noiseSeed, (byte) 2);
        Poly.a(v, epp, v);
        short[] c = new short[1024];
        ErrorCorrection.e(c, v, noiseSeed, (byte) 3);
        d(send, bp, c);
        ErrorCorrection.f(sharedKey, v, c);
        g(sharedKey);
    }

    public static void h(byte[] sharedKey, short[] sk, byte[] received) {
        short[] bp = new short[1024];
        short[] c = new short[1024];
        b(bp, c, received);
        short[] v = new short[1024];
        Poly.f(sk, bp, v);
        Poly.c(v);
        ErrorCorrection.f(sharedKey, v, c);
        g(sharedKey);
    }

    static void a(short[] pk, byte[] seed, byte[] r) {
        Poly.b(pk, r);
        System.arraycopy(r, 1792, seed, 0, 32);
    }

    static void b(short[] b, short[] c, byte[] r) {
        Poly.b(b, r);
        for (int i = 0; i < 256; i++) {
            int j = i * 4;
            int ri = r[i + 1792] & 255;
            c[j + 0] = (short) (ri & 3);
            c[j + 1] = (short) ((ri >>> 2) & 3);
            c[j + 2] = (short) ((ri >>> 4) & 3);
            c[j + 3] = (short) (ri >>> 6);
        }
    }

    static void c(byte[] r, short[] pk, byte[] seed) {
        Poly.g(r, pk);
        System.arraycopy(seed, 0, r, 1792, 32);
    }

    static void d(byte[] r, short[] b, short[] c) {
        Poly.g(r, b);
        for (int i = 0; i < 256; i++) {
            int j = i * 4;
            r[i + 1792] = (byte) (c[j] | (c[j + 1] << 2) | (c[j + 2] << 4) | (c[j + 3] << 6));
        }
    }

    static void e(short[] a, byte[] seed) {
        Poly.i(a, seed);
    }

    static void g(byte[] sharedKey) {
        SHA3Digest d = new SHA3Digest(256);
        d.update(sharedKey, 0, 32);
        d.c(sharedKey, 0);
    }
}
