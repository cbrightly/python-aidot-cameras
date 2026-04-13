package org.spongycastle.crypto.modes.gcm;

import java.lang.reflect.Array;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;

public class Tables8kGCMMultiplier implements GCMMultiplier {
    private byte[] a;
    private int[][][] b;

    public void a(byte[] H) {
        if (this.b == null) {
            this.b = (int[][][]) Array.newInstance(int.class, new int[]{32, 16, 4});
        } else if (Arrays.b(this.a, H)) {
            return;
        }
        this.a = Arrays.h(H);
        GCMUtil.b(H, this.b[1][8]);
        for (int j = 4; j >= 1; j >>= 1) {
            int[][][] iArr = this.b;
            GCMUtil.g(iArr[1][j + j], iArr[1][j]);
        }
        int[][][] iArr2 = this.b;
        GCMUtil.g(iArr2[1][1], iArr2[0][8]);
        for (int j2 = 4; j2 >= 1; j2 >>= 1) {
            int[][][] iArr3 = this.b;
            GCMUtil.g(iArr3[0][j2 + j2], iArr3[0][j2]);
        }
        int i = 0;
        while (true) {
            for (int j3 = 2; j3 < 16; j3 += j3) {
                for (int k = 1; k < j3; k++) {
                    int[][][] iArr4 = this.b;
                    GCMUtil.n(iArr4[i][j3], iArr4[i][k], iArr4[i][j3 + k]);
                }
            }
            i++;
            if (i != 32) {
                if (i > 1) {
                    for (int j4 = 8; j4 > 0; j4 >>= 1) {
                        int[][][] iArr5 = this.b;
                        GCMUtil.h(iArr5[i - 2][j4], iArr5[i][j4]);
                    }
                }
            } else {
                return;
            }
        }
    }

    public void b(byte[] x) {
        int[] z = new int[4];
        for (int i = 15; i >= 0; i--) {
            int[][][] iArr = this.b;
            int[] m = iArr[i + i][x[i] & 15];
            z[0] = z[0] ^ m[0];
            z[1] = z[1] ^ m[1];
            z[2] = z[2] ^ m[2];
            z[3] = z[3] ^ m[3];
            int[] m2 = iArr[i + i + 1][(x[i] & 240) >>> 4];
            z[0] = z[0] ^ m2[0];
            z[1] = z[1] ^ m2[1];
            z[2] = z[2] ^ m2[2];
            z[3] = z[3] ^ m2[3];
        }
        Pack.e(z, x, 0);
    }
}
