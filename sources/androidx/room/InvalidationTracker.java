package androidx.room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

public class InvalidationTracker {
    private static final String CREATE_TRACKING_TABLE_SQL = "CREATE TEMP TABLE room_table_modification_log(table_id INTEGER PRIMARY KEY, invalidated INTEGER NOT NULL DEFAULT 0)";
    private static final String INVALIDATED_COLUMN_NAME = "invalidated";
    @VisibleForTesting
    static final String RESET_UPDATED_TABLES_SQL = "UPDATE room_table_modification_log SET invalidated = 0 WHERE invalidated = 1 ";
    @VisibleForTesting
    static final String SELECT_UPDATED_TABLES_SQL = "SELECT * FROM room_table_modification_log WHERE invalidated = 1;";
    private static final String TABLE_ID_COLUMN_NAME = "table_id";
    private static final String[] TRIGGERS = {"UPDATE", "DELETE", "INSERT"};
    private static final String UPDATE_TABLE_NAME = "room_table_modification_log";
    volatile SupportSQLiteStatement mCleanupStatement;
    final RoomDatabase mDatabase;
    private volatile boolean mInitialized;
    private final InvalidationLiveDataContainer mInvalidationLiveDataContainer;
    private MultiInstanceInvalidationClient mMultiInstanceInvalidationClient;
    private ObservedTableTracker mObservedTableTracker;
    @VisibleForTesting
    @SuppressLint({"RestrictedApi"})
    final SafeIterableMap<Observer, ObserverWrapper> mObserverMap;
    AtomicBoolean mPendingRefresh;
    @VisibleForTesting
    Runnable mRefreshRunnable;
    @NonNull
    final HashMap<String, Integer> mTableIdLookup;
    final String[] mTableNames;
    @NonNull
    private Map<String, Set<String>> mViewTables;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public InvalidationTracker(RoomDatabase database, String... tableNames) {
        this(database, new HashMap(), Collections.emptyMap(), tableNames);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public InvalidationTracker(RoomDatabase database, Map<String, String> shadowTablesMap, Map<String, Set<String>> viewTables, String... tableNames) {
        this.mPendingRefresh = new AtomicBoolean(false);
        this.mInitialized = false;
        this.mObserverMap = new SafeIterableMap<>();
        this.mRefreshRunnable = new Runnable() {
            public void run() {
                SupportSQLiteDatabase db;
                Lock closeLock = InvalidationTracker.this.mDatabase.getCloseLock();
                Set<Integer> invalidatedTableIds = null;
                try {
                    closeLock.lock();
                    if (!InvalidationTracker.this.ensureInitialization()) {
                        closeLock.unlock();
                    } else if (!InvalidationTracker.this.mPendingRefresh.compareAndSet(true, false)) {
                        closeLock.unlock();
                    } else if (InvalidationTracker.this.mDatabase.inTransaction()) {
                        closeLock.unlock();
                    } else {
                        RoomDatabase roomDatabase = InvalidationTracker.this.mDatabase;
                        if (roomDatabase.mWriteAheadLoggingEnabled) {
                            db = roomDatabase.getOpenHelper().getWritableDatabase();
                            db.beginTransaction();
                            invalidatedTableIds = checkUpdatedTable();
                            db.setTransactionSuccessful();
                            db.endTransaction();
                        } else {
                            invalidatedTableIds = checkUpdatedTable();
                        }
                        closeLock.unlock();
                        if (invalidatedTableIds != null && !invalidatedTableIds.isEmpty()) {
                            synchronized (InvalidationTracker.this.mObserverMap) {
                                Iterator<Map.Entry<Observer, ObserverWrapper>> it = InvalidationTracker.this.mObserverMap.iterator();
                                while (it.hasNext()) {
                                    it.next().getValue().notifyByTableInvalidStatus(invalidatedTableIds);
                                }
                            }
                        }
                    }
                } catch (SQLiteException | IllegalStateException exception) {
                    try {
                        Log.e("ROOM", "Cannot run invalidation tracker. Is the db closed?", exception);
                    } catch (Throwable th) {
                        closeLock.unlock();
                        throw th;
                    }
                } catch (Throwable th2) {
                    db.endTransaction();
                    throw th2;
                }
            }

            /* JADX INFO: finally extract failed */
            private Set<Integer> checkUpdatedTable() {
                HashSet<Integer> invalidatedTableIds = new HashSet<>();
                Cursor cursor = InvalidationTracker.this.mDatabase.query(new SimpleSQLiteQuery(InvalidationTracker.SELECT_UPDATED_TABLES_SQL));
                while (cursor.moveToNext()) {
                    try {
                        invalidatedTableIds.add(Integer.valueOf(cursor.getInt(0)));
                    } catch (Throwable th) {
                        cursor.close();
                        throw th;
                    }
                }
                cursor.close();
                if (!invalidatedTableIds.isEmpty()) {
                    InvalidationTracker.this.mCleanupStatement.executeUpdateDelete();
                }
                return invalidatedTableIds;
            }
        };
        this.mDatabase = database;
        this.mObservedTableTracker = new ObservedTableTracker(tableNames.length);
        this.mTableIdLookup = new HashMap<>();
        this.mViewTables = viewTables;
        this.mInvalidationLiveDataContainer = new InvalidationLiveDataContainer(database);
        int size = tableNames.length;
        this.mTableNames = new String[size];
        for (int id = 0; id < size; id++) {
            String str = tableNames[id];
            Locale locale = Locale.US;
            String tableName = str.toLowerCase(locale);
            this.mTableIdLookup.put(tableName, Integer.valueOf(id));
            String shadowTableName = shadowTablesMap.get(tableNames[id]);
            if (shadowTableName != null) {
                this.mTableNames[id] = shadowTableName.toLowerCase(locale);
            } else {
                this.mTableNames[id] = tableName;
            }
        }
        for (Map.Entry<String, String> shadowTableEntry : shadowTablesMap.entrySet()) {
            Locale locale2 = Locale.US;
            String shadowTableName2 = shadowTableEntry.getValue().toLowerCase(locale2);
            if (this.mTableIdLookup.containsKey(shadowTableName2)) {
                String tableName2 = shadowTableEntry.getKey().toLowerCase(locale2);
                HashMap<String, Integer> hashMap = this.mTableIdLookup;
                hashMap.put(tableName2, hashMap.get(shadowTableName2));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void internalInit(SupportSQLiteDatabase database) {
        synchronized (this) {
            if (this.mInitialized) {
                Log.e("ROOM", "Invalidation tracker is initialized twice :/.");
                return;
            }
            database.execSQL("PRAGMA temp_store = MEMORY;");
            database.execSQL("PRAGMA recursive_triggers='ON';");
            database.execSQL(CREATE_TRACKING_TABLE_SQL);
            syncTriggers(database);
            this.mCleanupStatement = database.compileStatement(RESET_UPDATED_TABLES_SQL);
            this.mInitialized = true;
        }
    }

    /* access modifiers changed from: package-private */
    public void startMultiInstanceInvalidation(Context context, String name) {
        this.mMultiInstanceInvalidationClient = new MultiInstanceInvalidationClient(context, name, this, this.mDatabase.getQueryExecutor());
    }

    /* access modifiers changed from: package-private */
    public void stopMultiInstanceInvalidation() {
        MultiInstanceInvalidationClient multiInstanceInvalidationClient = this.mMultiInstanceInvalidationClient;
        if (multiInstanceInvalidationClient != null) {
            multiInstanceInvalidationClient.stop();
            this.mMultiInstanceInvalidationClient = null;
        }
    }

    private static void appendTriggerName(StringBuilder builder, String tableName, String triggerType) {
        builder.append("`");
        builder.append("room_table_modification_trigger_");
        builder.append(tableName);
        builder.append("_");
        builder.append(triggerType);
        builder.append("`");
    }

    private void stopTrackingTable(SupportSQLiteDatabase writableDb, int tableId) {
        String tableName = this.mTableNames[tableId];
        StringBuilder stringBuilder = new StringBuilder();
        for (String trigger : TRIGGERS) {
            stringBuilder.setLength(0);
            stringBuilder.append("DROP TRIGGER IF EXISTS ");
            appendTriggerName(stringBuilder, tableName, trigger);
            writableDb.execSQL(stringBuilder.toString());
        }
    }

    private void startTrackingTable(SupportSQLiteDatabase writableDb, int tableId) {
        writableDb.execSQL("INSERT OR IGNORE INTO room_table_modification_log VALUES(" + tableId + ", 0)");
        String tableName = this.mTableNames[tableId];
        StringBuilder stringBuilder = new StringBuilder();
        for (String trigger : TRIGGERS) {
            stringBuilder.setLength(0);
            stringBuilder.append("CREATE TEMP TRIGGER IF NOT EXISTS ");
            appendTriggerName(stringBuilder, tableName, trigger);
            stringBuilder.append(" AFTER ");
            stringBuilder.append(trigger);
            stringBuilder.append(" ON `");
            stringBuilder.append(tableName);
            stringBuilder.append("` BEGIN UPDATE ");
            stringBuilder.append(UPDATE_TABLE_NAME);
            stringBuilder.append(" SET ");
            stringBuilder.append(INVALIDATED_COLUMN_NAME);
            stringBuilder.append(" = 1");
            stringBuilder.append(" WHERE ");
            stringBuilder.append(TABLE_ID_COLUMN_NAME);
            stringBuilder.append(" = ");
            stringBuilder.append(tableId);
            stringBuilder.append(" AND ");
            stringBuilder.append(INVALIDATED_COLUMN_NAME);
            stringBuilder.append(" = 0");
            stringBuilder.append("; END");
            writableDb.execSQL(stringBuilder.toString());
        }
    }

    @WorkerThread
    @SuppressLint({"RestrictedApi"})
    public void addObserver(@NonNull Observer observer) {
        ObserverWrapper currentObserver;
        String[] tableNames = resolveViews(observer.mTables);
        int[] tableIds = new int[tableNames.length];
        int size = tableNames.length;
        int i = 0;
        while (i < size) {
            Integer tableId = this.mTableIdLookup.get(tableNames[i].toLowerCase(Locale.US));
            if (tableId != null) {
                tableIds[i] = tableId.intValue();
                i++;
            } else {
                throw new IllegalArgumentException("There is no table with name " + tableNames[i]);
            }
        }
        ObserverWrapper wrapper = new ObserverWrapper(observer, tableIds, tableNames);
        synchronized (this.mObserverMap) {
            currentObserver = this.mObserverMap.putIfAbsent(observer, wrapper);
        }
        if (currentObserver == null && this.mObservedTableTracker.onAdded(tableIds)) {
            syncTriggers();
        }
    }

    private String[] validateAndResolveTableNames(String[] tableNames) {
        String[] resolved = resolveViews(tableNames);
        int length = resolved.length;
        int i = 0;
        while (i < length) {
            String tableName = resolved[i];
            if (this.mTableIdLookup.containsKey(tableName.toLowerCase(Locale.US))) {
                i++;
            } else {
                throw new IllegalArgumentException("There is no table with name " + tableName);
            }
        }
        return resolved;
    }

    private String[] resolveViews(String[] names) {
        Set<String> tables = new HashSet<>();
        for (String name : names) {
            String lowercase = name.toLowerCase(Locale.US);
            if (this.mViewTables.containsKey(lowercase)) {
                tables.addAll(this.mViewTables.get(lowercase));
            } else {
                tables.add(name);
            }
        }
        return (String[]) tables.toArray(new String[tables.size()]);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void addWeakObserver(Observer observer) {
        addObserver(new WeakObserver(this, observer));
    }

    @WorkerThread
    @SuppressLint({"RestrictedApi"})
    public void removeObserver(@NonNull Observer observer) {
        ObserverWrapper wrapper;
        synchronized (this.mObserverMap) {
            wrapper = this.mObserverMap.remove(observer);
        }
        if (wrapper != null && this.mObservedTableTracker.onRemoved(wrapper.mTableIds)) {
            syncTriggers();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean ensureInitialization() {
        if (!this.mDatabase.isOpen()) {
            return false;
        }
        if (!this.mInitialized) {
            this.mDatabase.getOpenHelper().getWritableDatabase();
        }
        if (this.mInitialized) {
            return true;
        }
        Log.e("ROOM", "database is not initialized even though it is open");
        return false;
    }

    public void refreshVersionsAsync() {
        if (this.mPendingRefresh.compareAndSet(false, true)) {
            this.mDatabase.getQueryExecutor().execute(this.mRefreshRunnable);
        }
    }

    @WorkerThread
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void refreshVersionsSync() {
        syncTriggers();
        this.mRefreshRunnable.run();
    }

    @VisibleForTesting(otherwise = 3)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void notifyObserversByTableNames(String... tables) {
        synchronized (this.mObserverMap) {
            Iterator<Map.Entry<Observer, ObserverWrapper>> it = this.mObserverMap.iterator();
            while (it.hasNext()) {
                Map.Entry<Observer, ObserverWrapper> entry = it.next();
                if (!entry.getKey().isRemote()) {
                    entry.getValue().notifyByTableNames(tables);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void syncTriggers(SupportSQLiteDatabase database) {
        if (!database.inTransaction()) {
            while (true) {
                try {
                    Lock closeLock = this.mDatabase.getCloseLock();
                    closeLock.lock();
                    try {
                        int[] tablesToSync = this.mObservedTableTracker.getTablesToSync();
                        if (tablesToSync == null) {
                            closeLock.unlock();
                            return;
                        }
                        int limit = tablesToSync.length;
                        database.beginTransaction();
                        for (int tableId = 0; tableId < limit; tableId++) {
                            switch (tablesToSync[tableId]) {
                                case 1:
                                    startTrackingTable(database, tableId);
                                    break;
                                case 2:
                                    stopTrackingTable(database, tableId);
                                    break;
                            }
                        }
                        database.setTransactionSuccessful();
                        database.endTransaction();
                        this.mObservedTableTracker.onSyncCompleted();
                        closeLock.unlock();
                    } catch (Throwable th) {
                        closeLock.unlock();
                        throw th;
                    }
                } catch (SQLiteException | IllegalStateException exception) {
                    Log.e("ROOM", "Cannot run invalidation tracker. Is the db closed?", exception);
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void syncTriggers() {
        if (this.mDatabase.isOpen()) {
            syncTriggers(this.mDatabase.getOpenHelper().getWritableDatabase());
        }
    }

    @Deprecated
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public <T> LiveData<T> createLiveData(String[] tableNames, Callable<T> computeFunction) {
        return createLiveData(tableNames, false, computeFunction);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public <T> LiveData<T> createLiveData(String[] tableNames, boolean inTransaction, Callable<T> computeFunction) {
        return this.mInvalidationLiveDataContainer.create(validateAndResolveTableNames(tableNames), inTransaction, computeFunction);
    }

    public static class ObserverWrapper {
        final Observer mObserver;
        private final Set<String> mSingleTableSet;
        final int[] mTableIds;
        private final String[] mTableNames;

        ObserverWrapper(Observer observer, int[] tableIds, String[] tableNames) {
            this.mObserver = observer;
            this.mTableIds = tableIds;
            this.mTableNames = tableNames;
            if (tableIds.length == 1) {
                HashSet<String> set = new HashSet<>();
                set.add(tableNames[0]);
                this.mSingleTableSet = Collections.unmodifiableSet(set);
                return;
            }
            this.mSingleTableSet = null;
        }

        /* access modifiers changed from: package-private */
        public void notifyByTableInvalidStatus(Set<Integer> invalidatedTablesIds) {
            Set<String> invalidatedTables = null;
            int size = this.mTableIds.length;
            for (int index = 0; index < size; index++) {
                if (invalidatedTablesIds.contains(Integer.valueOf(this.mTableIds[index]))) {
                    if (size == 1) {
                        invalidatedTables = this.mSingleTableSet;
                    } else {
                        if (invalidatedTables == null) {
                            invalidatedTables = new HashSet<>(size);
                        }
                        invalidatedTables.add(this.mTableNames[index]);
                    }
                }
            }
            if (invalidatedTables != null) {
                this.mObserver.onInvalidated(invalidatedTables);
            }
        }

        /* access modifiers changed from: package-private */
        public void notifyByTableNames(String[] tables) {
            Set<String> invalidatedTables = null;
            if (this.mTableNames.length == 1) {
                int length = tables.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    } else if (tables[i].equalsIgnoreCase(this.mTableNames[0])) {
                        invalidatedTables = this.mSingleTableSet;
                        break;
                    } else {
                        i++;
                    }
                }
            } else {
                HashSet<String> set = new HashSet<>();
                for (String table : tables) {
                    String[] strArr = this.mTableNames;
                    int length2 = strArr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length2) {
                            break;
                        }
                        String ourTable = strArr[i2];
                        if (ourTable.equalsIgnoreCase(table)) {
                            set.add(ourTable);
                            break;
                        }
                        i2++;
                    }
                }
                if (set.size() > 0) {
                    invalidatedTables = set;
                }
            }
            if (invalidatedTables != null) {
                this.mObserver.onInvalidated(invalidatedTables);
            }
        }
    }

    public static abstract class Observer {
        final String[] mTables;

        public abstract void onInvalidated(@NonNull Set<String> set);

        protected Observer(@NonNull String firstTable, String... rest) {
            String[] strArr = (String[]) Arrays.copyOf(rest, rest.length + 1);
            this.mTables = strArr;
            strArr[rest.length] = firstTable;
        }

        public Observer(@NonNull String[] tables) {
            this.mTables = (String[]) Arrays.copyOf(tables, tables.length);
        }

        /* access modifiers changed from: package-private */
        public boolean isRemote() {
            return false;
        }
    }

    public static class ObservedTableTracker {
        static final int ADD = 1;
        static final int NO_OP = 0;
        static final int REMOVE = 2;
        boolean mNeedsSync;
        boolean mPendingSync;
        final long[] mTableObservers;
        final int[] mTriggerStateChanges;
        final boolean[] mTriggerStates;

        ObservedTableTracker(int tableCount) {
            long[] jArr = new long[tableCount];
            this.mTableObservers = jArr;
            boolean[] zArr = new boolean[tableCount];
            this.mTriggerStates = zArr;
            this.mTriggerStateChanges = new int[tableCount];
            Arrays.fill(jArr, 0);
            Arrays.fill(zArr, false);
        }

        /* access modifiers changed from: package-private */
        public boolean onAdded(int... tableIds) {
            boolean needTriggerSync = false;
            synchronized (this) {
                for (int tableId : tableIds) {
                    long[] jArr = this.mTableObservers;
                    long prevObserverCount = jArr[tableId];
                    jArr[tableId] = 1 + prevObserverCount;
                    if (prevObserverCount == 0) {
                        this.mNeedsSync = true;
                        needTriggerSync = true;
                    }
                }
            }
            return needTriggerSync;
        }

        /* access modifiers changed from: package-private */
        public boolean onRemoved(int... tableIds) {
            boolean needTriggerSync = false;
            synchronized (this) {
                for (int tableId : tableIds) {
                    long[] jArr = this.mTableObservers;
                    long prevObserverCount = jArr[tableId];
                    jArr[tableId] = prevObserverCount - 1;
                    if (prevObserverCount == 1) {
                        this.mNeedsSync = true;
                        needTriggerSync = true;
                    }
                }
            }
            return needTriggerSync;
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public int[] getTablesToSync() {
            synchronized (this) {
                if (this.mNeedsSync) {
                    if (!this.mPendingSync) {
                        int tableCount = this.mTableObservers.length;
                        int i = 0;
                        while (true) {
                            int i2 = 1;
                            if (i < tableCount) {
                                boolean newState = this.mTableObservers[i] > 0;
                                boolean[] zArr = this.mTriggerStates;
                                if (newState != zArr[i]) {
                                    int[] iArr = this.mTriggerStateChanges;
                                    if (!newState) {
                                        i2 = 2;
                                    }
                                    iArr[i] = i2;
                                } else {
                                    this.mTriggerStateChanges[i] = 0;
                                }
                                zArr[i] = newState;
                                i++;
                            } else {
                                this.mPendingSync = true;
                                this.mNeedsSync = false;
                                int[] iArr2 = this.mTriggerStateChanges;
                                return iArr2;
                            }
                        }
                    }
                }
                return null;
            }
        }

        /* access modifiers changed from: package-private */
        public void onSyncCompleted() {
            synchronized (this) {
                this.mPendingSync = false;
            }
        }
    }

    public static class WeakObserver extends Observer {
        final WeakReference<Observer> mDelegateRef;
        final InvalidationTracker mTracker;

        WeakObserver(InvalidationTracker tracker, Observer delegate) {
            super(delegate.mTables);
            this.mTracker = tracker;
            this.mDelegateRef = new WeakReference<>(delegate);
        }

        public void onInvalidated(@NonNull Set<String> tables) {
            Observer observer = (Observer) this.mDelegateRef.get();
            if (observer == null) {
                this.mTracker.removeObserver(this);
            } else {
                observer.onInvalidated(tables);
            }
        }
    }
}
