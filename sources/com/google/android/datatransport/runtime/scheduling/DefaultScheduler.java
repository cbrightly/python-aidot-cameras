package com.google.android.datatransport.runtime.scheduling;

import com.google.android.datatransport.TransportScheduleCallback;
import com.google.android.datatransport.runtime.EventInternal;
import com.google.android.datatransport.runtime.TransportContext;
import com.google.android.datatransport.runtime.TransportRuntime;
import com.google.android.datatransport.runtime.backends.BackendRegistry;
import com.google.android.datatransport.runtime.backends.TransportBackend;
import com.google.android.datatransport.runtime.scheduling.jobscheduling.WorkScheduler;
import com.google.android.datatransport.runtime.scheduling.persistence.EventStore;
import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

public class DefaultScheduler implements Scheduler {
    private static final Logger LOGGER = Logger.getLogger(TransportRuntime.class.getName());
    private final BackendRegistry backendRegistry;
    private final EventStore eventStore;
    private final Executor executor;
    private final SynchronizationGuard guard;
    private final WorkScheduler workScheduler;

    public /* synthetic */ Object a(TransportContext transportContext, EventInternal eventInternal) {
        lambda$schedule$0(transportContext, eventInternal);
        return null;
    }

    public DefaultScheduler(Executor executor2, BackendRegistry backendRegistry2, WorkScheduler workScheduler2, EventStore eventStore2, SynchronizationGuard guard2) {
        this.executor = executor2;
        this.backendRegistry = backendRegistry2;
        this.workScheduler = workScheduler2;
        this.eventStore = eventStore2;
        this.guard = guard2;
    }

    public void schedule(TransportContext transportContext, EventInternal event, TransportScheduleCallback callback) {
        this.executor.execute(new a(this, transportContext, callback, event));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$schedule$1 */
    public /* synthetic */ void b(TransportContext transportContext, TransportScheduleCallback callback, EventInternal event) {
        try {
            TransportBackend transportBackend = this.backendRegistry.get(transportContext.getBackendName());
            if (transportBackend == null) {
                String errorMsg = String.format("Transport backend '%s' is not registered", new Object[]{transportContext.getBackendName()});
                LOGGER.warning(errorMsg);
                callback.onSchedule(new IllegalArgumentException(errorMsg));
                return;
            }
            this.guard.runCriticalSection(new b(this, transportContext, transportBackend.decorate(event)));
            callback.onSchedule((Exception) null);
        } catch (Exception e) {
            Logger logger = LOGGER;
            logger.warning("Error scheduling event " + e.getMessage());
            callback.onSchedule(e);
        }
    }

    private /* synthetic */ Object lambda$schedule$0(TransportContext transportContext, EventInternal decoratedEvent) {
        this.eventStore.persist(transportContext, decoratedEvent);
        this.workScheduler.schedule(transportContext, 1);
        return null;
    }
}
