package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class m implements SQLiteEventStore.Function {
    public final /* synthetic */ long a;

    public /* synthetic */ m(long j) {
        this.a = j;
    }

    public final Object apply(Object obj) {
        return SQLiteEventStore.lambda$getTimeWindow$22(this.a, (SQLiteDatabase) obj);
    }
}
