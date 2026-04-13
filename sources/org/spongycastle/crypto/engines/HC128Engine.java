package org.spongycastle.crypto.engines;

import com.tutk.IOTC.AVIOCTRLDEFs;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class HC128Engine implements StreamCipher {
    private int[] a = new int[512];
    private int[] b = new int[512];
    private int c = 0;
    private byte[] d;
    private byte[] e;
    private boolean f;
    private byte[] g = new byte[4];
    private int h = 0;

    private static int f(int x) {
        return (q(x, 7) ^ q(x, 18)) ^ (x >>> 3);
    }

    private static int g(int x) {
        return (q(x, 17) ^ q(x, 19)) ^ (x >>> 10);
    }

    private int h(int x, int y, int z) {
        return (q(x, 10) ^ q(z, 23)) + q(y, 8);
    }

    private int i(int x, int y, int z) {
        return (p(x, 10) ^ p(z, 23)) + p(y, 8);
    }

    private static int p(int x, int bits) {
        return (x << bits) | (x >>> (-bits));
    }

    private static int q(int x, int bits) {
        return (x >>> bits) | (x << (-bits));
    }

    private int k(int x) {
        int[] iArr = this.b;
        return iArr[x & 255] + iArr[((x >> 16) & 255) + 256];
    }

    private int l(int x) {
        int[] iArr = this.a;
        return iArr[x & 255] + iArr[((x >> 16) & 255) + 256];
    }

    private static int n(int x) {
        return x & 1023;
    }

    private static int o(int x) {
        return x & 511;
    }

    private static int c(int x, int y) {
        return o(x - y);
    }

    private int r() {
        int ret;
        int j = o(this.c);
        if (this.c < 512) {
            int[] iArr = this.a;
            iArr[j] = iArr[j] + h(iArr[c(j, 3)], this.a[c(j, 10)], this.a[c(j, 511)]);
            ret = k(this.a[c(j, 12)]) ^ this.a[j];
        } else {
            int[] iArr2 = this.b;
            iArr2[j] = iArr2[j] + i(iArr2[c(j, 3)], this.b[c(j, 10)], this.b[c(j, 511)]);
            ret = l(this.b[c(j, 12)]) ^ this.b[j];
        }
        this.c = n(this.c + 1);
        return ret;
    }

    private void m() {
        if (this.d.length == 16) {
            this.h = 0;
            this.c = 0;
            int[] w = new int[AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_SAVE_DROPBOX_REQ];
            for (int i = 0; i < 16; i++) {
                int i2 = i >> 2;
                w[i2] = ((this.d[i] & 255) << ((i & 3) * 8)) | w[i2];
            }
            System.arraycopy(w, 0, w, 4, 4);
            int i3 = 0;
            while (true) {
                byte[] bArr = this.e;
                if (i3 >= bArr.length || i3 >= 16) {
                    System.arraycopy(w, 8, w, 12, 4);
                } else {
                    int i4 = (i3 >> 2) + 8;
                    w[i4] = ((bArr[i3] & 255) << ((i3 & 3) * 8)) | w[i4];
                    i3++;
                }
            }
            System.arraycopy(w, 8, w, 12, 4);
            for (int i5 = 16; i5 < 1280; i5++) {
                w[i5] = g(w[i5 - 2]) + w[i5 - 7] + f(w[i5 - 15]) + w[i5 - 16] + i5;
            }
            System.arraycopy(w, 256, this.a, 0, 512);
            System.arraycopy(w, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART, this.b, 0, 512);
            for (int i6 = 0; i6 < 512; i6++) {
                this.a[i6] = r();
            }
            for (int i7 = 0; i7 < 512; i7++) {
                this.b[i7] = r();
            }
            this.c = 0;
            return;
        }
        throw new IllegalArgumentException("The key must be 128 bits long");
    }

    public String b() {
        return "HC-128";
    }

    public void a(boolean forEncryption, CipherParameters params) {
        CipherParameters keyParam = params;
        if (params instanceof ParametersWithIV) {
            this.e = ((ParametersWithIV) params).a();
            keyParam = ((ParametersWithIV) params).b();
        } else {
            this.e = new byte[0];
        }
        if (keyParam instanceof KeyParameter) {
            this.d = ((KeyParameter) keyParam).a();
            m();
            this.f = true;
            return;
        }
        throw new IllegalArgumentException("Invalid parameter passed to HC128 init - " + params.getClass().getName());
    }

    private byte j() {
        if (this.h == 0) {
            int step = r();
            byte[] bArr = this.g;
            bArr[0] = (byte) (step & 255);
            int step2 = step >> 8;
            bArr[1] = (byte) (step2 & 255);
            int step3 = step2 >> 8;
            bArr[2] = (byte) (step3 & 255);
            bArr[3] = (byte) ((step3 >> 8) & 255);
        }
        byte[] bArr2 = this.g;
        int i = this.h;
        byte ret = bArr2[i];
        this.h = 3 & (i + 1);
        return ret;
    }

    public int d(byte[] in, int inOff, int len, byte[] out, int outOff) {
        if (!this.f) {
            throw new IllegalStateException(b() + " not initialised");
        } else if (inOff + len > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + len <= out.length) {
            for (int i = 0; i < len; i++) {
                out[outOff + i] = (byte) (in[inOff + i] ^ j());
            }
            return len;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
        m();
    }

    public byte e(byte in) {
        return (byte) (j() ^ in);
    }
}
