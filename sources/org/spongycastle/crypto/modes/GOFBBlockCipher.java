package org.spongycastle.crypto.modes;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.StreamBlockCipher;
import org.spongycastle.crypto.params.ParametersWithIV;

public class GOFBBlockCipher extends StreamBlockCipher {
    private byte[] b;
    private byte[] c;
    private byte[] d;
    private int e;
    private final int f;
    private final BlockCipher g;
    boolean h = true;
    int i;
    int j;

    public GOFBBlockCipher(BlockCipher cipher) {
        super(cipher);
        this.g = cipher;
        int c2 = cipher.c();
        this.f = c2;
        if (c2 == 8) {
            this.b = new byte[cipher.c()];
            this.c = new byte[cipher.c()];
            this.d = new byte[cipher.c()];
            return;
        }
        throw new IllegalArgumentException("GCTR only for 64 bit block ciphers");
    }

    public void a(boolean encrypting, CipherParameters params) {
        this.h = true;
        this.i = 0;
        this.j = 0;
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
        return this.g.b() + "/GCTR";
    }

    public int c() {
        return this.f;
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        d(in, inOff, this.f, out, outOff);
        return this.f;
    }

    public void reset() {
        this.h = true;
        this.i = 0;
        this.j = 0;
        byte[] bArr = this.b;
        System.arraycopy(bArr, 0, this.c, 0, bArr.length);
        this.e = 0;
        this.g.reset();
    }

    private int i(byte[] in, int inOff) {
        return ((in[inOff + 3] << 24) & ViewCompat.MEASURED_STATE_MASK) + ((in[inOff + 2] << MappingData.PATH) & 16711680) + ((in[inOff + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) + (in[inOff] & 255);
    }

    private void j(int num, byte[] out, int outOff) {
        out[outOff + 3] = (byte) (num >>> 24);
        out[outOff + 2] = (byte) (num >>> 16);
        out[outOff + 1] = (byte) (num >>> 8);
        out[outOff] = (byte) num;
    }

    /* access modifiers changed from: protected */
    public byte g(byte b2) {
        if (this.e == 0) {
            if (this.h) {
                this.h = false;
                this.g.f(this.c, 0, this.d, 0);
                this.i = i(this.d, 0);
                this.j = i(this.d, 4);
            }
            int i2 = this.i + 16843009;
            this.i = i2;
            int i3 = this.j + 16843012;
            this.j = i3;
            if (i3 < 16843012 && i3 > 0) {
                this.j = i3 + 1;
            }
            j(i2, this.c, 0);
            j(this.j, this.c, 4);
            this.g.f(this.c, 0, this.d, 0);
        }
        byte[] bArr = this.d;
        int i4 = this.e;
        int i5 = i4 + 1;
        this.e = i5;
        byte rv = (byte) (bArr[i4] ^ b2);
        int i6 = this.f;
        if (i5 == i6) {
            this.e = 0;
            byte[] bArr2 = this.c;
            System.arraycopy(bArr2, i6, bArr2, 0, bArr2.length - i6);
            byte[] bArr3 = this.d;
            byte[] bArr4 = this.c;
            int length = bArr4.length;
            int i7 = this.f;
            System.arraycopy(bArr3, 0, bArr4, length - i7, i7);
        }
        return rv;
    }
}
