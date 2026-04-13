package com.google.android.datatransport.runtime.scheduling;

import android.content.Context;
import com.google.android.datatransport.runtime.dagger.internal.Factory;
import com.google.android.datatransport.runtime.dagger.internal.Preconditions;
import com.google.android.datatransport.runtime.scheduling.jobscheduling.SchedulerConfig;
import com.google.android.datatransport.runtime.scheduling.jobscheduling.WorkScheduler;
import com.google.android.datatransport.runtime.scheduling.persistence.EventStore;
import com.google.android.datatransport.runtime.time.Clock;
import javax.inject.a;

public final class SchedulingModule_WorkSchedulerFactory implements Factory<WorkScheduler> {
    private final a<Clock> clockProvider;
    private final a<SchedulerConfig> configProvider;
    private final a<Context> contextProvider;
    private final a<EventStore> eventStoreProvider;

    public SchedulingModule_WorkSchedulerFactory(a<Context> contextProvider2, a<EventStore> eventStoreProvider2, a<SchedulerConfig> configProvider2, a<Clock> clockProvider2) {
        this.contextProvider = contextProvider2;
        this.eventStoreProvider = eventStoreProvider2;
        this.configProvider = configProvider2;
        this.clockProvider = clockProvider2;
    }

    public WorkScheduler get() {
        return workScheduler(this.contextProvider.get(), this.eventStoreProvider.get(), this.configProvider.get(), this.clockProvider.get());
    }

    public static SchedulingModule_WorkSchedulerFactory create(a<Context> contextProvider2, a<EventStore> eventStoreProvider2, a<SchedulerConfig> configProvider2, a<Clock> clockProvider2) {
        return new SchedulingModule_WorkSchedulerFactory(contextProvider2, eventStoreProvider2, configProvider2, clockProvider2);
    }

    public static WorkScheduler workScheduler(Context context, EventStore eventStore, SchedulerConfig config, Clock clock) {
        return (WorkScheduler) Preconditions.checkNotNull(SchedulingModule.workScheduler(context, eventStore, config, clock), "Cannot return null from a non-@Nullable @Provides method");
    }
}
