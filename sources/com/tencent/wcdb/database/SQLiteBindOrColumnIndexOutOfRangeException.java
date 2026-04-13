package com.tencent.wcdb.database;

public class SQLiteBindOrColumnIndexOutOfRangeException extends SQLiteException {
    public SQLiteBindOrColumnIndexOutOfRangeException() {
    }

    public SQLiteBindOrColumnIndexOutOfRangeException(String error) {
        super(error);
    }
}
