package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.Cursor;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class z implements SQLiteEventStore.Function {
    public static final /* synthetic */ z a = new z();

    private /* synthetic */ z() {
    }

    public final Object apply(Object obj) {
        return SQLiteEventStore.lambda$recordLogEventDropped$17((Cursor) obj);
    }
}
