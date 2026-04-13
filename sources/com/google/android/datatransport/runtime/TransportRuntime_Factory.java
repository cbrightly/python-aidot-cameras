package com.google.android.datatransport.runtime;

import com.google.android.datatransport.runtime.dagger.internal.Factory;
import com.google.android.datatransport.runtime.scheduling.Scheduler;
import com.google.android.datatransport.runtime.scheduling.jobscheduling.Uploader;
import com.google.android.datatransport.runtime.scheduling.jobscheduling.WorkInitializer;
import com.google.android.datatransport.runtime.time.Clock;
import javax.inject.a;

public final class TransportRuntime_Factory implements Factory<TransportRuntime> {
    private final a<Clock> eventClockProvider;
    private final a<WorkInitializer> initializerProvider;
    private final a<Scheduler> schedulerProvider;
    private final a<Uploader> uploaderProvider;
    private final a<Clock> uptimeClockProvider;

    public TransportRuntime_Factory(a<Clock> eventClockProvider2, a<Clock> uptimeClockProvider2, a<Scheduler> schedulerProvider2, a<Uploader> uploaderProvider2, a<WorkInitializer> initializerProvider2) {
        this.eventClockProvider = eventClockProvider2;
        this.uptimeClockProvider = uptimeClockProvider2;
        this.schedulerProvider = schedulerProvider2;
        this.uploaderProvider = uploaderProvider2;
        this.initializerProvider = initializerProvider2;
    }

    public TransportRuntime get() {
        return newInstance(this.eventClockProvider.get(), this.uptimeClockProvider.get(), this.schedulerProvider.get(), this.uploaderProvider.get(), this.initializerProvider.get());
    }

    public static TransportRuntime_Factory create(a<Clock> eventClockProvider2, a<Clock> uptimeClockProvider2, a<Scheduler> schedulerProvider2, a<Uploader> uploaderProvider2, a<WorkInitializer> initializerProvider2) {
        return new TransportRuntime_Factory(eventClockProvider2, uptimeClockProvider2, schedulerProvider2, uploaderProvider2, initializerProvider2);
    }

    public static TransportRuntime newInstance(Clock eventClock, Clock uptimeClock, Scheduler scheduler, Uploader uploader, WorkInitializer initializer) {
        return new TransportRuntime(eventClock, uptimeClock, scheduler, uploader, initializer);
    }
}
