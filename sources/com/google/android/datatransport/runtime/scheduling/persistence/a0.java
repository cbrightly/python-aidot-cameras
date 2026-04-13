package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.scheduling.persistence.SchemaManager;

/* compiled from: lambda */
public final /* synthetic */ class a0 implements SchemaManager.Migration {
    public static final /* synthetic */ a0 a = new a0();

    private /* synthetic */ a0() {
    }

    public final void upgrade(SQLiteDatabase sQLiteDatabase) {
        SchemaManager.lambda$static$1(sQLiteDatabase);
    }
}
