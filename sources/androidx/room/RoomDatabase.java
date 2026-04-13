package androidx.room;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.WorkerThread;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.room.migration.Migration;
import androidx.room.util.SneakyThrow;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class RoomDatabase {
    private static final String DB_IMPL_SUFFIX = "_Impl";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final int MAX_BIND_PARAMETER_CNT = 999;
    private boolean mAllowMainThreadQueries;
    private final Map<String, Object> mBackingFieldMap = new ConcurrentHashMap();
    @Deprecated
    @Nullable
    protected List<Callback> mCallbacks;
    private final ReentrantReadWriteLock mCloseLock = new ReentrantReadWriteLock();
    @Deprecated
    protected volatile SupportSQLiteDatabase mDatabase;
    private final InvalidationTracker mInvalidationTracker = createInvalidationTracker();
    private SupportSQLiteOpenHelper mOpenHelper;
    private Executor mQueryExecutor;
    private final ThreadLocal<Integer> mSuspendingTransactionId = new ThreadLocal<>();
    private Executor mTransactionExecutor;
    boolean mWriteAheadLoggingEnabled;

    @WorkerThread
    public abstract void clearAllTables();

    /* access modifiers changed from: protected */
    @NonNull
    public abstract InvalidationTracker createInvalidationTracker();

    /* access modifiers changed from: protected */
    @NonNull
    public abstract SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration databaseConfiguration);

    /* access modifiers changed from: package-private */
    public Lock getCloseLock() {
        return this.mCloseLock.readLock();
    }

    /* access modifiers changed from: package-private */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public ThreadLocal<Integer> getSuspendingTransactionId() {
        return this.mSuspendingTransactionId;
    }

    /* access modifiers changed from: package-private */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Map<String, Object> getBackingFieldMap() {
        return this.mBackingFieldMap;
    }

    @CallSuper
    public void init(@NonNull DatabaseConfiguration configuration) {
        SupportSQLiteOpenHelper createOpenHelper = createOpenHelper(configuration);
        this.mOpenHelper = createOpenHelper;
        if (createOpenHelper instanceof SQLiteCopyOpenHelper) {
            ((SQLiteCopyOpenHelper) createOpenHelper).setDatabaseConfiguration(configuration);
        }
        boolean wal = false;
        if (Build.VERSION.SDK_INT >= 16) {
            wal = configuration.journalMode == JournalMode.WRITE_AHEAD_LOGGING;
            this.mOpenHelper.setWriteAheadLoggingEnabled(wal);
        }
        this.mCallbacks = configuration.callbacks;
        this.mQueryExecutor = configuration.queryExecutor;
        this.mTransactionExecutor = new TransactionExecutor(configuration.transactionExecutor);
        this.mAllowMainThreadQueries = configuration.allowMainThreadQueries;
        this.mWriteAheadLoggingEnabled = wal;
        if (configuration.multiInstanceInvalidation) {
            this.mInvalidationTracker.startMultiInstanceInvalidation(configuration.context, configuration.name);
        }
    }

    @NonNull
    public SupportSQLiteOpenHelper getOpenHelper() {
        return this.mOpenHelper;
    }

    public boolean isOpen() {
        SupportSQLiteDatabase db = this.mDatabase;
        return db != null && db.isOpen();
    }

    public void close() {
        if (isOpen()) {
            Lock closeLock = this.mCloseLock.writeLock();
            try {
                closeLock.lock();
                this.mInvalidationTracker.stopMultiInstanceInvalidation();
                this.mOpenHelper.close();
            } finally {
                closeLock.unlock();
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void assertNotMainThread() {
        if (!this.mAllowMainThreadQueries && isMainThread()) {
            throw new IllegalStateException("Cannot access database on the main thread since it may potentially lock the UI for a long period of time.");
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void assertNotSuspendingTransaction() {
        if (!inTransaction() && this.mSuspendingTransactionId.get() != null) {
            throw new IllegalStateException("Cannot access database on a different coroutine context inherited from a suspending transaction.");
        }
    }

    @NonNull
    public Cursor query(@NonNull String query, @Nullable Object[] args) {
        return this.mOpenHelper.getWritableDatabase().query((SupportSQLiteQuery) new SimpleSQLiteQuery(query, args));
    }

    @NonNull
    public Cursor query(@NonNull SupportSQLiteQuery query) {
        return query(query, (CancellationSignal) null);
    }

    @NonNull
    public Cursor query(@NonNull SupportSQLiteQuery query, @Nullable CancellationSignal signal) {
        assertNotMainThread();
        assertNotSuspendingTransaction();
        if (signal == null || Build.VERSION.SDK_INT < 16) {
            return this.mOpenHelper.getWritableDatabase().query(query);
        }
        return this.mOpenHelper.getWritableDatabase().query(query, signal);
    }

    public SupportSQLiteStatement compileStatement(@NonNull String sql) {
        assertNotMainThread();
        assertNotSuspendingTransaction();
        return this.mOpenHelper.getWritableDatabase().compileStatement(sql);
    }

    @Deprecated
    public void beginTransaction() {
        assertNotMainThread();
        SupportSQLiteDatabase database = this.mOpenHelper.getWritableDatabase();
        this.mInvalidationTracker.syncTriggers(database);
        database.beginTransaction();
    }

    @Deprecated
    public void endTransaction() {
        this.mOpenHelper.getWritableDatabase().endTransaction();
        if (!inTransaction()) {
            this.mInvalidationTracker.refreshVersionsAsync();
        }
    }

    @NonNull
    public Executor getQueryExecutor() {
        return this.mQueryExecutor;
    }

    @NonNull
    public Executor getTransactionExecutor() {
        return this.mTransactionExecutor;
    }

    @Deprecated
    public void setTransactionSuccessful() {
        this.mOpenHelper.getWritableDatabase().setTransactionSuccessful();
    }

    public void runInTransaction(@NonNull Runnable body) {
        beginTransaction();
        try {
            body.run();
            setTransactionSuccessful();
        } finally {
            endTransaction();
        }
    }

    public <V> V runInTransaction(@NonNull Callable<V> body) {
        beginTransaction();
        try {
            V result = body.call();
            setTransactionSuccessful();
            endTransaction();
            return result;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            SneakyThrow.reThrow(e2);
            endTransaction();
            return null;
        } catch (Throwable result2) {
            endTransaction();
            throw result2;
        }
    }

    /* access modifiers changed from: protected */
    public void internalInitInvalidationTracker(@NonNull SupportSQLiteDatabase db) {
        this.mInvalidationTracker.internalInit(db);
    }

    @NonNull
    public InvalidationTracker getInvalidationTracker() {
        return this.mInvalidationTracker;
    }

    public boolean inTransaction() {
        return this.mOpenHelper.getWritableDatabase().inTransaction();
    }

    public enum JournalMode {
        AUTOMATIC,
        TRUNCATE,
        WRITE_AHEAD_LOGGING;

        /* access modifiers changed from: package-private */
        @SuppressLint({"NewApi"})
        public JournalMode resolve(Context context) {
            ActivityManager manager;
            if (this != AUTOMATIC) {
                return this;
            }
            if (Build.VERSION.SDK_INT < 16 || (manager = (ActivityManager) context.getSystemService("activity")) == null || isLowRamDevice(manager)) {
                return TRUNCATE;
            }
            return WRITE_AHEAD_LOGGING;
        }

        private static boolean isLowRamDevice(@NonNull ActivityManager activityManager) {
            if (Build.VERSION.SDK_INT >= 19) {
                return activityManager.isLowRamDevice();
            }
            return false;
        }
    }

    public static class Builder<T extends RoomDatabase> {
        private boolean mAllowDestructiveMigrationOnDowngrade;
        private boolean mAllowMainThreadQueries;
        private ArrayList<Callback> mCallbacks;
        private final Context mContext;
        private String mCopyFromAssetPath;
        private File mCopyFromFile;
        private final Class<T> mDatabaseClass;
        private SupportSQLiteOpenHelper.Factory mFactory;
        private JournalMode mJournalMode = JournalMode.AUTOMATIC;
        private final MigrationContainer mMigrationContainer = new MigrationContainer();
        private Set<Integer> mMigrationStartAndEndVersions;
        private Set<Integer> mMigrationsNotRequiredFrom;
        private boolean mMultiInstanceInvalidation;
        private final String mName;
        private Executor mQueryExecutor;
        private boolean mRequireMigration = true;
        private Executor mTransactionExecutor;

        Builder(@NonNull Context context, @NonNull Class<T> klass, @Nullable String name) {
            this.mContext = context;
            this.mDatabaseClass = klass;
            this.mName = name;
        }

        @NonNull
        public Builder<T> createFromAsset(@NonNull String databaseFilePath) {
            this.mCopyFromAssetPath = databaseFilePath;
            return this;
        }

        @NonNull
        public Builder<T> createFromFile(@NonNull File databaseFile) {
            this.mCopyFromFile = databaseFile;
            return this;
        }

        @NonNull
        public Builder<T> openHelperFactory(@Nullable SupportSQLiteOpenHelper.Factory factory) {
            this.mFactory = factory;
            return this;
        }

        @NonNull
        public Builder<T> addMigrations(@NonNull Migration... migrations) {
            if (this.mMigrationStartAndEndVersions == null) {
                this.mMigrationStartAndEndVersions = new HashSet();
            }
            for (Migration migration : migrations) {
                this.mMigrationStartAndEndVersions.add(Integer.valueOf(migration.startVersion));
                this.mMigrationStartAndEndVersions.add(Integer.valueOf(migration.endVersion));
            }
            this.mMigrationContainer.addMigrations(migrations);
            return this;
        }

        @NonNull
        public Builder<T> allowMainThreadQueries() {
            this.mAllowMainThreadQueries = true;
            return this;
        }

        @NonNull
        public Builder<T> setJournalMode(@NonNull JournalMode journalMode) {
            this.mJournalMode = journalMode;
            return this;
        }

        @NonNull
        public Builder<T> setQueryExecutor(@NonNull Executor executor) {
            this.mQueryExecutor = executor;
            return this;
        }

        @NonNull
        public Builder<T> setTransactionExecutor(@NonNull Executor executor) {
            this.mTransactionExecutor = executor;
            return this;
        }

        @NonNull
        public Builder<T> enableMultiInstanceInvalidation() {
            this.mMultiInstanceInvalidation = this.mName != null;
            return this;
        }

        @NonNull
        public Builder<T> fallbackToDestructiveMigration() {
            this.mRequireMigration = false;
            this.mAllowDestructiveMigrationOnDowngrade = true;
            return this;
        }

        @NonNull
        public Builder<T> fallbackToDestructiveMigrationOnDowngrade() {
            this.mRequireMigration = true;
            this.mAllowDestructiveMigrationOnDowngrade = true;
            return this;
        }

        @NonNull
        public Builder<T> fallbackToDestructiveMigrationFrom(int... startVersions) {
            if (this.mMigrationsNotRequiredFrom == null) {
                this.mMigrationsNotRequiredFrom = new HashSet(startVersions.length);
            }
            for (int startVersion : startVersions) {
                this.mMigrationsNotRequiredFrom.add(Integer.valueOf(startVersion));
            }
            return this;
        }

        @NonNull
        public Builder<T> addCallback(@NonNull Callback callback) {
            if (this.mCallbacks == null) {
                this.mCallbacks = new ArrayList<>();
            }
            this.mCallbacks.add(callback);
            return this;
        }

        @SuppressLint({"RestrictedApi"})
        @NonNull
        public T build() {
            Executor executor;
            if (this.mContext == null) {
                throw new IllegalArgumentException("Cannot provide null context for the database.");
            } else if (this.mDatabaseClass != null) {
                Executor executor2 = this.mQueryExecutor;
                if (executor2 == null && this.mTransactionExecutor == null) {
                    Executor iOThreadExecutor = ArchTaskExecutor.getIOThreadExecutor();
                    this.mTransactionExecutor = iOThreadExecutor;
                    this.mQueryExecutor = iOThreadExecutor;
                } else if (executor2 != null && this.mTransactionExecutor == null) {
                    this.mTransactionExecutor = executor2;
                } else if (executor2 == null && (executor = this.mTransactionExecutor) != null) {
                    this.mQueryExecutor = executor;
                }
                Set<Integer> set = this.mMigrationStartAndEndVersions;
                if (!(set == null || this.mMigrationsNotRequiredFrom == null)) {
                    for (Integer version : set) {
                        if (this.mMigrationsNotRequiredFrom.contains(version)) {
                            throw new IllegalArgumentException("Inconsistency detected. A Migration was supplied to addMigration(Migration... migrations) that has a start or end version equal to a start version supplied to fallbackToDestructiveMigrationFrom(int... startVersions). Start version: " + version);
                        }
                    }
                }
                if (this.mFactory == null) {
                    this.mFactory = new FrameworkSQLiteOpenHelperFactory();
                }
                String str = this.mCopyFromAssetPath;
                if (!(str == null && this.mCopyFromFile == null)) {
                    if (this.mName == null) {
                        throw new IllegalArgumentException("Cannot create from asset or file for an in-memory database.");
                    } else if (str == null || this.mCopyFromFile == null) {
                        this.mFactory = new SQLiteCopyOpenHelperFactory(str, this.mCopyFromFile, this.mFactory);
                    } else {
                        throw new IllegalArgumentException("Both createFromAsset() and createFromFile() was called on this Builder but the database can only be created using one of the two configurations.");
                    }
                }
                Context context = this.mContext;
                String str2 = this.mName;
                SupportSQLiteOpenHelper.Factory factory = this.mFactory;
                MigrationContainer migrationContainer = this.mMigrationContainer;
                ArrayList<Callback> arrayList = this.mCallbacks;
                boolean z = this.mAllowMainThreadQueries;
                JournalMode resolve = this.mJournalMode.resolve(context);
                Executor executor3 = this.mQueryExecutor;
                Executor executor4 = this.mTransactionExecutor;
                boolean z2 = this.mMultiInstanceInvalidation;
                boolean z3 = this.mRequireMigration;
                boolean z4 = this.mAllowDestructiveMigrationOnDowngrade;
                boolean z5 = z3;
                boolean z6 = z4;
                DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration(context, str2, factory, migrationContainer, arrayList, z, resolve, executor3, executor4, z2, z5, z6, this.mMigrationsNotRequiredFrom, this.mCopyFromAssetPath, this.mCopyFromFile);
                T db = (RoomDatabase) Room.getGeneratedImplementation(this.mDatabaseClass, RoomDatabase.DB_IMPL_SUFFIX);
                db.init(databaseConfiguration);
                return db;
            } else {
                throw new IllegalArgumentException("Must provide an abstract class that extends RoomDatabase");
            }
        }
    }

    public static class MigrationContainer {
        private HashMap<Integer, TreeMap<Integer, Migration>> mMigrations = new HashMap<>();

        public void addMigrations(@NonNull Migration... migrations) {
            for (Migration migration : migrations) {
                addMigration(migration);
            }
        }

        private void addMigration(Migration migration) {
            int start = migration.startVersion;
            int end = migration.endVersion;
            TreeMap<Integer, Migration> targetMap = this.mMigrations.get(Integer.valueOf(start));
            if (targetMap == null) {
                targetMap = new TreeMap<>();
                this.mMigrations.put(Integer.valueOf(start), targetMap);
            }
            Migration existing = targetMap.get(Integer.valueOf(end));
            if (existing != null) {
                Log.w("ROOM", "Overriding migration " + existing + " with " + migration);
            }
            targetMap.put(Integer.valueOf(end), migration);
        }

        @Nullable
        public List<Migration> findMigrationPath(int start, int end) {
            if (start == end) {
                return Collections.emptyList();
            }
            return findUpMigrationPath(new ArrayList<>(), end > start, start, end);
        }

        private List<Migration> findUpMigrationPath(List<Migration> result, boolean upgrade, int start, int end) {
            Set<Integer> keySet;
            boolean found;
            do {
                if (upgrade) {
                    if (start >= end) {
                        return result;
                    }
                } else if (start <= end) {
                    return result;
                }
                TreeMap treeMap = this.mMigrations.get(Integer.valueOf(start));
                if (treeMap != null) {
                    if (upgrade) {
                        keySet = treeMap.descendingKeySet();
                    } else {
                        keySet = treeMap.keySet();
                    }
                    found = false;
                    Iterator<Integer> it = keySet.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        int targetVersion = it.next().intValue();
                        boolean shouldAddToPath = true;
                        if (upgrade) {
                            if (targetVersion > end || targetVersion <= start) {
                                shouldAddToPath = false;
                                continue;
                            }
                        } else if (targetVersion < end || targetVersion >= start) {
                            shouldAddToPath = false;
                            continue;
                        }
                        if (shouldAddToPath) {
                            result.add(treeMap.get(Integer.valueOf(targetVersion)));
                            start = targetVersion;
                            found = true;
                            continue;
                            break;
                        }
                    }
                } else {
                    return null;
                }
            } while (found);
            return null;
        }
    }

    private static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public static abstract class Callback {
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
        }

        public void onOpen(@NonNull SupportSQLiteDatabase db) {
        }

        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
        }
    }
}
