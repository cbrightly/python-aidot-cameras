package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class x implements SQLiteEventStore.Function {
    public final /* synthetic */ SQLiteEventStore a;
    public final /* synthetic */ long b;

    public /* synthetic */ x(SQLiteEventStore sQLiteEventStore, long j) {
        this.a = sQLiteEventStore;
        this.b = j;
    }

    public final Object apply(Object obj) {
        return this.a.c(this.b, (SQLiteDatabase) obj);
    }
}
