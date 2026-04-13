package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.Cursor;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class g implements SQLiteEventStore.Function {
    public static final /* synthetic */ g a = new g();

    private /* synthetic */ g() {
    }

    public final Object apply(Object obj) {
        return SQLiteEventStore.lambda$readPayload$15((Cursor) obj);
    }
}
