package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.Cursor;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class w implements SQLiteEventStore.Function {
    public final /* synthetic */ SQLiteEventStore a;

    public /* synthetic */ w(SQLiteEventStore sQLiteEventStore) {
        this.a = sQLiteEventStore;
    }

    public final Object apply(Object obj) {
        this.a.o((Cursor) obj);
        return null;
    }
}
