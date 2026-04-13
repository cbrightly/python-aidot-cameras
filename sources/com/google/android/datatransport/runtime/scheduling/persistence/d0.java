package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.scheduling.persistence.SchemaManager;

/* compiled from: lambda */
public final /* synthetic */ class d0 implements SchemaManager.Migration {
    public static final /* synthetic */ d0 a = new d0();

    private /* synthetic */ d0() {
    }

    public final void upgrade(SQLiteDatabase sQLiteDatabase) {
        SchemaManager.lambda$static$0(sQLiteDatabase);
    }
}
