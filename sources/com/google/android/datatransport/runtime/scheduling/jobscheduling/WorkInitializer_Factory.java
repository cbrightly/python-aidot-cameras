package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import com.google.android.datatransport.runtime.dagger.internal.Factory;
import com.google.android.datatransport.runtime.scheduling.persistence.EventStore;
import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;
import java.util.concurrent.Executor;
import javax.inject.a;

public final class WorkInitializer_Factory implements Factory<WorkInitializer> {
    private final a<Executor> executorProvider;
    private final a<SynchronizationGuard> guardProvider;
    private final a<WorkScheduler> schedulerProvider;
    private final a<EventStore> storeProvider;

    public WorkInitializer_Factory(a<Executor> executorProvider2, a<EventStore> storeProvider2, a<WorkScheduler> schedulerProvider2, a<SynchronizationGuard> guardProvider2) {
        this.executorProvider = executorProvider2;
        this.storeProvider = storeProvider2;
        this.schedulerProvider = schedulerProvider2;
        this.guardProvider = guardProvider2;
    }

    public WorkInitializer get() {
        return newInstance(this.executorProvider.get(), this.storeProvider.get(), this.schedulerProvider.get(), this.guardProvider.get());
    }

    public static WorkInitializer_Factory create(a<Executor> executorProvider2, a<EventStore> storeProvider2, a<WorkScheduler> schedulerProvider2, a<SynchronizationGuard> guardProvider2) {
        return new WorkInitializer_Factory(executorProvider2, storeProvider2, schedulerProvider2, guardProvider2);
    }

    public static WorkInitializer newInstance(Executor executor, EventStore store, WorkScheduler scheduler, SynchronizationGuard guard) {
        return new WorkInitializer(executor, store, scheduler, guard);
    }
}
