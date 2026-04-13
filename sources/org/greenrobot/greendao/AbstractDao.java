package org.greenrobot.greendao;

import android.database.CrossProcessCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.greenrobot.greendao.annotation.apihint.Experimental;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.identityscope.IdentityScope;
import org.greenrobot.greendao.identityscope.IdentityScopeLong;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.internal.TableStatements;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.rx.RxDao;
import rx.schedulers.Schedulers;

public abstract class AbstractDao<T, K> {
    protected final DaoConfig config;
    protected final Database db;
    protected final IdentityScope<K, T> identityScope;
    protected final IdentityScopeLong<T> identityScopeLong;
    protected final boolean isStandardSQLite;
    protected final int pkOrdinal;
    private volatile RxDao<T, K> rxDao;
    private volatile RxDao<T, K> rxDaoPlain;
    protected final AbstractDaoSession session;
    protected final TableStatements statements;

    /* access modifiers changed from: protected */
    public abstract void bindValues(SQLiteStatement sQLiteStatement, T t);

    /* access modifiers changed from: protected */
    public abstract void bindValues(DatabaseStatement databaseStatement, T t);

    /* access modifiers changed from: protected */
    public abstract K getKey(T t);

    /* access modifiers changed from: protected */
    public abstract boolean hasKey(T t);

    /* access modifiers changed from: protected */
    public abstract boolean isEntityUpdateable();

    /* access modifiers changed from: protected */
    public abstract T readEntity(Cursor cursor, int i);

    /* access modifiers changed from: protected */
    public abstract void readEntity(Cursor cursor, T t, int i);

    /* access modifiers changed from: protected */
    public abstract K readKey(Cursor cursor, int i);

    /* access modifiers changed from: protected */
    public abstract K updateKeyAfterInsert(T t, long j);

    public AbstractDao(DaoConfig config2) {
        this(config2, (AbstractDaoSession) null);
    }

    public AbstractDao(DaoConfig config2, AbstractDaoSession daoSession) {
        this.config = config2;
        this.session = daoSession;
        Database database = config2.db;
        this.db = database;
        this.isStandardSQLite = database.getRawDatabase() instanceof SQLiteDatabase;
        IdentityScope<?, ?> identityScope2 = config2.getIdentityScope();
        this.identityScope = identityScope2;
        if (identityScope2 instanceof IdentityScopeLong) {
            this.identityScopeLong = (IdentityScopeLong) identityScope2;
        } else {
            this.identityScopeLong = null;
        }
        this.statements = config2.statements;
        Property property = config2.pkProperty;
        this.pkOrdinal = property != null ? property.ordinal : -1;
    }

    public AbstractDaoSession getSession() {
        return this.session;
    }

    /* access modifiers changed from: package-private */
    public TableStatements getStatements() {
        return this.config.statements;
    }

    public String getTablename() {
        return this.config.tablename;
    }

    public Property[] getProperties() {
        return this.config.properties;
    }

    public Property getPkProperty() {
        return this.config.pkProperty;
    }

    public String[] getAllColumns() {
        return this.config.allColumns;
    }

    public String[] getPkColumns() {
        return this.config.pkColumns;
    }

    public String[] getNonPkColumns() {
        return this.config.nonPkColumns;
    }

    public T load(K key) {
        T entity;
        assertSinglePk();
        if (key == null) {
            return null;
        }
        IdentityScope<K, T> identityScope2 = this.identityScope;
        if (identityScope2 != null && (entity = identityScope2.get(key)) != null) {
            return entity;
        }
        return loadUniqueAndCloseCursor(this.db.rawQuery(this.statements.getSelectByKey(), new String[]{key.toString()}));
    }

    public T loadByRowId(long rowId) {
        return loadUniqueAndCloseCursor(this.db.rawQuery(this.statements.getSelectByRowId(), new String[]{Long.toString(rowId)}));
    }

    /* access modifiers changed from: protected */
    public T loadUniqueAndCloseCursor(Cursor cursor) {
        try {
            return loadUnique(cursor);
        } finally {
            cursor.close();
        }
    }

    /* access modifiers changed from: protected */
    public T loadUnique(Cursor cursor) {
        if (!cursor.moveToFirst()) {
            return null;
        }
        if (cursor.isLast()) {
            return loadCurrent(cursor, 0, true);
        }
        throw new DaoException("Expected unique result, but count was " + cursor.getCount());
    }

    public List<T> loadAll() {
        return loadAllAndCloseCursor(this.db.rawQuery(this.statements.getSelectAll(), (String[]) null));
    }

    public boolean detach(T entity) {
        if (this.identityScope == null) {
            return false;
        }
        return this.identityScope.detach(getKeyVerified(entity), entity);
    }

    public void detachAll() {
        IdentityScope<K, T> identityScope2 = this.identityScope;
        if (identityScope2 != null) {
            identityScope2.clear();
        }
    }

    /* access modifiers changed from: protected */
    public List<T> loadAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }

    public void insertInTx(Iterable<T> entities) {
        insertInTx(entities, isEntityUpdateable());
    }

    public void insertInTx(T... entities) {
        insertInTx(Arrays.asList(entities), isEntityUpdateable());
    }

    public void insertInTx(Iterable<T> entities, boolean setPrimaryKey) {
        executeInsertInTx(this.statements.getInsertStatement(), entities, setPrimaryKey);
    }

    public void insertOrReplaceInTx(Iterable<T> entities, boolean setPrimaryKey) {
        executeInsertInTx(this.statements.getInsertOrReplaceStatement(), entities, setPrimaryKey);
    }

    public void insertOrReplaceInTx(Iterable<T> entities) {
        insertOrReplaceInTx(entities, isEntityUpdateable());
    }

    public void insertOrReplaceInTx(T... entities) {
        insertOrReplaceInTx(Arrays.asList(entities), isEntityUpdateable());
    }

    private void executeInsertInTx(DatabaseStatement stmt, Iterable<T> entities, boolean setPrimaryKey) {
        this.db.beginTransaction();
        try {
            synchronized (stmt) {
                IdentityScope<K, T> identityScope2 = this.identityScope;
                if (identityScope2 != null) {
                    identityScope2.lock();
                }
                try {
                    if (this.isStandardSQLite) {
                        SQLiteStatement rawStmt = (SQLiteStatement) stmt.getRawStatement();
                        for (T entity : entities) {
                            bindValues(rawStmt, entity);
                            if (setPrimaryKey) {
                                updateKeyAfterInsertAndAttach(entity, rawStmt.executeInsert(), false);
                            } else {
                                rawStmt.execute();
                            }
                        }
                    } else {
                        for (T entity2 : entities) {
                            bindValues(stmt, entity2);
                            if (setPrimaryKey) {
                                updateKeyAfterInsertAndAttach(entity2, stmt.executeInsert(), false);
                            } else {
                                stmt.execute();
                            }
                        }
                    }
                } finally {
                    IdentityScope<K, T> identityScope3 = this.identityScope;
                    if (identityScope3 != null) {
                        identityScope3.unlock();
                    }
                }
            }
            this.db.setTransactionSuccessful();
        } finally {
            this.db.endTransaction();
        }
    }

    public long insert(T entity) {
        return executeInsert(entity, this.statements.getInsertStatement(), true);
    }

    public long insertWithoutSettingPk(T entity) {
        return executeInsert(entity, this.statements.getInsertOrReplaceStatement(), false);
    }

    public long insertOrReplace(T entity) {
        return executeInsert(entity, this.statements.getInsertOrReplaceStatement(), true);
    }

    private long executeInsert(T entity, DatabaseStatement stmt, boolean setKeyAndAttach) {
        long rowId;
        if (this.db.isDbLockedByCurrentThread()) {
            rowId = insertInsideTx(entity, stmt);
        } else {
            this.db.beginTransaction();
            try {
                rowId = insertInsideTx(entity, stmt);
                this.db.setTransactionSuccessful();
            } finally {
                this.db.endTransaction();
            }
        }
        if (setKeyAndAttach) {
            updateKeyAfterInsertAndAttach(entity, rowId, true);
        }
        return rowId;
    }

    private long insertInsideTx(T entity, DatabaseStatement stmt) {
        synchronized (stmt) {
            if (this.isStandardSQLite) {
                SQLiteStatement rawStmt = (SQLiteStatement) stmt.getRawStatement();
                bindValues(rawStmt, entity);
                long executeInsert = rawStmt.executeInsert();
                return executeInsert;
            }
            bindValues(stmt, entity);
            long executeInsert2 = stmt.executeInsert();
            return executeInsert2;
        }
    }

    /* access modifiers changed from: protected */
    public void updateKeyAfterInsertAndAttach(T entity, long rowId, boolean lock) {
        if (rowId != -1) {
            attachEntity(updateKeyAfterInsert(entity, rowId), entity, lock);
        } else {
            DaoLog.w("Could not insert row (executeInsert returned -1)");
        }
    }

    public void save(T entity) {
        if (hasKey(entity)) {
            update(entity);
        } else {
            insert(entity);
        }
    }

    public void saveInTx(T... entities) {
        saveInTx(Arrays.asList(entities));
    }

    public void saveInTx(Iterable<T> entities) {
        int updateCount = 0;
        int insertCount = 0;
        for (T entity : entities) {
            if (hasKey(entity)) {
                updateCount++;
            } else {
                insertCount++;
            }
        }
        if (updateCount > 0 && insertCount > 0) {
            List<T> toUpdate = new ArrayList<>(updateCount);
            List<T> toInsert = new ArrayList<>(insertCount);
            for (T entity2 : entities) {
                if (hasKey(entity2)) {
                    toUpdate.add(entity2);
                } else {
                    toInsert.add(entity2);
                }
            }
            this.db.beginTransaction();
            try {
                updateInTx(toUpdate);
                insertInTx(toInsert);
                this.db.setTransactionSuccessful();
            } finally {
                this.db.endTransaction();
            }
        } else if (insertCount > 0) {
            insertInTx(entities);
        } else if (updateCount > 0) {
            updateInTx(entities);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x007e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<T> loadAllFromCursor(android.database.Cursor r7) {
        /*
            r6 = this;
            int r0 = r7.getCount()
            if (r0 != 0) goto L_0x000c
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            return r1
        L_0x000c:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>(r0)
            r2 = 0
            r3 = 0
            boolean r4 = r7 instanceof android.database.CrossProcessCursor
            if (r4 == 0) goto L_0x004e
            r4 = r7
            android.database.CrossProcessCursor r4 = (android.database.CrossProcessCursor) r4
            android.database.CursorWindow r2 = r4.getWindow()
            if (r2 == 0) goto L_0x004e
            int r4 = r2.getNumRows()
            if (r4 != r0) goto L_0x002e
            org.greenrobot.greendao.internal.FastCursor r4 = new org.greenrobot.greendao.internal.FastCursor
            r4.<init>(r2)
            r7 = r4
            r3 = 1
            goto L_0x004e
        L_0x002e:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Window vs. result size: "
            r4.append(r5)
            int r5 = r2.getNumRows()
            r4.append(r5)
            java.lang.String r5 = "/"
            r4.append(r5)
            r4.append(r0)
            java.lang.String r4 = r4.toString()
            org.greenrobot.greendao.DaoLog.d(r4)
        L_0x004e:
            boolean r4 = r7.moveToFirst()
            if (r4 == 0) goto L_0x008b
            org.greenrobot.greendao.identityscope.IdentityScope<K, T> r4 = r6.identityScope
            if (r4 == 0) goto L_0x0060
            r4.lock()
            org.greenrobot.greendao.identityscope.IdentityScope<K, T> r4 = r6.identityScope
            r4.reserveRoom(r0)
        L_0x0060:
            if (r3 != 0) goto L_0x006c
            if (r2 == 0) goto L_0x006c
            org.greenrobot.greendao.identityscope.IdentityScope<K, T> r4 = r6.identityScope     // Catch:{ all -> 0x0082 }
            if (r4 == 0) goto L_0x006c
            r6.loadAllUnlockOnWindowBounds(r7, r2, r1)     // Catch:{ all -> 0x0082 }
            goto L_0x007a
        L_0x006c:
            r4 = 0
            java.lang.Object r4 = r6.loadCurrent(r7, r4, r4)     // Catch:{ all -> 0x0082 }
            r1.add(r4)     // Catch:{ all -> 0x0082 }
            boolean r4 = r7.moveToNext()     // Catch:{ all -> 0x0082 }
            if (r4 != 0) goto L_0x006c
        L_0x007a:
            org.greenrobot.greendao.identityscope.IdentityScope<K, T> r4 = r6.identityScope
            if (r4 == 0) goto L_0x008b
            r4.unlock()
            goto L_0x008b
        L_0x0082:
            r4 = move-exception
            org.greenrobot.greendao.identityscope.IdentityScope<K, T> r5 = r6.identityScope
            if (r5 == 0) goto L_0x008a
            r5.unlock()
        L_0x008a:
            throw r4
        L_0x008b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.greenrobot.greendao.AbstractDao.loadAllFromCursor(android.database.Cursor):java.util.List");
    }

    private void loadAllUnlockOnWindowBounds(Cursor cursor, CursorWindow window, List<T> list) {
        int windowEnd = window.getStartPosition() + window.getNumRows();
        int row = 0;
        while (true) {
            list.add(loadCurrent(cursor, 0, false));
            int row2 = row + 1;
            if (row2 >= windowEnd) {
                CursorWindow window2 = moveToNextUnlocked(cursor);
                if (window2 != null) {
                    windowEnd = window2.getStartPosition() + window2.getNumRows();
                } else {
                    return;
                }
            } else if (cursor.moveToNext() == 0) {
                return;
            }
            row = row2 + 1;
        }
    }

    private CursorWindow moveToNextUnlocked(Cursor cursor) {
        this.identityScope.unlock();
        try {
            if (cursor.moveToNext()) {
                return ((CrossProcessCursor) cursor).getWindow();
            }
            this.identityScope.lock();
            return null;
        } finally {
            this.identityScope.lock();
        }
    }

    /* access modifiers changed from: protected */
    public final T loadCurrent(Cursor cursor, int offset, boolean lock) {
        if (this.identityScopeLong != null) {
            if (offset != 0 && cursor.isNull(this.pkOrdinal + offset)) {
                return null;
            }
            long key = cursor.getLong(this.pkOrdinal + offset);
            IdentityScopeLong<T> identityScopeLong2 = this.identityScopeLong;
            T entity = lock ? identityScopeLong2.get2(key) : identityScopeLong2.get2NoLock(key);
            if (entity != null) {
                return entity;
            }
            T entity2 = readEntity(cursor, offset);
            attachEntity(entity2);
            if (lock) {
                this.identityScopeLong.put2(key, entity2);
            } else {
                this.identityScopeLong.put2NoLock(key, entity2);
            }
            return entity2;
        } else if (this.identityScope != null) {
            K key2 = readKey(cursor, offset);
            if (offset != 0 && key2 == null) {
                return null;
            }
            IdentityScope<K, T> identityScope2 = this.identityScope;
            T entity3 = lock ? identityScope2.get(key2) : identityScope2.getNoLock(key2);
            if (entity3 != null) {
                return entity3;
            }
            T entity4 = readEntity(cursor, offset);
            attachEntity(key2, entity4, lock);
            return entity4;
        } else if (offset != 0 && readKey(cursor, offset) == null) {
            return null;
        } else {
            T entity5 = readEntity(cursor, offset);
            attachEntity(entity5);
            return entity5;
        }
    }

    /* access modifiers changed from: protected */
    public final <O> O loadCurrentOther(AbstractDao<O, ?> dao, Cursor cursor, int offset) {
        return dao.loadCurrent(cursor, offset, true);
    }

    public List<T> queryRaw(String where, String... selectionArg) {
        Database database = this.db;
        return loadAllAndCloseCursor(database.rawQuery(this.statements.getSelectAll() + where, selectionArg));
    }

    public Query<T> queryRawCreate(String where, Object... selectionArg) {
        return queryRawCreateListArgs(where, Arrays.asList(selectionArg));
    }

    public Query<T> queryRawCreateListArgs(String where, Collection<Object> selectionArg) {
        return Query.internalCreate(this, this.statements.getSelectAll() + where, selectionArg.toArray());
    }

    public void deleteAll() {
        Database database = this.db;
        database.execSQL("DELETE FROM '" + this.config.tablename + "'");
        IdentityScope<K, T> identityScope2 = this.identityScope;
        if (identityScope2 != null) {
            identityScope2.clear();
        }
    }

    public void delete(T entity) {
        assertSinglePk();
        deleteByKey(getKeyVerified(entity));
    }

    public void deleteByKey(K key) {
        assertSinglePk();
        DatabaseStatement stmt = this.statements.getDeleteStatement();
        if (this.db.isDbLockedByCurrentThread()) {
            synchronized (stmt) {
                deleteByKeyInsideSynchronized(key, stmt);
            }
        } else {
            this.db.beginTransaction();
            try {
                synchronized (stmt) {
                    deleteByKeyInsideSynchronized(key, stmt);
                }
                this.db.setTransactionSuccessful();
                this.db.endTransaction();
            } catch (Throwable th) {
                this.db.endTransaction();
                throw th;
            }
        }
        IdentityScope<K, T> identityScope2 = this.identityScope;
        if (identityScope2 != null) {
            identityScope2.remove(key);
        }
    }

    private void deleteByKeyInsideSynchronized(K key, DatabaseStatement stmt) {
        if (key instanceof Long) {
            stmt.bindLong(1, ((Long) key).longValue());
        } else if (key != null) {
            stmt.bindString(1, key.toString());
        } else {
            throw new DaoException("Cannot delete entity, key is null");
        }
        stmt.execute();
    }

    private void deleteInTxInternal(Iterable<T> entities, Iterable<K> keys) {
        IdentityScope<K, T> identityScope2;
        assertSinglePk();
        DatabaseStatement stmt = this.statements.getDeleteStatement();
        List<K> keysToRemoveFromIdentityScope = null;
        this.db.beginTransaction();
        try {
            synchronized (stmt) {
                IdentityScope<K, T> identityScope3 = this.identityScope;
                if (identityScope3 != null) {
                    identityScope3.lock();
                    keysToRemoveFromIdentityScope = new ArrayList<>();
                }
                if (entities != null) {
                    try {
                        for (T entity : entities) {
                            K key = getKeyVerified(entity);
                            deleteByKeyInsideSynchronized(key, stmt);
                            if (keysToRemoveFromIdentityScope != null) {
                                keysToRemoveFromIdentityScope.add(key);
                            }
                        }
                    } catch (Throwable th) {
                        IdentityScope<K, T> identityScope4 = this.identityScope;
                        if (identityScope4 != null) {
                            identityScope4.unlock();
                        }
                        throw th;
                    }
                }
                if (keys != null) {
                    for (K key2 : keys) {
                        deleteByKeyInsideSynchronized(key2, stmt);
                        if (keysToRemoveFromIdentityScope != null) {
                            keysToRemoveFromIdentityScope.add(key2);
                        }
                    }
                }
                IdentityScope<K, T> identityScope5 = this.identityScope;
                if (identityScope5 != null) {
                    identityScope5.unlock();
                }
            }
            this.db.setTransactionSuccessful();
            if (!(keysToRemoveFromIdentityScope == null || (identityScope2 = this.identityScope) == null)) {
                identityScope2.remove((Iterable<K>) keysToRemoveFromIdentityScope);
            }
        } finally {
            this.db.endTransaction();
        }
    }

    public void deleteInTx(Iterable<T> entities) {
        deleteInTxInternal(entities, (Iterable) null);
    }

    public void deleteInTx(T... entities) {
        deleteInTxInternal(Arrays.asList(entities), (Iterable) null);
    }

    public void deleteByKeyInTx(Iterable<K> keys) {
        deleteInTxInternal((Iterable) null, keys);
    }

    public void deleteByKeyInTx(K... keys) {
        deleteInTxInternal((Iterable) null, Arrays.asList(keys));
    }

    public void refresh(T entity) {
        assertSinglePk();
        K key = getKeyVerified(entity);
        Cursor cursor = this.db.rawQuery(this.statements.getSelectByKey(), new String[]{key.toString()});
        try {
            if (!cursor.moveToFirst()) {
                throw new DaoException("Entity does not exist in the database anymore: " + entity.getClass() + " with key " + key);
            } else if (cursor.isLast()) {
                readEntity(cursor, entity, 0);
                attachEntity(key, entity, true);
            } else {
                throw new DaoException("Expected unique result, but count was " + cursor.getCount());
            }
        } finally {
            cursor.close();
        }
    }

    public void update(T entity) {
        assertSinglePk();
        DatabaseStatement stmt = this.statements.getUpdateStatement();
        if (this.db.isDbLockedByCurrentThread()) {
            synchronized (stmt) {
                if (this.isStandardSQLite) {
                    updateInsideSynchronized(entity, (SQLiteStatement) stmt.getRawStatement(), true);
                } else {
                    updateInsideSynchronized(entity, stmt, true);
                }
            }
            return;
        }
        this.db.beginTransaction();
        try {
            synchronized (stmt) {
                updateInsideSynchronized(entity, stmt, true);
            }
            this.db.setTransactionSuccessful();
            this.db.endTransaction();
        } catch (Throwable th) {
            this.db.endTransaction();
            throw th;
        }
    }

    public QueryBuilder<T> queryBuilder() {
        return QueryBuilder.internalCreate(this);
    }

    /* access modifiers changed from: protected */
    public void updateInsideSynchronized(T entity, DatabaseStatement stmt, boolean lock) {
        bindValues(stmt, entity);
        int index = this.config.allColumns.length + 1;
        K key = getKey(entity);
        if (key instanceof Long) {
            stmt.bindLong(index, ((Long) key).longValue());
        } else if (key != null) {
            stmt.bindString(index, key.toString());
        } else {
            throw new DaoException("Cannot update entity without key - was it inserted before?");
        }
        stmt.execute();
        attachEntity(key, entity, lock);
    }

    /* access modifiers changed from: protected */
    public void updateInsideSynchronized(T entity, SQLiteStatement stmt, boolean lock) {
        bindValues(stmt, entity);
        int index = this.config.allColumns.length + 1;
        K key = getKey(entity);
        if (key instanceof Long) {
            stmt.bindLong(index, ((Long) key).longValue());
        } else if (key != null) {
            stmt.bindString(index, key.toString());
        } else {
            throw new DaoException("Cannot update entity without key - was it inserted before?");
        }
        stmt.execute();
        attachEntity(key, entity, lock);
    }

    /* access modifiers changed from: protected */
    public final void attachEntity(K key, T entity, boolean lock) {
        attachEntity(entity);
        IdentityScope<K, T> identityScope2 = this.identityScope;
        if (identityScope2 != null && key != null) {
            if (lock) {
                identityScope2.put(key, entity);
            } else {
                identityScope2.putNoLock(key, entity);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void attachEntity(T t) {
    }

    public void updateInTx(Iterable<T> entities) {
        DatabaseStatement stmt = this.statements.getUpdateStatement();
        this.db.beginTransaction();
        RuntimeException txEx = null;
        try {
            synchronized (stmt) {
                IdentityScope<K, T> identityScope2 = this.identityScope;
                if (identityScope2 != null) {
                    identityScope2.lock();
                }
                try {
                    if (this.isStandardSQLite) {
                        SQLiteStatement rawStmt = (SQLiteStatement) stmt.getRawStatement();
                        for (T entity : entities) {
                            updateInsideSynchronized(entity, rawStmt, false);
                        }
                    } else {
                        for (T entity2 : entities) {
                            updateInsideSynchronized(entity2, stmt, false);
                        }
                    }
                } finally {
                    IdentityScope<K, T> identityScope3 = this.identityScope;
                    if (identityScope3 != null) {
                        identityScope3.unlock();
                    }
                }
            }
            this.db.setTransactionSuccessful();
            try {
                this.db.endTransaction();
                if (txEx != null) {
                    throw txEx;
                }
            } catch (RuntimeException e) {
                if (0 != 0) {
                    DaoLog.w("Could not end transaction (rethrowing initial exception)", e);
                    throw null;
                }
                throw e;
            }
        } catch (RuntimeException e2) {
            txEx = e2;
            try {
                this.db.endTransaction();
            } catch (RuntimeException e3) {
                DaoLog.w("Could not end transaction (rethrowing initial exception)", e3);
                throw txEx;
            }
        } catch (Throwable th) {
            try {
                this.db.endTransaction();
                throw th;
            } catch (RuntimeException e4) {
                if (0 != 0) {
                    DaoLog.w("Could not end transaction (rethrowing initial exception)", e4);
                    throw null;
                }
                throw e4;
            }
        }
    }

    public void updateInTx(T... entities) {
        updateInTx(Arrays.asList(entities));
    }

    /* access modifiers changed from: protected */
    public void assertSinglePk() {
        if (this.config.pkColumns.length != 1) {
            throw new DaoException(this + " (" + this.config.tablename + ") does not have a single-column primary key");
        }
    }

    public long count() {
        return this.statements.getCountStatement().simpleQueryForLong();
    }

    /* access modifiers changed from: protected */
    public K getKeyVerified(T entity) {
        K key = getKey(entity);
        if (key != null) {
            return key;
        }
        if (entity == null) {
            throw new NullPointerException("Entity may not be null");
        }
        throw new DaoException("Entity has no key");
    }

    @Experimental
    public RxDao<T, K> rxPlain() {
        if (this.rxDaoPlain == null) {
            this.rxDaoPlain = new RxDao<>(this);
        }
        return this.rxDaoPlain;
    }

    @Experimental
    public RxDao<T, K> rx() {
        if (this.rxDao == null) {
            this.rxDao = new RxDao<>(this, Schedulers.io());
        }
        return this.rxDao;
    }

    public Database getDatabase() {
        return this.db;
    }
}
