package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.Cursor;
import com.google.android.datatransport.runtime.firebase.transport.ClientMetrics;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class v implements SQLiteEventStore.Function {
    public final /* synthetic */ SQLiteEventStore a;
    public final /* synthetic */ Map b;
    public final /* synthetic */ ClientMetrics.Builder c;

    public /* synthetic */ v(SQLiteEventStore sQLiteEventStore, Map map, ClientMetrics.Builder builder) {
        this.a = sQLiteEventStore;
        this.b = map;
        this.c = builder;
    }

    public final Object apply(Object obj) {
        return this.a.j(this.b, this.c, (Cursor) obj);
    }
}
