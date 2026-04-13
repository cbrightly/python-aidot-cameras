package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard;

/* compiled from: lambda */
public final /* synthetic */ class l implements SynchronizationGuard.CriticalSection {
    public final /* synthetic */ Uploader a;

    public /* synthetic */ l(Uploader uploader) {
        this.a = uploader;
    }

    public final Object execute() {
        this.a.e();
        return null;
    }
}
