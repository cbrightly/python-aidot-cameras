package org.spongycastle.crypto.engines;

import com.tutk.IOTC.AVIOCTRLDEFs;
import leedarson.platform.playersdk.PlayerStateDefine;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class HC256Engine implements StreamCipher {
    private int[] a = new int[1024];
    private int[] b = new int[1024];
    private int c = 0;
    private byte[] d;
    private byte[] e;
    private boolean f;
    private byte[] g = new byte[4];
    private int h = 0;

    private int h() {
        int ret;
        int i = this.c;
        int j = i & 1023;
        if (i < 1024) {
            int[] iArr = this.a;
            int x = iArr[(j - 3) & 1023];
            int y = iArr[(j + PlayerStateDefine.EC_NO_INIT) & 1023];
            int i2 = iArr[j];
            int g2 = iArr[(j - 10) & 1023] + (g(y, 23) ^ g(x, 10));
            int[] iArr2 = this.b;
            iArr[j] = i2 + g2 + iArr2[(x ^ y) & 1023];
            int[] iArr3 = this.a;
            int x2 = iArr3[(j - 12) & 1023];
            ret = iArr3[j] ^ (((iArr2[x2 & 255] + iArr2[((x2 >> 8) & 255) + 256]) + iArr2[((x2 >> 16) & 255) + 512]) + iArr2[((x2 >> 24) & 255) + AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART]);
        } else {
            int[] iArr4 = this.b;
            int x3 = iArr4[(j - 3) & 1023];
            int y2 = iArr4[(j + PlayerStateDefine.EC_NO_INIT) & 1023];
            int i3 = iArr4[j];
            int g3 = iArr4[(j - 10) & 1023] + (g(y2, 23) ^ g(x3, 10));
            int[] iArr5 = this.a;
            iArr4[j] = i3 + g3 + iArr5[(x3 ^ y2) & 1023];
            int[] iArr6 = this.b;
            int x4 = iArr6[(j - 12) & 1023];
            ret = iArr6[j] ^ (((iArr5[x4 & 255] + iArr5[((x4 >> 8) & 255) + 256]) + iArr5[((x4 >> 16) & 255) + 512]) + iArr5[((x4 >> 24) & 255) + AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART]);
        }
        this.c = (this.c + 1) & 2047;
        return ret;
    }

    private void f() {
        byte[] bArr = this.d;
        if (bArr.length != 32 && bArr.length != 16) {
            throw new IllegalArgumentException("The key must be 128/256 bits long");
        } else if (this.e.length >= 16) {
            if (bArr.length != 32) {
                byte[] k = new byte[32];
                System.arraycopy(bArr, 0, k, 0, bArr.length);
                byte[] bArr2 = this.d;
                System.arraycopy(bArr2, 0, k, 16, bArr2.length);
                this.d = k;
            }
            byte[] bArr3 = this.e;
            if (bArr3.length < 32) {
                byte[] newIV = new byte[32];
                System.arraycopy(bArr3, 0, newIV, 0, bArr3.length);
                byte[] bArr4 = this.e;
                System.arraycopy(bArr4, 0, newIV, bArr4.length, newIV.length - bArr4.length);
                this.e = newIV;
            }
            this.h = 0;
            this.c = 0;
            int[] w = new int[2560];
            for (int i = 0; i < 32; i++) {
                int i2 = i >> 2;
                w[i2] = w[i2] | ((this.d[i] & 255) << ((i & 3) * 8));
            }
            for (int i3 = 0; i3 < 32; i3++) {
                int i4 = (i3 >> 2) + 8;
                w[i4] = w[i4] | ((this.e[i3] & 255) << ((i3 & 3) * 8));
            }
            for (int i5 = 16; i5 < 2560; i5++) {
                int x = w[i5 - 2];
                int y = w[i5 - 15];
                w[i5] = ((g(x, 17) ^ g(x, 19)) ^ (x >>> 10)) + w[i5 - 7] + ((g(y, 7) ^ g(y, 18)) ^ (y >>> 3)) + w[i5 - 16] + i5;
            }
            System.arraycopy(w, 512, this.a, 0, 1024);
            System.arraycopy(w, AVIOCTRLDEFs.IOTYPE_CRUISEMODE_CRUISE_START, this.b, 0, 1024);
            for (int i6 = 0; i6 < 4096; i6++) {
                h();
            }
            this.c = 0;
        } else {
            throw new IllegalArgumentException("The IV must be at least 128 bits long");
        }
    }

    public String b() {
        return "HC-256";
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
            f();
            this.f = true;
            return;
        }
        throw new IllegalArgumentException("Invalid parameter passed to HC256 init - " + params.getClass().getName());
    }

    private byte c() {
        if (this.h == 0) {
            int step = h();
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
                out[outOff + i] = (byte) (in[inOff + i] ^ c());
            }
            return len;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
        f();
    }

    public byte e(byte in) {
        return (byte) (c() ^ in);
    }

    private static int g(int x, int bits) {
        return (x >>> bits) | (x << (-bits));
    }
}
