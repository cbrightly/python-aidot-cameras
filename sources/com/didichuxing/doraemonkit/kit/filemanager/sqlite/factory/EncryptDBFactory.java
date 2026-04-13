package com.didichuxing.doraemonkit.kit.filemanager.sqlite.factory;

import android.content.Context;
import com.didichuxing.doraemonkit.kit.filemanager.sqlite.dao.EncryptSQLiteDB;
import com.didichuxing.doraemonkit.kit.filemanager.sqlite.dao.SQLiteDB;
import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.f;
import kotlin.jvm.internal.k;
import kotlin.text.c;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: EncryptDBFactory.kt */
public final class EncryptDBFactory implements DBFactory {
    @NotNull
    public SQLiteDB create(@NotNull Context context, @NotNull String path, @Nullable String password) {
        byte[] bArr;
        k.f(context, "context");
        k.f(path, "path");
        if (password != null) {
            bArr = password.getBytes(c.a);
            k.b(bArr, "(this as java.lang.String).getBytes(charset)");
        } else {
            bArr = null;
        }
        SQLiteDatabase o0 = SQLiteDatabase.o0(path, bArr, (SQLiteDatabase.b) null, (f) null, 1);
        k.b(o0, "SQLiteDatabase.openOrCre…teArray(), null, null, 1)");
        return new EncryptSQLiteDB(o0);
    }
}
