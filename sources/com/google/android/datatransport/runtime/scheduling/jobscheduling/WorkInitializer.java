package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import com.google.android.datatransport.runtime.TransportContext;
import com.google.android.datatransport.runtime.scheduling.persistence.EventStore;
import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;
import java.util.concurrent.Executor;

public class WorkInitializer {
    private final Executor executor;
    private final SynchronizationGuard guard;
    private final WorkScheduler scheduler;
    private final EventStore store;

    public /* synthetic */ Object a() {
        lambda$ensureContextsScheduled$0();
        return null;
    }

    WorkInitializer(Executor executor2, EventStore store2, WorkScheduler scheduler2, SynchronizationGuard guard2) {
        this.executor = executor2;
        this.store = store2;
        this.scheduler = scheduler2;
        this.guard = guard2;
    }

    public void ensureContextsScheduled() {
        this.executor.execute(new n(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$ensureContextsScheduled$1 */
    public /* synthetic */ void b() {
        this.guard.runCriticalSection(new m(this));
    }

    private /* synthetic */ Object lambda$ensureContextsScheduled$0() {
        for (TransportContext context : this.store.loadActiveContexts()) {
            this.scheduler.schedule(context, 1);
        }
        return null;
    }
}
