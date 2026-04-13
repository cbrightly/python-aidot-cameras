package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import com.google.android.datatransport.runtime.TransportContext;
import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;

/* compiled from: lambda */
public final /* synthetic */ class k implements SynchronizationGuard.CriticalSection {
    public final /* synthetic */ Uploader a;
    public final /* synthetic */ TransportContext b;
    public final /* synthetic */ int c;

    public /* synthetic */ k(Uploader uploader, TransportContext transportContext, int i) {
        this.a = uploader;
        this.b = transportContext;
        this.c = i;
    }

    public final Object execute() {
        this.a.h(this.b, this.c);
        return null;
    }
}
