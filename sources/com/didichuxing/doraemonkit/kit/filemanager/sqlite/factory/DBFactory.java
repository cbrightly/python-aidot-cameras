package com.didichuxing.doraemonkit.kit.filemanager.sqlite.factory;

import android.content.Context;
import com.didichuxing.doraemonkit.kit.filemanager.sqlite.dao.SQLiteDB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DBFactory.kt */
public interface DBFactory {
    @NotNull
    SQLiteDB create(@NotNull Context context, @NotNull String str, @Nullable String str2);
}
