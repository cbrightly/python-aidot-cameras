package org.spongycastle.pqc.crypto.newhope;

import com.tutk.IOTC.AVIOCTRLDEFs;
import org.spongycastle.util.Arrays;

public class ErrorCorrection {
    ErrorCorrection() {
    }

    static int b(int v) {
        int mask = v >> 31;
        return (v ^ mask) - mask;
    }

    static int c(int[] v, int off0, int off1, int x) {
        int t = (x * 2730) >> 25;
        int t2 = t - ((12288 - (x - (t * 12289))) >> 31);
        v[off0] = (t2 >> 1) + (t2 & 1);
        int t3 = t2 - 1;
        v[off1] = (t3 >> 1) + (t3 & 1);
        return b(x - ((v[off0] * 2) * 12289));
    }

    static int d(int x) {
        int t = (x * 2730) >> 27;
        int t2 = t - ((49155 - (x - (49156 * t))) >> 31);
        return b((((t2 >> 1) + (t2 & 1)) * 98312) - x);
    }

    static void e(short[] c, short[] v, byte[] seed, byte nonce) {
        short s = 8;
        byte[] iv = new byte[8];
        iv[0] = nonce;
        byte[] rand = new byte[32];
        ChaCha20.a(seed, iv, rand, 0, rand.length);
        int[] vs = new int[8];
        int[] vTmp = new int[4];
        int i = 0;
        while (i < 256) {
            int rBit = (rand[i >>> 3] >>> (i & 7)) & 1;
            int k = (24577 - (((c(vs, 0, 4, (v[i + 0] * s) + (rBit * 4)) + c(vs, 1, 5, (v[i + 256] * s) + (rBit * 4))) + c(vs, 2, 6, (v[i + 512] * s) + (rBit * 4))) + c(vs, 3, 7, (v[i + AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART] * s) + (rBit * 4)))) >> 31;
            vTmp[0] = ((~k) & vs[0]) ^ (k & vs[4]);
            vTmp[1] = ((~k) & vs[1]) ^ (vs[5] & k);
            vTmp[2] = ((~k) & vs[2]) ^ (vs[6] & k);
            vTmp[3] = ((~k) & vs[3]) ^ (vs[7] & k);
            c[i + 0] = (short) ((vTmp[0] - vTmp[3]) & 3);
            c[i + 256] = (short) ((vTmp[1] - vTmp[3]) & 3);
            c[i + 512] = (short) ((vTmp[2] - vTmp[3]) & 3);
            c[i + AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART] = (short) (3 & ((-k) + (vTmp[3] * 2)));
            i++;
            s = 8;
        }
    }

    static short a(int xi0, int xi1, int xi2, int xi3) {
        return (short) (((((d(xi0) + d(xi1)) + d(xi2)) + d(xi3)) - 98312) >>> 31);
    }

    static void f(byte[] key, short[] v, short[] c) {
        Arrays.F(key, (byte) 0);
        int[] tmp = new int[4];
        for (int i = 0; i < 256; i++) {
            tmp[0] = ((v[i + 0] * 8) + 196624) - (((c[i + 0] * 2) + c[i + AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART]) * 12289);
            tmp[1] = ((v[i + 256] * 8) + 196624) - (((c[i + 256] * 2) + c[i + AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART]) * 12289);
            tmp[2] = ((v[i + 512] * 8) + 196624) - (((c[i + 512] * 2) + c[i + AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART]) * 12289);
            tmp[3] = ((v[i + AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART] * 8) + 196624) - (c[i + AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART] * 12289);
            int i2 = i >>> 3;
            key[i2] = (byte) ((a(tmp[0], tmp[1], tmp[2], tmp[3]) << (i & 7)) | key[i2]);
        }
    }
}
