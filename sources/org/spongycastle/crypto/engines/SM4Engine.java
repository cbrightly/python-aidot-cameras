package org.spongycastle.crypto.engines;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.util.Constants;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Pack;

public class SM4Engine implements BlockCipher {
    private static final byte[] a = {-42, -112, -23, -2, -52, -31, 61, -73, 22, -74, 20, -62, 40, -5, Constants.COMMA, 5, 43, 103, -102, 118, 42, -66, 4, -61, -86, 68, 19, 38, 73, -122, 6, -103, -100, 66, 80, -12, -111, -17, -104, 122, 51, 84, 11, 67, -19, -49, -84, 98, -28, -77, 28, -87, -55, 8, -24, -107, OTACommand.RESP_ID_VERSION, -33, -108, -6, 117, -113, 63, -90, 71, 7, -89, -4, -13, 115, 23, -70, OTACommand.RESP_ID_END_OTA, 89, 60, 25, -26, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, 79, -88, 104, 107, OTACommand.RESP_ID_START_OTA, -78, 113, 100, -38, -117, -8, -21, 15, 75, 112, 86, -99, 53, 30, 36, 14, 94, 99, 88, -47, -94, 37, 34, 124, Constants.SEMI_COLON, 1, 33, 120, -121, -44, 0, 70, 87, -97, -45, 39, 82, 76, 54, 2, -25, -96, -60, -56, -98, -22, -65, -118, -46, 64, -57, 56, -75, -93, -9, -14, -50, -7, 97, 21, -95, -32, -82, 93, -92, -101, 52, 26, 85, -83, -109, 50, 48, -11, -116, -79, -29, 29, -10, -30, 46, OTACommand.RESP_ID_SEND_OTA, 102, -54, 96, -64, 41, 35, -85, 13, 83, 78, 111, -43, -37, 55, 69, -34, -3, -114, 47, 3, -1, 106, 114, 109, 108, 91, 81, -115, 27, -81, -110, -69, -35, -68, Byte.MAX_VALUE, 17, -39, 92, 65, 31, MappingData.PATH, 90, -40, 10, -63, 49, -120, -91, -51, 123, -67, 45, 116, -48, 18, -72, -27, -76, -80, -119, 105, -105, 74, 12, -106, 119, 126, 101, -71, -15, 9, -59, 110, -58, OTACommand.RESP_ID_END_OTA_MD5, 24, -16, 125, -20, 58, -36, 77, 32, 121, -18, 95, 62, -41, -53, 57, 72};
    private static final int[] b = {462357, 472066609, 943670861, 1415275113, 1886879365, -1936483679, -1464879427, -993275175, -521670923, -66909679, 404694573, 876298825, 1347903077, 1819507329, -2003855715, -1532251463, -1060647211, -589042959, -117504499, 337322537, 808926789, 1280531041, 1752135293, -2071227751, -1599623499, -1128019247, -656414995, -184876535, 269950501, 741554753, 1213159005, 1684763257};
    private static final int[] c = {-1548633402, 1453994832, 1736282519, -1301273892};
    private final int[] d = new int[4];
    private int[] e;

    private int o(int x, int bits) {
        return (x << bits) | (x >>> (-bits));
    }

    private int p(int A) {
        byte[] bArr = a;
        return ((bArr[(A >> 24) & 255] & 255) << 24) | ((bArr[(A >> 16) & 255] & 255) << 16) | ((bArr[(A >> 8) & 255] & 255) << 8) | (bArr[A & 255] & 255);
    }

    private int j(int B) {
        return (o(B, 13) ^ B) ^ o(B, 23);
    }

    private int m(int Z) {
        return j(p(Z));
    }

    private int[] n(boolean forEncryption, byte[] key) {
        int[] rk = new int[32];
        int[] MK = {Pack.a(key, 0), Pack.a(key, 4), Pack.a(key, 8), Pack.a(key, 12)};
        int i = MK[0];
        int[] iArr = c;
        int[] K = {i ^ iArr[0], MK[1] ^ iArr[1], MK[2] ^ iArr[2], MK[3] ^ iArr[3]};
        if (forEncryption) {
            int i2 = K[0];
            int i3 = (K[1] ^ K[2]) ^ K[3];
            int[] iArr2 = b;
            rk[0] = i2 ^ m(i3 ^ iArr2[0]);
            rk[1] = K[1] ^ m(((K[2] ^ K[3]) ^ rk[0]) ^ iArr2[1]);
            rk[2] = K[2] ^ m(((K[3] ^ rk[0]) ^ rk[1]) ^ iArr2[2]);
            rk[3] = m(((rk[0] ^ rk[1]) ^ rk[2]) ^ iArr2[3]) ^ K[3];
            for (int i4 = 4; i4 < 32; i4++) {
                rk[i4] = rk[i4 - 4] ^ m(((rk[i4 - 3] ^ rk[i4 - 2]) ^ rk[i4 - 1]) ^ b[i4]);
            }
        } else {
            int i5 = K[0];
            int i6 = (K[1] ^ K[2]) ^ K[3];
            int[] iArr3 = b;
            rk[31] = i5 ^ m(iArr3[0] ^ i6);
            rk[30] = K[1] ^ m(((K[2] ^ K[3]) ^ rk[31]) ^ iArr3[1]);
            rk[29] = K[2] ^ m(((K[3] ^ rk[31]) ^ rk[30]) ^ iArr3[2]);
            rk[28] = m(((rk[31] ^ rk[30]) ^ rk[29]) ^ iArr3[3]) ^ K[3];
            for (int i7 = 27; i7 >= 0; i7--) {
                rk[i7] = rk[i7 + 4] ^ m(((rk[i7 + 3] ^ rk[i7 + 2]) ^ rk[i7 + 1]) ^ b[31 - i7]);
            }
        }
        return rk;
    }

    private int i(int B) {
        return (((o(B, 2) ^ B) ^ o(B, 10)) ^ o(B, 18)) ^ o(B, 24);
    }

    private int l(int Z) {
        return i(p(Z));
    }

    private void k(int[] A, int off) {
        int off0 = off;
        int off1 = off + 1;
        int off2 = off + 2;
        int off3 = off + 3;
        A[off0] = A[off0] ^ A[off3];
        A[off3] = A[off0] ^ A[off3];
        A[off0] = A[off0] ^ A[off3];
        A[off1] = A[off1] ^ A[off2];
        A[off2] = A[off1] ^ A[off2];
        A[off1] = A[off1] ^ A[off2];
    }

    private int d(int[] X, int rk) {
        return X[0] ^ l(((X[1] ^ X[2]) ^ X[3]) ^ rk);
    }

    private int e(int[] X, int rk) {
        return X[1] ^ l(((X[2] ^ X[3]) ^ X[0]) ^ rk);
    }

    private int g(int[] X, int rk) {
        return X[2] ^ l(((X[3] ^ X[0]) ^ X[1]) ^ rk);
    }

    private int h(int[] X, int rk) {
        return X[3] ^ l(((X[0] ^ X[1]) ^ X[2]) ^ rk);
    }

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof KeyParameter) {
            byte[] key = ((KeyParameter) params).a();
            if (key.length == 16) {
                this.e = n(forEncryption, key);
                return;
            }
            throw new IllegalArgumentException("SM4 requires a 128 bit key");
        }
        throw new IllegalArgumentException("invalid parameter passed to SM4 init - " + params.getClass().getName());
    }

    public String b() {
        return "SM4";
    }

    public int c() {
        return 16;
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        if (this.e == null) {
            throw new IllegalStateException("SM4 not initialised");
        } else if (inOff + 16 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + 16 <= out.length) {
            this.d[0] = Pack.a(in, inOff);
            this.d[1] = Pack.a(in, inOff + 4);
            this.d[2] = Pack.a(in, inOff + 8);
            this.d[3] = Pack.a(in, inOff + 12);
            for (int i = 0; i < 32; i += 4) {
                int[] iArr = this.d;
                iArr[0] = d(iArr, this.e[i]);
                int[] iArr2 = this.d;
                iArr2[1] = e(iArr2, this.e[i + 1]);
                int[] iArr3 = this.d;
                iArr3[2] = g(iArr3, this.e[i + 2]);
                int[] iArr4 = this.d;
                iArr4[3] = h(iArr4, this.e[i + 3]);
            }
            k(this.d, 0);
            Pack.d(this.d[0], out, outOff);
            Pack.d(this.d[1], out, outOff + 4);
            Pack.d(this.d[2], out, outOff + 8);
            Pack.d(this.d[3], out, outOff + 12);
            return 16;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
    }
}
