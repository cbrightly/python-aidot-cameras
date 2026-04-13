package org.spongycastle.crypto.encodings;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import java.math.BigInteger;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.InvalidCipherTextException;

public class ISO9796d1Encoding implements AsymmetricBlockCipher {
    private static final BigInteger a = BigInteger.valueOf(16);
    private static final BigInteger b = BigInteger.valueOf(6);
    private static byte[] c = {14, 3, 5, 8, 9, 4, 2, 15, 0, 13, 11, 6, 7, 10, 12, 1};
    private static byte[] d = {8, 15, 6, 1, 5, 2, 11, 12, 3, 4, 13, 10, 14, 9, 0, 7};
    private AsymmetricBlockCipher e;
    private boolean f;
    private int g;
    private int h = 0;
    private BigInteger i;

    public ISO9796d1Encoding(AsymmetricBlockCipher cipher) {
        this.e = cipher;
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [org.spongycastle.crypto.CipherParameters] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(boolean r4, org.spongycastle.crypto.CipherParameters r5) {
        /*
            r3 = this;
            r0 = 0
            boolean r1 = r5 instanceof org.spongycastle.crypto.params.ParametersWithRandom
            if (r1 == 0) goto L_0x0010
            r1 = r5
            org.spongycastle.crypto.params.ParametersWithRandom r1 = (org.spongycastle.crypto.params.ParametersWithRandom) r1
            org.spongycastle.crypto.CipherParameters r2 = r1.a()
            r0 = r2
            org.spongycastle.crypto.params.RSAKeyParameters r0 = (org.spongycastle.crypto.params.RSAKeyParameters) r0
            goto L_0x0013
        L_0x0010:
            r0 = r5
            org.spongycastle.crypto.params.RSAKeyParameters r0 = (org.spongycastle.crypto.params.RSAKeyParameters) r0
        L_0x0013:
            org.spongycastle.crypto.AsymmetricBlockCipher r1 = r3.e
            r1.a(r4, r5)
            java.math.BigInteger r1 = r0.c()
            r3.i = r1
            int r1 = r1.bitLength()
            r3.g = r1
            r3.f = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.crypto.encodings.ISO9796d1Encoding.a(boolean, org.spongycastle.crypto.CipherParameters):void");
    }

    public int c() {
        int baseBlockSize = this.e.c();
        if (this.f) {
            return (baseBlockSize + 1) / 2;
        }
        return baseBlockSize;
    }

    public int b() {
        int baseBlockSize = this.e.b();
        if (this.f) {
            return baseBlockSize;
        }
        return (baseBlockSize + 1) / 2;
    }

    public byte[] d(byte[] in, int inOff, int inLen) {
        if (this.f) {
            return g(in, inOff, inLen);
        }
        return f(in, inOff, inLen);
    }

    private byte[] g(byte[] in, int inOff, int inLen) {
        int i2 = this.g;
        byte[] block = new byte[((i2 + 7) / 8)];
        int r = this.h + 1;
        int z = inLen;
        int t = (i2 + 13) / 16;
        for (int i3 = 0; i3 < t; i3 += z) {
            if (i3 > t - z) {
                System.arraycopy(in, (inOff + inLen) - (t - i3), block, block.length - t, t - i3);
            } else {
                System.arraycopy(in, inOff, block, block.length - (i3 + z), z);
            }
        }
        for (int i4 = block.length - (t * 2); i4 != block.length; i4 += 2) {
            byte val = block[(block.length - t) + (i4 / 2)];
            byte[] bArr = c;
            block[i4] = (byte) (bArr[val & 15] | (bArr[(val & 255) >>> 4] << 4));
            block[i4 + 1] = val;
        }
        int length = block.length - (z * 2);
        block[length] = (byte) (block[length] ^ r);
        block[block.length - 1] = (byte) ((block[block.length - 1] << 4) | 6);
        int maxBit = 8 - ((this.g - 1) % 8);
        int offSet = 0;
        if (maxBit != 8) {
            block[0] = (byte) (block[0] & (255 >>> maxBit));
            block[0] = (byte) (block[0] | (128 >>> maxBit));
        } else {
            block[0] = 0;
            block[1] = (byte) (block[1] | OTACommand.RESP_ID_VERSION);
            offSet = 1;
        }
        return this.e.d(block, offSet, block.length - offSet);
    }

    private byte[] f(byte[] in, int inOff, int inLen) {
        BigInteger iR;
        byte[] block = this.e.d(in, inOff, inLen);
        int r = 1;
        int t = (this.g + 13) / 16;
        BigInteger iS = new BigInteger(1, block);
        BigInteger bigInteger = a;
        BigInteger mod = iS.mod(bigInteger);
        BigInteger bigInteger2 = b;
        if (mod.equals(bigInteger2)) {
            iR = iS;
        } else if (this.i.subtract(iS).mod(bigInteger).equals(bigInteger2)) {
            iR = this.i.subtract(iS);
        } else {
            throw new InvalidCipherTextException("resulting integer iS or (modulus - iS) is not congruent to 6 mod 16");
        }
        byte[] block2 = e(iR);
        if ((block2[block2.length - 1] & 15) == 6) {
            block2[block2.length - 1] = (byte) (((block2[block2.length - 1] & 255) >>> 4) | (d[(block2[block2.length - 2] & 255) >> 4] << 4));
            byte[] bArr = c;
            block2[0] = (byte) (bArr[block2[1] & 15] | (bArr[(block2[1] & 255) >>> 4] << 4));
            boolean boundaryFound = false;
            int boundary = 0;
            for (int i2 = block2.length - 1; i2 >= block2.length - (t * 2); i2 -= 2) {
                byte[] bArr2 = c;
                int val = bArr2[block2[i2] & 15] | (bArr2[(block2[i2] & 255) >>> 4] << 4);
                if (((block2[i2 - 1] ^ val) & 255) != 0) {
                    if (!boundaryFound) {
                        boundaryFound = true;
                        r = (block2[i2 - 1] ^ val) & 255;
                        boundary = i2 - 1;
                    } else {
                        throw new InvalidCipherTextException("invalid tsums in block");
                    }
                }
            }
            block2[boundary] = 0;
            byte[] nblock = new byte[((block2.length - boundary) / 2)];
            for (int i3 = 0; i3 < nblock.length; i3++) {
                nblock[i3] = block2[(i3 * 2) + boundary + 1];
            }
            this.h = r - 1;
            return nblock;
        }
        throw new InvalidCipherTextException("invalid forcing byte in block");
    }

    private static byte[] e(BigInteger result) {
        byte[] output = result.toByteArray();
        if (output[0] != 0) {
            return output;
        }
        byte[] tmp = new byte[(output.length - 1)];
        System.arraycopy(output, 1, tmp, 0, tmp.length);
        return tmp;
    }
}
