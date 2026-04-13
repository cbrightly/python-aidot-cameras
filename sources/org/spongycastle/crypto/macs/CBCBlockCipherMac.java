package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.BlockCipherPadding;

public class CBCBlockCipherMac implements Mac {
    private byte[] a;
    private byte[] b;
    private int c;
    private BlockCipher d;
    private BlockCipherPadding e;
    private int f;

    public CBCBlockCipherMac(BlockCipher cipher) {
        this(cipher, (cipher.c() * 8) / 2, (BlockCipherPadding) null);
    }

    public CBCBlockCipherMac(BlockCipher cipher, int macSizeInBits) {
        this(cipher, macSizeInBits, (BlockCipherPadding) null);
    }

    public CBCBlockCipherMac(BlockCipher cipher, int macSizeInBits, BlockCipherPadding padding) {
        if (macSizeInBits % 8 == 0) {
            this.d = new CBCBlockCipher(cipher);
            this.e = padding;
            this.f = macSizeInBits / 8;
            this.a = new byte[cipher.c()];
            this.b = new byte[cipher.c()];
            this.c = 0;
            return;
        }
        throw new IllegalArgumentException("MAC size must be multiple of 8");
    }

    public String b() {
        return this.d.b();
    }

    public void a(CipherParameters params) {
        reset();
        this.d.a(true, params);
    }

    public int e() {
        return this.f;
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
                this.d.f(this.b, 0, this.a, 0);
                this.c = 0;
                len -= gapLen;
                inOff += gapLen;
                while (len > blockSize) {
                    this.d.f(in, inOff, this.a, 0);
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
        if (this.e == null) {
            while (true) {
                int i = this.c;
                if (i >= blockSize) {
                    break;
                }
                this.b[i] = 0;
                this.c = i + 1;
            }
        } else {
            if (this.c == blockSize) {
                this.d.f(this.b, 0, this.a, 0);
                this.c = 0;
            }
            this.e.c(this.b, this.c);
        }
        this.d.f(this.b, 0, this.a, 0);
        System.arraycopy(this.a, 0, out, outOff, this.f);
        reset();
        return this.f;
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
