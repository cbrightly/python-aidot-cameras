package com.sensorsdata.analytics.android.sdk.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;

public class SensorsDataContentProvider extends ContentProvider {
    private static final UriMatcher uriMatcher = new UriMatcher(-1);
    private SensorsDataDBHelper dbHelper;
    private SAProviderHelper mProviderHelper;

    public boolean onCreate() {
        String packageName;
        try {
            Context context = getContext();
            if (context == null) {
                return true;
            }
            try {
                packageName = context.getApplicationContext().getPackageName();
            } catch (UnsupportedOperationException e) {
                packageName = "com.sensorsdata.analytics.android.sdk.test";
            }
            SensorsDataDBHelper sensorsDataDBHelper = new SensorsDataDBHelper(context);
            this.dbHelper = sensorsDataDBHelper;
            SAProviderHelper sAProviderHelper = new SAProviderHelper(context, sensorsDataDBHelper);
            this.mProviderHelper = sAProviderHelper;
            UriMatcher uriMatcher2 = uriMatcher;
            sAProviderHelper.appendUri(uriMatcher2, packageName + ".SensorsDataContentProvider");
            this.mProviderHelper.migratingDB(context, packageName);
            return true;
        } catch (Exception e2) {
            SALog.printStackTrace(e2);
            return true;
        }
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        try {
            int code = uriMatcher.match(uri);
            if (1 == code) {
                return this.mProviderHelper.deleteEvents(selection, selectionArgs);
            }
            if (code == 15) {
                return this.mProviderHelper.removeSP(uri.getQueryParameter(DbParams.REMOVE_SP_KEY));
            }
            return 0;
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        if (values == null || values.size() == 0) {
            return uri;
        }
        try {
            int code = uriMatcher.match(uri);
            if (code == 1) {
                return this.mProviderHelper.insertEvent(uri, values);
            }
            if (code == 8) {
                return this.mProviderHelper.insertChannelPersistent(uri, values);
            }
            this.mProviderHelper.insertPersistent(code, uri, values);
            return uri;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return uri;
        }
    }

    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase database = null;
        try {
            database = this.dbHelper.getWritableDatabase();
            database.beginTransaction();
            for (ContentValues insert : values) {
                insert(uri, insert);
            }
            database.setTransactionSuccessful();
            database.endTransaction();
            return numValues;
        } catch (SQLiteException e) {
            SALog.printStackTrace(e);
            if (database != null) {
                database.endTransaction();
            }
            return 0;
        } catch (Throwable th) {
            if (database != null) {
                database.endTransaction();
            }
            throw th;
        }
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        try {
            int code = uriMatcher.match(uri);
            if (code == 1) {
                return this.mProviderHelper.queryByTable("events", projection, selection, selectionArgs, sortOrder);
            }
            if (code == 8) {
                return this.mProviderHelper.queryByTable(DbParams.TABLE_CHANNEL_PERSISTENT, projection, selection, selectionArgs, sortOrder);
            }
            return this.mProviderHelper.queryPersistent(code, uri);
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
