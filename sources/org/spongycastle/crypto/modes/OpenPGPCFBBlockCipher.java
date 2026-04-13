package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;

public class OpenPGPCFBBlockCipher implements BlockCipher {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private BlockCipher d;
    private int e;
    private int f;
    private boolean g;

    public OpenPGPCFBBlockCipher(BlockCipher cipher) {
        this.d = cipher;
        int c2 = cipher.c();
        this.f = c2;
        this.a = new byte[c2];
        this.b = new byte[c2];
        this.c = new byte[c2];
    }

    public String b() {
        return this.d.b() + "/OpenPGPCFB";
    }

    public int c() {
        return this.d.c();
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        return this.g ? e(in, inOff, out, outOff) : d(in, inOff, out, outOff);
    }

    public void reset() {
        this.e = 0;
        byte[] bArr = this.a;
        byte[] bArr2 = this.b;
        System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
        this.d.reset();
    }

    public void a(boolean forEncryption, CipherParameters params) {
        this.g = forEncryption;
        reset();
        this.d.a(true, params);
    }

    private byte g(byte data, int blockOff) {
        return (byte) (this.c[blockOff] ^ data);
    }

    private int e(byte[] in, int inOff, byte[] out, int outOff) {
        int i;
        int i2;
        int i3 = this.f;
        if (inOff + i3 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + i3 <= out.length) {
            int i4 = this.e;
            if (i4 > i3) {
                byte[] bArr = this.b;
                int i5 = i3 - 2;
                byte g2 = g(in[inOff], i3 - 2);
                out[outOff] = g2;
                bArr[i5] = g2;
                byte[] bArr2 = this.b;
                int i6 = this.f;
                int i7 = i6 - 1;
                byte g3 = g(in[inOff + 1], i6 - 1);
                out[outOff + 1] = g3;
                bArr2[i7] = g3;
                this.d.f(this.b, 0, this.c, 0);
                for (int n = 2; n < this.f; n++) {
                    byte g4 = g(in[inOff + n], n - 2);
                    out[outOff + n] = g4;
                    this.b[n - 2] = g4;
                }
            } else if (i4 == 0) {
                this.d.f(this.b, 0, this.c, 0);
                int n2 = 0;
                while (true) {
                    i2 = this.f;
                    if (n2 >= i2) {
                        break;
                    }
                    byte[] bArr3 = this.b;
                    byte g5 = g(in[inOff + n2], n2);
                    out[outOff + n2] = g5;
                    bArr3[n2] = g5;
                    n2++;
                }
                this.e += i2;
            } else if (i4 == i3) {
                this.d.f(this.b, 0, this.c, 0);
                out[outOff] = g(in[inOff], 0);
                out[outOff + 1] = g(in[inOff + 1], 1);
                byte[] bArr4 = this.b;
                System.arraycopy(bArr4, 2, bArr4, 0, this.f - 2);
                System.arraycopy(out, outOff, this.b, this.f - 2, 2);
                this.d.f(this.b, 0, this.c, 0);
                int n3 = 2;
                while (true) {
                    i = this.f;
                    if (n3 >= i) {
                        break;
                    }
                    byte g6 = g(in[inOff + n3], n3 - 2);
                    out[outOff + n3] = g6;
                    this.b[n3 - 2] = g6;
                    n3++;
                }
                this.e += i;
            }
            return this.f;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    private int d(byte[] in, int inOff, byte[] out, int outOff) {
        int i;
        int i2;
        int i3 = this.f;
        if (inOff + i3 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + i3 <= out.length) {
            int i4 = this.e;
            if (i4 > i3) {
                byte inVal = in[inOff];
                this.b[i3 - 2] = inVal;
                out[outOff] = g(inVal, i3 - 2);
                byte inVal2 = in[inOff + 1];
                byte[] bArr = this.b;
                int i5 = this.f;
                bArr[i5 - 1] = inVal2;
                out[outOff + 1] = g(inVal2, i5 - 1);
                this.d.f(this.b, 0, this.c, 0);
                for (int n = 2; n < this.f; n++) {
                    byte inVal3 = in[inOff + n];
                    this.b[n - 2] = inVal3;
                    out[outOff + n] = g(inVal3, n - 2);
                }
            } else if (i4 == 0) {
                this.d.f(this.b, 0, this.c, 0);
                int n2 = 0;
                while (true) {
                    i2 = this.f;
                    if (n2 >= i2) {
                        break;
                    }
                    this.b[n2] = in[inOff + n2];
                    out[n2] = g(in[inOff + n2], n2);
                    n2++;
                }
                this.e += i2;
            } else if (i4 == i3) {
                this.d.f(this.b, 0, this.c, 0);
                byte inVal1 = in[inOff];
                byte inVal22 = in[inOff + 1];
                out[outOff] = g(inVal1, 0);
                out[outOff + 1] = g(inVal22, 1);
                byte[] bArr2 = this.b;
                System.arraycopy(bArr2, 2, bArr2, 0, this.f - 2);
                byte[] bArr3 = this.b;
                int i6 = this.f;
                bArr3[i6 - 2] = inVal1;
                bArr3[i6 - 1] = inVal22;
                this.d.f(bArr3, 0, this.c, 0);
                int n3 = 2;
                while (true) {
                    i = this.f;
                    if (n3 >= i) {
                        break;
                    }
                    byte inVal4 = in[inOff + n3];
                    this.b[n3 - 2] = inVal4;
                    out[outOff + n3] = g(inVal4, n3 - 2);
                    n3++;
                }
                this.e += i;
            }
            return this.f;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }
}
