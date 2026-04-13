package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class q implements SQLiteEventStore.Function {
    public final /* synthetic */ SQLiteEventStore a;

    public /* synthetic */ q(SQLiteEventStore sQLiteEventStore) {
        this.a = sQLiteEventStore;
    }

    public final Object apply(Object obj) {
        this.a.s((SQLiteDatabase) obj);
        return null;
    }
}
