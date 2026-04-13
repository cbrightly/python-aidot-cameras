package com.didichuxing.doraemonkit.kit.filemanager.sqlite.factory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.didichuxing.doraemonkit.kit.filemanager.sqlite.dao.NormalSQLiteDB;
import com.didichuxing.doraemonkit.kit.filemanager.sqlite.dao.SQLiteDB;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NormalDBFactory.kt */
public final class NormalDBFactory implements DBFactory {
    @NotNull
    public SQLiteDB create(@NotNull Context context, @NotNull String path, @Nullable String password) {
        k.f(context, "context");
        k.f(path, "path");
        SQLiteDatabase openOrCreateDatabase = SQLiteDatabase.openOrCreateDatabase(path, (SQLiteDatabase.CursorFactory) null);
        k.b(openOrCreateDatabase, "SQLiteDatabase.openOrCreateDatabase(path, null)");
        return new NormalSQLiteDB(openOrCreateDatabase);
    }
}
