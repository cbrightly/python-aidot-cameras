package com.google.android.gms.measurement.internal;

import android.database.sqlite.SQLiteDatabase;
import java.io.File;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzan {
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0022, code lost:
        if (r3 == false) goto L_0x003f;
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0075 A[Catch:{ all -> 0x00cc, SQLiteException -> 0x00d1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00a1 A[Catch:{ all -> 0x00cc, SQLiteException -> 0x00d1 }, LOOP:1: B:32:0x00a1->B:37:0x00b3, LOOP_START, PHI: r0 
      PHI: (r0v1 int) = (r0v0 int), (r0v2 int) binds: [B:31:0x009f, B:37:0x00b3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00bc A[Catch:{ all -> 0x00cc, SQLiteException -> 0x00d1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:60:? A[Catch:{  }, RETURN, SYNTHETIC] */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void zza(com.google.android.gms.measurement.internal.zzeu r10, android.database.sqlite.SQLiteDatabase r11, java.lang.String r12, java.lang.String r13, java.lang.String r14, java.lang.String[] r15) {
        /*
            if (r10 == 0) goto L_0x00e4
            r0 = 0
            r1 = 0
            java.lang.String r3 = "SQLITE_MASTER"
            java.lang.String r2 = "name"
            java.lang.String[] r4 = new java.lang.String[]{r2}     // Catch:{ SQLiteException -> 0x002e, all -> 0x002b }
            java.lang.String r5 = "name=?"
            r2 = 1
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x002e, all -> 0x002b }
            r6[r0] = r12     // Catch:{ SQLiteException -> 0x002e, all -> 0x002b }
            r7 = 0
            r8 = 0
            r9 = 0
            r2 = r11
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ SQLiteException -> 0x002e, all -> 0x002b }
            boolean r3 = r2.moveToFirst()     // Catch:{ SQLiteException -> 0x0029, all -> 0x0025 }
            r2.close()
            if (r3 != 0) goto L_0x0042
            goto L_0x003f
        L_0x0025:
            r10 = move-exception
            r1 = r2
            goto L_0x00de
        L_0x0029:
            r3 = move-exception
            goto L_0x0031
        L_0x002b:
            r10 = move-exception
            goto L_0x00de
        L_0x002e:
            r2 = move-exception
            r3 = r2
            r2 = r1
        L_0x0031:
            com.google.android.gms.measurement.internal.zzes r4 = r10.zzk()     // Catch:{ all -> 0x00dc }
            java.lang.String r5 = "Error querying for table"
            r4.zzc(r5, r12, r3)     // Catch:{ all -> 0x00dc }
            if (r2 == 0) goto L_0x003f
            r2.close()
        L_0x003f:
            r11.execSQL(r13)
        L_0x0042:
            java.util.HashSet r13 = new java.util.HashSet     // Catch:{ SQLiteException -> 0x00d1 }
            r13.<init>()     // Catch:{ SQLiteException -> 0x00d1 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x00d1 }
            r2.<init>()     // Catch:{ SQLiteException -> 0x00d1 }
            java.lang.String r3 = "SELECT * FROM "
            r2.append(r3)     // Catch:{ SQLiteException -> 0x00d1 }
            r2.append(r12)     // Catch:{ SQLiteException -> 0x00d1 }
            java.lang.String r3 = " LIMIT 0"
            r2.append(r3)     // Catch:{ SQLiteException -> 0x00d1 }
            java.lang.String r2 = r2.toString()     // Catch:{ SQLiteException -> 0x00d1 }
            android.database.Cursor r1 = r11.rawQuery(r2, r1)     // Catch:{ SQLiteException -> 0x00d1 }
            java.lang.String[] r2 = r1.getColumnNames()     // Catch:{ all -> 0x00cc }
            java.util.Collections.addAll(r13, r2)     // Catch:{ all -> 0x00cc }
            r1.close()     // Catch:{ SQLiteException -> 0x00d1 }
            java.lang.String r1 = ","
            java.lang.String[] r14 = r14.split(r1)     // Catch:{ SQLiteException -> 0x00d1 }
            int r1 = r14.length     // Catch:{ SQLiteException -> 0x00d1 }
            r2 = r0
        L_0x0073:
            if (r2 >= r1) goto L_0x009f
            r3 = r14[r2]     // Catch:{ SQLiteException -> 0x00d1 }
            boolean r4 = r13.remove(r3)     // Catch:{ SQLiteException -> 0x00d1 }
            if (r4 == 0) goto L_0x0080
            int r2 = r2 + 1
            goto L_0x0073
        L_0x0080:
            android.database.sqlite.SQLiteException r11 = new android.database.sqlite.SQLiteException     // Catch:{ SQLiteException -> 0x00d1 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x00d1 }
            r13.<init>()     // Catch:{ SQLiteException -> 0x00d1 }
            java.lang.String r14 = "Table "
            r13.append(r14)     // Catch:{ SQLiteException -> 0x00d1 }
            r13.append(r12)     // Catch:{ SQLiteException -> 0x00d1 }
            java.lang.String r14 = " is missing required column: "
            r13.append(r14)     // Catch:{ SQLiteException -> 0x00d1 }
            r13.append(r3)     // Catch:{ SQLiteException -> 0x00d1 }
            java.lang.String r13 = r13.toString()     // Catch:{ SQLiteException -> 0x00d1 }
            r11.<init>(r13)     // Catch:{ SQLiteException -> 0x00d1 }
            throw r11     // Catch:{ SQLiteException -> 0x00d1 }
        L_0x009f:
            if (r15 == 0) goto L_0x00b6
        L_0x00a1:
            int r14 = r15.length     // Catch:{ SQLiteException -> 0x00d1 }
            if (r0 >= r14) goto L_0x00b6
            r14 = r15[r0]     // Catch:{ SQLiteException -> 0x00d1 }
            boolean r14 = r13.remove(r14)     // Catch:{ SQLiteException -> 0x00d1 }
            if (r14 != 0) goto L_0x00b3
            int r14 = r0 + 1
            r14 = r15[r14]     // Catch:{ SQLiteException -> 0x00d1 }
            r11.execSQL(r14)     // Catch:{ SQLiteException -> 0x00d1 }
        L_0x00b3:
            int r0 = r0 + 2
            goto L_0x00a1
        L_0x00b6:
            boolean r11 = r13.isEmpty()     // Catch:{ SQLiteException -> 0x00d1 }
            if (r11 != 0) goto L_0x00cb
            com.google.android.gms.measurement.internal.zzes r11 = r10.zzk()     // Catch:{ SQLiteException -> 0x00d1 }
            java.lang.String r14 = "Table has extra columns. table, columns"
            java.lang.String r15 = ", "
            java.lang.String r13 = android.text.TextUtils.join(r15, r13)     // Catch:{ SQLiteException -> 0x00d1 }
            r11.zzc(r14, r12, r13)     // Catch:{ SQLiteException -> 0x00d1 }
        L_0x00cb:
            return
        L_0x00cc:
            r11 = move-exception
            r1.close()     // Catch:{ SQLiteException -> 0x00d1 }
            throw r11     // Catch:{ SQLiteException -> 0x00d1 }
        L_0x00d1:
            r11 = move-exception
            com.google.android.gms.measurement.internal.zzes r10 = r10.zzd()
            java.lang.String r13 = "Failed to verify columns on table that was just created"
            r10.zzb(r13, r12)
            throw r11
        L_0x00dc:
            r10 = move-exception
            r1 = r2
        L_0x00de:
            if (r1 == 0) goto L_0x00e3
            r1.close()
        L_0x00e3:
            throw r10
        L_0x00e4:
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            java.lang.String r11 = "Monitor must not be null"
            r10.<init>(r11)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzan.zza(com.google.android.gms.measurement.internal.zzeu, android.database.sqlite.SQLiteDatabase, java.lang.String, java.lang.String, java.lang.String, java.lang.String[]):void");
    }

    static void zzb(zzeu zzeu, SQLiteDatabase sQLiteDatabase) {
        if (zzeu != null) {
            File file = new File(sQLiteDatabase.getPath());
            if (!file.setReadable(false, false)) {
                zzeu.zzk().zza("Failed to turn off database read permission");
            }
            if (!file.setWritable(false, false)) {
                zzeu.zzk().zza("Failed to turn off database write permission");
            }
            if (!file.setReadable(true, true)) {
                zzeu.zzk().zza("Failed to turn on database read permission for owner");
            }
            if (!file.setWritable(true, true)) {
                zzeu.zzk().zza("Failed to turn on database write permission for owner");
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Monitor must not be null");
    }
}
