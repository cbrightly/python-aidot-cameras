package org.spongycastle.crypto.paddings;

import java.security.SecureRandom;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.ParametersWithRandom;

public class PaddedBufferedBlockCipher extends BufferedBlockCipher {
    BlockCipherPadding g;

    public PaddedBufferedBlockCipher(BlockCipher cipher, BlockCipherPadding padding) {
        this.d = cipher;
        this.g = padding;
        this.a = new byte[cipher.c()];
        this.b = 0;
    }

    public PaddedBufferedBlockCipher(BlockCipher cipher) {
        this(cipher, new PKCS7Padding());
    }

    public void f(boolean forEncryption, CipherParameters params) {
        this.c = forEncryption;
        h();
        if (params instanceof ParametersWithRandom) {
            ParametersWithRandom p = (ParametersWithRandom) params;
            this.g.b(p.b());
            this.d.a(forEncryption, p.a());
            return;
        }
        this.g.b((SecureRandom) null);
        this.d.a(forEncryption, params);
    }

    public int c(int len) {
        int total = this.b + len;
        byte[] bArr = this.a;
        int leftOver = total % bArr.length;
        if (leftOver != 0) {
            return (total - leftOver) + bArr.length;
        }
        if (this.c) {
            return bArr.length + total;
        }
        return total;
    }

    public int e(int len) {
        int total = this.b + len;
        byte[] bArr = this.a;
        int leftOver = total % bArr.length;
        if (leftOver == 0) {
            return Math.max(0, total - bArr.length);
        }
        return total - leftOver;
    }

    public int g(byte[] in, int inOff, int len, byte[] out, int outOff) {
        if (len >= 0) {
            int blockSize = b();
            int length = e(len);
            if (length <= 0 || outOff + length <= out.length) {
                int resultLen = 0;
                byte[] bArr = this.a;
                int length2 = bArr.length;
                int i = this.b;
                int gapLen = length2 - i;
                if (len > gapLen) {
                    System.arraycopy(in, inOff, bArr, i, gapLen);
                    resultLen = 0 + this.d.f(this.a, 0, out, outOff);
                    this.b = 0;
                    len -= gapLen;
                    inOff += gapLen;
                    while (len > this.a.length) {
                        resultLen += this.d.f(in, inOff, out, outOff + resultLen);
                        len -= blockSize;
                        inOff += blockSize;
                    }
                }
                System.arraycopy(in, inOff, this.a, this.b, len);
                this.b += len;
                return resultLen;
            }
            throw new OutputLengthException("output buffer too short");
        }
        throw new IllegalArgumentException("Can't have a negative input length!");
    }

    public int a(byte[] out, int outOff) {
        int blockSize = this.d.c();
        int resultLen = 0;
        if (this.c) {
            if (this.b == blockSize) {
                if ((blockSize * 2) + outOff <= out.length) {
                    resultLen = this.d.f(this.a, 0, out, outOff);
                    this.b = 0;
                } else {
                    h();
                    throw new OutputLengthException("output buffer too short");
                }
            }
            this.g.c(this.a, this.b);
            int resultLen2 = resultLen + this.d.f(this.a, 0, out, outOff + resultLen);
            h();
            return resultLen2;
        } else if (this.b == blockSize) {
            BlockCipher blockCipher = this.d;
            byte[] bArr = this.a;
            int resultLen3 = blockCipher.f(bArr, 0, bArr, 0);
            this.b = 0;
            try {
                int resultLen4 = resultLen3 - this.g.a(this.a);
                System.arraycopy(this.a, 0, out, outOff, resultLen4);
                return resultLen4;
            } finally {
                h();
            }
        } else {
            h();
            throw new DataLengthException("last block incomplete in decryption");
        }
    }
}
