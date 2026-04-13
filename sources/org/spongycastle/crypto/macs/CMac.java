package org.spongycastle.crypto.macs;

import com.alibaba.fastjson.asm.Opcodes;
import com.didichuxing.doraemonkit.BuildConfig;
import com.tutk.IOTC.AVIOCTRLDEFs;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.ISO7816d4Padding;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Pack;

public class CMac implements Mac {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private byte[] d;
    private int e;
    private BlockCipher f;
    private int g;
    private byte[] h;
    private byte[] i;

    public CMac(BlockCipher cipher) {
        this(cipher, cipher.c() * 8);
    }

    public CMac(BlockCipher cipher, int macSizeInBits) {
        if (macSizeInBits % 8 != 0) {
            throw new IllegalArgumentException("MAC size must be multiple of 8");
        } else if (macSizeInBits <= cipher.c() * 8) {
            this.f = new CBCBlockCipher(cipher);
            this.g = macSizeInBits / 8;
            this.a = g(cipher.c());
            this.c = new byte[cipher.c()];
            this.d = new byte[cipher.c()];
            this.b = new byte[cipher.c()];
            this.e = 0;
        } else {
            throw new IllegalArgumentException("MAC size must be less or equal to " + (cipher.c() * 8));
        }
    }

    public String b() {
        return this.f.b();
    }

    private static int h(byte[] block, byte[] output) {
        int i2 = block.length;
        int bit = 0;
        while (true) {
            i2--;
            if (i2 < 0) {
                return bit;
            }
            int b2 = block[i2] & 255;
            output[i2] = (byte) ((b2 << 1) | bit);
            bit = (b2 >>> 7) & 1;
        }
    }

    private byte[] f(byte[] in) {
        byte[] ret = new byte[in.length];
        int mask = (-h(in, ret)) & 255;
        int length = in.length - 3;
        byte b2 = ret[length];
        byte[] bArr = this.a;
        ret[length] = (byte) (b2 ^ (bArr[1] & mask));
        int length2 = in.length - 2;
        ret[length2] = (byte) ((bArr[2] & mask) ^ ret[length2]);
        int length3 = in.length - 1;
        ret[length3] = (byte) ((bArr[3] & mask) ^ ret[length3]);
        return ret;
    }

    private static byte[] g(int blockSizeLength) {
        int xor;
        switch (blockSizeLength * 8) {
            case 64:
                xor = 27;
                break;
            case 128:
                xor = 135;
                break;
            case Opcodes.IF_ICMPNE:
                xor = 45;
                break;
            case Opcodes.CHECKCAST:
                xor = 135;
                break;
            case 224:
                xor = 777;
                break;
            case 256:
                xor = 1061;
                break;
            case BuildConfig.VERSION_CODE:
                xor = 27;
                break;
            case 384:
                xor = 4109;
                break;
            case 448:
                xor = 2129;
                break;
            case 512:
                xor = 293;
                break;
            case AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART:
                xor = 655377;
                break;
            case 1024:
                xor = 524355;
                break;
            case 2048:
                xor = 548865;
                break;
            default:
                throw new IllegalArgumentException("Unknown block size for CMAC: " + (blockSizeLength * 8));
        }
        return Pack.f(xor);
    }

    public void a(CipherParameters params) {
        i(params);
        this.f.a(true, params);
        byte[] bArr = this.b;
        byte[] L = new byte[bArr.length];
        this.f.f(bArr, 0, L, 0);
        byte[] f2 = f(L);
        this.h = f2;
        this.i = f(f2);
        reset();
    }

    /* access modifiers changed from: package-private */
    public void i(CipherParameters params) {
        if (params != null && !(params instanceof KeyParameter)) {
            throw new IllegalArgumentException("CMac mode only permits key to be set.");
        }
    }

    public int e() {
        return this.g;
    }

    public void d(byte in) {
        int i2 = this.e;
        byte[] bArr = this.d;
        if (i2 == bArr.length) {
            this.f.f(bArr, 0, this.c, 0);
            this.e = 0;
        }
        byte[] bArr2 = this.d;
        int i3 = this.e;
        this.e = i3 + 1;
        bArr2[i3] = in;
    }

    public void update(byte[] in, int inOff, int len) {
        if (len >= 0) {
            int blockSize = this.f.c();
            int i2 = this.e;
            int gapLen = blockSize - i2;
            if (len > gapLen) {
                System.arraycopy(in, inOff, this.d, i2, gapLen);
                this.f.f(this.d, 0, this.c, 0);
                this.e = 0;
                len -= gapLen;
                inOff += gapLen;
                while (len > blockSize) {
                    this.f.f(in, inOff, this.c, 0);
                    len -= blockSize;
                    inOff += blockSize;
                }
            }
            System.arraycopy(in, inOff, this.d, this.e, len);
            this.e += len;
            return;
        }
        throw new IllegalArgumentException("Can't have a negative input length!");
    }

    public int c(byte[] out, int outOff) {
        byte[] lu;
        if (this.e == this.f.c()) {
            lu = this.h;
        } else {
            new ISO7816d4Padding().c(this.d, this.e);
            lu = this.i;
        }
        int i2 = 0;
        while (true) {
            byte[] bArr = this.c;
            if (i2 < bArr.length) {
                byte[] bArr2 = this.d;
                bArr2[i2] = (byte) (bArr2[i2] ^ lu[i2]);
                i2++;
            } else {
                this.f.f(this.d, 0, bArr, 0);
                System.arraycopy(this.c, 0, out, outOff, this.g);
                reset();
                return this.g;
            }
        }
    }

    public void reset() {
        int i2 = 0;
        while (true) {
            byte[] bArr = this.d;
            if (i2 < bArr.length) {
                bArr[i2] = 0;
                i2++;
            } else {
                this.e = 0;
                this.f.reset();
                return;
            }
        }
    }
}
