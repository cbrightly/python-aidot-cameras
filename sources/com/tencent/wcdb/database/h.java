package com.tencent.wcdb.database;

import com.tencent.wcdb.database.SQLiteConnection;
import com.tencent.wcdb.g;
import com.tencent.wcdb.support.a;
import java.util.Arrays;

/* compiled from: SQLiteProgram */
public abstract class h extends b {
    private static final String[] d = new String[0];
    protected SQLiteConnection.d a1;
    private final SQLiteDatabase f;
    private final Object[] p0;
    private j p1;
    private final String q;
    private final boolean x;
    private final String[] y;
    private final int z;

    protected h(SQLiteDatabase db, String sql, Object[] bindArgs, a cancellationSignalForPrepare) {
        this.f = db;
        String trim = sql.trim();
        this.q = trim;
        int n = g.e(trim);
        switch (n) {
            case 4:
            case 5:
            case 6:
                this.x = false;
                this.y = d;
                this.z = 0;
                break;
            default:
                boolean assumeReadOnly = n != 1 ? false : true;
                l info = new l();
                db.t().m(trim, db.s(assumeReadOnly), cancellationSignalForPrepare, info);
                this.x = info.c;
                this.y = info.b;
                this.z = info.a;
                break;
        }
        if (bindArgs == null || bindArgs.length <= this.z) {
            int i = this.z;
            if (i != 0) {
                Object[] objArr = new Object[i];
                this.p0 = objArr;
                if (bindArgs != null) {
                    System.arraycopy(bindArgs, 0, objArr, 0, bindArgs.length);
                }
            } else {
                this.p0 = null;
            }
            this.a1 = null;
            this.p1 = null;
            return;
        }
        throw new IllegalArgumentException("Too many bind arguments.  " + bindArgs.length + " arguments were provided but the statement needs " + this.z + " arguments.");
    }

    public final SQLiteDatabase o() {
        return this.f;
    }

    /* access modifiers changed from: protected */
    public final String s() {
        return this.q;
    }

    /* access modifiers changed from: protected */
    public final Object[] m() {
        return this.p0;
    }

    public final String[] getColumnNames() {
        return this.y;
    }

    /* access modifiers changed from: protected */
    public final j r() {
        return this.f.t();
    }

    /* access modifiers changed from: protected */
    public final int n() {
        return this.f.s(this.x);
    }

    /* access modifiers changed from: protected */
    public final void l(SQLiteException e) {
        boolean isCorruption = false;
        if (e instanceof SQLiteDatabaseCorruptException) {
            isCorruption = true;
        } else if ((e instanceof SQLiteFullException) && this.x) {
            isCorruption = true;
        }
        if (isCorruption) {
            SQLiteDebug.b(this.f);
            this.f.I();
        }
    }

    public void bindString(int index, String value) {
        if (value != null) {
            i(index, value);
            return;
        }
        throw new IllegalArgumentException("the bind value at index " + index + " is null");
    }

    public void clearBindings() {
        Object[] objArr = this.p0;
        if (objArr != null) {
            Arrays.fill(objArr, (Object) null);
        }
    }

    public void j(String[] bindArgs) {
        if (bindArgs != null) {
            for (int i = bindArgs.length; i != 0; i--) {
                bindString(i, bindArgs[i - 1]);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void c() {
        t();
        clearBindings();
    }

    private void i(int index, Object value) {
        if (index < 1 || index > this.z) {
            throw new IllegalArgumentException("Cannot bind argument at index " + index + " because the index is out of range.  The statement has " + this.z + " parameters.");
        }
        this.p0[index - 1] = value;
    }

    /* access modifiers changed from: protected */
    public synchronized void t() {
        j jVar = this.p1;
        if (jVar != null || this.a1 != null) {
            if (jVar != null) {
                if (this.a1 != null) {
                    if (jVar == this.f.t()) {
                        this.p1.q(this.a1);
                        this.a1 = null;
                        this.p1 = null;
                        return;
                    }
                    throw new IllegalStateException("SQLiteProgram has bound to another thread.");
                }
            }
            throw new IllegalStateException("Internal state error.");
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        synchronized (this) {
            if (this.p1 != null || this.a1 != null) {
                throw new SQLiteMisuseException("Acquired prepared statement is not released.");
            }
        }
        super.finalize();
    }
}
