package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.Cursor;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class u implements SQLiteEventStore.Function {
    public final /* synthetic */ Map a;

    public /* synthetic */ u(Map map) {
        this.a = map;
    }

    public final Object apply(Object obj) {
        SQLiteEventStore.lambda$loadMetadata$16(this.a, (Cursor) obj);
        return null;
    }
}
