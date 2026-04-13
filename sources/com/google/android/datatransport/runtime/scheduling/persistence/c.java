package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.Cursor;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class c implements SQLiteEventStore.Function {
    public final /* synthetic */ long a;

    public /* synthetic */ c(long j) {
        this.a = j;
    }

    public final Object apply(Object obj) {
        return ((Cursor) obj).moveToNext();
    }
}
