package org.spongycastle.crypto.engines;

import org.glassfish.grizzly.http.server.util.MappingData;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public class TEAEngine implements BlockCipher {
    private int a;
    private int b;
    private int c;
    private int d;
    private boolean e = false;
    private boolean f;

    public String b() {
        return "TEA";
    }

    public int c() {
        return 8;
    }

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof KeyParameter) {
            this.f = forEncryption;
            this.e = true;
            h(((KeyParameter) params).a());
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to TEA init - " + params.getClass().getName());
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        if (!this.e) {
            throw new IllegalStateException(b() + " not initialised");
        } else if (inOff + 8 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + 8 > out.length) {
            throw new OutputLengthException("output buffer too short");
        } else if (this.f) {
            return g(in, inOff, out, outOff);
        } else {
            return e(in, inOff, out, outOff);
        }
    }

    public void reset() {
    }

    private void h(byte[] key) {
        if (key.length == 16) {
            this.a = d(key, 0);
            this.b = d(key, 4);
            this.c = d(key, 8);
            this.d = d(key, 12);
            return;
        }
        throw new IllegalArgumentException("Key size must be 128 bits.");
    }

    private int g(byte[] in, int inOff, byte[] out, int outOff) {
        int v0 = d(in, inOff);
        int v1 = d(in, inOff + 4);
        int sum = 0;
        for (int i = 0; i != 32; i++) {
            sum -= 1640531527;
            v0 += (((v1 << 4) + this.a) ^ (v1 + sum)) ^ ((v1 >>> 5) + this.b);
            v1 += (((v0 << 4) + this.c) ^ (v0 + sum)) ^ ((v0 >>> 5) + this.d);
        }
        i(v0, out, outOff);
        i(v1, out, outOff + 4);
        return 8;
    }

    private int e(byte[] in, int inOff, byte[] out, int outOff) {
        int v0 = d(in, inOff);
        int v1 = d(in, inOff + 4);
        int sum = -957401312;
        for (int i = 0; i != 32; i++) {
            v1 -= (((v0 << 4) + this.c) ^ (v0 + sum)) ^ ((v0 >>> 5) + this.d);
            v0 -= (((v1 << 4) + this.a) ^ (v1 + sum)) ^ ((v1 >>> 5) + this.b);
            sum += 1640531527;
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
