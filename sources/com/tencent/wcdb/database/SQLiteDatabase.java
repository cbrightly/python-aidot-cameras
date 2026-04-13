package com.tencent.wcdb.database;

import android.content.ContentValues;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import com.tencent.wcdb.SQLException;
import com.tencent.wcdb.e;
import com.tencent.wcdb.f;
import com.tencent.wcdb.g;
import com.tencent.wcdb.h;
import com.tencent.wcdb.support.Log;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;
import java.util.WeakHashMap;

public final class SQLiteDatabase extends b {
    private static final WeakHashMap<SQLiteDatabase, Object> d = new WeakHashMap<>();
    private static final String[] f = {"", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE "};
    private c a1;
    private final f p0;
    private boolean p1;
    private final ThreadLocal<j> q = new a();
    private final b x;
    private final f y;
    private final Object z = new Object();

    public interface CustomFunction {
        void a(String[] strArr);
    }

    public interface b {
        e a(SQLiteDatabase sQLiteDatabase, e eVar, String str, h hVar);

        h b(SQLiteDatabase sQLiteDatabase, String str, Object[] objArr, com.tencent.wcdb.support.a aVar);
    }

    static {
        Class<SQLiteDatabase> cls = SQLiteDatabase.class;
        SQLiteGlobal.a();
    }

    public class a extends ThreadLocal<j> {
        a() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public j initialValue() {
            return SQLiteDatabase.this.l();
        }
    }

    private SQLiteDatabase(String path, int openFlags, b cursorFactory, f errorHandler) {
        this.x = cursorFactory;
        this.y = errorHandler != null ? errorHandler : new h(true);
        this.p0 = new f(path, openFlags);
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            n(true);
        } finally {
            super.finalize();
        }
    }

    /* access modifiers changed from: protected */
    public void c() {
        n(false);
    }

    private void n(boolean finalized) {
        c pool;
        synchronized (this.z) {
            pool = this.a1;
            this.a1 = null;
        }
        if (!finalized) {
            WeakHashMap<SQLiteDatabase, Object> weakHashMap = d;
            synchronized (weakHashMap) {
                weakHashMap.remove(this);
            }
            if (pool != null) {
                pool.close();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public String r() {
        String str;
        synchronized (this.z) {
            str = this.p0.b;
        }
        return str;
    }

    /* access modifiers changed from: package-private */
    public void I() {
        this.y.a(this);
    }

    /* access modifiers changed from: package-private */
    public j t() {
        return this.q.get();
    }

    /* access modifiers changed from: package-private */
    public j l() {
        c pool;
        synchronized (this.z) {
            a1();
            pool = this.a1;
        }
        return new j(pool);
    }

    /* access modifiers changed from: package-private */
    public int s(boolean readOnly) {
        int flags = readOnly ? 1 : 2;
        if (z()) {
            return flags | 4;
        }
        return flags;
    }

    private static boolean z() {
        Looper looper = Looper.myLooper();
        return looper != null && looper == Looper.getMainLooper();
    }

    public static SQLiteDatabase P(String path, byte[] password, SQLiteCipherSpec cipher, b factory, int flags, f errorHandler, int poolSize) {
        SQLiteDatabase db = new SQLiteDatabase(path, flags, factory, errorHandler);
        db.J(password, cipher, poolSize);
        return db;
    }

    public static SQLiteDatabase W(String path, byte[] password, SQLiteCipherSpec cipher, b factory, f errorHandler, int poolSize) {
        return P(path, password, cipher, factory, net.sqlcipher.database.SQLiteDatabase.CREATE_IF_NECESSARY, errorHandler, poolSize);
    }

    public static SQLiteDatabase o0(String path, byte[] password, b factory, f errorHandler, int poolSize) {
        return W(path, password, (SQLiteCipherSpec) null, factory, errorHandler, poolSize);
    }

    private void J(byte[] password, SQLiteCipherSpec cipher, int poolSize) {
        try {
            T(password, cipher, poolSize);
        } catch (SQLiteDatabaseCorruptException e) {
            try {
                I();
                T(password, cipher, poolSize);
            } catch (SQLiteException ex) {
                Log.b("WCDB.SQLiteDatabase", "Failed to open database '" + r() + "'.", ex);
                close();
                throw ex;
            }
        }
    }

    private void T(byte[] password, SQLiteCipherSpec cipher, int poolSize) {
        synchronized (this.z) {
            if (this.a1 == null) {
                this.a1 = c.P(this, this.p0, password, cipher, poolSize);
            } else {
                throw new AssertionError();
            }
        }
        WeakHashMap<SQLiteDatabase, Object> weakHashMap = d;
        synchronized (weakHashMap) {
            weakHashMap.put(this, (Object) null);
        }
    }

    public int getVersion() {
        return Long.valueOf(g.g(this, "PRAGMA user_version;", (String[]) null)).intValue();
    }

    public k j(String sql) {
        a();
        try {
            return new k(this, sql, (Object[]) null);
        } finally {
            g();
        }
    }

    public e u0(String sql, Object[] selectionArgs) {
        return F0((b) null, sql, selectionArgs, (String) null, (com.tencent.wcdb.support.a) null);
    }

    public e F0(b cursorFactory, String sql, Object[] selectionArgs, String editTable, com.tencent.wcdb.support.a cancellationSignal) {
        a();
        try {
            return new g(this, sql, editTable, cancellationSignal).a(cursorFactory != null ? cursorFactory : this.x, selectionArgs);
        } finally {
            g();
        }
    }

    public long v(String table, String nullColumnHack, ContentValues values) {
        try {
            return x(table, nullColumnHack, values, 0);
        } catch (SQLiteDatabaseCorruptException e) {
            throw e;
        } catch (SQLException e2) {
            Log.b("WCDB.SQLiteDatabase", "Error inserting %s: %s", values, e2);
            return -1;
        }
    }

    private Set<String> E(ContentValues cv) {
        if (Build.VERSION.SDK_INT >= 11) {
            return cv.keySet();
        }
        try {
            Field fd = Class.forName("android.content.ContentValues").getDeclaredField("mValues");
            fd.setAccessible(true);
            return ((HashMap) fd.get(cv)).keySet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public long x(String table, String nullColumnHack, ContentValues initialValues, int conflictAlgorithm) {
        k statement;
        a();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT");
            sql.append(f[conflictAlgorithm]);
            sql.append(" INTO ");
            sql.append(table);
            sql.append('(');
            Object[] bindArgs = null;
            int size = (initialValues == null || initialValues.size() <= 0) ? 0 : initialValues.size();
            if (size > 0) {
                bindArgs = new Object[size];
                int i = 0;
                for (String colName : E(initialValues)) {
                    sql.append(i > 0 ? "," : "");
                    sql.append(colName);
                    bindArgs[i] = initialValues.get(colName);
                    i++;
                }
                sql.append(')');
                sql.append(" VALUES (");
                int i2 = 0;
                while (i2 < size) {
                    sql.append(i2 > 0 ? ",?" : "?");
                    i2++;
                }
            } else {
                sql.append(nullColumnHack + ") VALUES (NULL");
            }
            sql.append(')');
            statement = new k(this, sql.toString(), bindArgs);
            long executeInsert = statement.executeInsert();
            statement.close();
            g();
            return executeInsert;
        } catch (Throwable th) {
            g();
            throw th;
        }
    }

    public int m(String table, String whereClause, String[] whereArgs) {
        k statement;
        String str;
        a();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ");
            sb.append(table);
            if (!TextUtils.isEmpty(whereClause)) {
                str = " WHERE " + whereClause;
            } else {
                str = "";
            }
            sb.append(str);
            statement = new k(this, sb.toString(), whereArgs);
            int executeUpdateDelete = statement.executeUpdateDelete();
            statement.close();
            g();
            return executeUpdateDelete;
        } catch (Throwable th) {
            g();
            throw th;
        }
    }

    public int b1(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return c1(table, values, whereClause, whereArgs, 0);
    }

    public int c1(String table, ContentValues values, String whereClause, String[] whereArgs, int conflictAlgorithm) {
        k statement;
        if (values == null || values.size() == 0) {
            throw new IllegalArgumentException("Empty values");
        }
        a();
        try {
            StringBuilder sql = new StringBuilder(120);
            sql.append("UPDATE ");
            sql.append(f[conflictAlgorithm]);
            sql.append(table);
            sql.append(" SET ");
            int setValuesSize = values.size();
            int bindArgsSize = whereArgs == null ? setValuesSize : whereArgs.length + setValuesSize;
            Object[] bindArgs = new Object[bindArgsSize];
            int i = 0;
            for (String colName : E(values)) {
                sql.append(i > 0 ? "," : "");
                sql.append(colName);
                bindArgs[i] = values.get(colName);
                sql.append("=?");
                i++;
            }
            if (whereArgs != null) {
                for (int i2 = setValuesSize; i2 < bindArgsSize; i2++) {
                    bindArgs[i2] = whereArgs[i2 - setValuesSize];
                }
            }
            if (!TextUtils.isEmpty(whereClause)) {
                sql.append(" WHERE ");
                sql.append(whereClause);
            }
            statement = new k(this, sql.toString(), bindArgs);
            int executeUpdateDelete = statement.executeUpdateDelete();
            statement.close();
            g();
            return executeUpdateDelete;
        } catch (Throwable th) {
            g();
            throw th;
        }
    }

    public void execSQL(String sql) {
        o(sql, (Object[]) null, (com.tencent.wcdb.support.a) null);
    }

    private int o(String sql, Object[] bindArgs, com.tencent.wcdb.support.a cancellationSignal) {
        k statement;
        a();
        try {
            if (g.e(sql) == 3) {
                boolean disableWal = false;
                synchronized (this.z) {
                    if (!this.p1) {
                        this.p1 = true;
                        disableWal = true;
                    }
                }
                if (disableWal) {
                    disableWriteAheadLogging();
                }
            }
            statement = new k(this, sql, bindArgs);
            int v = statement.v(cancellationSignal);
            statement.close();
            g();
            return v;
        } catch (Throwable th) {
            g();
            throw th;
        }
    }

    public boolean isOpen() {
        boolean z2;
        synchronized (this.z) {
            z2 = this.a1 != null;
        }
        return z2;
    }

    public final String getPath() {
        String str;
        synchronized (this.z) {
            str = this.p0.a;
        }
        return str;
    }

    public void disableWriteAheadLogging() {
        synchronized (this.z) {
            a1();
            f fVar = this.p0;
            int i = fVar.d;
            if ((i & 536870912) != 0) {
                fVar.d = i & -536870913;
                try {
                    this.a1.o0(fVar);
                } catch (RuntimeException ex) {
                    f fVar2 = this.p0;
                    fVar2.d = 536870912 | fVar2.d;
                    throw ex;
                }
            }
        }
    }

    public m u() {
        m u;
        synchronized (this.z) {
            a1();
            u = this.a1.u();
        }
        return u;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0027, code lost:
        r1 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r1 = u0("pragma database_list;", (java.lang.Object[]) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0034, code lost:
        if (r1.moveToNext() == false) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0036, code lost:
        r0.add(new android.util.Pair(r1.getString(1), r1.getString(2)));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004d, code lost:
        g();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0051, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0052, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0053, code lost:
        if (r1 != null) goto L_0x0055;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0059, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005a, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x005b, code lost:
        g();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x005e, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<android.util.Pair<java.lang.String, java.lang.String>> getAttachedDbs() {
        /*
            r5 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.Object r1 = r5.z
            monitor-enter(r1)
            com.tencent.wcdb.database.c r2 = r5.a1     // Catch:{ all -> 0x005f }
            r3 = 0
            if (r2 != 0) goto L_0x000f
            monitor-exit(r1)     // Catch:{ all -> 0x005f }
            return r3
        L_0x000f:
            boolean r2 = r5.p1     // Catch:{ all -> 0x005f }
            if (r2 != 0) goto L_0x0023
            android.util.Pair r2 = new android.util.Pair     // Catch:{ all -> 0x005f }
            java.lang.String r3 = "main"
            com.tencent.wcdb.database.f r4 = r5.p0     // Catch:{ all -> 0x005f }
            java.lang.String r4 = r4.a     // Catch:{ all -> 0x005f }
            r2.<init>(r3, r4)     // Catch:{ all -> 0x005f }
            r0.add(r2)     // Catch:{ all -> 0x005f }
            monitor-exit(r1)     // Catch:{ all -> 0x005f }
            return r0
        L_0x0023:
            r5.a()     // Catch:{ all -> 0x005f }
            monitor-exit(r1)     // Catch:{ all -> 0x005f }
            r1 = 0
            java.lang.String r2 = "pragma database_list;"
            com.tencent.wcdb.e r2 = r5.u0(r2, r3)     // Catch:{ all -> 0x0052 }
            r1 = r2
        L_0x0030:
            boolean r2 = r1.moveToNext()     // Catch:{ all -> 0x0052 }
            if (r2 == 0) goto L_0x0049
            android.util.Pair r2 = new android.util.Pair     // Catch:{ all -> 0x0052 }
            r3 = 1
            java.lang.String r3 = r1.getString(r3)     // Catch:{ all -> 0x0052 }
            r4 = 2
            java.lang.String r4 = r1.getString(r4)     // Catch:{ all -> 0x0052 }
            r2.<init>(r3, r4)     // Catch:{ all -> 0x0052 }
            r0.add(r2)     // Catch:{ all -> 0x0052 }
            goto L_0x0030
        L_0x0049:
            r1.close()     // Catch:{ all -> 0x005a }
            r5.g()
            return r0
        L_0x0052:
            r2 = move-exception
            if (r1 == 0) goto L_0x0058
            r1.close()     // Catch:{ all -> 0x005a }
        L_0x0058:
            throw r2     // Catch:{ all -> 0x005a }
        L_0x005a:
            r1 = move-exception
            r5.g()
            throw r1
        L_0x005f:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x005f }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wcdb.database.SQLiteDatabase.getAttachedDbs():java.util.List");
    }

    public long i(String operation, boolean readOnly, boolean interactive) {
        if (operation == null) {
            operation = "unnamedNative";
        }
        int connectionFlags = readOnly ? 1 : 2;
        if (interactive) {
            connectionFlags |= 4;
        }
        long handle = t().b(connectionFlags).x(operation);
        if (handle != 0) {
            return handle;
        }
        throw new IllegalStateException("SQLiteConnection native handle not initialized.");
    }

    public void P0(long nativePtr, Exception ex) {
        t().p(ex);
    }

    public String toString() {
        return "SQLiteDatabase: " + getPath();
    }

    private void a1() {
        if (this.a1 == null) {
            throw new IllegalStateException("The database '" + this.p0.b + "' is not open.");
        }
    }
}
