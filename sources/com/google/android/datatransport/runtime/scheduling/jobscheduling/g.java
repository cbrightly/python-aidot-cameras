package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import com.google.android.datatransport.runtime.TransportContext;
import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;

/* compiled from: lambda */
public final /* synthetic */ class g implements SynchronizationGuard.CriticalSection {
    public final /* synthetic */ Uploader a;
    public final /* synthetic */ TransportContext b;

    public /* synthetic */ g(Uploader uploader, TransportContext transportContext) {
        this.a = uploader;
        this.b = transportContext;
    }

    public final Object execute() {
        return this.a.b(this.b);
    }
}
