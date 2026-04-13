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
import org.spongycastle.util.Pack;

public class AESLightEngine implements BlockCipher {
    private static final byte[] a = {99, 124, 119, 123, -14, 107, 111, -59, 48, 1, 103, 43, -2, -41, -85, 118, -54, OTACommand.RESP_ID_SEND_OTA, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, -64, -73, -3, -109, 38, 54, 63, -9, -52, 52, -91, -27, -15, 113, -40, 49, 21, 4, -57, 35, -61, 24, -106, 5, -102, 7, 18, OTACommand.RESP_ID_VERSION, -30, -21, 39, -78, 117, 9, OTACommand.RESP_ID_END_OTA, Constants.COMMA, 26, 27, 110, 90, -96, 82, Constants.SEMI_COLON, -42, -77, 41, -29, 47, OTACommand.RESP_ID_END_OTA_MD5, 83, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, 76, 88, -49, -48, -17, -86, -5, 67, 77, 51, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, 69, -7, 2, Byte.MAX_VALUE, 80, 60, -97, -88, 81, -93, 64, -113, -110, -99, 56, -11, -68, -74, -38, 33, MappingData.PATH, -1, -13, -46, -51, 12, 19, -20, 95, -105, 68, 23, -60, -89, 126, 61, 100, 93, 25, 115, 96, OTACommand.RESP_ID_START_OTA, 79, -36, 34, 42, -112, -120, 70, -18, -72, 20, -34, 94, 11, -37, -32, 50, 58, 10, 73, 6, 36, 92, -62, -45, -84, 98, -111, -107, -28, 121, -25, -56, 55, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8, -70, 120, 37, 46, 28, -90, -76, -58, -24, -35, 116, 31, 75, -67, -117, -118, 112, 62, -75, 102, 72, 3, -10, 14, 97, 53, 87, -71, -122, -63, 29, -98, -31, -8, -104, 17, 105, -39, -114, -108, -101, 30, -121, -23, -50, 85, 40, -33, -116, -95, -119, 13, -65, -26, 66, 104, 65, -103, 45, 15, -80, 84, -69, 22};
    private static final byte[] b = {82, 9, 106, -43, 48, 54, -91, 56, -65, 64, -93, -98, OTACommand.RESP_ID_START_OTA, -13, -41, -5, 124, -29, 57, OTACommand.RESP_ID_SEND_OTA, -101, 47, -1, -121, 52, -114, 67, 68, -60, -34, -23, -53, 84, 123, -108, 50, -90, -62, 35, 61, -18, 76, -107, 11, 66, -6, -61, 78, 8, 46, -95, 102, 40, -39, 36, -78, 118, 91, -94, 73, 109, -117, -47, 37, 114, -8, -10, 100, -122, 104, -104, 22, -44, -92, 92, -52, 93, 101, -74, -110, 108, 112, 72, 80, -3, -19, -71, -38, 94, 21, 70, 87, -89, -115, -99, OTACommand.RESP_ID_END_OTA_MD5, -112, -40, -85, 0, -116, -68, -45, 10, -9, -28, 88, 5, -72, -77, 69, 6, -48, Constants.COMMA, 30, -113, -54, 63, 15, 2, -63, -81, -67, 3, 1, 19, -118, 107, 58, -111, 17, 65, 79, 103, -36, -22, -105, -14, -49, -50, -16, -76, -26, 115, -106, -84, 116, 34, -25, -83, 53, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, -30, -7, 55, -24, 28, 117, -33, 110, 71, -15, 26, 113, 29, 41, -59, -119, 111, -73, 98, 14, -86, 24, -66, 27, -4, 86, 62, 75, -58, -46, 121, 32, -102, -37, -64, -2, 120, -51, 90, -12, 31, -35, -88, 51, -120, 7, -57, 49, -79, 18, MappingData.PATH, 89, 39, OTACommand.RESP_ID_VERSION, -20, 95, 96, 81, Byte.MAX_VALUE, -87, 25, -75, 74, 13, 45, -27, 122, -97, -109, -55, -100, -17, -96, -32, Constants.SEMI_COLON, 77, -82, 42, -11, -80, -56, -21, -69, 60, OTACommand.RESP_ID_END_OTA, 83, -103, 97, 23, 43, 4, 126, -70, 119, -42, 38, -31, 105, 20, 99, 85, 33, 12, 125};
    private static final int[] c = {1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, 171, 77, Opcodes.IFNE, 47, 94, 188, 99, Opcodes.IFNULL, Opcodes.DCMPL, 53, 106, 212, 179, 125, 250, 239, 197, 145};
    private int d;
    private int[][] e = null;
    private int f;
    private int g;
    private int h;
    private int i;
    private boolean j;

    private static int m(int r, int shift) {
        return (r >>> shift) | (r << (-shift));
    }

    private static int d(int x) {
        return ((2139062143 & x) << 1) ^ (((-2139062144 & x) >>> 7) * 27);
    }

    private static int e(int x) {
        int t1 = -1061109568 & x;
        int t12 = t1 ^ (t1 >>> 1);
        return ((t12 >>> 2) ^ ((1061109567 & x) << 2)) ^ (t12 >>> 5);
    }

    private static int k(int x) {
        int t0 = m(x, 8);
        int t1 = x ^ t0;
        return (m(t1, 16) ^ t0) ^ d(t1);
    }

    private static int j(int x) {
        int t0 = x;
        int t1 = m(t0, 8) ^ t0;
        int t02 = t0 ^ d(t1);
        int t12 = t1 ^ e(t02);
        return t02 ^ (m(t12, 16) ^ t12);
    }

    private static int n(int x) {
        byte[] bArr = a;
        return (bArr[(x >> 24) & 255] << 24) | (bArr[x & 255] & 255) | ((bArr[(x >> 8) & 255] & 255) << 8) | ((bArr[(x >> 16) & 255] & 255) << MappingData.PATH);
    }

    private int[][] i(byte[] key, boolean forEncryption) {
        byte[] bArr = key;
        int keyLen = bArr.length;
        if (keyLen < 16 || keyLen > 32 || (keyLen & 7) != 0) {
            throw new IllegalArgumentException("Key length not 128/192/256 bits.");
        }
        int KC = keyLen >> 2;
        int i2 = KC + 6;
        this.d = i2;
        int[] iArr = new int[2];
        iArr[1] = 4;
        iArr[0] = i2 + 1;
        int[][] W = (int[][]) Array.newInstance(int.class, iArr);
        switch (KC) {
            case 4:
                int t0 = Pack.j(bArr, 0);
                W[0][0] = t0;
                int t1 = Pack.j(bArr, 4);
                W[0][1] = t1;
                int t2 = Pack.j(bArr, 8);
                W[0][2] = t2;
                int t3 = Pack.j(bArr, 12);
                W[0][3] = t3;
                for (int i3 = 1; i3 <= 10; i3++) {
                    t0 ^= n(m(t3, 8)) ^ c[i3 - 1];
                    W[i3][0] = t0;
                    t1 ^= t0;
                    W[i3][1] = t1;
                    t2 ^= t1;
                    W[i3][2] = t2;
                    t3 ^= t2;
                    W[i3][3] = t3;
                }
                break;
            case 6:
                int t02 = Pack.j(bArr, 0);
                W[0][0] = t02;
                int t12 = Pack.j(bArr, 4);
                W[0][1] = t12;
                int t22 = Pack.j(bArr, 8);
                W[0][2] = t22;
                int t32 = Pack.j(bArr, 12);
                W[0][3] = t32;
                int t4 = Pack.j(bArr, 16);
                W[1][0] = t4;
                int t5 = Pack.j(bArr, 20);
                W[1][1] = t5;
                int rcon = 1 << 1;
                int t03 = t02 ^ (n(m(t5, 8)) ^ 1);
                W[1][2] = t03;
                int t13 = t12 ^ t03;
                W[1][3] = t13;
                int t23 = t22 ^ t13;
                W[2][0] = t23;
                int t33 = t32 ^ t23;
                W[2][1] = t33;
                int t42 = t4 ^ t33;
                W[2][2] = t42;
                int t52 = t5 ^ t42;
                W[2][3] = t52;
                for (int i4 = 3; i4 < 12; i4 += 3) {
                    int u = n(m(t52, 8)) ^ rcon;
                    int rcon2 = rcon << 1;
                    int t04 = t03 ^ u;
                    W[i4][0] = t04;
                    int t14 = t13 ^ t04;
                    W[i4][1] = t14;
                    int t24 = t23 ^ t14;
                    W[i4][2] = t24;
                    int t34 = t33 ^ t24;
                    W[i4][3] = t34;
                    int t43 = t42 ^ t34;
                    W[i4 + 1][0] = t43;
                    int t53 = t52 ^ t43;
                    W[i4 + 1][1] = t53;
                    int u2 = n(m(t53, 8)) ^ rcon2;
                    rcon = rcon2 << 1;
                    t03 = t04 ^ u2;
                    W[i4 + 1][2] = t03;
                    t13 = t14 ^ t03;
                    W[i4 + 1][3] = t13;
                    t23 = t24 ^ t13;
                    W[i4 + 2][0] = t23;
                    t33 = t34 ^ t23;
                    W[i4 + 2][1] = t33;
                    t42 = t43 ^ t33;
                    W[i4 + 2][2] = t42;
                    t52 = t53 ^ t42;
                    W[i4 + 2][3] = t52;
                }
                int t05 = t03 ^ (n(m(t52, 8)) ^ rcon);
                W[12][0] = t05;
                int t15 = t13 ^ t05;
                W[12][1] = t15;
                int t25 = t23 ^ t15;
                W[12][2] = t25;
                W[12][3] = t33 ^ t25;
                break;
            case 8:
                int t06 = Pack.j(bArr, 0);
                W[0][0] = t06;
                int t16 = Pack.j(bArr, 4);
                W[0][1] = t16;
                int t26 = Pack.j(bArr, 8);
                W[0][2] = t26;
                int t35 = Pack.j(bArr, 12);
                W[0][3] = t35;
                int t44 = Pack.j(bArr, 16);
                W[1][0] = t44;
                int t54 = Pack.j(bArr, 20);
                W[1][1] = t54;
                int t6 = Pack.j(bArr, 24);
                W[1][2] = t6;
                int t7 = Pack.j(bArr, 28);
                W[1][3] = t7;
                int rcon3 = 1;
                for (int i5 = 2; i5 < 14; i5 += 2) {
                    int u3 = n(m(t7, 8)) ^ rcon3;
                    rcon3 <<= 1;
                    t06 ^= u3;
                    W[i5][0] = t06;
                    t16 ^= t06;
                    W[i5][1] = t16;
                    t26 ^= t16;
                    W[i5][2] = t26;
                    t35 ^= t26;
                    W[i5][3] = t35;
                    t44 ^= n(t35);
                    W[i5 + 1][0] = t44;
                    t54 ^= t44;
                    W[i5 + 1][1] = t54;
                    t6 ^= t54;
                    W[i5 + 1][2] = t6;
                    t7 ^= t6;
                    W[i5 + 1][3] = t7;
                }
                int t07 = t06 ^ (n(m(t7, 8)) ^ rcon3);
                W[14][0] = t07;
                int t17 = t16 ^ t07;
                W[14][1] = t17;
                int t27 = t26 ^ t17;
                W[14][2] = t27;
                W[14][3] = t35 ^ t27;
                break;
            default:
                throw new IllegalStateException("Should never get here");
        }
        if (!forEncryption) {
            for (int j2 = 1; j2 < this.d; j2++) {
                for (int i6 = 0; i6 < 4; i6++) {
                    W[j2][i6] = j(W[j2][i6]);
                }
            }
        }
        return W;
    }

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof KeyParameter) {
            this.e = i(((KeyParameter) params).a(), forEncryption);
            this.j = forEncryption;
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to AES init - " + params.getClass().getName());
    }

    public String b() {
        return "AES";
    }

    public int c() {
        return 16;
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        if (this.e == null) {
            throw new IllegalStateException("AES engine not initialised");
        } else if (inOff + 16 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + 16 > out.length) {
            throw new OutputLengthException("output buffer too short");
        } else if (this.j) {
            o(in, inOff);
            h(this.e);
            l(out, outOff);
            return 16;
        } else {
            o(in, inOff);
            g(this.e);
            l(out, outOff);
            return 16;
        }
    }

    public void reset() {
    }

    private void o(byte[] bytes, int off) {
        int index = off;
        int index2 = index + 1;
        byte b2 = bytes[index] & 255;
        this.f = b2;
        int index3 = index2 + 1;
        byte b3 = b2 | ((bytes[index2] & 255) << 8);
        this.f = b3;
        int index4 = index3 + 1;
        byte b4 = b3 | ((bytes[index3] & 255) << MappingData.PATH);
        this.f = b4;
        int index5 = index4 + 1;
        this.f = b4 | (bytes[index4] << 24);
        int index6 = index5 + 1;
        byte b5 = bytes[index5] & 255;
        this.g = b5;
        int index7 = index6 + 1;
        byte b6 = ((bytes[index6] & 255) << 8) | b5;
        this.g = b6;
        int index8 = index7 + 1;
        byte b7 = b6 | ((bytes[index7] & 255) << MappingData.PATH);
        this.g = b7;
        int index9 = index8 + 1;
        this.g = b7 | (bytes[index8] << 24);
        int index10 = index9 + 1;
        byte b8 = bytes[index9] & 255;
        this.h = b8;
        int index11 = index10 + 1;
        byte b9 = ((bytes[index10] & 255) << 8) | b8;
        this.h = b9;
        int index12 = index11 + 1;
        byte b10 = b9 | ((bytes[index11] & 255) << MappingData.PATH);
        this.h = b10;
        int index13 = index12 + 1;
        this.h = b10 | (bytes[index12] << 24);
        int index14 = index13 + 1;
        byte b11 = bytes[index13] & 255;
        this.i = b11;
        int index15 = index14 + 1;
        byte b12 = ((bytes[index14] & 255) << 8) | b11;
        this.i = b12;
        int index16 = index15 + 1;
        byte b13 = b12 | ((bytes[index15] & 255) << MappingData.PATH);
        this.i = b13;
        int i2 = index16 + 1;
        this.i = b13 | (bytes[index16] << 24);
    }

    private void l(byte[] bytes, int off) {
        int index = off;
        int index2 = index + 1;
        int i2 = this.f;
        bytes[index] = (byte) i2;
        int index3 = index2 + 1;
        bytes[index2] = (byte) (i2 >> 8);
        int index4 = index3 + 1;
        bytes[index3] = (byte) (i2 >> 16);
        int index5 = index4 + 1;
        bytes[index4] = (byte) (i2 >> 24);
        int index6 = index5 + 1;
        int i3 = this.g;
        bytes[index5] = (byte) i3;
        int index7 = index6 + 1;
        bytes[index6] = (byte) (i3 >> 8);
        int index8 = index7 + 1;
        bytes[index7] = (byte) (i3 >> 16);
        int index9 = index8 + 1;
        bytes[index8] = (byte) (i3 >> 24);
        int index10 = index9 + 1;
        int i4 = this.h;
        bytes[index9] = (byte) i4;
        int index11 = index10 + 1;
        bytes[index10] = (byte) (i4 >> 8);
        int index12 = index11 + 1;
        bytes[index11] = (byte) (i4 >> 16);
        int index13 = index12 + 1;
        bytes[index12] = (byte) (i4 >> 24);
        int index14 = index13 + 1;
        int i5 = this.i;
        bytes[index13] = (byte) i5;
        int index15 = index14 + 1;
        bytes[index14] = (byte) (i5 >> 8);
        int index16 = index15 + 1;
        bytes[index15] = (byte) (i5 >> 16);
        int i6 = index16 + 1;
        bytes[index16] = (byte) (i5 >> 24);
    }

    private void h(int[][] KW) {
        int t0 = this.f ^ KW[0][0];
        int t1 = this.g ^ KW[0][1];
        int t2 = this.h ^ KW[0][2];
        int r3 = 1;
        int r32 = this.i ^ KW[0][3];
        while (r3 < this.d - 1) {
            byte[] bArr = a;
            int r0 = k((((bArr[t0 & 255] & 255) ^ ((bArr[(t1 >> 8) & 255] & 255) << 8)) ^ ((bArr[(t2 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr[(r32 >> 24) & 255] << 24)) ^ KW[r3][0];
            int r1 = k((((bArr[t1 & 255] & 255) ^ ((bArr[(t2 >> 8) & 255] & 255) << 8)) ^ ((bArr[(r32 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr[(t0 >> 24) & 255] << 24)) ^ KW[r3][1];
            int r2 = k((((bArr[t2 & 255] & 255) ^ ((bArr[(r32 >> 8) & 255] & 255) << 8)) ^ ((bArr[(t0 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr[(t1 >> 24) & 255] << 24)) ^ KW[r3][2];
            int r = r3 + 1;
            int r33 = KW[r3][3] ^ k((((bArr[r32 & 255] & 255) ^ ((bArr[(t0 >> 8) & 255] & 255) << 8)) ^ ((bArr[(t1 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr[(t2 >> 24) & 255] << 24));
            t0 = k((((bArr[r0 & 255] & 255) ^ ((bArr[(r1 >> 8) & 255] & 255) << 8)) ^ ((bArr[(r2 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr[(r33 >> 24) & 255] << 24)) ^ KW[r][0];
            t1 = k((((bArr[r1 & 255] & 255) ^ ((bArr[(r2 >> 8) & 255] & 255) << 8)) ^ ((bArr[(r33 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr[(r0 >> 24) & 255] << 24)) ^ KW[r][1];
            t2 = k((((bArr[r2 & 255] & 255) ^ ((bArr[(r33 >> 8) & 255] & 255) << 8)) ^ ((bArr[(r0 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr[(r1 >> 24) & 255] << 24)) ^ KW[r][2];
            r32 = k((((bArr[r33 & 255] & 255) ^ ((bArr[(r0 >> 8) & 255] & 255) << 8)) ^ ((bArr[(r1 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr[(r2 >> 24) & 255] << 24)) ^ KW[r][3];
            r3 = r + 1;
        }
        byte[] bArr2 = a;
        int r02 = k((((bArr2[t0 & 255] & 255) ^ ((bArr2[(t1 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(t2 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr2[(r32 >> 24) & 255] << 24)) ^ KW[r3][0];
        int r12 = k((((bArr2[t1 & 255] & 255) ^ ((bArr2[(t2 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(r32 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr2[(t0 >> 24) & 255] << 24)) ^ KW[r3][1];
        int r22 = k((((bArr2[t2 & 255] & 255) ^ ((bArr2[(r32 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(t0 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr2[(t1 >> 24) & 255] << 24)) ^ KW[r3][2];
        int r4 = r3 + 1;
        int r34 = KW[r3][3] ^ k((((bArr2[r32 & 255] & 255) ^ ((bArr2[(t0 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(t1 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr2[(t2 >> 24) & 255] << 24));
        this.f = KW[r4][0] ^ ((((bArr2[r02 & 255] & 255) ^ ((bArr2[(r12 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(r22 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr2[(r34 >> 24) & 255] << 24));
        this.g = ((((bArr2[r12 & 255] & 255) ^ ((bArr2[(r22 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(r34 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr2[(r02 >> 24) & 255] << 24)) ^ KW[r4][1];
        this.h = ((((bArr2[r22 & 255] & 255) ^ ((bArr2[(r34 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(r02 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr2[(r12 >> 24) & 255] << 24)) ^ KW[r4][2];
        this.i = ((((bArr2[r34 & 255] & 255) ^ ((bArr2[(r02 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(r12 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr2[(r22 >> 24) & 255] << 24)) ^ KW[r4][3];
    }

    private void g(int[][] KW) {
        int i2 = this.f;
        int i3 = this.d;
        int t0 = i2 ^ KW[i3][0];
        int t1 = this.g ^ KW[i3][1];
        int t2 = this.h ^ KW[i3][2];
        int r = i3 - 1;
        int r3 = KW[i3][3] ^ this.i;
        while (r > 1) {
            byte[] bArr = b;
            int r0 = j((((bArr[t0 & 255] & 255) ^ ((bArr[(r3 >> 8) & 255] & 255) << 8)) ^ ((bArr[(t2 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr[(t1 >> 24) & 255] << 24)) ^ KW[r][0];
            int r1 = j((((bArr[t1 & 255] & 255) ^ ((bArr[(t0 >> 8) & 255] & 255) << 8)) ^ ((bArr[(r3 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr[(t2 >> 24) & 255] << 24)) ^ KW[r][1];
            int r2 = j((((bArr[t2 & 255] & 255) ^ ((bArr[(t1 >> 8) & 255] & 255) << 8)) ^ ((bArr[(t0 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr[(r3 >> 24) & 255] << 24)) ^ KW[r][2];
            int r4 = r - 1;
            int r32 = j((((bArr[r3 & 255] & 255) ^ ((bArr[(t2 >> 8) & 255] & 255) << 8)) ^ ((bArr[(t1 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr[(t0 >> 24) & 255] << 24)) ^ KW[r][3];
            t0 = j((((bArr[r0 & 255] & 255) ^ ((bArr[(r32 >> 8) & 255] & 255) << 8)) ^ ((bArr[(r2 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr[(r1 >> 24) & 255] << 24)) ^ KW[r4][0];
            t1 = j((((bArr[r1 & 255] & 255) ^ ((bArr[(r0 >> 8) & 255] & 255) << 8)) ^ ((bArr[(r32 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr[(r2 >> 24) & 255] << 24)) ^ KW[r4][1];
            t2 = j((((bArr[r2 & 255] & 255) ^ ((bArr[(r1 >> 8) & 255] & 255) << 8)) ^ ((bArr[(r0 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr[(r32 >> 24) & 255] << 24)) ^ KW[r4][2];
            r3 = j((((bArr[r32 & 255] & 255) ^ ((bArr[(r2 >> 8) & 255] & 255) << 8)) ^ ((bArr[(r1 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr[(r0 >> 24) & 255] << 24)) ^ KW[r4][3];
            r = r4 - 1;
        }
        byte[] bArr2 = b;
        int r02 = j((((bArr2[t0 & 255] & 255) ^ ((bArr2[(r3 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(t2 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr2[(t1 >> 24) & 255] << 24)) ^ KW[r][0];
        int r12 = j((((bArr2[t1 & 255] & 255) ^ ((bArr2[(t0 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(r3 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr2[(t2 >> 24) & 255] << 24)) ^ KW[r][1];
        int r22 = j((((bArr2[t2 & 255] & 255) ^ ((bArr2[(t1 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(t0 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr2[(r3 >> 24) & 255] << 24)) ^ KW[r][2];
        int r33 = j((((bArr2[r3 & 255] & 255) ^ ((bArr2[(t2 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(t1 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr2[(t0 >> 24) & 255] << 24)) ^ KW[r][3];
        this.f = ((((bArr2[r02 & 255] & 255) ^ ((bArr2[(r33 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(r22 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr2[(r12 >> 24) & 255] << 24)) ^ KW[0][0];
        this.g = KW[0][1] ^ ((((bArr2[r12 & 255] & 255) ^ ((bArr2[(r02 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(r33 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr2[(r22 >> 24) & 255] << 24));
        this.h = ((((bArr2[r22 & 255] & 255) ^ ((bArr2[(r12 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(r02 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr2[(r33 >> 24) & 255] << 24)) ^ KW[0][2];
        this.i = KW[0][3] ^ ((((bArr2[r33 & 255] & 255) ^ ((bArr2[(r22 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(r12 >> 16) & 255] & 255) << MappingData.PATH)) ^ (bArr2[(r02 >> 24) & 255] << 24));
    }
}
