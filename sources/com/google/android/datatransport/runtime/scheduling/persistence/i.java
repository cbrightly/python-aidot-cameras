package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.Cursor;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class i implements SQLiteEventStore.Function {
    public static final /* synthetic */ i a = new i();

    private /* synthetic */ i() {
    }

    public final Object apply(Object obj) {
        return SQLiteEventStore.lambda$loadActiveContexts$9((Cursor) obj);
    }
}
