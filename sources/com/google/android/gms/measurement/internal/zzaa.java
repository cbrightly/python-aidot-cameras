package com.google.android.gms.measurement.internal;

import java.util.Map;
import java.util.Set;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzaa extends zzkt {
    private String zza;
    private Set zzb;
    private Map zzc;
    private Long zzd;
    private Long zze;

    zzaa(zzlg zzlg) {
        super(zzlg);
    }

    private final zzu zzd(Integer num) {
        if (this.zzc.containsKey(num)) {
            return (zzu) this.zzc.get(num);
        }
        zzu zzu = new zzu(this, this.zza, (zzt) null);
        this.zzc.put(num, zzu);
        return zzu;
    }

    private final boolean zzf(int i, int i2) {
        zzu zzu = (zzu) this.zzc.get(Integer.valueOf(i));
        if (zzu == null) {
            return false;
        }
        return zzu.zze.get(i2);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x02e2, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x02e3, code lost:
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x0301, code lost:
        if (r5 != null) goto L_0x02d2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:281:0x076b, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:282:0x076d, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:283:0x076f, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:284:0x0770, code lost:
        r22 = r7;
        r28 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:286:0x0777, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:287:0x0778, code lost:
        r29 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:289:0x077f, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:290:0x0780, code lost:
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:294:0x079f, code lost:
        if (r5 == null) goto L_0x07a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:298:0x07a9, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:357:0x092c, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:360:0x0932, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:361:0x0933, code lost:
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:365:0x0952, code lost:
        if (r3 == null) goto L_0x0955;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:370:0x095d, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:399:0x0a6c, code lost:
        if (r7.zzj() == false) goto L_0x0a77;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:400:0x0a6e, code lost:
        r7 = java.lang.Integer.valueOf(r7.zza());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:401:0x0a77, code lost:
        r7 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:402:0x0a78, code lost:
        r0.zzc("Invalid property filter ID. appId, id", r6, java.lang.String.valueOf(r7));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0173, code lost:
        if (r5 != null) goto L_0x0151;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0222, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0224, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0225, code lost:
        r5 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0228, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x0229, code lost:
        r18 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x022b, code lost:
        r19 = r7;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x025c  */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x0264  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x02e2 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:107:0x028f] */
    /* JADX WARNING: Removed duplicated region for block: B:159:0x0425  */
    /* JADX WARNING: Removed duplicated region for block: B:234:0x05e5  */
    /* JADX WARNING: Removed duplicated region for block: B:235:0x05e9  */
    /* JADX WARNING: Removed duplicated region for block: B:282:0x076d A[ExcHandler: all (th java.lang.Throwable), Splitter:B:252:0x06f5] */
    /* JADX WARNING: Removed duplicated region for block: B:289:0x077f A[ExcHandler: all (th java.lang.Throwable), Splitter:B:247:0x06d6] */
    /* JADX WARNING: Removed duplicated region for block: B:298:0x07a9  */
    /* JADX WARNING: Removed duplicated region for block: B:321:0x085d  */
    /* JADX WARNING: Removed duplicated region for block: B:322:0x0861  */
    /* JADX WARNING: Removed duplicated region for block: B:360:0x0932 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:328:0x08a6] */
    /* JADX WARNING: Removed duplicated region for block: B:370:0x095d  */
    /* JADX WARNING: Removed duplicated region for block: B:412:0x0abb  */
    /* JADX WARNING: Removed duplicated region for block: B:430:0x0b54  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0179  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01b3 A[Catch:{ SQLiteException -> 0x0228, all -> 0x0224 }] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x01c1 A[SYNTHETIC, Splitter:B:66:0x01c1] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0224 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:61:0x01ad] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0251  */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List zza(java.lang.String r65, java.util.List r66, java.util.List r67, java.lang.Long r68, java.lang.Long r69) {
        /*
            r64 = this;
            r10 = r64
            java.lang.String r11 = "current_results"
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r65)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r66)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r67)
            r0 = r65
            r10.zza = r0
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            r10.zzb = r0
            androidx.collection.ArrayMap r0 = new androidx.collection.ArrayMap
            r0.<init>()
            r10.zzc = r0
            r0 = r68
            r10.zzd = r0
            r0 = r69
            r10.zze = r0
            java.util.Iterator r0 = r66.iterator()
        L_0x002b:
            boolean r1 = r0.hasNext()
            r12 = 0
            r13 = 1
            if (r1 == 0) goto L_0x0047
            java.lang.Object r1 = r0.next()
            com.google.android.gms.internal.measurement.zzft r1 = (com.google.android.gms.internal.measurement.zzft) r1
            java.lang.String r1 = r1.zzh()
            java.lang.String r2 = "_s"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L_0x002b
            r1 = r13
            goto L_0x0048
        L_0x0047:
            r1 = r12
        L_0x0048:
            com.google.android.gms.internal.measurement.zzov.zzc()
            com.google.android.gms.measurement.internal.zzge r0 = r10.zzt
            com.google.android.gms.measurement.internal.zzag r0 = r0.zzf()
            java.lang.String r2 = r10.zza
            com.google.android.gms.measurement.internal.zzeg r3 = com.google.android.gms.measurement.internal.zzeh.zzY
            boolean r14 = r0.zzs(r2, r3)
            com.google.android.gms.internal.measurement.zzov.zzc()
            com.google.android.gms.measurement.internal.zzge r0 = r10.zzt
            com.google.android.gms.measurement.internal.zzag r0 = r0.zzf()
            java.lang.String r2 = r10.zza
            com.google.android.gms.measurement.internal.zzeg r3 = com.google.android.gms.measurement.internal.zzeh.zzX
            boolean r15 = r0.zzs(r2, r3)
            if (r1 == 0) goto L_0x00af
            com.google.android.gms.measurement.internal.zzlg r0 = r10.zzf
            com.google.android.gms.measurement.internal.zzam r2 = r0.zzi()
            java.lang.String r3 = r10.zza
            r2.zzW()
            r2.zzg()
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r3)
            android.content.ContentValues r0 = new android.content.ContentValues
            r0.<init>()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r12)
            java.lang.String r5 = "current_session_count"
            r0.put(r5, r4)
            android.database.sqlite.SQLiteDatabase r4 = r2.zzh()     // Catch:{ SQLiteException -> 0x009b }
            java.lang.String r5 = "events"
            java.lang.String r6 = "app_id = ?"
            java.lang.String[] r7 = new java.lang.String[r13]     // Catch:{ SQLiteException -> 0x009b }
            r7[r12] = r3     // Catch:{ SQLiteException -> 0x009b }
            r4.update(r5, r0, r6, r7)     // Catch:{ SQLiteException -> 0x009b }
            goto L_0x00af
        L_0x009b:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzge r2 = r2.zzt
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzeu.zzn(r3)
            java.lang.String r4 = "Error resetting session-scoped event counts. appId"
            r2.zzc(r4, r3, r0)
        L_0x00af:
            java.util.Map r0 = java.util.Collections.emptyMap()
            java.lang.String r9 = "Failed to merge filter. appId"
            java.lang.String r8 = "Database error querying filters. appId"
            java.lang.String r7 = "data"
            java.lang.String r6 = "audience_id"
            if (r15 == 0) goto L_0x017d
            if (r14 == 0) goto L_0x017d
            com.google.android.gms.measurement.internal.zzlg r0 = r10.zzf
            com.google.android.gms.measurement.internal.zzam r2 = r0.zzi()
            java.lang.String r3 = r10.zza
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r3)
            androidx.collection.ArrayMap r4 = new androidx.collection.ArrayMap
            r4.<init>()
            android.database.sqlite.SQLiteDatabase r16 = r2.zzh()
            java.lang.String r17 = "event_filters"
            java.lang.String[] r18 = new java.lang.String[]{r6, r7}     // Catch:{ SQLiteException -> 0x015c, all -> 0x0159 }
            java.lang.String r19 = "app_id=?"
            java.lang.String[] r0 = new java.lang.String[r13]     // Catch:{ SQLiteException -> 0x015c, all -> 0x0159 }
            r0[r12] = r3     // Catch:{ SQLiteException -> 0x015c, all -> 0x0159 }
            r21 = 0
            r22 = 0
            r23 = 0
            r20 = r0
            android.database.Cursor r5 = r16.query(r17, r18, r19, r20, r21, r22, r23)     // Catch:{ SQLiteException -> 0x015c, all -> 0x0159 }
            boolean r0 = r5.moveToFirst()     // Catch:{ SQLiteException -> 0x0157, all -> 0x0155 }
            if (r0 == 0) goto L_0x014d
        L_0x00f1:
            byte[] r0 = r5.getBlob(r13)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0155 }
            com.google.android.gms.internal.measurement.zzej r13 = com.google.android.gms.internal.measurement.zzek.zzc()     // Catch:{ IOException -> 0x012d }
            com.google.android.gms.internal.measurement.zzmh r0 = com.google.android.gms.measurement.internal.zzli.zzl(r13, r0)     // Catch:{ IOException -> 0x012d }
            com.google.android.gms.internal.measurement.zzej r0 = (com.google.android.gms.internal.measurement.zzej) r0     // Catch:{ IOException -> 0x012d }
            com.google.android.gms.internal.measurement.zzlb r0 = r0.zzaD()     // Catch:{ IOException -> 0x012d }
            com.google.android.gms.internal.measurement.zzek r0 = (com.google.android.gms.internal.measurement.zzek) r0     // Catch:{ IOException -> 0x012d }
            boolean r13 = r0.zzo()     // Catch:{ SQLiteException -> 0x0157, all -> 0x0155 }
            if (r13 != 0) goto L_0x010d
            goto L_0x013f
        L_0x010d:
            int r13 = r5.getInt(r12)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0155 }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0155 }
            java.lang.Object r16 = r4.get(r13)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0155 }
            java.util.List r16 = (java.util.List) r16     // Catch:{ SQLiteException -> 0x0157, all -> 0x0155 }
            if (r16 != 0) goto L_0x0127
            java.util.ArrayList r12 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x0157, all -> 0x0155 }
            r12.<init>()     // Catch:{ SQLiteException -> 0x0157, all -> 0x0155 }
            r4.put(r13, r12)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0155 }
            goto L_0x0129
        L_0x0127:
            r12 = r16
        L_0x0129:
            r12.add(r0)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0155 }
            goto L_0x013f
        L_0x012d:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzge r12 = r2.zzt     // Catch:{ SQLiteException -> 0x0157, all -> 0x0155 }
            com.google.android.gms.measurement.internal.zzeu r12 = r12.zzaA()     // Catch:{ SQLiteException -> 0x0157, all -> 0x0155 }
            com.google.android.gms.measurement.internal.zzes r12 = r12.zzd()     // Catch:{ SQLiteException -> 0x0157, all -> 0x0155 }
            java.lang.Object r13 = com.google.android.gms.measurement.internal.zzeu.zzn(r3)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0155 }
            r12.zzc(r9, r13, r0)     // Catch:{ SQLiteException -> 0x0157, all -> 0x0155 }
        L_0x013f:
            boolean r0 = r5.moveToNext()     // Catch:{ SQLiteException -> 0x0157, all -> 0x0155 }
            if (r0 != 0) goto L_0x014a
            r5.close()
            r12 = r4
            goto L_0x017e
        L_0x014a:
            r12 = 0
            r13 = 1
            goto L_0x00f1
        L_0x014d:
            java.util.Map r0 = java.util.Collections.emptyMap()     // Catch:{ SQLiteException -> 0x0157, all -> 0x0155 }
        L_0x0151:
            r5.close()
            goto L_0x017d
        L_0x0155:
            r0 = move-exception
            goto L_0x0177
        L_0x0157:
            r0 = move-exception
            goto L_0x015e
        L_0x0159:
            r0 = move-exception
            r5 = 0
            goto L_0x0177
        L_0x015c:
            r0 = move-exception
            r5 = 0
        L_0x015e:
            com.google.android.gms.measurement.internal.zzge r2 = r2.zzt     // Catch:{ all -> 0x0176 }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ all -> 0x0176 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x0176 }
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzeu.zzn(r3)     // Catch:{ all -> 0x0176 }
            r2.zzc(r8, r3, r0)     // Catch:{ all -> 0x0176 }
            java.util.Map r0 = java.util.Collections.emptyMap()     // Catch:{ all -> 0x0176 }
            if (r5 == 0) goto L_0x017d
            goto L_0x0151
        L_0x0176:
            r0 = move-exception
        L_0x0177:
            if (r5 == 0) goto L_0x017c
            r5.close()
        L_0x017c:
            throw r0
        L_0x017d:
            r12 = r0
        L_0x017e:
            com.google.android.gms.measurement.internal.zzlg r0 = r10.zzf
            com.google.android.gms.measurement.internal.zzam r2 = r0.zzi()
            java.lang.String r3 = r10.zza
            r2.zzW()
            r2.zzg()
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r3)
            android.database.sqlite.SQLiteDatabase r16 = r2.zzh()
            java.lang.String r17 = "audience_filter_values"
            java.lang.String[] r18 = new java.lang.String[]{r6, r11}     // Catch:{ SQLiteException -> 0x0232, all -> 0x022e }
            java.lang.String r19 = "app_id=?"
            r4 = 1
            java.lang.String[] r0 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x0232, all -> 0x022e }
            r4 = 0
            r0[r4] = r3     // Catch:{ SQLiteException -> 0x0232, all -> 0x022e }
            r21 = 0
            r22 = 0
            r23 = 0
            r20 = r0
            android.database.Cursor r4 = r16.query(r17, r18, r19, r20, r21, r22, r23)     // Catch:{ SQLiteException -> 0x0232, all -> 0x022e }
            boolean r0 = r4.moveToFirst()     // Catch:{ SQLiteException -> 0x0228, all -> 0x0224 }
            if (r0 != 0) goto L_0x01c1
            java.util.Map r0 = java.util.Collections.emptyMap()     // Catch:{ SQLiteException -> 0x0228, all -> 0x0224 }
            r4.close()
            r13 = r0
            r18 = r6
            r19 = r7
            goto L_0x0255
        L_0x01c1:
            androidx.collection.ArrayMap r5 = new androidx.collection.ArrayMap     // Catch:{ SQLiteException -> 0x0228, all -> 0x0224 }
            r5.<init>()     // Catch:{ SQLiteException -> 0x0228, all -> 0x0224 }
        L_0x01c6:
            r13 = 0
            int r16 = r4.getInt(r13)     // Catch:{ SQLiteException -> 0x0228, all -> 0x0224 }
            r13 = 1
            byte[] r0 = r4.getBlob(r13)     // Catch:{ SQLiteException -> 0x0228, all -> 0x0224 }
            com.google.android.gms.internal.measurement.zzgh r13 = com.google.android.gms.internal.measurement.zzgi.zze()     // Catch:{ IOException -> 0x01ef }
            com.google.android.gms.internal.measurement.zzmh r0 = com.google.android.gms.measurement.internal.zzli.zzl(r13, r0)     // Catch:{ IOException -> 0x01ef }
            com.google.android.gms.internal.measurement.zzgh r0 = (com.google.android.gms.internal.measurement.zzgh) r0     // Catch:{ IOException -> 0x01ef }
            com.google.android.gms.internal.measurement.zzlb r0 = r0.zzaD()     // Catch:{ IOException -> 0x01ef }
            com.google.android.gms.internal.measurement.zzgi r0 = (com.google.android.gms.internal.measurement.zzgi) r0     // Catch:{ IOException -> 0x01ef }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r16)     // Catch:{ SQLiteException -> 0x0228, all -> 0x0224 }
            r5.put(r13, r0)     // Catch:{ SQLiteException -> 0x0228, all -> 0x0224 }
            r17 = r5
            r18 = r6
            r19 = r7
            goto L_0x020d
        L_0x01ef:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzge r13 = r2.zzt     // Catch:{ SQLiteException -> 0x0228, all -> 0x0224 }
            com.google.android.gms.measurement.internal.zzeu r13 = r13.zzaA()     // Catch:{ SQLiteException -> 0x0228, all -> 0x0224 }
            com.google.android.gms.measurement.internal.zzes r13 = r13.zzd()     // Catch:{ SQLiteException -> 0x0228, all -> 0x0224 }
            r17 = r5
            java.lang.String r5 = "Failed to merge filter results. appId, audienceId, error"
            r18 = r6
            java.lang.Object r6 = com.google.android.gms.measurement.internal.zzeu.zzn(r3)     // Catch:{ SQLiteException -> 0x0222, all -> 0x0224 }
            r19 = r7
            java.lang.Integer r7 = java.lang.Integer.valueOf(r16)     // Catch:{ SQLiteException -> 0x0220, all -> 0x0224 }
            r13.zzd(r5, r6, r7, r0)     // Catch:{ SQLiteException -> 0x0220, all -> 0x0224 }
        L_0x020d:
            boolean r0 = r4.moveToNext()     // Catch:{ SQLiteException -> 0x0220, all -> 0x0224 }
            if (r0 != 0) goto L_0x0219
            r4.close()
            r13 = r17
            goto L_0x0255
        L_0x0219:
            r5 = r17
            r6 = r18
            r7 = r19
            goto L_0x01c6
        L_0x0220:
            r0 = move-exception
            goto L_0x0238
        L_0x0222:
            r0 = move-exception
            goto L_0x022b
        L_0x0224:
            r0 = move-exception
            r5 = r4
            goto L_0x0b52
        L_0x0228:
            r0 = move-exception
            r18 = r6
        L_0x022b:
            r19 = r7
            goto L_0x0238
        L_0x022e:
            r0 = move-exception
            r5 = 0
            goto L_0x0b52
        L_0x0232:
            r0 = move-exception
            r18 = r6
            r19 = r7
            r4 = 0
        L_0x0238:
            com.google.android.gms.measurement.internal.zzge r2 = r2.zzt     // Catch:{ all -> 0x0b50 }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ all -> 0x0b50 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x0b50 }
            java.lang.String r5 = "Database error querying filter results. appId"
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzeu.zzn(r3)     // Catch:{ all -> 0x0b50 }
            r2.zzc(r5, r3, r0)     // Catch:{ all -> 0x0b50 }
            java.util.Map r0 = java.util.Collections.emptyMap()     // Catch:{ all -> 0x0b50 }
            if (r4 == 0) goto L_0x0254
            r4.close()
        L_0x0254:
            r13 = r0
        L_0x0255:
            boolean r0 = r13.isEmpty()
            r7 = 2
            if (r0 == 0) goto L_0x0264
            r12 = r8
            r13 = r9
            r28 = r18
            r29 = r19
            goto L_0x05dd
        L_0x0264:
            java.util.HashSet r2 = new java.util.HashSet
            java.util.Set r0 = r13.keySet()
            r2.<init>(r0)
            if (r1 == 0) goto L_0x0429
            java.lang.String r1 = r10.zza
            com.google.android.gms.measurement.internal.zzlg r0 = r10.zzf
            com.google.android.gms.measurement.internal.zzam r3 = r0.zzi()
            java.lang.String r4 = r10.zza
            r3.zzW()
            r3.zzg()
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r4)
            androidx.collection.ArrayMap r0 = new androidx.collection.ArrayMap
            r0.<init>()
            android.database.sqlite.SQLiteDatabase r5 = r3.zzh()
            java.lang.String r6 = "select audience_id, filter_id from event_filters where app_id = ? and session_scoped = 1 UNION select audience_id, filter_id from property_filters where app_id = ? and session_scoped = 1;"
            r16 = r8
            java.lang.String[] r8 = new java.lang.String[r7]     // Catch:{ SQLiteException -> 0x02e0, all -> 0x02e2 }
            r17 = 0
            r8[r17] = r4     // Catch:{ SQLiteException -> 0x02e0, all -> 0x02e2 }
            r17 = 1
            r8[r17] = r4     // Catch:{ SQLiteException -> 0x02e0, all -> 0x02e2 }
            android.database.Cursor r5 = r5.rawQuery(r6, r8)     // Catch:{ SQLiteException -> 0x02e0, all -> 0x02e2 }
            boolean r6 = r5.moveToFirst()     // Catch:{ SQLiteException -> 0x02de, all -> 0x02db }
            if (r6 == 0) goto L_0x02d6
        L_0x02a3:
            r6 = 0
            int r8 = r5.getInt(r6)     // Catch:{ SQLiteException -> 0x02de, all -> 0x02db }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r8)     // Catch:{ SQLiteException -> 0x02de, all -> 0x02db }
            java.lang.Object r8 = r0.get(r6)     // Catch:{ SQLiteException -> 0x02de, all -> 0x02db }
            java.util.List r8 = (java.util.List) r8     // Catch:{ SQLiteException -> 0x02de, all -> 0x02db }
            if (r8 != 0) goto L_0x02be
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x02de, all -> 0x02db }
            r8.<init>()     // Catch:{ SQLiteException -> 0x02de, all -> 0x02db }
            r0.put(r6, r8)     // Catch:{ SQLiteException -> 0x02de, all -> 0x02db }
            goto L_0x02bf
        L_0x02be:
        L_0x02bf:
            r6 = 1
            int r17 = r5.getInt(r6)     // Catch:{ SQLiteException -> 0x02de, all -> 0x02db }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r17)     // Catch:{ SQLiteException -> 0x02de, all -> 0x02db }
            r8.add(r6)     // Catch:{ SQLiteException -> 0x02de, all -> 0x02db }
            boolean r6 = r5.moveToNext()     // Catch:{ SQLiteException -> 0x02de, all -> 0x02db }
            if (r6 != 0) goto L_0x02a3
        L_0x02d2:
            r5.close()
            goto L_0x0304
        L_0x02d6:
            java.util.Map r0 = java.util.Collections.emptyMap()     // Catch:{ SQLiteException -> 0x02de, all -> 0x02db }
            goto L_0x02d2
        L_0x02db:
            r0 = move-exception
            goto L_0x0423
        L_0x02de:
            r0 = move-exception
            goto L_0x02ea
        L_0x02e0:
            r0 = move-exception
            goto L_0x02e9
        L_0x02e2:
            r0 = move-exception
            r5 = 0
            goto L_0x0423
        L_0x02e6:
            r0 = move-exception
            r16 = r8
        L_0x02e9:
            r5 = 0
        L_0x02ea:
            com.google.android.gms.measurement.internal.zzge r3 = r3.zzt     // Catch:{ all -> 0x0422 }
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()     // Catch:{ all -> 0x0422 }
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()     // Catch:{ all -> 0x0422 }
            java.lang.String r6 = "Database error querying scoped filters. appId"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeu.zzn(r4)     // Catch:{ all -> 0x0422 }
            r3.zzc(r6, r4, r0)     // Catch:{ all -> 0x0422 }
            java.util.Map r0 = java.util.Collections.emptyMap()     // Catch:{ all -> 0x0422 }
            if (r5 == 0) goto L_0x0304
            goto L_0x02d2
        L_0x0304:
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r1)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r13)
            androidx.collection.ArrayMap r1 = new androidx.collection.ArrayMap
            r1.<init>()
            boolean r3 = r13.isEmpty()
            if (r3 == 0) goto L_0x0317
            goto L_0x0420
        L_0x0317:
            java.util.Set r3 = r13.keySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x031f:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x041f
            java.lang.Object r4 = r3.next()
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r4)
            java.lang.Object r6 = r13.get(r5)
            com.google.android.gms.internal.measurement.zzgi r6 = (com.google.android.gms.internal.measurement.zzgi) r6
            java.lang.Object r8 = r0.get(r5)
            java.util.List r8 = (java.util.List) r8
            if (r8 == 0) goto L_0x0411
            boolean r17 = r8.isEmpty()
            if (r17 == 0) goto L_0x034d
            r20 = r0
            r22 = r3
            goto L_0x0415
        L_0x034d:
            com.google.android.gms.measurement.internal.zzlg r5 = r10.zzf
            com.google.android.gms.measurement.internal.zzli r5 = r5.zzu()
            java.util.List r7 = r6.zzi()
            java.util.List r5 = r5.zzq(r7, r8)
            boolean r7 = r5.isEmpty()
            if (r7 != 0) goto L_0x040a
            com.google.android.gms.internal.measurement.zzkx r7 = r6.zzbB()
            com.google.android.gms.internal.measurement.zzgh r7 = (com.google.android.gms.internal.measurement.zzgh) r7
            r7.zzf()
            r7.zzb(r5)
            com.google.android.gms.measurement.internal.zzlg r5 = r10.zzf
            com.google.android.gms.measurement.internal.zzli r5 = r5.zzu()
            r20 = r0
            java.util.List r0 = r6.zzk()
            java.util.List r0 = r5.zzq(r0, r8)
            r7.zzh()
            r7.zzd(r0)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.List r5 = r6.zzh()
            java.util.Iterator r5 = r5.iterator()
        L_0x0390:
            boolean r21 = r5.hasNext()
            if (r21 == 0) goto L_0x03bd
            java.lang.Object r21 = r5.next()
            r22 = r3
            r3 = r21
            com.google.android.gms.internal.measurement.zzfr r3 = (com.google.android.gms.internal.measurement.zzfr) r3
            int r21 = r3.zza()
            r23 = r5
            java.lang.Integer r5 = java.lang.Integer.valueOf(r21)
            boolean r5 = r8.contains(r5)
            if (r5 != 0) goto L_0x03b8
            r0.add(r3)
            r3 = r22
            r5 = r23
            goto L_0x0390
        L_0x03b8:
            r3 = r22
            r5 = r23
            goto L_0x0390
        L_0x03bd:
            r22 = r3
            r7.zze()
            r7.zza(r0)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.List r3 = r6.zzj()
            java.util.Iterator r3 = r3.iterator()
        L_0x03d2:
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x03f0
            java.lang.Object r5 = r3.next()
            com.google.android.gms.internal.measurement.zzgk r5 = (com.google.android.gms.internal.measurement.zzgk) r5
            int r6 = r5.zzb()
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            boolean r6 = r8.contains(r6)
            if (r6 != 0) goto L_0x03d2
            r0.add(r5)
            goto L_0x03d2
        L_0x03f0:
            r7.zzg()
            r7.zzc(r0)
            java.lang.Integer r0 = java.lang.Integer.valueOf(r4)
            com.google.android.gms.internal.measurement.zzlb r3 = r7.zzaD()
            com.google.android.gms.internal.measurement.zzgi r3 = (com.google.android.gms.internal.measurement.zzgi) r3
            r1.put(r0, r3)
            r0 = r20
            r3 = r22
            r7 = 2
            goto L_0x031f
        L_0x040a:
            r20 = r0
            r22 = r3
            r7 = 2
            goto L_0x031f
        L_0x0411:
            r20 = r0
            r22 = r3
        L_0x0415:
            r1.put(r5, r6)
            r0 = r20
            r3 = r22
            r7 = 2
            goto L_0x031f
        L_0x041f:
        L_0x0420:
            r0 = r1
            goto L_0x042c
        L_0x0422:
            r0 = move-exception
        L_0x0423:
            if (r5 == 0) goto L_0x0428
            r5.close()
        L_0x0428:
            throw r0
        L_0x0429:
            r16 = r8
            r0 = r13
        L_0x042c:
            java.util.Iterator r20 = r2.iterator()
        L_0x0430:
            boolean r1 = r20.hasNext()
            if (r1 == 0) goto L_0x05d6
            java.lang.Object r1 = r20.next()
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r21 = r1.intValue()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r21)
            java.lang.Object r1 = r0.get(r1)
            com.google.android.gms.internal.measurement.zzgi r1 = (com.google.android.gms.internal.measurement.zzgi) r1
            java.util.BitSet r5 = new java.util.BitSet
            r5.<init>()
            java.util.BitSet r6 = new java.util.BitSet
            r6.<init>()
            androidx.collection.ArrayMap r7 = new androidx.collection.ArrayMap
            r7.<init>()
            if (r1 == 0) goto L_0x0498
            int r2 = r1.zza()
            if (r2 != 0) goto L_0x0462
            goto L_0x0498
        L_0x0462:
            java.util.List r2 = r1.zzh()
            java.util.Iterator r2 = r2.iterator()
        L_0x046a:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0498
            java.lang.Object r3 = r2.next()
            com.google.android.gms.internal.measurement.zzfr r3 = (com.google.android.gms.internal.measurement.zzfr) r3
            boolean r4 = r3.zzh()
            if (r4 == 0) goto L_0x046a
            int r4 = r3.zza()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            boolean r8 = r3.zzg()
            if (r8 == 0) goto L_0x0493
            long r22 = r3.zzb()
            java.lang.Long r3 = java.lang.Long.valueOf(r22)
            goto L_0x0494
        L_0x0493:
            r3 = 0
        L_0x0494:
            r7.put(r4, r3)
            goto L_0x046a
        L_0x0498:
            androidx.collection.ArrayMap r8 = new androidx.collection.ArrayMap
            r8.<init>()
            if (r1 == 0) goto L_0x04ec
            int r2 = r1.zzc()
            if (r2 != 0) goto L_0x04a8
            r23 = r0
            goto L_0x04ee
        L_0x04a8:
            java.util.List r2 = r1.zzj()
            java.util.Iterator r2 = r2.iterator()
        L_0x04b0:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x04e9
            java.lang.Object r3 = r2.next()
            com.google.android.gms.internal.measurement.zzgk r3 = (com.google.android.gms.internal.measurement.zzgk) r3
            boolean r4 = r3.zzi()
            if (r4 == 0) goto L_0x04e6
            int r4 = r3.zza()
            if (r4 <= 0) goto L_0x04e6
            int r4 = r3.zzb()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            int r22 = r3.zza()
            r23 = r0
            int r0 = r22 + -1
            long r24 = r3.zzc(r0)
            java.lang.Long r0 = java.lang.Long.valueOf(r24)
            r8.put(r4, r0)
            r0 = r23
            goto L_0x04b0
        L_0x04e6:
            r23 = r0
            goto L_0x04b0
        L_0x04e9:
            r23 = r0
            goto L_0x04ee
        L_0x04ec:
            r23 = r0
        L_0x04ee:
            if (r1 == 0) goto L_0x053e
            r0 = 0
        L_0x04f1:
            int r2 = r1.zzd()
            int r2 = r2 * 64
            if (r0 >= r2) goto L_0x053b
            java.util.List r2 = r1.zzk()
            boolean r2 = com.google.android.gms.measurement.internal.zzli.zzv(r2, r0)
            if (r2 == 0) goto L_0x052d
            com.google.android.gms.measurement.internal.zzge r2 = r10.zzt
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzj()
            java.lang.Integer r3 = java.lang.Integer.valueOf(r21)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r0)
            r22 = r9
            java.lang.String r9 = "Filter already evaluated. audience ID, filter ID"
            r2.zzc(r9, r3, r4)
            r6.set(r0)
            java.util.List r2 = r1.zzi()
            boolean r2 = com.google.android.gms.measurement.internal.zzli.zzv(r2, r0)
            if (r2 == 0) goto L_0x052f
            r5.set(r0)
            goto L_0x0536
        L_0x052d:
            r22 = r9
        L_0x052f:
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)
            r7.remove(r2)
        L_0x0536:
            int r0 = r0 + 1
            r9 = r22
            goto L_0x04f1
        L_0x053b:
            r22 = r9
            goto L_0x0540
        L_0x053e:
            r22 = r9
        L_0x0540:
            java.lang.Integer r0 = java.lang.Integer.valueOf(r21)
            java.lang.Object r1 = r13.get(r0)
            r4 = r1
            com.google.android.gms.internal.measurement.zzgi r4 = (com.google.android.gms.internal.measurement.zzgi) r4
            if (r15 == 0) goto L_0x05ad
            if (r14 == 0) goto L_0x05ad
            java.lang.Object r0 = r12.get(r0)
            java.util.List r0 = (java.util.List) r0
            if (r0 == 0) goto L_0x05ad
            java.lang.Long r1 = r10.zze
            if (r1 == 0) goto L_0x05ad
            java.lang.Long r1 = r10.zzd
            if (r1 != 0) goto L_0x0560
            goto L_0x05ad
        L_0x0560:
            java.util.Iterator r0 = r0.iterator()
        L_0x0564:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x05ad
            java.lang.Object r1 = r0.next()
            com.google.android.gms.internal.measurement.zzek r1 = (com.google.android.gms.internal.measurement.zzek) r1
            int r2 = r1.zzb()
            java.lang.Long r3 = r10.zze
            long r24 = r3.longValue()
            r26 = 1000(0x3e8, double:4.94E-321)
            long r24 = r24 / r26
            boolean r1 = r1.zzm()
            if (r1 == 0) goto L_0x058d
            java.lang.Long r1 = r10.zzd
            long r24 = r1.longValue()
            long r24 = r24 / r26
            goto L_0x058e
        L_0x058d:
        L_0x058e:
            java.lang.Integer r1 = java.lang.Integer.valueOf(r2)
            boolean r2 = r7.containsKey(r1)
            if (r2 == 0) goto L_0x059f
            java.lang.Long r2 = java.lang.Long.valueOf(r24)
            r7.put(r1, r2)
        L_0x059f:
            boolean r2 = r8.containsKey(r1)
            if (r2 == 0) goto L_0x0564
            java.lang.Long r2 = java.lang.Long.valueOf(r24)
            r8.put(r1, r2)
            goto L_0x0564
        L_0x05ad:
            com.google.android.gms.measurement.internal.zzu r0 = new com.google.android.gms.measurement.internal.zzu
            java.lang.String r3 = r10.zza
            r9 = 0
            r1 = r0
            r2 = r64
            r28 = r18
            r29 = r19
            r17 = r12
            r12 = r16
            r65 = r13
            r13 = r22
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
            java.util.Map r1 = r10.zzc
            java.lang.Integer r2 = java.lang.Integer.valueOf(r21)
            r1.put(r2, r0)
            r9 = r13
            r12 = r17
            r0 = r23
            r13 = r65
            goto L_0x0430
        L_0x05d6:
            r13 = r9
            r12 = r16
            r28 = r18
            r29 = r19
        L_0x05dd:
            boolean r0 = r66.isEmpty()
            java.lang.String r1 = "Skipping failed audience ID"
            if (r0 == 0) goto L_0x05e9
            r24 = r11
            goto L_0x0857
        L_0x05e9:
            com.google.android.gms.measurement.internal.zzw r2 = new com.google.android.gms.measurement.internal.zzw
            r3 = 0
            r2.<init>(r10, r3)
            androidx.collection.ArrayMap r4 = new androidx.collection.ArrayMap
            r4.<init>()
            java.util.Iterator r5 = r66.iterator()
        L_0x05f8:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L_0x0855
            java.lang.Object r0 = r5.next()
            com.google.android.gms.internal.measurement.zzft r0 = (com.google.android.gms.internal.measurement.zzft) r0
            java.lang.String r6 = r10.zza
            com.google.android.gms.internal.measurement.zzft r6 = r2.zza(r6, r0)
            if (r6 == 0) goto L_0x084c
            com.google.android.gms.measurement.internal.zzlg r7 = r10.zzf
            com.google.android.gms.measurement.internal.zzam r7 = r7.zzi()
            java.lang.String r8 = r10.zza
            java.lang.String r9 = r6.zzh()
            java.lang.String r14 = r0.zzh()
            com.google.android.gms.measurement.internal.zzas r14 = r7.zzn(r8, r14)
            if (r14 != 0) goto L_0x0661
            com.google.android.gms.measurement.internal.zzge r14 = r7.zzt
            com.google.android.gms.measurement.internal.zzeu r14 = r14.zzaA()
            com.google.android.gms.measurement.internal.zzes r14 = r14.zzk()
            java.lang.Object r15 = com.google.android.gms.measurement.internal.zzeu.zzn(r8)
            com.google.android.gms.measurement.internal.zzge r7 = r7.zzt
            com.google.android.gms.measurement.internal.zzep r7 = r7.zzj()
            java.lang.String r7 = r7.zzd(r9)
            java.lang.String r9 = "Event aggregate wasn't created during raw event logging. appId, event"
            r14.zzc(r9, r15, r7)
            com.google.android.gms.measurement.internal.zzas r7 = new com.google.android.gms.measurement.internal.zzas
            r30 = r7
            java.lang.String r32 = r0.zzh()
            r33 = 1
            r35 = 1
            r37 = 1
            long r39 = r0.zzd()
            r41 = 0
            r43 = 0
            r44 = 0
            r45 = 0
            r46 = 0
            r31 = r8
            r30.<init>(r31, r32, r33, r35, r37, r39, r41, r43, r44, r45, r46)
            goto L_0x0696
        L_0x0661:
            com.google.android.gms.measurement.internal.zzas r7 = new com.google.android.gms.measurement.internal.zzas
            r47 = r7
            java.lang.String r0 = r14.zza
            r48 = r0
            java.lang.String r0 = r14.zzb
            r49 = r0
            long r8 = r14.zzc
            r15 = 1
            long r50 = r8 + r15
            long r8 = r14.zzd
            long r52 = r8 + r15
            long r8 = r14.zze
            long r54 = r8 + r15
            long r8 = r14.zzf
            r56 = r8
            long r8 = r14.zzg
            r58 = r8
            java.lang.Long r0 = r14.zzh
            r60 = r0
            java.lang.Long r0 = r14.zzi
            r61 = r0
            java.lang.Long r0 = r14.zzj
            r62 = r0
            java.lang.Boolean r0 = r14.zzk
            r63 = r0
            r47.<init>(r48, r49, r50, r52, r54, r56, r58, r60, r61, r62, r63)
        L_0x0696:
            com.google.android.gms.measurement.internal.zzlg r0 = r10.zzf
            com.google.android.gms.measurement.internal.zzam r0 = r0.zzi()
            r0.zzE(r7)
            long r8 = r7.zzc
            java.lang.String r14 = r6.zzh()
            java.lang.Object r0 = r4.get(r14)
            java.util.Map r0 = (java.util.Map) r0
            if (r0 != 0) goto L_0x07ad
            com.google.android.gms.measurement.internal.zzlg r0 = r10.zzf
            com.google.android.gms.measurement.internal.zzam r15 = r0.zzi()
            java.lang.String r3 = r10.zza
            r15.zzW()
            r15.zzg()
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r3)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r14)
            r65 = r2
            androidx.collection.ArrayMap r2 = new androidx.collection.ArrayMap
            r2.<init>()
            android.database.sqlite.SQLiteDatabase r16 = r15.zzh()
            java.lang.String r17 = "event_filters"
            r66 = r5
            r24 = r11
            r11 = r28
            r5 = r29
            java.lang.String[] r18 = new java.lang.String[]{r11, r5}     // Catch:{ SQLiteException -> 0x0777, all -> 0x077f }
            java.lang.String r19 = "app_id=? AND event_name=?"
            r29 = r5
            r5 = 2
            java.lang.String[] r0 = new java.lang.String[r5]     // Catch:{ SQLiteException -> 0x0775, all -> 0x077f }
            r20 = 0
            r0[r20] = r3     // Catch:{ SQLiteException -> 0x0775, all -> 0x077f }
            r20 = 1
            r0[r20] = r14     // Catch:{ SQLiteException -> 0x0775, all -> 0x077f }
            r21 = 0
            r22 = 0
            r23 = 0
            r20 = r0
            android.database.Cursor r5 = r16.query(r17, r18, r19, r20, r21, r22, r23)     // Catch:{ SQLiteException -> 0x0775, all -> 0x077f }
            boolean r0 = r5.moveToFirst()     // Catch:{ SQLiteException -> 0x076f, all -> 0x076d }
            if (r0 == 0) goto L_0x075f
        L_0x06fb:
            r28 = r11
            r11 = 1
            byte[] r0 = r5.getBlob(r11)     // Catch:{ SQLiteException -> 0x075b, all -> 0x076d }
            com.google.android.gms.internal.measurement.zzej r11 = com.google.android.gms.internal.measurement.zzek.zzc()     // Catch:{ IOException -> 0x0737 }
            com.google.android.gms.internal.measurement.zzmh r0 = com.google.android.gms.measurement.internal.zzli.zzl(r11, r0)     // Catch:{ IOException -> 0x0737 }
            com.google.android.gms.internal.measurement.zzej r0 = (com.google.android.gms.internal.measurement.zzej) r0     // Catch:{ IOException -> 0x0737 }
            com.google.android.gms.internal.measurement.zzlb r0 = r0.zzaD()     // Catch:{ IOException -> 0x0737 }
            com.google.android.gms.internal.measurement.zzek r0 = (com.google.android.gms.internal.measurement.zzek) r0     // Catch:{ IOException -> 0x0737 }
            r11 = 0
            int r16 = r5.getInt(r11)     // Catch:{ SQLiteException -> 0x075b, all -> 0x076d }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r16)     // Catch:{ SQLiteException -> 0x075b, all -> 0x076d }
            java.lang.Object r16 = r2.get(r11)     // Catch:{ SQLiteException -> 0x075b, all -> 0x076d }
            java.util.List r16 = (java.util.List) r16     // Catch:{ SQLiteException -> 0x075b, all -> 0x076d }
            if (r16 != 0) goto L_0x072f
            r22 = r7
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x076b, all -> 0x076d }
            r7.<init>()     // Catch:{ SQLiteException -> 0x076b, all -> 0x076d }
            r2.put(r11, r7)     // Catch:{ SQLiteException -> 0x076b, all -> 0x076d }
            goto L_0x0733
        L_0x072f:
            r22 = r7
            r7 = r16
        L_0x0733:
            r7.add(r0)     // Catch:{ SQLiteException -> 0x076b, all -> 0x076d }
            goto L_0x074b
        L_0x0737:
            r0 = move-exception
            r22 = r7
            com.google.android.gms.measurement.internal.zzge r7 = r15.zzt     // Catch:{ SQLiteException -> 0x076b, all -> 0x076d }
            com.google.android.gms.measurement.internal.zzeu r7 = r7.zzaA()     // Catch:{ SQLiteException -> 0x076b, all -> 0x076d }
            com.google.android.gms.measurement.internal.zzes r7 = r7.zzd()     // Catch:{ SQLiteException -> 0x076b, all -> 0x076d }
            java.lang.Object r11 = com.google.android.gms.measurement.internal.zzeu.zzn(r3)     // Catch:{ SQLiteException -> 0x076b, all -> 0x076d }
            r7.zzc(r13, r11, r0)     // Catch:{ SQLiteException -> 0x076b, all -> 0x076d }
        L_0x074b:
            boolean r0 = r5.moveToNext()     // Catch:{ SQLiteException -> 0x076b, all -> 0x076d }
            if (r0 != 0) goto L_0x0756
            r5.close()
            r0 = r2
            goto L_0x07a2
        L_0x0756:
            r7 = r22
            r11 = r28
            goto L_0x06fb
        L_0x075b:
            r0 = move-exception
            r22 = r7
            goto L_0x078a
        L_0x075f:
            r22 = r7
            r28 = r11
            java.util.Map r0 = java.util.Collections.emptyMap()     // Catch:{ SQLiteException -> 0x076b, all -> 0x076d }
        L_0x0767:
            r5.close()
            goto L_0x07a2
        L_0x076b:
            r0 = move-exception
            goto L_0x078a
        L_0x076d:
            r0 = move-exception
            goto L_0x07a7
        L_0x076f:
            r0 = move-exception
            r22 = r7
            r28 = r11
            goto L_0x078a
        L_0x0775:
            r0 = move-exception
            goto L_0x077a
        L_0x0777:
            r0 = move-exception
            r29 = r5
        L_0x077a:
            r22 = r7
            r28 = r11
            goto L_0x0789
        L_0x077f:
            r0 = move-exception
            r5 = 0
            goto L_0x07a7
        L_0x0782:
            r0 = move-exception
            r66 = r5
            r22 = r7
            r24 = r11
        L_0x0789:
            r5 = 0
        L_0x078a:
            com.google.android.gms.measurement.internal.zzge r2 = r15.zzt     // Catch:{ all -> 0x07a6 }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ all -> 0x07a6 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x07a6 }
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzeu.zzn(r3)     // Catch:{ all -> 0x07a6 }
            r2.zzc(r12, r3, r0)     // Catch:{ all -> 0x07a6 }
            java.util.Map r0 = java.util.Collections.emptyMap()     // Catch:{ all -> 0x07a6 }
            if (r5 == 0) goto L_0x07a2
            goto L_0x0767
        L_0x07a2:
            r4.put(r14, r0)
            goto L_0x07b5
        L_0x07a6:
            r0 = move-exception
        L_0x07a7:
            if (r5 == 0) goto L_0x07ac
            r5.close()
        L_0x07ac:
            throw r0
        L_0x07ad:
            r65 = r2
            r66 = r5
            r22 = r7
            r24 = r11
        L_0x07b5:
            java.util.Set r2 = r0.keySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x07bd:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0843
            java.lang.Object r3 = r2.next()
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            java.util.Set r5 = r10.zzb
            java.lang.Integer r7 = java.lang.Integer.valueOf(r3)
            boolean r5 = r5.contains(r7)
            if (r5 == 0) goto L_0x07e7
            com.google.android.gms.measurement.internal.zzge r3 = r10.zzt
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzj()
            r3.zzb(r1, r7)
            goto L_0x07bd
        L_0x07e7:
            java.lang.Object r5 = r0.get(r7)
            java.util.List r5 = (java.util.List) r5
            java.util.Iterator r5 = r5.iterator()
            r7 = 1
        L_0x07f2:
            boolean r11 = r5.hasNext()
            if (r11 == 0) goto L_0x0836
            java.lang.Object r7 = r5.next()
            com.google.android.gms.internal.measurement.zzek r7 = (com.google.android.gms.internal.measurement.zzek) r7
            com.google.android.gms.measurement.internal.zzx r11 = new com.google.android.gms.measurement.internal.zzx
            java.lang.String r14 = r10.zza
            r11.<init>(r10, r14, r3, r7)
            java.lang.Long r15 = r10.zzd
            java.lang.Long r14 = r10.zze
            int r7 = r7.zzb()
            boolean r21 = r10.zzf(r3, r7)
            r7 = r14
            r14 = r11
            r16 = r7
            r17 = r6
            r18 = r8
            r20 = r22
            boolean r7 = r14.zzd(r15, r16, r17, r18, r20, r21)
            if (r7 == 0) goto L_0x082d
            java.lang.Integer r14 = java.lang.Integer.valueOf(r3)
            com.google.android.gms.measurement.internal.zzu r14 = r10.zzd(r14)
            r14.zzc(r11)
            goto L_0x07f2
        L_0x082d:
            java.util.Set r5 = r10.zzb
            java.lang.Integer r11 = java.lang.Integer.valueOf(r3)
            r5.add(r11)
        L_0x0836:
            if (r7 != 0) goto L_0x07bd
            java.util.Set r5 = r10.zzb
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r5.add(r3)
            goto L_0x07bd
        L_0x0843:
            r2 = r65
            r5 = r66
            r11 = r24
            r3 = 0
            goto L_0x05f8
        L_0x084c:
            r65 = r2
            r66 = r5
            r24 = r11
            r3 = 0
            goto L_0x05f8
        L_0x0855:
            r24 = r11
        L_0x0857:
            boolean r0 = r67.isEmpty()
            if (r0 == 0) goto L_0x0861
            r11 = r28
            goto L_0x0aa1
        L_0x0861:
            androidx.collection.ArrayMap r2 = new androidx.collection.ArrayMap
            r2.<init>()
            java.util.Iterator r3 = r67.iterator()
        L_0x086a:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x0a9f
            java.lang.Object r0 = r3.next()
            r4 = r0
            com.google.android.gms.internal.measurement.zzgm r4 = (com.google.android.gms.internal.measurement.zzgm) r4
            java.lang.String r5 = r4.zzf()
            java.lang.Object r0 = r2.get(r5)
            java.util.Map r0 = (java.util.Map) r0
            if (r0 != 0) goto L_0x0961
            com.google.android.gms.measurement.internal.zzlg r0 = r10.zzf
            com.google.android.gms.measurement.internal.zzam r6 = r0.zzi()
            java.lang.String r7 = r10.zza
            r6.zzW()
            r6.zzg()
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r7)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r5)
            androidx.collection.ArrayMap r8 = new androidx.collection.ArrayMap
            r8.<init>()
            android.database.sqlite.SQLiteDatabase r13 = r6.zzh()
            java.lang.String r14 = "property_filters"
            r11 = r28
            r9 = r29
            java.lang.String[] r15 = new java.lang.String[]{r11, r9}     // Catch:{ SQLiteException -> 0x092e, all -> 0x0932 }
            java.lang.String r16 = "app_id=? AND property_name=?"
            r65 = r3
            r3 = 2
            java.lang.String[] r0 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x092c, all -> 0x0932 }
            r3 = 0
            r0[r3] = r7     // Catch:{ SQLiteException -> 0x092c, all -> 0x0932 }
            r3 = 1
            r0[r3] = r5     // Catch:{ SQLiteException -> 0x092c, all -> 0x0932 }
            r18 = 0
            r19 = 0
            r20 = 0
            r17 = r0
            android.database.Cursor r3 = r13.query(r14, r15, r16, r17, r18, r19, r20)     // Catch:{ SQLiteException -> 0x092c, all -> 0x0932 }
            boolean r0 = r3.moveToFirst()     // Catch:{ SQLiteException -> 0x092a, all -> 0x0927 }
            if (r0 == 0) goto L_0x091f
        L_0x08c9:
            r13 = 1
            byte[] r0 = r3.getBlob(r13)     // Catch:{ SQLiteException -> 0x092a, all -> 0x0927 }
            com.google.android.gms.internal.measurement.zzes r14 = com.google.android.gms.internal.measurement.zzet.zzc()     // Catch:{ IOException -> 0x08ff }
            com.google.android.gms.internal.measurement.zzmh r0 = com.google.android.gms.measurement.internal.zzli.zzl(r14, r0)     // Catch:{ IOException -> 0x08ff }
            com.google.android.gms.internal.measurement.zzes r0 = (com.google.android.gms.internal.measurement.zzes) r0     // Catch:{ IOException -> 0x08ff }
            com.google.android.gms.internal.measurement.zzlb r0 = r0.zzaD()     // Catch:{ IOException -> 0x08ff }
            com.google.android.gms.internal.measurement.zzet r0 = (com.google.android.gms.internal.measurement.zzet) r0     // Catch:{ IOException -> 0x08ff }
            r14 = 0
            int r15 = r3.getInt(r14)     // Catch:{ SQLiteException -> 0x092a, all -> 0x0927 }
            java.lang.Integer r15 = java.lang.Integer.valueOf(r15)     // Catch:{ SQLiteException -> 0x092a, all -> 0x0927 }
            java.lang.Object r16 = r8.get(r15)     // Catch:{ SQLiteException -> 0x092a, all -> 0x0927 }
            java.util.List r16 = (java.util.List) r16     // Catch:{ SQLiteException -> 0x092a, all -> 0x0927 }
            if (r16 != 0) goto L_0x08f9
            java.util.ArrayList r13 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x092a, all -> 0x0927 }
            r13.<init>()     // Catch:{ SQLiteException -> 0x092a, all -> 0x0927 }
            r8.put(r15, r13)     // Catch:{ SQLiteException -> 0x092a, all -> 0x0927 }
            goto L_0x08fb
        L_0x08f9:
            r13 = r16
        L_0x08fb:
            r13.add(r0)     // Catch:{ SQLiteException -> 0x092a, all -> 0x0927 }
            goto L_0x0914
        L_0x08ff:
            r0 = move-exception
            r14 = 0
            com.google.android.gms.measurement.internal.zzge r13 = r6.zzt     // Catch:{ SQLiteException -> 0x092a, all -> 0x0927 }
            com.google.android.gms.measurement.internal.zzeu r13 = r13.zzaA()     // Catch:{ SQLiteException -> 0x092a, all -> 0x0927 }
            com.google.android.gms.measurement.internal.zzes r13 = r13.zzd()     // Catch:{ SQLiteException -> 0x092a, all -> 0x0927 }
            java.lang.String r15 = "Failed to merge filter"
            java.lang.Object r14 = com.google.android.gms.measurement.internal.zzeu.zzn(r7)     // Catch:{ SQLiteException -> 0x092a, all -> 0x0927 }
            r13.zzc(r15, r14, r0)     // Catch:{ SQLiteException -> 0x092a, all -> 0x0927 }
        L_0x0914:
            boolean r0 = r3.moveToNext()     // Catch:{ SQLiteException -> 0x092a, all -> 0x0927 }
            if (r0 != 0) goto L_0x08c9
            r3.close()
            r0 = r8
            goto L_0x0955
        L_0x091f:
            java.util.Map r0 = java.util.Collections.emptyMap()     // Catch:{ SQLiteException -> 0x092a, all -> 0x0927 }
        L_0x0923:
            r3.close()
            goto L_0x0955
        L_0x0927:
            r0 = move-exception
            r5 = r3
            goto L_0x095b
        L_0x092a:
            r0 = move-exception
            goto L_0x093d
        L_0x092c:
            r0 = move-exception
            goto L_0x093c
        L_0x092e:
            r0 = move-exception
            r65 = r3
            goto L_0x093c
        L_0x0932:
            r0 = move-exception
            r5 = 0
            goto L_0x095b
        L_0x0935:
            r0 = move-exception
            r65 = r3
            r11 = r28
            r9 = r29
        L_0x093c:
            r3 = 0
        L_0x093d:
            com.google.android.gms.measurement.internal.zzge r6 = r6.zzt     // Catch:{ all -> 0x0959 }
            com.google.android.gms.measurement.internal.zzeu r6 = r6.zzaA()     // Catch:{ all -> 0x0959 }
            com.google.android.gms.measurement.internal.zzes r6 = r6.zzd()     // Catch:{ all -> 0x0959 }
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzeu.zzn(r7)     // Catch:{ all -> 0x0959 }
            r6.zzc(r12, r7, r0)     // Catch:{ all -> 0x0959 }
            java.util.Map r0 = java.util.Collections.emptyMap()     // Catch:{ all -> 0x0959 }
            if (r3 == 0) goto L_0x0955
            goto L_0x0923
        L_0x0955:
            r2.put(r5, r0)
            goto L_0x0967
        L_0x0959:
            r0 = move-exception
            r5 = r3
        L_0x095b:
            if (r5 == 0) goto L_0x0960
            r5.close()
        L_0x0960:
            throw r0
        L_0x0961:
            r65 = r3
            r11 = r28
            r9 = r29
        L_0x0967:
            java.util.Set r3 = r0.keySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x096f:
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x0a97
            java.lang.Object r5 = r3.next()
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            java.util.Set r6 = r10.zzb
            java.lang.Integer r7 = java.lang.Integer.valueOf(r5)
            boolean r6 = r6.contains(r7)
            if (r6 == 0) goto L_0x09a0
            com.google.android.gms.measurement.internal.zzge r0 = r10.zzt
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzj()
            r0.zzb(r1, r7)
            r3 = r65
            r29 = r9
            r28 = r11
            goto L_0x086a
        L_0x09a0:
            java.lang.Object r6 = r0.get(r7)
            java.util.List r6 = (java.util.List) r6
            java.util.Iterator r6 = r6.iterator()
            r7 = 1
        L_0x09ab:
            boolean r8 = r6.hasNext()
            if (r8 == 0) goto L_0x0a82
            java.lang.Object r7 = r6.next()
            com.google.android.gms.internal.measurement.zzet r7 = (com.google.android.gms.internal.measurement.zzet) r7
            com.google.android.gms.measurement.internal.zzge r8 = r10.zzt
            com.google.android.gms.measurement.internal.zzeu r8 = r8.zzaA()
            java.lang.String r8 = r8.zzr()
            r13 = 2
            boolean r8 = android.util.Log.isLoggable(r8, r13)
            if (r8 == 0) goto L_0x0a15
            com.google.android.gms.measurement.internal.zzge r8 = r10.zzt
            com.google.android.gms.measurement.internal.zzeu r8 = r8.zzaA()
            com.google.android.gms.measurement.internal.zzes r8 = r8.zzj()
            java.lang.Integer r14 = java.lang.Integer.valueOf(r5)
            boolean r15 = r7.zzj()
            if (r15 == 0) goto L_0x09e5
            int r15 = r7.zza()
            java.lang.Integer r15 = java.lang.Integer.valueOf(r15)
            goto L_0x09e6
        L_0x09e5:
            r15 = 0
        L_0x09e6:
            com.google.android.gms.measurement.internal.zzge r13 = r10.zzt
            com.google.android.gms.measurement.internal.zzep r13 = r13.zzj()
            r66 = r0
            java.lang.String r0 = r7.zze()
            java.lang.String r0 = r13.zzf(r0)
            java.lang.String r13 = "Evaluating filter. audience, filter, property"
            r8.zzd(r13, r14, r15, r0)
            com.google.android.gms.measurement.internal.zzge r0 = r10.zzt
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzj()
            com.google.android.gms.measurement.internal.zzlg r8 = r10.zzf
            com.google.android.gms.measurement.internal.zzli r8 = r8.zzu()
            java.lang.String r8 = r8.zzp(r7)
            java.lang.String r13 = "Filter definition"
            r0.zzb(r13, r8)
            goto L_0x0a17
        L_0x0a15:
            r66 = r0
        L_0x0a17:
            boolean r0 = r7.zzj()
            if (r0 == 0) goto L_0x0a58
            int r0 = r7.zza()
            r8 = 256(0x100, float:3.59E-43)
            if (r0 <= r8) goto L_0x0a26
            goto L_0x0a58
        L_0x0a26:
            com.google.android.gms.measurement.internal.zzz r0 = new com.google.android.gms.measurement.internal.zzz
            java.lang.String r8 = r10.zza
            r0.<init>(r10, r8, r5, r7)
            java.lang.Long r8 = r10.zzd
            java.lang.Long r13 = r10.zze
            int r7 = r7.zza()
            boolean r7 = r10.zzf(r5, r7)
            boolean r7 = r0.zzd(r8, r13, r4, r7)
            if (r7 == 0) goto L_0x0a4e
            java.lang.Integer r8 = java.lang.Integer.valueOf(r5)
            com.google.android.gms.measurement.internal.zzu r8 = r10.zzd(r8)
            r8.zzc(r0)
            r0 = r66
            goto L_0x09ab
        L_0x0a4e:
            java.util.Set r0 = r10.zzb
            java.lang.Integer r6 = java.lang.Integer.valueOf(r5)
            r0.add(r6)
            goto L_0x0a84
        L_0x0a58:
            com.google.android.gms.measurement.internal.zzge r0 = r10.zzt
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzk()
            java.lang.String r6 = r10.zza
            java.lang.Object r6 = com.google.android.gms.measurement.internal.zzeu.zzn(r6)
            boolean r8 = r7.zzj()
            if (r8 == 0) goto L_0x0a77
            int r7 = r7.zza()
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            goto L_0x0a78
        L_0x0a77:
            r7 = 0
        L_0x0a78:
            java.lang.String r7 = java.lang.String.valueOf(r7)
            java.lang.String r8 = "Invalid property filter ID. appId, id"
            r0.zzc(r8, r6, r7)
            goto L_0x0a86
        L_0x0a82:
            r66 = r0
        L_0x0a84:
            if (r7 != 0) goto L_0x0a93
        L_0x0a86:
            java.util.Set r0 = r10.zzb
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r0.add(r5)
            r0 = r66
            goto L_0x096f
        L_0x0a93:
            r0 = r66
            goto L_0x096f
        L_0x0a97:
            r3 = r65
            r29 = r9
            r28 = r11
            goto L_0x086a
        L_0x0a9f:
            r11 = r28
        L_0x0aa1:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.util.Map r0 = r10.zzc
            java.util.Set r0 = r0.keySet()
            java.util.Set r2 = r10.zzb
            r0.removeAll(r2)
            java.util.Iterator r2 = r0.iterator()
        L_0x0ab5:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x0b4f
            java.lang.Object r0 = r2.next()
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            java.util.Map r3 = r10.zzc
            java.lang.Integer r4 = java.lang.Integer.valueOf(r0)
            java.lang.Object r3 = r3.get(r4)
            com.google.android.gms.measurement.internal.zzu r3 = (com.google.android.gms.measurement.internal.zzu) r3
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r3)
            com.google.android.gms.internal.measurement.zzfp r0 = r3.zza(r0)
            r1.add(r0)
            com.google.android.gms.measurement.internal.zzlg r3 = r10.zzf
            com.google.android.gms.measurement.internal.zzam r3 = r3.zzi()
            java.lang.String r5 = r10.zza
            com.google.android.gms.internal.measurement.zzgi r0 = r0.zzd()
            r3.zzW()
            r3.zzg()
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r5)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r0)
            byte[] r0 = r0.zzbx()
            android.content.ContentValues r6 = new android.content.ContentValues
            r6.<init>()
            java.lang.String r7 = "app_id"
            r6.put(r7, r5)
            r6.put(r11, r4)
            r4 = r24
            r6.put(r4, r0)
            android.database.sqlite.SQLiteDatabase r0 = r3.zzh()     // Catch:{ SQLiteException -> 0x0b36 }
            java.lang.String r7 = "audience_filter_values"
            r8 = 5
            r9 = 0
            long r6 = r0.insertWithOnConflict(r7, r9, r6, r8)     // Catch:{ SQLiteException -> 0x0b34 }
            r12 = -1
            int r0 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1))
            if (r0 != 0) goto L_0x0b31
            com.google.android.gms.measurement.internal.zzge r0 = r3.zzt     // Catch:{ SQLiteException -> 0x0b34 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ SQLiteException -> 0x0b34 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ SQLiteException -> 0x0b34 }
            java.lang.String r6 = "Failed to insert filter results (got -1). appId"
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzeu.zzn(r5)     // Catch:{ SQLiteException -> 0x0b34 }
            r0.zzb(r6, r7)     // Catch:{ SQLiteException -> 0x0b34 }
            r24 = r4
            goto L_0x0ab5
        L_0x0b31:
            r24 = r4
            goto L_0x0ab5
        L_0x0b34:
            r0 = move-exception
            goto L_0x0b38
        L_0x0b36:
            r0 = move-exception
            r9 = 0
        L_0x0b38:
            com.google.android.gms.measurement.internal.zzge r3 = r3.zzt
            com.google.android.gms.measurement.internal.zzeu r3 = r3.zzaA()
            com.google.android.gms.measurement.internal.zzes r3 = r3.zzd()
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzeu.zzn(r5)
            java.lang.String r6 = "Error storing filter results. appId"
            r3.zzc(r6, r5, r0)
            r24 = r4
            goto L_0x0ab5
        L_0x0b4f:
            return r1
        L_0x0b50:
            r0 = move-exception
            r5 = r4
        L_0x0b52:
            if (r5 == 0) goto L_0x0b57
            r5.close()
        L_0x0b57:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzaa.zza(java.lang.String, java.util.List, java.util.List, java.lang.Long, java.lang.Long):java.util.List");
    }

    /* access modifiers changed from: protected */
    public final boolean zzb() {
        return false;
    }
}
