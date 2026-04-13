package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.OutputLengthException;

public class PaddedBlockCipher extends BufferedBlockCipher {
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
            return total - bArr.length;
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
        int resultLen;
        int blockSize = this.d.c();
        int resultLen2 = 0;
        if (this.c) {
            if (this.b == blockSize) {
                if ((blockSize * 2) + outOff <= out.length) {
                    resultLen2 = this.d.f(this.a, 0, out, outOff);
                    this.b = 0;
                } else {
                    throw new OutputLengthException("output buffer too short");
                }
            }
            byte code = (byte) (blockSize - this.b);
            while (true) {
                int i = this.b;
                if (i >= blockSize) {
                    break;
                }
                this.a[i] = code;
                this.b = i + 1;
            }
            resultLen = resultLen2 + this.d.f(this.a, 0, out, outOff + resultLen2);
        } else if (this.b == blockSize) {
            BlockCipher blockCipher = this.d;
            byte[] bArr = this.a;
            int resultLen3 = blockCipher.f(bArr, 0, bArr, 0);
            this.b = 0;
            byte[] bArr2 = this.a;
            int count = bArr2[blockSize - 1] & 255;
            if (count < 0 || count > blockSize) {
                throw new InvalidCipherTextException("pad block corrupted");
            }
            resultLen = resultLen3 - count;
            System.arraycopy(bArr2, 0, out, outOff, resultLen);
        } else {
            throw new DataLengthException("last block incomplete in decryption");
        }
        h();
        return resultLen;
    }
}
