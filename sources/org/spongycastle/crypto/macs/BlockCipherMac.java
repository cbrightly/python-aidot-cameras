package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;

public class BlockCipherMac implements Mac {
    private byte[] a;
    private byte[] b;
    private int c;
    private BlockCipher d;
    private int e;

    public String b() {
        return this.d.b();
    }

    public void a(CipherParameters params) {
        reset();
        this.d.a(true, params);
    }

    public int e() {
        return this.e;
    }

    public void d(byte in) {
        int i = this.c;
        byte[] bArr = this.b;
        if (i == bArr.length) {
            this.d.f(bArr, 0, this.a, 0);
            this.c = 0;
        }
        byte[] bArr2 = this.b;
        int i2 = this.c;
        this.c = i2 + 1;
        bArr2[i2] = in;
    }

    public void update(byte[] in, int inOff, int len) {
        if (len >= 0) {
            int blockSize = this.d.c();
            int i = this.c;
            int gapLen = blockSize - i;
            if (len > gapLen) {
                System.arraycopy(in, inOff, this.b, i, gapLen);
                int resultLen = 0 + this.d.f(this.b, 0, this.a, 0);
                this.c = 0;
                len -= gapLen;
                inOff += gapLen;
                while (len > blockSize) {
                    resultLen += this.d.f(in, inOff, this.a, 0);
                    len -= blockSize;
                    inOff += blockSize;
                }
            }
            System.arraycopy(in, inOff, this.b, this.c, len);
            this.c += len;
            return;
        }
        throw new IllegalArgumentException("Can't have a negative input length!");
    }

    public int c(byte[] out, int outOff) {
        int blockSize = this.d.c();
        while (true) {
            int i = this.c;
            if (i < blockSize) {
                this.b[i] = 0;
                this.c = i + 1;
            } else {
                this.d.f(this.b, 0, this.a, 0);
                System.arraycopy(this.a, 0, out, outOff, this.e);
                reset();
                return this.e;
            }
        }
    }

    public void reset() {
        int i = 0;
        while (true) {
            byte[] bArr = this.b;
            if (i < bArr.length) {
                bArr[i] = 0;
                i++;
            } else {
                this.c = 0;
                this.d.reset();
                return;
            }
        }
    }
}
