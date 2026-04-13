package org.spongycastle.crypto.engines;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import java.lang.reflect.Array;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.util.Constants;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.encoders.Hex;

public class ARIAEngine implements BlockCipher {
    private static final byte[][] a = {Hex.a("517cc1b727220a94fe13abe8fa9a6ee0"), Hex.a("6db14acc9e21c820ff28b1d5ef5de2b0"), Hex.a("db92371d2126e9700324977504e8c90e")};
    private static final byte[] b = {99, 124, 119, 123, -14, 107, 111, -59, 48, 1, 103, 43, -2, -41, -85, 118, -54, OTACommand.RESP_ID_SEND_OTA, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, -64, -73, -3, -109, 38, 54, 63, -9, -52, 52, -91, -27, -15, 113, -40, 49, 21, 4, -57, 35, -61, 24, -106, 5, -102, 7, 18, OTACommand.RESP_ID_VERSION, -30, -21, 39, -78, 117, 9, OTACommand.RESP_ID_END_OTA, Constants.COMMA, 26, 27, 110, 90, -96, 82, Constants.SEMI_COLON, -42, -77, 41, -29, 47, OTACommand.RESP_ID_END_OTA_MD5, 83, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, 76, 88, -49, -48, -17, -86, -5, 67, 77, 51, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, 69, -7, 2, Byte.MAX_VALUE, 80, 60, -97, -88, 81, -93, 64, -113, -110, -99, 56, -11, -68, -74, -38, 33, MappingData.PATH, -1, -13, -46, -51, 12, 19, -20, 95, -105, 68, 23, -60, -89, 126, 61, 100, 93, 25, 115, 96, OTACommand.RESP_ID_START_OTA, 79, -36, 34, 42, -112, -120, 70, -18, -72, 20, -34, 94, 11, -37, -32, 50, 58, 10, 73, 6, 36, 92, -62, -45, -84, 98, -111, -107, -28, 121, -25, -56, 55, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8, -70, 120, 37, 46, 28, -90, -76, -58, -24, -35, 116, 31, 75, -67, -117, -118, 112, 62, -75, 102, 72, 3, -10, 14, 97, 53, 87, -71, -122, -63, 29, -98, -31, -8, -104, 17, 105, -39, -114, -108, -101, 30, -121, -23, -50, 85, 40, -33, -116, -95, -119, 13, -65, -26, 66, 104, 65, -103, 45, 15, -80, 84, -69, 22};
    private static final byte[] c = {-30, 78, 84, -4, -108, -62, 74, -52, 98, 13, 106, 70, 60, 77, -117, -47, 94, -6, 100, -53, -76, -105, -66, 43, -68, 119, 46, 3, -45, 25, 89, -63, 29, 6, 65, 107, 85, -16, -103, 105, -22, -100, 24, -82, 99, -33, -25, -69, 0, 115, 102, -5, -106, 76, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, -28, 58, 9, 69, -86, 15, -18, MappingData.PATH, -21, 45, Byte.MAX_VALUE, -12, 41, -84, -49, -83, -111, -115, 120, -56, -107, -7, 47, -50, -51, 8, 122, -120, 56, 92, OTACommand.RESP_ID_END_OTA, 42, 40, 71, -37, -72, -57, -109, -92, 18, 83, -1, -121, 14, 49, 54, 33, 88, 72, 1, -114, 55, 116, 50, -54, -23, -79, -73, -85, 12, -41, -60, 86, 66, 38, 7, -104, 96, -39, -74, -71, 17, 64, -20, 32, -116, -67, -96, -55, OTACommand.RESP_ID_END_OTA_MD5, 4, 73, 35, -15, 79, 80, 31, 19, -36, -40, -64, -98, 87, -29, -61, 123, 101, Constants.SEMI_COLON, 2, -113, 62, -24, 37, -110, -27, 21, -35, -3, 23, -87, -65, -44, -102, 126, -59, 57, 103, -2, 118, -99, 67, -89, -31, -48, -11, 104, -14, 27, 52, 112, 5, -93, -118, -43, 121, -122, -88, 48, -58, 81, 75, 30, -90, 39, -10, 53, -46, 110, 36, 22, OTACommand.RESP_ID_SEND_OTA, 95, -38, -26, 117, -94, -17, Constants.COMMA, -78, 28, -97, 93, 111, OTACommand.RESP_ID_VERSION, 10, 114, 68, -101, 108, -112, 11, 91, 51, 125, 90, 82, -13, 97, -95, -9, -80, -42, 63, 124, 109, -19, 20, -32, -91, 61, 34, -77, -8, -119, -34, 113, 26, -81, -70, -75, OTACommand.RESP_ID_START_OTA};
    private static final byte[] d = {82, 9, 106, -43, 48, 54, -91, 56, -65, 64, -93, -98, OTACommand.RESP_ID_START_OTA, -13, -41, -5, 124, -29, 57, OTACommand.RESP_ID_SEND_OTA, -101, 47, -1, -121, 52, -114, 67, 68, -60, -34, -23, -53, 84, 123, -108, 50, -90, -62, 35, 61, -18, 76, -107, 11, 66, -6, -61, 78, 8, 46, -95, 102, 40, -39, 36, -78, 118, 91, -94, 73, 109, -117, -47, 37, 114, -8, -10, 100, -122, 104, -104, 22, -44, -92, 92, -52, 93, 101, -74, -110, 108, 112, 72, 80, -3, -19, -71, -38, 94, 21, 70, 87, -89, -115, -99, OTACommand.RESP_ID_END_OTA_MD5, -112, -40, -85, 0, -116, -68, -45, 10, -9, -28, 88, 5, -72, -77, 69, 6, -48, Constants.COMMA, 30, -113, -54, 63, 15, 2, -63, -81, -67, 3, 1, 19, -118, 107, 58, -111, 17, 65, 79, 103, -36, -22, -105, -14, -49, -50, -16, -76, -26, 115, -106, -84, 116, 34, -25, -83, 53, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, -30, -7, 55, -24, 28, 117, -33, 110, 71, -15, 26, 113, 29, 41, -59, -119, 111, -73, 98, 14, -86, 24, -66, 27, -4, 86, 62, 75, -58, -46, 121, 32, -102, -37, -64, -2, 120, -51, 90, -12, 31, -35, -88, 51, -120, 7, -57, 49, -79, 18, MappingData.PATH, 89, 39, OTACommand.RESP_ID_VERSION, -20, 95, 96, 81, Byte.MAX_VALUE, -87, 25, -75, 74, 13, 45, -27, 122, -97, -109, -55, -100, -17, -96, -32, Constants.SEMI_COLON, 77, -82, 42, -11, -80, -56, -21, -69, 60, OTACommand.RESP_ID_END_OTA, 83, -103, 97, 23, 43, 4, 126, -70, 119, -42, 38, -31, 105, 20, 99, 85, 33, 12, 125};
    private static final byte[] e = {48, 104, -103, 27, -121, -71, 33, 120, 80, 57, -37, -31, 114, 9, 98, 60, 62, 126, 94, -114, -15, -96, -52, -93, 42, 29, -5, -74, -42, 32, -60, -115, OTACommand.RESP_ID_START_OTA, 101, -11, -119, -53, -99, 119, -58, 87, 67, 86, 23, -44, 64, 26, 77, -64, 99, 108, -29, -73, -56, 100, 106, 83, -86, 56, -104, 12, -12, -101, -19, Byte.MAX_VALUE, 34, 118, -81, -35, 58, 11, 88, 103, -120, 6, -61, 53, 13, 1, -117, -116, -62, -26, 95, 2, 36, 117, -109, 102, 30, -27, -30, 84, -40, MappingData.PATH, -50, 122, -24, 8, Constants.COMMA, 18, -105, 50, -85, -76, 39, 10, 35, -33, -17, -54, -39, -72, -6, -36, 49, 107, -47, -83, 25, 73, -67, 81, -106, -18, -28, -88, 65, -38, -1, -51, 85, -122, 54, -66, 97, 82, -8, -69, 14, OTACommand.RESP_ID_SEND_OTA, 72, 105, -102, -32, 71, -98, 92, 4, 75, 52, 21, 121, 38, -89, -34, 41, -82, -110, -41, OTACommand.RESP_ID_END_OTA_MD5, -23, -46, -70, 93, -13, -59, -80, -65, -92, Constants.SEMI_COLON, 113, 68, 70, 43, -4, -21, 111, -43, -10, 20, -2, 124, 112, 90, 125, -3, 47, 24, OTACommand.RESP_ID_END_OTA, 22, -91, -111, 31, 5, -107, 116, -87, -63, 91, 74, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, 109, 19, 7, 79, 78, 69, -78, 15, -55, 28, -90, -68, -20, 115, -112, 123, -49, 89, -113, -95, -7, 45, -14, -79, 0, -108, 55, -97, -48, 46, -100, 110, 40, 63, OTACommand.RESP_ID_VERSION, -16, 61, -45, 37, -118, -75, -25, 66, -77, -57, -22, -9, 76, 17, 51, 3, -94, -84, 96};
    private byte[][] f;

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof KeyParameter) {
            this.f = n(forEncryption, ((KeyParameter) params).a());
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to ARIA init - " + params.getClass().getName());
    }

    public String b() {
        return "ARIA";
    }

    public int c() {
        return 16;
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        if (this.f == null) {
            throw new IllegalStateException("ARIA engine not initialised");
        } else if (inOff > in.length - 16) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff <= out.length - 16) {
            byte[] z = new byte[16];
            System.arraycopy(in, inOff, z, 0, 16);
            int i = 0;
            int rounds = this.f.length - 3;
            while (i < rounds) {
                int i2 = i + 1;
                g(z, this.f[i]);
                e(z, this.f[i2]);
                i = i2 + 1;
            }
            int i3 = i + 1;
            g(z, this.f[i]);
            q(z, this.f[i3]);
            m(z);
            q(z, this.f[i3 + 1]);
            System.arraycopy(z, 0, out, outOff, 16);
            return 16;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
    }

    protected static void d(byte[] z) {
        byte x0 = z[0];
        byte x1 = z[1];
        byte x2 = z[2];
        byte x3 = z[3];
        byte x4 = z[4];
        byte x5 = z[5];
        byte x6 = z[6];
        byte x7 = z[7];
        byte x8 = z[8];
        byte x9 = z[9];
        byte x10 = z[10];
        byte x11 = z[11];
        byte x12 = z[12];
        byte x13 = z[13];
        byte x14 = z[14];
        byte x15 = z[15];
        z[0] = (byte) ((((((x3 ^ x4) ^ x6) ^ x8) ^ x9) ^ x13) ^ x14);
        z[1] = (byte) ((((((x2 ^ x5) ^ x7) ^ x8) ^ x9) ^ x12) ^ x15);
        z[2] = (byte) ((((((x1 ^ x4) ^ x6) ^ x10) ^ x11) ^ x12) ^ x15);
        z[3] = (byte) ((((((x0 ^ x5) ^ x7) ^ x10) ^ x11) ^ x13) ^ x14);
        z[4] = (byte) ((((((x0 ^ x2) ^ x5) ^ x8) ^ x11) ^ x14) ^ x15);
        z[5] = (byte) ((((((x1 ^ x3) ^ x4) ^ x9) ^ x10) ^ x14) ^ x15);
        z[6] = (byte) ((((((x0 ^ x2) ^ x7) ^ x9) ^ x10) ^ x12) ^ x13);
        z[7] = (byte) ((((((x1 ^ x3) ^ x6) ^ x8) ^ x11) ^ x12) ^ x13);
        z[8] = (byte) ((((((x0 ^ x1) ^ x4) ^ x7) ^ x10) ^ x13) ^ x15);
        z[9] = (byte) ((((((x0 ^ x1) ^ x5) ^ x6) ^ x11) ^ x12) ^ x14);
        z[10] = (byte) ((((((x2 ^ x3) ^ x5) ^ x6) ^ x8) ^ x13) ^ x15);
        z[11] = (byte) ((((((x2 ^ x3) ^ x4) ^ x7) ^ x9) ^ x12) ^ x14);
        z[12] = (byte) ((((((x1 ^ x2) ^ x6) ^ x7) ^ x9) ^ x11) ^ x12);
        z[13] = (byte) ((((((x0 ^ x3) ^ x6) ^ x7) ^ x8) ^ x10) ^ x13);
        z[14] = (byte) ((((((x0 ^ x3) ^ x4) ^ x5) ^ x9) ^ x11) ^ x14);
        z[15] = (byte) ((((((x1 ^ x2) ^ x4) ^ x5) ^ x8) ^ x10) ^ x15);
    }

    protected static void e(byte[] D, byte[] RK) {
        q(D, RK);
        m(D);
        d(D);
    }

    protected static void g(byte[] D, byte[] RK) {
        q(D, RK);
        l(D);
        d(D);
    }

    protected static byte[][] n(boolean forEncryption, byte[] K) {
        byte[] bArr = K;
        int keyLen = bArr.length;
        if (keyLen < 16 || keyLen > 32 || (keyLen & 7) != 0) {
            throw new IllegalArgumentException("Key length not 128/192/256 bits.");
        }
        int keyLenIdx = (keyLen >>> 3) - 2;
        byte[][] bArr2 = a;
        byte[] CK1 = bArr2[keyLenIdx];
        byte[] CK2 = bArr2[(keyLenIdx + 1) % 3];
        byte[] CK3 = bArr2[(keyLenIdx + 2) % 3];
        byte[] KL = new byte[16];
        byte[] KR = new byte[16];
        System.arraycopy(bArr, 0, KL, 0, 16);
        System.arraycopy(bArr, 16, KR, 0, keyLen - 16);
        byte[] W0 = new byte[16];
        byte[] W1 = new byte[16];
        byte[] W2 = new byte[16];
        byte[] W3 = new byte[16];
        System.arraycopy(KL, 0, W0, 0, 16);
        System.arraycopy(W0, 0, W1, 0, 16);
        g(W1, CK1);
        q(W1, KR);
        System.arraycopy(W1, 0, W2, 0, 16);
        e(W2, CK2);
        q(W2, W0);
        System.arraycopy(W2, 0, W3, 0, 16);
        g(W3, CK3);
        q(W3, W1);
        int numRounds = (keyLenIdx * 2) + 12;
        int[] iArr = new int[2];
        iArr[1] = 16;
        iArr[0] = numRounds + 1;
        byte[][] rks = (byte[][]) Array.newInstance(byte.class, iArr);
        o(rks[0], W0, W1, 19);
        o(rks[1], W1, W2, 19);
        o(rks[2], W2, W3, 19);
        o(rks[3], W3, W0, 19);
        o(rks[4], W0, W1, 31);
        o(rks[5], W1, W2, 31);
        o(rks[6], W2, W3, 31);
        o(rks[7], W3, W0, 31);
        o(rks[8], W0, W1, 67);
        o(rks[9], W1, W2, 67);
        o(rks[10], W2, W3, 67);
        o(rks[11], W3, W0, 67);
        o(rks[12], W0, W1, 97);
        if (numRounds > 12) {
            o(rks[13], W1, W2, 97);
            o(rks[14], W2, W3, 97);
            if (numRounds > 14) {
                o(rks[15], W3, W0, 97);
                o(rks[16], W0, W1, 109);
            }
        }
        if (!forEncryption) {
            p(rks);
            for (int i = 1; i < numRounds; i++) {
                d(rks[i]);
            }
        }
        return rks;
    }

    protected static void o(byte[] rk, byte[] w, byte[] wr, int n) {
        int off = n >>> 3;
        int right = n & 7;
        int left = 8 - right;
        int hi = wr[15 - off] & 255;
        for (int to = 0; to < 16; to++) {
            int lo = wr[(to - off) & 15] & 255;
            rk[to] = (byte) (((hi << left) | (lo >>> right)) ^ (w[to] & 255));
            hi = lo;
        }
    }

    protected static void p(byte[][] keys) {
        int length = keys.length;
        int limit = length / 2;
        int last = length - 1;
        for (int i = 0; i < limit; i++) {
            byte[] t = keys[i];
            keys[i] = keys[last - i];
            keys[last - i] = t;
        }
    }

    protected static byte h(byte x) {
        return b[x & 255];
    }

    protected static byte i(byte x) {
        return c[x & 255];
    }

    protected static byte j(byte x) {
        return d[x & 255];
    }

    protected static byte k(byte x) {
        return e[x & 255];
    }

    protected static void l(byte[] z) {
        z[0] = h(z[0]);
        z[1] = i(z[1]);
        z[2] = j(z[2]);
        z[3] = k(z[3]);
        z[4] = h(z[4]);
        z[5] = i(z[5]);
        z[6] = j(z[6]);
        z[7] = k(z[7]);
        z[8] = h(z[8]);
        z[9] = i(z[9]);
        z[10] = j(z[10]);
        z[11] = k(z[11]);
        z[12] = h(z[12]);
        z[13] = i(z[13]);
        z[14] = j(z[14]);
        z[15] = k(z[15]);
    }

    protected static void m(byte[] z) {
        z[0] = j(z[0]);
        z[1] = k(z[1]);
        z[2] = h(z[2]);
        z[3] = i(z[3]);
        z[4] = j(z[4]);
        z[5] = k(z[5]);
        z[6] = h(z[6]);
        z[7] = i(z[7]);
        z[8] = j(z[8]);
        z[9] = k(z[9]);
        z[10] = h(z[10]);
        z[11] = i(z[11]);
        z[12] = j(z[12]);
        z[13] = k(z[13]);
        z[14] = h(z[14]);
        z[15] = i(z[15]);
    }

    protected static void q(byte[] z, byte[] x) {
        for (int i = 0; i < 16; i++) {
            z[i] = (byte) (z[i] ^ x[i]);
        }
    }
}
