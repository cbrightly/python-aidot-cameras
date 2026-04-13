package com.google.android.datatransport.runtime.scheduling.persistence;

import com.google.android.datatransport.runtime.dagger.internal.Factory;
import com.google.android.datatransport.runtime.time.Clock;
import javax.inject.a;

public final class SQLiteEventStore_Factory implements Factory<SQLiteEventStore> {
    private final a<Clock> clockProvider;
    private final a<EventStoreConfig> configProvider;
    private final a<String> packageNameProvider;
    private final a<SchemaManager> schemaManagerProvider;
    private final a<Clock> wallClockProvider;

    public SQLiteEventStore_Factory(a<Clock> wallClockProvider2, a<Clock> clockProvider2, a<EventStoreConfig> configProvider2, a<SchemaManager> schemaManagerProvider2, a<String> packageNameProvider2) {
        this.wallClockProvider = wallClockProvider2;
        this.clockProvider = clockProvider2;
        this.configProvider = configProvider2;
        this.schemaManagerProvider = schemaManagerProvider2;
        this.packageNameProvider = packageNameProvider2;
    }

    public SQLiteEventStore get() {
        return newInstance(this.wallClockProvider.get(), this.clockProvider.get(), this.configProvider.get(), this.schemaManagerProvider.get(), this.packageNameProvider);
    }

    public static SQLiteEventStore_Factory create(a<Clock> wallClockProvider2, a<Clock> clockProvider2, a<EventStoreConfig> configProvider2, a<SchemaManager> schemaManagerProvider2, a<String> packageNameProvider2) {
        return new SQLiteEventStore_Factory(wallClockProvider2, clockProvider2, configProvider2, schemaManagerProvider2, packageNameProvider2);
    }

    public static SQLiteEventStore newInstance(Clock wallClock, Clock clock, Object config, Object schemaManager, a<String> packageName) {
        return new SQLiteEventStore(wallClock, clock, (EventStoreConfig) config, (SchemaManager) schemaManager, packageName);
    }
}
