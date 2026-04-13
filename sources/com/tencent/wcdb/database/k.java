package com.tencent.wcdb.database;

import com.tencent.wcdb.support.a;

/* compiled from: SQLiteStatement */
public final class k extends h {
    k(SQLiteDatabase db, String sql, Object[] bindArgs) {
        super(db, sql, bindArgs, (a) null);
    }

    public int executeUpdateDelete() {
        return v((a) null);
    }

    public int v(a cancellationSignal) {
        a();
        try {
            int g = r().g(s(), m(), n(), cancellationSignal);
            g();
            return g;
        } catch (SQLiteDatabaseCorruptException ex) {
            l(ex);
            throw ex;
        } catch (Throwable th) {
            g();
            throw th;
        }
    }

    public long executeInsert() {
        return u((a) null);
    }

    public long u(a cancellationSignal) {
        a();
        try {
            long i = r().i(s(), m(), n(), cancellationSignal);
            g();
            return i;
        } catch (SQLiteDatabaseCorruptException ex) {
            l(ex);
            throw ex;
        } catch (Throwable th) {
            g();
            throw th;
        }
    }

    public long simpleQueryForLong() {
        return x((a) null);
    }

    public long x(a cancellationSignal) {
        a();
        try {
            long j = r().j(s(), m(), n(), cancellationSignal);
            g();
            return j;
        } catch (SQLiteDatabaseCorruptException ex) {
            l(ex);
            throw ex;
        } catch (Throwable th) {
            g();
            throw th;
        }
    }

    public String toString() {
        return "SQLiteProgram: " + s();
    }
}
