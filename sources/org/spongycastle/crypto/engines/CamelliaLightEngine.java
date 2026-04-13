package org.spongycastle.crypto.engines;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.util.Constants;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public class CamelliaLightEngine implements BlockCipher {
    private static final int[] a = {-1600231809, 1003262091, -1233459112, 1286239154, -957401297, -380665154, 1426019237, -237801700, 283453434, -563598051, -1336506174, -1276722691};
    private static final byte[] b = {112, OTACommand.RESP_ID_SEND_OTA, Constants.COMMA, -20, -77, 39, -64, -27, -28, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, 87, 53, -22, 12, -82, 65, 35, -17, 107, -109, 69, 25, -91, 33, -19, 14, 79, 78, 29, 101, -110, -67, -122, -72, -81, -113, 124, -21, 31, -50, 62, 48, -36, 95, 94, -59, 11, 26, -90, -31, 57, -54, -43, 71, 93, 61, -39, 1, 90, -42, 81, 86, 108, 77, -117, 13, -102, 102, -5, -52, -80, 45, 116, 18, 43, 32, -16, -79, OTACommand.RESP_ID_END_OTA_MD5, -103, -33, 76, -53, -62, 52, 126, 118, 5, 109, -73, -87, 49, -47, 23, 4, -41, 20, 88, 58, 97, -34, 27, 17, 28, 50, 15, -100, 22, 83, 24, -14, 34, -2, 68, -49, -78, -61, -75, 122, -111, 36, 8, -24, -88, 96, -4, 105, 80, -86, -48, -96, 125, -95, -119, 98, -105, 84, 91, 30, -107, -32, -1, 100, -46, MappingData.PATH, -60, 0, 72, -93, -9, 117, -37, -118, 3, -26, -38, 9, 63, -35, -108, -121, 92, OTACommand.RESP_ID_END_OTA, 2, -51, 74, -112, 51, 115, 103, -10, -13, -99, Byte.MAX_VALUE, -65, -30, 82, -101, -40, 38, -56, 55, -58, Constants.SEMI_COLON, OTACommand.RESP_ID_START_OTA, -106, 111, 75, 19, -66, 99, 46, -23, 121, -89, -116, -97, 110, -68, -114, 41, -11, -7, -74, 47, -3, -76, 89, 120, -104, 6, 106, -25, 70, 113, -70, -44, 37, -85, 66, -120, -94, -115, -6, 114, 7, -71, 85, -8, -18, -84, 10, 54, 73, 42, 104, 60, 56, -15, -92, 64, 40, -45, 123, -69, -55, 67, -63, 21, -29, -83, -12, 119, -57, OTACommand.RESP_ID_VERSION, -98};
    private boolean c;
    private boolean d;
    private int[] e = new int[96];
    private int[] f = new int[8];
    private int[] g = new int[12];
    private int[] h = new int[4];

    private static int o(int x, int s) {
        return (x >>> s) + (x << (32 - s));
    }

    private static int l(int x, int s) {
        return (x << s) + (x >>> (32 - s));
    }

    private static void p(int rot, int[] ki, int ioff, int[] ko, int ooff) {
        ko[ooff + 0] = (ki[ioff + 0] << rot) | (ki[ioff + 1] >>> (32 - rot));
        ko[ooff + 1] = (ki[ioff + 1] << rot) | (ki[ioff + 2] >>> (32 - rot));
        ko[ooff + 2] = (ki[ioff + 2] << rot) | (ki[ioff + 3] >>> (32 - rot));
        ko[ooff + 3] = (ki[ioff + 3] << rot) | (ki[ioff + 0] >>> (32 - rot));
        ki[ioff + 0] = ko[ooff + 0];
        ki[ioff + 1] = ko[ooff + 1];
        ki[ioff + 2] = ko[ooff + 2];
        ki[ioff + 3] = ko[ooff + 3];
    }

    private static void h(int rot, int[] ki, int ioff, int[] ko, int ooff) {
        ko[ooff + 2] = (ki[ioff + 0] << rot) | (ki[ioff + 1] >>> (32 - rot));
        ko[ooff + 3] = (ki[ioff + 1] << rot) | (ki[ioff + 2] >>> (32 - rot));
        ko[ooff + 0] = (ki[ioff + 2] << rot) | (ki[ioff + 3] >>> (32 - rot));
        ko[ooff + 1] = (ki[ioff + 3] << rot) | (ki[ioff + 0] >>> (32 - rot));
        ki[ioff + 0] = ko[ooff + 2];
        ki[ioff + 1] = ko[ooff + 3];
        ki[ioff + 2] = ko[ooff + 0];
        ki[ioff + 3] = ko[ooff + 1];
    }

    private static void q(int rot, int[] ki, int ioff, int[] ko, int ooff) {
        ko[ooff + 0] = (ki[ioff + 1] << (rot - 32)) | (ki[ioff + 2] >>> (64 - rot));
        ko[ooff + 1] = (ki[ioff + 2] << (rot - 32)) | (ki[ioff + 3] >>> (64 - rot));
        ko[ooff + 2] = (ki[ioff + 3] << (rot - 32)) | (ki[ioff + 0] >>> (64 - rot));
        ko[ooff + 3] = (ki[ioff + 0] << (rot - 32)) | (ki[ioff + 1] >>> (64 - rot));
        ki[ioff + 0] = ko[ooff + 0];
        ki[ioff + 1] = ko[ooff + 1];
        ki[ioff + 2] = ko[ooff + 2];
        ki[ioff + 3] = ko[ooff + 3];
    }

    private static void i(int rot, int[] ki, int ioff, int[] ko, int ooff) {
        ko[ooff + 2] = (ki[ioff + 1] << (rot - 32)) | (ki[ioff + 2] >>> (64 - rot));
        ko[ooff + 3] = (ki[ioff + 2] << (rot - 32)) | (ki[ioff + 3] >>> (64 - rot));
        ko[ooff + 0] = (ki[ioff + 3] << (rot - 32)) | (ki[ioff + 0] >>> (64 - rot));
        ko[ooff + 1] = (ki[ioff + 0] << (rot - 32)) | (ki[ioff + 1] >>> (64 - rot));
        ki[ioff + 0] = ko[ooff + 2];
        ki[ioff + 1] = ko[ooff + 3];
        ki[ioff + 2] = ko[ooff + 0];
        ki[ioff + 3] = ko[ooff + 1];
    }

    private int d(byte[] src, int offset) {
        int word = 0;
        for (int i = 0; i < 4; i++) {
            word = (word << 8) + (src[i + offset] & 255);
        }
        return word;
    }

    private void j(int word, byte[] dst, int offset) {
        for (int i = 0; i < 4; i++) {
            dst[(3 - i) + offset] = (byte) word;
            word >>>= 8;
        }
    }

    private byte k(byte v, int rot) {
        return (byte) ((v << rot) | ((v & 255) >>> (8 - rot)));
    }

    private int r(int x) {
        return k(b[x], 1) & 255;
    }

    private int s(int x) {
        return k(b[x], 7) & 255;
    }

    private int t(int x) {
        return b[k((byte) x, 1) & 255] & 255;
    }

    private void e(int[] s, int[] skey, int keyoff) {
        int t1 = s[0] ^ skey[keyoff + 0];
        int u = t(t1 & 255) | (s((t1 >>> 8) & 255) << 8) | (r((t1 >>> 16) & 255) << 16);
        byte[] bArr = b;
        int t2 = s[1] ^ skey[keyoff + 1];
        int v = l((bArr[t2 & 255] & 255) | (t((t2 >>> 8) & 255) << 8) | (s((t2 >>> 16) & 255) << 16) | (r((t2 >>> 24) & 255) << 24), 8);
        int u2 = (u | ((bArr[(t1 >>> 24) & 255] & 255) << 24)) ^ v;
        int v2 = l(v, 8) ^ u2;
        int u3 = o(u2, 8) ^ v2;
        s[2] = s[2] ^ (l(v2, 16) ^ u3);
        s[3] = s[3] ^ l(u3, 8);
        int t12 = s[2] ^ skey[keyoff + 2];
        int u4 = t(t12 & 255) | (s((t12 >>> 8) & 255) << 8) | (r((t12 >>> 16) & 255) << 16) | ((bArr[(t12 >>> 24) & 255] & 255) << 24);
        int t22 = s[3] ^ skey[keyoff + 3];
        int v3 = l((bArr[t22 & 255] & 255) | (t((t22 >>> 8) & 255) << 8) | (s((t22 >>> 16) & 255) << 16) | (r((t22 >>> 24) & 255) << 24), 8);
        int u5 = u4 ^ v3;
        int v4 = l(v3, 8) ^ u5;
        int u6 = o(u5, 8) ^ v4;
        s[0] = (l(v4, 16) ^ u6) ^ s[0];
        s[1] = s[1] ^ l(u6, 8);
    }

    private void g(int[] s, int[] fkey, int keyoff) {
        s[1] = s[1] ^ l(s[0] & fkey[keyoff + 0], 1);
        s[0] = s[0] ^ (fkey[keyoff + 1] | s[1]);
        s[2] = s[2] ^ (fkey[keyoff + 3] | s[3]);
        s[3] = l(s[2] & fkey[keyoff + 2], 1) ^ s[3];
    }

    private void u(boolean forEncryption, byte[] key) {
        byte[] bArr = key;
        int[] k = new int[8];
        int[] ka = new int[4];
        int[] kb = new int[4];
        int[] t = new int[4];
        switch (bArr.length) {
            case 16:
                this.d = true;
                k[0] = d(bArr, 0);
                k[1] = d(bArr, 4);
                k[2] = d(bArr, 8);
                k[3] = d(bArr, 12);
                k[7] = 0;
                k[6] = 0;
                k[5] = 0;
                k[4] = 0;
                break;
            case 24:
                k[0] = d(bArr, 0);
                k[1] = d(bArr, 4);
                k[2] = d(bArr, 8);
                k[3] = d(bArr, 12);
                k[4] = d(bArr, 16);
                k[5] = d(bArr, 20);
                k[6] = ~k[4];
                k[7] = ~k[5];
                this.d = false;
                break;
            case 32:
                k[0] = d(bArr, 0);
                k[1] = d(bArr, 4);
                k[2] = d(bArr, 8);
                k[3] = d(bArr, 12);
                k[4] = d(bArr, 16);
                k[5] = d(bArr, 20);
                k[6] = d(bArr, 24);
                k[7] = d(bArr, 28);
                this.d = false;
                break;
            default:
                throw new IllegalArgumentException("key sizes are only 16/24/32 bytes.");
        }
        for (int i = 0; i < 4; i++) {
            ka[i] = k[i] ^ k[i + 4];
        }
        e(ka, a, 0);
        for (int i2 = 0; i2 < 4; i2++) {
            ka[i2] = ka[i2] ^ k[i2];
        }
        e(ka, a, 4);
        if (!this.d) {
            for (int i3 = 0; i3 < 4; i3++) {
                kb[i3] = ka[i3] ^ k[i3 + 4];
            }
            e(kb, a, 8);
            if (forEncryption) {
                int[] iArr = this.f;
                iArr[0] = k[0];
                iArr[1] = k[1];
                iArr[2] = k[2];
                iArr[3] = k[3];
                q(45, k, 0, this.e, 16);
                p(15, k, 0, this.g, 4);
                p(17, k, 0, this.e, 32);
                q(34, k, 0, this.e, 44);
                p(15, k, 4, this.e, 4);
                p(15, k, 4, this.g, 0);
                p(30, k, 4, this.e, 24);
                q(34, k, 4, this.e, 36);
                p(15, ka, 0, this.e, 8);
                p(30, ka, 0, this.e, 20);
                int[] iArr2 = this.g;
                iArr2[8] = ka[1];
                iArr2[9] = ka[2];
                iArr2[10] = ka[3];
                iArr2[11] = ka[0];
                q(49, ka, 0, this.e, 40);
                int[] iArr3 = this.e;
                iArr3[0] = kb[0];
                iArr3[1] = kb[1];
                iArr3[2] = kb[2];
                iArr3[3] = kb[3];
                p(30, kb, 0, iArr3, 12);
                p(30, kb, 0, this.e, 28);
                q(51, kb, 0, this.f, 4);
                return;
            }
            int[] iArr4 = this.f;
            iArr4[4] = k[0];
            iArr4[5] = k[1];
            iArr4[6] = k[2];
            iArr4[7] = k[3];
            i(45, k, 0, this.e, 28);
            h(15, k, 0, this.g, 4);
            h(17, k, 0, this.e, 12);
            i(34, k, 0, this.e, 0);
            h(15, k, 4, this.e, 40);
            h(15, k, 4, this.g, 8);
            h(30, k, 4, this.e, 20);
            i(34, k, 4, this.e, 8);
            h(15, ka, 0, this.e, 36);
            h(30, ka, 0, this.e, 24);
            int[] iArr5 = this.g;
            iArr5[2] = ka[1];
            iArr5[3] = ka[2];
            iArr5[0] = ka[3];
            iArr5[1] = ka[0];
            i(49, ka, 0, this.e, 4);
            int[] iArr6 = this.e;
            iArr6[46] = kb[0];
            iArr6[47] = kb[1];
            iArr6[44] = kb[2];
            iArr6[45] = kb[3];
            h(30, kb, 0, iArr6, 32);
            h(30, kb, 0, this.e, 16);
            q(51, kb, 0, this.f, 0);
        } else if (forEncryption) {
            int[] iArr7 = this.f;
            iArr7[0] = k[0];
            iArr7[1] = k[1];
            iArr7[2] = k[2];
            iArr7[3] = k[3];
            p(15, k, 0, this.e, 4);
            p(30, k, 0, this.e, 12);
            p(15, k, 0, t, 0);
            int[] iArr8 = this.e;
            iArr8[18] = t[2];
            iArr8[19] = t[3];
            p(17, k, 0, this.g, 4);
            p(17, k, 0, this.e, 24);
            p(17, k, 0, this.e, 32);
            int[] iArr9 = this.e;
            iArr9[0] = ka[0];
            iArr9[1] = ka[1];
            iArr9[2] = ka[2];
            iArr9[3] = ka[3];
            p(15, ka, 0, iArr9, 8);
            p(15, ka, 0, this.g, 0);
            p(15, ka, 0, t, 0);
            int[] iArr10 = this.e;
            iArr10[16] = t[0];
            iArr10[17] = t[1];
            p(15, ka, 0, iArr10, 20);
            q(34, ka, 0, this.e, 28);
            p(17, ka, 0, this.f, 4);
        } else {
            int[] iArr11 = this.f;
            iArr11[4] = k[0];
            iArr11[5] = k[1];
            iArr11[6] = k[2];
            iArr11[7] = k[3];
            h(15, k, 0, this.e, 28);
            h(30, k, 0, this.e, 20);
            h(15, k, 0, t, 0);
            int[] iArr12 = this.e;
            iArr12[16] = t[0];
            iArr12[17] = t[1];
            h(17, k, 0, this.g, 0);
            h(17, k, 0, this.e, 8);
            h(17, k, 0, this.e, 0);
            int[] iArr13 = this.e;
            iArr13[34] = ka[0];
            iArr13[35] = ka[1];
            iArr13[32] = ka[2];
            iArr13[33] = ka[3];
            h(15, ka, 0, iArr13, 24);
            h(15, ka, 0, this.g, 4);
            h(15, ka, 0, t, 0);
            int[] iArr14 = this.e;
            iArr14[18] = t[2];
            iArr14[19] = t[3];
            h(15, ka, 0, iArr14, 12);
            i(34, ka, 0, this.e, 4);
            p(17, ka, 0, this.f, 0);
        }
    }

    private int m(byte[] in, int inOff, byte[] out, int outOff) {
        for (int i = 0; i < 4; i++) {
            this.h[i] = d(in, (i * 4) + inOff);
            int[] iArr = this.h;
            iArr[i] = iArr[i] ^ this.f[i];
        }
        e(this.h, this.e, 0);
        e(this.h, this.e, 4);
        e(this.h, this.e, 8);
        g(this.h, this.g, 0);
        e(this.h, this.e, 12);
        e(this.h, this.e, 16);
        e(this.h, this.e, 20);
        g(this.h, this.g, 4);
        e(this.h, this.e, 24);
        e(this.h, this.e, 28);
        e(this.h, this.e, 32);
        int[] iArr2 = this.h;
        int i2 = iArr2[2];
        int[] iArr3 = this.f;
        iArr2[2] = iArr3[4] ^ i2;
        iArr2[3] = iArr2[3] ^ iArr3[5];
        iArr2[0] = iArr2[0] ^ iArr3[6];
        iArr2[1] = iArr3[7] ^ iArr2[1];
        j(iArr2[2], out, outOff);
        j(this.h[3], out, outOff + 4);
        j(this.h[0], out, outOff + 8);
        j(this.h[1], out, outOff + 12);
        return 16;
    }

    private int n(byte[] in, int inOff, byte[] out, int outOff) {
        for (int i = 0; i < 4; i++) {
            this.h[i] = d(in, (i * 4) + inOff);
            int[] iArr = this.h;
            iArr[i] = iArr[i] ^ this.f[i];
        }
        e(this.h, this.e, 0);
        e(this.h, this.e, 4);
        e(this.h, this.e, 8);
        g(this.h, this.g, 0);
        e(this.h, this.e, 12);
        e(this.h, this.e, 16);
        e(this.h, this.e, 20);
        g(this.h, this.g, 4);
        e(this.h, this.e, 24);
        e(this.h, this.e, 28);
        e(this.h, this.e, 32);
        g(this.h, this.g, 8);
        e(this.h, this.e, 36);
        e(this.h, this.e, 40);
        e(this.h, this.e, 44);
        int[] iArr2 = this.h;
        int i2 = iArr2[2];
        int[] iArr3 = this.f;
        iArr2[2] = iArr3[4] ^ i2;
        iArr2[3] = iArr2[3] ^ iArr3[5];
        iArr2[0] = iArr2[0] ^ iArr3[6];
        iArr2[1] = iArr3[7] ^ iArr2[1];
        j(iArr2[2], out, outOff);
        j(this.h[3], out, outOff + 4);
        j(this.h[0], out, outOff + 8);
        j(this.h[1], out, outOff + 12);
        return 16;
    }

    public String b() {
        return "Camellia";
    }

    public int c() {
        return 16;
    }

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof KeyParameter) {
            u(forEncryption, ((KeyParameter) params).a());
            this.c = true;
            return;
        }
        throw new IllegalArgumentException("only simple KeyParameter expected.");
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        if (!this.c) {
            throw new IllegalStateException("Camellia is not initialized");
        } else if (inOff + 16 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + 16 > out.length) {
            throw new OutputLengthException("output buffer too short");
        } else if (this.d) {
            return m(in, inOff, out, outOff);
        } else {
            return n(in, inOff, out, outOff);
        }
    }

    public void reset() {
    }
}
