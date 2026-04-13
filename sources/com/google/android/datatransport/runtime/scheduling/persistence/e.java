package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class e implements SQLiteEventStore.Producer {
    public final /* synthetic */ SQLiteDatabase a;

    public /* synthetic */ e(SQLiteDatabase sQLiteDatabase) {
        this.a = sQLiteDatabase;
    }

    public final Object produce() {
        Object unused = this.a.beginTransaction();
        return null;
    }
}
