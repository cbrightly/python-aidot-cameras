package org.spongycastle.crypto.modes.gcm;

import java.lang.reflect.Array;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;

public class Tables64kGCMMultiplier implements GCMMultiplier {
    private byte[] a;
    private int[][][] b;

    public void a(byte[] H) {
        if (this.b == null) {
            this.b = (int[][][]) Array.newInstance(int.class, new int[]{16, 256, 4});
        } else if (Arrays.b(this.a, H)) {
            return;
        }
        this.a = Arrays.h(H);
        GCMUtil.b(H, this.b[0][128]);
        for (int j = 64; j >= 1; j >>= 1) {
            int[][][] iArr = this.b;
            GCMUtil.g(iArr[0][j + j], iArr[0][j]);
        }
        int i = 0;
        while (true) {
            for (int j2 = 2; j2 < 256; j2 += j2) {
                for (int k = 1; k < j2; k++) {
                    int[][][] iArr2 = this.b;
                    GCMUtil.n(iArr2[i][j2], iArr2[i][k], iArr2[i][j2 + k]);
                }
            }
            i++;
            if (i != 16) {
                for (int j3 = 128; j3 > 0; j3 >>= 1) {
                    int[][][] iArr3 = this.b;
                    GCMUtil.h(iArr3[i - 1][j3], iArr3[i][j3]);
                }
            } else {
                return;
            }
        }
    }

    public void b(byte[] x) {
        int[] z = new int[4];
        for (int i = 15; i >= 0; i--) {
            int[] m = this.b[i][x[i] & 255];
            z[0] = z[0] ^ m[0];
            z[1] = z[1] ^ m[1];
            z[2] = z[2] ^ m[2];
            z[3] = z[3] ^ m[3];
        }
        Pack.e(z, x, 0);
    }
}
