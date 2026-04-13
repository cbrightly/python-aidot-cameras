package com.tencent.wcdb.database;

import com.tencent.wcdb.SQLException;

public class SQLiteException extends SQLException {
    public SQLiteException() {
    }

    public SQLiteException(String error) {
        super(error);
    }

    public SQLiteException(String error, Throwable cause) {
        super(error, cause);
    }
}
