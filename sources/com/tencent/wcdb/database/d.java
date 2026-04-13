package com.tencent.wcdb.database;

import com.tencent.wcdb.CursorWindow;
import com.tencent.wcdb.b;
import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.e;
import com.tencent.wcdb.g;
import com.tencent.wcdb.support.Log;
import java.util.HashMap;
import java.util.Map;

/* compiled from: SQLiteCursor */
public class d extends b {
    public static final SQLiteDatabase.b z4 = new a();
    private final String A4;
    private final String[] B4;
    private final i C4;
    private final e D4;
    private int E4 = -1;
    private int F4;
    private Map<String, Integer> G4;
    private final Throwable H4;

    public d(e driver, String editTable, i query) {
        if (query != null) {
            this.H4 = null;
            this.D4 = driver;
            this.A4 = editTable;
            this.G4 = null;
            this.C4 = query;
            String[] columnNames = query.getColumnNames();
            this.B4 = columnNames;
            this.f = g.d(columnNames);
            return;
        }
        throw new IllegalArgumentException("query object cannot be null");
    }

    public SQLiteDatabase m() {
        return this.C4.o();
    }

    public boolean onMove(int oldPosition, int newPosition) {
        CursorWindow cursorWindow = this.p4;
        if (cursorWindow != null && newPosition >= cursorWindow.u() && newPosition < this.p4.u() + this.p4.s()) {
            return true;
        }
        l(newPosition);
        return true;
    }

    public int getCount() {
        if (this.E4 == -1) {
            l(0);
        }
        return this.E4;
    }

    private void l(int requiredPos) {
        i(m().getPath());
        try {
            if (this.E4 == -1) {
                this.E4 = this.C4.u(this.p4, g.b(requiredPos, 0), requiredPos, true);
                this.F4 = this.p4.s();
                return;
            }
            this.C4.u(this.p4, g.b(requiredPos, this.F4), requiredPos, false);
        } catch (RuntimeException ex) {
            j();
            throw ex;
        }
    }

    public int getColumnIndex(String columnName) {
        if (this.G4 == null) {
            String[] columns = this.B4;
            int columnCount = columns.length;
            HashMap<String, Integer> map = new HashMap<>(columnCount, 1.0f);
            for (int i = 0; i < columnCount; i++) {
                map.put(columns[i], Integer.valueOf(i));
            }
            this.G4 = map;
        }
        int periodIndex = columnName.lastIndexOf(46);
        if (periodIndex != -1) {
            Exception e = new Exception();
            Log.b("WCDB.SQLiteCursor", "requesting column name with table name -- " + columnName, e);
            columnName = columnName.substring(periodIndex + 1);
        }
        Integer i2 = this.G4.get(columnName);
        if (i2 != null) {
            return i2.intValue();
        }
        return -1;
    }

    public String[] getColumnNames() {
        return this.B4;
    }

    public void deactivate() {
        super.deactivate();
        this.D4.cursorDeactivated();
    }

    public void close() {
        super.close();
        synchronized (this) {
            this.C4.close();
            this.D4.cursorClosed();
        }
    }

    public boolean requery() {
        if (isClosed()) {
            return false;
        }
        synchronized (this) {
            if (!this.C4.o().isOpen()) {
                return false;
            }
            CursorWindow cursorWindow = this.p4;
            if (cursorWindow != null) {
                cursorWindow.clear();
            }
            this.d = -1;
            this.E4 = -1;
            this.D4.b(this);
            try {
                return super.requery();
            } catch (IllegalStateException e) {
                Log.g("WCDB.SQLiteCursor", "requery() failed " + e.getMessage(), e);
                return false;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            if (this.p4 != null) {
                close();
            }
        } finally {
            super.finalize();
        }
    }

    /* compiled from: SQLiteCursor */
    public static final class a implements SQLiteDatabase.b {
        a() {
        }

        public e a(SQLiteDatabase db, e masterQuery, String editTable, h query) {
            return new d(masterQuery, editTable, (i) query);
        }

        public h b(SQLiteDatabase db, String query, Object[] bindArgs, com.tencent.wcdb.support.a cancellationSignalForPrepare) {
            return new i(db, query, bindArgs, cancellationSignalForPrepare);
        }
    }
}
