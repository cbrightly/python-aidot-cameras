package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.engines.DSTU7624Engine;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Arrays;

public class DSTU7624Mac implements Mac {
    private byte[] a;
    private int b;
    private int c;
    private int d;
    private DSTU7624Engine e;
    private byte[] f;
    private byte[] g;
    private byte[] h;

    public void a(CipherParameters params) {
        if (params instanceof KeyParameter) {
            this.e.a(true, params);
            DSTU7624Engine dSTU7624Engine = this.e;
            byte[] bArr = this.h;
            dSTU7624Engine.f(bArr, 0, bArr, 0);
            return;
        }
        throw new IllegalArgumentException("Invalid parameter passed to DSTU7624Mac");
    }

    public String b() {
        return "DSTU7624Mac";
    }

    public int e() {
        return this.c;
    }

    public void d(byte in) {
        int i = this.b;
        byte[] bArr = this.a;
        if (i == bArr.length) {
            f(bArr, 0);
            this.b = 0;
        }
        byte[] bArr2 = this.a;
        int i2 = this.b;
        this.b = i2 + 1;
        bArr2[i2] = in;
    }

    public void update(byte[] in, int inOff, int len) {
        if (len >= 0) {
            int blockSize = this.e.c();
            int i = this.b;
            int gapLen = blockSize - i;
            if (len > gapLen) {
                System.arraycopy(in, inOff, this.a, i, gapLen);
                f(this.a, 0);
                this.b = 0;
                len -= gapLen;
                inOff += gapLen;
                while (len > blockSize) {
                    f(in, inOff);
                    len -= blockSize;
                    inOff += blockSize;
                }
            }
            System.arraycopy(in, inOff, this.a, this.b, len);
            this.b += len;
            return;
        }
        throw new IllegalArgumentException("can't have a negative input length!");
    }

    private void f(byte[] in, int inOff) {
        g(this.f, 0, in, inOff, this.g);
        this.e.f(this.g, 0, this.f, 0);
    }

    public int c(byte[] out, int outOff) {
        int i = this.b;
        byte[] bArr = this.a;
        if (i % bArr.length == 0) {
            g(this.f, 0, bArr, 0, this.g);
            g(this.g, 0, this.h, 0, this.f);
            DSTU7624Engine dSTU7624Engine = this.e;
            byte[] bArr2 = this.f;
            dSTU7624Engine.f(bArr2, 0, bArr2, 0);
            int i2 = this.c;
            if (i2 + outOff <= out.length) {
                System.arraycopy(this.f, 0, out, outOff, i2);
                return this.c;
            }
            throw new OutputLengthException("output buffer too short");
        }
        throw new DataLengthException("input must be a multiple of blocksize");
    }

    public void reset() {
        Arrays.F(this.f, (byte) 0);
        Arrays.F(this.g, (byte) 0);
        Arrays.F(this.h, (byte) 0);
        Arrays.F(this.a, (byte) 0);
        this.e.reset();
        DSTU7624Engine dSTU7624Engine = this.e;
        byte[] bArr = this.h;
        dSTU7624Engine.f(bArr, 0, bArr, 0);
        this.b = 0;
    }

    private void g(byte[] x, int xOff, byte[] y, int yOff, byte[] x_xor_y) {
        int length = x.length - xOff;
        int i = this.d;
        if (length < i || y.length - yOff < i || x_xor_y.length < i) {
            throw new IllegalArgumentException("some of input buffers too short");
        }
        for (int byteIndex = 0; byteIndex < this.d; byteIndex++) {
            x_xor_y[byteIndex] = (byte) (x[byteIndex + xOff] ^ y[byteIndex + yOff]);
        }
    }
}
