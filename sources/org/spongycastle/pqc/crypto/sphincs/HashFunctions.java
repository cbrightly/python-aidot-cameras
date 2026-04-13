package org.spongycastle.pqc.crypto.sphincs;

import org.spongycastle.crypto.Digest;
import org.spongycastle.util.Strings;

public class HashFunctions {
    private static final byte[] a = Strings.f("expand 32-byte to 64-byte state!");
    private final Digest b;
    private final Digest c;
    private final Permute d;

    HashFunctions(Digest dig256) {
        this(dig256, (Digest) null);
    }

    HashFunctions(Digest dig256, Digest dig512) {
        this.d = new Permute();
        this.b = dig256;
        this.c = dig512;
    }

    /* access modifiers changed from: package-private */
    public int f(byte[] out, int outOff, byte[] in, int inLen) {
        this.b.update(in, 0, inLen);
        this.b.c(out, outOff);
        return 0;
    }

    /* access modifiers changed from: package-private */
    public Digest a() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public int b(byte[] out, int outOff, byte[] in, int inOff) {
        byte[] x = new byte[64];
        for (int i = 0; i < 32; i++) {
            x[i] = in[inOff + i];
            x[i + 32] = a[i];
        }
        this.d.a(x, x);
        for (int i2 = 0; i2 < 32; i2++) {
            x[i2] = (byte) (x[i2] ^ in[(inOff + i2) + 32]);
        }
        this.d.a(x, x);
        for (int i3 = 0; i3 < 32; i3++) {
            out[outOff + i3] = x[i3];
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int c(byte[] out, int outOff, byte[] in, int inOff, byte[] mask, int maskOff) {
        byte[] buf = new byte[64];
        for (int i = 0; i < 64; i++) {
            buf[i] = (byte) (in[inOff + i] ^ mask[maskOff + i]);
        }
        return b(out, outOff, buf, 0);
    }

    /* access modifiers changed from: package-private */
    public int d(byte[] out, int outOff, byte[] in, int inOff) {
        byte[] x = new byte[64];
        for (int i = 0; i < 32; i++) {
            x[i] = in[inOff + i];
            x[i + 32] = a[i];
        }
        this.d.a(x, x);
        for (int i2 = 0; i2 < 32; i2++) {
            out[outOff + i2] = x[i2];
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int e(byte[] out, int outOff, byte[] in, int inOff, byte[] mask, int maskOff) {
        byte[] buf = new byte[32];
        for (int i = 0; i < 32; i++) {
            buf[i] = (byte) (in[inOff + i] ^ mask[maskOff + i]);
        }
        return d(out, outOff, buf, 0);
    }
}
