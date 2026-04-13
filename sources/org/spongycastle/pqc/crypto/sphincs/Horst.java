package org.spongycastle.pqc.crypto.sphincs;

import android.support.v4.media.session.PlaybackStateCompat;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.tutk.IOTC.AVIOCTRLDEFs;

public class Horst {
    Horst() {
    }

    static void a(byte[] outseeds, byte[] inseed) {
        Seed.b(outseeds, 0, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE, inseed, 0);
    }

    static int b(HashFunctions hs, byte[] sig, int sigOff, byte[] pk, byte[] seed, byte[] masks, byte[] m_hash) {
        byte[] sk = new byte[2097152];
        int sigpos = sigOff;
        byte[] tree = new byte[4194272];
        a(sk, seed);
        for (int i = 0; i < 65536; i++) {
            hs.d(tree, (65535 + i) * 32, sk, i * 32);
        }
        HashFunctions hashFunctions = hs;
        for (int i2 = 0; i2 < 16; i2++) {
            long offset_in = (long) ((1 << (16 - i2)) - 1);
            long offset_out = (long) ((1 << ((16 - i2) - 1)) - 1);
            int j = 0;
            while (j < (1 << ((16 - i2) - 1))) {
                hs.c(tree, (int) ((((long) j) + offset_out) * 32), tree, (int) ((((long) (j * 2)) + offset_in) * 32), masks, i2 * 2 * 32);
                j++;
                offset_in = offset_in;
                offset_out = offset_out;
            }
            int i3 = j;
            long j2 = offset_out;
            long j3 = offset_in;
        }
        int j4 = 2016;
        while (j4 < 4064) {
            sig[sigpos] = tree[j4];
            j4++;
            sigpos++;
        }
        for (int i4 = 0; i4 < 32; i4++) {
            int idx = (m_hash[i4 * 2] & 255) + ((m_hash[(i4 * 2) + 1] & 255) << 8);
            int k = 0;
            while (k < 32) {
                sig[sigpos] = sk[(idx * 32) + k];
                k++;
                sigpos++;
            }
            int idx2 = idx + 65535;
            for (int j5 = 0; j5 < 10; j5++) {
                int idx3 = (idx2 & 1) != 0 ? idx2 + 1 : idx2 - 1;
                int k2 = 0;
                while (k2 < 32) {
                    sig[sigpos] = tree[(idx3 * 32) + k2];
                    k2++;
                    sigpos++;
                }
                idx2 = (idx3 - 1) / 2;
            }
        }
        for (int i5 = 0; i5 < 32; i5++) {
            pk[i5] = tree[i5];
        }
        return 13312;
    }

    static int c(HashFunctions hs, byte[] pk, byte[] sig, int sigOff, byte[] masks, byte[] m_hash) {
        int k;
        int i;
        int k2;
        HashFunctions hashFunctions = hs;
        byte[] bArr = sig;
        int i2 = sigOff;
        byte[] buffer = new byte[1024];
        int sigOffset = i2 + 2048;
        int i3 = 0;
        while (true) {
            int i4 = 32;
            if (i3 < 32) {
                int idx = (m_hash[i3 * 2] & 255) + ((m_hash[(i3 * 2) + 1] & 255) << 8);
                if ((idx & 1) == 0) {
                    hashFunctions.d(buffer, 0, bArr, sigOffset);
                    k = 0;
                    while (k < 32) {
                        buffer[k + 32] = bArr[sigOffset + 32 + k];
                        k++;
                    }
                } else {
                    hashFunctions.d(buffer, 32, bArr, sigOffset);
                    int k3 = 0;
                    while (k < 32) {
                        buffer[k] = bArr[sigOffset + 32 + k];
                        k3 = k + 1;
                    }
                }
                int j = 1;
                int sigOffset2 = sigOffset + 64;
                int sigOffset3 = k;
                while (j < 10) {
                    int idx2 = idx >>> 1;
                    if ((idx2 & 1) == 0) {
                        i = i4;
                        hs.c(buffer, 0, buffer, 0, masks, (j - 1) * 2 * 32);
                        k2 = 0;
                        while (k2 < i) {
                            buffer[k2 + 32] = bArr[sigOffset2 + k2];
                            k2++;
                        }
                    } else {
                        i = i4;
                        hs.c(buffer, 32, buffer, 0, masks, (j - 1) * 2 * 32);
                        int k4 = 0;
                        while (k2 < i) {
                            buffer[k2] = bArr[sigOffset2 + k2];
                            k4 = k2 + 1;
                        }
                    }
                    int i5 = k2;
                    sigOffset2 += 32;
                    j++;
                    i4 = i;
                    idx = idx2;
                }
                int i6 = i4;
                int idx3 = idx >>> 1;
                hs.c(buffer, 0, buffer, 0, masks, 576);
                for (int k5 = 0; k5 < i6; k5++) {
                    if (bArr[(idx3 * 32) + i2 + k5] != buffer[k5]) {
                        for (int k6 = 0; k6 < i6; k6++) {
                            pk[k6] = 0;
                        }
                        return -1;
                    }
                }
                i3++;
                sigOffset = sigOffset2;
            } else {
                int j2 = 0;
                while (j2 < 32) {
                    hs.c(buffer, j2 * 32, sig, i2 + (j2 * 2 * 32), masks, 640);
                    j2++;
                }
                int i7 = j2;
                int j3 = 0;
                while (j3 < 16) {
                    hs.c(buffer, j3 * 32, buffer, j3 * 2 * 32, masks, TypedValues.TransitionType.TYPE_AUTO_TRANSITION);
                    j3++;
                }
                int i8 = j3;
                int j4 = 0;
                while (j4 < 8) {
                    hs.c(buffer, j4 * 32, buffer, j4 * 2 * 32, masks, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART);
                    j4++;
                }
                int i9 = j4;
                for (int j5 = 0; j5 < 4; j5++) {
                    hs.c(buffer, j5 * 32, buffer, j5 * 2 * 32, masks, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_LISTWIFIAP_REQ);
                }
                for (int j6 = 0; j6 < 2; j6++) {
                    hs.c(buffer, j6 * 32, buffer, j6 * 2 * 32, masks, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_FORMATEXTSTORAGE_REQ);
                }
                hs.c(pk, 0, buffer, 0, masks, 960);
                return 0;
            }
        }
    }
}
