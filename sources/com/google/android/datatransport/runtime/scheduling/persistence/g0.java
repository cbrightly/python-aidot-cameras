package com.google.android.datatransport.runtime.scheduling.persistence;

import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
public final /* synthetic */ class g0 implements SQLiteEventStore.Producer {
    public final /* synthetic */ SchemaManager a;

    public /* synthetic */ g0(SchemaManager schemaManager) {
        this.a = schemaManager;
    }

    public final Object produce() {
        return this.a.getWritableDatabase();
    }
}
