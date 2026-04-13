package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.scheduling.persistence.SchemaManager;

/* compiled from: lambda */
public final /* synthetic */ class e0 implements SchemaManager.Migration {
    public static final /* synthetic */ e0 a = new e0();

    private /* synthetic */ e0() {
    }

    public final void upgrade(SQLiteDatabase sQLiteDatabase) {
        SchemaManager.lambda$static$4(sQLiteDatabase);
    }
}
