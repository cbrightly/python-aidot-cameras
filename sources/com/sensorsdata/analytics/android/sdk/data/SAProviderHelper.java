package com.sensorsdata.analytics.android.sdk.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import com.sensorsdata.analytics.android.sdk.data.persistent.LoginIdKeyPersistent;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentAppEndData;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentLoader;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentLoginId;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentRemoteSDKConfig;
import com.sensorsdata.analytics.android.sdk.data.persistent.UserIdentityPersistent;
import com.sensorsdata.analytics.android.sdk.plugin.encrypt.SAStoreManager;
import com.sensorsdata.analytics.android.sdk.util.AppInfoUtils;

public class SAProviderHelper {
    private ContentResolver contentResolver;
    /* access modifiers changed from: private */
    public boolean isDbWritable = true;
    private long mAppStartTime = 0;
    private Context mContext;
    private SQLiteOpenHelper mDbHelper;
    private boolean mIsFlushDataState = false;
    private LoginIdKeyPersistent mLoginIdKeyPersistent;
    private int mSessionTime = 30000;
    private UserIdentityPersistent mUserIdsPersistent;
    private PersistentAppEndData persistentAppEndData;
    private PersistentLoginId persistentLoginId;
    private PersistentRemoteSDKConfig persistentRemoteSDKConfig;
    private int startActivityCount = 0;

    public interface QueryEventsListener {
        void insert(String str, String str2);
    }

    public interface URI_CODE {
        public static final int ACTIVITY_START_COUNT = 2;
        public static final int APP_END_DATA = 4;
        public static final int APP_PAUSED_TIME = 5;
        public static final int APP_START_TIME = 3;
        public static final int CHANNEL_PERSISTENT = 8;
        public static final int DISABLE_SDK = 11;
        public static final int EVENTS = 1;
        public static final int FIRST_PROCESS_START = 10;
        public static final int FLUSH_DATA = 9;
        public static final int LOGIN_ID = 7;
        public static final int LOGIN_ID_KEY = 14;
        public static final int PUSH_ID_KEY = 15;
        public static final int REMOTE_CONFIG = 12;
        public static final int SESSION_INTERVAL_TIME = 6;
        public static final int USER_IDENTITY_ID = 13;
    }

    public SAProviderHelper(Context context, SQLiteOpenHelper dbHelper) {
        try {
            this.mDbHelper = dbHelper;
            this.mContext = context;
            this.contentResolver = context.getContentResolver();
            PersistentLoader.initLoader(context);
            this.persistentAppEndData = (PersistentAppEndData) PersistentLoader.loadPersistent(DbParams.PersistentName.APP_END_DATA);
            this.persistentLoginId = (PersistentLoginId) PersistentLoader.loadPersistent(DbParams.PersistentName.LOGIN_ID);
            this.persistentRemoteSDKConfig = (PersistentRemoteSDKConfig) PersistentLoader.loadPersistent(DbParams.PersistentName.REMOTE_CONFIG);
            this.mUserIdsPersistent = (UserIdentityPersistent) PersistentLoader.loadPersistent(DbParams.PersistentName.PERSISTENT_USER_ID);
            this.mLoginIdKeyPersistent = (LoginIdKeyPersistent) PersistentLoader.loadPersistent(DbParams.PersistentName.PERSISTENT_LOGIN_ID_KEY);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void migratingDB(final Context context, final String packageName) {
        try {
            if (AppInfoUtils.getAppInfoBundle(context).getBoolean("com.sensorsdata.analytics.android.EnableMigratingDB", true)) {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            if (context.getDatabasePath(packageName).exists()) {
                                OldBDatabaseHelper oldBDatabaseHelper = new OldBDatabaseHelper(context, packageName);
                                final SQLiteDatabase database = SAProviderHelper.this.getWritableDatabase();
                                if (database != null) {
                                    final ContentValues cv = new ContentValues();
                                    oldBDatabaseHelper.getAllEvents(database, new QueryEventsListener() {
                                        public void insert(String data, String keyCreated) {
                                            cv.put("data", data);
                                            cv.put(DbParams.KEY_CREATED_AT, keyCreated);
                                            database.insert("events", "_id", cv);
                                            cv.clear();
                                        }
                                    });
                                }
                            }
                            if (SAProviderHelper.this.isDbWritable) {
                                context.deleteDatabase(packageName);
                            }
                        } catch (Exception e) {
                            SALog.printStackTrace(e);
                        }
                    }
                }).start();
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void appendUri(UriMatcher uriMatcher, String authority) {
        try {
            uriMatcher.addURI(authority, "events", 1);
            uriMatcher.addURI(authority, DbParams.TABLE_ACTIVITY_START_COUNT, 2);
            uriMatcher.addURI(authority, DbParams.TABLE_APP_START_TIME, 3);
            uriMatcher.addURI(authority, DbParams.PersistentName.APP_END_DATA, 4);
            uriMatcher.addURI(authority, DbParams.TABLE_SESSION_INTERVAL_TIME, 6);
            uriMatcher.addURI(authority, DbParams.PersistentName.LOGIN_ID, 7);
            uriMatcher.addURI(authority, DbParams.TABLE_CHANNEL_PERSISTENT, 8);
            uriMatcher.addURI(authority, DbParams.PersistentName.SUB_PROCESS_FLUSH_DATA, 9);
            uriMatcher.addURI(authority, DbParams.TABLE_FIRST_PROCESS_START, 10);
            uriMatcher.addURI(authority, DbParams.TABLE_DATA_DISABLE_SDK, 11);
            uriMatcher.addURI(authority, DbParams.PersistentName.REMOTE_CONFIG, 12);
            uriMatcher.addURI(authority, DbParams.PersistentName.PERSISTENT_USER_ID, 13);
            uriMatcher.addURI(authority, DbParams.PersistentName.PERSISTENT_LOGIN_ID_KEY, 14);
            uriMatcher.addURI(authority, DbParams.PUSH_ID_KEY, 15);
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    public Uri insertEvent(Uri uri, ContentValues values) {
        try {
            SQLiteDatabase database = getWritableDatabase();
            if (database != null && values.containsKey("data")) {
                if (values.containsKey(DbParams.KEY_CREATED_AT)) {
                    return ContentUris.withAppendedId(uri, database.insert("events", "_id", values));
                }
            }
            return uri;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return uri;
        }
    }

    public int deleteEvents(String selection, String[] selectionArgs) {
        if (!this.isDbWritable) {
            return 0;
        }
        try {
            SQLiteDatabase database = getWritableDatabase();
            if (database != null) {
                return database.delete("events", selection, selectionArgs);
            }
        } catch (SQLiteException e) {
            this.isDbWritable = false;
            SALog.printStackTrace(e);
        }
        return 0;
    }

    public Uri insertChannelPersistent(Uri uri, ContentValues values) {
        try {
            SQLiteDatabase database = getWritableDatabase();
            if (database != null) {
                if (values.containsKey(DbParams.KEY_CHANNEL_EVENT_NAME)) {
                    return ContentUris.withAppendedId(uri, database.insertWithOnConflict(DbParams.TABLE_CHANNEL_PERSISTENT, (String) null, values, 5));
                }
            }
            return uri;
        } catch (Exception exception) {
            SALog.printStackTrace(exception);
            return uri;
        }
    }

    public void insertPersistent(int code, Uri uri, ContentValues values) {
        switch (code) {
            case 2:
                this.startActivityCount = values.getAsInteger(DbParams.TABLE_ACTIVITY_START_COUNT).intValue();
                return;
            case 3:
                this.mAppStartTime = values.getAsLong(DbParams.TABLE_APP_START_TIME).longValue();
                return;
            case 4:
                this.persistentAppEndData.commit(values.getAsString(DbParams.PersistentName.APP_END_DATA));
                return;
            case 6:
                this.mSessionTime = values.getAsInteger(DbParams.TABLE_SESSION_INTERVAL_TIME).intValue();
                this.contentResolver.notifyChange(uri, (ContentObserver) null);
                return;
            case 7:
                this.persistentLoginId.commit(values.getAsString(DbParams.PersistentName.LOGIN_ID));
                this.contentResolver.notifyChange(uri, (ContentObserver) null);
                return;
            case 9:
                this.mIsFlushDataState = values.getAsBoolean(DbParams.PersistentName.SUB_PROCESS_FLUSH_DATA).booleanValue();
                return;
            case 12:
                this.persistentRemoteSDKConfig.commit(values.getAsString(DbParams.PersistentName.REMOTE_CONFIG));
                return;
            case 13:
                this.mUserIdsPersistent.commit(values.getAsString(DbParams.PersistentName.PERSISTENT_USER_ID));
                this.contentResolver.notifyChange(uri, (ContentObserver) null);
                return;
            case 14:
                this.mLoginIdKeyPersistent.commit(values.getAsString(DbParams.PersistentName.PERSISTENT_LOGIN_ID_KEY));
                return;
            case 15:
                try {
                    SAStoreManager.getInstance().setString(values.getAsString(DbParams.PUSH_ID_KEY), values.getAsString(DbParams.PUSH_ID_VALUE));
                    return;
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                    return;
                }
            default:
                return;
        }
    }

    public Cursor queryByTable(String tableName, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (!this.isDbWritable) {
            return null;
        }
        try {
            SQLiteDatabase liteDatabase = getWritableDatabase();
            if (liteDatabase != null) {
                return liteDatabase.query(tableName, projection, selection, selectionArgs, (String) null, (String) null, sortOrder);
            }
            return null;
        } catch (SQLiteException e) {
            this.isDbWritable = false;
            SALog.printStackTrace(e);
            return null;
        }
    }

    public Cursor queryPersistent(int code, Uri uri) {
        String column = null;
        Object data = null;
        switch (code) {
            case 2:
                data = Integer.valueOf(this.startActivityCount);
                column = DbParams.TABLE_ACTIVITY_START_COUNT;
                break;
            case 3:
                data = Long.valueOf(this.mAppStartTime);
                column = DbParams.TABLE_APP_START_TIME;
                break;
            case 4:
                data = this.persistentAppEndData.get();
                column = DbParams.PersistentName.APP_END_DATA;
                break;
            case 6:
                data = Integer.valueOf(this.mSessionTime);
                column = DbParams.TABLE_SESSION_INTERVAL_TIME;
                break;
            case 7:
                data = this.persistentLoginId.get();
                column = DbParams.PersistentName.LOGIN_ID;
                break;
            case 9:
                data = Integer.valueOf(this.mIsFlushDataState ? 1 : 0);
                column = DbParams.PersistentName.SUB_PROCESS_FLUSH_DATA;
                break;
            case 12:
                data = this.persistentRemoteSDKConfig.get();
                break;
            case 13:
                data = this.mUserIdsPersistent.get();
                column = DbParams.PersistentName.PERSISTENT_USER_ID;
                break;
            case 14:
                data = this.mLoginIdKeyPersistent.get();
                column = DbParams.PersistentName.PERSISTENT_LOGIN_ID_KEY;
                break;
            case 15:
                try {
                    data = SAStoreManager.getInstance().getString(uri.getQueryParameter(DbParams.PUSH_ID_KEY), "");
                    column = DbParams.PUSH_ID_KEY;
                    break;
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                    return null;
                }
        }
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{column});
        matrixCursor.addRow(new Object[]{data});
        return matrixCursor;
    }

    /* access modifiers changed from: private */
    public SQLiteDatabase getWritableDatabase() {
        try {
            if (!isDBExist()) {
                this.mDbHelper.close();
                this.isDbWritable = true;
            }
            return this.mDbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            SALog.printStackTrace(e);
            this.isDbWritable = false;
            return null;
        }
    }

    private boolean isDBExist() {
        return this.mContext.getDatabasePath(DbParams.DATABASE_NAME).exists();
    }

    public int removeSP(String key) {
        SAStoreManager.getInstance().remove(key);
        return 1;
    }
}
