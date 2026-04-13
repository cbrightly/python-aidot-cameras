package com.didichuxing.doraemonkit.kit.filemanager.sqlite.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NormalSQLiteDB.kt */
public final class NormalSQLiteDB implements SQLiteDB {
    private final SQLiteDatabase database;

    public NormalSQLiteDB(@NotNull SQLiteDatabase database2) {
        k.f(database2, "database");
        this.database = database2;
    }

    public int delete(@NotNull String table, @NotNull String whereClause, @NotNull String[] whereArgs) {
        k.f(table, "table");
        k.f(whereClause, "whereClause");
        k.f(whereArgs, "whereArgs");
        return this.database.delete(table, whereClause, whereArgs);
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
        return this.database.rawQuery(sql, selectionArgs);
    }

    public void execSQL(@NotNull String sql) {
        k.f(sql, "sql");
        this.database.execSQL(sql);
    }

    public long insert(@NotNull String table, @Nullable String nullColumnHack, @NotNull ContentValues values) {
        k.f(table, "table");
        k.f(values, "values");
        return this.database.insert(table, nullColumnHack, values);
    }

    public int update(@NotNull String table, @NotNull ContentValues values, @NotNull String whereClause, @NotNull String[] whereArgs) {
        k.f(table, "table");
        k.f(values, "values");
        k.f(whereClause, "whereClause");
        k.f(whereArgs, "whereArgs");
        return this.database.update(table, values, whereClause, whereArgs);
    }

    public int getVersion() {
        return this.database.getVersion();
    }
}
