package org.spongycastle.crypto.engines;

import androidx.core.view.InputDeviceCompat;
import com.leedarson.serviceimpl.business.bean.OTACommand;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.util.Constants;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public final class TwofishEngine implements BlockCipher {
    private static final byte[][] a = {new byte[]{-87, 103, -77, -24, 4, -3, -93, 118, -102, -110, OTACommand.RESP_ID_VERSION, 120, -28, -35, -47, 56, 13, -58, 53, -104, 24, -9, -20, 108, 67, 117, 55, 38, -6, 19, -108, 72, -14, -48, -117, 48, OTACommand.RESP_ID_END_OTA_MD5, 84, -33, 35, 25, 91, 61, 89, -13, -82, -94, OTACommand.RESP_ID_SEND_OTA, 99, 1, OTACommand.RESP_ID_END_OTA, 46, -39, 81, -101, 124, -90, -21, -91, -66, 22, 12, -29, 97, -64, -116, 58, -11, 115, Constants.COMMA, 37, 11, -69, 78, -119, 107, 83, 106, -76, -15, -31, -26, -67, 69, -30, -12, -74, 102, -52, -107, 3, 86, -44, 28, 30, -41, -5, -61, -114, -75, -23, -49, -65, -70, -22, 119, 57, -81, 51, -55, 98, 113, OTACommand.RESP_ID_START_OTA, 121, 9, -83, 36, -51, -7, -40, -27, -59, -71, 77, 68, 8, -122, -25, -95, 29, -86, -19, 6, 112, -78, -46, 65, 123, -96, 17, 49, -62, 39, -112, 32, -10, 96, -1, -106, 92, -79, -85, -98, -100, 82, 27, 95, -109, 10, -17, -111, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, 73, -18, 45, 79, -113, Constants.SEMI_COLON, 71, -121, 109, 70, -42, 62, 105, 100, 42, -50, -53, 47, -4, -105, 5, 122, -84, Byte.MAX_VALUE, -43, 26, 75, 14, -89, 90, 40, 20, 63, 41, -120, 60, 76, 2, -72, -38, -80, 23, 85, 31, -118, 125, 87, -57, -115, 116, -73, -60, -97, 114, 126, 21, 34, 18, 88, 7, -103, 52, 110, 80, -34, 104, 101, -68, -37, -8, -56, -88, 43, 64, -36, -2, 50, -92, -54, MappingData.PATH, 33, -16, -45, 93, 15, 0, 111, -99, 54, 66, 74, 94, -63, -32}, new byte[]{117, -13, -58, -12, -37, 123, -5, -56, 74, -45, -26, 107, 69, 125, -24, 75, -42, 50, -40, -3, 55, 113, -15, -31, 48, 15, -8, 27, -121, -6, 6, 63, 94, -70, -82, 91, -118, 0, -68, -99, 109, -63, -79, 14, OTACommand.RESP_ID_VERSION, 93, -46, -43, -96, OTACommand.RESP_ID_END_OTA_MD5, 7, 20, -75, -112, Constants.COMMA, -93, -78, 115, 76, 84, -110, 116, 54, 81, 56, -80, -67, 90, -4, 96, 98, -106, 108, 66, -9, MappingData.PATH, 124, 40, 39, -116, 19, -107, -100, -57, 36, 70, Constants.SEMI_COLON, 112, -54, -29, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, -53, 17, -48, -109, -72, -90, OTACommand.RESP_ID_END_OTA, 32, -1, -97, 119, -61, -52, 3, 111, 8, -65, 64, -25, 43, -30, 121, 12, -86, OTACommand.RESP_ID_SEND_OTA, 65, 58, -22, -71, -28, -102, -92, -105, 126, -38, 122, 23, 102, -108, -95, 29, 61, -16, -34, -77, 11, 114, -89, 28, -17, -47, 83, 62, -113, 51, 38, 95, -20, 118, 42, 73, OTACommand.RESP_ID_START_OTA, -120, -18, 33, -60, 26, -21, -39, -59, 57, -103, -51, -83, 49, -117, 1, 24, 35, -35, 31, 78, 45, -7, 72, 79, -14, 101, -114, 120, 92, 88, 25, -115, -27, -104, 87, 103, Byte.MAX_VALUE, 5, 100, -81, 99, -74, -2, -11, -73, 60, -91, -50, -23, 104, 68, -32, 77, 67, 105, 41, 46, -84, 21, 89, -88, 10, -98, 110, 71, -33, 52, 53, 106, -49, -36, 34, -55, -64, -101, -119, -44, -19, -85, 18, -94, 13, 82, -69, 2, 47, -87, -41, 97, 30, -76, 80, 4, -10, -62, 22, 37, -122, 86, 85, 9, -66, -111}};
    private boolean b = false;
    private int[] c = new int[256];
    private int[] d = new int[256];
    private int[] e = new int[256];
    private int[] f = new int[256];
    private int[] g;
    private int[] h;
    private int i = 0;
    private byte[] j = null;

    public TwofishEngine() {
        int[] m1 = new int[2];
        int[] mX = new int[2];
        int[] mY = new int[2];
        for (int i2 = 0; i2 < 256; i2++) {
            byte[][] bArr = a;
            int j2 = bArr[0][i2] & 255;
            m1[0] = j2;
            mX[0] = l(j2) & 255;
            mY[0] = m(j2) & 255;
            int j3 = bArr[1][i2] & 255;
            m1[1] = j3;
            mX[1] = l(j3) & 255;
            mY[1] = m(j3) & 255;
            this.c[i2] = m1[1] | (mX[1] << 8) | (mY[1] << 16) | (mY[1] << 24);
            this.d[i2] = mY[0] | (mY[0] << 8) | (mX[0] << 16) | (m1[0] << 24);
            this.e[i2] = (mY[1] << 24) | mX[1] | (mY[1] << 8) | (m1[1] << 16);
            this.f[i2] = mX[0] | (m1[0] << 8) | (mY[0] << 16) | (mX[0] << 24);
        }
    }

    public void a(boolean encrypting, CipherParameters params) {
        if (params instanceof KeyParameter) {
            this.b = encrypting;
            byte[] a2 = ((KeyParameter) params).a();
            this.j = a2;
            this.i = a2.length / 8;
            v(a2);
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to Twofish init - " + params.getClass().getName());
    }

    public String b() {
        return "Twofish";
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        if (this.j == null) {
            throw new IllegalStateException("Twofish not initialised");
        } else if (inOff + 16 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + 16 > out.length) {
            throw new OutputLengthException("output buffer too short");
        } else if (this.b) {
            u(in, inOff, out, outOff);
            return 16;
        } else {
            t(in, inOff, out, outOff);
            return 16;
        }
    }

    public void reset() {
        byte[] bArr = this.j;
        if (bArr != null) {
            v(bArr);
        }
    }

    public int c() {
        return 16;
    }

    private void v(byte[] key) {
        int[] k32e;
        char c2;
        byte[] bArr = key;
        int[] k32e2 = new int[4];
        int[] k32o = new int[4];
        int[] sBoxKeys = new int[4];
        this.g = new int[40];
        int i2 = this.i;
        if (i2 < 1) {
            throw new IllegalArgumentException("Key size less than 64 bits");
        } else if (i2 <= 4) {
            for (int i3 = 0; i3 < this.i; i3++) {
                int p = i3 * 8;
                k32e2[i3] = e(bArr, p);
                k32o[i3] = e(bArr, p + 4);
                sBoxKeys[(this.i - 1) - i3] = n(k32e2[i3], k32o[i3]);
            }
            for (int i4 = 0; i4 < 20; i4++) {
                int q = 33686018 * i4;
                int A = g(q, k32e2);
                int B = g(16843009 + q, k32o);
                int B2 = (B << 8) | (B >>> 24);
                int A2 = A + B2;
                int[] iArr = this.g;
                iArr[i4 * 2] = A2;
                int A3 = A2 + B2;
                iArr[(i4 * 2) + 1] = (A3 << 9) | (A3 >>> 23);
            }
            int i5 = 0;
            int k0 = sBoxKeys[0];
            int k1 = sBoxKeys[1];
            int k2 = sBoxKeys[2];
            int i6 = 3;
            int k3 = sBoxKeys[3];
            this.h = new int[1024];
            int i7 = 0;
            while (i7 < 256) {
                int b3 = i7;
                int b2 = i7;
                int b1 = i7;
                int b0 = i7;
                switch (this.i & i6) {
                    case 0:
                        byte[][] bArr2 = a;
                        b0 = (bArr2[1][b0] & 255) ^ p(k3);
                        b1 = (bArr2[0][b1] & 255) ^ q(k3);
                        b2 = (bArr2[0][b2] & 255) ^ r(k3);
                        c2 = 1;
                        b3 = (bArr2[1][b3] & 255) ^ s(k3);
                        break;
                    case 1:
                        int[] iArr2 = this.c;
                        byte[][] bArr3 = a;
                        this.h[i7 * 2] = iArr2[(bArr3[i5][b0] & 255) ^ p(k0)];
                        this.h[(i7 * 2) + 1] = this.d[(bArr3[0][b1] & 255) ^ q(k0)];
                        this.h[(i7 * 2) + 512] = this.e[(bArr3[1][b2] & 255) ^ r(k0)];
                        this.h[(i7 * 2) + InputDeviceCompat.SOURCE_DPAD] = this.f[(bArr3[1][b3] & 255) ^ s(k0)];
                        k32e = k32e2;
                        continue;
                    case 2:
                        int[] iArr3 = this.c;
                        byte[][] bArr4 = a;
                        k32e = k32e2;
                        this.h[i7 * 2] = iArr3[(bArr4[0][(bArr4[0][b0] & 255) ^ p(k1)] & 255) ^ p(k0)];
                        this.h[(i7 * 2) + 1] = this.d[(bArr4[0][(bArr4[1][b1] & 255) ^ q(k1)] & 255) ^ q(k0)];
                        this.h[(i7 * 2) + 512] = this.e[(bArr4[1][(bArr4[0][b2] & 255) ^ r(k1)] & 255) ^ r(k0)];
                        this.h[(i7 * 2) + InputDeviceCompat.SOURCE_DPAD] = this.f[(bArr4[1][(bArr4[1][b3] & 255) ^ s(k1)] & 255) ^ s(k0)];
                        continue;
                    case 3:
                        c2 = 1;
                        break;
                    default:
                        k32e = k32e2;
                        continue;
                }
                byte[][] bArr5 = a;
                b0 = (bArr5[c2][b0] & 255) ^ p(k2);
                b1 = (bArr5[c2][b1] & 255) ^ q(k2);
                b2 = (bArr5[0][b2] & 255) ^ r(k2);
                b3 = (bArr5[0][b3] & 255) ^ s(k2);
                int[] iArr32 = this.c;
                byte[][] bArr42 = a;
                k32e = k32e2;
                this.h[i7 * 2] = iArr32[(bArr42[0][(bArr42[0][b0] & 255) ^ p(k1)] & 255) ^ p(k0)];
                this.h[(i7 * 2) + 1] = this.d[(bArr42[0][(bArr42[1][b1] & 255) ^ q(k1)] & 255) ^ q(k0)];
                this.h[(i7 * 2) + 512] = this.e[(bArr42[1][(bArr42[0][b2] & 255) ^ r(k1)] & 255) ^ r(k0)];
                this.h[(i7 * 2) + InputDeviceCompat.SOURCE_DPAD] = this.f[(bArr42[1][(bArr42[1][b3] & 255) ^ s(k1)] & 255) ^ s(k0)];
                continue;
                i7++;
                byte[] bArr6 = key;
                k32e2 = k32e;
                i5 = 0;
                i6 = 3;
            }
        } else {
            throw new IllegalArgumentException("Key size larger than 256 bits");
        }
    }

    private void u(byte[] src, int srcIndex, byte[] dst, int dstIndex) {
        byte[] bArr = src;
        byte[] bArr2 = dst;
        int i2 = dstIndex;
        int x0 = e(src, srcIndex) ^ this.g[0];
        int x1 = e(bArr, srcIndex + 4) ^ this.g[1];
        int x2 = e(bArr, srcIndex + 8) ^ this.g[2];
        int x3 = e(bArr, srcIndex + 12) ^ this.g[3];
        int t0 = 8;
        int r = 0;
        while (r < 16) {
            int t02 = h(x0);
            int t1 = i(x1);
            int[] iArr = this.g;
            int k = t0 + 1;
            int x22 = x2 ^ ((t02 + t1) + iArr[t0]);
            x2 = (x22 >>> 1) | (x22 << 31);
            int k2 = k + 1;
            x3 = ((x3 << 1) | (x3 >>> 31)) ^ (((t1 * 2) + t02) + iArr[k]);
            int t03 = h(x2);
            int t12 = i(x3);
            int[] iArr2 = this.g;
            int k3 = k2 + 1;
            int x02 = x0 ^ ((t03 + t12) + iArr2[k2]);
            x0 = (x02 >>> 1) | (x02 << 31);
            x1 = ((x1 << 1) | (x1 >>> 31)) ^ (((t12 * 2) + t03) + iArr2[k3]);
            r += 2;
            t0 = k3 + 1;
        }
        d(this.g[4] ^ x2, bArr2, i2);
        d(this.g[5] ^ x3, bArr2, i2 + 4);
        d(this.g[6] ^ x0, bArr2, i2 + 8);
        d(this.g[7] ^ x1, bArr2, i2 + 12);
    }

    private void t(byte[] src, int srcIndex, byte[] dst, int dstIndex) {
        byte[] bArr = src;
        byte[] bArr2 = dst;
        int i2 = dstIndex;
        int x2 = e(src, srcIndex) ^ this.g[4];
        int x3 = e(bArr, srcIndex + 4) ^ this.g[5];
        int x0 = e(bArr, srcIndex + 8) ^ this.g[6];
        int x1 = e(bArr, srcIndex + 12) ^ this.g[7];
        int t0 = 39;
        int r = 0;
        while (r < 16) {
            int t02 = h(x2);
            int t1 = i(x3);
            int[] iArr = this.g;
            int k = t0 - 1;
            int x12 = x1 ^ (((t1 * 2) + t02) + iArr[t0]);
            int k2 = k - 1;
            x0 = ((x0 << 1) | (x0 >>> 31)) ^ ((t02 + t1) + iArr[k]);
            x1 = (x12 >>> 1) | (x12 << 31);
            int t03 = h(x0);
            int t12 = i(x1);
            int[] iArr2 = this.g;
            int k3 = k2 - 1;
            int x32 = x3 ^ (((t12 * 2) + t03) + iArr2[k2]);
            x2 = ((x2 << 1) | (x2 >>> 31)) ^ ((t03 + t12) + iArr2[k3]);
            x3 = (x32 >>> 1) | (x32 << 31);
            r += 2;
            t0 = k3 - 1;
        }
        d(this.g[0] ^ x0, bArr2, i2);
        d(this.g[1] ^ x1, bArr2, i2 + 4);
        d(this.g[2] ^ x2, bArr2, i2 + 8);
        d(this.g[3] ^ x3, bArr2, i2 + 12);
    }

    private int g(int x, int[] k32) {
        int b0 = p(x);
        int b1 = q(x);
        int b2 = r(x);
        int b3 = s(x);
        int k0 = k32[0];
        int k1 = k32[1];
        int k2 = k32[2];
        int k3 = k32[3];
        switch (3 & this.i) {
            case 0:
                byte[][] bArr = a;
                b0 = (bArr[1][b0] & 255) ^ p(k3);
                b1 = (bArr[0][b1] & 255) ^ q(k3);
                b2 = (bArr[0][b2] & 255) ^ r(k3);
                b3 = (bArr[1][b3] & 255) ^ s(k3);
                break;
            case 1:
                int[] iArr = this.c;
                byte[][] bArr2 = a;
                return ((this.d[(bArr2[0][b1] & 255) ^ q(k0)] ^ iArr[(bArr2[0][b0] & 255) ^ p(k0)]) ^ this.e[(bArr2[1][b2] & 255) ^ r(k0)]) ^ this.f[(bArr2[1][b3] & 255) ^ s(k0)];
            case 2:
                break;
            case 3:
                break;
            default:
                return 0;
        }
        byte[][] bArr3 = a;
        b0 = (bArr3[1][b0] & 255) ^ p(k2);
        b1 = (bArr3[1][b1] & 255) ^ q(k2);
        b2 = (bArr3[0][b2] & 255) ^ r(k2);
        b3 = (bArr3[0][b3] & 255) ^ s(k2);
        int[] iArr2 = this.c;
        byte[][] bArr4 = a;
        return ((this.d[(bArr4[0][(bArr4[1][b1] & 255) ^ q(k1)] & 255) ^ q(k0)] ^ iArr2[(bArr4[0][(bArr4[0][b0] & 255) ^ p(k1)] & 255) ^ p(k0)]) ^ this.e[(bArr4[1][(bArr4[0][b2] & 255) ^ r(k1)] & 255) ^ r(k0)]) ^ this.f[(bArr4[1][(bArr4[1][b3] & 255) ^ s(k1)] & 255) ^ s(k0)];
    }

    private int n(int k0, int k1) {
        int r = k1;
        for (int i2 = 0; i2 < 4; i2++) {
            r = o(r);
        }
        int r2 = r ^ k0;
        for (int i3 = 0; i3 < 4; i3++) {
            r2 = o(r2);
        }
        return r2;
    }

    private int o(int x) {
        int b2 = (x >>> 24) & 255;
        int i2 = 0;
        int g2 = ((b2 << 1) ^ ((b2 & 128) != 0 ? 333 : 0)) & 255;
        int i3 = b2 >>> 1;
        if ((b2 & 1) != 0) {
            i2 = 166;
        }
        int g3 = (i3 ^ i2) ^ g2;
        return ((((x << 8) ^ (g3 << 24)) ^ (g2 << 16)) ^ (g3 << 8)) ^ b2;
    }

    private int j(int x) {
        return (x >> 1) ^ ((x & 1) != 0 ? 180 : 0);
    }

    private int k(int x) {
        int i2 = 0;
        int i3 = (x >> 2) ^ ((x & 2) != 0 ? 180 : 0);
        if ((x & 1) != 0) {
            i2 = 90;
        }
        return i3 ^ i2;
    }

    private int l(int x) {
        return k(x) ^ x;
    }

    private int m(int x) {
        return (j(x) ^ x) ^ k(x);
    }

    private int p(int x) {
        return x & 255;
    }

    private int q(int x) {
        return (x >>> 8) & 255;
    }

    private int r(int x) {
        return (x >>> 16) & 255;
    }

    private int s(int x) {
        return (x >>> 24) & 255;
    }

    private int h(int x) {
        int[] iArr = this.h;
        return iArr[(((x >>> 24) & 255) * 2) + InputDeviceCompat.SOURCE_DPAD] ^ ((iArr[((x & 255) * 2) + 0] ^ iArr[(((x >>> 8) & 255) * 2) + 1]) ^ iArr[(((x >>> 16) & 255) * 2) + 512]);
    }

    private int i(int x) {
        int[] iArr = this.h;
        return iArr[(((x >>> 16) & 255) * 2) + InputDeviceCompat.SOURCE_DPAD] ^ ((iArr[(((x >>> 24) & 255) * 2) + 0] ^ iArr[((x & 255) * 2) + 1]) ^ iArr[(((x >>> 8) & 255) * 2) + 512]);
    }

    private int e(byte[] b2, int p) {
        return (b2[p] & 255) | ((b2[p + 1] & 255) << 8) | ((b2[p + 2] & 255) << MappingData.PATH) | ((b2[p + 3] & 255) << 24);
    }

    private void d(int in, byte[] b2, int offset) {
        b2[offset] = (byte) in;
        b2[offset + 1] = (byte) (in >> 8);
        b2[offset + 2] = (byte) (in >> 16);
        b2[offset + 3] = (byte) (in >> 24);
    }
}
