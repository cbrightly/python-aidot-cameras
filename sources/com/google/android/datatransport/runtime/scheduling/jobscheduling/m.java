package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;

/* compiled from: lambda */
public final /* synthetic */ class m implements SynchronizationGuard.CriticalSection {
    public final /* synthetic */ WorkInitializer a;

    public /* synthetic */ m(WorkInitializer workInitializer) {
        this.a = workInitializer;
    }

    public final Object execute() {
        this.a.a();
        return null;
    }
}
