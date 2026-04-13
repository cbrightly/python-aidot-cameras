package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;

public class NISTCTSBlockCipher extends BufferedBlockCipher {
    private final int g;

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
            int i = this.b;
            int len = i - blockSize;
            byte[] block = new byte[blockSize];
            if (this.c) {
                if (i < blockSize) {
                    throw new DataLengthException("need at least one block of input for NISTCTS");
                } else if (i > blockSize) {
                    byte[] lastBlock = new byte[blockSize];
                    int i2 = this.g;
                    if (i2 == 2 || i2 == 3) {
                        this.d.f(this.a, 0, block, 0);
                        System.arraycopy(this.a, blockSize, lastBlock, 0, len);
                        this.d.f(lastBlock, 0, lastBlock, 0);
                        if (this.g == 2 && len == blockSize) {
                            System.arraycopy(block, 0, out, outOff, blockSize);
                            System.arraycopy(lastBlock, 0, out, outOff + blockSize, len);
                        } else {
                            System.arraycopy(lastBlock, 0, out, outOff, blockSize);
                            System.arraycopy(block, 0, out, outOff + blockSize, len);
                        }
                    } else {
                        System.arraycopy(this.a, 0, block, 0, blockSize);
                        this.d.f(block, 0, block, 0);
                        System.arraycopy(block, 0, out, outOff, len);
                        System.arraycopy(this.a, this.b - len, lastBlock, 0, len);
                        this.d.f(lastBlock, 0, lastBlock, 0);
                        System.arraycopy(lastBlock, 0, out, outOff + len, blockSize);
                    }
                } else {
                    this.d.f(this.a, 0, block, 0);
                    System.arraycopy(block, 0, out, outOff, blockSize);
                }
            } else if (i >= blockSize) {
                byte[] lastBlock2 = new byte[blockSize];
                if (i > blockSize) {
                    int i3 = this.g;
                    if (i3 == 3 || (i3 == 2 && (this.a.length - i) % blockSize != 0)) {
                        BlockCipher blockCipher = this.d;
                        if (blockCipher instanceof CBCBlockCipher) {
                            ((CBCBlockCipher) blockCipher).g().f(this.a, 0, block, 0);
                        } else {
                            blockCipher.f(this.a, 0, block, 0);
                        }
                        for (int i4 = blockSize; i4 != this.b; i4++) {
                            lastBlock2[i4 - blockSize] = (byte) (block[i4 - blockSize] ^ this.a[i4]);
                        }
                        System.arraycopy(this.a, blockSize, block, 0, len);
                        this.d.f(block, 0, out, outOff);
                        System.arraycopy(lastBlock2, 0, out, outOff + blockSize, len);
                    } else {
                        ((CBCBlockCipher) this.d).g().f(this.a, this.b - blockSize, lastBlock2, 0);
                        System.arraycopy(this.a, 0, block, 0, blockSize);
                        if (len != blockSize) {
                            System.arraycopy(lastBlock2, len, block, len, blockSize - len);
                        }
                        this.d.f(block, 0, block, 0);
                        System.arraycopy(block, 0, out, outOff, blockSize);
                        for (int i5 = 0; i5 != len; i5++) {
                            lastBlock2[i5] = (byte) (lastBlock2[i5] ^ this.a[i5]);
                        }
                        System.arraycopy(lastBlock2, 0, out, outOff + blockSize, len);
                    }
                } else {
                    this.d.f(this.a, 0, block, 0);
                    System.arraycopy(block, 0, out, outOff, blockSize);
                }
            } else {
                throw new DataLengthException("need at least one block of input for CTS");
            }
            int offset = this.b;
            h();
            return offset;
        }
        throw new OutputLengthException("output buffer to small in doFinal");
    }
}
