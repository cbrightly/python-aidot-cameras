package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public final class zzel<K, V> extends zzek<K, V> {
    /* JADX WARNING: type inference failed for: r9v5, types: [com.google.android.gms.internal.vision.zzex] */
    /* JADX WARNING: type inference failed for: r9v6, types: [com.google.android.gms.internal.vision.zzex] */
    /* JADX WARNING: type inference failed for: r9v11, types: [com.google.android.gms.internal.vision.zzeb, com.google.android.gms.internal.vision.zzej] */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0049, code lost:
        if (r10 == false) goto L_0x00cf;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.vision.zzem<K, V> zza() {
        /*
            r19 = this;
            r0 = r19
            java.util.Map<K, java.util.Collection<V>> r1 = r0.zza
            java.util.Set r1 = r1.entrySet()
            boolean r2 = r1.isEmpty()
            if (r2 == 0) goto L_0x0012
            com.google.android.gms.internal.vision.zzdz r1 = com.google.android.gms.internal.vision.zzdz.zza
            return r1
        L_0x0012:
            com.google.android.gms.internal.vision.zzei r2 = new com.google.android.gms.internal.vision.zzei
            int r3 = r1.size()
            r2.<init>(r3)
            java.util.Iterator r1 = r1.iterator()
            r3 = 0
            r4 = r3
        L_0x0022:
            boolean r5 = r1.hasNext()
            r6 = 0
            if (r5 == 0) goto L_0x012a
            java.lang.Object r5 = r1.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            java.lang.Object r8 = r5.getKey()
            java.lang.Object r5 = r5.getValue()
            java.util.Collection r5 = (java.util.Collection) r5
            boolean r9 = r5 instanceof com.google.android.gms.internal.vision.zzej
            if (r9 == 0) goto L_0x004d
            boolean r9 = r5 instanceof java.util.SortedSet
            if (r9 != 0) goto L_0x004d
            r9 = r5
            com.google.android.gms.internal.vision.zzej r9 = (com.google.android.gms.internal.vision.zzej) r9
            boolean r10 = r9.zzf()
            if (r10 != 0) goto L_0x004d
            goto L_0x00cf
        L_0x004d:
            java.lang.Object[] r5 = r5.toArray()
            int r9 = r5.length
        L_0x0052:
            switch(r9) {
                case 0: goto L_0x006d;
                case 1: goto L_0x0063;
                default: goto L_0x0055;
            }
        L_0x0055:
            int r10 = com.google.android.gms.internal.vision.zzej.zza(r9)
            java.lang.Object[] r14 = new java.lang.Object[r10]
            int r15 = r10 + -1
            r11 = r3
            r12 = r11
            r13 = r12
            goto L_0x0070
        L_0x0063:
            r5 = r5[r3]
            com.google.android.gms.internal.vision.zzex r9 = new com.google.android.gms.internal.vision.zzex
            r9.<init>(r5)
            goto L_0x00cf
        L_0x006d:
            com.google.android.gms.internal.vision.zzev<java.lang.Object> r9 = com.google.android.gms.internal.vision.zzev.zza
            goto L_0x00cf
        L_0x0070:
            if (r11 >= r9) goto L_0x009d
            r3 = r5[r11]
            java.lang.Object r3 = com.google.android.gms.internal.vision.zzeq.zza(r3, r11)
            int r16 = r3.hashCode()
            int r17 = com.google.android.gms.internal.vision.zzec.zza((int) r16)
        L_0x0080:
            r18 = r17 & r15
            r7 = r14[r18]
            if (r7 != 0) goto L_0x0090
            int r7 = r12 + 1
            r5[r12] = r3
            r14[r18] = r3
            int r13 = r13 + r16
            r12 = r7
            goto L_0x0099
        L_0x0090:
            boolean r7 = r7.equals(r3)
            if (r7 != 0) goto L_0x0099
            int r17 = r17 + 1
            goto L_0x0080
        L_0x0099:
            int r11 = r11 + 1
            r3 = 0
            goto L_0x0070
        L_0x009d:
            java.util.Arrays.fill(r5, r12, r9, r6)
            r3 = 1
            if (r12 != r3) goto L_0x00ac
            r3 = 0
            r5 = r5[r3]
            com.google.android.gms.internal.vision.zzex r9 = new com.google.android.gms.internal.vision.zzex
            r9.<init>(r5, r13)
            goto L_0x00cf
        L_0x00ac:
            int r3 = com.google.android.gms.internal.vision.zzej.zza(r12)
            int r10 = r10 / 2
            if (r3 >= r10) goto L_0x00b7
            r9 = r12
            r3 = 0
            goto L_0x0052
        L_0x00b7:
            int r3 = r5.length
            int r6 = r3 >> 1
            int r3 = r3 >> 2
            int r6 = r6 + r3
            if (r12 >= r6) goto L_0x00c4
            java.lang.Object[] r5 = java.util.Arrays.copyOf(r5, r12)
            goto L_0x00c5
        L_0x00c4:
        L_0x00c5:
            com.google.android.gms.internal.vision.zzev r9 = new com.google.android.gms.internal.vision.zzev
            r11 = r9
            r3 = r12
            r12 = r5
            r16 = r3
            r11.<init>(r12, r13, r14, r15, r16)
        L_0x00cf:
            boolean r3 = r9.isEmpty()
            if (r3 != 0) goto L_0x0127
            int r3 = r2.zzb
            r5 = 1
            int r3 = r3 + r5
            int r3 = r3 << r5
            java.lang.Object[] r5 = r2.zza
            int r6 = r5.length
            if (r3 <= r6) goto L_0x010c
            int r6 = r5.length
            if (r3 < 0) goto L_0x0104
            int r7 = r6 >> 1
            int r6 = r6 + r7
            r7 = 1
            int r6 = r6 + r7
            if (r6 >= r3) goto L_0x00f4
            int r3 = r3 + -1
            int r3 = java.lang.Integer.highestOneBit(r3)
            int r6 = r3 << 1
        L_0x00f4:
            if (r6 >= 0) goto L_0x00f9
            r6 = 2147483647(0x7fffffff, float:NaN)
        L_0x00f9:
            java.lang.Object[] r3 = java.util.Arrays.copyOf(r5, r6)
            r2.zza = r3
            r3 = 0
            r2.zzc = r3
            goto L_0x010d
        L_0x0104:
            java.lang.AssertionError r1 = new java.lang.AssertionError
            java.lang.String r2 = "cannot store more than MAX_VALUE elements"
            r1.<init>(r2)
            throw r1
        L_0x010c:
            r3 = 0
        L_0x010d:
            com.google.android.gms.internal.vision.zzdq.zza(r8, r9)
            java.lang.Object[] r5 = r2.zza
            int r6 = r2.zzb
            int r7 = r6 * 2
            r5[r7] = r8
            int r7 = r6 * 2
            r8 = 1
            int r7 = r7 + r8
            r5[r7] = r9
            int r6 = r6 + r8
            r2.zzb = r6
            int r5 = r9.size()
            int r4 = r4 + r5
            goto L_0x0128
        L_0x0127:
            r3 = 0
        L_0x0128:
            goto L_0x0022
        L_0x012a:
            com.google.android.gms.internal.vision.zzem r1 = new com.google.android.gms.internal.vision.zzem
            r3 = 1
            r2.zzc = r3
            int r3 = r2.zzb
            java.lang.Object[] r2 = r2.zza
            com.google.android.gms.internal.vision.zzes r2 = com.google.android.gms.internal.vision.zzes.zza(r3, r2)
            r1.<init>(r2, r4, r6)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzel.zza():com.google.android.gms.internal.vision.zzem");
    }
}
