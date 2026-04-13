package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.StreamBlockCipher;

public class CTSBlockCipher extends BufferedBlockCipher {
    private int g;

    public CTSBlockCipher(BlockCipher cipher) {
        if (!(cipher instanceof StreamBlockCipher)) {
            this.d = cipher;
            int c = cipher.c();
            this.g = c;
            this.a = new byte[(c * 2)];
            this.b = 0;
            return;
        }
        throw new IllegalArgumentException("CTSBlockCipher can only accept ECB, or CBC ciphers");
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
                if (i >= blockSize) {
                    this.d.f(this.a, 0, block, 0);
                    if (this.b > blockSize) {
                        int i2 = this.b;
                        while (true) {
                            byte[] bArr = this.a;
                            if (i2 == bArr.length) {
                                break;
                            }
                            bArr[i2] = block[i2 - blockSize];
                            i2++;
                        }
                        for (int i3 = blockSize; i3 != this.b; i3++) {
                            byte[] bArr2 = this.a;
                            bArr2[i3] = (byte) (bArr2[i3] ^ block[i3 - blockSize]);
                        }
                        BlockCipher blockCipher = this.d;
                        if (blockCipher instanceof CBCBlockCipher) {
                            ((CBCBlockCipher) blockCipher).g().f(this.a, blockSize, out, outOff);
                        } else {
                            blockCipher.f(this.a, blockSize, out, outOff);
                        }
                        System.arraycopy(block, 0, out, outOff + blockSize, len);
                    } else {
                        System.arraycopy(block, 0, out, outOff, blockSize);
                    }
                } else {
                    throw new DataLengthException("need at least one block of input for CTS");
                }
            } else if (i >= blockSize) {
                byte[] lastBlock = new byte[blockSize];
                if (i > blockSize) {
                    BlockCipher blockCipher2 = this.d;
                    if (blockCipher2 instanceof CBCBlockCipher) {
                        ((CBCBlockCipher) blockCipher2).g().f(this.a, 0, block, 0);
                    } else {
                        blockCipher2.f(this.a, 0, block, 0);
                    }
                    for (int i4 = blockSize; i4 != this.b; i4++) {
                        lastBlock[i4 - blockSize] = (byte) (block[i4 - blockSize] ^ this.a[i4]);
                    }
                    System.arraycopy(this.a, blockSize, block, 0, len);
                    this.d.f(block, 0, out, outOff);
                    System.arraycopy(lastBlock, 0, out, outOff + blockSize, len);
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
