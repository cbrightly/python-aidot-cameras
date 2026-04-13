package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.scheduling.persistence.SchemaManager;

/* compiled from: lambda */
public final /* synthetic */ class c0 implements SchemaManager.Migration {
    public static final /* synthetic */ c0 a = new c0();

    private /* synthetic */ c0() {
    }

    public final void upgrade(SQLiteDatabase sQLiteDatabase) {
        SchemaManager.lambda$static$3(sQLiteDatabase);
    }
}
