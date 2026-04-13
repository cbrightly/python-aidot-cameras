package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import com.google.android.datatransport.runtime.TransportContext;
import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;

/* compiled from: lambda */
public final /* synthetic */ class e implements SynchronizationGuard.CriticalSection {
    public final /* synthetic */ Uploader a;
    public final /* synthetic */ TransportContext b;

    public /* synthetic */ e(Uploader uploader, TransportContext transportContext) {
        this.a = uploader;
        this.b = transportContext;
    }

    public final Object execute() {
        return this.a.a(this.b);
    }
}
