package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class h implements SQLiteEventStore.Function {
    public static final /* synthetic */ h a = new h();

    private /* synthetic */ h() {
    }

    public final Object apply(Object obj) {
        SQLiteEventStore.lambda$clearDb$13((SQLiteDatabase) obj);
        return null;
    }
}
