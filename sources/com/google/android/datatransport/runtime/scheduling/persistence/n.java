package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.Cursor;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class n implements SQLiteEventStore.Function {
    public static final /* synthetic */ n a = new n();

    private /* synthetic */ n() {
    }

    public final Object apply(Object obj) {
        return SQLiteEventStore.lambda$getNextCallTime$5((Cursor) obj);
    }
}
