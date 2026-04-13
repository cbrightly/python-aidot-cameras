package com.sensorsdata.analytics.android.sdk.data.adapter;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import java.io.File;
import org.json.JSONObject;

public abstract class DataOperation {
    String TAG = "EventDataOperation";
    ContentResolver contentResolver;
    private Context mContext;
    private final File mDatabaseFile;

    /* access modifiers changed from: package-private */
    public abstract int insertData(Uri uri, ContentValues contentValues);

    /* access modifiers changed from: package-private */
    public abstract int insertData(Uri uri, JSONObject jSONObject);

    /* access modifiers changed from: package-private */
    public abstract String[] queryData(Uri uri, int i);

    DataOperation(Context context) {
        this.mContext = context;
        this.contentResolver = context.getContentResolver();
        this.mDatabaseFile = context.getDatabasePath(DbParams.DATABASE_NAME);
    }

    /* access modifiers changed from: package-private */
    public int queryDataCount(Uri uri) {
        return queryDataCount(uri, (String[]) null, (String) null, (String[]) null, (String) null);
    }

    /* access modifiers changed from: package-private */
    public int queryDataCount(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        try {
            cursor = this.contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
            if (cursor != null) {
                int count = cursor.getCount();
                cursor.close();
                return count;
            }
            if (cursor == null) {
                return 0;
            }
            cursor.close();
            return 0;
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            if (cursor == null) {
                return 0;
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public void deleteData(Uri uri, String id) {
        try {
            if ("DB_DELETE_ALL".equals(id)) {
                this.contentResolver.delete(uri, (String) null, (String[]) null);
                return;
            }
            this.contentResolver.delete(uri, "_id <= ?", new String[]{id});
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    /* access modifiers changed from: package-private */
    public String parseData(String keyData) {
        try {
            if (TextUtils.isEmpty(keyData)) {
                return "";
            }
            int index = keyData.lastIndexOf("\t");
            if (index > -1) {
                String crc = keyData.substring(index).replaceFirst("\t", "");
                keyData = keyData.substring(0, index);
                if (TextUtils.isEmpty(keyData) || TextUtils.isEmpty(crc) || !crc.equals(String.valueOf(keyData.hashCode()))) {
                    return "";
                }
            }
            return keyData;
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    /* access modifiers changed from: package-private */
    public int deleteDataLowMemory(Uri uri) {
        if (belowMemThreshold()) {
            SALog.i(this.TAG, "There is not enough space left on the device to store events, so will delete 100 oldest events");
            String[] eventsData = queryData(uri, 100);
            if (eventsData == null) {
                return -2;
            }
            deleteData(uri, eventsData[0]);
            if (queryDataCount(uri) <= 0) {
                return -2;
            }
        }
        return 0;
    }

    private long getMaxCacheSize(Context context) {
        try {
            return SensorsDataAPI.sharedInstance(context).getMaxCacheSize();
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return 33554432;
        }
    }

    private boolean belowMemThreshold() {
        if (!this.mDatabaseFile.exists() || this.mDatabaseFile.length() < getMaxCacheSize(this.mContext)) {
            return false;
        }
        return true;
    }
}
