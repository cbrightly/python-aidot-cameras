package com.didichuxing.doraemonkit.okgo.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.didichuxing.doraemonkit.okgo.utils.OkLogger;

public class DBUtils {
    public static boolean isNeedUpgradeTable(SQLiteDatabase db, TableEntity table) {
        if (!isTableExists(db, table.tableName)) {
            return true;
        }
        Cursor cursor = db.rawQuery("select * from " + table.tableName, (String[]) null);
        if (cursor == null) {
            return false;
        }
        try {
            int columnCount = table.getColumnCount();
            if (columnCount == cursor.getColumnCount()) {
                for (int i = 0; i < columnCount; i++) {
                    if (table.getColumnIndex(cursor.getColumnName(i)) == -1) {
                        return true;
                    }
                }
                cursor.close();
                return false;
            }
            cursor.close();
            return true;
        } finally {
            cursor.close();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003b, code lost:
        if (r1 == null) goto L_0x003e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isTableExists(android.database.sqlite.SQLiteDatabase r7, java.lang.String r8) {
        /*
            r0 = 0
            if (r8 == 0) goto L_0x0048
            if (r7 == 0) goto L_0x0048
            boolean r1 = r7.isOpen()
            if (r1 != 0) goto L_0x000c
            goto L_0x0048
        L_0x000c:
            r1 = 0
            r2 = 0
            r3 = 1
            java.lang.String r4 = "SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?"
            r5 = 2
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch:{ Exception -> 0x0037 }
            java.lang.String r6 = "table"
            r5[r0] = r6     // Catch:{ Exception -> 0x0037 }
            r5[r3] = r8     // Catch:{ Exception -> 0x0037 }
            android.database.Cursor r4 = r7.rawQuery(r4, r5)     // Catch:{ Exception -> 0x0037 }
            r1 = r4
            boolean r4 = r1.moveToFirst()     // Catch:{ Exception -> 0x0037 }
            if (r4 != 0) goto L_0x002b
            r1.close()
            return r0
        L_0x002b:
            int r4 = r1.getInt(r0)     // Catch:{ Exception -> 0x0037 }
            r2 = r4
        L_0x0031:
            r1.close()
            goto L_0x003e
        L_0x0035:
            r0 = move-exception
            goto L_0x0042
        L_0x0037:
            r4 = move-exception
            com.didichuxing.doraemonkit.okgo.utils.OkLogger.printStackTrace(r4)     // Catch:{ all -> 0x0035 }
            if (r1 == 0) goto L_0x003e
            goto L_0x0031
        L_0x003e:
            if (r2 <= 0) goto L_0x0041
            r0 = r3
        L_0x0041:
            return r0
        L_0x0042:
            if (r1 == 0) goto L_0x0047
            r1.close()
        L_0x0047:
            throw r0
        L_0x0048:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.okgo.db.DBUtils.isTableExists(android.database.sqlite.SQLiteDatabase, java.lang.String):boolean");
    }

    public static boolean isFieldExists(SQLiteDatabase db, String tableName, String fieldName) {
        boolean z = false;
        if (tableName == null || db == null || fieldName == null || !db.isOpen()) {
            return false;
        }
        Cursor cursor = null;
        try {
            Cursor cursor2 = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0", (String[]) null);
            if (!(cursor2 == null || cursor2.getColumnIndex(fieldName) == -1)) {
                z = true;
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            return z;
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
            if (cursor != null) {
                cursor.close();
            }
            return false;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }
}
