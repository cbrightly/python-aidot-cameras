package org.spongycastle.crypto.prng;

import org.spongycastle.crypto.BlockCipher;

public class X931RNG {
    private final BlockCipher a;
    private final EntropySource b;
    private final byte[] c;
    private final byte[] d;
    private final byte[] e;
    private byte[] f;
    private long g;

    /* access modifiers changed from: package-private */
    public int a(byte[] output, boolean predictionResistant) {
        if (this.e.length == 8) {
            if (this.g > 32768) {
                return -1;
            }
            if (d(output, 512)) {
                throw new IllegalArgumentException("Number of bits per request limited to 4096");
            }
        } else if (this.g > 8388608) {
            return -1;
        } else {
            if (d(output, 32768)) {
                throw new IllegalArgumentException("Number of bits per request limited to 262144");
            }
        }
        if (predictionResistant || this.f == null) {
            byte[] a2 = this.b.a();
            this.f = a2;
            if (a2.length != this.a.c()) {
                throw new IllegalStateException("Insufficient entropy returned");
            }
        }
        int m = output.length / this.e.length;
        for (int i = 0; i < m; i++) {
            this.a.f(this.c, 0, this.d, 0);
            e(this.e, this.d, this.f);
            e(this.f, this.e, this.d);
            byte[] bArr = this.e;
            System.arraycopy(bArr, 0, output, bArr.length * i, bArr.length);
            c(this.c);
        }
        int bytesToCopy = output.length - (this.e.length * m);
        if (bytesToCopy > 0) {
            this.a.f(this.c, 0, this.d, 0);
            e(this.e, this.d, this.f);
            e(this.f, this.e, this.d);
            byte[] bArr2 = this.e;
            System.arraycopy(bArr2, 0, output, bArr2.length * m, bytesToCopy);
            c(this.c);
        }
        this.g++;
        return output.length;
    }

    /* access modifiers changed from: package-private */
    public void f() {
        byte[] a2 = this.b.a();
        this.f = a2;
        if (a2.length == this.a.c()) {
            this.g = 1;
            return;
        }
        throw new IllegalStateException("Insufficient entropy returned");
    }

    /* access modifiers changed from: package-private */
    public EntropySource b() {
        return this.b;
    }

    private void e(byte[] res, byte[] a2, byte[] b2) {
        for (int i = 0; i != res.length; i++) {
            res[i] = (byte) (a2[i] ^ b2[i]);
        }
        this.a.f(res, 0, res, 0);
    }

    private void c(byte[] val) {
        int i = val.length - 1;
        while (i >= 0) {
            byte b2 = (byte) (val[i] + 1);
            val[i] = b2;
            if (b2 == 0) {
                i--;
            } else {
                return;
            }
        }
    }

    private static boolean d(byte[] bytes, int maxBytes) {
        return bytes != null && bytes.length > maxBytes;
    }
}
