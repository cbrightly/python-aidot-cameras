package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.ParametersWithIV;

public class PGPCFBBlockCipher implements BlockCipher {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private byte[] d;
    private BlockCipher e;
    private int f;
    private int g;
    private boolean h;
    private boolean i;

    public PGPCFBBlockCipher(BlockCipher cipher, boolean inlineIv) {
        this.e = cipher;
        this.i = inlineIv;
        int c2 = cipher.c();
        this.g = c2;
        this.a = new byte[c2];
        this.b = new byte[c2];
        this.c = new byte[c2];
        this.d = new byte[c2];
    }

    public String b() {
        if (this.i) {
            return this.e.b() + "/PGPCFBwithIV";
        }
        return this.e.b() + "/PGPCFB";
    }

    public int c() {
        return this.e.c();
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        return this.i ? this.h ? h(in, inOff, out, outOff) : e(in, inOff, out, outOff) : this.h ? g(in, inOff, out, outOff) : d(in, inOff, out, outOff);
    }

    public void reset() {
        this.f = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.b;
            if (i2 != bArr.length) {
                if (this.i) {
                    bArr[i2] = 0;
                } else {
                    bArr[i2] = this.a[i2];
                }
                i2++;
            } else {
                this.e.reset();
                return;
            }
        }
    }

    public void a(boolean forEncryption, CipherParameters params) {
        this.h = forEncryption;
        if (params instanceof ParametersWithIV) {
            ParametersWithIV ivParam = (ParametersWithIV) params;
            byte[] iv = ivParam.a();
            int length = iv.length;
            byte[] bArr = this.a;
            if (length < bArr.length) {
                System.arraycopy(iv, 0, bArr, bArr.length - iv.length, iv.length);
                int i2 = 0;
                while (true) {
                    byte[] bArr2 = this.a;
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
            this.e.a(true, ivParam.b());
            return;
        }
        reset();
        this.e.a(true, params);
    }

    private byte i(byte data, int blockOff) {
        return (byte) (this.c[blockOff] ^ data);
    }

    private int h(byte[] in, int inOff, byte[] out, int outOff) {
        int i2;
        int i3;
        int i4 = this.g;
        if (inOff + i4 <= in.length) {
            int i5 = this.f;
            if (i5 != 0) {
                if (i5 >= i4 + 2) {
                    if (i4 + outOff <= out.length) {
                        this.e.f(this.b, 0, this.c, 0);
                        int n = 0;
                        while (true) {
                            i2 = this.g;
                            if (n >= i2) {
                                break;
                            }
                            out[outOff + n] = i(in[inOff + n], n);
                            n++;
                        }
                        System.arraycopy(out, outOff, this.b, 0, i2);
                    } else {
                        throw new OutputLengthException("output buffer too short");
                    }
                }
                return this.g;
            } else if ((i4 * 2) + outOff + 2 <= out.length) {
                this.e.f(this.b, 0, this.c, 0);
                int n2 = 0;
                while (true) {
                    i3 = this.g;
                    if (n2 >= i3) {
                        break;
                    }
                    out[outOff + n2] = i(this.a[n2], n2);
                    n2++;
                }
                System.arraycopy(out, outOff, this.b, 0, i3);
                this.e.f(this.b, 0, this.c, 0);
                int i6 = this.g;
                out[outOff + i6] = i(this.a[i6 - 2], 0);
                int i7 = this.g;
                out[outOff + i7 + 1] = i(this.a[i7 - 1], 1);
                System.arraycopy(out, outOff + 2, this.b, 0, this.g);
                this.e.f(this.b, 0, this.c, 0);
                int n3 = 0;
                while (true) {
                    int i8 = this.g;
                    if (n3 < i8) {
                        out[i8 + outOff + 2 + n3] = i(in[inOff + n3], n3);
                        n3++;
                    } else {
                        System.arraycopy(out, outOff + i8 + 2, this.b, 0, i8);
                        int i9 = this.f;
                        int i10 = this.g;
                        this.f = i9 + (i10 * 2) + 2;
                        return (i10 * 2) + 2;
                    }
                }
            } else {
                throw new OutputLengthException("output buffer too short");
            }
        } else {
            throw new DataLengthException("input buffer too short");
        }
    }

    private int e(byte[] in, int inOff, byte[] out, int outOff) {
        int i2;
        int i3 = this.g;
        if (inOff + i3 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + i3 <= out.length) {
            int i4 = this.f;
            if (i4 == 0) {
                for (int n = 0; n < this.g; n++) {
                    this.b[n] = in[inOff + n];
                }
                this.e.f(this.b, 0, this.c, 0);
                this.f += this.g;
                return 0;
            } else if (i4 == i3) {
                System.arraycopy(in, inOff, this.d, 0, i3);
                byte[] bArr = this.b;
                System.arraycopy(bArr, 2, bArr, 0, this.g - 2);
                byte[] bArr2 = this.b;
                int i5 = this.g;
                byte[] bArr3 = this.d;
                bArr2[i5 - 2] = bArr3[0];
                bArr2[i5 - 1] = bArr3[1];
                this.e.f(bArr2, 0, this.c, 0);
                int n2 = 0;
                while (true) {
                    int i6 = this.g;
                    if (n2 < i6 - 2) {
                        out[outOff + n2] = i(this.d[n2 + 2], n2);
                        n2++;
                    } else {
                        System.arraycopy(this.d, 2, this.b, 0, i6 - 2);
                        this.f += 2;
                        return this.g - 2;
                    }
                }
            } else {
                if (i4 >= i3 + 2) {
                    System.arraycopy(in, inOff, this.d, 0, i3);
                    out[outOff + 0] = i(this.d[0], this.g - 2);
                    out[outOff + 1] = i(this.d[1], this.g - 1);
                    System.arraycopy(this.d, 0, this.b, this.g - 2, 2);
                    this.e.f(this.b, 0, this.c, 0);
                    int n3 = 0;
                    while (true) {
                        i2 = this.g;
                        if (n3 >= i2 - 2) {
                            break;
                        }
                        out[outOff + n3 + 2] = i(this.d[n3 + 2], n3);
                        n3++;
                    }
                    System.arraycopy(this.d, 2, this.b, 0, i2 - 2);
                }
                return this.g;
            }
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    private int g(byte[] in, int inOff, byte[] out, int outOff) {
        int i2 = this.g;
        if (inOff + i2 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + outOff <= out.length) {
            this.e.f(this.b, 0, this.c, 0);
            for (int n = 0; n < this.g; n++) {
                out[outOff + n] = i(in[inOff + n], n);
            }
            int n2 = 0;
            while (true) {
                int i3 = this.g;
                if (n2 >= i3) {
                    return i3;
                }
                this.b[n2] = out[outOff + n2];
                n2++;
            }
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    private int d(byte[] in, int inOff, byte[] out, int outOff) {
        int i2 = this.g;
        if (inOff + i2 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + outOff <= out.length) {
            this.e.f(this.b, 0, this.c, 0);
            for (int n = 0; n < this.g; n++) {
                out[outOff + n] = i(in[inOff + n], n);
            }
            int n2 = 0;
            while (true) {
                int i3 = this.g;
                if (n2 >= i3) {
                    return i3;
                }
                this.b[n2] = in[inOff + n2];
                n2++;
            }
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }
}
