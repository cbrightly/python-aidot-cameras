package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import com.google.android.datatransport.runtime.TransportContext;
import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;

/* compiled from: lambda */
public final /* synthetic */ class h implements SynchronizationGuard.CriticalSection {
    public final /* synthetic */ Uploader a;
    public final /* synthetic */ Iterable b;
    public final /* synthetic */ TransportContext c;
    public final /* synthetic */ long d;

    public /* synthetic */ h(Uploader uploader, Iterable iterable, TransportContext transportContext, long j) {
        this.a = uploader;
        this.b = iterable;
        this.c = transportContext;
        this.d = j;
    }

    public final Object execute() {
        this.a.c(this.b, this.c, this.d);
        return null;
    }
}
