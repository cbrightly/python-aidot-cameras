package com.tencent.wcdb.database;

import com.tencent.wcdb.CursorWindow;
import com.tencent.wcdb.support.Log;
import com.tencent.wcdb.support.a;

/* compiled from: SQLiteQuery */
public final class i extends h {
    private final a a2;

    i(SQLiteDatabase db, String query, Object[] bindArgs, a cancellationSignal) {
        super(db, query, bindArgs, cancellationSignal);
        this.a2 = cancellationSignal;
    }

    /* access modifiers changed from: package-private */
    public int u(CursorWindow window, int startPos, int requiredPos, boolean countAllRows) {
        a();
        try {
            window.a();
            int numRows = r().h(s(), m(), window, startPos, requiredPos, countAllRows, n(), this.a2);
            window.g();
            g();
            return numRows;
        } catch (SQLiteException ex) {
            Log.a("WCDB.SQLiteQuery", "exception: " + ex.getMessage() + "; query: " + s());
            l(ex);
            throw ex;
        } catch (Throwable th) {
            g();
            throw th;
        }
    }

    public String toString() {
        return "SQLiteQuery: " + s();
    }
}
