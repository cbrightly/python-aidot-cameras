package com.google.android.datatransport.runtime.scheduling.persistence;

import android.content.Context;
import com.google.android.datatransport.runtime.dagger.internal.Factory;
import javax.inject.a;

public final class SchemaManager_Factory implements Factory<SchemaManager> {
    private final a<Context> contextProvider;
    private final a<String> dbNameProvider;
    private final a<Integer> schemaVersionProvider;

    public SchemaManager_Factory(a<Context> contextProvider2, a<String> dbNameProvider2, a<Integer> schemaVersionProvider2) {
        this.contextProvider = contextProvider2;
        this.dbNameProvider = dbNameProvider2;
        this.schemaVersionProvider = schemaVersionProvider2;
    }

    public SchemaManager get() {
        return newInstance(this.contextProvider.get(), this.dbNameProvider.get(), this.schemaVersionProvider.get().intValue());
    }

    public static SchemaManager_Factory create(a<Context> contextProvider2, a<String> dbNameProvider2, a<Integer> schemaVersionProvider2) {
        return new SchemaManager_Factory(contextProvider2, dbNameProvider2, schemaVersionProvider2);
    }

    public static SchemaManager newInstance(Context context, String dbName, int schemaVersion) {
        return new SchemaManager(context, dbName, schemaVersion);
    }
}
