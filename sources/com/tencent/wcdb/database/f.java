package com.tencent.wcdb.database;

import java.util.ArrayList;
import java.util.Locale;
import net.sqlcipher.database.SQLiteDatabase;

/* compiled from: SQLiteDatabaseConfiguration */
public final class f {
    public final String a;
    public final String b;
    public String c;
    public int d;
    public int e;
    public Locale f;
    public boolean g;
    public boolean h;
    public int i;
    public boolean j;
    public boolean k;
    public final ArrayList<SQLiteCustomFunction> l = new ArrayList<>();

    public f(String path, int openFlags) {
        if (path != null) {
            this.a = path;
            this.b = path;
            this.d = openFlags;
            this.i = 2;
            this.e = 25;
            this.f = Locale.getDefault();
            this.c = (openFlags & 256) != 0 ? "vfslog" : null;
            return;
        }
        throw new IllegalArgumentException("path must not be null.");
    }

    public f(f other) {
        if (other != null) {
            this.a = other.a;
            this.b = other.b;
            b(other);
            return;
        }
        throw new IllegalArgumentException("other must not be null.");
    }

    public void b(f other) {
        if (other == null) {
            throw new IllegalArgumentException("other must not be null.");
        } else if (this.a.equals(other.a)) {
            this.d = other.d;
            this.e = other.e;
            this.f = other.f;
            this.g = other.g;
            this.h = other.h;
            this.j = other.j;
            this.k = other.k;
            this.i = other.i;
            this.c = other.c;
            this.l.clear();
            this.l.addAll(other.l);
        } else {
            throw new IllegalArgumentException("other configuration must refer to the same database.");
        }
    }

    public boolean a() {
        return this.a.equalsIgnoreCase(SQLiteDatabase.MEMORY);
    }
}
