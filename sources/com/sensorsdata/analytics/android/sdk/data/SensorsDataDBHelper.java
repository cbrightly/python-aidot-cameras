package com.sensorsdata.analytics.android.sdk.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;

public class SensorsDataDBHelper extends SQLiteOpenHelper {
    private static final String CHANNEL_EVENT_PERSISTENT_TABLE = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY, %s INTEGER)", new Object[]{DbParams.TABLE_CHANNEL_PERSISTENT, DbParams.KEY_CHANNEL_EVENT_NAME, "result"});
    private static final String CREATE_EVENTS_TABLE = String.format("CREATE TABLE %s (_id INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s INTEGER NOT NULL);", new Object[]{"events", "data", DbParams.KEY_CREATED_AT});
    private static final String EVENTS_TIME_INDEX = String.format("CREATE INDEX IF NOT EXISTS time_idx ON %s (%s);", new Object[]{"events", DbParams.KEY_CREATED_AT});
    private static final String TAG = "SA.SQLiteOpenHelper";

    SensorsDataDBHelper(Context context) {
        super(context, DbParams.DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 5);
    }

    public void onCreate(SQLiteDatabase db) {
        SALog.i(TAG, "Creating a new Sensors Analytics DB");
        db.execSQL(CREATE_EVENTS_TABLE);
        db.execSQL(EVENTS_TIME_INDEX);
        db.execSQL(CHANNEL_EVENT_PERSISTENT_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        SALog.i(TAG, "Upgrading app, replacing Sensors Analytics DB");
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", new Object[]{"events"}));
        db.execSQL(CREATE_EVENTS_TABLE);
        db.execSQL(EVENTS_TIME_INDEX);
        db.execSQL(CHANNEL_EVENT_PERSISTENT_TABLE);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
