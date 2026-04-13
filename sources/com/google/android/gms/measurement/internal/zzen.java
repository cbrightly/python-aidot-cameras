package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Parcel;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.util.VisibleForTesting;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzen extends zzf {
    private final zzem zza;
    private boolean zzb;

    zzen(zzge zzge) {
        super(zzge);
        Context zzaw = this.zzt.zzaw();
        this.zzt.zzf();
        this.zza = new zzem(this, zzaw, "google_app_measurement_local.db");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v4, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v5, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v7, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v11, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v12, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r2v1, types: [int, boolean] */
    /* JADX WARNING: type inference failed for: r8v0 */
    /* JADX WARNING: type inference failed for: r8v1, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r8v2 */
    /* JADX WARNING: type inference failed for: r8v3 */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r8v6, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r8v8 */
    /* JADX WARNING: type inference failed for: r8v9 */
    /* JADX WARNING: type inference failed for: r8v10 */
    /* JADX WARNING: type inference failed for: r8v13 */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00ce A[SYNTHETIC, Splitter:B:48:0x00ce] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00eb  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0100  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x011a  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x012d  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0132  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0122 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0122 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0122 A[SYNTHETIC] */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean zzq(int r17, byte[] r18) {
        /*
            r16 = this;
            r1 = r16
            r16.zzg()
            boolean r0 = r1.zzb
            r2 = 0
            if (r0 == 0) goto L_0x000b
            return r2
        L_0x000b:
            android.content.ContentValues r3 = new android.content.ContentValues
            r3.<init>()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r17)
            java.lang.String r4 = "type"
            r3.put(r4, r0)
            java.lang.String r0 = "entry"
            r4 = r18
            r3.put(r0, r4)
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt
            r0.zzf()
            r4 = 5
            r5 = r2
            r6 = r4
        L_0x0029:
            if (r5 >= r4) goto L_0x0136
            r7 = 1
            r8 = 0
            android.database.sqlite.SQLiteDatabase r9 = r16.zzh()     // Catch:{ SQLiteFullException -> 0x0104, SQLiteDatabaseLockedException -> 0x00f1, SQLiteException -> 0x00ca, all -> 0x00c7 }
            if (r9 != 0) goto L_0x0036
            r1.zzb = r7     // Catch:{ SQLiteFullException -> 0x00c3, SQLiteDatabaseLockedException -> 0x00c1, SQLiteException -> 0x00bd }
            return r2
        L_0x0036:
            r9.beginTransaction()     // Catch:{ SQLiteFullException -> 0x00c3, SQLiteDatabaseLockedException -> 0x00c1, SQLiteException -> 0x00bd }
            java.lang.String r0 = "select count(1) from messages"
            android.database.Cursor r10 = r9.rawQuery(r0, r8)     // Catch:{ SQLiteFullException -> 0x00c3, SQLiteDatabaseLockedException -> 0x00c1, SQLiteException -> 0x00bd }
            r11 = 0
            if (r10 == 0) goto L_0x0058
            boolean r0 = r10.moveToFirst()     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            if (r0 == 0) goto L_0x0058
            long r11 = r10.getLong(r2)     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            goto L_0x0059
        L_0x004e:
            r0 = move-exception
            goto L_0x012a
        L_0x0051:
            r0 = move-exception
            goto L_0x00bf
        L_0x0053:
            r0 = move-exception
            goto L_0x00b8
        L_0x0055:
            r0 = move-exception
            goto L_0x00c5
        L_0x0058:
        L_0x0059:
            r13 = 100000(0x186a0, double:4.94066E-319)
            int r0 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            java.lang.String r15 = "messages"
            if (r0 < 0) goto L_0x00a4
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            java.lang.String r4 = "Data loss, local db full"
            r0.zza(r4)     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            long r13 = r13 - r11
            java.lang.String r0 = "rowid in (select rowid from messages order by rowid asc limit ?)"
            java.lang.String[] r4 = new java.lang.String[r7]     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            r11 = 1
            long r13 = r13 + r11
            java.lang.String r11 = java.lang.Long.toString(r13)     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            r4[r2] = r11     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            int r0 = r9.delete(r15, r0, r4)     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            long r11 = (long) r0     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            int r0 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r0 == 0) goto L_0x00a4
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            java.lang.String r4 = "Different delete count than expected in local db. expected, received, difference"
            java.lang.Long r2 = java.lang.Long.valueOf(r13)     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            java.lang.Long r7 = java.lang.Long.valueOf(r11)     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            long r13 = r13 - r11
            java.lang.Long r11 = java.lang.Long.valueOf(r13)     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            r0.zzd(r4, r2, r7, r11)     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
        L_0x00a4:
            r9.insertOrThrow(r15, r8, r3)     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            r9.setTransactionSuccessful()     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            r9.endTransaction()     // Catch:{ SQLiteFullException -> 0x0055, SQLiteDatabaseLockedException -> 0x0053, SQLiteException -> 0x0051, all -> 0x004e }
            if (r10 == 0) goto L_0x00b3
            r10.close()
        L_0x00b3:
            r9.close()
            r2 = 1
            return r2
        L_0x00b8:
            r8 = r10
            goto L_0x00f3
        L_0x00ba:
            r0 = move-exception
            goto L_0x012b
        L_0x00bd:
            r0 = move-exception
            r10 = r8
        L_0x00bf:
            r8 = r9
            goto L_0x00cc
        L_0x00c1:
            r0 = move-exception
            goto L_0x00f3
        L_0x00c3:
            r0 = move-exception
            r10 = r8
        L_0x00c5:
            r8 = r9
            goto L_0x0106
        L_0x00c7:
            r0 = move-exception
            r9 = r8
            goto L_0x012b
        L_0x00ca:
            r0 = move-exception
            r10 = r8
        L_0x00cc:
            if (r8 == 0) goto L_0x00d7
            boolean r2 = r8.inTransaction()     // Catch:{ all -> 0x0128 }
            if (r2 == 0) goto L_0x00d7
            r8.endTransaction()     // Catch:{ all -> 0x0128 }
        L_0x00d7:
            com.google.android.gms.measurement.internal.zzge r2 = r1.zzt     // Catch:{ all -> 0x0128 }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ all -> 0x0128 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x0128 }
            java.lang.String r4 = "Error writing entry to local database"
            r2.zzb(r4, r0)     // Catch:{ all -> 0x0128 }
            r2 = 1
            r1.zzb = r2     // Catch:{ all -> 0x0128 }
            if (r10 == 0) goto L_0x00ee
            r10.close()
        L_0x00ee:
            if (r8 == 0) goto L_0x0122
            goto L_0x011f
        L_0x00f1:
            r0 = move-exception
            r9 = r8
        L_0x00f3:
            long r10 = (long) r6
            android.os.SystemClock.sleep(r10)     // Catch:{ all -> 0x00ba }
            int r6 = r6 + 20
            if (r8 == 0) goto L_0x00fe
            r8.close()
        L_0x00fe:
            if (r9 == 0) goto L_0x0122
            r9.close()
            goto L_0x0122
        L_0x0104:
            r0 = move-exception
            r10 = r8
        L_0x0106:
            com.google.android.gms.measurement.internal.zzge r2 = r1.zzt     // Catch:{ all -> 0x0128 }
            com.google.android.gms.measurement.internal.zzeu r2 = r2.zzaA()     // Catch:{ all -> 0x0128 }
            com.google.android.gms.measurement.internal.zzes r2 = r2.zzd()     // Catch:{ all -> 0x0128 }
            java.lang.String r4 = "Error writing entry; local database full"
            r2.zzb(r4, r0)     // Catch:{ all -> 0x0128 }
            r2 = 1
            r1.zzb = r2     // Catch:{ all -> 0x0128 }
            if (r10 == 0) goto L_0x011d
            r10.close()
        L_0x011d:
            if (r8 == 0) goto L_0x0122
        L_0x011f:
            r8.close()
        L_0x0122:
            int r5 = r5 + 1
            r2 = 0
            r4 = 5
            goto L_0x0029
        L_0x0128:
            r0 = move-exception
            r9 = r8
        L_0x012a:
            r8 = r10
        L_0x012b:
            if (r8 == 0) goto L_0x0130
            r8.close()
        L_0x0130:
            if (r9 == 0) goto L_0x0135
            r9.close()
        L_0x0135:
            throw r0
        L_0x0136:
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzj()
            java.lang.String r2 = "Failed to write entry to local database"
            r0.zza(r2)
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzen.zzq(int, byte[]):boolean");
    }

    /* access modifiers changed from: protected */
    public final boolean zzf() {
        return false;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    @VisibleForTesting
    public final SQLiteDatabase zzh() {
        if (this.zzb) {
            return null;
        }
        SQLiteDatabase writableDatabase = this.zza.getWritableDatabase();
        if (writableDatabase != null) {
            return writableDatabase;
        }
        this.zzb = true;
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:132:0x01e7 A[SYNTHETIC, Splitter:B:132:0x01e7] */
    /* JADX WARNING: Removed duplicated region for block: B:156:0x020f A[SYNTHETIC, Splitter:B:156:0x020f] */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x0229  */
    /* JADX WARNING: Removed duplicated region for block: B:171:0x023a  */
    /* JADX WARNING: Removed duplicated region for block: B:179:0x0257  */
    /* JADX WARNING: Removed duplicated region for block: B:185:0x0265  */
    /* JADX WARNING: Removed duplicated region for block: B:187:0x026a  */
    /* JADX WARNING: Removed duplicated region for block: B:193:0x01ea A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:194:0x025d A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:195:0x025d A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:197:0x025d A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List zzi(int r23) {
        /*
            r22 = this;
            r1 = r22
            java.lang.String r2 = "rowid"
            java.lang.String r3 = "Error reading entries from local database"
            r22.zzg()
            boolean r0 = r1.zzb
            r4 = 0
            if (r0 == 0) goto L_0x000f
            return r4
        L_0x000f:
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            boolean r0 = r22.zzl()
            if (r0 == 0) goto L_0x027e
            r6 = 5
            r7 = 0
            r9 = r6
            r8 = r7
        L_0x001e:
            if (r8 >= r6) goto L_0x026e
            r10 = 1
            android.database.sqlite.SQLiteDatabase r15 = r22.zzh()     // Catch:{ SQLiteFullException -> 0x0243, SQLiteDatabaseLockedException -> 0x022f, SQLiteException -> 0x020a, all -> 0x0207 }
            if (r15 != 0) goto L_0x002a
            r1.zzb = r10     // Catch:{ SQLiteFullException -> 0x0202, SQLiteDatabaseLockedException -> 0x01fd, SQLiteException -> 0x01f8, all -> 0x01f3 }
            return r4
        L_0x002a:
            r15.beginTransaction()     // Catch:{ SQLiteFullException -> 0x0202, SQLiteDatabaseLockedException -> 0x01fd, SQLiteException -> 0x01f8, all -> 0x01f3 }
            java.lang.String r0 = "3"
            java.lang.String r12 = "messages"
            java.lang.String[] r13 = new java.lang.String[]{r2}     // Catch:{ all -> 0x01e2 }
            java.lang.String r14 = "type=?"
            java.lang.String[] r0 = new java.lang.String[]{r0}     // Catch:{ all -> 0x01e2 }
            r16 = 0
            r17 = 0
            java.lang.String r18 = "rowid desc"
            java.lang.String r19 = "1"
            r11 = r15
            r23 = r15
            r15 = r0
            android.database.Cursor r11 = r11.query(r12, r13, r14, r15, r16, r17, r18, r19)     // Catch:{ all -> 0x01de }
            boolean r0 = r11.moveToFirst()     // Catch:{ all -> 0x01da }
            r20 = -1
            if (r0 == 0) goto L_0x005c
            long r12 = r11.getLong(r7)     // Catch:{ all -> 0x01da }
            r11.close()     // Catch:{ SQLiteFullException -> 0x01d6, SQLiteDatabaseLockedException -> 0x01d2, SQLiteException -> 0x01ce, all -> 0x01ca }
            goto L_0x0062
        L_0x005c:
            r11.close()     // Catch:{ SQLiteFullException -> 0x01d6, SQLiteDatabaseLockedException -> 0x01d2, SQLiteException -> 0x01ce, all -> 0x01ca }
            r12 = r20
        L_0x0062:
            int r0 = (r12 > r20 ? 1 : (r12 == r20 ? 0 : -1))
            if (r0 == 0) goto L_0x0073
            java.lang.String r0 = "rowid<?"
            java.lang.String[] r11 = new java.lang.String[r10]     // Catch:{ SQLiteFullException -> 0x01d6, SQLiteDatabaseLockedException -> 0x01d2, SQLiteException -> 0x01ce, all -> 0x01ca }
            java.lang.String r12 = java.lang.String.valueOf(r12)     // Catch:{ SQLiteFullException -> 0x01d6, SQLiteDatabaseLockedException -> 0x01d2, SQLiteException -> 0x01ce, all -> 0x01ca }
            r11[r7] = r12     // Catch:{ SQLiteFullException -> 0x01d6, SQLiteDatabaseLockedException -> 0x01d2, SQLiteException -> 0x01ce, all -> 0x01ca }
            r14 = r0
            r15 = r11
            goto L_0x0075
        L_0x0073:
            r14 = r4
            r15 = r14
        L_0x0075:
            java.lang.String r12 = "messages"
            java.lang.String r0 = "type"
            java.lang.String r11 = "entry"
            java.lang.String[] r13 = new java.lang.String[]{r2, r0, r11}     // Catch:{ SQLiteFullException -> 0x01d6, SQLiteDatabaseLockedException -> 0x01d2, SQLiteException -> 0x01ce, all -> 0x01ca }
            r16 = 0
            r17 = 0
            java.lang.String r18 = "rowid asc"
            r0 = 100
            java.lang.String r19 = java.lang.Integer.toString(r0)     // Catch:{ SQLiteFullException -> 0x01d6, SQLiteDatabaseLockedException -> 0x01d2, SQLiteException -> 0x01ce, all -> 0x01ca }
            r11 = r23
            android.database.Cursor r11 = r11.query(r12, r13, r14, r15, r16, r17, r18, r19)     // Catch:{ SQLiteFullException -> 0x01d6, SQLiteDatabaseLockedException -> 0x01d2, SQLiteException -> 0x01ce, all -> 0x01ca }
        L_0x0092:
            boolean r0 = r11.moveToNext()     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            if (r0 == 0) goto L_0x0175
            long r20 = r11.getLong(r7)     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            int r0 = r11.getInt(r10)     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            r12 = 2
            byte[] r13 = r11.getBlob(r12)     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            if (r0 != 0) goto L_0x00dd
            android.os.Parcel r12 = android.os.Parcel.obtain()     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            int r0 = r13.length     // Catch:{ ParseException -> 0x00c5 }
            r12.unmarshall(r13, r7, r0)     // Catch:{ ParseException -> 0x00c5 }
            r12.setDataPosition(r7)     // Catch:{ ParseException -> 0x00c5 }
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzaw> r0 = com.google.android.gms.measurement.internal.zzaw.CREATOR     // Catch:{ ParseException -> 0x00c5 }
            java.lang.Object r0 = r0.createFromParcel(r12)     // Catch:{ ParseException -> 0x00c5 }
            com.google.android.gms.measurement.internal.zzaw r0 = (com.google.android.gms.measurement.internal.zzaw) r0     // Catch:{ ParseException -> 0x00c5 }
            r12.recycle()     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            if (r0 == 0) goto L_0x00c2
            r5.add(r0)     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
        L_0x00c2:
            goto L_0x0092
        L_0x00c3:
            r0 = move-exception
            goto L_0x00d9
        L_0x00c5:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ all -> 0x00c3 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ all -> 0x00c3 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ all -> 0x00c3 }
            java.lang.String r13 = "Failed to load event from local database"
            r0.zza(r13)     // Catch:{ all -> 0x00c3 }
            r12.recycle()     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            goto L_0x0092
        L_0x00d9:
            r12.recycle()     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            throw r0     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
        L_0x00dd:
            if (r0 != r10) goto L_0x0116
            android.os.Parcel r12 = android.os.Parcel.obtain()     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            int r0 = r13.length     // Catch:{ ParseException -> 0x00f8 }
            r12.unmarshall(r13, r7, r0)     // Catch:{ ParseException -> 0x00f8 }
            r12.setDataPosition(r7)     // Catch:{ ParseException -> 0x00f8 }
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzlj> r0 = com.google.android.gms.measurement.internal.zzlj.CREATOR     // Catch:{ ParseException -> 0x00f8 }
            java.lang.Object r0 = r0.createFromParcel(r12)     // Catch:{ ParseException -> 0x00f8 }
            com.google.android.gms.measurement.internal.zzlj r0 = (com.google.android.gms.measurement.internal.zzlj) r0     // Catch:{ ParseException -> 0x00f8 }
            r12.recycle()     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            goto L_0x010c
        L_0x00f6:
            r0 = move-exception
            goto L_0x0112
        L_0x00f8:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ all -> 0x00f6 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ all -> 0x00f6 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ all -> 0x00f6 }
            java.lang.String r13 = "Failed to load user property from local database"
            r0.zza(r13)     // Catch:{ all -> 0x00f6 }
            r12.recycle()     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            r0 = r4
        L_0x010c:
            if (r0 == 0) goto L_0x0111
            r5.add(r0)     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
        L_0x0111:
            goto L_0x0092
        L_0x0112:
            r12.recycle()     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            throw r0     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
        L_0x0116:
            if (r0 != r12) goto L_0x0150
            android.os.Parcel r12 = android.os.Parcel.obtain()     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            int r0 = r13.length     // Catch:{ ParseException -> 0x0131 }
            r12.unmarshall(r13, r7, r0)     // Catch:{ ParseException -> 0x0131 }
            r12.setDataPosition(r7)     // Catch:{ ParseException -> 0x0131 }
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzac> r0 = com.google.android.gms.measurement.internal.zzac.CREATOR     // Catch:{ ParseException -> 0x0131 }
            java.lang.Object r0 = r0.createFromParcel(r12)     // Catch:{ ParseException -> 0x0131 }
            com.google.android.gms.measurement.internal.zzac r0 = (com.google.android.gms.measurement.internal.zzac) r0     // Catch:{ ParseException -> 0x0131 }
            r12.recycle()     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            goto L_0x0145
        L_0x012f:
            r0 = move-exception
            goto L_0x014c
        L_0x0131:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ all -> 0x012f }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ all -> 0x012f }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ all -> 0x012f }
            java.lang.String r13 = "Failed to load conditional user property from local database"
            r0.zza(r13)     // Catch:{ all -> 0x012f }
            r12.recycle()     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            r0 = r4
        L_0x0145:
            if (r0 == 0) goto L_0x014a
            r5.add(r0)     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
        L_0x014a:
            goto L_0x0092
        L_0x014c:
            r12.recycle()     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            throw r0     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
        L_0x0150:
            r12 = 3
            if (r0 != r12) goto L_0x0164
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzk()     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            java.lang.String r12 = "Skipping app launch break"
            r0.zza(r12)     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            goto L_0x0092
        L_0x0164:
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            java.lang.String r12 = "Unknown record type in local database"
            r0.zza(r12)     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            goto L_0x0092
        L_0x0175:
            java.lang.String r0 = "messages"
            java.lang.String r12 = "rowid <= ?"
            java.lang.String[] r13 = new java.lang.String[r10]     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            java.lang.String r14 = java.lang.Long.toString(r20)     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            r13[r7] = r14     // Catch:{ SQLiteFullException -> 0x01c4, SQLiteDatabaseLockedException -> 0x01be, SQLiteException -> 0x01b8, all -> 0x01b1 }
            r14 = r23
            int r0 = r14.delete(r0, r12, r13)     // Catch:{ SQLiteFullException -> 0x01af, SQLiteDatabaseLockedException -> 0x01ad, SQLiteException -> 0x01ab, all -> 0x01a9 }
            int r12 = r5.size()     // Catch:{ SQLiteFullException -> 0x01af, SQLiteDatabaseLockedException -> 0x01ad, SQLiteException -> 0x01ab, all -> 0x01a9 }
            if (r0 >= r12) goto L_0x019c
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt     // Catch:{ SQLiteFullException -> 0x01af, SQLiteDatabaseLockedException -> 0x01ad, SQLiteException -> 0x01ab, all -> 0x01a9 }
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()     // Catch:{ SQLiteFullException -> 0x01af, SQLiteDatabaseLockedException -> 0x01ad, SQLiteException -> 0x01ab, all -> 0x01a9 }
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzd()     // Catch:{ SQLiteFullException -> 0x01af, SQLiteDatabaseLockedException -> 0x01ad, SQLiteException -> 0x01ab, all -> 0x01a9 }
            java.lang.String r12 = "Fewer entries removed from local database than expected"
            r0.zza(r12)     // Catch:{ SQLiteFullException -> 0x01af, SQLiteDatabaseLockedException -> 0x01ad, SQLiteException -> 0x01ab, all -> 0x01a9 }
        L_0x019c:
            r14.setTransactionSuccessful()     // Catch:{ SQLiteFullException -> 0x01af, SQLiteDatabaseLockedException -> 0x01ad, SQLiteException -> 0x01ab, all -> 0x01a9 }
            r14.endTransaction()     // Catch:{ SQLiteFullException -> 0x01af, SQLiteDatabaseLockedException -> 0x01ad, SQLiteException -> 0x01ab, all -> 0x01a9 }
            r11.close()
            r14.close()
            return r5
        L_0x01a9:
            r0 = move-exception
            goto L_0x01b4
        L_0x01ab:
            r0 = move-exception
            goto L_0x01bb
        L_0x01ad:
            r0 = move-exception
            goto L_0x01c1
        L_0x01af:
            r0 = move-exception
            goto L_0x01c7
        L_0x01b1:
            r0 = move-exception
            r14 = r23
        L_0x01b4:
            r4 = r11
            r15 = r14
            goto L_0x0263
        L_0x01b8:
            r0 = move-exception
            r14 = r23
        L_0x01bb:
            r15 = r14
            goto L_0x020d
        L_0x01be:
            r0 = move-exception
            r14 = r23
        L_0x01c1:
            r15 = r14
            goto L_0x0232
        L_0x01c4:
            r0 = move-exception
            r14 = r23
        L_0x01c7:
            r15 = r14
            goto L_0x0246
        L_0x01ca:
            r0 = move-exception
            r14 = r23
            goto L_0x01f5
        L_0x01ce:
            r0 = move-exception
            r14 = r23
            goto L_0x01fa
        L_0x01d2:
            r0 = move-exception
            r14 = r23
            goto L_0x01ff
        L_0x01d6:
            r0 = move-exception
            r14 = r23
            goto L_0x0204
        L_0x01da:
            r0 = move-exception
            r14 = r23
            goto L_0x01e5
        L_0x01de:
            r0 = move-exception
            r14 = r23
            goto L_0x01e4
        L_0x01e2:
            r0 = move-exception
            r14 = r15
        L_0x01e4:
            r11 = r4
        L_0x01e5:
            if (r11 == 0) goto L_0x01ea
            r11.close()     // Catch:{ SQLiteFullException -> 0x01f1, SQLiteDatabaseLockedException -> 0x01ef, SQLiteException -> 0x01ed, all -> 0x01eb }
        L_0x01ea:
            throw r0     // Catch:{ SQLiteFullException -> 0x01f1, SQLiteDatabaseLockedException -> 0x01ef, SQLiteException -> 0x01ed, all -> 0x01eb }
        L_0x01eb:
            r0 = move-exception
            goto L_0x01f5
        L_0x01ed:
            r0 = move-exception
            goto L_0x01fa
        L_0x01ef:
            r0 = move-exception
            goto L_0x01ff
        L_0x01f1:
            r0 = move-exception
            goto L_0x0204
        L_0x01f3:
            r0 = move-exception
            r14 = r15
        L_0x01f5:
            r15 = r14
            goto L_0x0263
        L_0x01f8:
            r0 = move-exception
            r14 = r15
        L_0x01fa:
            r11 = r4
            r15 = r14
            goto L_0x020d
        L_0x01fd:
            r0 = move-exception
            r14 = r15
        L_0x01ff:
            r11 = r4
            r15 = r14
            goto L_0x0232
        L_0x0202:
            r0 = move-exception
            r14 = r15
        L_0x0204:
            r11 = r4
            r15 = r14
            goto L_0x0246
        L_0x0207:
            r0 = move-exception
            r15 = r4
            goto L_0x0263
        L_0x020a:
            r0 = move-exception
            r11 = r4
            r15 = r11
        L_0x020d:
            if (r15 == 0) goto L_0x0218
            boolean r12 = r15.inTransaction()     // Catch:{ all -> 0x0261 }
            if (r12 == 0) goto L_0x0218
            r15.endTransaction()     // Catch:{ all -> 0x0261 }
        L_0x0218:
            com.google.android.gms.measurement.internal.zzge r12 = r1.zzt     // Catch:{ all -> 0x0261 }
            com.google.android.gms.measurement.internal.zzeu r12 = r12.zzaA()     // Catch:{ all -> 0x0261 }
            com.google.android.gms.measurement.internal.zzes r12 = r12.zzd()     // Catch:{ all -> 0x0261 }
            r12.zzb(r3, r0)     // Catch:{ all -> 0x0261 }
            r1.zzb = r10     // Catch:{ all -> 0x0261 }
            if (r11 == 0) goto L_0x022c
            r11.close()
        L_0x022c:
            if (r15 == 0) goto L_0x025d
            goto L_0x023f
        L_0x022f:
            r0 = move-exception
            r11 = r4
            r15 = r11
        L_0x0232:
            long r12 = (long) r9
            android.os.SystemClock.sleep(r12)     // Catch:{ all -> 0x0261 }
            int r9 = r9 + 20
            if (r11 == 0) goto L_0x023d
            r11.close()
        L_0x023d:
            if (r15 == 0) goto L_0x025d
        L_0x023f:
            r15.close()
            goto L_0x025d
        L_0x0243:
            r0 = move-exception
            r11 = r4
            r15 = r11
        L_0x0246:
            com.google.android.gms.measurement.internal.zzge r12 = r1.zzt     // Catch:{ all -> 0x0261 }
            com.google.android.gms.measurement.internal.zzeu r12 = r12.zzaA()     // Catch:{ all -> 0x0261 }
            com.google.android.gms.measurement.internal.zzes r12 = r12.zzd()     // Catch:{ all -> 0x0261 }
            r12.zzb(r3, r0)     // Catch:{ all -> 0x0261 }
            r1.zzb = r10     // Catch:{ all -> 0x0261 }
            if (r11 == 0) goto L_0x025a
            r11.close()
        L_0x025a:
            if (r15 == 0) goto L_0x025d
            goto L_0x023f
        L_0x025d:
            int r8 = r8 + 1
            goto L_0x001e
        L_0x0261:
            r0 = move-exception
            r4 = r11
        L_0x0263:
            if (r4 == 0) goto L_0x0268
            r4.close()
        L_0x0268:
            if (r15 == 0) goto L_0x026d
            r15.close()
        L_0x026d:
            throw r0
        L_0x026e:
            com.google.android.gms.measurement.internal.zzge r0 = r1.zzt
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzk()
            java.lang.String r2 = "Failed to read events from database in reasonable time"
            r0.zza(r2)
            return r4
        L_0x027e:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzen.zzi(int):java.util.List");
    }

    @WorkerThread
    public final void zzj() {
        int delete;
        zzg();
        try {
            SQLiteDatabase zzh = zzh();
            if (zzh != null && (delete = zzh.delete("messages", (String) null, (String[]) null)) > 0) {
                this.zzt.zzaA().zzj().zzb("Reset local analytics data. records", Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            this.zzt.zzaA().zzd().zzb("Error resetting local analytics data. error", e);
        }
    }

    @WorkerThread
    public final boolean zzk() {
        return zzq(3, new byte[0]);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final boolean zzl() {
        Context zzaw = this.zzt.zzaw();
        this.zzt.zzf();
        return zzaw.getDatabasePath("google_app_measurement_local.db").exists();
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x004c A[SYNTHETIC, Splitter:B:24:0x004c] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0066 A[Catch:{ SQLiteFullException -> 0x0074, SQLiteDatabaseLockedException -> 0x0067, SQLiteException -> 0x0049, all -> 0x0047, all -> 0x003f }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0087 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0087 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0087 A[SYNTHETIC] */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zzm() {
        /*
            r11 = this;
            java.lang.String r0 = "Error deleting app launch break from local database"
            r11.zzg()
            boolean r1 = r11.zzb
            r2 = 0
            if (r1 == 0) goto L_0x000b
            return r2
        L_0x000b:
            boolean r1 = r11.zzl()
            if (r1 == 0) goto L_0x009f
            r1 = 5
            r4 = r1
            r3 = r2
        L_0x0014:
            if (r3 >= r1) goto L_0x0090
            r5 = 0
            r6 = 1
            android.database.sqlite.SQLiteDatabase r5 = r11.zzh()     // Catch:{ SQLiteFullException -> 0x0074, SQLiteDatabaseLockedException -> 0x0067, SQLiteException -> 0x0049, all -> 0x0047 }
            if (r5 != 0) goto L_0x0021
            r11.zzb = r6     // Catch:{ SQLiteFullException -> 0x0045, SQLiteDatabaseLockedException -> 0x0043, SQLiteException -> 0x0041 }
            return r2
        L_0x0021:
            r5.beginTransaction()     // Catch:{ SQLiteFullException -> 0x0045, SQLiteDatabaseLockedException -> 0x0043, SQLiteException -> 0x0041 }
            java.lang.String r7 = "messages"
            java.lang.String r8 = "type == ?"
            java.lang.String[] r9 = new java.lang.String[r6]     // Catch:{ SQLiteFullException -> 0x0045, SQLiteDatabaseLockedException -> 0x0043, SQLiteException -> 0x0041 }
            r10 = 3
            java.lang.String r10 = java.lang.Integer.toString(r10)     // Catch:{ SQLiteFullException -> 0x0045, SQLiteDatabaseLockedException -> 0x0043, SQLiteException -> 0x0041 }
            r9[r2] = r10     // Catch:{ SQLiteFullException -> 0x0045, SQLiteDatabaseLockedException -> 0x0043, SQLiteException -> 0x0041 }
            r5.delete(r7, r8, r9)     // Catch:{ SQLiteFullException -> 0x0045, SQLiteDatabaseLockedException -> 0x0043, SQLiteException -> 0x0041 }
            r5.setTransactionSuccessful()     // Catch:{ SQLiteFullException -> 0x0045, SQLiteDatabaseLockedException -> 0x0043, SQLiteException -> 0x0041 }
            r5.endTransaction()     // Catch:{ SQLiteFullException -> 0x0045, SQLiteDatabaseLockedException -> 0x0043, SQLiteException -> 0x0041 }
            r5.close()
            return r6
        L_0x003f:
            r0 = move-exception
            goto L_0x008a
        L_0x0041:
            r7 = move-exception
            goto L_0x004a
        L_0x0043:
            r6 = move-exception
            goto L_0x0068
        L_0x0045:
            r7 = move-exception
            goto L_0x0075
        L_0x0047:
            r0 = move-exception
            goto L_0x008a
        L_0x0049:
            r7 = move-exception
        L_0x004a:
            if (r5 == 0) goto L_0x0055
            boolean r8 = r5.inTransaction()     // Catch:{ all -> 0x003f }
            if (r8 == 0) goto L_0x0055
            r5.endTransaction()     // Catch:{ all -> 0x003f }
        L_0x0055:
            com.google.android.gms.measurement.internal.zzge r8 = r11.zzt     // Catch:{ all -> 0x003f }
            com.google.android.gms.measurement.internal.zzeu r8 = r8.zzaA()     // Catch:{ all -> 0x003f }
            com.google.android.gms.measurement.internal.zzes r8 = r8.zzd()     // Catch:{ all -> 0x003f }
            r8.zzb(r0, r7)     // Catch:{ all -> 0x003f }
            r11.zzb = r6     // Catch:{ all -> 0x003f }
            if (r5 == 0) goto L_0x0087
            goto L_0x0070
        L_0x0067:
            r6 = move-exception
        L_0x0068:
            long r6 = (long) r4     // Catch:{ all -> 0x003f }
            android.os.SystemClock.sleep(r6)     // Catch:{ all -> 0x003f }
            int r4 = r4 + 20
            if (r5 == 0) goto L_0x0087
        L_0x0070:
            r5.close()
            goto L_0x0087
        L_0x0074:
            r7 = move-exception
        L_0x0075:
            com.google.android.gms.measurement.internal.zzge r8 = r11.zzt     // Catch:{ all -> 0x003f }
            com.google.android.gms.measurement.internal.zzeu r8 = r8.zzaA()     // Catch:{ all -> 0x003f }
            com.google.android.gms.measurement.internal.zzes r8 = r8.zzd()     // Catch:{ all -> 0x003f }
            r8.zzb(r0, r7)     // Catch:{ all -> 0x003f }
            r11.zzb = r6     // Catch:{ all -> 0x003f }
            if (r5 == 0) goto L_0x0087
            goto L_0x0070
        L_0x0087:
            int r3 = r3 + 1
            goto L_0x0014
        L_0x008a:
            if (r5 == 0) goto L_0x008f
            r5.close()
        L_0x008f:
            throw r0
        L_0x0090:
            com.google.android.gms.measurement.internal.zzge r0 = r11.zzt
            com.google.android.gms.measurement.internal.zzeu r0 = r0.zzaA()
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzk()
            java.lang.String r1 = "Error deleting app launch break from local database in reasonable time"
            r0.zza(r1)
        L_0x009f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzen.zzm():boolean");
    }

    public final boolean zzn(zzac zzac) {
        byte[] zzap = this.zzt.zzv().zzap(zzac);
        if (zzap.length <= 131072) {
            return zzq(2, zzap);
        }
        this.zzt.zzaA().zzh().zza("Conditional user property too long for local database. Sending directly to service");
        return false;
    }

    public final boolean zzo(zzaw zzaw) {
        Parcel obtain = Parcel.obtain();
        zzax.zza(zzaw, obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        if (marshall.length <= 131072) {
            return zzq(0, marshall);
        }
        this.zzt.zzaA().zzh().zza("Event is too long for local database. Sending event directly to service");
        return false;
    }

    public final boolean zzp(zzlj zzlj) {
        Parcel obtain = Parcel.obtain();
        zzlk.zza(zzlj, obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        if (marshall.length <= 131072) {
            return zzq(1, marshall);
        }
        this.zzt.zzaA().zzh().zza("User property too long for local database. Sending directly to service");
        return false;
    }
}
