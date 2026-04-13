package com.didichuxing.doraemonkit.kit.filemanager.sqlite.dao;

import android.content.ContentValues;
import android.database.Cursor;
import com.tencent.wcdb.database.SQLiteDatabase;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: EncryptSQLiteDB.kt */
public final class EncryptSQLiteDB implements SQLiteDB {
    private final SQLiteDatabase database;

    public EncryptSQLiteDB(@NotNull SQLiteDatabase database2) {
        k.f(database2, "database");
        this.database = database2;
    }

    public int delete(@NotNull String table, @NotNull String whereClause, @NotNull String[] whereArgs) {
        k.f(table, "table");
        k.f(whereClause, "whereClause");
        k.f(whereArgs, "whereArgs");
        return this.database.m(table, whereClause, whereArgs);
    }

    public boolean isOpen() {
        return this.database.isOpen();
    }

    public void close() {
        this.database.close();
    }

    @Nullable
    public Cursor rawQuery(@NotNull String sql, @Nullable String[] selectionArgs) {
        k.f(sql, "sql");
        return this.database.u0(sql, selectionArgs);
    }

    public void execSQL(@NotNull String sql) {
        k.f(sql, "sql");
        this.database.execSQL(sql);
    }

    public long insert(@NotNull String table, @Nullable String nullColumnHack, @NotNull ContentValues values) {
        k.f(table, "table");
        k.f(values, "values");
        return this.database.v(table, nullColumnHack, values);
    }

    public int update(@NotNull String table, @NotNull ContentValues values, @NotNull String whereClause, @NotNull String[] whereArgs) {
        k.f(table, "table");
        k.f(values, "values");
        k.f(whereClause, "whereClause");
        k.f(whereArgs, "whereArgs");
        return this.database.b1(table, values, whereClause, whereArgs);
    }

    public int getVersion() {
        return this.database.getVersion();
    }
}
