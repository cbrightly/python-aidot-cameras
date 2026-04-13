package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.firebase.transport.LogEventDropped;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class j implements SQLiteEventStore.Function {
    public final /* synthetic */ String a;
    public final /* synthetic */ LogEventDropped.Reason b;
    public final /* synthetic */ long c;

    public /* synthetic */ j(String str, LogEventDropped.Reason reason, long j) {
        this.a = str;
        this.b = reason;
        this.c = j;
    }

    public final Object apply(Object obj) {
        SQLiteEventStore.lambda$recordLogEventDropped$18(this.a, this.b, this.c, (SQLiteDatabase) obj);
        return null;
    }
}
