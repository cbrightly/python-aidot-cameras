package com.tencent.wcdb.database;

import android.os.SystemClock;
import com.didichuxing.doraemonkit.widget.JustifyTextView;
import com.tencent.wcdb.database.SQLiteConnectionPool;
import com.tencent.wcdb.g;
import com.tencent.wcdb.support.Log;
import com.tencent.wcdb.support.OperationCanceledException;
import com.tencent.wcdb.support.a;
import java.io.Closeable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/* compiled from: SQLiteConnectionPool */
public final class c implements Closeable {
    private final WeakHashMap<SQLiteConnection, b> A4 = new WeakHashMap<>();
    private int a1;
    private int a2;
    private final WeakReference<SQLiteDatabase> c;
    private volatile m d;
    private volatile a f;
    private final f p0;
    private boolean p1;
    private C0223c p2;
    private C0223c p3;
    private final ArrayList<SQLiteConnection> p4 = new ArrayList<>();
    private byte[] q;
    private SQLiteCipherSpec x;
    /* access modifiers changed from: private */
    public final Object y = new Object();
    private final AtomicBoolean z = new AtomicBoolean();
    private SQLiteConnection z4;

    /* compiled from: SQLiteConnectionPool */
    public enum b {
        NORMAL,
        RECONFIGURE,
        DISCARD
    }

    static {
        Class<c> cls = c.class;
    }

    private c(SQLiteDatabase db, f configuration, int poolSize) {
        this.c = new WeakReference<>(db);
        this.p0 = new f(configuration);
        b1(poolSize);
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            r(true);
        } finally {
            super.finalize();
        }
    }

    public static c P(SQLiteDatabase db, f configuration, byte[] password, SQLiteCipherSpec cipher, int poolSize) {
        if (configuration != null) {
            c pool = new c(db, configuration, poolSize);
            pool.q = password;
            pool.x = cipher == null ? null : new SQLiteCipherSpec(cipher);
            pool.T();
            return pool;
        }
        throw new IllegalArgumentException("configuration must not be null.");
    }

    private void T() {
        this.z4 = W(this.p0, true);
        this.p1 = true;
    }

    public void close() {
        r(false);
    }

    private void r(boolean finalized) {
        if (!finalized) {
            synchronized (this.y) {
                c1();
                this.p1 = false;
                j();
                int pendingCount = this.A4.size();
                if (pendingCount != 0) {
                    Log.c("WCDB.SQLiteConnectionPool", "The connection pool for " + this.p0.b + " has been closed but there are still " + pendingCount + " connections in use.  They will be closed as they are released back to the pool.");
                }
                h1();
            }
        }
    }

    public void o0(f configuration) {
        if (configuration != null) {
            synchronized (this.y) {
                c1();
                boolean walModeChanged = ((configuration.d ^ this.p0.d) & 536870912) != 0;
                if (walModeChanged) {
                    if (this.A4.isEmpty()) {
                        l();
                    } else {
                        throw new IllegalStateException("Write Ahead Logging (WAL) mode cannot be enabled or disabled while there are transactions in progress.  Finish all transactions and release all active database connections first.");
                    }
                }
                if (configuration.g != this.p0.g) {
                    if (!this.A4.isEmpty()) {
                        throw new IllegalStateException("Foreign Key Constraints cannot be enabled or disabled while there are transactions in progress.  Finish all transactions and release all active database connections first.");
                    }
                }
                f fVar = this.p0;
                if (((fVar.d ^ configuration.d) & 268435473) == 0) {
                    if (g.i(fVar.c, configuration.c)) {
                        this.p0.b(configuration);
                        b1(0);
                        n();
                        u0();
                        h1();
                    }
                }
                if (walModeChanged) {
                    j();
                }
                SQLiteConnection newPrimaryConnection = W(configuration, true);
                j();
                o();
                this.z4 = newPrimaryConnection;
                this.p0.b(configuration);
                b1(0);
                h1();
            }
            return;
        }
        throw new IllegalArgumentException("configuration must not be null.");
    }

    public SQLiteConnection g(String sql, int connectionFlags, com.tencent.wcdb.support.a cancellationSignal) {
        long startTime = SystemClock.uptimeMillis();
        SQLiteConnection connection = g1(sql, connectionFlags, cancellationSignal);
        if (this.d != null) {
            long waitTime = SystemClock.uptimeMillis() - startTime;
            SQLiteDatabase db = (SQLiteDatabase) this.c.get();
            if (db != null) {
                this.d.d(db, sql, waitTime, (connectionFlags & 2) != 0);
            }
        }
        return connection;
    }

    public void a1(SQLiteConnection connection) {
        synchronized (this.y) {
            b status = this.A4.remove(connection);
            if (status == null) {
                throw new IllegalStateException("Cannot perform this operation because the specified connection was not acquired from this pool or has already been released.");
            } else if (!this.p1) {
                m(connection);
            } else if (connection.A()) {
                if (F0(connection, status)) {
                    if (this.z4 == null) {
                        this.z4 = connection;
                    } else {
                        throw new AssertionError();
                    }
                }
                h1();
            } else if (this.p4.size() >= this.a1 - 1) {
                m(connection);
            } else {
                if (F0(connection, status)) {
                    this.p4.add(connection);
                }
                h1();
            }
        }
    }

    private boolean F0(SQLiteConnection connection, b status) {
        if (status == b.RECONFIGURE) {
            try {
                connection.F(this.p0);
            } catch (RuntimeException ex) {
                Log.b("WCDB.SQLiteConnectionPool", "Failed to reconfigure released connection, closing it: " + connection, ex);
                status = b.DISCARD;
            }
        }
        if (status != b.DISCARD) {
            return true;
        }
        m(connection);
        return false;
    }

    private SQLiteConnection W(f configuration, boolean primaryConnection) {
        int connectionId = this.a2;
        this.a2 = connectionId + 1;
        return SQLiteConnection.C(this, configuration, connectionId, primaryConnection, this.q, this.x);
    }

    /* access modifiers changed from: package-private */
    public void J() {
        Log.f("WCDB.SQLiteConnectionPool", "A SQLiteConnection object for database '" + this.p0.b + "' was leaked!  Please fix your application to end transactions in progress properly and to close the database when it is no longer needed.");
        this.z.set(true);
    }

    private void j() {
        l();
        SQLiteConnection sQLiteConnection = this.z4;
        if (sQLiteConnection != null) {
            m(sQLiteConnection);
            this.z4 = null;
        }
    }

    private void l() {
        int count = this.p4.size();
        for (int i = 0; i < count; i++) {
            m(this.p4.get(i));
        }
        this.p4.clear();
    }

    private void n() {
        int availableCount = this.p4.size();
        while (true) {
            int availableCount2 = availableCount - 1;
            if (availableCount > this.a1 - 1) {
                m(this.p4.remove(availableCount2));
                availableCount = availableCount2;
            } else {
                return;
            }
        }
    }

    private void m(SQLiteConnection connection) {
        try {
            connection.l();
        } catch (RuntimeException ex) {
            Log.a("WCDB.SQLiteConnectionPool", "Failed to close connection, its fate is now in the hands of the merciful GC: " + connection + ex.getMessage());
        }
    }

    private void o() {
        z(b.DISCARD);
    }

    private void u0() {
        SQLiteConnection sQLiteConnection = this.z4;
        if (sQLiteConnection != null) {
            try {
                sQLiteConnection.F(this.p0);
            } catch (RuntimeException ex) {
                Log.b("WCDB.SQLiteConnectionPool", "Failed to reconfigure available primary connection, closing it: " + this.z4, ex);
                m(this.z4);
                this.z4 = null;
            }
        }
        int count = this.p4.size();
        int i = 0;
        while (i < count) {
            SQLiteConnection connection = this.p4.get(i);
            try {
                connection.F(this.p0);
            } catch (RuntimeException ex2) {
                Log.b("WCDB.SQLiteConnectionPool", "Failed to reconfigure available non-primary connection, closing it: " + connection, ex2);
                m(connection);
                this.p4.remove(i);
                count += -1;
                i--;
            }
            i++;
        }
        z(b.RECONFIGURE);
    }

    private void z(b status) {
        if (!this.A4.isEmpty()) {
            ArrayList<SQLiteConnection> keysToUpdate = new ArrayList<>(this.A4.size());
            for (Map.Entry<SQLiteConnection, SQLiteConnectionPool.AcquiredConnectionStatus> entry : this.A4.entrySet()) {
                b oldStatus = (b) entry.getValue();
                if (!(status == oldStatus || oldStatus == b.DISCARD)) {
                    keysToUpdate.add(entry.getKey());
                }
            }
            int updateCount = keysToUpdate.size();
            for (int i = 0; i < updateCount; i++) {
                this.A4.put(keysToUpdate.get(i), status);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x006b, code lost:
        if (r11 == null) goto L_0x0075;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x006d, code lost:
        r11.setOnCancelListener(new com.tencent.wcdb.database.c.a(r9, r1, r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0075, code lost:
        r3 = meshsdk.ctrl.GroupCtrlAdapter.RETRY_TIMEOUT;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r6 = r1.c + meshsdk.ctrl.GroupCtrlAdapter.RETRY_TIMEOUT;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0082, code lost:
        if (r9.z.compareAndSet(true, r13) == false) goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r12 = r9.y;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0086, code lost:
        monitor-enter(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        h1();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x008a, code lost:
        monitor-exit(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x008f, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0090, code lost:
        r8 = r20;
        r18 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        java.util.concurrent.locks.LockSupport.parkNanos(com.amazonaws.kinesisvideo.producer.Time.NANOS_IN_A_MILLISECOND * r3);
        java.lang.Thread.interrupted();
        r12 = r9.y;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00a2, code lost:
        monitor-enter(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        c1();
        r0 = r1.h;
        r15 = r1.i;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00aa, code lost:
        if (r0 != null) goto L_0x00da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00ac, code lost:
        if (r15 == null) goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00ae, code lost:
        r8 = r20;
        r18 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00b3, code lost:
        r16 = android.os.SystemClock.uptimeMillis();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00b9, code lost:
        if (r16 >= r6) goto L_0x00c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00bb, code lost:
        r3 = r16 - r6;
        r8 = r20;
        r18 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00c2, code lost:
        r18 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:?, code lost:
        x(r20, r16 - r1.c, r10);
        r3 = meshsdk.ctrl.GroupCtrlAdapter.RETRY_TIMEOUT;
        r6 = r16 + meshsdk.ctrl.GroupCtrlAdapter.RETRY_TIMEOUT;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x00d1, code lost:
        monitor-exit(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x00d2, code lost:
        r14 = r18;
        r13 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x00d6, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x00d7, code lost:
        r8 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x00da, code lost:
        r8 = r20;
        r18 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x00de, code lost:
        P0(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x00e1, code lost:
        if (r0 == null) goto L_0x00eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x00e3, code lost:
        monitor-exit(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x00e4, code lost:
        if (r11 == null) goto L_0x00e9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x00e6, code lost:
        r11.setOnCancelListener((com.tencent.wcdb.support.a.C0224a) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x00e9, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:?, code lost:
        throw r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x00ec, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x00ed, code lost:
        r8 = r20;
        r18 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x00f1, code lost:
        monitor-exit(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:?, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x00f3, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x00f5, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x00f7, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x00f8, code lost:
        r8 = r20;
        r18 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x00fc, code lost:
        if (r11 != null) goto L_0x00fe;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x00fe, code lost:
        r11.setOnCancelListener((com.tencent.wcdb.support.a.C0224a) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0101, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.tencent.wcdb.database.SQLiteConnection g1(java.lang.String r20, int r21, com.tencent.wcdb.support.a r22) {
        /*
            r19 = this;
            r9 = r19
            r10 = r21
            r11 = r22
            r0 = r10 & 2
            r13 = 0
            if (r0 == 0) goto L_0x000d
            r0 = 1
            goto L_0x000e
        L_0x000d:
            r0 = r13
        L_0x000e:
            r14 = r0
            java.lang.Object r15 = r9.y
            monitor-enter(r15)
            r19.c1()     // Catch:{ all -> 0x0102 }
            if (r11 == 0) goto L_0x0022
            r22.b()     // Catch:{ all -> 0x001b }
            goto L_0x0022
        L_0x001b:
            r0 = move-exception
            r8 = r20
            r18 = r14
            goto L_0x0107
        L_0x0022:
            r0 = 0
            if (r14 != 0) goto L_0x002a
            com.tencent.wcdb.database.SQLiteConnection r1 = r19.e1(r20, r21)     // Catch:{ all -> 0x001b }
            r0 = r1
        L_0x002a:
            if (r0 != 0) goto L_0x0031
            com.tencent.wcdb.database.SQLiteConnection r1 = r9.f1(r10)     // Catch:{ all -> 0x001b }
            r0 = r1
        L_0x0031:
            if (r0 == 0) goto L_0x0035
            monitor-exit(r15)     // Catch:{ all -> 0x001b }
            return r0
        L_0x0035:
            int r1 = t(r21)     // Catch:{ all -> 0x0102 }
            r8 = r1
            long r3 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x0102 }
            java.lang.Thread r2 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0102 }
            r1 = r19
            r5 = r8
            r6 = r14
            r7 = r20
            r12 = r8
            r8 = r21
            com.tencent.wcdb.database.c$c r1 = r1.I(r2, r3, r5, r6, r7, r8)     // Catch:{ all -> 0x0102 }
            r2 = 0
            com.tencent.wcdb.database.c$c r5 = r9.p3     // Catch:{ all -> 0x0102 }
        L_0x0052:
            if (r5 == 0) goto L_0x0060
            int r6 = r5.d     // Catch:{ all -> 0x001b }
            if (r12 <= r6) goto L_0x005b
            r1.a = r5     // Catch:{ all -> 0x001b }
            goto L_0x0060
        L_0x005b:
            r2 = r5
            com.tencent.wcdb.database.c$c r6 = r5.a     // Catch:{ all -> 0x001b }
            r5 = r6
            goto L_0x0052
        L_0x0060:
            if (r2 == 0) goto L_0x0065
            r2.a = r1     // Catch:{ all -> 0x001b }
            goto L_0x0067
        L_0x0065:
            r9.p3 = r1     // Catch:{ all -> 0x0102 }
        L_0x0067:
            int r6 = r1.j     // Catch:{ all -> 0x0102 }
            r2 = r6
            monitor-exit(r15)     // Catch:{ all -> 0x0102 }
            if (r11 == 0) goto L_0x0075
            com.tencent.wcdb.database.c$a r0 = new com.tencent.wcdb.database.c$a
            r0.<init>(r1, r2)
            r11.setOnCancelListener(r0)
        L_0x0075:
            r3 = 3000(0xbb8, double:1.482E-320)
            r5 = 0
            long r6 = r1.c     // Catch:{ all -> 0x00f7 }
            long r6 = r6 + r3
        L_0x007b:
            java.util.concurrent.atomic.AtomicBoolean r0 = r9.z     // Catch:{ all -> 0x00f7 }
            r8 = 1
            boolean r0 = r0.compareAndSet(r8, r13)     // Catch:{ all -> 0x00f7 }
            if (r0 == 0) goto L_0x0096
            java.lang.Object r12 = r9.y     // Catch:{ all -> 0x008f }
            monitor-enter(r12)     // Catch:{ all -> 0x008f }
            r19.h1()     // Catch:{ all -> 0x008c }
            monitor-exit(r12)     // Catch:{ all -> 0x008c }
            goto L_0x0096
        L_0x008c:
            r0 = move-exception
            monitor-exit(r12)     // Catch:{ all -> 0x008c }
            throw r0     // Catch:{ all -> 0x008f }
        L_0x008f:
            r0 = move-exception
            r8 = r20
            r18 = r14
            goto L_0x00fc
        L_0x0096:
            r15 = 1000000(0xf4240, double:4.940656E-318)
            long r15 = r15 * r3
            java.util.concurrent.locks.LockSupport.parkNanos(r15)     // Catch:{ all -> 0x00f7 }
            java.lang.Thread.interrupted()     // Catch:{ all -> 0x00f7 }
            java.lang.Object r12 = r9.y     // Catch:{ all -> 0x00f7 }
            monitor-enter(r12)     // Catch:{ all -> 0x00f7 }
            r19.c1()     // Catch:{ all -> 0x00ec }
            com.tencent.wcdb.database.SQLiteConnection r0 = r1.h     // Catch:{ all -> 0x00ec }
            java.lang.RuntimeException r15 = r1.i     // Catch:{ all -> 0x00ec }
            if (r0 != 0) goto L_0x00da
            if (r15 == 0) goto L_0x00b3
            r8 = r20
            r18 = r14
            goto L_0x00de
        L_0x00b3:
            long r16 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x00ec }
            int r18 = (r16 > r6 ? 1 : (r16 == r6 ? 0 : -1))
            if (r18 >= 0) goto L_0x00c2
            long r3 = r16 - r6
            r8 = r20
            r18 = r14
            goto L_0x00d1
        L_0x00c2:
            r18 = r14
            long r13 = r1.c     // Catch:{ all -> 0x00d6 }
            long r13 = r16 - r13
            r8 = r20
            r9.x(r8, r13, r10)     // Catch:{ all -> 0x00f5 }
            r3 = 3000(0xbb8, double:1.482E-320)
            long r6 = r16 + r3
        L_0x00d1:
            monitor-exit(r12)     // Catch:{ all -> 0x00f5 }
            r14 = r18
            r13 = 0
            goto L_0x007b
        L_0x00d6:
            r0 = move-exception
            r8 = r20
            goto L_0x00f1
        L_0x00da:
            r8 = r20
            r18 = r14
        L_0x00de:
            r9.P0(r1)     // Catch:{ all -> 0x00f5 }
            if (r0 == 0) goto L_0x00ea
            monitor-exit(r12)     // Catch:{ all -> 0x00f5 }
            if (r11 == 0) goto L_0x00e9
            r11.setOnCancelListener(r5)
        L_0x00e9:
            return r0
        L_0x00ea:
            throw r15     // Catch:{ all -> 0x00f5 }
        L_0x00ec:
            r0 = move-exception
            r8 = r20
            r18 = r14
        L_0x00f1:
            monitor-exit(r12)     // Catch:{ all -> 0x00f5 }
            throw r0     // Catch:{ all -> 0x00f3 }
        L_0x00f3:
            r0 = move-exception
            goto L_0x00fc
        L_0x00f5:
            r0 = move-exception
            goto L_0x00f1
        L_0x00f7:
            r0 = move-exception
            r8 = r20
            r18 = r14
        L_0x00fc:
            if (r11 == 0) goto L_0x0101
            r11.setOnCancelListener(r5)
        L_0x0101:
            throw r0
        L_0x0102:
            r0 = move-exception
            r8 = r20
            r18 = r14
        L_0x0107:
            monitor-exit(r15)     // Catch:{ all -> 0x0109 }
            throw r0
        L_0x0109:
            r0 = move-exception
            goto L_0x0107
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wcdb.database.c.g1(java.lang.String, int, com.tencent.wcdb.support.a):com.tencent.wcdb.database.SQLiteConnection");
    }

    /* compiled from: SQLiteConnectionPool */
    public class a implements a.C0224a {
        final /* synthetic */ C0223c a;
        final /* synthetic */ int b;

        a(C0223c cVar, int i) {
            this.a = cVar;
            this.b = i;
        }

        public void onCancel() {
            synchronized (c.this.y) {
                C0223c cVar = this.a;
                if (cVar.j == this.b) {
                    c.this.i(cVar);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void i(C0223c waiter) {
        if (waiter.h == null && waiter.i == null) {
            C0223c predecessor = null;
            C0223c current = this.p3;
            while (current != waiter) {
                if (current != null) {
                    predecessor = current;
                    current = current.a;
                } else {
                    throw new AssertionError();
                }
            }
            if (predecessor != null) {
                predecessor.a = waiter.a;
            } else {
                this.p3 = waiter.a;
            }
            waiter.i = new OperationCanceledException();
            LockSupport.unpark(waiter.b);
            h1();
        }
    }

    public void v(String sql) {
        synchronized (this.y) {
            x(sql, 0, 0);
        }
    }

    private void x(String sql, long waitMillis, int connectionFlags) {
        StringBuilder msg = new StringBuilder();
        if (waitMillis != 0) {
            Thread thread = Thread.currentThread();
            msg.append("The connection pool for database '");
            msg.append(this.p0.b);
            msg.append("' has been unable to grant a connection to thread ");
            msg.append(thread.getId());
            msg.append(" (");
            msg.append(thread.getName());
            msg.append(") ");
            msg.append("with flags 0x");
            msg.append(Integer.toHexString(connectionFlags));
            msg.append(" for ");
            msg.append(((float) waitMillis) * 0.001f);
            msg.append(" seconds.\n");
        }
        ArrayList<String> requests = new ArrayList<>();
        int activeConnections = 0;
        int idleConnections = 0;
        if (!this.A4.isEmpty()) {
            for (SQLiteConnection connection : this.A4.keySet()) {
                String description = connection.m();
                if (description != null) {
                    requests.add(description);
                    activeConnections++;
                } else {
                    idleConnections++;
                }
            }
        }
        int availableConnections = this.p4.size();
        if (this.z4 != null) {
            availableConnections++;
        }
        msg.append("Connections: ");
        msg.append(activeConnections);
        msg.append(" active, ");
        msg.append(idleConnections);
        msg.append(" idle, ");
        msg.append(availableConnections);
        msg.append(" available.\n");
        if (!requests.isEmpty()) {
            msg.append("\nRequests in progress:\n");
            Iterator<String> it = requests.iterator();
            while (it.hasNext()) {
                msg.append(JustifyTextView.TWO_CHINESE_BLANK);
                msg.append(it.next());
                msg.append("\n");
            }
        }
        String message = msg.toString();
        Log.f("WCDB.SQLiteConnectionPool", message);
        SQLiteDatabase db = (SQLiteDatabase) this.c.get();
        if (db != null && this.d != null) {
            this.d.c(db, sql, requests, message);
        }
    }

    private void h1() {
        C0223c predecessor = null;
        C0223c waiter = this.p3;
        boolean primaryConnectionNotAvailable = false;
        boolean nonPrimaryConnectionNotAvailable = false;
        while (waiter != null) {
            boolean unpark = false;
            if (!this.p1) {
                unpark = true;
            } else {
                SQLiteConnection connection = null;
                try {
                    if (!waiter.e && !nonPrimaryConnectionNotAvailable && (connection = e1(waiter.f, waiter.g)) == null) {
                        nonPrimaryConnectionNotAvailable = true;
                    }
                    if (connection == null && !primaryConnectionNotAvailable && (connection = f1(waiter.g)) == null) {
                        primaryConnectionNotAvailable = true;
                    }
                    if (connection != null) {
                        waiter.h = connection;
                        unpark = true;
                    } else if (nonPrimaryConnectionNotAvailable && primaryConnectionNotAvailable) {
                        return;
                    }
                } catch (RuntimeException ex) {
                    waiter.i = ex;
                    unpark = true;
                }
            }
            C0223c successor = waiter.a;
            if (unpark) {
                if (predecessor != null) {
                    predecessor.a = successor;
                } else {
                    this.p3 = successor;
                }
                waiter.a = null;
                LockSupport.unpark(waiter.b);
            } else {
                predecessor = waiter;
            }
            waiter = successor;
        }
    }

    private SQLiteConnection f1(int connectionFlags) {
        SQLiteConnection connection = this.z4;
        if (connection != null) {
            this.z4 = null;
            s(connection, connectionFlags);
            return connection;
        }
        for (SQLiteConnection acquiredConnection : this.A4.keySet()) {
            if (acquiredConnection.A()) {
                return null;
            }
        }
        SQLiteConnection connection2 = W(this.p0, true);
        s(connection2, connectionFlags);
        return connection2;
    }

    private SQLiteConnection e1(String sql, int connectionFlags) {
        int availableCount = this.p4.size();
        if (availableCount > 1 && sql != null) {
            for (int i = 0; i < availableCount; i++) {
                SQLiteConnection connection = this.p4.get(i);
                if (connection.z(sql)) {
                    this.p4.remove(i);
                    s(connection, connectionFlags);
                    return connection;
                }
            }
        }
        if (availableCount > 0) {
            SQLiteConnection connection2 = this.p4.remove(availableCount - 1);
            s(connection2, connectionFlags);
            return connection2;
        }
        int openConnections = this.A4.size();
        if (this.z4 != null) {
            openConnections++;
        }
        if (openConnections >= this.a1) {
            return null;
        }
        SQLiteConnection connection3 = W(this.p0, false);
        s(connection3, connectionFlags);
        return connection3;
    }

    private void s(SQLiteConnection connection, int connectionFlags) {
        try {
            connection.Q((connectionFlags & 1) != 0);
            this.A4.put(connection, b.NORMAL);
        } catch (RuntimeException ex) {
            Log.a("WCDB.SQLiteConnectionPool", "Failed to prepare acquired connection for session, closing it: " + connection + ", connectionFlags=" + connectionFlags);
            m(connection);
            throw ex;
        }
    }

    private static int t(int connectionFlags) {
        return (connectionFlags & 4) != 0 ? 1 : 0;
    }

    private void b1(int poolSize) {
        if (poolSize <= 0) {
            if ((this.p0.d & 536870912) != 0) {
                poolSize = 4;
            } else {
                poolSize = 1;
            }
        }
        this.a1 = poolSize;
        Log.d("WCDB.SQLiteConnectionPool", "Max connection pool size is %d.", Integer.valueOf(poolSize));
    }

    private void c1() {
        if (!this.p1) {
            throw new IllegalStateException("Cannot perform this operation because the connection pool has been closed.");
        }
    }

    private C0223c I(Thread thread, long startTime, int priority, boolean wantPrimaryConnection, String sql, int connectionFlags) {
        C0223c waiter = this.p2;
        if (waiter != null) {
            this.p2 = waiter.a;
            waiter.a = null;
        } else {
            waiter = new C0223c((a) null);
        }
        waiter.b = thread;
        waiter.c = startTime;
        waiter.d = priority;
        waiter.e = wantPrimaryConnection;
        waiter.f = sql;
        waiter.g = connectionFlags;
        return waiter;
    }

    private void P0(C0223c waiter) {
        waiter.a = this.p2;
        waiter.b = null;
        waiter.f = null;
        waiter.h = null;
        waiter.i = null;
        waiter.j++;
        this.p2 = waiter;
    }

    /* access modifiers changed from: package-private */
    public m u() {
        return this.d;
    }

    /* access modifiers changed from: package-private */
    public void d1(String sql, int type, long time) {
        SQLiteDatabase db = (SQLiteDatabase) this.c.get();
        m trace = this.d;
        if (trace != null && db != null) {
            trace.a(db, sql, type, time);
        }
    }

    /* access modifiers changed from: package-private */
    public void E(String dbName, int pages) {
        SQLiteDatabase db = (SQLiteDatabase) this.c.get();
        a walHook = this.f;
        if (walHook != null && db != null) {
            walHook.a(db, dbName, pages);
        }
    }

    public String toString() {
        return "SQLiteConnectionPool: " + this.p0.a;
    }

    /* renamed from: com.tencent.wcdb.database.c$c  reason: collision with other inner class name */
    /* compiled from: SQLiteConnectionPool */
    public static final class C0223c {
        public C0223c a;
        public Thread b;
        public long c;
        public int d;
        public boolean e;
        public String f;
        public int g;
        public SQLiteConnection h;
        public RuntimeException i;
        public int j;

        private C0223c() {
        }

        /* synthetic */ C0223c(a x0) {
            this();
        }
    }
}
