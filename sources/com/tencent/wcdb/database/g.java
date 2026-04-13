package com.tencent.wcdb.database;

import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.e;
import com.tencent.wcdb.support.a;

/* compiled from: SQLiteDirectCursorDriver */
public final class g implements e {
    private static SQLiteDatabase.b a = d.z4;
    private final SQLiteDatabase b;
    private final String c;
    private final String d;
    private final a e;
    private h f;

    public g(SQLiteDatabase db, String sql, String editTable, a cancellationSignal) {
        this.b = db;
        this.c = editTable;
        this.d = sql;
        this.e = cancellationSignal;
    }

    public e a(SQLiteDatabase.b factory, Object[] bindArgs) {
        if (factory == null) {
            factory = a;
        }
        h query = null;
        try {
            h query2 = factory.b(this.b, this.d, bindArgs, this.e);
            e cursor = factory.a(this.b, this, this.c, query2);
            this.f = query2;
            return cursor;
        } catch (RuntimeException ex) {
            if (query != null) {
                query.close();
            }
            throw ex;
        }
    }

    public void cursorClosed() {
    }

    public void cursorDeactivated() {
    }

    public void b(e cursor) {
    }

    public String toString() {
        return "SQLiteDirectCursorDriver: " + this.d;
    }
}
