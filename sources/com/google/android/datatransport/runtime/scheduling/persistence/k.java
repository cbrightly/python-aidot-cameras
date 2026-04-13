package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.Cursor;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class k implements SQLiteEventStore.Function {
    public final /* synthetic */ SQLiteEventStore a;

    public /* synthetic */ k(SQLiteEventStore sQLiteEventStore) {
        this.a = sQLiteEventStore;
    }

    public final Object apply(Object obj) {
        this.a.a((Cursor) obj);
        return null;
    }
}
