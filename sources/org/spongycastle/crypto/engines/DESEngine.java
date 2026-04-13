package org.spongycastle.crypto.engines;

import androidx.core.view.InputDeviceCompat;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.tutk.IOTC.AVIOCTRLDEFs;
import net.sqlcipher.database.SQLiteDatabase;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.util.Constants;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;

public class DESEngine implements BlockCipher {
    private static final short[] a = {MqttException.REASON_CODE_SUBSCRIBE_FAILED, 64, 32, 16, 8, 4, 2, 1};
    private static final int[] b = {8388608, 4194304, 2097152, 1048576, 524288, 262144, 131072, 65536, 32768, 16384, 8192, 4096, 2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1};
    private static final byte[] c = {56, 48, 40, 32, 24, MappingData.PATH, 8, 0, 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, Constants.SEMI_COLON, 51, 43, 35, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 60, 52, Constants.COMMA, 36, 28, 20, 12, 4, 27, 19, 11, 3};
    private static final byte[] d = {1, 2, 4, 6, 8, 10, 12, 14, 15, 17, 19, 21, 23, 25, 27, 28};
    private static final byte[] e = {13, MappingData.PATH, 10, 23, 0, 4, 2, 27, 14, 5, 20, 9, 22, 18, 11, 3, 25, 7, 15, 6, 26, 19, 12, 1, 40, 51, 30, 36, 46, 54, 29, 39, 50, Constants.COMMA, 32, 47, 43, 48, 38, 55, 33, 52, 45, 41, 49, 35, 28, 31};
    private static final int[] f = {16843776, 0, 65536, 16843780, 16842756, 66564, 4, 65536, 1024, 16843776, 16843780, 1024, 16778244, 16842756, 16777216, 4, 1028, 16778240, 16778240, 66560, 66560, 16842752, 16842752, 16778244, InputDeviceCompat.SOURCE_TRACKBALL, 16777220, 16777220, InputDeviceCompat.SOURCE_TRACKBALL, 0, 1028, 66564, 16777216, 65536, 16843780, 4, 16842752, 16843776, 16777216, 16777216, 1024, 16842756, 65536, 66560, 16777220, 1024, 4, 16778244, 66564, 16843780, InputDeviceCompat.SOURCE_TRACKBALL, 16842752, 16778244, 16777220, 1028, 66564, 16843776, 1028, 16778240, 16778240, 0, InputDeviceCompat.SOURCE_TRACKBALL, 66560, 0, 16842756};
    private static final int[] g = {-2146402272, -2147450880, 32768, 1081376, 1048576, 32, -2146435040, -2147450848, -2147483616, -2146402272, -2146402304, Integer.MIN_VALUE, -2147450880, 1048576, 32, -2146435040, 1081344, 1048608, -2147450848, 0, Integer.MIN_VALUE, 32768, 1081376, -2146435072, 1048608, -2147483616, 0, 1081344, 32800, -2146402304, -2146435072, 32800, 0, 1081376, -2146435040, 1048576, -2147450848, -2146435072, -2146402304, 32768, -2146435072, -2147450880, 32, -2146402272, 1081376, 32, 32768, Integer.MIN_VALUE, 32800, -2146402304, 1048576, -2147483616, 1048608, -2147450848, -2147483616, 1048608, 1081344, 0, -2147450880, 32800, Integer.MIN_VALUE, -2146435040, -2146402272, 1081344};
    private static final int[] h = {520, 134349312, 0, 134348808, 134218240, 0, 131592, 134218240, 131080, 134217736, 134217736, 131072, 134349320, 131080, 134348800, 520, 134217728, 8, 134349312, 512, 131584, 134348800, 134348808, 131592, 134218248, 131584, 131072, 134218248, 8, 134349320, 512, 134217728, 134349312, 134217728, 131080, 520, 131072, 134349312, 134218240, 0, 512, 131080, 134349320, 134218240, 134217736, 512, 0, 134348808, 134218248, 131072, 134217728, 134349320, 8, 131592, 131584, 134217736, 134348800, 134218248, 520, 134348800, 131592, 8, 134348808, 131584};
    private static final int[] i = {8396801, 8321, 8321, 128, 8396928, 8388737, 8388609, 8193, 0, 8396800, 8396800, 8396929, NeedPermissionEvent.PER_IPC_GET_SPEAK_PERM, 0, 8388736, 8388609, 1, 8192, 8388608, 8396801, 128, 8388608, 8193, 8320, 8388737, 1, 8320, 8388736, 8192, 8396928, 8396929, NeedPermissionEvent.PER_IPC_GET_SPEAK_PERM, 8388736, 8388609, 8396800, 8396929, NeedPermissionEvent.PER_IPC_GET_SPEAK_PERM, 0, 0, 8396800, 8320, 8388736, 8388737, 1, 8396801, 8321, 8321, 128, 8396929, NeedPermissionEvent.PER_IPC_GET_SPEAK_PERM, 1, 8192, 8388609, 8193, 8396928, 8388737, 8193, 8320, 8388608, 8396801, 128, 8388608, 8192, 8396928};
    private static final int[] j = {256, 34078976, 34078720, 1107296512, 524288, 256, 1073741824, 34078720, 1074266368, 524288, 33554688, 1074266368, 1107296512, 1107820544, 524544, 1073741824, 33554432, 1074266112, 1074266112, 0, 1073742080, 1107820800, 1107820800, 33554688, 1107820544, 1073742080, 0, 1107296256, 34078976, 33554432, 1107296256, 524544, 524288, 1107296512, 256, 33554432, 1073741824, 34078720, 1107296512, 1074266368, 33554688, 1073741824, 1107820544, 34078976, 1074266368, 256, 33554432, 1107820544, 1107820800, 524544, 1107296256, 1107820800, 34078720, 0, 1074266112, 1107296256, 524544, 33554688, 1073742080, 524288, 0, 1074266112, 34078976, 1073742080};
    private static final int[] k = {536870928, 541065216, 16384, 541081616, 541065216, 16, 541081616, 4194304, 536887296, 4210704, 4194304, 536870928, 4194320, 536887296, 536870912, 16400, 0, 4194320, 536887312, 16384, 4210688, 536887312, 16, 541065232, 541065232, 0, 4210704, 541081600, 16400, 4210688, 541081600, 536870912, 536887296, 16, 541065232, 4210688, 541081616, 4194304, 16400, 536870928, 4194304, 536887296, 536870912, 16400, 536870928, 541081616, 4210688, 541065216, 4210704, 541081600, 0, 541065232, 16, 16384, 541065216, 4210704, 16384, 4194320, 536887312, 0, 541081600, 536870912, 4194320, 536887312};
    private static final int[] l = {2097152, 69206018, 67110914, 0, 2048, 67110914, 2099202, 69208064, 69208066, 2097152, 0, 67108866, 2, 67108864, 69206018, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_LIVEVIEWTIMESTAMP_REQ, 67110912, 2099202, 2097154, 67110912, 67108866, 69206016, 69208064, 2097154, 69206016, 2048, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_LIVEVIEWTIMESTAMP_REQ, 69208066, 2099200, 2, 67108864, 2099200, 67108864, 2099200, 2097152, 67110914, 67110914, 69206018, 69206018, 2, 2097154, 67108864, 67110912, 2097152, 69208064, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_LIVEVIEWTIMESTAMP_REQ, 2099202, 69208064, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_LIVEVIEWTIMESTAMP_REQ, 67108866, 69208066, 69206016, 2099200, 0, 2, 69208066, 0, 2099202, 69206016, 2048, 67108866, 67110912, 2048, 2097154};
    private static final int[] m = {268439616, 4096, 262144, 268701760, SQLiteDatabase.CREATE_IF_NECESSARY, 268439616, 64, SQLiteDatabase.CREATE_IF_NECESSARY, 262208, 268697600, 268701760, 266240, 268701696, 266304, 4096, 64, 268697600, 268435520, 268439552, 4160, 266240, 262208, 268697664, 268701696, 4160, 0, 0, 268697664, 268435520, 268439552, 266304, 262144, 266304, 262144, 268701696, 4096, 64, 268697664, 4096, 266304, 268439552, 64, 268435520, 268697600, 268697664, SQLiteDatabase.CREATE_IF_NECESSARY, 262144, 268439616, 0, 268701760, 262208, 268435520, 268697600, 268439552, 268439616, 0, 268701760, 266240, 266240, 4160, 4160, 262208, SQLiteDatabase.CREATE_IF_NECESSARY, 268701696};
    private int[] n = null;

    public void a(boolean encrypting, CipherParameters params) {
        if (!(params instanceof KeyParameter)) {
            throw new IllegalArgumentException("invalid parameter passed to DES init - " + params.getClass().getName());
        } else if (((KeyParameter) params).a().length <= 8) {
            this.n = e(encrypting, ((KeyParameter) params).a());
        } else {
            throw new IllegalArgumentException("DES key too long - should be 8 bytes");
        }
    }

    public String b() {
        return "DES";
    }

    public int c() {
        return 8;
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        int[] iArr = this.n;
        if (iArr == null) {
            throw new IllegalStateException("DES engine not initialised");
        } else if (inOff + 8 > in.length) {
            throw new DataLengthException("input buffer too short");
        } else if (outOff + 8 <= out.length) {
            d(iArr, in, inOff, out, outOff);
            return 8;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
    }

    /* access modifiers changed from: protected */
    public int[] e(boolean encrypting, byte[] key) {
        int m2;
        int[] newKey = new int[32];
        boolean[] pc1m = new boolean[56];
        boolean[] pcr = new boolean[56];
        int j2 = 0;
        while (true) {
            boolean z = false;
            if (j2 >= 56) {
                break;
            }
            byte l2 = c[j2];
            if ((key[l2 >>> 3] & a[l2 & 7]) != 0) {
                z = true;
            }
            pc1m[j2] = z;
            j2++;
        }
        for (int i2 = 0; i2 < 16; i2++) {
            if (encrypting) {
                m2 = i2 << 1;
            } else {
                m2 = (15 - i2) << 1;
            }
            int n2 = m2 + 1;
            newKey[n2] = 0;
            newKey[m2] = 0;
            for (int j3 = 0; j3 < 28; j3++) {
                int l3 = d[i2] + j3;
                if (l3 < 28) {
                    pcr[j3] = pc1m[l3];
                } else {
                    pcr[j3] = pc1m[l3 - 28];
                }
            }
            for (int j4 = 28; j4 < 56; j4++) {
                int l4 = d[i2] + j4;
                if (l4 < 56) {
                    pcr[j4] = pc1m[l4];
                } else {
                    pcr[j4] = pc1m[l4 - 28];
                }
            }
            for (int j5 = 0; j5 < 24; j5++) {
                byte[] bArr = e;
                if (pcr[bArr[j5]]) {
                    newKey[m2] = newKey[m2] | b[j5];
                }
                if (pcr[bArr[j5 + 24]]) {
                    newKey[n2] = newKey[n2] | b[j5];
                }
            }
        }
        for (int i3 = 0; i3 != 32; i3 += 2) {
            int i1 = newKey[i3];
            int i22 = newKey[i3 + 1];
            newKey[i3] = ((16515072 & i22) >>> 10) | ((i1 & 16515072) << 6) | ((i1 & 4032) << 10) | ((i22 & 4032) >>> 6);
            newKey[i3 + 1] = ((258048 & i22) >>> 4) | ((i1 & 258048) << 12) | ((i1 & 63) << 16) | (i22 & 63);
        }
        return newKey;
    }

    /* access modifiers changed from: protected */
    public void d(int[] wKey, byte[] in, int inOff, byte[] out, int outOff) {
        int left = ((in[inOff + 0] & 255) << 24) | ((in[inOff + 1] & 255) << MappingData.PATH) | ((in[inOff + 2] & 255) << 8) | (in[inOff + 3] & 255);
        int right = ((in[inOff + 4] & 255) << 24) | ((in[inOff + 5] & 255) << MappingData.PATH) | ((in[inOff + 6] & 255) << 8) | (in[inOff + 7] & 255);
        int work = ((left >>> 4) ^ right) & 252645135;
        int right2 = right ^ work;
        int left2 = left ^ (work << 4);
        int work2 = ((left2 >>> 16) ^ right2) & 65535;
        int right3 = right2 ^ work2;
        int left3 = left2 ^ (work2 << 16);
        int work3 = ((right3 >>> 2) ^ left3) & 858993459;
        int left4 = left3 ^ work3;
        int right4 = right3 ^ (work3 << 2);
        int work4 = ((right4 >>> 8) ^ left4) & 16711935;
        int left5 = left4 ^ work4;
        int right5 = right4 ^ (work4 << 8);
        int right6 = ((right5 << 1) | ((right5 >>> 31) & 1)) & -1;
        int work5 = (left5 ^ right6) & -1431655766;
        int left6 = left5 ^ work5;
        int right7 = right6 ^ work5;
        int left7 = ((left6 << 1) | ((left6 >>> 31) & 1)) & -1;
        for (int round = 0; round < 8; round++) {
            int work6 = ((right7 << 28) | (right7 >>> 4)) ^ wKey[(round * 4) + 0];
            int[] iArr = l;
            int fval = iArr[work6 & 63];
            int[] iArr2 = j;
            int fval2 = fval | iArr2[(work6 >>> 8) & 63];
            int[] iArr3 = h;
            int fval3 = fval2 | iArr3[(work6 >>> 16) & 63];
            int[] iArr4 = f;
            int fval4 = fval3 | iArr4[(work6 >>> 24) & 63];
            int work7 = right7 ^ wKey[(round * 4) + 1];
            int[] iArr5 = m;
            int fval5 = fval4 | iArr5[work7 & 63];
            int[] iArr6 = k;
            int fval6 = fval5 | iArr6[(work7 >>> 8) & 63];
            int[] iArr7 = i;
            int fval7 = fval6 | iArr7[(work7 >>> 16) & 63];
            int[] iArr8 = g;
            left7 ^= fval7 | iArr8[(work7 >>> 24) & 63];
            int work8 = ((left7 << 28) | (left7 >>> 4)) ^ wKey[(round * 4) + 2];
            int fval8 = iArr[work8 & 63] | iArr2[(work8 >>> 8) & 63] | iArr3[(work8 >>> 16) & 63] | iArr4[(work8 >>> 24) & 63];
            int work9 = left7 ^ wKey[(round * 4) + 3];
            right7 ^= (((fval8 | iArr5[work9 & 63]) | iArr6[(work9 >>> 8) & 63]) | iArr7[(work9 >>> 16) & 63]) | iArr8[(work9 >>> 24) & 63];
        }
        int right8 = (right7 << 31) | (right7 >>> 1);
        int work10 = (left7 ^ right8) & -1431655766;
        int left8 = left7 ^ work10;
        int right9 = right8 ^ work10;
        int left9 = (left8 << 31) | (left8 >>> 1);
        int work11 = ((left9 >>> 8) ^ right9) & 16711935;
        int right10 = right9 ^ work11;
        int left10 = left9 ^ (work11 << 8);
        int work12 = ((left10 >>> 2) ^ right10) & 858993459;
        int right11 = right10 ^ work12;
        int left11 = left10 ^ (work12 << 2);
        int work13 = ((right11 >>> 16) ^ left11) & 65535;
        int left12 = left11 ^ work13;
        int right12 = right11 ^ (work13 << 16);
        int work14 = ((right12 >>> 4) ^ left12) & 252645135;
        int left13 = left12 ^ work14;
        int right13 = right12 ^ (work14 << 4);
        out[outOff + 0] = (byte) ((right13 >>> 24) & 255);
        out[outOff + 1] = (byte) ((right13 >>> 16) & 255);
        out[outOff + 2] = (byte) ((right13 >>> 8) & 255);
        out[outOff + 3] = (byte) (right13 & 255);
        out[outOff + 4] = (byte) ((left13 >>> 24) & 255);
        out[outOff + 5] = (byte) ((left13 >>> 16) & 255);
        out[outOff + 6] = (byte) ((left13 >>> 8) & 255);
        out[outOff + 7] = (byte) (left13 & 255);
    }
}
