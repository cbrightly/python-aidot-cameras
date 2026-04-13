package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.EventInternal;
import com.google.android.datatransport.runtime.TransportContext;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class t implements SQLiteEventStore.Function {
    public final /* synthetic */ SQLiteEventStore a;
    public final /* synthetic */ EventInternal b;
    public final /* synthetic */ TransportContext c;

    public /* synthetic */ t(SQLiteEventStore sQLiteEventStore, EventInternal eventInternal, TransportContext transportContext) {
        this.a = sQLiteEventStore;
        this.b = eventInternal;
        this.c = transportContext;
    }

    public final Object apply(Object obj) {
        return this.a.n(this.b, this.c, (SQLiteDatabase) obj);
    }
}
