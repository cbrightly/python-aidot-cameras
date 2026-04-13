package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.firebase.transport.ClientMetrics;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class o implements SQLiteEventStore.Function {
    public final /* synthetic */ SQLiteEventStore a;
    public final /* synthetic */ String b;
    public final /* synthetic */ Map c;
    public final /* synthetic */ ClientMetrics.Builder d;

    public /* synthetic */ o(SQLiteEventStore sQLiteEventStore, String str, Map map, ClientMetrics.Builder builder) {
        this.a = sQLiteEventStore;
        this.b = str;
        this.c = map;
        this.d = builder;
    }

    public final Object apply(Object obj) {
        return this.a.l(this.b, this.c, this.d, (SQLiteDatabase) obj);
    }
}
