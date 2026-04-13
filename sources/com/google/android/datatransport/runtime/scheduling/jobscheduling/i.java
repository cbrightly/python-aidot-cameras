package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import com.google.android.datatransport.runtime.TransportContext;
import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;

/* compiled from: lambda */
public final /* synthetic */ class i implements SynchronizationGuard.CriticalSection {
    public final /* synthetic */ Uploader a;
    public final /* synthetic */ TransportContext b;
    public final /* synthetic */ long c;

    public /* synthetic */ i(Uploader uploader, TransportContext transportContext, long j) {
        this.a = uploader;
        this.b = transportContext;
        this.c = j;
    }

    public final Object execute() {
        this.a.g(this.b, this.c);
        return null;
    }
}
