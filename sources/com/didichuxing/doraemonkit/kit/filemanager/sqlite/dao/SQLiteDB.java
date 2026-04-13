package com.didichuxing.doraemonkit.kit.filemanager.sqlite.dao;

import android.content.ContentValues;
import android.database.Cursor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SQLiteDB.kt */
public interface SQLiteDB {
    void close();

    int delete(@NotNull String str, @NotNull String str2, @NotNull String[] strArr);

    void execSQL(@NotNull String str);

    int getVersion();

    long insert(@NotNull String str, @Nullable String str2, @NotNull ContentValues contentValues);

    boolean isOpen();

    @Nullable
    Cursor rawQuery(@NotNull String str, @Nullable String[] strArr);

    int update(@NotNull String str, @NotNull ContentValues contentValues, @NotNull String str2, @NotNull String[] strArr);
}
