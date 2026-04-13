package org.spongycastle.pqc.crypto.newhope;

import org.spongycastle.crypto.digests.SHAKEDigest;
import org.spongycastle.util.Pack;

public class Poly {
    Poly() {
    }

    static void a(short[] x, short[] y, short[] z) {
        for (int i = 0; i < 1024; i++) {
            z[i] = Reduce.a((short) (x[i] + y[i]));
        }
    }

    static void b(short[] r, byte[] a) {
        for (int i = 0; i < 256; i++) {
            int j = i * 7;
            int a1 = a[j + 1] & 255;
            int a3 = a[j + 3] & 255;
            int a5 = a[j + 5] & 255;
            int k = i * 4;
            r[k + 0] = (short) (((a1 & 63) << 8) | (a[j + 0] & 255));
            r[k + 1] = (short) ((a1 >>> 6) | ((a[j + 2] & 255) << 2) | ((a3 & 15) << 10));
            r[k + 2] = (short) ((a3 >>> 4) | ((a[j + 4] & 255) << 4) | ((a5 & 3) << 12));
            r[k + 3] = (short) ((a5 >>> 2) | ((a[j + 6] & 255) << 6));
        }
    }

    static void c(short[] r) {
        NTT.a(r);
        NTT.b(r, Precomp.b);
        NTT.c(r, Precomp.d);
    }

    static void d(short[] r, byte[] seed, byte nonce) {
        byte[] iv = new byte[8];
        iv[0] = nonce;
        byte[] buf = new byte[4096];
        ChaCha20.a(seed, iv, buf, 0, buf.length);
        for (int i = 0; i < 1024; i++) {
            int t = Pack.a(buf, i * 4);
            int d = 0;
            for (int j = 0; j < 8; j++) {
                d += (t >> j) & 16843009;
            }
            r[i] = (short) (((((d >>> 24) + (d >>> 0)) & 255) + 12289) - (((d >>> 16) + (d >>> 8)) & 255));
        }
    }

    static void f(short[] x, short[] y, short[] z) {
        for (int i = 0; i < 1024; i++) {
            z[i] = Reduce.b((65535 & Reduce.b((y[i] & 65535) * 3186)) * (x[i] & 65535));
        }
    }

    static void g(byte[] r, short[] p) {
        for (int i = 0; i < 256; i++) {
            int j = i * 4;
            short t0 = e(p[j + 0]);
            short t1 = e(p[j + 1]);
            short t2 = e(p[j + 2]);
            short t3 = e(p[j + 3]);
            int k = i * 7;
            r[k + 0] = (byte) t0;
            r[k + 1] = (byte) ((t0 >> 8) | (t1 << 6));
            r[k + 2] = (byte) (t1 >> 2);
            r[k + 3] = (byte) ((t1 >> 10) | (t2 << 4));
            r[k + 4] = (byte) (t2 >> 4);
            r[k + 5] = (byte) ((t2 >> 12) | (t3 << 2));
            r[k + 6] = (byte) (t3 >> 6);
        }
    }

    static void h(short[] r) {
        NTT.c(r, Precomp.c);
        NTT.b(r, Precomp.a);
    }

    static void i(short[] a, byte[] seed) {
        SHAKEDigest xof = new SHAKEDigest(128);
        xof.update(seed, 0, seed.length);
        int pos = 0;
        while (true) {
            byte[] output = new byte[256];
            xof.F(output, 0, output.length);
            int i = 0;
            while (true) {
                if (i < output.length) {
                    int val = ((output[i] & 255) | ((output[i + 1] & 255) << 8)) & 16383;
                    if (val < 12289) {
                        int pos2 = pos + 1;
                        a[pos] = (short) val;
                        if (pos2 != 1024) {
                            pos = pos2;
                        } else {
                            return;
                        }
                    }
                    i += 2;
                }
            }
        }
    }

    private static short e(short x) {
        int t = Reduce.a(x);
        int m = t - 12289;
        return (short) (m ^ ((t ^ m) & (m >> 31)));
    }
}
