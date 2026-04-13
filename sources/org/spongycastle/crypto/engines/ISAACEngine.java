package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Pack;

public class ISAACEngine implements StreamCipher {
    private final int a = 8;
    private final int b = 256;
    private int[] c = null;
    private int[] d = null;
    private int e = 0;
    private int f = 0;
    private int g = 0;
    private int h = 0;
    private byte[] i = new byte[1024];
    private byte[] j = null;
    private boolean k = false;

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof KeyParameter) {
            g(((KeyParameter) params).a());
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to ISAAC init - " + params.getClass().getName());
    }

    public byte e(byte in) {
        if (this.h == 0) {
            c();
            this.i = Pack.g(this.d);
        }
        byte[] bArr = this.i;
        int i2 = this.h;
        byte out = (byte) (bArr[i2] ^ in);
        this.h = (i2 + 1) & 1023;
        return out;
    }

    public int d(byte[] in, int inOff, int len, byte[] out, int outOff) {
        if (!this.k) {
            throw new IllegalStateException(b() + " not initialised");
        } else if (inOff + len > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + len <= out.length) {
            for (int i2 = 0; i2 < len; i2++) {
                if (this.h == 0) {
                    c();
                    this.i = Pack.g(this.d);
                }
                byte[] bArr = this.i;
                int i3 = this.h;
                out[i2 + outOff] = (byte) (bArr[i3] ^ in[i2 + inOff]);
                this.h = (i3 + 1) & 1023;
            }
            return len;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public String b() {
        return "ISAAC";
    }

    public void reset() {
        g(this.j);
    }

    private void g(byte[] keyBytes) {
        this.j = keyBytes;
        if (this.c == null) {
            this.c = new int[256];
        }
        if (this.d == null) {
            this.d = new int[256];
        }
        for (int i2 = 0; i2 < 256; i2++) {
            int[] iArr = this.c;
            this.d[i2] = 0;
            iArr[i2] = 0;
        }
        this.g = 0;
        this.f = 0;
        this.e = 0;
        this.h = 0;
        byte[] t = new byte[(keyBytes.length + (keyBytes.length & 3))];
        System.arraycopy(keyBytes, 0, t, 0, keyBytes.length);
        for (int i3 = 0; i3 < t.length; i3 += 4) {
            this.d[i3 >>> 2] = Pack.j(t, i3);
        }
        int[] abcdefgh = new int[8];
        for (int i4 = 0; i4 < 8; i4++) {
            abcdefgh[i4] = -1640531527;
        }
        for (int i5 = 0; i5 < 4; i5++) {
            f(abcdefgh);
        }
        int i6 = 0;
        while (i6 < 2) {
            for (int j2 = 0; j2 < 256; j2 += 8) {
                for (int k2 = 0; k2 < 8; k2++) {
                    abcdefgh[k2] = abcdefgh[k2] + (i6 < 1 ? this.d[j2 + k2] : this.c[j2 + k2]);
                }
                f(abcdefgh);
                for (int k3 = 0; k3 < 8; k3++) {
                    this.c[j2 + k3] = abcdefgh[k3];
                }
            }
            i6++;
        }
        c();
        this.k = true;
    }

    private void c() {
        int i2 = this.f;
        int i3 = this.g + 1;
        this.g = i3;
        this.f = i2 + i3;
        for (int i4 = 0; i4 < 256; i4++) {
            int[] iArr = this.c;
            int x = iArr[i4];
            switch (i4 & 3) {
                case 0:
                    int i5 = this.e;
                    this.e = i5 ^ (i5 << 13);
                    break;
                case 1:
                    int i6 = this.e;
                    this.e = i6 ^ (i6 >>> 6);
                    break;
                case 2:
                    int i7 = this.e;
                    this.e = i7 ^ (i7 << 2);
                    break;
                case 3:
                    int i8 = this.e;
                    this.e = i8 ^ (i8 >>> 16);
                    break;
            }
            int i9 = this.e + iArr[(i4 + 128) & 255];
            this.e = i9;
            int i10 = iArr[(x >>> 2) & 255] + i9 + this.f;
            int y = i10;
            iArr[i4] = i10;
            int[] iArr2 = this.d;
            int i11 = iArr[(y >>> 10) & 255] + x;
            this.f = i11;
            iArr2[i4] = i11;
        }
    }

    private void f(int[] x) {
        x[0] = x[0] ^ (x[1] << 11);
        x[3] = x[3] + x[0];
        x[1] = x[1] + x[2];
        x[1] = x[1] ^ (x[2] >>> 2);
        x[4] = x[4] + x[1];
        x[2] = x[2] + x[3];
        x[2] = x[2] ^ (x[3] << 8);
        x[5] = x[5] + x[2];
        x[3] = x[3] + x[4];
        x[3] = x[3] ^ (x[4] >>> 16);
        x[6] = x[6] + x[3];
        x[4] = x[4] + x[5];
        x[4] = x[4] ^ (x[5] << 10);
        x[7] = x[7] + x[4];
        x[5] = x[5] + x[6];
        x[5] = (x[6] >>> 4) ^ x[5];
        x[0] = x[0] + x[5];
        x[6] = x[6] + x[7];
        x[6] = x[6] ^ (x[7] << 8);
        x[1] = x[1] + x[6];
        x[7] = x[7] + x[0];
        x[7] = x[7] ^ (x[0] >>> 9);
        x[2] = x[2] + x[7];
        x[0] = x[0] + x[1];
    }
}
