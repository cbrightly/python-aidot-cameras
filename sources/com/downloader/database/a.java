package com.downloader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/* compiled from: AppDbHelper */
public class a implements c {
    private final SQLiteDatabase a;

    public a(Context context) {
        this.a = new b(context).getWritableDatabase();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0095, code lost:
        if (r0 == null) goto L_0x0098;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0098, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0089, code lost:
        if (r0 != null) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x008b, code lost:
        r0.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.downloader.database.d a(int r6) {
        /*
            r5 = this;
            r0 = 0
            r1 = 0
            android.database.sqlite.SQLiteDatabase r2 = r5.a     // Catch:{ Exception -> 0x0091 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0091 }
            r3.<init>()     // Catch:{ Exception -> 0x0091 }
            java.lang.String r4 = "SELECT * FROM prdownloader WHERE id = "
            r3.append(r4)     // Catch:{ Exception -> 0x0091 }
            r3.append(r6)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0091 }
            r4 = 0
            android.database.Cursor r2 = r2.rawQuery(r3, r4)     // Catch:{ Exception -> 0x0091 }
            r0 = r2
            if (r0 == 0) goto L_0x0089
            boolean r2 = r0.moveToFirst()     // Catch:{ Exception -> 0x0091 }
            if (r2 == 0) goto L_0x0089
            com.downloader.database.d r2 = new com.downloader.database.d     // Catch:{ Exception -> 0x0091 }
            r2.<init>()     // Catch:{ Exception -> 0x0091 }
            r1 = r2
            r1.m(r6)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r2 = "url"
            int r2 = r0.getColumnIndex(r2)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r2 = r0.getString(r2)     // Catch:{ Exception -> 0x0091 }
            r1.p(r2)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r2 = "etag"
            int r2 = r0.getColumnIndex(r2)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r2 = r0.getString(r2)     // Catch:{ Exception -> 0x0091 }
            r1.k(r2)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r2 = "dir_path"
            int r2 = r0.getColumnIndex(r2)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r2 = r0.getString(r2)     // Catch:{ Exception -> 0x0091 }
            r1.i(r2)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r2 = "file_name"
            int r2 = r0.getColumnIndex(r2)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r2 = r0.getString(r2)     // Catch:{ Exception -> 0x0091 }
            r1.l(r2)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r2 = "total_bytes"
            int r2 = r0.getColumnIndex(r2)     // Catch:{ Exception -> 0x0091 }
            long r2 = r0.getLong(r2)     // Catch:{ Exception -> 0x0091 }
            r1.o(r2)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r2 = "downloaded_bytes"
            int r2 = r0.getColumnIndex(r2)     // Catch:{ Exception -> 0x0091 }
            long r2 = r0.getLong(r2)     // Catch:{ Exception -> 0x0091 }
            r1.j(r2)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r2 = "last_modified_at"
            int r2 = r0.getColumnIndex(r2)     // Catch:{ Exception -> 0x0091 }
            long r2 = r0.getLong(r2)     // Catch:{ Exception -> 0x0091 }
            r1.n(r2)     // Catch:{ Exception -> 0x0091 }
        L_0x0089:
            if (r0 == 0) goto L_0x0098
        L_0x008b:
            r0.close()
            goto L_0x0098
        L_0x008f:
            r2 = move-exception
            goto L_0x0099
        L_0x0091:
            r2 = move-exception
            r2.printStackTrace()     // Catch:{ all -> 0x008f }
            if (r0 == 0) goto L_0x0098
            goto L_0x008b
        L_0x0098:
            return r1
        L_0x0099:
            if (r0 == 0) goto L_0x009e
            r0.close()
        L_0x009e:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.downloader.database.a.a(int):com.downloader.database.d");
    }

    public void d(d model) {
        try {
            ContentValues values = new ContentValues();
            values.put("id", Integer.valueOf(model.e()));
            values.put("url", model.h());
            values.put("etag", model.c());
            values.put("dir_path", model.a());
            values.put("file_name", model.d());
            values.put("total_bytes", Long.valueOf(model.g()));
            values.put("downloaded_bytes", Long.valueOf(model.b()));
            values.put("last_modified_at", Long.valueOf(model.f()));
            this.a.insert("prdownloader", (String) null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void b(int id, long downloadedBytes, long lastModifiedAt) {
        try {
            ContentValues values = new ContentValues();
            values.put("downloaded_bytes", Long.valueOf(downloadedBytes));
            values.put("last_modified_at", Long.valueOf(lastModifiedAt));
            this.a.update("prdownloader", values, "id = ? ", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(int id) {
        try {
            SQLiteDatabase sQLiteDatabase = this.a;
            sQLiteDatabase.execSQL("DELETE FROM prdownloader WHERE id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x00bb, code lost:
        if (r1 == null) goto L_0x00be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x00be, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x00af, code lost:
        if (r1 == null) goto L_0x00be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x00b1, code lost:
        r1.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.downloader.database.d> c(int r10) {
        /*
            r9 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            int r2 = r10 * 24
            int r2 = r2 * 60
            int r2 = r2 * 60
            long r2 = (long) r2
            r4 = 1000(0x3e8, double:4.94E-321)
            long r2 = r2 * r4
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00b7 }
            long r4 = r4 - r2
            android.database.sqlite.SQLiteDatabase r6 = r9.a     // Catch:{ Exception -> 0x00b7 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b7 }
            r7.<init>()     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r8 = "SELECT * FROM prdownloader WHERE last_modified_at <= "
            r7.append(r8)     // Catch:{ Exception -> 0x00b7 }
            r7.append(r4)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x00b7 }
            r8 = 0
            android.database.Cursor r6 = r6.rawQuery(r7, r8)     // Catch:{ Exception -> 0x00b7 }
            r1 = r6
            if (r1 == 0) goto L_0x00af
            boolean r6 = r1.moveToFirst()     // Catch:{ Exception -> 0x00b7 }
            if (r6 == 0) goto L_0x00af
        L_0x0036:
            com.downloader.database.d r6 = new com.downloader.database.d     // Catch:{ Exception -> 0x00b7 }
            r6.<init>()     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r7 = "id"
            int r7 = r1.getColumnIndex(r7)     // Catch:{ Exception -> 0x00b7 }
            int r7 = r1.getInt(r7)     // Catch:{ Exception -> 0x00b7 }
            r6.m(r7)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r7 = "url"
            int r7 = r1.getColumnIndex(r7)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r7 = r1.getString(r7)     // Catch:{ Exception -> 0x00b7 }
            r6.p(r7)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r7 = "etag"
            int r7 = r1.getColumnIndex(r7)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r7 = r1.getString(r7)     // Catch:{ Exception -> 0x00b7 }
            r6.k(r7)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r7 = "dir_path"
            int r7 = r1.getColumnIndex(r7)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r7 = r1.getString(r7)     // Catch:{ Exception -> 0x00b7 }
            r6.i(r7)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r7 = "file_name"
            int r7 = r1.getColumnIndex(r7)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r7 = r1.getString(r7)     // Catch:{ Exception -> 0x00b7 }
            r6.l(r7)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r7 = "total_bytes"
            int r7 = r1.getColumnIndex(r7)     // Catch:{ Exception -> 0x00b7 }
            long r7 = r1.getLong(r7)     // Catch:{ Exception -> 0x00b7 }
            r6.o(r7)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r7 = "downloaded_bytes"
            int r7 = r1.getColumnIndex(r7)     // Catch:{ Exception -> 0x00b7 }
            long r7 = r1.getLong(r7)     // Catch:{ Exception -> 0x00b7 }
            r6.j(r7)     // Catch:{ Exception -> 0x00b7 }
            java.lang.String r7 = "last_modified_at"
            int r7 = r1.getColumnIndex(r7)     // Catch:{ Exception -> 0x00b7 }
            long r7 = r1.getLong(r7)     // Catch:{ Exception -> 0x00b7 }
            r6.n(r7)     // Catch:{ Exception -> 0x00b7 }
            r0.add(r6)     // Catch:{ Exception -> 0x00b7 }
            boolean r6 = r1.moveToNext()     // Catch:{ Exception -> 0x00b7 }
            if (r6 != 0) goto L_0x0036
        L_0x00af:
            if (r1 == 0) goto L_0x00be
        L_0x00b1:
            r1.close()
            goto L_0x00be
        L_0x00b5:
            r2 = move-exception
            goto L_0x00bf
        L_0x00b7:
            r2 = move-exception
            r2.printStackTrace()     // Catch:{ all -> 0x00b5 }
            if (r1 == 0) goto L_0x00be
            goto L_0x00b1
        L_0x00be:
            return r0
        L_0x00bf:
            if (r1 == 0) goto L_0x00c4
            r1.close()
        L_0x00c4:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.downloader.database.a.c(int):java.util.List");
    }
}
