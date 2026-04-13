package org.spongycastle.crypto.engines;

import org.glassfish.grizzly.http.server.util.MappingData;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public class XTEAEngine implements BlockCipher {
    private int[] a = new int[4];
    private int[] b = new int[32];
    private int[] c = new int[32];
    private boolean d = false;
    private boolean e;

    public String b() {
        return "XTEA";
    }

    public int c() {
        return 8;
    }

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof KeyParameter) {
            this.e = forEncryption;
            this.d = true;
            h(((KeyParameter) params).a());
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to TEA init - " + params.getClass().getName());
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        if (!this.d) {
            throw new IllegalStateException(b() + " not initialised");
        } else if (inOff + 8 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + 8 > out.length) {
            throw new OutputLengthException("output buffer too short");
        } else if (this.e) {
            return g(in, inOff, out, outOff);
        } else {
            return e(in, inOff, out, outOff);
        }
    }

    public void reset() {
    }

    private void h(byte[] key) {
        if (key.length == 16) {
            int j = 0;
            int i = 0;
            while (i < 4) {
                this.a[i] = d(key, j);
                i++;
                j += 4;
            }
            int j2 = 0;
            for (int i2 = 0; i2 < 32; i2++) {
                int[] iArr = this.b;
                int[] iArr2 = this.a;
                iArr[i2] = iArr2[j2 & 3] + j2;
                j2 -= 1640531527;
                this.c[i2] = iArr2[(j2 >>> 11) & 3] + j2;
            }
            return;
        }
        throw new IllegalArgumentException("Key size must be 128 bits.");
    }

    private int g(byte[] in, int inOff, byte[] out, int outOff) {
        int v0 = d(in, inOff);
        int v1 = d(in, inOff + 4);
        for (int i = 0; i < 32; i++) {
            v0 += (((v1 << 4) ^ (v1 >>> 5)) + v1) ^ this.b[i];
            v1 += (((v0 << 4) ^ (v0 >>> 5)) + v0) ^ this.c[i];
        }
        i(v0, out, outOff);
        i(v1, out, outOff + 4);
        return 8;
    }

    private int e(byte[] in, int inOff, byte[] out, int outOff) {
        int v0 = d(in, inOff);
        int v1 = d(in, inOff + 4);
        for (int i = 31; i >= 0; i--) {
            v1 -= (((v0 << 4) ^ (v0 >>> 5)) + v0) ^ this.c[i];
            v0 -= (((v1 << 4) ^ (v1 >>> 5)) + v1) ^ this.b[i];
        }
        i(v0, out, outOff);
        i(v1, out, outOff + 4);
        return 8;
    }

    private int d(byte[] in, int inOff) {
        int inOff2 = inOff + 1;
        int inOff3 = inOff2 + 1;
        return (in[inOff] << 24) | ((in[inOff2] & 255) << MappingData.PATH) | ((in[inOff3] & 255) << 8) | (in[inOff3 + 1] & 255);
    }

    private void i(int v, byte[] out, int outOff) {
        int outOff2 = outOff + 1;
        out[outOff] = (byte) (v >>> 24);
        int outOff3 = outOff2 + 1;
        out[outOff2] = (byte) (v >>> 16);
        out[outOff3] = (byte) (v >>> 8);
        out[outOff3 + 1] = (byte) v;
    }
}
