package net.sqlcipher.database;

import android.content.Context;
import android.util.Log;
import java.io.File;
import net.sqlcipher.DatabaseErrorHandler;
import net.sqlcipher.DefaultDatabaseErrorHandler;
import net.sqlcipher.database.SQLiteDatabase;

public abstract class SQLiteOpenHelper {
    private static final String TAG = SQLiteOpenHelper.class.getSimpleName();
    private final Context mContext;
    private SQLiteDatabase mDatabase;
    private boolean mDeferSetWriteAheadLoggingEnabled;
    private boolean mEnableWriteAheadLogging;
    private final DatabaseErrorHandler mErrorHandler;
    private final SQLiteDatabase.CursorFactory mFactory;
    private final SQLiteDatabaseHook mHook;
    private boolean mIsInitializing;
    private final String mName;
    private final int mNewVersion;

    public abstract void onCreate(SQLiteDatabase sQLiteDatabase);

    public abstract void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2);

    public SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        this(context, name, factory, version, (SQLiteDatabaseHook) null, new DefaultDatabaseErrorHandler());
    }

    public SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, SQLiteDatabaseHook hook) {
        this(context, name, factory, version, hook, new DefaultDatabaseErrorHandler());
    }

    public SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, SQLiteDatabaseHook hook, DatabaseErrorHandler errorHandler) {
        this.mDatabase = null;
        this.mIsInitializing = false;
        if (version < 1) {
            throw new IllegalArgumentException("Version must be >= 1, was " + version);
        } else if (errorHandler != null) {
            this.mContext = context;
            this.mName = name;
            this.mFactory = factory;
            this.mNewVersion = version;
            this.mHook = hook;
            this.mErrorHandler = errorHandler;
        } else {
            throw new IllegalArgumentException("DatabaseErrorHandler param value can't be null.");
        }
    }

    public synchronized SQLiteDatabase getWritableDatabase(String password) {
        return getWritableDatabase(password == null ? null : password.toCharArray());
    }

    public synchronized SQLiteDatabase getWritableDatabase(char[] password) {
        return getWritableDatabase(password == null ? null : SQLiteDatabase.getBytes(password));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00bc, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized net.sqlcipher.database.SQLiteDatabase getWritableDatabase(byte[] r9) {
        /*
            r8 = this;
            monitor-enter(r8)
            net.sqlcipher.database.SQLiteDatabase r0 = r8.mDatabase     // Catch:{ all -> 0x00e8 }
            if (r0 == 0) goto L_0x0017
            boolean r0 = r0.isOpen()     // Catch:{ all -> 0x00e8 }
            if (r0 == 0) goto L_0x0017
            net.sqlcipher.database.SQLiteDatabase r0 = r8.mDatabase     // Catch:{ all -> 0x00e8 }
            boolean r0 = r0.isReadOnly()     // Catch:{ all -> 0x00e8 }
            if (r0 != 0) goto L_0x0017
            net.sqlcipher.database.SQLiteDatabase r0 = r8.mDatabase     // Catch:{ all -> 0x00e8 }
            monitor-exit(r8)
            return r0
        L_0x0017:
            boolean r0 = r8.mIsInitializing     // Catch:{ all -> 0x00e8 }
            if (r0 != 0) goto L_0x00e0
            r0 = 0
            r1 = 0
            net.sqlcipher.database.SQLiteDatabase r2 = r8.mDatabase     // Catch:{ all -> 0x00e8 }
            if (r2 == 0) goto L_0x0024
            r2.lock()     // Catch:{ all -> 0x00e8 }
        L_0x0024:
            r2 = 1
            r3 = 0
            r8.mIsInitializing = r2     // Catch:{ all -> 0x00bd }
            java.lang.String r2 = r8.mName     // Catch:{ all -> 0x00bd }
            if (r2 != 0) goto L_0x0035
            r2 = 0
            java.lang.String r4 = ""
            net.sqlcipher.database.SQLiteDatabase r2 = net.sqlcipher.database.SQLiteDatabase.create((net.sqlcipher.database.SQLiteDatabase.CursorFactory) r2, (java.lang.String) r4)     // Catch:{ all -> 0x00bd }
            r1 = r2
            goto L_0x005c
        L_0x0035:
            android.content.Context r4 = r8.mContext     // Catch:{ all -> 0x00bd }
            java.io.File r2 = r4.getDatabasePath(r2)     // Catch:{ all -> 0x00bd }
            java.lang.String r2 = r2.getPath()     // Catch:{ all -> 0x00bd }
            java.io.File r4 = new java.io.File     // Catch:{ all -> 0x00bd }
            r4.<init>(r2)     // Catch:{ all -> 0x00bd }
            boolean r5 = r4.exists()     // Catch:{ all -> 0x00bd }
            if (r5 != 0) goto L_0x0051
            java.io.File r5 = r4.getParentFile()     // Catch:{ all -> 0x00bd }
            r5.mkdirs()     // Catch:{ all -> 0x00bd }
        L_0x0051:
            net.sqlcipher.database.SQLiteDatabase$CursorFactory r5 = r8.mFactory     // Catch:{ all -> 0x00bd }
            net.sqlcipher.database.SQLiteDatabaseHook r6 = r8.mHook     // Catch:{ all -> 0x00bd }
            net.sqlcipher.DatabaseErrorHandler r7 = r8.mErrorHandler     // Catch:{ all -> 0x00bd }
            net.sqlcipher.database.SQLiteDatabase r5 = net.sqlcipher.database.SQLiteDatabase.openOrCreateDatabase((java.lang.String) r2, (byte[]) r9, (net.sqlcipher.database.SQLiteDatabase.CursorFactory) r5, (net.sqlcipher.database.SQLiteDatabaseHook) r6, (net.sqlcipher.DatabaseErrorHandler) r7)     // Catch:{ all -> 0x00bd }
            r1 = r5
        L_0x005c:
            boolean r2 = r8.mDeferSetWriteAheadLoggingEnabled     // Catch:{ all -> 0x00bd }
            if (r2 == 0) goto L_0x0066
            boolean r2 = r1.enableWriteAheadLogging()     // Catch:{ all -> 0x00bd }
            r8.mEnableWriteAheadLogging = r2     // Catch:{ all -> 0x00bd }
        L_0x0066:
            r8.onConfigure(r1)     // Catch:{ all -> 0x00bd }
            int r2 = r1.getVersion()     // Catch:{ all -> 0x00bd }
            int r4 = r8.mNewVersion     // Catch:{ all -> 0x00bd }
            if (r2 == r4) goto L_0x0097
            r1.beginTransaction()     // Catch:{ all -> 0x00bd }
            if (r2 != 0) goto L_0x007a
            r8.onCreate(r1)     // Catch:{ all -> 0x0091 }
            goto L_0x0085
        L_0x007a:
            int r4 = r8.mNewVersion     // Catch:{ all -> 0x0091 }
            if (r2 <= r4) goto L_0x0082
            r8.onDowngrade(r1, r2, r4)     // Catch:{ all -> 0x0091 }
            goto L_0x0085
        L_0x0082:
            r8.onUpgrade(r1, r2, r4)     // Catch:{ all -> 0x0091 }
        L_0x0085:
            int r4 = r8.mNewVersion     // Catch:{ all -> 0x0091 }
            r1.setVersion(r4)     // Catch:{ all -> 0x0091 }
            r1.setTransactionSuccessful()     // Catch:{ all -> 0x0091 }
            r1.endTransaction()     // Catch:{ all -> 0x00bd }
            goto L_0x0097
        L_0x0091:
            r4 = move-exception
            r1.endTransaction()     // Catch:{ all -> 0x00bd }
            throw r4     // Catch:{ all -> 0x00bd }
        L_0x0097:
            r8.onOpen(r1)     // Catch:{ all -> 0x00bd }
            r0 = 1
            r8.mIsInitializing = r3     // Catch:{ all -> 0x00e8 }
            if (r0 == 0) goto L_0x00b1
            net.sqlcipher.database.SQLiteDatabase r3 = r8.mDatabase     // Catch:{ all -> 0x00e8 }
            if (r3 == 0) goto L_0x00ae
            r3.close()     // Catch:{ Exception -> 0x00a8 }
            goto L_0x00a9
        L_0x00a8:
            r3 = move-exception
        L_0x00a9:
            net.sqlcipher.database.SQLiteDatabase r3 = r8.mDatabase     // Catch:{ all -> 0x00e8 }
            r3.unlock()     // Catch:{ all -> 0x00e8 }
        L_0x00ae:
            r8.mDatabase = r1     // Catch:{ all -> 0x00e8 }
            goto L_0x00bb
        L_0x00b1:
            net.sqlcipher.database.SQLiteDatabase r3 = r8.mDatabase     // Catch:{ all -> 0x00e8 }
            if (r3 == 0) goto L_0x00b8
            r3.unlock()     // Catch:{ all -> 0x00e8 }
        L_0x00b8:
            r1.close()     // Catch:{ all -> 0x00e8 }
        L_0x00bb:
            monitor-exit(r8)
            return r1
        L_0x00bd:
            r2 = move-exception
            r8.mIsInitializing = r3     // Catch:{ all -> 0x00e8 }
            if (r0 == 0) goto L_0x00d3
            net.sqlcipher.database.SQLiteDatabase r3 = r8.mDatabase     // Catch:{ all -> 0x00e8 }
            if (r3 == 0) goto L_0x00d0
            r3.close()     // Catch:{ Exception -> 0x00ca }
            goto L_0x00cb
        L_0x00ca:
            r3 = move-exception
        L_0x00cb:
            net.sqlcipher.database.SQLiteDatabase r3 = r8.mDatabase     // Catch:{ all -> 0x00e8 }
            r3.unlock()     // Catch:{ all -> 0x00e8 }
        L_0x00d0:
            r8.mDatabase = r1     // Catch:{ all -> 0x00e8 }
            goto L_0x00df
        L_0x00d3:
            net.sqlcipher.database.SQLiteDatabase r3 = r8.mDatabase     // Catch:{ all -> 0x00e8 }
            if (r3 == 0) goto L_0x00da
            r3.unlock()     // Catch:{ all -> 0x00e8 }
        L_0x00da:
            if (r1 == 0) goto L_0x00df
            r1.close()     // Catch:{ all -> 0x00e8 }
        L_0x00df:
            throw r2     // Catch:{ all -> 0x00e8 }
        L_0x00e0:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00e8 }
            java.lang.String r1 = "getWritableDatabase called recursively"
            r0.<init>(r1)     // Catch:{ all -> 0x00e8 }
            throw r0     // Catch:{ all -> 0x00e8 }
        L_0x00e8:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sqlcipher.database.SQLiteOpenHelper.getWritableDatabase(byte[]):net.sqlcipher.database.SQLiteDatabase");
    }

    public synchronized SQLiteDatabase getReadableDatabase(String password) {
        return getReadableDatabase(password == null ? null : password.toCharArray());
    }

    public synchronized SQLiteDatabase getReadableDatabase(char[] password) {
        return getReadableDatabase(password == null ? null : SQLiteDatabase.getBytes(password));
    }

    public synchronized SQLiteDatabase getReadableDatabase(byte[] password) {
        SQLiteDatabase db;
        SQLiteDatabase sQLiteDatabase = this.mDatabase;
        if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
            return this.mDatabase;
        } else if (!this.mIsInitializing) {
            try {
                return getWritableDatabase(password);
            } catch (SQLiteException e) {
                if (this.mName != null) {
                    String str = TAG;
                    Log.e(str, "Couldn't open " + this.mName + " for writing (will try read-only):", e);
                    db = null;
                    this.mIsInitializing = true;
                    String path = this.mContext.getDatabasePath(this.mName).getPath();
                    File databasePath = new File(path);
                    File databasesDirectory = new File(this.mContext.getDatabasePath(this.mName).getParent());
                    if (!databasesDirectory.exists()) {
                        databasesDirectory.mkdirs();
                    }
                    if (!databasePath.exists()) {
                        this.mIsInitializing = false;
                        this.mIsInitializing = true;
                        getWritableDatabase(password).close();
                    }
                    db = SQLiteDatabase.openDatabase(path, password, this.mFactory, 1, this.mHook, this.mErrorHandler);
                    if (db.getVersion() == this.mNewVersion) {
                        onOpen(db);
                        Log.w(str, "Opened " + this.mName + " in read-only mode");
                        this.mDatabase = db;
                        this.mIsInitializing = false;
                        if (db != db) {
                            db.close();
                        }
                        return db;
                    }
                    throw new SQLiteException("Can't upgrade read-only database from version " + db.getVersion() + " to " + this.mNewVersion + ": " + path);
                }
                throw e;
            } catch (Throwable th) {
                this.mIsInitializing = false;
                if (!(db == null || db == this.mDatabase)) {
                    db.close();
                }
                throw th;
            }
        } else {
            throw new IllegalStateException("getReadableDatabase called recursively");
        }
    }

    public synchronized void close() {
        if (!this.mIsInitializing) {
            SQLiteDatabase sQLiteDatabase = this.mDatabase;
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                this.mDatabase.close();
                this.mDatabase = null;
            }
        } else {
            throw new IllegalStateException("Closed during initialization");
        }
    }

    public String getDatabaseName() {
        return this.mName;
    }

    public void setWriteAheadLoggingEnabled(boolean enabled) {
        synchronized (this) {
            if (this.mEnableWriteAheadLogging != enabled) {
                SQLiteDatabase sQLiteDatabase = this.mDatabase;
                if (sQLiteDatabase == null || !sQLiteDatabase.isOpen() || this.mDatabase.isReadOnly()) {
                    this.mDeferSetWriteAheadLoggingEnabled = enabled;
                } else {
                    if (enabled) {
                        this.mDatabase.enableWriteAheadLogging();
                    } else {
                        this.mDatabase.disableWriteAheadLogging();
                    }
                    this.mEnableWriteAheadLogging = enabled;
                }
            }
        }
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new SQLiteException("Can't downgrade database from version " + oldVersion + " to " + newVersion);
    }

    public void onConfigure(SQLiteDatabase db) {
    }

    public void onOpen(SQLiteDatabase db) {
    }
}
