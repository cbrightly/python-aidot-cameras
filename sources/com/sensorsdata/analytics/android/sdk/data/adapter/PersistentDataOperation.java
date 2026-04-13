package com.sensorsdata.analytics.android.sdk.data.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import org.json.JSONObject;

public class PersistentDataOperation extends DataOperation {
    PersistentDataOperation(Context context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String[] queryData(Uri uri, int limit) {
        return handleQueryUri(uri);
    }

    /* access modifiers changed from: package-private */
    public int insertData(Uri uri, JSONObject jsonObject) {
        return handleInsertUri(uri, jsonObject);
    }

    /* access modifiers changed from: package-private */
    public int insertData(Uri uri, ContentValues contentValues) {
        this.contentResolver.insert(uri, contentValues);
        return 0;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00a7, code lost:
        r16 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00ac, code lost:
        switch(r8) {
            case 0: goto L_0x011c;
            case 1: goto L_0x0114;
            case 2: goto L_0x0108;
            case 3: goto L_0x00fc;
            case 4: goto L_0x00f4;
            case 5: goto L_0x00e8;
            case 6: goto L_0x00dc;
            case 7: goto L_0x00d4;
            case 8: goto L_0x00cc;
            case 9: goto L_0x00c4;
            case 10: goto L_0x00b5;
            default: goto L_0x00af;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00af, code lost:
        r1 = r17;
        r3 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00b4, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        r5.put(com.sensorsdata.analytics.android.sdk.data.adapter.DbParams.PUSH_ID_KEY, r2.optString(com.sensorsdata.analytics.android.sdk.data.adapter.DbParams.PUSH_ID_KEY));
        r5.put(com.sensorsdata.analytics.android.sdk.data.adapter.DbParams.PUSH_ID_VALUE, r2.optString(com.sensorsdata.analytics.android.sdk.data.adapter.DbParams.PUSH_ID_VALUE));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00c4, code lost:
        r5.put(com.sensorsdata.analytics.android.sdk.data.adapter.DbParams.PersistentName.PERSISTENT_LOGIN_ID_KEY, r2.optString("value"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00cc, code lost:
        r5.put(com.sensorsdata.analytics.android.sdk.data.adapter.DbParams.PersistentName.PERSISTENT_USER_ID, r2.optString("value"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00d4, code lost:
        r5.put(com.sensorsdata.analytics.android.sdk.data.adapter.DbParams.PersistentName.REMOTE_CONFIG, r2.optString("value"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00dc, code lost:
        r5.put(com.sensorsdata.analytics.android.sdk.data.adapter.DbParams.TABLE_FIRST_PROCESS_START, java.lang.Boolean.valueOf(r2.optBoolean("value")));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00e8, code lost:
        r5.put(com.sensorsdata.analytics.android.sdk.data.adapter.DbParams.PersistentName.SUB_PROCESS_FLUSH_DATA, java.lang.Boolean.valueOf(r2.optBoolean("value")));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00f4, code lost:
        r5.put(com.sensorsdata.analytics.android.sdk.data.adapter.DbParams.PersistentName.LOGIN_ID, r2.optString("value"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00fc, code lost:
        r5.put(com.sensorsdata.analytics.android.sdk.data.adapter.DbParams.TABLE_SESSION_INTERVAL_TIME, java.lang.Long.valueOf(r2.optLong("value")));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0108, code lost:
        r5.put(com.sensorsdata.analytics.android.sdk.data.adapter.DbParams.TABLE_APP_START_TIME, java.lang.Long.valueOf(r2.optLong("value")));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0114, code lost:
        r5.put(com.sensorsdata.analytics.android.sdk.data.adapter.DbParams.PersistentName.APP_END_DATA, r2.optString("value"));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x011c, code lost:
        r5.put(com.sensorsdata.analytics.android.sdk.data.adapter.DbParams.TABLE_ACTIVITY_START_COUNT, java.lang.Integer.valueOf(r2.optInt("value")));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
        r17.contentResolver.insert(r18, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0132, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0134, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:?, code lost:
        return 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int handleInsertUri(android.net.Uri r18, org.json.JSONObject r19) {
        /*
            r17 = this;
            r1 = r18
            r2 = r19
            java.lang.String r0 = "push_value"
            r3 = -1
            if (r1 != 0) goto L_0x000b
            return r3
        L_0x000b:
            android.content.ContentValues r5 = new android.content.ContentValues     // Catch:{ Exception -> 0x013b }
            r5.<init>()     // Catch:{ Exception -> 0x013b }
            java.lang.String r6 = r18.getPath()     // Catch:{ Exception -> 0x013b }
            boolean r7 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x013b }
            if (r7 != 0) goto L_0x0137
            r7 = 1
            java.lang.String r8 = r6.substring(r7)     // Catch:{ Exception -> 0x013b }
            r6 = r8
            int r8 = r6.hashCode()     // Catch:{ Exception -> 0x013b }
            java.lang.String r9 = "sub_process_flush_data"
            java.lang.String r10 = "app_end_data"
            java.lang.String r11 = "sensorsdata_sdk_configuration"
            java.lang.String r12 = "app_start_time"
            java.lang.String r13 = "user_ids"
            java.lang.String r14 = "login_id_key"
            java.lang.String r15 = "first_process_start"
            java.lang.String r7 = "session_interval_time"
            java.lang.String r4 = "events_login_id"
            java.lang.String r3 = "activity_started_count"
            java.lang.String r1 = "push_key"
            switch(r8) {
                case -1437430111: goto L_0x0097;
                case -1436067305: goto L_0x008f;
                case -1173524450: goto L_0x0087;
                case -1109940413: goto L_0x007f;
                case -456824111: goto L_0x0076;
                case -266152892: goto L_0x006d;
                case 791585128: goto L_0x0065;
                case 947194773: goto L_0x005d;
                case 1521941740: goto L_0x0055;
                case 1776312250: goto L_0x004c;
                case 1964784692: goto L_0x0044;
                default: goto L_0x0042;
            }
        L_0x0042:
            goto L_0x00a6
        L_0x0044:
            boolean r8 = r6.equals(r9)     // Catch:{ Exception -> 0x009f }
            if (r8 == 0) goto L_0x0042
            r8 = 5
            goto L_0x00a7
        L_0x004c:
            boolean r8 = r6.equals(r1)     // Catch:{ Exception -> 0x009f }
            if (r8 == 0) goto L_0x0042
            r8 = 10
            goto L_0x00a7
        L_0x0055:
            boolean r8 = r6.equals(r10)     // Catch:{ Exception -> 0x009f }
            if (r8 == 0) goto L_0x0042
            r8 = 1
            goto L_0x00a7
        L_0x005d:
            boolean r8 = r6.equals(r11)     // Catch:{ Exception -> 0x009f }
            if (r8 == 0) goto L_0x0042
            r8 = 7
            goto L_0x00a7
        L_0x0065:
            boolean r8 = r6.equals(r12)     // Catch:{ Exception -> 0x009f }
            if (r8 == 0) goto L_0x0042
            r8 = 2
            goto L_0x00a7
        L_0x006d:
            boolean r8 = r6.equals(r13)     // Catch:{ Exception -> 0x009f }
            if (r8 == 0) goto L_0x0042
            r8 = 8
            goto L_0x00a7
        L_0x0076:
            boolean r8 = r6.equals(r14)     // Catch:{ Exception -> 0x009f }
            if (r8 == 0) goto L_0x0042
            r8 = 9
            goto L_0x00a7
        L_0x007f:
            boolean r8 = r6.equals(r15)     // Catch:{ Exception -> 0x009f }
            if (r8 == 0) goto L_0x0042
            r8 = 6
            goto L_0x00a7
        L_0x0087:
            boolean r8 = r6.equals(r7)     // Catch:{ Exception -> 0x009f }
            if (r8 == 0) goto L_0x0042
            r8 = 3
            goto L_0x00a7
        L_0x008f:
            boolean r8 = r6.equals(r4)     // Catch:{ Exception -> 0x009f }
            if (r8 == 0) goto L_0x0042
            r8 = 4
            goto L_0x00a7
        L_0x0097:
            boolean r8 = r6.equals(r3)     // Catch:{ Exception -> 0x009f }
            if (r8 == 0) goto L_0x0042
            r8 = 0
            goto L_0x00a7
        L_0x009f:
            r0 = move-exception
            r1 = r17
        L_0x00a2:
            r3 = r18
            goto L_0x013f
        L_0x00a6:
            r8 = -1
        L_0x00a7:
            r16 = r6
            java.lang.String r6 = "value"
            switch(r8) {
                case 0: goto L_0x011c;
                case 1: goto L_0x0114;
                case 2: goto L_0x0108;
                case 3: goto L_0x00fc;
                case 4: goto L_0x00f4;
                case 5: goto L_0x00e8;
                case 6: goto L_0x00dc;
                case 7: goto L_0x00d4;
                case 8: goto L_0x00cc;
                case 9: goto L_0x00c4;
                case 10: goto L_0x00b5;
                default: goto L_0x00af;
            }
        L_0x00af:
            r1 = r17
            r3 = r18
            r0 = -1
            return r0
        L_0x00b5:
            java.lang.String r3 = r2.optString(r1)     // Catch:{ Exception -> 0x009f }
            r5.put(r1, r3)     // Catch:{ Exception -> 0x009f }
            java.lang.String r1 = r2.optString(r0)     // Catch:{ Exception -> 0x009f }
            r5.put(r0, r1)     // Catch:{ Exception -> 0x009f }
            goto L_0x0128
        L_0x00c4:
            java.lang.String r0 = r2.optString(r6)     // Catch:{ Exception -> 0x009f }
            r5.put(r14, r0)     // Catch:{ Exception -> 0x009f }
            goto L_0x0128
        L_0x00cc:
            java.lang.String r0 = r2.optString(r6)     // Catch:{ Exception -> 0x009f }
            r5.put(r13, r0)     // Catch:{ Exception -> 0x009f }
            goto L_0x0128
        L_0x00d4:
            java.lang.String r0 = r2.optString(r6)     // Catch:{ Exception -> 0x009f }
            r5.put(r11, r0)     // Catch:{ Exception -> 0x009f }
            goto L_0x0128
        L_0x00dc:
            boolean r0 = r2.optBoolean(r6)     // Catch:{ Exception -> 0x009f }
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x009f }
            r5.put(r15, r0)     // Catch:{ Exception -> 0x009f }
            goto L_0x0128
        L_0x00e8:
            boolean r0 = r2.optBoolean(r6)     // Catch:{ Exception -> 0x009f }
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x009f }
            r5.put(r9, r0)     // Catch:{ Exception -> 0x009f }
            goto L_0x0128
        L_0x00f4:
            java.lang.String r0 = r2.optString(r6)     // Catch:{ Exception -> 0x009f }
            r5.put(r4, r0)     // Catch:{ Exception -> 0x009f }
            goto L_0x0128
        L_0x00fc:
            long r0 = r2.optLong(r6)     // Catch:{ Exception -> 0x009f }
            java.lang.Long r0 = java.lang.Long.valueOf(r0)     // Catch:{ Exception -> 0x009f }
            r5.put(r7, r0)     // Catch:{ Exception -> 0x009f }
            goto L_0x0128
        L_0x0108:
            long r0 = r2.optLong(r6)     // Catch:{ Exception -> 0x009f }
            java.lang.Long r0 = java.lang.Long.valueOf(r0)     // Catch:{ Exception -> 0x009f }
            r5.put(r12, r0)     // Catch:{ Exception -> 0x009f }
            goto L_0x0128
        L_0x0114:
            java.lang.String r0 = r2.optString(r6)     // Catch:{ Exception -> 0x009f }
            r5.put(r10, r0)     // Catch:{ Exception -> 0x009f }
            goto L_0x0128
        L_0x011c:
            int r0 = r2.optInt(r6)     // Catch:{ Exception -> 0x009f }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x009f }
            r5.put(r3, r0)     // Catch:{ Exception -> 0x009f }
        L_0x0128:
            r1 = r17
            android.content.ContentResolver r0 = r1.contentResolver     // Catch:{ Exception -> 0x0134 }
            r3 = r18
            r0.insert(r3, r5)     // Catch:{ Exception -> 0x0132 }
            goto L_0x013a
        L_0x0132:
            r0 = move-exception
            goto L_0x013f
        L_0x0134:
            r0 = move-exception
            goto L_0x00a2
        L_0x0137:
            r3 = r1
            r1 = r17
        L_0x013a:
            goto L_0x0142
        L_0x013b:
            r0 = move-exception
            r3 = r1
            r1 = r17
        L_0x013f:
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
        L_0x0142:
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.data.adapter.PersistentDataOperation.handleInsertUri(android.net.Uri, org.json.JSONObject):int");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00e6, code lost:
        if (r2 != null) goto L_0x00e8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00e8, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x00f2, code lost:
        if (r2 != null) goto L_0x00e8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x00f5, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String[] handleQueryUri(android.net.Uri r11) {
        /*
            r10 = this;
            r0 = 0
            if (r11 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.String r1 = r11.getPath()
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 == 0) goto L_0x000f
            return r0
        L_0x000f:
            r2 = 0
            r3 = 1
            java.lang.String r4 = r1.substring(r3)     // Catch:{ Exception -> 0x00ee }
            r1 = r4
            android.content.ContentResolver r4 = r10.contentResolver     // Catch:{ Exception -> 0x00ee }
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r5 = r11
            android.database.Cursor r4 = r4.query(r5, r6, r7, r8, r9)     // Catch:{ Exception -> 0x00ee }
            r2 = r4
            if (r2 == 0) goto L_0x00e6
            int r4 = r2.getCount()     // Catch:{ Exception -> 0x00ee }
            if (r4 <= 0) goto L_0x00e6
            r2.moveToNext()     // Catch:{ Exception -> 0x00ee }
            r4 = -1
            int r5 = r1.hashCode()     // Catch:{ Exception -> 0x00ee }
            r6 = 0
            switch(r5) {
                case -1437430111: goto L_0x00a5;
                case -1436067305: goto L_0x009b;
                case -1173524450: goto L_0x008f;
                case -1109940413: goto L_0x0085;
                case -456824111: goto L_0x007b;
                case -266152892: goto L_0x0070;
                case 791585128: goto L_0x0065;
                case 947194773: goto L_0x005a;
                case 1521941740: goto L_0x0050;
                case 1776312250: goto L_0x0044;
                case 1964784692: goto L_0x0038;
                default: goto L_0x0036;
            }     // Catch:{ Exception -> 0x00ee }
        L_0x0036:
            goto L_0x00ae
        L_0x0038:
            java.lang.String r5 = "sub_process_flush_data"
            boolean r5 = r1.equals(r5)     // Catch:{ Exception -> 0x00ee }
            if (r5 == 0) goto L_0x0036
            r4 = r3
            goto L_0x00ae
        L_0x0044:
            java.lang.String r5 = "push_key"
            boolean r5 = r1.equals(r5)     // Catch:{ Exception -> 0x00ee }
            if (r5 == 0) goto L_0x0036
            r4 = 8
            goto L_0x00ae
        L_0x0050:
            java.lang.String r5 = "app_end_data"
            boolean r5 = r1.equals(r5)     // Catch:{ Exception -> 0x00ee }
            if (r5 == 0) goto L_0x0036
            r4 = 3
            goto L_0x00ae
        L_0x005a:
            java.lang.String r5 = "sensorsdata_sdk_configuration"
            boolean r5 = r1.equals(r5)     // Catch:{ Exception -> 0x00ee }
            if (r5 == 0) goto L_0x0036
            r4 = 5
            goto L_0x00ae
        L_0x0065:
            java.lang.String r5 = "app_start_time"
            boolean r5 = r1.equals(r5)     // Catch:{ Exception -> 0x00ee }
            if (r5 == 0) goto L_0x0036
            r4 = 10
            goto L_0x00ae
        L_0x0070:
            java.lang.String r5 = "user_ids"
            boolean r5 = r1.equals(r5)     // Catch:{ Exception -> 0x00ee }
            if (r5 == 0) goto L_0x0036
            r4 = 6
            goto L_0x00ae
        L_0x007b:
            java.lang.String r5 = "login_id_key"
            boolean r5 = r1.equals(r5)     // Catch:{ Exception -> 0x00ee }
            if (r5 == 0) goto L_0x0036
            r4 = 7
            goto L_0x00ae
        L_0x0085:
            java.lang.String r5 = "first_process_start"
            boolean r5 = r1.equals(r5)     // Catch:{ Exception -> 0x00ee }
            if (r5 == 0) goto L_0x0036
            r4 = 2
            goto L_0x00ae
        L_0x008f:
            java.lang.String r5 = "session_interval_time"
            boolean r5 = r1.equals(r5)     // Catch:{ Exception -> 0x00ee }
            if (r5 == 0) goto L_0x0036
            r4 = 9
            goto L_0x00ae
        L_0x009b:
            java.lang.String r5 = "events_login_id"
            boolean r5 = r1.equals(r5)     // Catch:{ Exception -> 0x00ee }
            if (r5 == 0) goto L_0x0036
            r4 = 4
            goto L_0x00ae
        L_0x00a5:
            java.lang.String r5 = "activity_started_count"
            boolean r5 = r1.equals(r5)     // Catch:{ Exception -> 0x00ee }
            if (r5 == 0) goto L_0x0036
            r4 = r6
        L_0x00ae:
            switch(r4) {
                case 0: goto L_0x00d1;
                case 1: goto L_0x00d1;
                case 2: goto L_0x00d1;
                case 3: goto L_0x00c4;
                case 4: goto L_0x00c4;
                case 5: goto L_0x00c4;
                case 6: goto L_0x00c4;
                case 7: goto L_0x00c4;
                case 8: goto L_0x00c4;
                case 9: goto L_0x00b3;
                case 10: goto L_0x00b3;
                default: goto L_0x00b1;
            }     // Catch:{ Exception -> 0x00ee }
        L_0x00b1:
            goto L_0x00e2
        L_0x00b3:
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ Exception -> 0x00ee }
            long r4 = r2.getLong(r6)     // Catch:{ Exception -> 0x00ee }
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ Exception -> 0x00ee }
            r3[r6] = r4     // Catch:{ Exception -> 0x00ee }
            r2.close()
            return r3
        L_0x00c4:
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ Exception -> 0x00ee }
            java.lang.String r4 = r2.getString(r6)     // Catch:{ Exception -> 0x00ee }
            r3[r6] = r4     // Catch:{ Exception -> 0x00ee }
            r2.close()
            return r3
        L_0x00d1:
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ Exception -> 0x00ee }
            int r4 = r2.getInt(r6)     // Catch:{ Exception -> 0x00ee }
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ Exception -> 0x00ee }
            r3[r6] = r4     // Catch:{ Exception -> 0x00ee }
            r2.close()
            return r3
        L_0x00e2:
            r2.close()
            return r0
        L_0x00e6:
            if (r2 == 0) goto L_0x00f5
        L_0x00e8:
            r2.close()
            goto L_0x00f5
        L_0x00ec:
            r0 = move-exception
            goto L_0x00f6
        L_0x00ee:
            r3 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r3)     // Catch:{ all -> 0x00ec }
            if (r2 == 0) goto L_0x00f5
            goto L_0x00e8
        L_0x00f5:
            return r0
        L_0x00f6:
            if (r2 == 0) goto L_0x00fb
            r2.close()
        L_0x00fb:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.data.adapter.PersistentDataOperation.handleQueryUri(android.net.Uri):java.lang.String[]");
    }

    /* access modifiers changed from: package-private */
    public void deleteData(Uri uri, String key) {
        this.contentResolver.delete(uri.buildUpon().appendQueryParameter(DbParams.REMOVE_SP_KEY, key).build(), (String) null, (String[]) null);
    }
}
