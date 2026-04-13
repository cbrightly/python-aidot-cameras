package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class y implements SQLiteEventStore.Function {
    public static final /* synthetic */ y a = new y();

    private /* synthetic */ y() {
    }

    public final Object apply(Object obj) {
        return SQLiteEventStore.lambda$loadActiveContexts$10((SQLiteDatabase) obj);
    }
}
