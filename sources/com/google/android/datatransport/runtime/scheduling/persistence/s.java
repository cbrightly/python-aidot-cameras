package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class s implements SQLiteEventStore.Function {
    public final /* synthetic */ SQLiteEventStore a;
    public final /* synthetic */ String b;
    public final /* synthetic */ String c;

    public /* synthetic */ s(SQLiteEventStore sQLiteEventStore, String str, String str2) {
        this.a = sQLiteEventStore;
        this.b = str;
        this.c = str2;
    }

    public final Object apply(Object obj) {
        this.a.r(this.b, this.c, (SQLiteDatabase) obj);
        return null;
    }
}
