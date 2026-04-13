package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.paddings.BlockCipherPadding;

public class CFBBlockCipherMac implements Mac {
    private byte[] a;
    private byte[] b;
    private int c;
    private MacCFBBlockCipher d;
    private BlockCipherPadding e;
    private int f;

    public CFBBlockCipherMac(BlockCipher cipher) {
        this(cipher, 8, (cipher.c() * 8) / 2, (BlockCipherPadding) null);
    }

    public CFBBlockCipherMac(BlockCipher cipher, int cfbBitSize, int macSizeInBits, BlockCipherPadding padding) {
        this.e = null;
        if (macSizeInBits % 8 == 0) {
            this.a = new byte[cipher.c()];
            MacCFBBlockCipher macCFBBlockCipher = new MacCFBBlockCipher(cipher, cfbBitSize);
            this.d = macCFBBlockCipher;
            this.e = padding;
            this.f = macSizeInBits / 8;
            this.b = new byte[macCFBBlockCipher.b()];
            this.c = 0;
            return;
        }
        throw new IllegalArgumentException("MAC size must be multiple of 8");
    }

    public String b() {
        return this.d.a();
    }

    public void a(CipherParameters params) {
        reset();
        this.d.d(params);
    }

    public int e() {
        return this.f;
    }

    public void d(byte in) {
        int i = this.c;
        byte[] bArr = this.b;
        if (i == bArr.length) {
            this.d.e(bArr, 0, this.a, 0);
            this.c = 0;
        }
        byte[] bArr2 = this.b;
        int i2 = this.c;
        this.c = i2 + 1;
        bArr2[i2] = in;
    }

    public void update(byte[] in, int inOff, int len) {
        if (len >= 0) {
            int blockSize = this.d.b();
            int i = this.c;
            int gapLen = blockSize - i;
            if (len > gapLen) {
                System.arraycopy(in, inOff, this.b, i, gapLen);
                int resultLen = 0 + this.d.e(this.b, 0, this.a, 0);
                this.c = 0;
                len -= gapLen;
                inOff += gapLen;
                while (len > blockSize) {
                    resultLen += this.d.e(in, inOff, this.a, 0);
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
        int blockSize = this.d.b();
        BlockCipherPadding blockCipherPadding = this.e;
        if (blockCipherPadding == null) {
            while (true) {
                int i = this.c;
                if (i >= blockSize) {
                    break;
                }
                this.b[i] = 0;
                this.c = i + 1;
            }
        } else {
            blockCipherPadding.c(this.b, this.c);
        }
        this.d.e(this.b, 0, this.a, 0);
        this.d.c(this.a);
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
                this.d.f();
                return;
            }
        }
    }
}
