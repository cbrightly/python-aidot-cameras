package com.google.android.gms.common.util;

import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class MurmurHash3 {
    private MurmurHash3() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0079, code lost:
        return r7 ^ (r7 >>> 16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0050, code lost:
        r0 = r0 | ((r7[r1 + 1] & 255) << 8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0059, code lost:
        r7 = ((r7[r1] & 255) | r0) * -862048943;
        r10 = r10 ^ (((r7 >>> 17) | (r7 << 15)) * 461845907);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0066, code lost:
        r7 = r10 ^ r9;
        r7 = (r7 ^ (r7 >>> 16)) * -2048144789;
        r7 = (r7 ^ (r7 >>> 13)) * -1028477387;
     */
    @com.google.android.gms.common.annotation.KeepForSdk
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int murmurhash3_x86_32(@androidx.annotation.NonNull byte[] r7, int r8, int r9, int r10) {
        /*
            r0 = r8
        L_0x0001:
            r1 = r9 & -4
            int r1 = r1 + r8
            r2 = 461845907(0x1b873593, float:2.2368498E-22)
            r3 = -862048943(0xffffffffcc9e2d51, float:-8.2930312E7)
            if (r0 >= r1) goto L_0x003f
            byte r1 = r7[r0]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r4 = r0 + 1
            byte r4 = r7[r4]
            r4 = r4 & 255(0xff, float:3.57E-43)
            int r4 = r4 << 8
            int r5 = r0 + 2
            byte r5 = r7[r5]
            r5 = r5 & 255(0xff, float:3.57E-43)
            int r5 = r5 << 16
            int r6 = r0 + 3
            byte r6 = r7[r6]
            int r6 = r6 << 24
            r1 = r1 | r4
            r1 = r1 | r5
            r1 = r1 | r6
            int r1 = r1 * r3
            int r3 = r1 << 15
            int r1 = r1 >>> 17
            r1 = r1 | r3
            int r1 = r1 * r2
            r10 = r10 ^ r1
            int r1 = r10 << 13
            int r10 = r10 >>> 19
            r10 = r10 | r1
            int r10 = r10 * 5
            r1 = -430675100(0xffffffffe6546b64, float:-2.5078068E23)
            int r10 = r10 + r1
            int r0 = r0 + 4
            goto L_0x0001
        L_0x003f:
            r8 = r9 & 3
            r0 = 0
            switch(r8) {
                case 1: goto L_0x0059;
                case 2: goto L_0x004f;
                case 3: goto L_0x0046;
                default: goto L_0x0045;
            }
        L_0x0045:
            goto L_0x0066
        L_0x0046:
            int r8 = r1 + 2
            byte r8 = r7[r8]
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r0 = r8 << 16
            goto L_0x0050
        L_0x004f:
        L_0x0050:
            int r8 = r1 + 1
            byte r8 = r7[r8]
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r8 = r8 << 8
            r0 = r0 | r8
        L_0x0059:
            byte r7 = r7[r1]
            r7 = r7 & 255(0xff, float:3.57E-43)
            r7 = r7 | r0
            int r7 = r7 * r3
            int r8 = r7 << 15
            int r7 = r7 >>> 17
            r7 = r7 | r8
            int r7 = r7 * r2
            r10 = r10 ^ r7
        L_0x0066:
            r7 = r10 ^ r9
            int r8 = r7 >>> 16
            r7 = r7 ^ r8
            r8 = -2048144789(0xffffffff85ebca6b, float:-2.217365E-35)
            int r7 = r7 * r8
            int r8 = r7 >>> 13
            r7 = r7 ^ r8
            r8 = -1028477387(0xffffffffc2b2ae35, float:-89.34025)
            int r7 = r7 * r8
            int r8 = r7 >>> 16
            r7 = r7 ^ r8
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.MurmurHash3.murmurhash3_x86_32(byte[], int, int, int):int");
    }
}
