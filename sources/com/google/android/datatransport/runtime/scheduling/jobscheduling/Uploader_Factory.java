package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import android.content.Context;
import com.google.android.datatransport.runtime.backends.BackendRegistry;
import com.google.android.datatransport.runtime.dagger.internal.Factory;
import com.google.android.datatransport.runtime.scheduling.persistence.ClientHealthMetricsStore;
import com.google.android.datatransport.runtime.scheduling.persistence.EventStore;
import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;
import com.google.android.datatransport.runtime.time.Clock;
import java.util.concurrent.Executor;
import javax.inject.a;

public final class Uploader_Factory implements Factory<Uploader> {
    private final a<BackendRegistry> backendRegistryProvider;
    private final a<ClientHealthMetricsStore> clientHealthMetricsStoreProvider;
    private final a<Clock> clockProvider;
    private final a<Context> contextProvider;
    private final a<EventStore> eventStoreProvider;
    private final a<Executor> executorProvider;
    private final a<SynchronizationGuard> guardProvider;
    private final a<Clock> uptimeClockProvider;
    private final a<WorkScheduler> workSchedulerProvider;

    public Uploader_Factory(a<Context> contextProvider2, a<BackendRegistry> backendRegistryProvider2, a<EventStore> eventStoreProvider2, a<WorkScheduler> workSchedulerProvider2, a<Executor> executorProvider2, a<SynchronizationGuard> guardProvider2, a<Clock> clockProvider2, a<Clock> uptimeClockProvider2, a<ClientHealthMetricsStore> clientHealthMetricsStoreProvider2) {
        this.contextProvider = contextProvider2;
        this.backendRegistryProvider = backendRegistryProvider2;
        this.eventStoreProvider = eventStoreProvider2;
        this.workSchedulerProvider = workSchedulerProvider2;
        this.executorProvider = executorProvider2;
        this.guardProvider = guardProvider2;
        this.clockProvider = clockProvider2;
        this.uptimeClockProvider = uptimeClockProvider2;
        this.clientHealthMetricsStoreProvider = clientHealthMetricsStoreProvider2;
    }

    public Uploader get() {
        return newInstance(this.contextProvider.get(), this.backendRegistryProvider.get(), this.eventStoreProvider.get(), this.workSchedulerProvider.get(), this.executorProvider.get(), this.guardProvider.get(), this.clockProvider.get(), this.uptimeClockProvider.get(), this.clientHealthMetricsStoreProvider.get());
    }

    public static Uploader_Factory create(a<Context> contextProvider2, a<BackendRegistry> backendRegistryProvider2, a<EventStore> eventStoreProvider2, a<WorkScheduler> workSchedulerProvider2, a<Executor> executorProvider2, a<SynchronizationGuard> guardProvider2, a<Clock> clockProvider2, a<Clock> uptimeClockProvider2, a<ClientHealthMetricsStore> clientHealthMetricsStoreProvider2) {
        return new Uploader_Factory(contextProvider2, backendRegistryProvider2, eventStoreProvider2, workSchedulerProvider2, executorProvider2, guardProvider2, clockProvider2, uptimeClockProvider2, clientHealthMetricsStoreProvider2);
    }

    public static Uploader newInstance(Context context, BackendRegistry backendRegistry, EventStore eventStore, WorkScheduler workScheduler, Executor executor, SynchronizationGuard guard, Clock clock, Clock uptimeClock, ClientHealthMetricsStore clientHealthMetricsStore) {
        return new Uploader(context, backendRegistry, eventStore, workScheduler, executor, guard, clock, uptimeClock, clientHealthMetricsStore);
    }
}
