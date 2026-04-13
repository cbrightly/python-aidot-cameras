package com.sensorsdata.analytics.android.sdk.data.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.encrypt.SensorsDataEncrypt;
import org.json.JSONObject;

public class EncryptDataOperation extends DataOperation {
    private final SensorsDataEncrypt mSensorsDataEncrypt;

    EncryptDataOperation(Context context, SensorsDataEncrypt sensorsDataEncrypt) {
        super(context);
        this.mSensorsDataEncrypt = sensorsDataEncrypt;
    }

    /* access modifiers changed from: package-private */
    public int insertData(Uri uri, JSONObject jsonObject) {
        try {
            if (deleteDataLowMemory(uri) != 0) {
                return -2;
            }
            JSONObject jsonObject2 = this.mSensorsDataEncrypt.encryptTrackData(jsonObject);
            ContentValues cv = new ContentValues();
            cv.put("data", jsonObject2.toString() + "\t" + jsonObject2.toString().hashCode());
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
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00ed, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0104, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0105, code lost:
        r5 = r1;
        r6 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01ab, code lost:
        if (r4 != null) goto L_0x01ad;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01ad, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x01bb, code lost:
        if (r4 == null) goto L_0x01be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x01be, code lost:
        if (r6 == null) goto L_0x01cd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x01cc, code lost:
        return new java.lang.String[]{r6, r5, r7};
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x01cd, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x01d2, code lost:
        r4.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0104 A[ExcHandler: all (th java.lang.Throwable), PHI: r1 r20 
      PHI: (r1v22 'data' java.lang.String) = (r1v25 'data' java.lang.String), (r1v25 'data' java.lang.String), (r1v28 'data' java.lang.String), (r1v28 'data' java.lang.String) binds: [B:49:0x00f9, B:50:?, B:42:0x00e2, B:43:?] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r20v1 'last_id' java.lang.String) = (r20v3 'last_id' java.lang.String), (r20v3 'last_id' java.lang.String), (r20v6 'last_id' java.lang.String), (r20v6 'last_id' java.lang.String) binds: [B:49:0x00f9, B:50:?, B:42:0x00e2, B:43:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:42:0x00e2] */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x01d2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String[] queryData(android.net.Uri r23, int r24) {
        /*
            r22 = this;
            r1 = r22
            java.lang.String r2 = "pkv"
            java.lang.String r3 = "ekey"
            r4 = 0
            r5 = 0
            r6 = 0
            java.lang.String r7 = "9"
            java.util.HashMap r0 = new java.util.HashMap     // Catch:{ Exception -> 0x01b5, all -> 0x01b1 }
            r0.<init>()     // Catch:{ Exception -> 0x01b5, all -> 0x01b1 }
            r10 = r0
            org.json.JSONArray r0 = new org.json.JSONArray     // Catch:{ Exception -> 0x01b5, all -> 0x01b1 }
            r0.<init>()     // Catch:{ Exception -> 0x01b5, all -> 0x01b1 }
            r11 = r0
            android.content.ContentResolver r12 = r1.contentResolver     // Catch:{ Exception -> 0x01b5, all -> 0x01b1 }
            r14 = 0
            r15 = 0
            r16 = 0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01b5, all -> 0x01b1 }
            r0.<init>()     // Catch:{ Exception -> 0x01b5, all -> 0x01b1 }
            java.lang.String r13 = "created_at ASC LIMIT "
            r0.append(r13)     // Catch:{ Exception -> 0x01b5, all -> 0x01b1 }
            r13 = r24
            r0.append(r13)     // Catch:{ Exception -> 0x01b5, all -> 0x01b1 }
            java.lang.String r17 = r0.toString()     // Catch:{ Exception -> 0x01b5, all -> 0x01b1 }
            r13 = r23
            android.database.Cursor r0 = r12.query(r13, r14, r15, r16, r17)     // Catch:{ Exception -> 0x01b5, all -> 0x01b1 }
            r4 = r0
            if (r4 == 0) goto L_0x01a9
            r12 = r3
            r13 = r2
            java.lang.String r14 = "payloads"
            r0 = r14
            r15 = r0
        L_0x0041:
            boolean r0 = r4.moveToNext()     // Catch:{ Exception -> 0x01b5, all -> 0x01b1 }
            java.lang.String r8 = "$"
            if (r0 == 0) goto L_0x0118
            boolean r0 = r4.isLast()     // Catch:{ Exception -> 0x0114, all -> 0x0110 }
            if (r0 == 0) goto L_0x005e
            java.lang.String r0 = "_id"
            int r0 = r4.getColumnIndexOrThrow(r0)     // Catch:{ Exception -> 0x005b }
            java.lang.String r0 = r4.getString(r0)     // Catch:{ Exception -> 0x005b }
            r6 = r0
            goto L_0x005e
        L_0x005b:
            r0 = move-exception
            goto L_0x01b8
        L_0x005e:
            java.lang.String r0 = "data"
            int r0 = r4.getColumnIndexOrThrow(r0)     // Catch:{ Exception -> 0x00f5, all -> 0x00ef }
            java.lang.String r0 = r4.getString(r0)     // Catch:{ Exception -> 0x00f5, all -> 0x00ef }
            java.lang.String r17 = r1.parseData(r0)     // Catch:{ Exception -> 0x00f5, all -> 0x00ef }
            r0 = r17
            boolean r17 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x00f5, all -> 0x00ef }
            if (r17 == 0) goto L_0x0079
            r1 = r5
            r20 = r6
            goto L_0x00fd
        L_0x0079:
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ Exception -> 0x00f5, all -> 0x00ef }
            r9.<init>((java.lang.String) r0)     // Catch:{ Exception -> 0x00f5, all -> 0x00ef }
            boolean r18 = r9.has(r3)     // Catch:{ Exception -> 0x00f5, all -> 0x00ef }
            if (r18 != 0) goto L_0x0094
            r19 = r0
            com.sensorsdata.analytics.android.sdk.encrypt.SensorsDataEncrypt r0 = r1.mSensorsDataEncrypt     // Catch:{ Exception -> 0x008e }
            org.json.JSONObject r0 = r0.encryptTrackData(r9)     // Catch:{ Exception -> 0x008e }
            r9 = r0
            goto L_0x0096
        L_0x008e:
            r0 = move-exception
            r1 = r5
            r20 = r6
            goto L_0x00f9
        L_0x0094:
            r19 = r0
        L_0x0096:
            boolean r0 = r9.has(r3)     // Catch:{ Exception -> 0x00f5, all -> 0x00ef }
            if (r0 == 0) goto L_0x00dd
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008e }
            r0.<init>()     // Catch:{ Exception -> 0x008e }
            java.lang.String r1 = r9.getString(r3)     // Catch:{ Exception -> 0x008e }
            r0.append(r1)     // Catch:{ Exception -> 0x008e }
            r0.append(r8)     // Catch:{ Exception -> 0x008e }
            int r1 = r9.getInt(r2)     // Catch:{ Exception -> 0x008e }
            r0.append(r1)     // Catch:{ Exception -> 0x008e }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x008e }
            boolean r1 = r10.containsKey(r0)     // Catch:{ Exception -> 0x008e }
            if (r1 == 0) goto L_0x00ca
            java.lang.Object r1 = r10.get(r0)     // Catch:{ Exception -> 0x008e }
            org.json.JSONArray r1 = (org.json.JSONArray) r1     // Catch:{ Exception -> 0x008e }
            java.lang.String r8 = r9.getString(r14)     // Catch:{ Exception -> 0x008e }
            r1.put((java.lang.Object) r8)     // Catch:{ Exception -> 0x008e }
            goto L_0x00d9
        L_0x00ca:
            org.json.JSONArray r1 = new org.json.JSONArray     // Catch:{ Exception -> 0x008e }
            r1.<init>()     // Catch:{ Exception -> 0x008e }
            java.lang.String r8 = r9.getString(r14)     // Catch:{ Exception -> 0x008e }
            r1.put((java.lang.Object) r8)     // Catch:{ Exception -> 0x008e }
            r10.put(r0, r1)     // Catch:{ Exception -> 0x008e }
        L_0x00d9:
            r1 = r5
            r20 = r6
            goto L_0x00ec
        L_0x00dd:
            java.lang.String r0 = "_flush_time"
            r1 = r5
            r20 = r6
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00ed, all -> 0x0104 }
            r9.put((java.lang.String) r0, (long) r5)     // Catch:{ Exception -> 0x00ed, all -> 0x0104 }
            r11.put((java.lang.Object) r9)     // Catch:{ Exception -> 0x00ed, all -> 0x0104 }
        L_0x00ec:
            goto L_0x00fd
        L_0x00ed:
            r0 = move-exception
            goto L_0x00f9
        L_0x00ef:
            r0 = move-exception
            r1 = r5
            r20 = r6
            goto L_0x01d0
        L_0x00f5:
            r0 = move-exception
            r1 = r5
            r20 = r6
        L_0x00f9:
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)     // Catch:{ Exception -> 0x010a, all -> 0x0104 }
        L_0x00fd:
            r5 = r1
            r6 = r20
            r1 = r22
            goto L_0x0041
        L_0x0104:
            r0 = move-exception
            r5 = r1
            r6 = r20
            goto L_0x01d0
        L_0x010a:
            r0 = move-exception
            r5 = r1
            r6 = r20
            goto L_0x01b8
        L_0x0110:
            r0 = move-exception
            r1 = r5
            goto L_0x01d0
        L_0x0114:
            r0 = move-exception
            r1 = r5
            goto L_0x01b8
        L_0x0118:
            r1 = r5
            org.json.JSONArray r0 = new org.json.JSONArray     // Catch:{ Exception -> 0x01a3, all -> 0x019d }
            r0.<init>()     // Catch:{ Exception -> 0x01a3, all -> 0x019d }
            java.util.Set r5 = r10.keySet()     // Catch:{ Exception -> 0x01a3, all -> 0x019d }
            java.util.Iterator r5 = r5.iterator()     // Catch:{ Exception -> 0x01a3, all -> 0x019d }
        L_0x0126:
            boolean r9 = r5.hasNext()     // Catch:{ Exception -> 0x01a3, all -> 0x019d }
            if (r9 == 0) goto L_0x017e
            java.lang.Object r9 = r5.next()     // Catch:{ Exception -> 0x01a3, all -> 0x019d }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ Exception -> 0x01a3, all -> 0x019d }
            org.json.JSONObject r18 = new org.json.JSONObject     // Catch:{ Exception -> 0x01a3, all -> 0x019d }
            r18.<init>()     // Catch:{ Exception -> 0x01a3, all -> 0x019d }
            r19 = r18
            r18 = r1
            int r1 = r9.indexOf(r8)     // Catch:{ Exception -> 0x0199, all -> 0x0195 }
            r20 = r5
            r5 = 0
            java.lang.String r1 = r9.substring(r5, r1)     // Catch:{ Exception -> 0x0199, all -> 0x0195 }
            r5 = r19
            r5.put((java.lang.String) r3, (java.lang.Object) r1)     // Catch:{ Exception -> 0x0199, all -> 0x0195 }
            int r1 = r9.indexOf(r8)     // Catch:{ Exception -> 0x0199, all -> 0x0195 }
            r16 = 1
            int r1 = r1 + 1
            java.lang.String r1 = r9.substring(r1)     // Catch:{ Exception -> 0x0199, all -> 0x0195 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0199, all -> 0x0195 }
            r5.put((java.lang.String) r2, (java.lang.Object) r1)     // Catch:{ Exception -> 0x0199, all -> 0x0195 }
            java.lang.Object r1 = r10.get(r9)     // Catch:{ Exception -> 0x0199, all -> 0x0195 }
            r5.put((java.lang.String) r14, (java.lang.Object) r1)     // Catch:{ Exception -> 0x0199, all -> 0x0195 }
            java.lang.String r1 = "flush_time"
            r19 = r2
            r21 = r3
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0199, all -> 0x0195 }
            r5.put((java.lang.String) r1, (long) r2)     // Catch:{ Exception -> 0x0199, all -> 0x0195 }
            r0.put((java.lang.Object) r5)     // Catch:{ Exception -> 0x0199, all -> 0x0195 }
            r1 = r18
            r2 = r19
            r5 = r20
            r3 = r21
            goto L_0x0126
        L_0x017e:
            r18 = r1
            int r1 = r0.length()     // Catch:{ Exception -> 0x0199, all -> 0x0195 }
            if (r1 <= 0) goto L_0x018c
            java.lang.String r1 = r0.toString()     // Catch:{ Exception -> 0x0199, all -> 0x0195 }
            r5 = r1
            goto L_0x01ab
        L_0x018c:
            java.lang.String r1 = r11.toString()     // Catch:{ Exception -> 0x0199, all -> 0x0195 }
            r5 = r1
            java.lang.String r1 = "1"
            r7 = r1
            goto L_0x01ab
        L_0x0195:
            r0 = move-exception
            r5 = r18
            goto L_0x01d0
        L_0x0199:
            r0 = move-exception
            r5 = r18
            goto L_0x01b8
        L_0x019d:
            r0 = move-exception
            r18 = r1
            r5 = r18
            goto L_0x01d0
        L_0x01a3:
            r0 = move-exception
            r18 = r1
            r5 = r18
            goto L_0x01b8
        L_0x01a9:
            r18 = r5
        L_0x01ab:
            if (r4 == 0) goto L_0x01be
        L_0x01ad:
            r4.close()
            goto L_0x01be
        L_0x01b1:
            r0 = move-exception
            r18 = r5
            goto L_0x01d0
        L_0x01b5:
            r0 = move-exception
            r18 = r5
        L_0x01b8:
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)     // Catch:{ all -> 0x01cf }
            if (r4 == 0) goto L_0x01be
            goto L_0x01ad
        L_0x01be:
            if (r6 == 0) goto L_0x01cd
            r0 = 3
            java.lang.String[] r0 = new java.lang.String[r0]
            r1 = 0
            r0[r1] = r6
            r1 = 1
            r0[r1] = r5
            r1 = 2
            r0[r1] = r7
            return r0
        L_0x01cd:
            r0 = 0
            return r0
        L_0x01cf:
            r0 = move-exception
        L_0x01d0:
            if (r4 == 0) goto L_0x01d5
            r4.close()
        L_0x01d5:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.data.adapter.EncryptDataOperation.queryData(android.net.Uri, int):java.lang.String[]");
    }

    /* access modifiers changed from: package-private */
    public void deleteData(Uri uri, String id) {
        super.deleteData(uri, id);
    }
}
