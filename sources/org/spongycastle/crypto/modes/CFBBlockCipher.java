package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.StreamBlockCipher;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;

public class CFBBlockCipher extends StreamBlockCipher {
    private byte[] b;
    private byte[] c;
    private byte[] d;
    private byte[] e;
    private int f;
    private BlockCipher g = null;
    private boolean h;
    private int i;

    public CFBBlockCipher(BlockCipher cipher, int bitBlockSize) {
        super(cipher);
        this.g = cipher;
        this.f = bitBlockSize / 8;
        this.b = new byte[cipher.c()];
        this.c = new byte[cipher.c()];
        this.d = new byte[cipher.c()];
        this.e = new byte[this.f];
    }

    public void a(boolean encrypting, CipherParameters params) {
        this.h = encrypting;
        if (params instanceof ParametersWithIV) {
            ParametersWithIV ivParam = (ParametersWithIV) params;
            byte[] iv = ivParam.a();
            int length = iv.length;
            byte[] bArr = this.b;
            if (length < bArr.length) {
                System.arraycopy(iv, 0, bArr, bArr.length - iv.length, iv.length);
                int i2 = 0;
                while (true) {
                    byte[] bArr2 = this.b;
                    if (i2 >= bArr2.length - iv.length) {
                        break;
                    }
                    bArr2[i2] = 0;
                    i2++;
                }
            } else {
                System.arraycopy(iv, 0, bArr, 0, bArr.length);
            }
            reset();
            if (ivParam.b() != null) {
                this.g.a(true, ivParam.b());
                return;
            }
            return;
        }
        reset();
        if (params != null) {
            this.g.a(true, params);
        }
    }

    public String b() {
        return this.g.b() + "/CFB" + (this.f * 8);
    }

    /* access modifiers changed from: protected */
    public byte g(byte in) {
        return this.h ? j(in) : i(in);
    }

    private byte j(byte in) {
        if (this.i == 0) {
            this.g.f(this.c, 0, this.d, 0);
        }
        byte[] bArr = this.d;
        int i2 = this.i;
        byte rv = (byte) (bArr[i2] ^ in);
        byte[] bArr2 = this.e;
        int i3 = i2 + 1;
        this.i = i3;
        bArr2[i2] = rv;
        int i4 = this.f;
        if (i3 == i4) {
            this.i = 0;
            byte[] bArr3 = this.c;
            System.arraycopy(bArr3, i4, bArr3, 0, bArr3.length - i4);
            byte[] bArr4 = this.e;
            byte[] bArr5 = this.c;
            int length = bArr5.length;
            int i5 = this.f;
            System.arraycopy(bArr4, 0, bArr5, length - i5, i5);
        }
        return rv;
    }

    private byte i(byte in) {
        if (this.i == 0) {
            this.g.f(this.c, 0, this.d, 0);
        }
        byte[] bArr = this.e;
        int i2 = this.i;
        bArr[i2] = in;
        byte[] bArr2 = this.d;
        int i3 = i2 + 1;
        this.i = i3;
        byte rv = (byte) (bArr2[i2] ^ in);
        int i4 = this.f;
        if (i3 == i4) {
            this.i = 0;
            byte[] bArr3 = this.c;
            System.arraycopy(bArr3, i4, bArr3, 0, bArr3.length - i4);
            byte[] bArr4 = this.e;
            byte[] bArr5 = this.c;
            int length = bArr5.length;
            int i5 = this.f;
            System.arraycopy(bArr4, 0, bArr5, length - i5, i5);
        }
        return rv;
    }

    public int c() {
        return this.f;
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        d(in, inOff, this.f, out, outOff);
        return this.f;
    }

    public byte[] k() {
        return Arrays.h(this.c);
    }

    public void reset() {
        byte[] bArr = this.b;
        System.arraycopy(bArr, 0, this.c, 0, bArr.length);
        Arrays.F(this.e, (byte) 0);
        this.i = 0;
        this.g.reset();
    }
}
