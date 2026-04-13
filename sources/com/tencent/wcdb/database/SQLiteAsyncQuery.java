package com.tencent.wcdb.database;

public class SQLiteAsyncQuery extends h {
    private static native int nativeCount(long j);

    private static native int nativeFillRows(long j, long j2, int i, int i2);
}
