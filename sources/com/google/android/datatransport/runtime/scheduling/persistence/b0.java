package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.scheduling.persistence.SchemaManager;

/* compiled from: lambda */
public final /* synthetic */ class b0 implements SchemaManager.Migration {
    public static final /* synthetic */ b0 a = new b0();

    private /* synthetic */ b0() {
    }

    public final void upgrade(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("ALTER TABLE events ADD COLUMN payload_encoding TEXT");
    }
}
