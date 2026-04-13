package org.spongycastle.crypto.engines;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.util.Constants;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.RC2Parameters;

public class RC2Engine implements BlockCipher {
    private static byte[] a = {-39, 120, -7, -60, 25, -35, -75, -19, 40, -23, -3, 121, 74, -96, -40, -99, -58, 126, 55, OTACommand.RESP_ID_END_OTA, 43, 118, 83, -114, 98, 76, 100, -120, 68, -117, -5, -94, 23, -102, 89, -11, -121, -77, 79, 19, 97, 69, 109, -115, 9, OTACommand.RESP_ID_START_OTA, 125, 50, -67, -113, 64, -21, -122, -73, 123, 11, -16, -107, 33, 34, 92, 107, 78, OTACommand.RESP_ID_SEND_OTA, 84, -42, 101, -109, -50, 96, -78, 28, 115, 86, -64, 20, -89, -116, -15, -36, 18, 117, -54, 31, Constants.SEMI_COLON, -66, -28, -47, 66, 61, -44, 48, -93, 60, -74, 38, 111, -65, 14, -38, 70, 105, 7, 87, 39, -14, 29, -101, -68, -108, 67, 3, -8, 17, -57, -10, -112, -17, 62, -25, 6, -61, -43, 47, -56, 102, 30, -41, 8, -24, -22, -34, OTACommand.RESP_ID_VERSION, 82, -18, -9, OTACommand.RESP_ID_END_OTA_MD5, -86, 114, -84, 53, 77, 106, 42, -106, 26, -46, 113, 90, 21, 73, 116, 75, -97, -48, 94, 4, 24, -92, -20, -62, -32, 65, 110, 15, 81, -53, -52, 36, -111, -81, 80, -95, -12, 112, 57, -103, 124, 58, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, 35, -72, -76, 122, -4, 2, 54, 91, 37, 85, -105, 49, 45, 93, -6, -104, -29, -118, -110, -82, 5, -33, 41, MappingData.PATH, 103, 108, -70, -55, -45, 0, -26, -49, -31, -98, -88, Constants.COMMA, 99, 22, 1, 63, 88, -30, -119, -87, 13, 56, 52, 27, -85, 51, -1, -80, -69, 72, 12, 95, -71, -79, -51, 46, -59, -13, -37, 71, -27, -91, -100, 119, 10, -90, 32, 104, -2, Byte.MAX_VALUE, -63, -83};
    private int[] b;
    private boolean c;

    private int[] g(byte[] key, int bits) {
        int[] xKey = new int[128];
        for (int i = 0; i != key.length; i++) {
            xKey[i] = key[i] & 255;
        }
        int len = key.length;
        if (len < 128) {
            int len2 = 0;
            int x = xKey[len - 1];
            while (true) {
                int index = len2 + 1;
                x = a[(xKey[len2] + x) & 255] & 255;
                int len3 = len + 1;
                xKey[len] = x;
                len = len3;
                if (len3 >= 128) {
                    break;
                }
                len2 = index;
            }
        }
        int len4 = (bits + 7) >> 3;
        int x2 = a[xKey[128 - len4] & (255 >> ((-bits) & 7))] & 255;
        xKey[128 - len4] = x2;
        for (int i2 = (128 - len4) - 1; i2 >= 0; i2--) {
            x2 = a[xKey[i2 + len4] ^ x2] & 255;
            xKey[i2] = x2;
        }
        int[] newKey = new int[64];
        for (int i3 = 0; i3 != newKey.length; i3++) {
            newKey[i3] = xKey[i3 * 2] + (xKey[(i3 * 2) + 1] << 8);
        }
        return newKey;
    }

    public void a(boolean encrypting, CipherParameters params) {
        this.c = encrypting;
        if (params instanceof RC2Parameters) {
            RC2Parameters param = (RC2Parameters) params;
            this.b = g(param.a(), param.b());
        } else if (params instanceof KeyParameter) {
            byte[] key = ((KeyParameter) params).a();
            this.b = g(key, key.length * 8);
        } else {
            throw new IllegalArgumentException("invalid parameter passed to RC2 init - " + params.getClass().getName());
        }
    }

    public void reset() {
    }

    public String b() {
        return "RC2";
    }

    public int c() {
        return 8;
    }

    public final int f(byte[] in, int inOff, byte[] out, int outOff) {
        if (this.b == null) {
            throw new IllegalStateException("RC2 engine not initialised");
        } else if (inOff + 8 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + 8 > out.length) {
            throw new OutputLengthException("output buffer too short");
        } else if (this.c) {
            e(in, inOff, out, outOff);
            return 8;
        } else {
            d(in, inOff, out, outOff);
            return 8;
        }
    }

    private int h(int x, int y) {
        int x2 = x & 65535;
        return (x2 << y) | (x2 >> (16 - y));
    }

    private void e(byte[] in, int inOff, byte[] out, int outOff) {
        int x76 = ((in[inOff + 7] & 255) << 8) + (in[inOff + 6] & 255);
        int x54 = ((in[inOff + 5] & 255) << 8) + (in[inOff + 4] & 255);
        int x32 = ((in[inOff + 3] & 255) << 8) + (in[inOff + 2] & 255);
        int x10 = ((in[inOff + 1] & 255) << 8) + (in[inOff + 0] & 255);
        for (int i = 0; i <= 16; i += 4) {
            x10 = h(((~x76) & x32) + x10 + (x54 & x76) + this.b[i], 1);
            x32 = h(((~x10) & x54) + x32 + (x76 & x10) + this.b[i + 1], 2);
            x54 = h(((~x32) & x76) + x54 + (x10 & x32) + this.b[i + 2], 3);
            x76 = h(((~x54) & x10) + x76 + (x32 & x54) + this.b[i + 3], 5);
        }
        int[] iArr = this.b;
        int x102 = x10 + iArr[x76 & 63];
        int x322 = x32 + iArr[x102 & 63];
        int x542 = x54 + iArr[x322 & 63];
        int x762 = x76 + iArr[x542 & 63];
        for (int i2 = 20; i2 <= 40; i2 += 4) {
            x102 = h(((~x762) & x322) + x102 + (x542 & x762) + this.b[i2], 1);
            x322 = h(((~x102) & x542) + x322 + (x762 & x102) + this.b[i2 + 1], 2);
            x542 = h(((~x322) & x762) + x542 + (x102 & x322) + this.b[i2 + 2], 3);
            x762 = h(((~x542) & x102) + x762 + (x322 & x542) + this.b[i2 + 3], 5);
        }
        int[] iArr2 = this.b;
        int x103 = x102 + iArr2[x762 & 63];
        int x323 = x322 + iArr2[x103 & 63];
        int x543 = x542 + iArr2[x323 & 63];
        int x763 = x762 + iArr2[x543 & 63];
        for (int i3 = 44; i3 < 64; i3 += 4) {
            x103 = h(((~x763) & x323) + x103 + (x543 & x763) + this.b[i3], 1);
            x323 = h(((~x103) & x543) + x323 + (x763 & x103) + this.b[i3 + 1], 2);
            x543 = h(((~x323) & x763) + x543 + (x103 & x323) + this.b[i3 + 2], 3);
            x763 = h(((~x543) & x103) + x763 + (x323 & x543) + this.b[i3 + 3], 5);
        }
        out[outOff + 0] = (byte) x103;
        out[outOff + 1] = (byte) (x103 >> 8);
        out[outOff + 2] = (byte) x323;
        out[outOff + 3] = (byte) (x323 >> 8);
        out[outOff + 4] = (byte) x543;
        out[outOff + 5] = (byte) (x543 >> 8);
        out[outOff + 6] = (byte) x763;
        out[outOff + 7] = (byte) (x763 >> 8);
    }

    private void d(byte[] in, int inOff, byte[] out, int outOff) {
        int x76 = ((in[inOff + 7] & 255) << 8) + (in[inOff + 6] & 255);
        int x54 = ((in[inOff + 5] & 255) << 8) + (in[inOff + 4] & 255);
        int x32 = ((in[inOff + 3] & 255) << 8) + (in[inOff + 2] & 255);
        int x10 = ((in[inOff + 1] & 255) << 8) + (in[inOff + 0] & 255);
        for (int i = 60; i >= 44; i -= 4) {
            x76 = h(x76, 11) - ((((~x54) & x10) + (x32 & x54)) + this.b[i + 3]);
            x54 = h(x54, 13) - ((((~x32) & x76) + (x10 & x32)) + this.b[i + 2]);
            x32 = h(x32, 14) - ((((~x10) & x54) + (x76 & x10)) + this.b[i + 1]);
            x10 = h(x10, 15) - ((((~x76) & x32) + (x54 & x76)) + this.b[i]);
        }
        int[] iArr = this.b;
        int x762 = x76 - iArr[x54 & 63];
        int x542 = x54 - iArr[x32 & 63];
        int x322 = x32 - iArr[x10 & 63];
        int x102 = x10 - iArr[x762 & 63];
        for (int i2 = 40; i2 >= 20; i2 -= 4) {
            x762 = h(x762, 11) - ((((~x542) & x102) + (x322 & x542)) + this.b[i2 + 3]);
            x542 = h(x542, 13) - ((((~x322) & x762) + (x102 & x322)) + this.b[i2 + 2]);
            x322 = h(x322, 14) - ((((~x102) & x542) + (x762 & x102)) + this.b[i2 + 1]);
            x102 = h(x102, 15) - ((((~x762) & x322) + (x542 & x762)) + this.b[i2]);
        }
        int[] iArr2 = this.b;
        int x763 = x762 - iArr2[x542 & 63];
        int x543 = x542 - iArr2[x322 & 63];
        int x323 = x322 - iArr2[x102 & 63];
        int x103 = x102 - iArr2[x763 & 63];
        for (int i3 = 16; i3 >= 0; i3 -= 4) {
            x763 = h(x763, 11) - ((((~x543) & x103) + (x323 & x543)) + this.b[i3 + 3]);
            x543 = h(x543, 13) - ((((~x323) & x763) + (x103 & x323)) + this.b[i3 + 2]);
            x323 = h(x323, 14) - ((((~x103) & x543) + (x763 & x103)) + this.b[i3 + 1]);
            x103 = h(x103, 15) - ((((~x763) & x323) + (x543 & x763)) + this.b[i3]);
        }
        out[outOff + 0] = (byte) x103;
        out[outOff + 1] = (byte) (x103 >> 8);
        out[outOff + 2] = (byte) x323;
        out[outOff + 3] = (byte) (x323 >> 8);
        out[outOff + 4] = (byte) x543;
        out[outOff + 5] = (byte) (x543 >> 8);
        out[outOff + 6] = (byte) x763;
        out[outOff + 7] = (byte) (x763 >> 8);
    }
}
