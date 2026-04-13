package com.tencent.wcdb.database;

public class SQLiteBlobTooBigException extends SQLiteException {
    public SQLiteBlobTooBigException() {
    }

    public SQLiteBlobTooBigException(String error) {
        super(error);
    }
}
