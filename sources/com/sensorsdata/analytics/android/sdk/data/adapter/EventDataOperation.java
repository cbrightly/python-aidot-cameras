package com.sensorsdata.analytics.android.sdk.data.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.sensorsdata.analytics.android.sdk.SALog;
import org.json.JSONObject;

public class EventDataOperation extends DataOperation {
    EventDataOperation(Context context) {
        super(context);
        this.TAG = "EventDataOperation";
    }

    /* access modifiers changed from: package-private */
    public int insertData(Uri uri, JSONObject jsonObject) {
        try {
            if (deleteDataLowMemory(uri) != 0) {
                return -2;
            }
            ContentValues cv = new ContentValues();
            cv.put("data", jsonObject.toString() + "\t" + jsonObject.toString().hashCode());
            cv.put(DbParams.KEY_CREATED_AT, Long.valueOf(System.currentTimeMillis()));
            this.contentResolver.insert(uri, cv);
            return 0;
        } catch (Throwable e) {
            SALog.d(this.TAG, e.getMessage());
            return 0;
        }
    }

    /* access modifiers changed from: package-private */
    public int insertData(Uri uri, ContentValues contentValues) {
        try {
            if (deleteDataLowMemory(uri) != 0) {
                return -2;
            }
            this.contentResolver.insert(uri, contentValues);
            return 0;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return 0;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0087, code lost:
        if (r1 != null) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0089, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0099, code lost:
        if (r1 == null) goto L_0x009c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x009c, code lost:
        if (r3 == null) goto L_0x00ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00aa, code lost:
        return new java.lang.String[]{r3, r2, "1"};
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00ab, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String[] queryData(android.net.Uri r13, int r14) {
        /*
            r12 = this;
            java.lang.String r0 = ",\"_flush_time\":"
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 1
            r5 = 0
            android.content.ContentResolver r6 = r12.contentResolver     // Catch:{ SQLiteException -> 0x008f }
            r8 = 0
            r9 = 0
            r10 = 0
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x008f }
            r7.<init>()     // Catch:{ SQLiteException -> 0x008f }
            java.lang.String r11 = "created_at ASC LIMIT "
            r7.append(r11)     // Catch:{ SQLiteException -> 0x008f }
            r7.append(r14)     // Catch:{ SQLiteException -> 0x008f }
            java.lang.String r11 = r7.toString()     // Catch:{ SQLiteException -> 0x008f }
            r7 = r13
            android.database.Cursor r6 = r6.query(r7, r8, r9, r10, r11)     // Catch:{ SQLiteException -> 0x008f }
            r1 = r6
            if (r1 == 0) goto L_0x0087
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x008f }
            r6.<init>()     // Catch:{ SQLiteException -> 0x008f }
            r7 = r0
            java.lang.String r8 = ","
            java.lang.String r9 = "["
            r6.append(r9)     // Catch:{ SQLiteException -> 0x008f }
        L_0x0032:
            boolean r9 = r1.moveToNext()     // Catch:{ SQLiteException -> 0x008f }
            if (r9 == 0) goto L_0x0082
            boolean r9 = r1.isLast()     // Catch:{ SQLiteException -> 0x008f }
            if (r9 == 0) goto L_0x004c
            java.lang.String r9 = "]"
            r8 = r9
            java.lang.String r9 = "_id"
            int r9 = r1.getColumnIndexOrThrow(r9)     // Catch:{ SQLiteException -> 0x008f }
            java.lang.String r9 = r1.getString(r9)     // Catch:{ SQLiteException -> 0x008f }
            r3 = r9
        L_0x004c:
            java.lang.String r9 = "data"
            int r9 = r1.getColumnIndexOrThrow(r9)     // Catch:{ Exception -> 0x007d }
            java.lang.String r9 = r1.getString(r9)     // Catch:{ Exception -> 0x007d }
            java.lang.String r10 = r12.parseData(r9)     // Catch:{ Exception -> 0x007d }
            r9 = r10
            boolean r10 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x007d }
            if (r10 != 0) goto L_0x007c
            int r10 = r9.length()     // Catch:{ Exception -> 0x007d }
            int r10 = r10 - r4
            r6.append(r9, r5, r10)     // Catch:{ Exception -> 0x007d }
            r6.append(r0)     // Catch:{ Exception -> 0x007d }
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x007d }
            r6.append(r10)     // Catch:{ Exception -> 0x007d }
            java.lang.String r10 = "}"
            r6.append(r10)     // Catch:{ Exception -> 0x007d }
            r6.append(r8)     // Catch:{ Exception -> 0x007d }
        L_0x007c:
            goto L_0x0032
        L_0x007d:
            r9 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r9)     // Catch:{ SQLiteException -> 0x008f }
            goto L_0x0032
        L_0x0082:
            java.lang.String r0 = r6.toString()     // Catch:{ SQLiteException -> 0x008f }
            r2 = r0
        L_0x0087:
            if (r1 == 0) goto L_0x009c
        L_0x0089:
            r1.close()
            goto L_0x009c
        L_0x008d:
            r0 = move-exception
            goto L_0x00ad
        L_0x008f:
            r0 = move-exception
            java.lang.String r6 = r12.TAG     // Catch:{ all -> 0x008d }
            java.lang.String r7 = "Could not pull records for SensorsData out of database events. Waiting to send."
            com.sensorsdata.analytics.android.sdk.SALog.i(r6, r7, r0)     // Catch:{ all -> 0x008d }
            r3 = 0
            r2 = 0
            if (r1 == 0) goto L_0x009c
            goto L_0x0089
        L_0x009c:
            if (r3 == 0) goto L_0x00ab
            r0 = 3
            java.lang.String[] r0 = new java.lang.String[r0]
            r0[r5] = r3
            r0[r4] = r2
            r4 = 2
            java.lang.String r5 = "1"
            r0[r4] = r5
            return r0
        L_0x00ab:
            r0 = 0
            return r0
        L_0x00ad:
            if (r1 == 0) goto L_0x00b2
            r1.close()
        L_0x00b2:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.data.adapter.EventDataOperation.queryData(android.net.Uri, int):java.lang.String[]");
    }

    /* access modifiers changed from: package-private */
    public void deleteData(Uri uri, String id) {
        super.deleteData(uri, id);
    }
}
