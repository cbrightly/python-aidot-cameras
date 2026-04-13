package org.spongycastle.crypto.engines;

import com.alibaba.fastjson.asm.Opcodes;
import com.leedarson.serviceimpl.business.bean.OTACommand;
import java.lang.reflect.Array;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.util.Constants;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public class RijndaelEngine implements BlockCipher {
    private static final byte[] a = {0, 0, 25, 1, 50, 2, 26, -58, 75, -57, 27, 104, 51, -18, -33, 3, 100, 4, -32, 14, 52, -115, OTACommand.RESP_ID_START_OTA, -17, 76, 113, 8, -56, -8, 105, 28, -63, 125, -62, 29, -75, -7, -71, 39, 106, 77, -28, -90, 114, -102, -55, 9, 120, 101, 47, -118, 5, 33, 15, -31, 36, 18, -16, OTACommand.RESP_ID_SEND_OTA, 69, 53, -109, -38, -114, -106, -113, -37, -67, 54, -48, -50, -108, 19, 92, -46, -15, 64, 70, OTACommand.RESP_ID_END_OTA, 56, 102, -35, -3, 48, -65, 6, -117, 98, -77, 37, -30, -104, 34, -120, -111, MappingData.PATH, 126, 110, 72, -61, -93, -74, 30, 66, 58, 107, 40, 84, -6, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, 61, -70, 43, 121, 10, 21, -101, -97, 94, -54, 78, -44, -84, -27, -13, 115, -89, 87, -81, 88, -88, 80, -12, -22, -42, 116, 79, -82, -23, -43, -25, -26, -83, -24, Constants.COMMA, -41, 117, 122, -21, 22, 11, -11, 89, -53, 95, -80, -100, -87, 81, -96, Byte.MAX_VALUE, 12, -10, 111, 23, -60, 73, -20, -40, 67, 31, 45, -92, 118, 123, -73, -52, -69, 62, 90, -5, 96, -79, -122, Constants.SEMI_COLON, 82, -95, 108, -86, 85, 41, -99, -105, -78, -121, -112, 97, -66, -36, -4, -68, -107, -49, -51, 55, 63, 91, -47, 83, 57, OTACommand.RESP_ID_END_OTA_MD5, 60, 65, -94, 109, 71, 20, 42, -98, 93, 86, -14, -45, -85, 68, 17, -110, -39, 35, 32, 46, -119, -76, 124, -72, 38, 119, -103, -29, -91, 103, 74, -19, -34, -59, 49, -2, 24, 13, 99, -116, OTACommand.RESP_ID_VERSION, -64, -9, 112, 7};
    private static final byte[] b = {0, 3, 5, 15, 17, 51, 85, -1, 26, 46, 114, -106, -95, -8, 19, 53, 95, -31, 56, 72, -40, 115, -107, -92, -9, 2, 6, 10, 30, 34, 102, -86, -27, 52, 92, -28, 55, 89, -21, 38, 106, -66, -39, 112, -112, -85, -26, 49, 83, -11, 4, 12, 20, 60, 68, -52, 79, -47, 104, -72, -45, 110, -78, -51, 76, -44, 103, -87, -32, Constants.SEMI_COLON, 77, -41, 98, -90, -15, 8, 24, 40, 120, -120, OTACommand.RESP_ID_END_OTA, -98, -71, -48, 107, -67, -36, Byte.MAX_VALUE, OTACommand.RESP_ID_START_OTA, -104, -77, -50, 73, -37, 118, -102, -75, -60, 87, -7, MappingData.PATH, 48, 80, -16, 11, 29, 39, 105, -69, -42, 97, -93, -2, 25, 43, 125, -121, -110, -83, -20, 47, 113, -109, -82, -23, 32, 96, -96, -5, 22, 58, 78, -46, 109, -73, -62, 93, -25, 50, 86, -6, 21, 63, 65, -61, 94, -30, 61, 71, -55, 64, -64, 91, -19, Constants.COMMA, 116, -100, -65, -38, 117, -97, -70, -43, 100, -84, -17, 42, 126, OTACommand.RESP_ID_SEND_OTA, -99, -68, -33, 122, -114, -119, OTACommand.RESP_ID_VERSION, -101, -74, -63, 88, -24, 35, 101, -81, -22, 37, 111, -79, -56, 67, -59, 84, -4, 31, 33, 99, -91, -12, 7, 9, 27, 45, 119, -103, -80, -53, 70, -54, 69, -49, 74, -34, 121, -117, -122, -111, -88, -29, 62, 66, -58, 81, -13, 14, 18, 54, 90, -18, 41, 123, -115, -116, -113, -118, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, -108, -89, -14, 13, 23, 57, 75, -35, 124, OTACommand.RESP_ID_END_OTA_MD5, -105, -94, -3, 28, 36, 108, -76, -57, 82, -10, 1, 3, 5, 15, 17, 51, 85, -1, 26, 46, 114, -106, -95, -8, 19, 53, 95, -31, 56, 72, -40, 115, -107, -92, -9, 2, 6, 10, 30, 34, 102, -86, -27, 52, 92, -28, 55, 89, -21, 38, 106, -66, -39, 112, -112, -85, -26, 49, 83, -11, 4, 12, 20, 60, 68, -52, 79, -47, 104, -72, -45, 110, -78, -51, 76, -44, 103, -87, -32, Constants.SEMI_COLON, 77, -41, 98, -90, -15, 8, 24, 40, 120, -120, OTACommand.RESP_ID_END_OTA, -98, -71, -48, 107, -67, -36, Byte.MAX_VALUE, OTACommand.RESP_ID_START_OTA, -104, -77, -50, 73, -37, 118, -102, -75, -60, 87, -7, MappingData.PATH, 48, 80, -16, 11, 29, 39, 105, -69, -42, 97, -93, -2, 25, 43, 125, -121, -110, -83, -20, 47, 113, -109, -82, -23, 32, 96, -96, -5, 22, 58, 78, -46, 109, -73, -62, 93, -25, 50, 86, -6, 21, 63, 65, -61, 94, -30, 61, 71, -55, 64, -64, 91, -19, Constants.COMMA, 116, -100, -65, -38, 117, -97, -70, -43, 100, -84, -17, 42, 126, OTACommand.RESP_ID_SEND_OTA, -99, -68, -33, 122, -114, -119, OTACommand.RESP_ID_VERSION, -101, -74, -63, 88, -24, 35, 101, -81, -22, 37, 111, -79, -56, 67, -59, 84, -4, 31, 33, 99, -91, -12, 7, 9, 27, 45, 119, -103, -80, -53, 70, -54, 69, -49, 74, -34, 121, -117, -122, -111, -88, -29, 62, 66, -58, 81, -13, 14, 18, 54, 90, -18, 41, 123, -115, -116, -113, -118, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, -108, -89, -14, 13, 23, 57, 75, -35, 124, OTACommand.RESP_ID_END_OTA_MD5, -105, -94, -3, 28, 36, 108, -76, -57, 82, -10, 1};
    private static final byte[] c = {99, 124, 119, 123, -14, 107, 111, -59, 48, 1, 103, 43, -2, -41, -85, 118, -54, OTACommand.RESP_ID_SEND_OTA, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, -64, -73, -3, -109, 38, 54, 63, -9, -52, 52, -91, -27, -15, 113, -40, 49, 21, 4, -57, 35, -61, 24, -106, 5, -102, 7, 18, OTACommand.RESP_ID_VERSION, -30, -21, 39, -78, 117, 9, OTACommand.RESP_ID_END_OTA, Constants.COMMA, 26, 27, 110, 90, -96, 82, Constants.SEMI_COLON, -42, -77, 41, -29, 47, OTACommand.RESP_ID_END_OTA_MD5, 83, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, 76, 88, -49, -48, -17, -86, -5, 67, 77, 51, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, 69, -7, 2, Byte.MAX_VALUE, 80, 60, -97, -88, 81, -93, 64, -113, -110, -99, 56, -11, -68, -74, -38, 33, MappingData.PATH, -1, -13, -46, -51, 12, 19, -20, 95, -105, 68, 23, -60, -89, 126, 61, 100, 93, 25, 115, 96, OTACommand.RESP_ID_START_OTA, 79, -36, 34, 42, -112, -120, 70, -18, -72, 20, -34, 94, 11, -37, -32, 50, 58, 10, 73, 6, 36, 92, -62, -45, -84, 98, -111, -107, -28, 121, -25, -56, 55, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8, -70, 120, 37, 46, 28, -90, -76, -58, -24, -35, 116, 31, 75, -67, -117, -118, 112, 62, -75, 102, 72, 3, -10, 14, 97, 53, 87, -71, -122, -63, 29, -98, -31, -8, -104, 17, 105, -39, -114, -108, -101, 30, -121, -23, -50, 85, 40, -33, -116, -95, -119, 13, -65, -26, 66, 104, 65, -103, 45, 15, -80, 84, -69, 22};
    private static final byte[] d = {82, 9, 106, -43, 48, 54, -91, 56, -65, 64, -93, -98, OTACommand.RESP_ID_START_OTA, -13, -41, -5, 124, -29, 57, OTACommand.RESP_ID_SEND_OTA, -101, 47, -1, -121, 52, -114, 67, 68, -60, -34, -23, -53, 84, 123, -108, 50, -90, -62, 35, 61, -18, 76, -107, 11, 66, -6, -61, 78, 8, 46, -95, 102, 40, -39, 36, -78, 118, 91, -94, 73, 109, -117, -47, 37, 114, -8, -10, 100, -122, 104, -104, 22, -44, -92, 92, -52, 93, 101, -74, -110, 108, 112, 72, 80, -3, -19, -71, -38, 94, 21, 70, 87, -89, -115, -99, OTACommand.RESP_ID_END_OTA_MD5, -112, -40, -85, 0, -116, -68, -45, 10, -9, -28, 88, 5, -72, -77, 69, 6, -48, Constants.COMMA, 30, -113, -54, 63, 15, 2, -63, -81, -67, 3, 1, 19, -118, 107, 58, -111, 17, 65, 79, 103, -36, -22, -105, -14, -49, -50, -16, -76, -26, 115, -106, -84, 116, 34, -25, -83, 53, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, -30, -7, 55, -24, 28, 117, -33, 110, 71, -15, 26, 113, 29, 41, -59, -119, 111, -73, 98, 14, -86, 24, -66, 27, -4, 86, 62, 75, -58, -46, 121, 32, -102, -37, -64, -2, 120, -51, 90, -12, 31, -35, -88, 51, -120, 7, -57, 49, -79, 18, MappingData.PATH, 89, 39, OTACommand.RESP_ID_VERSION, -20, 95, 96, 81, Byte.MAX_VALUE, -87, 25, -75, 74, 13, 45, -27, 122, -97, -109, -55, -100, -17, -96, -32, Constants.SEMI_COLON, 77, -82, 42, -11, -80, -56, -21, -69, 60, OTACommand.RESP_ID_END_OTA, 83, -103, 97, 23, 43, 4, 126, -70, 119, -42, 38, -31, 105, 20, 99, 85, 33, 12, 125};
    private static final int[] e = {1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, 171, 77, Opcodes.IFNE, 47, 94, 188, 99, Opcodes.IFNULL, Opcodes.DCMPL, 53, 106, 212, 179, 125, 250, 239, 197, 145};
    static byte[][] f = {new byte[]{0, 8, MappingData.PATH, 24}, new byte[]{0, 8, MappingData.PATH, 24}, new byte[]{0, 8, MappingData.PATH, 24}, new byte[]{0, 8, MappingData.PATH, 32}, new byte[]{0, 8, 24, 32}};
    static byte[][] g = {new byte[]{0, 24, MappingData.PATH, 8}, new byte[]{0, 32, 24, MappingData.PATH}, new byte[]{0, 40, 32, 24}, new byte[]{0, 48, 40, 24}, new byte[]{0, 56, 40, 32}};
    private int h;
    private long i;
    private int j;
    private int k;
    private long[][] l;
    private long m;
    private long n;
    private long o;
    private long p;
    private boolean q;
    private byte[] r;
    private byte[] s;

    private byte n(int b2) {
        if (b2 != 0) {
            return b[(a[b2] & 255) + 25];
        }
        return 0;
    }

    private byte o(int b2) {
        if (b2 != 0) {
            return b[(a[b2] & 255) + 1];
        }
        return 0;
    }

    private byte p(int b2) {
        if (b2 >= 0) {
            return b[b2 + 199];
        }
        return 0;
    }

    private byte q(int b2) {
        if (b2 >= 0) {
            return b[b2 + 104];
        }
        return 0;
    }

    private byte r(int b2) {
        if (b2 >= 0) {
            return b[b2 + 238];
        }
        return 0;
    }

    private byte s(int b2) {
        if (b2 >= 0) {
            return b[b2 + 223];
        }
        return 0;
    }

    private void e(long[] rk) {
        this.m ^= rk[0];
        this.n ^= rk[1];
        this.o ^= rk[2];
        this.p ^= rk[3];
    }

    private long u(long r2, int shift) {
        return ((r2 >>> shift) | (r2 << (this.h - shift))) & this.i;
    }

    private void h(byte[] shiftsSC) {
        this.n = u(this.n, shiftsSC[1]);
        this.o = u(this.o, shiftsSC[2]);
        this.p = u(this.p, shiftsSC[3]);
    }

    private long j(long r2, byte[] box) {
        long res = 0;
        for (int j2 = 0; j2 < this.h; j2 += 8) {
            res |= ((long) (box[(int) ((r2 >> j2) & 255)] & 255)) << j2;
        }
        return res;
    }

    private void i(byte[] box) {
        this.m = j(this.m, box);
        this.n = j(this.n, box);
        this.o = j(this.o, box);
        this.p = j(this.p, box);
    }

    private void g() {
        long r0 = 0;
        long r3 = 0;
        long r2 = 0;
        long r1 = 0;
        for (int j2 = 0; j2 < this.h; j2 += 8) {
            int a0 = (int) ((this.m >> j2) & 255);
            int a1 = (int) ((this.n >> j2) & 255);
            int a2 = (int) ((this.o >> j2) & 255);
            long r32 = r3;
            int a3 = (int) ((this.p >> j2) & 255);
            r0 |= ((long) ((((n(a0) ^ o(a1)) ^ a2) ^ a3) & 255)) << j2;
            r1 |= ((long) ((((n(a1) ^ o(a2)) ^ a3) ^ a0) & 255)) << j2;
            r2 |= ((long) ((((n(a2) ^ o(a3)) ^ a0) ^ a1) & 255)) << j2;
            r3 = r32 | (((long) ((((n(a3) ^ o(a0)) ^ a1) ^ a2) & 255)) << j2);
        }
        this.m = r0;
        this.n = r1;
        this.o = r2;
        this.p = r3;
    }

    private void d() {
        long r0 = 0;
        long r3 = 0;
        long r2 = 0;
        long r1 = 0;
        for (int j2 = 0; j2 < this.h; j2 += 8) {
            int a0 = (int) ((this.m >> j2) & 255);
            int a1 = (int) ((this.n >> j2) & 255);
            int a2 = (int) ((this.o >> j2) & 255);
            long r32 = r3;
            int a3 = (int) ((this.p >> j2) & 255);
            byte b2 = -1;
            int a02 = a0 != 0 ? a[a0 & 255] & 255 : -1;
            int a12 = a1 != 0 ? a[a1 & 255] & 255 : -1;
            int a22 = a2 != 0 ? a[a2 & 255] & 255 : -1;
            if (a3 != 0) {
                b2 = a[a3 & 255] & 255;
            }
            int a32 = b2;
            r0 |= ((long) ((((s(a02) ^ q(a12)) ^ r(a22)) ^ p(a32)) & 255)) << j2;
            r1 |= ((long) ((((s(a12) ^ q(a22)) ^ r(a32)) ^ p(a02)) & 255)) << j2;
            r2 |= ((long) ((((s(a22) ^ q(a32)) ^ r(a02)) ^ p(a12)) & 255)) << j2;
            r3 = r32 | (((long) ((((s(a32) ^ q(a02)) ^ r(a12)) ^ p(a22)) & 255)) << j2);
        }
        this.m = r0;
        this.n = r1;
        this.o = r2;
        this.p = r3;
    }

    private long[][] m(byte[] key) {
        int KC;
        byte[] bArr = key;
        int j2 = 0;
        int keyBits = bArr.length * 8;
        byte[][] tk = (byte[][]) Array.newInstance(byte.class, new int[]{4, 64});
        long[][] W = (long[][]) Array.newInstance(long.class, new int[]{15, 4});
        switch (keyBits) {
            case 128:
                KC = 4;
                break;
            case Opcodes.IF_ICMPNE:
                KC = 5;
                break;
            case Opcodes.CHECKCAST:
                KC = 6;
                break;
            case 224:
                KC = 7;
                break;
            case 256:
                KC = 8;
                break;
            default:
                throw new IllegalArgumentException("Key length not 128/160/192/224/256 bits.");
        }
        if (keyBits >= this.k) {
            this.j = KC + 6;
        } else {
            this.j = (this.h / 8) + 6;
        }
        int index = 0;
        int i2 = 0;
        while (i2 < bArr.length) {
            tk[i2 % 4][i2 / 4] = bArr[index];
            i2++;
            index++;
        }
        int t = 0;
        int j3 = 0;
        while (true) {
            if (j3 < KC && t < (this.j + 1) * (this.h / 8)) {
                int i3 = 0;
                for (int i4 = 4; i3 < i4; i4 = 4) {
                    int i5 = this.h;
                    long[] jArr = W[t / (i5 / 8)];
                    int i6 = i3;
                    jArr[i6] = (((long) (tk[i3][j3] & 255)) << ((t * 8) % i5)) | jArr[i3];
                    i3 = i6 + 1;
                }
                int i7 = i3;
                j3++;
                t++;
            }
        }
        while (t < (this.j + 1) * (this.h / 8)) {
            for (int i8 = 0; i8 < 4; i8++) {
                byte[] bArr2 = tk[i8];
                bArr2[0] = (byte) (c[tk[(i8 + 1) % 4][KC - 1] & 255] ^ bArr2[0]);
            }
            byte[] bArr3 = tk[0];
            int rconpointer = j2 + 1;
            bArr3[0] = (byte) (e[j2] ^ bArr3[0]);
            if (KC <= 6) {
                for (int j4 = 1; j4 < KC; j4++) {
                    for (int i9 = 0; i9 < 4; i9++) {
                        byte[] bArr4 = tk[i9];
                        bArr4[j4] = (byte) (bArr4[j4] ^ tk[i9][j4 - 1]);
                    }
                }
            } else {
                int j5 = 1;
                while (true) {
                    if (j5 < 4) {
                        int i10 = 0;
                        for (int i11 = 4; i10 < i11; i11 = 4) {
                            byte[] bArr5 = tk[i10];
                            bArr5[j5] = (byte) (bArr5[j5] ^ tk[i10][j5 - 1]);
                            i10++;
                        }
                        j5++;
                    } else {
                        for (int i12 = 0; i12 < 4; i12++) {
                            byte[] bArr6 = tk[i12];
                            bArr6[4] = (byte) (bArr6[4] ^ c[tk[i12][3] & 255]);
                        }
                        for (int j6 = 5; j6 < KC; j6++) {
                            for (int i13 = 0; i13 < 4; i13++) {
                                byte[] bArr7 = tk[i13];
                                bArr7[j6] = (byte) (bArr7[j6] ^ tk[i13][j6 - 1]);
                            }
                        }
                    }
                }
            }
            int j7 = 0;
            while (j7 < KC && t < (this.j + 1) * (this.h / 8)) {
                for (int i14 = 0; i14 < 4; i14++) {
                    int i15 = this.h;
                    long[] jArr2 = W[t / (i15 / 8)];
                    jArr2[i14] = (((long) (tk[i14][j7] & 255)) << ((t * 8) % i15)) | jArr2[i14];
                }
                j7++;
                t++;
            }
            j2 = rconpointer;
        }
        return W;
    }

    public RijndaelEngine() {
        this(128);
    }

    public RijndaelEngine(int blockBits) {
        switch (blockBits) {
            case 128:
                this.h = 32;
                this.i = 4294967295L;
                this.r = f[0];
                this.s = g[0];
                break;
            case Opcodes.IF_ICMPNE:
                this.h = 40;
                this.i = 1099511627775L;
                this.r = f[1];
                this.s = g[1];
                break;
            case Opcodes.CHECKCAST:
                this.h = 48;
                this.i = 281474976710655L;
                this.r = f[2];
                this.s = g[2];
                break;
            case 224:
                this.h = 56;
                this.i = 72057594037927935L;
                this.r = f[3];
                this.s = g[3];
                break;
            case 256:
                this.h = 64;
                this.i = -1;
                this.r = f[4];
                this.s = g[4];
                break;
            default:
                throw new IllegalArgumentException("unknown blocksize to Rijndael");
        }
        this.k = blockBits;
    }

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof KeyParameter) {
            this.l = m(((KeyParameter) params).a());
            this.q = forEncryption;
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to Rijndael init - " + params.getClass().getName());
    }

    public String b() {
        return "Rijndael";
    }

    public int c() {
        return this.h / 2;
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        if (this.l != null) {
            int i2 = this.h;
            if ((i2 / 2) + inOff > in.length) {
                throw new DataLengthException("input buffer too short");
            } else if ((i2 / 2) + outOff <= out.length) {
                if (this.q) {
                    v(in, inOff);
                    l(this.l);
                    t(out, outOff);
                } else {
                    v(in, inOff);
                    k(this.l);
                    t(out, outOff);
                }
                return this.h / 2;
            } else {
                throw new OutputLengthException("output buffer too short");
            }
        } else {
            throw new IllegalStateException("Rijndael engine not initialised");
        }
    }

    public void reset() {
    }

    private void v(byte[] bytes, int off) {
        int index = off;
        int index2 = index + 1;
        this.m = (long) (bytes[index] & 255);
        int index3 = index2 + 1;
        this.n = (long) (bytes[index2] & 255);
        int index4 = index3 + 1;
        this.o = (long) (bytes[index3] & 255);
        int index5 = index4 + 1;
        this.p = (long) (bytes[index4] & 255);
        for (int j2 = 8; j2 != this.h; j2 += 8) {
            int index6 = index5 + 1;
            this.m |= ((long) (bytes[index5] & 255)) << j2;
            int index7 = index6 + 1;
            this.n |= ((long) (bytes[index6] & 255)) << j2;
            int index8 = index7 + 1;
            this.o |= ((long) (bytes[index7] & 255)) << j2;
            index5 = index8 + 1;
            this.p |= ((long) (bytes[index8] & 255)) << j2;
        }
    }

    private void t(byte[] bytes, int off) {
        int index = off;
        for (int j2 = 0; j2 != this.h; j2 += 8) {
            int index2 = index + 1;
            bytes[index] = (byte) ((int) (this.m >> j2));
            int index3 = index2 + 1;
            bytes[index2] = (byte) ((int) (this.n >> j2));
            int index4 = index3 + 1;
            bytes[index3] = (byte) ((int) (this.o >> j2));
            index = index4 + 1;
            bytes[index4] = (byte) ((int) (this.p >> j2));
        }
    }

    private void l(long[][] rk) {
        e(rk[0]);
        for (int r2 = 1; r2 < this.j; r2++) {
            i(c);
            h(this.r);
            g();
            e(rk[r2]);
        }
        i(c);
        h(this.r);
        e(rk[this.j]);
    }

    private void k(long[][] rk) {
        e(rk[this.j]);
        i(d);
        h(this.s);
        for (int r2 = this.j - 1; r2 > 0; r2--) {
            e(rk[r2]);
            d();
            i(d);
            h(this.s);
        }
        e(rk[0]);
    }
}
