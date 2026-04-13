package net.sqlcipher.database;

import android.util.Log;

public class SQLiteCompiledSql {
    private static final String TAG = "SQLiteCompiledSql";
    SQLiteDatabase mDatabase;
    private boolean mInUse = false;
    private String mSqlStmt = null;
    long nHandle = 0;
    long nStatement = 0;

    private final native void native_compile(String str);

    private final native void native_finalize();

    SQLiteCompiledSql(SQLiteDatabase db, String sql) {
        if (db.isOpen()) {
            this.mDatabase = db;
            this.mSqlStmt = sql;
            this.nHandle = db.mNativeHandle;
            compile(sql, true);
            return;
        }
        throw new IllegalStateException("database " + db.getPath() + " already closed");
    }

    private void compile(String sql, boolean forceCompilation) {
        if (!this.mDatabase.isOpen()) {
            throw new IllegalStateException("database " + this.mDatabase.getPath() + " already closed");
        } else if (forceCompilation) {
            this.mDatabase.lock();
            try {
                native_compile(sql);
            } finally {
                this.mDatabase.unlock();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void releaseSqlStatement() {
        if (this.nStatement != 0) {
            if (SQLiteDebug.DEBUG_ACTIVE_CURSOR_FINALIZATION) {
                Log.v(TAG, "closed and deallocated DbObj (id#" + this.nStatement + ")");
            }
            try {
                this.mDatabase.lock();
                native_finalize();
                this.nStatement = 0;
            } finally {
                this.mDatabase.unlock();
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002d, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean acquire() {
        /*
            r5 = this;
            monitor-enter(r5)
            boolean r0 = r5.mInUse     // Catch:{ all -> 0x002e }
            if (r0 == 0) goto L_0x0008
            r0 = 0
            monitor-exit(r5)
            return r0
        L_0x0008:
            r0 = 1
            r5.mInUse = r0     // Catch:{ all -> 0x002e }
            boolean r1 = net.sqlcipher.database.SQLiteDebug.DEBUG_ACTIVE_CURSOR_FINALIZATION     // Catch:{ all -> 0x002e }
            if (r1 == 0) goto L_0x002c
            java.lang.String r1 = "SQLiteCompiledSql"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x002e }
            r2.<init>()     // Catch:{ all -> 0x002e }
            java.lang.String r3 = "Acquired DbObj (id#"
            r2.append(r3)     // Catch:{ all -> 0x002e }
            long r3 = r5.nStatement     // Catch:{ all -> 0x002e }
            r2.append(r3)     // Catch:{ all -> 0x002e }
            java.lang.String r3 = ") from DB cache"
            r2.append(r3)     // Catch:{ all -> 0x002e }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x002e }
            android.util.Log.v(r1, r2)     // Catch:{ all -> 0x002e }
        L_0x002c:
            monitor-exit(r5)
            return r0
        L_0x002e:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.sqlcipher.database.SQLiteCompiledSql.acquire():boolean");
    }

    /* access modifiers changed from: package-private */
    public synchronized void release() {
        if (SQLiteDebug.DEBUG_ACTIVE_CURSOR_FINALIZATION) {
            Log.v(TAG, "Released DbObj (id#" + this.nStatement + ") back to DB cache");
        }
        this.mInUse = false;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            if (this.nStatement != 0) {
                if (SQLiteDebug.DEBUG_ACTIVE_CURSOR_FINALIZATION) {
                    Log.v(TAG, "** warning ** Finalized DbObj (id#" + this.nStatement + ")");
                }
                releaseSqlStatement();
                super.finalize();
            }
        } finally {
            super.finalize();
        }
    }
}
