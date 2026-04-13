package com.tencent.wcdb.database;

import android.database.sqlite.SQLiteTransactionListener;
import android.os.Process;
import com.tencent.wcdb.CursorWindow;
import com.tencent.wcdb.database.SQLiteConnection;
import com.tencent.wcdb.g;

/* compiled from: SQLiteSession */
public final class j {
    private final c a;
    private SQLiteConnection b;
    private int c;
    private int d;
    private b e;
    private b f;

    public j(c connectionPool) {
        if (connectionPool != null) {
            this.a = connectionPool;
            return;
        }
        throw new IllegalArgumentException("connectionPool must not be null");
    }

    public void c(int transactionMode, SQLiteTransactionListener transactionListener, int connectionFlags, com.tencent.wcdb.support.a cancellationSignal) {
        t();
        d(transactionMode, transactionListener, connectionFlags, cancellationSignal);
    }

    private void d(int transactionMode, SQLiteTransactionListener transactionListener, int connectionFlags, com.tencent.wcdb.support.a cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.b();
        }
        if (this.f == null) {
            a((String) null, connectionFlags, cancellationSignal);
        }
        try {
            if (this.f == null) {
                switch (transactionMode) {
                    case 1:
                        this.b.q("BEGIN IMMEDIATE;", (Object[]) null, cancellationSignal);
                        break;
                    case 2:
                        this.b.q("BEGIN EXCLUSIVE;", (Object[]) null, cancellationSignal);
                        break;
                    default:
                        this.b.q("BEGIN;", (Object[]) null, cancellationSignal);
                        break;
                }
            }
            if (transactionListener != null) {
                transactionListener.onBegin();
            }
            b transaction = l(transactionMode, transactionListener);
            transaction.a = this.f;
            this.f = transaction;
            if (transaction == null) {
                o();
            }
        } catch (RuntimeException ex) {
            if (this.f == null) {
                this.b.q("ROLLBACK;", (Object[]) null, cancellationSignal);
            }
            throw ex;
        } catch (Throwable th) {
            if (this.f == null) {
                o();
            }
            throw th;
        }
    }

    public void r() {
        s();
        t();
        this.f.d = true;
    }

    public void e(com.tencent.wcdb.support.a cancellationSignal) {
        s();
        if (this.b != null) {
            f(cancellationSignal, false);
            return;
        }
        throw new AssertionError();
    }

    private void f(com.tencent.wcdb.support.a cancellationSignal, boolean yielding) {
        if (cancellationSignal != null) {
            cancellationSignal.b();
        }
        b top = this.f;
        boolean successful = (top.d || yielding) && !top.e;
        RuntimeException listenerException = null;
        SQLiteTransactionListener listener = top.c;
        if (listener != null) {
            if (successful) {
                try {
                    listener.onCommit();
                } catch (RuntimeException ex) {
                    listenerException = ex;
                    successful = false;
                }
            } else {
                listener.onRollback();
            }
        }
        this.f = top.a;
        n(top);
        b bVar = this.f;
        if (bVar == null) {
            if (successful) {
                try {
                    this.b.q("COMMIT;", (Object[]) null, cancellationSignal);
                } catch (Throwable th) {
                    o();
                    throw th;
                }
            } else {
                this.b.q("ROLLBACK;", (Object[]) null, cancellationSignal);
            }
            o();
        } else if (!successful) {
            bVar.e = true;
        }
        if (listenerException != null) {
            throw listenerException;
        }
    }

    public void m(String sql, int connectionFlags, com.tencent.wcdb.support.a cancellationSignal, l outStatementInfo) {
        if (sql != null) {
            if (cancellationSignal != null) {
                cancellationSignal.b();
            }
            a(sql, connectionFlags, cancellationSignal);
            try {
                this.b.E(sql, outStatementInfo);
            } finally {
                o();
            }
        } else {
            throw new IllegalArgumentException("sql must not be null.");
        }
    }

    public long j(String sql, Object[] bindArgs, int connectionFlags, com.tencent.wcdb.support.a cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        } else if (k(sql, bindArgs, connectionFlags, cancellationSignal)) {
            return 0;
        } else {
            a(sql, connectionFlags, cancellationSignal);
            try {
                return this.b.u(sql, bindArgs, cancellationSignal);
            } finally {
                o();
            }
        }
    }

    public int g(String sql, Object[] bindArgs, int connectionFlags, com.tencent.wcdb.support.a cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        } else if (k(sql, bindArgs, connectionFlags, cancellationSignal)) {
            return 0;
        } else {
            a(sql, connectionFlags, cancellationSignal);
            try {
                return this.b.r(sql, bindArgs, cancellationSignal);
            } finally {
                o();
            }
        }
    }

    public long i(String sql, Object[] bindArgs, int connectionFlags, com.tencent.wcdb.support.a cancellationSignal) {
        if (sql == null) {
            throw new IllegalArgumentException("sql must not be null.");
        } else if (k(sql, bindArgs, connectionFlags, cancellationSignal)) {
            return 0;
        } else {
            a(sql, connectionFlags, cancellationSignal);
            try {
                return this.b.t(sql, bindArgs, cancellationSignal);
            } finally {
                o();
            }
        }
    }

    public int h(String sql, Object[] bindArgs, CursorWindow window, int startPos, int requiredPos, boolean countAllRows, int connectionFlags, com.tencent.wcdb.support.a cancellationSignal) {
        int i = connectionFlags;
        com.tencent.wcdb.support.a aVar = cancellationSignal;
        if (sql == null) {
            Object[] objArr = bindArgs;
            throw new IllegalArgumentException("sql must not be null.");
        } else if (window == null) {
            Object[] objArr2 = bindArgs;
            throw new IllegalArgumentException("window must not be null.");
        } else if (k(sql, bindArgs, i, aVar)) {
            window.clear();
            return 0;
        } else {
            a(sql, i, aVar);
            try {
                return this.b.s(sql, bindArgs, window, startPos, requiredPos, countAllRows, cancellationSignal);
            } finally {
                o();
            }
        }
    }

    private boolean k(String sql, Object[] bindArgs, int connectionFlags, com.tencent.wcdb.support.a cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.b();
        }
        switch (g.e(sql)) {
            case 4:
                c(2, (SQLiteTransactionListener) null, connectionFlags, cancellationSignal);
                return true;
            case 5:
                r();
                e(cancellationSignal);
                return true;
            case 6:
                e(cancellationSignal);
                return true;
            default:
                return false;
        }
    }

    private void a(String sql, int connectionFlags, com.tencent.wcdb.support.a cancellationSignal) {
        if (this.b == null) {
            SQLiteConnection g = this.a.g(sql, connectionFlags, cancellationSignal);
            this.b = g;
            this.c = connectionFlags;
            g.J(Thread.currentThread(), Process.myTid());
        }
        this.d++;
    }

    private void o() {
        int i = this.d - 1;
        this.d = i;
        if (i == 0) {
            try {
                this.b.J((Thread) null, 0);
                this.a.a1(this.b);
            } finally {
                this.b = null;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public SQLiteConnection b(int connectionFlags) {
        a((String) null, connectionFlags, (com.tencent.wcdb.support.a) null);
        return this.b;
    }

    /* access modifiers changed from: package-private */
    public void p(Exception ex) {
        SQLiteConnection sQLiteConnection = this.b;
        if (sQLiteConnection != null) {
            sQLiteConnection.p(ex);
        }
        o();
    }

    /* access modifiers changed from: package-private */
    public void q(SQLiteConnection.d statement) {
        SQLiteConnection sQLiteConnection = this.b;
        if (sQLiteConnection != null) {
            sQLiteConnection.H(statement);
            o();
        }
    }

    private void s() {
        if (this.f == null) {
            throw new IllegalStateException("Cannot perform this operation because there is no current transaction.");
        }
    }

    private void t() {
        b bVar = this.f;
        if (bVar != null && bVar.d) {
            throw new IllegalStateException("Cannot perform this operation because the transaction has already been marked successful.  The only thing you can do now is call endTransaction().");
        }
    }

    private b l(int mode, SQLiteTransactionListener listener) {
        b transaction = this.e;
        if (transaction != null) {
            this.e = transaction.a;
            transaction.a = null;
            transaction.d = false;
            transaction.e = false;
        } else {
            transaction = new b();
        }
        transaction.b = mode;
        transaction.c = listener;
        return transaction;
    }

    private void n(b transaction) {
        transaction.a = this.e;
        transaction.c = null;
        this.e = transaction;
    }

    /* compiled from: SQLiteSession */
    public static final class b {
        public b a;
        public int b;
        public SQLiteTransactionListener c;
        public boolean d;
        public boolean e;

        private b() {
        }
    }
}
