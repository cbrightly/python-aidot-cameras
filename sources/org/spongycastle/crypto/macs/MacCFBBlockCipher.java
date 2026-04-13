package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.ParametersWithIV;

/* compiled from: CFBBlockCipherMac */
public class MacCFBBlockCipher {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private int d;
    private BlockCipher e = null;

    public MacCFBBlockCipher(BlockCipher cipher, int bitBlockSize) {
        this.e = cipher;
        this.d = bitBlockSize / 8;
        this.a = new byte[cipher.c()];
        this.b = new byte[cipher.c()];
        this.c = new byte[cipher.c()];
    }

    public void d(CipherParameters params) {
        if (params instanceof ParametersWithIV) {
            ParametersWithIV ivParam = (ParametersWithIV) params;
            byte[] iv = ivParam.a();
            int length = iv.length;
            byte[] bArr = this.a;
            if (length < bArr.length) {
                System.arraycopy(iv, 0, bArr, bArr.length - iv.length, iv.length);
            } else {
                System.arraycopy(iv, 0, bArr, 0, bArr.length);
            }
            f();
            this.e.a(true, ivParam.b());
            return;
        }
        f();
        this.e.a(true, params);
    }

    public String a() {
        return this.e.b() + "/CFB" + (this.d * 8);
    }

    public int b() {
        return this.d;
    }

    public int e(byte[] in, int inOff, byte[] out, int outOff) {
        int i = this.d;
        if (inOff + i > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i + outOff <= out.length) {
            this.e.f(this.b, 0, this.c, 0);
            int i2 = 0;
            while (true) {
                int i3 = this.d;
                if (i2 < i3) {
                    out[outOff + i2] = (byte) (this.c[i2] ^ in[inOff + i2]);
                    i2++;
                } else {
                    byte[] bArr = this.b;
                    System.arraycopy(bArr, i3, bArr, 0, bArr.length - i3);
                    byte[] bArr2 = this.b;
                    int length = bArr2.length;
                    int i4 = this.d;
                    System.arraycopy(out, outOff, bArr2, length - i4, i4);
                    return this.d;
                }
            }
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void f() {
        byte[] bArr = this.a;
        System.arraycopy(bArr, 0, this.b, 0, bArr.length);
        this.e.reset();
    }

    /* access modifiers changed from: package-private */
    public void c(byte[] mac) {
        this.e.f(this.b, 0, mac, 0);
    }
}
