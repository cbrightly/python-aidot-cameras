package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import com.google.android.datatransport.runtime.scheduling.persistence.ClientHealthMetricsStore;
import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;

/* compiled from: lambda */
public final /* synthetic */ class o implements SynchronizationGuard.CriticalSection {
    public final /* synthetic */ ClientHealthMetricsStore a;

    public /* synthetic */ o(ClientHealthMetricsStore clientHealthMetricsStore) {
        this.a = clientHealthMetricsStore;
    }

    public final Object execute() {
        return this.a.loadClientMetrics();
    }
}
