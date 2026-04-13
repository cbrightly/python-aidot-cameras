package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.engines.DESEngine;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.BlockCipherPadding;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class ISO9797Alg3Mac implements Mac {
    private byte[] a;
    private byte[] b;
    private int c;
    private BlockCipher d;
    private BlockCipherPadding e;
    private int f;
    private KeyParameter g;
    private KeyParameter h;

    public ISO9797Alg3Mac(BlockCipher cipher) {
        this(cipher, cipher.c() * 8, (BlockCipherPadding) null);
    }

    public ISO9797Alg3Mac(BlockCipher cipher, BlockCipherPadding padding) {
        this(cipher, cipher.c() * 8, padding);
    }

    public ISO9797Alg3Mac(BlockCipher cipher, int macSizeInBits, BlockCipherPadding padding) {
        if (macSizeInBits % 8 != 0) {
            throw new IllegalArgumentException("MAC size must be multiple of 8");
        } else if (cipher instanceof DESEngine) {
            this.d = new CBCBlockCipher(cipher);
            this.e = padding;
            this.f = macSizeInBits / 8;
            this.a = new byte[cipher.c()];
            this.b = new byte[cipher.c()];
            this.c = 0;
        } else {
            throw new IllegalArgumentException("cipher must be instance of DESEngine");
        }
    }

    public String b() {
        return "ISO9797Alg3";
    }

    public void a(CipherParameters params) {
        KeyParameter kp;
        KeyParameter key1;
        reset();
        if ((params instanceof KeyParameter) || (params instanceof ParametersWithIV)) {
            if (params instanceof KeyParameter) {
                kp = (KeyParameter) params;
            } else {
                kp = (KeyParameter) ((ParametersWithIV) params).b();
            }
            byte[] keyvalue = kp.a();
            if (keyvalue.length == 16) {
                key1 = new KeyParameter(keyvalue, 0, 8);
                this.g = new KeyParameter(keyvalue, 8, 8);
                this.h = key1;
            } else if (keyvalue.length == 24) {
                key1 = new KeyParameter(keyvalue, 0, 8);
                this.g = new KeyParameter(keyvalue, 8, 8);
                this.h = new KeyParameter(keyvalue, 16, 8);
            } else {
                throw new IllegalArgumentException("Key must be either 112 or 168 bit long");
            }
            if (params instanceof ParametersWithIV) {
                this.d.a(true, new ParametersWithIV(key1, ((ParametersWithIV) params).a()));
            } else {
                this.d.a(true, key1);
            }
        } else {
            throw new IllegalArgumentException("params must be an instance of KeyParameter or ParametersWithIV");
        }
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
        DESEngine deseng = new DESEngine();
        deseng.a(false, this.g);
        byte[] bArr = this.a;
        deseng.f(bArr, 0, bArr, 0);
        deseng.a(true, this.h);
        byte[] bArr2 = this.a;
        deseng.f(bArr2, 0, bArr2, 0);
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
