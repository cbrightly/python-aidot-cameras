package org.spongycastle.crypto.macs;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithSBox;

public class GOST28147Mac implements Mac {
    private int a = 8;
    private int b = 4;
    private int c = 0;
    private byte[] d = new byte[8];
    private byte[] e = new byte[8];
    private boolean f = true;
    private int[] g = null;
    private byte[] h = null;
    private byte[] i = {9, 6, 3, 2, 8, 11, 1, 7, 10, 4, 14, 15, 12, 0, 13, 5, 3, 7, 14, 9, 8, 10, 15, 0, 5, 2, 6, 12, 11, 4, 13, 1, 14, 4, 6, 2, 11, 3, 13, 8, 12, 15, 5, 10, 0, 7, 1, 9, 14, 7, 10, 12, 13, 1, 3, 9, 0, 2, 11, 4, 15, 8, 5, 6, 11, 5, 1, 9, 8, 13, 15, 0, 14, 4, 2, 3, 12, 7, 10, 6, 3, 10, 13, 12, 1, 2, 0, 11, 7, 5, 9, 4, 8, 15, 14, 6, 1, 13, 2, 9, 7, 10, 6, 0, 8, 12, 4, 5, 15, 3, 11, 14, 11, 10, 15, 5, 0, 12, 14, 8, 6, 2, 3, 9, 1, 7, 13, 4};

    private int[] h(byte[] userKey) {
        if (userKey.length == 32) {
            int[] key = new int[8];
            for (int i2 = 0; i2 != 8; i2++) {
                key[i2] = g(userKey, i2 * 4);
            }
            return key;
        }
        throw new IllegalArgumentException("Key length invalid. Key needs to be 32 byte - 256 bit!!!");
    }

    public void a(CipherParameters params) {
        reset();
        this.d = new byte[this.a];
        this.h = null;
        if (params instanceof ParametersWithSBox) {
            ParametersWithSBox param = (ParametersWithSBox) params;
            System.arraycopy(param.b(), 0, this.i, 0, param.b().length);
            if (param.a() != null) {
                this.g = h(((KeyParameter) param.a()).a());
            }
        } else if (params instanceof KeyParameter) {
            this.g = h(((KeyParameter) params).a());
        } else if (params instanceof ParametersWithIV) {
            ParametersWithIV p = (ParametersWithIV) params;
            this.g = h(((KeyParameter) p.b()).a());
            byte[] a2 = p.a();
            byte[] bArr = this.e;
            System.arraycopy(a2, 0, bArr, 0, bArr.length);
            this.h = p.a();
        } else {
            throw new IllegalArgumentException("invalid parameter passed to GOST28147 init - " + params.getClass().getName());
        }
    }

    public String b() {
        return "GOST28147Mac";
    }

    public int e() {
        return this.b;
    }

    private int j(int n1, int key) {
        int cm = key + n1;
        byte[] bArr = this.i;
        int om = (bArr[((cm >> 0) & 15) + 0] << 0) + (bArr[((cm >> 4) & 15) + 16] << 4) + (bArr[((cm >> 8) & 15) + 32] << 8) + (bArr[((cm >> 12) & 15) + 48] << 12) + (bArr[((cm >> 16) & 15) + 64] << MappingData.PATH) + (bArr[((cm >> 20) & 15) + 80] << 20) + (bArr[((cm >> 24) & 15) + 96] << 24) + (bArr[((cm >> 28) & 15) + 112] << 28);
        return (om << 11) | (om >>> 21);
    }

    private void i(int[] workingKey, byte[] in, int inOff, byte[] out, int outOff) {
        int N1 = g(in, inOff);
        int N2 = g(in, inOff + 4);
        for (int k = 0; k < 2; k++) {
            for (int j = 0; j < 8; j++) {
                int tmp = N1;
                N1 = N2 ^ j(N1, workingKey[j]);
                N2 = tmp;
            }
        }
        k(N1, out, outOff);
        k(N2, out, outOff + 4);
    }

    private int g(byte[] in, int inOff) {
        return ((in[inOff + 3] << 24) & ViewCompat.MEASURED_STATE_MASK) + ((in[inOff + 2] << MappingData.PATH) & 16711680) + ((in[inOff + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) + (in[inOff] & 255);
    }

    private void k(int num, byte[] out, int outOff) {
        out[outOff + 3] = (byte) (num >>> 24);
        out[outOff + 2] = (byte) (num >>> 16);
        out[outOff + 1] = (byte) (num >>> 8);
        out[outOff] = (byte) num;
    }

    private byte[] f(byte[] buf, int bufOff, byte[] mac) {
        byte[] sum = new byte[(buf.length - bufOff)];
        System.arraycopy(buf, bufOff, sum, 0, mac.length);
        for (int i2 = 0; i2 != mac.length; i2++) {
            sum[i2] = (byte) (sum[i2] ^ mac[i2]);
        }
        return sum;
    }

    public void d(byte in) {
        int i2 = this.c;
        byte[] bArr = this.d;
        if (i2 == bArr.length) {
            byte[] sumbuf = new byte[bArr.length];
            System.arraycopy(bArr, 0, sumbuf, 0, this.e.length);
            if (this.f) {
                this.f = false;
                byte[] bArr2 = this.h;
                if (bArr2 != null) {
                    sumbuf = f(this.d, 0, bArr2);
                }
            } else {
                sumbuf = f(this.d, 0, this.e);
            }
            i(this.g, sumbuf, 0, this.e, 0);
            this.c = 0;
        }
        byte[] sumbuf2 = this.d;
        int i3 = this.c;
        this.c = i3 + 1;
        sumbuf2[i3] = in;
    }

    public void update(byte[] in, int inOff, int len) {
        if (len >= 0) {
            int i2 = this.a;
            int i3 = this.c;
            int gapLen = i2 - i3;
            if (len > gapLen) {
                System.arraycopy(in, inOff, this.d, i3, gapLen);
                byte[] bArr = this.d;
                byte[] sumbuf = new byte[bArr.length];
                System.arraycopy(bArr, 0, sumbuf, 0, this.e.length);
                if (this.f) {
                    this.f = false;
                    byte[] bArr2 = this.h;
                    if (bArr2 != null) {
                        sumbuf = f(this.d, 0, bArr2);
                    }
                } else {
                    sumbuf = f(this.d, 0, this.e);
                }
                i(this.g, sumbuf, 0, this.e, 0);
                this.c = 0;
                len -= gapLen;
                inOff += gapLen;
                while (len > this.a) {
                    i(this.g, f(in, inOff, this.e), 0, this.e, 0);
                    int i4 = this.a;
                    len -= i4;
                    inOff += i4;
                }
            }
            System.arraycopy(in, inOff, this.d, this.c, len);
            this.c += len;
            return;
        }
        throw new IllegalArgumentException("Can't have a negative input length!");
    }

    public int c(byte[] out, int outOff) {
        while (true) {
            int i2 = this.c;
            if (i2 >= this.a) {
                break;
            }
            this.d[i2] = 0;
            this.c = i2 + 1;
        }
        byte[] bArr = this.d;
        byte[] sumbuf = new byte[bArr.length];
        System.arraycopy(bArr, 0, sumbuf, 0, this.e.length);
        if (this.f) {
            this.f = false;
        } else {
            sumbuf = f(this.d, 0, this.e);
        }
        i(this.g, sumbuf, 0, this.e, 0);
        byte[] bArr2 = this.e;
        int i3 = this.b;
        System.arraycopy(bArr2, (bArr2.length / 2) - i3, out, outOff, i3);
        reset();
        return this.b;
    }

    public void reset() {
        int i2 = 0;
        while (true) {
            byte[] bArr = this.d;
            if (i2 < bArr.length) {
                bArr[i2] = 0;
                i2++;
            } else {
                this.c = 0;
                this.f = true;
                return;
            }
        }
    }
}
