package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;

public class OldCTSBlockCipher extends BufferedBlockCipher {
    public int e(int len) {
        int total = this.b + len;
        byte[] bArr = this.a;
        int leftOver = total % bArr.length;
        if (leftOver == 0) {
            return total - bArr.length;
        }
        return total - leftOver;
    }

    public int c(int len) {
        return this.b + len;
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
                    byte[] bArr2 = this.a;
                    System.arraycopy(bArr2, blockSize, bArr2, 0, blockSize);
                    this.b = blockSize;
                    len -= gapLen;
                    inOff += gapLen;
                    while (len > blockSize) {
                        System.arraycopy(in, inOff, this.a, this.b, blockSize);
                        resultLen += this.d.f(this.a, 0, out, outOff + resultLen);
                        byte[] bArr3 = this.a;
                        System.arraycopy(bArr3, blockSize, bArr3, 0, blockSize);
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
        if (this.b + outOff <= out.length) {
            int blockSize = this.d.c();
            int len = this.b - blockSize;
            byte[] block = new byte[blockSize];
            if (this.c) {
                this.d.f(this.a, 0, block, 0);
                if (this.b >= blockSize) {
                    int i = this.b;
                    while (true) {
                        byte[] bArr = this.a;
                        if (i == bArr.length) {
                            break;
                        }
                        bArr[i] = block[i - blockSize];
                        i++;
                    }
                    for (int i2 = blockSize; i2 != this.b; i2++) {
                        byte[] bArr2 = this.a;
                        bArr2[i2] = (byte) (bArr2[i2] ^ block[i2 - blockSize]);
                    }
                    BlockCipher blockCipher = this.d;
                    if (blockCipher instanceof CBCBlockCipher) {
                        ((CBCBlockCipher) blockCipher).g().f(this.a, blockSize, out, outOff);
                    } else {
                        blockCipher.f(this.a, blockSize, out, outOff);
                    }
                    System.arraycopy(block, 0, out, outOff + blockSize, len);
                } else {
                    throw new DataLengthException("need at least one block of input for CTS");
                }
            } else {
                byte[] lastBlock = new byte[blockSize];
                BlockCipher blockCipher2 = this.d;
                if (blockCipher2 instanceof CBCBlockCipher) {
                    ((CBCBlockCipher) blockCipher2).g().f(this.a, 0, block, 0);
                } else {
                    blockCipher2.f(this.a, 0, block, 0);
                }
                for (int i3 = blockSize; i3 != this.b; i3++) {
                    lastBlock[i3 - blockSize] = (byte) (block[i3 - blockSize] ^ this.a[i3]);
                }
                System.arraycopy(this.a, blockSize, block, 0, len);
                this.d.f(block, 0, out, outOff);
                System.arraycopy(lastBlock, 0, out, outOff + blockSize, len);
            }
            int offset = this.b;
            h();
            return offset;
        }
        throw new OutputLengthException("output buffer to small in doFinal");
    }
}
