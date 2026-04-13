package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import com.google.android.datatransport.runtime.scheduling.persistence.EventStore;
import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;

/* compiled from: lambda */
public final /* synthetic */ class b implements SynchronizationGuard.CriticalSection {
    public final /* synthetic */ EventStore a;

    public /* synthetic */ b(EventStore eventStore) {
        this.a = eventStore;
    }

    public final Object execute() {
        return Integer.valueOf(this.a.cleanUp());
    }
}
