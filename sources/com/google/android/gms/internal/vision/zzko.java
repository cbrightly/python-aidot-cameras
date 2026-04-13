package com.google.android.gms.internal.vision;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzko<T> implements zzlc<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzma.zzc();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzkk zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final boolean zzj;
    private final boolean zzk;
    private final int[] zzl;
    private final int zzm;
    private final int zzn;
    private final zzks zzo;
    private final zzju zzp;
    private final zzlu<?, ?> zzq;
    private final zziq<?> zzr;
    private final zzkh zzs;

    private zzko(int[] iArr, Object[] objArr, int i, int i2, zzkk zzkk, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzks zzks, zzju zzju, zzlu<?, ?> zzlu, zziq<?> zziq, zzkh zzkh) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i;
        this.zzf = i2;
        this.zzi = zzkk instanceof zzjb;
        this.zzj = z;
        this.zzh = zziq != null && zziq.zza(zzkk);
        this.zzk = false;
        this.zzl = iArr2;
        this.zzm = i3;
        this.zzn = i4;
        this.zzo = zzks;
        this.zzp = zzju;
        this.zzq = zzlu;
        this.zzr = zziq;
        this.zzg = zzkk;
        this.zzs = zzkh;
    }

    /* JADX WARNING: Removed duplicated region for block: B:155:0x0349  */
    /* JADX WARNING: Removed duplicated region for block: B:171:0x03a3  */
    /* JADX WARNING: Removed duplicated region for block: B:175:0x03b0 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static <T> com.google.android.gms.internal.vision.zzko<T> zza(java.lang.Class<T> r33, com.google.android.gms.internal.vision.zzki r34, com.google.android.gms.internal.vision.zzks r35, com.google.android.gms.internal.vision.zzju r36, com.google.android.gms.internal.vision.zzlu<?, ?> r37, com.google.android.gms.internal.vision.zziq<?> r38, com.google.android.gms.internal.vision.zzkh r39) {
        /*
            r0 = r34
            boolean r1 = r0 instanceof com.google.android.gms.internal.vision.zzla
            if (r1 == 0) goto L_0x0424
            com.google.android.gms.internal.vision.zzla r0 = (com.google.android.gms.internal.vision.zzla) r0
            int r1 = r0.zza()
            int r2 = com.google.android.gms.internal.vision.zzkz.zzb
            r3 = 0
            if (r1 != r2) goto L_0x0013
            r11 = 1
            goto L_0x0014
        L_0x0013:
            r11 = r3
        L_0x0014:
            java.lang.String r1 = r0.zzd()
            int r2 = r1.length()
            char r5 = r1.charAt(r3)
            r6 = 55296(0xd800, float:7.7486E-41)
            if (r5 < r6) goto L_0x0033
            r5 = 1
        L_0x0028:
            int r7 = r5 + 1
            char r5 = r1.charAt(r5)
            if (r5 < r6) goto L_0x0034
            r5 = r7
            goto L_0x0028
        L_0x0033:
            r7 = 1
        L_0x0034:
            int r5 = r7 + 1
            char r7 = r1.charAt(r7)
            if (r7 < r6) goto L_0x0053
            r7 = r7 & 8191(0x1fff, float:1.1478E-41)
            r9 = 13
        L_0x0040:
            int r10 = r5 + 1
            char r5 = r1.charAt(r5)
            if (r5 < r6) goto L_0x0050
            r5 = r5 & 8191(0x1fff, float:1.1478E-41)
            int r5 = r5 << r9
            r7 = r7 | r5
            int r9 = r9 + 13
            r5 = r10
            goto L_0x0040
        L_0x0050:
            int r5 = r5 << r9
            r7 = r7 | r5
            r5 = r10
        L_0x0053:
            if (r7 != 0) goto L_0x0067
            int[] r7 = zza
            r9 = r3
            r10 = r9
            r12 = r10
            r13 = r12
            r15 = r13
            r14 = r7
            r7 = r15
            goto L_0x0180
        L_0x0067:
            int r7 = r5 + 1
            char r5 = r1.charAt(r5)
            if (r5 < r6) goto L_0x0086
            r5 = r5 & 8191(0x1fff, float:1.1478E-41)
            r9 = 13
        L_0x0073:
            int r10 = r7 + 1
            char r7 = r1.charAt(r7)
            if (r7 < r6) goto L_0x0083
            r7 = r7 & 8191(0x1fff, float:1.1478E-41)
            int r7 = r7 << r9
            r5 = r5 | r7
            int r9 = r9 + 13
            r7 = r10
            goto L_0x0073
        L_0x0083:
            int r7 = r7 << r9
            r5 = r5 | r7
            r7 = r10
        L_0x0086:
            int r9 = r7 + 1
            char r7 = r1.charAt(r7)
            if (r7 < r6) goto L_0x00a6
            r7 = r7 & 8191(0x1fff, float:1.1478E-41)
            r10 = 13
        L_0x0093:
            int r12 = r9 + 1
            char r9 = r1.charAt(r9)
            if (r9 < r6) goto L_0x00a3
            r9 = r9 & 8191(0x1fff, float:1.1478E-41)
            int r9 = r9 << r10
            r7 = r7 | r9
            int r10 = r10 + 13
            r9 = r12
            goto L_0x0093
        L_0x00a3:
            int r9 = r9 << r10
            r7 = r7 | r9
            r9 = r12
        L_0x00a6:
            int r10 = r9 + 1
            char r9 = r1.charAt(r9)
            if (r9 < r6) goto L_0x00c6
            r9 = r9 & 8191(0x1fff, float:1.1478E-41)
            r12 = 13
        L_0x00b3:
            int r13 = r10 + 1
            char r10 = r1.charAt(r10)
            if (r10 < r6) goto L_0x00c3
            r10 = r10 & 8191(0x1fff, float:1.1478E-41)
            int r10 = r10 << r12
            r9 = r9 | r10
            int r12 = r12 + 13
            r10 = r13
            goto L_0x00b3
        L_0x00c3:
            int r10 = r10 << r12
            r9 = r9 | r10
            r10 = r13
        L_0x00c6:
            int r12 = r10 + 1
            char r10 = r1.charAt(r10)
            if (r10 < r6) goto L_0x00e6
            r10 = r10 & 8191(0x1fff, float:1.1478E-41)
            r13 = 13
        L_0x00d3:
            int r14 = r12 + 1
            char r12 = r1.charAt(r12)
            if (r12 < r6) goto L_0x00e3
            r12 = r12 & 8191(0x1fff, float:1.1478E-41)
            int r12 = r12 << r13
            r10 = r10 | r12
            int r13 = r13 + 13
            r12 = r14
            goto L_0x00d3
        L_0x00e3:
            int r12 = r12 << r13
            r10 = r10 | r12
            r12 = r14
        L_0x00e6:
            int r13 = r12 + 1
            char r12 = r1.charAt(r12)
            if (r12 < r6) goto L_0x0106
            r12 = r12 & 8191(0x1fff, float:1.1478E-41)
            r14 = 13
        L_0x00f3:
            int r15 = r13 + 1
            char r13 = r1.charAt(r13)
            if (r13 < r6) goto L_0x0103
            r13 = r13 & 8191(0x1fff, float:1.1478E-41)
            int r13 = r13 << r14
            r12 = r12 | r13
            int r14 = r14 + 13
            r13 = r15
            goto L_0x00f3
        L_0x0103:
            int r13 = r13 << r14
            r12 = r12 | r13
            r13 = r15
        L_0x0106:
            int r14 = r13 + 1
            char r13 = r1.charAt(r13)
            if (r13 < r6) goto L_0x0128
            r13 = r13 & 8191(0x1fff, float:1.1478E-41)
            r15 = 13
        L_0x0113:
            int r16 = r14 + 1
            char r14 = r1.charAt(r14)
            if (r14 < r6) goto L_0x0124
            r14 = r14 & 8191(0x1fff, float:1.1478E-41)
            int r14 = r14 << r15
            r13 = r13 | r14
            int r15 = r15 + 13
            r14 = r16
            goto L_0x0113
        L_0x0124:
            int r14 = r14 << r15
            r13 = r13 | r14
            r14 = r16
        L_0x0128:
            int r15 = r14 + 1
            char r14 = r1.charAt(r14)
            if (r14 < r6) goto L_0x014c
            r14 = r14 & 8191(0x1fff, float:1.1478E-41)
            r16 = 13
        L_0x0135:
            int r17 = r15 + 1
            char r15 = r1.charAt(r15)
            if (r15 < r6) goto L_0x0147
            r15 = r15 & 8191(0x1fff, float:1.1478E-41)
            int r15 = r15 << r16
            r14 = r14 | r15
            int r16 = r16 + 13
            r15 = r17
            goto L_0x0135
        L_0x0147:
            int r15 = r15 << r16
            r14 = r14 | r15
            r15 = r17
        L_0x014c:
            int r16 = r15 + 1
            char r15 = r1.charAt(r15)
            if (r15 < r6) goto L_0x0172
            r15 = r15 & 8191(0x1fff, float:1.1478E-41)
            r3 = r16
            r16 = 13
        L_0x015b:
            int r17 = r3 + 1
            char r3 = r1.charAt(r3)
            if (r3 < r6) goto L_0x016d
            r3 = r3 & 8191(0x1fff, float:1.1478E-41)
            int r3 = r3 << r16
            r15 = r15 | r3
            int r16 = r16 + 13
            r3 = r17
            goto L_0x015b
        L_0x016d:
            int r3 = r3 << r16
            r15 = r15 | r3
            r16 = r17
        L_0x0172:
            int r3 = r15 + r13
            int r3 = r3 + r14
            int[] r3 = new int[r3]
            int r14 = r5 << 1
            int r14 = r14 + r7
            r7 = r14
            r14 = r3
            r3 = r5
            r5 = r16
        L_0x0180:
            sun.misc.Unsafe r8 = zzb
            java.lang.Object[] r16 = r0.zze()
            com.google.android.gms.internal.vision.zzkk r17 = r0.zzc()
            java.lang.Class r6 = r17.getClass()
            int r4 = r12 * 3
            int[] r4 = new int[r4]
            r17 = 1
            int r12 = r12 << 1
            java.lang.Object[] r12 = new java.lang.Object[r12]
            int r19 = r15 + r13
            r21 = r15
            r22 = r19
            r13 = 0
            r20 = 0
        L_0x01a3:
            if (r5 >= r2) goto L_0x03f9
            int r23 = r5 + 1
            char r5 = r1.charAt(r5)
            r24 = r2
            r2 = 55296(0xd800, float:7.7486E-41)
            if (r5 < r2) goto L_0x01d7
            r5 = r5 & 8191(0x1fff, float:1.1478E-41)
            r2 = r23
            r23 = 13
        L_0x01b8:
            int r25 = r2 + 1
            char r2 = r1.charAt(r2)
            r26 = r15
            r15 = 55296(0xd800, float:7.7486E-41)
            if (r2 < r15) goto L_0x01d1
            r2 = r2 & 8191(0x1fff, float:1.1478E-41)
            int r2 = r2 << r23
            r5 = r5 | r2
            int r23 = r23 + 13
            r2 = r25
            r15 = r26
            goto L_0x01b8
        L_0x01d1:
            int r2 = r2 << r23
            r5 = r5 | r2
            r2 = r25
            goto L_0x01db
        L_0x01d7:
            r26 = r15
            r2 = r23
        L_0x01db:
            int r15 = r2 + 1
            char r2 = r1.charAt(r2)
            r23 = r15
            r15 = 55296(0xd800, float:7.7486E-41)
            if (r2 < r15) goto L_0x020e
            r2 = r2 & 8191(0x1fff, float:1.1478E-41)
            r15 = r23
            r23 = 13
        L_0x01ef:
            int r25 = r15 + 1
            char r15 = r1.charAt(r15)
            r27 = r10
            r10 = 55296(0xd800, float:7.7486E-41)
            if (r15 < r10) goto L_0x0208
            r10 = r15 & 8191(0x1fff, float:1.1478E-41)
            int r10 = r10 << r23
            r2 = r2 | r10
            int r23 = r23 + 13
            r15 = r25
            r10 = r27
            goto L_0x01ef
        L_0x0208:
            int r10 = r15 << r23
            r2 = r2 | r10
            r15 = r25
            goto L_0x0212
        L_0x020e:
            r27 = r10
            r15 = r23
        L_0x0212:
            r10 = r2 & 255(0xff, float:3.57E-43)
            r23 = r9
            r9 = r2 & 1024(0x400, float:1.435E-42)
            if (r9 == 0) goto L_0x0220
            int r9 = r13 + 1
            r14[r13] = r20
            r13 = r9
        L_0x0220:
            r9 = 51
            r28 = r13
            if (r10 < r9) goto L_0x02bf
            int r9 = r15 + 1
            char r15 = r1.charAt(r15)
            r13 = 55296(0xd800, float:7.7486E-41)
            if (r15 < r13) goto L_0x024f
            r15 = r15 & 8191(0x1fff, float:1.1478E-41)
            r29 = 13
        L_0x0235:
            int r30 = r9 + 1
            char r9 = r1.charAt(r9)
            if (r9 < r13) goto L_0x024a
            r9 = r9 & 8191(0x1fff, float:1.1478E-41)
            int r9 = r9 << r29
            r15 = r15 | r9
            int r29 = r29 + 13
            r9 = r30
            r13 = 55296(0xd800, float:7.7486E-41)
            goto L_0x0235
        L_0x024a:
            int r9 = r9 << r29
            r15 = r15 | r9
            r9 = r30
        L_0x024f:
            int r13 = r10 + -51
            r29 = r9
            r9 = 9
            if (r13 == r9) goto L_0x0273
            r9 = 17
            if (r13 != r9) goto L_0x025d
            goto L_0x0273
        L_0x025d:
            r9 = 12
            if (r13 != r9) goto L_0x0271
            if (r11 != 0) goto L_0x0271
            int r9 = r20 / 3
            r13 = 1
            int r9 = r9 << r13
            int r9 = r9 + r13
            int r13 = r7 + 1
            r7 = r16[r7]
            r12[r9] = r7
            r7 = r13
            r13 = 1
            goto L_0x0280
        L_0x0271:
            r13 = 1
            goto L_0x0280
        L_0x0273:
            int r9 = r20 / 3
            r13 = 1
            int r9 = r9 << r13
            int r9 = r9 + r13
            int r17 = r7 + 1
            r7 = r16[r7]
            r12[r9] = r7
            r7 = r17
        L_0x0280:
            int r9 = r15 << 1
            r13 = r16[r9]
            boolean r15 = r13 instanceof java.lang.reflect.Field
            if (r15 == 0) goto L_0x028b
            java.lang.reflect.Field r13 = (java.lang.reflect.Field) r13
            goto L_0x0293
        L_0x028b:
            java.lang.String r13 = (java.lang.String) r13
            java.lang.reflect.Field r13 = zza((java.lang.Class<?>) r6, (java.lang.String) r13)
            r16[r9] = r13
        L_0x0293:
            r30 = r4
            r31 = r5
            long r4 = r8.objectFieldOffset(r13)
            int r4 = (int) r4
            int r9 = r9 + 1
            r5 = r16[r9]
            boolean r13 = r5 instanceof java.lang.reflect.Field
            if (r13 == 0) goto L_0x02a7
            java.lang.reflect.Field r5 = (java.lang.reflect.Field) r5
            goto L_0x02af
        L_0x02a7:
            java.lang.String r5 = (java.lang.String) r5
            java.lang.reflect.Field r5 = zza((java.lang.Class<?>) r6, (java.lang.String) r5)
            r16[r9] = r5
        L_0x02af:
            r9 = r4
            long r4 = r8.objectFieldOffset(r5)
            int r4 = (int) r4
            r17 = r6
            r5 = r29
            r13 = 0
            r6 = r4
            r4 = r9
            goto L_0x03bd
        L_0x02bf:
            r30 = r4
            r31 = r5
            int r4 = r7 + 1
            r5 = r16[r7]
            java.lang.String r5 = (java.lang.String) r5
            java.lang.reflect.Field r5 = zza((java.lang.Class<?>) r6, (java.lang.String) r5)
            r7 = 49
            r9 = 9
            if (r10 == r9) goto L_0x0332
            r9 = 17
            if (r10 != r9) goto L_0x02d9
            r13 = 1
            goto L_0x0333
        L_0x02d9:
            r9 = 27
            if (r10 == r9) goto L_0x0324
            if (r10 != r7) goto L_0x02e0
            goto L_0x0324
        L_0x02e0:
            r9 = 12
            if (r10 == r9) goto L_0x0315
            r9 = 30
            if (r10 == r9) goto L_0x0315
            r9 = 44
            if (r10 != r9) goto L_0x02ed
            goto L_0x0315
        L_0x02ed:
            r9 = 50
            if (r10 != r9) goto L_0x033d
            int r9 = r21 + 1
            r14[r21] = r20
            int r13 = r20 / 3
            r17 = 1
            int r13 = r13 << 1
            int r21 = r4 + 1
            r4 = r16[r4]
            r12[r13] = r4
            r4 = r2 & 2048(0x800, float:2.87E-42)
            if (r4 == 0) goto L_0x0310
            int r13 = r13 + 1
            int r4 = r21 + 1
            r21 = r16[r21]
            r12[r13] = r21
            r21 = r9
            goto L_0x033d
        L_0x0310:
            r4 = r21
            r21 = r9
            goto L_0x033d
        L_0x0315:
            if (r11 != 0) goto L_0x033d
            int r9 = r20 / 3
            r13 = 1
            int r9 = r9 << r13
            int r9 = r9 + r13
            int r13 = r4 + 1
            r4 = r16[r4]
            r12[r9] = r4
            r4 = r13
            goto L_0x033d
        L_0x0324:
            int r9 = r20 / 3
            r13 = 1
            int r9 = r9 << r13
            int r9 = r9 + r13
            int r17 = r4 + 1
            r4 = r16[r4]
            r12[r9] = r4
            r4 = r17
            goto L_0x033d
        L_0x0332:
            r13 = 1
        L_0x0333:
            int r9 = r20 / 3
            int r9 = r9 << r13
            int r9 = r9 + r13
            java.lang.Class r13 = r5.getType()
            r12[r9] = r13
        L_0x033d:
            r9 = r4
            long r4 = r8.objectFieldOffset(r5)
            int r4 = (int) r4
            r5 = r2 & 4096(0x1000, float:5.74E-42)
            r13 = 4096(0x1000, float:5.74E-42)
            if (r5 != r13) goto L_0x03a3
            r5 = 17
            if (r10 > r5) goto L_0x039d
            int r5 = r15 + 1
            char r13 = r1.charAt(r15)
            r15 = 55296(0xd800, float:7.7486E-41)
            if (r13 < r15) goto L_0x0372
            r13 = r13 & 8191(0x1fff, float:1.1478E-41)
            r18 = 13
        L_0x035c:
            int r25 = r5 + 1
            char r5 = r1.charAt(r5)
            if (r5 < r15) goto L_0x036e
            r5 = r5 & 8191(0x1fff, float:1.1478E-41)
            int r5 = r5 << r18
            r13 = r13 | r5
            int r18 = r18 + 13
            r5 = r25
            goto L_0x035c
        L_0x036e:
            int r5 = r5 << r18
            r13 = r13 | r5
            goto L_0x0374
        L_0x0372:
            r25 = r5
        L_0x0374:
            r5 = 1
            int r17 = r3 << 1
            int r18 = r13 / 32
            int r17 = r17 + r18
            r5 = r16[r17]
            boolean r15 = r5 instanceof java.lang.reflect.Field
            if (r15 == 0) goto L_0x0385
            java.lang.reflect.Field r5 = (java.lang.reflect.Field) r5
            goto L_0x038d
        L_0x0385:
            java.lang.String r5 = (java.lang.String) r5
            java.lang.reflect.Field r5 = zza((java.lang.Class<?>) r6, (java.lang.String) r5)
            r16[r17] = r5
        L_0x038d:
            r17 = r6
            long r5 = r8.objectFieldOffset(r5)
            int r5 = (int) r5
            int r13 = r13 % 32
            r6 = r5
            r15 = r25
            r5 = 55296(0xd800, float:7.7486E-41)
            goto L_0x03ac
        L_0x039d:
            r17 = r6
            r5 = 55296(0xd800, float:7.7486E-41)
            goto L_0x03a8
        L_0x03a3:
            r17 = r6
            r5 = 55296(0xd800, float:7.7486E-41)
        L_0x03a8:
            r6 = 1048575(0xfffff, float:1.469367E-39)
            r13 = 0
        L_0x03ac:
            r5 = 18
            if (r10 < r5) goto L_0x03bb
            if (r10 > r7) goto L_0x03bb
            int r5 = r22 + 1
            r14[r22] = r4
            r22 = r5
            r7 = r9
            r5 = r15
            goto L_0x03bd
        L_0x03bb:
            r7 = r9
            r5 = r15
        L_0x03bd:
            int r9 = r20 + 1
            r30[r20] = r31
            int r15 = r9 + 1
            r20 = r1
            r1 = r2 & 512(0x200, float:7.175E-43)
            if (r1 == 0) goto L_0x03cc
            r1 = 536870912(0x20000000, float:1.0842022E-19)
            goto L_0x03cd
        L_0x03cc:
            r1 = 0
        L_0x03cd:
            r2 = r2 & 256(0x100, float:3.59E-43)
            if (r2 == 0) goto L_0x03d4
            r2 = 268435456(0x10000000, float:2.5243549E-29)
            goto L_0x03d5
        L_0x03d4:
            r2 = 0
        L_0x03d5:
            r1 = r1 | r2
            int r2 = r10 << 20
            r1 = r1 | r2
            r1 = r1 | r4
            r30[r9] = r1
            int r1 = r15 + 1
            int r2 = r13 << 20
            r2 = r2 | r6
            r30[r15] = r2
            r6 = r17
            r9 = r23
            r2 = r24
            r15 = r26
            r10 = r27
            r13 = r28
            r4 = r30
            r32 = r20
            r20 = r1
            r1 = r32
            goto L_0x01a3
        L_0x03f9:
            r30 = r4
            r23 = r9
            r27 = r10
            r26 = r15
            com.google.android.gms.internal.vision.zzko r1 = new com.google.android.gms.internal.vision.zzko
            com.google.android.gms.internal.vision.zzkk r10 = r0.zzc()
            r0 = 0
            r5 = r1
            r6 = r30
            r7 = r12
            r8 = r23
            r9 = r27
            r12 = r0
            r13 = r14
            r14 = r26
            r15 = r19
            r16 = r35
            r17 = r36
            r18 = r37
            r19 = r38
            r20 = r39
            r5.<init>(r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
            return r1
        L_0x0424:
            com.google.android.gms.internal.vision.zzlr r0 = (com.google.android.gms.internal.vision.zzlr) r0
            int r0 = r0.zza()
            int r1 = com.google.android.gms.internal.vision.zzkz.zzb
            java.lang.NoSuchMethodError r0 = new java.lang.NoSuchMethodError
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zza(java.lang.Class, com.google.android.gms.internal.vision.zzki, com.google.android.gms.internal.vision.zzks, com.google.android.gms.internal.vision.zzju, com.google.android.gms.internal.vision.zzlu, com.google.android.gms.internal.vision.zziq, com.google.android.gms.internal.vision.zzkh):com.google.android.gms.internal.vision.zzko");
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException e) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String arrays = Arrays.toString(declaredFields);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 40 + String.valueOf(name).length() + String.valueOf(arrays).length());
            sb.append("Field ");
            sb.append(str);
            sb.append(" for ");
            sb.append(name);
            sb.append(" not found. Known fields are ");
            sb.append(arrays);
            throw new RuntimeException(sb.toString());
        }
    }

    public final T zza() {
        return this.zzo.zza(this.zzg);
    }

    public final boolean zza(T t, T t2) {
        int length = this.zzc.length;
        int i = 0;
        while (true) {
            boolean z = true;
            if (i < length) {
                int zzd2 = zzd(i);
                long j = (long) (zzd2 & 1048575);
                switch ((zzd2 & 267386880) >>> 20) {
                    case 0:
                        if (!zzc(t, t2, i) || Double.doubleToLongBits(zzma.zze(t, j)) != Double.doubleToLongBits(zzma.zze(t2, j))) {
                            z = false;
                            break;
                        }
                    case 1:
                        if (!zzc(t, t2, i) || Float.floatToIntBits(zzma.zzd(t, j)) != Float.floatToIntBits(zzma.zzd(t2, j))) {
                            z = false;
                            break;
                        }
                    case 2:
                        if (!zzc(t, t2, i) || zzma.zzb(t, j) != zzma.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 3:
                        if (!zzc(t, t2, i) || zzma.zzb(t, j) != zzma.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 4:
                        if (!zzc(t, t2, i) || zzma.zza((Object) t, j) != zzma.zza((Object) t2, j)) {
                            z = false;
                            break;
                        }
                    case 5:
                        if (!zzc(t, t2, i) || zzma.zzb(t, j) != zzma.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 6:
                        if (!zzc(t, t2, i) || zzma.zza((Object) t, j) != zzma.zza((Object) t2, j)) {
                            z = false;
                            break;
                        }
                    case 7:
                        if (!zzc(t, t2, i) || zzma.zzc(t, j) != zzma.zzc(t2, j)) {
                            z = false;
                            break;
                        }
                    case 8:
                        if (!zzc(t, t2, i) || !zzle.zza(zzma.zzf(t, j), zzma.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                    case 9:
                        if (!zzc(t, t2, i) || !zzle.zza(zzma.zzf(t, j), zzma.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                    case 10:
                        if (!zzc(t, t2, i) || !zzle.zza(zzma.zzf(t, j), zzma.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                    case 11:
                        if (!zzc(t, t2, i) || zzma.zza((Object) t, j) != zzma.zza((Object) t2, j)) {
                            z = false;
                            break;
                        }
                    case 12:
                        if (!zzc(t, t2, i) || zzma.zza((Object) t, j) != zzma.zza((Object) t2, j)) {
                            z = false;
                            break;
                        }
                    case 13:
                        if (!zzc(t, t2, i) || zzma.zza((Object) t, j) != zzma.zza((Object) t2, j)) {
                            z = false;
                            break;
                        }
                    case 14:
                        if (!zzc(t, t2, i) || zzma.zzb(t, j) != zzma.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 15:
                        if (!zzc(t, t2, i) || zzma.zza((Object) t, j) != zzma.zza((Object) t2, j)) {
                            z = false;
                            break;
                        }
                    case 16:
                        if (!zzc(t, t2, i) || zzma.zzb(t, j) != zzma.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 17:
                        if (!zzc(t, t2, i) || !zzle.zza(zzma.zzf(t, j), zzma.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                        z = zzle.zza(zzma.zzf(t, j), zzma.zzf(t2, j));
                        break;
                    case 50:
                        z = zzle.zza(zzma.zzf(t, j), zzma.zzf(t2, j));
                        break;
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                    case 60:
                    case 61:
                    case 62:
                    case 63:
                    case 64:
                    case 65:
                    case 66:
                    case 67:
                    case 68:
                        long zze2 = (long) (zze(i) & 1048575);
                        if (zzma.zza((Object) t, zze2) != zzma.zza((Object) t2, zze2) || !zzle.zza(zzma.zzf(t, j), zzma.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                }
                if (!z) {
                    return false;
                }
                i += 3;
            } else if (!this.zzq.zzb(t).equals(this.zzq.zzb(t2))) {
                return false;
            } else {
                if (this.zzh) {
                    return this.zzr.zza((Object) t).equals(this.zzr.zza((Object) t2));
                }
                return true;
            }
        }
    }

    public final int zza(T t) {
        int length = this.zzc.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2 += 3) {
            int zzd2 = zzd(i2);
            int i3 = this.zzc[i2];
            long j = (long) (1048575 & zzd2);
            int i4 = 37;
            switch ((zzd2 & 267386880) >>> 20) {
                case 0:
                    i = (i * 53) + zzjf.zza(Double.doubleToLongBits(zzma.zze(t, j)));
                    break;
                case 1:
                    i = (i * 53) + Float.floatToIntBits(zzma.zzd(t, j));
                    break;
                case 2:
                    i = (i * 53) + zzjf.zza(zzma.zzb(t, j));
                    break;
                case 3:
                    i = (i * 53) + zzjf.zza(zzma.zzb(t, j));
                    break;
                case 4:
                    i = (i * 53) + zzma.zza((Object) t, j);
                    break;
                case 5:
                    i = (i * 53) + zzjf.zza(zzma.zzb(t, j));
                    break;
                case 6:
                    i = (i * 53) + zzma.zza((Object) t, j);
                    break;
                case 7:
                    i = (i * 53) + zzjf.zza(zzma.zzc(t, j));
                    break;
                case 8:
                    i = (i * 53) + ((String) zzma.zzf(t, j)).hashCode();
                    break;
                case 9:
                    Object zzf2 = zzma.zzf(t, j);
                    if (zzf2 != null) {
                        i4 = zzf2.hashCode();
                    }
                    i = (i * 53) + i4;
                    break;
                case 10:
                    i = (i * 53) + zzma.zzf(t, j).hashCode();
                    break;
                case 11:
                    i = (i * 53) + zzma.zza((Object) t, j);
                    break;
                case 12:
                    i = (i * 53) + zzma.zza((Object) t, j);
                    break;
                case 13:
                    i = (i * 53) + zzma.zza((Object) t, j);
                    break;
                case 14:
                    i = (i * 53) + zzjf.zza(zzma.zzb(t, j));
                    break;
                case 15:
                    i = (i * 53) + zzma.zza((Object) t, j);
                    break;
                case 16:
                    i = (i * 53) + zzjf.zza(zzma.zzb(t, j));
                    break;
                case 17:
                    Object zzf3 = zzma.zzf(t, j);
                    if (zzf3 != null) {
                        i4 = zzf3.hashCode();
                    }
                    i = (i * 53) + i4;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = (i * 53) + zzma.zzf(t, j).hashCode();
                    break;
                case 50:
                    i = (i * 53) + zzma.zzf(t, j).hashCode();
                    break;
                case 51:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzjf.zza(Double.doubleToLongBits(zzb(t, j)));
                        break;
                    }
                case 52:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + Float.floatToIntBits(zzc(t, j));
                        break;
                    }
                case 53:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzjf.zza(zze(t, j));
                        break;
                    }
                case 54:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzjf.zza(zze(t, j));
                        break;
                    }
                case 55:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 56:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzjf.zza(zze(t, j));
                        break;
                    }
                case 57:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 58:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzjf.zza(zzf(t, j));
                        break;
                    }
                case 59:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + ((String) zzma.zzf(t, j)).hashCode();
                        break;
                    }
                case 60:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzma.zzf(t, j).hashCode();
                        break;
                    }
                case 61:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzma.zzf(t, j).hashCode();
                        break;
                    }
                case 62:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 63:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 64:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 65:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzjf.zza(zze(t, j));
                        break;
                    }
                case 66:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 67:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzjf.zza(zze(t, j));
                        break;
                    }
                case 68:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzma.zzf(t, j).hashCode();
                        break;
                    }
            }
        }
        int hashCode = (i * 53) + this.zzq.zzb(t).hashCode();
        if (this.zzh) {
            return (hashCode * 53) + this.zzr.zza((Object) t).hashCode();
        }
        return hashCode;
    }

    public final void zzb(T t, T t2) {
        if (t2 != null) {
            for (int i = 0; i < this.zzc.length; i += 3) {
                int zzd2 = zzd(i);
                long j = (long) (1048575 & zzd2);
                int i2 = this.zzc[i];
                switch ((zzd2 & 267386880) >>> 20) {
                    case 0:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzma.zza((Object) t, j, zzma.zze(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 1:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzma.zza((Object) t, j, zzma.zzd(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 2:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzma.zza((Object) t, j, zzma.zzb(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 3:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzma.zza((Object) t, j, zzma.zzb(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 4:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzma.zza((Object) t, j, zzma.zza((Object) t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 5:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzma.zza((Object) t, j, zzma.zzb(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 6:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzma.zza((Object) t, j, zzma.zza((Object) t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 7:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzma.zza((Object) t, j, zzma.zzc(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 8:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzma.zza((Object) t, j, zzma.zzf(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 9:
                        zza(t, t2, i);
                        break;
                    case 10:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzma.zza((Object) t, j, zzma.zzf(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 11:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzma.zza((Object) t, j, zzma.zza((Object) t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 12:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzma.zza((Object) t, j, zzma.zza((Object) t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 13:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzma.zza((Object) t, j, zzma.zza((Object) t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 14:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzma.zza((Object) t, j, zzma.zzb(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 15:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzma.zza((Object) t, j, zzma.zza((Object) t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 16:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzma.zza((Object) t, j, zzma.zzb(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 17:
                        zza(t, t2, i);
                        break;
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                        this.zzp.zza(t, t2, j);
                        break;
                    case 50:
                        zzle.zza(this.zzs, t, t2, j);
                        break;
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                        if (!zza(t2, i2, i)) {
                            break;
                        } else {
                            zzma.zza((Object) t, j, zzma.zzf(t2, j));
                            zzb(t, i2, i);
                            break;
                        }
                    case 60:
                        zzb(t, t2, i);
                        break;
                    case 61:
                    case 62:
                    case 63:
                    case 64:
                    case 65:
                    case 66:
                    case 67:
                        if (!zza(t2, i2, i)) {
                            break;
                        } else {
                            zzma.zza((Object) t, j, zzma.zzf(t2, j));
                            zzb(t, i2, i);
                            break;
                        }
                    case 68:
                        zzb(t, t2, i);
                        break;
                }
            }
            zzle.zza(this.zzq, t, t2);
            if (this.zzh) {
                zzle.zza(this.zzr, t, t2);
                return;
            }
            return;
        }
        throw new NullPointerException();
    }

    private final void zza(T t, T t2, int i) {
        long zzd2 = (long) (zzd(i) & 1048575);
        if (zza(t2, i)) {
            Object zzf2 = zzma.zzf(t, zzd2);
            Object zzf3 = zzma.zzf(t2, zzd2);
            if (zzf2 != null && zzf3 != null) {
                zzma.zza((Object) t, zzd2, zzjf.zza(zzf2, zzf3));
                zzb(t, i);
            } else if (zzf3 != null) {
                zzma.zza((Object) t, zzd2, zzf3);
                zzb(t, i);
            }
        }
    }

    private final void zzb(T t, T t2, int i) {
        int zzd2 = zzd(i);
        int i2 = this.zzc[i];
        long j = (long) (zzd2 & 1048575);
        if (zza(t2, i2, i)) {
            Object obj = null;
            if (zza(t, i2, i)) {
                obj = zzma.zzf(t, j);
            }
            Object zzf2 = zzma.zzf(t2, j);
            if (obj != null && zzf2 != null) {
                zzma.zza((Object) t, j, zzjf.zza(obj, zzf2));
                zzb(t, i2, i);
            } else if (zzf2 != null) {
                zzma.zza((Object) t, j, zzf2);
                zzb(t, i2, i);
            }
        }
    }

    public final int zzb(T t) {
        int i;
        long j;
        int i2;
        boolean z;
        T t2 = t;
        int i3 = 267386880;
        int i4 = 1048575;
        boolean z2 = true;
        int i5 = 0;
        if (this.zzj) {
            Unsafe unsafe = zzb;
            int i6 = 0;
            int i7 = 0;
            while (i6 < this.zzc.length) {
                int zzd2 = zzd(i6);
                int i8 = (zzd2 & i3) >>> 20;
                int i9 = this.zzc[i6];
                long j2 = (long) (zzd2 & 1048575);
                if (i8 >= zziv.DOUBLE_LIST_PACKED.zza() && i8 <= zziv.SINT64_LIST_PACKED.zza()) {
                    int i10 = this.zzc[i6 + 2];
                }
                switch (i8) {
                    case 0:
                        if (!zza(t2, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzb(i9, 0.0d);
                            break;
                        }
                    case 1:
                        if (!zza(t2, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzb(i9, 0.0f);
                            break;
                        }
                    case 2:
                        if (!zza(t2, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzd(i9, zzma.zzb(t2, j2));
                            break;
                        }
                    case 3:
                        if (!zza(t2, i6)) {
                            break;
                        } else {
                            i7 += zzii.zze(i9, zzma.zzb(t2, j2));
                            break;
                        }
                    case 4:
                        if (!zza(t2, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzf(i9, zzma.zza((Object) t2, j2));
                            break;
                        }
                    case 5:
                        if (!zza(t2, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzg(i9, 0);
                            break;
                        }
                    case 6:
                        if (!zza(t2, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzi(i9, 0);
                            break;
                        }
                    case 7:
                        if (!zza(t2, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzb(i9, true);
                            break;
                        }
                    case 8:
                        if (!zza(t2, i6)) {
                            break;
                        } else {
                            Object zzf2 = zzma.zzf(t2, j2);
                            if (!(zzf2 instanceof zzht)) {
                                i7 += zzii.zzb(i9, (String) zzf2);
                                break;
                            } else {
                                i7 += zzii.zzc(i9, (zzht) zzf2);
                                break;
                            }
                        }
                    case 9:
                        if (!zza(t2, i6)) {
                            break;
                        } else {
                            i7 += zzle.zza(i9, zzma.zzf(t2, j2), zza(i6));
                            break;
                        }
                    case 10:
                        if (!zza(t2, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzc(i9, (zzht) zzma.zzf(t2, j2));
                            break;
                        }
                    case 11:
                        if (!zza(t2, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzg(i9, zzma.zza((Object) t2, j2));
                            break;
                        }
                    case 12:
                        if (!zza(t2, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzk(i9, zzma.zza((Object) t2, j2));
                            break;
                        }
                    case 13:
                        if (!zza(t2, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzj(i9, 0);
                            break;
                        }
                    case 14:
                        if (!zza(t2, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzh(i9, 0);
                            break;
                        }
                    case 15:
                        if (!zza(t2, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzh(i9, zzma.zza((Object) t2, j2));
                            break;
                        }
                    case 16:
                        if (!zza(t2, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzf(i9, zzma.zzb(t2, j2));
                            break;
                        }
                    case 17:
                        if (!zza(t2, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzc(i9, (zzkk) zzma.zzf(t2, j2), zza(i6));
                            break;
                        }
                    case 18:
                        i7 += zzle.zzi(i9, zza((Object) t2, j2), false);
                        break;
                    case 19:
                        i7 += zzle.zzh(i9, zza((Object) t2, j2), false);
                        break;
                    case 20:
                        i7 += zzle.zza(i9, (List<Long>) zza((Object) t2, j2), false);
                        break;
                    case 21:
                        i7 += zzle.zzb(i9, (List<Long>) zza((Object) t2, j2), false);
                        break;
                    case 22:
                        i7 += zzle.zze(i9, zza((Object) t2, j2), false);
                        break;
                    case 23:
                        i7 += zzle.zzi(i9, zza((Object) t2, j2), false);
                        break;
                    case 24:
                        i7 += zzle.zzh(i9, zza((Object) t2, j2), false);
                        break;
                    case 25:
                        i7 += zzle.zzj(i9, zza((Object) t2, j2), false);
                        break;
                    case 26:
                        i7 += zzle.zza(i9, zza((Object) t2, j2));
                        break;
                    case 27:
                        i7 += zzle.zza(i9, zza((Object) t2, j2), zza(i6));
                        break;
                    case 28:
                        i7 += zzle.zzb(i9, zza((Object) t2, j2));
                        break;
                    case 29:
                        i7 += zzle.zzf(i9, zza((Object) t2, j2), false);
                        break;
                    case 30:
                        i7 += zzle.zzd(i9, zza((Object) t2, j2), false);
                        break;
                    case 31:
                        i7 += zzle.zzh(i9, zza((Object) t2, j2), false);
                        break;
                    case 32:
                        i7 += zzle.zzi(i9, zza((Object) t2, j2), false);
                        break;
                    case 33:
                        i7 += zzle.zzg(i9, zza((Object) t2, j2), false);
                        break;
                    case 34:
                        i7 += zzle.zzc(i9, zza((Object) t2, j2), false);
                        break;
                    case 35:
                        int zzi2 = zzle.zzi((List) unsafe.getObject(t2, j2));
                        if (zzi2 <= 0) {
                            break;
                        } else {
                            i7 += zzii.zze(i9) + zzii.zzg(zzi2) + zzi2;
                            break;
                        }
                    case 36:
                        int zzh2 = zzle.zzh((List) unsafe.getObject(t2, j2));
                        if (zzh2 <= 0) {
                            break;
                        } else {
                            i7 += zzii.zze(i9) + zzii.zzg(zzh2) + zzh2;
                            break;
                        }
                    case 37:
                        int zza2 = zzle.zza((List<Long>) (List) unsafe.getObject(t2, j2));
                        if (zza2 <= 0) {
                            break;
                        } else {
                            i7 += zzii.zze(i9) + zzii.zzg(zza2) + zza2;
                            break;
                        }
                    case 38:
                        int zzb2 = zzle.zzb((List) unsafe.getObject(t2, j2));
                        if (zzb2 <= 0) {
                            break;
                        } else {
                            i7 += zzii.zze(i9) + zzii.zzg(zzb2) + zzb2;
                            break;
                        }
                    case 39:
                        int zze2 = zzle.zze((List) unsafe.getObject(t2, j2));
                        if (zze2 <= 0) {
                            break;
                        } else {
                            i7 += zzii.zze(i9) + zzii.zzg(zze2) + zze2;
                            break;
                        }
                    case 40:
                        int zzi3 = zzle.zzi((List) unsafe.getObject(t2, j2));
                        if (zzi3 <= 0) {
                            break;
                        } else {
                            i7 += zzii.zze(i9) + zzii.zzg(zzi3) + zzi3;
                            break;
                        }
                    case 41:
                        int zzh3 = zzle.zzh((List) unsafe.getObject(t2, j2));
                        if (zzh3 <= 0) {
                            break;
                        } else {
                            i7 += zzii.zze(i9) + zzii.zzg(zzh3) + zzh3;
                            break;
                        }
                    case 42:
                        int zzj2 = zzle.zzj((List) unsafe.getObject(t2, j2));
                        if (zzj2 <= 0) {
                            break;
                        } else {
                            i7 += zzii.zze(i9) + zzii.zzg(zzj2) + zzj2;
                            break;
                        }
                    case 43:
                        int zzf3 = zzle.zzf((List) unsafe.getObject(t2, j2));
                        if (zzf3 <= 0) {
                            break;
                        } else {
                            i7 += zzii.zze(i9) + zzii.zzg(zzf3) + zzf3;
                            break;
                        }
                    case 44:
                        int zzd3 = zzle.zzd((List) unsafe.getObject(t2, j2));
                        if (zzd3 <= 0) {
                            break;
                        } else {
                            i7 += zzii.zze(i9) + zzii.zzg(zzd3) + zzd3;
                            break;
                        }
                    case 45:
                        int zzh4 = zzle.zzh((List) unsafe.getObject(t2, j2));
                        if (zzh4 <= 0) {
                            break;
                        } else {
                            i7 += zzii.zze(i9) + zzii.zzg(zzh4) + zzh4;
                            break;
                        }
                    case 46:
                        int zzi4 = zzle.zzi((List) unsafe.getObject(t2, j2));
                        if (zzi4 <= 0) {
                            break;
                        } else {
                            i7 += zzii.zze(i9) + zzii.zzg(zzi4) + zzi4;
                            break;
                        }
                    case 47:
                        int zzg2 = zzle.zzg((List) unsafe.getObject(t2, j2));
                        if (zzg2 <= 0) {
                            break;
                        } else {
                            i7 += zzii.zze(i9) + zzii.zzg(zzg2) + zzg2;
                            break;
                        }
                    case 48:
                        int zzc2 = zzle.zzc((List) unsafe.getObject(t2, j2));
                        if (zzc2 <= 0) {
                            break;
                        } else {
                            i7 += zzii.zze(i9) + zzii.zzg(zzc2) + zzc2;
                            break;
                        }
                    case 49:
                        i7 += zzle.zzb(i9, (List<zzkk>) zza((Object) t2, j2), zza(i6));
                        break;
                    case 50:
                        i7 += this.zzs.zza(i9, zzma.zzf(t2, j2), zzb(i6));
                        break;
                    case 51:
                        if (!zza(t2, i9, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzb(i9, 0.0d);
                            break;
                        }
                    case 52:
                        if (!zza(t2, i9, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzb(i9, 0.0f);
                            break;
                        }
                    case 53:
                        if (!zza(t2, i9, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzd(i9, zze(t2, j2));
                            break;
                        }
                    case 54:
                        if (!zza(t2, i9, i6)) {
                            break;
                        } else {
                            i7 += zzii.zze(i9, zze(t2, j2));
                            break;
                        }
                    case 55:
                        if (!zza(t2, i9, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzf(i9, zzd(t2, j2));
                            break;
                        }
                    case 56:
                        if (!zza(t2, i9, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzg(i9, 0);
                            break;
                        }
                    case 57:
                        if (!zza(t2, i9, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzi(i9, 0);
                            break;
                        }
                    case 58:
                        if (!zza(t2, i9, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzb(i9, true);
                            break;
                        }
                    case 59:
                        if (!zza(t2, i9, i6)) {
                            break;
                        } else {
                            Object zzf4 = zzma.zzf(t2, j2);
                            if (!(zzf4 instanceof zzht)) {
                                i7 += zzii.zzb(i9, (String) zzf4);
                                break;
                            } else {
                                i7 += zzii.zzc(i9, (zzht) zzf4);
                                break;
                            }
                        }
                    case 60:
                        if (!zza(t2, i9, i6)) {
                            break;
                        } else {
                            i7 += zzle.zza(i9, zzma.zzf(t2, j2), zza(i6));
                            break;
                        }
                    case 61:
                        if (!zza(t2, i9, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzc(i9, (zzht) zzma.zzf(t2, j2));
                            break;
                        }
                    case 62:
                        if (!zza(t2, i9, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzg(i9, zzd(t2, j2));
                            break;
                        }
                    case 63:
                        if (!zza(t2, i9, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzk(i9, zzd(t2, j2));
                            break;
                        }
                    case 64:
                        if (!zza(t2, i9, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzj(i9, 0);
                            break;
                        }
                    case 65:
                        if (!zza(t2, i9, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzh(i9, 0);
                            break;
                        }
                    case 66:
                        if (!zza(t2, i9, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzh(i9, zzd(t2, j2));
                            break;
                        }
                    case 67:
                        if (!zza(t2, i9, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzf(i9, zze(t2, j2));
                            break;
                        }
                    case 68:
                        if (!zza(t2, i9, i6)) {
                            break;
                        } else {
                            i7 += zzii.zzc(i9, (zzkk) zzma.zzf(t2, j2), zza(i6));
                            break;
                        }
                }
                i6 += 3;
                i3 = 267386880;
            }
            return i7 + zza(this.zzq, t2);
        }
        Unsafe unsafe2 = zzb;
        int i11 = 1048575;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        while (i12 < this.zzc.length) {
            int zzd4 = zzd(i12);
            int[] iArr = this.zzc;
            int i15 = iArr[i12];
            int i16 = (zzd4 & 267386880) >>> 20;
            if (i16 <= 17) {
                int i17 = iArr[i12 + 2];
                int i18 = i17 & i4;
                i = (z2 ? 1 : 0) << (i17 >>> 20);
                if (i18 != i11) {
                    i14 = unsafe2.getInt(t2, (long) i18);
                    i11 = i18;
                }
            } else {
                i = 0;
            }
            long j3 = (long) (zzd4 & i4);
            switch (i16) {
                case 0:
                    z = true;
                    i2 = 0;
                    j = 0;
                    if ((i14 & i) == 0) {
                        break;
                    } else {
                        i13 += zzii.zzb(i15, 0.0d);
                        break;
                    }
                case 1:
                    z = true;
                    i2 = 0;
                    j = 0;
                    if ((i14 & i) == 0) {
                        break;
                    } else {
                        i13 += zzii.zzb(i15, 0.0f);
                        break;
                    }
                case 2:
                    z = true;
                    i2 = 0;
                    j = 0;
                    if ((i & i14) == 0) {
                        break;
                    } else {
                        i13 += zzii.zzd(i15, unsafe2.getLong(t2, j3));
                        break;
                    }
                case 3:
                    z = true;
                    i2 = 0;
                    j = 0;
                    if ((i & i14) == 0) {
                        break;
                    } else {
                        i13 += zzii.zze(i15, unsafe2.getLong(t2, j3));
                        break;
                    }
                case 4:
                    z = true;
                    i2 = 0;
                    j = 0;
                    if ((i & i14) == 0) {
                        break;
                    } else {
                        i13 += zzii.zzf(i15, unsafe2.getInt(t2, j3));
                        break;
                    }
                case 5:
                    z = true;
                    i2 = 0;
                    if ((i14 & i) == 0) {
                        j = 0;
                        break;
                    } else {
                        j = 0;
                        i13 += zzii.zzg(i15, 0);
                        break;
                    }
                case 6:
                    z = true;
                    if ((i14 & i) == 0) {
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i2 = 0;
                        i13 += zzii.zzi(i15, 0);
                        j = 0;
                        break;
                    }
                case 7:
                    if ((i14 & i) == 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        z = true;
                        i13 += zzii.zzb(i15, true);
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 8:
                    if ((i14 & i) == 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        Object object = unsafe2.getObject(t2, j3);
                        if (!(object instanceof zzht)) {
                            i13 += zzii.zzb(i15, (String) object);
                            z = true;
                            i2 = 0;
                            j = 0;
                            break;
                        } else {
                            i13 += zzii.zzc(i15, (zzht) object);
                            z = true;
                            i2 = 0;
                            j = 0;
                            break;
                        }
                    }
                case 9:
                    if ((i14 & i) == 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzle.zza(i15, unsafe2.getObject(t2, j3), zza(i12));
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 10:
                    if ((i14 & i) == 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzc(i15, (zzht) unsafe2.getObject(t2, j3));
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 11:
                    if ((i14 & i) == 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzg(i15, unsafe2.getInt(t2, j3));
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 12:
                    if ((i14 & i) == 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzk(i15, unsafe2.getInt(t2, j3));
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 13:
                    if ((i14 & i) == 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzj(i15, 0);
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 14:
                    if ((i14 & i) == 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzh(i15, 0);
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 15:
                    if ((i14 & i) == 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzh(i15, unsafe2.getInt(t2, j3));
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 16:
                    if ((i14 & i) == 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzf(i15, unsafe2.getLong(t2, j3));
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 17:
                    if ((i14 & i) == 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzc(i15, (zzkk) unsafe2.getObject(t2, j3), zza(i12));
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 18:
                    i13 += zzle.zzi(i15, (List) unsafe2.getObject(t2, j3), false);
                    z = true;
                    i2 = 0;
                    j = 0;
                    break;
                case 19:
                    i2 = 0;
                    i13 += zzle.zzh(i15, (List) unsafe2.getObject(t2, j3), false);
                    z = true;
                    j = 0;
                    break;
                case 20:
                    i2 = 0;
                    i13 += zzle.zza(i15, (List<Long>) (List) unsafe2.getObject(t2, j3), false);
                    z = true;
                    j = 0;
                    break;
                case 21:
                    i2 = 0;
                    i13 += zzle.zzb(i15, (List<Long>) (List) unsafe2.getObject(t2, j3), false);
                    z = true;
                    j = 0;
                    break;
                case 22:
                    i2 = 0;
                    i13 += zzle.zze(i15, (List) unsafe2.getObject(t2, j3), false);
                    z = true;
                    j = 0;
                    break;
                case 23:
                    i2 = 0;
                    i13 += zzle.zzi(i15, (List) unsafe2.getObject(t2, j3), false);
                    z = true;
                    j = 0;
                    break;
                case 24:
                    i2 = 0;
                    i13 += zzle.zzh(i15, (List) unsafe2.getObject(t2, j3), false);
                    z = true;
                    j = 0;
                    break;
                case 25:
                    i2 = 0;
                    i13 += zzle.zzj(i15, (List) unsafe2.getObject(t2, j3), false);
                    z = true;
                    j = 0;
                    break;
                case 26:
                    i13 += zzle.zza(i15, (List<?>) (List) unsafe2.getObject(t2, j3));
                    z = true;
                    i2 = 0;
                    j = 0;
                    break;
                case 27:
                    i13 += zzle.zza(i15, (List<?>) (List) unsafe2.getObject(t2, j3), zza(i12));
                    z = true;
                    i2 = 0;
                    j = 0;
                    break;
                case 28:
                    i13 += zzle.zzb(i15, (List) unsafe2.getObject(t2, j3));
                    z = true;
                    i2 = 0;
                    j = 0;
                    break;
                case 29:
                    i13 += zzle.zzf(i15, (List) unsafe2.getObject(t2, j3), false);
                    z = true;
                    i2 = 0;
                    j = 0;
                    break;
                case 30:
                    i2 = 0;
                    i13 += zzle.zzd(i15, (List) unsafe2.getObject(t2, j3), false);
                    z = true;
                    j = 0;
                    break;
                case 31:
                    i2 = 0;
                    i13 += zzle.zzh(i15, (List) unsafe2.getObject(t2, j3), false);
                    z = true;
                    j = 0;
                    break;
                case 32:
                    i2 = 0;
                    i13 += zzle.zzi(i15, (List) unsafe2.getObject(t2, j3), false);
                    z = true;
                    j = 0;
                    break;
                case 33:
                    i2 = 0;
                    i13 += zzle.zzg(i15, (List) unsafe2.getObject(t2, j3), false);
                    z = true;
                    j = 0;
                    break;
                case 34:
                    i2 = 0;
                    i13 += zzle.zzc(i15, (List) unsafe2.getObject(t2, j3), false);
                    z = true;
                    j = 0;
                    break;
                case 35:
                    int zzi5 = zzle.zzi((List) unsafe2.getObject(t2, j3));
                    if (zzi5 <= 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zze(i15) + zzii.zzg(zzi5) + zzi5;
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 36:
                    int zzh5 = zzle.zzh((List) unsafe2.getObject(t2, j3));
                    if (zzh5 <= 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zze(i15) + zzii.zzg(zzh5) + zzh5;
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 37:
                    int zza3 = zzle.zza((List<Long>) (List) unsafe2.getObject(t2, j3));
                    if (zza3 <= 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zze(i15) + zzii.zzg(zza3) + zza3;
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 38:
                    int zzb3 = zzle.zzb((List) unsafe2.getObject(t2, j3));
                    if (zzb3 <= 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zze(i15) + zzii.zzg(zzb3) + zzb3;
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 39:
                    int zze3 = zzle.zze((List) unsafe2.getObject(t2, j3));
                    if (zze3 <= 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zze(i15) + zzii.zzg(zze3) + zze3;
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 40:
                    int zzi6 = zzle.zzi((List) unsafe2.getObject(t2, j3));
                    if (zzi6 <= 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zze(i15) + zzii.zzg(zzi6) + zzi6;
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 41:
                    int zzh6 = zzle.zzh((List) unsafe2.getObject(t2, j3));
                    if (zzh6 <= 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zze(i15) + zzii.zzg(zzh6) + zzh6;
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 42:
                    int zzj3 = zzle.zzj((List) unsafe2.getObject(t2, j3));
                    if (zzj3 <= 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zze(i15) + zzii.zzg(zzj3) + zzj3;
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 43:
                    int zzf5 = zzle.zzf((List) unsafe2.getObject(t2, j3));
                    if (zzf5 <= 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zze(i15) + zzii.zzg(zzf5) + zzf5;
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 44:
                    int zzd5 = zzle.zzd((List) unsafe2.getObject(t2, j3));
                    if (zzd5 <= 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zze(i15) + zzii.zzg(zzd5) + zzd5;
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 45:
                    int zzh7 = zzle.zzh((List) unsafe2.getObject(t2, j3));
                    if (zzh7 <= 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zze(i15) + zzii.zzg(zzh7) + zzh7;
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 46:
                    int zzi7 = zzle.zzi((List) unsafe2.getObject(t2, j3));
                    if (zzi7 <= 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zze(i15) + zzii.zzg(zzi7) + zzi7;
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 47:
                    int zzg3 = zzle.zzg((List) unsafe2.getObject(t2, j3));
                    if (zzg3 <= 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zze(i15) + zzii.zzg(zzg3) + zzg3;
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 48:
                    int zzc3 = zzle.zzc((List) unsafe2.getObject(t2, j3));
                    if (zzc3 <= 0) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zze(i15) + zzii.zzg(zzc3) + zzc3;
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 49:
                    i13 += zzle.zzb(i15, (List<zzkk>) (List) unsafe2.getObject(t2, j3), zza(i12));
                    z = true;
                    i2 = 0;
                    j = 0;
                    break;
                case 50:
                    i13 += this.zzs.zza(i15, unsafe2.getObject(t2, j3), zzb(i12));
                    z = true;
                    i2 = 0;
                    j = 0;
                    break;
                case 51:
                    if (!zza(t2, i15, i12)) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzb(i15, 0.0d);
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 52:
                    if (!zza(t2, i15, i12)) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzb(i15, 0.0f);
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 53:
                    if (!zza(t2, i15, i12)) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzd(i15, zze(t2, j3));
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 54:
                    if (!zza(t2, i15, i12)) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zze(i15, zze(t2, j3));
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 55:
                    if (!zza(t2, i15, i12)) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzf(i15, zzd(t2, j3));
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 56:
                    if (!zza(t2, i15, i12)) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzg(i15, 0);
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 57:
                    if (!zza(t2, i15, i12)) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzi(i15, 0);
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 58:
                    if (!zza(t2, i15, i12)) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzb(i15, true);
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 59:
                    if (!zza(t2, i15, i12)) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        Object object2 = unsafe2.getObject(t2, j3);
                        if (!(object2 instanceof zzht)) {
                            i13 += zzii.zzb(i15, (String) object2);
                            z = true;
                            i2 = 0;
                            j = 0;
                            break;
                        } else {
                            i13 += zzii.zzc(i15, (zzht) object2);
                            z = true;
                            i2 = 0;
                            j = 0;
                            break;
                        }
                    }
                case 60:
                    if (!zza(t2, i15, i12)) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzle.zza(i15, unsafe2.getObject(t2, j3), zza(i12));
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 61:
                    if (!zza(t2, i15, i12)) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzc(i15, (zzht) unsafe2.getObject(t2, j3));
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 62:
                    if (!zza(t2, i15, i12)) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzg(i15, zzd(t2, j3));
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 63:
                    if (!zza(t2, i15, i12)) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzk(i15, zzd(t2, j3));
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 64:
                    if (!zza(t2, i15, i12)) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzj(i15, 0);
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 65:
                    if (!zza(t2, i15, i12)) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzh(i15, 0);
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 66:
                    if (!zza(t2, i15, i12)) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzh(i15, zzd(t2, j3));
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 67:
                    if (!zza(t2, i15, i12)) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzf(i15, zze(t2, j3));
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                case 68:
                    if (!zza(t2, i15, i12)) {
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    } else {
                        i13 += zzii.zzc(i15, (zzkk) unsafe2.getObject(t2, j3), zza(i12));
                        z = true;
                        i2 = 0;
                        j = 0;
                        break;
                    }
                default:
                    z = true;
                    i2 = 0;
                    j = 0;
                    break;
            }
            i12 += 3;
            z2 = z;
            i5 = i2;
            long j4 = j;
            i4 = 1048575;
        }
        int i19 = i5;
        int zza4 = i13 + zza(this.zzq, t2);
        if (!this.zzh) {
            return zza4;
        }
        zziu<?> zza5 = this.zzr.zza((Object) t2);
        for (int i20 = i19; i20 < zza5.zza.zzc(); i20++) {
            Map.Entry<T, Object> zzb4 = zza5.zza.zzb(i20);
            i19 += zziu.zzc((zziw) zzb4.getKey(), zzb4.getValue());
        }
        for (Map.Entry next : zza5.zza.zzd()) {
            i19 += zziu.zzc((zziw) next.getKey(), next.getValue());
        }
        return zza4 + i19;
    }

    private static <UT, UB> int zza(zzlu<UT, UB> zzlu, T t) {
        return zzlu.zzf(zzlu.zzb(t));
    }

    private static List<?> zza(Object obj, long j) {
        return (List) zzma.zzf(obj, j);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x059f  */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x05e2  */
    /* JADX WARNING: Removed duplicated region for block: B:331:0x0b42  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(T r14, com.google.android.gms.internal.vision.zzmr r15) {
        /*
            r13 = this;
            int r0 = r15.zza()
            int r1 = com.google.android.gms.internal.vision.zzmq.zzb
            r2 = 267386880(0xff00000, float:2.3665827E-29)
            r3 = 0
            r4 = 1
            r5 = 0
            r6 = 1048575(0xfffff, float:1.469367E-39)
            if (r0 != r1) goto L_0x05b5
            com.google.android.gms.internal.vision.zzlu<?, ?> r0 = r13.zzq
            zza(r0, r14, (com.google.android.gms.internal.vision.zzmr) r15)
            boolean r0 = r13.zzh
            if (r0 == 0) goto L_0x0036
            com.google.android.gms.internal.vision.zziq<?> r0 = r13.zzr
            com.google.android.gms.internal.vision.zziu r0 = r0.zza((java.lang.Object) r14)
            com.google.android.gms.internal.vision.zzlh<T, java.lang.Object> r1 = r0.zza
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L_0x0036
            java.util.Iterator r0 = r0.zze()
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            goto L_0x0038
        L_0x0036:
            r0 = r3
            r1 = r0
        L_0x0038:
            int[] r7 = r13.zzc
            int r7 = r7.length
            int r7 = r7 + -3
        L_0x003d:
            if (r7 < 0) goto L_0x059d
            int r8 = r13.zzd((int) r7)
            int[] r9 = r13.zzc
            r9 = r9[r7]
        L_0x0049:
            if (r1 == 0) goto L_0x0067
            com.google.android.gms.internal.vision.zziq<?> r10 = r13.zzr
            int r10 = r10.zza((java.util.Map.Entry<?, ?>) r1)
            if (r10 <= r9) goto L_0x0067
            com.google.android.gms.internal.vision.zziq<?> r10 = r13.zzr
            r10.zza(r15, r1)
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0065
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            goto L_0x0049
        L_0x0065:
            r1 = r3
            goto L_0x0049
        L_0x0067:
            r10 = r8 & r2
            int r10 = r10 >>> 20
            switch(r10) {
                case 0: goto L_0x0589;
                case 1: goto L_0x0578;
                case 2: goto L_0x0567;
                case 3: goto L_0x0556;
                case 4: goto L_0x0545;
                case 5: goto L_0x0534;
                case 6: goto L_0x0523;
                case 7: goto L_0x0511;
                case 8: goto L_0x04ff;
                case 9: goto L_0x04e9;
                case 10: goto L_0x04d5;
                case 11: goto L_0x04c3;
                case 12: goto L_0x04b1;
                case 13: goto L_0x049f;
                case 14: goto L_0x048d;
                case 15: goto L_0x047b;
                case 16: goto L_0x0469;
                case 17: goto L_0x0453;
                case 18: goto L_0x043f;
                case 19: goto L_0x042b;
                case 20: goto L_0x0417;
                case 21: goto L_0x0403;
                case 22: goto L_0x03ef;
                case 23: goto L_0x03db;
                case 24: goto L_0x03c7;
                case 25: goto L_0x03b3;
                case 26: goto L_0x039f;
                case 27: goto L_0x0387;
                case 28: goto L_0x0373;
                case 29: goto L_0x035f;
                case 30: goto L_0x034b;
                case 31: goto L_0x0337;
                case 32: goto L_0x0323;
                case 33: goto L_0x030f;
                case 34: goto L_0x02fb;
                case 35: goto L_0x02e7;
                case 36: goto L_0x02d3;
                case 37: goto L_0x02bf;
                case 38: goto L_0x02ab;
                case 39: goto L_0x0297;
                case 40: goto L_0x0283;
                case 41: goto L_0x026f;
                case 42: goto L_0x025b;
                case 43: goto L_0x0247;
                case 44: goto L_0x0233;
                case 45: goto L_0x021f;
                case 46: goto L_0x020b;
                case 47: goto L_0x01f7;
                case 48: goto L_0x01e3;
                case 49: goto L_0x01cb;
                case 50: goto L_0x01bf;
                case 51: goto L_0x01ad;
                case 52: goto L_0x019b;
                case 53: goto L_0x0189;
                case 54: goto L_0x0177;
                case 55: goto L_0x0165;
                case 56: goto L_0x0153;
                case 57: goto L_0x0141;
                case 58: goto L_0x012f;
                case 59: goto L_0x011d;
                case 60: goto L_0x0107;
                case 61: goto L_0x00f3;
                case 62: goto L_0x00e1;
                case 63: goto L_0x00cf;
                case 64: goto L_0x00bd;
                case 65: goto L_0x00ab;
                case 66: goto L_0x0099;
                case 67: goto L_0x0087;
                case 68: goto L_0x0071;
                default: goto L_0x006f;
            }
        L_0x006f:
            goto L_0x0599
        L_0x0071:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            com.google.android.gms.internal.vision.zzlc r10 = r13.zza((int) r7)
            r15.zzb((int) r9, (java.lang.Object) r8, (com.google.android.gms.internal.vision.zzlc) r10)
            goto L_0x0599
        L_0x0087:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zze(r14, r10)
            r15.zze((int) r9, (long) r10)
            goto L_0x0599
        L_0x0099:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzd(r14, r10)
            r15.zzf(r9, r8)
            goto L_0x0599
        L_0x00ab:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zze(r14, r10)
            r15.zzb((int) r9, (long) r10)
            goto L_0x0599
        L_0x00bd:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzd(r14, r10)
            r15.zza((int) r9, (int) r8)
            goto L_0x0599
        L_0x00cf:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzd(r14, r10)
            r15.zzb((int) r9, (int) r8)
            goto L_0x0599
        L_0x00e1:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzd(r14, r10)
            r15.zze((int) r9, (int) r8)
            goto L_0x0599
        L_0x00f3:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            com.google.android.gms.internal.vision.zzht r8 = (com.google.android.gms.internal.vision.zzht) r8
            r15.zza((int) r9, (com.google.android.gms.internal.vision.zzht) r8)
            goto L_0x0599
        L_0x0107:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            com.google.android.gms.internal.vision.zzlc r10 = r13.zza((int) r7)
            r15.zza((int) r9, (java.lang.Object) r8, (com.google.android.gms.internal.vision.zzlc) r10)
            goto L_0x0599
        L_0x011d:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            zza((int) r9, (java.lang.Object) r8, (com.google.android.gms.internal.vision.zzmr) r15)
            goto L_0x0599
        L_0x012f:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            boolean r8 = zzf(r14, r10)
            r15.zza((int) r9, (boolean) r8)
            goto L_0x0599
        L_0x0141:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzd(r14, r10)
            r15.zzd((int) r9, (int) r8)
            goto L_0x0599
        L_0x0153:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zze(r14, r10)
            r15.zzd((int) r9, (long) r10)
            goto L_0x0599
        L_0x0165:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzd(r14, r10)
            r15.zzc((int) r9, (int) r8)
            goto L_0x0599
        L_0x0177:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zze(r14, r10)
            r15.zzc((int) r9, (long) r10)
            goto L_0x0599
        L_0x0189:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zze(r14, r10)
            r15.zza((int) r9, (long) r10)
            goto L_0x0599
        L_0x019b:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            float r8 = zzc(r14, r10)
            r15.zza((int) r9, (float) r8)
            goto L_0x0599
        L_0x01ad:
            boolean r10 = r13.zza(r14, (int) r9, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            double r10 = zzb(r14, (long) r10)
            r15.zza((int) r9, (double) r10)
            goto L_0x0599
        L_0x01bf:
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            r13.zza((com.google.android.gms.internal.vision.zzmr) r15, (int) r9, (java.lang.Object) r8, (int) r7)
            goto L_0x0599
        L_0x01cb:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzlc r10 = r13.zza((int) r7)
            com.google.android.gms.internal.vision.zzle.zzb((int) r9, (java.util.List<?>) r8, (com.google.android.gms.internal.vision.zzmr) r15, (com.google.android.gms.internal.vision.zzlc) r10)
            goto L_0x0599
        L_0x01e3:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zze(r9, r8, r15, r4)
            goto L_0x0599
        L_0x01f7:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzj(r9, r8, r15, r4)
            goto L_0x0599
        L_0x020b:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzg(r9, r8, r15, r4)
            goto L_0x0599
        L_0x021f:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzl(r9, r8, r15, r4)
            goto L_0x0599
        L_0x0233:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzm(r9, r8, r15, r4)
            goto L_0x0599
        L_0x0247:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzi(r9, r8, r15, r4)
            goto L_0x0599
        L_0x025b:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzn(r9, r8, r15, r4)
            goto L_0x0599
        L_0x026f:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzk(r9, r8, r15, r4)
            goto L_0x0599
        L_0x0283:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzf(r9, r8, r15, r4)
            goto L_0x0599
        L_0x0297:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzh(r9, r8, r15, r4)
            goto L_0x0599
        L_0x02ab:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzd(r9, r8, r15, r4)
            goto L_0x0599
        L_0x02bf:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzc(r9, r8, r15, r4)
            goto L_0x0599
        L_0x02d3:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzb((int) r9, (java.util.List<java.lang.Float>) r8, (com.google.android.gms.internal.vision.zzmr) r15, (boolean) r4)
            goto L_0x0599
        L_0x02e7:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zza((int) r9, (java.util.List<java.lang.Double>) r8, (com.google.android.gms.internal.vision.zzmr) r15, (boolean) r4)
            goto L_0x0599
        L_0x02fb:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zze(r9, r8, r15, r5)
            goto L_0x0599
        L_0x030f:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzj(r9, r8, r15, r5)
            goto L_0x0599
        L_0x0323:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzg(r9, r8, r15, r5)
            goto L_0x0599
        L_0x0337:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzl(r9, r8, r15, r5)
            goto L_0x0599
        L_0x034b:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzm(r9, r8, r15, r5)
            goto L_0x0599
        L_0x035f:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzi(r9, r8, r15, r5)
            goto L_0x0599
        L_0x0373:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzb((int) r9, (java.util.List<com.google.android.gms.internal.vision.zzht>) r8, (com.google.android.gms.internal.vision.zzmr) r15)
            goto L_0x0599
        L_0x0387:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzlc r10 = r13.zza((int) r7)
            com.google.android.gms.internal.vision.zzle.zza((int) r9, (java.util.List<?>) r8, (com.google.android.gms.internal.vision.zzmr) r15, (com.google.android.gms.internal.vision.zzlc) r10)
            goto L_0x0599
        L_0x039f:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zza((int) r9, (java.util.List<java.lang.String>) r8, (com.google.android.gms.internal.vision.zzmr) r15)
            goto L_0x0599
        L_0x03b3:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzn(r9, r8, r15, r5)
            goto L_0x0599
        L_0x03c7:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzk(r9, r8, r15, r5)
            goto L_0x0599
        L_0x03db:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzf(r9, r8, r15, r5)
            goto L_0x0599
        L_0x03ef:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzh(r9, r8, r15, r5)
            goto L_0x0599
        L_0x0403:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzd(r9, r8, r15, r5)
            goto L_0x0599
        L_0x0417:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzc(r9, r8, r15, r5)
            goto L_0x0599
        L_0x042b:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzb((int) r9, (java.util.List<java.lang.Float>) r8, (com.google.android.gms.internal.vision.zzmr) r15, (boolean) r5)
            goto L_0x0599
        L_0x043f:
            int[] r9 = r13.zzc
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zza((int) r9, (java.util.List<java.lang.Double>) r8, (com.google.android.gms.internal.vision.zzmr) r15, (boolean) r5)
            goto L_0x0599
        L_0x0453:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            com.google.android.gms.internal.vision.zzlc r10 = r13.zza((int) r7)
            r15.zzb((int) r9, (java.lang.Object) r8, (com.google.android.gms.internal.vision.zzlc) r10)
            goto L_0x0599
        L_0x0469:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.vision.zzma.zzb(r14, r10)
            r15.zze((int) r9, (long) r10)
            goto L_0x0599
        L_0x047b:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r14, (long) r10)
            r15.zzf(r9, r8)
            goto L_0x0599
        L_0x048d:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.vision.zzma.zzb(r14, r10)
            r15.zzb((int) r9, (long) r10)
            goto L_0x0599
        L_0x049f:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r14, (long) r10)
            r15.zza((int) r9, (int) r8)
            goto L_0x0599
        L_0x04b1:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r14, (long) r10)
            r15.zzb((int) r9, (int) r8)
            goto L_0x0599
        L_0x04c3:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r14, (long) r10)
            r15.zze((int) r9, (int) r8)
            goto L_0x0599
        L_0x04d5:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            com.google.android.gms.internal.vision.zzht r8 = (com.google.android.gms.internal.vision.zzht) r8
            r15.zza((int) r9, (com.google.android.gms.internal.vision.zzht) r8)
            goto L_0x0599
        L_0x04e9:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            com.google.android.gms.internal.vision.zzlc r10 = r13.zza((int) r7)
            r15.zza((int) r9, (java.lang.Object) r8, (com.google.android.gms.internal.vision.zzlc) r10)
            goto L_0x0599
        L_0x04ff:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.vision.zzma.zzf(r14, r10)
            zza((int) r9, (java.lang.Object) r8, (com.google.android.gms.internal.vision.zzmr) r15)
            goto L_0x0599
        L_0x0511:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            boolean r8 = com.google.android.gms.internal.vision.zzma.zzc(r14, r10)
            r15.zza((int) r9, (boolean) r8)
            goto L_0x0599
        L_0x0523:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r14, (long) r10)
            r15.zzd((int) r9, (int) r8)
            goto L_0x0599
        L_0x0534:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.vision.zzma.zzb(r14, r10)
            r15.zzd((int) r9, (long) r10)
            goto L_0x0599
        L_0x0545:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r14, (long) r10)
            r15.zzc((int) r9, (int) r8)
            goto L_0x0599
        L_0x0556:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.vision.zzma.zzb(r14, r10)
            r15.zzc((int) r9, (long) r10)
            goto L_0x0599
        L_0x0567:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.vision.zzma.zzb(r14, r10)
            r15.zza((int) r9, (long) r10)
            goto L_0x0599
        L_0x0578:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            float r8 = com.google.android.gms.internal.vision.zzma.zzd(r14, r10)
            r15.zza((int) r9, (float) r8)
            goto L_0x0599
        L_0x0589:
            boolean r10 = r13.zza(r14, (int) r7)
            if (r10 == 0) goto L_0x0599
            r8 = r8 & r6
            long r10 = (long) r8
            double r10 = com.google.android.gms.internal.vision.zzma.zze(r14, r10)
            r15.zza((int) r9, (double) r10)
        L_0x0599:
            int r7 = r7 + -3
            goto L_0x003d
        L_0x059d:
            if (r1 == 0) goto L_0x05b4
            com.google.android.gms.internal.vision.zziq<?> r14 = r13.zzr
            r14.zza(r15, r1)
            boolean r14 = r0.hasNext()
            if (r14 == 0) goto L_0x05b2
            java.lang.Object r14 = r0.next()
            java.util.Map$Entry r14 = (java.util.Map.Entry) r14
            r1 = r14
            goto L_0x059d
        L_0x05b2:
            r1 = r3
            goto L_0x059d
        L_0x05b4:
            return
        L_0x05b5:
            boolean r0 = r13.zzj
            if (r0 == 0) goto L_0x0b5c
            boolean r0 = r13.zzh
            if (r0 == 0) goto L_0x05da
            com.google.android.gms.internal.vision.zziq<?> r0 = r13.zzr
            com.google.android.gms.internal.vision.zziu r0 = r0.zza((java.lang.Object) r14)
            com.google.android.gms.internal.vision.zzlh<T, java.lang.Object> r1 = r0.zza
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L_0x05da
            java.util.Iterator r0 = r0.zzd()
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            goto L_0x05dc
        L_0x05da:
            r0 = r3
            r1 = r0
        L_0x05dc:
            int[] r7 = r13.zzc
            int r7 = r7.length
            r8 = r5
        L_0x05e0:
            if (r8 >= r7) goto L_0x0b40
            int r9 = r13.zzd((int) r8)
            int[] r10 = r13.zzc
            r10 = r10[r8]
        L_0x05ec:
            if (r1 == 0) goto L_0x060a
            com.google.android.gms.internal.vision.zziq<?> r11 = r13.zzr
            int r11 = r11.zza((java.util.Map.Entry<?, ?>) r1)
            if (r11 > r10) goto L_0x060a
            com.google.android.gms.internal.vision.zziq<?> r11 = r13.zzr
            r11.zza(r15, r1)
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0608
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            goto L_0x05ec
        L_0x0608:
            r1 = r3
            goto L_0x05ec
        L_0x060a:
            r11 = r9 & r2
            int r11 = r11 >>> 20
            switch(r11) {
                case 0: goto L_0x0b2c;
                case 1: goto L_0x0b1b;
                case 2: goto L_0x0b0a;
                case 3: goto L_0x0af9;
                case 4: goto L_0x0ae8;
                case 5: goto L_0x0ad7;
                case 6: goto L_0x0ac6;
                case 7: goto L_0x0ab4;
                case 8: goto L_0x0aa2;
                case 9: goto L_0x0a8c;
                case 10: goto L_0x0a78;
                case 11: goto L_0x0a66;
                case 12: goto L_0x0a54;
                case 13: goto L_0x0a42;
                case 14: goto L_0x0a30;
                case 15: goto L_0x0a1e;
                case 16: goto L_0x0a0c;
                case 17: goto L_0x09f6;
                case 18: goto L_0x09e2;
                case 19: goto L_0x09ce;
                case 20: goto L_0x09ba;
                case 21: goto L_0x09a6;
                case 22: goto L_0x0992;
                case 23: goto L_0x097e;
                case 24: goto L_0x096a;
                case 25: goto L_0x0956;
                case 26: goto L_0x0942;
                case 27: goto L_0x092a;
                case 28: goto L_0x0916;
                case 29: goto L_0x0902;
                case 30: goto L_0x08ee;
                case 31: goto L_0x08da;
                case 32: goto L_0x08c6;
                case 33: goto L_0x08b2;
                case 34: goto L_0x089e;
                case 35: goto L_0x088a;
                case 36: goto L_0x0876;
                case 37: goto L_0x0862;
                case 38: goto L_0x084e;
                case 39: goto L_0x083a;
                case 40: goto L_0x0826;
                case 41: goto L_0x0812;
                case 42: goto L_0x07fe;
                case 43: goto L_0x07ea;
                case 44: goto L_0x07d6;
                case 45: goto L_0x07c2;
                case 46: goto L_0x07ae;
                case 47: goto L_0x079a;
                case 48: goto L_0x0786;
                case 49: goto L_0x076e;
                case 50: goto L_0x0762;
                case 51: goto L_0x0750;
                case 52: goto L_0x073e;
                case 53: goto L_0x072c;
                case 54: goto L_0x071a;
                case 55: goto L_0x0708;
                case 56: goto L_0x06f6;
                case 57: goto L_0x06e4;
                case 58: goto L_0x06d2;
                case 59: goto L_0x06c0;
                case 60: goto L_0x06aa;
                case 61: goto L_0x0696;
                case 62: goto L_0x0684;
                case 63: goto L_0x0672;
                case 64: goto L_0x0660;
                case 65: goto L_0x064e;
                case 66: goto L_0x063c;
                case 67: goto L_0x062a;
                case 68: goto L_0x0614;
                default: goto L_0x0612;
            }
        L_0x0612:
            goto L_0x0b3c
        L_0x0614:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            com.google.android.gms.internal.vision.zzlc r11 = r13.zza((int) r8)
            r15.zzb((int) r10, (java.lang.Object) r9, (com.google.android.gms.internal.vision.zzlc) r11)
            goto L_0x0b3c
        L_0x062a:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zze(r14, r11)
            r15.zze((int) r10, (long) r11)
            goto L_0x0b3c
        L_0x063c:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzd(r14, r11)
            r15.zzf(r10, r9)
            goto L_0x0b3c
        L_0x064e:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zze(r14, r11)
            r15.zzb((int) r10, (long) r11)
            goto L_0x0b3c
        L_0x0660:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzd(r14, r11)
            r15.zza((int) r10, (int) r9)
            goto L_0x0b3c
        L_0x0672:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzd(r14, r11)
            r15.zzb((int) r10, (int) r9)
            goto L_0x0b3c
        L_0x0684:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzd(r14, r11)
            r15.zze((int) r10, (int) r9)
            goto L_0x0b3c
        L_0x0696:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            com.google.android.gms.internal.vision.zzht r9 = (com.google.android.gms.internal.vision.zzht) r9
            r15.zza((int) r10, (com.google.android.gms.internal.vision.zzht) r9)
            goto L_0x0b3c
        L_0x06aa:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            com.google.android.gms.internal.vision.zzlc r11 = r13.zza((int) r8)
            r15.zza((int) r10, (java.lang.Object) r9, (com.google.android.gms.internal.vision.zzlc) r11)
            goto L_0x0b3c
        L_0x06c0:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            zza((int) r10, (java.lang.Object) r9, (com.google.android.gms.internal.vision.zzmr) r15)
            goto L_0x0b3c
        L_0x06d2:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            boolean r9 = zzf(r14, r11)
            r15.zza((int) r10, (boolean) r9)
            goto L_0x0b3c
        L_0x06e4:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzd(r14, r11)
            r15.zzd((int) r10, (int) r9)
            goto L_0x0b3c
        L_0x06f6:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zze(r14, r11)
            r15.zzd((int) r10, (long) r11)
            goto L_0x0b3c
        L_0x0708:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzd(r14, r11)
            r15.zzc((int) r10, (int) r9)
            goto L_0x0b3c
        L_0x071a:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zze(r14, r11)
            r15.zzc((int) r10, (long) r11)
            goto L_0x0b3c
        L_0x072c:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zze(r14, r11)
            r15.zza((int) r10, (long) r11)
            goto L_0x0b3c
        L_0x073e:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            float r9 = zzc(r14, r11)
            r15.zza((int) r10, (float) r9)
            goto L_0x0b3c
        L_0x0750:
            boolean r11 = r13.zza(r14, (int) r10, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            double r11 = zzb(r14, (long) r11)
            r15.zza((int) r10, (double) r11)
            goto L_0x0b3c
        L_0x0762:
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            r13.zza((com.google.android.gms.internal.vision.zzmr) r15, (int) r10, (java.lang.Object) r9, (int) r8)
            goto L_0x0b3c
        L_0x076e:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzlc r11 = r13.zza((int) r8)
            com.google.android.gms.internal.vision.zzle.zzb((int) r10, (java.util.List<?>) r9, (com.google.android.gms.internal.vision.zzmr) r15, (com.google.android.gms.internal.vision.zzlc) r11)
            goto L_0x0b3c
        L_0x0786:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zze(r10, r9, r15, r4)
            goto L_0x0b3c
        L_0x079a:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzj(r10, r9, r15, r4)
            goto L_0x0b3c
        L_0x07ae:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzg(r10, r9, r15, r4)
            goto L_0x0b3c
        L_0x07c2:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzl(r10, r9, r15, r4)
            goto L_0x0b3c
        L_0x07d6:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzm(r10, r9, r15, r4)
            goto L_0x0b3c
        L_0x07ea:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzi(r10, r9, r15, r4)
            goto L_0x0b3c
        L_0x07fe:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzn(r10, r9, r15, r4)
            goto L_0x0b3c
        L_0x0812:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzk(r10, r9, r15, r4)
            goto L_0x0b3c
        L_0x0826:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzf(r10, r9, r15, r4)
            goto L_0x0b3c
        L_0x083a:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzh(r10, r9, r15, r4)
            goto L_0x0b3c
        L_0x084e:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzd(r10, r9, r15, r4)
            goto L_0x0b3c
        L_0x0862:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzc(r10, r9, r15, r4)
            goto L_0x0b3c
        L_0x0876:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzb((int) r10, (java.util.List<java.lang.Float>) r9, (com.google.android.gms.internal.vision.zzmr) r15, (boolean) r4)
            goto L_0x0b3c
        L_0x088a:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zza((int) r10, (java.util.List<java.lang.Double>) r9, (com.google.android.gms.internal.vision.zzmr) r15, (boolean) r4)
            goto L_0x0b3c
        L_0x089e:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zze(r10, r9, r15, r5)
            goto L_0x0b3c
        L_0x08b2:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzj(r10, r9, r15, r5)
            goto L_0x0b3c
        L_0x08c6:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzg(r10, r9, r15, r5)
            goto L_0x0b3c
        L_0x08da:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzl(r10, r9, r15, r5)
            goto L_0x0b3c
        L_0x08ee:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzm(r10, r9, r15, r5)
            goto L_0x0b3c
        L_0x0902:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzi(r10, r9, r15, r5)
            goto L_0x0b3c
        L_0x0916:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzb((int) r10, (java.util.List<com.google.android.gms.internal.vision.zzht>) r9, (com.google.android.gms.internal.vision.zzmr) r15)
            goto L_0x0b3c
        L_0x092a:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzlc r11 = r13.zza((int) r8)
            com.google.android.gms.internal.vision.zzle.zza((int) r10, (java.util.List<?>) r9, (com.google.android.gms.internal.vision.zzmr) r15, (com.google.android.gms.internal.vision.zzlc) r11)
            goto L_0x0b3c
        L_0x0942:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zza((int) r10, (java.util.List<java.lang.String>) r9, (com.google.android.gms.internal.vision.zzmr) r15)
            goto L_0x0b3c
        L_0x0956:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzn(r10, r9, r15, r5)
            goto L_0x0b3c
        L_0x096a:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzk(r10, r9, r15, r5)
            goto L_0x0b3c
        L_0x097e:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzf(r10, r9, r15, r5)
            goto L_0x0b3c
        L_0x0992:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzh(r10, r9, r15, r5)
            goto L_0x0b3c
        L_0x09a6:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzd(r10, r9, r15, r5)
            goto L_0x0b3c
        L_0x09ba:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzc(r10, r9, r15, r5)
            goto L_0x0b3c
        L_0x09ce:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zzb((int) r10, (java.util.List<java.lang.Float>) r9, (com.google.android.gms.internal.vision.zzmr) r15, (boolean) r5)
            goto L_0x0b3c
        L_0x09e2:
            int[] r10 = r13.zzc
            r10 = r10[r8]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.vision.zzle.zza((int) r10, (java.util.List<java.lang.Double>) r9, (com.google.android.gms.internal.vision.zzmr) r15, (boolean) r5)
            goto L_0x0b3c
        L_0x09f6:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            com.google.android.gms.internal.vision.zzlc r11 = r13.zza((int) r8)
            r15.zzb((int) r10, (java.lang.Object) r9, (com.google.android.gms.internal.vision.zzlc) r11)
            goto L_0x0b3c
        L_0x0a0c:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.vision.zzma.zzb(r14, r11)
            r15.zze((int) r10, (long) r11)
            goto L_0x0b3c
        L_0x0a1e:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r14, (long) r11)
            r15.zzf(r10, r9)
            goto L_0x0b3c
        L_0x0a30:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.vision.zzma.zzb(r14, r11)
            r15.zzb((int) r10, (long) r11)
            goto L_0x0b3c
        L_0x0a42:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r14, (long) r11)
            r15.zza((int) r10, (int) r9)
            goto L_0x0b3c
        L_0x0a54:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r14, (long) r11)
            r15.zzb((int) r10, (int) r9)
            goto L_0x0b3c
        L_0x0a66:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r14, (long) r11)
            r15.zze((int) r10, (int) r9)
            goto L_0x0b3c
        L_0x0a78:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            com.google.android.gms.internal.vision.zzht r9 = (com.google.android.gms.internal.vision.zzht) r9
            r15.zza((int) r10, (com.google.android.gms.internal.vision.zzht) r9)
            goto L_0x0b3c
        L_0x0a8c:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            com.google.android.gms.internal.vision.zzlc r11 = r13.zza((int) r8)
            r15.zza((int) r10, (java.lang.Object) r9, (com.google.android.gms.internal.vision.zzlc) r11)
            goto L_0x0b3c
        L_0x0aa2:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.vision.zzma.zzf(r14, r11)
            zza((int) r10, (java.lang.Object) r9, (com.google.android.gms.internal.vision.zzmr) r15)
            goto L_0x0b3c
        L_0x0ab4:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            boolean r9 = com.google.android.gms.internal.vision.zzma.zzc(r14, r11)
            r15.zza((int) r10, (boolean) r9)
            goto L_0x0b3c
        L_0x0ac6:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r14, (long) r11)
            r15.zzd((int) r10, (int) r9)
            goto L_0x0b3c
        L_0x0ad7:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.vision.zzma.zzb(r14, r11)
            r15.zzd((int) r10, (long) r11)
            goto L_0x0b3c
        L_0x0ae8:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r14, (long) r11)
            r15.zzc((int) r10, (int) r9)
            goto L_0x0b3c
        L_0x0af9:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.vision.zzma.zzb(r14, r11)
            r15.zzc((int) r10, (long) r11)
            goto L_0x0b3c
        L_0x0b0a:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.vision.zzma.zzb(r14, r11)
            r15.zza((int) r10, (long) r11)
            goto L_0x0b3c
        L_0x0b1b:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            float r9 = com.google.android.gms.internal.vision.zzma.zzd(r14, r11)
            r15.zza((int) r10, (float) r9)
            goto L_0x0b3c
        L_0x0b2c:
            boolean r11 = r13.zza(r14, (int) r8)
            if (r11 == 0) goto L_0x0b3c
            r9 = r9 & r6
            long r11 = (long) r9
            double r11 = com.google.android.gms.internal.vision.zzma.zze(r14, r11)
            r15.zza((int) r10, (double) r11)
        L_0x0b3c:
            int r8 = r8 + 3
            goto L_0x05e0
        L_0x0b40:
            if (r1 == 0) goto L_0x0b56
            com.google.android.gms.internal.vision.zziq<?> r2 = r13.zzr
            r2.zza(r15, r1)
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0b54
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            goto L_0x0b40
        L_0x0b54:
            r1 = r3
            goto L_0x0b40
        L_0x0b56:
            com.google.android.gms.internal.vision.zzlu<?, ?> r0 = r13.zzq
            zza(r0, r14, (com.google.android.gms.internal.vision.zzmr) r15)
            return
        L_0x0b5c:
            r13.zzb(r14, (com.google.android.gms.internal.vision.zzmr) r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zza(java.lang.Object, com.google.android.gms.internal.vision.zzmr):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:186:0x0555  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0035  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzb(T r18, com.google.android.gms.internal.vision.zzmr r19) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            boolean r3 = r0.zzh
            if (r3 == 0) goto L_0x0025
            com.google.android.gms.internal.vision.zziq<?> r3 = r0.zzr
            com.google.android.gms.internal.vision.zziu r3 = r3.zza((java.lang.Object) r1)
            com.google.android.gms.internal.vision.zzlh<T, java.lang.Object> r5 = r3.zza
            boolean r5 = r5.isEmpty()
            if (r5 != 0) goto L_0x0025
            java.util.Iterator r3 = r3.zzd()
            java.lang.Object r5 = r3.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            goto L_0x0027
        L_0x0025:
            r3 = 0
            r5 = 0
        L_0x0027:
            int[] r6 = r0.zzc
            int r6 = r6.length
            sun.misc.Unsafe r7 = zzb
            r10 = 0
            r11 = 1048575(0xfffff, float:1.469367E-39)
            r12 = 0
        L_0x0033:
            if (r10 >= r6) goto L_0x0553
            int r13 = r0.zzd((int) r10)
            int[] r14 = r0.zzc
            r15 = r14[r10]
            r16 = 267386880(0xff00000, float:2.3665827E-29)
            r16 = r13 & r16
            int r4 = r16 >>> 20
            r9 = 17
            if (r4 > r9) goto L_0x0064
            int r9 = r10 + 2
            r9 = r14[r9]
            r14 = 1048575(0xfffff, float:1.469367E-39)
            r8 = r9 & r14
            if (r8 == r11) goto L_0x005e
            long r11 = (long) r8
            int r12 = r7.getInt(r1, r11)
            r11 = r8
        L_0x005e:
            int r8 = r9 >>> 20
            r9 = 1
            int r8 = r9 << r8
            goto L_0x0065
        L_0x0064:
            r8 = 0
        L_0x0065:
            if (r5 == 0) goto L_0x0083
            com.google.android.gms.internal.vision.zziq<?> r9 = r0.zzr
            int r9 = r9.zza((java.util.Map.Entry<?, ?>) r5)
            if (r9 > r15) goto L_0x0083
            com.google.android.gms.internal.vision.zziq<?> r9 = r0.zzr
            r9.zza(r2, r5)
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x0081
            java.lang.Object r5 = r3.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            goto L_0x0065
        L_0x0081:
            r5 = 0
            goto L_0x0065
        L_0x0083:
            r9 = 1048575(0xfffff, float:1.469367E-39)
            r13 = r13 & r9
            long r13 = (long) r13
            switch(r4) {
                case 0: goto L_0x0543;
                case 1: goto L_0x0536;
                case 2: goto L_0x052a;
                case 3: goto L_0x051e;
                case 4: goto L_0x0512;
                case 5: goto L_0x0506;
                case 6: goto L_0x04fa;
                case 7: goto L_0x04ed;
                case 8: goto L_0x04e1;
                case 9: goto L_0x04d0;
                case 10: goto L_0x04c1;
                case 11: goto L_0x04b4;
                case 12: goto L_0x04a7;
                case 13: goto L_0x049a;
                case 14: goto L_0x048d;
                case 15: goto L_0x0480;
                case 16: goto L_0x0473;
                case 17: goto L_0x0461;
                case 18: goto L_0x044e;
                case 19: goto L_0x043b;
                case 20: goto L_0x0428;
                case 21: goto L_0x0415;
                case 22: goto L_0x0402;
                case 23: goto L_0x03ef;
                case 24: goto L_0x03dc;
                case 25: goto L_0x03c9;
                case 26: goto L_0x03b7;
                case 27: goto L_0x03a0;
                case 28: goto L_0x038e;
                case 29: goto L_0x037b;
                case 30: goto L_0x0368;
                case 31: goto L_0x0355;
                case 32: goto L_0x0342;
                case 33: goto L_0x032f;
                case 34: goto L_0x031c;
                case 35: goto L_0x0309;
                case 36: goto L_0x02f6;
                case 37: goto L_0x02e3;
                case 38: goto L_0x02d0;
                case 39: goto L_0x02bd;
                case 40: goto L_0x02aa;
                case 41: goto L_0x0297;
                case 42: goto L_0x0284;
                case 43: goto L_0x0271;
                case 44: goto L_0x025e;
                case 45: goto L_0x024b;
                case 46: goto L_0x0238;
                case 47: goto L_0x0225;
                case 48: goto L_0x0212;
                case 49: goto L_0x01fb;
                case 50: goto L_0x01f1;
                case 51: goto L_0x01de;
                case 52: goto L_0x01cb;
                case 53: goto L_0x01b8;
                case 54: goto L_0x01a5;
                case 55: goto L_0x0192;
                case 56: goto L_0x017f;
                case 57: goto L_0x016c;
                case 58: goto L_0x0159;
                case 59: goto L_0x0146;
                case 60: goto L_0x012f;
                case 61: goto L_0x011a;
                case 62: goto L_0x0107;
                case 63: goto L_0x00f4;
                case 64: goto L_0x00e1;
                case 65: goto L_0x00ce;
                case 66: goto L_0x00bb;
                case 67: goto L_0x00a8;
                case 68: goto L_0x0090;
                default: goto L_0x008d;
            }
        L_0x008d:
            r4 = 0
            goto L_0x054f
        L_0x0090:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x00a5
            java.lang.Object r4 = r7.getObject(r1, r13)
            com.google.android.gms.internal.vision.zzlc r8 = r0.zza((int) r10)
            r2.zzb((int) r15, (java.lang.Object) r4, (com.google.android.gms.internal.vision.zzlc) r8)
            r4 = 0
            goto L_0x054f
        L_0x00a5:
            r4 = 0
            goto L_0x054f
        L_0x00a8:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x00b8
            long r13 = zze(r1, r13)
            r2.zze((int) r15, (long) r13)
            r4 = 0
            goto L_0x054f
        L_0x00b8:
            r4 = 0
            goto L_0x054f
        L_0x00bb:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x00cb
            int r4 = zzd(r1, r13)
            r2.zzf(r15, r4)
            r4 = 0
            goto L_0x054f
        L_0x00cb:
            r4 = 0
            goto L_0x054f
        L_0x00ce:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x00de
            long r13 = zze(r1, r13)
            r2.zzb((int) r15, (long) r13)
            r4 = 0
            goto L_0x054f
        L_0x00de:
            r4 = 0
            goto L_0x054f
        L_0x00e1:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x00f1
            int r4 = zzd(r1, r13)
            r2.zza((int) r15, (int) r4)
            r4 = 0
            goto L_0x054f
        L_0x00f1:
            r4 = 0
            goto L_0x054f
        L_0x00f4:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x0104
            int r4 = zzd(r1, r13)
            r2.zzb((int) r15, (int) r4)
            r4 = 0
            goto L_0x054f
        L_0x0104:
            r4 = 0
            goto L_0x054f
        L_0x0107:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x0117
            int r4 = zzd(r1, r13)
            r2.zze((int) r15, (int) r4)
            r4 = 0
            goto L_0x054f
        L_0x0117:
            r4 = 0
            goto L_0x054f
        L_0x011a:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x012c
            java.lang.Object r4 = r7.getObject(r1, r13)
            com.google.android.gms.internal.vision.zzht r4 = (com.google.android.gms.internal.vision.zzht) r4
            r2.zza((int) r15, (com.google.android.gms.internal.vision.zzht) r4)
            r4 = 0
            goto L_0x054f
        L_0x012c:
            r4 = 0
            goto L_0x054f
        L_0x012f:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x0143
            java.lang.Object r4 = r7.getObject(r1, r13)
            com.google.android.gms.internal.vision.zzlc r8 = r0.zza((int) r10)
            r2.zza((int) r15, (java.lang.Object) r4, (com.google.android.gms.internal.vision.zzlc) r8)
            r4 = 0
            goto L_0x054f
        L_0x0143:
            r4 = 0
            goto L_0x054f
        L_0x0146:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x0156
            java.lang.Object r4 = r7.getObject(r1, r13)
            zza((int) r15, (java.lang.Object) r4, (com.google.android.gms.internal.vision.zzmr) r2)
            r4 = 0
            goto L_0x054f
        L_0x0156:
            r4 = 0
            goto L_0x054f
        L_0x0159:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x0169
            boolean r4 = zzf(r1, r13)
            r2.zza((int) r15, (boolean) r4)
            r4 = 0
            goto L_0x054f
        L_0x0169:
            r4 = 0
            goto L_0x054f
        L_0x016c:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x017c
            int r4 = zzd(r1, r13)
            r2.zzd((int) r15, (int) r4)
            r4 = 0
            goto L_0x054f
        L_0x017c:
            r4 = 0
            goto L_0x054f
        L_0x017f:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x018f
            long r13 = zze(r1, r13)
            r2.zzd((int) r15, (long) r13)
            r4 = 0
            goto L_0x054f
        L_0x018f:
            r4 = 0
            goto L_0x054f
        L_0x0192:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x01a2
            int r4 = zzd(r1, r13)
            r2.zzc((int) r15, (int) r4)
            r4 = 0
            goto L_0x054f
        L_0x01a2:
            r4 = 0
            goto L_0x054f
        L_0x01a5:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x01b5
            long r13 = zze(r1, r13)
            r2.zzc((int) r15, (long) r13)
            r4 = 0
            goto L_0x054f
        L_0x01b5:
            r4 = 0
            goto L_0x054f
        L_0x01b8:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x01c8
            long r13 = zze(r1, r13)
            r2.zza((int) r15, (long) r13)
            r4 = 0
            goto L_0x054f
        L_0x01c8:
            r4 = 0
            goto L_0x054f
        L_0x01cb:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x01db
            float r4 = zzc(r1, r13)
            r2.zza((int) r15, (float) r4)
            r4 = 0
            goto L_0x054f
        L_0x01db:
            r4 = 0
            goto L_0x054f
        L_0x01de:
            boolean r4 = r0.zza(r1, (int) r15, (int) r10)
            if (r4 == 0) goto L_0x01ee
            double r13 = zzb(r1, (long) r13)
            r2.zza((int) r15, (double) r13)
            r4 = 0
            goto L_0x054f
        L_0x01ee:
            r4 = 0
            goto L_0x054f
        L_0x01f1:
            java.lang.Object r4 = r7.getObject(r1, r13)
            r0.zza((com.google.android.gms.internal.vision.zzmr) r2, (int) r15, (java.lang.Object) r4, (int) r10)
            r4 = 0
            goto L_0x054f
        L_0x01fb:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzlc r13 = r0.zza((int) r10)
            com.google.android.gms.internal.vision.zzle.zzb((int) r4, (java.util.List<?>) r8, (com.google.android.gms.internal.vision.zzmr) r2, (com.google.android.gms.internal.vision.zzlc) r13)
            r4 = 0
            goto L_0x054f
        L_0x0212:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.vision.zzle.zze(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x0225:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.vision.zzle.zzj(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x0238:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.vision.zzle.zzg(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x024b:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.vision.zzle.zzl(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x025e:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.vision.zzle.zzm(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x0271:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.vision.zzle.zzi(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x0284:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.vision.zzle.zzn(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x0297:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.vision.zzle.zzk(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x02aa:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.vision.zzle.zzf(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x02bd:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.vision.zzle.zzh(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x02d0:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.vision.zzle.zzd(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x02e3:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.vision.zzle.zzc(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x02f6:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.vision.zzle.zzb((int) r4, (java.util.List<java.lang.Float>) r8, (com.google.android.gms.internal.vision.zzmr) r2, (boolean) r13)
            r4 = 0
            goto L_0x054f
        L_0x0309:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 1
            com.google.android.gms.internal.vision.zzle.zza((int) r4, (java.util.List<java.lang.Double>) r8, (com.google.android.gms.internal.vision.zzmr) r2, (boolean) r13)
            r4 = 0
            goto L_0x054f
        L_0x031c:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.vision.zzle.zze(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x032f:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.vision.zzle.zzj(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x0342:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.vision.zzle.zzg(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x0355:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.vision.zzle.zzl(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x0368:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.vision.zzle.zzm(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x037b:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.vision.zzle.zzi(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x038e:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zzb((int) r4, (java.util.List<com.google.android.gms.internal.vision.zzht>) r8, (com.google.android.gms.internal.vision.zzmr) r2)
            r4 = 0
            goto L_0x054f
        L_0x03a0:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzlc r13 = r0.zza((int) r10)
            com.google.android.gms.internal.vision.zzle.zza((int) r4, (java.util.List<?>) r8, (com.google.android.gms.internal.vision.zzmr) r2, (com.google.android.gms.internal.vision.zzlc) r13)
            r4 = 0
            goto L_0x054f
        L_0x03b7:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.vision.zzle.zza((int) r4, (java.util.List<java.lang.String>) r8, (com.google.android.gms.internal.vision.zzmr) r2)
            r4 = 0
            goto L_0x054f
        L_0x03c9:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.vision.zzle.zzn(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x03dc:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.vision.zzle.zzk(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x03ef:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.vision.zzle.zzf(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x0402:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.vision.zzle.zzh(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x0415:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.vision.zzle.zzd(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x0428:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.vision.zzle.zzc(r4, r8, r2, r13)
            r4 = 0
            goto L_0x054f
        L_0x043b:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.vision.zzle.zzb((int) r4, (java.util.List<java.lang.Float>) r8, (com.google.android.gms.internal.vision.zzmr) r2, (boolean) r13)
            r4 = 0
            goto L_0x054f
        L_0x044e:
            int[] r4 = r0.zzc
            r4 = r4[r10]
            java.lang.Object r8 = r7.getObject(r1, r13)
            java.util.List r8 = (java.util.List) r8
            r13 = 0
            com.google.android.gms.internal.vision.zzle.zza((int) r4, (java.util.List<java.lang.Double>) r8, (com.google.android.gms.internal.vision.zzmr) r2, (boolean) r13)
            r4 = r13
            goto L_0x054f
        L_0x0461:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x054f
            java.lang.Object r8 = r7.getObject(r1, r13)
            com.google.android.gms.internal.vision.zzlc r13 = r0.zza((int) r10)
            r2.zzb((int) r15, (java.lang.Object) r8, (com.google.android.gms.internal.vision.zzlc) r13)
            goto L_0x054f
        L_0x0473:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x054f
            long r13 = r7.getLong(r1, r13)
            r2.zze((int) r15, (long) r13)
            goto L_0x054f
        L_0x0480:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x054f
            int r8 = r7.getInt(r1, r13)
            r2.zzf(r15, r8)
            goto L_0x054f
        L_0x048d:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x054f
            long r13 = r7.getLong(r1, r13)
            r2.zzb((int) r15, (long) r13)
            goto L_0x054f
        L_0x049a:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x054f
            int r8 = r7.getInt(r1, r13)
            r2.zza((int) r15, (int) r8)
            goto L_0x054f
        L_0x04a7:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x054f
            int r8 = r7.getInt(r1, r13)
            r2.zzb((int) r15, (int) r8)
            goto L_0x054f
        L_0x04b4:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x054f
            int r8 = r7.getInt(r1, r13)
            r2.zze((int) r15, (int) r8)
            goto L_0x054f
        L_0x04c1:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x054f
            java.lang.Object r8 = r7.getObject(r1, r13)
            com.google.android.gms.internal.vision.zzht r8 = (com.google.android.gms.internal.vision.zzht) r8
            r2.zza((int) r15, (com.google.android.gms.internal.vision.zzht) r8)
            goto L_0x054f
        L_0x04d0:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x054f
            java.lang.Object r8 = r7.getObject(r1, r13)
            com.google.android.gms.internal.vision.zzlc r13 = r0.zza((int) r10)
            r2.zza((int) r15, (java.lang.Object) r8, (com.google.android.gms.internal.vision.zzlc) r13)
            goto L_0x054f
        L_0x04e1:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x054f
            java.lang.Object r8 = r7.getObject(r1, r13)
            zza((int) r15, (java.lang.Object) r8, (com.google.android.gms.internal.vision.zzmr) r2)
            goto L_0x054f
        L_0x04ed:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x054f
            boolean r8 = com.google.android.gms.internal.vision.zzma.zzc(r1, r13)
            r2.zza((int) r15, (boolean) r8)
            goto L_0x054f
        L_0x04fa:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x054f
            int r8 = r7.getInt(r1, r13)
            r2.zzd((int) r15, (int) r8)
            goto L_0x054f
        L_0x0506:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x054f
            long r13 = r7.getLong(r1, r13)
            r2.zzd((int) r15, (long) r13)
            goto L_0x054f
        L_0x0512:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x054f
            int r8 = r7.getInt(r1, r13)
            r2.zzc((int) r15, (int) r8)
            goto L_0x054f
        L_0x051e:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x054f
            long r13 = r7.getLong(r1, r13)
            r2.zzc((int) r15, (long) r13)
            goto L_0x054f
        L_0x052a:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x054f
            long r13 = r7.getLong(r1, r13)
            r2.zza((int) r15, (long) r13)
            goto L_0x054f
        L_0x0536:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x054f
            float r8 = com.google.android.gms.internal.vision.zzma.zzd(r1, r13)
            r2.zza((int) r15, (float) r8)
            goto L_0x054f
        L_0x0543:
            r4 = 0
            r8 = r8 & r12
            if (r8 == 0) goto L_0x054f
            double r13 = com.google.android.gms.internal.vision.zzma.zze(r1, r13)
            r2.zza((int) r15, (double) r13)
        L_0x054f:
            int r10 = r10 + 3
            goto L_0x0033
        L_0x0553:
            if (r5 == 0) goto L_0x056a
            com.google.android.gms.internal.vision.zziq<?> r4 = r0.zzr
            r4.zza(r2, r5)
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0568
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            r5 = r4
            goto L_0x0553
        L_0x0568:
            r5 = 0
            goto L_0x0553
        L_0x056a:
            com.google.android.gms.internal.vision.zzlu<?, ?> r3 = r0.zzq
            zza(r3, r1, (com.google.android.gms.internal.vision.zzmr) r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zzb(java.lang.Object, com.google.android.gms.internal.vision.zzmr):void");
    }

    private final <K, V> void zza(zzmr zzmr, int i, Object obj, int i2) {
        if (obj != null) {
            zzmr.zza(i, this.zzs.zzb(zzb(i2)), this.zzs.zzc(obj));
        }
    }

    private static <UT, UB> void zza(zzlu<UT, UB> zzlu, T t, zzmr zzmr) {
        zzlu.zza(zzlu.zzb(t), zzmr);
    }

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public final void zza(T r13, com.google.android.gms.internal.vision.zzld r14, com.google.android.gms.internal.vision.zzio r15) {
        /*
            r12 = this;
            if (r15 == 0) goto L_0x0625
            com.google.android.gms.internal.vision.zzlu<?, ?> r7 = r12.zzq
            com.google.android.gms.internal.vision.zziq<?> r8 = r12.zzr
            r9 = 0
            r0 = r9
            r10 = r0
        L_0x000a:
            int r1 = r14.zza()     // Catch:{ all -> 0x060d }
            int r2 = r12.zzg(r1)     // Catch:{ all -> 0x060d }
            if (r2 >= 0) goto L_0x007c
            r2 = 2147483647(0x7fffffff, float:NaN)
            if (r1 != r2) goto L_0x0030
            int r14 = r12.zzm
        L_0x001b:
            int r15 = r12.zzn
            if (r14 >= r15) goto L_0x002a
            int[] r15 = r12.zzl
            r15 = r15[r14]
            java.lang.Object r10 = r12.zza((java.lang.Object) r13, (int) r15, r10, r7)
            int r14 = r14 + 1
            goto L_0x001b
        L_0x002a:
            if (r10 == 0) goto L_0x002f
            r7.zzb((java.lang.Object) r13, r10)
        L_0x002f:
            return
        L_0x0030:
            boolean r2 = r12.zzh     // Catch:{ all -> 0x060d }
            if (r2 != 0) goto L_0x0036
            r2 = r9
            goto L_0x003d
        L_0x0036:
            com.google.android.gms.internal.vision.zzkk r2 = r12.zzg     // Catch:{ all -> 0x060d }
            java.lang.Object r1 = r8.zza(r15, r2, r1)     // Catch:{ all -> 0x060d }
            r2 = r1
        L_0x003d:
            if (r2 == 0) goto L_0x0055
            if (r0 != 0) goto L_0x0047
            com.google.android.gms.internal.vision.zziu r0 = r8.zzb(r13)     // Catch:{ all -> 0x060d }
            r11 = r0
            goto L_0x0048
        L_0x0047:
            r11 = r0
        L_0x0048:
            r0 = r8
            r1 = r14
            r3 = r15
            r4 = r11
            r5 = r10
            r6 = r7
            java.lang.Object r10 = r0.zza(r1, r2, r3, r4, r5, r6)     // Catch:{ all -> 0x060d }
            r0 = r11
            goto L_0x000a
        L_0x0055:
            r7.zza((com.google.android.gms.internal.vision.zzld) r14)     // Catch:{ all -> 0x060d }
            if (r10 != 0) goto L_0x005f
            java.lang.Object r1 = r7.zzc(r13)     // Catch:{ all -> 0x060d }
            r10 = r1
        L_0x005f:
            boolean r1 = r7.zza(r10, (com.google.android.gms.internal.vision.zzld) r14)     // Catch:{ all -> 0x060d }
            if (r1 != 0) goto L_0x000a
            int r14 = r12.zzm
        L_0x0067:
            int r15 = r12.zzn
            if (r14 >= r15) goto L_0x0076
            int[] r15 = r12.zzl
            r15 = r15[r14]
            java.lang.Object r10 = r12.zza((java.lang.Object) r13, (int) r15, r10, r7)
            int r14 = r14 + 1
            goto L_0x0067
        L_0x0076:
            if (r10 == 0) goto L_0x007b
            r7.zzb((java.lang.Object) r13, r10)
        L_0x007b:
            return
        L_0x007c:
            int r3 = r12.zzd((int) r2)     // Catch:{ all -> 0x060d }
            r4 = 267386880(0xff00000, float:2.3665827E-29)
            r4 = r4 & r3
            int r4 = r4 >>> 20
            r5 = 1048575(0xfffff, float:1.469367E-39)
            switch(r4) {
                case 0: goto L_0x05b3;
                case 1: goto L_0x05a3;
                case 2: goto L_0x0593;
                case 3: goto L_0x0583;
                case 4: goto L_0x0573;
                case 5: goto L_0x0563;
                case 6: goto L_0x0553;
                case 7: goto L_0x0543;
                case 8: goto L_0x053b;
                case 9: goto L_0x0505;
                case 10: goto L_0x04f5;
                case 11: goto L_0x04e5;
                case 12: goto L_0x04c1;
                case 13: goto L_0x04b1;
                case 14: goto L_0x04a1;
                case 15: goto L_0x0491;
                case 16: goto L_0x0481;
                case 17: goto L_0x044b;
                case 18: goto L_0x043d;
                case 19: goto L_0x042f;
                case 20: goto L_0x0421;
                case 21: goto L_0x0413;
                case 22: goto L_0x0405;
                case 23: goto L_0x03f7;
                case 24: goto L_0x03e9;
                case 25: goto L_0x03db;
                case 26: goto L_0x03b8;
                case 27: goto L_0x03a2;
                case 28: goto L_0x0394;
                case 29: goto L_0x0386;
                case 30: goto L_0x0370;
                case 31: goto L_0x0362;
                case 32: goto L_0x0354;
                case 33: goto L_0x0346;
                case 34: goto L_0x0338;
                case 35: goto L_0x032a;
                case 36: goto L_0x031c;
                case 37: goto L_0x030e;
                case 38: goto L_0x0300;
                case 39: goto L_0x02f2;
                case 40: goto L_0x02e4;
                case 41: goto L_0x02d6;
                case 42: goto L_0x02c8;
                case 43: goto L_0x02ba;
                case 44: goto L_0x02a4;
                case 45: goto L_0x0296;
                case 46: goto L_0x0288;
                case 47: goto L_0x027a;
                case 48: goto L_0x026c;
                case 49: goto L_0x0257;
                case 50: goto L_0x0213;
                case 51: goto L_0x0200;
                case 52: goto L_0x01ed;
                case 53: goto L_0x01da;
                case 54: goto L_0x01c7;
                case 55: goto L_0x01b4;
                case 56: goto L_0x01a1;
                case 57: goto L_0x018e;
                case 58: goto L_0x017b;
                case 59: goto L_0x0173;
                case 60: goto L_0x013d;
                case 61: goto L_0x012e;
                case 62: goto L_0x011b;
                case 63: goto L_0x00f4;
                case 64: goto L_0x00e1;
                case 65: goto L_0x00ce;
                case 66: goto L_0x00bb;
                case 67: goto L_0x00a8;
                case 68: goto L_0x0094;
                default: goto L_0x008c;
            }
        L_0x008c:
            if (r10 != 0) goto L_0x05c4
            java.lang.Object r1 = r7.zza()     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x05c3
        L_0x0094:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzlc r5 = r12.zza((int) r2)     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Object r5 = r14.zzb(r5, (com.google.android.gms.internal.vision.zzio) r15)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x00a8:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            long r5 = r14.zzt()     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x00bb:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            int r5 = r14.zzs()     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x00ce:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            long r5 = r14.zzr()     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x00e1:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            int r5 = r14.zzq()     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x00f4:
            int r4 = r14.zzp()     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzjg r6 = r12.zzc((int) r2)     // Catch:{ zzjn -> 0x05e3 }
            if (r6 == 0) goto L_0x010c
            boolean r6 = r6.zza(r4)     // Catch:{ zzjn -> 0x05e3 }
            if (r6 == 0) goto L_0x0105
            goto L_0x010c
        L_0x0105:
            java.lang.Object r10 = com.google.android.gms.internal.vision.zzle.zza((int) r1, (int) r4, r10, r7)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x010c:
            r3 = r3 & r5
            long r5 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r4)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r5, (java.lang.Object) r3)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x011b:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            int r5 = r14.zzo()     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x012e:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzht r5 = r14.zzn()     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x013d:
            boolean r4 = r12.zza(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            if (r4 == 0) goto L_0x015c
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Object r5 = com.google.android.gms.internal.vision.zzma.zzf(r13, r3)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzlc r6 = r12.zza((int) r2)     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Object r6 = r14.zza(r6, (com.google.android.gms.internal.vision.zzio) r15)     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Object r5 = com.google.android.gms.internal.vision.zzjf.zza((java.lang.Object) r5, (java.lang.Object) r6)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x016e
        L_0x015c:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzlc r5 = r12.zza((int) r2)     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Object r5 = r14.zza(r5, (com.google.android.gms.internal.vision.zzio) r15)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
        L_0x016e:
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0173:
            r12.zza((java.lang.Object) r13, (int) r3, (com.google.android.gms.internal.vision.zzld) r14)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x017b:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            boolean r5 = r14.zzk()     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x018e:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            int r5 = r14.zzj()     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x01a1:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            long r5 = r14.zzi()     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x01b4:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            int r5 = r14.zzh()     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x01c7:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            long r5 = r14.zzf()     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x01da:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            long r5 = r14.zzg()     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x01ed:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            float r5 = r14.zze()     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Float r5 = java.lang.Float.valueOf(r5)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0200:
            r3 = r3 & r5
            long r3 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            double r5 = r14.zzd()     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Double r5 = java.lang.Double.valueOf(r5)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r1, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0213:
            java.lang.Object r1 = r12.zzb((int) r2)     // Catch:{ zzjn -> 0x05e3 }
            int r2 = r12.zzd((int) r2)     // Catch:{ zzjn -> 0x05e3 }
            r2 = r2 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Object r4 = com.google.android.gms.internal.vision.zzma.zzf(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            if (r4 != 0) goto L_0x022e
            com.google.android.gms.internal.vision.zzkh r4 = r12.zzs     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Object r4 = r4.zzf(r1)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r2, (java.lang.Object) r4)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x0246
        L_0x022e:
            com.google.android.gms.internal.vision.zzkh r5 = r12.zzs     // Catch:{ zzjn -> 0x05e3 }
            boolean r5 = r5.zzd(r4)     // Catch:{ zzjn -> 0x05e3 }
            if (r5 == 0) goto L_0x0246
            com.google.android.gms.internal.vision.zzkh r5 = r12.zzs     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Object r5 = r5.zzf(r1)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzkh r6 = r12.zzs     // Catch:{ zzjn -> 0x05e3 }
            r6.zza(r5, r4)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r2, (java.lang.Object) r5)     // Catch:{ zzjn -> 0x05e3 }
            r4 = r5
        L_0x0246:
            com.google.android.gms.internal.vision.zzkh r2 = r12.zzs     // Catch:{ zzjn -> 0x05e3 }
            java.util.Map r2 = r2.zza(r4)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzkh r3 = r12.zzs     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzkf r1 = r3.zzb(r1)     // Catch:{ zzjn -> 0x05e3 }
            r14.zza(r2, r1, (com.google.android.gms.internal.vision.zzio) r15)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0257:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzlc r1 = r12.zza((int) r2)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzju r2 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r2 = r2.zza(r13, r3)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzb(r2, r1, r15)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x026c:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzq(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x027a:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzp(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0288:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzo(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0296:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzn(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x02a4:
            com.google.android.gms.internal.vision.zzju r4 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r3 = r3 & r5
            long r5 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r3 = r4.zza(r13, r5)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzm(r3)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzjg r2 = r12.zzc((int) r2)     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Object r10 = com.google.android.gms.internal.vision.zzle.zza(r1, r3, r2, r10, r7)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x02ba:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzl(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x02c8:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzh(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x02d6:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzg(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x02e4:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzf(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x02f2:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zze(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0300:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzc(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x030e:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzd(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x031c:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzb(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x032a:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zza(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0338:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzq(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0346:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzp(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0354:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzo(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0362:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzn(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0370:
            com.google.android.gms.internal.vision.zzju r4 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r3 = r3 & r5
            long r5 = (long) r3     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r3 = r4.zza(r13, r5)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzm(r3)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzjg r2 = r12.zzc((int) r2)     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Object r10 = com.google.android.gms.internal.vision.zzle.zza(r1, r3, r2, r10, r7)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0386:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzl(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0394:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzk(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x03a2:
            com.google.android.gms.internal.vision.zzlc r1 = r12.zza((int) r2)     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzju r4 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r2 = r4.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zza(r2, r1, (com.google.android.gms.internal.vision.zzio) r15)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x03b8:
            boolean r1 = zzf(r3)     // Catch:{ zzjn -> 0x05e3 }
            if (r1 == 0) goto L_0x03cd
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzj(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x03cd:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzi(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x03db:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzh(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x03e9:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzg(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x03f7:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzf(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0405:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zze(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0413:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzc(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0421:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzd(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x042f:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zzb(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x043d:
            com.google.android.gms.internal.vision.zzju r1 = r12.zzp     // Catch:{ zzjn -> 0x05e3 }
            r2 = r3 & r5
            long r2 = (long) r2     // Catch:{ zzjn -> 0x05e3 }
            java.util.List r1 = r1.zza(r13, r2)     // Catch:{ zzjn -> 0x05e3 }
            r14.zza(r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x044b:
            boolean r1 = r12.zza(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            if (r1 == 0) goto L_0x046c
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Object r1 = com.google.android.gms.internal.vision.zzma.zzf(r13, r3)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzlc r2 = r12.zza((int) r2)     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Object r2 = r14.zzb(r2, (com.google.android.gms.internal.vision.zzio) r15)     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Object r1 = com.google.android.gms.internal.vision.zzjf.zza((java.lang.Object) r1, (java.lang.Object) r2)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x046c:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzlc r1 = r12.zza((int) r2)     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Object r1 = r14.zzb(r1, (com.google.android.gms.internal.vision.zzio) r15)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r1)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0481:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            long r5 = r14.zzt()     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (long) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0491:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            int r1 = r14.zzs()     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (int) r1)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x04a1:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            long r5 = r14.zzr()     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (long) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x04b1:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            int r1 = r14.zzq()     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (int) r1)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x04c1:
            int r4 = r14.zzp()     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzjg r6 = r12.zzc((int) r2)     // Catch:{ zzjn -> 0x05e3 }
            if (r6 == 0) goto L_0x04d9
            boolean r6 = r6.zza(r4)     // Catch:{ zzjn -> 0x05e3 }
            if (r6 == 0) goto L_0x04d2
            goto L_0x04d9
        L_0x04d2:
            java.lang.Object r10 = com.google.android.gms.internal.vision.zzle.zza((int) r1, (int) r4, r10, r7)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x04d9:
            r1 = r3 & r5
            long r5 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r5, (int) r4)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x04e5:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            int r1 = r14.zzo()     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (int) r1)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x04f5:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzht r1 = r14.zzn()     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r1)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0505:
            boolean r1 = r12.zza(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            if (r1 == 0) goto L_0x0526
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Object r1 = com.google.android.gms.internal.vision.zzma.zzf(r13, r3)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzlc r2 = r12.zza((int) r2)     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Object r2 = r14.zza(r2, (com.google.android.gms.internal.vision.zzio) r15)     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Object r1 = com.google.android.gms.internal.vision.zzjf.zza((java.lang.Object) r1, (java.lang.Object) r2)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r1)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0526:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzlc r1 = r12.zza((int) r2)     // Catch:{ zzjn -> 0x05e3 }
            java.lang.Object r1 = r14.zza(r1, (com.google.android.gms.internal.vision.zzio) r15)     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (java.lang.Object) r1)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x053b:
            r12.zza((java.lang.Object) r13, (int) r3, (com.google.android.gms.internal.vision.zzld) r14)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0543:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            boolean r1 = r14.zzk()     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (boolean) r1)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0553:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            int r1 = r14.zzj()     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (int) r1)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0563:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            long r5 = r14.zzi()     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (long) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0573:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            int r1 = r14.zzh()     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (int) r1)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0583:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            long r5 = r14.zzf()     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (long) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x0593:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            long r5 = r14.zzg()     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (long) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x05a3:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            float r1 = r14.zze()     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (float) r1)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x05b3:
            r1 = r3 & r5
            long r3 = (long) r1     // Catch:{ zzjn -> 0x05e3 }
            double r5 = r14.zzd()     // Catch:{ zzjn -> 0x05e3 }
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r13, (long) r3, (double) r5)     // Catch:{ zzjn -> 0x05e3 }
            r12.zzb(r13, (int) r2)     // Catch:{ zzjn -> 0x05e3 }
            goto L_0x000a
        L_0x05c3:
            r10 = r1
        L_0x05c4:
            boolean r1 = r7.zza(r10, (com.google.android.gms.internal.vision.zzld) r14)     // Catch:{ zzjn -> 0x05e3 }
            if (r1 != 0) goto L_0x05e1
            int r14 = r12.zzm
        L_0x05cc:
            int r15 = r12.zzn
            if (r14 >= r15) goto L_0x05db
            int[] r15 = r12.zzl
            r15 = r15[r14]
            java.lang.Object r10 = r12.zza((java.lang.Object) r13, (int) r15, r10, r7)
            int r14 = r14 + 1
            goto L_0x05cc
        L_0x05db:
            if (r10 == 0) goto L_0x05e0
            r7.zzb((java.lang.Object) r13, r10)
        L_0x05e0:
            return
        L_0x05e1:
            goto L_0x000a
        L_0x05e3:
            r1 = move-exception
            r7.zza((com.google.android.gms.internal.vision.zzld) r14)     // Catch:{ all -> 0x060d }
            if (r10 != 0) goto L_0x05ee
            java.lang.Object r1 = r7.zzc(r13)     // Catch:{ all -> 0x060d }
            r10 = r1
        L_0x05ee:
            boolean r1 = r7.zza(r10, (com.google.android.gms.internal.vision.zzld) r14)     // Catch:{ all -> 0x060d }
            if (r1 != 0) goto L_0x060b
            int r14 = r12.zzm
        L_0x05f6:
            int r15 = r12.zzn
            if (r14 >= r15) goto L_0x0605
            int[] r15 = r12.zzl
            r15 = r15[r14]
            java.lang.Object r10 = r12.zza((java.lang.Object) r13, (int) r15, r10, r7)
            int r14 = r14 + 1
            goto L_0x05f6
        L_0x0605:
            if (r10 == 0) goto L_0x060a
            r7.zzb((java.lang.Object) r13, r10)
        L_0x060a:
            return
        L_0x060b:
            goto L_0x000a
        L_0x060d:
            r14 = move-exception
            int r15 = r12.zzm
        L_0x0610:
            int r0 = r12.zzn
            if (r15 >= r0) goto L_0x061f
            int[] r0 = r12.zzl
            r0 = r0[r15]
            java.lang.Object r10 = r12.zza((java.lang.Object) r13, (int) r0, r10, r7)
            int r15 = r15 + 1
            goto L_0x0610
        L_0x061f:
            if (r10 == 0) goto L_0x0624
            r7.zzb((java.lang.Object) r13, r10)
        L_0x0624:
            throw r14
        L_0x0625:
            java.lang.NullPointerException r13 = new java.lang.NullPointerException
            r13.<init>()
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zza(java.lang.Object, com.google.android.gms.internal.vision.zzld, com.google.android.gms.internal.vision.zzio):void");
    }

    private static zzlx zze(Object obj) {
        zzjb zzjb = (zzjb) obj;
        zzlx zzlx = zzjb.zzb;
        if (zzlx != zzlx.zza()) {
            return zzlx;
        }
        zzlx zzb2 = zzlx.zzb();
        zzjb.zzb = zzb2;
        return zzb2;
    }

    private static int zza(byte[] bArr, int i, int i2, zzml zzml, Class<?> cls, zzhn zzhn) {
        switch (zzkr.zza[zzml.ordinal()]) {
            case 1:
                int zzb2 = zzhl.zzb(bArr, i, zzhn);
                zzhn.zzc = Boolean.valueOf(zzhn.zzb != 0);
                return zzb2;
            case 2:
                return zzhl.zze(bArr, i, zzhn);
            case 3:
                zzhn.zzc = Double.valueOf(zzhl.zzc(bArr, i));
                return i + 8;
            case 4:
            case 5:
                zzhn.zzc = Integer.valueOf(zzhl.zza(bArr, i));
                return i + 4;
            case 6:
            case 7:
                zzhn.zzc = Long.valueOf(zzhl.zzb(bArr, i));
                return i + 8;
            case 8:
                zzhn.zzc = Float.valueOf(zzhl.zzd(bArr, i));
                return i + 4;
            case 9:
            case 10:
            case 11:
                int zza2 = zzhl.zza(bArr, i, zzhn);
                zzhn.zzc = Integer.valueOf(zzhn.zza);
                return zza2;
            case 12:
            case 13:
                int zzb3 = zzhl.zzb(bArr, i, zzhn);
                zzhn.zzc = Long.valueOf(zzhn.zzb);
                return zzb3;
            case 14:
                return zzhl.zza((zzlc) zzky.zza().zza(cls), bArr, i, i2, zzhn);
            case 15:
                int zza3 = zzhl.zza(bArr, i, zzhn);
                zzhn.zzc = Integer.valueOf(zzif.zze(zzhn.zza));
                return zza3;
            case 16:
                int zzb4 = zzhl.zzb(bArr, i, zzhn);
                zzhn.zzc = Long.valueOf(zzif.zza(zzhn.zzb));
                return zzb4;
            case 17:
                return zzhl.zzd(bArr, i, zzhn);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzhn zzhn) {
        int i8;
        int i9;
        int i10;
        int i11;
        T t2 = t;
        byte[] bArr2 = bArr;
        int i12 = i;
        int i13 = i2;
        int i14 = i3;
        int i15 = i5;
        int i16 = i6;
        long j3 = j2;
        zzhn zzhn2 = zzhn;
        Unsafe unsafe = zzb;
        zzjl zzjl = (zzjl) unsafe.getObject(t2, j3);
        if (!zzjl.zza()) {
            int size = zzjl.size();
            zzjl = zzjl.zza(size == 0 ? 10 : size << 1);
            unsafe.putObject(t2, j3, zzjl);
        }
        switch (i7) {
            case 18:
            case 35:
                if (i15 == 2) {
                    zzin zzin = (zzin) zzjl;
                    int zza2 = zzhl.zza(bArr2, i12, zzhn2);
                    int i17 = zzhn2.zza + zza2;
                    while (zza2 < i17) {
                        zzin.zza(zzhl.zzc(bArr2, zza2));
                        zza2 += 8;
                    }
                    if (zza2 == i17) {
                        return zza2;
                    }
                    throw zzjk.zza();
                } else if (i15 == 1) {
                    zzin zzin2 = (zzin) zzjl;
                    zzin2.zza(zzhl.zzc(bArr, i));
                    int i18 = i12 + 8;
                    while (i18 < i13) {
                        int zza3 = zzhl.zza(bArr2, i18, zzhn2);
                        if (i14 != zzhn2.zza) {
                            return i18;
                        }
                        zzin2.zza(zzhl.zzc(bArr2, zza3));
                        i18 = zza3 + 8;
                    }
                    return i18;
                }
                break;
            case 19:
            case 36:
                if (i15 == 2) {
                    zzja zzja = (zzja) zzjl;
                    int zza4 = zzhl.zza(bArr2, i12, zzhn2);
                    int i19 = zzhn2.zza + zza4;
                    while (zza4 < i19) {
                        zzja.zza(zzhl.zzd(bArr2, zza4));
                        zza4 += 4;
                    }
                    if (zza4 == i19) {
                        return zza4;
                    }
                    throw zzjk.zza();
                } else if (i15 == 5) {
                    zzja zzja2 = (zzja) zzjl;
                    zzja2.zza(zzhl.zzd(bArr, i));
                    int i20 = i12 + 4;
                    while (i20 < i13) {
                        int zza5 = zzhl.zza(bArr2, i20, zzhn2);
                        if (i14 != zzhn2.zza) {
                            return i20;
                        }
                        zzja2.zza(zzhl.zzd(bArr2, zza5));
                        i20 = zza5 + 4;
                    }
                    return i20;
                }
                break;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i15 == 2) {
                    zzjy zzjy = (zzjy) zzjl;
                    int zza6 = zzhl.zza(bArr2, i12, zzhn2);
                    int i21 = zzhn2.zza + zza6;
                    while (zza6 < i21) {
                        zza6 = zzhl.zzb(bArr2, zza6, zzhn2);
                        zzjy.zza(zzhn2.zzb);
                    }
                    if (zza6 == i21) {
                        return zza6;
                    }
                    throw zzjk.zza();
                } else if (i15 == 0) {
                    zzjy zzjy2 = (zzjy) zzjl;
                    int zzb2 = zzhl.zzb(bArr2, i12, zzhn2);
                    zzjy2.zza(zzhn2.zzb);
                    while (zzb2 < i13) {
                        int zza7 = zzhl.zza(bArr2, zzb2, zzhn2);
                        if (i14 != zzhn2.zza) {
                            return zzb2;
                        }
                        zzb2 = zzhl.zzb(bArr2, zza7, zzhn2);
                        zzjy2.zza(zzhn2.zzb);
                    }
                    return zzb2;
                }
                break;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i15 == 2) {
                    return zzhl.zza(bArr2, i12, (zzjl<?>) zzjl, zzhn2);
                }
                if (i15 == 0) {
                    return zzhl.zza(i3, bArr, i, i2, (zzjl<?>) zzjl, zzhn);
                }
                break;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i15 == 2) {
                    zzjy zzjy3 = (zzjy) zzjl;
                    int zza8 = zzhl.zza(bArr2, i12, zzhn2);
                    int i22 = zzhn2.zza + zza8;
                    while (zza8 < i22) {
                        zzjy3.zza(zzhl.zzb(bArr2, zza8));
                        zza8 += 8;
                    }
                    if (zza8 == i22) {
                        return zza8;
                    }
                    throw zzjk.zza();
                } else if (i15 == 1) {
                    zzjy zzjy4 = (zzjy) zzjl;
                    zzjy4.zza(zzhl.zzb(bArr, i));
                    int i23 = i12 + 8;
                    while (i23 < i13) {
                        int zza9 = zzhl.zza(bArr2, i23, zzhn2);
                        if (i14 != zzhn2.zza) {
                            return i23;
                        }
                        zzjy4.zza(zzhl.zzb(bArr2, zza9));
                        i23 = zza9 + 8;
                    }
                    return i23;
                }
                break;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i15 == 2) {
                    zzjd zzjd = (zzjd) zzjl;
                    int zza10 = zzhl.zza(bArr2, i12, zzhn2);
                    int i24 = zzhn2.zza + zza10;
                    while (zza10 < i24) {
                        zzjd.zzc(zzhl.zza(bArr2, zza10));
                        zza10 += 4;
                    }
                    if (zza10 == i24) {
                        return zza10;
                    }
                    throw zzjk.zza();
                } else if (i15 == 5) {
                    zzjd zzjd2 = (zzjd) zzjl;
                    zzjd2.zzc(zzhl.zza(bArr, i));
                    int i25 = i12 + 4;
                    while (i25 < i13) {
                        int zza11 = zzhl.zza(bArr2, i25, zzhn2);
                        if (i14 != zzhn2.zza) {
                            return i25;
                        }
                        zzjd2.zzc(zzhl.zza(bArr2, zza11));
                        i25 = zza11 + 4;
                    }
                    return i25;
                }
                break;
            case 25:
            case 42:
                if (i15 == 2) {
                    zzhr zzhr = (zzhr) zzjl;
                    int zza12 = zzhl.zza(bArr2, i12, zzhn2);
                    int i26 = zzhn2.zza + zza12;
                    while (zza12 < i26) {
                        zza12 = zzhl.zzb(bArr2, zza12, zzhn2);
                        zzhr.zza(zzhn2.zzb != 0);
                    }
                    if (zza12 == i26) {
                        return zza12;
                    }
                    throw zzjk.zza();
                } else if (i15 == 0) {
                    zzhr zzhr2 = (zzhr) zzjl;
                    int zzb3 = zzhl.zzb(bArr2, i12, zzhn2);
                    zzhr2.zza(zzhn2.zzb != 0);
                    while (zzb3 < i13) {
                        int zza13 = zzhl.zza(bArr2, zzb3, zzhn2);
                        if (i14 != zzhn2.zza) {
                            return zzb3;
                        }
                        zzb3 = zzhl.zzb(bArr2, zza13, zzhn2);
                        zzhr2.zza(zzhn2.zzb != 0);
                    }
                    return zzb3;
                }
                break;
            case 26:
                if (i15 == 2) {
                    if ((j & IjkMediaMeta.AV_CH_STEREO_LEFT) == 0) {
                        int zza14 = zzhl.zza(bArr2, i12, zzhn2);
                        int i27 = zzhn2.zza;
                        if (i27 >= 0) {
                            if (i27 == 0) {
                                zzjl.add("");
                            } else {
                                zzjl.add(new String(bArr2, zza14, i27, zzjf.zza));
                                zza14 += i27;
                            }
                            while (i9 < i13) {
                                int zza15 = zzhl.zza(bArr2, i9, zzhn2);
                                if (i14 != zzhn2.zza) {
                                    return i9;
                                }
                                i9 = zzhl.zza(bArr2, zza15, zzhn2);
                                int i28 = zzhn2.zza;
                                if (i28 < 0) {
                                    throw zzjk.zzb();
                                } else if (i28 == 0) {
                                    zzjl.add("");
                                } else {
                                    zzjl.add(new String(bArr2, i9, i28, zzjf.zza));
                                    i9 += i28;
                                }
                            }
                            return i9;
                        }
                        throw zzjk.zzb();
                    }
                    int zza16 = zzhl.zza(bArr2, i12, zzhn2);
                    int i29 = zzhn2.zza;
                    if (i29 >= 0) {
                        if (i29 == 0) {
                            zzjl.add("");
                        } else {
                            int i30 = zza16 + i29;
                            if (zzmd.zza(bArr2, zza16, i30)) {
                                zzjl.add(new String(bArr2, zza16, i29, zzjf.zza));
                                zza16 = i30;
                            } else {
                                throw zzjk.zzh();
                            }
                        }
                        while (i8 < i13) {
                            int zza17 = zzhl.zza(bArr2, i8, zzhn2);
                            if (i14 != zzhn2.zza) {
                                return i8;
                            }
                            i8 = zzhl.zza(bArr2, zza17, zzhn2);
                            int i31 = zzhn2.zza;
                            if (i31 < 0) {
                                throw zzjk.zzb();
                            } else if (i31 == 0) {
                                zzjl.add("");
                            } else {
                                int i32 = i8 + i31;
                                if (zzmd.zza(bArr2, i8, i32)) {
                                    zzjl.add(new String(bArr2, i8, i31, zzjf.zza));
                                    i8 = i32;
                                } else {
                                    throw zzjk.zzh();
                                }
                            }
                        }
                        return i8;
                    }
                    throw zzjk.zzb();
                }
                break;
            case 27:
                if (i15 == 2) {
                    return zzhl.zza(zza(i16), i3, bArr, i, i2, zzjl, zzhn);
                }
                break;
            case 28:
                if (i15 == 2) {
                    int zza18 = zzhl.zza(bArr2, i12, zzhn2);
                    int i33 = zzhn2.zza;
                    if (i33 < 0) {
                        throw zzjk.zzb();
                    } else if (i33 <= bArr2.length - zza18) {
                        if (i33 == 0) {
                            zzjl.add(zzht.zza);
                        } else {
                            zzjl.add(zzht.zza(bArr2, zza18, i33));
                            zza18 += i33;
                        }
                        while (i10 < i13) {
                            int zza19 = zzhl.zza(bArr2, i10, zzhn2);
                            if (i14 != zzhn2.zza) {
                                return i10;
                            }
                            i10 = zzhl.zza(bArr2, zza19, zzhn2);
                            int i34 = zzhn2.zza;
                            if (i34 < 0) {
                                throw zzjk.zzb();
                            } else if (i34 > bArr2.length - i10) {
                                throw zzjk.zza();
                            } else if (i34 == 0) {
                                zzjl.add(zzht.zza);
                            } else {
                                zzjl.add(zzht.zza(bArr2, i10, i34));
                                i10 += i34;
                            }
                        }
                        return i10;
                    } else {
                        throw zzjk.zza();
                    }
                }
                break;
            case 30:
            case 44:
                if (i15 == 2) {
                    i11 = zzhl.zza(bArr2, i12, (zzjl<?>) zzjl, zzhn2);
                } else if (i15 == 0) {
                    i11 = zzhl.zza(i3, bArr, i, i2, (zzjl<?>) zzjl, zzhn);
                }
                zzjb zzjb = (zzjb) t2;
                zzlx zzlx = zzjb.zzb;
                if (zzlx == zzlx.zza()) {
                    zzlx = null;
                }
                zzlx zzlx2 = (zzlx) zzle.zza(i4, zzjl, zzc(i16), zzlx, this.zzq);
                if (zzlx2 != null) {
                    zzjb.zzb = zzlx2;
                }
                return i11;
            case 33:
            case 47:
                if (i15 == 2) {
                    zzjd zzjd3 = (zzjd) zzjl;
                    int zza20 = zzhl.zza(bArr2, i12, zzhn2);
                    int i35 = zzhn2.zza + zza20;
                    while (zza20 < i35) {
                        zza20 = zzhl.zza(bArr2, zza20, zzhn2);
                        zzjd3.zzc(zzif.zze(zzhn2.zza));
                    }
                    if (zza20 == i35) {
                        return zza20;
                    }
                    throw zzjk.zza();
                } else if (i15 == 0) {
                    zzjd zzjd4 = (zzjd) zzjl;
                    int zza21 = zzhl.zza(bArr2, i12, zzhn2);
                    zzjd4.zzc(zzif.zze(zzhn2.zza));
                    while (zza21 < i13) {
                        int zza22 = zzhl.zza(bArr2, zza21, zzhn2);
                        if (i14 != zzhn2.zza) {
                            return zza21;
                        }
                        zza21 = zzhl.zza(bArr2, zza22, zzhn2);
                        zzjd4.zzc(zzif.zze(zzhn2.zza));
                    }
                    return zza21;
                }
                break;
            case 34:
            case 48:
                if (i15 == 2) {
                    zzjy zzjy5 = (zzjy) zzjl;
                    int zza23 = zzhl.zza(bArr2, i12, zzhn2);
                    int i36 = zzhn2.zza + zza23;
                    while (zza23 < i36) {
                        zza23 = zzhl.zzb(bArr2, zza23, zzhn2);
                        zzjy5.zza(zzif.zza(zzhn2.zzb));
                    }
                    if (zza23 == i36) {
                        return zza23;
                    }
                    throw zzjk.zza();
                } else if (i15 == 0) {
                    zzjy zzjy6 = (zzjy) zzjl;
                    int zzb4 = zzhl.zzb(bArr2, i12, zzhn2);
                    zzjy6.zza(zzif.zza(zzhn2.zzb));
                    while (zzb4 < i13) {
                        int zza24 = zzhl.zza(bArr2, zzb4, zzhn2);
                        if (i14 != zzhn2.zza) {
                            return zzb4;
                        }
                        zzb4 = zzhl.zzb(bArr2, zza24, zzhn2);
                        zzjy6.zza(zzif.zza(zzhn2.zzb));
                    }
                    return zzb4;
                }
                break;
            case 49:
                if (i15 == 3) {
                    zzlc zza25 = zza(i16);
                    int i37 = (i14 & -8) | 4;
                    int zza26 = zzhl.zza(zza25, bArr, i, i2, i37, zzhn);
                    zzjl.add(zzhn2.zzc);
                    while (zza26 < i13) {
                        int zza27 = zzhl.zza(bArr2, zza26, zzhn2);
                        if (i14 != zzhn2.zza) {
                            return zza26;
                        }
                        zza26 = zzhl.zza(zza25, bArr, zza27, i2, i37, zzhn);
                        zzjl.add(zzhn2.zzc);
                    }
                    return zza26;
                }
                break;
        }
        return i12;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v11, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v12, resolved type: byte} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final <K, V> int zza(T r8, byte[] r9, int r10, int r11, int r12, long r13, com.google.android.gms.internal.vision.zzhn r15) {
        /*
            r7 = this;
            sun.misc.Unsafe r0 = zzb
            java.lang.Object r12 = r7.zzb((int) r12)
            java.lang.Object r1 = r0.getObject(r8, r13)
            com.google.android.gms.internal.vision.zzkh r2 = r7.zzs
            boolean r2 = r2.zzd(r1)
            if (r2 == 0) goto L_0x0022
            com.google.android.gms.internal.vision.zzkh r2 = r7.zzs
            java.lang.Object r2 = r2.zzf(r12)
            com.google.android.gms.internal.vision.zzkh r3 = r7.zzs
            r3.zza(r2, r1)
            r0.putObject(r8, r13, r2)
            r1 = r2
        L_0x0022:
            com.google.android.gms.internal.vision.zzkh r8 = r7.zzs
            com.google.android.gms.internal.vision.zzkf r8 = r8.zzb(r12)
            com.google.android.gms.internal.vision.zzkh r12 = r7.zzs
            java.util.Map r12 = r12.zza(r1)
            int r10 = com.google.android.gms.internal.vision.zzhl.zza(r9, r10, r15)
            int r13 = r15.zza
            if (r13 < 0) goto L_0x0099
            int r14 = r11 - r10
            if (r13 > r14) goto L_0x0099
            int r13 = r13 + r10
            K r14 = r8.zzb
            V r0 = r8.zzd
        L_0x0040:
            if (r10 >= r13) goto L_0x008d
            int r1 = r10 + 1
            byte r10 = r9[r10]
            if (r10 >= 0) goto L_0x0050
            int r1 = com.google.android.gms.internal.vision.zzhl.zza((int) r10, (byte[]) r9, (int) r1, (com.google.android.gms.internal.vision.zzhn) r15)
            int r10 = r15.zza
            r2 = r1
            goto L_0x0051
        L_0x0050:
            r2 = r1
        L_0x0051:
            int r1 = r10 >>> 3
            r3 = r10 & 7
            switch(r1) {
                case 1: goto L_0x0073;
                case 2: goto L_0x0059;
                default: goto L_0x0058;
            }
        L_0x0058:
            goto L_0x0088
        L_0x0059:
            com.google.android.gms.internal.vision.zzml r1 = r8.zzc
            int r1 = r1.zzb()
            if (r3 != r1) goto L_0x0088
            com.google.android.gms.internal.vision.zzml r4 = r8.zzc
            V r10 = r8.zzd
            java.lang.Class r5 = r10.getClass()
            r1 = r9
            r3 = r11
            r6 = r15
            int r10 = zza((byte[]) r1, (int) r2, (int) r3, (com.google.android.gms.internal.vision.zzml) r4, (java.lang.Class<?>) r5, (com.google.android.gms.internal.vision.zzhn) r6)
            java.lang.Object r0 = r15.zzc
            goto L_0x0040
        L_0x0073:
            com.google.android.gms.internal.vision.zzml r1 = r8.zza
            int r1 = r1.zzb()
            if (r3 != r1) goto L_0x0088
            com.google.android.gms.internal.vision.zzml r4 = r8.zza
            r5 = 0
            r1 = r9
            r3 = r11
            r6 = r15
            int r10 = zza((byte[]) r1, (int) r2, (int) r3, (com.google.android.gms.internal.vision.zzml) r4, (java.lang.Class<?>) r5, (com.google.android.gms.internal.vision.zzhn) r6)
            java.lang.Object r14 = r15.zzc
            goto L_0x0040
        L_0x0088:
            int r10 = com.google.android.gms.internal.vision.zzhl.zza((int) r10, (byte[]) r9, (int) r2, (int) r11, (com.google.android.gms.internal.vision.zzhn) r15)
            goto L_0x0040
        L_0x008d:
            if (r10 != r13) goto L_0x0094
            r12.put(r14, r0)
            return r13
        L_0x0094:
            com.google.android.gms.internal.vision.zzjk r8 = com.google.android.gms.internal.vision.zzjk.zzg()
            throw r8
        L_0x0099:
            com.google.android.gms.internal.vision.zzjk r8 = com.google.android.gms.internal.vision.zzjk.zza()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zza(java.lang.Object, byte[], int, int, int, long, com.google.android.gms.internal.vision.zzhn):int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:65:0x01a4, code lost:
        r12.putInt(r1, r13, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:?, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:?, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int zza(T r17, byte[] r18, int r19, int r20, int r21, int r22, int r23, int r24, int r25, long r26, int r28, com.google.android.gms.internal.vision.zzhn r29) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r3 = r18
            r4 = r19
            r2 = r21
            r8 = r22
            r5 = r23
            r9 = r26
            r6 = r28
            r11 = r29
            sun.misc.Unsafe r12 = zzb
            int[] r7 = r0.zzc
            int r13 = r6 + 2
            r7 = r7[r13]
            r13 = 1048575(0xfffff, float:1.469367E-39)
            r7 = r7 & r13
            long r13 = (long) r7
            r7 = 5
            r15 = 2
            switch(r25) {
                case 51: goto L_0x0193;
                case 52: goto L_0x0183;
                case 53: goto L_0x0173;
                case 54: goto L_0x0173;
                case 55: goto L_0x0163;
                case 56: goto L_0x0152;
                case 57: goto L_0x0142;
                case 58: goto L_0x0129;
                case 59: goto L_0x00f5;
                case 60: goto L_0x00c6;
                case 61: goto L_0x00b9;
                case 62: goto L_0x0163;
                case 63: goto L_0x008b;
                case 64: goto L_0x0142;
                case 65: goto L_0x0152;
                case 66: goto L_0x0076;
                case 67: goto L_0x0061;
                case 68: goto L_0x0028;
                default: goto L_0x0026;
            }
        L_0x0026:
            goto L_0x01a8
        L_0x0028:
            r7 = 3
            if (r5 != r7) goto L_0x01a8
            r2 = r2 & -8
            r7 = r2 | 4
            com.google.android.gms.internal.vision.zzlc r2 = r0.zza((int) r6)
            r3 = r18
            r4 = r19
            r5 = r20
            r6 = r7
            r7 = r29
            int r2 = com.google.android.gms.internal.vision.zzhl.zza((com.google.android.gms.internal.vision.zzlc) r2, (byte[]) r3, (int) r4, (int) r5, (int) r6, (com.google.android.gms.internal.vision.zzhn) r7)
            int r3 = r12.getInt(r1, r13)
            if (r3 != r8) goto L_0x004c
            java.lang.Object r15 = r12.getObject(r1, r9)
            goto L_0x004d
        L_0x004c:
            r15 = 0
        L_0x004d:
            if (r15 != 0) goto L_0x0056
            java.lang.Object r3 = r11.zzc
            r12.putObject(r1, r9, r3)
            goto L_0x01a4
        L_0x0056:
            java.lang.Object r3 = r11.zzc
            java.lang.Object r3 = com.google.android.gms.internal.vision.zzjf.zza((java.lang.Object) r15, (java.lang.Object) r3)
            r12.putObject(r1, r9, r3)
            goto L_0x01a4
        L_0x0061:
            if (r5 != 0) goto L_0x01a8
            int r2 = com.google.android.gms.internal.vision.zzhl.zzb(r3, r4, r11)
            long r3 = r11.zzb
            long r3 = com.google.android.gms.internal.vision.zzif.zza((long) r3)
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            r12.putObject(r1, r9, r3)
            goto L_0x01a4
        L_0x0076:
            if (r5 != 0) goto L_0x01a8
            int r2 = com.google.android.gms.internal.vision.zzhl.zza(r3, r4, r11)
            int r3 = r11.zza
            int r3 = com.google.android.gms.internal.vision.zzif.zze(r3)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r12.putObject(r1, r9, r3)
            goto L_0x01a4
        L_0x008b:
            if (r5 != 0) goto L_0x01a8
            int r3 = com.google.android.gms.internal.vision.zzhl.zza(r3, r4, r11)
            int r4 = r11.zza
            com.google.android.gms.internal.vision.zzjg r5 = r0.zzc((int) r6)
            if (r5 == 0) goto L_0x00af
            boolean r5 = r5.zza(r4)
            if (r5 == 0) goto L_0x00a0
            goto L_0x00af
        L_0x00a0:
            com.google.android.gms.internal.vision.zzlx r1 = zze((java.lang.Object) r17)
            long r4 = (long) r4
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r1.zza((int) r2, (java.lang.Object) r4)
            r2 = r3
            goto L_0x01a9
        L_0x00af:
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            r12.putObject(r1, r9, r2)
            r2 = r3
            goto L_0x01a4
        L_0x00b9:
            if (r5 != r15) goto L_0x01a8
            int r2 = com.google.android.gms.internal.vision.zzhl.zze(r3, r4, r11)
            java.lang.Object r3 = r11.zzc
            r12.putObject(r1, r9, r3)
            goto L_0x01a4
        L_0x00c6:
            if (r5 != r15) goto L_0x01a8
            com.google.android.gms.internal.vision.zzlc r2 = r0.zza((int) r6)
            r5 = r20
            int r2 = com.google.android.gms.internal.vision.zzhl.zza((com.google.android.gms.internal.vision.zzlc) r2, (byte[]) r3, (int) r4, (int) r5, (com.google.android.gms.internal.vision.zzhn) r11)
            int r3 = r12.getInt(r1, r13)
            if (r3 != r8) goto L_0x00de
            java.lang.Object r15 = r12.getObject(r1, r9)
            goto L_0x00df
        L_0x00de:
            r15 = 0
        L_0x00df:
            if (r15 != 0) goto L_0x00e7
            java.lang.Object r3 = r11.zzc
            r12.putObject(r1, r9, r3)
            goto L_0x00f0
        L_0x00e7:
            java.lang.Object r3 = r11.zzc
            java.lang.Object r3 = com.google.android.gms.internal.vision.zzjf.zza((java.lang.Object) r15, (java.lang.Object) r3)
            r12.putObject(r1, r9, r3)
        L_0x00f0:
            r12.putInt(r1, r13, r8)
            goto L_0x01a9
        L_0x00f5:
            if (r5 != r15) goto L_0x01a8
            int r2 = com.google.android.gms.internal.vision.zzhl.zza(r3, r4, r11)
            int r4 = r11.zza
            if (r4 != 0) goto L_0x0105
            java.lang.String r3 = ""
            r12.putObject(r1, r9, r3)
            goto L_0x0124
        L_0x0105:
            r5 = 536870912(0x20000000, float:1.0842022E-19)
            r5 = r24 & r5
            if (r5 == 0) goto L_0x0119
            int r5 = r2 + r4
            boolean r5 = com.google.android.gms.internal.vision.zzmd.zza((byte[]) r3, (int) r2, (int) r5)
            if (r5 == 0) goto L_0x0114
            goto L_0x0119
        L_0x0114:
            com.google.android.gms.internal.vision.zzjk r1 = com.google.android.gms.internal.vision.zzjk.zzh()
            throw r1
        L_0x0119:
            java.lang.String r5 = new java.lang.String
            java.nio.charset.Charset r6 = com.google.android.gms.internal.vision.zzjf.zza
            r5.<init>(r3, r2, r4, r6)
            r12.putObject(r1, r9, r5)
            int r2 = r2 + r4
        L_0x0124:
            r12.putInt(r1, r13, r8)
            goto L_0x01a9
        L_0x0129:
            if (r5 != 0) goto L_0x01a8
            int r2 = com.google.android.gms.internal.vision.zzhl.zzb(r3, r4, r11)
            long r3 = r11.zzb
            r5 = 0
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 == 0) goto L_0x0139
            r15 = 1
            goto L_0x013a
        L_0x0139:
            r15 = 0
        L_0x013a:
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r15)
            r12.putObject(r1, r9, r3)
            goto L_0x01a4
        L_0x0142:
            if (r5 != r7) goto L_0x01a8
            int r2 = com.google.android.gms.internal.vision.zzhl.zza(r18, r19)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r12.putObject(r1, r9, r2)
            int r2 = r4 + 4
            goto L_0x01a4
        L_0x0152:
            r2 = 1
            if (r5 != r2) goto L_0x01a8
            long r2 = com.google.android.gms.internal.vision.zzhl.zzb(r18, r19)
            java.lang.Long r2 = java.lang.Long.valueOf(r2)
            r12.putObject(r1, r9, r2)
            int r2 = r4 + 8
            goto L_0x01a4
        L_0x0163:
            if (r5 != 0) goto L_0x01a8
            int r2 = com.google.android.gms.internal.vision.zzhl.zza(r3, r4, r11)
            int r3 = r11.zza
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r12.putObject(r1, r9, r3)
            goto L_0x01a4
        L_0x0173:
            if (r5 != 0) goto L_0x01a8
            int r2 = com.google.android.gms.internal.vision.zzhl.zzb(r3, r4, r11)
            long r3 = r11.zzb
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            r12.putObject(r1, r9, r3)
            goto L_0x01a4
        L_0x0183:
            if (r5 != r7) goto L_0x01a8
            float r2 = com.google.android.gms.internal.vision.zzhl.zzd(r18, r19)
            java.lang.Float r2 = java.lang.Float.valueOf(r2)
            r12.putObject(r1, r9, r2)
            int r2 = r4 + 4
            goto L_0x01a4
        L_0x0193:
            r2 = 1
            if (r5 != r2) goto L_0x01a8
            double r2 = com.google.android.gms.internal.vision.zzhl.zzc(r18, r19)
            java.lang.Double r2 = java.lang.Double.valueOf(r2)
            r12.putObject(r1, r9, r2)
            int r2 = r4 + 8
        L_0x01a4:
            r12.putInt(r1, r13, r8)
            goto L_0x01a9
        L_0x01a8:
            r2 = r4
        L_0x01a9:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zza(java.lang.Object, byte[], int, int, int, int, int, int, int, long, int, com.google.android.gms.internal.vision.zzhn):int");
    }

    private final zzlc zza(int i) {
        int i2 = (i / 3) << 1;
        zzlc zzlc = (zzlc) this.zzd[i2];
        if (zzlc != null) {
            return zzlc;
        }
        zzlc zza2 = zzky.zza().zza((Class) this.zzd[i2 + 1]);
        this.zzd[i2] = zza2;
        return zza2;
    }

    private final Object zzb(int i) {
        return this.zzd[(i / 3) << 1];
    }

    private final zzjg zzc(int i) {
        return (zzjg) this.zzd[((i / 3) << 1) + 1];
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: com.google.android.gms.internal.vision.zzlx} */
    /* JADX WARNING: type inference failed for: r1v17, types: [java.lang.Throwable, com.google.android.gms.internal.vision.zzjh] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int zza(T r34, byte[] r35, int r36, int r37, int r38, com.google.android.gms.internal.vision.zzhn r39) {
        /*
            r33 = this;
            r15 = r33
            r14 = r34
            r12 = r35
            r13 = r37
            r11 = r38
            r9 = r39
            sun.misc.Unsafe r10 = zzb
            r16 = 0
            r0 = r36
            r2 = r16
            r3 = r2
            r5 = r3
            r1 = -1
            r6 = 1048575(0xfffff, float:1.469367E-39)
        L_0x001e:
            if (r0 >= r13) goto L_0x0743
            int r3 = r0 + 1
            byte r0 = r12[r0]
            if (r0 >= 0) goto L_0x002d
            int r0 = com.google.android.gms.internal.vision.zzhl.zza((int) r0, (byte[]) r12, (int) r3, (com.google.android.gms.internal.vision.zzhn) r9)
            int r3 = r9.zza
            goto L_0x0032
        L_0x002d:
            r32 = r3
            r3 = r0
            r0 = r32
        L_0x0032:
            int r7 = r3 >>> 3
            r8 = r3 & 7
            r4 = 3
            if (r7 <= r1) goto L_0x0040
            int r2 = r2 / r4
            int r1 = r15.zza((int) r7, (int) r2)
            r2 = r1
            goto L_0x0045
        L_0x0040:
            int r1 = r15.zzg(r7)
            r2 = r1
        L_0x0045:
            r19 = 0
            r4 = -1
            if (r2 != r4) goto L_0x005d
            r2 = r0
            r21 = r3
            r18 = r4
            r25 = r5
            r36 = r7
            r31 = r10
            r15 = r11
            r24 = r16
            r23 = 1
            goto L_0x04e6
        L_0x005d:
            int[] r4 = r15.zzc
            int r22 = r2 + 1
            r1 = r4[r22]
            r22 = 267386880(0xff00000, float:2.3665827E-29)
            r22 = r1 & r22
            int r11 = r22 >>> 20
            r22 = r0
            r17 = 1048575(0xfffff, float:1.469367E-39)
            r0 = r1 & r17
            long r12 = (long) r0
            r0 = 17
            r24 = r3
            if (r11 > r0) goto L_0x03b2
            int r0 = r2 + 2
            r0 = r4[r0]
            int r4 = r0 >>> 20
            r23 = 1
            int r25 = r23 << r4
            r4 = 1048575(0xfffff, float:1.469367E-39)
            r0 = r0 & r4
            if (r0 == r6) goto L_0x009b
            if (r6 == r4) goto L_0x0091
            long r3 = (long) r6
            r10.putInt(r14, r3, r5)
        L_0x0091:
            long r3 = (long) r0
            int r5 = r10.getInt(r14, r3)
            r26 = r0
            r6 = r5
            goto L_0x009e
        L_0x009b:
            r26 = r6
            r6 = r5
        L_0x009e:
            r0 = 5
            switch(r11) {
                case 0: goto L_0x0379;
                case 1: goto L_0x0352;
                case 2: goto L_0x0322;
                case 3: goto L_0x0322;
                case 4: goto L_0x02fa;
                case 5: goto L_0x02c8;
                case 6: goto L_0x029e;
                case 7: goto L_0x0268;
                case 8: goto L_0x0233;
                case 9: goto L_0x01ee;
                case 10: goto L_0x01c5;
                case 11: goto L_0x02fa;
                case 12: goto L_0x016e;
                case 13: goto L_0x029e;
                case 14: goto L_0x02c8;
                case 15: goto L_0x0142;
                case 16: goto L_0x010a;
                case 17: goto L_0x00b1;
                default: goto L_0x00a2;
            }
        L_0x00a2:
            r12 = r35
            r4 = r2
            r11 = r22
            r13 = r24
            r0 = 1
            r18 = -1
            r21 = 1048575(0xfffff, float:1.469367E-39)
            goto L_0x039f
        L_0x00b1:
            r0 = 3
            if (r8 != r0) goto L_0x00f9
            int r0 = r7 << 3
            r4 = r0 | 4
            com.google.android.gms.internal.vision.zzlc r0 = r15.zza((int) r2)
            r11 = r22
            r1 = r35
            r8 = r2
            r2 = r11
            r11 = r24
            r3 = r37
            r18 = -1
            r21 = 1048575(0xfffff, float:1.469367E-39)
            r5 = r39
            int r0 = com.google.android.gms.internal.vision.zzhl.zza((com.google.android.gms.internal.vision.zzlc) r0, (byte[]) r1, (int) r2, (int) r3, (int) r4, (com.google.android.gms.internal.vision.zzhn) r5)
            r1 = r6 & r25
            if (r1 != 0) goto L_0x00dc
            java.lang.Object r1 = r9.zzc
            r10.putObject(r14, r12, r1)
            goto L_0x00ea
        L_0x00dc:
            java.lang.Object r1 = r10.getObject(r14, r12)
            java.lang.Object r2 = r9.zzc
            java.lang.Object r1 = com.google.android.gms.internal.vision.zzjf.zza((java.lang.Object) r1, (java.lang.Object) r2)
            r10.putObject(r14, r12, r1)
        L_0x00ea:
            r5 = r6 | r25
            r12 = r35
            r13 = r37
            r1 = r7
            r2 = r8
            r3 = r11
            r6 = r26
            r11 = r38
            goto L_0x001e
        L_0x00f9:
            r8 = r2
            r11 = r22
            r4 = r24
            r18 = -1
            r21 = 1048575(0xfffff, float:1.469367E-39)
            r12 = r35
            r13 = r4
            r4 = r8
            r0 = 1
            goto L_0x039f
        L_0x010a:
            r5 = r2
            r11 = r22
            r4 = r24
            r18 = -1
            r21 = 1048575(0xfffff, float:1.469367E-39)
            if (r8 != 0) goto L_0x013b
            r2 = r12
            r12 = r35
            int r8 = com.google.android.gms.internal.vision.zzhl.zzb(r12, r11, r9)
            long r0 = r9.zzb
            long r19 = com.google.android.gms.internal.vision.zzif.zza((long) r0)
            r0 = r10
            r1 = r34
            r13 = r4
            r11 = r5
            r4 = r19
            r0.putLong(r1, r2, r4)
            r5 = r6 | r25
            r1 = r7
            r0 = r8
            r2 = r11
            r3 = r13
            r6 = r26
            r13 = r37
            r11 = r38
            goto L_0x001e
        L_0x013b:
            r12 = r35
            r13 = r4
            r4 = r5
            r0 = 1
            goto L_0x039f
        L_0x0142:
            r4 = r2
            r2 = r12
            r11 = r22
            r13 = r24
            r18 = -1
            r21 = 1048575(0xfffff, float:1.469367E-39)
            r12 = r35
            if (r8 != 0) goto L_0x016b
            int r0 = com.google.android.gms.internal.vision.zzhl.zza(r12, r11, r9)
            int r1 = r9.zza
            int r1 = com.google.android.gms.internal.vision.zzif.zze(r1)
            r10.putInt(r14, r2, r1)
            r5 = r6 | r25
            r11 = r38
            r2 = r4
            r1 = r7
            r3 = r13
            r6 = r26
            r13 = r37
            goto L_0x001e
        L_0x016b:
            r0 = 1
            goto L_0x039f
        L_0x016e:
            r4 = r2
            r2 = r12
            r11 = r22
            r13 = r24
            r18 = -1
            r21 = 1048575(0xfffff, float:1.469367E-39)
            r12 = r35
            if (r8 != 0) goto L_0x01c2
            int r0 = com.google.android.gms.internal.vision.zzhl.zza(r12, r11, r9)
            int r1 = r9.zza
            com.google.android.gms.internal.vision.zzjg r5 = r15.zzc((int) r4)
            if (r5 == 0) goto L_0x01ae
            boolean r5 = r5.zza(r1)
            if (r5 == 0) goto L_0x0192
            r36 = r0
            goto L_0x01b0
        L_0x0192:
            com.google.android.gms.internal.vision.zzlx r2 = zze((java.lang.Object) r34)
            r36 = r0
            long r0 = (long) r1
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            r2.zza((int) r13, (java.lang.Object) r0)
            r0 = r36
            r11 = r38
            r2 = r4
            r5 = r6
            r1 = r7
            r3 = r13
            r6 = r26
            r13 = r37
            goto L_0x001e
        L_0x01ae:
            r36 = r0
        L_0x01b0:
            r10.putInt(r14, r2, r1)
            r5 = r6 | r25
            r0 = r36
            r11 = r38
            r2 = r4
            r1 = r7
            r3 = r13
            r6 = r26
            r13 = r37
            goto L_0x001e
        L_0x01c2:
            r0 = 1
            goto L_0x039f
        L_0x01c5:
            r4 = r2
            r2 = r12
            r11 = r22
            r13 = r24
            r18 = -1
            r21 = 1048575(0xfffff, float:1.469367E-39)
            r12 = r35
            r0 = 2
            if (r8 != r0) goto L_0x01eb
            int r0 = com.google.android.gms.internal.vision.zzhl.zze(r12, r11, r9)
            java.lang.Object r1 = r9.zzc
            r10.putObject(r14, r2, r1)
            r5 = r6 | r25
            r11 = r38
            r2 = r4
            r1 = r7
            r3 = r13
            r6 = r26
            r13 = r37
            goto L_0x001e
        L_0x01eb:
            r0 = 1
            goto L_0x039f
        L_0x01ee:
            r4 = r2
            r2 = r12
            r11 = r22
            r13 = r24
            r18 = -1
            r21 = 1048575(0xfffff, float:1.469367E-39)
            r12 = r35
            r0 = 2
            if (r8 != r0) goto L_0x022e
            com.google.android.gms.internal.vision.zzlc r0 = r15.zza((int) r4)
            r5 = r37
            int r0 = com.google.android.gms.internal.vision.zzhl.zza((com.google.android.gms.internal.vision.zzlc) r0, (byte[]) r12, (int) r11, (int) r5, (com.google.android.gms.internal.vision.zzhn) r9)
            r1 = r6 & r25
            if (r1 != 0) goto L_0x0213
            java.lang.Object r1 = r9.zzc
            r10.putObject(r14, r2, r1)
            goto L_0x0221
        L_0x0213:
            java.lang.Object r1 = r10.getObject(r14, r2)
            java.lang.Object r8 = r9.zzc
            java.lang.Object r1 = com.google.android.gms.internal.vision.zzjf.zza((java.lang.Object) r1, (java.lang.Object) r8)
            r10.putObject(r14, r2, r1)
        L_0x0221:
            r1 = r6 | r25
            r11 = r38
            r2 = r4
            r3 = r13
            r6 = r26
            r13 = r5
            r5 = r1
            r1 = r7
            goto L_0x001e
        L_0x022e:
            r5 = r37
            r0 = 1
            goto L_0x039f
        L_0x0233:
            r5 = r37
            r4 = r2
            r2 = r12
            r11 = r22
            r13 = r24
            r18 = -1
            r21 = 1048575(0xfffff, float:1.469367E-39)
            r12 = r35
            r0 = 2
            if (r8 != r0) goto L_0x0265
            r0 = 536870912(0x20000000, float:1.0842022E-19)
            r0 = r0 & r1
            if (r0 != 0) goto L_0x024f
            int r0 = com.google.android.gms.internal.vision.zzhl.zzc(r12, r11, r9)
            goto L_0x0253
        L_0x024f:
            int r0 = com.google.android.gms.internal.vision.zzhl.zzd(r12, r11, r9)
        L_0x0253:
            java.lang.Object r1 = r9.zzc
            r10.putObject(r14, r2, r1)
            r1 = r6 | r25
            r11 = r38
            r2 = r4
            r3 = r13
            r6 = r26
            r13 = r5
            r5 = r1
            r1 = r7
            goto L_0x001e
        L_0x0265:
            r0 = 1
            goto L_0x039f
        L_0x0268:
            r5 = r37
            r4 = r2
            r2 = r12
            r11 = r22
            r13 = r24
            r18 = -1
            r21 = 1048575(0xfffff, float:1.469367E-39)
            r12 = r35
            if (r8 != 0) goto L_0x029b
            int r0 = com.google.android.gms.internal.vision.zzhl.zzb(r12, r11, r9)
            r36 = r0
            long r0 = r9.zzb
            int r0 = (r0 > r19 ? 1 : (r0 == r19 ? 0 : -1))
            if (r0 == 0) goto L_0x0287
            r1 = 1
            goto L_0x0289
        L_0x0287:
            r1 = r16
        L_0x0289:
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r14, (long) r2, (boolean) r1)
            r0 = r6 | r25
            r11 = r38
            r2 = r4
            r1 = r7
            r3 = r13
            r6 = r26
            r13 = r5
            r5 = r0
            r0 = r36
            goto L_0x001e
        L_0x029b:
            r0 = 1
            goto L_0x039f
        L_0x029e:
            r5 = r37
            r4 = r2
            r2 = r12
            r11 = r22
            r13 = r24
            r18 = -1
            r21 = 1048575(0xfffff, float:1.469367E-39)
            r12 = r35
            if (r8 != r0) goto L_0x02c5
            int r0 = com.google.android.gms.internal.vision.zzhl.zza(r12, r11)
            r10.putInt(r14, r2, r0)
            int r0 = r11 + 4
            r1 = r6 | r25
            r11 = r38
            r2 = r4
            r3 = r13
            r6 = r26
            r13 = r5
            r5 = r1
            r1 = r7
            goto L_0x001e
        L_0x02c5:
            r0 = 1
            goto L_0x039f
        L_0x02c8:
            r5 = r37
            r4 = r2
            r2 = r12
            r11 = r22
            r13 = r24
            r18 = -1
            r21 = 1048575(0xfffff, float:1.469367E-39)
            r12 = r35
            r0 = 1
            if (r8 != r0) goto L_0x02f6
            long r19 = com.google.android.gms.internal.vision.zzhl.zzb(r12, r11)
            r0 = r10
            r1 = r34
            r8 = r4
            r4 = r19
            r0.putLong(r1, r2, r4)
            int r0 = r11 + 8
            r5 = r6 | r25
            r11 = r38
            r1 = r7
            r2 = r8
            r3 = r13
            r6 = r26
            r13 = r37
            goto L_0x001e
        L_0x02f6:
            r8 = r4
            r0 = 1
            goto L_0x039f
        L_0x02fa:
            r4 = r2
            r2 = r12
            r11 = r22
            r13 = r24
            r18 = -1
            r21 = 1048575(0xfffff, float:1.469367E-39)
            r12 = r35
            if (r8 != 0) goto L_0x031f
            int r0 = com.google.android.gms.internal.vision.zzhl.zza(r12, r11, r9)
            int r1 = r9.zza
            r10.putInt(r14, r2, r1)
            r5 = r6 | r25
            r11 = r38
            r2 = r4
            r1 = r7
            r3 = r13
            r6 = r26
            r13 = r37
            goto L_0x001e
        L_0x031f:
            r0 = 1
            goto L_0x039f
        L_0x0322:
            r4 = r2
            r2 = r12
            r11 = r22
            r13 = r24
            r18 = -1
            r21 = 1048575(0xfffff, float:1.469367E-39)
            r12 = r35
            if (r8 != 0) goto L_0x0350
            int r8 = com.google.android.gms.internal.vision.zzhl.zzb(r12, r11, r9)
            long r0 = r9.zzb
            r19 = r0
            r0 = r10
            r1 = r34
            r11 = r4
            r4 = r19
            r0.putLong(r1, r2, r4)
            r5 = r6 | r25
            r1 = r7
            r0 = r8
            r2 = r11
            r3 = r13
            r6 = r26
            r13 = r37
            r11 = r38
            goto L_0x001e
        L_0x0350:
            r0 = 1
            goto L_0x039f
        L_0x0352:
            r4 = r2
            r2 = r12
            r11 = r22
            r13 = r24
            r18 = -1
            r21 = 1048575(0xfffff, float:1.469367E-39)
            r12 = r35
            if (r8 != r0) goto L_0x0377
            float r0 = com.google.android.gms.internal.vision.zzhl.zzd(r12, r11)
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r14, (long) r2, (float) r0)
            int r0 = r11 + 4
            r5 = r6 | r25
            r11 = r38
            r2 = r4
            r1 = r7
            r3 = r13
            r6 = r26
            r13 = r37
            goto L_0x001e
        L_0x0377:
            r0 = 1
            goto L_0x039f
        L_0x0379:
            r4 = r2
            r2 = r12
            r11 = r22
            r13 = r24
            r18 = -1
            r21 = 1048575(0xfffff, float:1.469367E-39)
            r12 = r35
            r0 = 1
            if (r8 != r0) goto L_0x039f
            double r0 = com.google.android.gms.internal.vision.zzhl.zzc(r12, r11)
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r14, (long) r2, (double) r0)
            int r0 = r11 + 8
            r5 = r6 | r25
            r11 = r38
            r2 = r4
            r1 = r7
            r3 = r13
            r6 = r26
            r13 = r37
            goto L_0x001e
        L_0x039f:
            r15 = r38
            r23 = r0
            r24 = r4
            r25 = r6
            r36 = r7
            r31 = r10
            r2 = r11
            r21 = r13
            r6 = r26
            goto L_0x04e6
        L_0x03b2:
            r4 = r2
            r2 = r12
            r13 = r24
            r0 = 1
            r18 = -1
            r21 = 1048575(0xfffff, float:1.469367E-39)
            r12 = r35
            r0 = 27
            if (r11 != r0) goto L_0x0422
            r0 = 2
            if (r8 != r0) goto L_0x040e
            java.lang.Object r0 = r10.getObject(r14, r2)
            com.google.android.gms.internal.vision.zzjl r0 = (com.google.android.gms.internal.vision.zzjl) r0
            boolean r1 = r0.zza()
            if (r1 != 0) goto L_0x03e6
            int r1 = r0.size()
            if (r1 != 0) goto L_0x03db
            r1 = 10
            goto L_0x03dd
        L_0x03db:
            int r1 = r1 << 1
        L_0x03dd:
            com.google.android.gms.internal.vision.zzjl r0 = r0.zza(r1)
            r10.putObject(r14, r2, r0)
            r8 = r0
            goto L_0x03e7
        L_0x03e6:
            r8 = r0
        L_0x03e7:
            com.google.android.gms.internal.vision.zzlc r0 = r15.zza((int) r4)
            r1 = r13
            r2 = r35
            r3 = r22
            r24 = r4
            r4 = r37
            r25 = r5
            r5 = r8
            r26 = r6
            r6 = r39
            int r0 = com.google.android.gms.internal.vision.zzhl.zza(r0, r1, r2, r3, r4, r5, r6)
            r11 = r38
            r1 = r7
            r3 = r13
            r2 = r24
            r5 = r25
            r6 = r26
            r13 = r37
            goto L_0x001e
        L_0x040e:
            r24 = r4
            r25 = r5
            r26 = r6
            r15 = r38
            r36 = r7
            r31 = r10
            r21 = r13
            r14 = r22
            r23 = 1
            goto L_0x04c0
        L_0x0422:
            r24 = r4
            r25 = r5
            r26 = r6
            r0 = 49
            if (r11 > r0) goto L_0x0478
            long r5 = (long) r1
            r1 = 1
            r0 = r33
            r23 = r1
            r1 = r34
            r27 = r2
            r2 = r35
            r3 = r22
            r4 = r37
            r29 = r5
            r5 = r13
            r6 = r7
            r36 = r7
            r7 = r8
            r8 = r24
            r31 = r10
            r9 = r29
            r15 = r38
            r21 = r13
            r12 = r27
            r14 = r39
            int r0 = r0.zza(r1, (byte[]) r2, (int) r3, (int) r4, (int) r5, (int) r6, (int) r7, (int) r8, (long) r9, (int) r11, (long) r12, (com.google.android.gms.internal.vision.zzhn) r14)
            r14 = r22
            if (r0 != r14) goto L_0x045f
            r2 = r0
            r6 = r26
            goto L_0x04e6
        L_0x045f:
            r14 = r34
            r12 = r35
            r1 = r36
            r13 = r37
            r9 = r39
            r11 = r15
            r3 = r21
            r2 = r24
            r5 = r25
            r6 = r26
            r10 = r31
            r15 = r33
            goto L_0x001e
        L_0x0478:
            r15 = r38
            r27 = r2
            r36 = r7
            r31 = r10
            r21 = r13
            r14 = r22
            r23 = 1
            r0 = 50
            if (r11 != r0) goto L_0x04c4
            r0 = 2
            if (r8 != r0) goto L_0x04c0
            r0 = r33
            r1 = r34
            r2 = r35
            r3 = r14
            r4 = r37
            r5 = r24
            r6 = r27
            r8 = r39
            int r0 = r0.zza(r1, (byte[]) r2, (int) r3, (int) r4, (int) r5, (long) r6, (com.google.android.gms.internal.vision.zzhn) r8)
            if (r0 != r14) goto L_0x04a7
            r2 = r0
            r6 = r26
            goto L_0x04e6
        L_0x04a7:
            r14 = r34
            r12 = r35
            r1 = r36
            r13 = r37
            r9 = r39
            r11 = r15
            r3 = r21
            r2 = r24
            r5 = r25
            r6 = r26
            r10 = r31
            r15 = r33
            goto L_0x001e
        L_0x04c0:
            r2 = r14
            r6 = r26
            goto L_0x04e6
        L_0x04c4:
            r0 = r33
            r9 = r1
            r1 = r34
            r2 = r35
            r3 = r14
            r4 = r37
            r5 = r21
            r6 = r36
            r7 = r8
            r8 = r9
            r9 = r11
            r10 = r27
            r12 = r24
            r13 = r39
            int r0 = r0.zza(r1, (byte[]) r2, (int) r3, (int) r4, (int) r5, (int) r6, (int) r7, (int) r8, (int) r9, (long) r10, (int) r12, (com.google.android.gms.internal.vision.zzhn) r13)
            if (r0 != r14) goto L_0x0721
            r2 = r0
            r6 = r26
        L_0x04e6:
            r7 = r21
            if (r7 != r15) goto L_0x04fc
            if (r15 != 0) goto L_0x04ed
            goto L_0x04fc
        L_0x04ed:
            r8 = r33
            r13 = r34
            r0 = r2
            r2 = r6
            r3 = r7
            r9 = r15
            r5 = r25
            r1 = 0
            r6 = r37
            goto L_0x0750
        L_0x04fc:
            r8 = r33
            r9 = r15
            boolean r0 = r8.zzh
            if (r0 == 0) goto L_0x06f2
            r10 = r39
            com.google.android.gms.internal.vision.zzio r0 = r10.zzd
            com.google.android.gms.internal.vision.zzio r1 = com.google.android.gms.internal.vision.zzio.zzb()
            if (r0 == r1) goto L_0x06e7
            com.google.android.gms.internal.vision.zzkk r0 = r8.zzg
            com.google.android.gms.internal.vision.zzio r1 = r10.zzd
            r11 = r36
            com.google.android.gms.internal.vision.zzjb$zze r12 = r1.zza(r0, r11)
            if (r12 != 0) goto L_0x0536
            com.google.android.gms.internal.vision.zzlx r4 = zze((java.lang.Object) r34)
            r0 = r7
            r1 = r35
            r3 = r37
            r5 = r39
            int r0 = com.google.android.gms.internal.vision.zzhl.zza((int) r0, (byte[]) r1, (int) r2, (int) r3, (com.google.android.gms.internal.vision.zzlx) r4, (com.google.android.gms.internal.vision.zzhn) r5)
            r13 = r34
            r36 = r6
            r17 = r11
            r11 = r35
            r6 = r37
            goto L_0x06c7
        L_0x0536:
            r13 = r34
            r0 = r13
            com.google.android.gms.internal.vision.zzjb$zzc r0 = (com.google.android.gms.internal.vision.zzjb.zzc) r0
            r0.zza()
            com.google.android.gms.internal.vision.zziu<com.google.android.gms.internal.vision.zzjb$zzf> r14 = r0.zzc
            com.google.android.gms.internal.vision.zzjb$zzf r0 = r12.zzd
            boolean r1 = r0.zzd
            com.google.android.gms.internal.vision.zzml r0 = r0.zzc
            com.google.android.gms.internal.vision.zzml r1 = com.google.android.gms.internal.vision.zzml.ENUM
            if (r0 == r1) goto L_0x06da
            int[] r15 = com.google.android.gms.internal.vision.zzhk.zza
            int r0 = r0.ordinal()
            r0 = r15[r0]
            switch(r0) {
                case 1: goto L_0x0689;
                case 2: goto L_0x0676;
                case 3: goto L_0x0663;
                case 4: goto L_0x0663;
                case 5: goto L_0x0650;
                case 6: goto L_0x0650;
                case 7: goto L_0x063d;
                case 8: goto L_0x063d;
                case 9: goto L_0x062a;
                case 10: goto L_0x062a;
                case 11: goto L_0x060d;
                case 12: goto L_0x05f5;
                case 13: goto L_0x05dd;
                case 14: goto L_0x05d5;
                case 15: goto L_0x05c5;
                case 16: goto L_0x05b5;
                case 17: goto L_0x0589;
                case 18: goto L_0x0568;
                default: goto L_0x055d;
            }
        L_0x055d:
            r36 = r6
            r17 = r11
            r11 = r35
            r6 = r37
            r4 = 0
            goto L_0x069c
        L_0x0568:
            com.google.android.gms.internal.vision.zzky r0 = com.google.android.gms.internal.vision.zzky.zza()
            com.google.android.gms.internal.vision.zzkk r1 = r12.zzc
            java.lang.Class r1 = r1.getClass()
            com.google.android.gms.internal.vision.zzlc r0 = r0.zza(r1)
            r5 = r35
            r4 = r37
            int r2 = com.google.android.gms.internal.vision.zzhl.zza((com.google.android.gms.internal.vision.zzlc) r0, (byte[]) r5, (int) r2, (int) r4, (com.google.android.gms.internal.vision.zzhn) r10)
            java.lang.Object r0 = r10.zzc
            r36 = r6
            r17 = r11
            r6 = r4
            r11 = r5
            r4 = r0
            goto L_0x069c
        L_0x0589:
            r5 = r35
            r4 = r37
            int r0 = r11 << 3
            r17 = r0 | 4
            com.google.android.gms.internal.vision.zzky r0 = com.google.android.gms.internal.vision.zzky.zza()
            com.google.android.gms.internal.vision.zzkk r1 = r12.zzc
            java.lang.Class r1 = r1.getClass()
            com.google.android.gms.internal.vision.zzlc r0 = r0.zza(r1)
            r1 = r35
            r3 = r37
            r36 = r6
            r6 = r4
            r4 = r17
            r17 = r11
            r11 = r5
            r5 = r39
            int r2 = com.google.android.gms.internal.vision.zzhl.zza((com.google.android.gms.internal.vision.zzlc) r0, (byte[]) r1, (int) r2, (int) r3, (int) r4, (com.google.android.gms.internal.vision.zzhn) r5)
            java.lang.Object r4 = r10.zzc
            goto L_0x069c
        L_0x05b5:
            r36 = r6
            r17 = r11
            r11 = r35
            r6 = r37
            int r2 = com.google.android.gms.internal.vision.zzhl.zzc(r11, r2, r10)
            java.lang.Object r4 = r10.zzc
            goto L_0x069c
        L_0x05c5:
            r36 = r6
            r17 = r11
            r11 = r35
            r6 = r37
            int r2 = com.google.android.gms.internal.vision.zzhl.zze(r11, r2, r10)
            java.lang.Object r4 = r10.zzc
            goto L_0x069c
        L_0x05d5:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Shouldn't reach here."
            r0.<init>(r1)
            throw r0
        L_0x05dd:
            r36 = r6
            r17 = r11
            r11 = r35
            r6 = r37
            int r2 = com.google.android.gms.internal.vision.zzhl.zzb(r11, r2, r10)
            long r0 = r10.zzb
            long r0 = com.google.android.gms.internal.vision.zzif.zza((long) r0)
            java.lang.Long r4 = java.lang.Long.valueOf(r0)
            goto L_0x069c
        L_0x05f5:
            r36 = r6
            r17 = r11
            r11 = r35
            r6 = r37
            int r2 = com.google.android.gms.internal.vision.zzhl.zza(r11, r2, r10)
            int r0 = r10.zza
            int r0 = com.google.android.gms.internal.vision.zzif.zze(r0)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r0)
            goto L_0x069c
        L_0x060d:
            r36 = r6
            r17 = r11
            r11 = r35
            r6 = r37
            int r2 = com.google.android.gms.internal.vision.zzhl.zzb(r11, r2, r10)
            long r0 = r10.zzb
            int r0 = (r0 > r19 ? 1 : (r0 == r19 ? 0 : -1))
            if (r0 == 0) goto L_0x0622
            r1 = r23
            goto L_0x0624
        L_0x0622:
            r1 = r16
        L_0x0624:
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r1)
            goto L_0x069c
        L_0x062a:
            r36 = r6
            r17 = r11
            r11 = r35
            r6 = r37
            int r0 = com.google.android.gms.internal.vision.zzhl.zza(r11, r2)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r0)
            int r2 = r2 + 4
            goto L_0x069c
        L_0x063d:
            r36 = r6
            r17 = r11
            r11 = r35
            r6 = r37
            long r0 = com.google.android.gms.internal.vision.zzhl.zzb(r11, r2)
            java.lang.Long r4 = java.lang.Long.valueOf(r0)
            int r2 = r2 + 8
            goto L_0x069c
        L_0x0650:
            r36 = r6
            r17 = r11
            r11 = r35
            r6 = r37
            int r2 = com.google.android.gms.internal.vision.zzhl.zza(r11, r2, r10)
            int r0 = r10.zza
            java.lang.Integer r4 = java.lang.Integer.valueOf(r0)
            goto L_0x069c
        L_0x0663:
            r36 = r6
            r17 = r11
            r11 = r35
            r6 = r37
            int r2 = com.google.android.gms.internal.vision.zzhl.zzb(r11, r2, r10)
            long r0 = r10.zzb
            java.lang.Long r4 = java.lang.Long.valueOf(r0)
            goto L_0x069c
        L_0x0676:
            r36 = r6
            r17 = r11
            r11 = r35
            r6 = r37
            float r0 = com.google.android.gms.internal.vision.zzhl.zzd(r11, r2)
            java.lang.Float r4 = java.lang.Float.valueOf(r0)
            int r2 = r2 + 4
            goto L_0x069c
        L_0x0689:
            r36 = r6
            r17 = r11
            r11 = r35
            r6 = r37
            double r0 = com.google.android.gms.internal.vision.zzhl.zzc(r11, r2)
            java.lang.Double r4 = java.lang.Double.valueOf(r0)
            int r2 = r2 + 8
        L_0x069c:
            com.google.android.gms.internal.vision.zzjb$zzf r0 = r12.zzd
            boolean r1 = r0.zzd
            if (r1 == 0) goto L_0x06a7
            r14.zzb(r0, r4)
            goto L_0x06c6
        L_0x06a7:
            com.google.android.gms.internal.vision.zzml r0 = r0.zzc
            int r0 = r0.ordinal()
            r0 = r15[r0]
            switch(r0) {
                case 17: goto L_0x06b5;
                case 18: goto L_0x06b5;
                default: goto L_0x06b4;
            }
        L_0x06b4:
            goto L_0x06c1
        L_0x06b5:
            com.google.android.gms.internal.vision.zzjb$zzf r0 = r12.zzd
            java.lang.Object r0 = r14.zza(r0)
            if (r0 == 0) goto L_0x06c1
            java.lang.Object r4 = com.google.android.gms.internal.vision.zzjf.zza((java.lang.Object) r0, (java.lang.Object) r4)
        L_0x06c1:
            com.google.android.gms.internal.vision.zzjb$zzf r0 = r12.zzd
            r14.zza(r0, (java.lang.Object) r4)
        L_0x06c6:
            r0 = r2
        L_0x06c7:
            r3 = r7
            r15 = r8
            r12 = r11
            r14 = r13
            r1 = r17
            r2 = r24
            r5 = r25
            r13 = r6
            r11 = r9
            r9 = r10
            r10 = r31
            r6 = r36
            goto L_0x001e
        L_0x06da:
            r11 = r35
            com.google.android.gms.internal.vision.zzhl.zza(r11, r2, r10)
            int r0 = r10.zza
            r1 = 0
            r1.zza(r0)
            throw r1
        L_0x06e7:
            r13 = r34
            r11 = r35
            r17 = r36
            r36 = r6
            r6 = r37
            goto L_0x06fe
        L_0x06f2:
            r13 = r34
            r11 = r35
            r17 = r36
            r10 = r39
            r36 = r6
            r6 = r37
        L_0x06fe:
            com.google.android.gms.internal.vision.zzlx r4 = zze((java.lang.Object) r34)
            r0 = r7
            r1 = r35
            r3 = r37
            r5 = r39
            int r0 = com.google.android.gms.internal.vision.zzhl.zza((int) r0, (byte[]) r1, (int) r2, (int) r3, (com.google.android.gms.internal.vision.zzlx) r4, (com.google.android.gms.internal.vision.zzhn) r5)
            r3 = r7
            r15 = r8
            r12 = r11
            r14 = r13
            r1 = r17
            r2 = r24
            r5 = r25
            r13 = r6
            r11 = r9
            r9 = r10
            r10 = r31
            r6 = r36
            goto L_0x001e
        L_0x0721:
            r8 = r33
            r13 = r34
            r11 = r35
            r17 = r36
            r6 = r37
            r10 = r39
            r9 = r15
            r7 = r21
            r3 = r7
            r15 = r8
            r12 = r11
            r14 = r13
            r1 = r17
            r2 = r24
            r5 = r25
            r13 = r6
            r11 = r9
            r9 = r10
            r6 = r26
            r10 = r31
            goto L_0x001e
        L_0x0743:
            r25 = r5
            r26 = r6
            r31 = r10
            r9 = r11
            r6 = r13
            r13 = r14
            r8 = r15
            r1 = 0
            r2 = r26
        L_0x0750:
            r4 = 1048575(0xfffff, float:1.469367E-39)
            if (r2 == r4) goto L_0x075b
            long r10 = (long) r2
            r2 = r31
            r2.putInt(r13, r10, r5)
        L_0x075b:
            int r2 = r8.zzm
            r4 = r1
        L_0x075f:
            int r1 = r8.zzn
            if (r2 >= r1) goto L_0x0773
            int[] r1 = r8.zzl
            r1 = r1[r2]
            com.google.android.gms.internal.vision.zzlu<?, ?> r5 = r8.zzq
            java.lang.Object r1 = r8.zza((java.lang.Object) r13, (int) r1, r4, r5)
            r4 = r1
            com.google.android.gms.internal.vision.zzlx r4 = (com.google.android.gms.internal.vision.zzlx) r4
            int r2 = r2 + 1
            goto L_0x075f
        L_0x0773:
            if (r4 == 0) goto L_0x077a
            com.google.android.gms.internal.vision.zzlu<?, ?> r1 = r8.zzq
            r1.zzb((java.lang.Object) r13, r4)
        L_0x077a:
            if (r9 != 0) goto L_0x0784
            if (r0 != r6) goto L_0x077f
            goto L_0x0788
        L_0x077f:
            com.google.android.gms.internal.vision.zzjk r0 = com.google.android.gms.internal.vision.zzjk.zzg()
            throw r0
        L_0x0784:
            if (r0 > r6) goto L_0x0789
            if (r3 != r9) goto L_0x0789
        L_0x0788:
            return r0
        L_0x0789:
            com.google.android.gms.internal.vision.zzjk r0 = com.google.android.gms.internal.vision.zzjk.zzg()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zza(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.vision.zzhn):int");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v0, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v20, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v3, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(T r30, byte[] r31, int r32, int r33, com.google.android.gms.internal.vision.zzhn r34) {
        /*
            r29 = this;
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            boolean r0 = r15.zzj
            if (r0 == 0) goto L_0x042b
            sun.misc.Unsafe r9 = zzb
            r10 = -1
            r16 = 0
            r0 = r32
            r1 = r10
            r2 = r16
            r6 = r2
            r7 = 1048575(0xfffff, float:1.469367E-39)
        L_0x0020:
            if (r0 >= r13) goto L_0x040c
            int r3 = r0 + 1
            byte r0 = r12[r0]
            if (r0 >= 0) goto L_0x0032
            int r0 = com.google.android.gms.internal.vision.zzhl.zza((int) r0, (byte[]) r12, (int) r3, (com.google.android.gms.internal.vision.zzhn) r11)
            int r3 = r11.zza
            r4 = r0
            r17 = r3
            goto L_0x0035
        L_0x0032:
            r17 = r0
            r4 = r3
        L_0x0035:
            int r5 = r17 >>> 3
            r3 = r17 & 7
            if (r5 <= r1) goto L_0x0043
            int r2 = r2 / 3
            int r0 = r15.zza((int) r5, (int) r2)
            r2 = r0
            goto L_0x0048
        L_0x0043:
            int r0 = r15.zzg(r5)
            r2 = r0
        L_0x0048:
            if (r2 != r10) goto L_0x0056
            r2 = r4
            r19 = r5
            r26 = r9
            r27 = r10
            r20 = r16
            goto L_0x03cf
        L_0x0056:
            int[] r0 = r15.zzc
            int r1 = r2 + 1
            r1 = r0[r1]
            r18 = 267386880(0xff00000, float:2.3665827E-29)
            r18 = r1 & r18
            int r8 = r18 >>> 20
            r18 = 1048575(0xfffff, float:1.469367E-39)
            r10 = r1 & r18
            r18 = r4
            r32 = r5
            long r4 = (long) r10
            r10 = 17
            r20 = r1
            if (r8 > r10) goto L_0x029c
            int r10 = r2 + 2
            r0 = r0[r10]
            int r10 = r0 >>> 20
            r1 = 1
            int r10 = r1 << r10
            r13 = 1048575(0xfffff, float:1.469367E-39)
            r0 = r0 & r13
            if (r0 == r7) goto L_0x009a
            if (r7 == r13) goto L_0x008e
            r19 = r2
            long r1 = (long) r7
            r9.putInt(r14, r1, r6)
            goto L_0x0090
        L_0x008e:
            r19 = r2
        L_0x0090:
            if (r0 == r13) goto L_0x0098
            long r1 = (long) r0
            int r1 = r9.getInt(r14, r1)
            r6 = r1
        L_0x0098:
            r7 = r0
            goto L_0x009c
        L_0x009a:
            r19 = r2
        L_0x009c:
            r0 = 5
            switch(r8) {
                case 0: goto L_0x0276;
                case 1: goto L_0x025a;
                case 2: goto L_0x0235;
                case 3: goto L_0x0235;
                case 4: goto L_0x0219;
                case 5: goto L_0x01f4;
                case 6: goto L_0x01d4;
                case 7: goto L_0x01a6;
                case 8: goto L_0x017a;
                case 9: goto L_0x013c;
                case 10: goto L_0x011c;
                case 11: goto L_0x0219;
                case 12: goto L_0x00fd;
                case 13: goto L_0x01d4;
                case 14: goto L_0x01f4;
                case 15: goto L_0x00da;
                case 16: goto L_0x00aa;
                default: goto L_0x00a0;
            }
        L_0x00a0:
            r8 = r18
            r18 = r13
            r13 = r19
            r19 = r32
            goto L_0x0293
        L_0x00aa:
            if (r3 != 0) goto L_0x00cf
            r8 = r18
            int r8 = com.google.android.gms.internal.vision.zzhl.zzb(r12, r8, r11)
            long r0 = r11.zzb
            long r17 = com.google.android.gms.internal.vision.zzif.zza((long) r0)
            r0 = r9
            r1 = r30
            r13 = r19
            r2 = r4
            r19 = r32
            r4 = r17
            r0.putLong(r1, r2, r4)
            r6 = r6 | r10
            r0 = r8
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x00cf:
            r8 = r18
            r13 = r19
            r19 = r32
            r18 = 1048575(0xfffff, float:1.469367E-39)
            goto L_0x0293
        L_0x00da:
            r8 = r18
            r13 = r19
            r19 = r32
            if (r3 != 0) goto L_0x00f8
            int r0 = com.google.android.gms.internal.vision.zzhl.zza(r12, r8, r11)
            int r1 = r11.zza
            int r1 = com.google.android.gms.internal.vision.zzif.zze(r1)
            r9.putInt(r14, r4, r1)
            r6 = r6 | r10
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x00f8:
            r18 = 1048575(0xfffff, float:1.469367E-39)
            goto L_0x0293
        L_0x00fd:
            r8 = r18
            r13 = r19
            r19 = r32
            if (r3 != 0) goto L_0x0117
            int r0 = com.google.android.gms.internal.vision.zzhl.zza(r12, r8, r11)
            int r1 = r11.zza
            r9.putInt(r14, r4, r1)
            r6 = r6 | r10
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x0117:
            r18 = 1048575(0xfffff, float:1.469367E-39)
            goto L_0x0293
        L_0x011c:
            r8 = r18
            r13 = r19
            r19 = r32
            r0 = 2
            if (r3 != r0) goto L_0x0137
            int r0 = com.google.android.gms.internal.vision.zzhl.zze(r12, r8, r11)
            java.lang.Object r1 = r11.zzc
            r9.putObject(r14, r4, r1)
            r6 = r6 | r10
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x0137:
            r18 = 1048575(0xfffff, float:1.469367E-39)
            goto L_0x0293
        L_0x013c:
            r8 = r18
            r13 = r19
            r19 = r32
            r0 = 2
            if (r3 != r0) goto L_0x0173
            com.google.android.gms.internal.vision.zzlc r0 = r15.zza((int) r13)
            r2 = r33
            r18 = 1048575(0xfffff, float:1.469367E-39)
            int r0 = com.google.android.gms.internal.vision.zzhl.zza((com.google.android.gms.internal.vision.zzlc) r0, (byte[]) r12, (int) r8, (int) r2, (com.google.android.gms.internal.vision.zzhn) r11)
            java.lang.Object r1 = r9.getObject(r14, r4)
            if (r1 != 0) goto L_0x015f
            java.lang.Object r1 = r11.zzc
            r9.putObject(r14, r4, r1)
            goto L_0x0168
        L_0x015f:
            java.lang.Object r3 = r11.zzc
            java.lang.Object r1 = com.google.android.gms.internal.vision.zzjf.zza((java.lang.Object) r1, (java.lang.Object) r3)
            r9.putObject(r14, r4, r1)
        L_0x0168:
            r6 = r6 | r10
            r1 = r19
            r10 = -1
            r28 = r13
            r13 = r2
            r2 = r28
            goto L_0x0020
        L_0x0173:
            r2 = r33
            r18 = 1048575(0xfffff, float:1.469367E-39)
            goto L_0x0293
        L_0x017a:
            r2 = r33
            r8 = r18
            r18 = r13
            r13 = r19
            r19 = r32
            r0 = 2
            if (r3 != r0) goto L_0x0293
            r0 = 536870912(0x20000000, float:1.0842022E-19)
            r0 = r20 & r0
            if (r0 != 0) goto L_0x0192
            int r0 = com.google.android.gms.internal.vision.zzhl.zzc(r12, r8, r11)
            goto L_0x0196
        L_0x0192:
            int r0 = com.google.android.gms.internal.vision.zzhl.zzd(r12, r8, r11)
        L_0x0196:
            java.lang.Object r1 = r11.zzc
            r9.putObject(r14, r4, r1)
            r6 = r6 | r10
            r1 = r19
            r10 = -1
            r28 = r13
            r13 = r2
            r2 = r28
            goto L_0x0020
        L_0x01a6:
            r2 = r33
            r8 = r18
            r18 = r13
            r13 = r19
            r19 = r32
            if (r3 != 0) goto L_0x0293
            int r0 = com.google.android.gms.internal.vision.zzhl.zzb(r12, r8, r11)
            r32 = r0
            long r0 = r11.zzb
            r20 = 0
            int r0 = (r0 > r20 ? 1 : (r0 == r20 ? 0 : -1))
            if (r0 == 0) goto L_0x01c2
            r1 = 1
            goto L_0x01c4
        L_0x01c2:
            r1 = r16
        L_0x01c4:
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r14, (long) r4, (boolean) r1)
            r6 = r6 | r10
            r0 = r32
            r1 = r19
            r10 = -1
            r28 = r13
            r13 = r2
            r2 = r28
            goto L_0x0020
        L_0x01d4:
            r2 = r33
            r8 = r18
            r18 = r13
            r13 = r19
            r19 = r32
            if (r3 != r0) goto L_0x0293
            int r0 = com.google.android.gms.internal.vision.zzhl.zza(r12, r8)
            r9.putInt(r14, r4, r0)
            int r0 = r8 + 4
            r6 = r6 | r10
            r1 = r19
            r10 = -1
            r28 = r13
            r13 = r2
            r2 = r28
            goto L_0x0020
        L_0x01f4:
            r2 = r33
            r8 = r18
            r18 = r13
            r13 = r19
            r19 = r32
            r0 = 1
            if (r3 != r0) goto L_0x0293
            long r20 = com.google.android.gms.internal.vision.zzhl.zzb(r12, r8)
            r0 = r9
            r1 = r30
            r2 = r4
            r4 = r20
            r0.putLong(r1, r2, r4)
            int r0 = r8 + 8
            r6 = r6 | r10
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x0219:
            r8 = r18
            r18 = r13
            r13 = r19
            r19 = r32
            if (r3 != 0) goto L_0x0293
            int r0 = com.google.android.gms.internal.vision.zzhl.zza(r12, r8, r11)
            int r1 = r11.zza
            r9.putInt(r14, r4, r1)
            r6 = r6 | r10
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x0235:
            r8 = r18
            r18 = r13
            r13 = r19
            r19 = r32
            if (r3 != 0) goto L_0x0293
            int r8 = com.google.android.gms.internal.vision.zzhl.zzb(r12, r8, r11)
            long r2 = r11.zzb
            r0 = r9
            r1 = r30
            r20 = r2
            r2 = r4
            r4 = r20
            r0.putLong(r1, r2, r4)
            r6 = r6 | r10
            r0 = r8
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x025a:
            r8 = r18
            r18 = r13
            r13 = r19
            r19 = r32
            if (r3 != r0) goto L_0x0293
            float r0 = com.google.android.gms.internal.vision.zzhl.zzd(r12, r8)
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r14, (long) r4, (float) r0)
            int r0 = r8 + 4
            r6 = r6 | r10
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x0276:
            r8 = r18
            r18 = r13
            r13 = r19
            r19 = r32
            r0 = 1
            if (r3 != r0) goto L_0x0293
            double r0 = com.google.android.gms.internal.vision.zzhl.zzc(r12, r8)
            com.google.android.gms.internal.vision.zzma.zza((java.lang.Object) r14, (long) r4, (double) r0)
            int r0 = r8 + 8
            r6 = r6 | r10
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x0293:
            r2 = r8
            r26 = r9
            r20 = r13
            r27 = -1
            goto L_0x03cf
        L_0x029c:
            r19 = r32
            r13 = r2
            r10 = r18
            r18 = 1048575(0xfffff, float:1.469367E-39)
            r0 = 27
            if (r8 != r0) goto L_0x02f7
            r0 = 2
            if (r3 != r0) goto L_0x02e9
            java.lang.Object r0 = r9.getObject(r14, r4)
            com.google.android.gms.internal.vision.zzjl r0 = (com.google.android.gms.internal.vision.zzjl) r0
            boolean r1 = r0.zza()
            if (r1 != 0) goto L_0x02cc
            int r1 = r0.size()
            if (r1 != 0) goto L_0x02c1
            r1 = 10
            goto L_0x02c3
        L_0x02c1:
            int r1 = r1 << 1
        L_0x02c3:
            com.google.android.gms.internal.vision.zzjl r0 = r0.zza(r1)
            r9.putObject(r14, r4, r0)
            r5 = r0
            goto L_0x02cd
        L_0x02cc:
            r5 = r0
        L_0x02cd:
            com.google.android.gms.internal.vision.zzlc r0 = r15.zza((int) r13)
            r1 = r17
            r2 = r31
            r3 = r10
            r4 = r33
            r8 = r6
            r6 = r34
            int r0 = com.google.android.gms.internal.vision.zzhl.zza(r0, r1, r2, r3, r4, r5, r6)
            r6 = r8
            r2 = r13
            r1 = r19
            r10 = -1
            r13 = r33
            goto L_0x0020
        L_0x02e9:
            r8 = r6
            r18 = r7
            r24 = r8
            r26 = r9
            r15 = r10
            r20 = r13
            r27 = -1
            goto L_0x03a7
        L_0x02f7:
            r0 = 49
            if (r8 > r0) goto L_0x0355
            r1 = r20
            long r1 = (long) r1
            r0 = r29
            r20 = r1
            r1 = r30
            r2 = r31
            r32 = r3
            r3 = r10
            r22 = r4
            r4 = r33
            r5 = r17
            r15 = r6
            r6 = r19
            r24 = r15
            r15 = r7
            r7 = r32
            r25 = r8
            r28 = r18
            r18 = r15
            r15 = r28
            r8 = r13
            r26 = r9
            r15 = r10
            r27 = -1
            r9 = r20
            r11 = r25
            r20 = r13
            r12 = r22
            r14 = r34
            int r0 = r0.zza(r1, (byte[]) r2, (int) r3, (int) r4, (int) r5, (int) r6, (int) r7, (int) r8, (long) r9, (int) r11, (long) r12, (com.google.android.gms.internal.vision.zzhn) r14)
            if (r0 != r15) goto L_0x033d
            r2 = r0
            r7 = r18
            r6 = r24
            goto L_0x03cf
        L_0x033d:
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            r7 = r18
            r1 = r19
            r2 = r20
            r6 = r24
            r9 = r26
            r10 = r27
            goto L_0x0020
        L_0x0355:
            r32 = r3
            r22 = r4
            r24 = r6
            r18 = r7
            r25 = r8
            r26 = r9
            r15 = r10
            r1 = r20
            r27 = -1
            r20 = r13
            r0 = 50
            r9 = r25
            if (r9 != r0) goto L_0x03ad
            r7 = r32
            r0 = 2
            if (r7 != r0) goto L_0x03a7
            r0 = r29
            r1 = r30
            r2 = r31
            r3 = r15
            r4 = r33
            r5 = r20
            r6 = r22
            r8 = r34
            int r0 = r0.zza(r1, (byte[]) r2, (int) r3, (int) r4, (int) r5, (long) r6, (com.google.android.gms.internal.vision.zzhn) r8)
            if (r0 != r15) goto L_0x038f
            r2 = r0
            r7 = r18
            r6 = r24
            goto L_0x03cf
        L_0x038f:
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            r7 = r18
            r1 = r19
            r2 = r20
            r6 = r24
            r9 = r26
            r10 = r27
            goto L_0x0020
        L_0x03a7:
            r2 = r15
            r7 = r18
            r6 = r24
            goto L_0x03cf
        L_0x03ad:
            r7 = r32
            r0 = r29
            r8 = r1
            r1 = r30
            r2 = r31
            r3 = r15
            r4 = r33
            r5 = r17
            r6 = r19
            r10 = r22
            r12 = r20
            r13 = r34
            int r0 = r0.zza(r1, (byte[]) r2, (int) r3, (int) r4, (int) r5, (int) r6, (int) r7, (int) r8, (int) r9, (long) r10, (int) r12, (com.google.android.gms.internal.vision.zzhn) r13)
            if (r0 != r15) goto L_0x03f4
            r2 = r0
            r7 = r18
            r6 = r24
        L_0x03cf:
            com.google.android.gms.internal.vision.zzlx r4 = zze((java.lang.Object) r30)
            r0 = r17
            r1 = r31
            r3 = r33
            r5 = r34
            int r0 = com.google.android.gms.internal.vision.zzhl.zza((int) r0, (byte[]) r1, (int) r2, (int) r3, (com.google.android.gms.internal.vision.zzlx) r4, (com.google.android.gms.internal.vision.zzhn) r5)
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            r1 = r19
            r2 = r20
            r9 = r26
            r10 = r27
            goto L_0x0020
        L_0x03f4:
            r15 = r29
            r14 = r30
            r12 = r31
            r13 = r33
            r11 = r34
            r7 = r18
            r1 = r19
            r2 = r20
            r6 = r24
            r9 = r26
            r10 = r27
            goto L_0x0020
        L_0x040c:
            r24 = r6
            r18 = r7
            r26 = r9
            r1 = 1048575(0xfffff, float:1.469367E-39)
            if (r7 == r1) goto L_0x0421
            long r1 = (long) r7
            r3 = r30
            r6 = r24
            r4 = r26
            r4.putInt(r3, r1, r6)
        L_0x0421:
            r4 = r33
            if (r0 != r4) goto L_0x0426
            return
        L_0x0426:
            com.google.android.gms.internal.vision.zzjk r0 = com.google.android.gms.internal.vision.zzjk.zzg()
            throw r0
        L_0x042b:
            r4 = r13
            r3 = r14
            r5 = 0
            r0 = r29
            r1 = r30
            r2 = r31
            r3 = r32
            r4 = r33
            r6 = r34
            r0.zza(r1, (byte[]) r2, (int) r3, (int) r4, (int) r5, (com.google.android.gms.internal.vision.zzhn) r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzko.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.vision.zzhn):void");
    }

    public final void zzc(T t) {
        int i;
        int i2 = this.zzm;
        while (true) {
            i = this.zzn;
            if (i2 >= i) {
                break;
            }
            long zzd2 = (long) (zzd(this.zzl[i2]) & 1048575);
            Object zzf2 = zzma.zzf(t, zzd2);
            if (zzf2 != null) {
                zzma.zza((Object) t, zzd2, this.zzs.zze(zzf2));
            }
            i2++;
        }
        int length = this.zzl.length;
        while (i < length) {
            this.zzp.zzb(t, (long) this.zzl[i]);
            i++;
        }
        this.zzq.zzd(t);
        if (this.zzh) {
            this.zzr.zzc(t);
        }
    }

    private final <UT, UB> UB zza(Object obj, int i, UB ub, zzlu<UT, UB> zzlu) {
        zzjg zzc2;
        int i2 = this.zzc[i];
        Object zzf2 = zzma.zzf(obj, (long) (zzd(i) & 1048575));
        if (zzf2 == null || (zzc2 = zzc(i)) == null) {
            return ub;
        }
        return zza(i, i2, this.zzs.zza(zzf2), zzc2, ub, zzlu);
    }

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzjg zzjg, UB ub, zzlu<UT, UB> zzlu) {
        zzkf<?, ?> zzb2 = this.zzs.zzb(zzb(i));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry next = it.next();
            if (!zzjg.zza(((Integer) next.getValue()).intValue())) {
                if (ub == null) {
                    ub = zzlu.zza();
                }
                zzib zzc2 = zzht.zzc(zzkc.zza(zzb2, next.getKey(), next.getValue()));
                try {
                    zzkc.zza(zzc2.zzb(), zzb2, next.getKey(), next.getValue());
                    zzlu.zza(ub, i2, zzc2.zza());
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
    }

    public final boolean zzd(T t) {
        int i;
        int i2;
        T t2 = t;
        int i3 = 1048575;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            boolean z = true;
            if (i5 >= this.zzm) {
                return !this.zzh || this.zzr.zza((Object) t2).zzf();
            }
            int i6 = this.zzl[i5];
            int i7 = this.zzc[i6];
            int zzd2 = zzd(i6);
            int i8 = this.zzc[i6 + 2];
            int i9 = i8 & 1048575;
            int i10 = 1 << (i8 >>> 20);
            if (i9 == i3) {
                i2 = i3;
                i = i4;
            } else if (i9 != 1048575) {
                i = zzb.getInt(t2, (long) i9);
                i2 = i9;
            } else {
                i = i4;
                i2 = i9;
            }
            if (((268435456 & zzd2) != 0) && !zza(t, i6, i2, i, i10)) {
                return false;
            }
            switch ((267386880 & zzd2) >>> 20) {
                case 9:
                case 17:
                    if (zza(t, i6, i2, i, i10) && !zza((Object) t2, zzd2, zza(i6))) {
                        return false;
                    }
                case 27:
                case 49:
                    List list = (List) zzma.zzf(t2, (long) (zzd2 & 1048575));
                    if (!list.isEmpty()) {
                        zzlc zza2 = zza(i6);
                        int i11 = 0;
                        while (true) {
                            if (i11 < list.size()) {
                                if (!zza2.zzd(list.get(i11))) {
                                    z = false;
                                } else {
                                    i11++;
                                }
                            }
                        }
                    }
                    if (z) {
                        break;
                    } else {
                        return false;
                    }
                case 50:
                    Map<?, ?> zzc2 = this.zzs.zzc(zzma.zzf(t2, (long) (zzd2 & 1048575)));
                    if (!zzc2.isEmpty()) {
                        if (this.zzs.zzb(zzb(i6)).zzc.zza() == zzmo.MESSAGE) {
                            zzlc<?> zzlc = null;
                            Iterator<?> it = zzc2.values().iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    Object next = it.next();
                                    if (zzlc == null) {
                                        zzlc = zzky.zza().zza(next.getClass());
                                    }
                                    if (!zzlc.zzd(next)) {
                                        z = false;
                                    }
                                }
                            }
                        }
                    }
                    if (z) {
                        break;
                    } else {
                        return false;
                    }
                case 60:
                case 68:
                    if (zza(t2, i7, i6) && !zza((Object) t2, zzd2, zza(i6))) {
                        return false;
                    }
            }
            i5++;
            i3 = i2;
            i4 = i;
        }
    }

    private static boolean zza(Object obj, int i, zzlc zzlc) {
        return zzlc.zzd(zzma.zzf(obj, (long) (i & 1048575)));
    }

    private static void zza(int i, Object obj, zzmr zzmr) {
        if (obj instanceof String) {
            zzmr.zza(i, (String) obj);
        } else {
            zzmr.zza(i, (zzht) obj);
        }
    }

    private final void zza(Object obj, int i, zzld zzld) {
        if (zzf(i)) {
            zzma.zza(obj, (long) (i & 1048575), (Object) zzld.zzm());
        } else if (this.zzi) {
            zzma.zza(obj, (long) (i & 1048575), (Object) zzld.zzl());
        } else {
            zzma.zza(obj, (long) (i & 1048575), (Object) zzld.zzn());
        }
    }

    private final int zzd(int i) {
        return this.zzc[i + 1];
    }

    private final int zze(int i) {
        return this.zzc[i + 2];
    }

    private static boolean zzf(int i) {
        return (i & 536870912) != 0;
    }

    private static <T> double zzb(T t, long j) {
        return ((Double) zzma.zzf(t, j)).doubleValue();
    }

    private static <T> float zzc(T t, long j) {
        return ((Float) zzma.zzf(t, j)).floatValue();
    }

    private static <T> int zzd(T t, long j) {
        return ((Integer) zzma.zzf(t, j)).intValue();
    }

    private static <T> long zze(T t, long j) {
        return ((Long) zzma.zzf(t, j)).longValue();
    }

    private static <T> boolean zzf(T t, long j) {
        return ((Boolean) zzma.zzf(t, j)).booleanValue();
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza(t, i) == zza(t2, i);
    }

    private final boolean zza(T t, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zza(t, i);
        }
        return (i3 & i4) != 0;
    }

    private final boolean zza(T t, int i) {
        int zze2 = zze(i);
        long j = (long) (zze2 & 1048575);
        if (j == 1048575) {
            int zzd2 = zzd(i);
            long j2 = (long) (zzd2 & 1048575);
            switch ((zzd2 & 267386880) >>> 20) {
                case 0:
                    return zzma.zze(t, j2) != 0.0d;
                case 1:
                    return zzma.zzd(t, j2) != 0.0f;
                case 2:
                    return zzma.zzb(t, j2) != 0;
                case 3:
                    return zzma.zzb(t, j2) != 0;
                case 4:
                    return zzma.zza((Object) t, j2) != 0;
                case 5:
                    return zzma.zzb(t, j2) != 0;
                case 6:
                    return zzma.zza((Object) t, j2) != 0;
                case 7:
                    return zzma.zzc(t, j2);
                case 8:
                    Object zzf2 = zzma.zzf(t, j2);
                    if (zzf2 instanceof String) {
                        return !((String) zzf2).isEmpty();
                    }
                    if (zzf2 instanceof zzht) {
                        return !zzht.zza.equals(zzf2);
                    }
                    throw new IllegalArgumentException();
                case 9:
                    return zzma.zzf(t, j2) != null;
                case 10:
                    return !zzht.zza.equals(zzma.zzf(t, j2));
                case 11:
                    return zzma.zza((Object) t, j2) != 0;
                case 12:
                    return zzma.zza((Object) t, j2) != 0;
                case 13:
                    return zzma.zza((Object) t, j2) != 0;
                case 14:
                    return zzma.zzb(t, j2) != 0;
                case 15:
                    return zzma.zza((Object) t, j2) != 0;
                case 16:
                    return zzma.zzb(t, j2) != 0;
                case 17:
                    return zzma.zzf(t, j2) != null;
                default:
                    throw new IllegalArgumentException();
            }
        } else {
            return (zzma.zza((Object) t, j) & (1 << (zze2 >>> 20))) != 0;
        }
    }

    private final void zzb(T t, int i) {
        int zze2 = zze(i);
        long j = (long) (1048575 & zze2);
        if (j != 1048575) {
            zzma.zza((Object) t, j, (1 << (zze2 >>> 20)) | zzma.zza((Object) t, j));
        }
    }

    private final boolean zza(T t, int i, int i2) {
        return zzma.zza((Object) t, (long) (zze(i2) & 1048575)) == i;
    }

    private final void zzb(T t, int i, int i2) {
        zzma.zza((Object) t, (long) (zze(i2) & 1048575), i);
    }

    private final int zzg(int i) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzb(i, 0);
    }

    private final int zza(int i, int i2) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzb(i, i2);
    }

    private final int zzb(int i, int i2) {
        int length = (this.zzc.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int i5 = this.zzc[i4];
            if (i == i5) {
                return i4;
            }
            if (i < i5) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }
}
