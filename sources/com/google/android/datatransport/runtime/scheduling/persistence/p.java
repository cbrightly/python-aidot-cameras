package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.Cursor;
import com.google.android.datatransport.runtime.TransportContext;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;
import java.util.List;

/* compiled from: lambda */
public final /* synthetic */ class p implements SQLiteEventStore.Function {
    public final /* synthetic */ SQLiteEventStore a;
    public final /* synthetic */ List b;
    public final /* synthetic */ TransportContext c;

    public /* synthetic */ p(SQLiteEventStore sQLiteEventStore, List list, TransportContext transportContext) {
        this.a = sQLiteEventStore;
        this.b = list;
        this.c = transportContext;
    }

    public final Object apply(Object obj) {
        this.a.m(this.b, this.c, (Cursor) obj);
        return null;
    }
}
