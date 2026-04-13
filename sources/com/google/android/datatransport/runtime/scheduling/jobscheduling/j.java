package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;

/* compiled from: lambda */
public final /* synthetic */ class j implements SynchronizationGuard.CriticalSection {
    public final /* synthetic */ Uploader a;
    public final /* synthetic */ Iterable b;

    public /* synthetic */ j(Uploader uploader, Iterable iterable) {
        this.a = uploader;
        this.b = iterable;
    }

    public final Object execute() {
        this.a.d(this.b);
        return null;
    }
}
