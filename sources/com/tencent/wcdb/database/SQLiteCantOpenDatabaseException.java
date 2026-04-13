package com.tencent.wcdb.database;

public class SQLiteCantOpenDatabaseException extends SQLiteException {
    public SQLiteCantOpenDatabaseException() {
    }

    public SQLiteCantOpenDatabaseException(String error) {
        super(error);
    }
}
