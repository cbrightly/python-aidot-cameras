package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.TransportContext;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class f implements SQLiteEventStore.Function {
    public final /* synthetic */ long a;
    public final /* synthetic */ TransportContext b;

    public /* synthetic */ f(long j, TransportContext transportContext) {
        this.a = j;
        this.b = transportContext;
    }

    public final Object apply(Object obj) {
        SQLiteEventStore.lambda$recordNextCallTime$7(this.a, this.b, (SQLiteDatabase) obj);
        return null;
    }
}
