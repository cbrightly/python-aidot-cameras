package com.google.android.datatransport.runtime;

import android.content.Context;
import com.google.android.datatransport.runtime.TransportRuntimeComponent;
import com.google.android.datatransport.runtime.backends.CreationContextFactory_Factory;
import com.google.android.datatransport.runtime.backends.MetadataBackendRegistry_Factory;
import com.google.android.datatransport.runtime.dagger.internal.DoubleCheck;
import com.google.android.datatransport.runtime.dagger.internal.Factory;
import com.google.android.datatransport.runtime.dagger.internal.InstanceFactory;
import com.google.android.datatransport.runtime.dagger.internal.Preconditions;
import com.google.android.datatransport.runtime.scheduling.DefaultScheduler;
import com.google.android.datatransport.runtime.scheduling.DefaultScheduler_Factory;
import com.google.android.datatransport.runtime.scheduling.SchedulingConfigModule_ConfigFactory;
import com.google.android.datatransport.runtime.scheduling.SchedulingModule_WorkSchedulerFactory;
import com.google.android.datatransport.runtime.scheduling.jobscheduling.SchedulerConfig;
import com.google.android.datatransport.runtime.scheduling.jobscheduling.Uploader;
import com.google.android.datatransport.runtime.scheduling.jobscheduling.Uploader_Factory;
import com.google.android.datatransport.runtime.scheduling.jobscheduling.WorkInitializer;
import com.google.android.datatransport.runtime.scheduling.jobscheduling.WorkInitializer_Factory;
import com.google.android.datatransport.runtime.scheduling.jobscheduling.WorkScheduler;
import com.google.android.datatransport.runtime.scheduling.persistence.EventStore;
import com.google.android.datatransport.runtime.scheduling.persistence.EventStoreModule_DbNameFactory;
import com.google.android.datatransport.runtime.scheduling.persistence.EventStoreModule_PackageNameFactory;
import com.google.android.datatransport.runtime.scheduling.persistence.EventStoreModule_SchemaVersionFactory;
import com.google.android.datatransport.runtime.scheduling.persistence.EventStoreModule_StoreConfigFactory;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore_Factory;
import com.google.android.datatransport.runtime.scheduling.persistence.SchemaManager_Factory;
import com.google.android.datatransport.runtime.time.TimeModule_EventClockFactory;
import com.google.android.datatransport.runtime.time.TimeModule_UptimeClockFactory;
import java.util.concurrent.Executor;
import javax.inject.a;

public final class DaggerTransportRuntimeComponent extends TransportRuntimeComponent {
    private a<SchedulerConfig> configProvider;
    private a creationContextFactoryProvider;
    private a<DefaultScheduler> defaultSchedulerProvider;
    private a<Executor> executorProvider;
    private a metadataBackendRegistryProvider;
    private a<String> packageNameProvider;
    private a<SQLiteEventStore> sQLiteEventStoreProvider;
    private a schemaManagerProvider;
    private a<Context> setApplicationContextProvider;
    private a<TransportRuntime> transportRuntimeProvider;
    private a<Uploader> uploaderProvider;
    private a<WorkInitializer> workInitializerProvider;
    private a<WorkScheduler> workSchedulerProvider;

    private DaggerTransportRuntimeComponent(Context setApplicationContextParam) {
        initialize(setApplicationContextParam);
    }

    public static TransportRuntimeComponent.Builder builder() {
        return new Builder();
    }

    private void initialize(Context setApplicationContextParam) {
        this.executorProvider = DoubleCheck.provider(ExecutionModule_ExecutorFactory.create());
        Factory create = InstanceFactory.create(setApplicationContextParam);
        this.setApplicationContextProvider = create;
        CreationContextFactory_Factory create2 = CreationContextFactory_Factory.create(create, TimeModule_EventClockFactory.create(), TimeModule_UptimeClockFactory.create());
        this.creationContextFactoryProvider = create2;
        this.metadataBackendRegistryProvider = DoubleCheck.provider(MetadataBackendRegistry_Factory.create(this.setApplicationContextProvider, create2));
        this.schemaManagerProvider = SchemaManager_Factory.create(this.setApplicationContextProvider, EventStoreModule_DbNameFactory.create(), EventStoreModule_SchemaVersionFactory.create());
        this.packageNameProvider = DoubleCheck.provider(EventStoreModule_PackageNameFactory.create(this.setApplicationContextProvider));
        this.sQLiteEventStoreProvider = DoubleCheck.provider(SQLiteEventStore_Factory.create(TimeModule_EventClockFactory.create(), TimeModule_UptimeClockFactory.create(), EventStoreModule_StoreConfigFactory.create(), this.schemaManagerProvider, this.packageNameProvider));
        SchedulingConfigModule_ConfigFactory create3 = SchedulingConfigModule_ConfigFactory.create(TimeModule_EventClockFactory.create());
        this.configProvider = create3;
        SchedulingModule_WorkSchedulerFactory create4 = SchedulingModule_WorkSchedulerFactory.create(this.setApplicationContextProvider, this.sQLiteEventStoreProvider, create3, TimeModule_UptimeClockFactory.create());
        this.workSchedulerProvider = create4;
        a<Executor> aVar = this.executorProvider;
        a aVar2 = this.metadataBackendRegistryProvider;
        a<SQLiteEventStore> aVar3 = this.sQLiteEventStoreProvider;
        this.defaultSchedulerProvider = DefaultScheduler_Factory.create(aVar, aVar2, create4, aVar3, aVar3);
        a<Context> aVar4 = this.setApplicationContextProvider;
        a aVar5 = this.metadataBackendRegistryProvider;
        a<SQLiteEventStore> aVar6 = this.sQLiteEventStoreProvider;
        this.uploaderProvider = Uploader_Factory.create(aVar4, aVar5, aVar6, this.workSchedulerProvider, this.executorProvider, aVar6, TimeModule_EventClockFactory.create(), TimeModule_UptimeClockFactory.create(), this.sQLiteEventStoreProvider);
        a<Executor> aVar7 = this.executorProvider;
        a<SQLiteEventStore> aVar8 = this.sQLiteEventStoreProvider;
        this.workInitializerProvider = WorkInitializer_Factory.create(aVar7, aVar8, this.workSchedulerProvider, aVar8);
        this.transportRuntimeProvider = DoubleCheck.provider(TransportRuntime_Factory.create(TimeModule_EventClockFactory.create(), TimeModule_UptimeClockFactory.create(), this.defaultSchedulerProvider, this.uploaderProvider, this.workInitializerProvider));
    }

    /* access modifiers changed from: package-private */
    public TransportRuntime getTransportRuntime() {
        return this.transportRuntimeProvider.get();
    }

    /* access modifiers changed from: package-private */
    public EventStore getEventStore() {
        return this.sQLiteEventStoreProvider.get();
    }

    public static final class Builder implements TransportRuntimeComponent.Builder {
        private Context setApplicationContext;

        private Builder() {
        }

        public Builder setApplicationContext(Context applicationContext) {
            this.setApplicationContext = (Context) Preconditions.checkNotNull(applicationContext);
            return this;
        }

        public TransportRuntimeComponent build() {
            Preconditions.checkBuilderRequirement(this.setApplicationContext, Context.class);
            return new DaggerTransportRuntimeComponent(this.setApplicationContext);
        }
    }
}
