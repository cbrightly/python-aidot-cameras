package net.sqlcipher.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteTransactionListener;
import android.os.CancellationSignal;
import android.os.Debug;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import androidx.room.FtsOptions;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteQuery;
import com.amazonaws.kinesisvideo.producer.Time;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;
import meshsdk.cache.CacheHandler;
import net.sqlcipher.CrossProcessCursorWrapper;
import net.sqlcipher.Cursor;
import net.sqlcipher.CursorWrapper;
import net.sqlcipher.DatabaseErrorHandler;
import net.sqlcipher.DatabaseUtils;
import net.sqlcipher.DefaultDatabaseErrorHandler;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDebug;

public class SQLiteDatabase extends SQLiteClosable implements SupportSQLiteDatabase {
    private static final String COMMIT_SQL = "COMMIT;";
    public static final int CONFLICT_ABORT = 2;
    public static final int CONFLICT_FAIL = 3;
    public static final int CONFLICT_IGNORE = 4;
    public static final int CONFLICT_NONE = 0;
    public static final int CONFLICT_REPLACE = 5;
    public static final int CONFLICT_ROLLBACK = 1;
    private static final String[] CONFLICT_VALUES = {"", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE "};
    public static final int CREATE_IF_NECESSARY = 268435456;
    private static final Pattern EMAIL_IN_DB_PATTERN = Pattern.compile("[\\w\\.\\-]+@[\\w\\.\\-]+");
    private static final int EVENT_DB_CORRUPT = 75004;
    private static final int EVENT_DB_OPERATION = 52000;
    static final String GET_LOCK_LOG_PREFIX = "GETLOCK:";
    private static final String KEY_ENCODING = "UTF-8";
    private static final int LOCK_ACQUIRED_WARNING_THREAD_TIME_IN_MS = 100;
    private static final int LOCK_ACQUIRED_WARNING_TIME_IN_MS = 300;
    private static final int LOCK_ACQUIRED_WARNING_TIME_IN_MS_ALWAYS_PRINT = 2000;
    private static final int LOCK_WARNING_WINDOW_IN_MS = 20000;
    private static final String LOG_SLOW_QUERIES_PROPERTY = "db.log.slow_query_threshold";
    public static final int MAX_SQL_CACHE_SIZE = 250;
    private static final int MAX_WARNINGS_ON_CACHESIZE_CONDITION = 1;
    public static final String MEMORY = ":memory:";
    public static final int NO_LOCALIZED_COLLATORS = 16;
    public static final int OPEN_READONLY = 1;
    public static final int OPEN_READWRITE = 0;
    private static final int OPEN_READ_MASK = 1;
    private static final int QUERY_LOG_SQL_LENGTH = 64;
    private static final int SLEEP_AFTER_YIELD_QUANTUM = 1000;
    public static final String SQLCIPHER_ANDROID_VERSION = "4.4.2";
    public static final int SQLITE_MAX_LIKE_PATTERN_LENGTH = 50000;
    private static final String TAG = "Database";
    private static WeakHashMap<SQLiteDatabase, Object> sActiveDatabases = new WeakHashMap<>();
    private static int sQueryLogTimeInMillis = 0;
    private int mCacheFullWarnings;
    Map<String, SQLiteCompiledSql> mCompiledQueries;
    private final DatabaseErrorHandler mErrorHandler;
    private CursorFactory mFactory;
    private int mFlags;
    private boolean mInnerTransactionIsSuccessful;
    private long mLastLockMessageTime;
    private String mLastSqlStatement;
    private final ReentrantLock mLock;
    private long mLockAcquiredThreadTime;
    private long mLockAcquiredWallTime;
    private boolean mLockingEnabled;
    private int mMaxSqlCacheSize;
    long mNativeHandle;
    private int mNumCacheHits;
    private int mNumCacheMisses;
    private String mPath;
    private String mPathForLogs;
    private WeakHashMap<SQLiteClosable, Object> mPrograms;
    private final int mSlowQueryThreshold;
    private Throwable mStackTrace;
    private final Map<String, SyncUpdateInfo> mSyncUpdateInfo;
    int mTempTableSequence;
    private String mTimeClosed;
    private String mTimeOpened;
    private boolean mTransactionIsSuccessful;
    private SQLiteTransactionListener mTransactionListener;

    public interface CursorFactory {
        Cursor newCursor(SQLiteDatabase sQLiteDatabase, SQLiteCursorDriver sQLiteCursorDriver, String str, SQLiteQuery sQLiteQuery);
    }

    public interface LibraryLoader {
        void loadLibraries(String... strArr);
    }

    public enum SQLiteDatabaseTransactionType {
        Deferred,
        Immediate,
        Exclusive
    }

    private native void dbclose();

    private native void dbopen(String str, int i);

    private native void enableSqlProfiling(String str);

    private native void enableSqlTracing(String str);

    /* access modifiers changed from: private */
    public native void key(byte[] bArr);

    /* access modifiers changed from: private */
    public native void key_mutf8(char[] cArr);

    private native int native_getDbLookaside();

    private native void native_rawExecSQL(String str);

    private native int native_status(int i, boolean z);

    private native void rekey(byte[] bArr);

    public static native int releaseMemory();

    public static native void setICURoot(String str);

    /* access modifiers changed from: package-private */
    public native int lastChangeCount();

    /* access modifiers changed from: package-private */
    public native long lastInsertRow();

    /* access modifiers changed from: package-private */
    public native void native_execSQL(String str);

    /* access modifiers changed from: package-private */
    public native void native_setLocale(String str, int i);

    public int status(int operation, boolean reset) {
        return native_status(operation, reset);
    }

    public void changePassword(String password) {
        if (!isOpen()) {
            throw new SQLiteException("database not open");
        } else if (password != null) {
            byte[] keyMaterial = getBytes(password.toCharArray());
            rekey(keyMaterial);
            Arrays.fill(keyMaterial, (byte) 0);
        }
    }

    public void changePassword(char[] password) {
        if (!isOpen()) {
            throw new SQLiteException("database not open");
        } else if (password != null) {
            byte[] keyMaterial = getBytes(password);
            rekey(keyMaterial);
            Arrays.fill(keyMaterial, (byte) 0);
        }
    }

    private static void loadICUData(Context context, File workingDir) {
        OutputStream out = null;
        ZipInputStream in = null;
        File icuDir = new File(workingDir, FtsOptions.TOKENIZER_ICU);
        File icuDataFile = new File(icuDir, "icudt46l.dat");
        try {
            if (!icuDir.exists()) {
                icuDir.mkdirs();
            }
            if (!icuDataFile.exists()) {
                in = new ZipInputStream(context.getAssets().open("icudt46l.zip"));
                in.getNextEntry();
                out = new FileOutputStream(icuDataFile);
                byte[] buf = new byte[1024];
                while (true) {
                    int read = in.read(buf);
                    int len = read;
                    if (read <= 0) {
                        break;
                    }
                    out.write(buf, 0, len);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            }
            if (out != null) {
                out.flush();
                out.close();
            }
        } catch (Exception ex) {
            if (icuDataFile.exists()) {
                icuDataFile.delete();
            }
            throw new RuntimeException(ex);
        } catch (Throwable th) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioe2) {
                    throw new RuntimeException(ioe2);
                }
            }
            if (out != null) {
                out.flush();
                out.close();
            }
            throw th;
        }
    }

    public static synchronized void loadLibs(Context context) {
        synchronized (SQLiteDatabase.class) {
            loadLibs(context, context.getFilesDir());
        }
    }

    public static synchronized void loadLibs(Context context, File workingDir) {
        synchronized (SQLiteDatabase.class) {
            loadLibs(context, workingDir, new LibraryLoader() {
                public void loadLibraries(String... libNames) {
                    for (String libName : libNames) {
                        System.loadLibrary(libName);
                    }
                }
            });
        }
    }

    public static synchronized void loadLibs(Context context, LibraryLoader libraryLoader) {
        synchronized (SQLiteDatabase.class) {
            loadLibs(context, context.getFilesDir(), libraryLoader);
        }
    }

    public static synchronized void loadLibs(Context context, File workingDir, LibraryLoader libraryLoader) {
        synchronized (SQLiteDatabase.class) {
            libraryLoader.loadLibraries("sqlcipher");
        }
    }

    /* access modifiers changed from: package-private */
    public void addSQLiteClosable(SQLiteClosable closable) {
        lock();
        try {
            this.mPrograms.put(closable, (Object) null);
        } finally {
            unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public void removeSQLiteClosable(SQLiteClosable closable) {
        lock();
        try {
            this.mPrograms.remove(closable);
        } finally {
            unlock();
        }
    }

    /* access modifiers changed from: protected */
    public void onAllReferencesReleased() {
        if (isOpen()) {
            if (SQLiteDebug.DEBUG_SQL_CACHE) {
                this.mTimeClosed = getTime();
            }
            dbclose();
            synchronized (sActiveDatabases) {
                sActiveDatabases.remove(this);
            }
        }
    }

    public void setLockingEnabled(boolean lockingEnabled) {
        this.mLockingEnabled = lockingEnabled;
    }

    /* access modifiers changed from: package-private */
    public void onCorruption() {
        this.mErrorHandler.onCorruption(this);
    }

    /* access modifiers changed from: package-private */
    public void lock() {
        if (this.mLockingEnabled) {
            this.mLock.lock();
            if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && this.mLock.getHoldCount() == 1) {
                this.mLockAcquiredWallTime = SystemClock.elapsedRealtime();
                this.mLockAcquiredThreadTime = Debug.threadCpuTimeNanos();
            }
        }
    }

    private void lockForced() {
        this.mLock.lock();
        if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && this.mLock.getHoldCount() == 1) {
            this.mLockAcquiredWallTime = SystemClock.elapsedRealtime();
            this.mLockAcquiredThreadTime = Debug.threadCpuTimeNanos();
        }
    }

    /* access modifiers changed from: package-private */
    public void unlock() {
        if (this.mLockingEnabled) {
            if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && this.mLock.getHoldCount() == 1) {
                checkLockHoldTime();
            }
            this.mLock.unlock();
        }
    }

    private void unlockForced() {
        if (SQLiteDebug.DEBUG_LOCK_TIME_TRACKING && this.mLock.getHoldCount() == 1) {
            checkLockHoldTime();
        }
        this.mLock.unlock();
    }

    private void checkLockHoldTime() {
        long elapsedTime = SystemClock.elapsedRealtime();
        long lockedTime = elapsedTime - this.mLockAcquiredWallTime;
        if ((lockedTime >= CacheHandler.delayTime || Log.isLoggable(TAG, 2) || elapsedTime - this.mLastLockMessageTime >= 20000) && lockedTime > 300) {
            int threadTime = (int) ((Debug.threadCpuTimeNanos() - this.mLockAcquiredThreadTime) / Time.NANOS_IN_A_MILLISECOND);
            if (threadTime > 100 || lockedTime > CacheHandler.delayTime) {
                this.mLastLockMessageTime = elapsedTime;
                String str = "lock held on " + this.mPath + " for " + lockedTime + "ms. Thread time was " + threadTime + "ms";
                boolean z = SQLiteDebug.DEBUG_LOCK_TIME_TRACKING_STACK_TRACE;
            }
        }
    }

    public boolean isDatabaseIntegrityOk() {
        Pair<Boolean, String> result = getResultFromPragma("PRAGMA integrity_check;");
        return ((Boolean) result.first).booleanValue() ? ((String) result.second).equals("ok") : ((Boolean) result.first).booleanValue();
    }

    public List<Pair<String, String>> getAttachedDbs() {
        return getAttachedDbs(this);
    }

    public boolean enableWriteAheadLogging() {
        if (!inTransaction()) {
            List<Pair<String, String>> attachedDbs = getAttachedDbs(this);
            if ((attachedDbs != null && attachedDbs.size() > 1) || isReadOnly() || getPath().equals(MEMORY)) {
                return false;
            }
            rawExecSQL("PRAGMA journal_mode = WAL;");
            return true;
        }
        throw new IllegalStateException("Write Ahead Logging cannot be enabled while in a transaction");
    }

    public void disableWriteAheadLogging() {
        if (!inTransaction()) {
            rawExecSQL("PRAGMA journal_mode = DELETE;");
            return;
        }
        throw new IllegalStateException("Write Ahead Logging cannot be disabled while in a transaction");
    }

    public boolean isWriteAheadLoggingEnabled() {
        Pair<Boolean, String> result = getResultFromPragma("PRAGMA journal_mode;");
        return ((Boolean) result.first).booleanValue() ? ((String) result.second).equals("wal") : ((Boolean) result.first).booleanValue();
    }

    public void setForeignKeyConstraintsEnabled(boolean enable) {
        if (!inTransaction()) {
            Object[] objArr = new Object[1];
            objArr[0] = enable ? "ON" : "OFF";
            execSQL(String.format("PRAGMA foreign_keys = %s;", objArr));
            return;
        }
        throw new IllegalStateException("Foreign key constraints may not be changed while in a transaction");
    }

    public void beginTransaction() {
        beginTransactionWithListener((SQLiteTransactionListener) null);
    }

    public void beginTransactionWithListener(SQLiteTransactionListener transactionListener) {
        beginTransactionWithListenerInternal(transactionListener, SQLiteDatabaseTransactionType.Exclusive);
    }

    public void beginTransactionNonExclusive() {
        beginTransactionWithListenerInternal((SQLiteTransactionListener) null, SQLiteDatabaseTransactionType.Immediate);
    }

    public void beginTransactionWithListenerNonExclusive(SQLiteTransactionListener transactionListener) {
        beginTransactionWithListenerInternal(transactionListener, SQLiteDatabaseTransactionType.Immediate);
    }

    public void endTransaction() {
        RuntimeException savedException;
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        } else if (this.mLock.isHeldByCurrentThread()) {
            try {
                if (this.mInnerTransactionIsSuccessful) {
                    this.mInnerTransactionIsSuccessful = false;
                } else {
                    this.mTransactionIsSuccessful = false;
                }
                if (this.mLock.getHoldCount() != 1) {
                    this.mTransactionListener = null;
                    unlockForced();
                    return;
                }
                savedException = null;
                SQLiteTransactionListener sQLiteTransactionListener = this.mTransactionListener;
                if (sQLiteTransactionListener != null) {
                    if (this.mTransactionIsSuccessful) {
                        sQLiteTransactionListener.onCommit();
                    } else {
                        sQLiteTransactionListener.onRollback();
                    }
                }
                if (this.mTransactionIsSuccessful) {
                    execSQL(COMMIT_SQL);
                } else {
                    try {
                        execSQL("ROLLBACK;");
                        if (savedException != null) {
                            throw savedException;
                        }
                    } catch (SQLException e) {
                    }
                }
                this.mTransactionListener = null;
                unlockForced();
            } catch (RuntimeException e2) {
                savedException = e2;
                this.mTransactionIsSuccessful = false;
            } catch (Throwable th) {
                this.mTransactionListener = null;
                unlockForced();
                throw th;
            }
        } else {
            throw new IllegalStateException("no transaction pending");
        }
    }

    public void setTransactionSuccessful() {
        if (!isOpen()) {
            throw new IllegalStateException("database not open");
        } else if (!this.mLock.isHeldByCurrentThread()) {
            throw new IllegalStateException("no transaction pending");
        } else if (!this.mInnerTransactionIsSuccessful) {
            this.mInnerTransactionIsSuccessful = true;
        } else {
            throw new IllegalStateException("setTransactionSuccessful may only be called once per call to beginTransaction");
        }
    }

    public boolean inTransaction() {
        return this.mLock.getHoldCount() > 0;
    }

    public boolean isDbLockedByCurrentThread() {
        return this.mLock.isHeldByCurrentThread();
    }

    public boolean isDbLockedByOtherThreads() {
        return !this.mLock.isHeldByCurrentThread() && this.mLock.isLocked();
    }

    @Deprecated
    public boolean yieldIfContended() {
        if (!isOpen()) {
            return false;
        }
        return yieldIfContendedHelper(false, -1);
    }

    public boolean yieldIfContendedSafely() {
        if (!isOpen()) {
            return false;
        }
        return yieldIfContendedHelper(true, -1);
    }

    public boolean yieldIfContendedSafely(long sleepAfterYieldDelay) {
        if (!isOpen()) {
            return false;
        }
        return yieldIfContendedHelper(true, sleepAfterYieldDelay);
    }

    private boolean yieldIfContendedHelper(boolean checkFullyYielded, long sleepAfterYieldDelay) {
        if (this.mLock.getQueueLength() == 0) {
            this.mLockAcquiredWallTime = SystemClock.elapsedRealtime();
            this.mLockAcquiredThreadTime = Debug.threadCpuTimeNanos();
            return false;
        }
        setTransactionSuccessful();
        SQLiteTransactionListener transactionListener = this.mTransactionListener;
        endTransaction();
        if (!checkFullyYielded || !isDbLockedByCurrentThread()) {
            if (sleepAfterYieldDelay > 0) {
                long remainingDelay = sleepAfterYieldDelay;
                while (remainingDelay > 0) {
                    try {
                        Thread.sleep(remainingDelay < 1000 ? remainingDelay : 1000);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    }
                    remainingDelay -= 1000;
                    if (this.mLock.getQueueLength() == 0) {
                        break;
                    }
                }
            }
            beginTransactionWithListener(transactionListener);
            return true;
        }
        throw new IllegalStateException("Db locked more than once. yielfIfContended cannot yield");
    }

    public Map<String, String> getSyncedTables() {
        HashMap<String, String> tables;
        synchronized (this.mSyncUpdateInfo) {
            tables = new HashMap<>();
            for (String table : this.mSyncUpdateInfo.keySet()) {
                String str = this.mSyncUpdateInfo.get(table).deletedTable;
                if (str != null) {
                    tables.put(table, str);
                }
            }
        }
        return tables;
    }

    public static class SyncUpdateInfo {
        String deletedTable;
        String foreignKey;
        String masterTable;

        SyncUpdateInfo(String masterTable2, String deletedTable2, String foreignKey2) {
            this.masterTable = masterTable2;
            this.deletedTable = deletedTable2;
            this.foreignKey = foreignKey2;
        }
    }

    public static SQLiteDatabase openDatabase(String path, String password, CursorFactory factory, int flags) {
        return openDatabase(path, password, factory, flags, (SQLiteDatabaseHook) null);
    }

    public static SQLiteDatabase openDatabase(String path, char[] password, CursorFactory factory, int flags) {
        return openDatabase(path, password, factory, flags, (SQLiteDatabaseHook) null, (DatabaseErrorHandler) null);
    }

    public static SQLiteDatabase openDatabase(String path, String password, CursorFactory factory, int flags, SQLiteDatabaseHook hook) {
        return openDatabase(path, password, factory, flags, hook, (DatabaseErrorHandler) null);
    }

    public static SQLiteDatabase openDatabase(String path, char[] password, CursorFactory factory, int flags, SQLiteDatabaseHook hook) {
        return openDatabase(path, password, factory, flags, hook, (DatabaseErrorHandler) null);
    }

    public static SQLiteDatabase openDatabase(String path, String password, CursorFactory factory, int flags, SQLiteDatabaseHook hook, DatabaseErrorHandler errorHandler) {
        return openDatabase(path, password == null ? null : password.toCharArray(), factory, flags, hook, errorHandler);
    }

    public static SQLiteDatabase openDatabase(String path, char[] password, CursorFactory factory, int flags, SQLiteDatabaseHook hook, DatabaseErrorHandler errorHandler) {
        return openDatabase(path, getBytes(password), factory, flags, hook, errorHandler);
    }

    public static SQLiteDatabase openDatabase(String path, byte[] password, CursorFactory factory, int flags, SQLiteDatabaseHook hook, DatabaseErrorHandler errorHandler) {
        SQLiteDatabase sqliteDatabase = null;
        DatabaseErrorHandler myErrorHandler = errorHandler != null ? errorHandler : new DefaultDatabaseErrorHandler();
        try {
            sqliteDatabase = new SQLiteDatabase(path, factory, flags, myErrorHandler);
            sqliteDatabase.openDatabaseInternal(password, hook);
        } catch (SQLiteDatabaseCorruptException e) {
            myErrorHandler.onCorruption(sqliteDatabase);
            sqliteDatabase = new SQLiteDatabase(path, factory, flags, myErrorHandler);
            sqliteDatabase.openDatabaseInternal(password, hook);
        }
        if (SQLiteDebug.DEBUG_SQL_STATEMENTS) {
            sqliteDatabase.enableSqlTracing(path);
        }
        if (SQLiteDebug.DEBUG_SQL_TIME) {
            sqliteDatabase.enableSqlProfiling(path);
        }
        synchronized (sActiveDatabases) {
            sActiveDatabases.put(sqliteDatabase, (Object) null);
        }
        return sqliteDatabase;
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, String password, CursorFactory factory, SQLiteDatabaseHook databaseHook) {
        return openOrCreateDatabase(file, password, factory, databaseHook, (DatabaseErrorHandler) null);
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, String password, CursorFactory factory, SQLiteDatabaseHook databaseHook, DatabaseErrorHandler errorHandler) {
        return openOrCreateDatabase(file == null ? null : file.getPath(), password, factory, databaseHook, errorHandler);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, String password, CursorFactory factory, SQLiteDatabaseHook databaseHook) {
        return openDatabase(path, password, factory, (int) CREATE_IF_NECESSARY, databaseHook);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, String password, CursorFactory factory, SQLiteDatabaseHook databaseHook, DatabaseErrorHandler errorHandler) {
        return openDatabase(path, password == null ? null : password.toCharArray(), factory, (int) CREATE_IF_NECESSARY, databaseHook, errorHandler);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, char[] password, CursorFactory factory, SQLiteDatabaseHook databaseHook) {
        return openDatabase(path, password, factory, (int) CREATE_IF_NECESSARY, databaseHook);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, char[] password, CursorFactory factory, SQLiteDatabaseHook databaseHook, DatabaseErrorHandler errorHandler) {
        return openDatabase(path, password, factory, (int) CREATE_IF_NECESSARY, databaseHook, errorHandler);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, byte[] password, CursorFactory factory, SQLiteDatabaseHook databaseHook) {
        return openDatabase(path, password, factory, (int) CREATE_IF_NECESSARY, databaseHook, (DatabaseErrorHandler) null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, byte[] password, CursorFactory factory, SQLiteDatabaseHook databaseHook, DatabaseErrorHandler errorHandler) {
        return openDatabase(path, password, factory, (int) CREATE_IF_NECESSARY, databaseHook, errorHandler);
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, String password, CursorFactory factory) {
        return openOrCreateDatabase(file, password, factory, (SQLiteDatabaseHook) null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, String password, CursorFactory factory) {
        return openDatabase(path, password, factory, (int) CREATE_IF_NECESSARY, (SQLiteDatabaseHook) null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, char[] password, CursorFactory factory) {
        return openDatabase(path, password, factory, (int) CREATE_IF_NECESSARY, (SQLiteDatabaseHook) null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, byte[] password, CursorFactory factory) {
        return openDatabase(path, password, factory, (int) CREATE_IF_NECESSARY, (SQLiteDatabaseHook) null, (DatabaseErrorHandler) null);
    }

    public static SQLiteDatabase create(CursorFactory factory, String password) {
        return openDatabase(MEMORY, password == null ? null : password.toCharArray(), factory, (int) CREATE_IF_NECESSARY);
    }

    public static SQLiteDatabase create(CursorFactory factory, char[] password) {
        return openDatabase(MEMORY, password, factory, (int) CREATE_IF_NECESSARY);
    }

    public void close() {
        if (isOpen()) {
            lock();
            try {
                closeClosable();
                onAllReferencesReleased();
            } finally {
                unlock();
            }
        }
    }

    private void closeClosable() {
        deallocCachedSqlStatements();
        for (Map.Entry<SQLiteClosable, Object> entry : this.mPrograms.entrySet()) {
            SQLiteClosable program = entry.getKey();
            if (program != null) {
                program.onAllReferencesReleasedFromContainer();
            }
        }
    }

    public int getVersion() {
        SQLiteStatement prog = null;
        lock();
        try {
            if (isOpen()) {
                SQLiteStatement prog2 = new SQLiteStatement(this, "PRAGMA user_version;");
                int simpleQueryForLong = (int) prog2.simpleQueryForLong();
                prog2.close();
                unlock();
                return simpleQueryForLong;
            }
            throw new IllegalStateException("database not open");
        } catch (Throwable th) {
            if (prog != null) {
                prog.close();
            }
            unlock();
            throw th;
        }
    }

    public void setVersion(int version) {
        execSQL("PRAGMA user_version = " + version);
    }

    public long getMaximumSize() {
        SQLiteStatement prog = null;
        lock();
        try {
            if (isOpen()) {
                SQLiteStatement prog2 = new SQLiteStatement(this, "PRAGMA max_page_count;");
                long pageSize = getPageSize() * prog2.simpleQueryForLong();
                prog2.close();
                unlock();
                return pageSize;
            }
            throw new IllegalStateException("database not open");
        } catch (Throwable th) {
            if (prog != null) {
                prog.close();
            }
            unlock();
            throw th;
        }
    }

    public long setMaximumSize(long numBytes) {
        SQLiteStatement prog = null;
        lock();
        try {
            if (isOpen()) {
                long pageSize = getPageSize();
                long numPages = numBytes / pageSize;
                if (numBytes % pageSize != 0) {
                    numPages++;
                }
                SQLiteStatement prog2 = new SQLiteStatement(this, "PRAGMA max_page_count = " + numPages);
                long simpleQueryForLong = prog2.simpleQueryForLong() * pageSize;
                prog2.close();
                unlock();
                return simpleQueryForLong;
            }
            throw new IllegalStateException("database not open");
        } catch (Throwable th) {
            if (prog != null) {
                prog.close();
            }
            unlock();
            throw th;
        }
    }

    public long getPageSize() {
        SQLiteStatement prog = null;
        lock();
        try {
            if (isOpen()) {
                SQLiteStatement prog2 = new SQLiteStatement(this, "PRAGMA page_size;");
                long size = prog2.simpleQueryForLong();
                prog2.close();
                unlock();
                return size;
            }
            throw new IllegalStateException("database not open");
        } catch (Throwable th) {
            if (prog != null) {
                prog.close();
            }
            unlock();
            throw th;
        }
    }

    public void setPageSize(long numBytes) {
        execSQL("PRAGMA page_size = " + numBytes);
    }

    public void markTableSyncable(String table, String deletedTable) {
        if (isOpen()) {
            markTableSyncable(table, "_id", table, deletedTable);
            return;
        }
        throw new SQLiteException("database not open");
    }

    public void markTableSyncable(String table, String foreignKey, String updateTable) {
        if (isOpen()) {
            markTableSyncable(table, foreignKey, updateTable, (String) null);
            return;
        }
        throw new SQLiteException("database not open");
    }

    /* JADX INFO: finally extract failed */
    private void markTableSyncable(String table, String foreignKey, String updateTable, String deletedTable) {
        lock();
        try {
            native_execSQL("SELECT _sync_dirty FROM " + updateTable + " LIMIT 0");
            native_execSQL("SELECT " + foreignKey + " FROM " + table + " LIMIT 0");
            unlock();
            SyncUpdateInfo info = new SyncUpdateInfo(updateTable, deletedTable, foreignKey);
            synchronized (this.mSyncUpdateInfo) {
                this.mSyncUpdateInfo.put(table, info);
            }
        } catch (Throwable th) {
            unlock();
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public void rowUpdated(String table, long rowId) {
        SyncUpdateInfo info;
        synchronized (this.mSyncUpdateInfo) {
            info = this.mSyncUpdateInfo.get(table);
        }
        if (info != null) {
            execSQL("UPDATE " + info.masterTable + " SET _sync_dirty=1 WHERE _id=(SELECT " + info.foreignKey + " FROM " + table + " WHERE _id=" + rowId + ")");
        }
    }

    public static String findEditTable(String tables) {
        if (!TextUtils.isEmpty(tables)) {
            int spacepos = tables.indexOf(32);
            int commapos = tables.indexOf(44);
            if (spacepos > 0 && (spacepos < commapos || commapos < 0)) {
                return tables.substring(0, spacepos);
            }
            if (commapos <= 0 || (commapos >= spacepos && spacepos >= 0)) {
                return tables;
            }
            return tables.substring(0, commapos);
        }
        throw new IllegalStateException("Invalid tables");
    }

    public SQLiteStatement compileStatement(String sql) {
        lock();
        try {
            if (isOpen()) {
                return new SQLiteStatement(this, sql);
            }
            throw new IllegalStateException("database not open");
        } finally {
            unlock();
        }
    }

    public Cursor query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return queryWithFactory((CursorFactory) null, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public Cursor queryWithFactory(CursorFactory cursorFactory, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        if (isOpen()) {
            CursorFactory cursorFactory2 = cursorFactory;
            String[] strArr = selectionArgs;
            return rawQueryWithFactory(cursorFactory, SQLiteQueryBuilder.buildQueryString(distinct, table, columns, selection, groupBy, having, orderBy, limit), selectionArgs, findEditTable(table));
        }
        CursorFactory cursorFactory3 = cursorFactory;
        String[] strArr2 = selectionArgs;
        throw new IllegalStateException("database not open");
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return query(false, table, columns, selection, selectionArgs, groupBy, having, orderBy, (String) null);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return query(false, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return rawQueryWithFactory((CursorFactory) null, sql, selectionArgs, (String) null);
    }

    public SQLiteQueryStats getQueryStats(String sql, Object[] args) {
        try {
            execSQL(String.format("CREATE TABLE tempstat AS %s", new Object[]{sql}), args);
            Cursor cursor = rawQuery("SELECT sum(payload) FROM dbstat WHERE name = 'tempstat';", new Object[0]);
            if (cursor == null) {
                return new SQLiteQueryStats(0, 0);
            }
            cursor.moveToFirst();
            long totalPayload = cursor.getLong(0);
            cursor.close();
            Cursor cursor2 = rawQuery("SELECT max(mx_payload) FROM dbstat WHERE name = 'tempstat';", new Object[0]);
            if (cursor2 == null) {
                return new SQLiteQueryStats(totalPayload, 0);
            }
            cursor2.moveToFirst();
            long largestIndividualPayload = cursor2.getLong(0);
            cursor2.close();
            execSQL("DROP TABLE tempstat;");
            return new SQLiteQueryStats(totalPayload, largestIndividualPayload);
        } catch (SQLiteException ex) {
            execSQL("DROP TABLE IF EXISTS tempstat;");
            throw ex;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00ad  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public net.sqlcipher.Cursor rawQuery(java.lang.String r18, java.lang.Object[] r19) {
        /*
            r17 = this;
            r1 = r17
            java.lang.String r2 = ", args are <redacted>, count is "
            java.lang.String r3 = " ms): "
            java.lang.String r4 = "query ("
            java.lang.String r5 = "Database"
            boolean r0 = r17.isOpen()
            if (r0 == 0) goto L_0x00af
            r6 = 0
            int r0 = r1.mSlowQueryThreshold
            r8 = -1
            if (r0 == r8) goto L_0x001c
            long r6 = java.lang.System.currentTimeMillis()
        L_0x001c:
            net.sqlcipher.database.SQLiteDirectCursorDriver r0 = new net.sqlcipher.database.SQLiteDirectCursorDriver
            r9 = 0
            r10 = r18
            r0.<init>(r1, r10, r9)
            r9 = r0
            r11 = 0
            net.sqlcipher.database.SQLiteDatabase$CursorFactory r0 = r1.mFactory     // Catch:{ all -> 0x006f }
            r12 = r19
            net.sqlcipher.Cursor r0 = r9.query((net.sqlcipher.database.SQLiteDatabase.CursorFactory) r0, (java.lang.Object[]) r12)     // Catch:{ all -> 0x006d }
            int r11 = r1.mSlowQueryThreshold
            if (r11 == r8) goto L_0x0067
            r8 = -1
            if (r0 == 0) goto L_0x0039
            int r8 = r0.getCount()
        L_0x0039:
            long r13 = java.lang.System.currentTimeMillis()
            long r13 = r13 - r6
            int r11 = r1.mSlowQueryThreshold
            long r10 = (long) r11
            int r10 = (r13 > r10 ? 1 : (r13 == r10 ? 0 : -1))
            if (r10 < 0) goto L_0x0067
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r4)
            r10.append(r13)
            r10.append(r3)
            java.lang.String r3 = r9.toString()
            r10.append(r3)
            r10.append(r2)
            r10.append(r8)
            java.lang.String r2 = r10.toString()
            android.util.Log.v(r5, r2)
        L_0x0067:
            net.sqlcipher.CrossProcessCursorWrapper r2 = new net.sqlcipher.CrossProcessCursorWrapper
            r2.<init>(r0)
            return r2
        L_0x006d:
            r0 = move-exception
            goto L_0x0072
        L_0x006f:
            r0 = move-exception
            r12 = r19
        L_0x0072:
            int r10 = r1.mSlowQueryThreshold
            if (r10 == r8) goto L_0x00ad
            r8 = -1
            if (r11 == 0) goto L_0x007d
            int r8 = r11.getCount()
        L_0x007d:
            long r13 = java.lang.System.currentTimeMillis()
            long r13 = r13 - r6
            int r10 = r1.mSlowQueryThreshold
            r15 = r6
            long r6 = (long) r10
            int r6 = (r13 > r6 ? 1 : (r13 == r6 ? 0 : -1))
            if (r6 < 0) goto L_0x00ae
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r4)
            r6.append(r13)
            r6.append(r3)
            java.lang.String r3 = r9.toString()
            r6.append(r3)
            r6.append(r2)
            r6.append(r8)
            java.lang.String r2 = r6.toString()
            android.util.Log.v(r5, r2)
            goto L_0x00ae
        L_0x00ad:
            r15 = r6
        L_0x00ae:
            throw r0
        L_0x00af:
            r12 = r19
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "database not open"
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sqlcipher.database.SQLiteDatabase.rawQuery(java.lang.String, java.lang.Object[]):net.sqlcipher.Cursor");
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x007c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public net.sqlcipher.Cursor rawQueryWithFactory(net.sqlcipher.database.SQLiteDatabase.CursorFactory r17, java.lang.String r18, java.lang.String[] r19, java.lang.String r20) {
        /*
            r16 = this;
            r1 = r16
            boolean r0 = r16.isOpen()
            if (r0 == 0) goto L_0x00b2
            r2 = 0
            int r0 = r1.mSlowQueryThreshold
            r4 = -1
            if (r0 == r4) goto L_0x0013
            long r2 = java.lang.System.currentTimeMillis()
        L_0x0013:
            net.sqlcipher.database.SQLiteDirectCursorDriver r0 = new net.sqlcipher.database.SQLiteDirectCursorDriver
            r5 = r18
            r6 = r20
            r0.<init>(r1, r5, r6)
            r7 = r0
            r8 = 0
            java.lang.String r9 = ", args are <redacted>, count is "
            java.lang.String r10 = " ms): "
            java.lang.String r11 = "query ("
            java.lang.String r12 = "Database"
            if (r17 == 0) goto L_0x002c
            r0 = r17
            goto L_0x002e
        L_0x002c:
            net.sqlcipher.database.SQLiteDatabase$CursorFactory r0 = r1.mFactory     // Catch:{ all -> 0x0075 }
        L_0x002e:
            r13 = r19
            net.sqlcipher.Cursor r0 = r7.query(r0, r13)     // Catch:{ all -> 0x0073 }
            int r8 = r1.mSlowQueryThreshold
            if (r8 == r4) goto L_0x006d
            r4 = -1
            if (r0 == 0) goto L_0x003f
            int r4 = r0.getCount()
        L_0x003f:
            long r14 = java.lang.System.currentTimeMillis()
            long r14 = r14 - r2
            int r8 = r1.mSlowQueryThreshold
            long r5 = (long) r8
            int r5 = (r14 > r5 ? 1 : (r14 == r5 ? 0 : -1))
            if (r5 < 0) goto L_0x006d
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r11)
            r5.append(r14)
            r5.append(r10)
            java.lang.String r6 = r7.toString()
            r5.append(r6)
            r5.append(r9)
            r5.append(r4)
            java.lang.String r5 = r5.toString()
            android.util.Log.v(r12, r5)
        L_0x006d:
            net.sqlcipher.CrossProcessCursorWrapper r4 = new net.sqlcipher.CrossProcessCursorWrapper
            r4.<init>(r0)
            return r4
        L_0x0073:
            r0 = move-exception
            goto L_0x0078
        L_0x0075:
            r0 = move-exception
            r13 = r19
        L_0x0078:
            int r5 = r1.mSlowQueryThreshold
            if (r5 == r4) goto L_0x00b1
            r4 = -1
            if (r8 == 0) goto L_0x0083
            int r4 = r8.getCount()
        L_0x0083:
            long r5 = java.lang.System.currentTimeMillis()
            long r5 = r5 - r2
            int r14 = r1.mSlowQueryThreshold
            long r14 = (long) r14
            int r14 = (r5 > r14 ? 1 : (r5 == r14 ? 0 : -1))
            if (r14 < 0) goto L_0x00b1
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r14.append(r11)
            r14.append(r5)
            r14.append(r10)
            java.lang.String r10 = r7.toString()
            r14.append(r10)
            r14.append(r9)
            r14.append(r4)
            java.lang.String r9 = r14.toString()
            android.util.Log.v(r12, r9)
        L_0x00b1:
            throw r0
        L_0x00b2:
            r13 = r19
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "database not open"
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sqlcipher.database.SQLiteDatabase.rawQueryWithFactory(net.sqlcipher.database.SQLiteDatabase$CursorFactory, java.lang.String, java.lang.String[], java.lang.String):net.sqlcipher.Cursor");
    }

    public Cursor rawQuery(String sql, String[] selectionArgs, int initialRead, int maxRead) {
        CursorWrapper cursorWrapper = (CursorWrapper) rawQueryWithFactory((CursorFactory) null, sql, selectionArgs, (String) null);
        ((SQLiteCursor) cursorWrapper.getWrappedCursor()).setLoadStyle(initialRead, maxRead);
        return cursorWrapper;
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        try {
            return insertWithOnConflict(table, nullColumnHack, values, 0);
        } catch (SQLException e) {
            return -1;
        }
    }

    public long insertOrThrow(String table, String nullColumnHack, ContentValues values) {
        return insertWithOnConflict(table, nullColumnHack, values, 0);
    }

    public long replace(String table, String nullColumnHack, ContentValues initialValues) {
        try {
            return insertWithOnConflict(table, nullColumnHack, initialValues, 5);
        } catch (SQLException e) {
            return -1;
        }
    }

    public long replaceOrThrow(String table, String nullColumnHack, ContentValues initialValues) {
        return insertWithOnConflict(table, nullColumnHack, initialValues, 5);
    }

    public long insertWithOnConflict(String table, String nullColumnHack, ContentValues initialValues, int conflictAlgorithm) {
        if (isOpen()) {
            StringBuilder sql = new StringBuilder(152);
            sql.append("INSERT");
            sql.append(CONFLICT_VALUES[conflictAlgorithm]);
            sql.append(" INTO ");
            sql.append(table);
            StringBuilder values = new StringBuilder(40);
            Set<Map.Entry<String, Object>> entrySet = null;
            if (initialValues == null || initialValues.size() <= 0) {
                sql.append("(" + nullColumnHack + ") ");
                values.append("NULL");
            } else {
                entrySet = initialValues.valueSet();
                sql.append('(');
                boolean needSeparator = false;
                for (Map.Entry<String, Object> entry : entrySet) {
                    if (needSeparator) {
                        sql.append(", ");
                        values.append(", ");
                    }
                    needSeparator = true;
                    sql.append(entry.getKey());
                    values.append('?');
                }
                sql.append(')');
            }
            sql.append(" VALUES(");
            sql.append(values);
            sql.append(");");
            lock();
            SQLiteStatement statement = null;
            try {
                SQLiteStatement statement2 = compileStatement(sql.toString());
                if (entrySet != null) {
                    int size = entrySet.size();
                    Iterator<Map.Entry<String, Object>> entriesIter = entrySet.iterator();
                    for (int i = 0; i < size; i++) {
                        DatabaseUtils.bindObjectToProgram(statement2, i + 1, entriesIter.next().getValue());
                    }
                }
                statement2.execute();
                long insertedRowId = lastChangeCount() > 0 ? lastInsertRow() : -1;
                statement2.close();
                unlock();
                return insertedRowId;
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            } catch (Throwable th) {
                if (statement != null) {
                    statement.close();
                }
                unlock();
                throw th;
            }
        } else {
            throw new IllegalStateException("database not open");
        }
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        return delete(table, whereClause, (Object[]) whereArgs);
    }

    public int delete(String table, String whereClause, Object[] whereArgs) {
        String str;
        SQLiteStatement statement = null;
        lock();
        try {
            if (isOpen()) {
                StringBuilder sb = new StringBuilder();
                sb.append("DELETE FROM ");
                sb.append(table);
                if (!TextUtils.isEmpty(whereClause)) {
                    str = " WHERE " + whereClause;
                } else {
                    str = "";
                }
                sb.append(str);
                SQLiteStatement statement2 = compileStatement(sb.toString());
                if (whereArgs != null) {
                    int numArgs = whereArgs.length;
                    for (int i = 0; i < numArgs; i++) {
                        DatabaseUtils.bindObjectToProgram(statement2, i + 1, whereArgs[i]);
                    }
                }
                statement2.execute();
                int lastChangeCount = lastChangeCount();
                statement2.close();
                unlock();
                return lastChangeCount;
            }
            throw new IllegalStateException("database not open");
        } catch (SQLiteDatabaseCorruptException e) {
            onCorruption();
            throw e;
        } catch (Throwable th) {
            if (statement != null) {
                statement.close();
            }
            unlock();
            throw th;
        }
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return updateWithOnConflict(table, values, whereClause, whereArgs, 0);
    }

    public int updateWithOnConflict(String table, ContentValues values, String whereClause, String[] whereArgs, int conflictAlgorithm) {
        if (values == null || values.size() == 0) {
            throw new IllegalArgumentException("Empty values");
        }
        StringBuilder sql = new StringBuilder(120);
        sql.append("UPDATE ");
        sql.append(CONFLICT_VALUES[conflictAlgorithm]);
        sql.append(table);
        sql.append(" SET ");
        Set<Map.Entry<String, Object>> entrySet = values.valueSet();
        Iterator<Map.Entry<String, Object>> entriesIter = entrySet.iterator();
        while (entriesIter.hasNext()) {
            sql.append(entriesIter.next().getKey());
            sql.append("=?");
            if (entriesIter.hasNext()) {
                sql.append(", ");
            }
        }
        if (!TextUtils.isEmpty(whereClause)) {
            sql.append(" WHERE ");
            sql.append(whereClause);
        }
        SQLiteStatement statement = null;
        lock();
        try {
            if (isOpen()) {
                SQLiteStatement statement2 = compileStatement(sql.toString());
                int size = entrySet.size();
                Iterator<Map.Entry<String, Object>> entriesIter2 = entrySet.iterator();
                int bindArg = 1;
                for (int i = 0; i < size; i++) {
                    DatabaseUtils.bindObjectToProgram(statement2, bindArg, entriesIter2.next().getValue());
                    bindArg++;
                }
                if (whereArgs != null) {
                    for (String bindString : whereArgs) {
                        statement2.bindString(bindArg, bindString);
                        bindArg++;
                    }
                }
                statement2.execute();
                int numChangedRows = lastChangeCount();
                statement2.close();
                unlock();
                return numChangedRows;
            }
            throw new IllegalStateException("database not open");
        } catch (SQLiteDatabaseCorruptException e) {
            onCorruption();
            throw e;
        } catch (SQLException e2) {
            throw e2;
        } catch (Throwable th) {
            if (statement != null) {
                statement.close();
            }
            unlock();
            throw th;
        }
    }

    public void execSQL(String sql) {
        long uptimeMillis = SystemClock.uptimeMillis();
        lock();
        try {
            if (isOpen()) {
                native_execSQL(sql);
                unlock();
                return;
            }
            throw new IllegalStateException("database not open");
        } catch (SQLiteDatabaseCorruptException e) {
            onCorruption();
            throw e;
        } catch (Throwable th) {
            unlock();
            throw th;
        }
    }

    public void rawExecSQL(String sql) {
        long uptimeMillis = SystemClock.uptimeMillis();
        lock();
        try {
            if (isOpen()) {
                native_rawExecSQL(sql);
                unlock();
                return;
            }
            throw new IllegalStateException("database not open");
        } catch (SQLiteDatabaseCorruptException e) {
            onCorruption();
            throw e;
        } catch (Throwable th) {
            unlock();
            throw th;
        }
    }

    public void execSQL(String sql, Object[] bindArgs) {
        SQLiteStatement statement = null;
        if (bindArgs != null) {
            long uptimeMillis = SystemClock.uptimeMillis();
            lock();
            try {
                if (isOpen()) {
                    SQLiteStatement statement2 = compileStatement(sql);
                    int numArgs = bindArgs.length;
                    for (int i = 0; i < numArgs; i++) {
                        DatabaseUtils.bindObjectToProgram(statement2, i + 1, bindArgs[i]);
                    }
                    statement2.execute();
                    statement2.close();
                    unlock();
                    return;
                }
                throw new IllegalStateException("database not open");
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            } catch (Throwable th) {
                if (statement != null) {
                    statement.close();
                }
                unlock();
                throw th;
            }
        } else {
            throw new IllegalArgumentException("Empty bindArgs");
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        if (isOpen()) {
            closeClosable();
            onAllReferencesReleased();
        }
    }

    public SQLiteDatabase(String path, char[] password, CursorFactory factory, int flags) {
        this(path, factory, flags, (DatabaseErrorHandler) null);
        openDatabaseInternal(password, (SQLiteDatabaseHook) null);
    }

    public SQLiteDatabase(String path, char[] password, CursorFactory factory, int flags, SQLiteDatabaseHook databaseHook) {
        this(path, factory, flags, (DatabaseErrorHandler) null);
        openDatabaseInternal(password, databaseHook);
    }

    public SQLiteDatabase(String path, byte[] password, CursorFactory factory, int flags, SQLiteDatabaseHook databaseHook) {
        this(path, factory, flags, (DatabaseErrorHandler) null);
        openDatabaseInternal(password, databaseHook);
    }

    private SQLiteDatabase(String path, CursorFactory factory, int flags, DatabaseErrorHandler errorHandler) {
        this.mLock = new ReentrantLock(true);
        this.mLockAcquiredWallTime = 0;
        this.mLockAcquiredThreadTime = 0;
        this.mLastLockMessageTime = 0;
        this.mLastSqlStatement = null;
        this.mNativeHandle = 0;
        this.mTempTableSequence = 0;
        this.mPathForLogs = null;
        this.mCompiledQueries = new HashMap();
        this.mMaxSqlCacheSize = 250;
        this.mTimeOpened = null;
        this.mTimeClosed = null;
        this.mStackTrace = null;
        this.mLockingEnabled = true;
        this.mSyncUpdateInfo = new HashMap();
        if (path != null) {
            this.mFlags = flags;
            this.mPath = path;
            this.mSlowQueryThreshold = -1;
            this.mStackTrace = new DatabaseObjectNotClosedException().fillInStackTrace();
            this.mFactory = factory;
            this.mPrograms = new WeakHashMap<>();
            this.mErrorHandler = errorHandler;
            return;
        }
        throw new IllegalArgumentException("path should not be null");
    }

    private void openDatabaseInternal(char[] password, SQLiteDatabaseHook hook) {
        openDatabaseInternal(getBytes(password), hook);
    }

    private void openDatabaseInternal(final byte[] password, SQLiteDatabaseHook hook) {
        dbopen(this.mPath, this.mFlags);
        try {
            keyDatabase(hook, new Runnable() {
                public void run() {
                    byte[] bArr = password;
                    if (bArr != null && bArr.length > 0) {
                        SQLiteDatabase.this.key(bArr);
                    }
                }
            });
            if (0 != 0) {
                dbclose();
                if (!SQLiteDebug.DEBUG_SQL_CACHE) {
                    return;
                }
                this.mTimeClosed = getTime();
            }
        } catch (RuntimeException ex) {
            final char[] keyMaterial = getChars(password);
            if (containsNull(keyMaterial)) {
                keyDatabase(hook, new Runnable() {
                    public void run() {
                        if (password != null) {
                            SQLiteDatabase.this.key_mutf8(keyMaterial);
                        }
                    }
                });
                if (password != null && password.length > 0) {
                    rekey(password);
                }
                if (keyMaterial != null && keyMaterial.length > 0) {
                    Arrays.fill(keyMaterial, 0);
                }
                if (0 != 0) {
                    dbclose();
                    if (!SQLiteDebug.DEBUG_SQL_CACHE) {
                    }
                }
            } else {
                throw ex;
            }
        } catch (Throwable th) {
            if (1 != 0) {
                dbclose();
                if (SQLiteDebug.DEBUG_SQL_CACHE) {
                    this.mTimeClosed = getTime();
                }
            }
            throw th;
        }
    }

    private boolean containsNull(char[] data) {
        if (data == null || data.length <= 0) {
            return false;
        }
        for (char datum : data) {
            if (datum == 0) {
                return true;
            }
        }
        return false;
    }

    private void keyDatabase(SQLiteDatabaseHook databaseHook, Runnable keyOperation) {
        if (databaseHook != null) {
            databaseHook.preKey(this);
        }
        if (keyOperation != null) {
            keyOperation.run();
        }
        if (databaseHook != null) {
            databaseHook.postKey(this);
        }
        if (SQLiteDebug.DEBUG_SQL_CACHE) {
            this.mTimeOpened = getTime();
        }
        try {
            Cursor cursor = rawQuery("select count(*) from sqlite_master;", new String[0]);
            if (cursor != null) {
                cursor.moveToFirst();
                int i = cursor.getInt(0);
                cursor.close();
            }
        } catch (RuntimeException e) {
            throw e;
        }
    }

    private String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ", Locale.US).format(Long.valueOf(System.currentTimeMillis()));
    }

    public boolean isReadOnly() {
        return (this.mFlags & 1) == 1;
    }

    public boolean isOpen() {
        return this.mNativeHandle != 0;
    }

    public boolean needUpgrade(int newVersion) {
        return newVersion > getVersion();
    }

    public final String getPath() {
        return this.mPath;
    }

    private String getPathForLogs() {
        String str = this.mPathForLogs;
        if (str != null) {
            return str;
        }
        String str2 = this.mPath;
        if (str2 == null) {
            return null;
        }
        if (str2.indexOf(64) == -1) {
            this.mPathForLogs = this.mPath;
        } else {
            this.mPathForLogs = EMAIL_IN_DB_PATTERN.matcher(this.mPath).replaceAll("XX@YY");
        }
        return this.mPathForLogs;
    }

    public void setLocale(Locale locale) {
        lock();
        try {
            native_setLocale(locale.toString(), this.mFlags);
        } finally {
            unlock();
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0031, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addToCompiledQueries(java.lang.String r5, net.sqlcipher.database.SQLiteCompiledSql r6) {
        /*
            r4 = this;
            int r0 = r4.mMaxSqlCacheSize
            if (r0 != 0) goto L_0x0007
            boolean r0 = net.sqlcipher.database.SQLiteDebug.DEBUG_SQL_CACHE
            return
        L_0x0007:
            r0 = 0
            java.util.Map<java.lang.String, net.sqlcipher.database.SQLiteCompiledSql> r1 = r4.mCompiledQueries
            monitor-enter(r1)
            java.util.Map<java.lang.String, net.sqlcipher.database.SQLiteCompiledSql> r2 = r4.mCompiledQueries     // Catch:{ all -> 0x0032 }
            java.lang.Object r2 = r2.get(r5)     // Catch:{ all -> 0x0032 }
            net.sqlcipher.database.SQLiteCompiledSql r2 = (net.sqlcipher.database.SQLiteCompiledSql) r2     // Catch:{ all -> 0x0032 }
            r0 = r2
            if (r0 == 0) goto L_0x0018
            monitor-exit(r1)     // Catch:{ all -> 0x0032 }
            return
        L_0x0018:
            java.util.Map<java.lang.String, net.sqlcipher.database.SQLiteCompiledSql> r2 = r4.mCompiledQueries     // Catch:{ all -> 0x0032 }
            int r2 = r2.size()     // Catch:{ all -> 0x0032 }
            int r3 = r4.mMaxSqlCacheSize     // Catch:{ all -> 0x0032 }
            if (r2 != r3) goto L_0x0029
            int r2 = r4.mCacheFullWarnings     // Catch:{ all -> 0x0032 }
            r3 = 1
            int r2 = r2 + r3
            r4.mCacheFullWarnings = r2     // Catch:{ all -> 0x0032 }
            goto L_0x0030
        L_0x0029:
            java.util.Map<java.lang.String, net.sqlcipher.database.SQLiteCompiledSql> r2 = r4.mCompiledQueries     // Catch:{ all -> 0x0032 }
            r2.put(r5, r6)     // Catch:{ all -> 0x0032 }
            boolean r2 = net.sqlcipher.database.SQLiteDebug.DEBUG_SQL_CACHE     // Catch:{ all -> 0x0032 }
        L_0x0030:
            monitor-exit(r1)     // Catch:{ all -> 0x0032 }
            return
        L_0x0032:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0032 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sqlcipher.database.SQLiteDatabase.addToCompiledQueries(java.lang.String, net.sqlcipher.database.SQLiteCompiledSql):void");
    }

    private void deallocCachedSqlStatements() {
        synchronized (this.mCompiledQueries) {
            for (SQLiteCompiledSql compiledSql : this.mCompiledQueries.values()) {
                compiledSql.releaseSqlStatement();
            }
            this.mCompiledQueries.clear();
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001d, code lost:
        if (r2 == false) goto L_0x0025;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001f, code lost:
        r4.mNumCacheHits++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0025, code lost:
        r4.mNumCacheMisses++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002a, code lost:
        r1 = net.sqlcipher.database.SQLiteDebug.DEBUG_SQL_CACHE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002c, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public net.sqlcipher.database.SQLiteCompiledSql getCompiledStatementForSql(java.lang.String r5) {
        /*
            r4 = this;
            r0 = 0
            java.util.Map<java.lang.String, net.sqlcipher.database.SQLiteCompiledSql> r1 = r4.mCompiledQueries
            monitor-enter(r1)
            int r2 = r4.mMaxSqlCacheSize     // Catch:{ all -> 0x002d }
            if (r2 != 0) goto L_0x000d
            boolean r2 = net.sqlcipher.database.SQLiteDebug.DEBUG_SQL_CACHE     // Catch:{ all -> 0x002d }
            r2 = 0
            monitor-exit(r1)     // Catch:{ all -> 0x002d }
            return r2
        L_0x000d:
            java.util.Map<java.lang.String, net.sqlcipher.database.SQLiteCompiledSql> r2 = r4.mCompiledQueries     // Catch:{ all -> 0x002d }
            java.lang.Object r2 = r2.get(r5)     // Catch:{ all -> 0x002d }
            net.sqlcipher.database.SQLiteCompiledSql r2 = (net.sqlcipher.database.SQLiteCompiledSql) r2     // Catch:{ all -> 0x002d }
            r0 = r2
            r3 = 1
            if (r2 == 0) goto L_0x001b
            r2 = r3
            goto L_0x001c
        L_0x001b:
            r2 = 0
        L_0x001c:
            monitor-exit(r1)     // Catch:{ all -> 0x002d }
            if (r2 == 0) goto L_0x0025
            int r1 = r4.mNumCacheHits
            int r1 = r1 + r3
            r4.mNumCacheHits = r1
            goto L_0x002a
        L_0x0025:
            int r1 = r4.mNumCacheMisses
            int r1 = r1 + r3
            r4.mNumCacheMisses = r1
        L_0x002a:
            boolean r1 = net.sqlcipher.database.SQLiteDebug.DEBUG_SQL_CACHE
            return r0
        L_0x002d:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x002d }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sqlcipher.database.SQLiteDatabase.getCompiledStatementForSql(java.lang.String):net.sqlcipher.database.SQLiteCompiledSql");
    }

    public boolean isInCompiledSqlCache(String sql) {
        boolean containsKey;
        synchronized (this.mCompiledQueries) {
            containsKey = this.mCompiledQueries.containsKey(sql);
        }
        return containsKey;
    }

    public void purgeFromCompiledSqlCache(String sql) {
        synchronized (this.mCompiledQueries) {
            this.mCompiledQueries.remove(sql);
        }
    }

    public void resetCompiledSqlCache() {
        synchronized (this.mCompiledQueries) {
            this.mCompiledQueries.clear();
        }
    }

    public synchronized int getMaxSqlCacheSize() {
        return this.mMaxSqlCacheSize;
    }

    public synchronized void setMaxSqlCacheSize(int cacheSize) {
        if (cacheSize > 250 || cacheSize < 0) {
            throw new IllegalStateException("expected value between 0 and 250");
        } else if (cacheSize >= this.mMaxSqlCacheSize) {
            this.mMaxSqlCacheSize = cacheSize;
        } else {
            throw new IllegalStateException("cannot set cacheSize to a value less than the value set with previous setMaxSqlCacheSize() call.");
        }
    }

    public static byte[] getBytes(char[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(CharBuffer.wrap(data));
        byte[] result = new byte[byteBuffer.limit()];
        byteBuffer.get(result);
        return result;
    }

    public static char[] getChars(byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        CharBuffer charBuffer = Charset.forName("UTF-8").decode(ByteBuffer.wrap(data));
        char[] result = new char[charBuffer.limit()];
        charBuffer.get(result);
        return result;
    }

    public android.database.Cursor query(String query) {
        return rawQuery(query, (String[]) null);
    }

    public android.database.Cursor query(String query, Object[] bindArgs) {
        return rawQuery(query, bindArgs);
    }

    public android.database.Cursor query(SupportSQLiteQuery query) {
        return query(query, (CancellationSignal) null);
    }

    public android.database.Cursor query(SupportSQLiteQuery supportQuery, CancellationSignal cancellationSignal) {
        String sql = supportQuery.getSql();
        SQLiteDirectCursorDriver driver = new SQLiteDirectCursorDriver(this, sql, (String) null);
        SQLiteQuery query = new SQLiteQuery(this, sql, 0, new Object[supportQuery.getArgCount()]);
        supportQuery.bindTo(query);
        return new CrossProcessCursorWrapper(new SQLiteCursor(this, driver, (String) null, query));
    }

    public long insert(String table, int conflictAlgorithm, ContentValues values) {
        return insertWithOnConflict(table, (String) null, values, conflictAlgorithm);
    }

    public int update(String table, int conflictAlgorithm, ContentValues values, String whereClause, Object[] whereArgs) {
        String[] args = new String[whereArgs.length];
        for (int i = 0; i < whereArgs.length; i++) {
            args[i] = whereArgs[i].toString();
        }
        return updateWithOnConflict(table, values, whereClause, args, conflictAlgorithm);
    }

    public void beginTransactionWithListener(final SQLiteTransactionListener transactionListener) {
        beginTransactionWithListener((SQLiteTransactionListener) new SQLiteTransactionListener() {
            public void onBegin() {
                transactionListener.onBegin();
            }

            public void onCommit() {
                transactionListener.onCommit();
            }

            public void onRollback() {
                transactionListener.onRollback();
            }
        });
    }

    public void beginTransactionWithListenerNonExclusive(final SQLiteTransactionListener transactionListener) {
        beginTransactionWithListenerNonExclusive((SQLiteTransactionListener) new SQLiteTransactionListener() {
            public void onBegin() {
                transactionListener.onBegin();
            }

            public void onCommit() {
                transactionListener.onCommit();
            }

            public void onRollback() {
                transactionListener.onRollback();
            }
        });
    }

    private void beginTransactionWithListenerInternal(SQLiteTransactionListener transactionListener, SQLiteDatabaseTransactionType transactionType) {
        lockForced();
        if (isOpen()) {
            try {
                if (this.mLock.getHoldCount() <= 1) {
                    if (transactionType == SQLiteDatabaseTransactionType.Exclusive) {
                        execSQL("BEGIN EXCLUSIVE;");
                    } else if (transactionType == SQLiteDatabaseTransactionType.Immediate) {
                        execSQL("BEGIN IMMEDIATE;");
                    } else if (transactionType == SQLiteDatabaseTransactionType.Deferred) {
                        execSQL("BEGIN DEFERRED;");
                    } else {
                        throw new IllegalArgumentException(String.format("%s is an unsupported transaction type", new Object[]{transactionType}));
                    }
                    this.mTransactionListener = transactionListener;
                    this.mTransactionIsSuccessful = true;
                    this.mInnerTransactionIsSuccessful = false;
                    if (transactionListener != null) {
                        transactionListener.onBegin();
                    }
                    if (1 == 0) {
                        unlockForced();
                    }
                } else if (this.mInnerTransactionIsSuccessful) {
                    throw new IllegalStateException("Cannot call beginTransaction between calling setTransactionSuccessful and endTransaction");
                } else if (1 == 0) {
                    unlockForced();
                }
            } catch (RuntimeException e) {
                execSQL("ROLLBACK;");
                throw e;
            } catch (Throwable th) {
                if (0 == 0) {
                    unlockForced();
                }
                throw th;
            }
        } else {
            throw new IllegalStateException("database not open");
        }
    }

    static ArrayList<SQLiteDebug.DbStats> getDbStats() {
        int indx;
        String dbName;
        int idx;
        ArrayList<SQLiteDebug.DbStats> dbStatsList = new ArrayList<>();
        Iterator<SQLiteDatabase> it = getActiveDatabases().iterator();
        while (it.hasNext()) {
            SQLiteDatabase db = it.next();
            if (db != null && db.isOpen()) {
                int lookasideUsed = db.native_getDbLookaside();
                String path = db.getPath();
                int indx2 = path.lastIndexOf("/");
                if (indx2 != -1) {
                    indx = indx2 + 1;
                    int i = indx;
                } else {
                    int i2 = indx2;
                    indx = 0;
                }
                String lastnode = path.substring(indx);
                ArrayList<Pair<String, String>> attachedDbs = getAttachedDbs(db);
                if (attachedDbs != null) {
                    for (int i3 = 0; i3 < attachedDbs.size(); i3++) {
                        Pair<String, String> p = attachedDbs.get(i3);
                        long pageCount = getPragmaVal(db, ((String) p.first) + ".page_count;");
                        if (i3 == 0) {
                            dbName = lastnode;
                        } else {
                            lookasideUsed = 0;
                            dbName = "  (attached) " + ((String) p.first);
                            if (((String) p.second).trim().length() > 0) {
                                int idx2 = ((String) p.second).lastIndexOf("/");
                                StringBuilder sb = new StringBuilder();
                                sb.append(dbName);
                                sb.append(" : ");
                                String str = (String) p.second;
                                if (idx2 != -1) {
                                    idx = idx2 + 1;
                                    int i4 = idx;
                                } else {
                                    int i5 = idx2;
                                    idx = 0;
                                }
                                sb.append(str.substring(idx));
                                dbName = sb.toString();
                            }
                        }
                        if (pageCount > 0) {
                            dbStatsList.add(new SQLiteDebug.DbStats(dbName, pageCount, db.getPageSize(), lookasideUsed));
                        }
                    }
                }
            }
        }
        return dbStatsList;
    }

    private static ArrayList<SQLiteDatabase> getActiveDatabases() {
        ArrayList<SQLiteDatabase> databases = new ArrayList<>();
        synchronized (sActiveDatabases) {
            databases.addAll(sActiveDatabases.keySet());
        }
        return databases;
    }

    private static long getPragmaVal(SQLiteDatabase db, String pragma) {
        if (!db.isOpen()) {
            return 0;
        }
        SQLiteStatement prog = null;
        try {
            SQLiteStatement prog2 = new SQLiteStatement(db, "PRAGMA " + pragma);
            long val = prog2.simpleQueryForLong();
            prog2.close();
            return val;
        } catch (Throwable th) {
            if (prog != null) {
                prog.close();
            }
            throw th;
        }
    }

    private static ArrayList<Pair<String, String>> getAttachedDbs(SQLiteDatabase dbObj) {
        if (!dbObj.isOpen()) {
            return null;
        }
        ArrayList<Pair<String, String>> attachedDbs = new ArrayList<>();
        Cursor c = dbObj.rawQuery("pragma database_list;", (String[]) null);
        while (c.moveToNext()) {
            attachedDbs.add(new Pair(c.getString(1), c.getString(2)));
        }
        c.close();
        return attachedDbs;
    }

    private Pair<Boolean, String> getResultFromPragma(String command) {
        Cursor cursor = rawQuery(command, new Object[0]);
        if (cursor == null) {
            return new Pair<>(false, "");
        }
        cursor.moveToFirst();
        String value = cursor.getString(0);
        cursor.close();
        return new Pair<>(true, value);
    }
}
