package com.sensorsdata.analytics.android.sdk.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.data.SAProviderHelper;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;

public class OldBDatabaseHelper extends SQLiteOpenHelper {
    OldBDatabaseHelper(Context context, String dbName) {
        super(context, dbName, (SQLiteDatabase.CursorFactory) null, 4);
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /* access modifiers changed from: package-private */
    public void getAllEvents(SQLiteDatabase sqLiteDatabase, SAProviderHelper.QueryEventsListener listener) {
        Cursor c = null;
        try {
            c = getReadableDatabase().rawQuery(String.format("SELECT * FROM %s ORDER BY %s", new Object[]{"events", DbParams.KEY_CREATED_AT}), (String[]) null);
            sqLiteDatabase.beginTransaction();
            while (c.moveToNext()) {
                listener.insert(c.getString(c.getColumnIndex("data")), c.getString(c.getColumnIndex(DbParams.KEY_CREATED_AT)));
            }
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
            close();
        } catch (Exception e) {
            SALog.printStackTrace(e);
            sqLiteDatabase.endTransaction();
            close();
            if (c == null) {
                return;
            }
        } catch (Throwable th) {
            sqLiteDatabase.endTransaction();
            close();
            if (c != null) {
                c.close();
            }
            throw th;
        }
        c.close();
    }
}
