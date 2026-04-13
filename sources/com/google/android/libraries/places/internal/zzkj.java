package com.google.android.libraries.places.internal;

import javax.annotation.CheckForNull;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzkj extends zzjt {
    static final zzjt zza = new zzkj((Object) null, new Object[0], 0);
    final transient Object[] zzb;
    @CheckForNull
    private final transient Object zzc;
    private final transient int zzd;

    private zzkj(@CheckForNull Object obj, Object[] objArr, int i) {
        this.zzc = obj;
        this.zzb = objArr;
        this.zzd = i;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v9, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.google.android.libraries.places.internal.zzkj zzf(int r17, java.lang.Object[] r18, com.google.android.libraries.places.internal.zzjs r19) {
        /*
            r0 = r17
            r1 = r18
            if (r0 != 0) goto L_0x000b
            com.google.android.libraries.places.internal.zzjt r0 = zza
            com.google.android.libraries.places.internal.zzkj r0 = (com.google.android.libraries.places.internal.zzkj) r0
            return r0
        L_0x000b:
            r2 = 0
            r3 = 0
            r4 = 1
            if (r0 != r4) goto L_0x0023
            r0 = r1[r3]
            r0.getClass()
            r3 = r1[r4]
            r3.getClass()
            com.google.android.libraries.places.internal.zzjf.zza(r0, r3)
            com.google.android.libraries.places.internal.zzkj r0 = new com.google.android.libraries.places.internal.zzkj
            r0.<init>(r2, r1, r4)
            return r0
        L_0x0023:
            int r5 = r1.length
            int r5 = r5 >> r4
            java.lang.String r6 = "index"
            com.google.android.libraries.places.internal.zziy.zzb(r0, r5, r6)
            r5 = 2
            int r6 = java.lang.Math.max(r0, r5)
            r7 = 751619276(0x2ccccccc, float:5.8207657E-12)
            r8 = 1073741824(0x40000000, float:2.0)
            if (r6 >= r7) goto L_0x004c
            int r7 = r6 + -1
            int r7 = java.lang.Integer.highestOneBit(r7)
            int r7 = r7 + r7
            r8 = r7
        L_0x003e:
            double r9 = (double) r8
            r11 = 4604480259023595110(0x3fe6666666666666, double:0.7)
            double r9 = r9 * r11
            double r11 = (double) r6
            int r7 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r7 >= 0) goto L_0x0057
            int r8 = r8 + r8
            goto L_0x003e
        L_0x004c:
            if (r6 >= r8) goto L_0x0050
            r6 = r4
            goto L_0x0051
        L_0x0050:
            r6 = r3
        L_0x0051:
            java.lang.String r7 = "collection too large"
            com.google.android.libraries.places.internal.zziy.zze(r6, r7)
        L_0x0057:
            if (r0 != r4) goto L_0x0068
            r6 = r1[r3]
            r6.getClass()
            r7 = r1[r4]
            r7.getClass()
            com.google.android.libraries.places.internal.zzjf.zza(r6, r7)
            goto L_0x01b8
        L_0x0068:
            int r6 = r8 + -1
            r7 = 128(0x80, float:1.794E-43)
            r9 = 3
            r10 = -1
            if (r8 > r7) goto L_0x00df
            byte[] r7 = new byte[r8]
            java.util.Arrays.fill(r7, r10)
            r8 = r3
            r10 = r8
        L_0x0077:
            if (r8 >= r0) goto L_0x00c8
            int r11 = r10 + r10
            int r12 = r8 + r8
            r13 = r1[r12]
            r13.getClass()
            r12 = r12 ^ r4
            r12 = r1[r12]
            r12.getClass()
            com.google.android.libraries.places.internal.zzjf.zza(r13, r12)
            int r14 = r13.hashCode()
            int r14 = com.google.android.libraries.places.internal.zzjm.zza(r14)
        L_0x0093:
            r14 = r14 & r6
            byte r15 = r7[r14]
            r5 = 255(0xff, float:3.57E-43)
            r15 = r15 & r5
            if (r15 != r5) goto L_0x00a9
            byte r5 = (byte) r11
            r7[r14] = r5
            if (r10 >= r8) goto L_0x00a6
            r1[r11] = r13
            r5 = r11 ^ 1
            r1[r5] = r12
        L_0x00a6:
            int r10 = r10 + 1
            goto L_0x00c0
        L_0x00a9:
            r5 = r1[r15]
            boolean r5 = r13.equals(r5)
            if (r5 == 0) goto L_0x00c4
            r2 = r15 ^ 1
            com.google.android.libraries.places.internal.zzjr r5 = new com.google.android.libraries.places.internal.zzjr
            r11 = r1[r2]
            r11.getClass()
            r5.<init>(r13, r12, r11)
            r1[r2] = r12
            r2 = r5
        L_0x00c0:
            int r8 = r8 + 1
            r5 = 2
            goto L_0x0077
        L_0x00c4:
            int r14 = r14 + 1
            r5 = 2
            goto L_0x0093
        L_0x00c8:
            if (r10 != r0) goto L_0x00ce
            r2 = r7
            r5 = 2
            goto L_0x01b8
        L_0x00ce:
            java.lang.Object[] r5 = new java.lang.Object[r9]
            r5[r3] = r7
            java.lang.Integer r6 = java.lang.Integer.valueOf(r10)
            r5[r4] = r6
            r6 = 2
            r5[r6] = r2
            r2 = r5
            r5 = r6
            goto L_0x01b8
        L_0x00df:
            r5 = 32768(0x8000, float:4.5918E-41)
            if (r8 > r5) goto L_0x0151
            short[] r5 = new short[r8]
            java.util.Arrays.fill(r5, r10)
            r7 = r3
            r8 = r7
        L_0x00eb:
            if (r7 >= r0) goto L_0x013b
            int r10 = r8 + r8
            int r11 = r7 + r7
            r12 = r1[r11]
            r12.getClass()
            r11 = r11 ^ r4
            r11 = r1[r11]
            r11.getClass()
            com.google.android.libraries.places.internal.zzjf.zza(r12, r11)
            int r13 = r12.hashCode()
            int r13 = com.google.android.libraries.places.internal.zzjm.zza(r13)
        L_0x0107:
            r13 = r13 & r6
            short r14 = r5[r13]
            char r14 = (char) r14
            r15 = 65535(0xffff, float:9.1834E-41)
            if (r14 != r15) goto L_0x011e
            short r14 = (short) r10
            r5[r13] = r14
            if (r8 >= r7) goto L_0x011b
            r1[r10] = r12
            r10 = r10 ^ 1
            r1[r10] = r11
        L_0x011b:
            int r8 = r8 + 1
            goto L_0x0135
        L_0x011e:
            r15 = r1[r14]
            boolean r15 = r12.equals(r15)
            if (r15 == 0) goto L_0x0138
            r2 = r14 ^ 1
            com.google.android.libraries.places.internal.zzjr r10 = new com.google.android.libraries.places.internal.zzjr
            r13 = r1[r2]
            r13.getClass()
            r10.<init>(r12, r11, r13)
            r1[r2] = r11
            r2 = r10
        L_0x0135:
            int r7 = r7 + 1
            goto L_0x00eb
        L_0x0138:
            int r13 = r13 + 1
            goto L_0x0107
        L_0x013b:
            if (r8 != r0) goto L_0x0141
            r2 = r5
            r5 = 2
            goto L_0x01b8
        L_0x0141:
            java.lang.Object[] r6 = new java.lang.Object[r9]
            r6[r3] = r5
            java.lang.Integer r5 = java.lang.Integer.valueOf(r8)
            r6[r4] = r5
            r5 = 2
            r6[r5] = r2
            r2 = r6
            goto L_0x01b8
        L_0x0151:
            int[] r5 = new int[r8]
            java.util.Arrays.fill(r5, r10)
            r7 = r3
            r8 = r7
        L_0x0158:
            if (r7 >= r0) goto L_0x01a5
            int r11 = r8 + r8
            int r12 = r7 + r7
            r13 = r1[r12]
            r13.getClass()
            r12 = r12 ^ r4
            r12 = r1[r12]
            r12.getClass()
            com.google.android.libraries.places.internal.zzjf.zza(r13, r12)
            int r14 = r13.hashCode()
            int r14 = com.google.android.libraries.places.internal.zzjm.zza(r14)
        L_0x0174:
            r14 = r14 & r6
            r15 = r5[r14]
            if (r15 != r10) goto L_0x0186
            r5[r14] = r11
            if (r8 >= r7) goto L_0x0183
            r1[r11] = r13
            r11 = r11 ^ 1
            r1[r11] = r12
        L_0x0183:
            int r8 = r8 + 1
            goto L_0x019d
        L_0x0186:
            r10 = r1[r15]
            boolean r10 = r13.equals(r10)
            if (r10 == 0) goto L_0x01a1
            r2 = r15 ^ 1
            com.google.android.libraries.places.internal.zzjr r10 = new com.google.android.libraries.places.internal.zzjr
            r11 = r1[r2]
            r11.getClass()
            r10.<init>(r13, r12, r11)
            r1[r2] = r12
            r2 = r10
        L_0x019d:
            int r7 = r7 + 1
            r10 = -1
            goto L_0x0158
        L_0x01a1:
            int r14 = r14 + 1
            r10 = -1
            goto L_0x0174
        L_0x01a5:
            if (r8 != r0) goto L_0x01aa
            r2 = r5
            r5 = 2
            goto L_0x01b8
        L_0x01aa:
            java.lang.Object[] r6 = new java.lang.Object[r9]
            r6[r3] = r5
            java.lang.Integer r5 = java.lang.Integer.valueOf(r8)
            r6[r4] = r5
            r5 = 2
            r6[r5] = r2
            r2 = r6
        L_0x01b8:
            boolean r6 = r2 instanceof java.lang.Object[]
            if (r6 == 0) goto L_0x01dc
            java.lang.Object[] r2 = (java.lang.Object[]) r2
            r0 = r2[r5]
            com.google.android.libraries.places.internal.zzjr r0 = (com.google.android.libraries.places.internal.zzjr) r0
            r5 = r19
            r5.zzc = r0
            r0 = r2[r3]
            r2 = r2[r4]
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            int r3 = r2 + r2
            java.lang.Object[] r1 = java.util.Arrays.copyOf(r1, r3)
            r16 = r2
            r2 = r0
            r0 = r16
        L_0x01dc:
            com.google.android.libraries.places.internal.zzkj r3 = new com.google.android.libraries.places.internal.zzkj
            r3.<init>(r2, r1, r0)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.places.internal.zzkj.zzf(int, java.lang.Object[], com.google.android.libraries.places.internal.zzjs):com.google.android.libraries.places.internal.zzkj");
    }

    @CheckForNull
    public final Object get(@CheckForNull Object obj) {
        Object obj2;
        Object obj3 = this.zzc;
        Object[] objArr = this.zzb;
        int i = this.zzd;
        if (obj == null) {
            obj2 = null;
        } else {
            if (i == 1) {
                Object obj4 = objArr[0];
                obj4.getClass();
                if (obj4.equals(obj)) {
                    obj2 = objArr[1];
                    obj2.getClass();
                }
            } else if (obj3 != null) {
                if (obj3 instanceof byte[]) {
                    byte[] bArr = (byte[]) obj3;
                    int length = bArr.length - 1;
                    int zza2 = zzjm.zza(obj.hashCode());
                    while (true) {
                        int i2 = zza2 & length;
                        byte b = bArr[i2] & 255;
                        if (b == 255) {
                            obj2 = null;
                            break;
                        } else if (obj.equals(objArr[b])) {
                            obj2 = objArr[b ^ 1];
                            break;
                        } else {
                            zza2 = i2 + 1;
                        }
                    }
                } else if (obj3 instanceof short[]) {
                    short[] sArr = (short[]) obj3;
                    int length2 = sArr.length - 1;
                    int zza3 = zzjm.zza(obj.hashCode());
                    while (true) {
                        int i3 = zza3 & length2;
                        char c = (char) sArr[i3];
                        if (c == 65535) {
                            obj2 = null;
                            break;
                        } else if (obj.equals(objArr[c])) {
                            obj2 = objArr[c ^ 1];
                            break;
                        } else {
                            zza3 = i3 + 1;
                        }
                    }
                } else {
                    int[] iArr = (int[]) obj3;
                    int length3 = iArr.length - 1;
                    int zza4 = zzjm.zza(obj.hashCode());
                    while (true) {
                        int i4 = zza4 & length3;
                        int i5 = iArr[i4];
                        if (i5 == -1) {
                            obj2 = null;
                            break;
                        } else if (obj.equals(objArr[i5])) {
                            obj2 = objArr[i5 ^ 1];
                            break;
                        } else {
                            zza4 = i4 + 1;
                        }
                    }
                }
            }
            obj2 = null;
        }
        if (obj2 == null) {
            return null;
        }
        return obj2;
    }

    public final int size() {
        return this.zzd;
    }

    /* access modifiers changed from: package-private */
    public final zzjn zza() {
        return new zzki(this.zzb, 1, this.zzd);
    }

    /* access modifiers changed from: package-private */
    public final zzju zzc() {
        return new zzkg(this, this.zzb, 0, this.zzd);
    }

    /* access modifiers changed from: package-private */
    public final zzju zzd() {
        return new zzkh(this, new zzki(this.zzb, 0, this.zzd));
    }
}
