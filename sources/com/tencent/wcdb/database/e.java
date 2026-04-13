package com.tencent.wcdb.database;

import com.tencent.wcdb.database.SQLiteDatabase;

/* compiled from: SQLiteCursorDriver */
public interface e {
    com.tencent.wcdb.e a(SQLiteDatabase.b bVar, Object[] objArr);

    void b(com.tencent.wcdb.e eVar);

    void cursorClosed();

    void cursorDeactivated();
}
