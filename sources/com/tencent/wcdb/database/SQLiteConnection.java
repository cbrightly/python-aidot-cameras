package com.tencent.wcdb.database;

import android.annotation.SuppressLint;
import android.support.v4.media.session.PlaybackStateCompat;
import com.google.maps.android.BuildConfig;
import com.tencent.wcdb.g;
import com.tencent.wcdb.support.Log;
import com.tencent.wcdb.support.a;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

public final class SQLiteConnection implements a.C0224a {
    private static final String[] a = new String[0];
    /* access modifiers changed from: private */
    public static final byte[] b = new byte[0];
    private static final Pattern c = Pattern.compile("[\\s]*\\n+[\\s]*");
    /* access modifiers changed from: private */
    public final c d;
    private final f e;
    private final int f;
    private final boolean g;
    private final boolean h;
    private final e i;
    private d j;
    /* access modifiers changed from: private */
    public final c k = new c();
    private Thread l;
    /* access modifiers changed from: private */
    public int m;
    private long n;
    private boolean o;
    private int p;
    private byte[] q;
    private SQLiteCipherSpec r;
    private b s;
    private int t;

    private static native void nativeBindBlob(long j2, long j3, int i2, byte[] bArr);

    private static native void nativeBindDouble(long j2, long j3, int i2, double d2);

    private static native void nativeBindLong(long j2, long j3, int i2, long j4);

    private static native void nativeBindNull(long j2, long j3, int i2);

    private static native void nativeBindString(long j2, long j3, int i2, String str);

    private static native void nativeCancel(long j2);

    private static native void nativeClose(long j2);

    private static native void nativeExecute(long j2, long j3);

    private static native int nativeExecuteForChangedRowCount(long j2, long j3);

    private static native long nativeExecuteForCursorWindow(long j2, long j3, long j4, int i2, int i3, boolean z);

    private static native long nativeExecuteForLastInsertedRowId(long j2, long j3);

    private static native long nativeExecuteForLong(long j2, long j3);

    private static native String nativeExecuteForString(long j2, long j3);

    private static native void nativeFinalizeStatement(long j2, long j3);

    private static native int nativeGetColumnCount(long j2, long j3);

    private static native String nativeGetColumnName(long j2, long j3, int i2);

    private static native int nativeGetDbLookaside(long j2);

    private static native int nativeGetParameterCount(long j2, long j3);

    private static native boolean nativeIsReadOnly(long j2, long j3);

    private native long nativeOpen(String str, int i2, String str2);

    private static native long nativePrepareStatement(long j2, String str);

    private static native void nativeRegisterCustomFunction(long j2, SQLiteCustomFunction sQLiteCustomFunction);

    private static native void nativeRegisterLocalizedCollators(long j2, String str);

    private static native void nativeResetCancel(long j2, boolean z);

    private static native void nativeResetStatement(long j2, long j3, boolean z);

    private static native long nativeSQLiteHandle(long j2, boolean z);

    private static native void nativeSetKey(long j2, byte[] bArr);

    private static native void nativeSetUpdateNotification(long j2, boolean z, boolean z2);

    private static native void nativeSetWalHook(long j2);

    private static native long nativeWalCheckpoint(long j2, String str);

    private SQLiteConnection(c pool, f configuration, int connectionId, boolean primaryConnection, byte[] password, SQLiteCipherSpec cipher) {
        SQLiteCipherSpec sQLiteCipherSpec = null;
        this.q = password;
        this.r = cipher != null ? new SQLiteCipherSpec(cipher) : sQLiteCipherSpec;
        this.d = pool;
        f fVar = new f(configuration);
        this.e = fVar;
        this.f = connectionId;
        this.g = primaryConnection;
        this.h = (configuration.d & 1) == 0 ? false : true;
        this.i = new e(fVar.e);
    }

    /* access modifiers changed from: package-private */
    public long x(String operation) {
        if (this.n == 0) {
            return 0;
        }
        if (operation != null && this.s == null) {
            b a2 = this.k.a(operation, (String) null, (Object[]) null);
            this.s = a2;
            a2.j = 99;
        }
        this.t++;
        return nativeSQLiteHandle(this.n, true);
    }

    /* access modifiers changed from: package-private */
    public void p(Exception ex) {
        int i2 = this.t - 1;
        this.t = i2;
        if (i2 == 0 && this.s != null) {
            nativeSQLiteHandle(this.n, false);
            if (ex == null) {
                this.k.d(this.s.i);
            } else {
                this.k.f(this.s.i, ex);
            }
            this.s = null;
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            c cVar = this.d;
            if (!(cVar == null || this.n == 0)) {
                cVar.J();
            }
            o(true);
        } finally {
            super.finalize();
        }
    }

    static SQLiteConnection C(c pool, f configuration, int connectionId, boolean primaryConnection, byte[] password, SQLiteCipherSpec cipher) {
        SQLiteConnection connection = new SQLiteConnection(pool, configuration, connectionId, primaryConnection, password, cipher);
        try {
            connection.D();
            return connection;
        } catch (SQLiteException ex) {
            SQLiteDebug.a(connection);
            connection.o(false);
            throw ex;
        }
    }

    /* access modifiers changed from: package-private */
    public void l() {
        o(false);
    }

    private void D() {
        f fVar = this.e;
        long nativeOpen = nativeOpen(fVar.a, fVar.d, fVar.c);
        this.n = nativeOpen;
        byte[] bArr = this.q;
        if (bArr != null && bArr.length == 0) {
            this.q = null;
        }
        byte[] bArr2 = this.q;
        if (bArr2 != null) {
            nativeSetKey(nativeOpen, bArr2);
            L();
        }
        R();
        S();
        M();
        V();
        T();
        O();
        K();
        P();
        U();
        int functionCount = this.e.l.size();
        for (int i2 = 0; i2 < functionCount; i2++) {
            nativeRegisterCustomFunction(this.n, this.e.l.get(i2));
        }
    }

    private void o(boolean finalized) {
        if (this.n != 0) {
            int cookie = this.k.a("close", (String) null, (Object[]) null).i;
            try {
                this.i.c();
                nativeClose(this.n);
                this.n = 0;
            } finally {
                this.k.c(cookie);
            }
        }
    }

    private void R() {
        long newValue;
        String pragmaCmd;
        int i2;
        if (!this.e.a()) {
            if (this.q != null) {
                pragmaCmd = "PRAGMA cipher_page_size";
                SQLiteCipherSpec sQLiteCipherSpec = this.r;
                if (sQLiteCipherSpec == null || (i2 = sQLiteCipherSpec.pageSize) <= 0) {
                    i2 = SQLiteGlobal.a;
                }
                newValue = (long) i2;
            } else {
                pragmaCmd = "PRAGMA page_size";
                newValue = (long) SQLiteGlobal.a;
            }
            if (u(pragmaCmd, (Object[]) null, (com.tencent.wcdb.support.a) null) != newValue) {
                q(pragmaCmd + "=" + newValue, (Object[]) null, (com.tencent.wcdb.support.a) null);
            }
        }
    }

    private void L() {
        SQLiteCipherSpec sQLiteCipherSpec = this.r;
        if (sQLiteCipherSpec != null) {
            if (sQLiteCipherSpec.cipher != null) {
                q("PRAGMA cipher=" + g.j(this.r.cipher), (Object[]) null, (com.tencent.wcdb.support.a) null);
            }
            if (this.r.kdfIteration != 0) {
                q("PRAGMA kdf_iter=" + this.r.kdfIteration, (Object[]) null, (com.tencent.wcdb.support.a) null);
            }
            q("PRAGMA cipher_use_hmac=" + this.r.hmacEnabled, (Object[]) null, (com.tencent.wcdb.support.a) null);
        }
    }

    private void notifyCheckpoint(String dbName, int pages) {
        this.d.E(dbName, pages);
    }

    private void K() {
        if (!this.e.a() && !this.h) {
            if (this.e.h) {
                nativeSetWalHook(this.n);
            } else if (u("PRAGMA wal_autocheckpoint", (Object[]) null, (com.tencent.wcdb.support.a) null) != 100) {
                u("PRAGMA wal_autocheckpoint=100", (Object[]) null, (com.tencent.wcdb.support.a) null);
            }
        }
    }

    private void O() {
        if (!this.e.a() && !this.h && u("PRAGMA journal_size_limit", (Object[]) null, (com.tencent.wcdb.support.a) null) != PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED) {
            u("PRAGMA journal_size_limit=524288", (Object[]) null, (com.tencent.wcdb.support.a) null);
        }
    }

    private void M() {
        if (!this.h) {
            long newValue = this.e.g ? 1 : 0;
            if (u("PRAGMA foreign_keys", (Object[]) null, (com.tencent.wcdb.support.a) null) != newValue) {
                q("PRAGMA foreign_keys=" + newValue, (Object[]) null, (com.tencent.wcdb.support.a) null);
            }
        }
    }

    private void V() {
        String journalMode;
        if (!this.e.a() && !this.h) {
            if ((this.e.d & 536870912) != 0) {
                journalMode = "WAL";
            } else {
                journalMode = "PERSIST";
            }
            N(journalMode);
        }
    }

    private void T() {
        int syncMode = this.e.i;
        q("PRAGMA synchronous=" + syncMode, (Object[]) null, (com.tencent.wcdb.support.a) null);
    }

    private void N(String newValue) {
        String value = v("PRAGMA journal_mode", (Object[]) null, (com.tencent.wcdb.support.a) null);
        if (!value.equalsIgnoreCase(newValue)) {
            try {
                if (v("PRAGMA journal_mode=" + newValue, (Object[]) null, (com.tencent.wcdb.support.a) null).equalsIgnoreCase(newValue)) {
                    return;
                }
            } catch (SQLiteDatabaseLockedException e2) {
            }
            Log.f("WCDB.SQLiteConnection", "Could not change the database journal mode of '" + this.e.b + "' from '" + value + "' to '" + newValue + "' because the database is locked.  This usually means that there are other open connections to the database which prevents the database from enabling or disabling write-ahead logging mode.  Proceeding without changing the journal mode.");
        }
    }

    private void P() {
        String str = "COMMIT";
        f fVar = this.e;
        int i2 = fVar.d | 16;
        fVar.d = i2;
        if ((i2 & 16) == 0) {
            String newLocale = fVar.f.toString();
            nativeRegisterLocalizedCollators(this.n, newLocale);
            if (!this.h) {
                try {
                    q("CREATE TABLE IF NOT EXISTS android_metadata (locale TEXT)", (Object[]) null, (com.tencent.wcdb.support.a) null);
                    String oldLocale = v("SELECT locale FROM android_metadata UNION SELECT NULL ORDER BY locale DESC LIMIT 1", (Object[]) null, (com.tencent.wcdb.support.a) null);
                    if (oldLocale == null || !oldLocale.equals(newLocale)) {
                        q("BEGIN", (Object[]) null, (com.tencent.wcdb.support.a) null);
                        q("DELETE FROM android_metadata", (Object[]) null, (com.tencent.wcdb.support.a) null);
                        q("INSERT INTO android_metadata (locale) VALUES(?)", new Object[]{newLocale}, (com.tencent.wcdb.support.a) null);
                        q("REINDEX LOCALIZED", (Object[]) null, (com.tencent.wcdb.support.a) null);
                        if (1 == 0) {
                            str = "ROLLBACK";
                        }
                        q(str, (Object[]) null, (com.tencent.wcdb.support.a) null);
                    }
                } catch (RuntimeException ex) {
                    throw new SQLiteException("Failed to change locale for db '" + this.e.b + "' to '" + newLocale + "'.", ex);
                } catch (Throwable th) {
                    if (0 == 0) {
                        str = "ROLLBACK";
                    }
                    q(str, (Object[]) null, (com.tencent.wcdb.support.a) null);
                    throw th;
                }
            }
        }
    }

    private void S() {
        if (this.h) {
            q("PRAGMA query_only = 1", (Object[]) null, (com.tencent.wcdb.support.a) null);
        }
    }

    private void U() {
        long j2 = this.n;
        f fVar = this.e;
        nativeSetUpdateNotification(j2, fVar.j, fVar.k);
    }

    /* access modifiers changed from: package-private */
    public void F(f configuration) {
        boolean updateNotificationChanged = false;
        this.o = false;
        int functionCount = configuration.l.size();
        for (int i2 = 0; i2 < functionCount; i2++) {
            SQLiteCustomFunction function = configuration.l.get(i2);
            if (!this.e.l.contains(function)) {
                nativeRegisterCustomFunction(this.n, function);
            }
        }
        int i3 = configuration.d;
        f fVar = this.e;
        boolean walModeChanged = (536870912 & (i3 ^ fVar.d)) != 0;
        boolean foreignKeyModeChanged = configuration.g != fVar.g;
        boolean localeChanged = !configuration.f.equals(fVar.f);
        boolean z = configuration.h;
        f fVar2 = this.e;
        boolean checkpointStrategyChanged = z != fVar2.h;
        boolean synchronousChanged = configuration.i != fVar2.i;
        if (!(configuration.j == fVar2.j && configuration.k == fVar2.k)) {
            updateNotificationChanged = true;
        }
        fVar2.b(configuration);
        this.i.g(configuration.e);
        if (foreignKeyModeChanged) {
            M();
        }
        if (walModeChanged) {
            V();
        }
        if (synchronousChanged) {
            T();
        }
        if (checkpointStrategyChanged) {
            K();
        }
        if (localeChanged) {
            P();
        }
        if (updateNotificationChanged) {
            U();
        }
    }

    /* access modifiers changed from: package-private */
    public void Q(boolean readOnly) {
        this.o = readOnly;
    }

    /* access modifiers changed from: package-private */
    public void J(Thread thread, int tid) {
        this.l = thread;
        this.m = tid;
    }

    /* access modifiers changed from: package-private */
    public boolean z(String sql) {
        return this.i.d(sql) != null;
    }

    public boolean A() {
        return this.g;
    }

    public void E(String sql, l outStatementInfo) {
        c cVar;
        d statement;
        if (sql != null) {
            b operation = this.k.a("prepare", sql, (Object[]) null);
            int cookie = operation.i;
            try {
                statement = h(sql);
                operation.j = statement.f;
                if (outStatementInfo != null) {
                    outStatementInfo.a = statement.e;
                    outStatementInfo.c = statement.g;
                    int columnCount = nativeGetColumnCount(this.n, statement.r());
                    if (columnCount == 0) {
                        outStatementInfo.b = a;
                    } else {
                        outStatementInfo.b = new String[columnCount];
                        for (int i2 = 0; i2 < columnCount; i2++) {
                            outStatementInfo.b[i2] = nativeGetColumnName(this.n, statement.r(), i2);
                        }
                    }
                }
                H(statement);
                this.k.c(cookie);
            } catch (RuntimeException ex) {
                try {
                    if (((ex instanceof SQLiteDatabaseLockedException) || (ex instanceof SQLiteTableLockedException)) && (cVar = this.d) != null) {
                        cVar.v(sql);
                    }
                    this.k.f(cookie, ex);
                    throw ex;
                } catch (Throwable th) {
                    this.k.c(cookie);
                    throw th;
                }
            } catch (Throwable th2) {
                H(statement);
                throw th2;
            }
        } else {
            throw new IllegalArgumentException("sql must not be null.");
        }
    }

    public void q(String sql, Object[] bindArgs, com.tencent.wcdb.support.a cancellationSignal) {
        c cVar;
        if (sql != null) {
            b operation = this.k.a("execute", sql, bindArgs);
            int cookie = operation.i;
            try {
                d statement = h(sql);
                operation.j = statement.f;
                try {
                    W(statement);
                    k(statement, bindArgs);
                    i(statement);
                    j(cancellationSignal);
                    nativeExecute(this.n, statement.r());
                    n(cancellationSignal);
                    H(statement);
                    this.k.c(cookie);
                } catch (Throwable th) {
                    H(statement);
                    throw th;
                }
            } catch (RuntimeException ex) {
                try {
                    if (((ex instanceof SQLiteDatabaseLockedException) || (ex instanceof SQLiteTableLockedException)) && (cVar = this.d) != null) {
                        cVar.v(sql);
                    }
                    this.k.f(cookie, ex);
                    throw ex;
                } catch (Throwable th2) {
                    this.k.c(cookie);
                    throw th2;
                }
            }
        } else {
            throw new IllegalArgumentException("sql must not be null.");
        }
    }

    public long u(String sql, Object[] bindArgs, com.tencent.wcdb.support.a cancellationSignal) {
        c cVar;
        if (sql != null) {
            b operation = this.k.a("executeForLong", sql, bindArgs);
            int cookie = operation.i;
            try {
                d statement = h(sql);
                operation.j = statement.f;
                try {
                    W(statement);
                    k(statement, bindArgs);
                    i(statement);
                    j(cancellationSignal);
                    long nativeExecuteForLong = nativeExecuteForLong(this.n, statement.r());
                    n(cancellationSignal);
                    H(statement);
                    this.k.c(cookie);
                    return nativeExecuteForLong;
                } catch (Throwable th) {
                    H(statement);
                    throw th;
                }
            } catch (RuntimeException ex) {
                try {
                    if (((ex instanceof SQLiteDatabaseLockedException) || (ex instanceof SQLiteTableLockedException)) && (cVar = this.d) != null) {
                        cVar.v(sql);
                    }
                    this.k.f(cookie, ex);
                    throw ex;
                } catch (Throwable th2) {
                    this.k.c(cookie);
                    throw th2;
                }
            }
        } else {
            throw new IllegalArgumentException("sql must not be null.");
        }
    }

    public String v(String sql, Object[] bindArgs, com.tencent.wcdb.support.a cancellationSignal) {
        c cVar;
        if (sql != null) {
            b operation = this.k.a("executeForString", sql, bindArgs);
            int cookie = operation.i;
            try {
                d statement = h(sql);
                operation.j = statement.f;
                try {
                    W(statement);
                    k(statement, bindArgs);
                    i(statement);
                    j(cancellationSignal);
                    String nativeExecuteForString = nativeExecuteForString(this.n, statement.r());
                    n(cancellationSignal);
                    H(statement);
                    this.k.c(cookie);
                    return nativeExecuteForString;
                } catch (Throwable th) {
                    H(statement);
                    throw th;
                }
            } catch (RuntimeException ex) {
                try {
                    if (((ex instanceof SQLiteDatabaseLockedException) || (ex instanceof SQLiteTableLockedException)) && (cVar = this.d) != null) {
                        cVar.v(sql);
                    }
                    this.k.f(cookie, ex);
                    throw ex;
                } catch (Throwable th2) {
                    this.k.c(cookie);
                    throw th2;
                }
            }
        } else {
            throw new IllegalArgumentException("sql must not be null.");
        }
    }

    public int r(String sql, Object[] bindArgs, com.tencent.wcdb.support.a cancellationSignal) {
        c cVar;
        if (sql != null) {
            int changedRows = 0;
            b operation = this.k.a("executeForChangedRowCount", sql, bindArgs);
            int cookie = operation.i;
            try {
                d statement = h(sql);
                operation.j = statement.f;
                try {
                    W(statement);
                    k(statement, bindArgs);
                    i(statement);
                    j(cancellationSignal);
                    changedRows = nativeExecuteForChangedRowCount(this.n, statement.r());
                    n(cancellationSignal);
                    H(statement);
                    if (this.k.d(cookie)) {
                        c cVar2 = this.k;
                        cVar2.h(cookie, "changedRows=" + changedRows);
                    }
                    return changedRows;
                } catch (Throwable th) {
                    H(statement);
                    throw th;
                }
            } catch (RuntimeException ex) {
                try {
                    if (((ex instanceof SQLiteDatabaseLockedException) || (ex instanceof SQLiteTableLockedException)) && (cVar = this.d) != null) {
                        cVar.v(sql);
                    }
                    this.k.f(cookie, ex);
                    throw ex;
                } catch (Throwable th2) {
                    if (this.k.d(cookie)) {
                        c cVar3 = this.k;
                        cVar3.h(cookie, "changedRows=" + changedRows);
                    }
                    throw th2;
                }
            }
        } else {
            throw new IllegalArgumentException("sql must not be null.");
        }
    }

    public long t(String sql, Object[] bindArgs, com.tencent.wcdb.support.a cancellationSignal) {
        c cVar;
        if (sql != null) {
            b operation = this.k.a("executeForLastInsertedRowId", sql, bindArgs);
            int cookie = operation.i;
            try {
                d statement = h(sql);
                operation.j = statement.f;
                try {
                    W(statement);
                    k(statement, bindArgs);
                    i(statement);
                    j(cancellationSignal);
                    long nativeExecuteForLastInsertedRowId = nativeExecuteForLastInsertedRowId(this.n, statement.r());
                    n(cancellationSignal);
                    H(statement);
                    this.k.c(cookie);
                    return nativeExecuteForLastInsertedRowId;
                } catch (Throwable th) {
                    H(statement);
                    throw th;
                }
            } catch (RuntimeException ex) {
                try {
                    if (((ex instanceof SQLiteDatabaseLockedException) || (ex instanceof SQLiteTableLockedException)) && (cVar = this.d) != null) {
                        cVar.v(sql);
                    }
                    this.k.f(cookie, ex);
                    throw ex;
                } catch (Throwable th2) {
                    this.k.c(cookie);
                    throw th2;
                }
            }
        } else {
            throw new IllegalArgumentException("sql must not be null.");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x01dc A[Catch:{ all -> 0x020a }] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0196 A[SYNTHETIC, Splitter:B:78:0x0196] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x01af  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x01b6 A[Catch:{ all -> 0x01c0 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int s(java.lang.String r30, java.lang.Object[] r31, com.tencent.wcdb.CursorWindow r32, int r33, int r34, boolean r35, com.tencent.wcdb.support.a r36) {
        /*
            r29 = this;
            r1 = r29
            r2 = r30
            r3 = r31
            r4 = r32
            r14 = r33
            r15 = r36
            java.lang.String r13 = ", countedRows="
            java.lang.String r12 = ", filledRows="
            java.lang.String r11 = ", actualPos="
            java.lang.String r9 = "', startPos="
            java.lang.String r10 = "window='"
            if (r2 == 0) goto L_0x021c
            if (r4 == 0) goto L_0x0212
            r32.a()
            r16 = -1
            r17 = -1
            r18 = -1
            com.tencent.wcdb.database.SQLiteConnection$c r0 = r1.k     // Catch:{ all -> 0x020c }
            java.lang.String r5 = "executeForCursorWindow"
            com.tencent.wcdb.database.SQLiteConnection$b r0 = r0.a(r5, r2, r3)     // Catch:{ all -> 0x020c }
            r7 = r0
            int r0 = r7.i     // Catch:{ all -> 0x020c }
            r8 = r0
            com.tencent.wcdb.database.SQLiteConnection$d r0 = r29.h(r30)     // Catch:{ RuntimeException -> 0x0185, all -> 0x016d }
            r5 = r0
            int r0 = r5.f     // Catch:{ RuntimeException -> 0x0185, all -> 0x016d }
            r7.j = r0     // Catch:{ RuntimeException -> 0x0185, all -> 0x016d }
            r1.W(r5)     // Catch:{ all -> 0x0159 }
            r1.k(r5, r3)     // Catch:{ all -> 0x0159 }
            r1.i(r5)     // Catch:{ all -> 0x0159 }
            r1.j(r15)     // Catch:{ all -> 0x0159 }
            r19 = r7
            long r6 = r1.n     // Catch:{ all -> 0x0147 }
            long r20 = r5.r()     // Catch:{ all -> 0x0147 }
            r22 = r9
            r23 = r10
            long r9 = r4.f     // Catch:{ all -> 0x013d }
            r3 = r5
            r5 = r6
            r2 = r8
            r7 = r20
            r24 = r22
            r14 = r23
            r25 = r11
            r11 = r33
            r26 = r12
            r12 = r34
            r27 = r13
            r13 = r35
            long r5 = nativeExecuteForCursorWindow(r5, r7, r9, r11, r12, r13)     // Catch:{ all -> 0x0130 }
            r0 = 32
            long r7 = r5 >> r0
            int r7 = (int) r7
            int r8 = (int) r5
            int r0 = r32.s()     // Catch:{ all -> 0x011f }
            r9 = r0
            r4.z(r7)     // Catch:{ all -> 0x010c }
            r1.n(r15)     // Catch:{ all -> 0x00f8 }
            r1.H(r3)     // Catch:{ RuntimeException -> 0x00e4, all -> 0x00d1 }
            com.tencent.wcdb.database.SQLiteConnection$c r0 = r1.k     // Catch:{ all -> 0x00ca }
            boolean r0 = r0.d(r2)     // Catch:{ all -> 0x00ca }
            if (r0 == 0) goto L_0x00c4
            com.tencent.wcdb.database.SQLiteConnection$c r0 = r1.k     // Catch:{ all -> 0x00ca }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ca }
            r10.<init>()     // Catch:{ all -> 0x00ca }
            r10.append(r14)     // Catch:{ all -> 0x00ca }
            r10.append(r4)     // Catch:{ all -> 0x00ca }
            r11 = r24
            r10.append(r11)     // Catch:{ all -> 0x00ca }
            r12 = r33
            r10.append(r12)     // Catch:{ all -> 0x00c2 }
            r13 = r25
            r10.append(r13)     // Catch:{ all -> 0x00c2 }
            r10.append(r7)     // Catch:{ all -> 0x00c2 }
            r14 = r26
            r10.append(r14)     // Catch:{ all -> 0x00c2 }
            r10.append(r9)     // Catch:{ all -> 0x00c2 }
            r11 = r27
            r10.append(r11)     // Catch:{ all -> 0x00c2 }
            r10.append(r8)     // Catch:{ all -> 0x00c2 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x00c2 }
            r0.h(r2, r10)     // Catch:{ all -> 0x00c2 }
            goto L_0x00c6
        L_0x00c2:
            r0 = move-exception
            goto L_0x00cd
        L_0x00c4:
            r12 = r33
        L_0x00c6:
            r32.g()
            return r8
        L_0x00ca:
            r0 = move-exception
            r12 = r33
        L_0x00cd:
            r2 = r30
            goto L_0x020e
        L_0x00d1:
            r0 = move-exception
            r12 = r33
            r5 = r14
            r11 = r24
            r13 = r25
            r14 = r26
            r6 = r27
            r3 = r0
            r0 = r7
            r7 = r2
            r2 = r30
            goto L_0x01d4
        L_0x00e4:
            r0 = move-exception
            r12 = r33
            r5 = r14
            r11 = r24
            r13 = r25
            r14 = r26
            r6 = r27
            r16 = r7
            r17 = r8
            r18 = r9
            goto L_0x0192
        L_0x00f8:
            r0 = move-exception
            r12 = r33
            r5 = r14
            r11 = r24
            r13 = r25
            r14 = r26
            r6 = r27
            r16 = r7
            r17 = r8
            r18 = r9
            goto L_0x0167
        L_0x010c:
            r0 = move-exception
            r12 = r33
            r5 = r14
            r11 = r24
            r13 = r25
            r14 = r26
            r6 = r27
            r16 = r7
            r17 = r8
            r18 = r9
            goto L_0x0153
        L_0x011f:
            r0 = move-exception
            r12 = r33
            r5 = r14
            r11 = r24
            r13 = r25
            r14 = r26
            r6 = r27
            r16 = r7
            r17 = r8
            goto L_0x0153
        L_0x0130:
            r0 = move-exception
            r12 = r33
            r5 = r14
            r11 = r24
            r13 = r25
            r14 = r26
            r6 = r27
            goto L_0x0153
        L_0x013d:
            r0 = move-exception
            r3 = r5
            r2 = r8
            r6 = r13
            r5 = r23
            r13 = r11
            r11 = r22
            goto L_0x014e
        L_0x0147:
            r0 = move-exception
            r3 = r5
            r2 = r8
            r5 = r10
            r6 = r13
            r13 = r11
            r11 = r9
        L_0x014e:
            r28 = r14
            r14 = r12
            r12 = r28
        L_0x0153:
            r1.n(r15)     // Catch:{ all -> 0x0157 }
            throw r0     // Catch:{ all -> 0x0157 }
        L_0x0157:
            r0 = move-exception
            goto L_0x0167
        L_0x0159:
            r0 = move-exception
            r3 = r5
            r19 = r7
            r2 = r8
            r5 = r10
            r6 = r13
            r13 = r11
            r11 = r9
            r28 = r14
            r14 = r12
            r12 = r28
        L_0x0167:
            r1.H(r3)     // Catch:{ RuntimeException -> 0x016b }
            throw r0     // Catch:{ RuntimeException -> 0x016b }
        L_0x016b:
            r0 = move-exception
            goto L_0x0192
        L_0x016d:
            r0 = move-exception
            r19 = r7
            r5 = r10
            r6 = r13
            r13 = r11
            r11 = r9
            r28 = r14
            r14 = r12
            r12 = r28
            r2 = r30
            r3 = r0
            r7 = r8
            r0 = r16
            r8 = r17
            r9 = r18
            goto L_0x01d4
        L_0x0185:
            r0 = move-exception
            r19 = r7
            r2 = r8
            r5 = r10
            r6 = r13
            r13 = r11
            r11 = r9
            r28 = r14
            r14 = r12
            r12 = r28
        L_0x0192:
            boolean r3 = r0 instanceof com.tencent.wcdb.database.SQLiteDatabaseLockedException     // Catch:{ all -> 0x01c9 }
            if (r3 != 0) goto L_0x01ab
            boolean r3 = r0 instanceof com.tencent.wcdb.database.SQLiteTableLockedException     // Catch:{ all -> 0x019f }
            if (r3 == 0) goto L_0x019b
            goto L_0x01ab
        L_0x019b:
            r7 = r2
            r2 = r30
            goto L_0x01b9
        L_0x019f:
            r0 = move-exception
            r3 = r0
            r7 = r2
            r0 = r16
            r8 = r17
            r9 = r18
            r2 = r30
            goto L_0x01d4
        L_0x01ab:
            com.tencent.wcdb.database.c r3 = r1.d     // Catch:{ all -> 0x01c9 }
            if (r3 == 0) goto L_0x01b6
            r7 = r2
            r2 = r30
            r3.v(r2)     // Catch:{ all -> 0x01c0 }
            goto L_0x01b9
        L_0x01b6:
            r7 = r2
            r2 = r30
        L_0x01b9:
            com.tencent.wcdb.database.SQLiteConnection$c r3 = r1.k     // Catch:{ all -> 0x01c0 }
            r3.f(r7, r0)     // Catch:{ all -> 0x01c0 }
            throw r0     // Catch:{ all -> 0x01c0 }
        L_0x01c0:
            r0 = move-exception
            r3 = r0
            r0 = r16
            r8 = r17
            r9 = r18
            goto L_0x01d4
        L_0x01c9:
            r0 = move-exception
            r7 = r2
            r2 = r30
            r3 = r0
            r0 = r16
            r8 = r17
            r9 = r18
        L_0x01d4:
            com.tencent.wcdb.database.SQLiteConnection$c r10 = r1.k     // Catch:{ all -> 0x020a }
            boolean r10 = r10.d(r7)     // Catch:{ all -> 0x020a }
            if (r10 == 0) goto L_0x0208
            com.tencent.wcdb.database.SQLiteConnection$c r10 = r1.k     // Catch:{ all -> 0x020a }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x020a }
            r1.<init>()     // Catch:{ all -> 0x020a }
            r1.append(r5)     // Catch:{ all -> 0x020a }
            r1.append(r4)     // Catch:{ all -> 0x020a }
            r1.append(r11)     // Catch:{ all -> 0x020a }
            r1.append(r12)     // Catch:{ all -> 0x020a }
            r1.append(r13)     // Catch:{ all -> 0x020a }
            r1.append(r0)     // Catch:{ all -> 0x020a }
            r1.append(r14)     // Catch:{ all -> 0x020a }
            r1.append(r9)     // Catch:{ all -> 0x020a }
            r1.append(r6)     // Catch:{ all -> 0x020a }
            r1.append(r8)     // Catch:{ all -> 0x020a }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x020a }
            r10.h(r7, r1)     // Catch:{ all -> 0x020a }
        L_0x0208:
            throw r3     // Catch:{ all -> 0x020a }
        L_0x020a:
            r0 = move-exception
            goto L_0x020e
        L_0x020c:
            r0 = move-exception
            r12 = r14
        L_0x020e:
            r32.g()
            throw r0
        L_0x0212:
            r12 = r14
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "window must not be null."
            r0.<init>(r1)
            throw r0
        L_0x021c:
            r12 = r14
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "sql must not be null."
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wcdb.database.SQLiteConnection.s(java.lang.String, java.lang.Object[], com.tencent.wcdb.CursorWindow, int, int, boolean, com.tencent.wcdb.support.a):int");
    }

    /* access modifiers changed from: package-private */
    public d h(String sql) {
        d statement = (d) this.i.d(sql);
        boolean skipCache = false;
        if (statement != null) {
            if (!statement.i) {
                boolean unused = statement.i = true;
                return statement;
            }
            skipCache = true;
        }
        long statementPtr = nativePrepareStatement(this.n, sql);
        try {
            int numParameters = nativeGetParameterCount(this.n, statementPtr);
            int type = g.e(sql);
            d statement2 = B(sql, statementPtr, numParameters, type, nativeIsReadOnly(this.n, statementPtr));
            if (!skipCache && y(type)) {
                this.i.e(sql, statement2);
                boolean unused2 = statement2.h = true;
            }
            boolean unused3 = statement2.i = true;
            return statement2;
        } catch (RuntimeException ex) {
            if (statement == null || !statement.h) {
                nativeFinalizeStatement(this.n, statementPtr);
            }
            throw ex;
        }
    }

    /* access modifiers changed from: package-private */
    public void H(d statement) {
        boolean unused = statement.i = false;
        if (statement.h) {
            try {
                I(statement, true);
            } catch (SQLiteException e2) {
                this.i.f(statement.c);
            }
        } else {
            w(statement);
        }
    }

    /* access modifiers changed from: private */
    public void w(d statement) {
        nativeFinalizeStatement(this.n, statement.r());
        G(statement);
    }

    private void j(com.tencent.wcdb.support.a cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.b();
            int i2 = this.p + 1;
            this.p = i2;
            if (i2 == 1) {
                nativeResetCancel(this.n, true);
                cancellationSignal.setOnCancelListener(this);
            }
        }
    }

    /* access modifiers changed from: private */
    public void n(com.tencent.wcdb.support.a cancellationSignal) {
        if (cancellationSignal != null) {
            int i2 = this.p - 1;
            this.p = i2;
            if (i2 == 0) {
                cancellationSignal.setOnCancelListener((a.C0224a) null);
                nativeResetCancel(this.n, false);
            }
        }
    }

    public void onCancel() {
        nativeCancel(this.n);
    }

    private void k(d statement, Object[] bindArgs) {
        long j2;
        int count = bindArgs != null ? bindArgs.length : 0;
        if (count != statement.e) {
            throw new SQLiteBindOrColumnIndexOutOfRangeException("Expected " + statement.e + " bind arguments but " + count + " were provided.");
        } else if (count != 0) {
            long statementPtr = statement.r();
            for (int i2 = 0; i2 < count; i2++) {
                Boolean bool = bindArgs[i2];
                switch (g.f(bool)) {
                    case 0:
                        nativeBindNull(this.n, statementPtr, i2 + 1);
                        break;
                    case 1:
                        nativeBindLong(this.n, statementPtr, i2 + 1, bool.longValue());
                        break;
                    case 2:
                        nativeBindDouble(this.n, statementPtr, i2 + 1, bool.doubleValue());
                        break;
                    case 4:
                        nativeBindBlob(this.n, statementPtr, i2 + 1, bool);
                        break;
                    default:
                        if (!(bool instanceof Boolean)) {
                            nativeBindString(this.n, statementPtr, i2 + 1, bool.toString());
                            break;
                        } else {
                            long j3 = this.n;
                            int i3 = i2 + 1;
                            if (bool.booleanValue()) {
                                j2 = 1;
                            } else {
                                j2 = 0;
                            }
                            nativeBindLong(j3, statementPtr, i3, j2);
                            break;
                        }
                }
            }
        }
    }

    private void I(d statement, boolean clearBindings) {
        nativeResetStatement(this.n, statement.r(), clearBindings);
    }

    private void W(d statement) {
        if (this.o && !statement.g) {
            throw new SQLiteException("Cannot execute this statement because it might modify the database but the connection is read-only.");
        }
    }

    private static boolean y(int statementType) {
        if (statementType == 2 || statementType == 1) {
            return true;
        }
        return false;
    }

    private void i(d statement) {
    }

    /* access modifiers changed from: package-private */
    public String m() {
        return this.k.b();
    }

    public String toString() {
        return "SQLiteConnection: " + this.e.a + " (" + this.f + ")";
    }

    private d B(String sql, long statementPtr, int numParameters, int type, boolean readOnly) {
        d statement = this.j;
        if (statement != null) {
            this.j = statement.b;
            d unused = statement.b = null;
            boolean unused2 = statement.h = false;
        } else {
            statement = new d(this);
        }
        String unused3 = statement.c = sql;
        long unused4 = statement.d = statementPtr;
        int unused5 = statement.e = numParameters;
        int unused6 = statement.f = type;
        boolean unused7 = statement.g = readOnly;
        return statement;
    }

    private void G(d statement) {
        String unused = statement.c = null;
        d unused2 = statement.b = this.j;
        this.j = statement;
    }

    /* access modifiers changed from: private */
    public static String X(String sql) {
        return c.matcher(sql).replaceAll(" ");
    }

    public static final class d {
        private WeakReference<SQLiteConnection> a;
        /* access modifiers changed from: private */
        public d b;
        /* access modifiers changed from: private */
        public String c;
        /* access modifiers changed from: private */
        public long d;
        /* access modifiers changed from: private */
        public int e;
        /* access modifiers changed from: private */
        public int f;
        /* access modifiers changed from: private */
        public boolean g;
        /* access modifiers changed from: private */
        public boolean h;
        /* access modifiers changed from: private */
        public boolean i;
        private b j;

        d(SQLiteConnection connection) {
            this.a = new WeakReference<>(connection);
        }

        public long r() {
            return this.d;
        }

        public void p(com.tencent.wcdb.support.a cancellationSignal) {
            SQLiteConnection connection = (SQLiteConnection) this.a.get();
            if (connection != null) {
                connection.n(cancellationSignal);
            }
        }

        public void q(String detail) {
            SQLiteConnection connection;
            if (this.j != null && (connection = (SQLiteConnection) this.a.get()) != null) {
                if (connection.k.d(this.j.i)) {
                    connection.k.h(this.j.i, detail);
                }
                this.j = null;
            }
        }
    }

    public final class e extends com.tencent.wcdb.support.b<String, d> {
        public e(int size) {
            super(size);
        }

        /* access modifiers changed from: protected */
        /* renamed from: k */
        public void b(boolean evicted, String key, d oldValue, d newValue) {
            boolean unused = oldValue.h = false;
            if (!oldValue.i) {
                SQLiteConnection.this.w(oldValue);
            }
        }
    }

    public final class c {
        private final b[] a;
        private int b;
        private int c;

        private c() {
            this.a = new b[20];
        }

        public b a(String kind, String sql, Object[] bindArgs) {
            b operation;
            synchronized (this.a) {
                int index = (this.b + 1) % 20;
                operation = this.a[index];
                if (operation == null) {
                    operation = new b();
                    this.a[index] = operation;
                } else {
                    operation.g = false;
                    operation.h = null;
                    ArrayList<Object> arrayList = operation.f;
                    if (arrayList != null) {
                        arrayList.clear();
                    }
                }
                operation.b = System.currentTimeMillis();
                operation.d = kind;
                operation.e = sql;
                if (bindArgs != null) {
                    ArrayList<Object> arrayList2 = operation.f;
                    if (arrayList2 == null) {
                        operation.f = new ArrayList<>();
                    } else {
                        arrayList2.clear();
                    }
                    for (Object arg : bindArgs) {
                        if (arg == null || !(arg instanceof byte[])) {
                            operation.f.add(arg);
                        } else {
                            operation.f.add(SQLiteConnection.b);
                        }
                    }
                }
                operation.i = j(index);
                operation.k = SQLiteConnection.this.m;
                this.b = index;
            }
            return operation;
        }

        public void f(int cookie, Exception ex) {
            synchronized (this.a) {
                b operation = g(cookie);
                if (operation != null) {
                    operation.h = ex;
                }
            }
        }

        public void c(int cookie) {
            String sql;
            String kind;
            int type;
            long elapsedTimeMillis;
            synchronized (this.a) {
                b operation = g(cookie);
                if (e(operation)) {
                    i(operation, (String) null);
                }
                sql = operation.e;
                kind = operation.d;
                type = operation.j;
                elapsedTimeMillis = operation.c - operation.b;
            }
            if (!"prepare".equals(kind)) {
                SQLiteConnection.this.d.d1(sql, type, elapsedTimeMillis);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0023, code lost:
            if ("prepare".equals(r4) != false) goto L_0x002e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0025, code lost:
            com.tencent.wcdb.database.SQLiteConnection.g(r10.d).d1(r3, r5, r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x002e, code lost:
            return r2;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean d(int r11) {
            /*
                r10 = this;
                com.tencent.wcdb.database.SQLiteConnection$b[] r0 = r10.a
                monitor-enter(r0)
                com.tencent.wcdb.database.SQLiteConnection$b r1 = r10.g(r11)     // Catch:{ all -> 0x002f }
                if (r1 != 0) goto L_0x000c
                r2 = 0
                monitor-exit(r0)     // Catch:{ all -> 0x002f }
                return r2
            L_0x000c:
                boolean r2 = r10.e(r1)     // Catch:{ all -> 0x002f }
                java.lang.String r3 = r1.e     // Catch:{ all -> 0x002f }
                java.lang.String r4 = r1.d     // Catch:{ all -> 0x002f }
                int r5 = r1.j     // Catch:{ all -> 0x002f }
                long r6 = r1.c     // Catch:{ all -> 0x002f }
                long r8 = r1.b     // Catch:{ all -> 0x002f }
                long r6 = r6 - r8
                monitor-exit(r0)     // Catch:{ all -> 0x002f }
                java.lang.String r0 = "prepare"
                boolean r0 = r0.equals(r4)
                if (r0 != 0) goto L_0x002e
                com.tencent.wcdb.database.SQLiteConnection r0 = com.tencent.wcdb.database.SQLiteConnection.this
                com.tencent.wcdb.database.c r0 = r0.d
                r0.d1(r3, r5, r6)
            L_0x002e:
                return r2
            L_0x002f:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x002f }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.wcdb.database.SQLiteConnection.c.d(int):boolean");
        }

        public void h(int cookie, String detail) {
            synchronized (this.a) {
                b operation = g(cookie);
                if (operation != null) {
                    i(operation, detail);
                }
            }
        }

        private boolean e(b operation) {
            if (operation == null) {
                return false;
            }
            operation.c = System.currentTimeMillis();
            operation.g = true;
            Exception exc = operation.h;
            if (exc == null || exc.getMessage() == null) {
                return SQLiteDebug.c(operation.c - operation.b);
            }
            return true;
        }

        private void i(b operation, String detail) {
            StringBuilder msg = new StringBuilder();
            operation.a(msg, false);
            if (detail != null) {
                msg.append(", ");
                msg.append(detail);
            }
            Log.c("WCDB.SQLiteConnection", msg.toString());
        }

        private int j(int index) {
            int generation = this.c;
            this.c = generation + 1;
            return (generation << 8) | index;
        }

        private b g(int cookie) {
            b operation = this.a[cookie & 255];
            if (operation.i == cookie) {
                return operation;
            }
            return null;
        }

        public String b() {
            synchronized (this.a) {
                b operation = this.a[this.b];
                if (operation == null || operation.g) {
                    return null;
                }
                StringBuilder msg = new StringBuilder();
                operation.a(msg, false);
                String sb = msg.toString();
                return sb;
            }
        }
    }

    @SuppressLint({"SimpleDateFormat"})
    public static final class b {
        private static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        public long b;
        public long c;
        public String d;
        public String e;
        public ArrayList<Object> f;
        public boolean g;
        public Exception h;
        public int i;
        public int j;
        public int k;

        private b() {
        }

        public void a(StringBuilder msg, boolean verbose) {
            ArrayList<Object> arrayList;
            msg.append(this.d);
            if (this.g) {
                msg.append(" took ");
                msg.append(this.c - this.b);
                msg.append("ms");
            } else {
                msg.append(" started ");
                msg.append(System.currentTimeMillis() - this.b);
                msg.append("ms ago");
            }
            msg.append(" - ");
            msg.append(b());
            if (this.e != null) {
                msg.append(", sql=\"");
                msg.append(SQLiteConnection.X(this.e));
                msg.append("\"");
            }
            if (this.k > 0) {
                msg.append(", tid=");
                msg.append(this.k);
            }
            if (!(!verbose || (arrayList = this.f) == null || arrayList.size() == 0)) {
                msg.append(", bindArgs=[");
                int count = this.f.size();
                for (int i2 = 0; i2 < count; i2++) {
                    Object arg = this.f.get(i2);
                    if (i2 != 0) {
                        msg.append(", ");
                    }
                    if (arg == null) {
                        msg.append(BuildConfig.TRAVIS);
                    } else if (arg instanceof byte[]) {
                        msg.append("<byte[]>");
                    } else if (arg instanceof String) {
                        msg.append("\"");
                        msg.append((String) arg);
                        msg.append("\"");
                    } else {
                        msg.append(arg);
                    }
                }
                msg.append("]");
            }
            Exception exc = this.h;
            if (exc != null && exc.getMessage() != null) {
                msg.append(", exception=\"");
                msg.append(this.h.getMessage());
                msg.append("\"");
            }
        }

        private String b() {
            if (!this.g) {
                return "running";
            }
            return this.h != null ? "failed" : "succeeded";
        }
    }
}
