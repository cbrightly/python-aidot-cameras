package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.StreamBlockCipher;
import org.spongycastle.crypto.params.ParametersWithIV;

public class OFBBlockCipher extends StreamBlockCipher {
    private int b;
    private byte[] c;
    private byte[] d;
    private byte[] e;
    private final int f;
    private final BlockCipher g;

    public OFBBlockCipher(BlockCipher cipher, int blockSize) {
        super(cipher);
        this.g = cipher;
        this.f = blockSize / 8;
        this.c = new byte[cipher.c()];
        this.d = new byte[cipher.c()];
        this.e = new byte[cipher.c()];
    }

    public void a(boolean encrypting, CipherParameters params) {
        if (params instanceof ParametersWithIV) {
            ParametersWithIV ivParam = (ParametersWithIV) params;
            byte[] iv = ivParam.a();
            int length = iv.length;
            byte[] bArr = this.c;
            if (length < bArr.length) {
                System.arraycopy(iv, 0, bArr, bArr.length - iv.length, iv.length);
                int i = 0;
                while (true) {
                    byte[] bArr2 = this.c;
                    if (i >= bArr2.length - iv.length) {
                        break;
                    }
                    bArr2[i] = 0;
                    i++;
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
        return this.g.b() + "/OFB" + (this.f * 8);
    }

    public int c() {
        return this.f;
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        d(in, inOff, this.f, out, outOff);
        return this.f;
    }

    public void reset() {
        byte[] bArr = this.c;
        System.arraycopy(bArr, 0, this.d, 0, bArr.length);
        this.b = 0;
        this.g.reset();
    }

    /* access modifiers changed from: protected */
    public byte g(byte in) {
        if (this.b == 0) {
            this.g.f(this.d, 0, this.e, 0);
        }
        byte[] bArr = this.e;
        int i = this.b;
        int i2 = i + 1;
        this.b = i2;
        byte rv = (byte) (bArr[i] ^ in);
        int i3 = this.f;
        if (i2 == i3) {
            this.b = 0;
            byte[] bArr2 = this.d;
            System.arraycopy(bArr2, i3, bArr2, 0, bArr2.length - i3);
            byte[] bArr3 = this.e;
            byte[] bArr4 = this.d;
            int length = bArr4.length;
            int i4 = this.f;
            System.arraycopy(bArr3, 0, bArr4, length - i4, i4);
        }
        return rv;
    }
}
