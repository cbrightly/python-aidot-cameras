package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.TransportContext;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class l implements SQLiteEventStore.Function {
    public final /* synthetic */ SQLiteEventStore a;
    public final /* synthetic */ TransportContext b;

    public /* synthetic */ l(SQLiteEventStore sQLiteEventStore, TransportContext transportContext) {
        this.a = sQLiteEventStore;
        this.b = transportContext;
    }

    public final Object apply(Object obj) {
        return this.a.g(this.b, (SQLiteDatabase) obj);
    }
}
