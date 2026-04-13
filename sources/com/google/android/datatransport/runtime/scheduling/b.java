package com.google.android.datatransport.runtime.scheduling;

import com.google.android.datatransport.runtime.EventInternal;
import com.google.android.datatransport.runtime.TransportContext;
import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;

/* compiled from: lambda */
public final /* synthetic */ class b implements SynchronizationGuard.CriticalSection {
    public final /* synthetic */ DefaultScheduler a;
    public final /* synthetic */ TransportContext b;
    public final /* synthetic */ EventInternal c;

    public /* synthetic */ b(DefaultScheduler defaultScheduler, TransportContext transportContext, EventInternal eventInternal) {
        this.a = defaultScheduler;
        this.b = transportContext;
        this.c = eventInternal;
    }

    public final Object execute() {
        this.a.a(this.b, this.c);
        return null;
    }
}
